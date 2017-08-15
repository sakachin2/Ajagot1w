//*CID://+1Ag5R~:                                   update#=  111109;//+1Ag5R~
//***********************************************************************//~@@@1I~
//1Ag5 2016/10/09 checkbox pie,set on as default when version>=5   //~1Ag5I~
//1Ag0 2014/10/05 displaying bot version number on menu delays until next restart(menu itemname is set at start and not changed)//~1Ag0I~
//1Afr 2016/10/02 add gnugo gtp mode                               //~1AfcR~
//***********************************************************************//~@@@1I~
package com.Ajagoc.gtp;                                            //~v1B6R~


import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

//***********************************************************************//~1AfcI~
//* by "ray --help" ****                                           //~1AfcM~
//time_settings:=--clock, --byo-time, --byo-period                 //~1AfcI~
//***********************************************************************//~1AfcI~
//GNU Go 3.8                                                       //~1AfcM~
                                                                   //~1AfcM~
//Usage: gnugo [-opts]                                             //~1AfcM~
                                                                   //~1AfcM~
//Examples:                                                        //~1AfcM~
//  gnugo --mode gtp --level 5                                     //~1AfcM~
//         To play against gnugo in level 5 from a GTP client      //~1AfcM~
//  gnugo --mode ascii -l game.sgf -L 123                          //~1AfcM~
//         Resume game at move 123 in ASCII mode                   //~1AfcM~
//  gnugo --score estimate -l game.sgf                             //~1AfcM~
//         Give a rough score estimate of the end position in game.sgf//~1AfcM~
                                                                   //~1AfcM~
//Main Options:                                                    //~1AfcM~
//       --mode <mode>     Force the playing mode ('ascii', 'gmp', 'sgmp',//~1AfcM~
//                         or 'gtp'). Default is ASCII.            //~1AfcM~
//                         If no terminal is detected GMP (Go Modem Protocol)//~1AfcM~
//                         will be assumed.                        //~1AfcM~
//       --quiet  --silent Don't print copyright and informational messages//~1AfcM~
//       --level <amount>  strength (default 10)                   //~1AfcM~
//       --never-resign    Forbid GNU Go to resign                 //~1AfcM~
//       --resign-allowed  Allow resignation (default)             //~1AfcM~
//   -l, --infile <file>   Load name sgf file                      //~1AfcM~
//   -L, --until <move>    Stop loading just before move is played. <move>//~1AfcM~
//                         can be the move number or location (eg L10).//~1AfcM~
//   -o, --outfile <file>  Write sgf output to file                //~1AfcM~
//       --printsgf <file>     Write position as a diagram to file (use with -l)//~1AfcM~
                                                                   //~1AfcM~
//Scoring:                                                         //~1AfcM~
//   --score estimate      estimate score at loaded position       //~1AfcM~
//   --score finish        generate moves to finish game, then score//~1AfcM~
//   --score aftermath     generate moves to finish, use best algorithm//~1AfcM~
                                                                   //~1AfcM~
//Game Options:                                                    //~1AfcM~
//Used with --mode ascii (or other modes for non-interactive settings)//~1AfcM~
//   --boardsize num   Set the board size to use (1--19)           //~1AfcM~
//   --color <color>   Choose your color ('black' or 'white')      //~1AfcM~
//   --handicap <num>  Set the number of handicap stones (0--9)    //~1AfcM~
//   --komi <num>      Set the komi                                //~1AfcM~
//   --clock <sec>     Initialize the timer.                       //~1AfcM~
//   --byo-time <sec>  Initialize the byo-yomi timer.              //~1AfcM~
//   --byo-period <stones>  Initialize the byo-yomi period.        //~1AfcM~
                                                                   //~1AfcM~
//   --japanese-rules     (default)                                //~1AfcM~
//   --chinese-rules                                               //~1AfcM~
//   --forbid-suicide      Forbid suicide. (default)               //~1AfcM~
//   --allow-suicide       Allow suicide except single-stone suicide.//~1AfcM~
//   --allow-all-suicide   Allow all suicide moves.                //~1AfcM~
//   --simple-ko           Forbid simple ko recapture. (default)   //~1AfcM~
//   --no-ko               Allow any ko recapture.                 //~1AfcM~
//   --positional-superko  Positional superko restrictions.        //~1AfcM~
//   --situational-superko Situational superko restrictions.       //~1AfcM~
                                                                   //~1AfcM~
//   --play-out-aftermath                                          //~1AfcM~
//   --capture-all-dead                                            //~1AfcM~
                                                                   //~1AfcM~
//   --min-level <amount>         minimum level for adjustment schemes//~1AfcM~
//   --max-level <amount>         maximum level for adjustment schemes//~1AfcM~
//   --autolevel                  adapt gnugo level during game to respect//~1AfcM~
//                                the time specified by --clock <sec>.//~1AfcM~
                                                                   //~1AfcM~
//Connection options                                               //~1AfcM~
//   --gtp-input <file>Read gtp commands from file instead of stdin//~1AfcM~
//   --gtp-connect [HOST:]PORT                                     //~1AfcM~
//                     Connect to given host (127.0.0.1 if omitted) and port//~1AfcM~
//                     and receive GTP commands on the established connection//~1AfcM~
//   --gtp-listen [HOST:]PORT                                      //~1AfcM~
//                     Wait for the first TCP/IP connection on the given port//~1AfcM~
//                     (if HOST is specified, only to that host)   //~1AfcM~
//   --gtp-version                                                 //~1AfcM~
                                                                   //~1AfcM~
//Experimental options:                                            //~1AfcM~
//   --with-break-in         use the break-in code (on at level 10 by default)//~1AfcM~
//   --without-break-in      do not use the break-in code          //~1AfcM~
//   --cosmic-gnugo          use center oriented influence         //~1AfcM~
//   --no-cosmic-gnugo       don't use center oriented influence (default)//~1AfcM~
//   --large-scale           look for large scale captures         //~1AfcM~
//   --no-large-scale        don't seek large scale captures (default)//~1AfcM~
//   --nofusekidb            turn off fuseki database              //~1AfcM~
//   --nofuseki              turn off fuseki moves entirely        //~1AfcM~
//   --nojosekidb            turn off joseki database              //~1AfcM~
//   --mirror                try to play mirror go                 //~1AfcM~
//   --mirror-limit <n>      stop mirroring when n stones on board //~1AfcM~
                                                                   //~1AfcM~
//   --monte-carlo           enable Monte Carlo move generation (9x9 or smaller)//~1AfcM~
//   --mc-games-per-level <n> number of Monte Carlo simulations per level//~1AfcM~
//   --mc-list-patterns      list names of builtin Monte Carlo patterns//~1AfcM~
//   --mc-patterns <name>    choose a built in Monte Carlo pattern database//~1AfcM~
//   --mc-load-patterns <filename> read Monte Carlo patterns from file//~1AfcM~
//   --alternate-connections                                       //~1AfcM~
//   --experimental-connections                                    //~1AfcM~
//   --experimental-owl-ext                                        //~1AfcM~
//   --experimental-semeai                                         //~1AfcM~
//   --standard-connections                                        //~1AfcM~
//   --standard-semeai                                             //~1AfcM~
//   --oracle                Read the documentation                //~1AfcM~
                                                                   //~1AfcM~
//Cache size (higher=more memory usage, faster unless swapping occurs)://~1AfcM~
//   -M, --cache-size <megabytes>  RAM cache for read results (default  8.0 Mb)//~1AfcM~
                                                                   //~1AfcM~
//Informative Output:                                              //~1AfcM~
//   -v, --version         Display the version and copyright of GNU Go//~1AfcM~
//   --options             Display configure options               //~1AfcM~
//   -h, --help            Display this help message               //~1AfcM~
//       --help debug      Display help about debugging options    //~1AfcM~
//       --copyright       Display copyright notice                //~1AfcM~
                                                                   //~1AfcM~
                                                                   //~1AfcM~
//********************************************************************//~1AfcM~
//list_commands                                                    //~1AfcM~
//********************************************************************//~1AfcM~
//aa_confirm_safety                                                //~1AfcM~
//accurate_approxlib                                               //~1AfcM~
//accuratelib                                                      //~1AfcM~
//advance_random_seed                                              //~1AfcM~
//all_legal                                                        //~1AfcM~
//all_move_values                                                  //~1AfcM~
//analyze_eyegraph                                                 //~1AfcM~
//analyze_semeai                                                   //~1AfcM~
//analyze_semeai_after_move                                        //~1AfcM~
//attack                                                           //~1AfcM~
//attack_either                                                    //~1AfcM~
//black                                                            //~1AfcM~
//block_off                                                        //~1AfcM~
//boardsize                                                        //~1AfcM~
//break_in                                                         //~1AfcM~
//captures                                                         //~1AfcM~
//clear_board                                                      //~1AfcM~
//clear_cache                                                      //~1AfcM~
//color                                                            //~1AfcM~
//combination_attack                                               //~1AfcM~
//combination_defend                                               //~1AfcM~
//connect                                                          //~1AfcM~
//countlib                                                         //~1AfcM~
//cputime                                                          //~1AfcM~
//decrease_depths                                                  //~1AfcM~
//defend                                                           //~1AfcM~
//defend_both                                                      //~1AfcM~
//disconnect                                                       //~1AfcM~
//does_attack                                                      //~1AfcM~
//does_defend                                                      //~1AfcM~
//does_surround                                                    //~1AfcM~
//dragon_data                                                      //~1AfcM~
//dragon_status                                                    //~1AfcM~
//dragon_stones                                                    //~1AfcM~
//draw_search_area                                                 //~1AfcM~
//dump_stack                                                       //~1AfcM~
//echo                                                             //~1AfcM~
//echo_err                                                         //~1AfcM~
//estimate_score                                                   //~1AfcM~
//eval_eye                                                         //~1AfcM~
//experimental_score                                               //~1AfcM~
//eye_data                                                         //~1AfcM~
//final_score                                                      //~1AfcM~
//final_status                                                     //~1AfcM~
//final_status_list                                                //~1AfcM~
//findlib                                                          //~1AfcM~
//finish_sgftrace                                                  //~1AfcM~
//fixed_handicap                                                   //~1AfcM~
//followup_influence                                               //~1AfcM~
//genmove                                                          //~1AfcM~
//genmove_black                                                    //~1AfcM~
//genmove_white                                                    //~1AfcM~
//get_connection_node_counter                                      //~1AfcM~
//get_handicap                                                     //~1AfcM~
//get_komi                                                         //~1AfcM~
//get_life_node_counter                                            //~1AfcM~
//get_owl_node_counter                                             //~1AfcM~
//get_random_seed                                                  //~1AfcM~
//get_reading_node_counter                                         //~1AfcM~
//get_trymove_counter                                              //~1AfcM~
//gg-undo                                                          //~1AfcM~
//gg_genmove                                                       //~1AfcM~
//half_eye_data                                                    //~1AfcM~
//help                                                             //~1AfcM~
//increase_depths                                                  //~1AfcM~
//initial_influence                                                //~1AfcM~
//invariant_hash_for_moves                                         //~1AfcM~
//invariant_hash                                                   //~1AfcM~
//is_legal                                                         //~1AfcM~
//is_surrounded                                                    //~1AfcM~
//kgs-genmove_cleanup                                              //~1AfcM~
//known_command                                                    //~1AfcM~
//komi                                                             //~1AfcM~
//ladder_attack                                                    //~1AfcM~
//last_move                                                        //~1AfcM~
//level                                                            //~1AfcM~
//limit_search                                                     //~1AfcM~
//list_commands                                                    //~1AfcM~
//list_stones                                                      //~1AfcM~
//loadsgf                                                          //~1AfcM~
//move_influence                                                   //~1AfcM~
//move_probabilities                                               //~1AfcM~
//move_reasons                                                     //~1AfcM~
//move_uncertainty                                                 //~1AfcM~
//move_history                                                     //~1AfcM~
//name                                                             //~1AfcM~
//new_score                                                        //~1AfcM~
//orientation                                                      //~1AfcM~
//owl_attack                                                       //~1AfcM~
//owl_connection_defends                                           //~1AfcM~
//owl_defend                                                       //~1AfcM~
//owl_does_attack                                                  //~1AfcM~
//owl_does_defend                                                  //~1AfcM~
//owl_substantial                                                  //~1AfcM~
//owl_threaten_attack                                              //~1AfcM~
//owl_threaten_defense                                             //~1AfcM~
//place_free_handicap                                              //~1AfcM~
//play                                                             //~1AfcM~
//popgo                                                            //~1AfcM~
//printsgf                                                         //~1AfcM~
//protocol_version                                                 //~1AfcM~
//query_boardsize                                                  //~1AfcM~
//query_orientation                                                //~1AfcM~
//quit                                                             //~1AfcM~
//reg_genmove                                                      //~1AfcM~
//report_uncertainty                                               //~1AfcM~
//reset_connection_node_counter                                    //~1AfcM~
//reset_life_node_counter                                          //~1AfcM~
//reset_owl_node_counter                                           //~1AfcM~
//reset_reading_node_counter                                       //~1AfcM~
//reset_search_mask                                                //~1AfcM~
//reset_trymove_counter                                            //~1AfcM~
//restricted_genmove                                               //~1AfcM~
//same_dragon                                                      //~1AfcM~
//set_free_handicap                                                //~1AfcM~
//set_random_seed                                                  //~1AfcM~
//set_search_diamond                                               //~1AfcM~
//set_search_limit                                                 //~1AfcM~
//showboard                                                        //~1AfcM~
//start_sgftrace                                                   //~1AfcM~
//surround_map                                                     //~1AfcM~
//tactical_analyze_semeai                                          //~1AfcM~
//test_eyeshape                                                    //~1AfcM~
//time_left                                                        //~1AfcM~
//time_settings                                                    //~1AfcM~
//top_moves                                                        //~1AfcM~
//top_moves_black                                                  //~1AfcM~
//top_moves_white                                                  //~1AfcM~
//tryko                                                            //~1AfcM~
//trymove                                                          //~1AfcM~
//tune_move_ordering                                               //~1AfcM~
//unconditional_status                                             //~1AfcM~
//undo                                                             //~1AfcM~
//version                                                          //~1AfcM~
//white                                                            //~1AfcM~
//worm_cutstone                                                    //~1AfcM~
//worm_data                                                        //~1AfcM~
//worm_stones                                                      //~1AfcM~
//********************************************************************//~1AfcM~


public class GTPConnectionGnugo extends GTPConnection                   //~v1B6I~//~1AfcR~
	implements GTPInterface
{                                                                  //~v1B6R~
                                                                   //~1AfcI~
    private static final int LAYOUT=R.layout.dialoggtpconnection_gnugo;//~1AfcR~
                                                                   //~1AfcI~
//  private static final String DEFAULT_GTPSERVER="Afuego";        //~1AfcR~
    private static final String DEFAULT_GTPSERVER="Agnugo";        //~1AfcR~
//  private static final String DEFAULT_GTPSERVER_ORGNAME="fuego"; //~1AfcR~
//  private static final String DEFAULT_GTPSERVER_ORGNAME="GnuGo"; //~1Ag0R~
    public  static final String DEFAULT_GTPSERVER_ORGNAME="GnuGo"; //~1Ag0I~
//  private static final String FUEGO_RULE_JAPANESE="go_rules japanese";//~1AfcR~
//  private static final String FUEGO_VERSION="1.1";               //~1AfcR~
    private static final String GNUGO_VERSION="3.8";               //~1AfcR~
//  private static final String PREFKEY_PROGRAM="GTPprogramFuego"; //~1AfcR~
    private static final String PREFKEY_PROGRAM="GTPprogramGnugo"; //~1AfcR~
    private static final String PREFKEY_PGMOPTION="GTPprogramoptionGnugo";//~1AfcR~
//  private static final String PREFKEY_BOARDSIZE="GTPboardsizeFuego";//~1AfcR~
    private static final String PREFKEY_BOARDSIZE="GTPboardsizeGnugo";//~1AfcR~
//  private static final String PREFKEY_YOURNAME="GTPyournameFuego";//~1AfcR~
    private static final String PREFKEY_YOURNAME="GTPyournameGnugo";//~1AfcR~
    private static final String PREFKEY_TIMESETTINGMAIN="GTPtimesettingMainGnugo";//~1AfcR~
    private static final String PREFKEY_TIMESETTINGEXTRA="GTPtimesettingExtraGnugo";//~1AfcR~
    private static final String PREFKEY_TIMESETTINGMOVE="GTPtimesettingMoveGnugo";//~1AfcR~
//  private static final String PREFKEY_HANDICAP="GTPhandicapFuego";//~1AfcR~
    private static final String PREFKEY_HANDICAP="GTPhandicapGnugo";//~1AfcR~
    private static final String PREFKEY_WHITE="GTPyouarewhiteGnugo";//~1AfcR~
//  private static final String PREFKEY_RULE="GTPruleFuego";       //~1AfcR~
    private static final String PREFKEY_RULE="GTPruleGnugo";       //~1AfcI~
//  private static final String PREFKEY_KOMI="GTPkomiFuego";       //~1AfcR~
    private static final String PREFKEY_KOMI="GTPkomiGnugo";       //~1AfcR~
//  private static final String PREFKEY_VERBOSE="GTPverboseFuego"; //~1AfcR~
    private static final String PREFKEY_VERBOSE="GTPverboseGnugo"; //~1AfcR~
    private static final String PREFKEY_NOFUSEKI="GTPnofusekiGnugo";//  private static final String PREFKEY_BOOKFILE="GTPbookFileFuego";//~1AfcR~
    private static final String PREFKEY_NOJOSEKI="GTPnojosekiGnugo";//    private static final String PREFKEY_BOOKUSE="GTPbookUseGnugo"; //use fuseki db//~1AfcR~
    private static final String PREFKEY_COSMIC="GTPcosmicGnugo";//  private static final String PREFKEY_BOOKFILE_ZIPSIZE="GTPbookZipSizeFuego";//~1AfcR~
//  private static final String PREFKEY_GTPSERVER_ZIPSIZE="GTPserverZipSizeFuego";//~v1C1R~//~1AfcR~
    private static final String PREFKEY_GTPSERVER_ZIPSIZE="GTPserverZipSizeGnugo";//~1AfcR~
    public  static final String PREFKEY_GTPSERVER_VERSION="GTPserverVersionGnugo";//~1AfcR~
    public  static final String PREFKEY_RULES="GTPruleGnugo";      //~1AfcI~
                                                                   //~1AfcI~
//  private static final String PREFKEY_PLAYMODE="GTPplaymodeRay"; //~1AfcR~
//  private static final String PREFKEY_PLAYOUT="GTPplayoutRay";   //~1AfcR~
//  private static final String PREFKEY_CONSTTIME="GTPconsttimeRay";//~1AfcR~
//  private static final String PREFKEY_ALLTIME="GTPalltimeRay";   //~1AfcR~
    public  static final String PREFKEY_TIMESETTINGS="GTPtimesettingsGnugo";//~1AfcI~
//  private static final String PREFKEY_PONDERING="GTPponderingRay";//~1AfcR~
//  private static final String PREFKEY_TREESIZE="GTPtreesizeRay"; //~1AfcR~
//  private static final String PREFKEY_REUSESUBTREE="GTPreusesubtreeRay";//~1AfcR~
//  private static final String PREFKEY_THREAD="GTPThreadRay";     //~1AfcR~
    private static final String PREFKEY_LEVEL="GTPGnugoLevel";     //~1AfcI~
//    private static final int PLAYMODE_PLAYOUT=1;         //playout count per move//~1AfcR~
//    private static final int PLAYMODE_CONSTTIME=2;       //const time per move//~1AfcR~
//    private static final int PLAYMODE_ALLTIME=3;       //const time per move//~1AfcR~
//  private static final int PLAYMODE_DEFAULT=PLAYMODE_PLAYOUT;    //~1AfcR~
//  private static final int PLAYOUT_DEFAULT=10000;                //~1AfcR~
//  private static final int CONSTTIME_DEFAULT=10;                 //~1AfcR~
//  private static final int ALLTIME_DEFAULT=90;                   //~1AfcR~
//  private static final String TIMESETTINGS_RAY="300m+60s/1";  //ray ignores time settings//~1AfcR~
//  private static final String OPT_PLAYOUT         =" --playout ";//~1AfcR~
//  private static final String OPT_ALLTIME         =" --time ";   //~1AfcR~
    private static final String OPT_BOARDSIZE       =" --boardsize ";//~1AfcR~
//  private static final String OPT_CONSTTIME       =" --const-time ";//~1AfcR~
//  private static final String OPT_THREAD          =" --thread "; //~1AfcR~
    private static final String OPT_KOMI            =" --komi ";   //~1AfcI~
    private static final String OPT_HANDICAP        =" --handicap ";//~1AfcI~
//  private static final String OPT_PONDERING       =" --pondering ";//~1AfcR~
//  private static final String OPT_TREESIZE        =" --tree-size ";//~1AfcR~
//  private static final String OPT_REUSESUBTREE    =" --reuse-subtree ";//~1AfcR~
//  private static final String OPT_REUSESUBTREE    =" --reuse-subtree ";//~1AfcI~
    private static final String OPT_NODEBUG         =" --quiet";   //~1AfcR~
    private static final String OPT_RULE_CHINESE    =" --chinese-rules";//~1AfcI~
    private static final String OPT_LEVEL           =" --level ";  //~1AfcI~
    private static final String OPT_COSMIC          =" --cosmic-gnugo";//~1AfcI~
    private static final String OPT_GTP             =" --mode gtp";//~1AfcI~
    private static final String OPT_NOFUSEKI        =" --nofusekidb";//~1AfcI~
    private static final String OPT_NOJOSEKI        =" --nojosekidb";//~1AfcI~
    private static final boolean DEFAULT_NOFUSEKI=false;           //~1AfcI~
    private static final boolean DEFAULT_NOJOSEKI=false;           //~1AfcI~
    private static final boolean DEFAULT_COSMIC=false;             //~1AfcI~
    private static final boolean DEFAULT_RULEJ=true;               //~1AfcI~
    private static final boolean DEFAULT_TIMESETTINGS=false;       //~1AfcR~
    private static final int     DEFAULT_LEVEL=10;                 //~1AfcI~
//  private static final int MAX_THREAD=32;                        //~1AfcR~
//  private static final int BOARDSIZE_MIN=9;                      //~1AfcR~
//  private static final int BOARDSIZE_MID=13;                     //~1AfcR~
//  private static final int BOARDSIZE_MAX=19;                     //~1AfcR~
                                                                   //~v1BbI~
//  private static final String FUEGO_OPTION_CONFIG="--config";    //~1AfcR~
//  private static final String FUEGO_CFG_FILENAME="Afuego.cfg";   //~1AfcR~
//  private String book;                                           //~1AfcR~
//  private RadioGroup rgBoardsize;                                //~1AfcR~
    private EditText etLevel;                                      //~1AfcR~
//  private EditText   etPlayout,etConsttime,etAlltime,etThread;   //~1AfcR~
//  private int Playmode,Playout,Consttime,Alltime,Thread;         //~1AfcR~
    private int Level;                                             //~1AfcI~

//  private boolean Pondering;                                     //~1AfcR~
//  private CheckBox  cbPondering;                                 //~1AfcR~
//    private ButtonRG rgPlaymode;                                   //~1AfcI~
    private Button btnShowOption;                                  //~1AfcI~
    private LinearLayout llDebug;
    private CheckBox cbNofuseki,cbNojoseki,cbCosmic,cbRulesJ,cbTimeSettings;//~1AfcR~
    public static String Sversion="";
    private boolean Cosmic,Nofuseki,Nojoseki,RulesJ,TimeSettings;  //~1AfcR~

//  public GTPConnectionFuego(Frame f)                             //~1AfcR~
    public GTPConnectionGnugo(Frame f)                             //~1AfcR~
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
        prefkey_TIMESETTINGMAIN=PREFKEY_TIMESETTINGMAIN;           //~1AfcR~
        prefkey_TIMESETTINGEXTRA=PREFKEY_TIMESETTINGEXTRA;         //~1AfcR~
        prefkey_TIMESETTINGMOVE=PREFKEY_TIMESETTINGMOVE;           //~1AfcR~
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
//      strTimesettings=TIMESETTINGS_RAY;                          //~1AfcR~
    }//initVariable                                                //~1AfcR~
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
        if (Dump.Y) Dump.println("GtpConnectionGnugo cmd="+text);  //~1AfcI~
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
//        int btnid;                                                 //~1AfcI~
    //************************                                     //~1AfcI~
        String yn=YourNameField.getText().toString();              //~1AfcM~
        if (yn!=null && !yn.equals(""))                            //~1AfcM~
        {                                                          //~1AfcM~
        	YourName=yn;                                           //~1AfcM~
        	Global.setParameter(prefkey_YOURNAME,YourName);        //~1AfcM~
        }                                                          //~1AfcM~
        BoardSize=AjagoUtils.str2int(BoardSizeField.getText().toString(),DEFAULT_BOARDSIZE);//~1AfcI~
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
        RulesJ=cbRulesJ.isChecked();                               //~1AfcI~
        Global.setParameter(PREFKEY_RULES,RulesJ);                 //~1AfcI~
                                                                   //~1AfcI~
//        Playmode=rgPlaymode.getChecked();                        //~1AfcR~
//        Playout=AjagoUtils.str2int(etPlayout.getText().toString(),0);//~1AfcR~
//        if (Playout<=0)                                          //~1AfcR~
//            Playout=PLAYOUT_DEFAULT;                             //~1AfcR~
//        Consttime=AjagoUtils.str2int(etConsttime.getText().toString(),0);//~1AfcR~
//        if (Consttime<=0)                                        //~1AfcR~
//            Consttime=CONSTTIME_DEFAULT;                         //~1AfcR~
//        Alltime=AjagoUtils.str2int(etAlltime.getText().toString(),0);//~1AfcR~
//        if (Alltime<=0)                                          //~1AfcR~
//            Alltime=ALLTIME_DEFAULT;                             //~1AfcR~
//        Global.setParameter(PREFKEY_PLAYMODE,Playmode);          //~1AfcR~
//        Global.setParameter(PREFKEY_PLAYOUT,Playout);            //~1AfcR~
//        Global.setParameter(PREFKEY_CONSTTIME,Consttime);        //~1AfcR~
//        Global.setParameter(PREFKEY_ALLTIME,Alltime);            //~1AfcR~
                                                                   //~1AfcI~
//        Pondering=cbPondering.isChecked();                       //~1AfcR~
//        Global.setParameter(PREFKEY_PONDERING,Pondering);        //~1AfcR~
        TimeSettings=cbTimeSettings.isChecked();                   //~1AfcR~
        Global.setParameter(PREFKEY_TIMESETTINGS,TimeSettings);    //~1AfcR~
                                                                   //~1AfcI~
        strTimesettings=getTimeSettingsParm();                     //~1AfcR~
        if (!TimeSettings)                                         //~1AfcI~
        	strTimesettings="";                                    //~1AfcI~
                                                                   //~1AfcI~
//        Thread=AjagoUtils.str2int(etThread.getText().toString(),0);//~1AfcR~
//        if (Thread>MAX_THREAD)                                   //~1AfcR~
//            Thread=MAX_THREAD;                                   //~1AfcR~
//        Global.setParameter(PREFKEY_THREAD,Thread);              //~1AfcR~
                                                                   //~1AfcI~
        Cosmic=cbCosmic.isChecked();                               //~1AfcI~
        Global.setParameter(PREFKEY_COSMIC,Cosmic);                //~1AfcI~
                                                                   //~1AfcI~
        Nofuseki=cbNofuseki.isChecked();                           //~1AfcI~
        Global.setParameter(PREFKEY_NOFUSEKI,Nofuseki);            //~1AfcI~
                                                                   //~1AfcI~
        Nojoseki=cbNojoseki.isChecked();                           //~1AfcI~
        Global.setParameter(PREFKEY_NOJOSEKI,Nojoseki);            //~1AfcI~
                                                                   //~1AfcI~
                                                                   //~1AfcI~
        Level=AjagoUtils.str2int(etLevel.getText().toString(),DEFAULT_LEVEL);//~1AfcR~
        Global.setParameter(PREFKEY_LEVEL,Level);                  //~1AfcI~
                                                                   //~1AfcI~
        String option=etOption.getText().toString();               //~1AfcR~
        Global.setParameter(PREFKEY_PGMOPTION,option);             //~1AfcI~
                                                                   //~1AfcI~
	    if (!AG.isDebuggable)                                      //~1AfcI~
        	Verbose=false;                                         //~1AfcI~
        else                                                       //~1AfcI~
        {                                                          //~1AfcI~
        	Verbose=cbVerbose.isChecked();                         //~1AfcI~
          	Global.setParameter(PREFKEY_VERBOSE,Verbose);          //~1AfcI~
        }                                                          //~1AfcI~
        return ""+getPgmOptionString()+" "+option;                 //~1AfcR~
	}//getPgmOption                                                //~1AfcR~
	protected String getPgmOptionString()                          //~1AfcI~
    {                                                              //~1AfcI~
        String option;                                             //~1AfcR~
    //************************                                     //~1AfcI~
    	option=OPT_GTP;      //--mode gtp                          //~1AfcI~
        option+=OPT_BOARDSIZE+BoardSize;                           //~1AfcI~
        if (Handicap!=0)                                           //~1AfcI~
        	option+=OPT_HANDICAP+Handicap;                         //~1AfcI~
        if (Komi!=0)                                               //~1AfcI~
        	option+=OPT_KOMI+Komi+".5";                            //~1AfcR~
//        if (Playmode==PLAYMODE_CONSTTIME)                        //~1AfcR~
//            option+=OPT_CONSTTIME+Consttime;                     //~1AfcR~
//        else                                                     //~1AfcR~
//        if (Playmode==PLAYMODE_ALLTIME)                          //~1AfcR~
//            option+=OPT_ALLTIME+Alltime*60;    //min-->second    //~1AfcR~
//        else                                                     //~1AfcR~
//            option+=OPT_PLAYOUT+Playout;                         //~1AfcR~
//        if (Pondering)                                           //~1AfcR~
//            option+=OPT_PONDERING;                               //~1AfcR~
//        if (Thread!=0)                                           //~1AfcR~
//            option+=OPT_THREAD+Thread;                           //~1AfcR~
		if (!RulesJ)//default is japanese                          //~1AfcR~
        	option+=OPT_RULE_CHINESE;                              //~1AfcI~
        if (Level!=DEFAULT_LEVEL)                                  //~1AfcI~
        	option+=OPT_LEVEL+Level;                              //~1AfcI~
        if (Cosmic)                                                //~1AfcI~
        	option+=OPT_COSMIC;                                    //~1AfcI~
        if (Nofuseki)                                              //~1AfcI~
        	option+=OPT_NOFUSEKI;                                  //~1AfcI~
        if (Nojoseki)                                              //~1AfcI~
        	option+=OPT_NOJOSEKI;                                 //~1AfcI~
	    if (AG.isDebuggable)                                       //~1AfcI~
        {                                                          //~1AfcI~
        	Verbose=cbVerbose.isChecked();                         //~1AfcI~
        	if (!Verbose)                                          //~1AfcI~
				option+=OPT_NODEBUG;                               //~1AfcI~
        }                                                          //~1AfcI~
        return option;                                             //~1AfcI~
	}//getPgmOptionString                                          //~1AfcI~
//***************************************************************************//~v1B6I~
//    @Override                                                    //~1AfcR~
//    protected void initGtpCommand(GoColor Pgc)                                //~v1B6I~//~1AfcR~
//    {                                                              //~v1B6I~//~1AfcR~
////        if (Rules==RULE_JAPANESE)//default is chinese          //~1AfcR~
////            C.gogui.sendGtpString(FUEGO_RULE_JAPANESE);        //~1AfcR~
//        if (Pgc!=null)                                              //~v1B6I~//~1AfcR~
//            C.gogui.generateMove(false/*singleMove*/,Pgc/*human color*/);//~v1B6R~//~1AfcR~
//    }//initGtpCommand                                            //~1AfcR~
//******************                                               //~v1B6I~
    protected String editVersion(String Pversion)                  //~v1C1I~
    {                                                              //~v1C1I~
    	if (Pversion!=null && !Pversion.equals(""))                //~1AfcI~
        	return Pversion;                                        //~1AfcI~
    	String version=GNUGO_VERSION;                              //~1AfcR~
        return version;                                            //~v1C1I~
    }                                                              //~v1C1I~
	
	
//*********                                                        //~v1B6I~
    @Override                                                      //~v1B6I~//~1AfcR~
    protected void setupDialogExtend(ViewGroup PlayoutView)        //~v1B6I~//~1AfcR~
    {                                                              //~v1B6I~//~1AfcR~
	    Program=(EditText)PlayoutView.findViewById(R.id.GTP_COMMAND);//~1AfcI~
    	String gtpserver=getCommand();                             //~1AfcM~
		Program.setText(gtpserver);                                //~1AfcM~
                                                                   //~1AfcI~
        YourNameField=(EditText)PlayoutView.findViewById(R.id.GTP_YOURNAME);//~v1B6I~//~1AfcI~
        YourName=Global.getParameter(prefkey_YOURNAME,DEFAULT_YOURNAME);//~v1B6I~//~1AfcM~
        YourNameField.setText(YourName);                           //~v1B6I~//~1AfcM~
                                                                   //~1AfcI~
        BoardSizeField=(EditText)PlayoutView.findViewById(R.id.GTP_BOARDSIZE);//~1AfcR~
        BoardSize=Global.getParameter(PREFKEY_BOARDSIZE,DEFAULT_BOARDSIZE);//~v1B6R~//~1AfcM~
	    BoardSizeField.setText(Integer.toString(BoardSize));       //~1AfcR~
                                                                   //~1AfcI~
        HandicapField=(EditText)PlayoutView.findViewById(R.id.GTP_HANDICAP);//~v1B6R~//~1AfcR~
        Handicap=Global.getParameter(PREFKEY_HANDICAP,DEFAULT_HANDICAP);//~v1B6R~//~1AfcM~
        HandicapField.setText(Integer.toString(Handicap));         //~v1B6R~//~1AfcM~
                                                                   //~1AfcI~
        KomiField=(EditText)layoutView.findViewById(R.id.GTP_KOMI);//~v1B6I~//~1AfcI~
        Komi=Global.getParameter(PREFKEY_KOMI,DEFAULT_KOMI);       //~v1B6R~//~1AfcM~
        String strKomi;                                            //~1AfcM~
        if (Komi==0)                                               //~1AfcM~
        	strKomi="0";                                           //~1AfcM~
        else                                                       //~1AfcM~
        	strKomi=Integer.toString(Komi)+".5";                   //~1AfcM~
        KomiField.setText(strKomi);                                //~1AfcI~
                                                                   //~1AfcI~
        White=(CheckBox)PlayoutView.findViewById(R.id.GTP_WHITE);  //~1AfcI~
    	MyColor=Global.getParameter(PREFKEY_WHITE,DEFAULT_WHITE);  //~1AfcI~
		White.setChecked(MyColor!=GTPConnector.BLACK);             //~1AfcM~
                                                                   //~1AfcI~
                                                                   //~1AfcI~
        cbRulesJ=(CheckBox)PlayoutView.findViewById(R.id.GTP_RULE);//~1AfcR~
    	RulesJ=Global.getParameter(PREFKEY_RULE,DEFAULT_RULEJ);    //~1AfcR~
        cbRulesJ.setChecked(RulesJ);                                //~1AfcR~
                                                                   //~1AfcI~
//      initRGPlaymode(PlayoutView);                               //~1AfcR~
//      etPlayout=(EditText)PlayoutView.findViewById(R.id.GTPRAY_PLAYOUT);//~v1B6I~//~1AfcR~
//      etConsttime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_CONSTTIME);//~1AfcR~
//      etAlltime=(EditText)PlayoutView.findViewById(R.id.GTPRAY_ALLTIME);//~1AfcR~
//      cbPondering=(CheckBox)PlayoutView.findViewById(R.id.GTPRAY_PONDERING);//~1AfcR~
//      etThread=(EditText)PlayoutView.findViewById(R.id.GTPRAY_THREAD);//~1AfcR~
                                                                   //~1AfcI~
        cbTimeSettings=(CheckBox)PlayoutView.findViewById(R.id.GTPRAY_PLAYMODE_TIMESETTING);//~1AfcR~
    	TimeSettings=Global.getParameter(PREFKEY_TIMESETTINGS,DEFAULT_TIMESETTINGS);//~1AfcM~
        cbTimeSettings.setChecked(TimeSettings);                   //~1AfcR~
                                                                   //~1AfcI~
        etTimeSettingMain=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTING);//~1AfcI~
        etTimeSettingExtra=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRA);//~1AfcI~
        etTimeSettingMove=(EditText)PlayoutView.findViewById(R.id.GTP_TIMESETTINGEXTRAMOVE);//~1AfcI~
        setTimeSettingsField();                                    //~1AfcI~
                                                                   //~1AfcI~
        etLevel=(EditText)PlayoutView.findViewById(R.id.GTPGNUGO_LEVEL);//~1AfcI~
        Level=Global.getParameter(PREFKEY_LEVEL,DEFAULT_LEVEL);    //~1AfcI~
        etLevel.setText(Integer.toString(Level));                  //~1AfcI~
                                                                   //~1AfcI~
        cbCosmic=(CheckBox)PlayoutView.findViewById(R.id.GTPGNUGO_COSMIC);//~1AfcI~
    	Cosmic=Global.getParameter(PREFKEY_COSMIC,DEFAULT_COSMIC); //~1AfcI~
        cbCosmic.setChecked(Cosmic);                               //~1AfcI~
                                                                   //~1AfcI~
        cbNofuseki=(CheckBox)PlayoutView.findViewById(R.id.GTPGNUGO_NOFUSEKI);//~1AfcI~
    	Nofuseki=Global.getParameter(PREFKEY_NOFUSEKI,DEFAULT_NOFUSEKI);//~1AfcI~
        cbNofuseki.setChecked(Nofuseki);                           //~1AfcI~
                                                                   //~1AfcI~
        cbNojoseki=(CheckBox)PlayoutView.findViewById(R.id.GTPGNUGO_NOJOSEKI);//~1AfcI~
    	Nojoseki=Global.getParameter(PREFKEY_NOJOSEKI,DEFAULT_NOJOSEKI);//~1AfcI~
        cbNojoseki.setChecked(Nojoseki);                           //~1AfcI~
                                                                   //~1AfcI~
        etOption=(EditText)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION);//~1AfcI~
    	String option=Global.getParameter(PREFKEY_PGMOPTION,"");   //~1AfcI~
		etOption.setText(option);                                  //~1AfcI~
                                                                   //~1AfcI~
        cbVerbose=(CheckBox)PlayoutView.findViewById(R.id.GTP_VERBOSE);//~1AfcM~
    	Verbose=Global.getParameter(PREFKEY_VERBOSE,false);        //~1AfcM~
		cbVerbose.setChecked(Verbose);                             //~1AfcM~
                                                                   //~1AfcM~
	    tvOption=(TextView)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION_DISPLAY);//~1AfcM~
	    btnShowOption=(Button)PlayoutView.findViewById(R.id.GTPRAY_SHOWOPTION);//~1AfcM~
	    llDebug=(LinearLayout)PlayoutView.findViewById(R.id.GTPRAY_DEBUGOPTION);//~1AfcM~
	    if (!AG.isDebuggable)                                      //~1AfcI~
        	llDebug.setVisibility(View.GONE);                      //~1AfcR~
        else                                                       //~1AfcI~
	        setButtonListener(btnShowOption);                      //~1AfcI~
                                                                   //~1Ag5R~
		initcbPIE(PlayoutView);                                    //~1Ag5R~
    }//setupDialogExtend                                           //~v1B6R~//~1AfcR~
    //****************************************                     //~v1B6I~
    //*Button                                                      //~v1B6I~
    //****************************************                     //~v1B6I~
      @Override                                                      //~v1B6I~//~v1C1R~
      protected boolean onClickHelp()                                //~v1B6I~//~v1C1R~
      {                                                              //~v1B6I~//~v1C1R~
//        new HelpDialog(Global.frame(),"fuego");                  //~1AfcR~
          new HelpDialog(Global.frame(),"gnugo");                  //~1AfcR~
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
        rgPlaymode.setDefault(PLAYMODE_PLAYOUT);                   //~1AfcI~//+1Ag5R~
    }                                                              //~1AfcI~
}
