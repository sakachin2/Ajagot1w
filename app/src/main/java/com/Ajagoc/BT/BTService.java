//*CID://+1AebR~:                             update#=   57;       //~1AebR~
//**********************************************************************//~@@@@I~
//1Aeb 2015/07/26 When Ahasv BT conecction active Ajagoc BT connection fail by "RFCOMM_CreateConnection - already opened state:2, RFC state:4, MCB state:5"//~1AebI~
//                try close Accept socket just after acceped.  -->not work//+1AebI~
//                try different UUID                           -->Not work//+1AebI~
//                change connection faile msg.                     //+1AebI~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//2015/07/23 //1AbZ 2015/07/05 BT:try for pairing loop;cancel discovery not before connect but after connected//~1AbNI~
//2015/07/23 //1AbN 2015/07/03 BT:(BUG)"ASSERT btif_dm.c unhandled search devicve"(cancelDiscovery at not discovering status)//~1AbNI~
//2015/07/23 //1AbK 2015/07/02 1AbJ popup dialog to accept duartion change,so do not call stopDiscover//~1AbKI~
//2015/07/23 //1AbJ 2015/07/02 stop discoverable when connection complete//~1AbJI~
//2015/07/23 //1AbF 2015/06/28 Bluetooth ramins status of Pairing for InSecure connection;try same NAME for both Secure and Insecure case//~1AbFI~
//2015/07/23 //1Abt 2015/06/18 BT:Accept not both of Secure/Insecure but one of two.//~1AbcI~
//2015/07/23 //1Abc 2015/06/15 BT:no need to issue accept for connection request side//~1AbcI~
//2015/07/23 //1Abb 2015/06/15 NFCBT:try insecure only(if accepting both,connect insecure request is accepted by secure socket)
//1102:130123 bluetooth became unconnectable after some stop operation//~@@@@I~
//**********************************************************************//~@@@@I~
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.Ajagoc.BT;                                             //~@@@@I~

import jagoclient.Dump;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.util.Log;                                         //~@@@@R~
import android.os.ParcelUuid;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
//public class BluetoothChatService {                              //~@@@@R~
public class BTService {                                           //~@@@@I~
    // Debugging
//    private static final String TAG = "BluetoothChatService";
//    private static final boolean D = true;

    // Name for the SDP record when creating server socket
//  private static final String NAME = "BluetoothChat";            //~1Ae5R~
//  private static final String NAME_SECURE = "BluetoothChatSecure";//~v101I~//~1AbFI~
//  private static final String NAME_INSECURE = "BluetoothChatInsecure";//~v101I~//~1AbFI~
    private static final String NAME_SECURE = AG.appName+"_Bluetooth";//~1AbFI~
    private static final String NAME_INSECURE = AG.appName+"_Bluetooth";//~1AbFI~
    private static final String CONFIRM_ACCEPT="Service discovery failed";//~1A64I~//~1Ae5I~

    // Unique UUID for this application
//  private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");//~@@@@R~
//  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//~2C04R~//~@@@@I~//~1Ae5R~
    private static final UUID MY_UUID_SECURE =                     //~v101I~//~1Ae5I~
          UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //~v101I~//~1Ae5I~//+1AebR~
//        UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"); //+1AebR~
    private static final UUID MY_UUID_INSECURE =                   //~v101I~//~1Ae5I~
          UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //~v101I~//~1Ae5I~//+1AebR~
//        UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"); //+1AebR~

    // Member fields
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mAcceptThread;
    private AcceptThread mAcceptThreadInSecure;                    //~v101I~//~1Ae5I~
    private ConnectThread mConnectThread;
//    private ConnectedThread mConnectedThread;                    //~@@@@R~
    private int mState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    public static final int CONN_FAILED=1;                         //~@@@2I~//~1Ae5I~
    public static final int CONN_LOST=2;                           //~@@@2I~//~1Ae5I~
//  public static BluetoothSocket  mConnectedSocket;               //~v110R~
//  public BluetoothSocket  mConnectedSocket;                      //~v110R~//~1Ae5R~
    public static BluetoothSocket  mConnectedSocket;               //~@@@@R~//~1Ae5I~
//  private BluetoothServerSocket mmServerSocket;            //~@@@@I~//~1Ae5R~

    /**
     * Constructor. Prepares a new BluetoothChat session.
     * @param context  The UI Activity Context
     * @param handler  A Handler to send messages back to the UI Activity
     */
//  public BluetoothChatService(Context context, Handler handler) {//~@@@@R~
    public BTService(Context context, Handler handler) {           //~@@@@I~
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }

    /**
     * Set the current state of the chat connection
     * @param state  An integer defining the current connection state
     */
//  private synchronized void setState(int state) {                //~1Ae5R~
    private void setState(int state) {                             //~v101I~//~1Ae5I~
//      if (D) Log.d(TAG, "setState() " + mState + " -> " + state);//~@@@@R~
        if (Dump.Y) Dump.println("setState() SYNC " + mState + " -> " + state);//~@@@@I~//~v101R~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~
        mState = state;
	    if (AG.ajagoBT.swDestroy)                                      //~@@@2I~//~1Ae5I~
        	return;                                                //~@@@2I~//~1Ae5I~

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(BTControl.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();//~@@@@R~
      }                                                            //~1Ae5I~
        if (Dump.Y) Dump.println("setState() SYNC return");        //~v101I~//~1Ae5I~
    }
    private void notifyFailure(int flag)              //~@@@2I~    //~v101R~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
        if (Dump.Y) Dump.println("Notifyfailure SYNC");                 //~@@@2I~//~v101R~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~
	    if (AG.ajagoBT.swDestroy)                                      //~@@@2I~//~1Ae5I~
        	return;                                                //~@@@2I~//~1Ae5I~
        mHandler.obtainMessage(BTControl.MESSAGE_FAILURE, flag, -1).sendToTarget();//~@@@2I~//~1Ae5I~
      }                                                            //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("notifyFailure SYNC return");     //~v101I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
    private void notifyFailureAccept(String Pinfo)                 //~v101R~//~1Ae5I~
    {                                                              //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("NotifyfailureAccept SYNC");      //~v101I~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~
	    if (AG.ajagoBT.swDestroy)                                      //~v101I~//~1Ae5I~
        	return;                                                //~v101I~//~1Ae5I~
        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_FAILUREACCEPT);//~v101I~//~1Ae5I~
        Bundle bundle = new Bundle();                              //~v101I~//~1Ae5I~
        bundle.putString(BTControl.ACCEPT_TYPE,Pinfo);              //~v101I~//~1Ae5I~
        msg.setData(bundle);                                       //~v101I~//~1Ae5I~
        mHandler.sendMessage(msg);                                 //~v101I~//~1Ae5I~
      }                                                            //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("notifyFailureAccept SYNC return");//~v101I~//~1Ae5I~
    }                                                              //~v101I~//~1Ae5I~

    /**
     * Return the current connection state. */
//  public synchronized int getState() {                           //~1Ae5R~
    public int getState() {                                        //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("getState() SYNC " + mState );    //~1Ae5I~
      synchronized(this)                                           //~1Ae5I~
      {                                                            //~1Ae5I~
          if (Dump.Y) Dump.println("getState() SYNC return");         //~v101I~//~1Ae5I~
        return mState;
      }                                                            //~v101I~//~1Ae5I~
    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
//  public synchronized void start() {                             //~1Ae5R~
    public boolean start() {                                       //~v101R~//~1Ae5I~
//      if (D) Log.d(TAG, "start");                                //~@@@@R~
        if (Dump.Y) Dump.println("BTService SYNC:start()");               //~@@@@I~//~v101R~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~

        // Start the thread to listen on a BluetoothServerSocket
       if (!AG.ajagoBT.swConnect)                                      //~1AbcI~
       {                                                           //~1AbcI~
        if (mAcceptThread == null) {
//          mAcceptThread = new AcceptThread();                    //~@@@@R~
//          mAcceptThread.start();                                 //~@@@@R~
			acceptNext();                                          //~@@@@I~
        }
        if (mAcceptThreadInSecure == null) {                       //~v101I~//~1Ae5I~
			acceptNextInSecure();                                  //~v101I~//~1Ae5I~
        }                                                          //~v101I~//~1Ae5I~
        setState(STATE_LISTEN);
       }                                                           //~1AbcI~
      }                                                            //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("BTService SYNC:start() return swConnect="+AG.ajagoBT.swConnect); //~v101I~//~1AbcI~
        return (mAcceptThread!=null||mAcceptThreadInSecure!=null); //~v101I~//~1Ae5I~
    }

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * @param device  The BluetoothDevice to connect
     */
//  public synchronized void connect(BluetoothDevice device) {     //~1Ae5R~
    public void connect(BluetoothDevice device, boolean secure) {//~v101I~//~1Ae5I~
//      if (D) Log.d(TAG, "connect to: " + device);                //~@@@@R~
        if (Dump.Y) Dump.println("BTService:SYNC connect connect to: " + device);//~@@@@I~//~v101R~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~

        // Start the thread to connect with the given device
//      mConnectThread = new ConnectThread(device);                //~1Ae5R~
        mConnectThread = new ConnectThread(device,secure);         //~v101I~//~1Ae5I~
        mConnectThread.start();
        setState(STATE_CONNECTING);
      }                                                            //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("BTService:SYNC connect return"); //~v101I~//~1Ae5I~
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
//  public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {//~1Ae5R~
    public void connected(BluetoothSocket socket, BluetoothDevice device) {//~v101I~//~1Ae5I~
//      if (D) Log.d(TAG, "connected");                            //~@@@@R~
        if (Dump.Y) Dump.println("BTService:SYNC connected start");           //~@@@@I~//~v101R~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (Dump.Y) Dump.println("BTService:connected, after connectthread cancel");//~@@@2I~//~v101R~//~1Ae5I~

        // Cancel any thread currently running a connection
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~

        // Cancel the accept thread because we only want to connect to one device
//        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}//~1Ae5R~
                                                                   //~1Ae5I~
        // Cancel the accept thread because we only want to connect to one device//~1Ae5I~
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}//~1Ae5I~
        if (Dump.Y) Dump.println("BTService:connected after acceptthread cancel");//~@@@2I~//~v101R~//~1Ae5I~
                                                                   //~v101I~//~1Ae5I~
        if (mAcceptThreadInSecure != null) {mAcceptThreadInSecure.cancel(); mAcceptThreadInSecure = null;}//~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("BTService:connected after acceptthreadInSecure cancel");//~v101R~//~1Ae5I~

        // Start the thread to manage the connection and perform transmissions
//        mConnectedThread = new ConnectedThread(socket);          //~@@@@R~
//        mConnectedThread.start();                                //~@@@@R~

        // Send the name of the connected device back to the UI Activity
        mConnectedSocket=socket;  //before sendMessage             //~@@@@I~
        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_DEVICE_NAME);//~@@@@R~
        Bundle bundle = new Bundle();
        bundle.putString(BTControl.DEVICE_NAME, device.getName()); //~@@@@R~
        msg.setData(bundle);
        if (Dump.Y) Dump.println("BTService:before sendmsg");      //~@@@2I~//~1Ae5I~
        mHandler.sendMessage(msg);

        if (Dump.Y) Dump.println("BTService:connected return");    //~@@@2I~//~1Ae5I~
        setState(STATE_CONNECTED);
      }                                                            //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("BTService:SYNC connected return");//~v101I~//~1Ae5I~
    }

    /**
     * Stop all threads
     */
//  public synchronized void stop() {                              //~1Ae5R~
    public void stop() {                                           //~1Ae5I~
//      if (D) Log.d(TAG, "stop");                                 //~@@@@R~
        if (Dump.Y) Dump.println("BTService:stop SYNC");                //~@@@@I~//~v101R~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~
//      if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}//~1Ae5R~
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~
        if (mConnectThread != null)                                //~@@@2R~//~1Ae5I~
		{                                                          //~@@@2I~//~1Ae5I~
        	if (Dump.Y) Dump.println("BTService:cancel connectThread");//~@@@2I~//~1Ae5I~
			mConnectThread.cancel(); mConnectThread = null;        //~@@@2I~//~1Ae5I~
		}                                                          //~@@@2I~//~1Ae5I~
        if (mAcceptThread != null)                                 //~@@@2R~//~1Ae5I~
		{                                                          //~@@@2I~//~1Ae5I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThread");//~@@@2I~//~1Ae5I~
			mAcceptThread.cancel(); mAcceptThread = null;         //~@@@2I~//~1Ae5I~
		}                                                          //~@@@2I~//~1Ae5I~
        if (mAcceptThreadInSecure != null)                         //~v101I~//~1Ae5I~
		{                                                          //~v101I~//~1Ae5I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThreadInSecure");//~v101I~//~1Ae5I~
			mAcceptThreadInSecure.cancel(); mAcceptThreadInSecure = null;//~v101I~//~1Ae5I~
		}                                                          //~v101I~//~1Ae5I~
        setState(STATE_NONE);
      }                                                            //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("BTService:stop SYNC return");    //~v101I~//~1Ae5I~
    }

//    /**                                                          //~@@@@R~
//     * Write to the ConnectedThread in an unsynchronized manner  //~@@@@R~
//     * @param out The bytes to write                             //~@@@@R~
//     * @see ConnectedThread#write(byte[])                        //~@@@@R~
//     */                                                          //~@@@@R~
//    public void write(byte[] out) {                              //~@@@@R~
//        // Create temporary object                               //~@@@@R~
//        ConnectedThread r;                                       //~@@@@R~
//        // Synchronize a copy of the ConnectedThread             //~@@@@R~
//        synchronized (this) {                                    //~@@@@R~
//            if (mState != STATE_CONNECTED) return;               //~@@@@R~
//            r = mConnectedThread;                                //~@@@@R~
//        }                                                        //~@@@@R~
//        // Perform the write unsynchronized                      //~@@@@R~
//        r.write(out);                                            //~@@@@R~
//    }                                                            //~@@@@R~

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        if (AG.ajagoBT.swDestroy)                                      //~@@@2I~//~1Ae5I~
        	return;                                                //~@@@2I~//~1Ae5I~
    	notifyFailure(CONN_FAILED);                                 //~@@@2I~//~1Ae5I~
        setState(STATE_LISTEN);

        // Send a failure message back to the Activity
//        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_TOAST);//~@@@@R~//~1Ae5R~
//        Bundle bundle = new Bundle();                            //~1Ae5R~
//        bundle.putString(BTControl.TOAST, "Unable to connect device");//~@@@@R~//~1Ae5R~
//        msg.setData(bundle);                                     //~1Ae5R~
//        mHandler.sendMessage(msg);                               //~1Ae5R~
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
//  private void connectionLost() {                                //~@@@@R~
    public  void connectionLost() {                                //~@@@@I~
        if (AG.ajagoBT.swDestroy)                                      //~@@@2I~//~1Ae5I~
            return;                                                //~@@@2I~//~1Ae5I~
    	notifyFailure(CONN_LOST);                                   //~@@@2I~//~1Ae5I~
        setState(STATE_LISTEN);
        if (AG.ajagoBT.swDestroy)                                      //~v110I~
            return;                                                //~v110I~

        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_TOAST);//~@@@@R~
        Bundle bundle = new Bundle();
        bundle.putString(BTControl.TOAST, "Device connection was lost");//~@@@@R~
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
//      private final BluetoothServerSocket mmServerSocket;        //~@@@@R~
//      public final BluetoothServerSocket mmServerSocket;        //~@@@@R~//~v101R~//~1Ae5I~//~1AebR~
        private BluetoothServerSocket mmServerSocket;              //~1AebI~
        private String mSocketType;                                //~v101I~//~1Ae5I~
        private boolean swCancel=false;                            //~@@@@I~
        private boolean acceptSecure;                              //~v101I~//~1Ae5I~

//      public AcceptThread() {                                    //~1Ae5R~
        public AcceptThread(boolean secure) {                      //~v101I~//~1Ae5I~
            BluetoothServerSocket tmp = null;
            mSocketType = secure ? "Secure":"Insecure";            //~v101I~//~1Ae5I~
            acceptSecure=secure;                                   //~v101I~//~1Ae5I~

            // Create a new listening server socket
            try {
//              tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);//~1Ae5R~
              	if (secure)                                        //~v101I~//~1Ae5I~
                {                                                  //~1AebI~
		        	tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);//~v101I~//~1Ae5I~
	            	if (Dump.Y) Dump.println("AcceptThread:socket="+tmp+",name="+NAME_SECURE+",uuid="+MY_UUID_SECURE);//~1AebI~
                }                                                  //~1AebI~
                else                                                //~v101I~//~1Ae5I~
                {                                                  //~1AebI~
		        	tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);//~v101I~//~1Ae5I~
	            	if (Dump.Y) Dump.println("AcceptThread:socket="+tmp+",name="+NAME_INSECURE+",uuid="+MY_UUID_INSECURE);//~1AebI~
                }                                                  //~1AebI~
            } catch (IOException e) {
//              Log.e(TAG, "listen() failed", e);                  //~@@@@R~
                Dump.println(e,"AcceptThread:listenUsingRfcommWithServiceRecord");//~@@@@I~
                // Send a failure message back to the Activity     //~@@@@I~
//                Message msg = mHandler.obtainMessage(BTControl.MESSAGE_TOAST);//~@@@@I~//~1Ae5R~
//                Bundle bundle = new Bundle();                      //~@@@@I~//~1Ae5R~
//                bundle.putString(BTControl.TOAST, "Accept Failed,Adaptor may be closed,reboot may be required");//~@@@@I~//~1Ae5R~
//                msg.setData(bundle);                               //~@@@@I~//~1Ae5R~
//                mHandler.sendMessage(msg);                         //~@@@@I~//~1Ae5R~
			    notifyFailureAccept(mSocketType);                  //~v101R~//~1Ae5I~
            }
            mmServerSocket = tmp;
        }

        public void run() {
//          if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);       //~@@@@R~
            if (Dump.Y) Dump.println("AcceptThread:run() BEGIN mAcceptThread" + this);//~@@@@I~
//          setName("AcceptThread");                               //~1Ae5R~
            setName("AcceptThread" + mSocketType);                 //~v101I~//~1Ae5I~
            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
	            	if (Dump.Y) Dump.println("AcceptThread:accept() secure="+acceptSecure+",mmServerSocket="+mmServerSocket.toString());//~@@@2I~//~1AbcI~
                    AG.RemoteStatusAccept=(acceptSecure?AG.RS_BTLISTENING_SECURE:AG.RS_BTLISTENING_INSECURE);                //~@@@2R~//~v101R~//~1Ae5I~
                    socket = mmServerSocket.accept();
                    AG.RemoteStatusAccept&=~(acceptSecure?AG.RS_BTLISTENING_SECURE:AG.RS_BTLISTENING_INSECURE);//~v101I~//~1Ae5I~
	            	if (Dump.Y) Dump.println("AcceptThread:accept() accepted socket="+socket.toString()+",secure="+acceptSecure);//~@@@2I~//~v101R~//~1Ae5I~
//              } catch (IOException e) {                          //~v110R~
                } catch (Exception e) {                            //~v110I~
//                  Log.e(TAG, "accept() failed", e);              //~@@@@R~
                    AG.RemoteStatusAccept&=~(acceptSecure?AG.RS_BTLISTENING_SECURE:AG.RS_BTLISTENING_INSECURE);//~v101I~//~1Ae5I~
					if (swCancel)                                  //~@@@@I~
                    {                                              //~@@@@I~
	                    if (Dump.Y) Dump.println("AcceptThread:serverSocket accept canceled");//~@@@@I~
                    }                                              //~@@@@I~
                    else                                           //~@@@@I~
                    {                                              //~v110I~
	                    Dump.println(e,"AcceptThread:run accept()");//~@@@@R~
                    }                                              //~v110I~
//                    try {                                        //~v110R~
//                        if (Dump.Y) Dump.println("AcceptThread mmServerSoket close()="+mmServerSocket.toString());//~v110R~
//                        mmServerSocket.close();                  //~v110R~
//                    } catch (Exception ex) {                     //~v110R~
//                          Dump.println(ex,"AcceptThread:Server Socket Close at IOException");//~v110R~
//                    }                                            //~v110R~
                    break;
                }
                try                                                //~1AebI~
                {                                                  //~1AebI~
                    if (Dump.Y) Dump.println("AcceptThread mmServerSoket close()="+mmServerSocket.toString());//~1AebI~
                    mmServerSocket.close();                        //~1AebI~
                } catch (Exception ex) {                           //~1AebI~
                      Dump.println(ex,"AcceptThread:Server Socket Close at IOException");//~1AebI~
                }                                                  //~1AebI~
                mmServerSocket=null;                               //~1AebI~

                // If a connection was accepted
		        if (Dump.Y) Dump.println("AcceptThread:cancel discovery after accepted");//~1AbZI~//~1AbNI~
				requestDiscover(mAdapter,0/*not start discovery*/); //~1AbZI~//~1AbNI~
                if (socket != null) {
            		if (Dump.Y)  Dump.println("acceptThread run SYNC accepted start");//~1Ae5I~
                    synchronized (BTService.this) {                //~@@@@R~
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
            				if (Dump.Y)  Dump.println("acceptThread run connected socket="+socket.toString());//~v110I~
                            if (acceptSecure)                      //~1A60I~//~1Ae5I~
						        AjagoView.showToastLong(AG.resource.getString(R.string.AcceptedBTSecureConnection,socket.getRemoteDevice().getName()));//~1A60R~//~1Ae5I~
                            else                                   //~1A60I~//~1Ae5I~
						        AjagoView.showToastLong(AG.resource.getString(R.string.AcceptedBTInSecureConnection,socket.getRemoteDevice().getName()));//~1A60R~//~1Ae5I~
                            connected(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
            				    if (Dump.Y)  Dump.println("acceptThread run connected,but close() by mstate="+mState+",socket="+socket.toString());//~v110I~
                                socket.close();
                            } catch (IOException e) {
//                              Log.e(TAG, "Could not close unwanted socket", e);//~@@@@R~
                                Dump.println(e,"AcceptThread:run Could not close unwanted socket");//~@@@@I~
                            }
                            break;
                        }
                    }
            		if (Dump.Y)  Dump.println("acceptThread run SYNC accepted exit mState="+mState);//~v101I~//~1AbFI~
//                  AG.aBT.mBTC.BTstopDiscoverable();              //~1AbJR~//~1AbKR~
                }
            }
//          if (D) Log.i(TAG, "END mAcceptThread");                //~@@@@R~
            if (Dump.Y) Dump.println("END mAcceptThread");         //~@@@@I~
        }

        public void cancel() {
//          if (D) Log.d(TAG, "cancel " + this);                   //~@@@@R~
            if (Dump.Y) Dump.println("AcceptThread:cancel " + this);//~@@@@I~
            if (mmServerSocket==null)                              //~v101I~//~1Ae5I~
            {                                                      //~v101I~//~1Ae5I~
	            if (Dump.Y) Dump.println("AcceptThread:cancel mmserversocket:null");//~v101I~//~1Ae5I~
            	return;                                            //~v101I~//~1Ae5I~
            }                                                      //~v101I~//~1Ae5I~
            if (Dump.Y) Dump.println("AcceptThread cancel() close() serversocket="+mmServerSocket.toString());//~v110I~
            try {
                swCancel=true;                                     //~@@@@I~
                mmServerSocket.close();
            } catch (IOException e) {
//                Log.e(TAG, "close() of server failed", e);       //~@@@@R~
                  Dump.println(e,"AcceptThread:cancel:close");     //~@@@@I~
            }
        }
    }
//***************************************************************************//~@@@2I~//~1AbNI~
    @TargetApi(15)                                                 //~1AbNI~
    private void displayMyUuid(BluetoothDevice Pdevice)            //~1AbNI~
    {                                                              //~1AbNI~
    	ParcelUuid[] uuids=Pdevice.getUuids();                     //~1AbNI~//~1AbZR~//~1AbNR~
    	for (int ii=0;ii<uuids.length;ii++)                        //~1AbNI~
        	if (Dump.Y) Dump.println("BTService connectThread my uuid"+ii+"="+uuids[ii].getUuid());//~1AbNI~
    }                                                              //~1AbNI~


    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;                                //~v101I~//~1Ae5I~
        private boolean swCancel=false;                            //~v110I~
        private boolean swSecure;                                  //~1A60I~//~1Ae5I~

//      public ConnectThread(BluetoothDevice device) {             //~1Ae5R~
        public ConnectThread(BluetoothDevice device,boolean secure) {//~v101I~//~1Ae5I~
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";          //~v101I~//~1Ae5I~
            swSecure=secure;                                       //~1A60I~//~1Ae5I~

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
//          displayMyUuid(device);//@@@@test                       //~1AbNI~
	        if (Dump.Y) Dump.println("ConnectThread:createRfComm secure="+secure);//~v101I~//~1Ae5I~
            try {
//                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);//~1Ae5R~
	                if (secure)                                    //~v101I~//~1Ae5I~
                    {                                              //~1AebI~
  	              		tmp = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);//~v101I~//~1Ae5I~
		            	if (Dump.Y) Dump.println("ConnectThread:socket="+tmp+",name="+NAME_INSECURE+",uuid="+MY_UUID_INSECURE);//~1AebI~
                    }                                              //~1AebI~
                    else                                           //~v101I~//~1Ae5I~
                    {                                              //~1AebI~
  	              		tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);//~v101I~//~1Ae5I~
                    }                                              //~1AebI~
            } catch (IOException e) {
//              Log.e(TAG, "create() failed", e);                  //~@@@@R~
                Dump.println(e,"ConnectThread:createRfcommSocketToServiceRecord");//~@@@@I~
            }
            mmSocket = tmp;
            if (Dump.Y) Dump.println("BTService connectThread connected mmSocket="+mmSocket.toString());//~v110I~
        }

        public void run() {
//          Log.i(TAG, "BEGIN mConnectThread");                    //~@@@@R~
            if (Dump.Y) Dump.println("ConnectThread:BEGIN mConnectThread");//~@@@@I~
//            setName("ConnectThread");                            //~1Ae5R~
            setName("ConnectThread" + mSocketType);                //~v101I~//~1Ae5I~

            // Always cancel discovery because it will slow down a connection
	          if (Dump.Y) Dump.println("ConnectThread:connect() cancel discovery before connect discovering="+mAdapter.isDiscovering());//~1AbNI~
			if (mAdapter.isDiscovering())                          //~1AbNI~
			{                                                      //~1AbNI~
              try                                                  //~1A64I~//~1Ae5I~
              {                                                    //~1A64I~//~1Ae5I~
              	Thread.sleep(300); //300ms                         //~1A64I~//~1Ae5I~
              }                                                    //~1A64I~//~1Ae5I~
              catch(InterruptedException e)                        //~1A64I~//~1Ae5I~
              {                                                    //~1A64I~//~1Ae5I~
              }                                                    //~1A64I~//~1Ae5I~
	          if (Dump.Y) Dump.println("ConnectThread:connect() cancel discovery end before connect");//~v101I~//~1Ae5I~
//          mAdapter.cancelDiscovery();                            //~1Ae5R~
            }                                                      //~1AbNI~

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
	            if (Dump.Y) Dump.println("ConnectThread:sonnect() mmSocket="+mmSocket.toString());//~v110I~
                mmSocket.connect();
	            if (Dump.Y) Dump.println("ConnectThread:connect() returned");//~v101I~//~1Ae5I~
//          } catch (IOException e) {                              //~v110R~
            } catch (Exception e) {                                //~v110I~
              	if (!swCancel)                                     //~v110I~
                	Dump.println(e,"BTService:ConnectThread:run:connect");//~@@@@I~//~v110R~
		        String exceptionMsg=e.getMessage();                //~1A64I~//~1Ae5I~
    			if (exceptionMsg.equals(CONFIRM_ACCEPT))	//"Service discovery failed"//~1A64I~//~1Ae5I~
					AjagoView.showToastLong(R.string.ErrConnectConfirmAccept);//~1A64I~//~1Ae5I~
                connectionFailed();
                // Close the socket
	            if (Dump.Y) Dump.println("BTService:ConnectThread:mmSocket close()="+mmSocket.toString());//~v110I~
                try {
                    mmSocket.close();
                } catch (IOException e2) {
//                  Log.e(TAG, "unable to close() socket during connection failure", e2);//~@@@@R~
                	Dump.println(e,"BTService:ConnectThread:run:close");//~@@@@I~
                }
                // Start the service over to restart listening mode
//              BTService.this.start();                            //~@@@@R~//~v110R~
	            if (Dump.Y) Dump.println("BTService:ConnectThread:mmSocket closeed");//~v101I~//~1Ae5I~
                return;
            }
	        if (Dump.Y) Dump.println("ConnectThread:cancel discovery after connected");//~1AbZI~//~1AbNI~
			requestDiscover(mAdapter,0/*not start discovery*/);     //~1AbZI~//~1AbNI~

            // Reset the ConnectThread because we're done
            if (Dump.Y)  Dump.println("connectThread run SYNC connected");//~v101I~//~1Ae5I~
            synchronized (BTService.this) {                        //~@@@@R~
                mConnectThread = null;
            }
            if (Dump.Y)  Dump.println("connectThread run SYNC connected exit");//~v101I~//~1Ae5I~

            // Start the connected thread
            if (Dump.Y)  Dump.println("connectThread run connected socket="+mmSocket.toString());//~v110I~
            if (swSecure)                                          //~1A60I~//~1Ae5I~
				AjagoView.showToastLong(AG.resource.getString(R.string.ConnectedBTSecureConnection,mmDevice.getName()));//~1A60R~//~1Ae5I~
            else                                                   //~1A60I~//~1Ae5I~
				AjagoView.showToastLong(AG.resource.getString(R.string.ConnectedBTInSecureConnection,mmDevice.getName()));//~1A60R~//~1Ae5I~
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
            	if (Dump.Y) Dump.println("ConnectThread cancel() close socket="+mmSocket.toString());//~v110I~
                swCancel=true;                                     //~v110I~
                mmSocket.close();
            } catch (IOException e) {
//              Log.e(TAG, "close() of connect socket failed", e); //~@@@@R~
                Dump.println(e,"ConnectThread:cancel close()");    //~@@@@I~
            }
            if (Dump.Y) Dump.println("ConnectThread cancel() closed");//~v101I~//~1Ae5I~
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
//    private class ConnectedThread extends Thread {               //~@@@@R~
//        private final BluetoothSocket mmSocket;                  //~@@@@R~
//        private final InputStream mmInStream;                    //~@@@@R~
//        private final OutputStream mmOutStream;                  //~@@@@R~

//        public ConnectedThread(BluetoothSocket socket) {         //~@@@@R~
//            Log.d(TAG, "create ConnectedThread");                //~@@@@R~
//            mmSocket = socket;                                   //~@@@@R~
//            InputStream tmpIn = null;                            //~@@@@R~
//            OutputStream tmpOut = null;                          //~@@@@R~

//            // Get the BluetoothSocket input and output streams  //~@@@@R~
//            try {                                                //~@@@@R~
//                tmpIn = socket.getInputStream();                 //~@@@@R~
//                tmpOut = socket.getOutputStream();               //~@@@@R~
//            } catch (IOException e) {                            //~@@@@R~
//                Log.e(TAG, "temp sockets not created", e);       //~@@@@R~
//            }                                                    //~@@@@R~

//            mmInStream = tmpIn;                                  //~@@@@R~
//            mmOutStream = tmpOut;                                //~@@@@R~
//        }                                                        //~@@@@R~

//        public void run() {                                      //~@@@@R~
//            Log.i(TAG, "BEGIN mConnectedThread");                //~@@@@R~
//            byte[] buffer = new byte[1024];                      //~@@@@R~
//            int bytes;                                           //~@@@@R~

//            // Keep listening to the InputStream while connected //~@@@@R~
//            while (true) {                                       //~@@@@R~
//                try {                                            //~@@@@R~
//                    // Read from the InputStream                 //~@@@@R~
//                    bytes = mmInStream.read(buffer);             //~@@@@R~

//                    // Send the obtained bytes to the UI Activity//~@@@@R~
//                    mHandler.obtainMessage(BTControl.MESSAGE_READ, bytes, -1, buffer)//~@@@@R~
//                            .sendToTarget();                     //~@@@@R~
//                } catch (IOException e) {                        //~@@@@R~
//                    Log.e(TAG, "disconnected", e);               //~@@@@R~
//                    connectionLost();                            //~@@@@R~
//                    break;                                       //~@@@@R~
//                }                                                //~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
//        public void write(byte[] buffer) {                       //~@@@@R~
//            try {                                                //~@@@@R~
//                mmOutStream.write(buffer);                       //~@@@@R~

//                // Share the sent message back to the UI Activity//~@@@@R~
//                mHandler.obtainMessage(BTControl.MESSAGE_WRITE, -1, -1, buffer)//~@@@@R~
//                        .sendToTarget();                         //~@@@@R~
//            } catch (IOException e) {                            //~@@@@R~
//                Log.e(TAG, "Exception during write", e);         //~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~

//        public void cancel() {                                   //~@@@@R~
//            try {                                                //~@@@@R~
//                mmSocket.close();                                //~@@@@R~
//            } catch (IOException e) {                            //~@@@@R~
//                Log.e(TAG, "close() of connect socket failed", e);//~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~
//***************************************************************************//~@@@@I~
	public static InputStream getBTInputStream(BluetoothSocket Psocket)//~@@@@I~
    {                                                              //~@@@@I~
    	InputStream s=null;                                        //~@@@@I~
		try                                                        //~@@@@I~
    	{                                                          //~@@@@I~
			s=Psocket.getInputStream();                            //~@@@@I~
		}                                                          //~@@@@I~
		catch (Exception e)                                        //~@@@@I~
		{                                                          //~@@@@I~
        	Dump.println(e,"getBTInputStream");                    //~@@@@I~
		}                                                          //~@@@@I~
        return s;                                                  //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************************//~@@@@I~
	public static OutputStream getBTOutputStream(BluetoothSocket Psocket)//~@@@@I~
    {                                                              //~@@@@I~
    	OutputStream s=null;                                       //~@@@@I~
		try                                                        //~@@@@I~
    	{                                                          //~@@@@I~
			s=Psocket.getOutputStream();                           //~@@@@I~
		}                                                          //~@@@@I~
		catch (Exception e)                                        //~@@@@I~
		{                                                          //~@@@@I~
        	Dump.println(e,"getBTOutputStream");                   //~@@@@I~
		}                                                          //~@@@@I~
        return s;                                                  //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************************//~@@@@I~
	public boolean acceptNext()                                    //~@@@@R~
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("acceptNext:"+mAcceptThread+",swNFC="+AG.ajagoBT.swNFC+",secureNFC="+AG.ajagoBT.swSecureNFC+",secureNonNfc="+AG.ajagoBT.swSecureNonNFCAccept);     //~1A64I~//~1AbbR~//~1AbtI~//~1AbcI~
        if (AG.ajagoBT.swNFC && !AG.ajagoBT.swSecureNFC)                   //~1AbbI~
        	return false;                                          //~1AbbI~
        if (!AG.ajagoBT.swNFC && !AG.ajagoBT.swSecureNonNFCAccept)         //~1AbtI~//~1AbcI~
        	return false;                                          //~1AbtI~//~1AbcI~
        if (mAcceptThread == null) {                               //~@@@@I~
//            mAcceptThread = new AcceptThread();                    //~@@@@I~//~1Ae5R~
            mAcceptThread = new AcceptThread(true/*secure*/);      //~v101I~//~1Ae5I~
//  	  	if (mmServerSocket==null)                              //~@@@@I~//~1Ae5R~
    	  	if (mAcceptThread.mmServerSocket==null)                              //~@@@@I~//~v101R~//~1Ae5I~
            	mAcceptThread =null;                               //~@@@@I~
            else                                                   //~@@@@I~
            	mAcceptThread.start();                             //~@@@@R~
            return true;  //started thread                         //~@@@@I~
        }                                                          //~@@@@I~
        return false;	//now alive                                //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************************//~v101I~
	public boolean acceptNextInSecure()                            //~v101I~
    {                                                              //~v101I~
    	if (Dump.Y) Dump.println("acceptNextInSecure:"+mAcceptThread+",swNFC="+AG.ajagoBT.swNFC+",secureNFC="+AG.ajagoBT.swSecureNFC+",swSecureNonNFC="+AG.ajagoBT.swSecureNonNFCAccept);//~1AbbI~//~1AbtR~//~1AbZI~//~1AbNR~
        if (AG.ajagoBT.swNFC && AG.ajagoBT.swSecureNFC)                    //~1AbbI~
        	return false;                                          //~1AbbI~
        if (!AG.ajagoBT.swNFC && AG.ajagoBT.swSecureNonNFCAccept)          //~1AbtI~
        	return false;                                          //~1AbtI~
        if (mAcceptThreadInSecure == null) {                       //~v101I~
            mAcceptThreadInSecure = new AcceptThread(false/*insecure*/);//~v101I~
    	  	if (mAcceptThreadInSecure.mmServerSocket==null)        //~v101R~
            	mAcceptThreadInSecure =null;                       //~v101I~
            else                                                   //~v101I~
            	mAcceptThreadInSecure.start();                      //~v101I~
            return true;  //started thread                         //~v101I~
        }                                                          //~v101I~
        return false;	//now alive                                //~v101I~
    }                                                              //~v101I~
//***************************************************************************//~@@@2I~//~1Ae5I~
	public boolean stopListen()                                    //~@@@2I~//~v101R~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
    	boolean rc=false;                                          //~@@@2I~//~1Ae5I~
        if (Dump.Y) Dump.println("stopListen SYNC start");         //~v101I~//~1Ae5I~
      synchronized(this)                                           //~v101I~//~1Ae5I~
      {                                                            //~v101I~//~1Ae5I~
        if (mAcceptThread != null)                                 //~@@@2I~//~1Ae5I~
		{                                                          //~@@@2I~//~1Ae5I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThread");//~@@@2I~//~1Ae5I~
			mAcceptThread.cancel();                                //~@@@2I~//~1Ae5I~
			mAcceptThread = null;                                  //~@@@2I~//~1Ae5I~
            rc=true;                                               //~@@@2I~//~1Ae5I~
		}                                                          //~@@@2I~//~1Ae5I~
        if (mAcceptThreadInSecure != null)                         //~v101I~//~1Ae5I~
		{                                                          //~v101I~//~1Ae5I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThreadInsecure");//~v101I~//~1Ae5I~
			mAcceptThreadInSecure.cancel();                        //~v101I~//~1Ae5I~
			mAcceptThreadInSecure = null;                          //~v101I~//~1Ae5I~
            rc=true;                                               //~v101I~//~1Ae5I~
		}                                                          //~v101I~//~1Ae5I~
      }                                                            //~v101I~//~1Ae5I~
        if (Dump.Y) Dump.println("stopListen SYNC end");           //~v101I~//~1Ae5I~
        return rc;                                                 //~@@@2I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//***************************************************************************//~v101I~//~1AbKI~
	public static void requestDiscover(BTService Pservice,BluetoothAdapter Padapter,int Pdiscover)//~v101R~//~1AbKI~
    {                                                              //~v101I~//~1AbKI~
        if (Dump.Y) Dump.println("static requestDiscovery start Pservice="+Pservice); //~v101R~//~1AbKI~
        if (Pservice!=null)                                        //~v101I~//~1AbKI~
      	synchronized(Pservice)                                     //~v101R~//~1AbKI~
      	{                                                          //~v101I~//~1AbKI~
			if (Dump.Y) Dump.println("BTService requestDiscovery SYNC start");//~v101I~//~1AbKI~
			requestDiscover(Padapter,Pdiscover);                    //~v101I~//~1AbKI~
			if (Dump.Y) Dump.println("BTService requestDiscovery SYNC end");//~v101I~//~1AbKI~
        }                                                          //~v101I~//~1AbKI~
        else                                                       //~v101I~//~1AbKI~
        {                                                          //~v101I~//~1AbKI~
			if (Dump.Y) Dump.println("BTService requestDiscovery not SYNC start");//~1AbKI~
			requestDiscover(Padapter,Pdiscover);                    //~v101I~//~1AbKI~
			if (Dump.Y) Dump.println("BTService requestDiscovery not SYNC end");//~1AbKI~
      	}                                                          //~v101I~//~1AbKI~
        if (Dump.Y) Dump.println("static requestDiscovery end");   //~v101R~//~1AbKI~
    }                                                              //~v101I~//~1AbKI~
//***************************************************************************//~v101I~
	public static void requestDiscover(BluetoothAdapter Padapter,int Pdiscover)//~v101I~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("requestDiscover start Pdiscover="+Pdiscover);      //~v101I~//~1AbtI~
			if (Dump.Y) Dump.println("BTService requestDiscovery adapter="+Padapter);//~v101I~
			if (Padapter.isDiscovering())                          //~v101I~
			{                                                      //~v101I~
				if (Dump.Y) Dump.println("BTService:requestDiscovery cancelDiscover issue cancel");//~v101I~
				Padapter.cancelDiscovery();                        //~v101I~
			    if (Dump.Y) Dump.println("BTService requestDiscovery cancelDiscover end");//~v101I~//~1AbtI~
			}                                                      //~v101I~
            if (Pdiscover==1)    //start                           //~v101I~
            {                                                      //~v101I~
                if (Dump.Y) Dump.println("BTService requestDiscovery:startDiscovery");//~v101I~//~1AbtI~
              boolean rc=                                          //~1AbKI~
                Padapter.startDiscovery();                         //~v101I~
                if (Dump.Y) Dump.println("BTService requestDiscovery:startDiscovery end rc="+rc);//~v101I~//~1AbKI~
            }                                                      //~v101I~
        if (Dump.Y) Dump.println("requestDiscovery sub end");      //~v101I~
    }                                                              //~v101I~
//**************************************************************************//~v101I~//~1AbNI~
//*get device list pair of name,addr                               //~v101I~//~1AbNI~
//**************************************************************************//~v101I~//~1AbNI~
    public  static String[] getPairedDeviceList(BTService Pservice) //~v101R~//~1AbNI~
    {                                                              //~v101I~//~1AbNI~
      	String[] sa;                                               //~v101I~//~1AbNI~
        if (Dump.Y) Dump.println("BTService getPairDevice start"); //~v101R~//~1AbNI~
    	if (Pservice!=null)	//after service initialized            //~v101I~//~1AbNI~
        {                                                          //~v101I~//~1AbNI~
	        if (Dump.Y) Dump.println("BTService getPairDevice SYNC start");//~v101I~//~1AbNI~
	        synchronized(Pservice)                                 //~v101R~//~1AbNI~
      		{                                                      //~v101R~//~1AbNI~
	        	if (Dump.Y) Dump.println("BTService getPairDevice cancelDiscovery,isDiscovering="+Pservice.mAdapter.isDiscovering());//~1AbNI~
			  if (Pservice.mAdapter.isDiscovering())               //~1AbNI~
				Pservice.mAdapter.cancelDiscovery();               //~v101I~//~1AbNI~
	        	if (Dump.Y) Dump.println("BTService getPairDevice cancelDiscovery end");//~v101I~//~1AbNI~
				sa=getDeviceList(Pservice.mAdapter);                        //~v101I~//~1AbNI~
            }                                                      //~v101I~//~1AbNI~
	        if (Dump.Y) Dump.println("BTService getPairDevice SYNC end");//~v101I~//~1AbNI~
        }                                                          //~v101I~//~1AbNI~
        else                                                       //~v101I~//~1AbNI~
        {                                                          //~v101I~//~1AbNI~
        	BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();//~v101I~//~1AbNI~
			sa=getDeviceList(adapter);                             //~v101I~//~1AbNI~
        }                                                          //~v101I~//~1AbNI~
        if (Dump.Y) Dump.println("BTService getPairDevice end");   //~v101R~//~1AbNI~
        return sa;                                                 //~v101I~//~1AbNI~
    }                                                              //~v101I~//~1AbNI~
//**************************************************************************//~v101I~//~1AbNI~
//*get device list pair of name,addr                               //~v101I~//~1AbNI~
//**************************************************************************//~v101I~//~1AbNI~
    public  static String[] getDeviceList(BluetoothAdapter Padapter)//~v101I~//~1AbNI~
    {                                                              //~v101I~//~1AbNI~
        BluetoothAdapter bta=Padapter;                             //~v101I~//~1AbNI~
        if (bta==null)                                             //~v101I~//~1AbNI~
        	return null;                                           //~v101I~//~1AbNI~
        if (Dump.Y) Dump.println("BTService getDeviceList start"); //~v101I~//~1AbNI~
        Set<BluetoothDevice> pairedDevices = bta.getBondedDevices();//~v101I~//~1AbNI~
                                                                   //~v101I~//~1AbNI~
        int sz=pairedDevices.size();                               //~v101I~//~1AbNI~
        if (sz==0)                                                 //~v101I~//~1AbNI~
            return null;                                           //~v101I~//~1AbNI~
        String[] sa=new String[sz*2];                              //~v101I~//~1AbNI~
        int ii=0;                                                  //~v101I~//~1AbNI~
        for (BluetoothDevice device : pairedDevices)               //~v101I~//~1AbNI~
        {                                                          //~v101I~//~1AbNI~
            sa[ii*2]=device.getName();                             //~v101I~//~1AbNI~
            sa[ii*2+1]=device.getAddress();                        //~v101I~//~1AbNI~
            if (Dump.Y) Dump.println("getPairDeviceList="+sa[ii*2]+"="+sa[ii*2+1]);//~v101I~//~1AbNI~
            ii++;                                                  //~v101I~//~1AbNI~
        }                                                          //~v101I~//~1AbNI~
        if (Dump.Y) Dump.println("BTService getDevice end");       //~v101I~//~1AbNI~
        return sa;                                                 //~v101I~//~1AbNI~
    }                                                              //~v101I~//~1AbNI~
}
