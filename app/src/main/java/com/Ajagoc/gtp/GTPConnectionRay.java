//*CID://+1Aj1R~:                                   update#=  244; //~1Aj1R~
//***********************************************************************//~@@@1I~
//1Aj1 2017/02/04 reduce Aray plaout default 10000-->1000 and default playmode:time per each mode//~1Aj1I~
//1Ag5 2016/10/09 checkbox pie,set on as default when version>=5   //~1Ag5I~
//1Ag0 2014/10/05 displaying bot version number on menu delays until next restart(menu itemname is set at start and not changed)//~1Ag0I~
//1Afc 2016/09/22 like V1C1 fuego, add ray as player               //~1AfcI~
//v1C8 2014/09/01 Agnugo.png-->Agnugo.zip(unzip at first time)     //~v1C8I~
//v1C2 2014/08/23 pachi hepl filename not found exception          //~v1C2I~
//v1C1 2014/08/21 install fuego as GTP client                      //~v1C1I~
//v1C0 2014/08/15 pachi:-s(seed) parameter for more randamizing    //~v1C0I~//~v1C1R~
//v1Bb 2014/08/14 -f book.dat support for pachi                    //~v1BbI~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//@@@1 Modal dialog                                                //~@@@1I~//~v106R~
//***********************************************************************//~@@@1I~
package com.Ajagoc.gtp;                                            //~v1B6R~


import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;                                  //~1AfcI~
import android.widget.CheckBox;                                    //~1AfcI~
import android.widget.Button;                                      //~1AfcI~
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;                                     //~1AfcI~

import com.Ajagoc.AjagoUtils;                                      //~v106I~
import com.Ajagoc.R;
import com.Ajagoc.awt.ButtonRG;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.java.File;                                       //~1516I~
import com.Ajagoc.AjagoAlert;                                    //~v1B6I~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;

import com.Ajagoc.AG;                                              //~v1B6I~


//class OkAdapter                                                  //~v1C1R~
//{   public void gotOk() {}                                       //~v1C1R~
//}                                                                //~v1C1R~

public class GTPConnectionRay extends GTPConnection                   //~v1B6I~//~1AfcR~
	implements GTPInterface
{                                                                  //~v1B6R~
//* by "ray --help" ****                                           //~1AfcI~
//--playout              : Set playouts                         default mode       default:10000//~1AfcI~
//--time                 : Set all thinking time                                   default:90.0//~1AfcI~
//--size                 : Set board size                          //~1AfcI~
//--const-time           : Set mode const time, and set thinking time per move     default:10.0//~1AfcI~
//--thread               : Set threads                                             max:32//~1AfcI~
//--komi                 : Set komi                                //~1AfcI~
//--handicap             : Set the number of handicap stones (for testing)//~1AfcI~
//--reuse-subtree        : Reuse subtree                           //~1AfcI~
//--pondering            : Set pondering mode                      //~1AfcI~
//--tree-size            : Set tree size (tree size must be 2 ^ n) //~1AfcI~
//--no-debug             : Prohibit any debug message              //~1AfcI~
                                                                   //~1AfcI~
//********************************************************************//~1AfcI~
//commands                                                         //~1AfcR~
//  NO "go_rules japanese"                                         //~1AfcI~
//********************************************************************//~1AfcI~
//: boardsize                                                      //~1AfcI~
//: clear_board                                                    //~1AfcI~
//: name                                                           //~1AfcI~
//: protocol_version                                               //~1AfcI~
//: genmove                                                        //~1AfcI~
//: play                                                           //~1AfcI~
//: known_command                                                  //~1AfcI~
//: list_commands                                                  //~1AfcI~
//: quit                                                           //~1AfcI~
//: komi                                                           //~1AfcI~
//: get_komi                                                       //~1AfcI~
//: final_score                                                    //~1AfcI~
//: time_settings                                                  //~1AfcI~
//: time_left                                                      //~1AfcI~
//: version                                                        //~1AfcI~
//: genmove_black                                                  //~1AfcI~
//: genmove_white                                                  //~1AfcI~
//: black                                                          //~1AfcI~
//: white                                                          //~1AfcI~
//: showboard                                                      //~1AfcI~
//: final_status_list                                              //~1AfcI~
//: fixed_handicap                                                 //~1AfcI~
//: place_free_handicap                                            //~1AfcI~
//: set_free_handicap                                              //~1AfcI~
//: kgs-genmove_cleanup                                            //~1AfcI~
//********************************************************************//~1AfcI~
                                                                   //~1AfcI~
    private static final int LAYOUT=R.layout.dialoggtpconnection_ray;//~1AfcM~
                                                                   //~1AfcI~
//  private static final String DEFAULT_GTPSERVER="Afuego";        //~1AfcR~
    private static final String DEFAULT_GTPSERVER="Aray";          //~1AfcI~
//  private static final String DEFAULT_GTPSERVER_ORGNAME="fuego"; //~1AfcR~
//  private static final String DEFAULT_GTPSERVER_ORGNAME="ray";   //~1Ag0R~
    public  static final String DEFAULT_GTPSERVER_ORGNAME="ray";   //~1Ag0I~
//  private static final String FUEGO_RULE_JAPANESE="go_rules japanese";//~1AfcR~
//  private static final String FUEGO_VERSION="1.1";               //~1AfcR~
    private static final String RAY_VERSION="8.0";                 //~1AfcR~
//  private static final String PREFKEY_PROGRAM="GTPprogramFuego"; //~1AfcR~
    private static final String PREFKEY_PROGRAM="GTPprogramRay";   //~1AfcI~
//  private static final String PREFKEY_PGMOPTION="GTPprogramoptionFuego";//~1AfcR~
//  private static final String PREFKEY_BOARDSIZE="GTPboardsizeFuego";//~1AfcR~
    private static final String PREFKEY_BOARDSIZE="GTPboardsizeRay";//~1AfcI~
//  private static final String PREFKEY_YOURNAME="GTPyournameFuego";//~1AfcR~
    private static final String PREFKEY_YOURNAME="GTPyournameRay"; //~1AfcI~
//  private static final String PREFKEY_TIMESETTINGMAIN="GTPtimesettingMainFuego";//~1AfcR~
//  private static final String PREFKEY_TIMESETTINGEXTRA="GTPtimesettingExtraFuego";//~1AfcR~
//  private static final String PREFKEY_TIMESETTINGMOVE="GTPtimesettingMoveFuego";//~1AfcR~
//  private static final String PREFKEY_HANDICAP="GTPhandicapFuego";//~1AfcR~
    private static final String PREFKEY_HANDICAP="GTPhandicapRay"; //~1AfcI~
//  private static final String PREFKEY_WHITE="GTPyouarewhiteFuego";//~1AfcR~
//  private static final String PREFKEY_RULE="GTPruleFuego";       //~1AfcR~
//  private static final String PREFKEY_KOMI="GTPkomiFuego";       //~1AfcR~
    private static final String PREFKEY_KOMI="GTPkomiRay";         //~1AfcI~
//  private static final String PREFKEY_VERBOSE="GTPverboseFuego"; //~1AfcR~
    private static final String PREFKEY_VERBOSE="GTPverboseRay";   //~1AfcI~
//  private static final String PREFKEY_BOOKFILE="GTPbookFileFuego";//~1AfcR~
//  private static final String PREFKEY_BOOKUSE="GTPbookUseFuego"; //~1AfcR~
//  private static final String PREFKEY_BOOKFILE_ZIPSIZE="GTPbookZipSizeFuego";//~1AfcR~
//  private static final String PREFKEY_GTPSERVER_ZIPSIZE="GTPserverZipSizeFuego";//~v1C1R~//~1AfcR~
    private static final String PREFKEY_GTPSERVER_ZIPSIZE="GTPserverZipSizeRay";//~1AfcI~
	private static final String PREFKEY_WHITE="GTPyouarewhiteRay"; //~1AfcI~
    public  static final String PREFKEY_GTPSERVER_VERSION="GTPserverVersionRay";//~1AfcR~
                                                                   //~1AfcI~
    private static final String PREFKEY_PLAYMODE="GTPplaymodeRay"; //~1AfcR~
    private static final String PREFKEY_PLAYOUT="GTPplayoutRay";   //~1AfcI~
    private static final String PREFKEY_CONSTTIME="GTPconsttimeRay";//~1AfcI~
    private static final String PREFKEY_ALLTIME="GTPalltimeRay";   //~1AfcI~
    private static final String PREFKEY_PONDERING="GTPponderingRay";//~1AfcI~
    private static final String PREFKEY_TREESIZE="GTPtreesizeRay"; //~1AfcI~
    private static final String PREFKEY_REUSESUBTREE="GTPreusesubtreeRay";//~1AfcI~
    private static final String PREFKEY_THREAD="GTPThreadRay";     //~1AfcI~
//    private static final int PLAYMODE_PLAYOUT=1;         //playout count per move//~1AfcR~
//    private static final int PLAYMODE_CONSTTIME=2;       //const time per move//~1AfcR~
//    private static final int PLAYMODE_ALLTIME=3;       //const time per move//~1AfcR~
//  private static final int PLAYMODE_DEFAULT=PLAYMODE_PLAYOUT;    //~1AfcR~//+1Aj1R~
    private static final int PLAYMODE_DEFAULT=PLAYMODE_CONSTTIME;  //+1Aj1I~
//  private static final int PLAYOUT_DEFAULT=10000;                //~1AfcR~//~1Aj1R~
    private static final int PLAYOUT_DEFAULT=1000;                 //~1Aj1I~
    private static final int CONSTTIME_DEFAULT=10;                 //~1AfcI~
    private static final int ALLTIME_DEFAULT=90;                   //~1AfcI~
    private static final String TIMESETTINGS_RAY="300m+60s/1";  //ray ignores time settings//~1AfcR~
    private static final String OPT_PLAYOUT         =" --playout ";//~1AfcR~
    private static final String OPT_ALLTIME         =" --time ";   //~1AfcI~
    private static final String OPT_BOARDSIZE       =" --size ";   //~1AfcI~
    private static final String OPT_CONSTTIME       =" --const-time ";//~1AfcI~
    private static final String OPT_THREAD          =" --thread "; //~1AfcI~
    private static final String OPT_KOMI            =" --komi ";   //~1AfcI~
    private static final String OPT_HANDICAP        =" --handicap ";//~1AfcI~
    private static final String OPT_PONDERING       =" --pondering ";//~1AfcR~
    private static final String OPT_TREESIZE        =" --tree-size ";//~1AfcI~
    private static final String OPT_REUSESUBTREE    =" --reuse-subtree ";//~1AfcI~
    private static final String OPT_NODEBUG         =" --no-debug";//~1AfcI~
    private static final int MAX_THREAD=32;                        //~1AfcI~
    private static final int BOARDSIZE_MIN=9;                      //~1AfcI~
    private static final int BOARDSIZE_MID=13;                     //~1AfcI~
    private static final int BOARDSIZE_MAX=19;                     //~1AfcI~
                                                                   //~v1BbI~
//  private static final String FUEGO_OPTION_CONFIG="--config";    //~1AfcR~
//  private static final String FUEGO_CFG_FILENAME="Afuego.cfg";   //~1AfcR~
//  private String book;                                           //~1AfcR~
    private RadioGroup rgBoardsize;                                //~1AfcR~
    private EditText   etPlayout,etConsttime,etAlltime,etThread;   //~1AfcI~
    private int Playmode,Playout,Consttime,Alltime,Thread;         //~1AfcR~
    private boolean Pondering;                                     //~1AfcI~
    private CheckBox  cbPondering;                                 //~1AfcR~
    private ButtonRG rgPlaymode;                                   //~1AfcI~
    private Button btnShowOption;                                  //~1AfcI~
    private LinearLayout llDebug;                                  //~1AfcI~
    public static String Sversion="";                              //~1AfcI~

//  public GTPConnectionFuego(Frame f)                             //~1AfcR~
    public GTPConnectionRay(Frame f)                               //~1AfcI~
    {                                                              //~v1B6I~
        super(f,LAYOUT);                                           //~1AfcR~
	}
//*********                                                        //~1725I~//~v1B6I~
	@Override   //GTPConnection                                    //~v1C1R~
    void initVariable()                                            //~v1C1R~
    {                                                              //~v1C1R~
	    default_GTPSERVER_ORGNAME=DEFAULT_GTPSERVER_ORGNAME;       //~v1C1R~
	    default_GTPSERVER=DEFAULT_GTPSERVER;                       //~v1C1R~
        prefkey_PROGRAM=PREFKEY_PROGRAM;                           //~v1C1R~
//        prefkey_TIMESETTINGMAIN=PREFKEY_TIMESETTINGMAIN;         //~1AfcR~
//        prefkey_TIMESETTINGEXTRA=PREFKEY_TIMESETTINGEXTRA;       //~1AfcR~
//        prefkey_TIMESETTINGMOVE=PREFKEY_TIMESETTINGMOVE;         //~1AfcR~
//        prefkey_BOOKFILE=PREFKEY_BOOKFILE;                       //~1AfcR~
//        prefkey_BOOKFILE_ZIPSIZE=PREFKEY_BOOKFILE_ZIPSIZE;       //~1AfcR~
//        prefkey_PGMOPTION=PREFKEY_PGMOPTION;                     //~1AfcR~
//  	prefkey_VERBOSE=PREFKEY_VERBOSE;                           //~1AfcR~
        prefkey_GTPSERVER_ZIPSIZE=PREFKEY_GTPSERVER_ZIPSIZE;       //~v1C2I~
//        prefkey_HANDICAP=PREFKEY_HANDICAP;                       //~1AfcR~
//        prefkey_BOARDSIZE=PREFKEY_BOARDSIZE;                     //~1AfcR~
        prefkey_YOURNAME=PREFKEY_YOURNAME;                         //~1AfcR~
//        prefkey_KOMI=PREFKEY_KOMI;                               //~1AfcR~
//        prefkey_BOOKUSE=PREFKEY_BOOKUSE;                         //~1AfcR~
//        prefkey_WHITE=PREFKEY_WHITE;                             //~1AfcR~
//        prefkey_RULE=PREFKEY_RULE;                               //~1AfcR~
        prefkey_GTPSERVER_VERSION=PREFKEY_GTPSERVER_VERSION;       //~1AfcI~
                                                                   //~1AfcI~
        Sversion=Global.getParameter(PREFKEY_GTPSERVER_VERSION,"");//~1AfcI~
        strTimesettings=TIMESETTINGS_RAY;                          //~1AfcI~
    }                                                              //~v1C1R~
//**************************************************************   //~v1B6I~
//  GTPConnectionFuego Co=this;                                    //~1AfcR~
//***************************************************************************//~v1B6R~
	@Override   //GTPConnection                                    //~v1C1R~
            boolean play()                                         //~v1C1R~
    {                                                              //~v1B6I~
    	String text=getCommandParm();                              //~v1B6I~
        File f=new File(text);                                        //~v1B6I~
        if (!f.exists())                                           //~v1B6I~
        {                                                          //~v1B6R~
	    	int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;       //~1604I~//~v1B6I~
        	AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1B6R~
        			AG.resource.getString(R.string.GtpErr_Pgm_NotFound)+":\n"+text,flag);//~v1B6R~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6I~
        String option=getPgmOption();                              //~1AfcR~
        useBook=false;                                             //~1AfcI~
                                                                   //~v1B6I~
        if (!option.equals(""))                                    //~v1B6I~
        	text+=" "+option;                                      //~v1B6I~
        C=new GTPConnector(text,this);                                  //~v1B6R~
        C.setGTPInterface(this);                                   //~v1B6I~
        try                                                        //~v1B6I~
        {                                                          //~v1B6R~
            setOk(new OkAdapter ()                                 //~v1B6R~
                {   public void gotOk () //callback from C.connect //~v1B6R~
                    {                                              //~v1B6R~
                        if (Dump.Y) Dump.println("GTPConnection:gotOk");//~v1B6I~
                        initializedGoGui();                        //~v1B6I~
                    }                                              //~v1B6R~
                }                                                  //~v1B6R~
            );                                                     //~v1B6R~
            C.connect();                                           //~v1B6I~
        }                                                          //~v1B6I~
        catch (Exception e)                                        //~v1B6I~
        {                                                          //~v1B6R~
            Dump.println(e,"Play Exception");                      //~v1B6R~
	    	int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;   //~v1B6I~
        	AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1B6I~
					AG.resource.getString(R.string.GtpErr_Pgm_Failed)+e.toString(),flag);//~v1B6I~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6I~
        return true;                                               //~v1B6I~
    }//play                                                        //~v1B6I~
//***************************************************************************//~v1C2I~
	@Override                                                      //~v1C2I~
	protected String getPgmOption()                                //~1AfcR~
    {                                                              //~v1C2I~
        int btnid;                                                 //~1AfcI~
    //************************                                     //~1AfcI~
        String yn=YourNameField.getText().toString();              //~1AfcM~
        if (yn!=null && !yn.equals(""))                            //~1AfcM~
        {                                                          //~1AfcM~
        	YourName=yn;                                           //~1AfcM~
        	Global.setParameter(prefkey_YOURNAME,YourName);        //~1AfcM~
        }                                                          //~1AfcM~
        btnid=rgBoardsize.getCheckedRadioButtonId();               //~1AfcI~
        if (btnid==R.id.GTPRAY_BOARDSIZE_9)                       //~1AfcR~
	        BoardSize=BOARDSIZE_MIN;                              //~1AfcI~
        else                                                       //~1AfcI~
        if (btnid==R.id.GTPRAY_BOARDSIZE_13)                       //~1AfcR~
	        BoardSize=BOARDSIZE_MID;                              //~1AfcI~
        else                                                       //~1AfcI~
	        BoardSize=BOARDSIZE_MAX;                              //~1AfcI~
        Global.setParameter(PREFKEY_BOARDSIZE,BoardSize);          //~1AfcI~
                                                                   //~1AfcI~
        Handicap=AjagoUtils.str2int(HandicapField.getText().toString(),DEFAULT_HANDICAP);//~1AfcI~
        if (Handicap>MAX_HANDICAP)                                 //~1AfcI~
        	Handicap=MAX_HANDICAP;                                 //~1AfcI~
        Global.setParameter(PREFKEY_HANDICAP,Handicap);            //~1AfcR~
                                                                   //~1AfcI~
        String strKomi=KomiField.getText().toString();             //~1AfcI~
        int pos=strKomi.indexOf(".");                              //~1AfcI~
        if (pos>0)                                                 //~1AfcI~
        	strKomi=strKomi.substring(0,pos);                      //~1AfcI~
        Komi=AjagoUtils.str2int(strKomi,DEFAULT_KOMI);             //~1AfcR~
        Global.setParameter(PREFKEY_KOMI,Komi);                    //~1AfcI~
                                                                   //~1AfcI~
        MyColor=White.isChecked()?GTPConnector.WHITE:GTPConnector.BLACK;//~1AfcI~
        Global.setParameter(PREFKEY_WHITE,MyColor);                //~1AfcI~
                                                                   //~1AfcI~
        Playmode=rgPlaymode.getChecked();                          //~1AfcR~
	    Playout=AjagoUtils.str2int(etPlayout.getText().toString(),0);//~1AfcR~
        if (Playout<=0)                                            //~1AfcI~
        	Playout=PLAYOUT_DEFAULT;                               //~1AfcI~
	    Consttime=AjagoUtils.str2int(etConsttime.getText().toString(),0);//~1AfcR~
        if (Consttime<=0)                                          //~1AfcI~
        	Consttime=CONSTTIME_DEFAULT;                           //~1AfcI~
	    Alltime=AjagoUtils.str2int(etAlltime.getText().toString(),0);//~1AfcR~
        if (Alltime<=0)                                            //~1AfcI~
        	Alltime=ALLTIME_DEFAULT;                               //~1AfcI~
        Global.setParameter(PREFKEY_PLAYMODE,Playmode);            //~1AfcI~
        Global.setParameter(PREFKEY_PLAYOUT,Playout);              //~1AfcI~
        Global.setParameter(PREFKEY_CONSTTIME,Consttime);          //~1AfcI~
        Global.setParameter(PREFKEY_ALLTIME,Alltime);              //~1AfcI~
                                                                   //~1AfcI~
        Pondering=cbPondering.isChecked();                         //~1AfcR~
        Global.setParameter(PREFKEY_PONDERING,Pondering);          //~1AfcI~
//        strTimesettings=getTimeSettingsParm();                   //~1AfcR~
                                                                   //~1AfcI~
        Thread=AjagoUtils.str2int(etThread.getText().toString(),0);//~1AfcR~
        if (Thread>MAX_THREAD)                                     //~1AfcI~
        	Thread=MAX_THREAD;                                     //~1AfcI~
        Global.setParameter(PREFKEY_THREAD,Thread);                //~1AfcI~
                                                                   //~1AfcI~
	    if (!AG.isDebuggable)                                      //~1AfcI~
        	Verbose=false;                                         //~1AfcI~
        else                                                       //~1AfcI~
        {                                                          //~1AfcI~
        	Verbose=cbVerbose.isChecked();                         //~1AfcI~
          	Global.setParameter(PREFKEY_VERBOSE,Verbose);          //~1AfcI~
        }                                                          //~1AfcI~
        return ""+getPgmOptionString();                            //~1AfcI~
	}//getPgmOption                                                //~1AfcR~
	protected String getPgmOptionString()                          //~1AfcI~
    {                                                              //~1AfcI~
        String option="";                                          //~1AfcI~
    //************************                                     //~1AfcI~
        option+=OPT_BOARDSIZE+BoardSize;                           //~1AfcI~
        if (Handicap!=0)                                           //~1AfcI~
        	option+=OPT_HANDICAP+Handicap;                         //~1AfcI~
        if (Komi!=0)                                               //~1AfcI~
        	option+=OPT_KOMI+Komi+".5";                            //~1AfcR~
        if (Playmode==PLAYMODE_CONSTTIME)                          //~1AfcI~
        	option+=OPT_CONSTTIME+Consttime;                       //~1AfcI~
        else                                                       //~1AfcI~
        if (Playmode==PLAYMODE_ALLTIME)                            //~1AfcI~
        	option+=OPT_ALLTIME+Alltime*60;    //min-->second      //~1AfcR~
        else                                                       //~1AfcI~
        	option+=OPT_PLAYOUT+Playout;                           //~1AfcI~
        if (Pondering)                                             //~1AfcI~
        	option+=OPT_PONDERING;                                 //~1AfcI~
        if (Thread!=0)                                             //~1AfcI~
        	option+=OPT_THREAD+Thread;                             //~1AfcI~
	    if (AG.isDebuggable)                                       //~1AfcI~
        {                                                          //~1AfcI~
        	Verbose=cbVerbose.isChecked();                         //~1AfcI~
        	if (!Verbose)                                          //~1AfcI~
				option+=OPT_NODEBUG;                               //~1AfcI~
        }                                                          //~1AfcI~
        return option;                                             //~1AfcI~
	}//getPgmOptionString                                          //~1AfcI~
//***************************************************************************//~v1B6I~
	@Override                                                      //~v1C1R~
    protected void initGtpCommand(GoColor Pgc)                                //~v1B6I~//~v1C1R~
    {                                                              //~v1B6I~//~v1C1R~
//        if (Rules==RULE_JAPANESE)//default is chinese            //~1AfcR~
//            C.gogui.sendGtpString(FUEGO_RULE_JAPANESE);          //~1AfcR~
        if (Pgc!=null)                                              //~v1B6I~//~v1C1R~
            C.gogui.generateMove(false/*singleMove*/,Pgc/*human color*/);//~v1B6R~//~v1C1R~
    }//initGtpCommand                                              //~v1C2R~
//******************                                               //~v1B6I~
    protected String editVersion(String Pversion)                  //~v1C1I~
    {                                                              //~v1C1I~
    	if (Pversion!=null && !Pversion.equals(""))                //~1AfcI~
        	return Pversion;                                        //~1AfcI~
    	String version=RAY_VERSION;                                //~1AfcR~
        return version;                                            //~v1C1I~
    }                                                              //~v1C1I~
	
	
//*********                                                        //~v1B6I~
    @Override                                                      //~v1B6I~//~1AfcR~
    protected void setupDialogExtend(ViewGroup PlayoutView)        //~v1B6I~//~1AfcR~
    {                                                              //~v1B6I~//~1AfcR~
	    Program=(EditText)PlayoutView.findViewById(R.id.GTP_COMMAND);//~1AfcI~
        YourNameField=(EditText)PlayoutView.findViewById(R.id.GTP_YOURNAME);//~v1B6I~//~1AfcI~
        rgBoardsize=(RadioGroup)PlayoutView.findViewById(R.id.GTPRAY_BOARDSIZE);//~1AfcI~
        HandicapField=(EditText)PlayoutView.findViewById(R.id.GTP_HANDICAP);//~v1B6R~//~1AfcR~
        KomiField=(EditText)layoutView.findViewById(R.id.GTP_KOMI);//~v1B6I~//~1AfcI~
        White=(CheckBox)PlayoutView.findViewById(R.id.GTP_WHITE);  //~1AfcI~
        initRGPlaymode(PlayoutView);                               //~1AfcR~
        etPlayout=(EditText)PlayoutView.findViewById(R.id.GTPRAY_PLAYOUT);//~v1B6I~//~1AfcR~
        etConsttime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_CONSTTIME);//~1AfcI~
        etAlltime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_ALLTIME);//~1AfcI~
        cbPondering=(CheckBox)PlayoutView.findViewById(R.id.GTPRAY_PONDERING);//~1AfcI~
        etThread=(EditText)PlayoutView.findViewById(R.id.GTPRAY_THREAD);//~1AfcI~
	    tvOption=(TextView)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION_DISPLAY);//~1AfcI~
        cbVerbose=(CheckBox)PlayoutView.findViewById(R.id.GTP_VERBOSE);//~1AfcI~
//        etTimeSettingMain=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTING);//~1AfcR~
//        etTimeSettingExtra=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRA);//~1AfcR~
//        etTimeSettingMove=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRAMOVE);//~1AfcR~
                                                                   //~1AfcI~
    	String gtpserver=getCommand();                             //~1AfcI~
        YourName=Global.getParameter(prefkey_YOURNAME,DEFAULT_YOURNAME);//~v1B6I~//~1AfcR~
        BoardSize=Global.getParameter(PREFKEY_BOARDSIZE,DEFAULT_BOARDSIZE);//~v1B6R~//~1AfcR~
        Handicap=Global.getParameter(PREFKEY_HANDICAP,DEFAULT_HANDICAP);//~v1B6R~//~1AfcR~
        Komi=Global.getParameter(PREFKEY_KOMI,DEFAULT_KOMI);       //~v1B6R~//~1AfcR~
    	MyColor=Global.getParameter(PREFKEY_WHITE,DEFAULT_WHITE);  //~1AfcI~
        Playmode=Global.getParameter(PREFKEY_PLAYMODE,PLAYMODE_DEFAULT);      //~v1B6R~//~1AfcR~
        Playout=Global.getParameter(PREFKEY_PLAYOUT,0);            //~1AfcR~
        Consttime=Global.getParameter(PREFKEY_CONSTTIME,0);        //~1AfcR~
        Alltime=Global.getParameter(PREFKEY_ALLTIME,0);            //~1AfcR~
        if (Playout<=0)                                            //~1AfcI~
        	Playout=PLAYOUT_DEFAULT;                               //~1AfcI~
        if (Consttime<=0)                                          //~1AfcI~
        	Consttime=CONSTTIME_DEFAULT;                           //~1AfcI~
        if (Alltime<=0)                                            //~1AfcI~
        	Alltime=ALLTIME_DEFAULT;                               //~1AfcI~
        Pondering=Global.getParameter(PREFKEY_PONDERING,false);    //~1AfcI~
        Thread=Global.getParameter(PREFKEY_THREAD,0);              //~1AfcI~
    	Verbose=Global.getParameter(PREFKEY_VERBOSE,false);        //~1AfcI~
                                                                   //~1AfcI~
		Program.setText(gtpserver);                                //~1AfcI~
        YourNameField.setText(YourName);                           //~v1B6I~//~1AfcI~
        if (BoardSize==BOARDSIZE_MIN)                              //~1AfcI~
	        rgBoardsize.check(R.id.GTPRAY_BOARDSIZE_9);            //~1AfcR~
        else                                                       //~1AfcI~
        if (BoardSize==BOARDSIZE_MID)                              //~1AfcI~
	        rgBoardsize.check(R.id.GTPRAY_BOARDSIZE_13);           //~1AfcR~
        else                                                       //~1AfcI~
	        rgBoardsize.check(R.id.GTPRAY_BOARDSIZE_19);           //~1AfcR~
                                                                   //~1AfcI~
        HandicapField.setText(Integer.toString(Handicap));         //~v1B6R~//~1AfcI~
        String strKomi;                                            //~1AfcI~
        if (Komi==0)                                               //~1AfcI~
        	strKomi="0";                                           //~1AfcI~
        else                                                       //~1AfcI~
        	strKomi=Integer.toString(Komi)+".5";                   //~1AfcI~
        KomiField.setText(strKomi);                                //~1AfcR~
		White.setChecked(MyColor!=GTPConnector.BLACK);             //~1AfcI~
        rgPlaymode.setChecked(Playmode);                           //~1AfcR~
        etPlayout.setText(Integer.toString(Playout));              //~1AfcR~
        etConsttime.setText(Integer.toString(Consttime));          //~1AfcR~
        etAlltime.setText(Integer.toString(Alltime));              //~1AfcR~
        if (Thread>0)                                                //~1AfcI~
	        etThread.setText(Integer.toString(Thread));            //~1AfcI~
        cbPondering.setChecked(Pondering);                         //~1AfcI~
                                                                   //~1AfcI~
//        setTimeSettingsField();                                  //~1AfcR~
                                                                   //~1AfcI~
//  	String options=getPgmOptionString();                       //~1AfcR~
//		tvOption.setText(options);                                 //~1AfcR~
		cbVerbose.setChecked(Verbose);                             //~1AfcI~
                                                                   //~1AfcI~
                                                                   //~1AfcI~
	    btnShowOption=(Button)PlayoutView.findViewById(R.id.GTPRAY_SHOWOPTION);//~1AfcI~
	    llDebug=(LinearLayout)PlayoutView.findViewById(R.id.GTPRAY_DEBUGOPTION);//~1AfcI~
                                                                   //~1AfcI~
	    if (!AG.isDebuggable)                                      //~1AfcI~
        	llDebug.setVisibility(View.GONE);                      //~1AfcR~
        else                                                       //~1AfcI~
	        setButtonListener(btnShowOption);                      //~1AfcI~
                                                                   //~1Ag5I~
		initcbPIE(PlayoutView);                                    //~1Ag5I~
    }//setupDialogExtend                                           //~v1B6R~//~1AfcR~
    //****************************************                     //~v1B6I~
    //*Button                                                      //~v1B6I~
    //****************************************                     //~v1B6I~
      @Override                                                      //~v1B6I~//~v1C1R~
      protected boolean onClickHelp()                                //~v1B6I~//~v1C1R~
      {                                                              //~v1B6I~//~v1C1R~
//        new HelpDialog(Global.frame(),"fuego");                  //~1AfcR~
          new HelpDialog(Global.frame(),"ray");                    //~1AfcI~
          return false;   //no dismiss                               //~v1B6I~//~v1C1R~
      }                                                              //~v1B6I~//~v1C1R~
//******************                                               //~1AfcI~
	@Override                                                      //~1AfcI~
    protected boolean onClickOther(int PbuttonId)                  //~1AfcI~
    {                                                              //~1AfcI~
    	boolean rc=false;   //not dismiss at return                //~1AfcI~
    //****************                                             //~1AfcI~
        if (Dump.Y) Dump.println("GTPRayConnection onClickOther buttonid="+Integer.toHexString(PbuttonId));//~1AfcI~
        switch(PbuttonId)                                          //~1AfcI~
        {                                                          //~1AfcI~
        case R.id.GTPRAY_SHOWOPTION:                               //~1AfcI~
			String options=getPgmOption();                         //~1AfcI~
			tvOption.setText(options);                             //~1AfcR~
            break;                                                 //~1AfcI~
        case R.id.GTP_PLAY:                                        //~1AfcI~
	       	rc=play();                                             //~1AfcR~
            break;                                                 //~1AfcI~
        }                                                          //~1AfcI~
        return rc;                                                 //~1AfcI~
    }                                                              //~1AfcI~
	protected void initRGPlaymode(ViewGroup PlayoutView)             //~1AfcR~
    {                                                              //~1AfcI~
		rgPlaymode=new ButtonRG(PlayoutView,3);                    //~1AfcI~
        rgPlaymode.add(PLAYMODE_PLAYOUT,R.id.GTPRAY_PLAYMODE_PLAYOUT);//~1AfcI~
        rgPlaymode.add(PLAYMODE_CONSTTIME,R.id.GTPRAY_PLAYMODE_CONSTTIME);//~1AfcI~
        rgPlaymode.add(PLAYMODE_ALLTIME,R.id.GTPRAY_PLAYMODE_ALLTIME);//~1AfcI~
//      rgPlaymode.setDefault(PLAYMODE_PLAYOUT);                   //~1AfcI~//~1Aj1R~
        rgPlaymode.setDefault(PLAYMODE_DEFAULT);                   //+1Aj1R~
    }                                                              //~1AfcI~
}
