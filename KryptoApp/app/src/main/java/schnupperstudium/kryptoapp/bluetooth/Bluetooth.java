package schnupperstudium.kryptoapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import schnupperstudium.kryptoapp.MainActivity;


public class Bluetooth {

    private Handler handler;
    public final static String READ_TAG = "read";
    public final static int KEY_RECIEVED_WHAT = 1;
    public final static int RCV_FAIL_WHAT = 4;
    public final static int CIPHER_RECIEVED_WHAT = 2;
    public final static int SEND_WHAT = 3;
    public final static int SEND_FAILED_WHAT = 5;
    public final static int RCV_CONNECTED_WHAT = 6;
    public final static int CONNCTION_WHAT = 7;

    private int what;

    //BluetoothConnectionService bluetoothConnectionService;

    public Bluetooth(Handler handler, int what) {
        this.handler =  handler;
        this.what = what;
    }

    private BluetoothAdapter getBluetoothAdapter() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(!adapter.isEnabled()) {
            adapter.enable();
        }
        return adapter;
    }

    public void bluetoothEnable() {
        if(!getBluetoothAdapter().isEnabled()){
            getBluetoothAdapter().enable();
        }
    }

    public Set<BluetoothDevice> getPairDevices () {
        BluetoothAdapter adapter = getBluetoothAdapter();
        if(adapter == null) {
            return null;
        }
        return adapter.getBondedDevices();
    }

    private BluetoothDevice getDeviceByName(String name) {
        for(BluetoothDevice d : getPairDevices()){
            if(d.getName().equals(name)){
                return d;
            }
        }
        return  null;
    }
    public boolean send(String deviceName, String msg) {
        Log.d("Bluetooth", "Send");
        BluetoothDevice device = getDeviceByName(deviceName);
        if(device == null){
            return false;
        }
        ConnectThread connectThread = new ConnectThread(device,msg);
        connectThread.start();
        return true;
    }

    public void connectAsServer () {
        Log.d("Bluetooth", "Server");
        AcceptThread acceptConnection = new AcceptThread();
        acceptConnection.start();
        //ConnectedThread connectedThread = new ConnectedThread(acceptConnection.mmSocket);
        //connectedThread.start();
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String msg;

        public ConnectThread(BluetoothDevice device, String msg) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;
            this.msg = msg;


            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(new UUID(1,1));
            } catch (IOException e) {
                Log.e("BLUETOOTH ERROR", "Socket's create() method failed", e);
            }
            Log.d("Bluetooth", "Socket Established");
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
                ConnectedThread connectedThread = new ConnectedThread(mmSocket);
                //connectedThread.run();
                try {
                    connectedThread.write(msg.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Bluetooth", "FALSE");
                    handler.sendEmptyMessage(Bluetooth.SEND_FAILED_WHAT);
                }

            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                    Log.d("Bluetooth", "SocketClosed by run", connectException);
                } catch (IOException closeException) {
                    Log.e("BLUETOOTH ERROR", "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
                Log.d("Bluetooth", "close Socket in ConnectThread");
            } catch (IOException e) {
                Log.e("BLUETOOTH ERROR", "Could not close the client socket", e);
            }
        }
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;
        private  BluetoothSocket mmSocket;
        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket
            // because mmServerSocket is final.
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code.
                BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Schnupperstudium", new UUID(1,1));
                Message msg = handler.obtainMessage(RCV_CONNECTED_WHAT);
                msg.sendToTarget();
            } catch (IOException e) {
                Message msg = handler.obtainMessage(RCV_FAIL_WHAT);
                msg.sendToTarget();
                tmp = null;
            }
            Log.d("Bluetooth", "AcceptThread ");
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned.
            while (true) {
                try {
                    if(! (mmServerSocket == null)){
                        socket = mmServerSocket.accept();
                    } else {
                        return;
                    }
                    Log.d("Bluetooth", "Accepted: " + socket.getRemoteDevice().getName());
                } catch (IOException e) {
                    Log.e("BLUETOOTH ERROR", "Socket's accept() method failed", e);
                    break;
                }

                if (socket != null) {
                    // A connection was accepted. Perform work associated with
                    // the connection in a separate thread.
                    //manageMyConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            mmSocket = socket;
            ConnectedThread connectedThread = new ConnectedThread(socket);
            connectedThread.start();
        }

        // Closes the connect socket and causes the thread to finish.
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e("BLUETOOTH ERROR", "Could not close the connect socket", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e("Bluetooth Error", "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("Bluetooth Error", "Error occurred when creating output stream", e);
            }
            handler.sendEmptyMessage(CONNCTION_WHAT);
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes = 1024; // bytes returned from read()
            String result = "";

            // Keep listening to the InputStream until an exception occurs.
            try {
                while ((numBytes = mmInStream.read(mmBuffer)) != -1) {
                    result += new String(mmBuffer, "UTF-8").substring(0, numBytes);//TODO -1?
                    Log.d("Bluetooth", "Recieved: " + result);
                }
            } catch (IOException e) {
                Log.d("Bluetooth", "Fehler:  "  + e.getMessage());
            }
            if(result == "") {
                handler.sendEmptyMessage(Bluetooth.RCV_FAIL_WHAT);
                return;
            }
            Message msg = handler.obtainMessage(what);
            Bundle bundle = new Bundle();
            bundle.putString(READ_TAG, result);
            msg.setData(bundle);
            msg.sendToTarget();
            Log.d("Bluetooth", "Send to Target: " + result + " what " + what);
        }

        // Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) throws IOException {
            try {
                mmOutStream.write(bytes);
                Log.d("Bluetooth", "written");
                Message msg = handler.obtainMessage(SEND_WHAT);
                msg.sendToTarget();
                cancel();
                // Share the sent message with the UI activity.
                /**Message writtenMsg = handler.obtainMessage(
                        MessageConstants    .MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();**/
            } catch (IOException e) {
                Message msg = handler.obtainMessage(SEND_FAILED_WHAT);
                msg.sendToTarget();
                cancel();
            }
            Log.d("Bluetooth", "Send: " + new String(bytes, "UTF-8"));
        }

        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
                Log.d("Bluetooth", "SocketClosed");
            } catch (IOException e) {
                Log.e("Bluetooth Error", "Could not close the connect socket", e);
            }
        }
    }
}
