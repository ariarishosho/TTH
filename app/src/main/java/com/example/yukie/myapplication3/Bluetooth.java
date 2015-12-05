package com.example.yukie.myapplication3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

/**
 * Created by yukie on 2015/12/05.
 */
public class Bluetooth extends Thread {
//        // The local server socket
//        private final BluetoothServerSocket mmServerSocket;
//
//        public Bluetooth() {
//            BluetoothServerSocket tmp = null;
//
//            // Create a new listening server socket
//            try {
//                BluetoothAdapter mAdapter;
//                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
//            } catch (IOException e) {
//             //   Log.e(TAG, "listen() failed", e);
//            }
//            mmServerSocket = tmp;
//        }
//
//        public void run() {
//            if (D)
//                //Log.d(TAG, "BEGIN mAcceptThread" + this)
//                ;
//            setName("AcceptThread");
//            BluetoothSocket socket = null;
//
//            // Listen to the server socket if we're not connected
//            while (mState != STATE_CONNECTED) {
//                try {
//                    // This is a blocking call and will only return on a
//                    // successful connection or an exception
//                    socket = mmServerSocket.accept();
//                } catch (IOException e) {
//                   // Log.e(TAG, "accept() failed", e);
//                    break;
//                }
//
//                // If a connection was accepted
//                if (socket != null) {
//                    synchronized (BluetoothChatService.this) {
//                        switch (mState) {
//                            case STATE_LISTEN:
//                            case STATE_CONNECTING:
//                                // Situation normal. Start the connected thread.
//                                connected(socket, socket.getRemoteDevice());
//                                break;
//                            case STATE_NONE:
//                            case STATE_CONNECTED:
//                                // Either not ready or already connected. Terminate new socket.
//                                try {
//                                    socket.close();
//                                } catch (IOException e) {
//                                   // Log.e(TAG, "Could not close unwanted socket", e);
//                                }
//                                break;
//                        }
//                    }
//                }
//            }
//            if (D) //Log.i(TAG, "END mAcceptThread")
//            ;
//        }
//
//        public void cancel() {
//            if (D)//Log.d(TAG, "cancel " + this)
//            ;
//            try {
//                mmServerSocket.close();
//            } catch (IOException e) {
//               // Log.e(TAG, "close() of server failed", e);
//            }
//        }
//    }
}
