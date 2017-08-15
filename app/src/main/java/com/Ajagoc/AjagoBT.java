//*CID://+1AedR~:                             update#=   78;       //+1AedR~
//**********************************************************************//~v107I~
//1Aed 2015/07/30 show secure/insecure option to waiting msg       //+1AedI~
//1Ae9 2015/07/26 (Ajagoc only)All connection type passes to jagoclient PartnerFrame to (re)start/disconnect//~1Ae9I~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//2015/07/24 //1Ac5 2015/07/09 NFCBT:confirmation dialog is not shown and fails to pairig//~1Ac5I~
             //                (LocalBluetoothPreference:"Found no reason to show dialog",requires discovaring status in the 60 sec before)//~1Ac5I~
             //                Issue waring when NFCBT-Secure      //~1Ac5I~
//2015/07/23 //1AbW 2015/07/05 BT:chk Bluetooth supported foy system bluetooth//~1AbWI~
//2015/07/23 //1AbR 2015/07/03 BT:additional to paired, add device not paired but connected to devlice list//~1AbRI~
//2015/07/23 //1Abt 2015/06/18 BT:Accept not both of Secure/Insecure but one of two.//~1AbtI~
//2015/07/23 //1Abm 2015/06/16 NFCBT:chk secure level of requester and intent receiver//~1AbmI~
//2015/07/23 //1Abg 2015/06/15 NFCBT:transfer to NFCBT or NFCWD if active session exist//~1AbgI~
//2015/07/23 //1Abc 2015/06/15 BT:no need to issue accept for connection request side
//2015/07/23 //1Abb 2015/06/15 NFCBT:try insecure only(if accepting both,connect insecure request is accepted by secure socket)//~1AbbI~
//1Aa0 2015/04/18 (BUG)AjagoBT coding miss                         //~1Aa0I~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1B1h 130513 add help to BTMenu                                   //~1B1hI~
//1102:130123 bluetooth became unconnectable after some stop operation//~v110I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//**********************************************************************//~v107I~
package com.Ajagoc;

import wifidirect.DialogNFCBT;
import android.content.Intent;
import android.bluetooth.BluetoothSocket;                          //~v107I~

import com.Ajagoc.BT.BTDiscover;
import com.Ajagoc.BT.BTControl;
import com.Ajagoc.BT.BTService;
import com.Ajagoc.jagoclient.partner.PartnerFrame;                 //~v107I~
import com.Ajagoc.jagoclient.partner.Server;                       //~v107I~

import jagoclient.Dump;                                            //~v107I~
import jagoclient.Global;
import jagoclient.Go;
import jagoclient.dialogs.Message;
import jagoclient.gui.DoActionListener;
import jagoclient.partner.BluetoothConnection;

//********************************************************************//~1212I~
//* Interface to BT                                                //~v107R~
//********************************************************************//~1212I~
public class AjagoBT                                               //~1122R~//~v107R~
//         implements DoActionListener                               //~v107I~//~1Ae5R~
{                                                                  //~1109I~
	private static final String DISCOVERABLE="Make Discoverable";  //~v107M~
	private static final String STARTSERVER="Open Server";         //~v107R~
	private static final String CONNECT="Connect to Server";       //~v107R~
	private static final String BTHELP="BTHelp";                   //~1B1hI~
//  private BTControl mBTC;                                        //~v107R~//~1Ae5R~
    public BTControl mBTC;                                         //~1Ae5I~
	public  BTDiscover mBTD;                                       //~@@@2I~//~1Ae5I~
	private BluetoothSocket mBTSocket;                             //~v107R~
  	private PartnerFrame partnerFrame;                             //~v107I~
    private String mDeviceName;                                    //~v107I~
    private String requestDeviceName="Unknown";                    //~@@@2I~//~1Ae5I~
//  private boolean swServer;                                      //~v107I~//~1Ae5R~
//  private boolean swConnect;                                     //~@@@@I~//~1Ac5I~
    public  boolean swConnect;                                     //~1AbcI~//~1Ac5I~
//  private static Menu bluetooth;                                 //~v107I~//~v110R~
//  private Menu bluetooth;                                        //~v110I~//~1Ae5R~
	public boolean swDestroy;                                      //~v110I~
	private boolean swSecureConnect;                               //+1AedI~
    public boolean swNFC,swSecureNFC;                              //~1AbbI~
    public boolean swSecureNonNFCAccept;                           //~1AbtI~
    public AjagoBT()                                               //~v107R~
    {                                                              //~1329I~
        if (Dump.Y) Dump.println("===========ABT start============");//~@@@2I~//~1Ae5I~
		swDestroy=false;               //static clear for after finish()//~@@@2I~//~1Ae5I~
	    mBTC=new BTControl();                                      //~v107R~
	    mBTD=new BTDiscover();                                     //~@@@2I~//~1Ae5I~
    }
    public static AjagoBT createAjagoBT()                          //~v107I~
    {                                                              //~v107I~
		AjagoBT inst=null;                                         //~v107I~
        try                                                        //~v107I~
        {                                                          //~v107I~
		    inst=new AjagoBT();                                    //~v107I~
            if (!inst.mBTC.BTCreate())	//default adapter chk      //~v107R~//~1Ae5I~
            	inst.mBTC=null;                                    //~v107R~//~1Ae5I~
            else                                                   //~@@@@I~//~@@@2R~//~1Ae5I~
            {                                                      //~@@@2I~//~1Ae5I~
	    		AG.LocalDeviceName=inst.mBTC.getDeviceName();           //~@@@2I~//~1Ae5I~
            }                                                      //~@@@2I~//~1Ae5I~
        }                                                          //~v107I~
        catch(Throwable e)                                         //~v107R~
        {                                                          //~v107I~
            Dump.println(e.toString());                            //~v107R~
        }                                                          //~v107I~
        return inst;                                               //~v107I~
    }                                                              //~v107I~
//********************************************************************//~@@@2I~//~1Ae5I~
    public static boolean startListen()                        //~@@@2R~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
    	boolean rc=false;                                          //~@@@2I~//~1Ae5I~
		AjagoBT inst=AG.ajagoBT;                                           //~@@@2I~//~1Ae5I~
        if (inst.mBTC==null)                                       //~@@@2I~//~1Ae5I~
        {                                                          //~@@@2I~//~1Ae5I~
//            AView.showToast(R.string.BTNotAvalable);               //~@@@2I~//~1AbRR~//~1Ae5I~
//            return false;                                          //~@@@2I~//~1AbRR~//~1Ae5I~
			return BTNotAvailable();                               //~1AbRI~//~1Ae5I~
        }                                                          //~@@@2I~//~1Ae5I~
        try                                                        //~@@@2I~//~1Ae5I~
        {                                                          //~@@@2I~//~1Ae5I~
        	rc=inst.startServer();                                    //~@@@2I~//~v101R~//~1Ae5I~
        }                                                          //~@@@2I~//~1Ae5I~
        catch(Throwable e)                                         //~@@@2I~//~1Ae5I~
        {                                                          //~@@@2I~//~1Ae5I~
            Dump.println(e.toString());                            //~@@@2I~//~1Ae5I~
//          AView.showToast(R.string.BTNotAvalable);               //~@@@2I~//~1AbRR~//~1Ae5I~
			BTNotAvailable();                                      //~1AbRI~//~1Ae5I~
        }                                                          //~@@@2I~//~1Ae5I~
        AG.isNFCBT=false;                                          //~1AbgR~//~1Ae5I~
        return rc;                                                 //~@@@2I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//********************************************************************//~1AbbI~
//* NFCBT:startServer with secure option                           //~1AbbI~
//********************************************************************//~1AbbI~
    public static boolean startListen(boolean Psecure)             //~1AbbI~
    {                                                              //~1AbbI~
		AjagoBT inst=AG.ajagoBT;                                           //~1AbbI~
        inst.swNFC=true; //parm to BTstartServer()-->BTService.start()//~1AbbI~
    	inst.swSecureNFC=Psecure;                                  //~1AbbI~
    	AG.swSecureNFCBT=Psecure;                                  //~1AbgR~
	    boolean rc=startListen();                                  //~1AbbI~
        AG.isNFCBT=true;                                           //~1AbgR~
	    return rc;                                                 //~1AbbI~
    }                                                              //~1AbbI~
//********************************************************************//~1AbtI~
//* from Bluetooth                                                 //~1AbtI~
//* strtServer with secure option                                  //~1AbtI~
//********************************************************************//~1AbtI~
    public static boolean startListenNonNFC(boolean Psecure)       //~1AbtI~
    {                                                              //~1AbtI~
		AjagoBT inst=AG.ajagoBT;                                           //~1AbtI~
        inst.swNFC=false; //parm to BTstartServer()-->BTService.start()//~1AbtI~
    	inst.swSecureNonNFCAccept=Psecure;                         //~1AbtI~
	    boolean rc=startListen();                                  //~1AbtI~
	    return rc;                                                 //~1AbtI~
    }                                                              //~1AbtI~
//********************************************************************//~v107I~
    public void resume()                                           //~v107I~
    {                                                              //~v107I~
        try                                                        //~v107I~
        {                                                          //~v107I~
        	if (mBTC!=null)                                              //~v107I~
			    mBTC.BTResume();                                   //~v107R~
        }                                                          //~v107I~
        catch(Exception e)                                         //~v107R~
        {                                                          //~v107I~
            Dump.println(e,"AjagoBT:resume");                      //~v107I~
            AjagoView.showToast(R.string.failedBluetooth);         //~v107I~
        }                                                          //~v107I~
    }                                                              //~v107I~
//********************************************************************//~v107I~
    public boolean destroy()                                          //~v107I~//~v110R~//~1Ae5R~
    {                                                              //~v107I~
    	boolean rc=false;                                              //~v110I~//~1Ae5R~
    //*************************                                    //~v110I~
		if (Dump.Y) Dump.println("ABT:destroy");                   //~1Ae5I~
    	swDestroy=true;                                            //~@@@2I~//~1Ae5I~
        try                                                        //~v107I~
        {                                                          //~v107I~
        	if (mBTC!=null)                                              //~v107I~
            {                                                      //~v110I~
    	    	rc=mBTC.BTDestroy();                                  //~v107R~//~v110R~//~1Ae5R~
            }                                                      //~v110I~
            if (mBTSocket!=null)                                   //~@@@2I~//~v110I~
            {                                                      //~@@@2I~//~v110I~
            	rc=true;                                           //~v110I~//~1Ae5R~
				if (Dump.Y) Dump.println("AjagoBT:close mBTSocket="+mBTSocket.toString());//~@@@2I~//~v110I~
            	mBTSocket.close();                                 //~@@@2I~//~v110I~
                mBTSocket=null;                                     //~@@@2I~//~v110I~
            }                                                      //~@@@2I~//~v110I~
        }                                                          //~v107I~
        catch(Exception e)                                         //~v107I~
        {                                                          //~v107I~
            Dump.println(e,"AjagoBT:destroy");                     //~v107I~
            AjagoView.showToast(R.string.failedBluetooth);         //~v107I~
        }                                                          //~v107I~
        return rc;                                                 //~v110I~//~1Ae5R~
    }                                                              //~v107I~
//*************************************************************************//~v107I~
    public void activityResult(int requestCode, int resultCode, Intent data)//~v107I~
    {                                                              //~v107I~
		if (Dump.Y) Dump.println("ABT:activityResult");                   //~v110I~//~1Ae5R~
//  	swDestroy=true;                                            //~v110I~//~1Ae5R~
        try                                                        //~v107I~
        {                                                          //~v107I~
	    	mBTC.BTActivityResult(requestCode,resultCode,data);    //~v107R~
        }                                                          //~v107I~
        catch(Exception e)                                         //~v107I~
        {                                                          //~v107I~
            Dump.println(e,"AjagoBT:activityResult");              //~v107I~
            AjagoView.showToast(R.string.failedBluetooth);         //~v107I~
        }                                                          //~v107I~
    }                                                              //~v107I~
////********************************************************************//~v107I~//~1Ae5R~
////*from MenuBar;add submenu to mainframe                           //~v107R~//~1Ae5R~
////********************************************************************//~v107I~//~1Ae5R~
//    public void addBTMenu(MenuBar Pmenubar)                        //~v107R~//~1Ae5R~
//    {                                                              //~v107I~//~1Ae5R~
//        if (bluetooth==null)                                       //~v107I~//~1Ae5R~
//        {                                                          //~v107I~//~1Ae5R~
//            bluetooth=new MyMenu(Global.resourceString("Bluetooth"));//~v107R~//~1Ae5R~
//            bluetooth.add(new MenuItemAction(this,Global.resourceString(STARTSERVER)));//~v107R~//~1Ae5R~
//            bluetooth.add(new MenuItemAction(this,Global.resourceString(CONNECT)));//~v107R~//~1Ae5R~
//            bluetooth.add(new MenuItemAction(this,Global.resourceString(DISCOVERABLE)));//~v107M~//~1Ae5R~
//            bluetooth.add(new MenuItemAction(this,Global.resourceString(BTHELP)));//~1B1hI~//~1Ae5R~
//        }                                                          //~v107I~//~1Ae5R~
//        Pmenubar.menuList.add(bluetooth);                          //~v107R~//~1Ae5R~
//    }                                                              //~v107I~//~1Ae5R~
////********************************************************************//~v107I~//~1Ae5R~
////*implement DoActionListener                                      //~v107R~//~1Ae5R~
////********************************************************************//~v107I~//~1Ae5R~
//    @Override                                                      //~v107I~//~1Ae5R~
//    public void doAction(String o)                                 //~v107I~//~1Ae5R~
//    {                                                              //~v107I~//~1Ae5R~
//        try                                                        //~v107I~//~1Ae5R~
//        {                                                          //~v107I~//~1Ae5R~
//            if (mBTC==null)                                        //~v107I~//~1Ae5R~
//            {                                                      //~v107I~//~1Ae5R~
//                AjagoView.showToast(R.string.noBTadapter);         //~v107I~//~1Ae5R~
//                return;                                            //~v107I~//~1Ae5R~
//            }                                                      //~v107I~//~1Ae5R~
//            if (Global.resourceString(DISCOVERABLE).equals(o))     //~v107R~//~1Ae5R~
//                setDiscoverable();                                 //~v107R~//~1Ae5R~
//            else                                                   //~v107R~//~1Ae5R~
//            if (Global.resourceString(STARTSERVER).equals(o))      //~v107R~//~1Ae5R~
//                startServer();                                     //~v107R~//~1Ae5R~
//            else                                                   //~v107R~//~1Ae5R~
//            if (Global.resourceString(CONNECT).equals(o))          //~v107R~//~1Ae5R~
//                connect();                                         //~v107R~//~1Ae5R~
//            else                                                   //~1B1hI~//~1Ae5R~
//            if (Global.resourceString(BTHELP).equals(o))           //~1B1hI~//~1Ae5R~
//            {                                                      //~1B1hI~//~1Ae5R~
//                new HelpDialog(Global.frame(),"bluetoothAjagoc");  //~1B1hI~//~1Ae5R~
//            }                                                      //~1B1hI~//~1Ae5R~
//        }                                                          //~v107I~//~1Ae5R~
//        catch(Exception e)                                         //~v107I~//~1Ae5R~
//        {                                                          //~v107I~//~1Ae5R~
//            Dump.println(e,"AjagoBT:doAction");                    //~v107I~//~1Ae5R~
//            AjagoView.showToast(R.string.failedBluetooth);         //~v107I~//~1Ae5R~
//        }                                                          //~v107I~//~1Ae5R~
//    }                                                              //~v107I~//~1Ae5R~
////***************************************************************************//~v107I~//~1Ae5R~
//    @Override                                                      //~v107M~//~1Ae5R~
//    public void itemAction(String o, boolean flag) {               //~v107M~//~1Ae5R~
//    }                                                              //~v107M~//~1Ae5R~
//***************************************************************************//~v107I~
//  private void setDiscoverable()                                 //~v107R~//~1Ae5R~
    public void setDiscoverable()                                  //~v107R~//~1Ae5I~
    {                                                              //~v107I~
    	if (Dump.Y) Dump.println("AjagoBT:setDiscoverable");       //~v107I~
        if (mBTC==null)                                            //~v107I~//~1AbWR~
        {                                                          //~1AbWR~
			BTNotAvailable();                                      //~1AbWR~
            return;                                                //~v107I~//~1AbWR~
        }                                                          //~1AbWR~
        if (mBTC.BTensureDiscoverable(true/*request discoverable if not*/)==0)	//already discoverable//~v107R~
            AjagoView.showToast(R.string.nowDiscoverable);        //~v107I~
    }                                                              //~v107I~
//***************************************************************************//~@@@@I~//~v107M~
//  private void startServer()                                     //~v107R~//~1Ae5R~
    public boolean startServer()                                      //~v107R~//~v101R~//~1Ae5I~
    {                                                              //~v107I~
        boolean rc=false;                                          //~v101I~//~1Ae5I~
    //*******************                                          //~v107I~
    	if (Dump.Y) Dump.println("AjagoBT:startServer");           //~v107I~
        if (mBTC==null)                                            //~v107I~//~1Ae5I~
            return false;                                                //~v107I~//~v101R~//~1Ae5I~
    	if (Go.isAliveOtherSession(AG.AST_BT,true/*duper*/))       //~1A8gR~
//          return;                                                //~1Aa0I~//~1Ae5R~
            return false;                                          //~1Aa0R~//~1Ae5I~
//      swServer=true;                                             //~v107I~//~1Ae5R~
		if (mBTSocket==null)                                       //~@@@@R~//~1Ae5I~
//      mBTSocket=mBTC.BTstartServer();                            //~v107R~//~1Ae5R~
            rc=mBTC.BTstartServer();	//true if accept issued    //~v101I~//~1Ae5I~
//      if (mBTSocket!=null)                                       //~v107I~//~1Ae5R~
//      	openServer(mBTSocket);                                 //~v107I~//~1Ae5R~
        return rc;                                                 //~v101I~//~1Ae5I~
    }                                                              //~v107I~
////***************************************************************************//~v107I~//~1Ae5R~
//    private void connect()                //~@@@@I~                //~v107R~//~1Ae5R~
//    {                                                              //~@@@@I~//~v107I~//~1Ae5R~
//        int rc;                                                    //~v107I~//~1Ae5R~
//    //**********************                                       //~v107I~//~1Ae5R~
//        if (Dump.Y) Dump.println("AjagoBT:connect");               //~v107I~//~1Ae5R~
//        if (Go.isAliveOtherSession(AG.AST_BT,true/*dupok*/))       //~1A8gR~//~1Ae5R~
//            return;                                                //~1A8gI~//~1Ae5R~
//        swServer=false;                                            //~v107I~//~1Ae5R~
//        rc=mBTC.BTconnect();                                       //~v107R~//~1Ae5R~
//        if (rc==1)  //connecting                                   //~v107I~//~1Ae5R~
//        {                                                          //~v107I~//~1Ae5R~
//            new jagoclient.dialogs.Message(Global.frame(),Global.resourceString("Already_trying_this_connection"));//~v107I~//~1Ae5R~
//            return;                                                //~v107I~//~1Ae5R~
//        }                                                          //~v107I~//~1Ae5R~
//    }                                                              //~@@@@I~//~v107I~//~1Ae5R~
//***************************************************************************//~@@@2I~//~1AbgI~
//* device addr specified connect                                  //~@@@2I~//~1AbgI~
//***************************************************************************//~@@@2I~//~1AbgI~
    public void connect(String Pname,String Paddr,boolean Psecure) //~1A60I~//~1AbgI~
    {                                                              //~@@@2I~//~1AbgI~
    	int rc;                                                    //~@@@2I~//~1AbgI~
    //**********************                                       //~@@@2I~//~1AbgI~
    	swSecureConnect=Psecure;                                   //+1AedI~
	    requestDeviceName=Pname;                                   //~@@@2I~//~1AbgI~
    	if (Dump.Y) Dump.println("ABT:connect device="+Pname+",addr="+Paddr);//~@@@2R~//~1AbgI~
        if (mBTC==null)                                            //~@@@2I~//~1AbgI~
            return;                                                 //~@@@2I~//~1AbgI~
    	if (/*MainFrame*/Go.isAliveOtherSession(AG.AST_BT,true/*dupok*/))//~1A8gI~//~1AbgI~
            return;                                                //~1A8gI~//~1AbgI~
        swConnect=true;                                            //~@@@2I~//~1AbgI~
//      rc=mBTC.BTconnect(Paddr);                                  //~@@@2I~//~1A60R~//~1AbgI~
        rc=mBTC.BTconnect(Paddr,Psecure);                          //~1A60I~//~1AbgI~
        if (rc==1)	//connecting                                   //~@@@2I~//~1AbgI~
        {                                                          //~@@@2I~//~1AbgI~
//  		new jagoclient.dialogs.Message(Global.frame(),Global.resourceString("Already_trying_this_connection"));//~@@@2I~//~1A63R~//~1AbgI~
    		AjagoView.showToastLong(Global.resourceString("Already_trying_this_connection"));//~1A63I~//~1AbgI~
//			return;                                                //~@@@2I~//~1AbgI~
  		}                                                          //~@@@2I~//~1AbgI~
	    AG.isNFCBT=false;                                          //~1AbgI~
    }                                                              //~@@@2I~//~1AbgI~
//***************************************************************************//~1AbgI~
//* connect for NFCBT                                              //~1AbgI~
//***************************************************************************//~1AbgI~
    public void connectNFC(String Pname,String Paddr,boolean Psecure)//~1AbgI~
    {                                                              //~1AbgI~
	    swNFC=true;                                                //~1AbgI~
		swSecureNFC=Psecure;                                       //~1AbgI~
    	AG.swSecureNFCBT=Psecure;                                  //~1AbgI~
	    connect(Pname,Paddr,Psecure);                              //~1AbgI~
	    AG.isNFCBT=true;                                           //~1AbgI~
    }                                                              //~1AbgI~
//***************************************************************************//~v107I~
//*from BTControl;after request enable completed                   //~v107R~
//***************************************************************************//~v107I~
    public void enabled()                                          //~v107R~
    {                                                              //~v107I~
//  	if (Dump.Y) Dump.println("AjagoBT:enabled swServer="+swServer);//~v107R~//~1Ae5R~
    	if (Dump.Y) Dump.println("ABT:enabled swServer="+swConnect);//~v107R~//~@@@@R~//~1Ae5I~
//        if (swServer)                                              //~v107I~//~1Ae5R~
//        {                                                          //~v107I~//~1Ae5R~
//            startServer();                                         //~v107I~//~1Ae5R~
//        }                                                          //~v107I~//~1Ae5R~
		new jagoclient.dialogs.Message(Global.frame(),R.string.InfoBTEnabled);//~v101I~//~1Ae5I~
    }                                                              //~v107I~
//***************************************************************************//~v107I~
//*from BTControl;on MainThread                                    //~v107I~
//***************************************************************************//~v107I~
    public void connected(BluetoothSocket Psocket,String Pdevicename)//~v107I~
    {                                                              //~v107I~
        String addr=Psocket.getRemoteDevice().getAddress();        //~1AbRI~
    	if (Dump.Y) Dump.println("AjagoBT:connected swConnect="+swConnect+",device="+Pdevicename+"="+addr);//~v107R~//~@@@@R~//~1AbRI~
    	mBTSocket=Psocket;                                         //~v107I~
    	mDeviceName=Pdevicename;                                   //~v107I~
        BluetoothConnection.addConnectedDevice(Pdevicename,addr);  //~1AbRI~
//      if (swServer)                                              //~v107I~//~1Ae5R~
        if (!swConnect)                                            //~@@@@I~//~1Ae5I~
        {                                                          //~v107I~
	    	openServer(mBTSocket);                                 //~v107I~
        }                                                          //~v107I~
        else                                                       //~v107I~
        {                                                          //~v107I~
	    	openPartner(mBTSocket,mDeviceName,mBTC.getDeviceName());//~v107R~
        }                                                          //~v107I~
        swConnect=false;                                           //~@@@2I~//~1Ae5I~
    }                                                              //~v107I~
//***************************************************************************//~v107I~
    private void openServer(BluetoothSocket Psocket)               //~v107I~
    {                                                              //~v107I~
    	if (Dump.Y) Dump.println("AjagoBT:openServer");            //~v107I~
    	new Server(Psocket,mDeviceName,mBTC.getDeviceName());   //open PartnerFrame//~v107R~
    }                                                              //~v107I~
//***************************************************************************//~v107I~
    private void openPartner(BluetoothSocket Psocket,String Pdevicename,String Plocaldevicename)//~v107R~
    {                                                              //~v107I~
    	if (Dump.Y) Dump.println("ABT:openPartner socket="+Psocket.toString());//~@@@2I~//~1Ae5I~
    	if (Dump.Y) Dump.println("device="+Pdevicename+",local="+Plocaldevicename);//~@@@2I~//~1Ae5I~
    	partnerFrame=new PartnerFrame(Global.resourceString("Connection_to_"),Pdevicename,Plocaldevicename,false,Psocket);
//      partnerFrame.connect();                                    //~v107I~//~1AbmR~
        if (partnerFrame.connect())	//connected                                    //~v107I~//~@@@2R~//~1AbmI~
        {                                                          //~1AbmI~
//          partnerFrame.doAction(AG.resource.getString(R.string.Game));//~@@@2I~//~1AbmI~//~1Ae9R~
//          DialogNFCBT.chkSecureLevelMsg();	//after dismiss,issue alertDialog on GameQuetion dialog//~1AbmI~//~1Ae9R~
            partnerFrame.openPartner();                            //~1Ae9I~
        }                                                          //~1AbmI~
    }                                                              //~v107I~
//***************************************************************************//~v107I~
    public void connectionLost()                           //~v107I~
    {                                                              //~v107I~
    	mBTC.connectionLost();                                     //~v107I~
        if (mBTSocket!=null)                                       //~v110R~
        {                                                          //~v110R~
        	try                                                    //~v110R~
            {                                                      //~v110R~
                if (Dump.Y) Dump.println("AjagoBT connectionLost():close btsocket="+mBTSocket.toString());//~v110R~
    			mBTSocket.close();                                 //~v110R~
            }                                                      //~v110R~
            catch(Exception e)                                     //~v110R~
            {                                                      //~v110R~
	            Dump.println(e,"ABT:connectionLost:close()");      //~v110R~
            }                                                      //~v110R~
            mBTSocket=null;                                        //~v110R~
        }                                                          //~v110R~
        swConnect=false;                                           //~@@@2I~//~1Ae5I~
    }                                                              //~v107I~
//***************************************************************************//~@@@@I~//~1Ae5I~
    public boolean isConnectionAlive()                                //~@@@@I~//~1Ae5I~
    {                                                              //~@@@@I~//~1Ae5I~
    	return (mBTC!=null)&&mBTC.isConnectionAlive();             //~@@@@I~//~1Ae5I~
    }                                                              //~@@@@I~//~1Ae5I~
//***************************************************************************//~@@@2I~//~1Ae5I~
    public boolean stopListen()                                    //~@@@2I~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
    	return (mBTC!=null)&&mBTC.stopListen();                    //~@@@2I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//***************************************************************************//~@@@2I~//~1Ae5I~
//*BTService-->BTControl-->                                        //~@@@2I~//~1Ae5I~
//***************************************************************************//~@@@2I~//~1Ae5I~
    public void connFailed(int flag)                               //~@@@2I~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
        if (Dump.Y) Dump.println("ABT:connFailed "+flag);          //~@@@2I~//~1Ae5I~
        swConnect=false;                                           //~@@@2I~//~1Ae5I~
        PartnerFrame.dismissWaitingDialog();                       //~@@@2I~//~1Ae5I~
      if (flag==BTService.CONN_FAILED)                             //~1A6mI~//~1Ae5I~
      {                                                            //+1AedI~
    	String secureopt=AG.resource.getString(swSecureConnect ? R.string.BTSecure : R.string.BTInSecure);//+1AedI~
		new Message(Global.frame(),                                //~@@@2I~//~1Ae5I~
//  		Global.resourceString("No_connection_to_")+requestDeviceName);//~@@@2I~//~1A6iR~//~1Ae5I~
//  		AG.resource.getString(R.string.Err_No_connection_to_BT,requestDeviceName));//~1A6iI~//~1Ae5I~//+1AedR~
    		AG.resource.getString(R.string.Err_No_connection_to_BT,requestDeviceName,secureopt));//+1AedI~
      }                                                            //+1AedI~
      else                                                         //~1A6mI~//~1Ae5I~
		new Message(Global.frame(),                                //~1A6mI~//~1Ae5I~
    		Global.resourceString("No_connection_to_")+requestDeviceName);//~1A6mI~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//***************************************************************************//~@@@2I~//~1Ae5I~
    public void acceptFailed(String Psecure)                       //~@@@2I~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
        if (Dump.Y) Dump.println("ABT:acceptFailed "+Psecure);     //~@@@2I~//~1Ae5I~
        PartnerFrame.dismissWaitingDialog();                       //~@@@2I~//~1Ae5I~
		new Message(Global.frame(),AG.resource.getString(R.string.ErrAcceptfailed,Psecure));//~@@@2I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//***************************************************************************//~@@@2I~//~1Ae5I~
    public String[] getPairDeviceList()                            //~@@@2R~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
        if (mBTC==null)                                            //~@@@2I~//~1Ae5I~
            return null;                                           //~@@@2I~//~1Ae5I~
//        return BTList.getPairDeviceList();                       //~@@@2R~//~1Ae5I~
        if (!mBTC.BTEnable(false/*not resume*/))                                      //~v101I~//~1Ae5I~
            return null;                                           //~v101I~//~1Ae5I~
        return BTDiscover.getPairDeviceList();                     //~@@@2I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//***************************************************************************//~@@@2I~//~1AbRI~
    public boolean discover(DoActionListener Pdal)                 //~@@@2R~//~1AbRI~
    {                                                              //~@@@2I~//~1AbRI~
        if (mBTC==null)                                            //~@@@2I~//~1AbWR~
//          return false;                                          //~@@@2R~//~1AbWR~
			return BTNotAvailable();                               //~1AbWR~
        return mBTC.discover(Pdal);                              //~@@@2R~//~1AbRI~
    }                                                              //~@@@2I~//~1AbRI~
//***************************************************************************//~@@@2I~//~1Ae5I~
    public boolean cancelDiscover()                                //~@@@2I~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
        if (mBTC==null)                                            //~@@@2I~//~1Ae5I~
            return false;                                          //~@@@2I~//~1Ae5I~
        return mBTC.cancelDiscover();                              //~@@@2I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//***************************************************************************//~@@@2I~//~1Ae5I~
    public String[] getNewDevice()                                 //~@@@2I~//~1Ae5I~
    {                                                              //~@@@2I~//~1Ae5I~
        return BTDiscover.newDevice;                             //~@@@2I~//~1Ae5I~
    }                                                              //~@@@2I~//~1Ae5I~
//***************************************************************************//~1A6bI~//~1Ae5I~
//*from AMain                                                      //~1A6bI~//~1Ae5I~
//***************************************************************************//~1A6bI~//~1Ae5I~
    public void onDestroy()                                        //~1A6bI~//~1Ae5I~
    {                                                              //~1A6bI~//~1Ae5I~
        if (Dump.Y) Dump.println("ABT onDestroy");                 //~1A6bI~//~1Ae5I~
	    mBTD.unregister();                                          //~1A6bI~//~1Ae5I~
    }                                                              //~1A6bI~//~1Ae5I~
//***************************************************************************//~1AbWR~
	public static boolean BTNotAvailable()                         //~1AbWR~
    {                                                              //~1AbWR~
        AjagoView.showToast(R.string.BTNotAvailable);                   //~1AbWR~
        return false;                                              //~1AbWR~
    }                                                              //~1AbWR~
//***************************************************************************//~1Ac5I~
	public static boolean BTisDiscoverable()                       //~1Ac5I~
    {                                                              //~1Ac5I~
        return AG.ajagoBT.mBTC.BTisDiscoverable()==1;                  //~1Ac5I~
    }                                                              //~1Ac5I~
}//class                                                           //~1109R~