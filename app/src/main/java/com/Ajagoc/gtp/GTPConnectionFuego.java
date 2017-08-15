//*CID://+1Aj3R~:                                   update#=  227; //+1Aj3R~
//***********************************************************************//~@@@1I~
//1Aj3 2017/02/04 Bug:unmatch default and buttonrg default         //~1Ag3I~
//1Ag0 2014/10/05 displaying bot version number on menu delays until next restart(menu itemname is set at start and not changed)//~1Ag0I~
//1Afo 2016/09/28 pachi dialog change                              //~1AfoI~
//1Afn 2016/09/26 fuego;time_settings ignores timelimit(sec per move),so ignore time_settings if timelimit is set//~1AfnI~
//1Afm 2016/09/26 fuego;not genmove but reg_genmove is required for timelimit per move.//~1AfmI~
//1Afk 2016/09/26 set other option of fuego to dialog              //~1AfkI~
//1Afh 2016/09/24 drop yourname from title of computer mach        //~1AfhI~
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


import android.view.ViewGroup;
import android.widget.EditText;

import com.Ajagoc.AjagoProp;
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

//** fuego --help  *****************************************       //~1AfhI~
//Usage: fuego [options] [input files]                             //~1AfhI~
//Options:                                                         //~1AfhI~
//  --config arg          execuate GTP commands from file before starting main//~1AfhI~
//                        command loop                             //~1AfhI~
//  --help                Displays this help and exit              //~1AfhI~
//  --maxgames arg (=-1)  make clear_board fail after n invocations//~1AfhI~
//  --nobook              don't automatically load opening book    //~1AfhI~
//  --nohandicap          don't support handicap commands          //~1AfhI~
//  --quiet               don't print debug messages               //~1AfhI~
//  --srand arg (=0)      set random seed (-1:none, 0:time(0))     //~1AfhI~
//  --size arg (=0)       initial (and fixed) board size           //~1AfhI~
                                                                   //~1AfhI~
//** list_commands response ****************************           //~1AfhI~
// all_legal                                                       //~1AfhM~
// all_move_values                                                 //~1AfhM~
// autobook_additive_cover                                         //~1AfhM~
// autobook_close                                                  //~1AfhM~
// autobook_counts                                                 //~1AfhM~
// autobook_cover                                                  //~1AfhM~
// autobook_expand                                                 //~1AfhM~
// autobook_export                                                 //~1AfhM~
// autobook_import                                                 //~1AfhM~
// autobook_load_disabled_lines                                    //~1AfhM~
// autobook_mainline                                               //~1AfhM~
// autobook_merge                                                  //~1AfhM~
// autobook_open                                                   //~1AfhM~
// autobook_param                                                  //~1AfhM~
// autobook_priority                                               //~1AfhM~
// autobook_refresh                                                //~1AfhM~
// autobook_save                                                   //~1AfhM~
// autobook_scores                                                 //~1AfhM~
// autobook_state_info                                             //~1AfhM~
// autobook_truncate_by_depth                                      //~1AfhM~
// boardsize                                                       //~1AfhM~
// book_add                                                        //~1AfhM~
// book_clear                                                      //~1AfhM~
// book_delete                                                     //~1AfhM~
// book_info                                                       //~1AfhM~
// book_load                                                       //~1AfhM~
// book_moves                                                      //~1AfhM~
// book_position                                                   //~1AfhM~
// book_save                                                       //~1AfhM~
// book_save_as                                                    //~1AfhM~
// cgos-gameover                                                   //~1AfhM~
// clear_board                                                     //~1AfhM~
// cputime                                                         //~1AfhM~
// cputime_reset                                                   //~1AfhM~
// echo                                                            //~1AfhM~
// echo_err                                                        //~1AfhM~
// final_score                                                     //~1AfhM~
// final_status_list                                               //~1AfhM~
// fixed_handicap                                                  //~1AfhM~
// fuego-license                                                   //~1AfhM~
// genmove                                                         //~1AfhM~
// get_komi                                                        //~1AfhM~
// get_random_seed                                                 //~1AfhM~
// gg-undo                                                         //~1AfhM~
// go_board                                                        //~1AfhM~
// go_clock                                                        //~1AfhM~
// go_param                                                        //~1AfhM~
// go_param_rules                                                  //~1AfhM~
// go_param_timecontrol                                            //~1AfhM~
// go_player_board                                                 //~1AfhM~
// go_point_info                                                   //~1AfhM~
// go_point_numbers                                                //~1AfhM~
// go_rules                                                        //~1AfhM~
// go_safe                                                         //~1AfhM~
// go_safe_dame_static                                             //~1AfhM~
// go_safe_gfx                                                     //~1AfhM~
// go_safe_winner                                                  //~1AfhM~
// go_sentinel_file                                                //~1AfhM~
// go_set_info                                                     //~1AfhM~
// gogui-analyze_commands                                          //~1AfhM~
// gogui-interrupt                                                 //~1AfhM~
// gogui-play_sequence                                             //~1AfhM~
// gogui-setup                                                     //~1AfhM~
// gogui-setup_player                                              //~1AfhM~
// is_legal                                                        //~1AfhM~
// kgs-genmove_cleanup                                             //~1AfhM~
// kgs-time_settings                                               //~1AfhM~
// known_command                                                   //~1AfhM~
// komi                                                            //~1AfhM~
// list_commands                                                   //~1AfhM~
// list_stones                                                     //~1AfhM~
// loadsgf                                                         //~1AfhM~
// name                                                            //~1AfhM~
// pid                                                             //~1AfhM~
// place_free_handicap                                             //~1AfhM~
// play                                                            //~1AfhM~
// protocol_version                                                //~1AfhM~
// quiet                                                           //~1AfhM~
// quit                                                            //~1AfhM~
// reg_genmove                                                     //~1AfhM~
// reg_genmove_toplay                                              //~1AfhM~
// savesgf                                                         //~1AfhM~
// set_free_handicap                                               //~1AfhM~
// set_random_seed                                                 //~1AfhM~
// sg_compare_float                                                //~1AfhM~
// sg_compare_int                                                  //~1AfhM~
// sg_debugger                                                     //~1AfhM~
// sg_exec                                                         //~1AfhM~
// sg_param                                                        //~1AfhM~
// showboard                                                       //~1AfhM~
// time_lastmove                                                   //~1AfhM~
// time_left                                                       //~1AfhM~
// time_settings                                                   //~1AfhM~
// uct_bounds                                                      //~1AfhM~
// uct_default_policy                                              //~1AfhM~
// uct_estimator_stat                                              //~1AfhM~
// uct_gfx                                                         //~1AfhM~
// uct_max_memory                                                  //~1AfhM~
// uct_moves                                                       //~1AfhM~
// uct_param_globalsearch                                          //~1AfhM~
// uct_param_player                                                //~1AfhM~
// uct_param_policy                                                //~1AfhM~
// uct_param_rootfilter                                            //~1AfhM~
// uct_param_search                                                //~1AfhM~
// uct_patterns                                                    //~1AfhM~
// uct_policy_moves                                                //~1AfhM~
// uct_prior_knowledge                                             //~1AfhM~
// uct_rave_values                                                 //~1AfhM~
// uct_root_filter                                                 //~1AfhM~
// uct_savegames                                                   //~1AfhM~
// uct_savetree                                                    //~1AfhM~
// uct_score                                                       //~1AfhM~
// uct_sequence                                                    //~1AfhM~
// uct_stat_player                                                 //~1AfhM~
// uct_stat_player_clear                                           //~1AfhM~
// uct_stat_policy                                                 //~1AfhM~
// uct_stat_policy_clear                                           //~1AfhM~
// uct_stat_search                                                 //~1AfhM~
// uct_stat_territory                                              //~1AfhM~
// uct_value                                                       //~1AfhM~
// uct_value_black                                                 //~1AfhM~
// undo                                                            //~1AfhM~
// version                                                         //~1AfhM~

//*Fuego User Manual                                               //~1AfhI~
                                                                   //~1AfhI~
//Fuego is a Go playing engine, which communicates with a user interface using//~1AfhI~
//the Go Text Protocol (GTP).                                      //~1AfhI~
//You need a graphical user interface that supports GTP to play against Fuego.//~1AfhI~
//The recommended one is <a href="http://gogui.sf.net/">GoGui</a> because//~1AfhI~
//Fuego uses some of the special features of GoGui.                //~1AfhI~
//The most important feature is the Analyze Command window, which makes//~1AfhI~
//engine-specific GTP extension commands accessible in the user interface.//~1AfhI~
//These commands allow to query and display internal information about the//~1AfhI~
//move generation and to set engine parameters.                    //~1AfhI~
//Some of these are only of interest to engine developers, some of them might//~1AfhI~
//also be useful for users.                                        //~1AfhI~
                                                                   //~1AfhI~
//Fuego tries to use a reasonable default configuration with respect to memory//~1AfhI~
//usage and usage of parallel search on a multi-core or multi-CPU machine.//~1AfhI~
//Parameters can be changed with certain commands in the analyze window of//~1AfhI~
//GoGui, but this will affect only the current session.            //~1AfhI~
//To optimize the default configuration for a certain machine in a persistent//~1AfhI~
//way, you can write a text file with GTP commands (e.g. configfile)//~1AfhI~
//and change the command line for invocation of Fuego in the user interface to//~1AfhI~
//fuego --config configfile. Here is an example of such a configuration//~1AfhI~
//file:                                                            //~1AfhI~
//******                                                           //~1AfhI~
//uct_param_search number_threads 4                                //~1AfhI~
//uct_param_search lock_free 1                                     //~1AfhI~
//uct_max_memory 6000000000                                        //~1AfhI~
//uct_param_player reuse_subtree 1                                 //~1AfhI~
//uct_param_player ponder 1                                        //~1AfhI~
//******                                                           //~1AfhI~
//The meaning of these GTP commands is:                            //~1AfhI~
//uct_param_search number_threads                                  //~1AfhI~
//    The number of threads to use.                                //~1AfhI~
//    The default is the number of hardware threads available on the current machine.//~1AfhI~
                                                                   //~1AfhI~
//uct_param_search lock_free                                       //~1AfhI~
//    Whether to enable lock-free multithreading.                  //~1AfhI~
//    The default is 1 (=yes) for modern Intel or AMD CPUs.        //~1AfhI~
//    This should be enabled on modern Intel or AMD CPUs (with IA-32 and Intel-64//~1AfhI~
//    architecture) if more than two threads are used.             //~1AfhI~
//    Note that without lock-free search the performance of Fuego can even decrease//~1AfhI~
//    if you use more threads.                                     //~1AfhI~
//    The maximum number of threads that can be used without a decrease of//~1AfhI~
//    performance, if the lock-free mode is not used, depends on the board size.//~1AfhI~
//uct_max_memory                                                   //~1AfhI~
//    Determines the maximum amount of memory in the search tree, and thus the//~1AfhI~
//    maximum memory to use.                                       //~1AfhI~
//    The default is half of the total memory available on the system.//~1AfhI~
//    The example above is using 6GB. Fuego maintains two search trees internally, so//~1AfhI~
//    setting max_memory to 512MB gives 256MB to each tree. The second tree is used//~1AfhI~
//    for work space if using reuse_subtree, or if the search tree fills up and nodes//~1AfhI~
//    with small counts are removed.                               //~1AfhI~
//uct_param_player reuse_subtree                                   //~1AfhI~
//    Whether to reuse the reusable part of the tree from a previous move//~1AfhI~
//    generation.                                                  //~1AfhI~
//    The default is 1 (=yes).                                     //~1AfhI~
//    Setting this to 1 is required if pondering is used, but it also gives//~1AfhI~
//    a small improvement in playing strength if pondering is not used.//~1AfhI~
                                                                   //~1AfhI~
//uct_param_player ponder                                          //~1AfhI~
//    Whether to continue the search while waiting for the opponent's move.//~1AfhI~
//    The default is 0 (=no).                                      //~1AfhI~
//    If this is set to 1, uct_param_player reuse_subtree must also be//~1AfhI~
//    enabled. Note that with some versions of Fuego built with Cygwin pondering//~1AfhI~
//    does not work and Fuego might hang on subsequent commands if it is enabled.//~1AfhI~
//Game-specific Settings                                           //~1AfhI~
//    This a list of some game-specific parameters, which can be changed in GoGui's//~1AfhI~
//    analyze command window.                                      //~1AfhI~
//Go Param                                                         //~1AfhI~
//    There is one parameter that is interesting to users:         //~1AfhI~
//    Timelimit is the fixed time limit in seconds to use for a move//~1AfhI~
//    generation, if no time settings are used for the game.       //~1AfhI~
//    The default is 10.                                           //~1AfhI~
                                                                   //~1AfhI~
//Go Param Rules                                                   //~1AfhI~
//    Change the rules used in the game.                           //~1AfhI~
//    Note that entering text in the rules text entry of GoGui's game info dialog//~1AfhI~
//    is for storing this information in the file only.            //~1AfhI~
//    It is not transmitted to the Go program because there is no GTP standard//~1AfhI~
//    command for setting the rules.                               //~1AfhI~
//    What rules are used by Fuego depends only on the settings in Go Param Rules.//~1AfhI~
                                                                   //~1AfhI~
//Watching Fuego's Thinking Process                                //~1AfhI~
//    You can enable the display of Fuego's thinking process by setting//~1AfhI~
//    Live gfx in the Uct Param Search analyze command to          //~1AfhI~
//    Counts or Sequence.                                          //~1AfhI~
//    Counts will show the current exploration counts of the moves as labels on the//~1AfhI~
//    board and the currently best move using a half-transparent stone.//~1AfhI~
//    Sequence will show the current best sequence of moves using half-transparent//~1AfhI~
//    stones.                                                      //~1AfhI~
//    The display interval can be configured with Live gfx interval.//~1AfhI~
//    Another display mode is Live gfx in the                      //~1AfhI~
//    Uct Param GlobalSearch analyze command.                      //~1AfhI~
//    It shows the statistics for ownership of each point averaged over all end//~1AfhI~
//    positions of the simulations.                                //~1AfhI~
//    This command requires that Territory statistics in           //~1AfhI~
//    Uct Param GlobalSearch is also enabled.                      //~1AfhI~
//****************************************************************   //~1AfhI~
//class OkAdapter                                                  //~v1C1R~
//{   public void gotOk() {}                                       //~v1C1R~
//}                                                                //~v1C1R~
                                                                   //~1AfhI~

public class GTPConnectionFuego extends GTPConnection                   //~v1B6I~//~v1C1R~
	implements GTPInterface
{                                                                  //~v1B6R~
//  private static final int LAYOUT=R.layout.dialoggtpconnection;  //~v1B6I~//~v1C1R~
    private static final int LAYOUT=R.layout.dialoggtpconnection_fuego;//~1AfkI~
//  private static final int TITLE =R.string.DialogTitle_gtpconnection;//~v1B6I~//~v1C1R~
//  private static final String DEFAULT_GTPSERVER="Apachi";        //~v1B6R~//~v1C1R~
    private static final String DEFAULT_GTPSERVER="Afuego";        //~v1C1R~
//  private static final String DEFAULT_GTPSERVER_ORGNAME="pachi"; //~v1B6I~//~v1C1R~
//  private static final String DEFAULT_GTPSERVER_ORGNAME="fuego"; //~1Ag0R~
    public  static final String DEFAULT_GTPSERVER_ORGNAME="fuego"; //~1Ag0I~
    private static final String FUEGO_RULE_JAPANESE="go_rules japanese";//~v1C1R~
//  private static final String FUEGO_BOOK_LOAD="book_load";       //~v1C2I~//~v1C8R~
    private static final String FUEGO_VERSION="1.1";               //~v1C1I~
//  private static final String PREFKEY_PROGRAM="GTPprogram";      //~v1B6I~//~v1C1R~
    private static final String PREFKEY_PROGRAM="GTPprogramFuego"; //~v1C1R~
//  private static final String PREFKEY_PGMOPTION="GTPprogramoption";//~v1B6R~//~v1C1R~
    private static final String PREFKEY_PGMOPTION="GTPprogramoptionFuego";//~v1C1R~
//  private static final String PREFKEY_BOARDSIZE="GTPboardsize";  //~v1B6I~//~v1C1R~
    private static final String PREFKEY_BOARDSIZE="GTPboardsizeFuego";//~v1C1R~
//  private static final String PREFKEY_YOURNAME="GTPyourname";    //~v1B6I~//~v1C1R~
    private static final String PREFKEY_YOURNAME="GTPyournameFuego";//~v1C1R~
//  private static final String PREFKEY_TIMESETTINGMAIN="GTPtimesettingMain";//~v1B6R~//~v1C1R~
    private static final String PREFKEY_TIMESETTINGMAIN="GTPtimesettingMainFuego";//~v1C1R~
//  private static final String PREFKEY_TIMESETTINGEXTRA="GTPtimesettingExtra";//~v1B6I~//~v1C1R~
    private static final String PREFKEY_TIMESETTINGEXTRA="GTPtimesettingExtraFuego";//~v1C1R~
//  private static final String PREFKEY_TIMESETTINGMOVE="GTPtimesettingMove";//~v1B6I~//~v1C1R~
    private static final String PREFKEY_TIMESETTINGMOVE="GTPtimesettingMoveFuego";//~v1C1R~
//  private static final String PREFKEY_HANDICAP="GTPhandicap";    //~v1B6I~//~v1C1R~
    private static final String PREFKEY_HANDICAP="GTPhandicapFuego";//~v1C1R~
//  private static final String PREFKEY_WHITE="GTPyouarewhite";    //~v1B6R~//~v1C1R~
    private static final String PREFKEY_WHITE="GTPyouarewhiteFuego";//~v1C1R~
//  private static final String PREFKEY_RULE="GTPrule";            //~v1B6I~//~v1C1R~
    private static final String PREFKEY_RULE="GTPruleFuego";       //~v1C1R~
//  private static final String PREFKEY_KOMI="GTPkomi";            //~v1B6I~//~v1C1R~
    private static final String PREFKEY_KOMI="GTPkomiFuego";       //~v1C1R~
//  private static final String PREFKEY_VERBOSE="GTPverbose";      //~v1B6I~//~v1C1R~
    private static final String PREFKEY_VERBOSE="GTPverboseFuego"; //~v1C1R~
//  private static final String PREFKEY_BOOKFILE="GTPbookFile";    //~v1BbI~//~v1C1R~
    private static final String PREFKEY_BOOKFILE="GTPbookFileFuego";//~v1C1R~
//  private static final String PREFKEY_BOOKUSE="GTPbookUse";      //~v1BbI~//~v1C1R~
    private static final String PREFKEY_BOOKUSE="GTPbookUseFuego"; //~v1C1R~
//  private static final String PREFKEY_BOOKFILE_ZIPSIZE="GTPbookZipSize";//~v1BbI~//~v1C1R~
    private static final String PREFKEY_BOOKFILE_ZIPSIZE="GTPbookZipSizeFuego";//~v1C1R~
    private static final String PREFKEY_GTPSERVER_ZIPSIZE="GTPserverZipSizeFuego";//~v1C1R~//~v1C2R~
    public  static final String PREFKEY_GTPSERVER_VERSION="GTPserverVersionFuego";//~1AfhR~
    private static final String PREFKEY_PONDERING="GTPponderingFuego";//~1AfkI~
    private static final String PREFKEY_PLAYOUT="GTPplayoutFuego"; //~1AfoI~
    private static final String PREFKEY_CONSTTIME="GTPconsttimeFuego";//~1AfkI~
    private static final String PREFKEY_ALLTIME="GTPalltimeFuego"; //~1AfoI~
    private static final String PREFKEY_THREAD="GTPThreaFuego";    //~1AfkI~
    private static final String PREFKEY_PLAYMODE="GTPplaymodeFuego";//~1AfoI~
                                                                   //~1AfkI~
    private static final String OPT_PONDERING       ="uct_param_player ponder 1";//~1AfkI~
    private static final String OPT_TIMELIMIT       ="go_param timelimit ";//~1AfkI~
    private static final String OPT_THREAD          ="uct_param_search number_threads ";//~1AfkI~
    private static final String OPT_NODEBUGMSG      =" --quiet";    //~1AfoI~
    private static final int CONSTTIME_DEFAULT=10;                 //~1AfoR~
    private static final int PLAYMODE_DEFAULT=PLAYMODE_CONSTTIME;  //~1AfoI~
                                                                   //~v1BbI~
    private static final String FUEGO_OPTION_CONFIG=" --config";   //~1AfoR~
    private static final String FUEGO_CFG_FILENAME="Afuego.cfg";    //~v1C2I~
    public static String Sversion="";                              //~1AfhI~
    private String book;                                           //~v1C2I~
    private String cfgFilename;                                    //~1AfoI~

//  public GTPConnection (Frame f)                                 //~v1C1R~
    public GTPConnectionFuego(Frame f)                             //~v1C1R~
    {                                                              //~v1B6I~
//      super(LAYOUT);                                             //~v1B6I~//~v1C1R~
//      super(f);                                                  //~1AfkR~
        super(f,LAYOUT);                                           //~1AfkI~
//      String title=AG.resource.getString(TITLE);                 //~v1B6R~//~v1C1R~
//  	showDialog(title);     //callback setupDialogExtend()      //~v1B6I~//~v1C1R~
	}
//*********                                                        //~1725I~//~v1B6I~
	@Override   //GTPConnection                                    //~v1C1R~
    void initVariable()                                            //~v1C1R~
    {                                                              //~v1C1R~
	    default_GTPSERVER_ORGNAME=DEFAULT_GTPSERVER_ORGNAME;       //~v1C1R~
	    default_GTPSERVER=DEFAULT_GTPSERVER;                       //~v1C1R~
        prefkey_PROGRAM=PREFKEY_PROGRAM;                           //~v1C1R~
        prefkey_TIMESETTINGMAIN=PREFKEY_TIMESETTINGMAIN;           //~v1C1R~
        prefkey_TIMESETTINGEXTRA=PREFKEY_TIMESETTINGEXTRA;         //~v1C1R~
        prefkey_TIMESETTINGMOVE=PREFKEY_TIMESETTINGMOVE;           //~v1C1R~
        prefkey_BOOKFILE=PREFKEY_BOOKFILE;                         //~v1C1R~
        prefkey_BOOKFILE_ZIPSIZE=PREFKEY_BOOKFILE_ZIPSIZE;         //~v1C1I~
    	prefkey_PGMOPTION=PREFKEY_PGMOPTION;                       //~v1C2I~
    	prefkey_VERBOSE=PREFKEY_VERBOSE;                           //~v1C2I~
        prefkey_GTPSERVER_ZIPSIZE=PREFKEY_GTPSERVER_ZIPSIZE;       //~v1C2I~
        prefkey_HANDICAP=PREFKEY_HANDICAP;                         //~v1C2I~
        prefkey_BOARDSIZE=PREFKEY_BOARDSIZE;                       //~v1C2I~
        prefkey_YOURNAME=PREFKEY_YOURNAME;                         //~v1C2I~
        prefkey_KOMI=PREFKEY_KOMI;                                 //~v1C2I~
        prefkey_BOOKUSE=PREFKEY_BOOKUSE;                           //~v1C2I~
        prefkey_WHITE=PREFKEY_WHITE;                               //~v1C2I~
        prefkey_RULE=PREFKEY_RULE;                                 //~v1C2I~
        prefkey_GTPSERVER_VERSION=PREFKEY_GTPSERVER_VERSION;       //~1AfhI~
        prefkey_PLAYMODE=PREFKEY_PLAYMODE;                         //~1AfoI~
        prefkey_CONSTTIME=PREFKEY_CONSTTIME;                       //~1AfoI~
        prefkey_ALLTIME=PREFKEY_ALLTIME;                           //~1AfoI~
        prefkey_PLAYOUT=PREFKEY_PLAYOUT;                           //~1AfoI~
        prefkey_THREAD=PREFKEY_THREAD;                             //~1AfoI~
        prefkey_PONDERING=PREFKEY_PONDERING;                       //~1AfoI~
                                                                   //~1AfhI~
        Sversion=Global.getParameter(PREFKEY_GTPSERVER_VERSION,"");//~1AfhI~
    }                                                              //~v1C1R~
//**************************************************************   //~v1B6I~
//	GTPConnector C;                                                //~v1C1R~
//  GTPGoFrame F;                                                  //~v1C1R~
//  GTPConnection Co=this;                                         //~v1C1R~
    GTPConnectionFuego Co=this;                                    //~v1C1R~
//***************************************************************************//~v1B6R~
	@Override   //GTPConnection                                    //~v1C1R~
//  private boolean play()                                         //~v1B6R~//~v1C1R~
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
        String yn=YourNameField.getText().toString();              //~1AfoM~
        if (yn!=null && !yn.equals(""))                            //~1AfoM~
        {                                                          //~1AfoM~
        	YourName=yn;                                           //~1AfoM~
        	Global.setParameter(PREFKEY_YOURNAME,YourName);        //~1AfoM~
        }                                                          //~1AfoM~
                                                                   //~1AfoI~
        BoardSize=AjagoUtils.str2int(BoardSizeField.getText().toString(),DEFAULT_BOARDSIZE);//~1AfoM~
        Global.setParameter(prefkey_BOARDSIZE,BoardSize);          //~v1B6R~//~1AfoM~
                                                                   //~1AfoI~
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
        Global.setParameter(prefkey_KOMI,Komi);            //~v1B6I~//~1AfoM~
                                                                   //~1AfoI~
        MyColor=White.isChecked()?GTPConnector.WHITE:GTPConnector.BLACK;//~1AfoM~
        Global.setParameter(prefkey_WHITE,MyColor);                //~1AfoM~
                                                                   //~1AfoI~
//      if (btnid==R.id.GTP_RULE_JAPANESE)                         //~1AfoM~
        if (cbRule.isChecked())                                    //~1AfoM~
        	Rules=RULE_JAPANESE;                                   //~1AfoM~
        else                                                       //~1AfoM~
        	Rules=RULE_CHINESE;                                    //~1AfoM~
        Global.setParameter(prefkey_RULE,Rules);                   //~v1B6M~//~1AfoM~
                                                                   //~1AfoI~
		String cfgopt=getPgmOption();	//Consttime,Ponder,Thread,otherOption and other cmd option(not config)//~1AfoR~
                                                                   //~v1BbI~
        useBook=cbBook.isChecked();                                //~v1BbI~
        Global.setParameter(prefkey_BOOKUSE,useBook);              //~v1BbI~//~v1C2R~
//  	String book=null;                                          //~v1BbI~//~v1C2R~
        if (useBook)                                               //~v1BbI~
        {                                                          //~v1BbI~
//            if (option.indexOf("--nobook")>=0)                   //~1AfoR~
//            {                                                    //~1AfoR~
//                AjagoView.showToastLong(R.string.GtpErr_NoBook_OptionParm);//~1AfoR~
//                return false;                                    //~1AfoR~
//            }                                                    //~1AfoR~
    		book=getBookParm();                                    //~1AfoR~
            if (book==null)                                        //~v1BbI~
            	return false;                                      //~v1BbI~
//      	AjagoView.showToastLong(R.string.GtpErr_Book_Warning); //~v1C1R~//~v1C8R~
        }                                                           //~v1B6I~//~v1BbR~
//***********************                                          //~1AfoI~
        if (!cfgopt.equals(""))                                    //~1AfoM~
	        text+=FUEGO_OPTION_CONFIG+" "+cfgFilename;             //~1AfoR~
      if (Playmode!=PLAYMODE_TIMESETTING)                          //~1AfoR~
	    strTimesettings="";                                        //~1AfoM~
	    if (!AG.isDebuggable)                                      //~1AfoM~
        {                                                          //~1AfoI~
        	Verbose=false;                                         //~1AfoM~
        }                                                          //~1AfoI~
        else                                                       //~1AfoM~
        {                                                          //~1AfoM~
        	Verbose=cbVerbose.isChecked();                         //~1AfoM~
//      	Global.setParameter(PREFKEY_VERBOSE,Verbose);          //~v1B6R~//~1AfoM~
        	Global.setParameter(prefkey_VERBOSE,Verbose);          //~1AfoM~
        }                                                          //~1AfoM~
                                                                   //~v1B6I~
//      if (!option.equals(""))                                    //~1AfoR~
//      	text+=" "+option;                                      //~1AfoR~
        if (!cmdOption.equals(""))                                 //~1AfoI~
        	text+=" "+cmdOption;                                   //~1AfoI~
        if (useBook)                                               //~v1BbI~
//      	text+=" -f "+book;                                     //~v1C1R~
            ;                                                      //~v1C1I~
        else                                                       //~v1C1R~
        {                                                          //~v1C2I~
//      	if (option.indexOf("--nobook")<0)                      //~1AfoR~
	            text+=" --nobook";                                     //~v1C1R~//~v1C2R~
        }                                                          //~v1C2I~
//      text+=" -s "+getRandomSeed();                              //~v1C1R~
//      text+=" --srand "+getRandomSeed();  //default is 0(use time)//~1AfoR~
        if (!Verbose)                                              //~1AfoI~
        	text+=OPT_NODEBUGMSG;                                  //~1AfoI~
        if (swShowOption)                                          //~1AfoI~
        {                                                          //~1AfoI~
            if (cfgopt.equals(""))                                 //~1AfoI~
	        	showOption=text;                                   //~1AfoI~
            else                                                   //~1AfoI~
	        	showOption=text+";"+FUEGO_CFG_FILENAME+"=("+cfgopt+")";//~1AfoR~
        	return false;          //called by show button         //~1AfoI~
        }                                                          //~1AfoI~
//      C=new GTPConnector(text,this);                             //~1AfmR~
//      C=new GTPConnector(text,this,Consttime>0/*use reg_genmove*/);//~1AfmR~
        C=new GTPConnector(text,this,false/*use reg_genmove*/);    //reg_genmove returns fiex pos always//~1AfmI~
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
//***************************************************************************//~1AfoI~
	@Override                                                      //~1AfoI~
	protected String getPlaymode()                                 //~1AfoI~
    {
    	String strPlaymode="";                                     //~1AfoI~
        Playmode=rgPlaymode.getChecked();                          //~1AfoI~
        Global.setParameter(prefkey_PLAYMODE,Playmode);            //~1AfoI~
        if (Dump.Y) Dump.println("GTPConnection:getPlaymode="+Playmode);//~1AfoI~
	    Consttime=AjagoUtils.str2int(etConsttime.getText().toString(),0);//~1AfoI~
        Global.setParameter(prefkey_CONSTTIME,Consttime);          //~1AfoI~
        if (Consttime<=0)                                          //~1AfoI~
        	Consttime=CONSTTIME_DEFAULT;                           //~1AfoI~
        strTimesettings=getTimeSettingsParm();                     //~1AfoI~
        if (Playmode==PLAYMODE_CONSTTIME)                          //~1AfoI~
    		strPlaymode=OPT_TIMELIMIT+Consttime;                      //~1AfoR~
    	return strPlaymode;                                           //~1AfoI~
	}//getPlaymode                                                 //~1AfoI~
//***************************************************************************//~1AfoI~
	@Override                                                      //~1AfoI~
	protected void initRGPlaymode(ViewGroup PlayoutView)             //~1AfoI~
    {                                                              //~1AfoI~
		rgPlaymode=new ButtonRG(PlayoutView,2);                    //~1AfoI~
        rgPlaymode.add(PLAYMODE_CONSTTIME,R.id.GTPRAY_PLAYMODE_CONSTTIME);//~1AfoI~
        rgPlaymode.add(PLAYMODE_TIMESETTING,R.id.GTPRAY_PLAYMODE_TIMESETTING);//~1AfoI~
//      rgPlaymode.setDefault(PLAYMODE_TIMESETTING);               //~1AfoI~//+1Aj3R~
        rgPlaymode.setDefault(PLAYMODE_DEFAULT);                   //+1Aj3R~
                                                                   //~1AfoI~
        Playmode=Global.getParameter(prefkey_PLAYMODE,PLAYMODE_DEFAULT);//~1AfoI~
        rgPlaymode.setChecked(Playmode);                           //~1AfoI~
                                                                   //~1AfoI~
        etConsttime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_CONSTTIME);//~1AfoI~
        Consttime=Global.getParameter(prefkey_CONSTTIME,0);        //~1AfoI~
        if (Consttime<=0)                                          //~1AfoI~
        	Consttime=CONSTTIME_DEFAULT;                           //~1AfoI~
        etConsttime.setText(Integer.toString(Consttime));          //~1AfoI~
    }                                                              //~1AfoI~
//***************************************************************************//~v1C2I~
//*get option to be written to config                              //~1AfoI~
//***************************************************************************//~1AfoI~
	@Override                                                      //~v1C2I~
	protected String getPgmOption()                                //~v1C2I~
    {                                                              //~v1C2I~
    	String cfgopt="";                                          //~1AfkI~
        cfgFilename="";                                            //~1AfoI~
        Pondering=cbPondering.isChecked();                         //~1AfkI~
        Global.setParameter(prefkey_PONDERING,Pondering);          //~1AfoR~
        if (Pondering)                                             //~1AfkI~
        	cfgopt+=OPT_PONDERING+"\n";                            //~1AfkR~
                                                                   //~1AfkI~
		String playmode=getPlaymode();                             //~1AfoI~
        if (!playmode.equals(""))                                  //~1AfoR~
        	cfgopt+=playmode+"\n";                                 //~1AfoR~
                                                                   //~1AfkI~
        Thread=AjagoUtils.str2int(etThread.getText().toString(),0);//~1AfkI~
//      Global.setParameter(PREFKEY_THREAD,Thread);                //~1AfoR~
        Global.setParameter(prefkey_THREAD,Thread);                //~1AfoI~
        if (Thread>0)                                              //~1AfkI~
        	cfgopt+=OPT_THREAD+Thread+"\n";                        //~1AfkI~
                                                                   //~1AfkI~
        String option=etOption.getText().toString();               //~v1C2I~
        option=option.trim();                                      //~v1C2I~
        Global.setParameter(prefkey_PGMOPTION,option);             //~v1C2R~
        int pos=option.indexOf(';');//split pachi oftion and UCT option//~1AfoI~
        cmdOption="";                                              //~1AfoI~
        if (pos>0)                                                 //~1AfoI~
        {                                                          //~1AfoI~
        	cmdOption=option.substring(0,pos);                     //~1AfoI~
            option=option.substring(pos+1);                        //~1AfoI~
        }                                                          //~1AfoI~
//        int pos=option.indexOf(FUEGO_OPTION_CONFIG);             //~1AfkR~
//        if (pos>=0)                                              //~1AfkR~
//        {                                                        //~1AfkR~
        	try                                                    //~v1C2I~
            {                                                      //~v1C2I~
//                int pos2=pos+FUEGO_OPTION_CONFIG.length();       //~1AfkR~
//                String tmp=option.substring(pos2);               //~1AfkR~
//                tmp=tmp.trim();                                  //~1AfkR~
//                if (tmp.startsWith("\""))                        //~1AfkR~
//                {                                                //~1AfkR~
//                    int pos3=tmp.indexOf('\"',1);                //~1AfkR~
//                    if (pos3>0)                                  //~1AfkR~
//                    {                                            //~1AfkR~
//                        String linesrep=tmp.substring(0,pos3+1); //~1AfkR~
//                        String lines=tmp.substring(1,pos3);      //~1AfkR~
//                      String cfgparmlines=lines.replace(';','\n');  //~v1c2i~//~1AfkR~
					if (!option.equals(""))                        //~1AfoI~
                    {                                              //~1AfoI~
                        String cfgparmlines=option.replace(',','\n');  //~v1c2i~//~1AfoR~
                        if (!cfgparmlines.endsWith("\n"))          //~v1C2I~
                        	cfgparmlines+="\n";                    //~v1C2I~
                        cfgopt=cfgopt+cfgparmlines;                //~1AfoR~
                    }                                              //~1AfoI~
        if (Dump.Y) Dump.println("GTPConnectionFuego:getPgmOption --config file contents="+cfgopt);//~1AfoR~
					if (!cfgopt.equals(""))                        //~1AfoI~
                    {                                              //~1AfoI~
                        cfgFilename=putConfigFile(cfgopt);         //~1AfoR~
                    }                                              //~1AfoI~
//                        if (cfgfnm!=null)                        //~1AfkR~
//                            option=option.replace(linesrep,cfgfnm);//~1AfkR~
//                    }                                              //~v1C2I~
//                }                                                  //~v1C2I~
            }                                                      //~v1C2I~
            catch(Exception e)                                      //~v1C2I~
            {                                                      //~v1C2I~
            	Dump.println(e,"fuego:getPgmOption");              //~v1C2I~
                cfgopt="";                                         //~1AfoI~
            }                                                      //~v1C2I~
//        }                                                        //~1AfkR~
        if (Dump.Y) Dump.println("GTPConnectionFuego:getPGMOption return option="+cfgopt+",filename="+cfgFilename);//~1AfoR~
        return cfgopt;                                             //~1AfoR~
	}//getPgmOption()                                              //~1AfkR~
//***************************************************************************//~v1B6I~
	@Override                                                      //~v1C1R~
    protected void initGtpCommand(GoColor Pgc)                                //~v1B6I~//~v1C1R~
    {                                                              //~v1B6I~//~v1C1R~
//        if (useBook)                                             //~v1C2R~
//            C.gogui.sendGtpString(FUEGO_BOOK_LOAD+" "+book); //this method has to protect play move before initialize end//~v1C2R~
        if (Rules==RULE_JAPANESE)//default is chinese              //~v1C1R~
            C.gogui.sendGtpString(FUEGO_RULE_JAPANESE);            //~v1C1R~
        if (Pgc!=null)                                              //~v1B6I~//~v1C1R~
            C.gogui.generateMove(false/*singleMove*/,Pgc/*human color*/);//~v1B6R~//~v1C1R~
    }//initGtpCommand                                              //~v1C2R~
//******************                                               //~v1B6I~
//    private String getTimeSettingsParm()                           //~v1B6I~//~v1C1R~
//    {                                                              //~v1B6I~//~v1C1R~
//        String tsmain=etTimeSettingMain.getText().toString();      //~v1B6I~//~v1C1R~
//        if (tsmain==null)                                          //~v1B6I~//~v1C1R~
//            tsmain="";                                             //~v1B6I~//~v1C1R~
//        else                                                       //~v1B6I~//~v1C1R~
//            tsmain=tsmain.trim();                                  //~v1B6I~//~v1C1R~
//        Global.setParameter(PREFKEY_TIMESETTINGMAIN,tsmain);           //~v1B6I~//~v1C1R~
//        if (tsmain.equals(""))                                     //~v1B6I~//~v1C1R~
//            return "";                                             //~v1B6I~//~v1C1R~
//        tsmain+="m";//minutes                                      //~v1B6I~//~v1C1R~
//        String tsextra=etTimeSettingExtra.getText().toString();    //~v1B6I~//~v1C1R~
//        if (tsextra==null)                                         //~v1B6I~//~v1C1R~
//            tsextra="";                                            //~v1B6I~//~v1C1R~
//        else                                                       //~v1B6I~//~v1C1R~
//            tsextra=tsextra.trim();                                //~v1B6I~//~v1C1R~
//        Global.setParameter(PREFKEY_TIMESETTINGEXTRA,tsextra);     //~v1B6I~//~v1C1R~
//        if (tsextra.equals(""))                                    //~v1B6I~//~v1C1R~
//            return tsmain;                                         //~v1B6I~//~v1C1R~
//        tsmain+="+"+tsextra+"s";    //seconds                      //~v1B6R~//~v1C1R~
//        String tsmove=etTimeSettingMove.getText().toString();      //~v1B6I~//~v1C1R~
//        if (tsmove==null)                                          //~v1B6I~//~v1C1R~
//            tsmove="";                                             //~v1B6I~//~v1C1R~
//        else                                                       //~v1B6I~//~v1C1R~
//            tsmove.trim();                                        //~v1B6I~//~v1C1R~
//        if (tsmove.equals(""))                                      //~v1B6R~//~v1C1R~
//            tsmove=DEFAULT_TIMESETTINGMOVE;                        //~v1B6I~//~v1C1R~
//        if (AjagoUtils.str2int(tsmove,0)==0)                       //~v1B6I~//~v1C1R~
//            tsmove=DEFAULT_TIMESETTINGMOVE;                        //~v1B6I~//~v1C1R~
//        Global.setParameter(PREFKEY_TIMESETTINGMOVE,tsmove);       //~v1B6I~//~v1C1R~
//        return tsmain+="/"+tsmove;                                        //~v1B6I~//~v1C1R~
//    }                                                              //~v1B6I~//~v1C1R~
//******************                                               //~v1B6I~
//    private void setTimeSettingsField()                          //~v1B6I~//~v1C1R~
//    {                                                              //~v1B6I~//~v1C1R~
//        String ts;                                                 //~v1B6I~//~v1C1R~
//        ts=Global.getParameter(PREFKEY_TIMESETTINGMAIN,"");            //~v1B6I~//~v1C1R~
//        etTimeSettingMain.setText(ts);                             //~v1B6I~//~v1C1R~
//        ts=Global.getParameter(PREFKEY_TIMESETTINGEXTRA,"");       //~v1B6I~//~v1C1R~
//        etTimeSettingExtra.setText(ts);                            //~v1B6I~//~v1C1R~
//        ts=Global.getParameter(PREFKEY_TIMESETTINGMOVE,"");        //~v1B6I~//~v1C1R~
//        etTimeSettingMove.setText(ts);                             //~v1B6I~//~v1C1R~
//    }                                                              //~v1B6I~//~v1C1R~
//******************                                               //~v1B6I~
    protected String editVersion(String Pversion)                  //~v1C1I~
    {                                                              //~v1C1I~
    	String version=FUEGO_VERSION+Pversion;                     //~1AfhR~
        return version;                                            //~v1C1I~
    }                                                              //~v1C1I~
	
	
//*********                                                        //~v1B6I~
//    @Override                                                      //~v1B6I~//~v1C1R~
//    protected void setupDialogExtend(ViewGroup PlayoutView)        //~v1B6I~//~v1C1R~
//    {                                                              //~v1B6I~//~v1C1R~
//        Program=(EditText)PlayoutView.findViewById(R.id.GTP_COMMAND);//~v1B6R~//~v1C1R~
//        etOption=(EditText)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION);//~v1B6I~//~v1C1R~
//        etBook=(EditText)PlayoutView.findViewById(R.id.GTP_BOOKFILE);//~v1BbI~//~v1C1R~
//        cbBook=(CheckBox)PlayoutView.findViewById(R.id.GTP_BOOKUSE);//~v1BbI~//~v1C1R~
//        HandicapField=(EditText)PlayoutView.findViewById(R.id.GTP_HANDICAP);//~v1B6R~//~v1C1R~
//        BoardSizeField=(EditText)PlayoutView.findViewById(R.id.GTP_BOARDSIZE);//~v1B6R~//~v1C1R~
//        YourNameField=(EditText)PlayoutView.findViewById(R.id.GTP_YOURNAME);//~v1B6I~//~v1C1R~
//        etTimeSettingMain=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTING);//~v1B6I~//~v1C1R~
//        etTimeSettingExtra=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRA);//~v1B6I~//~v1C1R~
//        etTimeSettingMove=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRAMOVE);//~v1B6I~//~v1C1R~
//        White=(CheckBox)PlayoutView.findViewById(R.id.GTP_WHITE);  //~v1B6R~//~v1C1R~
//        cbVerbose=(CheckBox)PlayoutView.findViewById(R.id.GTP_VERBOSE);//~v1B6I~//~v1C1R~
//        if (!AG.isDebuggable)                                      //~v1B6I~//~v1C1R~
//        {                                                          //~v1B6I~//~v1C1R~
//            cbVerbose.setVisibility(View.GONE);                    //~v1B6I~//~v1C1R~
//        }                                                          //~v1B6I~//~v1C1R~
//        rgRule=(RadioGroup)PlayoutView.findViewById(R.id.GTP_RULE);//~v1B6R~//~v1C1R~
//        KomiField=(EditText)layoutView.findViewById(R.id.GTP_KOMI);//~v1B6I~//~v1C1R~
//                                                                   //~v1B6I~//~v1C1R~
//        String gtpserver=getCommand();                             //~v1B6R~//~v1C1R~
//        String book=getBook();  //orgname                          //~v1BbI~//~v1C1R~
//        MyColor=Global.getParameter(PREFKEY_WHITE,DEFAULT_WHITE);  //~v1B6R~//~v1C1R~
//        BoardSize=Global.getParameter(PREFKEY_BOARDSIZE,DEFAULT_BOARDSIZE);//~v1B6R~//~v1C1R~
//        YourName=Global.getParameter(PREFKEY_YOURNAME,DEFAULT_YOURNAME);//~v1B6I~//~v1C1R~
//        Handicap=Global.getParameter(PREFKEY_HANDICAP,DEFAULT_HANDICAP);//~v1B6R~//~v1C1R~
//        Komi=Global.getParameter(PREFKEY_KOMI,DEFAULT_KOMI);       //~v1B6R~//~v1C1R~
//        Verbose=Global.getParameter(PREFKEY_VERBOSE,false);        //~v1B6I~//~v1C1R~
//        Rules=Global.getParameter(PREFKEY_RULE,DEFAULT_RULE);      //~v1B6R~//~v1C1R~
//        String option=Global.getParameter(PREFKEY_PGMOPTION,"");   //~v1B6I~//~v1C1R~
//        Program.setText(gtpserver);                                //~v1B6I~//~v1C1R~
//        etOption.setText(option);                                  //~v1B6I~//~v1C1R~
//        useBook=Global.getParameter(PREFKEY_BOOKUSE,false);        //~v1BbI~//~v1C1R~
//        etBook.setText(book);                                      //~v1BbI~//~v1C1R~
//        cbBook.setChecked(useBook);                                  //~v1BbI~//~v1C1R~
//        HandicapField.setText(Integer.toString(Handicap));         //~v1B6R~//~v1C1R~
//        BoardSizeField.setText(Integer.toString(BoardSize));       //~v1B6R~//~v1C1R~
//        YourNameField.setText(YourName);                           //~v1B6I~//~v1C1R~
//        String strKomi;                                            //~v1B6I~//~v1C1R~
//        if (Komi==0)                                               //~v1B6I~//~v1C1R~
//            strKomi="0";                                           //~v1B6I~//~v1C1R~
//        else                                                       //~v1B6I~//~v1C1R~
//            strKomi=Integer.toString(Komi)+".5";                   //~v1B6I~//~v1C1R~
//        KomiField.setText(strKomi);                                //~v1B6R~//~v1C1R~
//        White.setChecked(MyColor!=GTPConnector.BLACK);                 //~v1B6R~//~v1C1R~
//        setTimeSettingsField();                                     //~v1B6I~//~v1C1R~
//        cbVerbose.setChecked(Verbose);                             //~v1B6I~//~v1C1R~
//        if (Rules==RULE_CHINESE)                                  //~v1B6R~//~v1C1R~
//            rgRule.check(R.id.GTP_RULE_CHINESE);                  //~v1B6I~//~v1C1R~
//        else                                                       //~v1B6I~//~v1C1R~
//        rgRule.check(R.id.GTP_RULE_JAPANESE);                   //~v1B6I~//~v1C1R~
//    }//setupDialogExtend                                           //~v1B6R~//~v1C1R~
      @Override                                                    //~v1C8I~
    protected void setupDialogExtend(ViewGroup PlayoutView)        //~v1C8I~
    {                                                              //~v1C8I~
    	super.setupDialogExtend(PlayoutView);                       //~v1C8I~
                                                                   //~1AfkI~
//      cbPondering=(CheckBox)PlayoutView.findViewById(R.id.GTPRAY_PONDERING);//~1AfoR~
//      Pondering=Global.getParameter(PREFKEY_PONDERING,false);    //~1AfoR~
//      cbPondering.setChecked(Pondering);                         //~1AfoR~
                                                                   //~1AfkI~
//      etConsttime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_CONSTTIME);//~1AfoR~
//      Consttime=Global.getParameter(PREFKEY_CONSTTIME,0);        //~1AfoR~
//      if (Consttime>0)                                           //~1AfoR~
//      etConsttime.setText(Integer.toString(Consttime));          //~1AfoR~
                                                                   //~1AfkI~
//      etThread=(EditText)PlayoutView.findViewById(R.id.GTPRAY_THREAD);//~1AfoR~
//      Thread=Global.getParameter(PREFKEY_THREAD,0);              //~1AfoR~
//      if (Thread>0)                                              //~1AfoR~
//          etThread.setText(Integer.toString(Thread));            //~1AfoR~
                                                                   //~1AfkI~
    }//setupDialogExtend                                           //~v1C8I~
    //****************************************                     //~v1B6I~
    //*Button                                                      //~v1B6I~
    //****************************************                     //~v1B6I~
//******************                                               //~v1B6I~
      @Override                                                      //~v1B6I~//~v1C1R~
      protected boolean onClickHelp()                                //~v1B6I~//~v1C1R~
      {                                                              //~v1B6I~//~v1C1R~
//        new HelpDialog(Global.frame(),"fuegoAjagoc");  //~1B1hI~   //~v1B6I~//~v1C1R~//~v1C2R~
          new HelpDialog(Global.frame(),"fuego");                  //~v1C2I~
          return false;   //no dismiss                               //~v1B6I~//~v1C1R~
      }                                                              //~v1B6I~//~v1C1R~
//************************************************************************//~v1B6I~
//    public String getCommand()                       //~v1B6R~   //~v1C1R~
//    {                                                              //~v1B6I~//~v1C1R~
//        String pgm;                                                //~v1B6R~//~v1C1R~
//    //***************************                                  //~v1B6I~//~v1C1R~
//        if (Dump.Y) Dump.println("GTPConnection:getCommand");      //~v1B6I~//~v1C1R~
//        installDefaultProgram();//if first time                    //~v1B6R~//~v1C1R~
//        pgm=Global.getParameter(PREFKEY_PROGRAM,DEFAULT_GTPSERVER_ORGNAME);//~v1B6R~//~v1C1R~
//        pgm=pgm.trim();                                            //~v1B6I~//~v1C1R~
//        if (pgm.equals(""))                                        //~v1B6I~//~v1C1R~
//            pgm=DEFAULT_GTPSERVER_ORGNAME;                         //~v1B6R~//~v1C1R~
//        if (Dump.Y) Dump.println("GTPConnection:getCommand path="+pgm);//~v1B6R~//~v1C1R~
//        return pgm;                                                //~v1B6I~//~v1C1R~
//    }//getCommand                                                  //~v1B6R~//~v1C1R~
//************************************************************************//~v1BbI~
//    public String getBook()                                        //~v1BbI~//~v1C1R~
//    {                                                              //~v1BbI~//~v1C1R~
//        String book;                                               //~v1BbR~//~v1C1R~
//    //***************************                                  //~v1BbI~//~v1C1R~
//        if (Dump.Y) Dump.println("GTPConnection:getBook");         //~v1BbI~//~v1C1R~
//        installDefaultBook();//if first time                       //~v1BbI~//~v1C1R~
//        book=Global.getParameter(PREFKEY_BOOKFILE,DEFAULT_BOOK_ORGNAME);//~v1BbR~//~v1C1R~
//        book=book.trim();                                          //~v1BbR~//~v1C1R~
//        if (book.equals(""))                                       //~v1BbR~//~v1C1R~
//            book=DEFAULT_BOOK_ORGNAME;                             //~v1BbR~//~v1C1R~
//        if (Dump.Y) Dump.println("GTPConnection:getBook path="+book);//~v1BbR~//~v1C1R~
//        return book;                                               //~v1BbR~//~v1C1R~
//    }//getBook                                                     //~v1BbI~//~v1C1R~
//************************************************************************//~v1B6I~
//    public String getCommandParm()                   //~v1B6I~   //~v1C1R~
//    {                                                              //~v1B6I~//~v1C1R~
//        String pgm;                                                //~v1B6I~//~v1C1R~
//    //***************************                                  //~v1B6I~//~v1C1R~
//        pgm=Program.getText().toString();                          //~v1B6I~//~v1C1R~
//        pgm=pgm.trim();                                            //~v1B6I~//~v1C1R~
//        Global.setParameter(PREFKEY_PROGRAM,pgm);  //save          //~v1B6I~//~v1C1R~
//        if (pgm.equals("")||pgm.equals(DEFAULT_GTPSERVER_ORGNAME)) //~v1B6R~//~v1C1R~
//            pgm=DEFAULT_GTPSERVER;                                 //~v1B6I~//~v1C1R~
//        if (pgm.equals(DEFAULT_GTPSERVER))                         //~v1B6I~//~v1C1R~
//            pgm=AjagoProp.getDataFileFullpath(DEFAULT_GTPSERVER);  //~v1B6I~//~v1C1R~
//        if (Dump.Y) Dump.println("GTPConnection:getCommand path="+pgm);//~v1B6I~//~v1C1R~
//        return pgm;                                                //~v1B6I~//~v1C1R~
//    }//getCommand                                                  //~v1B6I~//~v1C1R~
//************************************************************************//~v1BbI~//~v1C2R~
    @Override                                                      //~v1C1I~//~v1C2R~
    public String getBookParm()                                    //~v1BbI~//~v1C1R~//~v1C2R~
    {                                                              //~v1BbI~//~v1C1R~//~v1C2R~
        String book;                                               //~v1BbI~//~v1C1R~//~v1C2R~
    //***************************                                  //~v1BbI~//~v1C1R~//~v1C2R~
        book=super.getBookParm();                                  //~v1C1R~//~v1C2R~
        if (book==null)                                            //~v1C1I~//~v1C2R~
            return null;                                           //~v1C1I~//~v1C2R~
        if (Dump.Y) Dump.println("GTPConnection:getBook path="+book);//~v1BbI~//~v1C1R~//~v1C2R~
        copyFile(book);                                            //~v1C1I~//~v1C2R~
        return book;                                               //~v1BbI~//~v1C1R~//~v1C2R~
    }//getBookParm                                                 //~v1BbI~//~v1C1R~//~v1C2R~
//************************************************************************//~v1C1I~//~v1C2R~
    private void copyFile(String Psrc)                                  //~v1C1I~//~v1C2R~
    {                                                              //~v1C1I~//~v1C2R~
        long fszold=AjagoProp.getDataFileSize(DEFAULT_BOOK_ORGNAME);//~v1C1I~//~v1C2R~
        long fsznew=new File(Psrc).length();                       //~v1C1I~//~v1C2R~
        String tofile=AjagoProp.getDataFileFullpath(DEFAULT_BOOK_ORGNAME);//~v1C1I~//~v1C2R~
        if (Dump.Y) Dump.println("copy book file oldsz="+fszold+",new="+fsznew+",from="+Psrc+",to="+tofile);//~v1C1I~//~v1C2R~
        if (fszold!=fsznew && fsznew>0)                            //~v1C1I~//~v1C2R~
        {                                                          //~v1C1I~//~v1C2R~
            AjagoProp.copyFile(Psrc,tofile);                          //~v1C1R~//~v1C2R~
        }                                                          //~v1C1I~//~v1C2R~
    }                                                              //~v1C1I~//~v1C2R~
//***************************************************************************//~v1C2M~
    private String putConfigFile(String Plines)                    //~v1C2M~
    {                                                              //~v1C2M~
        String tofile=AjagoProp.getDataFileFullpath(FUEGO_CFG_FILENAME);//~v1C2I~
        AjagoProp.copyStringToFile(Plines,tofile);
        if (Dump.Y) Dump.println("GTPConnectionFuego:putConfigFile file="+tofile+",line="+Plines);//~1AfhI~
        return tofile;//~v1C2I~
    }                                                              //~v1C2M~
    //********************************************                 //~v1DhI~//~v1C8I~
    protected void updateoption(EditText PetOption)                //~v1DhI~//~v1C8I~
    {                                                              //~v1DhI~//~v1C8I~
    	new GTPOptionUpdateFuego(this,PetOption);                       //~v1DhR~//~v1C8R~
    }                                                              //~v1DhI~//~v1C8I~
}
