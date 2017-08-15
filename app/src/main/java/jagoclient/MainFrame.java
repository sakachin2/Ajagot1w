//*CID://+1Ag1R~:                             update#=   44;       //~1Ag1R~
//**************************************************************************//~1B10I~
//1Ag1 2016/10/06 Change Top panel. set menu panel as tabwidget.   //~1Ag1I~
//1Ag0 2014/10/05 displaying bot version number on menu delays until next restart(menu itemname is set at start and not changed)//~1Ag0I~
//1Afr 2016/10/02 add gnugo gtp mode                               //~1AfrI~
//1Afi 2016/09/25 display GTP version on menu                      //~1AfiI~
//1Afd 2016/09/24 change menu structure, move "vs computer" to top level//~1AfdI~
//1Afc 2016/09/22 like V1C1 fuego, add ray as player               //~1AfcI~
//v1Di 2014/11/13 JagoResource for other language use keyname if not defined(Glay_GO==>"Play Go"). change variable name.//~v1DiI~
//v1C1 2014/08/21 install fuego as GTP client                      //~v1C1I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1b6I~
//1B1n 130516 Advanced option help frame was shown behind the dialog,use HelpDialog//~1B1nI~
//1B1i 130513 drop help menu "online version" that query www.rene-grothmann.de//~1B1iI~
//1B1f 130511 drop relay server menu(relay is for applet on browser)//~1B1fI~
//1B10 130501 display ip addr on start server menu item            //~1B10I~
//**************************************************************************//~1B10I~
package jagoclient;

//import com.Ajagoc.awt.CheckboxMenuItem;
//import com.Ajagoc.awt.Menu;
//import com.Ajagoc.awt.MenuBar;

import com.Ajagoc.AjagoMenu;
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.awt.BorderLayout;
import com.Ajagoc.awt.CheckboxMenuItem;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.GridLayout;
import com.Ajagoc.awt.Menu;
import com.Ajagoc.awt.MenuBar;
import com.Ajagoc.awt.MenuItem;
import com.Ajagoc.awt.Checkbox;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.SystemColor;
import com.Ajagoc.awt.TextField;
import com.Ajagoc.awt.Window;
import com.Ajagoc.gtp.GTPConnection;
import com.Ajagoc.gtp.GTPConnectionFuego;
import com.Ajagoc.gtp.GTPConnectionGnugo;
import com.Ajagoc.gtp.GTPConnectionRay;

import jagoclient.gui.CheckboxMenuItemAction;
import jagoclient.gui.CloseFrame;
import jagoclient.gui.MenuItemAction;
import jagoclient.gui.MyMenu;


import jagoclient.board.GoFrame;
import jagoclient.dialogs.*;
import jagoclient.gmp.GMPConnection;
import jagoclient.gui.*;
import jagoclient.partner.Server;
import jagoclient.sound.JagoSound;

                                                        



//~1113I~
//import java.awt.*;
import java.util.Locale;
                                                           //~1113I~

/**
Get the port for the partner server.
*/
class GetPort extends GetParameter
{	MainFrame GCF;
	public GetPort (MainFrame gcf, int port)
	{	super(gcf,Global.resourceString("Server_Port"),
			Global.resourceString("Port"),gcf,true,"port");
		set(""+port);
		GCF=gcf;
		show();
	}
	public boolean tell (Object o, String S)
	{	int port=6970;
		try
		{	port=Integer.parseInt(S);
			Global.setParameter("serverport",port);
			GCF.ServerPort.setLabel(GCF.getServerPortLabel());     //~1B10I~
		}
		catch (NumberFormatException ex) {}
		return true;
	}
}
/**
Get some advanced options.
*/

class AdvancedOptionsEdit extends CloseDialog
{	Checkbox Pack,SetIcon,UseSystemViewer,UseSystemLister,UseConfirmation,
		WhoWindow,GamesWindow,KoRule;
	/**
	Initialize all dialog items.
	The main layout is a Nx1 grid with check boxes.
	*/
	public AdvancedOptionsEdit (Frame f)
	{	super(f,Global.resourceString("Advanced_Options"),true);
		setLayout(new BorderLayout());
		Panel p=new MyPanel();
		p.setLayout(new GridLayout(0,1));
		p.add(UseConfirmation=new Checkbox(Global.resourceString("Confirmations")));
		UseConfirmation.setState(Global.getParameter("confirmations",true));
		UseConfirmation.setFont(Global.SansSerif);
		p.add(KoRule=new Checkbox(Global.resourceString("Obey_Ko_Rule")));
		KoRule.setState(Global.getParameter("korule",true));
		KoRule.setFont(Global.SansSerif);
		p.add(WhoWindow=new Checkbox(Global.resourceString("Show_Who_Window")));
		WhoWindow.setState(Global.getParameter("whowindow",true));
		WhoWindow.setFont(Global.SansSerif);
		p.add(GamesWindow=new Checkbox(Global.resourceString("Show_Games_Window")));
		GamesWindow.setState(Global.getParameter("gameswindow",true));
		GamesWindow.setFont(Global.SansSerif);
		p.add(Pack=new Checkbox(Global.resourceString("Pack_some_of_the_dialogs")));
		Pack.setState(Global.getParameter("pack",true));
		Pack.setFont(Global.SansSerif);
		p.add(SetIcon=new Checkbox(Global.resourceString("Set_own_icon__buggy_in_windows__")));
		SetIcon.setState(Global.getParameter("icons",false));
		SetIcon.setFont(Global.SansSerif);
		p.add(UseSystemViewer=new Checkbox(Global.resourceString("Use_AWT_TextArea")));
		UseSystemViewer.setState(Global.getParameter("systemviewer",false));
		UseSystemViewer.setFont(Global.SansSerif);
		p.add(UseSystemLister=new Checkbox(Global.resourceString("Use_AWT_List")));
		UseSystemLister.setState(Global.getParameter("systemlister",false));
		UseSystemLister.setFont(Global.SansSerif);
		add("Center",new Panel3D(p));
		Panel ps=new MyPanel();
		ps.add(new ButtonAction(this,Global.resourceString("OK")));
		ps.add(new ButtonAction(this,Global.resourceString("Cancel")));
		ps.add(new MyLabel(" "));
		ps.add(new ButtonAction(this,Global.resourceString("Help")));
		add("South",new Panel3D(ps));
		Global.setpacked(this,"advancedoptionsedit",300,150,f);
		show();
	}
	
	public void doAction (String o)
	{	if (o.equals(Global.resourceString("OK")))
		{	setVisible(false); dispose();
			Global.setParameter("pack",Pack.getState());
			Global.setParameter("icons",SetIcon.getState());
			Global.setParameter("systemviewer",UseSystemViewer.getState());
			Global.setParameter("systemlister",UseSystemLister.getState());
			Global.setParameter("confirmations",UseConfirmation.getState());
			Global.setParameter("whowindow",WhoWindow.getState());
			Global.setParameter("gameswindow",GamesWindow.getState());
			Global.setParameter("korule",KoRule.getState());
		}
		else if (o.equals(Global.resourceString("Cancel")))
		{	setVisible(false); dispose();
		}
		else if (o.equals(Global.resourceString("Help")))
//		{	new Help("advanced");                                  //~1B1nR~
  		{                                                          //~1B1nI~
  			new HelpDialog(new Frame(),"advanced");               //~1B1nI~
		}
	}
}

/**
Get the name for the partner client.
*/

class YourNameQuestion extends GetParameter
{	MainFrame GCF;                                                 //~1B10I~
	public YourNameQuestion (MainFrame gcf)                        //~1B10R~
	{	super(gcf,Global.resourceString("Name"),
			Global.resourceString("Your_Name"),gcf,true,"yourname");
		set(Global.getParameter("yourname","Your Name"));
        GCF=gcf;                                                   //~1B10I~
		show();
	}
	public boolean tell (Object o, String S)
	{	if (!S.equals("")) Global.setParameter("yourname",S);
		if (!S.equals(""))                                         //~1B10I~
			GCF.YourName.setLabel(GCF.getYourNameLabel());         //~1B10I~
		return true;
	}
}

/**
Get the languate locale
*/

class GetLanguage extends GetParameter
{	public boolean done=false;
	public GetLanguage (MainFrame gcf)
	{	super(gcf,"Your Locale (leave empty for default)",
			"Language",gcf,true,"language");
		String S="Your Locale";
		String T=Global.resourceString(S);
		if (!S.equals(T)) Prompt.setText(T+" ("+S+")");
		set(Locale.getDefault().toString());
		show();
	}
	public boolean tell (Object o, String S)
	{	Global.setParameter("language",S);
		done=true;
		return true;
	}
}

/**
Get IP name of the relay server, if used.
*/

class GetRelayServer extends CloseDialog
{	TextField Server;
	IntField Port;
	public GetRelayServer (Frame F)
	{	super(F,Global.resourceString("Relay_Server"),true);
		Panel p=new MyPanel();
		p.setLayout(new GridLayout(0,2));
		p.add(new MyLabel(Global.resourceString("Server")));
		p.add(Server=new GrayTextField());
		Server.setText(Global.getParameter("relayserver","localhost"));
		p.add(new MyLabel(Global.resourceString("Port")));
		p.add(Port=new IntField(this,Global.resourceString("Port"),Global.getParameter("relayport",6971)));
		add("Center",new Panel3D(p));
		Panel bp=new MyPanel();
		bp.add(new ButtonAction(this,Global.resourceString("OK")));
		bp.add(new ButtonAction(this,Global.resourceString("Cancel")));
		bp.add(new MyLabel(" "));
		bp.add(new ButtonAction(this,Global.resourceString("Help")));
		add("South",new Panel3D(bp));
		Global.setpacked(this,"getrelay",300,200,F);
		validate(); show();
	}
	public void doAction (String o)
	{	Global.notewindow(this,"getrelay");   
		if (Global.resourceString("OK").equals(o))
		{	Global.setParameter("relayserver",Server.getText());
			Global.setParameter("relayport",Port.value());
			setVisible(false); dispose();
		}
		else if (Global.resourceString("Cancel").equals(o))
		{	setVisible(false); dispose();
		}
		else if (o.equals(Global.resourceString("Help")))
		{	new Help("relayserver");
		}
		else super.doAction(o);
	}
}

/**
Get the background color.
*/

class BackgroundColorEdit extends ColorEdit
{	public BackgroundColorEdit (Frame f, String s, Color c)
	{	super(f,s,c,false);
		show();
	}
	public void addbutton (Panel p)
	{	p.add(new ButtonAction(this,Global.resourceString("System")));
	}
	public void tell (Color C)
	{	Global.gray=C;
		Global.setParameter("color.background",C);
		Global.setParameter("color.control",C);
		Global.makeColors();
	}
	public void doAction (String o)
	{	if (o.equals(Global.resourceString("System")))
		{	Global.removeParameter("color.control");
			Global.removeParameter("color.background");
			Global.gray=SystemColor.window;
			Global.makeColors();
			super.doAction(Global.resourceString("OK"));
		}
		else super.doAction(o);
	}
}

/**
The MainFrame contains menus to edit some options, get help and
set some things. A card layout with the server and partner
connections is added to this frame later.
*/

public class MainFrame extends CloseFrame
{	CheckboxMenuItem 
		StartPublicServer,TimerInTitle,BigTimer,ExtraInformation,ExtraSendField,
		DoSound,SimpleSound,BeepOnly,Warning/*,RelayCheck*/,Automatic,//~1B1fR~
		EveryMove,FineBoard,Navigation;
	MenuItem StartServer;
	public MenuItem ServerPort;                                    //~1B10I~
	public MenuItem YourName;                                      //~1B10I~
	public Server S=null;
    public static final String 	SUBMENUNAME_ROBOT="vsComputer";    //+1Ag1I~
    public static final String 	SUBMENUNAME_OPTION="Options";      //+1Ag1I~
    public static final String 	SUBMENUNAME_ONLINEHELP="Help";     //+1Ag1I~

	public MainFrame (String c)
	{	super(c+" "+Global.resourceString("Version"));             //~1524R~
		seticon("ijago.gif");
		boolean constrainedapplet=c.equals(
			Global.resourceString("Jago_Applet"));
		// Menu :
		MenuBar menu=new MenuBar();
		setMenuBar(menu);
		// Actions
//  	Menu local=new MyMenu(Global.resourceString("Actions"));   //~1AfdR~
//  	local.add(new MenuItemAction(this,Global.resourceString("Local_Board")));//~1AfdI~
//  	local.addSeparator();                                      //~1AfdR~
    	Menu lg=new MyMenu(Global.resourceString("Local_Board"));  //~1AfdR~
		lg.add(new MenuItemAction(this,Global.resourceString("Local_Board")),true);//select the item by menu selection//~1AfdR~
    	menu.add(lg);                                              //~1AfdI~
//  	Menu local=new MyMenu(Global.resourceString("vsComputer"));//~1Ag1R~
    	Menu local=new MyMenu(Global.resourceString(SUBMENUNAME_ROBOT));//+1Ag1R~
//        local.add(new MenuItemAction(this,Global.resourceString("Play_Go")));//~1AfrR~
//  	local.add(new MenuItemAction(this,Global.resourceString("Play_Gtp")));//~v1b6I~//~v1DiR~
//  	local.add(new MenuItemAction(this,Global.resourceString("Play_Gtp_against_pachi")));//~1AfiR~
		String version,actionname;                                 //~1AfiR~
        GTPConnection.notifyMenu(local);                           //~1Ag0R~
                                                                   //~1AfrI~
		actionname=Global.resourceString("Play_Gtp_against_gnugo");//~1AfrI~
//  	version=actionname+"  : "+GTPConnection.getVersion(4/*Pachi*/);//~1Ag0R~
    	version=GTPConnection.getVersion(GTPConnection.GTPID_GNUGO);//~1Ag0R~
    	local.add(new MenuItemAction(this,version,actionname));    //~1AfrI~
                                                                   //~1AfrI~
		actionname=Global.resourceString("Play_Gtp_against_pachi");//~1AfiI~
//  	version=actionname+"  : "+GTPConnection.getVersion(1/*Pachi*/);//~1Ag0R~
    	version=GTPConnection.getVersion(GTPConnection.GTPID_PACHI);//~1Ag0R~
    	local.add(new MenuItemAction(this,version,actionname));     //~1AfiI~
                                                                   //~1Ag0I~
//  	local.add(new MenuItemAction(this,Global.resourceString("Play_GtpFuego")));//~v1C1I~//~v1DiR~
//  	local.add(new MenuItemAction(this,Global.resourceString("Play_Gtp_against_fuego")));//~1AfiR~
		actionname=Global.resourceString("Play_Gtp_against_fuego");//~1AfiI~
//  	version=actionname+"  : "+GTPConnection.getVersion(2/*Fuego*/);//~1Ag0R~
    	version=GTPConnection.getVersion(GTPConnection.GTPID_FUEGO);//~1Ag0R~
    	local.add(new MenuItemAction(this,version,actionname));     //~1AfiR~
                                                                   //~1Ag0I~
//  	local.add(new MenuItemAction(this,Global.resourceString("Play_Gtp_against_ray")));//~1AfiR~
		actionname=Global.resourceString("Play_Gtp_against_ray");  //~1AfiI~
//  	version=actionname+"    : "+GTPConnection.getVersion(3/*Ray*/);//~1Ag0R~
    	version=GTPConnection.getVersion(GTPConnection.GTPID_RAY); //~1Ag0R~
    	local.add(new MenuItemAction(this,version,actionname));     //~1AfiR~
                                                                   //~1Ag0I~
		local.addSeparator();
//  	local.add(new MenuItemAction(this,Global.resourceString("Close")));//~1AfdR~
		menu.add(local);
		// Server Options
		Menu soptions=new MyMenu(Global.resourceString("Go_Server"));
		if (!Global.isApplet())
		{	soptions.add(Automatic=new CheckboxMenuItemAction(this,Global.resourceString("Automatic_Login")));
			Automatic.setState(Global.getParameter("automatic",true));
			soptions.addSeparator();
		}
		soptions.add(new MenuItemAction(this,Global.resourceString("Filter")));
		soptions.add(new MenuItemAction(this,Global.resourceString("Function_Keys")));
		menu.add(soptions);
		// Partner Options
		if (!constrainedapplet)
		{	Menu poptions=new MyMenu(Global.resourceString("Partner"));
			poptions.add(StartServer=
				new MenuItemAction(this,Global.resourceString("Start_Server")));
            setStartServerLabel(true/*start*/);                    //~1B10R~
//  		poptions.add(new MenuItemAction(this,Global.resourceString("Server_Port")));//~1B10R~
    		poptions.add(ServerPort=new MenuItemAction(this,getServerPortLabel()));//~1B10R~
//  		poptions.add(new MenuItemAction(this,Global.resourceString("Your_Name")));//~1B10R~
    		poptions.add(YourName=new MenuItemAction(this,getYourNameLabel()));//~1B10I~
			poptions.add(StartPublicServer=
				new CheckboxMenuItemAction(this,Global.resourceString("Public")));
			StartPublicServer.setState(
				Global.getParameter("publicserver",true));
			menu.add(poptions);
		}
		// Options
//  	Menu options=new MyMenu(Global.resourceString("Options")); //~1Ag1R~
    	Menu options=new MyMenu(Global.resourceString(SUBMENUNAME_OPTION));//+1Ag1R~
		Menu bo=new MyMenu(Global.resourceString("Board_Options"));
		bo.add(Navigation=new CheckboxMenuItemAction(this,Global.resourceString("Navigation_Tree")));
		Navigation.setState(Global.getParameter("shownavigationtree",true));
		bo.add(TimerInTitle=new CheckboxMenuItemAction(this,Global.resourceString("Timer_in_Title")));
		TimerInTitle.setState(Global.getParameter("timerintitle",true));
		bo.add(BigTimer=new CheckboxMenuItemAction(this,Global.resourceString("Big_Timer")));
		BigTimer.setState(Global.getParameter("bigtimer",true));
		bo.add(ExtraInformation=new CheckboxMenuItemAction(this,Global.resourceString("Extra_Information")));
		ExtraInformation.setState(Global.getParameter("extrainformation",true));
		bo.add(ExtraSendField=new CheckboxMenuItemAction(this,Global.resourceString("Extra_Send_Field")));
		ExtraSendField.setState(Global.getParameter("extrasendfield",true));
		bo.add(FineBoard=new CheckboxMenuItemAction(this,Global.resourceString("Fine_Board")));
		FineBoard.setState(Global.getParameter("fineboard",true));
		options.add(bo);
		options.add(new MenuItemAction(this,Global.resourceString("Advanced_Options")));
		options.addSeparator();
		Menu fonts=new MyMenu(Global.resourceString("Fonts"));
		fonts.add(new MenuItemAction(this,Global.resourceString("Board_Font")));
		fonts.add(new MenuItemAction(this,Global.resourceString("Fixed_Font")));
		fonts.add(new MenuItemAction(this,Global.resourceString("Big_Font")));
		fonts.add(new MenuItemAction(this,Global.resourceString("Normal_Font")));
		options.add(fonts);
		options.add(new MenuItemAction(this,Global.resourceString("Background_Color")));
		options.addSeparator();
		options.add(DoSound=
			new CheckboxMenuItemAction(this,Global.resourceString("Sound_on")));
		DoSound.setState(!Global.getParameter("nosound",true));
		options.add(BeepOnly=new CheckboxMenuItemAction(this,Global.resourceString("Beep_only")));
		BeepOnly.setState(Global.getParameter("beep",true));
		Menu sound=new MyMenu(Global.resourceString("Sound_Options"));
		sound.add(SimpleSound=
			new CheckboxMenuItemAction(this,Global.resourceString("Simple_sound")));
		SimpleSound.setState(Global.getParameter("simplesound",true));
		sound.add(EveryMove=
			new CheckboxMenuItemAction(this,Global.resourceString("Every_move")));
		EveryMove.setState(Global.getParameter("sound.everymove",true));
		sound.add(Warning=
			new CheckboxMenuItemAction(this,Global.resourceString("Timeout_warning")));
		Warning.setState(Global.getParameter("warning",true));		
		sound.add(new MenuItemAction(this,Global.resourceString("Test_Sound")));
		options.add(sound);
		options.addSeparator();
//		options.add(RelayCheck=new CheckboxMenuItemAction(this,Global.resourceString("Use_Relay")));//~1B1fR~
//		RelayCheck.setState(Global.getParameter("userelay",false));//~1B1fR~
//		options.add(new MenuItemAction(this,Global.resourceString("Relay_Server")));//~1B1fR~
		options.addSeparator();
		options.add(new MenuItemAction(this,Global.resourceString("Set_Language")));
		options.add(new MenuItemAction(this,"Close and Use English","CloseEnglish"));
		menu.add(options);
		// Help
//  	Menu help=new MyMenu(Global.resourceString("Help"));       //+1Ag1R~
	  	Menu help=new MyMenu(Global.resourceString(SUBMENUNAME_ONLINEHELP));//+1Ag1I~
		help.add(new MenuItemAction(this,Global.resourceString("About_Jago")));
		help.add(new MenuItemAction(this,Global.resourceString("Overview")));
		help.addSeparator();
		help.add(new MenuItemAction(this,Global.resourceString("Using_Windows")));
		help.add(new MenuItemAction(this,Global.resourceString("Configuring_Connections")));
		help.add(new MenuItemAction(this,Global.resourceString("Partner_Connections")));
		help.add(new MenuItemAction(this,Global.resourceString("About_Sounds")));
		help.add(new MenuItemAction(this,Global.resourceString("About_Smart_Go_Format_SGF")));
		help.add(new MenuItemAction(this,Global.resourceString("About_Filter")));
		help.add(new MenuItemAction(this,Global.resourceString("About_Function_Keys")));
		help.add(new MenuItemAction(this,Global.resourceString("Overcoming_Firewalls")));
		help.add(new MenuItemAction(this,Global.resourceString("Play_Go_Help")));
		help.addSeparator();
		help.add(new MenuItemAction(this,Global.resourceString("About_Help")));
		help.addSeparator();
//  	help.add(new MenuItemAction(this,Global.resourceString("On_line_Version_Information")));//~1B1iR~
		menu.setHelpMenu(help);
    	Menu cl=new MyMenu(Global.resourceString("Close"));        //~1AfdI~
		cl.add(new MenuItemAction(this,Global.resourceString("Close")),true);//direct select this item//~1AfdR~
    	menu.add(cl);                                              //~1AfdI~
		pack();
		Global.setwindow(this,"mainframe",300,300);
	}

	public boolean close ()
	{	if (Global.getParameter("confirmations",true))
		{	CloseMainQuestion CMQ=new CloseMainQuestion(this);
			if (CMQ.Result) doclose();
			return false;
		}
		else
		{	doclose();
			return false;
		}
	}
	
	public void doclose ()
	{	Global.notewindow(this,"mainframe");                       //~1524R~
		super.doclose();
		Global.writeparameter(".go.cfg");
		if (S!=null) S.close();
//  	if (!Global.isApplet()) System.exit(0);		               //~1309R~
    	if (!Global.isApplet()) AjagoUtils.exit(0);	//@@@@ redirect to finish()//~1309I~
	}

	public void doAction (String o)
	{   if ("CloseEnglish".equals(o))                              //~1524R~
  		{	Global.setParameter("language","en");
  			if (close()) doclose();
  		}
  		else if (Global.resourceString("Overview").equals(o))
  		{	new Help("overview");
  		}
  		else if (Global.resourceString("Using_Windows").equals(o))
  		{	new Help("windows");
  		}
  		else if (Global.resourceString("Configuring_Connections").equals(o))
  		{	new Help("configure");
  		}
  		else if (Global.resourceString("Partner_Connections").equals(o))
  		{	new Help("confpartner");
  		}
  		else if (Global.resourceString("About_Jago").equals(o))
  		{	new Help("about");
  		}
  		else if (Global.resourceString("About_Help").equals(o))
  		{	new Help("help");
  		}
  		else if (Global.resourceString("About_Sounds").equals(o))
  		{	new Help("sound");
  		}
  		else if (Global.resourceString("About_Smart_Go_Format_SGF").equals(o))
  		{	new Help("sgf");
  		}
  		else if (Global.resourceString("About_Filter").equals(o))
  		{	new Help("filter");
  		}
  		else if (Global.resourceString("About_Function_Keys").equals(o))
  		{	new Help("fkeys");
  		}
  		else if (Global.resourceString("Overcoming_Firewalls").equals(o))
  		{	new Help("firewall");
  		}
  		else if (Global.resourceString("Play_Go_Help").equals(o))
  		{	new Help("gmp");
  		}
  		else if (Global.resourceString("On_line_Version_Information").equals(o))
  		{	new Help();
  		}
  		else if (Global.resourceString("Local_Board").equals(o))
//		{	GoFrame gf=new GoFrame(new Frame(),                    //~1AfiR~
        {                                                          //~1AfiI~
            if (!Window.IsThereAnyOpenedGoFrame())                 //~1AfiI~
  		 		new GoFrame(new Frame(),                           //~1AfiR~
  				Global.resourceString("Local_Viewer"));
  		}
  		else if (Global.resourceString("Play_Go").equals(o))
//		{	new GMPConnection(this);                               //~1AfiR~
        {                                                          //~1AfiI~
            if (!Window.IsThereAnyOpenedGoFrame())                 //~1AfiI~
            if (!Window.IsDuplicatedFrame(Global.resourceString("Play_Go")))//~1AfiI~
  		 		new GMPConnection(this);                           //~1AfiI~
  		}
//		else if (Global.resourceString("Play_Gtp").equals(o))      //~v1b6I~//~v1DiR~
  		else if (Global.resourceString("Play_Gtp_against_pachi").equals(o))//~v1DiR~
  		{                                                          //~v1b6I~
          if (!Window.IsThereAnyOpenedGoFrame())                   //~1AfiI~
  			new GTPConnection(this);                               //~v1b6I~
  		}                                                          //~v1b6I~
// 		else if (Global.resourceString("Play_GtpFuego").equals(o)) //~v1C1I~//~v1DiR~
   		else if (Global.resourceString("Play_Gtp_against_fuego").equals(o))//~v1DiI~
  		{                                                          //~v1C1I~
          if (!Window.IsThereAnyOpenedGoFrame())                   //~1AfiI~
  			new GTPConnectionFuego(this);                          //~v1C1I~
  		}                                                          //~v1C1I~
   		else if (Global.resourceString("Play_Gtp_against_ray").equals(o))//~1AfcI~
  		{                                                          //~1AfcI~
          if (!Window.IsThereAnyOpenedGoFrame())                   //~1AfiI~
  			new GTPConnectionRay(this);                            //~1AfcI~
  		}                                                          //~1AfcI~
   		else if (Global.resourceString("Play_Gtp_against_gnugo").equals(o))//~1AfrI~
  		{                                                          //~1AfrI~
          if (!Window.IsThereAnyOpenedGoFrame())                   //~1AfrI~
  			new GTPConnectionGnugo(this);                          //~1AfrI~
  		}                                                          //~1AfrI~
//		else if (Global.resourceString("Server_Port").equals(o))   //~1B10R~
  		else if (o.startsWith(Global.resourceString("Server_Port")))//~1B10I~
  		{	new GetPort(this,Global.getParameter("serverport",6970));
  		}
  		else if (Global.resourceString("Set_Language").equals(o))
  		{	GetLanguage d=new GetLanguage(this);
  			if (d.done && close()) doclose();
  		}
		else if (Global.resourceString("Board_Font").equals(o))
		{	(new GetFontSize(
			"boardfontname",Global.getParameter("boardfontname","SansSerif"),
	        "boardfontsize",Global.getParameter("boardfontsize",10),false)).show();
		}
		else if (Global.resourceString("Normal_Font").equals(o))
		{	(new GetFontSize(
			"sansserif",Global.getParameter("sansserif","SansSerif"),
			"ssfontsize",Global.getParameter("ssfontsize",11),false)).show();
		}
		else if (Global.resourceString("Fixed_Font").equals(o))
		{	(new GetFontSize(
			"monospaced",Global.getParameter("monospaced","Monospaced"),
			"msfontsize",Global.getParameter("msfontsize",11),false)).show();
		}
		else if (Global.resourceString("Big_Font").equals(o))
		{	(new GetFontSize(
			"bigmonospaced",Global.getParameter("bigmonospaced","BoldMonospaced"),
			"bigmsfontsize",Global.getParameter("bigmsfontsize",22),false)).show();
		}
//		else if (Global.resourceString("Your_Name").equals(o))     //~1B10R~
  		else if (o.startsWith(Global.resourceString("Your_Name"))) //~1B10I~
  		{	new YourNameQuestion(this);
  		}
  		else if (Global.resourceString("Filter").equals(o))
  		{	Global.MF.edit();
  		}
  		else if (Global.resourceString("Function_Keys").equals(o))
  		{	new FunctionKeyEdit();
  		}
  		else if (Global.resourceString("Relay_Server").equals(o))
  		{	new GetRelayServer(this);
  		}
  		else if (Global.resourceString("Test_Sound").equals(o))
  		{	JagoSound.play("high","wip",true);
  		}
//		else if (Global.resourceString("Start_Server").equals(o))  //~1B10R~
  		else if (o.startsWith(Global.resourceString("Start_Server")))//~1B10I~
  		{	if (Global.Busy)
//			{	Dump.println("Server started on "+                 //~1524I~
  			{	if (Dump.Y) Dump.println("Server started on "+     //~1506R~
  					Global.getParameter("serverport",6970));
  				if (S==null)
  					S=new Server(Global.getParameter("serverport",6970),
  						Global.getParameter("publicserver",true));
  				S.open();
  				try
//				{	StartServer.setLabel(Global.resourceString("Stop_Server"));//~1B10R~
  				{                                                  //~1B10I~
  					setStartServerLabel(false/*stop*/);            //~1B10I~
  				}
  				catch (Exception e)
  				{	System.err.println("Motif error with setLabel");
  				}
  			}
  			else
  			{	S.close();
//    			StartServer.setLabel(Global.resourceString("Start_Server"));//~1B10R~
            	setStartServerLabel(true/*start*/);                //~1B10R~
  			}
  		}
//		else if (Global.resourceString("Stop_Server").equals(o))   //~1B10R~
  		else if (o.startsWith(Global.resourceString("Stop_Server")))//~1B10I~
  		{	S.close();
//			StartServer.setLabel(Global.resourceString("Start_Server"));//~1B10R~
            setStartServerLabel(true/*start*/);                    //~1B10R~
  		}
  		else if (Global.resourceString("Advanced_Options").equals(o))
  		{	new AdvancedOptionsEdit(this);
  		}
  		else if (Global.resourceString("Background_Color").equals(o))
  		{	new BackgroundColorEdit(this,"globalgray",Global.gray);
  		}
		else super.doAction(o);
  	}
  	public void itemAction (String o, boolean flag)
  	{	if (Global.resourceString("Public").equals(o))
  		{	Global.setParameter("publicserver",flag);
  		}
  		else if (Global.resourceString("Timer_in_Title").equals(o))
  		{	Global.setParameter("timerintitle",flag);
  		}
  		else if (Global.resourceString("Navigation_Tree").equals(o))
  		{	Global.setParameter("shownavigationtree",flag);
  		}
  		else if (Global.resourceString("Fine_Board").equals(o))
  		{	Global.setParameter("fineboard",flag);
  		}
  		else if (Global.resourceString("Big_Timer").equals(o))
  		{	Global.setParameter("bigtimer",flag);
  		}
  		else if (Global.resourceString("Extra_Information").equals(o))
  		{	Global.setParameter("extrainformation",flag);
  		}
  		else if (Global.resourceString("Extra_Send_Field").equals(o))
  		{	Global.setParameter("extrasendfield",flag);
  		}
  		else if (Global.resourceString("Sound_on").equals(o))
  		{	Global.setParameter("nosound",!flag);
  		}
  		else if (Global.resourceString("Simple_sound").equals(o))
  		{	Global.setParameter("simplesound",flag);
  		}
  		else if (Global.resourceString("Every_move").equals(o))
  		{	Global.setParameter("sound.everymove",flag);
  		}
  		else if (Global.resourceString("Beep_only").equals(o))
  		{	Global.setParameter("beep",flag);
  		}
  		else if (Global.resourceString("Timeout_warning").equals(o))
  		{	Global.setParameter("warning",flag);
  		}
  		else if (Global.resourceString("Automatic_Login").equals(o))
  		{	Global.setParameter("automatic",flag);
  		}
  		else if (Global.resourceString("Use_Relay").equals(o))
  		{	Global.setParameter("userelay",flag);
  		}
  	}
  	
//*****************************************************************************//~1B10I~
	private void setStartServerLabel(boolean Pstart)               //~1B10R~
    {                                                              //~1B10I~
    	String label;                                              //~1B10I~
        String ipaddr=AjagoUtils.getIPAddress(false/*no need ipv6*/);//~1B10R~
		if (Pstart)                                                //~1B10I~
			label=Global.resourceString("Start_Server");           //~1B10R~
        else                                                       //~1B10I~
			label=Global.resourceString("Stop_Server");            //~1B10I~
		label+=" ("+ipaddr+")";                                   //~1B10I~
		StartServer.setLabel(label);                               //~1B10I~
    }                                                              //~1B10I~
//*****************************************************************************//~1B10I~
	public String getServerPortLabel()                             //~1B10R~
    {                                                              //~1B10I~
		String label=Global.resourceString("Server_Port");       //~1B10I~
  		int port=Global.getParameter("serverport",6970);           //~1B10I~
		label+=" ("+port+")";                                      //~1B10I~
        return label;                                              //~1B10I~
    }                                                              //~1B10I~
//*****************************************************************************//~1B10I~
	public String getYourNameLabel()                               //~1B10I~
    {                                                              //~1B10I~
		String label=Global.resourceString("Your_Name");           //~1B10I~
  		String name=Global.getParameter("yourname","Your Name");   //~1B10I~
		label+=" ("+name+")";                                      //~1B10I~
        return label;                                              //~1B10I~
    }                                                              //~1B10I~
}

