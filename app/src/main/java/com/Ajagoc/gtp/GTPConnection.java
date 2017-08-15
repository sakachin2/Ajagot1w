//*CID://+1Aj1R~:                                   update#=  245; //+1Aj1R~
//***********************************************************************//~@@@1I~
//1Aj1 2017/02/04 reduce ray/pachi plaout default 10000-->1000 and default playmode:time per each mode//+1Aj1I~
//1Agb 2016/10/10 AxeDialog is 2 two place,com.axe and com.Ajagoc.axe, del later.//~1AgbI~
//1Ag5 2016/10/09 checkbox pie,set on as default when version>=5   //~1Ag5I~
//1Ag0 2014/10/05 displaying bot version number on menu delays until next restart(menu itemname is set at start and not changed)//~1Ag0I~
//1Afu 2014/10/04 android5 allows only PIE for ndk binary          //~1AfuI~
//1Aft 2014/10/04 reinstall bookfile was deleted                   //~1AftI~
//1Afs 2014/10/04 Asset file open err (getAsset().openFD:it is probably compressed) for Agnugo when size over 64k//~1AftR~
//                reset v1C8(back zip to png)                      //~1AfsI~
//1Afr 2016/10/02 add gnugo gtp mode                               //~1AfrI~
//1Afo 2016/09/28 pachi dialog change                              //~1AfoI~
//1Afj 2016/09/25 replace Agnugo/Apachi armeabi-v7a(hardware floating point calc)//~1AfjI~
//1Afh 2016/09/24 drop yourname from title of computer mach        //~1AfcI~
//1Afc 2016/09/22 like V1C1 fuego, add ray as player               //~1AfcI~
//v1Dh 2014/11/12 add predefined parameter settion for pach/fuego match-settiong dialog//~v1DhI~
//v1D3 2014/10/07 set timestamp to filename on filedialog when save(current is *.sgf)//~v1D3I~
//v1C8 2014/09/01 Agnugo.png-->Agnugo.zip(unzip at first time)     //~v1C8I~
//v1C2 2014/08/23 pachi hepl filename not found exception          //~v1C2I~
//v1C1 2014/08/21 install fuego as GTP client                      //~v1C1I~
//v1C0 2014/08/15 pachi:-s(seed) parameter for more randamizing    //~v1C0I~
//v1Bb 2014/08/14 -f book.dat support for pachi                    //~v1BbI~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//@@@1 Modal dialog                                                //~@@@1I~//~v106R~
//***********************************************************************//~@@@1I~
package com.Ajagoc.gtp;                                            //~v1B6R~

//import java.util.*;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import android.widget.LinearLayout;
import android.widget.TextView;





import com.Ajagoc.AjagoMenu;
//import com.Ajagoc.AjagoUiThread;                                   //~v106I~
//import com.Ajagoc.AjagoGMP;                                      //~v1C8R~
import com.Ajagoc.AjagoProp;
import com.Ajagoc.AjagoUtils;                                      //~v106I~
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;
import com.Ajagoc.awt.ButtonRG;
//import com.Ajagoc.awt.BorderLayout;
//import com.Ajagoc.awt.Checkbox;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.Menu;
//import com.Ajagoc.awt.GridLayout;
//import com.Ajagoc.awt.Panel;
//import com.Ajagoc.awt.TextField;
import com.Ajagoc.java.File;                                       //~1516I~
import com.Ajagoc.AjagoAlert;                                    //~v1B6I~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;






//import jagoclient.gui.*;
                                                                   //~v1B6I~
//import com.Ajagoc.axe.AxeDialog;                                 //~1AgbR~
import com.axe.AxeDialog;                                          //~1AgbI~
import com.Ajagoc.AG;                                              //~v1B6I~

//Pachi version 10.00 (Satsugen)  cmdline option                   //~1AfjI~
//Usage: w:\pachiwbin\pachi-10.00-win32\pachi [-e random|replay|montecarlo|uct|distributed]//~1AfjI~
// [-d DEBUG_LEVEL] [-D] [-r RULESET] [-s RANDOM_SEED] [-t TIME_SETTINGS] [-u TEST_FILENAME]//~1AfjI~
// [-g [HOST:]GTP_PORT] [-l [HOST:]LOG_PORT] [-f FBOOKFILE] [ENGINE_ARGS]//~1AfjI~
//** list_commands response **********************                 //~1AfjR~
// protocol_version                                                //~1AfjI~
// echo                                                            //~1AfjI~
// name                                                            //~1AfjI~
// version                                                         //~1AfjI~
// list_commands                                                   //~1AfjI~
// known_command                                                   //~1AfjI~
// quit                                                            //~1AfjI~
// boardsize                                                       //~1AfjI~
// clear_board                                                     //~1AfjI~
// kgs-game_over                                                   //~1AfjI~
// komi                                                            //~1AfjI~
// kgs-rules                                                       //~1AfjI~
// play                                                            //~1AfjI~
// genmove                                                         //~1AfjI~
// kgs-genmove_cleanup                                             //~1AfjI~
// set_free_handicap                                               //~1AfjI~
// place_free_handicap                                             //~1AfjI~
// fixed_handicap                                                  //~1AfjI~
// final_score                                                     //~1AfjI~
// final_status_list                                               //~1AfjI~
// undo                                                            //~1AfjI~
// pachi-evaluate                                                  //~1AfjI~
// pachi-result                                                    //~1AfjI~
// pachi-gentbook                                                  //~1AfjI~
// pachi-dumptbook                                                 //~1AfjI~
// kgs-chat                                                        //~1AfjI~
// time_left                                                       //~1AfjI~
// time_settings                                                   //~1AfjI~
// kgs-time_settings                                               //~1AfjI~
//class GTPWait extends CloseDialog                                //~v1B6R~
//{   public GTPWait (GTPConnection f)                             //~v1B6R~
//    {   super(f,Global.resourceString("Play_Go"),true);          //~v1B6R~
//        setLayout(new BorderLayout());                           //~v1B6R~
//        add("Center",new MyLabel(Global.resourceString("Negotiating_with_Program")));//~v1B6R~
//        Panel p=new MyPanel();                                   //~v1B6R~
//        p.add(new ButtonAction(this,Global.resourceString("Abort")));//~v1B6R~
//        add("South",p);                                          //~v1B6R~
//        Global.setpacked(this,"gmpwait",300,150,f);              //~v1B6R~
//        show();                                                  //~v1B6R~
//        F=f;    //@@@@                                           //~v1B6R~
//    }                                                            //~v1B6R~
//    GTPConnection F; //@@@@                                      //~v1B6R~
//    public void doAction (String o)                              //~v1B6R~
//    {   setVisible(false); dispose();                            //~v1B6R~
//        if (Dump.Y) Dump.println("GTPConnection:doAction="+(o==null?"null":o));//~1513I~//~v1B6R~
//        F.setOk(null);        //@@@@Add  Mainthread will not wait modal dialog;clear at Abort button pused//~1512R~//~v1B6R~
//    }                                                            //~v1B6R~
//}                                                                //~v1B6R~

class OkAdapter
{	public void gotOk() {}
}

//public class GTPConnection extends CloseFrame                    //~v1B6R~
public class GTPConnection extends AxeDialog                       //~v1B6I~
	implements GTPInterface
{                                                                  //~v1B6R~
//  private static final int LAYOUT=R.layout.dialoggtpconnection;  //~v1B6I~//~v1C1R~
            static final int LAYOUT=R.layout.dialoggtpconnection;  //~v1C1I~
//  private static final int TITLE =R.string.DialogTitle_gtpconnection;//~v1B6I~//~v1C1R~
            static final int TITLE =R.string.DialogTitle_gtpconnection;//~v1C1I~
//  private static final int HELP_TITLE=R.string.HelpTitle_gtpconnection;//~v1B6R~
//  private static final int HELP_TEXT=R.string.HelpText_gtpconnection;//~v1B6R~
	private static final String DEFAULT_GTPSERVER="Apachi";        //~v1B6R~
//  private static final String DEFAULT_GTPSERVER_ORGNAME="pachi"; //~1Ag0R~
    public  static final String DEFAULT_GTPSERVER_ORGNAME="pachi"; //~1Ag0I~
//  private static final String DEFAULT_BOOK_ORGNAME="book.dat";   //~v1BbI~//~v1C1R~
                   final String DEFAULT_BOOK_ORGNAME="book.dat";   //~v1C1I~
    private static final String DEFAULT_BOOK_ORGNAME2="bookra6.dat";//~v1C1R~
//  private static final String DEFAULT_BOOK="Abook.dat";          //~v1BbI~//~v1C1R~
            static final String DEFAULT_BOOK="Abook.dat";          //~v1C1R~
            static final String DEFAULT_BOOK2="Abookra6.dat";      //~v1C1I~
//  private static final String DEFAULT_BOOK_ZIPFILE="Abook.ra6.zip";//~v1BbI~//~v1C1R~
//  private static final String DEFAULT_BOOK_ZIPFILE="Abook.zip";  //2 file in,book.dat and book_ra6_dat//~1AfsR~
    private static final String DEFAULT_BOOK_ZIPFILE="Abook.png";  //2 file in,book.dat and book_ra6_dat//~1AfsI~
//  private static final int DEFAULT_WHITE=GTPConnector.BLACK; //your are BLACK//~v1B6R~//~v1C1R~
            static final int DEFAULT_WHITE=GTPConnector.BLACK; //your are BLACK//~v1C1I~
//  private static final int DEFAULT_BOARDSIZE=19;                 //~v1B6I~//~v1C1R~
            static final int DEFAULT_BOARDSIZE=19;                 //~v1C1I~
//  private static final String DEFAULT_YOURNAME="You";               //~v1B6I~//~v1C1R~
            static final String DEFAULT_YOURNAME="You";            //~v1C1I~
//  private static final String DEFAULT_TIMESETTINGMOVE="1";       //~v1B6I~//~v1C1R~
            static final String DEFAULT_TIMESETTINGMOVE="1";       //~v1C1I~
//  private static final int DEFAULT_HANDICAP=0;                   //~v1B6R~//~v1C1R~
            static final int DEFAULT_HANDICAP=0;                   //~v1C1I~
//  private static final int DEFAULT_KOMI=0;                       //~v1B6R~//~v1C1R~
            static final boolean DEFAULT_PONDERING=true;           //~1AfoR~
            static final int DEFAULT_KOMI=0;                       //~1AfoI~
	public  static final int RULE_JAPANESE=GTPConnector.JAPANESE;  //~v1B6R~
	public  static final int RULE_CHINESE=GTPConnector.SST;        //~v1B6R~
//  private static final int DEFAULT_RULE=RULE_JAPANESE;           //~v1B6I~//~v1C1R~
            static final int DEFAULT_RULE=RULE_JAPANESE;           //~v1C1I~
	private static final String PACHI_RULE_JAPANESE=" -r japanese"; //pachi commandline parameter//~1AfoR~
//  private static final int MAX_HANDICAP=9;                       //~v1B6I~//~v1C1R~
            static final int MAX_HANDICAP=9;                       //~v1C1I~
            static final String POSTFIX_PIE=".pie";	//pie executable posfix//~1AfuI~
                                                                   //~1Ag0I~
    public  static final int GTPID_GNUGO  =1;                      //~1Ag0I~
    public  static final int GTPID_PACHI  =2;                      //~1Ag0R~
    public  static final int GTPID_FUEGO  =3;                      //~1Ag0R~
    public  static final int GTPID_RAY    =4;                      //~1Ag0R~
                                                                   //~1Ag0I~
	private static final String PREFKEY_PROGRAM="GTPprogram";      //~v1B6I~
	private static final String PREFKEY_PGMOPTION="GTPprogramoption";//~v1B6R~
	private static final String PREFKEY_BOARDSIZE="GTPboardsize";  //~v1B6I~
	private static final String PREFKEY_YOURNAME="GTPyourname";    //~v1B6I~
	private static final String PREFKEY_TIMESETTINGMAIN="GTPtimesettingMain";//~v1B6R~
	private static final String PREFKEY_TIMESETTINGEXTRA="GTPtimesettingExtra";//~v1B6I~
	private static final String PREFKEY_TIMESETTINGMOVE="GTPtimesettingMove";//~v1B6I~
	private static final String PREFKEY_HANDICAP="GTPhandicap";    //~v1B6I~
	private static final String PREFKEY_WHITE="GTPyouarewhite";    //~v1B6R~
	private static final String PREFKEY_RULE="GTPrule";            //~v1B6I~
	private static final String PREFKEY_KOMI="GTPkomi";            //~v1B6I~
	private static final String PREFKEY_VERBOSE="GTPverbose";      //~v1B6I~
	private static final String PREFKEY_BOOKFILE="GTPbookFile";    //~v1BbI~
	private static final String PREFKEY_BOOKUSE="GTPbookUse";      //~v1BbI~
	private static final String PREFKEY_BOOKFILE_ZIPSIZE="GTPbookZipSize";//~v1BbI~
    private static final String PREFKEY_GTPSERVER_ZIPSIZE="GTPserverZipSize";//~v1C1I~
    private static final String PREFKEY_GTPSERVER_VERSION="GTPserverVersion";//~1AfhI~
    private static final String PREFKEY_PLAYMODE="GTPplaymode";    //~1AfoR~
    private static final String PREFKEY_CONSTTIME="GTPconsttime";  //~1AfoI~
    private static final String PREFKEY_ALLTIME="GTPalltime";      //~1AfoI~
    private static final String PREFKEY_PLAYOUT="GTPplayout";      //~1AfoI~
    private static final String PREFKEY_THREAD="GTPThrea";         //~1AfoI~
    private static final String OPT_THREAD          ="threads=";   //~1AfoR~
    private static final String PREFKEY_PONDERING="GTPpondering";  //~1AfoI~
    private static final String OPT_PONDERING       ="pondering="; //~1AfoR~
    private static final String OPT_UCT             =" -e uct";    //~1AfoI~
    private static final String OPT_BOOK            =" -f ";       //~1AfoI~
    private static final String OPT_SEED            =" -s ";       //~1AfoI~
    private static final String OPT_NOBOARDPRINT    =" -D";        //~1AfoI~
    private static final String MIN_DEBUGLEVEL      =" -d 2";  //activate "UDBUGL(1 or 0) the fprintf"   2>1//~1AfoI~
                                                                   //~v1C1I~
	                     String default_GTPSERVER_ORGNAME;         //~v1C1I~
	                     String default_GTPSERVER="Apachi";        //~v1C1I~
	                     String prefkey_PROGRAM;                   //~v1C1I~
	                     String prefkey_TIMESETTINGMAIN;           //~v1C1I~
	                     String prefkey_TIMESETTINGEXTRA;          //~v1C1I~
	                     String prefkey_TIMESETTINGMOVE;           //~v1C1I~
	                     String prefkey_BOOKFILE;                  //~v1C1I~
	                     String prefkey_BOOKFILE_ZIPSIZE;          //~v1C1I~
	                     String prefkey_PGMOPTION;                 //~v1C1I~
	                     String prefkey_VERBOSE;                   //~v1C1I~
                         String prefkey_GTPSERVER_ZIPSIZE;         //~v1C1I~
                         String prefkey_HANDICAP;                  //~v1C1I~
                         String prefkey_BOARDSIZE;                 //~v1C1I~
                         String prefkey_YOURNAME;                  //~v1C1I~
                         String prefkey_KOMI;                      //~v1C1I~
                         String prefkey_BOOKUSE;                   //~v1C1I~
                         String prefkey_WHITE;                     //~v1C1I~
                         String prefkey_RULE;                      //~v1C1I~
                         String prefkey_GTPSERVER_VERSION;         //~1AfhI~
                         String prefkey_THREAD;                    //~1AfoI~
                         String prefkey_CONSTTIME;                 //~1AfoI~
                         String prefkey_ALLTIME;                   //~1AfoI~
                         String prefkey_PLAYOUT;                   //~1AfoI~
                         String prefkey_PLAYMODE;                  //~1AfoI~
                         String prefkey_PONDERING;                 //~1AfoI~
                                                                   //~v1BbI~
//  private static long installedProgramSize,installedBookSize;     //~v1BbI~//~v1C1R~
//          static long installedProgramSize,installedBookSize;    //~v1C1I~//~v1C8R~
            static long installedBookSize;  //share with fuego     //~v1C8I~
      public static String Sversion="";                            //~1AfhI~
	
//    TextField Program;                                           //~v1B6R~
//    IntField HandicapField,BoardSizeField;                       //~v1B6R~
//    Checkbox White;                                              //~v1B6R~
//  private EditText   Program;                                    //~v1B6I~//~v1C1R~
            EditText   Program;                                    //~v1C1I~
//  private EditText   HandicapField,BoardSizeField,KomiField,YourNameField,etOption;//~v1B6R~//~v1C1R~
            EditText   HandicapField,BoardSizeField,KomiField,YourNameField,etOption;//~v1C1I~
//  private EditText   etBook;                                     //~v1BbI~//~v1C1R~
            EditText   etBook;                                     //~v1C1I~
//  private EditText   etTimeSettingMain,etTimeSettingExtra,etTimeSettingMove;       //~v1B6R~//~v1C1R~
            EditText   etTimeSettingMain,etTimeSettingExtra,etTimeSettingMove;//~v1C1I~
            EditText   etPlayout,etConsttime,etAlltime,etThread;   //~1AfoI~
	    	TextView   tvOption;                                   //~1AfoI~
            Button btnShowOption;                                  //~1AfoI~
//  private CheckBox   White;                                      //~v1B6I~//~v1C1R~
            CheckBox   White;                                      //~v1C1I~
//  private CheckBox   cbVerbose;                                  //~v1B6I~//~v1C1R~
            CheckBox   cbVerbose;                                  //~v1C1I~
//  private CheckBox   cbBook;                                     //~v1BbI~//~v1C1R~
            CheckBox   cbBook;                                     //~v1C1I~
//  private RadioGroup rgRule;                                     //~v1B6R~//~v1C1R~
            CheckBox   cbRule;                                     //~1AfoR~
            CheckBox   cbPIE;                                      //~1Ag5I~
//  private String     YourName=DEFAULT_YOURNAME;                  //~v1B6R~//~v1C1R~
            String     YourName=DEFAULT_YOURNAME;                  //~v1C1I~
            ButtonRG rgPlaymode;                                   //~1AfoR~
                                                                   //~1AfoI~
            int Playmode;                                          //~1AfoR~
    protected static final int PLAYMODE_PLAYOUT=1;         //playout count per move//~1AfoI~
    protected static final int PLAYMODE_CONSTTIME=2;       //const time per move//~1AfoI~
    protected static final int PLAYMODE_ALLTIME=3;       //const time per move//~1AfoI~
    protected static final int PLAYMODE_TIMESETTING=4;       //const time per move//~1AfoR~
    private   static final int PLAYMODE_DEFAULT=PLAYMODE_CONSTTIME;//~1AfoI~
                                                                   //~1AfoI~
            int Playout,Consttime,Alltime,Thread;                  //~1AfoR~
//  private static final int PLAYOUT_DEFAULT=10000;                //~1AfoI~//+1Aj1R~
    private static final int PLAYOUT_DEFAULT=1000;                 //+1Aj1I~
    private static final int CONSTTIME_DEFAULT=15;                 //~1AfoI~
    private static final int ALLTIME_DEFAULT=90;                   //~1AfoI~
                                                                   //~1AfoI~
    public String strTimesettings="";                              //~v1B6R~
	public int Komi=DEFAULT_KOMI;                                  //~v1B6R~
	public int Handicap=DEFAULT_HANDICAP;                          //~v1B6I~
	public int BoardSize=DEFAULT_BOARDSIZE;                        //~v1B6I~
	public int Rules=DEFAULT_RULE;                                 //~v1B6I~
	public int MyColor=DEFAULT_WHITE;                              //~v1B6I~
	public GtpClient gtpclient;                                    //~v1B6I~
	public GuiGtpClient guigtpclient;                              //~v1B6I~
	public boolean Verbose=false;                                  //~v1B6R~
	public boolean swPassed;                                       //~v1B6I~
	public boolean useBook;                                        //~v1BbI~
	public int gameoverReason;                                     //~v1B6I~
            boolean Pondering;                                     //~1AfoR~
            CheckBox  cbPondering;                                 //~1AfoR~
//  private static final int GAMEOVER_RESIGN_COMPUTER=1;           //~v1B6I~//~v1C1R~
            static final int GAMEOVER_RESIGN_COMPUTER=1;           //~v1C1I~
//  private static final int GAMEOVER_RESIGN_HUMAN=2;              //~v1B6I~//~v1C1R~
            static final int GAMEOVER_RESIGN_HUMAN=2;              //~v1C1I~
//  private static final int GAMEOVER_BOTH_PASSED=3;               //~v1B6I~//~v1C1R~
            static final int GAMEOVER_BOTH_PASSED=3;               //~v1C1I~
    private Button btnOptionChange;                                //~v1DhI~
            String cmdOption="";                                   //~1AfoR~
            boolean swShowOption;                                  //~1AfoI~
            String showOption="";                                  //~1AfoI~
	private static Menu Smenu;                                            //~1Ag0I~

	public GTPConnection (Frame f)
    {                                                              //~1AfcI~
		this(f,LAYOUT);                                            //~1AfcI~
    }                                                              //~1AfcI~
	public GTPConnection (Frame f,int Playoutid)                   //~1AfcI~
//  {	super(Global.resourceString("Play_Go"));                   //~v1B6R~
    {                                                              //~v1B6I~
//      super(LAYOUT);                                             //~1AfcR~
        super(Playoutid);                                          //~1AfcI~
//        setLayout(new BorderLayout());                           //~v1B6R~
//        Panel center=new MyPanel();                              //~v1B6R~
//        center.setLayout(new GridLayout(0,2));                   //~v1B6R~
//        center.add(new MyLabel(Global.resourceString("Go_Protocol_Server")));//~v1B6R~
//        center.add(Program=new TextFieldAction(this,"",          //~v1B6R~
//            Global.getParameter("gmpserver","gnugo.exe"),16));   //~v1B6R~
//        center.add(new MyLabel(Global.resourceString("Board_size")));//~v1B6R~
//        center.add(BoardSizeField=new IntField(this,"BoardSize", //~v1B6R~
//            Global.getParameter("gmpboardsize",19)));            //~v1B6R~
//        center.add(new MyLabel(Global.resourceString("Handicap")));//~v1B6R~
//        center.add(HandicapField=new IntField(this,"Handicap",   //~v1B6R~
//            Global.getParameter("gmphandicap",9)));              //~v1B6R~
//        center.add(new MyLabel(Global.resourceString("Play_White")));//~v1B6R~
//        center.add(White=new CheckboxAction(this,""));           //~v1B6R~
//        White.setState(Global.getParameter("gmpwhite",true));    //~v1B6R~
//        add("Center",new Panel3D(center));                       //~v1B6R~
//        Panel south=new MyPanel();                               //~v1B6R~
//        south.add(new ButtonAction(this,Global.resourceString("Play")));//~v1B6R~
//        add("South",new Panel3D(south));                         //~v1B6R~
//        Global.setpacked(this,"gmpconnection",300,150,f);        //~v1B6R~
//        seticon("iboard.gif");                                   //~v1B6R~
//        setVisible(true);                                        //~v1B6R~
        initVariable();                                            //~v1C1I~
        String title=AG.resource.getString(TITLE);                 //~v1B6R~
		showDialog(title);     //callback setupDialogExtend()      //~v1B6I~
	}
//*********                                                        //~1725I~//~v1B6I~
    void initVariable()                                            //~v1C1I~
    {                                                              //~v1C1I~
	    default_GTPSERVER_ORGNAME=DEFAULT_GTPSERVER_ORGNAME;       //~v1C1I~
	    default_GTPSERVER=DEFAULT_GTPSERVER;                       //~v1C1I~
        prefkey_PROGRAM=PREFKEY_PROGRAM;                           //~v1C1I~
        prefkey_TIMESETTINGMAIN=PREFKEY_TIMESETTINGMAIN;           //~v1C1I~
        prefkey_TIMESETTINGEXTRA=PREFKEY_TIMESETTINGEXTRA;         //~v1C1I~
        prefkey_TIMESETTINGMOVE=PREFKEY_TIMESETTINGMOVE;           //~v1C1I~
        prefkey_BOOKFILE=PREFKEY_BOOKFILE;                         //~v1C1I~
        prefkey_BOOKFILE_ZIPSIZE=PREFKEY_BOOKFILE_ZIPSIZE;         //~v1C1I~
	    prefkey_PGMOPTION=PREFKEY_PGMOPTION;                       //~v1C1I~
	    prefkey_VERBOSE=PREFKEY_VERBOSE;                           //~v1C1I~
        prefkey_GTPSERVER_ZIPSIZE=PREFKEY_GTPSERVER_ZIPSIZE;       //~v1C1I~
        prefkey_HANDICAP=PREFKEY_HANDICAP;                         //~v1C1I~
        prefkey_BOARDSIZE=PREFKEY_BOARDSIZE;                       //~v1C1I~
        prefkey_YOURNAME=PREFKEY_YOURNAME;                         //~v1C1I~
        prefkey_KOMI=PREFKEY_KOMI;                                 //~v1C1I~
        prefkey_BOOKUSE=PREFKEY_BOOKUSE;                           //~v1C1I~
        prefkey_WHITE=PREFKEY_WHITE;                               //~v1C1I~
        prefkey_RULE=PREFKEY_RULE;                                 //~v1C1I~
        prefkey_GTPSERVER_VERSION=PREFKEY_GTPSERVER_VERSION;       //~1AfhI~
        prefkey_PLAYMODE=PREFKEY_PLAYMODE;                         //~1AfoR~
        prefkey_CONSTTIME=PREFKEY_CONSTTIME;                       //~1AfoI~
        prefkey_ALLTIME=PREFKEY_ALLTIME;                           //~1AfoI~
        prefkey_PLAYOUT=PREFKEY_PLAYOUT;                           //~1AfoI~
        prefkey_THREAD=PREFKEY_THREAD;                             //~1AfoI~
        prefkey_PONDERING=PREFKEY_PONDERING;                       //~1AfoI~
                                                                   //~1AfhI~
        Sversion=Global.getParameter(PREFKEY_GTPSERVER_VERSION,"");//~1AfhI~
    }                                                              //~v1C1I~
//**************************************************************   //~v1B6I~
	GTPConnector C;
	GTPGoFrame F;
	GTPConnection Co=this;
//**************************************************************   //~v1B6I~
//    public void doAction (String o)                              //~v1B6R~
//    {   if (o.equals(Global.resourceString("Play")))             //~v1B6R~
//        {   String text=Program.getText();                       //~v1B6R~
//            StringTokenizer t;                                   //~v1B6R~
//            if (text.startsWith("\""))                           //~v1B6R~
//                t=new StringTokenizer(Program.getText(),"\"");   //~v1B6R~
//            else                                                 //~v1B6R~
//                t=new StringTokenizer(Program.getText()," ");    //~v1B6R~
//            if (!t.hasMoreTokens()) return;                      //~v1B6R~
//            String s=t.nextToken();                              //~v1B6R~
//            File f=new File(s);                                  //~v1B6R~
//            if (!f.exists())                                     //~v1B6R~
//            {   rene.dialogs.Warning w=                          //~v1B6R~
//                new rene.dialogs.Warning(this,                   //~v1B6R~
//                    "Program not found!","Warning",true);        //~v1B6R~
//                w.center(this);                                  //~v1B6R~
//                w.setVisible(true);                              //~v1B6R~
//                return;                                          //~v1B6R~
//            }                                                    //~v1B6R~
//            C=new GTPConnector(Program.getText());               //~v1B6R~
//            Global.setParameter("gmpserver",Program.getText());  //~v1B6R~
//            C.setGTPInterface(this);                             //~v1B6R~
//            Handicap=HandicapField.value(0,9);                   //~v1B6R~
//            Global.setParameter("gmphandicap",Handicap);         //~v1B6R~
//            if (Handicap==0) Handicap=1;                         //~v1B6R~
//            BoardSize=BoardSizeField.value(5,19);                //~v1B6R~
//            Global.setParameter("gmpboardsize",BoardSize);       //~v1B6R~
//            MyColor=White.getState()?GTPConnector.WHITE:GTPConnector.BLACK;//~v1B6R~
//            Global.setParameter("gmpwhite",White.getState());    //~v1B6R~
//            try                                                  //~v1B6R~
//            {   setOk(new OkAdapter ()                           //~v1B6R~
//                    {   public void gotOk ()                     //~v1B6R~
////                      {   F=new GTPGoFrame(Co,BoardSize,         //~v106R~//~v1B6R~
//                        {                                          //~v106I~//~v1B6R~
//                            AjagoUiThread.wait(AjagoUiThread.LID_GTP);  //100ms wait GTPwait dialog setup to avoid AG.currentLayout is replaced//~v106I~//~v1B6R~
//                            F=new GTPGoFrame(Co,BoardSize,         //~v106I~//~v1B6R~
//                                White.getState()?1:-1);          //~v1B6R~
//                            if (Dump.Y) Dump.println("GTPConnection:after new GTPGoFrame");//~1511I~//~v1B6R~
//                            F.setVisible(true);                  //~v1B6R~
//                            Co.setVisible(false); Co.dispose();  //~v1B6R~
//                            if (Handicap>1) handicap(Handicap);  //~v1B6R~
//                        }                                        //~v1B6R~
//                    }                                            //~v1B6R~
//                );                                               //~v1B6R~
//                AjagoUiThread.initlatch(AjagoUiThread.LID_GTP); //wait GTPwait dialog setup to avoid AG.currentLayout is replaced//~v106I~//~v1B6R~
//                C.connect();                                     //~v1B6R~
//                new GTPWait(this);                               //~v1B6R~
//                AjagoUiThread.post(AjagoUiThread.LID_GTP);  //wait GTPwait dialog setup to avoid AG.currentLayout is replaced//~v106I~//~v1B6R~
////              Ok=null;        //@@@@Del Mainthread will not wait modal dialog;clear at Abort button pused//~1512R~//~v1B6R~
//            }                                                    //~v1B6R~
//            catch (Exception e)                                  //~v1B6R~
//            {   rene.dialogs.Warning w=                          //~v1B6R~
//                new rene.dialogs.Warning(this,                   //~v1B6R~
//                    "Error : "+e.toString(),"Warning",true);     //~v1B6R~
//                Dump.println(e,"Play Exception");                   //~1511I~//~v1B6R~
//                w.center(this);                                  //~v1B6R~
//                w.setVisible(true);                              //~v1B6R~
//            }                                                    //~v1B6R~
//        }                                                        //~v1B6R~
//    }                                                            //~v1B6R~
//***************************************************************************//~v1B6R~
//  private boolean play()                                         //~v1B6R~//~v1C1R~
            boolean play()                                         //~v1C1I~
    {                                                              //~v1B6I~
    	String enginarg="";                                        //~1AfoR~
    	String text=getCommandParm();                              //~1AfoI~
        File f=new File(text);                                        //~v1B6I~
        if (!f.exists())                                           //~v1B6I~
        {                                                          //~v1B6R~
//            rene.dialogs.Warning w=                              //~v1B6I~
//            new rene.dialogs.Warning(this,                       //~v1B6R~
//                "Program not found!","Warning",true);            //~v1B6R~
//            w.center(this);                                      //~v1B6R~
//            w.setVisible(true);                                  //~v1B6R~
	    	int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;       //~1604I~//~v1B6I~
        	AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1B6R~
        			AG.resource.getString(R.string.GtpErr_Pgm_NotFound)+":\n"+text,flag);//~v1B6R~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6I~
//        String option=etOption.getText().toString();               //~v1B6M~//~v1C1R~
//        option=option.trim();                                      //~v1B6M~//~v1C1R~
//        Global.setParameter(PREFKEY_PGMOPTION,option);             //~v1B6M~//~v1C1R~
                                                                   //~1AfoI~
        String yn=YourNameField.getText().toString();              //~1AfoM~
        if (yn!=null && !yn.equals(""))                            //~1AfoM~
        {                                                          //~1AfoM~
        	YourName=yn;                                           //~1AfoM~
//      	Global.setParameter(PREFKEY_YOURNAME,YourName);        //~v1B6R~//~1AfoM~
        	Global.setParameter(prefkey_YOURNAME,YourName);        //~1AfoM~
        }                                                          //~1AfoM~
                                                                   //~1AfoI~
//      BoardSize=BoardSizeField.value(5,19);                      //~1AfoM~
        BoardSize=AjagoUtils.str2int(BoardSizeField.getText().toString(),DEFAULT_BOARDSIZE);//~1AfoM~
        Global.setParameter(prefkey_BOARDSIZE,BoardSize);          //~v1B6R~//~1AfoM~
                                                                   //~1AfoI~
//      Handicap=HandicapField.value(0,9);                         //~1AfoM~
        Handicap=AjagoUtils.str2int(HandicapField.getText().toString(),DEFAULT_HANDICAP);//~1AfoM~
        if (Handicap>MAX_HANDICAP)                                 //~1AfoM~
        	Handicap=MAX_HANDICAP;                                 //~1AfoM~
        Global.setParameter(prefkey_HANDICAP,Handicap);            //~v1B6R~//~1AfoM~
        if (Handicap==0) Handicap=1;                               //~1AfoM~
                                                                   //~1AfoI~
        String strKomi=KomiField.getText().toString();             //~1AfoM~
        int pos=strKomi.indexOf(".");                              //~1AfoM~
        if (pos>0)                                                 //~1AfoM~
        	strKomi=strKomi.substring(0,pos);                      //~1AfoM~
        Komi=AjagoUtils.str2int(strKomi,DEFAULT_KOMI);             //~1AfoM~
//      Global.setParameter(PREFKEY_KOMI,Komi);            //~v1B6I~//~1AfoM~
        Global.setParameter(prefkey_KOMI,Komi);                    //~1AfoM~
                                                                   //~1AfoI~
        MyColor=White.isChecked()?GTPConnector.WHITE:GTPConnector.BLACK;//~1AfoM~
//      Global.setParameter(PREFKEY_WHITE,MyColor);                //~v1B6R~//~1AfoM~
        Global.setParameter(prefkey_WHITE,MyColor);                //~1AfoM~
                                                                   //~1AfoI~
//      int btnid=rgRule.getCheckedRadioButtonId();          //~1822R~//~1AfoM~
//      if (btnid==R.id.GTP_RULE_JAPANESE)                         //~1AfoM~
        if (cbRule.isChecked())                                    //~1AfoM~
        	Rules=RULE_JAPANESE;                                   //~1AfoM~
        else                                                       //~1AfoM~
        	Rules=RULE_CHINESE;                                    //~1AfoM~
//      Global.setParameter(PREFKEY_RULE,Rules);                   //~v1B6M~//~1AfoM~
        Global.setParameter(prefkey_RULE,Rules);                   //~1AfoM~
                                                                   //~1AfoM~
		String strPlaymode=getPlaymode();                          //~1AfoM~
                                                                   //~1AfoI~
        Pondering=cbPondering.isChecked();                         //~1AfoI~
        Global.setParameter(prefkey_PONDERING,Pondering);          //~1AfoI~
                                                                   //~1AfoI~
        Thread=AjagoUtils.str2int(etThread.getText().toString(),0);//~1AfoM~
        Global.setParameter(prefkey_THREAD,Thread);                //~1AfoM~
                                                                   //~1AfoI~
        useBook=cbBook.isChecked();                                //~1AfoM~
//      Global.setParameter(PREFKEY_BOOKUSE,useBook);              //~v1BbI~//~1AfoM~
        Global.setParameter(prefkey_BOOKUSE,useBook);              //~1AfoM~
    	String book=null;                                          //~1AfoM~
        if (useBook)                                               //~1AfoM~
        {                                                          //~1AfoM~
    		book=getBookParm();                                    //~1AfoM~
            if (book==null)                                        //~1AfoM~
            	return false;                                      //~1AfoM~
//      	AjagoView.showToastLong(R.string.GtpErr_Book_Warning); //~v1C0R~//~1AfoM~
        }                                                           //~v1B6I~//~1AfoM~
                                                                   //~1AfoI~
                                                                   //~v1BbI~
        text+=OPT_UCT;                                             //~1AfoI~
        text+=strPlaymode;                                         //~1AfoM~
        if (Rules==RULE_JAPANESE)//default is chinese              //~1AfoM~
            text+=PACHI_RULE_JAPANESE;                             //~1AfoR~
        if (Thread>0)                                              //~1AfoM~
        	if (enginarg.equals(""))                               //~1AfoI~
        		enginarg=OPT_THREAD+Thread;                        //~1AfoR~
            else                                                   //~1AfoI~
        		enginarg+=","+OPT_THREAD+Thread;                   //~1AfoI~
        if (Pondering)                                             //~1AfoI~
        	if (enginarg.equals(""))                               //~1AfoI~
        		enginarg=OPT_PONDERING+"1";                        //~1AfoR~
            else                                                   //~1AfoI~
        		enginarg+=","+OPT_PONDERING+"1";                   //~1AfoR~
        else                                                       //~1AfoI~
        	if (enginarg.equals(""))                               //~1AfoI~
        		enginarg=OPT_PONDERING+"0";                        //~1AfoI~
            else                                                   //~1AfoI~
        		enginarg+=","+OPT_PONDERING+"0";                   //~1AfoI~
        String option=getPgmOption();
        if (!option.equals(""))                                    //~1AfoI~
        	if (enginarg.equals(""))                               //~1AfoI~
        		enginarg=option;                                   //~1AfoI~
            else                                                   //~1AfoI~
        		enginarg+=","+option;                              //~1AfoI~
        if (useBook)                                               //~v1BbI~
        	text+=OPT_BOOK+book;                                   //~1AfoR~
        text+=OPT_SEED+getRandomSeed();                            //~1AfoR~
                                                                   //~1AfoI~
	    if (!AG.isDebuggable)                                      //~1AfoM~
        {                                                          //~1AfoM~
        	Verbose=false;                                         //~1AfoM~
            text+=MIN_DEBUGLEVEL; //   =" -d 2";  //activate "UDBUGL(1 or 0) the fprintf"   2>1//~1AfoI~
        }                                                          //~1AfoM~
        else                                                       //~1AfoM~
        {                                                          //~1AfoM~
        	Verbose=cbVerbose.isChecked();                         //~1AfoM~
//        	Global.setParameter(PREFKEY_VERBOSE,Verbose);          //~v1B6R~//~1AfoM~
          	Global.setParameter(prefkey_VERBOSE,Verbose);          //~1AfoM~
        }                                                          //~1AfoM~
        if (!Verbose)                                              //~1AfoM~
            text+=OPT_NOBOARDPRINT;   //no board print             //~1AfoM~
        text+=" "+cmdOption;                                       //~1AfoI~
        text+=" "+enginarg;                                        //~1AfoR~
        if (swShowOption)                                          //~1AfoI~
        {                                                          //~1AfoI~
        	showOption=text;                                       //~1AfoI~
        	return false;          //called by show button         //~1AfoI~
        }                                                          //~1AfoI~
        if (Dump.Y) Dump.println("GTPConnection:play text="+text); //~1AfoR~
        C=new GTPConnector(text,this);                                  //~v1B6R~
//      Global.setParameter("gtpserver",text);                     //~v1B6R~
        C.setGTPInterface(this);                                   //~v1B6I~
//      strTimesettings=getTimeSettingsParm();                     //~1AfoR~
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
////          AjagoUiThread.initlatch(AjagoUiThread.LID_GTP); //wait GTPwait dialog setup to avoid AG.currentLayout is replaced//~v1B6R~
            C.connect();                                           //~v1B6I~
////          new GTPWait(this);                                   //~v1B6R~
////          AjagoUiThread.post(AjagoUiThread.LID_GTP);  //wait GTPwait dialog setup to avoid AG.currentLayout is replaced//~v1B6R~
//            Ok=null;        //@@@@Del Mainthread will not wait modal dialog;clear at Abort button pused//~v1B6R~
        }                                                          //~v1B6I~
        catch (Exception e)                                        //~v1B6I~
        {                                                          //~v1B6R~
//            rene.dialogs.Warning w=                              //~v1B6I~
//            new rene.dialogs.Warning(this,                       //~v1B6R~
//                "Error : "+e.toString(),"Warning",true);         //~v1B6R~
            Dump.println(e,"Play Exception");                      //~v1B6R~
//            w.center(this);                                      //~v1B6R~
//            w.setVisible(true);                                  //~v1B6R~
	    	int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;   //~v1B6I~
        	AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1B6I~
					AG.resource.getString(R.string.GtpErr_Pgm_Failed)+e.toString(),flag);//~v1B6I~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6I~
        return true;                                               //~v1B6I~
    }//play                                                        //~v1B6I~
//***************************************************************************//~1AfoI~
	protected String getPlaymode()                                 //~1AfoI~
    {                                                              //~1AfoI~
    	String strPlaymode="";                                     //~1AfoI~
        Playmode=rgPlaymode.getChecked();                          //~1AfoI~
        if (Dump.Y) Dump.println("GTPConnection:getPlaymode="+Playmode);//~1AfoI~
	    Playout=AjagoUtils.str2int(etPlayout.getText().toString(),0);//~1AfoI~
        if (Playout<=0)                                            //~1AfoI~
        	Playout=PLAYOUT_DEFAULT;                               //~1AfoI~
	    Consttime=AjagoUtils.str2int(etConsttime.getText().toString(),0);//~1AfoI~
        if (Consttime<=0)                                          //~1AfoI~
        	Consttime=CONSTTIME_DEFAULT;                           //~1AfoI~
	    Alltime=AjagoUtils.str2int(etAlltime.getText().toString(),0);//~1AfoI~
        if (Alltime<=0)                                            //~1AfoI~
        	Alltime=ALLTIME_DEFAULT;                               //~1AfoI~
        Global.setParameter(prefkey_PLAYMODE,Playmode);            //~1AfoR~
        Global.setParameter(prefkey_PLAYOUT,Playout);              //~1AfoR~
        Global.setParameter(prefkey_CONSTTIME,Consttime);          //~1AfoR~
        Global.setParameter(prefkey_ALLTIME,Alltime);              //~1AfoR~
        strTimesettings=getTimeSettingsParm();                     //~1AfoI~
        if (Playmode!=PLAYMODE_TIMESETTING)                        //~1AfoR~
        {                                                          //~1AfoI~
        	strTimesettings="";                                    //~1AfoI~
	        if (Playmode==PLAYMODE_PLAYOUT)                        //~1AfoI~
            	strPlaymode=" -t ="+Playout;                       //~1AfoI~
            else                                                   //~1AfoI~
	        if (Playmode==PLAYMODE_CONSTTIME)                      //~1AfoR~
            	strPlaymode=" -t  "+Consttime;                      //~1AfoR~
            else                                                   //~1AfoI~
	        if (Playmode==PLAYMODE_ALLTIME)                        //~1AfoI~
            	strPlaymode=" -t _"+(Alltime*60);                  //~1AfoR~
        }                                                          //~1AfoI~
        return strPlaymode;                                         //~1AfoI~
	}//getPlaymode                                                 //~1AfoI~
//***************************************************************************//~v1C1I~
	protected String getPgmOption()                                //~v1C1R~
    {                                                              //~v1C1I~
                                                                   //~1AfoI~
        String option=etOption.getText().toString();               //~v1C1I~
        option=option.trim();                                      //~v1C1I~
//      Global.setParameter(PREFKEY_PGMOPTION,option);             //~v1C1R~
        Global.setParameter(prefkey_PGMOPTION,option);             //~v1C1I~
        int pos=option.indexOf(';');//split pachi oftion and UCT option//~1AfoI~
        cmdOption="";                                              //~1AfoI~
        if (pos>0)                                                 //~1AfoI~
        {                                                          //~1AfoI~
        	cmdOption=option.substring(0,pos);                   //~1AfoI~
            option=option.substring(pos+1);                               //~1AfoI~
        }                                                          //~1AfoI~
        return option;                                             //~v1C1I~
	}                                                              //~v1C1I~
//***************************************************************************//~v1B6I~
//  private void initializedGoGui()                                //~v1B6I~//~v1C1R~
            void initializedGoGui()                                //~v1C1I~
    {                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection:gotOk");           //~v1B6I~
        guigtpclient=C.gogui.m_gtp;                                //~v1B6I~
        gtpclient=guigtpclient.m_gtp;                              //~v1B6R~
//      AjagoUiThread.wait(AjagoUiThread.LID_GTP);  //100ms wait GTPwait dialog setup to avoid AG.currentLayout is replaced//~v1B6I~
        F=new GTPGoFrame(Co,BoardSize,                             //~v1B6I~
            White.isChecked()?1:-1);                               //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection:after new GTPGoFrame");//~v1B6I~
	    setTitleGtp(F);                                            //~v1B6R~
        F.setVisible(true);                                        //~v1B6I~
//      Co.setVisible(false); Co.dispose();                        //~v1B6I~
        if (Handicap>1) handicap(Handicap);                        //~v1B6I~
        if (C.gogui.m_timeSettings!=null)                            //~v1B6I~
        {                                                          //~v1B6I~
            int presec=(int)C.gogui.m_timeSettings.getPreByoyomi()/1000;//~v1B6I~
            int postmove=(int)C.gogui.m_timeSettings.getByoyomiMoves();//~v1B6R~
            int postsec=(int)C.gogui.m_timeSettings.getByoyomi()/1000;//~v1B6I~
	        F.settime(presec,postsec,postmove);                    //~v1B6R~
        }                                                          //~v1B6I~
        GoColor gc=null;                                           //~v1B6I~
        if (Handicap>1)                                            //~v1B6I~
        {                                                          //~v1B6I~
        	if (MyColor==GTPConnector.BLACK) //computer:White      //~v1B6R~
            	gc=GoColor.BLACK;     //first stone=computer:White;//~v1B6I~
        }                                                          //~v1B6I~
        else	//no handicap                                      //~v1B6I~
        {                                                          //~v1B6I~
        	if (MyColor==GTPConnector.WHITE) //computer:Black      //~v1B6I~
            	gc=GoColor.WHITE;     //first stone=computer:BLACK //~v1B6I~
        }                                                          //~v1B6I~
//      if (gc!=null)                                              //~v1B6I~//~v1C1R~
//          C.gogui.generateMove(false/*singleMove*/,gc/*human color*/);//~v1B6R~//~v1C1R~
        initGtpCommand(gc);                                        //~v1C1I~
    }//initializedGoGui                                            //~v1B6I~
//***************************************************************************//~v1C1I~
    protected void initGtpCommand(GoColor Pgc)                     //~v1C1I~
    {                                                              //~v1C1I~
        if (Pgc!=null)                                             //~v1C1I~
            C.gogui.generateMove(false/*singleMove*/,Pgc/*human color*/);//~v1C1I~
    }//initializedGoGui                                            //~v1C1I~
//******************                                               //~v1B6I~
//  private String getTimeSettingsParm()                           //~v1B6I~//~v1C1R~
            String getTimeSettingsParm()                           //~v1C1I~
    {                                                              //~v1B6I~
        String tsmain=etTimeSettingMain.getText().toString();      //~v1B6I~
        if (tsmain==null)                                          //~v1B6I~
        	tsmain="";                                             //~v1B6I~
        else                                                       //~v1B6I~
	        tsmain=tsmain.trim();                                  //~v1B6I~
//      Global.setParameter(PREFKEY_TIMESETTINGMAIN,tsmain);           //~v1B6I~//~v1C1R~
        Global.setParameter(prefkey_TIMESETTINGMAIN,tsmain);       //~v1C1I~
//      if (tsmain.equals(""))                                     //~1AfjR~
//      	return "";                                             //~1AfjR~
      if (!tsmain.equals(""))                                      //~1AfjI~
        tsmain+="m";//minutes                                      //~v1B6I~
        String tsextra=etTimeSettingExtra.getText().toString();    //~v1B6I~
        if (tsextra==null)                                         //~v1B6I~
	        tsextra="";                                            //~v1B6I~
        else                                                       //~v1B6I~
	        tsextra=tsextra.trim();                                //~v1B6I~
//      Global.setParameter(PREFKEY_TIMESETTINGEXTRA,tsextra);     //~v1B6I~//~v1C1R~
        Global.setParameter(prefkey_TIMESETTINGEXTRA,tsextra);     //~v1C1I~
//      if (tsextra.equals(""))                                    //~1AfjR~
//      	return tsmain;                                         //~1AfjR~
      if (!tsmain.equals(""))                                      //~1AfjI~
      if (!tsextra.equals(""))                                     //~1AfjR~
        tsmain+="+"+tsextra+"s";	//seconds                      //~v1B6R~
        String tsmove=etTimeSettingMove.getText().toString();      //~v1B6I~
        if (tsmove==null)                                          //~v1B6I~
	        tsmove="";                                             //~v1B6I~
        else                                                       //~v1B6I~
	        tsmove.trim();                                        //~v1B6I~
      if (!tsmain.equals(""))                                      //~1AfjI~
      {                                                            //~1AfjI~
        if (tsmove.equals(""))                                      //~v1B6R~
        	tsmove=DEFAULT_TIMESETTINGMOVE;                        //~v1B6I~
        if (AjagoUtils.str2int(tsmove,0)==0)                       //~v1B6I~
        	tsmove=DEFAULT_TIMESETTINGMOVE;                        //~v1B6I~
      }                                                            //~1AfjI~
//      Global.setParameter(PREFKEY_TIMESETTINGMOVE,tsmove);       //~v1B6I~//~v1C1R~
        Global.setParameter(prefkey_TIMESETTINGMOVE,tsmove);       //~v1C1I~
        if (tsmain.equals(""))                                     //~1AfjI~
			return "";                                             //~1AfjI~
        return tsmain+="/"+tsmove;                                        //~v1B6I~
    }                                                              //~v1B6I~
//******************                                               //~v1B6I~
//  private void setTimeSettingsField()                          //~v1B6I~//~v1C1R~
            void setTimeSettingsField()                            //~v1C1I~
    {                                                              //~v1B6I~
		String ts;                                                 //~v1B6I~
//  	ts=Global.getParameter(PREFKEY_TIMESETTINGMAIN,"");            //~v1B6I~//~v1C1R~
    	ts=Global.getParameter(prefkey_TIMESETTINGMAIN,"");        //~v1C1I~
        etTimeSettingMain.setText(ts);                             //~v1B6I~
//  	ts=Global.getParameter(PREFKEY_TIMESETTINGEXTRA,"");       //~v1B6I~//~v1C1R~
    	ts=Global.getParameter(prefkey_TIMESETTINGEXTRA,"");       //~v1C1I~
        etTimeSettingExtra.setText(ts);                            //~v1B6I~
//  	ts=Global.getParameter(PREFKEY_TIMESETTINGMOVE,"");        //~v1B6I~//~v1C1R~
    	ts=Global.getParameter(prefkey_TIMESETTINGMOVE,"");        //~v1C1I~
        etTimeSettingMove.setText(ts);                             //~v1B6I~
    }                                                              //~v1B6I~
//******************                                               //~v1B6I~
    private void setTitleGtp(GTPGoFrame Frame)                     //~v1B6R~//~v1C1R~
    {                                                              //~v1B6I~
    	String bn,wn,cn;                                           //~v1B6I~
        String pgm=C.gogui.getProgramName();                       //~v1B6R~
        String version=C.gogui.m_version;//10.00(Satugen):If...    //~v1B6R~
//        if (version==null)                                         //~v1B6I~//~v1C1R~
//            version="";                                            //~v1B6I~//~v1C1R~
//        int len=version.indexOf(' ');                               //~v1B6I~//~v1C1R~
//        int len2=version.indexOf(':');                              //~v1B6I~//~v1C1R~
//        if (len2>0 && len2<len)                                    //~v1B6I~//~v1C1R~
//            len=len2;                                              //~v1B6I~//~v1C1R~
//        if (len>0)                                                 //~v1B6I~//~v1C1R~
//            version=version.substring(0,len);                      //~v1B6I~//~v1C1R~
        version=editVersion(version);                              //~1AfhR~
        updateVersion(version);                                    //~1AfhI~
//      cn=pgm+"("+version+")";   //computer name                  //~1AfhR~
        cn=pgm;   //computer name                                  //~1AfhI~
//        String yc,cc;                                            //~v1D3R~
//        String bc=AG.resource.getString(R.string.SideBlack);     //~v1D3R~
//        String wc=AG.resource.getString(R.string.SideWhite);     //~v1D3R~
        if (MyColor==GTPConnector.BLACK)                           //~1AfcR~
        {   /*yc=bc;cc=wc;*/ bn=YourName; wn=cn;}                  //~1AfcR~
        else                                                       //~1AfcR~
        {   /*yc=wc;cc=bc;*/ bn=cn; wn=YourName;}                  //~1AfcR~
//        String title=yc+YourName+" vs "+cc+cn;                   //~1AfcR~
//        String title=wn+"-"+bn;                                  //~1AfcR~
          String title;                                            //~1AfcI~
        if (MyColor==GTPConnector.BLACK)                           //~1AfcI~
        	title="W:"+cn;                                         //~1AfcI~
        else                                                       //~1AfcI~
        	title="B:"+cn;                                         //~1AfcI~
//        if (strTimesettings!=null)                               //~1AfhR~
//        if (!strTimesettings.equals(""))                         //~1AfhR~
//            title+=" - "+strTimesettings;                        //~1AfhR~
        Frame.setTitle(title);                         //~v1B6R~
        if (Dump.Y) Dump.println("setTitleGtp title="+title);      //~1AfcI~
        String strKomi;                                            //~v1B6I~
        if (Komi==0)                                               //~v1B6I~
        	strKomi="0";                                           //~v1B6I~
        else                                                       //~v1B6I~
        	strKomi=Integer.toString(Komi)+".5";                   //~v1B6I~
        F.B.setinformation(bn/*blackName*/,""/*blackrank*/,wn/*white name*/,""/*whiterank*/,strKomi,Integer.toString(Handicap));//~v1B6I~
    }                                                              //~v1B6I~
//******************                                               //~v1C1I~
    protected String editVersion(String Pversion)                  //~v1C1I~
    {                                                              //~v1C1I~
        if (Pversion==null || Pversion.equals(""))                 //~1AfhI~
            return "";                                             //~1AfhI~
    	String version=Pversion;                                   //~1AfhI~
        int len=version.indexOf(' ');                              //~1AfhI~
        int len2=version.indexOf(':');                             //~1AfhI~
//      if (len2>0 && len2<len)                                    //~1AfhR~
        if (len2>len)                                              //~1AfhI~
            len=len2;                                              //~1AfhI~
        if (len>0)                                                 //~1AfhI~
            version=version.substring(0,len);                      //~1AfhI~
        if (Dump.Y) Dump.println("GTPConnection:editVersion Pversion="+Pversion+",out version="+version);//~1AfhR~
        return version;                                            //~1AfhI~
    }                                                              //~1AfhI~
    protected void updateVersion(String Pversion)                  //~1AfhI~
    {                                                              //~1AfhI~
    	boolean swupdate=false;                                        //~1AfhI~
        int gtpid=0;                                               //~1Ag0I~
        if (Dump.Y) Dump.println("GTPConnection:editVersion Fuego version="+Pversion);//~1AfhR~
        if (this instanceof GTPConnectionFuego)                    //~1AfhI~
        {                                                          //~1AfhI~
            gtpid=GTPID_FUEGO;                                     //~1Ag0I~
        	if (Dump.Y) Dump.println("GTPConnection:updateVersion Fuego old vesrion="+GTPConnectionFuego.Sversion+",new="+Pversion);//~1AfhI~
	        if (!Pversion.equals(GTPConnectionFuego.Sversion))     //~1AfhI~
            {                                                      //~1AfhI~
	        	GTPConnectionFuego.Sversion=Pversion;              //~1AfhI~
                swupdate=true;                                     //~1AfhI~
            }                                                      //~1AfhI~
        	                                                       //~1AfhI~
        }                                                          //~1AfhI~
        else                                                       //~1AfhI~
        if (this instanceof GTPConnectionRay)                      //~1AfhI~
        {                                                          //~1AfhI~
            gtpid=GTPID_RAY;                                       //~1Ag0I~
        	if (Dump.Y) Dump.println("GTPConnection:updateVersion Ray old vesrion="+GTPConnectionRay.Sversion+",new="+Pversion);//~1AfhI~
	        if (!Pversion.equals(GTPConnectionRay.Sversion))       //~1AfhI~
            {                                                      //~1AfhI~
	        	GTPConnectionRay.Sversion=Pversion;                //~1AfhI~
                swupdate=true;                                     //~1AfhI~
            }                                                      //~1AfhI~
                                                                   //~1AfhI~
        }                                                          //~1AfhI~
        else                                                       //~1AfhI~
        if (this instanceof GTPConnectionGnugo)                    //~1Ag0I~
        {                                                          //~1Ag0I~
            gtpid=GTPID_GNUGO;                                     //~1Ag0I~
        	if (Dump.Y) Dump.println("GTPConnection:updateVersion Gnugo old vesrion="+GTPConnectionGnugo.Sversion+",new="+Pversion);//~1Ag0I~
	        if (!Pversion.equals(GTPConnectionGnugo.Sversion))     //~1Ag0I~
            {                                                      //~1Ag0I~
	        	GTPConnectionGnugo.Sversion=Pversion;              //~1Ag0I~
                swupdate=true;                                     //~1Ag0I~
            }                                                      //~1Ag0I~
                                                                   //~1Ag0I~
        }                                                          //~1Ag0I~
        else                                                       //~1Ag0I~
        {                                                          //~1AfhI~
            gtpid=GTPID_PACHI;                                     //~1Ag0R~
        	if (Dump.Y) Dump.println("GTPConnection:updateVersion Pachi old vesrion="+GTPConnection.Sversion+",new="+Pversion);//~1Ag0R~
	        if (!Pversion.equals(GTPConnection.Sversion))          //~1AfhI~
            {                                                      //~1AfhI~
	        	GTPConnection.Sversion=Pversion;                   //~1AfhI~
                swupdate=true;                                     //~1AfhI~
            }                                                      //~1AfhI~
                                                                   //~1AfhI~
        }                                                          //~1AfhI~
        if (swupdate)                                              //~1AfhR~
        {                                                          //~1AfhR~
        	if (Dump.Y) Dump.println("GTPConnection:editVersion update prefkey="+prefkey_GTPSERVER_VERSION+",version="+Pversion);//~1AfhR~
	        Global.setParameter(prefkey_GTPSERVER_VERSION,Pversion);//~1AfhR~
    		String itemname=getMenuitemName(gtpid,Pversion);        //~1Ag0I~
            AjagoMenu.updateItemNameGTP(Smenu,gtpid,itemname);     //~1Ag0R~
        }                                                          //~1AfhI~
    }//updateVersion                                               //~1AfhR~
//********************************************************************//~1Ag0I~
//*save menubar and menu to update menuitemname                    //~1Ag0I~
//********************************************************************//~1Ag0I~
	public static void notifyMenu(Menu Pmenu)                      //~1Ag0R~
    {                                                              //~1Ag0I~
    	Smenu=Pmenu;                                               //~1Ag0I~
    }//notifyMenu                                                  //~1Ag0I~
//********************************************************************//~1Ag0R~
//*from MainFrame to setup menu                                    //~1Ag0I~
//********************************************************************//~1Ag0I~
    public static String getVersion(int Pserverid)                 //~1AfhI~
    {                                                              //~1AfhI~
    	String version,prefkey;                                    //~1AfhI~
        switch (Pserverid)                                         //~1AfhI~
        {                                                          //~1AfhI~
//      case 1:	//Pachi	                                           //~1Ag0R~
        case GTPID_PACHI:	//Pachi                                //~1Ag0I~
            version=GTPConnection.Sversion;                        //~1AfhI~
            prefkey=GTPConnection.PREFKEY_GTPSERVER_VERSION;        //~1AfhI~
            break;                                                 //~1AfhI~
//      case 2:	//Fuego                                            //~1Ag0R~
        case GTPID_FUEGO:	//Pachi                                //~1Ag0I~
            version=GTPConnectionFuego.Sversion;                   //~1AfhI~
            prefkey=GTPConnectionFuego.PREFKEY_GTPSERVER_VERSION;   //~1AfhI~
            break;                                                 //~1AfhI~
//      case 3:	//Ray                                              //~1Ag0R~
        case GTPID_RAY  :	//Pachi                                //~1Ag0I~
            version=GTPConnectionRay.Sversion;                     //~1AfhI~
            prefkey=GTPConnectionRay.PREFKEY_GTPSERVER_VERSION;     //~1AfhI~
            break;                                                 //~1AfhI~
//      case 4:	//GnuGo gtp mode                                   //~1Ag0R~
        case GTPID_GNUGO:	//Pachi                                //~1Ag0I~
            version=GTPConnectionGnugo.Sversion;                   //~1AfrI~
            prefkey=GTPConnectionGnugo.PREFKEY_GTPSERVER_VERSION;  //~1AfrI~
            break;                                                 //~1AfrI~
        default:                                                   //~1AfhI~
        	return "";                                             //~1AfhI~
        }                                                          //~1AfhI~
        if (version.equals(""))                                    //~1AfhI~
        	version=Global.getParameter(prefkey,"");               //~1AfhI~
        if (Dump.Y) Dump.println("GTPConnection:getVersion serverid="+Pserverid+",version="+version);//~1AfhI~
        version=getMenuitemName(Pserverid,version);                //~1Ag0R~
        return version;                                            //~1AfhI~
    }                                                              //~1AfhI~
    private static String getMenuitemName(int Pserverid,String Pversion)//~1Ag0I~
    {                                                              //~1Ag0I~
    	String itemname="";                                        //~1Ag0I~
        switch (Pserverid)                                         //~1Ag0I~
        {                                                          //~1Ag0I~
        case GTPID_PACHI:	//Pachi                                //~1Ag0I~
            itemname=GTPConnection.DEFAULT_GTPSERVER_ORGNAME     +"  : ";//~1Ag0I~
            break;                                                 //~1Ag0I~
        case GTPID_FUEGO:	//Pachi                                //~1Ag0I~
            itemname=GTPConnectionFuego.DEFAULT_GTPSERVER_ORGNAME+"  : ";//~1Ag0I~
            break;                                                 //~1Ag0I~
        case GTPID_RAY  :	//Pachi                                //~1Ag0I~
            itemname=GTPConnectionRay.DEFAULT_GTPSERVER_ORGNAME  +"    : ";//~1Ag0I~
            break;                                                 //~1Ag0I~
        case GTPID_GNUGO:	//Pachi                                //~1Ag0I~
            itemname=GTPConnectionGnugo.DEFAULT_GTPSERVER_ORGNAME+"  : ";//~1Ag0I~
            break;                                                 //~1Ag0I~
        }                                                          //~1Ag0I~
        return itemname+Pversion;                                  //~1Ag0R~
	}//getMenuitemName                                             //~1Ag0I~
//******************                                               //~v1B6I~
	public int getHandicap ()
	{	return Handicap;
	}
	public int getColor ()
	{	return MyColor;
	}
	public int getRules ()
	{	return Rules;
	}
	public int getBoardSize ()
	{	return BoardSize;
	}
	public void gotMove (int color, int pos)
	{	pos--;
		int i=pos%BoardSize;
		int j=pos/BoardSize;
    	if (Dump.Y) Dump.println("GTPConnection gotMove:i="+i+",j="+j);//~v106I~
		if (i<0 || j<0) F.gotPass(color);
        chkpassdoubled(false/*not human*/,(i<0 || j<0));    //chk both passed//~v1B6I~
		if (color==MyColor) F.gotSet(color,i,BoardSize-j-1);
		else F.gotMove(color,i,BoardSize-j-1);
	}
	OkAdapter Ok=null;
	public void setOk (OkAdapter ok)                               //~1513R~
    {                                                              //~1513I~
		Ok=ok;                                                     //~1513I~
    	if (Dump.Y) Dump.println("GTPConnection setOk:"+(Ok==null?"null":Ok.toString()));//~1513R~
    }                                                              //~1513I~
	public void gotOk ()
	{                                                              //~1513R~
    	if (Dump.Y) Dump.println("GTPConnection gotOk:"+(Ok==null?"null":Ok.toString()));//~1513I~
	 	if (Ok!=null) Ok.gotOk();                                  //~1513I~
		Ok=null;
	}
	public void gotAnswer (int a)
	{
	}
	
	public int I,J;
//********************************************************         //~v1B6R~
//*from GTPGoFrame:moveset                                         //~v1B6I~
//*player set stone                                                //~v1B6I~
//********************************************************         //~v1B6I~
	public void moveset (int i, int j)
	{                                                              //~v1B6R~
		int pos=(BoardSize-j-1)*BoardSize+i+1;                     //~v1B6I~
		I=i; J=j;
		try
		{	setOk(new OkAdapter ()
				{	public void gotOk ()
					{	F.gotMove(MyColor,I,J);
					}
				}
			);
          if (I<0)  //pass                                         //~v1B6I~
			C.move(MyColor,0);                                     //~v1B6I~
          else                                                     //~v1B6I~
			C.move(MyColor,pos);
            chkpassdoubled(true/*human*/,I<0);    //chk both passed//~v1B6I~
            gtpclient.getResponse();  //throw exception if err     //~v1B6R~
            gotOk();	//OkAdapter.gotOk():F.gotMove              //~v1B6R~
            GoColor gc=(MyColor==GTPConnector.BLACK ? GoColor.BLACK : GoColor.WHITE);//~v1B6I~
            C.gogui.generateMove(false/*singleMove*/,gc);           //~v1B6I~
            resetGameover();                                       //~v1B6I~
		}
		catch (Exception e)                                        //~v1B6R~
        {                                                          //~v1B6I~
            Dump.println(e,"GTPConnection:moveset");               //~v1B6I~
		}                                                          //~v1B6I~
	}
//*************************************************************************//~v1B6R~
//*from GTPGoFrame:notepass()                                      //~v1B6I~
//*************************************************************************//~v1B6I~
	public void pass ()
	{                                                              //~v1B6R~
//        try                                                      //~v1B6I~
//        {   C.move(MyColor,0);                                   //~v1B6R~
//        }                                                        //~v1B6R~
//        catch (Exception e) {}                                   //~v1B6R~
		moveset(-1,-1);                                            //~v1B6I~
	}
	
	public boolean undo ()                                         //~v1B6R~
	{                                                              //~v1B6R~
    	boolean rc=false;                                          //~v1B6I~
		try                                                        //~v1B6I~
		{                                                          //~v1B6R~
			rc=C.takeback(2);                                      //~v1B6I~
		}
		catch (GtpError e)                                 //~v1B6I~
		{                                                          //~v1B6I~
            Dump.println(e,"GTPConnection:undo");                  //~v1B6I~
        	AjagoView.showToastLong(R.string.GtpErr_Undo_Failed,"\n"+e.toString());//~v1B6R~
		}                                                          //~v1B6I~
		catch (Exception e)                                        //~v1B6R~
		{                                                          //~v1B6I~
            Dump.println(e,"GTPConnection:undo");                  //~v1B6I~
		}                                                          //~v1B6I~
        return rc;                                                 //~v1B6I~
	}
//***********************************************************      //~v1B6I~
//  private PointList handicapList;                                //~v1B6I~//~v1C1R~
            PointList handicapList;                                //~v1C1I~
//***********************************************************	   //~v1B6R~
	public void setblack (int i, int j)
	{	F.gotSet(GTPConnector.BLACK,i,j);
    	if (handicapList!=null)                                    //~v1B6I~
        	addHandicapList(i,j);                                   //~v1B6I~
	}
//***********************************************************      //~v1B6I~
    private void addHandicapList(int i,int j)                      //~v1B6R~
	{                                                              //~v1B6I~
		int x=i;                                                   //~v1B6I~
		int y=BoardSize-j-1;                                       //~v1B6I~
    	GoPoint p=GoPoint.get(x,y);                                //~v1B6I~
        handicapList.add(p);                                       //~v1B6I~
	}                                                              //~v1B6I~
	public void handicap (int n)
	{                                                              //~v1B6R~
    	handicapList=new PointList();                              //~v1B6R~
		int S=BoardSize;                                           //~v1B6I~
		int h=(S<13)?3:4;
		if (n>5)
		{	setblack(h-1,S/2); setblack(S-h,S/2);
		}
		if (n>7)
		{	setblack(S/2,h-1); setblack(S/2,S-h);
		}
		switch (n)
		{	case 9 :
			case 7 :
			case 5 :
				setblack(S/2,S/2);
			case 8 :
			case 6 :
			case 4 :
				setblack(S-h,S-h);
			case 3 :
				setblack(h-1,h-1);
			case 2 :
				setblack(h-1,S-h);
			case 1 :
				setblack(S-h,h-1);
		}
		F.color(GTPConnector.WHITE);
        try                                                        //~v1B6I~
        {                                                          //~v1B6I~
        	guigtpclient.m_gtpSynchronizer.sendHandicap(handicapList);//~v1B6R~
        }                                                          //~v1B6I~
        catch(Exception e)                                         //~v1B6I~
        {                                                          //~v1B6I~
        	Dump.println(e,"GTPConnection:sendHandicap");          //~v1B6I~
        }                                                          //~v1B6I~
        handicapList=null;                                         //~v1B6I~
	}
	
	public boolean askUndo ()
	{                                                              //~v1B6R~
//        setOk(new OkAdapter ()                                   //~v1B6I~
//            {   public void gotOk ()                             //~v1B6R~
//                {   F.doundo(2);                                 //~v1B6R~
//                }                                                //~v1B6R~
//            }                                                    //~v1B6R~
//        );                                                       //~v1B6R~
//        try                                                      //~v1B6R~
//        {   C.send(6,2);                                         //~v1B6R~
//        }                                                        //~v1B6R~
//        catch (Exception e)                                      //~v1B6R~
//        {}                                                       //~v1B6R~
//        try                                                      //~v1B6R~
//        {                                                        //~v1B6R~
//        }                                                        //~v1B6R~
//        catch (Exception e)                                      //~v1B6R~
//        {}                                                       //~v1B6R~
//*askUndo is never called for Connected Board                     //~v1B6I~
		return false;
	}
	
	public void doclose ()
	{	if (C!=null) C.doclose();
//  	super.doclose();                                           //~v1B6R~
	}
//*********                                                        //~v1B6I~
	@Override                                                      //~v1B6I~
    protected void onDismiss()                                     //~v1B6I~
    {                                                              //~v1B6I~
    }                                                              //~v1B6I~
//*********                                                        //~v1B6I~
	@Override                                                      //~v1B6I~
	protected void setupDialogExtend(ViewGroup PlayoutView)        //~v1B6I~
    {                                                              //~v1B6I~
	    Program=(EditText)PlayoutView.findViewById(R.id.GTP_COMMAND);//~v1B6R~
//  	String gtpserver=Global.getParameter(PREFKEY_PROGRAM,DEFUALT_GTPSERVER);//~1AfoM~
    	String gtpserver=getCommand();                             //~1AfoM~
		Program.setText(gtpserver);                                //~1AfoM~
                                                                   //~1AfoI~
	    YourNameField=(EditText)PlayoutView.findViewById(R.id.GTP_YOURNAME);//~1AfoM~
//  	YourName=Global.getParameter(PREFKEY_YOURNAME,DEFAULT_YOURNAME);//~v1B6I~//~1AfoM~
    	YourName=Global.getParameter(prefkey_YOURNAME,DEFAULT_YOURNAME);//~1AfoM~
		YourNameField.setText(YourName);                           //~1AfoM~
                                                                   //~1AfoI~
	    BoardSizeField=(EditText)PlayoutView.findViewById(R.id.GTP_BOARDSIZE);//~1AfoM~
//  	BoardSize=Global.getParameter(PREFKEY_BOARDSIZE,DEFAULT_BOARDSIZE);//~v1B6R~//~1AfoM~
    	BoardSize=Global.getParameter(prefkey_BOARDSIZE,DEFAULT_BOARDSIZE);//~1AfoM~
		BoardSizeField.setText(Integer.toString(BoardSize));       //~1AfoM~
                                                                   //~1AfoI~
	    HandicapField=(EditText)PlayoutView.findViewById(R.id.GTP_HANDICAP);//~1AfoM~
//  	Handicap=Global.getParameter(PREFKEY_HANDICAP,DEFAULT_HANDICAP);//~v1B6R~//~1AfoM~
    	Handicap=Global.getParameter(prefkey_HANDICAP,DEFAULT_HANDICAP);//~1AfoM~
		HandicapField.setText(Integer.toString(Handicap));         //~1AfoM~
                                                                   //~1AfoI~
	    KomiField=(EditText)layoutView.findViewById(R.id.GTP_KOMI);//~1AfoM~
//  	Komi=Global.getParameter(PREFKEY_KOMI,DEFAULT_KOMI);       //~v1B6R~//~1AfoM~
    	Komi=Global.getParameter(prefkey_KOMI,DEFAULT_KOMI);       //~1AfoM~
        String strKomi;                                            //~1AfoM~
        if (Komi==0)                                               //~1AfoM~
        	strKomi="0";                                           //~1AfoM~
        else                                                       //~1AfoM~
        	strKomi=Integer.toString(Komi)+".5";                   //~1AfoM~
		KomiField.setText(strKomi);                                //~1AfoM~
                                                                   //~1AfoI~
        White=(CheckBox)PlayoutView.findViewById(R.id.GTP_WHITE);  //~1AfoM~
//  	MyColor=Global.getParameter(PREFKEY_WHITE,DEFAULT_WHITE);  //~v1B6R~//~1AfoM~
    	MyColor=Global.getParameter(prefkey_WHITE,DEFAULT_WHITE);  //~1AfoM~
		White.setChecked(MyColor!=GTPConnector.BLACK);             //~1AfoM~
                                                                   //~1AfoI~
//      rgRule=(RadioGroup)PlayoutView.findViewById(R.id.GTP_RULE);//~1AfoR~
        cbRule=(CheckBox)PlayoutView.findViewById(R.id.GTP_RULE);//~1AfoI~
//  	Rules=Global.getParameter(PREFKEY_RULE,DEFAULT_RULE);      //~v1B6R~//~1AfoM~
    	Rules=Global.getParameter(prefkey_RULE,DEFAULT_RULE);      //~1AfoM~
//        if (Rules==RULE_CHINESE)                                 //~1AfoR~
//            rgRule.check(R.id.GTP_RULE_CHINESE);                 //~1AfoR~
//        else                                                     //~1AfoR~
//            rgRule.check(R.id.GTP_RULE_JAPANESE);                //~1AfoR~
        cbRule.setChecked(Rules==RULE_JAPANESE);                   //~1AfoI~
                                                                   //~1AfoI~
        initRGPlaymode(PlayoutView);                               //~1AfoM~
                                                                   //~1AfoI~
	    etTimeSettingMain=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTING);//~1AfoM~
	    etTimeSettingExtra=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRA);//~1AfoM~
	    etTimeSettingMove=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRAMOVE);//~1AfoM~
        setTimeSettingsField();                                    //~1AfoM~
                                                                   //~1AfoI~
        cbPondering=(CheckBox)PlayoutView.findViewById(R.id.GTPRAY_PONDERING);//~1AfoI~
        Pondering=Global.getParameter(prefkey_PONDERING,DEFAULT_PONDERING);//~1AfoR~
        cbPondering.setChecked(Pondering);                         //~1AfoI~
                                                                   //~1AfoI~
        etThread=(EditText)PlayoutView.findViewById(R.id.GTPRAY_THREAD);//~1AfoM~
        Thread=Global.getParameter(prefkey_THREAD,0);              //~1AfoM~
        if (Thread>0)                                              //~1AfoM~
	        etThread.setText(Integer.toString(Thread));            //~1AfoM~
                                                                   //~1AfoI~
	    cbBook=(CheckBox)PlayoutView.findViewById(R.id.GTP_BOOKUSE);//~1AfoM~
//  	useBook=Global.getParameter(PREFKEY_BOOKUSE,false);        //~v1BbI~//~1AfoM~
    	useBook=Global.getParameter(prefkey_BOOKUSE,false);        //~1AfoM~
		cbBook.setChecked(useBook);                                //~1AfoM~
                                                                   //~1AfoI~
	    etBook=(EditText)PlayoutView.findViewById(R.id.GTP_BOOKFILE);//~1AfoM~
    	String book=getBook();	//orgname                          //~1AfoM~
		etBook.setText(book);                                      //~1AfoM~
                                                                   //~1AfoI~
	    etOption=(EditText)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION);//~v1B6I~
//  	String option=Global.getParameter(PREFKEY_PGMOPTION,"");   //~v1B6I~//~1AfoM~
    	String option=Global.getParameter(prefkey_PGMOPTION,"");   //~1AfoM~
		etOption.setText(option);                                  //~1AfoM~
                                                                   //~1AfoI~
	    btnOptionChange=(Button)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION_UPDATE);//~v1DhI~
        setButtonListener(btnOptionChange);                        //~v1DhI~
                                                                   //~v1DhI~
        cbVerbose=(CheckBox)PlayoutView.findViewById(R.id.GTP_VERBOSE);//~1AfoM~
//  	Verbose=Global.getParameter(PREFKEY_VERBOSE,false);        //~v1B6I~//~1AfoM~
    	Verbose=Global.getParameter(prefkey_VERBOSE,false);        //~1AfoM~
	    if (!AG.isDebuggable)                                      //~v1B6I~
        {                                                          //~v1B6I~
        	cbVerbose.setVisibility(View.GONE);                    //~v1B6I~
        }                                                          //~v1B6I~
		cbVerbose.setChecked(Verbose);                             //~v1B6I~
                                                                   //~1AfoI~
	    tvOption=(TextView)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION_DISPLAY);//~1AfoR~
                                                                   //~1AfoI~
	    LinearLayout llDebug=(LinearLayout)PlayoutView.findViewById(R.id.GTPRAY_DEBUGOPTION);//~1AfoR~
                                                                   //~1AfoI~
	    btnShowOption=(Button)PlayoutView.findViewById(R.id.GTPRAY_SHOWOPTION);//~1AfoI~
	    if (!AG.isDebuggable)                                      //~1AfoI~
        	llDebug.setVisibility(View.GONE);                      //~1AfoI~
        else                                                       //~1AfoI~
	        setButtonListener(btnShowOption);                      //~1AfoI~
                                                                   //~1Ag5I~
		initcbPIE(PlayoutView);                                    //~1Ag5I~
                                                                   //~1AfoI~
    }//setupDialogExtend                                           //~v1B6R~
    //****************************************                     //~v1B6I~
    //*Button                                                      //~v1B6I~
    //****************************************                     //~v1B6I~
	@Override                                                      //~v1B6I~
    protected boolean onClickOther(int PbuttonId)                  //~v1B6I~
    {                                                              //~v1B6I~
    	boolean rc=false;   //not dismiss at return                //~v1B6I~
    //****************                                             //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection onClickOther buttonid="+Integer.toHexString(PbuttonId));//~v1B6I~
        switch(PbuttonId)                                          //~v1B6I~
        {                                                          //~v1B6I~
        case R.id.GTP_PLAY:                                        //~v1B6R~
	       	rc=play();                                             //~v1B6R~
            break;                                                 //~v1B6I~
//      case R.id.GTP_COMMANDOPTION_UPDATE:                        //~1AfoR~
//         	updateoption(etOption);                                //~1AfoR~
//          break;                                                 //~1AfoR~
        case R.id.GTPRAY_SHOWOPTION:                               //~1AfoI~
            swShowOption=true;                                     //~1AfoI~
			play();                                                //~1AfoI~
            swShowOption=false;                                    //~1AfoI~
			tvOption.setText(showOption);                          //~1AfoI~
            break;                                                 //~1AfoI~
        }                                                          //~v1B6I~
        return rc;                                                 //~v1B6I~
    }                                                              //~v1B6I~
//******************                                               //~v1B6I~
	@Override                                                      //~v1B6I~
    protected boolean onClickHelp()                                //~v1B6I~
    {                                                              //~v1B6I~
//  	showDialogHelp(HELP_TITLE,HELP_TEXT);                      //~v1B6R~
//      new HelpDialog(Global.frame(),"pachiAjagoc");  //~1B1hI~   //~v1B6I~//~v1C2R~
        new HelpDialog(Global.frame(),"pachi");                    //~v1C2I~
        return false;	//no dismiss                               //~v1B6I~
    }                                                              //~v1B6I~
//************************************************************************//~v1B6I~
	public String getCommand()                       //~v1B6R~
	{                                                              //~v1B6I~
        String pgm;                                                //~v1B6R~
    //***************************                                  //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection:getCommand");      //~v1B6I~
		installDefaultProgram();//if first time                    //~v1B6R~
//  	pgm=Global.getParameter(PREFKEY_PROGRAM,DEFAULT_GTPSERVER_ORGNAME);//~v1B6R~//~v1C1R~
    	pgm=Global.getParameter(prefkey_PROGRAM,default_GTPSERVER_ORGNAME);//~v1C1R~
        pgm=pgm.trim();                                            //~v1B6I~
        if (pgm.equals(""))                                        //~v1B6I~
//      	pgm=DEFAULT_GTPSERVER_ORGNAME;                         //~v1B6R~//~v1C1R~
        	pgm=default_GTPSERVER_ORGNAME;                         //~v1C1I~
	    if (Dump.Y) Dump.println("GTPConnection:getCommand path="+pgm);//~v1B6R~
		return pgm;                                                //~v1B6I~
    }//getCommand                                                  //~v1B6R~
//************************************************************************//~v1BbI~
	public String getBook()                                        //~v1BbI~
	{                                                              //~v1BbI~
        String book;                                               //~v1BbR~
    //***************************                                  //~v1BbI~
        if (Dump.Y) Dump.println("GTPConnection:getBook");         //~v1BbI~
		installDefaultBook();//if first time                       //~v1BbI~
//  	book=Global.getParameter(PREFKEY_BOOKFILE,DEFAULT_BOOK_ORGNAME);//~v1BbR~//~v1C1R~
    	book=Global.getParameter(prefkey_BOOKFILE,DEFAULT_BOOK_ORGNAME);//~v1C1I~
        book=book.trim();                                          //~v1BbR~
        if (book.equals(""))                                       //~v1BbR~
        	book=DEFAULT_BOOK_ORGNAME;                             //~v1BbR~
	    if (Dump.Y) Dump.println("GTPConnection:getBook path="+book);//~v1BbR~
		return book;                                               //~v1BbR~
    }//getBook                                                     //~v1BbI~
//************************************************************************//~1Ag5I~
	protected void initcbPIE(ViewGroup PlayoutView)                //~1Ag5I~
    {                                                              //~1Ag5I~
        cbPIE=(CheckBox)PlayoutView.findViewById(R.id.GTP_PIE);    //~1Ag5I~
    	boolean optPIE=(AG.osVersion>=AG.LOLLIPOP);                //~1Ag5I~
    	cbPIE.setChecked(optPIE);                                  //~1Ag5I~
	}//initcbPIE                                                   //~1Ag5I~
//************************************************************************//~1AfuI~
	private String getPgmPIE(String Ppgmnonpie)                    //~1AfuI~
    {                                                              //~1AfuI~
    	String pgm=Ppgmnonpie;                                     //~1AfuI~
	    boolean optPIE=false;                                      //~1AfuI~
	    if (AG.isDebuggable)                                       //~1AfuI~
        {                                                          //~1AfuI~
	        optPIE=cbPIE.isChecked();                              //~1AfuI~
        }                                                          //~1AfuI~
        else                                                       //~1AfuI~
    		optPIE=(AG.osVersion>=AG.LOLLIPOP);                     //~1AfuI~
        if (optPIE)                                                //~1AfuI~
	    	pgm+=POSTFIX_PIE;                                      //~1AfuI~
        if (Dump.Y) Dump.println("GTPConnection:getPgmPIE in="+Ppgmnonpie+",out="+pgm+",osVersion="+AG.osVersion+",optPIE="+optPIE);//~1AfuI~
        return pgm;	                                               //~1AfuI~
    }                                                              //~1AfuI~
//************************************************************************//~v1B6I~
	public String getCommandParm()                   //~v1B6I~
	{                                                              //~v1B6I~
        String pgm;                                                //~v1B6I~
    //***************************                                  //~v1B6I~
        pgm=Program.getText().toString();                          //~v1B6I~
        pgm=pgm.trim();                                            //~v1B6I~
//  	Global.setParameter(PREFKEY_PROGRAM,pgm);  //save          //~v1B6I~//~v1C1R~
    	Global.setParameter(prefkey_PROGRAM,pgm);  //save          //~v1C1I~
//      if (pgm.equals("")||pgm.equals(DEFAULT_GTPSERVER_ORGNAME)) //~v1B6R~//~v1C1R~
        if (pgm.equals("")||pgm.equals(default_GTPSERVER_ORGNAME)) //~v1C1I~
//      	pgm=DEFAULT_GTPSERVER;                                 //~v1B6I~//~v1C1R~
        	pgm=default_GTPSERVER;                                 //~v1C1I~
//      if (pgm.equals(DEFAULT_GTPSERVER))                         //~v1B6I~//~v1C1R~
        if (pgm.equals(default_GTPSERVER))                         //~v1C1I~
//  		pgm=AjagoProp.getDataFileFullpath(DEFAULT_GTPSERVER);  //~v1B6I~//~v1C1R~
//  		pgm=AjagoProp.getDataFileFullpath(default_GTPSERVER);  //~1AfuR~
    		pgm=AjagoProp.getDataFileFullpath(getPgmPIE(default_GTPSERVER));//~1AfuI~
	    if (Dump.Y) Dump.println("GTPConnection:getCommand path="+pgm);//~v1B6I~
		return pgm;                                                //~v1B6I~
    }//getCommand                                                  //~v1B6I~
//************************************************************************//~v1BbI~
	public String getBookParm()                                    //~v1BbI~
	{                                                              //~v1BbI~
        String book;                                               //~v1BbI~
    //***************************                                  //~v1BbI~
        book=etBook.getText().toString();                          //~v1BbI~
        book=book.trim();                                           //~v1BbI~
//  	Global.setParameter(PREFKEY_BOOKFILE,book);  //save        //~v1BbR~//~v1C1R~
    	Global.setParameter(prefkey_BOOKFILE,book);  //save        //~v1C1I~
        if (book.equals("")||book.equals(DEFAULT_BOOK_ORGNAME))     //~v1BbI~
        	book=DEFAULT_BOOK;                                     //~v1BbI~
        else                                                       //~v1C1I~
        if (book.equals(DEFAULT_BOOK_ORGNAME2))                    //~v1C1I~
        	book=DEFAULT_BOOK2;                                    //~v1C1I~
        if (book.equals(DEFAULT_BOOK))                             //~v1BbI~
			book=AjagoProp.getDataFileFullpath(DEFAULT_BOOK);      //~v1BbI~
        else                                                       //~v1C1I~
        if (book.equals(DEFAULT_BOOK2))                            //~v1C1I~
			book=AjagoProp.getDataFileFullpath(DEFAULT_BOOK2);     //~v1C1I~
        File f=new File(book);                                     //~v1BbI~
        if (!f.exists())                                           //~v1BbI~
        {                                                          //~v1BbI~
	    	int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;//~v1BbI~
        	AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1BbI~
        			AG.resource.getString(R.string.GtpErr_Book_NotFound)+":\n"+book,flag);//~v1BbI~
            return null;                                           //~v1BbI~
        }                                                          //~v1BbI~
        if (f.length()>1000000) //over 1MB                         //~v1C8I~
        	AjagoView.showToastLong(R.string.GtpErr_Book_Warning); //~v1C8I~
	    if (Dump.Y) Dump.println("GTPConnection:getBook path="+book);//~v1BbI~
		return book;                                               //~v1BbI~
    }//getBookParm                                                 //~v1BbI~//~v1C1R~
//************************************************************************//~v1B6I~
//  private long installDefaultProgram()                           //~v1B6R~//~v1C1R~
            long installDefaultProgram()                           //~v1C1I~
	{                                                              //~v1B6I~
        String path,assetfnm;                                      //~v1B6I~
        long fsz,fsza;                                             //~v1B6I~
    //***************************                                  //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection:installDefaultProgram");//~v1B6I~
//        if (installedProgramSize!=0)                               //~v1BbI~//~v1C8R~
//            return installedProgramSize;                           //~v1BbI~//~v1C8R~
//      path=AjagoProp.getDataFileFullpath(DEFAULT_GTPSERVER);     //~v1B6I~//~v1C1R~
//      fsz=AjagoProp.getDataFileSize(DEFAULT_GTPSERVER);          //~v1B6I~//~v1C1I~
        long fszunzip=AjagoProp.getDataFileSize(default_GTPSERVER);//~v1C8I~
        path=AjagoProp.getDataFileFullpath("");                    //~v1C1I~
        fsz=AjagoProp.getPreference(prefkey_GTPSERVER_ZIPSIZE,0);  //~v1C1I~
//      assetfnm=DEFAULT_GTPSERVER+".png";	//avoid compress at making apk; if compressed openAssetFD failes by FileNotFound with reason it is compressed file//~v1B6R~//~v1C1R~
//      assetfnm=default_GTPSERVER+".zip";	//avoid compress at making apk; if compressed openAssetFD failes by FileNotFound with reason it is compressed file//~1AfsR~
        assetfnm=default_GTPSERVER+".png";	//avoid compress at making apk; if compressed openAssetFD failes by FileNotFound with reason it is compressed file//~1AfsR~
        fsza=AjagoProp.getAssetFileSize(assetfnm);                 //~v1B6I~
        if (fsza==-1)	//no asset file                            //~v1B6I~
        {                                                          //~v1B6I~
        	AjagoView.showToast(R.string.GtpErr_MissingAssetPGM,assetfnm);//~v1B6I~
        }                                                          //~v1B6I~
        else                                                       //~v1BbI~
//      if (fsz!=fsza)  //updated                                  //~v1B6I~//~v1C8R~
        if (fsz!=fsza||fszunzip<=0)  //updated                     //~v1C8I~
        {                                                          //~v1B6I~
        	alertUnzipping(assetfnm);                              //~1AfjI~
//          if (!AjagoProp.copyAssetToDataDir(assetfnm,DEFAULT_GTPSERVER))//png:avaoid compress(for >1MB file;cause IOException)//~v1B6I~//~v1C1R~
//            if (!AjagoProp.copyAssetToDataDir(assetfnm,default_GTPSERVER))//png:avaoid compress(for >1MB file;cause IOException)//~v1C1I~//~v1C8R~
//            {                                                      //~v1B6I~//~v1C8R~
//                AjagoView.showToast(R.string.AgnugoCopyFailed);    //~v1B6I~//~v1C8R~
//                return fsz;                                        //~v1B6I~//~v1C8R~
//            }                                                      //~v1B6I~//~v1C8R~
            if (Dump.Y) Dump.println("GTPConnection:copy to datadir success");//~v1B6I~
//          AjagoGMP.chmodX(path,"744");                           //~v1B6I~//~v1C1R~
			AjagoProp.unzipAsset(path,assetfnm,0777);              //~v1C1I~
            fsz=fsza;                                              //~v1B6I~
//          installedProgramSize=fsz;                              //~v1BbI~//~v1C8R~
	        AjagoProp.putPreference(prefkey_GTPSERVER_ZIPSIZE,(int)fsza);//~v1C1I~
            Sversion="";                                           //~1AfhI~
        	Global.setParameter(prefkey_GTPSERVER_VERSION,"");  //reset registered version//~1AfhI~
        }                                                          //~v1B6I~
//        installedProgramSize=fsz;                                //~v1C8R~
        if (Dump.Y) Dump.println("GTPConnection:installProgram normal return fsz="+fsz);//~v1B6R~
        return fsz;                                                //~v1B6R~
	}//installDefaultProgram                                       //~v1BbR~
//************************************************************************//~v1BbI~
//*unzip Abook.ra6.zip to Abook.dat                                //~v1BbI~
//************************************************************************//~v1BbI~
//  private long installDefaultBook()                              //~v1BbI~//~v1C1R~
            long installDefaultBook()                              //~v1C1I~
	{                                                              //~v1BbI~
        String path,assetfnm;                                      //~v1BbI~
        long fsz,fsza;                                             //~v1BbI~
    //***************************                                  //~v1BbI~
        if (Dump.Y) Dump.println("GTPConnection:installDefaultBook");//~v1BbI~
        if (installedBookSize!=0)                                  //~v1BbI~
        	return installedBookSize;                              //~v1BbI~
        path=AjagoProp.getDataFileFullpath("");                    //~v1BbR~
//      fsz=AjagoProp.getDataFileSize(DEFAULT_BOOK);               //~v1BbI~
        long fszunzip=AjagoProp.getDataFileSize(DEFAULT_BOOK);     //~1AftI~
//      fsz=AjagoProp.getPreference(PREFKEY_BOOKFILE_ZIPSIZE,0);   //~v1BbR~//~v1C1R~
        fsz=AjagoProp.getPreference(prefkey_BOOKFILE_ZIPSIZE,0);   //~v1C1I~
        assetfnm=DEFAULT_BOOK_ZIPFILE;	//.zip avoid compress at making apk; if compressed openAssetFD failes by FileNotFound with reason it is compressed file//~v1BbR~
        fsza=AjagoProp.getAssetFileSize(assetfnm);                 //~v1BbI~
        if (fsza==-1)	//no asset file                            //~v1BbI~
        {                                                          //~v1BbI~
        	AjagoView.showToast(R.string.GtpErr_MissingAssetBook,assetfnm);//~v1BbI~
        }                                                          //~v1BbI~
        else                                                       //~v1BbI~
//      if (fsz!=fsza)  //updated                                  //~1AftR~
        if (fsz!=fsza||fszunzip<=0)  //updated                     //~1AftI~
        {                                                          //~v1BbI~
			AjagoProp.unzipAsset(path,assetfnm,0766);               //~v1BbI~
//          AjagoProp.putPreference(PREFKEY_BOOKFILE_ZIPSIZE,(int)fsza);//~v1BbI~//~v1C1R~
	        AjagoProp.putPreference(prefkey_BOOKFILE_ZIPSIZE,(int)fsza);//~v1C1I~
            fsz=fsza;                                              //~v1BbI~
//          installedBookSize=fsz;                                 //~v1BbI~//~v1C8R~
        }                                                          //~v1BbI~
        installedBookSize=fsz;                                     //~v1C8I~
        if (Dump.Y) Dump.println("GTPConnection:installProgram normal return fsz="+fsz);//~v1BbI~
        return fsz;                                                //~v1BbI~
	}//installDefaultBook                                         //~v1BbI~//~v1C8R~
//************************************************************************//~v1B6I~
//*pachi resigned                                                  //~v1B6I~
//************************************************************************//~v1B6I~
	public void resign()                                           //~v1B6I~
	{                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection:resign");          //~v1B6I~
	    int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;   //~v1B6I~
        AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1B6I~
        			R.string.GtpErr_Computer_Resigned,flag);       //~v1B6I~
		gameoverReason=GAMEOVER_RESIGN_COMPUTER;                   //~v1B6I~
        F.B.resigned();                                              //~v1B6R~
	}                                                              //~v1B6I~
//************************************************************************//~v1B6I~
//*pachi resigned                                                  //~v1B6I~
//************************************************************************//~v1B6I~
	public void humanresign(int Pcolor/*1 or -1*/)                 //~v1B6I~
	{                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection:humanresign");     //~v1B6I~
		gameoverReason=GAMEOVER_RESIGN_HUMAN;                      //~v1B6I~
	}                                                              //~v1B6I~
//************************************************************************//~v1B6I~
//* action taken after gameover status                             //~v1B6I~
//************************************************************************//~v1B6I~
	public void gameovered(int Pstatus)                            //~v1B6I~
	{                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GTPConnection:gameovered status="+Pstatus);//~v1B6I~
	    int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;   //~v1B6I~
        AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1B6I~
        			R.string.GtpErr_Already_Gameovered,flag);      //~v1B6I~
	}                                                              //~v1B6I~
//************************************************************************//~v1B6I~
//* action taken after gameover status                             //~v1B6I~
//************************************************************************//~v1B6I~
	public void chkpassdoubled(boolean Phuman,boolean Ppass)       //~v1B6I~
	{                                                              //~v1B6I~
        if (Dump.Y) Dump.println("chkpass doubled human="+Phuman+",pass="+Ppass);//~v1B6I~
        if (swPassed && Ppass)                                     //~v1B6I~
        {                                                          //~v1B6I~
			gameoverReason=GAMEOVER_BOTH_PASSED;                   //~v1B6I~
	    	int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;//~v1B6I~
        	AjagoAlert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~v1B6I~
        			R.string.GtpErr_Gameover_By_Both_Passed,flag); //~v1B6I~
        }                                                          //~v1B6I~
        else                                                       //~v1B6I~
	        swPassed=Ppass;                                        //~v1B6I~
	}                                                              //~v1B6I~
//************************************************************************//~v1B6I~
//*                                                                //~v1B6I~
//************************************************************************//~v1B6I~
	public void resetGameover()                                    //~v1B6I~
	{                                                              //~v1B6I~
		switch(gameoverReason)                                     //~v1B6I~
		{                                                          //~v1B6I~
		case GAMEOVER_BOTH_PASSED:                                 //~v1B6I~
        	break;                                                 //~v1B6I~
		case GAMEOVER_RESIGN_COMPUTER:                             //~v1B6I~
        	break;                                                 //~v1B6I~
		case GAMEOVER_RESIGN_HUMAN:                                //~v1B6I~
	        F.B.resetresigned(); 
		}//~v1B6I~
	}                                                              //~v1B6I~
//************************************************************************//~v1C0I~
//*                                                                //~v1C0I~
//************************************************************************//~v1C0I~
//  private String getRandomSeed()                                 //~v1C0I~//~v1C1R~
            String getRandomSeed()                                 //~v1C1I~
	{                                                              //~v1C0I~
    	long ms=System.currentTimeMillis();                           //~v1C0I~
        long ns=System.nanoTime();                                 //~v1C0I~
        int seed=(int)(ms*ns);                                      //~v1C0I~
        if (seed==0)                                               //~v1C0I~
	        seed=(int)(ns);                                        //~v1C0I~
        if (seed<0)                                                //~v1C0I~
        	seed=-seed;                                            //~v1C0I~
		return Integer.toString(seed);                             //~v1C0I~
	}                                                              //~v1C0I~
    //********************************************                 //~v1DhI~
    protected void updateoption(EditText PetOption)                //~v1DhI~
    {                                                              //~v1DhI~
    	new GTPOptionUpdate(this,PetOption);                       //~v1DhR~
    }                                                              //~v1DhI~
    //********************************************                 //~1AfjI~
    private void alertUnzipping(String Pzipfnm)                    //~1AfjI~
    {                                                              //~1AfjI~
    	AjagoView.showToastLong(AG.resource.getString(R.string.ProgressExpandGTPsoft,Pzipfnm));//~1AfjI~
    }                                                              //~1AfjI~
    //********************************************                 //~1AfoI~
	protected void initRGPlaymode(ViewGroup PlayoutView)           //~1AfoR~
    {                                                              //~1AfoI~
		rgPlaymode=new ButtonRG(PlayoutView,4);                    //~1AfoI~
        rgPlaymode.add(PLAYMODE_PLAYOUT,R.id.GTPRAY_PLAYMODE_PLAYOUT);//~1AfoI~
        rgPlaymode.add(PLAYMODE_CONSTTIME,R.id.GTPRAY_PLAYMODE_CONSTTIME);//~1AfoI~
        rgPlaymode.add(PLAYMODE_ALLTIME,R.id.GTPRAY_PLAYMODE_ALLTIME);//~1AfoI~
        rgPlaymode.add(PLAYMODE_TIMESETTING,R.id.GTPRAY_PLAYMODE_TIMESETTING);//~1AfoI~
//      rgPlaymode.setDefault(PLAYMODE_CONSTTIME);                 //~1AfoI~//+1Aj1R~
        rgPlaymode.setDefault(PLAYMODE_DEFAULT);                   //+1Aj1I~
                                                                   //~1AfoI~
        Playmode=Global.getParameter(prefkey_PLAYMODE,PLAYMODE_DEFAULT);//~1AfoM~
        rgPlaymode.setChecked(Playmode);                           //~1AfoM~
                                                                   //~1AfoM~
        etPlayout=(EditText)PlayoutView.findViewById(R.id.GTPRAY_PLAYOUT);//~1AfoM~
        Playout=Global.getParameter(prefkey_PLAYOUT,0);            //~1AfoM~
        if (Playout<=0)                                            //~1AfoM~
        	Playout=PLAYOUT_DEFAULT;                               //~1AfoM~
	    etPlayout.setText(Integer.toString(Playout));              //~1AfoM~
                                                                   //~1AfoM~
        etConsttime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_CONSTTIME);//~1AfoM~
        Consttime=Global.getParameter(prefkey_CONSTTIME,0);        //~1AfoM~
        if (Consttime<=0)                                          //~1AfoM~
        	Consttime=CONSTTIME_DEFAULT;                           //~1AfoM~
        etConsttime.setText(Integer.toString(Consttime));          //~1AfoM~
                                                                   //~1AfoM~
        etAlltime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_ALLTIME);//~1AfoM~
        Alltime=Global.getParameter(prefkey_ALLTIME,0);            //~1AfoM~
        if (Alltime<=0)                                            //~1AfoM~
        	Alltime=ALLTIME_DEFAULT;                               //~1AfoM~
	    etAlltime.setText(Integer.toString(Alltime));              //~1AfoM~
    }                                                              //~1AfoI~
}
