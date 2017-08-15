//*CID://+1Af6R~:                             update#=   82;       //+1Af6R~
//*****************************************************************//~v101I~
//1Af6 2016/07/08 NPE when bluetooth is not supported              //+1Af6I~
//1Af3 2016/07/06 (Ajagot1w)Display Discoverable status            //~1Af3I~
//1Af1 2016/07/05 update bluetooth connection dialog from bluetooth receiver//~1Af1I~
//1Aep 2015/08/09 (Bug) NPE when Bluetooth is not available        //~1AepI~
//1Aei 2015/08/06 Bluetooth +RU was not put to preference(Bluetooth CloseDialog was already closed)//~1AeiI~
//1Aed 2015/07/30 show secure/insecure option to waiting msg       //~1AedI~
//1Ae9 2015/07/26 (Ajagoc only)All connection type passes to jagoclient PartnerFrame to (re)start/disconnect//~1Ae9I~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//1Ad2 2015/07/17 HelpDialog by helptext                           //~1Ad2I~
//1Ac5 2015/07/09 NFCBT:confirmation dialog is not show and fails to pairig//~1Ac5I~
//1AbW 2015/07/05 BT:chk Bluetooth supported foy system bluetooth  //~1AbWI~
//1AbV 2015/07/05 BT:default change to secure                      //~1AbVI~
//1AbU 2015/07/04 BT:change String[] DeviceList to LinkedHashList  //~1AbUI~
//1AbT 2015/07/04 BT:keep discovered device in the session to avoid discovery//~1AbTI~
//1AbS 2015/07/03 BT:LeastRecentlyUsed List for once conneceted device list//~1AbSI~
//1AbR 2015/07/03 BT:additional to paired, add device not paired but connected to devlice list//~1AbRI~
//1AbE 2015/06/27 Add setting button to Remote-Bluetooth           //~1AbEI~
//1Abv 2015/06/19 BT:dismiss dialog at boardQuestion for also BT like as 1Abj//~1AbuI~
//1Abu 2015/06/18 BT:Secure option by radio button(Incsecure connection is only when insecure Accept & Insecure Connect)//~1AbuI~
//1Abt 2015/06/18 BT:Accept not both of Secure/Insecure but one of two.//~1AbtI~
//1Abi 2015/06/15 NFCBT:put DisconnectButton after Game button     //~1AbiI~
//1Abf 2015/06/15 NFCBT:no waiting ProgDialog is shown,dispose()=dismiss()->dismiss listener is sheduled before waitingMsg is set)//~1AbfI~
//1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8I~
//1A6r 2015/02/16 BT:update connection status when lost connection //~1A6rI~
//1A2j 2015/02/16 Asgts:2013/04/03 (Bug)sendsuspend(not main thread) call ProgDialog//~1A2jI~
//1A6k 2015/02/15 re-open IPConnection/BTConnection dialog when diconnected when dislog is opened.//~1A6kI~
//1A6i 2015/02/14 Bluetooth;display current bonded(paired) device list not initial status.//~1A6iI~
//1A6f 2015/02/13 support cutom layout of ListView for BluetoothConnection to show available/paired status//~1A6fI~
//1A6c 2015/02/13 Bluetooth;identify paired device and discovered device//~1A6cI~
//1A62 2015/01/27 keep BT dialog when discoverable button          //~1A62I~
//1A61 2015/01/27 avoid to fill screen when listview has few entry.(Motorolla is dencity=1.0(mdpi)//~1A60I~
//1A60 2015/01/25 also support Bluetooth secure SPP(Bluetooth 2.1) //~1A60I~
//101i 2013/02/09 for the case Bluetoothe disabled at initial      //~v101I~
//*****************************************************************//~v101I~
package jagoclient.partner;
//import com.Ahsv.awt.GridLayout;                                  //~2C26R~
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.Ajagoc.AjagoBT;
import com.Ajagoc.AG;
import com.Ajagoc.AjagoView;
import com.Ajagoc.AjagoAlert;
import com.Ajagoc.AjagoAlertI;
import com.Ajagoc.ProgDlg;
import com.Ajagoc.ProgDlgI;
import com.Ajagoc.AjagoProp;
import com.Ajagoc.R;
import com.Ajagoc.URunnable;
import com.Ajagoc.URunnableI;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Component;
import com.Ajagoc.awt.Container;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.List;                                          //~3203I~
import com.Ajagoc.awt.ListData;

import jagoclient.Dump;
import jagoclient.Global;                                          //~3203I~
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;

//import java.awt.GridLayout;
//import java.awt.Panel;
//import java.awt.TextField;

/**
Question to accept or decline a game with received parameters.
*/

public class BluetoothConnection extends CloseDialog               //~3105R~
		implements ProgDlgI                                         //~3203I~
{                                                                  //~2C29R~
    private static final String PKEY_BTSECURE="BTSecureOptionNonNFC";//~1AbuI~
//  private static final int BTSECURE_DEFAULT=0; //default InSecure//~1AbuI~//~1AbVR~
    private static final int BTSECURE_DEFAULT=1; //default Secure  //~1AbVR~
    private static final int STRID_ACCEPT=R.string.BTAcceptCommon; //~1AbuI~
    private static final int STRID_CONNECT=R.string.BTConnectCommon;//~1AbuI~
    private static final int MAX_LRU=5;                            //~1AbSI~
    private static final String PROPKEY_BTLRU="BTConnectedOnceLRU";//~1AbSI~
//  GoFrame GF;                                                    //~3105R~//~1Ae5R~
    Frame GF;                                                      //~1Ae5I~
    private int waitingDialog=0;                                   //~3201I~
    private ButtonAction acceptButton,gameButton,connectButton;                              //~3201I~//~3208R~
//  private ButtonAction settingsButton;                           //~1AbEI~//~1AbUR~
//  private ButtonAction connectSecureButton;                                   //~1A60I~//~1AbuR~
//  private ButtonAction acceptSecureButton;                       //~1AbtI~//~1AbuR~
    private ButtonAction disconnectButton;                         //~1AbiI~
    private ButtonAction discoverableButton;                       //~1Af3I~
//  private ButtonAction deleteButton;                             //~1AbSI~//~1AbUR~
    private CheckBox cbSecureOption;                               //~1AbuI~
    private TextView tvRemoteDevice;                               //~1A6fR~
//  private static String[] DeviceList;	//not each time to avoid deadlock                                    //~3203I~//~3204R~//~3205R~//~3206R~//~1AbTR~
//  private static String[] discoveredDeviceList;	//not each time to avoid deadlock//~1AbTI~//~1AbUR~
    private static DeviceDataList SdeviceList;                                   //~1AbTR~//~1AbUR~
//  private static String[] pairedList;                            //~1AbTI~
    private static String[] pairedDeviceList;                      //~1AbTI~
//  private static String[] DeviceListOld;                         //~3206I~//~1A6iR~
//  private List DL;   	//listview                                 //~3203I~//~1A6fR~
    private ListBT DL;   	//listview                             //~1A6fI~
    private boolean onDiscovery;                                   //~3203I~
    int waitingid;//~3203I~
//    private String SlastConnectName;                               //~3204I~
    private String myDevice,connectingDevice;                      //~v101I~
    private boolean swCancelDiscover;                                      //~3205I~
    private static int lastSelected=-1;                            //~3205I~
    private static final int connectedColor=android.graphics.Color.argb(0xff,0xff,0xff,0x00);//~3207I~
    private static final int ID_STATUS_PAIRED=R.string.BTStatusPaired;   //~1A6fI~//~1AbUI~
    private static final int ID_STATUS_DISCOVERED=R.string.BTStatusDiscovered;//~1A6fI~//~1AbUI~
    private static final int ID_STATUS_CONNECTED=R.string.BTStatusConnected;//~1A6fI~//~1AbUI~
    private static final int ID_STATUS_CONNECTED_ONCE=R.string.BTStatusConnectedOnce;//~1AbRI~//~1AbUR~//~1AbRI~
//  private static int ID_STATUS_DISCONNECTED=R.string.BTStatusDisConnected;//~1A6rI~//~1AbUR~
    private String statusPaired,statusDiscovered,statusConnected;//~1A6fR~//~1A6kR~
//  private String statusDisconnected;                             //~1A6rI~//~1AbUR~
    private String statusConnectedOnce;                            //~1AbRI~
    private boolean swSecure;                                      //~1AbuI~
                                                                   //~1A6fI~
    	private static final int BTROW_NAME=R.id.ListViewLine;     //~1A6fM~
    	private static final int BTROW_STATUS=R.id.ListViewLineStatus;//~1A6fM~
	    private static final Color COLOR_STATUS_PAIRED=new Color(0, 0x80, 0x80);//~1A6fM~//~1A6kR~
	    private static final Color COLOR_STATUS_DISCOVERED=new Color(0, 0xc0, 0xc0);//~1A6fM~//~1A6kR~
	    private static final Color COLOR_STATUS_CONNECTED=Color.orange;//~1A6kR~
    private static Map<String,String> SdeviceListLRU=new LinkedHashMap<String,String>();//~1AbSR~//~1AbTR~//~1AbUR~
    private static boolean swLRULoaded;                            //~1AbSI~
    private static boolean swServer,swAlertAction;                 //~1Ac5I~
                                                                   //~1A6fI~
//  public BluetoothConnection (GoFrame Pgf)                       //~3105R~//~1Ae5R~
    public BluetoothConnection (Frame Pgf)                         //~1Ae5I~
	{                                                              //~3105R~
//  	super(Pgf,AG.resource.getString(R.string.Title_Bluetooth),R.layout.bluetooth,true,false);//~3105I~//~3118R~//~1A60R~
    	super(Pgf,AG.resource.getString(R.string.Title_Bluetooth), //~1A60I~
				(AG.screenDencityMdpiSmallV || AG.screenDencityMdpiSmallH/*mdpi and height or width <=320*/ ? R.layout.bluetooth_mdpi : R.layout.bluetooth),//~1A61R~//~1A60I~
				true,false);                                       //~1A60I~
		GF=Pgf;                                                    //~3105R~
        statusPaired=AG.resource.getString(ID_STATUS_PAIRED);      //~1A6fM~
        statusDiscovered=AG.resource.getString(ID_STATUS_DISCOVERED);//~1A6fM~
        statusConnected=AG.resource.getString(ID_STATUS_CONNECTED);//~1A6fR~
        statusConnectedOnce=AG.resource.getString(ID_STATUS_CONNECTED_ONCE);//~1AbRI~
//      statusDisconnected=AG.resource.getString(ID_STATUS_DISCONNECTED);//~1A6rI~//~1AbUR~
        tvRemoteDevice=(TextView)(findViewById(R.id.RemoteDevice));//~1A6fR~
        cbSecureOption=(CheckBox)(findViewById(R.id.BTSecureOption));//~1AbuI~
        discoverableButton=new ButtonAction(this,0,R.id.Discoverable);//~1Af3I~
        displayDevice();                                           //~3202I~
        if (SdeviceList==null)                                          //~1AbTI~//~1AbUI~
			SdeviceList=new DeviceDataList();                                    //~1AbTI~//~1AbUI~
	    getDeviceList();                               //~v101R~   //~@@@2R~
	    setSelection();                                            //~3204I~
//      settingsButton=new ButtonAction(this,0,R.id.BTSettings);   //~1AbEI~//~1AbUR~
        new ButtonAction(this,0,R.id.BTSettings);                  //~1AbUI~
//        acceptButton=ButtonAction.init(this,0,R.id.BTAccept);                     //~3117I~//~3201R~//~3208R~
//        gameButton=ButtonAction.init(this,0,R.id.BTGame);                //~3201R~//~3208R~
//        connectButton=ButtonAction.init(this,0,R.id.BTConnect);  //~3208R~
        acceptButton=new ButtonAction(this,0,R.id.BTAccept);  //~3208I~
//      acceptSecureButton=new ButtonAction(this,0,R.id.BTAcceptSecure);//~1AbtI~//~1AbuR~
        gameButton=new ButtonAction(this,0,R.id.BTGame);      //~3208I~
        connectButton=new ButtonAction(this,0,R.id.BTConnect);//~3208I~
//      connectSecureButton=new ButtonAction(this,0,R.id.BTConnectSecure);//~1A60I~//~1AbuR~
        disconnectButton=new ButtonAction(this,0,R.id.BTDisConnect);//~1AbiI~
        new ButtonAction(this,0,R.id.Delete);         //~1AbSI~    //~1AbUR~
//        ButtonAction.init(this,0,R.id.Discoverable);                  //~2C30I~//~3105R~//~3203M~//~3208R~
//        ButtonAction.init(this,0,R.id.Discover);                   //~3203I~//~3208R~
//        ButtonAction.init(this,0,R.id.Cancel);                     //~2C30I~//~3105R~//~3117R~//~3208R~
//        ButtonAction.init(this,0,R.id.Help);                       //~3117I~//~3208R~
//      new ButtonAction(this,0,R.id.Discoverable);           //~3208I~//~1Af3R~
        new ButtonAction(this,0,R.id.Discover);               //~3208I~
        new ButtonAction(this,0,R.id.Cancel);                 //~3208I~
        new ButtonAction(this,0,R.id.Help);                   //~3208I~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)                    //~3201R~
        {                                                          //~3201I~
//          acceptButton.setEnabled(false);                              //~3201I~//~3208R~//~1AbtR~
//          acceptButton.setVisibility(View.GONE);                 //~1AbtR~//~1AbuR~
//          acceptSecureButton.setVisibility(View.GONE);           //~1AbtI~//~1AbuR~
//  	  if ((AG.RemoteStatusAccept & AG.RS_BTLISTENING_SECURE)!=0)//~1A60I~//~1AbiR~
//        {                                                        //~1A60I~//~1AbiR~
//          connectSecureButton.setAction(R.string.BTDisConnect);  //~1A60I~//~1AbiR~
//          connectButton.setEnabled(false);                       //~1A60I~//~1AbtR~
//        }                                                        //~1A60I~//~1AbiR~
//        else                                                     //~1A60I~//~1AbiR~
//        {                                                        //~1A60I~//~1AbiR~
//          connectButton.setAction(R.string.BTDisConnect);                   //~2C30I~//~3105R~//~3117R~//~3201R~//~3208R~//~1AbiR~
//          connectSecureButton.setEnabled(false);                 //~1A60I~//~1AbtR~
//        }                                                        //~1A60I~//~1AbiR~
//          connectButton.setVisibility(View.GONE);                //~1AbtI~//~1AbiM~//~1AbuR~
//          connectSecureButton.setVisibility(View.GONE);           //~1AbtI~//~1AbiM~//~1AbuR~
            acceptButton.setEnabled(false);                        //~1AbuI~
            connectButton.setEnabled(false);                       //~1AbuI~
        }                                                          //~3201I~
        else                                                       //~3201I~
    	if ((AG.RemoteStatusAccept & (AG.RS_BTLISTENING_SECURE|AG.RS_BTLISTENING_INSECURE))!=0)                    //~3201I~//~3207R~//~3208R~
        {                                                          //~3201I~
//          gameButton.setEnabled(false);                                //~3201I~//~3208R~//~1AbiR~
//          gameButton.setVisibility(View.GONE);                   //~1AbiI~//~1AbuR~
  	        gameButton.setEnabled(false);                          //~1AbuI~
//          disconnectButton.setVisibility(View.GONE);             //~1AbiI~//~1AbuR~
            disconnectButton.setEnabled(false);                    //~1AbuI~
//  	  if ((AG.RemoteStatusAccept & AG.RS_BTLISTENING_INSECURE)!=0) //~1AbtI~//~1AbuR~
//        {                                                        //~1AbtI~//~1AbuR~
//      	acceptButton.setAction(R.string.BTStopAccept);//~3201R~//~3203R~//~3204R~//~3208R~//~1AbuI~
//          acceptSecureButton.setEnabled(false);                  //~1AbtI~//~1AbuR~
//        }                                                        //~1AbtI~//~1AbuR~
//        else                                                     //~1AbtI~//~1AbuR~
//        {                                                        //~1AbtI~//~1AbuR~
//      	acceptSecureButton.setAction(R.string.BTStopAccept);   //~1AbtI~//~1AbuR~
//          acceptButton.setEnabled(false);                //~1AbtI~//~1AbuR~
//        }                                                        //~1AbtI~//~1AbuR~
        	acceptButton.setAction(R.string.BTStopAccept);         //~1AbuI~
        }                                                          //~3201I~
        else                                                       //~3201I~
        {                                                          //~3201I~
//          gameButton.setEnabled(false);                                //~3201I~//~3208R~//~1AbtR~
//          gameButton.setVisibility(View.GONE);                   //~1AbtI~//~1AbuR~
            gameButton.setEnabled(false);                          //~1AbuI~
//          disconnectButton.setVisibility(View.GONE);             //~1AbiI~//~1AbuR~
            disconnectButton.setEnabled(false);                    //~1AbuI~
        }                                                          //~3201I~
        setDismissActionListener(this/*DoActionListener*/);        //~3201I~
    	setFromPropSecureOption();                                 //~1AbuI~
		validate();
		show();
        AG.aBTConnection=this;	//used when PartnerThread detected err//~1A6kI~
	}
    //********************************************************************//~1Ab8I~
//  public BluetoothConnection (GoFrame Pgf,int Ptitleid,int Playoutid)//~1Ab8I~//~1Ae5R~
    public BluetoothConnection (Frame Pgf,int Ptitleid,int Playoutid)//~1Ae5I~
	{                                                              //~1Ab8I~
    	super(Pgf,AG.resource.getString(Ptitleid),Playoutid,false/*modal*/,false/*modalinput*/);//~1Ab8I~
    }                                                              //~1Ab8I~
    //******************************************                   //~1A6kI~
    public void updateViewDisconnected()                           //~1A6kI~
    {                                                              //~1A6kI~
    	acceptButton.setEnabled(true);                             //~1A6kI~//~1AbiI~
//  	acceptSecureButton.setEnabled(true);                       //~1AbiI~//~1AbuR~
//  	acceptButton.setVisibility(View.VISIBLE);                  //~1AbiI~//~1AbuR~
//  	acceptSecureButton.setVisibility(View.VISIBLE);            //~1AbiI~//~1AbuR~
//      acceptButton.setAction(R.string.BTAccept);       //~1A6kI~ //~1AbuR~
        acceptButton.setAction(STRID_ACCEPT);                      //~1AbuI~
//      acceptSecureButton.setAction(R.string.BTAcceptSecure);     //~1AbiI~//~1AbuR~
//      connectButton.setEnabled(true);                            //~1A6kI~//~1AbiR~
//      connectButton.setVisibility(View.VISIBLE);                 //~1AbiI~//~1AbuR~
    	connectButton.setEnabled(true);                            //~1AbiI~
//      connectButton.setAction(R.string.BTConnect);               //~1A6kI~//~1AbiR~
//      connectSecureButton.setEnabled(true);                      //~1A6kI~//~1AbiR~
//      connectSecureButton.setVisibility(View.VISIBLE);           //~1AbiI~//~1AbuR~
//      connectSecureButton.setAction(R.string.BTConnectSecure);   //~1A6kI~//~1AbiR~
//      gameButton.setEnabled(false);                              //~1A6kI~//~1AbiR~
//      gameButton.setVisibility(View.GONE);                       //~1AbiI~//~1AbuR~
        gameButton.setEnabled(false);                              //~1AbuI~
        disconnectButton.setEnabled(false);                        //~1AbuI~
        String s=AG.resource.getString(R.string.NoSession);        //~1A6kI~
        new Component().setText(tvRemoteDevice,s);	//do on main thread//~1A6kR~
//        if (AG.RemoteStatus==AG.RS_BTCONNECTED && AG.RemoteDeviceName!=null)//~1A6rI~//~1AbSR~
//        {                                                          //~1A6rI~//~1AbSR~
//            DL.update(AG.RemoteDeviceName,statusDisconnected);     //~1A6rR~//~1AbSR~
//            setSelection();                                        //~1A6rI~//~1AbSR~
//        }                                                          //~1A6rI~//~1AbSR~
    }                                                              //~1A6kI~
    //******************************************                   //~1A6kI~
	public void doAction (String o)
	{                                                              //~2C26R~
    	try                                                        //~3117I~
        {                                                          //~3117I~
            if (o.equals(AG.resource.getString(R.string.BTGame)))  //~3201I~
            {                                                      //~3201I~
//			    if (startGame())                                   //~3201I~//~1Ae9R~
  			    if (showPF())                                      //~1Ae9I~
			    {                                                  //~3201I~
			    	setVisible(false); dispose();                  //~3201I~
			    }                                                  //~3201I~
            }                                                      //~3201I~
            else                                                   //~3201I~
//          if (o.equals(AG.resource.getString(R.string.BTConnect)))     //~@@@@I~//~2C30I~//~3105R~//~3117R~//~3201R~//~1AbuR~
            if (o.equals(AG.resource.getString(STRID_CONNECT)))    //~1AbuI~
            {                                                          //~2C30R~//~3117R~
                if (AG.ajagoBT.mBTC==null)                         //~1AepI~
                {                                                  //~1AemI~//~1AepI~
                    AjagoBT.BTNotAvailable();                      //~1AemI~//~1AepI~
                    return;                                        //~1AepI~
                }                                                  //~1AemI~//~1AepI~
//                setVisible(false); dispose();                      //~3117R~//~3203R~
//                AG.aBT.connect();                                      //~3105I~//~3117R~//~3203R~
//                waitingDialog=R.string.BTConnect;             //~3201I~//~3203R~
//  		    if (connectPartner())                              //~3203I~//~1A60R~
//  		    if (connectPartner(false))                         //~1A60I~//~1AbuR~
                swSecure=getSecureOption();                        //~1AbuI~
    		    if (connectPartner(swSecure))                      //~1AbuI~
                {                                                  //~3203I~
//                  setVisible(false); dispose();                  //~3203I~//~1AbfR~
	            	acceptButton.setEnabled(false);                //~1AbiI~
//              	acceptSecureButton.setEnabled(false);          //~1AbiI~//~1AbuR~
	            	connectButton.setEnabled(false);               //~1AbiI~
//              	connectSecureButton.setEnabled(false);         //~1AbiI~//~1AbuR~
//                  waitingDialog=R.string.BTConnect;              //~3203I~//~1AbuR~
                    waitingDialog=STRID_CONNECT;                   //~1AbuI~
                    setVisible(false); dispose();                  //~1AbfI~
                }                                                  //~3203I~
            }                                                      //~3117R~
//            else                                                   //~3201I~//~1AbuR~
//            if (o.equals(AG.resource.getString(R.string.BTConnectSecure)))//~1A60I~//~1AbuR~
//            {                                                      //~1A60I~//~1AbuR~
//                if (connectPartner(true))                          //~1A60R~//~1AbuR~
//                {                                                  //~1A60I~//~1AbuR~
////                  setVisible(false); dispose();                  //~1A60I~//~1AbfR~//~1AbuR~
//                    acceptButton.setEnabled(false);                //~1AbiI~//~1AbuR~
////                  acceptSecureButton.setEnabled(false);          //~1AbiI~//~1AbuR~
//                    connectButton.setEnabled(false);               //~1AbiI~//~1AbuR~
////                  connectSecureButton.setEnabled(false);         //~1AbiI~//~1AbuR~
//                    waitingDialog=R.string.BTConnect;              //~1A60I~//~1AbuR~
//                    setVisible(false); dispose();                  //~1AbfI~//~1AbuR~
//                }                                                  //~1A60I~//~1AbuR~
//            }                                                      //~1A60I~//~1AbuR~
            else                                                   //~1A60I~
            if (o.equals(AG.resource.getString(R.string.BTDisConnect)))//~3201I~
            {                                                      //~3201I~
//              setVisible(false); dispose();                      //~3201I~//~1AbfR~
                waitingDialog=R.string.BTDisConnect;               //~3207I~
                setVisible(false); dispose();                      //~1AbfI~
            }                                                      //~3201I~
            else                                                   //~3117R~
//          if (o.equals(AG.resource.getString(R.string.BTAccept))) //~3117R~//~3201R~//~1AbuR~
            if (o.equals(AG.resource.getString(STRID_ACCEPT)))     //~1AbuI~
            {                                                      //~3117R~
                if (AG.ajagoBT.mBTC==null)                         //~1AepI~
                {                                                  //~1AepI~
                    AjagoBT.BTNotAvailable();                      //~1AepI~
                    return;                                        //~1AepI~
                }                                                  //~1AepI~
    			if (!chkDiscoverable(swSecure,true/*server*/))     //~1Ac5I~
                	return;                                        //~1Ac5I~
//              setVisible(false); dispose();                      //~3117R~//~1AbfR~
//              boolean rc=ABT.startListen();                                         //~3105I~//~3117R~//~v101R~//~1AbtR~//~1AbuR~
//              boolean rc=startListen(false);                     //~1AbtI~//~1AbuI~
                swSecure=getSecureOption();                        //~1AbuI~
                boolean rc=startListen(swSecure);                  //~1AbuI~
              if (rc)                                              //~v101I~
              {                                                    //~v101I~
//              acceptButton.setEnabled(false);                          //~3201I~//~3208R~//~1AbuR~
//              connectButton.setEnabled(false);                   //~1AbiI~//~1AbuR~
//              connectSecureButton.setEnabled(false);             //~1AbiI~//~1AbuR~
//              waitingDialog=R.string.BTAccept;           //~3201I~//~1AbuR~
                waitingDialog=STRID_ACCEPT;                        //~1AbuI~
              }                                                    //~v101I~
                setVisible(false); dispose();                      //~1AbfI~
            }                                                      //~3117R~
//            else                                                   //~1AbtI~//~1AbuI~
//            if (o.equals(AG.resource.getString(R.string.BTAcceptSecure)))//~1AbtI~//~1AbuI~
//            {                                                      //~1AbtI~//~1AbuI~
////              setVisible(false); dispose();                      //~1AbtI~//~1AbfR~//~1AbuI~
//                boolean rc=startListen(true);                     //~1AbtI~//~1AbiR~//~1AbuI~
//              if (rc)                                              //~1AbtI~//~1AbuI~
//              {                                                    //~1AbtI~//~1AbuI~
////              acceptSecureButton.setEnabled(false);              //~1AbtI~//~1AbuI~
//                acceptButton.setEnabled(false);            //~1AbtI~//~1AbuI~
//                connectButton.setEnabled(false);                   //~1AbiI~//~1AbuI~
////              connectSecureButton.setEnabled(false);             //~1AbiI~//~1AbuI~
//                waitingDialog=R.string.BTAcceptSecure;             //~1AbtI~//~1AbuI~
//              }                                                    //~1AbtI~//~1AbuI~
//                setVisible(false); dispose();                      //~1AbfI~//~1AbuI~
//            }                                                      //~1AbtI~//~1AbuI~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.BTStopAccept)))//~3204I~
            {                                                      //~3204I~
	            stopListen();                                      //~3204I~
//      		acceptButton.setAction(R.string.BTAccept);//~3204I~//~3208R~//~1AbuR~
        		acceptButton.setAction(STRID_ACCEPT);              //~1AbuI~
//      		acceptSecureButton.setAction(R.string.BTAcceptSecure);//~1AbiI~//~1AbuR~
	            acceptButton.setEnabled(true);                     //~1AbiI~
//              acceptSecureButton.setEnabled(true);               //~1AbiI~//~1AbuR~
	            connectButton.setEnabled(true);                    //~1AbiI~
//              connectSecureButton.setEnabled(true);              //~1AbiI~//~1AbuR~
            }                                                      //~3204I~
            else                                                   //~3204I~
            if (o.equals(AG.resource.getString(R.string.Discoverable)))//~3117R~
            {                                                          //~3105R~//~3117R~
//              setVisible(false); dispose();                      //~3117R~//~1A62R~
                AG.ajagoBT.setDiscoverable();                              //~3105I~//~3117R~//~1AbER~
            }                                                      //~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.Discover)))//~3203I~
            {                                                      //~3203I~
		    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)            //~3206I~
                {                                                  //~3206I~
					errConnectingForScan();                         //~3206I~
                    return;                                        //~3206I~
                }                                                  //~3206I~
              if (discover())                                      //~1AbWI~
              {                                                    //~1AbWI~
    			onDiscovery=true;                                  //~3203I~
//              discover();                                        //~3203R~//~1AbWR~
                afterDismiss(R.string.Discover);                   //~3203I~
              }                                                    //~1AbWI~
            }                                                      //~3203I~
            else                                                   //~1AbSI~
            if (o.equals(AG.resource.getString(R.string.Delete)))  //~1AbSI~
            {                                                      //~1AbSI~
            	deleteEntry();                                     //~1AbSI~
            }                                                      //~1AbSI~
            else                                                   //~3203I~
            if (o.equals(AG.resource.getString(R.string.ActionDiscovered)))//~3203I~
            {                                                      //~3203I~
            	if (!onDiscovery)                                  //~3205I~
                	return;                                        //~3205I~
    			onDiscovery=false;                                 //~3203I~
            	discovered();                                      //~3203I~
            }                                                      //~3203I~
            else                                                   //~3203I~
            if (o.equals(AG.resource.getString(R.string.Cancel)))  //~3117R~
            {                                                          //~2C30I~//~3117R~
                setVisible(false); dispose();                      //~3117R~
            }                                                          //~2C30I~//~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.Help)))    //~3117R~
            {                                                      //~3117R~
//              new HelpDialog(null,R.string.Help_BluetoothConnection); //~3117R~//~1Ad2R~
                new HelpDialog(null,R.string.HelpTitle_Bluetooth,"BTConnection");//~v101R~//~1Ad2I~
            }                                                      //~3117R~
            else                                                   //~3201I~
            if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~3201I~
            {               //callback from Dialog after currentLayout restored//~3201I~
    			cancelDiscover();                                  //~3203R~
                afterDismiss(waitingDialog);                       //~3201I~
            }                                                      //~3201I~
            else                                                   //~1AbEI~
            if (o.equals(AG.resource.getString(R.string.BTSettingsButton)))  //modal but no inputWait//~1AbEI~
            {                                                      //~1AbEI~
            	callSettingsBT();                                  //~1AbEI~
            }                                                      //~1AbEI~
            else super.doAction(o);                                //~3117R~
        }                                                          //~3117I~
        catch(Exception e)                                         //~3117I~
        {                                                          //~3117I~
            Dump.println(e,"BluetoothConnection:doAction:"+o);     //~3117I~
        }                                                          //~3117I~
	}
    //******************************************                   //~3203I~
//  private void discover()                                             //~3203I~//~1AbWR~
    private boolean discover()                                     //~1AbWI~
    {                                                              //~3203I~
		swCancelDiscover=false;                                    //~3205I~
      return                                                       //~1AbWI~
    	AG.ajagoBT.discover(this);                                     //~3203I~//~1AbER~
    }                                                              //~3203I~
    //******************************************                   //~3203I~
    private void cancelDiscover()                                       //~3203I~
    {                                                              //~3203I~
    	if (onDiscovery)                                           //~3203I~
        {                                                          //~3203I~
	        AG.ajagoBT.cancelDiscover();                               //~3203I~//~1AbER~
            onDiscovery=false;                                     //~3203I~
		    swCancelDiscover=true;                                 //~3205I~
        }                                                          //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~3201I~
    private boolean startGame()                                    //~3201I~
    {                                                              //~3201I~
    	if (AG.ajagoBT.isConnectionAlive())                               //~@@@2I~//~3201I~//~1AbER~
        {                                                          //~@@@2I~//~3201I~
            AG.aPartnerFrame.doAction(AG.resource.getString(R.string.Game));//~@@@2I~//~3201I~
            return true;                                                //~@@@2I~//~3201I~
        }                                                          //~@@@2I~//~3201I~
        errNoThread();                                             //~3201I~
        return false;                                              //~3201I~
    }                                                              //~3201I~
    //******************************************                   //~1Ae9I~
    private boolean showPF()                                       //~1Ae9I~
    {                                                              //~1Ae9I~
    	if (AG.ajagoBT.isConnectionAlive())                        //~1Ae9I~
        {                                                          //~1Ae9I~
            AG.aPartnerFrame.showPF();                             //~1Ae9I~
            return true;                                           //~1Ae9I~
        }                                                          //~1Ae9I~
        errNoThread();                                             //~1Ae9I~
        return false;                                              //~1Ae9I~
    }                                                              //~1Ae9I~
    //******************************************                   //~3201I~
    private void disconnectPartner()                               //~3201I~
    {                                                              //~3201I~
//        if (AG.RemoteStatus==AG.RS_BTLISTENING)                    //~3201I~//~3207R~
//        {                                                          //~3201I~//~3207R~
//            stopListen();                                          //~3201I~//~3207R~
//            return;                                                //~3201I~//~3207R~
//        }                                                          //~3201I~//~3207R~
    	if (AG.aPartnerFrame!=null)                                //~3201I~
        	AG.aPartnerFrame.disconnect();                         //~3201I~
  	}                                                              //~3201I~
    //******************************************                   //~3201I~
    private void stopListen()                                      //~3201I~
    {                                                              //~3201I~
        AG.ajagoBT.stopListen();                                       //~3201R~//~1AbER~
  	}                                                              //~3201I~
    //******************************************                   //~3202I~
    private void displayDevice()                                   //~3202I~
    {                                                              //~3202I~
        String dev;
        TextView v;//~3202I~
    //************************                                     //~3202I~
//      v=(TextView)(AG.findViewById(R.id.LocalDevice));  //~3202I~//~v101R~
        v=(TextView)(findViewById(R.id.LocalDevice));              //~v101I~
        if (AG.LocalDeviceName!=null)                              //~3202I~
        	dev=AG.LocalDeviceName;                                //~3202I~
        else                                                       //~3202I~
        	dev=AG.resource.getString(R.string.UnknownDeviceName); //~3202I~
        myDevice=dev;                                              //~v101I~
        v.setText(dev);                                            //~3202I~
        displayDiscoverableStatus();                               //~1Af3I~
        //*                                                        //~3202I~
//      v=(TextView)(AG.findViewById(R.id.RemoteDevice)); //~3202I~//~v101R~
//      v=(TextView)(findViewById(R.id.RemoteDevice));             //~v101I~//~1A6fR~
        v=tvRemoteDevice;                                          //~1A6fR~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED && AG.RemoteDeviceName!=null)//~3202I~
        {                                                          //~3207I~
        	dev=AG.RemoteDeviceName;                               //~3202I~
	        v.setTextColor(connectedColor);                        //~3207I~
        }                                                          //~3207I~
        else                                                       //~3202I~
        	dev=AG.resource.getString(R.string.NoSession);         //~3202I~
        v.setText(dev);                                            //~3202I~
    }                                                              //~3202I~
    //******************************************                   //~3203I~
    //*at opened dialog                                            //~1AbRI~
    //******************************************                   //~1AbRI~
    private void getDeviceList()                      //~v101R~    //~@@@2R~
    {                                                              //~3203I~        String l;                                                  //~3203I~
        if (Dump.Y) Dump.println("BluetoothConnection getDeviceList");//~1AbSI~
//      DL=new List(this,R.id.DeviceList,R.layout.textrowlist);         //~3203I~//~3209R~//~@@@2R~//~1A6cR~
//      DL=new List(this,R.id.DeviceList,(AG.layoutMdpi ? R.layout.textrowlist_bt_mdpi : R.layout.textrowlist_bt));//~1A6cI~//~1A6fR~
        DL=new ListBT(this,R.id.DeviceList,(AG.layoutMdpi ? R.layout.textrowlist_bt_mdpi : R.layout.textrowlist_bt));//~1A6fI~
        DL.addActionListener(this);                                //~3203I~//~3209R~//~@@@2R~
        DL.setBackground(Global.gray);                             //~3203I~//~3209R~//~@@@2R~
//      if (DeviceList==null)                                      //~3204I~//~1A6iR~
//      {                                                          //~3206I~//~1A6iR~
//      	DeviceList=AG.ajagoBT.getPairDeviceList();                  //~3203I~//~3204R~//~1AbTR~//~1AbER~
//          DeviceListOld=DeviceList;                              //~3206I~//~1A6iR~
//      }                                                          //~3206I~//~1A6iR~
//      else                                                       //~3206I~//~1A6iR~
//      {                                                          //~3206I~//~1A6iR~
//          DeviceList=DeviceListOld;   //newly added my not be registered,avoid scan begin for deadlock//~3206I~//~1A6iR~
//      }                                                          //~3206I~//~1A6iR~
//      pairedList=AG.ajagoBT.getPairDeviceList();                     //~1AbTI~//~1AbER~
//        if (DeviceList==null)                                    //~1AbTI~
//            DeviceList=pairedList;                               //~1AbTI~
//        else                                                     //~1AbTI~
//            if (pairedList!=null)                                //~1AbTI~
//                mergePairedDevice();                             //~1AbTI~
        pairedDeviceList=AG.ajagoBT.getPairDeviceList();               //~1AbTI~//~1AbER~
        updateDeviceStatus(ID_STATUS_PAIRED,ID_STATUS_DISCOVERED);	//clear paird previously//~1AbUI~
        if (pairedDeviceList!=null)                                //~1AbUI~
	        SdeviceList.merge(pairedDeviceList,ID_STATUS_PAIRED);           //~1AbTI~//~1AbUR~
        mergeLRU();                                                //~1AbTI~
//		setupListView();                                           //~1AbSI~//~1AbTI~
  		initListView();                                            //~1AbTI~
	}                                                              //~1AbSI~//~1AbTI~
    //******************************************                   //~1AbSI~//~1AbTI~
//    private void setupListView()                                   //~1AbSI~//~1AbTI~
//    {                                                              //~1AbSI~//~1AbTI~
//        if (Dump.Y) Dump.println("BluetoothConnection setupListView");//~1AbSI~//~1AbTI~
//        String[] sa=DeviceList;                                    //~3204I~//~1AbTI~
//        if (sa!=null)                                              //~3203I~//~1AbTI~
//        {                                                          //~3203I~//~1AbTI~
//            String remoteDevice=tvRemoteDevice.getText().toString();//~1A6fI~//~1AbTI~
//            int ctr=sa.length/2;                                       //~3203I~//~1AbTI~
//            for (int ii=0;ii<ctr;ii++)                             //~3203I~//~1AbTI~
//            {                                                      //~3203I~//~1AbTI~
//                if (Dump.Y) Dump.println("BluetoothConnection:setupListView DeviceList ii="+ii+",addr="+sa[ii*2]);//~3203I~//~1AbTI~
////              DL.add(sa[ii*2]);                                  //~3203I~//~1A6fR~//~1AbTI~
//                String nm=sa[ii*2];                                //~1A6fI~//~1AbTI~
//                if (remoteDevice!=null && remoteDevice.equals(nm)) //~1A6fI~//~1AbTI~
//                    DL.add(nm,statusConnected,ID_STATUS_CONNECTED);//~1A6fI~//~1AbTI~
//                 else                                              //~1A6fI~//~1AbTI~
//                if (chkPaired(sa[ii*2+1])!=null) //if defined in pairedList//~1AbTI~
//                    DL.add(nm,statusPaired,ID_STATUS_PAIRED);      //~1A6fI~//~1AbTI~
//                else                                             //~1AbTI~
//                    DL.add(nm,statusDiscovered,ID_STATUS_DISCOVERED);//~1AbTI~
//            }                                                      //~3203I~//~1AbTI~
//        }                                                          //~3203I~//~1AbTI~
////      mergeConnectedDevices();                                   //~1AbRR~//~1AbTI~
//    }                                                              //~3203I~//~1AbTI~
    //******************************************                   //~1AbTI~
    private void initListView()                                    //~1AbTI~
    {                                                              //~1AbTI~
        if (Dump.Y) Dump.println("BluetoothConnection initListView");//~1AbTI~
        String remoteDevice=tvRemoteDevice.getText().toString();   //~1AbTI~
        ArrayList<String> keys=new ArrayList<String>(SdeviceList.devlist.keySet());//~1AbTI~//~1AbUR~
        int ctr=keys.size();                                       //~1AbTI~
        for (int ii=0;ii<ctr;ii++)                                 //~1AbTI~
        {                                                          //~1AbTI~
        	String nm=keys.get(ii);                                //~1AbTI~
            DeviceData data=SdeviceList.get(nm);                        //~1AbTR~//~1AbUR~
            String addr=data.addr;                                 //~1AbTI~
            int stat=data.stat;                                    //~1AbTI~
            if (Dump.Y) Dump.println("BluetoothConnection:initListView DeviceList ii="+ii+",addr="+addr+",name="+nm);//~1AbTI~
            if (remoteDevice!=null && remoteDevice.equals(nm))     //~1AbTI~
                DL.add(nm,statusConnected,ID_STATUS_CONNECTED);    //~1AbTI~
            else                                                   //~1AbTI~
            {                                                      //~1AbTI~
            	switch(stat)                                       //~1AbTI~
                {                                                  //~1AbTI~
                case ID_STATUS_PAIRED:                             //~1AbTI~
	                DL.add(nm,statusPaired,ID_STATUS_PAIRED);      //~1AbTI~
                	break;                                         //~1AbTI~
                case ID_STATUS_CONNECTED_ONCE:                     //~1AbTI~
	                DL.add(nm,statusConnectedOnce,ID_STATUS_CONNECTED_ONCE);//~1AbTI~
                	break;                                         //~1AbTI~
                default:                                           //~1AbTI~
	                DL.add(nm,statusDiscovered,ID_STATUS_DISCOVERED);//~1AbTI~
                }                                                  //~1AbTI~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
    }                                                              //~1AbTI~
    //******************************************                   //~1AbUI~
    private void resetListView()                                   //~1AbUI~
    {                                                              //~1AbUI~
        if (Dump.Y) Dump.println("BluetoothConnection resetListView");//~1AbUI~
        DL.removeAll();                                            //~1AbUI~
    	initListView();                                            //~1AbUI~
    }                                                              //~1AbUI~
    //******************************************                   //~3204I~
	private void setSelection()                                    //~3204I~
    {                                                              //~3204I~
    	int idx;                                                   //~3204I~
		idx=setSelection(AG.RemoteDeviceName);                     //~3204I~
        if (idx<0)                                                 //~3204I~
        	if (lastSelected>=0)                                   //~3205I~
            	idx=lastSelected;                                  //~3205I~
        	else                                                   //~3205I~
				idx=0;                                                 //~3204I~//~3205R~
        if (idx<DL.getItemCount())                                         //~3204I~
        {                                                          //~3204I~
			DL.select(idx);                                        //~3204I~
            if (Dump.Y) Dump.println("setselect="+idx);            //~3204I~
        }                                                          //~3204I~
    }                                                              //~3204I~
    //******************************************                   //~3204I~
	private int setSelection(String Pdevice)                   //~3204I~
    {                                                              //~3204I~
    	int idx=-1;                                                //~3204I~
        if (Pdevice==null)                                         //~3204I~
        	return idx;                                            //~3204I~
//        if (DeviceList!=null)                                      //~3204I~//~1AbUR~
//        {                                                          //~3204I~//~1AbUR~
//            int ctr=DeviceList.length/2;                           //~3204I~//~1AbUR~
//            for (int ii=0;ii<ctr;ii++)                             //~3204I~//~1AbUR~
//            {                                                      //~3204I~//~1AbUR~
//                if (Pdevice.equals(DeviceList[ii*2]))             //~3204I~//~1AbUR~
//                {                                                  //~3204I~//~1AbUR~
//                    idx=ii;                                        //~3204I~//~1AbUR~
//                    break;                                         //~3204I~//~1AbUR~
//                }                                                  //~3204I~//~1AbUR~
//            }                                                      //~3204I~//~1AbUR~
//        }                                                          //~3204I~//~1AbUR~
        idx=SdeviceList.search(Pdevice);                           //~1AbUI~
        if (idx>=0)                                                //~1AbUI~
        DL.select(idx);                                            //~1AbUI~
        if (Dump.Y) Dump.println("BluetoothConnection:setSelection idx="+idx);//~1AbUI~
        return idx;                                                //~3204I~
    }                                                              //~3204I~
    //******************************************                   //~3203I~
    private void discovered()                                      //~3203I~
    {                                                              //~3203I~
    	String[] sa=AG.ajagoBT.getNewDevice();                         //~3203I~//~1AbER~
        if (sa==null)                                              //~3203I~
        {                                                          //~3203I~
            if (swCancelDiscover)                                  //~3205I~
	        	infoDiscoverCanceled();                            //~3205I~
            else                                                   //~3205I~
	        	errNoNewDevice();                                      //~3203I~//~3205R~
	        ProgDlg.dismiss();                                     //~3203I~
            return;                                                //~3203I~
        }                                                          //~3203I~
//        int ctr=sa.length/2;                                       //~3203I~//~1AbUR~
//        int ctr2,addctr=0;                                         //~3206I~//~1AbUR~
//        if (DeviceList==null)                                      //~3206I~//~1AbUR~
//            ctr2=0;                                                //~3206I~//~1AbUR~
//        else                                                       //~3206I~//~1AbUR~
//            ctr2=DeviceList.length/2;                              //~3206I~//~1AbUR~
//        for (int ii=0;ii<ctr;ii++)                                 //~3203I~//~1AbUR~
//        {                                                          //~3203I~//~1AbUR~
//            if (Dump.Y) Dump.println("new device add="+sa[ii*2]);  //~3203I~//~1AbUR~
//            int jj;                                                //~3206I~//~1AbUR~
//            for (jj=0;jj<ctr2;jj++)                                //~3206I~//~1AbUR~
//            {                                                      //~3206I~//~1AbUR~
//                if (sa[ii*2].equals(DeviceList[jj*2]))             //~3206I~//~1AbUR~
//                    break;                                         //~3206I~//~1AbUR~
//            }                                                      //~3206I~//~1AbUR~
//            if (jj==ctr2)                                          //~3206I~//~1AbUR~
//            {                                                      //~3206I~//~1AbUR~
//                addctr++;                                          //~3206I~//~1AbUR~
////              DL.add(sa[ii*2]);                                      //~3203I~//~3206R~//~1A6fR~//~1AbUR~
//                DL.add(sa[ii*2],statusDiscovered,ID_STATUS_DISCOVERED);//~1A6fR~//~1AbUR~
//                if (Dump.Y) Dump.println("discovered device add="+sa[ii*2]);//~1A6fI~//~1AbUR~
//            }                                                      //~3206I~//~1AbUR~
//            else                                                   //~1A6fI~//~1AbUR~
//            {                                                      //~1A6fI~//~1AbUR~
//                DL.update(sa[ii*2],statusDiscovered);              //~1A6fI~//~1AbUR~
//                if (Dump.Y) Dump.println("discovered device update="+sa[ii*2]);//~1A6fI~//~1AbUR~
//            }                                                      //~1A6fI~//~1AbUR~
//        }                                                          //~3203I~//~1AbUR~
		SdeviceList.merge(sa,ID_STATUS_DISCOVERED);                //~1AbUI~
		resetListView();                                           //~1AbUI~
//        if (DeviceList==null)                                      //~3203I~//~1AbUR~
//        {                                                          //~3203I~//~1AbUR~
//            DL.select(0); //first added pos                        //~3203I~//~1AbUR~
//            DeviceList=sa;                                         //~3203I~//~1AbUR~
//        }                                                          //~3203I~//~1AbUR~
//        else                                                       //~3203I~//~1AbUR~
//        {                                                          //~3203I~//~1AbUR~
//            DL.select(ctr2); //first added pos      //~3203R~      //~3206R~//~1AbUR~
//            String[] nl=new String[(ctr2+addctr)*2];   //~3203R~   //~3206R~//~1AbUR~
//            System.arraycopy(DeviceList,0,nl,0,ctr2*2); //~3203R~  //~3206R~//~1AbUR~
//            addctr=0;                                              //~3206I~//~1AbUR~
//            for (int ii=0;ii<ctr;ii++)                             //~3206I~//~1AbUR~
//            {                                                      //~3206I~//~1AbUR~
//                int jj;                                            //~3206I~//~1AbUR~
//                for (jj=0;jj<ctr2;jj++)                            //~3206I~//~1AbUR~
//                {                                                  //~3206I~//~1AbUR~
//                    if (sa[ii*2].equals(DeviceList[jj*2]))         //~3206I~//~1AbUR~
//                        break;                                     //~3206I~//~1AbUR~
//                }                                                  //~3206I~//~1AbUR~
//                if (jj==ctr2)                                      //~3206I~//~1AbUR~
//                {                                                  //~3206I~//~1AbUR~
//                    nl[(ctr2+addctr)*2]=sa[ii*2];                  //~3206R~//~1AbUR~
//                    nl[(ctr2+addctr)*2+1]=sa[ii*2+1];              //~3206R~//~1AbUR~
//                    if (Dump.Y) Dump.println("scaned ="+nl[(ctr+addctr)*2]+"="+nl[(ctr+addctr)*2+1]);//~3206I~//~1AbUR~
//                    addctr++;                                      //~3206I~//~1AbUR~
//                }                                                  //~3206I~//~1AbUR~
//            }                                                      //~3206I~//~1AbUR~
//            DeviceList=nl;                                         //~3203R~//~1AbUR~
//        }                                                          //~3203I~//~1AbUR~
		int ctr=sa.length/2;                                       //~1AbUI~
        int idx=0;                                                 //~1AbUI~
        if (ctr>0)                                                 //~1AbUI~
        {                                                          //~1AbUI~
        	String name=sa[(ctr-1)*2];                             //~1AbUI~
            idx=SdeviceList.search(name);                          //~1AbUI~
        }                                                          //~1AbUI~
        if (idx>=0)                                                //~1AbUI~
		    DL.select(idx); //last added pos                       //~1AbUI~
        ProgDlg.dismiss();                                         //~3203R~
        infoNewDevice(sa.length/2);                                //~3203R~
    }                                                              //~3203I~
    //******************************************                   //~3203I~
//  private boolean connectPartner()                               //~3203I~//~1A60R~
    private boolean connectPartner(boolean Psecure)                //~1A60I~
    {                                                              //~3203I~
	    int idx=getSelected();                                     //~3203I~
        if (idx==-1)                                               //~3203I~
        	return false;                                          //~3203I~
        lastSelected=idx;                                          //~3205I~
//      String name=DeviceList[idx*2];                             //~3203R~//~1AbUR~
//      String addr=DeviceList[idx*2+1];                           //~3203I~//~1AbUR~
		ListData ld=getListData(idx);                              //~1AbUI~
        String name=ld.itemtext;                                   //~1AbUI~
        DeviceData data=SdeviceList.get(name);                     //~1AbUI~
        String addr=data.addr;                                     //~1AbUI~
        connectingDevice=name;                                     //~v101I~
        if (ld.itemint!=ID_STATUS_PAIRED)                          //~1Ac5I~
    		if (!chkDiscoverable(Psecure,false/*not server*/))     //~1Ac5I~
                return false;                                      //~1Ac5I~
//      AG.ajagoBT.connect(name,addr);                                 //~3203R~//~1A60R~//~1AbER~
        AG.ajagoBT.connect(name,addr,Psecure);                         //~1A60I~//~1AbER~
        return true;                                               //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~1AbSI~
    private boolean deleteEntry()                                  //~1AbSI~
    {                                                              //~1AbSI~
	    int idx=getSelected();                                     //~1AbSI~
        if (idx==-1)                                               //~1AbSI~
        	return false;                                          //~1AbSI~
        lastSelected=0;                                            //~1AbSI~
		ListData ld=getListData(idx);                              //~1AbSI~
        String name=ld.itemtext;                                   //~1AbSI~
        String status=ld.itemtext2;                                //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection deleteEntry name="+name+",status="+status);//~1AbSI~
        if (status.equals(statusPaired))                           //~1AbSI~
    		AjagoView.showToast(R.string.WarningDeletingPaired);       //~1AbSI~
//      deleteConnectedDevice(name,idx);                           //~1AbSR~//~1AbTR~
        deleteDeviceList(name);                                        //~1AbTI~//~1AbUR~
        deleteFromListView(idx);                                   //~1AbSI~
        return true;                                               //~1AbSI~
    }                                                              //~1AbSI~
    //******************************************                   //~1AbtI~
    private boolean startListen(boolean Psecure)                   //~1AbtI~
    {                                                              //~1AbtI~
    	boolean rc=AjagoBT.startListenNonNFC(Psecure);                 //~1AbtI~
        return rc;                                                 //~1AbtI~
    }                                                              //~1AbtI~
    //******************************************                   //~1A6fR~
	private ListData getListData(int Pidx)                         //~1A6fR~
    {                                                              //~1A6fR~
    	ListData ld;                                               //~1A6fR~
        try                                                        //~1A6fR~
        {                                                          //~1A6fR~
    		ld=DL.arrayData.get(Pidx);                             //~1A6fR~
        }                                                          //~1A6fR~
        catch(Exception e)                                         //~1A6fR~
        {                                                          //~1A6fR~
        	Dump.println(e,"BluetoothConnection:getListData");     //~1A6fR~
            ld=new ListData("OutOfBound",Color.black/*dummy*/);    //~1A6fR~
        }                                                          //~1A6fR~
        return ld;                                                 //~1A6fR~
    }                                                              //~1A6fR~
    //******************************************                   //~3203I~
	private int getSelected()                                      //~3203I~
    {                                                              //~3203I~
	    int idx=DL.getValidSelectedPos();                           //~3203I~
        if (idx==-1)                                               //~3203I~
        {                                                          //~3203I~
//  		IPConnection.errNotSelected();                         //~3203R~//~1Ae5R~
    		errNotSelected();                                      //~1Ae5I~
        }                                                          //~3203I~
        return idx;                                                //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~3201I~
	public void afterDismiss(int Pwaiting)                         //~3201I~
    {                                                              //~3201I~
    	setToPropSecureOption();                                   //~1AbuI~
        waitingid=Pwaiting;                                        //~3203I~
    	if (Dump.Y) Dump.println("BTConnection afterDismiss");     //~3201I~//~3204R~
//      if (Pwaiting==R.string.BTAccept)                   //~3201I~//~1AbuR~
        if (Pwaiting==STRID_ACCEPT)                                //~1AbuI~
        {                                                          //~v101I~
//          String msg=AG.resource.getString(R.string.Msg_WaitingAccept,myDevice);//~v101I~//~1AedR~
            String msg=getMsgStringBTAccepting(myDevice,swSecure); //~1AedI~
			waitingResponse(R.string.Title_WaitingAccept,msg);//~3201I~//~v101R~
        }                                                          //~v101I~
        else                                                       //~3201I~
//      if (Pwaiting==R.string.BTConnect)                     //~3201I~//~1AbuR~
        if (Pwaiting==STRID_CONNECT)                               //~1AbuI~
        {                                                          //~v101I~
//          String msg=AG.resource.getString(R.string.Msg_WaitingConnect,connectingDevice);//~v101I~//~1AedR~
            String msg=getMsgStringBTConnecting(connectingDevice,swSecure);//~1AedI~
			waitingResponse(R.string.Title_WaitingConnect,msg);//~3201I~//~v101R~
        }                                                          //~v101I~
        else                                                       //~3203I~
        if (Pwaiting==R.string.Discover)                           //~3203I~
			waitingResponse(R.string.Title_WaitingDiscover,R.string.Msg_WaitingDiscover);//~3203I~
        else                                                       //~3207I~
        if (Pwaiting==R.string.BTDisConnect)                       //~3207I~
			disconnectPartner();                               //~3201I~//~3207I~
    	if (Dump.Y) Dump.println("BTConnection afterDismiss return");//~3207I~
        putLRU();                                                  //~1AbSI~
        AG.aBTConnection=null;                                     //~1A6kI~
    }                                                              //~3201I~
    //******************************************                   //~3201I~
	private void waitingResponse(int Ptitleresid,int Pmsgresid)    //~3201R~
    {                                                              //~3201I~
//        new MessageWaiting(parentFrame,AG.resource.getString(Ptitleresid),//~3201I~//~3203R~
//                            AG.resource.getString(Pmsgresid));     //~3201I~//~3203R~
//        AG.progDlg=new ProgDlg(Ptitleresid,Pmsgresid,true/*cancelable*/);//~3203R~//~1A2jR~
//        AG.progDlg.setCallback(this,true/*cancel CB*/,false/*dismisscallback*/);//~3203R~//~1A2jR~
//        AG.progDlg.show();                                         //~3203R~//~1A2jR~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,true/*cancel CB*/,Ptitleresid,Pmsgresid,true/*cancelable*/);//~1A2jI~
    }                                                              //~3201I~
    //******************************************                   //~v101I~
	private void waitingResponse(int Ptitleresid,String Pmsg)      //~v101I~
    {                                                              //~v101I~
//        AG.progDlg=new ProgDlg(Ptitleresid,Pmsg,true/*cancelable*/);//~v101I~//~1A2jR~
//        AG.progDlg.setCallback(this,true/*cancel CB*/,false/*dismisscallback*/);//~v101I~//~1A2jR~
//        AG.progDlg.show();                                         //~v101I~//~1A2jR~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,true/*cancel CB*/,Ptitleresid,Pmsg,true/*cancelable*/);//~1A2jI~
    }                                                              //~v101I~
    //******************************************                   //~3201I~
	private void errNoThread()                                     //~3201I~
    {                                                              //~3201I~
    	AjagoView.showToast(R.string.ErrNoThread);                     //~3201R~
    }                                                              //~3201I~
    //******************************************                   //~3203I~
	private void errNoNewDevice()                                  //~3203I~
    {                                                              //~3203I~
    	AjagoView.showToast(R.string.ErrNoNewDevice);                  //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~3206I~
	private void errConnectingForScan()                            //~3206I~
    {                                                              //~3206I~
    	AjagoView.showToast(R.string.ErrConnectingForScan);            //~3206I~
    }                                                              //~3206I~
	private void infoDiscoverCanceled()                             //~3205I~
    {                                                              //~3205I~
    	AjagoView.showToast(R.string.InfoDiscoverCanceled);            //~3205I~
    }                                                              //~3205I~
    //******************************************                   //~3203I~
	private void infoNewDevice(int Pctr)                           //~3203I~
    {                                                              //~3203I~
    	AjagoView.showToast(AG.resource.getString(R.string.InfoNewDevice,Pctr));//~3203I~
    }                                                              //~3203I~
    //******************************************                   //~3203I~
    //*reason:0:cancel,1:dismiss                                   //~3203I~
    //******************************************                   //~3203I~
    @Override                                                      //~3203I~
	public void onCancelProgDlg(int Preason)                       //~3203I~
    {                                                              //~3203I~
    	if (Dump.Y) Dump.println("onCancelProgDlgI reason="+Preason);//~3203I~
        if (Preason==0)	//cancel                                   //~3203I~
        {                                                          //~3203I~
	    	if (Dump.Y) Dump.println("onCancelProgDlgI waitingID="+Integer.toHexString(waitingid));//~3203I~
        	if (waitingid==R.string.Discover)                       //~3203I~
	        	cancelDiscover();                                 //~3203I~
        }                                                          //~3203I~
    }                                                              //~3203I~
    class ListBT extends List                                                  //~1114//~1A6fI~
    {                                                              //~1A6fI~
    //*****************                                            //~1A6fI~
        public ListBT(Container Pcontainer,int Presid,int Prowresid)//~1A6fI~
        {                                                          //~1A6fI~
            super(Pcontainer,Presid,Prowresid);                     //~1A6fI~
        }                                                          //~1A6fI~
    //**********************************************************************//~1A6fI~
        @Override                                                  //~1A6fI~
        public View getViewCustom(int Ppos, View Pview,ViewGroup Pparent)//~1A6fI~
        {                                                          //~1A6fI~
        //*******************                                      //~1A6fI~
            if (Dump.Y) Dump.println("ListBT:getViewCustom Ppos="+Ppos+"CheckedItemPos="+((ListView)Pparent).getCheckedItemPosition());//~1A6fI~
            View v=Pview;                                          //~1A6fI~
            if (v == null)                                         //~1A6fI~
			{                                                      //~1A6fI~
                v=AG.inflater.inflate(rowId/*super*/,null);            //~1A65I~//~1A6fI~
            }                                                      //~1A6fI~
            TextView v1=(TextView)v.findViewById(BTROW_NAME);      //~1A6fI~
            TextView v2=(TextView)v.findViewById(BTROW_STATUS);    //~1A6fI~
            if (font!=null)                                        //~1A6fI~
            {                                                      //~1A6fI~
                font.setFont(v1);                                  //~1A6fI~
                font.setFont(v2);                                  //~1A6fI~
            }                                                      //~1A6fI~
            ListData ld=getListData(Ppos);                         //~1A6fR~
            v1.setText(ld.itemtext);                               //~1A6fI~
            if (Ppos==selectedpos)                                 //~1A6fI~
            {                                                      //~1A6fI~
                v1.setBackgroundColor(bgColorSelected.getRGB());   //~1A6fI~
                v1.setTextColor(bgColor.getRGB());                 //~1A6fI~
            }                                                      //~1A6fI~
            else                                                   //~1A6fI~
            {                                                      //~1A6fI~
                v1.setBackgroundColor(bgColor.getRGB());           //~1A6fI~
                v1.setTextColor(ld.itemcolor.getRGB());            //~1A6fI~
            }                                                      //~1A6fI~
            String status;                                         //~1A6fI~
            if (ld.itemtext2==null)                                //~1A6fI~
            	status="";                                         //~1A6fI~
            else                                                   //~1A6fI~
            	status=ld.itemtext2;                               //~1A6fI~
            v2.setText(status);                                    //~1A6fI~
            v2.setBackgroundColor(bgColor.getRGB());               //~1A6fI~
            if (status.equals(statusPaired))                       //~1A6fI~
                v2.setTextColor(COLOR_STATUS_PAIRED.getRGB());              //~1A6fI~
            else                                                   //~1A6fI~
            if (status.equals(statusConnected))                    //~1A6kI~
                v2.setTextColor(COLOR_STATUS_CONNECTED.getRGB());  //~1A6kI~
            else                                                   //~1A6kI~
                v2.setTextColor(COLOR_STATUS_DISCOVERED.getRGB());          //~1A6fI~
            return v;                                              //~1A6fI~
        }                                                          //~1A6fI~
    }//class                                                       //~1A6fI~
	//**********************************************************************//~1AbuI~
    private boolean getSecureOption()                              //~1AbuI~
    {                                                              //~1AbuI~
    	return cbSecureOption.isChecked();                         //~1AbuI~
    }                                                              //~1AbuI~
//    //**********************************************************************//~1AbuI~//~1AbUR~
//    private void setSecureOption(boolean Psecure)               //~1AbuI~//~1AbUR~
//    {                                                              //~1AbuI~//~1AbUR~
//        cbSecureOption.setChecked(Psecure);                 //~1AbuI~//~1AbUR~
//    }                                                              //~1AbuI~//~1AbUR~
	//**********************************************************************//~1AbuI~
    private void setFromPropSecureOption()                         //~1AbuI~
    {                                                              //~1AbuI~
    	swSecure=AjagoProp.getPreference(PKEY_BTSECURE,BTSECURE_DEFAULT)!=0;//~1AbuI~
    	cbSecureOption.setChecked(swSecure);                       //~1AbuI~
    }                                                              //~1AbuI~
	//**********************************************************************//~1AbuI~
    private void setToPropSecureOption()                           //~1AbuI~
    {                                                              //~1AbuI~
    	swSecure=getSecureOption();                                //~1AbuI~
    	AjagoProp.putPreference(PKEY_BTSECURE,swSecure?1:0);            //~1AbuI~
    }                                                              //~1AbuI~
    //***********************************************************************//~1AbvI~//~1AbuI~
    public static void closeDialog()                               //~1AbvI~//~1AbuI~
    {                                                              //~1AbvI~//~1AbuI~
    	if (Dump.Y) Dump.println("BluetoothConnection:closeDialog");//~1AbvI~//~1AbuI~
        if (AG.aBTConnection!=null && AG.aBTConnection.androidDialog.isShowing())//~1AbvI~//~1AbuI~
        {                                                          //~1AbvI~//~1AbuI~
	    	if (Dump.Y) Dump.println("BluetoothConnection:closeDialog dismiss");//~1AbvI~//~1AbuI~
		    AG.aBTConnection.waitingDialog=0;               //~1AbvI~//~1AbuI~
        	AG.aBTConnection.dismiss();                     //~1AbvI~//~1AbuI~
        }                                                          //~1AbvI~//~1AbuI~
    }                                                              //~1AbvI~//~1AbuI~
	//***********************************************************************************//~1AbEI~
    private void callSettingsBT()                                  //~1AbEI~
    {                                                              //~1AbEI~
   	    if (Dump.Y) Dump.println("BluetoothConnection:callSettingsBT");//~1AbEI~
        if (AG.ajagoBT.mBTC==null)                                     //~1AbWR~//~1AbER~
        {                                                          //~1AbWR~
			AjagoBT.BTNotAvailable();                                      //~1AbRI~//~1AbWI~
        	return;                                                //~1AbWR~
        }                                                          //~1AbWR~
    	Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);//~1AbEI~
        AG.activity.startActivity(intent);                         //~1AbEI~
    }                                                              //~1AbEI~
	//***********************************************************************************//~1AbRI~
	//*maintain connected devices to list additional to paired device//~1AbRI~
	//***********************************************************************************//~1AbRI~
    public static void addConnectedDevice(String Pname,String Paddr)//~1AbRI~
    {                                                              //~1AbRI~
	    if (Dump.Y) Dump.println("BluetoothConnection addConnectedDevice name="+Pname+",addr="+Paddr);//~1AbRI~//~1AbSR~
    	if (Paddr==null)                                           //~1AbRI~
        	return;                                                //~1AbRI~
    	String nm=Pname;                                           //~1AbRI~
        if (nm==null || nm.equals(""))                                              //~1AbRI~//~1AbUR~
        	nm=Paddr;                                              //~1AbRI~
//      addToDeviceList(nm,Paddr);                                 //~1AbRI~//~1AbSR~
        synchronized(SdeviceListLRU)                                   //~1AbRI~//~1AbUR~
        {                                                          //~1AbRI~
        	if (SdeviceListLRU.get(nm)!=null)	//if dup ,insert to last   //~1AbSI~//~1AbUR~
            	SdeviceListLRU.remove(nm);                          //~1AbSI~//~1AbUR~
        	SdeviceListLRU.put(nm,Paddr);                              //~1AbRR~//~1AbUR~
        }                                                          //~1AbRI~
        putLRU();   //connected evennt after dismiss CloseDialog   //~1AeiI~
    }                                                              //~1AbRI~
//    //***********************************************************************************//~1AbSI~//~1AbUI~
//    //*maintain connected devices to list additional to paired device//~1AbSI~//~1AbUI~
//    //***********************************************************************************//~1AbSI~//~1AbUI~
//    private void deleteConnectedDevice(String Pname,int Pindex)    //~1AbSR~//~1AbUI~
//    {                                                              //~1AbSI~//~1AbUI~
//        int pairctr;                                             //~1AeiR~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice name="+Pname+",index="+Pindex);//~1AbSR~//~1AbUI~
//        if (DeviceList==null)                                      //~1AbSI~//~1AbUI~
//            pairctr=0;                                             //~1AbSI~//~1AbUI~
//        else                                                       //~1AbSI~//~1AbUI~
//            pairctr=DeviceList.length/2;                           //~1AbSI~//~1AbUI~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice name="+Pname+",index="+Pindex+",DeviceList size="+pairctr);//~1AbSI~//~1AbUI~
//        if (Pindex<pairctr)                                        //~1AbSR~//~1AbUI~
//        {                                                          //~1AbSI~//~1AbUI~
//            String addr=DeviceList[Pindex*2+1];                    //~1AbSI~//~1AbUI~
//            deleteFromDeviceList(addr);                            //~1AbSI~//~1AbUI~
//            return;                                                //~1AbSI~//~1AbUI~
//        }                                                          //~1AbSI~//~1AbUI~
//        int idx=Pindex-pairctr;                                    //~1AbSI~//~1AbUI~
//        if (SdeviceListLRU==null)                                      //~1AbSI~//~1AbUI~
//            return;                                                //~1AbSI~//~1AbUI~
//        synchronized(SdeviceListLRU)                                   //~1AbSR~//~1AbUI~
//        {                                                          //~1AbSR~//~1AbUI~
//            int sz=SdeviceListLRU.size();                              //~1AbSI~//~1AbUI~
//            if (idx<sz)                                            //~1AbSI~//~1AbUI~
//            {                                                      //~1AbSI~//~1AbUI~
//                ArrayList<String> listv=new ArrayList<String>(SdeviceListLRU.values());//~1AbSI~//~1AbUI~
//                if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice idx on SdeviceListLRU="+idx+"="+listv.get(idx));//~1AbSI~//~1AbUI~
//                if (Pname.equals(listv.get(idx)))                       //~1AbSI~//~1AbUI~
//                {                                                  //~1AbSI~//~1AbUI~
//                    ArrayList<String> listk=new ArrayList<String>(SdeviceListLRU.keySet());//~1AbSI~//~1AbUI~
//                    String addr=listk.get(idx);                        //~1AbSI~//~1AbUI~
//                    SdeviceListLRU.remove(addr);                       //~1AbSI~//~1AbUI~
//                    if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice remove addr="+addr);//~1AbSI~//~1AbUI~
//                }                                                  //~1AbSI~//~1AbUI~
//            }                                                      //~1AbSI~//~1AbUI~
//        }//synch                                                   //~1AbSR~//~1AbUI~
//    }                                                              //~1AbSI~//~1AbUI~
	//***********************************************************************************//~1AbTI~
    private void deleteDeviceList(String Pname)                    //~1AbTI~
    {                                                              //~1AbTI~
	    if (Dump.Y) Dump.println("BluetoothConnection deleteDeviceList name="+Pname);//~1AbTI~
        DeviceData data=SdeviceList.get(Pname);                         //~1AbTI~//~1AbUR~
        if (data!=null)                                            //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        	SdeviceList.remove(Pname);                                  //~1AbTI~//~1AbUR~
	    	if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice deleted");//~1AbTI~
        }                                                          //~1AbTI~
        synchronized(SdeviceListLRU)                               //~1AbUR~
        {                                                          //~1AbUR~
        	SdeviceListLRU.remove(Pname);                          //~1AbUR~
        }                                                          //~1AbUR~
    }                                                              //~1AbTI~
//    //***********************************************************************************//~1AbRI~//~1AbUI~
//    private static void addToDeviceList(String Pname,String Paddr)      //~1AbRI~//~1AbUI~
//    {                                                              //~1AbRI~//~1AbUI~
//        int ctr2;                                                  //~1AbRI~//~1AbUI~
//        if (DeviceList==null)                                      //~1AbRI~//~1AbUI~
//            ctr2=0;                                                //~1AbRI~//~1AbUI~
//        else                                                       //~1AbRI~//~1AbUI~
//            ctr2=DeviceList.length/2;                              //~1AbRI~//~1AbUI~
//        int jj;                                                    //~1AbRI~//~1AbUI~
//        for (jj=0;jj<ctr2;jj++)                                    //~1AbRI~//~1AbUI~
//        {                                                          //~1AbRI~//~1AbUI~
//            if (Paddr.equals(DeviceList[jj*2+1]))                  //~1AbRI~//~1AbUI~
//                break;                                             //~1AbRI~//~1AbUI~
//        }                                                          //~1AbRI~//~1AbUI~
//        if (jj==ctr2)   //to be add to DeviceList                    //~1AbRI~//~1AbUI~
//        {                                                          //~1AbRI~//~1AbUI~
//            if (Dump.Y) Dump.println("BluetoothConnection added"); //~1AbRI~//~1AbUI~
//            if (DeviceList==null)                                  //~1AbRI~//~1AbUI~
//            {                                                      //~1AbRI~//~1AbUI~
//                DeviceList=new String[2];                          //~1AbRI~//~1AbUI~
//                DeviceList[0]=Pname;                                  //~1AbRI~//~1AbUI~
//                DeviceList[1]=Paddr;                               //~1AbRI~//~1AbUI~
//            }                                                      //~1AbRI~//~1AbUI~
//            else                                                   //~1AbRI~//~1AbUI~
//            {                                                      //~1AbRI~//~1AbUI~
//                String[] nl=new String[(ctr2+1)*2];                //~1AbRI~//~1AbUI~
//                System.arraycopy(DeviceList,0,nl,0,ctr2*2);        //~1AbRI~//~1AbUI~
//                nl[ctr2*2]=Pname;                                     //~1AbRI~//~1AbUI~
//                nl[ctr2*2+1]=Paddr;                                //~1AbRI~//~1AbUI~
//                DeviceList=nl;                                     //~1AbRI~//~1AbUI~
//            }                                                      //~1AbRI~//~1AbUI~
//        }                                                          //~1AbRI~//~1AbUI~
//    }                                                              //~1AbRR~//~1AbUI~
//    //***********************************************************************************//~1AbSI~//~1AbUI~
//    private boolean deleteFromDeviceList(String Paddr)             //~1AbSI~//~1AbUI~
//    {                                                              //~1AbSI~//~1AbUI~
//        boolean rc=false;                                          //~1AbSI~//~1AbUI~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteFromDeviceList addr="+Paddr);//~1AbSI~//~1AbUI~
//        int ctr2;                                                  //~1AbSI~//~1AbUI~
//        if (DeviceList==null)                                      //~1AbSI~//~1AbUI~
//            return rc;                                             //~1AbSI~//~1AbUI~
//        ctr2=DeviceList.length/2;                                  //~1AbSI~//~1AbUI~
//        if (ctr2==0)                                               //~1AbSI~//~1AbUI~
//            return rc;                                             //~1AbSI~//~1AbUI~
//        int jj;                                                    //~1AbSI~//~1AbUI~
//        for (jj=0;jj<ctr2;jj++)                                    //~1AbSI~//~1AbUI~
//        {                                                          //~1AbSI~//~1AbUI~
//            if (Paddr.equals(DeviceList[jj*2+1]))                  //~1AbSI~//~1AbUI~
//                break;                                             //~1AbSI~//~1AbUI~
//        }                                                          //~1AbSI~//~1AbUI~
//        if (jj==ctr2)   //to be add to DeviceList                  //~1AbSI~//~1AbUI~
//            return rc;                                             //~1AbSI~//~1AbUI~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteFromDeviceList found idx="+jj);//~1AbSI~//~1AbUI~
//        String[] nl=new String[(ctr2-1)*2];                        //~1AbSI~//~1AbUI~
//        if (jj!=0)                                                 //~1AbSI~//~1AbUI~
//            System.arraycopy(DeviceList,0,nl,0,jj*2);              //~1AbSI~//~1AbUI~
//        if (jj!=ctr2-1)                                            //~1AbSI~//~1AbUI~
//            System.arraycopy(DeviceList,(jj+1)*2,nl,jj*2,((ctr2-1)-jj)*2);//~1AbSR~//~1AbUI~
//        DeviceList=nl;                                             //~1AbSI~//~1AbUI~
//        return true;                                               //~1AbSI~//~1AbUI~
//    }                                                              //~1AbSI~//~1AbUI~
	//***********************************************************************************//~1AbSI~
    private void deleteFromListView(int Pindex)                    //~1AbSR~
    {                                                              //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection deleteFromListView idx="+Pindex);//~1AbSR~
        DL.remove(Pindex);                                         //~1AbSR~
    }                                                              //~1AbSI~
	//***********************************************************************************//~1AbUI~
    private void updateDeviceStatus(int PstatFrom,int PstatTo)     //~1AbUI~
    {                                                              //~1AbUI~
        if (Dump.Y) Dump.println("BluetoothConnection updateDeviceStatus");//~1AbUI~
        SdeviceList.updateStatusAll(PstatFrom,PstatTo);            //~1AbUI~
    }                                                              //~1AbUI~
//    //***********************************************************************************//~1AbRI~//~1AbTI~
//    //*at open dialog,merge once connected addr                    //~1AbRI~//~1AbTI~
//    //***********************************************************************************//~1AbRI~//~1AbTI~
//    private void mergeConnectedDevices()                           //~1AbRI~//~1AbTI~
//    {                                                              //~1AbRI~//~1AbTI~
//        int hmctr;                                                 //~1AbRI~//~1AbTI~
//        synchronized(SdeviceListLRU)                                   //~1AbSI~//~1AbTR~//~1AbUR~
//        {                                                          //~1AbRI~//~1AbTI~
//            if (!swLRULoaded)                                      //~1AbSI~//~1AbTI~
//            {                                                      //~1AbSI~//~1AbTI~
//                swLRULoaded=true;                                  //~1AbSI~//~1AbTI~
//                getLRU();   //read preference                      //~1AbSM~//~1AbTI~
//            }                                                      //~1AbSI~//~1AbTI~
//            hmctr=SdeviceListLRU.size();                               //~1AbRR~//~1AbTR~//~1AbUR~
//            if (Dump.Y) Dump.println("mergeConnectedDevices size="+hmctr);//~1AbRR~//~1AbTI~
//            String[] sa=DeviceList;                                //~1AbRR~//~1AbTI~
//            if (sa!=null && hmctr!=0)                                          //~1AbRR~//~1AbSR~//~1AbTI~
//            {                                                      //~1AbRR~//~1AbTI~
//                int ctr=sa.length/2;                               //~1AbRR~//~1AbTI~
//                for (int ii=0;ii<ctr;ii++)                         //~1AbRR~//~1AbTI~
//                {                                                  //~1AbRR~//~1AbTI~
//                    String nm=sa[ii*2];                            //~1AbRR~//~1AbTI~
//                    String addr=sa[ii*2+1];                        //~1AbRR~//~1AbTI~
//                  if (chkPaired(addr)!=null)                     //~1AbTI~
//                  {                                              //~1AbTI~
//                    if (Dump.Y) Dump.println("mergerConnectedDevices delete because this is paired="+nm+"="+addr);//~1AbRR~//~1AbTI~
//                    SdeviceListLRU.remove(addr);                       //~1AbRR~//~1AbTR~//~1AbUR~
//                  }                                              //~1AbTI~
//                }                                                  //~1AbRR~//~1AbTI~
//                hmctr=SdeviceListLRU.size();                           //~1AbSI~//~1AbTR~//~1AbUR~
//            }                                                      //~1AbRR~//~1AbTI~
//            if (Dump.Y) Dump.println("mergeConnectedDevices after removed paired,size="+hmctr);//~1AbRR~//~1AbTI~
//            if (hmctr!=0)                                          //~1AbRR~//~1AbTI~
//            {                                                      //~1AbRR~//~1AbTI~
//                ArrayList<String> list=new ArrayList<String>(SdeviceListLRU.keySet()); //~1AbSI~//~1AbTR~//~1AbUR~
//                for (int ii=hmctr-1;ii>=0;ii--)                    //~1AbSI~//~1AbTI~
//                {                                                  //~1AbRR~//~1AbTI~
//                    String addr=list.get(ii);                          //~1AbSI~//~1AbTI~
//                    String nm=SdeviceListLRU.get(addr);                //~1AbRR~//~1AbTR~//~1AbUR~
//                  if (chkListed(addr)==null)    //               //~1AbTI~
//                  {                                              //~1AbTI~
//                    if (Dump.Y) Dump.println("mergeConnectedDevices add top list nm="+nm+",addr="+addr);//~1AbRR~//~1AbTI~
//                    DL.add(nm,statusConnectedOnce,ID_STATUS_CONNECTED_ONCE);//~1AbRR~//~1AbTI~
//                  }                                              //~1AbTI~
////                  addToDeviceList(nm,addr);                      //~1AbSR~//~1AbTI~
//                }                                                  //~1AbRR~//~1AbTI~
//            }                                                      //~1AbRR~//~1AbTI~
//        }//synch                                                   //~1AbRI~//~1AbTI~
//    }                                                              //~1AbRI~//~1AbTI~
	//***********************************************************************************//~1AbTI~
	//*at open dialog,merge once connected addr                    //~1AbTI~
	//***********************************************************************************//~1AbTI~
    private void mergeLRU()                                        //~1AbTI~
    {                                                              //~1AbTI~
        if (!swLRULoaded)                                          //~1AbTI~
        {                                                          //~1AbTI~
            swLRULoaded=true;                                      //~1AbTI~
            getLRU();	//read preference                          //~1AbTI~
        }                                                          //~1AbTI~
        synchronized(SdeviceListLRU)                                   //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        	SdeviceList.merge(SdeviceListLRU,ID_STATUS_CONNECTED_ONCE,false);//~1AbTR~//~1AbUR~
        }                                                          //~1AbTI~
    }                                                              //~1AbTI~
	//***********************************************************************************//~1AbSI~
//  private void putLRU()                                          //~1AbSI~//~1AeiR~
    private static void putLRU()                                   //~1AeiI~
    {                                                              //~1AbSI~
	    int hmctr;                                                 //~1AbSI~
        StringBuffer sb=new StringBuffer("");                      //~1AbSI~
        synchronized(SdeviceListLRU)                                   //~1AbSI~//~1AbUR~
        {                                                          //~1AbSI~
            hmctr=SdeviceListLRU.size();                               //~1AbSI~//~1AbUR~
            if (Dump.Y) Dump.println("BluetoothConnection putLRU,size="+hmctr);//~1AbSI~
            if (hmctr!=0)                                          //~1AbSI~
            {                                                      //~1AbSI~
            	ArrayList<String> list=new ArrayList<String>(SdeviceListLRU.keySet()); //~1AbSI~//~1AbUR~
                for (int ii=hmctr-1,lructr=0;ii>=0;ii--)           //~1AbSR~
                {                                                  //~1AbSI~
                    String name=list.get(ii);                          //~1AbSI~//~1AbUR~
                    String addr=SdeviceListLRU.get(name);              //~1AbSI~//~1AbUR~
			        if (Dump.Y) Dump.println("BluetoothConnection putLRU,size="+hmctr);//~1AbSI~
                    if (lructr==0)                                 //~1AbSI~
	                    sb.append(name+"\t"+addr);                 //~1AbSR~
                    else                                           //~1AbSI~
	                    sb.append("\n"+name+"\t"+addr);            //~1AbSI~
                    lructr++;                                      //~1AbSI~
                    if (lructr>=MAX_LRU)                           //~1AbSI~
                    	break;                                     //~1AbSI~
                }                                                  //~1AbSI~
            }                                                      //~1AbSI~
        }//synch                                                   //~1AbSI~
        String s=sb.toString();                                    //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection putLRU Prop data="+s);//~1AbSI~
        AjagoProp.putPreference(PROPKEY_BTLRU,s);                       //~1AbSI~
    }                                                              //~1AbSI~
	//***********************************************************************************//~1AbSI~
    private void getLRU()                                          //~1AbSI~
    {                                                              //~1AbSI~
        String s=AjagoProp.getPreference(PROPKEY_BTLRU,"");             //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection getLRU Prop data="+s);//~1AbSI~
        if (s.equals(""))                                          //~1AbSI~
        	return;                                                //~1AbSI~
        String lines[]=s.split("\n");                              //~1AbSI~
        if (lines==null)                                           //~1AbSI~
        	return;                                                //~1AbSI~
	    int ctr=lines.length;                                      //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection getLRU,size="+ctr);//~1AbSI~
        if (ctr==0)                                                //~1AbSI~
        	return;                                                //~1AbSI~
        synchronized(SdeviceListLRU)                                   //~1AbSI~//~1AbUR~
        {                                                          //~1AbSI~
            for (int ii=0;ii<ctr;ii++)                             //~1AbSR~
            {                                                      //~1AbSI~
                String name_addr[]=lines[ii].split("\t");          //~1AbSR~
                if (name_addr==null || name_addr.length!=2)          //~1AbSI~
                    continue;                                      //~1AbSI~
                String name=name_addr[0];                          //~1AbSI~
                String addr=name_addr[1];                          //~1AbSI~
                if (Dump.Y) Dump.println("BluetoothConnection getLRU name="+name+"="+addr);//~1AbSI~
        		SdeviceListLRU.put(name,addr);                         //~1AbSI~//~1AbUR~
            }                                                      //~1AbSI~
        }//synch                                                   //~1AbSI~
    }                                                              //~1AbSI~
//    //***********************************************************************************//~1AbTI~
//    private String chkPaired(String Paddr)                       //~1AbTI~
//    {                                                            //~1AbTI~
//        String name=null;                                        //~1AbTI~
//        if (Dump.Y) Dump.println("BluetoothConnection chkPaired addr="+Paddr);//~1AbTI~
//        if (pairedList!=null)                                    //~1AbTI~
//        {                                                        //~1AbTI~
//            String[] sa=pairedList;                              //~1AbTI~
//            int ctr=sa.length/2;                                 //~1AbTI~
//            for (int ii=0;ii<ctr;ii++)                           //~1AbTI~
//            {                                                    //~1AbTI~
//                String addr=sa[ii*2+1];                          //~1AbTI~
//                if (Dump.Y) Dump.println("BluetoothConnection chkPaired addr="+addr);//~1AbTI~
//                if (Paddr.equals(addr))                          //~1AbTI~
//                {                                                //~1AbTI~
//                    name=sa[ii*2];                               //~1AbTI~
//                    break;                                       //~1AbTI~
//                }                                                //~1AbTI~
//            }                                                    //~1AbTI~
//        }                                                        //~1AbTI~
//        if (Dump.Y) Dump.println("BluetoothConnection chkPaired return name="+name);//~1AbTI~
//        return name;                                             //~1AbTI~
//    }                                                            //~1AbTI~
//    //***********************************************************************************//~1AbTI~
//    private void mergePairedDevice()                             //~1AbTI~
//    {                                                            //~1AbTI~
//        if (Dump.Y) Dump.println("BluetoothConnection mergePaired");//~1AbTI~
//        String[] sapd=pairedList;                                //~1AbTI~
//        String[] sadl=DeviceList;                                //~1AbTI~
//        if (sapd==null || sadl==null)                            //~1AbTI~
//            return;                                              //~1AbTI~
//        int ctrpd=sapd.length/2;                                 //~1AbTI~
//        int ctr=sadl.length/2;                                   //~1AbTI~
//        int addctr=0;                                            //~1AbTI~
//        for (int ii=0;ii<ctrpd;ii++)                             //~1AbTI~
//        {                                                        //~1AbTI~
//            String addr=sapd[ii*2+1];                            //~1AbTI~
//            if (chkListed(addr)==null)                           //~1AbTI~
//                addctr++;                                        //~1AbTI~
//        }                                                        //~1AbTI~
//        if (Dump.Y) Dump.println("BluetoothConnection mergePaired addctr="+addctr);//~1AbTI~
//        if (addctr==0)                                           //~1AbTI~
//            return;                                              //~1AbTI~
//        String[] nl=new String[(ctr+addctr)*2];                  //~1AbTI~
//        System.arraycopy(sadl,0,nl,0,ctr*2);                     //~1AbTI~
//        addctr=0;                                                //~1AbTI~
//        for (int ii=0;ii<ctr;ii++)                               //~1AbTI~
//        {                                                        //~1AbTI~
//            String addr=sapd[ii*2+1];                            //~1AbTI~
//            String name=chkListed(addr);                         //~1AbTI~
//            if (name!=null)                                      //~1AbTI~
//                continue;                                        //~1AbTI~
//            sadl[(ctr+addctr)*2]=name;                           //~1AbTI~
//            sadl[(ctr+addctr)*2+1]=addr;                         //~1AbTI~
//            addctr++;                                            //~1AbTI~
//        }                                                        //~1AbTI~
//        DeviceList=nl;                                           //~1AbTI~
//    }                                                            //~1AbTI~
//    //***********************************************************************************//~1AbTI~
//    private String chkListed(String Paddr)                       //~1AbTI~
//    {                                                            //~1AbTI~
//        String name=null;                                        //~1AbTI~
//        if (Dump.Y) Dump.println("BluetoothConnection chkListed addr="+Paddr);//~1AbTI~
//        if (DeviceList!=null)                                    //~1AbTI~
//        {                                                        //~1AbTI~
//            String[] sa=DeviceList;                              //~1AbTI~
//            int ctr=sa.length/2;                                 //~1AbTI~
//            for (int ii=0;ii<ctr;ii++)                           //~1AbTI~
//            {                                                    //~1AbTI~
//                String addr=sa[ii*2+1];                          //~1AbTI~
//                if (Dump.Y) Dump.println("BluetoothConnection chkListed add="+addr);//~1AbTI~
//                if (Paddr.equals(addr))                          //~1AbTI~
//                {                                                //~1AbTI~
//                    name=sa[ii*2];                               //~1AbTI~
//                    break;                                       //~1AbTI~
//                }                                                //~1AbTI~
//            }                                                    //~1AbTI~
//        }                                                        //~1AbTI~
//        if (Dump.Y) Dump.println("BluetoothConnection chkListed return name="+name);//~1AbTI~
//        return name;                                             //~1AbTI~
//    }                                                            //~1AbTI~
    //****************************************************         //~1AbTI~
    class DeviceDataList                             //~1AbTI~     //~1AbUR~
    {                                                              //~1AbTI~
	    public Map<String,DeviceData> devlist=new LinkedHashMap<String,DeviceData>();//~1AbTI~//~1AbUR~
    //*****************                                            //~1AbTI~
        public DeviceDataList()                                             //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbTI~
        public DeviceData get(String Pname)                        //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        	return devlist.get(Pname);                             //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbTI~
        public void put(String[] Plist,int Pstat)                  //~1AbTI~
        {                                                          //~1AbTI~
        	for (int ii=0;ii<Plist.length/2;ii++)                  //~1AbTI~
            {                                                      //~1AbTI~
            	String name=Plist[ii*2];                           //~1AbTI~
            	String addr=Plist[ii*2+1];                         //~1AbTI~
            	DeviceData data=new DeviceData(name,addr,Pstat);   //~1AbTR~//~1AbUR~
                if (Dump.Y) Dump.println("DeviceDataList:put key="+name+",addr="+addr+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
    			devlist.put(name,data);                            //~1AbTI~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbUR~
        public void remove(String Pname)                           //~1AbUR~
        {                                                          //~1AbUR~
            if (Dump.Y) Dump.println("DeviceDataList:remove key="+Pname);//~1AbUR~
        	devlist.remove(Pname);                                 //~1AbUR~
        }                                                          //~1AbUR~
	    //*************************************************************//~1AbTI~
	    //*for Paired/Discovered device list                       //~1AbUR~
	    //*************************************************************//~1AbUR~
        public void merge(String[] Plist,int Pstat)                //~1AbTI~
        {                                                          //~1AbTI~
        	for (int ii=0;ii<Plist.length/2;ii++)                  //~1AbTI~
            {                                                      //~1AbTI~
            	String name=Plist[ii*2];                           //~1AbTI~
            	String addr=Plist[ii*2+1];                         //~1AbTI~
                DeviceData data=get(addr);                         //~1AbTR~//~1AbUR~
                if (data==null)                                    //~1AbTI~
                {                                                  //~1AbTI~
                	if (Dump.Y) Dump.println("DeviceDataList:merge add key="+addr+",name="+name+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
            		DeviceData adddata=new DeviceData(name,addr,Pstat);//~1AbTI~//~1AbUR~
    				devlist.put(name,adddata);                     //~1AbTI~
                }                                                  //~1AbTI~
                else                                               //~1AbTI~
                {                                                  //~1AbTI~
                	if (Dump.Y) Dump.println("DeviceDataList:merge rep key="+addr+",name="+name+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
                	data.addr=addr;                                //~1AbUR~
					data.stat=Pstat;                               //~1AbUR~
                }                                                  //~1AbTI~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbTI~
	    //*for ConnectedDeviceLRU                                  //~1AbUR~
	    //*************************************************************//~1AbUR~
        public void merge(Map<String,String> Pmap,int Pstat,boolean Preplace)//~1AbTI~
        {                                                          //~1AbTI~
            ArrayList<String> src=new ArrayList<String>(Pmap.keySet());    //names//~1AbTI~
        	for (int ii=0;ii<src.size();ii++)                      //~1AbTI~
            {                                                      //~1AbTI~
            	String name=src.get(ii);                           //~1AbTI~//~1AbUR~
            	String addr=Pmap.get(name);                        //~1AbTR~//~1AbUR~
                DeviceData data=get(name);                         //~1AbTR~//~1AbUR~
                if (data==null)                                    //~1AbTI~
                {                                                  //~1AbTI~
	                if (Dump.Y) Dump.println("DeviceDataList:merge Pmap add name="+name+",addr="+addr+",stat="+Pstat);//~1AbTR~//~1AbUR~
            		DeviceData adddata=new DeviceData(name,addr,Pstat);//~1AbTI~//~1AbUR~
    				devlist.put(name,adddata);                     //~1AbTI~
                }                                                  //~1AbTI~
                else                                               //~1AbTI~
                if (Preplace)                                      //~1AbTI~
                {                                                  //~1AbTI~
	                if (Dump.Y) Dump.println("DeviceDataList:merge Plist rep key="+addr+",name="+name+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
                	data.name=name;                                //~1AbTI~
                    data.addr=addr;                                //~1AbTI~
					data.stat=Pstat;                               //~1AbTI~
                }                                                  //~1AbTI~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbUI~
        public int search(String Pname)                            //~1AbUI~
        {                                                          //~1AbUI~
        	int idx=-1;                                            //~1AbUI~
            ArrayList<String> keys=new ArrayList<String>(devlist.keySet());    //names//~1AbUI~
        	for (int ii=0;ii<keys.size();ii++)                     //~1AbUI~
            {                                                      //~1AbUI~
            	String name=keys.get(ii);                          //~1AbUI~
                if (name.equals(Pname))                            //~1AbUI~
                {                                                  //~1AbUI~
                	idx=ii;                                        //~1AbUI~
                	break;                                         //~1AbUI~
                }                                                  //~1AbUI~
            }                                                      //~1AbUI~
	        if (Dump.Y) Dump.println("DeviceDataList:search name="+Pname+",idx="+idx);//~1AbUI~
            return idx;                                            //~1AbUI~
        }                                                          //~1AbUI~
	    //*************************************************************//~1AbUI~
        public int updateStatusAll(int Pfrom,int Pto)              //~1AbUI~
        {                                                          //~1AbUI~
        	int ctr=0;                                             //~1AbUI~
            ArrayList<String> keys=new ArrayList<String>(devlist.keySet());    //names//~1AbUI~
        	for (int ii=0;ii<keys.size();ii++)                     //~1AbUI~
            {                                                      //~1AbUI~
            	String name=keys.get(ii);                          //~1AbUI~
                DeviceData data=get(name);                         //~1AbUI~
                if (data.stat==Pfrom)                              //~1AbUI~
                {                                                  //~1AbUI~
			        if (Dump.Y) Dump.println("DeviceDataList:updateStatus updated="+name);//~1AbUI~
                	data.stat=Pto;                                 //~1AbUI~
                    ctr++;                                         //~1AbUI~
                }                                                  //~1AbUI~
            }                                                      //~1AbUI~
	        if (Dump.Y) Dump.println("DeviceDataList:updateStatus updatectr="+ctr);//~1AbUI~
            return ctr;                                            //~1AbUI~
        }                                                          //~1AbUI~
    }//class                                                       //~1AbTI~
    //****************************************************         //~1AbTI~
    class DeviceData                                               //~1AbTI~//~1AbUR~
    {                                                              //~1AbTI~
    //*****************                                            //~1AbTI~
    	public String name,addr;                                   //~1AbTI~
        public int stat;                                           //~1AbTI~
        public DeviceData(String Pname,String Paddr,int Pstat)     //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
    		name=Pname;addr=Paddr;stat=Pstat;                      //~1AbTI~
        }                                                          //~1AbTI~
    }//class                                                       //~1AbTI~
	//*************************************************************************//~1Ac5I~
    private boolean chkDiscoverable(boolean Psecure,boolean Pserver)//~1Ac5I~
	{                                                              //~1Ac5I~
    	boolean rc=true;                                           //~1Ac5I~
    	if (swAlertAction)                                         //~1Ac5I~
        	return rc;                                             //~1Ac5I~
        if (Dump.Y) Dump.println("BluetoothConnection.chkDiscoverable secure="+Psecure);//~1Ac5I~
        if (Psecure)                                               //~1Ac5I~
        {                                                          //~1Ac5I~
        	if (!AjagoBT.BTisDiscoverable())                           //~1Ac5I~
            {                                                      //~1Ac5I~
			    showNotDiscoverableAlert(Pserver);                 //~1Ac5I~
                rc=false;  //DialogNFCBT from alert Action         //~1Ac5I~
            }                                                      //~1Ac5I~
        }                                                          //~1Ac5I~
        return rc;                                                 //~1Ac5I~
    }                                                              //~1Ac5I~
	//*************************************************************************//~1Ac5I~
    private void showNotDiscoverableAlert(boolean Pserver)         //~1Ac5I~
    {                                                              //~1Ac5I~
    	swServer=Pserver;                                          //~1Ac5I~
        AjagoAlertI ai=new AjagoAlertI()                                     //~1Ac5I~
                            {                                      //~1Ac5I~
                                @Override                          //~1Ac5I~
                                public int alertButtonAction(int Pbuttonid,int Pitempos)//~1Ac5I~
                                {                                  //~1Ac5I~
                                	swAlertAction=true;            //~1Ac5I~
                                    if (Dump.Y) Dump.println("BluetoothConnection swServer="+swServer+",buttonid="+Integer.toHexString(Pbuttonid));//~1Ac5I~
                                    if (Pbuttonid==AjagoAlert.BUTTON_POSITIVE)//~1Ac5I~
                                    {                              //~1Ac5I~
                                    	if (swServer)              //~1Ac5I~
											doAction(AG.resource.getString(STRID_ACCEPT));//~1Ac5I~
                                        else                       //~1Ac5I~
											doAction(AG.resource.getString(STRID_CONNECT));//~1Ac5I~
                                    }                              //~1Ac5I~
                                	swAlertAction=false;           //~1Ac5I~
                                    return 1;                      //~1Ac5I~
                                }                                  //~1Ac5I~
                            };                                     //~1Ac5I~
        AjagoAlert.simpleAlertDialog(ai,R.string.Title_Bluetooth,R.string.WarningBTNotDiscoverable,//~1Ac5I~
                            AjagoAlert.BUTTON_POSITIVE|AjagoAlert.BUTTON_NEGATIVE);//~1Ac5I~
    }                                                              //~1Ac5I~
    //******************************************                   //~v101I~//~1Ae5I~
	public static void errNotSelected()                            //~v101R~//~1Ae5I~
    {                                                              //~v101I~//~1Ae5I~
    	AjagoView.showToast(R.string.ErrNotSelected);                  //~v101I~//~1Ae5I~
    }                                                              //~v101I~//~1Ae5I~
    //******************************************                   //~1AedI~
	public static String getMsgStringBTAccepting(String Pdevice,boolean Psecure)//~1AedI~
    {                                                              //~1AedI~
    	String secureopt=AG.resource.getString(Psecure ? R.string.BTSecure : R.string.BTInSecure);//~1AedI~
		String msg=AG.resource.getString(R.string.Msg_WaitingAccept,Pdevice,secureopt);//~1AedI~
        return msg;
    }                                                              //~1AedI~
    //******************************************                   //~1AedI~
	public static String getMsgStringBTConnecting(String Pdevice,boolean Psecure)//~1AedI~
    {                                                              //~1AedI~
    	String secureopt=AG.resource.getString(Psecure ? R.string.BTSecure : R.string.BTInSecure);//~1AedI~
        String msg=AG.resource.getString(R.string.Msg_WaitingConnect,Pdevice,secureopt);//~1AedI~
        return msg;
    }                                                              //~1AedI~
    //******************************************                   //~1Af1I~
	public static void onReceive(String Paction)                   //~1Af1I~
    {                                                              //~1Af1I~
        if (Dump.Y) Dump.println("BluetoothConnection.onReceive action="+Paction);//~1Af1I~
        try                                                        //~1Af1I~
        {                                                          //~1Af1I~
            if (AG.aBTConnection!=null && AG.aBTConnection.androidDialog.isShowing())//~1Af1I~
            {                                                      //~1Af1I~
                BluetoothConnection btc=AG.aBTConnection;          //~1Af1I~
                btc.runRenewal();                                  //~1Af1I~
            }                                                      //~1Af1I~
        }                                                          //~1Af1I~
        catch(Exception e)                                         //~1Af1I~
        {                                                          //~1Af1I~
        	Dump.println(e,"BluetoothConnection:onReceive");       //~1Af1I~
        }                                                          //~1Af1I~
    }                                                              //~1Af1I~
    //******************************************                   //~1Af1I~
    //* request run on UIThread                                    //~1Af1I~
    //******************************************                   //~1Af1I~
	public void runRenewal()                                       //~1Af1I~
    {                                                              //~1Af1I~
        if (Dump.Y) Dump.println("BluetoothConnection.renewal");   //~1Af1I~
        URunnableI ur=new URunnableI()                             //~1Af1I~
								{                                  //~1Af1I~
								 	public void runFunc(Object parmObj,int parmInt)//~1Af1I~
									{                              //~1Af1I~
								        if (Dump.Y) Dump.println("BluetoothConnection.runRenewal.runFunc");//~1Af1I~
								        try                        //~1Af1I~
        								{                          //~1Af1I~
                                        	renewal();             //~1Af1I~
                                        }                          //~1Af1I~
        								catch(Exception e)         //~1Af1I~
        								{                          //~1Af1I~
        									Dump.println(e,"BluetoothConnection:runFunc");//~1Af1I~
        								}                          //~1Af1I~
									}                              //~1Af1I~
                                };                                 //~1Af1I~
        URunnable.setRunFuncDirect(ur,this/*parm obj*/,0/*parm int*/);//widthout delay//~1Af1I~
    }                                                              //~1Af1I~
    //******************************************                   //~1Af1I~
    //*on MainThread                                               //~1Af1I~
    //******************************************                   //~1Af1I~
	public void renewal()                                          //~1Af1I~
    {                                                              //~1Af1I~
        if (Dump.Y) Dump.println("BluetoothConnection.renewal");   //~1Af1I~
//        super(Pgf,AG.resource.getString(R.string.Title_Bluetooth),//~1Af1R~
//                (AG.screenDencityMdpiSmallV || AG.screenDencityMdpiSmallH/*mdpi and height or width <=320*/ ? R.layout.bluetooth_mdpi : R.layout.bluetooth),//~1Af1R~
//                true,false);                                     //~1Af1R~
//        GF=Pgf;                                                  //~1Af1R~
//        statusPaired=AG.resource.getString(ID_STATUS_PAIRED);    //~1Af1R~
//        statusDiscovered=AG.resource.getString(ID_STATUS_DISCOVERED);//~1Af1R~
//        statusConnected=AG.resource.getString(ID_STATUS_CONNECTED);//~1Af1R~
//        statusConnectedOnce=AG.resource.getString(ID_STATUS_CONNECTED_ONCE);//~1Af1R~
//        tvRemoteDevice=(TextView)(findViewById(R.id.RemoteDevice));//~1Af1R~
//        cbSecureOption=(CheckBox)(findViewById(R.id.BTSecureOption));//~1Af1R~
        displayDevice();                                           //~1Af1I~
//        if (SdeviceList==null)                                   //~1Af1R~
//            SdeviceList=new DeviceDataList();                    //~1Af1R~
	    getDeviceList();                                           //~1Af1I~
	    setSelection();                                            //~1Af1I~
//        new ButtonAction(this,0,R.id.BTSettings);                //~1Af1R~
//        acceptButton=new ButtonAction(this,0,R.id.BTAccept);     //~1Af1R~
//        gameButton=new ButtonAction(this,0,R.id.BTGame);         //~1Af1R~
//        connectButton=new ButtonAction(this,0,R.id.BTConnect);   //~1Af1R~
//        disconnectButton=new ButtonAction(this,0,R.id.BTDisConnect);//~1Af1R~
//        new ButtonAction(this,0,R.id.Delete);                    //~1Af1R~
//        new ButtonAction(this,0,R.id.Discoverable);              //~1Af1R~
//        new ButtonAction(this,0,R.id.Discover);                  //~1Af1R~
//        new ButtonAction(this,0,R.id.Cancel);                    //~1Af1R~
//        new ButtonAction(this,0,R.id.Help);                      //~1Af1R~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)                    //~1Af1I~
        {                                                          //~1Af1I~
            acceptButton.setEnabled(false);                        //~1Af1I~
            connectButton.setEnabled(false);                       //~1Af1I~
        }                                                          //~1Af1I~
        else                                                       //~1Af1I~
    	if ((AG.RemoteStatusAccept & (AG.RS_BTLISTENING_SECURE|AG.RS_BTLISTENING_INSECURE))!=0)//~1Af1I~
        {                                                          //~1Af1I~
  	        gameButton.setEnabled(false);                          //~1Af1I~
            disconnectButton.setEnabled(false);                    //~1Af1I~
        	acceptButton.setAction(R.string.BTStopAccept);         //~1Af1I~
        }                                                          //~1Af1I~
        else                                                       //~1Af1I~
        {                                                          //~1Af1I~
            gameButton.setEnabled(false);                          //~1Af1I~
            disconnectButton.setEnabled(false);                    //~1Af1I~
        }                                                          //~1Af1I~
//        setDismissActionListener(this/*DoActionListener*/);      //~1Af1R~
//        setFromPropSecureOption();                               //~1Af1R~
//        validate();                                              //~1Af1R~
//        show();                                                  //~1Af1R~
//        AG.aBTConnection=this;  //used when PartnerThread detected err//~1Af1R~
        if (Dump.Y) Dump.println("BluetoothConnection.renewal return");//~1Af1I~
    }                                                              //~1Af1I~
    //******************************************                   //~1Af3I~
    private void displayDiscoverableStatus()                       //~1Af3I~
    {                                                              //~1Af3I~
        String dev;                                                //~1Af3I~
        TextView v;                                                //~1Af3I~
    //************************                                     //~1Af3I~
        v=(TextView)(findViewById(R.id.LocalDeviceDiscoverable));  //~1Af3I~
        if (AG.ajagoBT.mBTC==null)                                 //+1Af6I~
        {                                                          //+1Af6I~
        	dev=AG.resource.getString(R.string.noBTadapter);       //+1Af6I~
        }                                                          //+1Af6I~
        else                                                       //+1Af6I~
        if (AjagoBT.BTisDiscoverable())                            //~1Af3I~
        {                                                          //~1Af3I~
        	dev=AG.resource.getString(R.string.StatusDiscoverable);//~1Af3I~
            discoverableButton.setEnabled(false);                  //~1Af3I~
        }                                                          //~1Af3I~
        else                                                       //~1Af3I~
        {                                                          //~1Af3I~
        	dev=AG.resource.getString(R.string.StatusNotDiscoverable);//~1Af3I~
            discoverableButton.setEnabled(true);                   //~1Af3I~
        }                                                          //~1Af3I~
        v.setText(dev);                                            //~1Af3I~
    }                                                              //~1Af3I~
}
