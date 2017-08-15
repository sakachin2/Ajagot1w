//*CID://+1Ag1R~:                             update#=   60;       //~1Ag1R~
//****************************************************************************************//~1Ag1R~
//1Ag1 2016/10/06 Change Top panel. set menu panel as tabwidget.   //~1Ag1I~
//1Afu 2014/10/04 android5 allows only PIE for ndk binary          //~1AfuI~
//1Af9 2016/07/11 Additional to Server/Partner List update fuction, up/down/undelete.//~1Af9I~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//1Ae2 2015/07/24 addtional to 1Ab1 for Ajagoc                     //~1Ae2I~
//2015/07/24 //1Ad2 2015/07/17 HelpDialog by helptext              //~1Ad2I~
//2015/07/23 //1Abg 2015/06/15 NFCBT:transfer to NFCBT or NFCWD if active session exist//~1AbgI~
//2015/07/23 //1Ab7 2015/05/03 NFC Bluetooth handover v2           //~1Ab7I~
//2015/03/16 1A6p 2015/02/16 display.getWidth()/getHeight() was deprecated at api13,use getSize(Point)//~1A6pI~
//2015/03/06 1A6a 2015/02/10 NFC+Wifi support                      //~1A6aI~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//1A6s 2015/02/25 Asgts:2015/02/17 move NFC starter from WifiDirect dialog to MainFrame
//1A85 2015/02/25 close each time partnerframe for IP Connection   //~1A85I~
//1A40 2015/02/25 Asgts:1A40 2014/09/13 adjust for mdpi:HVGA:480x320(Ahsv:1A50,Ajagot1w:v1D4)//~1A40I~
//1A81 2015/02/24 ANFC is not used now                             //~1A81I~
//1A80 2015/02/24 ProgDlg from Asgts                               //~1A80I~
//1A6c 2015/02/13 Bluetooth;identify paired device and discovered device//~1A6cI~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A6cI~
//v1Eo 2014/12/15 (Asgts)//1A4j 2014/12/04 memory leak:after LGF closed static AG.currentDialog remains.//~v1EoI~
//                       //                It refers Frame through parentFrame//~v1EoI~
//v1Ee 2014/12/12 FileDialog:NPE at AjagoModal:actionPerforme by v1Ec//~v1EeI~
//                OnListItemClick has no modal consideration like as Button//~v1EeI~
//                FileDialog from LocalGoFrame is on subthread, OnItemClick of List Item scheduled on MainThread//~v1EeI~
//                AjagoModal do not allocalte countdown latch but subthreadModal flag indicate latch.countDown()//~v1EeI~
//                ==>Change FileDialog to from WaitInput(Modal) to Callback method//~v1EeI~
//v1E4 2014/12/08 (Asgts)//1A4h 2014/12/03 catch OutOfMemory       //~v1E4I~
//v1D4 2014/11/16 adjust for mdpi:HVGA:480x320(Ahsv:1A50)          //~v1D4I~
//v1C7 2014/08/30 no Dump.println(e,..) for helpfile not found for helpfile for update for Ajagocrve//~v1C7I~
//1078:121208 add "menu" to option menu if landscape               //~v107I~
//1077:121208 control greeting by app start counter                //~v107I~
//1075:121207 control dumptrace by manifest debuggable option      //~v107I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//v101:120514 (Axe)android3(honeycomb) tablet has System bar at bottom that hide xe button line with 48pix height//~v101I~
//******************************************************************************************************************//~v101I~
//*Ajago Globals *****                                             //~1107I~
//********************                                             //~1107I~
package com.Ajagoc;                                                    //~1108R~//~1109R~

import android.os.Build;                                           //~vab0R~//~v101I~
                                                                   
//~v101I~
import com.Ajagoc.BT.BTControl;
import com.Ajagoc.awt.Dialog;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.Window;
import com.Ajagoc.R;                                               //~1120I~

import jagoclient.Dump;
import jagoclient.Go;
import jagoclient.MainFrame;
import jagoclient.board.ConnectedGoFrame;
import jagoclient.igs.ConnectionFrame;
import jagoclient.igs.games.GamesFrame;
import jagoclient.partner.BluetoothConnection;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;


class IdTblEntry                                                   //~1120I~
{                                                                  //~1120I~
    String name; int id;                                           //~1120I~
    public IdTblEntry(String Pname,int Pid)                        //~1120I~
    {                                                              //~1120I~
        name=Pname; id=Pid;                                        //~1120I~
    }                                                              //~1120I~
}                                                                  //~1120I~
//**************************                                       //~1120I~
public class AG                                                    //~1107R~
{                                                                  //~1109R~
    public static final int ACTIVITY_REQUEST_CONNECT_DEVICE_BT = 1;//~1A6aI~
    public static final int ACTIVITY_REQUEST_ENABLE_BT = 2;        //~1A6cI~
    public static final int ACTIVITY_REQUEST_NFCBEAM   = 3;        //~1A6cI~
                                                                   //~1A6cI~
 	public static boolean isDebuggable;                            //~v107I~
 	public static int testdump=0;	//@@@@test                     //~1301I~
 	public static Activity activity;                                 //~1109I~//~1111R~
 	public static Context context;                                 //~1111I~
	public static jagoclient.partner.PartnerFrame aPartnerFrame;   //~v107I~
//  public static jagoclient.partner.PartnerFrame aPartnerFrameIP; //~@@@@I~//~1A85I~//~1A8cR~
    public static wifidirect.PartnerFrame aPartnerFrameIP;         //~1A8cI~
 	public static BluetoothConnection aBTConnection;               //~1A6kI~//~1Ae5I~
    public static int RemoteStatus;                                //~@@@@R~//~1A6sI~
    public static int RemoteStatusAccept;                          //~@@@@I~//~1A6sR~
    public static final int RS_IP=1;                               //~@@@@R~//~1A6sR~
    public static final int RS_BT=2;                               //~@@@@I~//~1A6sI~//~1Ae5R~
    public static final int RS_IPLISTENING=RS_IP+4;                //~@@@@R~//~1A6sR~
//  public static final int RS_BTLISTENING_SECURE=RS_BT+4;         //~@@@@R~//~1A6sI~
    public static final int RS_BTLISTENING_SECURE=RS_BT+4;         //~1Ae5I~
    public static final int RS_IPCONNECTED=RS_IP+8;                 //~@@@@R~//~1A6sR~
//  public static final int RS_BTCONNECTED=RS_BT+8;                //~@@@@R~//~1A6sI~
    public static final int RS_BTCONNECTED=RS_BT+8;                //~1Ae2I~
//  public static final int RS_BTLISTENING_INSECURE=RS_BT+16;      //~@@@@I~//~1A6sI~
    public static final int RS_BTLISTENING_INSECURE=RS_BT+16;      //~1Ae5I~
    public static String RemoteInetAddress;                        //~@@@@I~
    public static String LocalInetAddress;                         //~1A6sI~
    public static String RemoteInetAddressLAN;                     //~1A8fI~
    public static String LocalInetAddressLAN;                      //~1A8fI~
    public static String RemoteDeviceName;                         //~@@@@I~//~1Ae5I~
    public static String LocalDeviceName;                          //~@@@@I~//~1Ae5I~
	public static AjagoMain ajagoMain;                             //~1107R~
	public static AjagoMenu ajagoMenu;                             //~1107I~
	public static Ajagoc    ajagoc;                                //~1109R~
	public static AjagoView ajagov;                                //~1111I~
	public static Resources  resource;                              //~1109I~
	public static int       component;                             //~1109I~
	public static Go        go;                                    //~1109I~
//  public static int scrW,scrH;                                   //~1428R~//~v107R~
	public static float dip2pix;                                   //~1428I~
//  public static boolean landscape;                               //~1428R~//~v107R~
    public static final String SD_go_cfg="go.cfg.save";             //~1308R~
    public static Frame mainframe;                                 //~1111I~
    public static LayoutInflater inflater;                          //~1113I~
    public static Canvas    androidCanvasMain;                     //~1428R~
    public static boolean   appStart;                              //~1428R~
    public static boolean   portrait;                              //~1428R~
    public static String    appName;                               //~1428R~
    public static String    pkgName;                               //~1A6cI~
    public static String    appVersion;                            //~1506I~
    public static int       scrWidth,scrHeight;                    //~1428R~
    private static View      currentLayout;//~1120I~               //~1428R~
    public static int       currentLayoutId;                       //~1428R~
    private static int       currentLayoutLabelSeqNo;              //~1428R~
    private static int       currentLayoutTextFieldSeqNo;          //~1428R~
    private static int       currentLayoutTextAreaSeqNo;           //~1428R~
    private static int       currentLayoutButtonSeqNo;             //~1428R~
    private static int       currentLayoutCheckBoxSeqNo;           //~1428R~
    private static int       currentLayoutSpinnerSeqNo;            //~1428R~
    private static int       currentLayoutSeekBarSeqNo;            //~1428R~
    public static Dialog    currentDialog;                    //~1215I~//~1428R~
    public static Frame     currentFrame;                          //~1428R~
    public static int       mainframeTag;                          //~1428R~
    public static boolean currentIsDialog;                         //~1428R~
    public static boolean chkUpdateHelp;                           //~v1C7I~
	public static boolean tryHelpFileOpen;                         //~1A41R~//~1Ad2I~
                                                                   //~1211I~
    public static int       listViewRowId=R.layout.textrowlist;       //~1211I~//~1219R~
    public static int       viewerRowId  =R.layout.textrowviewer;  //~1219R~
    public static int       listViewRowIdMultipleChoice=android.R.layout.simple_list_item_multiple_choice;//~1211I~
    public static int       status=0;                              //~1212I~
    public static final int STATUS_MAINFRAME_OPEN=1;               //~1212I~
    public static final int STATUS_STOPFINISH=9;                   //~v107I~
    public static final int TIME_LONGPRESS=1000;//milliSeconds     //~1412I~
    public static int currentTabLayoutId;                          //~1428R~
    public static int titleBarTop;                                 //~1428R~
    public static int titleBarBottom;                              //~1428R~
//  public static WDANFC aWDANFC;                                  //~1A6cI~//~1A81R~
//  public static ANFC aANFC;                                      //~1A6cI~//~1A81R~
    public static AjagoBT ajagoBT;                                 //~v107R~
    public static final String PKEY_STARTUPCTR="startupctr";       //~v107I~
    public static int startupCtr;                                  //~v107I~
    public static int activeSessionType;                           //~1A8gI~
    public static final int AST_IP=1;                              //~1A8gI~
    public static final int AST_WD=2;                              //~1A8gI~
    public static final int AST_BT=3;                              //~1A8gI~
                                                                   //~v1D4I~
    public static boolean screenDencityMdpi;                       //~v1D4I~
    public static boolean screenDencityMdpiSmallH,screenDencityMdpiSmallV;//~1A40I~
    public static boolean layoutMdpi;                              //~1A6cI~
    public static ProgDlg progDlg;                                 //~1A80I~
    public static  boolean swNFCBT=true;   //support NFC Bluetooth handover//~1Ab7I~
    public static  boolean swSecureNFCBT;  //current active NFCBT session is secure//~1AbgI~
    public static  boolean isNFCBT; 		//BT is by NFC         //~1AbgI~
                                                                   //~1120I~
//****************                                                 //~1109I~
	public  static final String ListServer ="ListView_Server";     //~1120I~
	public  static final String ListPartner="ListView_Partner";    //~1120I~
//****************                                                 //~1122I~
	public  static final String tabName_ServerConnections   ="Server Connections";//~1122R~
	public  static final String tabName_PartnerConnections  ="Partner Connections";//~1122R~
	public  static final String tabName_Top                 ="Top";//~1Ag1I~
	public  static final int    TabLayoutID_Servers     =R.id.MainFrameTabLayout_ServerConnections;//~1122I~
	public  static final int    TabLayoutID_Partners    =R.id.MainFrameTabLayout_PartnerConnections;//~1122R~
	public  static final int    TabLayoutID_Top         =R.id.MainFrameTabLayout_Top;//~1Ag1R~
//****************                                                 //~1120I~
	public  static final String layout_ServerConnections   ="ServerConnections";//~1120I~
	public  static final String layout_PartnerConnections  ="PartnerConnections";//~1120I~
	public  static final String layout_SingleServer        ="SingleServer";//~1120I~
	public  static final String layout_GamesFrame          ="GamesFrame";//~1120I~
	public  static final String layout_ConnectionFrame     ="ConnectionFrame";//~1120I~
	public  static final String layout_ConnectedGoFrame    ="ConnectedGoFrame";//~1121I~
	public  static final String layout_MainFrame           ="MainFrame";//~1125I~
                                                                   //~1121I~
//  public  static final int frameId_MainFrame             =R.layout.mainframe; //initial//~1125R~//~1Af9R~
//  public  static final int frameId_ServerConnections     =R.layout.mainsl;//~1121I~//~1Af9R~
//  public  static final int frameId_PartnerConnections    =R.layout.mainpl;//~1121I~//~1Af9R~
    public  static       int frameId_MainFrame             =R.layout.mainframe; //initial//~1Af9R~
	public  static       int frameId_ServerConnections     =R.layout.mainsl;//~1Af9I~
    public  static       int frameId_PartnerConnections    =R.layout.mainpl;//~1Af9I~
    public  static       int frameId_Top                   =R.layout.maintop;//~1Ag1R~
    public  static final int frameId_MainFrame_mdpi        =R.layout.mainframe_mdpi; //initial//~1Af9I~
	public  static final int frameId_ServerConnections_mdpi=R.layout.mainsl_mdpi;//~1Af9I~
    public  static final int frameId_PartnerConnections_mdpi=R.layout.mainpl_mdpi;//~1Af9I~
    public  static final int frameId_Top_mdpi              =R.layout.maintop_mdpi;//~1Ag1R~
    public  static final int frameId_SingleServer          =R.layout.mainsv;//~1121I~
    public  static final int frameId_GamesFrame            =R.layout.gamesframe;//~1121I~
    public  static final int frameId_ConnectionFrame       =R.layout.connectionframe;//~1121I~
    public  static final int frameId_ConnectedGoFrame      =R.layout.connectedgoframe;//~1121I~
    public  static final int frameId_WhoFrame              =R.layout.whoframe;//~1306I~
    public  static final int frameId_MessageDialog         =R.layout.messagedialog;//~1310I~
    public  static final int frameId_SayDialog             =R.layout.saydialog;//~1311I~
    public  static final int frameId_PartnerFrame          =R.layout.partnerframe;//~1318I~
    public  static final int frameId_LocalViewer           =R.layout.localviewer;//~1323I~
    public  static final int frameId_Help                  =R.layout.help;//~1326I~
    public  static final int frameId_MessageFilter         =R.layout.messagefilter;//~1331I~
    public  static final int frameId_GMPConnection         =R.layout.gmpconnection;//~1404I~
    public  static final int frameId_OpenPartners          =R.layout.openpartners;//~1405I~
    public  static final int frameId_PartnerSendQuestion   =R.layout.partnersendquestion;//~1405I~
                                                                   //~1216I~
                                                                   //~1214I~
    public  static final int dialogId_Message              =R.layout.message;//~1214I~//~1310R~
    public  static final int dialogId_GetParameter         =R.layout.getparameter;//~1215R~//~1310R~
    public  static final int dialogId_Password             =R.layout.password;//~1125I~//~1215R~//~1310R~
    public  static final int dialogId_Information          =R.layout.information;//~1307I~//~1310R~
    public  static final int dialogId_MessageDialog        =R.layout.messagedialog;//~1310R~
    public  static final int dialogId_MatchQuestion        =R.layout.matchquestion;//~1310R~
    public  static final int dialogId_MatchDialog          =R.layout.matchdialog;//~1310I~
    public  static final int dialogId_TellQuestion         =R.layout.tellquestion;//~1311I~
    public  static final int dialogId_EditConnection       =R.layout.editconnection;//~1314I~
    public  static final int dialogId_EditPartner          =R.layout.editpartner;//~1318I~
    public  static final int dialogId_FileDialog           =R.layout.filedialog;//~1326I~
    public  static final int dialogId_HelpDialog           =R.layout.helpdialog;//~1327I~
    public  static final int dialogId_AdvancedOptionEdit   =R.layout.advancedoptionedit;                                                              //~1125I~
    public  static final int dialogId_Question             =R.layout.question;//~1329I~
    public  static final int dialogId_FunctionKeys         =R.layout.functionkeys;//~1330I~
    public  static final int dialogId_EditFilter           =R.layout.editfilter;//~1331I~
    public  static final int dialogId_EditButtons          =R.layout.editbuttons;//~1331I~
    public  static final int dialogId_RelayServer          =R.layout.relayserver;//~1331I~
    public  static final int dialogId_FontSize             =R.layout.fontsize;//~1331I~
    public  static final int dialogId_GlobalGray           =R.layout.globalgray;//~1331I~
    public  static final int dialogId_ColorEdit            =R.layout.editcolor;//~1331I~
    public  static final int dialogId_MailDialog           =R.layout.maildialog;//~1404I~
    public  static final int dialogId_GMPWait              =R.layout.gmpwait;//~1404I~
    public  static final int dialogId_Warning              =R.layout.warning;//~1404I~
    public  static final int dialogId_GameQuestion         =R.layout.gamequestion;//~1405R~
    public  static final int dialogId_BoardQuestion        =R.layout.boardquestion;//~1405I~
    public  static final int dialogId_GameInformation      =R.layout.gameinformation;//~1405I~
    public  static final int dialogId_TextMark             =R.layout.textmark;//~1405I~
    public  static final int dialogId_SendQuestion         =R.layout.sendquestion;//~1405I~
    public  static final int dialogId_PartnerSendQuestion  =R.layout.partnersendquestion;//~1405I~
                                                                   //~1331I~
    public  static final int menuLayoutId_WhoPopup           =R.menu.whopopup;//~1307I~
    public  static final int menuLayoutId_GamesPopup         =R.menu.gamespopup;//~1307I~
                                                                   //~1121I~
//    private static final IdTblEntry[] layouttbl={                //~1Af9R~
//                     new IdTblEntry(layout_MainFrame,             frameId_MainFrame),//~1125I~//~1Af9R~
//                     new IdTblEntry(layout_ServerConnections,     frameId_ServerConnections),//~1121R~//~1Af9R~
//                     new IdTblEntry(layout_PartnerConnections,    frameId_PartnerConnections),//~1121R~//~1Af9R~
//                     new IdTblEntry(layout_SingleServer,          frameId_SingleServer),//~1121R~//~1Af9R~
////                   new IdTblEntry(layout_GamesFrame,            frameId_GamesFrame),//~1121R~//~1217R~//~1Af9R~
////                   new IdTblEntry(layout_ConnectionFrame,       frameId_ConnectionFrame),//~1121R~//~1217R~//~1Af9R~
////                   new IdTblEntry(layout_ConnectedGoFrame,      frameId_ConnectedGoFrame),//~1121R~//~1217R~//~1Af9R~
//                     new IdTblEntry("dummy",-1)//~1120I~         //~1Af9R~
//                     };                                             //~1120I~//~1Af9R~
//*ViewID                                                          //~1121I~
                                                                   //~1216I~
	public  static final int    viewId_BigTimerLabel  =R.id.BigTimer;//~1216R~
	public  static final int    viewId_BoardPanel     =R.id.BoardPanel;//~1121I~//~1217I~
	public  static final int    viewId_ExampleCanvas  =R.id.ExampleCanvas;//~1331I~
	public  static final int    viewId_ListView       =R.id.ListView;//~1219I~
    public  static final int    viewId_Lister         =R.id.Lister;                                                               //~1216I~
    public  static final int    viewId_IconBar        =R.id.IconBar;//~1322I~
    public  static final int    viewId_IconBar1       =R.id.IconBar1;//~1324I~
    public  static final int    viewId_IconBar2       =R.id.IconBar2;//~1324I~
    public  static final int    viewId_NavigationPanel=R.id.NavigationPanel;//~1415I~
    public  static final int    viewId_SeekBarRed     =R.id.SeekBar1;//~1401I~
    public  static final int    viewId_SeekBarGreen   =R.id.SeekBar2;//~1401I~
    public  static final int    viewId_SeekBarBlue    =R.id.SeekBar3;//~1401I~
    public  static final int    viewId_ContainerRed   =R.id.ContainerRed;//~1401I~
    public  static final int    viewId_ContainerGreen =R.id.ContainerGreen;//~1401I~
    public  static final int    viewId_ContainerBlue  =R.id.ContainerBlue;//~1401I~
    public  static final int    viewId_ContainerFontSizeField=R.id.ContainerFontSizeField;//~1401I~
	public  static final int    viewId_DialogBackground=R.id.DialogBackground;//~1404I~
	public  static final int    viewId_Comment        =R.id.Comment;//~1416I~
	public  static final int    viewId_AllComments    =R.id.AllComments;//~1416I~
	public  static final int    viewId_SetStone       =R.id.Label2;//~1424I~

                                                                   //~1413I~
                                                                   //~1404I~
	private static final IdTblEntry[] viewtbl={  //by actionName   //~1124I~
                                                                   //~1124I~
					new IdTblEntry("Observe"       ,              R.id.Observe    ),//~1124I~
					new IdTblEntry("Peek"          ,              R.id.Peek       ),//~1124I~
					new IdTblEntry("Status"        ,              R.id.Status     ),//~1124I~
					new IdTblEntry("Information"   ,              R.id.Information),//~1125I~//~1216R~//~1307R~

					new IdTblEntry("Viewer"        ,              R.id.Viewer     ),//~1215I~
					new IdTblEntry("Lister"        ,              R.id.Lister     ),//~1220I~
					new IdTblEntry("SetStone"        ,            AG.viewId_SetStone),//~1424I~

                                                                   //~1328I~
    //Iconbar                                                      //~1322I~
					new IdTblEntry("undo"                ,        R.id.undo       ),//~1322R~
					new IdTblEntry("sendforward"         ,        R.id.sendforward),//~1322R~
					new IdTblEntry("allback"             ,        R.id.allback    ),//~1322R~
					new IdTblEntry("fastback"            ,        R.id.fastback   ),//~1322R~
					new IdTblEntry("back"                ,        R.id.back       ),//~1322R~
					new IdTblEntry("forward"             ,        R.id.forward    ),//~1322R~
					new IdTblEntry("fastforward"         ,        R.id.fastforward),//~1322R~
					new IdTblEntry("allforward"          ,        R.id.allforward ),//~1322R~
					new IdTblEntry("variationback"       ,        R.id.variationback),//~1322R~
					new IdTblEntry("variationstart"      ,        R.id.variationstart),//~1322R~
					new IdTblEntry("variationforward"    ,        R.id.variationforward),//~1322R~
					new IdTblEntry("main"                ,        R.id.main       ),//~1322R~
					new IdTblEntry("mainend"             ,        R.id.mainend    ),//~1322R~
					new IdTblEntry("send"                ,        R.id.send       ),//~1322R~
					new IdTblEntry("mark"                ,        R.id.mark       ),//~1322R~
					new IdTblEntry("square"              ,        R.id.square     ),//~1322R~
					new IdTblEntry("triangle"            ,        R.id.triangle   ),//~1322R~
					new IdTblEntry("circle"              ,        R.id.circle     ),//~1322R~
					new IdTblEntry("letter"              ,        R.id.letter     ),//~1322R~
					new IdTblEntry("text"                ,        R.id.text       ),//~1322R~
					new IdTblEntry("black"               ,        R.id.black      ),//~1322R~
					new IdTblEntry("white"               ,        R.id.white      ),//~1322R~
					new IdTblEntry("setblack"            ,        R.id.setblack   ),//~1322R~
					new IdTblEntry("setwhite"            ,        R.id.setwhite   ),//~1323R~
					new IdTblEntry("delete"              ,        R.id.delete     ),//~1322R~
					new IdTblEntry("deletemarks"         ,        R.id.deletemarks),//~1323I~
					new IdTblEntry("play"                ,        R.id.play),//~1323I~
                                                                   //~1322I~
    				new IdTblEntry("dummy",-1)                     //~1124I~
                     };                                            //~1124I~
//*                                                                //~v101I~
    public static int bottomSpaceHeight;                           //~v101I~
    public static final int SYSTEMBAR_HEIGHT=48;                   //~v101I~
	public static String PREFKEY_BOTTOMSPACE_HIGHT="BottomSpaceHeight";//~v101I~
    public static int osVersion;                                   //~vab0I~//~v101I~
    public static final int HONEYCOMB=11; //android3.0 (GINGERBREAD=9)//~vab0I~//~v101I~
    public static final int HONEYCOMB_MR1=12; //android3.1         //~v1E4I~
    public static final int HONEYCOMB_MR2=13; //android3.2         //~1A6pI~
    public static final int ICE_CREAM_SANDWICH=14; //android4.0    //~vab0I~//~v101I~
    public static final int LOLLIPOP=21; //android5.0.1 only support PIE(Position Independent Executable)//~1AfuI~
//****************                                                 //~1402I~
	public static void init(Ajagoc Pajagoc)                        //~1402I~
    {                                                              //~1402I~
    	osVersion=Build.VERSION.SDK_INT;                //~vab0I~  //~v101I~
        ajagoc=Pajagoc;                                            //~1109I~//~1329R~//~1402I~
        activity=(Activity)Pajagoc;                                //~1402I~
        context=(Context)Pajagoc;                                  //~1402I~
        isDebuggable=AjagoUtils.isDebuggable(context);             //~v107I~
        startupCtr=AjagoProp.getPreference(PKEY_STARTUPCTR,0);    //~v107I~
        AjagoProp.putPreference(PKEY_STARTUPCTR,startupCtr+1);    //~v107I~
        resource=Pajagoc.getResources();                                //~1109I~//~1329R~//~1402I~
        inflater=Pajagoc.getLayoutInflater();                           //~1113I~//~1329R~//~1402I~
		appName=context.getText(R.string.app_name).toString();     //~1402I~
		pkgName=context.getPackageName();                          //~1A6cI~
		appVersion=context.getText(R.string.Version).toString();   //~1506I~
                                                                   //~v101I~
        if (osVersion>=HONEYCOMB && osVersion<ICE_CREAM_SANDWICH)  //android3 api11-13//~vab0R~//~v101I~
        	bottomSpaceHeight=SYSTEMBAR_HEIGHT;                    //~vab0I~//~v101I~
        screenDencityMdpi=resource.getDisplayMetrics().density==1.0;//~v1D4I~
    }                                                              //~1402I~
//****************                                                 //~1402I~
//    public static int findLayoutIdByName(String Pname)               //~1120I~//~1125R~//~1Af9R~
//    {                                                              //~1120I~//~1Af9R~
//        for (int ii=0;ii<layouttbl.length;ii++)                      //~1120I~//~1Af9R~
//            if (Pname.equals(layouttbl[ii].name))                   //~1120I~//~1Af9R~
//            {                                                      //~1120I~//~1Af9R~
//                if (Dump.Y) Dump.println(Pname+" id="+Integer.toString(layouttbl[ii].id,16));//~1126R~//~1506R~//~1Af9R~
//                return layouttbl[ii].id;                             //~1120I~//~1Af9R~
//            }                                                      //~1120I~//~1Af9R~
//        if (Dump.Y) Dump.println(Pname+" not found(LayoutID)");    //~1506R~//~1Af9R~
//        return -1;                                               //~1Af9R~
//        //out of bound                                 //~1120I~ //~1Af9R~
//    }                                                              //~1120I~//~1Af9R~
//****************                                                 //~1213I~
	private static final IdTblEntry[] icontbl={                    //~1213I~//~1328M~
					new IdTblEntry("ijago.gif"     ,              R.drawable.ijago),//~1213I~//~1328M~
					new IdTblEntry("iboard.gif"    ,              R.drawable.iboard),//~1213I~//~1328M~
					new IdTblEntry("ihelp.gif"     ,              R.drawable.ihelp),//~1213I~//~1328M~
					new IdTblEntry("iconn.gif"     ,              R.drawable.iconn),//~1213I~//~1328M~
					new IdTblEntry("iwho.gif"      ,              R.drawable.iwho),//~1213I~//~1328M~
					new IdTblEntry("igames.gif"    ,              R.drawable.igames) //~1213I~//~1327R~//~1328M~
                    };                                              //~1213I~//~1328M~
	public static int findIconId(String Pname)                     //~1213I~
    {                                                              //~1213I~
		int id=findViewIdByName(icontbl,Pname);                        //~1328I~
    	if (Dump.Y) Dump.println("icon search "+Pname+" id="+Integer.toHexString(id));//~1213I~//~1506R~
    	return id;                                                 //~1213I~//~1328R~
    }                                                              //~1213I~
//****************                                                 //~1327I~
	private static final IdTblEntry[] soundtbl={                   //~1327I~//~1328M~
					new IdTblEntry("high"          ,              R.raw.high),//~1327I~//~1328M~
					new IdTblEntry("message"       ,              R.raw.message),//~1327I~//~1328M~
					new IdTblEntry("click"         ,              R.raw.click),//~1327I~//~1328M~
					new IdTblEntry("stone"         ,              R.raw.stone),//~1327I~//~1328M~
					new IdTblEntry("wip"           ,              R.raw.wip),//~1327I~//~1328M~
					new IdTblEntry("yourmove"      ,              R.raw.yourmove),//~1327I~//~1328M~
					new IdTblEntry("game"          ,              R.raw.game),//~1327I~//~1328M~
                    };                                             //~1327I~//~1328M~
	public static int findSoundId(String Pname)                    //~1327I~
    {                                                              //~1327I~
		int id=findViewIdByName(soundtbl,Pname);                       //~1328I~
        if (Dump.Y) Dump.println("Sound "+Pname+",id="+Integer.toHexString(id));//~1506R~
    	return id;                                                 //~1327I~//~1328R~
    }                                                              //~1327I~
//****************                                                 //~1125I~
	public static String findFrameNameByInstance(Object PframeObject) //~1125I~
    {                                                              //~1125I~
    	String framename;                                          //~1125I~
    //****************                                             //~1125I~
    	if (PframeObject instanceof MainFrame)                     //~1125I~
        	framename=layout_MainFrame;                            //~1125I~
        else                                                       //~1125I~
    	if (PframeObject instanceof Go)                            //~1125I~
        	framename=layout_SingleServer;                         //~1125I~
        else                                                       //~1125I~
    	if (PframeObject instanceof ConnectedGoFrame)             //~1125I~
        	framename=layout_ConnectedGoFrame;                     //~1125I~
        else                                                       //~1125I~
    	if (PframeObject instanceof GamesFrame)                   //~1125I~
        	framename=layout_GamesFrame;                           //~1125I~
        else                                                       //~1125I~
    	if (PframeObject instanceof ConnectionFrame)              //~1125I~
        	framename=layout_ConnectionFrame;                      //~1125I~
        else                                                       //~1125I~
        	framename=null;                                        //~1125I~
        return framename;                                          //~1125I~
    }                                                              //~1125I~
//****************                                                 //~1120I~
	public static int findViewIdByName(String Pname)               //~1120R~
    {                                                              //~1120I~
    	return findViewIdByName(viewtbl,Pname);                    //~1328I~
    }                                                              //~1120I~
//****************                                                 //~1328I~
	public static int findViewIdByName(IdTblEntry[] Pviewtbl,String Pname)//~1328R~
    {                                                              //~1328I~
    	int id=-1,sz=Pviewtbl.length;                              //~1328R~
    	for (int ii=0;ii<sz;ii++)                                  //~1328I~
    		if (Pname.equals(Pviewtbl[ii].name))                   //~1328I~
            {                                                      //~1328R~
    			id=Pviewtbl[ii].id;                                //~1328R~
                break;                                             //~1328I~
            }                                                      //~1328I~
    	if (Dump.Y) Dump.println("FindViewByName "+Pname+" id="+Integer.toHexString(id));//~1506R~
    	return id;                                                 //~1328R~
    }                                                              //~1328I~
//****************                                                 //~1328I~
	public static View findViewByName(IdTblEntry[] Pviewtbl,String Pname)//~1328I~
    {                                                              //~1328I~
		int id=findViewIdByName(Pviewtbl,Pname);                   //~1328I~
        if (id<0)                                                  //~1328I~
        	return null;                                           //~1328I~
    	return findViewById(id);                                   //~1328I~
    }                                                              //~1328I~
//****************                                                 //~1120I~
	public static View findViewByName(String Pname)                //~1120I~
    {                                                              //~1120I~
        return findViewByName(AG.currentLayout,Pname);             //~1121R~
    }                                                              //~1120I~
//****************                                                 //~1216I~
	public static View findViewById(int Presid)                   //~1216I~
    {                                                              //~1216I~
        return AG.currentLayout.findViewById(Presid);              //~1216I~
    }                                                              //~1216I~
//****************                                                 //~1416I~
	public static View findViewById(View Playout,int Pid)          //~1416I~
    {                                                              //~1416I~
		return Playout.findViewById(Pid);                          //~1416I~
    }                                                              //~1416I~
//****************                                                 //~1121I~//~1216M~
	public static View findViewByName(View Playout,String Pname)   //~1121I~//~1216M~
    {                                                              //~1121I~//~1216M~
        int id=findViewIdByName(Pname);                            //~1216M~
		return Playout.findViewById(id);                             //~1121I~//~1216M~
    }                                                              //~1121I~//~1216M~
//****************                                                 //~1216I~
	public static void setCurrentView(int Presourceid,View Pview)         //~1216I~
    {                                                              //~1216I~
        if (Pview==null)                                           //~v106I~
        {                                                          //~v106I~
        	if (Dump.Y) Dump.println("setCurrentView null resid="+Integer.toHexString(Presourceid));//~v106I~//~1Ae5R~
            return;                                                //~v106I~
        }                                                          //~v106I~
        currentLayout=Pview;                                       //~1216I~
        currentLayoutId=Presourceid;                               //~1216I~
        currentLayoutLabelSeqNo=0;                                 //~1216R~
        currentLayoutTextFieldSeqNo=0;                             //~1216I~
        currentLayoutTextAreaSeqNo=0;                               //~1416I~
        currentLayoutButtonSeqNo=0;                                //~1306I~
        currentLayoutCheckBoxSeqNo=0;                               //~1328I~
        currentLayoutSpinnerSeqNo=0;                               //~1331I~
        currentLayoutSeekBarSeqNo=0;                               //~1331I~
        if (Dump.Y) Dump.println("setCurrentView resid="+Integer.toHexString(Presourceid)+",Viewid="+(Pview==null?"null":Integer.toHexString(Pview.getId())));//~v106I~//~1Ae5R~
                                                                   //~v106I~
    }
//****************                                                 //~1216I~
	public static View getCurrentLayout()
	{
		return currentLayout;//~1216I~
	}
//****************                                                 //~1216I~
	public static void setCurrentDialog(Dialog Pdialog)            //~1216I~
	{                                                              //~1216I~
    	currentIsDialog=true;                                      //~1311I~
		currentDialog=Pdialog;                                     //~1216I~
	}                                                              //~1216I~
//****************                                                 //~1311I~
	public static void setCurrentFrame(Frame Pframe)               //~1311I~
	{                                                              //~1311I~
    	currentIsDialog=false;                                     //~1311I~
		currentDialog=null;                                        //~v1EoI~
		currentFrame=Pframe;                                       //~1311I~
    	setCurrentView(Pframe.framelayoutresourceid,Pframe.framelayoutview);//for wrap operation//~1504I~
	}                                                              //~1311I~
//****************                                                 //~1216I~
	public static void setDialogClosed()                           //~1325I~
	{                                                              //~1325I~
    	currentIsDialog=false;                                     //~1325I~
	}                                                              //~1325I~
//****************                                                 //~v1EeI~
    public static void setDialogClosed(Dialog Pdialog)             //~v1EeI~
	{                                                              //~v1EeI~
        if (Dump.Y) Dump.println("AG:setDialogClosed dialogname="+Pdialog.dialogname+",currentIsDialog="+currentIsDialog+",currentLayout="+currentLayout.toString());//~v1EeI~
     if (currentIsDialog)                                          //~v1EeR~
     {                                                             //~v1EeR~
      if (Pdialog.parentDialog!=null)   //dialog on dialog         //~v1EeR~
      {                                                            //~v1EeR~
        currentDialog=Pdialog.parentDialog;                        //~v1EeR~
        if (Dump.Y) Dump.println("AG:setDialogClosed parent is dialog="+currentDialog.toString());//~v1EeR~
      }                                                            //~v1EeR~
      else                                                         //~v1EeR~
      {                                                            //~v1EeR~
        currentIsDialog=false;                                     //~v1EeR~
//        Frame f=Pdialog.layoutFrame;                             //~v1EeI~
//        if (Dump.Y) Dump.println("AG:setDialogClosed parent is frame="+(f==null?"null":f.framename));//~v1EeI~
//        if (f!=null && f.framelayoutresourceid!=0)  //should recover framelayoutview//~v1EeI~
//        {                                                        //~v1EeI~
//            if (Dump.Y) Dump.println("AG:setDialogClosed parent is frame="+f.framename);//~v1EeI~
//            setCurrentView(f.framelayoutresourceid,f.framelayoutview);//~v1EeI~
//        }                                                        //~v1EeI~
      }                                                            //~v1EeR~
     }//maintain current view                                      //~v1EeR~
      	if (Pdialog.dismissListener!=null)                         //~v1EeI~
      	{                                                          //~v1EeI~
        	if (Dump.Y) Dump.println("AG:setDialogClosed dismisslistenercall");//~v1EeI~
            Pdialog.dismissListener.doAction(AG.resource.getString(R.string.ActionDismissDialog));	//notify to Dialog callet to new frame  will be opened//~v1EeI~
      	}                                                          //~v1EeI~
	}                                                              //~v1EeI~
//****************                                                 //~1325I~
	public static Dialog getCurrentDialog()                          //~1216I~
	{                                                              //~1216I~
        if (currentIsDialog)                                       //~1401I~
			return currentDialog;                                      //~1216I~//~1401R~
        return null;                                               //~1401I~
	}                                                              //~1216I~
//****************                                                 //~1324I~
	public static boolean currentIsDialog()                        //~1427R~
	{                                                              //~1324I~
		return currentIsDialog;                                    //~1427R~
	}                                                              //~1324I~
	public static Frame getCurrentFrame()                          //~1427I~
	{                                                              //~1427I~
		return currentFrame;                                       //~1427I~
	}                                                              //~1427I~
//****************                                                 //~1216I~
	private static final int[] labelviewtbl={               //~1328I~
					R.id.Label1     ,//~1307I~//~1328M~           //~1331R~
					R.id.Label2     ,//~1216I~//~1328M~           //~1331R~
					R.id.Label3     ,//~1216I~//~1328M~           //~1331R~
					R.id.Label4     ,//~1310I~//~1328M~           //~1331R~
					R.id.Label5     ,//~1310I~//~1328M~           //~1331R~
					R.id.Label6     ,//~1314I~//~1328M~           //~1331R~
					R.id.Label7     ,//~1314I~//~1328M~           //~1331R~
					R.id.Label8     ,//~1330I~                    //~1331R~
					R.id.Label9     ,//~1330I~                    //~1331R~
					R.id.Label10    ,//~1330I~                    //~1331R~
                    };                                             //~1328I~
	public static View findLabelView()                             //~1216I~
    {                                                              //~1216I~
    	int id=labelviewtbl[currentLayoutLabelSeqNo++];            //~1331I~
        if (Dump.Y) Dump.println("findLabelView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                               //~1216I~//~1328R~//~1331R~
    }                                                              //~1216I~
//****************                                                 //~1216I~
	private static final int[] textfieldviewtbl={           //~1328I~
					R.id.TextField1 ,//~1216I~//~1328M~           //~1331R~
					R.id.TextField2 ,//~1216I~//~1328M~           //~1331R~
					R.id.TextField3 ,//~1216I~//~1328M~           //~1331R~
					R.id.TextField4 ,//~1310I~//~1328M~           //~1331R~
					R.id.TextField5 ,//~1314I~//~1328M~           //~1331R~
					R.id.TextField6 ,//~1314I~//~1328M~           //~1331R~
					R.id.TextField7 ,//~1330I~                    //~1331R~
					R.id.TextField8 ,//~1330I~                    //~1331R~
					R.id.TextField9 ,//~1330I~                    //~1331R~
					R.id.TextField10,//~1330I~                    //~1331R~
                    };                                             //~1328I~
	public static View findTextFieldView()                         //~1216I~
    {                                                              //~1216I~
    	int id=textfieldviewtbl[currentLayoutTextFieldSeqNo++];    //~1331I~
        if (Dump.Y) Dump.println("findTextFieldView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                               //~1216I~//~1328R~//~1331R~
    }                                                              //~1216I~
//****************                                                 //~1416I~
	private static final int[] textareaviewtbl={                   //~1416I~
					R.id.TextArea1 ,                               //~1416I~
					R.id.TextArea2 ,                               //~1416I~
                    };                                             //~1416I~
	public static View findTextAreaView()                          //~1416I~
    {                                                              //~1416I~
    	int id=textareaviewtbl[currentLayoutTextAreaSeqNo++];      //~1416I~
        if (Dump.Y) Dump.println("findTextAreaView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                                   //~1416I~
    }                                                              //~1416I~
//****************                                                 //~1306I~
	private static final int[] buttonviewtbl={              //~1328I~
					R.id.Button1    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button2    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button3    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button4    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button5    ,//~1310I~//~1328M~           //~1331R~
					R.id.Button6    ,//mainsl.xml                  //~1Af9I~
					R.id.Button7    ,//mainsl.xml                  //~1Af9I~
					R.id.Button8    ,//mainpl.xml                  //~1Af9I~
                    };                                             //~1328I~
	public static View findButtonView()                            //~1306I~
    {                                                              //~1306I~
    	int id=buttonviewtbl[currentLayoutButtonSeqNo++];          //~1331I~
        if (Dump.Y) Dump.println("findButtonView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                               //~1306I~//~1328R~//~1331R~
    }                                                              //~1306I~
//****************                                                 //~1328I~
	private static final int[] chkboxviewtbl={              //~1328I~//~1331R~
					 R.id.CheckBox1  ,//~1328M~                   //~1331R~
					 R.id.CheckBox2  ,//~1328M~                   //~1331R~
					 R.id.CheckBox3  ,//~1328M~                   //~1331R~
					 R.id.CheckBox4  ,//~1328M~                   //~1331R~
					 R.id.CheckBox5  ,//~1328M~                   //~1331R~
					 R.id.CheckBox6  ,//~1328M~                   //~1331R~
					 R.id.CheckBox7  ,//~1328M~                   //~1331R~
					 R.id.CheckBox8  ,//~1328M~                   //~1331R~
                    };                                             //~1328I~
	public static View findCheckBoxView()                          //~1328I~
    {                                                              //~1328I~
    	int id=chkboxviewtbl[currentLayoutCheckBoxSeqNo++];        //~1331I~
        if (Dump.Y) Dump.println("findCheckBoxView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                 //~1328R~         //~1331R~
    }                                                              //~1328I~
//****************                                                 //~1331I~
	private static final int[] spinnerviewtbl={                    //~1331R~
					R.id.Spinner1  ,                              //~1331R~
					R.id.Spinner2  ,                              //~1331R~
                    };                                             //~1331I~
	public static View findSpinnerView()                           //~1331I~
    {                                                              //~1331I~
    	int id=spinnerviewtbl[currentLayoutSpinnerSeqNo++];        //~1331R~
        if (Dump.Y) Dump.println("findSpinnerView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                                   //~1331I~
    }                                                              //~1331I~
//****************                                                 //~1331I~
	private static final int[] seekbarviewtbl={                    //~1331I~
					R.id.SeekBar1  ,                               //~1331I~
					R.id.SeekBar2  ,                               //~1331I~
					R.id.SeekBar3  ,                               //~1331I~
                    };                                             //~1331I~
	public static View findSeekBarView()                           //~1331I~
    {                                                              //~1331I~
    	int id=seekbarviewtbl[currentLayoutSeekBarSeqNo++];        //~1331I~
        if (Dump.Y) Dump.println("findSeekBarView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                                   //~1331I~
    }                                                              //~1331I~
//****************                                                 //~1326I~
	public static boolean isTopFrame()                             //~1326I~
    {                                                              //~1326I~
        Frame frame=Window.getCurrentFrame();                      //~1326I~
        if (Dump.Y) Dump.println("isTopFrame current frame:"+frame.framename);//~1506R~
        return frame==AG.mainframe;                                //~1326I~
    }                                                              //~1326I~
	public static boolean isMainFrame(Frame Pframe)                //~1331I~
    {                                                              //~1331I~
	    return (Pframe.framelayoutresourceid==AG.frameId_MainFrame);//~1331I~
    }                                                              //~1331I~
//****************                                                 //~1126I~
//*for Modal Dialog                                                //~1126I~
//****************                                                 //~1126I~
	public static Thread mainThread;                               //~1126I~
    public static boolean isMainThread()                           //~1126M~
    {                                                              //~1126M~
    	return (Thread.currentThread()==AG.mainThread);              //~1126M~
    }                                                              //~1126M~

//*******************                                              //~1120I~
}//class AG                                                        //~1107R~
