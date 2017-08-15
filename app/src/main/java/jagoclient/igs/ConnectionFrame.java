//*CID://+v1EeR~:                             update#=    8;       //~v1EeR~
//*************************************************************************//~v1C5I~
//v1Ee 2014/12/12 FileDialog:NPE at AjagoModal:actionPerforme by v1Ec//~v1EeI~
//                OnListItemClick has no modal consideration like as Button//~v1EeI~
//                FileDialog from LocalGoFrame is on subthread, OnItemClick of List Item scheduled on MainThread//~v1EeI~
//                AjagoModal do not allocalte countdown latch but subthreadModal flag indicate latch.countDown()//~v1EeI~
//                ==>Change FileDialog to from WaitInput(Modal) to Callback method//~v1EeI~
//v1C6 2014/08/30 toast for missing input for peek/status/observe  //~v1C6I~
//v1C5 2014/08/28 protect wait who/game before login on connectionFrame//~v1C5I~
//*************************************************************************//~v1C5I~
package jagoclient.igs;

import jagoclient.CloseConnection;
import jagoclient.Dump;                                          //~1113R~
import jagoclient.Global;
import jagoclient.dialogs.GetParameter;
import jagoclient.dialogs.Message;
import jagoclient.dialogs.Help;                                  //~1113R~
import jagoclient.dialogs.Question;                              //~1113R~
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CheckboxMenuItemAction;
import jagoclient.gui.GrayTextField;
import jagoclient.gui.MenuItemAction;
import jagoclient.gui.MyMenu;
import jagoclient.gui.Panel3D;
import jagoclient.gui.CloseFrame;
import jagoclient.gui.HistoryTextField;
import jagoclient.gui.MyLabel;
import jagoclient.gui.MyPanel;                                     //~1113R~
import jagoclient.igs.connection.Connection;
import jagoclient.igs.games.GamesFrame;                          //~1113R~
import jagoclient.igs.who.WhoFrame;                              //~1113R~//~1306R~
//import java.awt.BorderLayout;                                    //~1417R~
//import java.awt.CheckboxMenuItem;                                //~1417R~
//import java.awt.Color;                                           //~1417R~
//import java.awt.FileDialog;                                      //~1417R~
//import java.awt.GridBagLayout;                                   //~1417R~
//import java.awt.Menu;                                            //~1417R~
//import java.awt.MenuBar;                                         //~1417R~
//import java.awt.Panel;                                           //~1417R~
//import java.awt.TextField;                                       //~1417R~
//import java.awt.event.KeyListener;                               //~1417R~
//import java.awt.event.WindowEvent;                               //~1417R~
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.Ajagoc.awt.BorderLayout;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.FileDialog;
import com.Ajagoc.awt.FileDialogI;
import com.Ajagoc.awt.GridBagLayout;
import com.Ajagoc.awt.KeyListener;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.TextField;
import com.Ajagoc.awt.KeyEvent;                                    //~1114R~//~1124M~
import com.Ajagoc.awt.CheckboxMenuItem;                            //~1124I~
import com.Ajagoc.awt.MenuBar;                                     //~1124M~
import com.Ajagoc.awt.Menu;                                        //~1124I~
import com.Ajagoc.awt.WindowEvent;
import com.Ajagoc.rene.viewer.Viewer;                              //~1214I~
import com.Ajagoc.rene.viewer.SystemViewer;                        //~1214I~
import com.Ajagoc.AjagoView;                                       //~v1C5I~
import com.Ajagoc.R;

import rene.util.list.ListClass;
import rene.util.list.ListElement;
//import rene.viewer.Viewer;                                       //~1214R~
//import rene.viewer.SystemViewer;                                 //~1113R~

class CloseConnectionQuestion extends Question
{	public CloseConnectionQuestion (ConnectionFrame g)
	{	super(g,Global.resourceString("This_will_close_your_connection_"),Global.resourceString("Close"),g,true); 
		show(); 
	}
}

class GetWaitfor extends GetParameter
{	ConnectionFrame CF; 
	public GetWaitfor (ConnectionFrame cf)
	{	super(cf,Global.resourceString("Wait_for_"),Global.resourceString("Wait_for_Player"),cf,true); 
		CF=cf; 
		set(CF.Waitfor); 
		show(); 
	}
	public boolean tell (Object o, String s)
	{	CF.Waitfor=s; 
		return true; 
	}
}

class GetReply extends GetParameter
{	ConnectionFrame CF; 
	public GetReply (ConnectionFrame cf)
	{	super(cf,Global.resourceString("Automatic_Reply_"),Global.resourceString("Auto_Reply"),cf,true); 
		CF=cf; 
		set(CF.Reply); 
		show(); 
	}
	public boolean tell (Object o, String s)
	{	CF.Reply=s; 
		Global.setParameter("autoreply",s); 
		return true; 
	}
}

class RefreshWriter extends PrintWriter
{	Thread T; 
	boolean NeedsRefresh; 
	boolean Stop=false; 
	public RefreshWriter (OutputStreamWriter out, boolean flag)
	{	super(out,flag); 
		if (Global.getParameter("refresh",true))
		{	T=new Thread()
			{	public void runAYT ()
				{	run();
				}
			};
			T.start(); 
		}
	}
	public void print (String s)
	{	super.print(s); 
		if (Dump.Y) Dump.println("Out ---> "+s);                   //~1506R~
		NeedsRefresh=false; 
	}
	public void printLn (String s)
	{	super.println(s); 
		if (Dump.Y) Dump.println("Out ---> "+s);                   //~1506R~
		NeedsRefresh=false; 
	}
	public void close ()
	{	super.close(); 
		Stop=true; 
	}
	public void runAYT ()
	{	while (!Stop)
		{	NeedsRefresh=true; 
			try {	Thread.sleep(300000); } catch (Exception e) {	}
			if (Stop) break; 
			if (NeedsRefresh)
			{	println("ayt"); 
				if (Dump.Y) Dump.println("ayt sent!");             //~1506R~
			}
		}
	}
}

/**
This frame contains a menu, a text area for the server output,
a text area to send commands to the server and buttons to call
who, games etc.
*/

public class ConnectionFrame extends CloseFrame
	implements KeyListener
    ,FileDialogI                                                   //~v1EeI~
{                                                                  //~1113R~
//  GridBagLayout girdbag;                                         //~1113I~
	Viewer Output; 
	HistoryTextField Input; 
	GridBagLayout gridbag; 
	public WhoFrame Who; 
	public GamesFrame Games; 
	
	Socket Server; 
	PrintWriter Out; 
	public DataOutputStream Outstream; 
	String Encoding; 
	IgsStream In; 
	ReceiveThread RT; 
	String Dir; 
	TextField Game; 
	CheckboxMenuItem CheckInfo,CheckMessages,CheckErrors,ReducedOutput,
		AutoReply; 
	public int MoveStyle=Connection.MOVE; 
	TextField WhoRange; // Kyu/Dan range for the who command.
	String Waitfor; // Pop a a message, when this player connects.
	ListClass OL; // List of Output-Listeners
	String Reply; 
	
	public boolean hasClosed=false; // note that the user closed the window
//  public boolean swLogin=false;   //ReceiveThread set true       //~v1C5R~
	
	public ConnectionFrame (String Name, String encoding)
	{	super(Name); 
		Encoding=encoding; 
		Waitfor=""; 
		OL=new ListClass(); 
		setLayout(new BorderLayout()); 
		// Menu
		MenuBar M=new MenuBar(); 
		setMenuBar(M); 
		Menu file=new MyMenu(Global.resourceString("File")); 
		M.add(file); 
		file.add(new MenuItemAction(this,Global.resourceString("Save"))); 
		file.addSeparator(); 
		file.add(new MenuItemAction(this,Global.resourceString("Clear"))); 
		file.addSeparator(); 
		file.add(new MenuItemAction(this,Global.resourceString("Close"))); 
		Menu options=new MyMenu(Global.resourceString("Options")); 
		M.add(options); 
		options.add(AutoReply=new CheckboxMenuItemAction(this,Global.resourceString("Auto_Reply"))); 
		AutoReply.setState(false); 
		options.add(new MenuItemAction(this,Global.resourceString("Set_Reply"))); 
		Reply=Global.getParameter("autoreply","I am busy! Please, try later."); 
		options.addSeparator(); 
		options.add(CheckInfo=new CheckboxMenuItemAction(this,Global.resourceString("Show_Information"))); 
		CheckInfo.setState(Global.getParameter("showinformation",false)); 
		options.add(CheckMessages=new CheckboxMenuItemAction(this,Global.resourceString("Show_Messages"))); 
		CheckMessages.setState(Global.getParameter("showmessages",true)); 
		options.add(CheckErrors=new CheckboxMenuItemAction(this,Global.resourceString("Show_Errors"))); 
		CheckErrors.setState(Global.getParameter("showerrors",false)); 
		options.add(ReducedOutput=new CheckboxMenuItemAction(this,Global.resourceString("Reduced_Output"))); 
		ReducedOutput.setState(Global.getParameter("reducedoutput",true)); 
		options.add(new MenuItemAction(this,Global.resourceString("Wait_for_Player"))); 
		Menu help=new MyMenu(Global.resourceString("Help")); 
		help.add(new MenuItemAction(this,Global.resourceString("Terminal_Window"))); 
		help.add(new MenuItemAction(this,Global.resourceString("Server_Help"))); 
		help.add(new MenuItemAction(this,Global.resourceString("Channels"))); 
		help.add(new MenuItemAction(this,Global.resourceString("Observing_Playing"))); 
		help.add(new MenuItemAction(this,Global.resourceString("Teaching"))); 
		M.setHelpMenu(help); 
		Panel center=new MyPanel(); 
		center.setLayout(new BorderLayout()); 
		// Text
		Output=Global.getParameter("systemviewer",false)?new SystemViewer():new Viewer(); 
		Output.setFont(Global.Monospaced); 
		Output.setBackground(Global.gray); 
		center.add("Center",Output); 
		// Input
		Input=new HistoryTextField(this,"Input"); 
		Input.loadHistory("input.history"); 
		center.add("South",Input); 
		add("Center",center); 
		// Buttons:
		Panel p=new MyPanel(); 
		p.add(new ButtonAction(this,Global.resourceString("Who"))); 
		p.add(WhoRange=new HistoryTextField(this,"WhoRange",5)); 
		WhoRange.setText(Global.getParameter("whorange","20k-8d")); 
		p.add(new ButtonAction(this,Global.resourceString("Games"))); 
		p.add(new MyLabel(" ")); 
		p.add(new ButtonAction(this,Global.resourceString("Peek"))); 
		p.add(new ButtonAction(this,Global.resourceString("Status"))); 
		p.add(new ButtonAction(this,Global.resourceString("Observe"))); 
		Game=new GrayTextField(4); 
		p.add(Game); 
		add("South",new Panel3D(p)); 
		//
		Dir=new String(""); 
		seticon("iconn.gif"); 
		addKeyListener(this); 
		Input.addKeyListener(this); 
	}
	
	void movestyle (int style)
	{	MoveStyle=style; 
	}
	
	/**
	Tries to connect to the server using IgsStream.
	Upon success, it starts the ReceiveThread, which
	handles the login and then all input from the server,
	scanned by IgsStream.
	<P>
	Then it starts some default distributors, shows itself and
	returns true.
	@returns success of failure
	@see jagoclient.igs.IgsStream
	@see jagoclient.igs.ReceiveThread
	*/
	public boolean connect (String server, int port, String user, String password,
		boolean proxy)
	{	try
		{	Server=new Socket(server,port); 
			String encoding=Encoding; 
			if (encoding.startsWith("!"))
			{	encoding=encoding.substring(1); 
			}
			if (encoding.equals(""))
				Out=new RefreshWriter(
				new OutputStreamWriter(
				Outstream=new DataOutputStream(Server.getOutputStream())),
				true); 
			else Out=new RefreshWriter(
				new OutputStreamWriter(
				Outstream=new DataOutputStream(Server.getOutputStream()),encoding),
				true); 
		}
		catch (UnsupportedEncodingException e)
		{	try
			{	Out=new RefreshWriter(
				new OutputStreamWriter(
					Outstream=new DataOutputStream(Server.getOutputStream())),
					true); 
			}
			catch (Exception ex)
			{                                                      //~1214R~
	        	if (Dump.Y) Dump.println("connect socket UnsupportedEncoding RefreshWriter exception ex="+ex.toString()+";e="+e.toString());//+v1EeR~
				return false;                                      //~1214I~
			}
		}
		catch (IllegalArgumentException e)
		{	try
			{	Out=new RefreshWriter(
				new OutputStreamWriter(
					Outstream=new DataOutputStream(Server.getOutputStream())),
					true); 
			}
			catch (Exception ex)
			{                                                      //~1214R~
	        	if (Dump.Y) Dump.println("connect socket IlleagalArgument RefreshWriter exception ex="+ex.toString()+",e="+e.toString());//+v1EeR~
				return false;                                      //~1214I~
			}
		}
		catch (IOException e)
		{                                                          //~1214R~
        	if (Dump.Y) Dump.println("connect socket IOException="+e.toString());//+v1EeR~
			return false;                                          //~1214I~
		}
		try
		{	/*if (proxy) In=
			new ProxyIgsStream(this,Server.getInputStream(),Out);
			else */In=new IgsStream(this,Server.getInputStream(),Out); 
		}
		catch (Exception e)
		{                                                          //~1214R~
        	if (Dump.Y) Dump.println("connect IgsStream exception ="+e.toString());//+v1EeR~
			return false;                                          //~1214I~
		}
		show(); 
		RT=new ReceiveThread(Output,In,Out,user,password,proxy,this); 
		RT.start(); 
		new PlayDistributor(this,In,Out); 
		new MessageDistributor(this,In,Out); 
		new ErrorDistributor(this,In,Out); 
		new InformationDistributor(this,In,Out); 
		new SayDistributor(this,In,Out); 
		return true; 
	}
	
	public boolean connectvia (String server, int port, String user, String password,
		String relayserver, int relayport)
	{	try
		{	Server=new Socket(relayserver,relayport); 
			String encoding=Encoding; 
			if (encoding.startsWith("!"))
			{	encoding=encoding.substring(1); 
			}
			if (encoding.equals(""))
				Out=new RefreshWriter(
				new OutputStreamWriter(
				Outstream=new DataOutputStream(Server.getOutputStream())),
				true); 
			else Out=new RefreshWriter(
				new OutputStreamWriter(
				Outstream=new DataOutputStream(Server.getOutputStream()),encoding),
				true); 
		}
		catch (UnsupportedEncodingException e)
		{	try
			{	Out=new RefreshWriter(
				new OutputStreamWriter(
					Outstream=new DataOutputStream(Server.getOutputStream())),
					true); 
			}
			catch (Exception ex)
			{	return false; 
			}
		}
		catch (IllegalArgumentException e)
		{	try
			{	Out=new RefreshWriter(
				new OutputStreamWriter(
					Outstream=new DataOutputStream(Server.getOutputStream())),
					true); 
			}
			catch (Exception ex)
			{	return false; 
			}
		}
		catch (IOException e)
		{	return false; 
		}
		try
		{	In=new IgsStream(this,Server.getInputStream(),Out); 
		}
		catch (Exception e)
		{	return false; 
		}
		Out.println(server); 
		Out.println(""+port); 
		show(); 
		RT=new ReceiveThread(Output,In,Out,user,password,false,this); 
		RT.start(); 
		new PlayDistributor(this,In,Out); 
		new MessageDistributor(this,In,Out); 
		new ErrorDistributor(this,In,Out); 
		new InformationDistributor(this,In,Out); 
		new SayDistributor(this,In,Out); 
		return true; 
	}
	
	public void doAction (String o)
	{	if (Global.resourceString("Close").equals(o))
		{	if (close()) doclose(); 
		}
		else if (Global.resourceString("Clear").equals(o))
		{	Output.setText(""); 
		}
		else if (Global.resourceString("Save").equals(o))
//  	{	FileDialog fd=new FileDialog(this,Global.resourceString("Save_Game"),FileDialog.SAVE);//~v1EeR~
    	{                                                          //~v1EeI~
    	 	FileDialog fd=new FileDialog(this,Global.resourceString("Save_Game"),FileDialog.SAVE,false/*modal*/);//~v1EeI~
			if (!Dir.equals("")) fd.setDirectory(Dir); 
			fd.setFile("*.txt"); 
			fd.show(); 
//            String fn=fd.getFile();                              //~v1EeR~
//            if (fn==null) return;                                //~v1EeR~
//            Dir=fd.getDirectory();                               //~v1EeR~
//            try                                                  //~v1EeR~
//            {   PrintWriter fo;                                  //~v1EeR~
//                if (Encoding.equals(""))                         //~v1EeR~
//                    fo=                                          //~v1EeR~
//                    new PrintWriter(new OutputStreamWriter(      //~v1EeR~
//                    new FileOutputStream(fd.getDirectory()+fn)),true);//~v1EeR~
//                else fo=                                         //~v1EeR~
//                    new PrintWriter(new OutputStreamWriter(      //~v1EeR~
//                    new FileOutputStream(fd.getDirectory()+fn),Encoding),true);//~v1EeR~
//                Output.save(fo);                                 //~v1EeR~
//                fo.close();                                      //~v1EeR~
//            }                                                    //~v1EeR~
//            catch (IOException ex)                               //~v1EeR~
//            {   System.err.println(Global.resourceString("Error_on__")+fn);//~v1EeR~
//            }                                                    //~v1EeR~
		}
		else if (Global.resourceString("Who").equals(o))
//  	{	goclient();                                            //~v1C5R~
    	{                                                          //~v1C5I~
//            if (!swLogin)                                        //~v1C5R~
//            {                                                    //~v1C5R~
//                requestLogin();                                  //~v1C5R~
//                return;                                          //~v1C5R~
//            }                                                    //~v1C5R~
    	 	goclient();                                            //~v1C5I~
			if (Global.getParameter("whowindow",true))
			{	if (Who!=null)
				{	Who.refresh(); 
					Who.requestFocus(); 
					return; 
				}
				Who=new WhoFrame(this,Out,In,WhoRange.getText()); 
				Global.setwindow(Who,"who",300,400); 
				Who.show(); 
				Who.refresh(); 
			}
			else
			{	if (WhoRange.getText().equals("")) command("who");
				else command("who "+WhoRange.getText());
			}
		}
		else if (Global.resourceString("Games").equals(o))
//  	{	goclient();                                            //~v1C5R~
		{                                                          //~v1C5I~
//            if (!swLogin)                                        //~v1C5R~
//            {                                                    //~v1C5R~
//                requestLogin();                                  //~v1C5R~
//                return;                                          //~v1C5R~
//            }                                                    //~v1C5R~
		 	goclient();                                            //~v1C5I~
			if (Global.getParameter("gameswindow",true))
			{	if (Games!=null)
				{	Games.refresh(); 
					Games.requestFocus(); 
					return; 
				}
				Games=new GamesFrame(this,Out,In); 
				Global.setwindow(Games,"games",500,400); 
				Games.show(); 
				Games.refresh(); 
			}
			else
			{	command("games");
			}
		}
		else if (Global.resourceString("Peek").equals(o))
		{	goclient(); 
			int n; 
			try
			{	n=Integer.parseInt(Game.getText()); 
				peek(n); 
			}
			catch (NumberFormatException ex)
//  		{	return;                                            //~v1C6R~
    		{                                                      //~v1C6I~
        		AjagoView.showToastLong(R.string.ErrGameNo);       //~v1C6I~
                return;                                            //~v1C6I~
			}
		}
		else if (Global.resourceString("Status").equals(o))
		{	goclient(); 
			int n; 
			try
			{	n=Integer.parseInt(Game.getText()); 
				status(n); 
			}
			catch (NumberFormatException ex)
//  		{	return;                                            //~v1C6R~
    		{                                                      //~v1C6I~
        		AjagoView.showToastLong(R.string.ErrGameNo);       //~v1C6I~
                return;                                            //~v1C6I~
			}
		}
		else if (Global.resourceString("Observe").equals(o))
		{	goclient(); 
			int n; 
			try
			{	n=Integer.parseInt(Game.getText()); 
				observe(n); 
			}
			catch (NumberFormatException ex)
//  		{	return;                                            //~v1C6R~
    		{                                                      //~v1C6I~
        		AjagoView.showToastLong(R.string.ErrGameNo);       //~v1C6I~
                return;                                            //~v1C6I~
			}
		}
		else if (Global.resourceString("Terminal_Window").equals(o))
		{	new Help("terminal"); 
		}
		else if (Global.resourceString("Server_Help").equals(o))
		{	new Help("server"); 
		}
		else if (Global.resourceString("Channels").equals(o))
		{	new Help("channels"); 
		}
		else if (Global.resourceString("Observing_Playing").equals(o))
		{	new Help("obsplay"); 
		}
		else if (Global.resourceString("Teaching").equals(o))
		{	new Help("teaching"); 
		}
		else if ("Input".equals(o))
		{	String os=Input.getText(); 
			command(os);
		}
		else if (Global.resourceString("Wait_for_Player").equals(o))
		{	new GetWaitfor(this); 
		}
		else if (Global.resourceString("Set_Reply").equals(o))
		{	new GetReply(this); 
		}
		else super.doAction(o); 
	}
	
	public void itemAction (String o, boolean flag)
	{	if (Global.resourceString("Show_Information").equals(o))
		{	Global.setParameter("showinformation",flag); 
		}
		else if (Global.resourceString("Show_Messages").equals(o))
		{	Global.setParameter("showmessages",flag); 
		}
		else if (Global.resourceString("Show_Errors").equals(o))
		{	Global.setParameter("showerrors",flag); 
		}
		else if (Global.resourceString("Reduced_Output").equals(o))
		{	Global.setParameter("reducedoutput",flag); 
		}
	}
	
	public void command (String os)
	{	if (os.startsWith(" "))
		{	os=os.trim(); 
		}
		else append(os,Color.green.darker()); 
		if (Global.getParameter("gameswindow",true) &&
			os.toLowerCase().startsWith("games"))
		{	Input.setText(""); 
			doAction(Global.resourceString("Games")); 
		}
		else if (Global.getParameter("whowindow",true) &&
			os.toLowerCase().startsWith("who"))
		{	Input.setText(""); 
			if (os.length()>4)
			{	os=os.substring(4).trim(); 
				if (!os.equals("")) WhoRange.setText(os); 
			}
			doAction(Global.resourceString("Who")); 
		}
		else if (os.toLowerCase().startsWith("observe"))
		{	Input.setText(""); 
			if (os.length()>7)
			{	os=os.substring(7).trim(); 
				if (!os.equals("")) Game.setText(os); 
				doAction(Global.resourceString("Observe")); 
			}
			else append("Observe needs a game number",Color.red); 
		}
		else if (os.toLowerCase().startsWith("peek"))
		{	Input.setText(""); 
			if (os.length()>5)
			{	os=os.substring(5).trim(); 
				if (!os.equals("")) Game.setText(os); 
				doAction(Global.resourceString("Peek")); 
			}
			else append("Peek needs a game number",Color.red); 
		}
		else if (os.toLowerCase().startsWith("status"))
		{	Input.setText(""); 
			if (os.length()>6)
			{	os=os.substring(6).trim(); 
				if (!os.equals("")) Game.setText(os); 
				doAction(Global.resourceString("Status")); 
			}
			else append("Status needs a game number",Color.red); 
		}
		else if (os.toLowerCase().startsWith("moves"))
		{	new Message(this,Global.resourceString("Do_not_enter_this_command_here_")); 
		}
		else
		{	if (!Input.getText().startsWith(" ")) Input.remember(os); 
			Out.println(os); 
			Input.setText(""); 
		}
	}
	
	public void peek (int n)
	{	if (In.gamewaiting(n))
		{	new Message(this,Global.resourceString("There_is_already_a_board_for_this_game_")); 
			return; 
		}
		IgsGoFrame gf=new IgsGoFrame(this,Global.resourceString("Peek_game")); 
		gf.setVisible(true); gf.repaint();
		new Peeker(gf,In,Out,n); 
	}
	
	public void status (int n)
	{	IgsGoFrame gf=new IgsGoFrame(this,Global.resourceString("Peek_game")); 
		gf.setVisible(true); gf.repaint();
		new Status(gf,In,Out,n); 
	}
	
	public void observe (int n)
	{	if (In.gamewaiting(n))
		{	new Message(this,Global.resourceString("There_is_already_a_board_for_this_game_")); 
			return; 
		}
		IgsGoFrame gf=new IgsGoFrame(this,Global.resourceString("Observe_game")); 
		gf.setVisible(true); gf.repaint();
		new GoObserver(gf,In,Out,n); 
	}
	
	public void doclose ()
	{	Global.notewindow(this,"connection");
		hasClosed=true; 
		Input.saveHistory("input.history"); 
		if (In!=null) In.removeall(); 
		Out.println("quit"); 
		Out.close(); 
		new CloseConnection(Server,In.getInputStream()); 
		inform(); 
		super.doclose(); 
	}
	
	public boolean close ()
	{	if (RT.isAlive())
		{	if (Global.getParameter("confirmations",true))
			{	return (new CloseConnectionQuestion(this)).result(); 
			}
		}
		return true; 
	}
	
	public void append (String s)
	{	append(s,Color.blue.darker()); 
	}
	
	public void append (String s, Color c)
	{	Output.append(s+"\n",c); 
		ListElement e=OL.first(); 
		while (e!=null)
		{	OutputListener ol=(OutputListener)e.content(); 
			ol.append(s,c); 
			e=e.next(); 
		}
	}
	
	public void addOutputListener (OutputListener l)
	{	OL.append(new ListElement(l)); 
	}
	
	public void removeOutputListener (OutputListener l)
	{	ListElement e=OL.first(); 
		while (e!=null)
		{	OutputListener ol=(OutputListener)e.content(); 
			if (ol==l)
			{	OL.remove(e); 
				return; 
			}
			e=e.next(); 
		}
	}
	
	public boolean wantsinformation ()
	{	return CheckInfo.getState() && Global.Silent<=0; 
	}
	
	public boolean wantsmessages ()
	{	return CheckMessages.getState() && Global.Silent<=0; 
	}
	
	public boolean wantserrors ()
	{	return CheckErrors.getState() && Global.Silent<=0; 
	}
	
	public void out (String s)
	{	if (s.startsWith("observe") || s.startsWith("status")
		|| s.startsWith("moves")) return; 
		if (Dump.Y) Dump.println("---> "+s);                       //~1506R~
		Out.println(s); 
	}
	
	public void goclient ()
	{	RT.goclient(); 
	}
	
	String reply ()
	{	if (AutoReply.getState()) return Reply; 
		else return ""; 
	}
	
	public void keyPressed (KeyEvent e) {	}
	public void keyTyped (KeyEvent e) {	}
	public void keyReleased (KeyEvent e)
	{	String s=Global.getFunctionKey(e.getKeyCode()); 
		if (s.equals("")) return; 
		Input.setText(s); 
	}
	
	public void windowOpened (WindowEvent e)
	{	Input.requestFocus(); 
		if (Dump.Y) Dump.println("windowOpened Listener requestFocus to Input id="+Integer.toString(Input.textView.getId(),16)+"focus="+Input.textView.isFocused());//+v1EeR~
	}
//    private void requestLogin()                                  //~v1C5R~
//    {                                                            //~v1C5R~
//        AjagoView.showToastLong(R.string.WarningNotLogin);       //~v1C5R~
//    }                                                            //~v1C5R~
//****************************************************************************//~v1C5I~
//*delete Who/Game frame before login comp                         //~v1C5I~
//****************************************************************************//~v1C5I~
    public  void enterLogin()                                      //~v1C5I~
    {                                                              //~v1C5I~
    	boolean swClose=false;                                     //~v1C5I~
    	if (Who!=null)                                             //~v1C5I~
        {                                                          //~v1C5I~
        	Who.doAction(Global.resourceString("Close"));          //~v1C5R~
            swClose=true;                                          //~v1C5I~
        }                                                          //~v1C5I~
    	if (Games!=null)                                           //~v1C5R~
        {                                                          //~v1C5I~
        	Games.doAction(Global.resourceString("Close"));        //~v1C5I~
            swClose=true;                                          //~v1C5I~
        }                                                          //~v1C5I~
        if (!swClose)                                              //~v1C5I~
        	return;                                                //~v1C5I~
        AjagoView.showToastLong(R.string.WarningNotLogin);         //~v1C5I~
        RT.resetgoclient(); //re-send "toggle client true" is required//~v1C5I~
    }                                                              //~v1C5I~
//****************************************************************************//~v1EeI~
    @Override                                                      //~v1EeI~
	public int fileDialogSaveCallback(FileDialog Pfd,String Pfnm)  //~v1EeI~
	{                                                              //~v1EeI~
        if (Dump.Y) Dump.println("ConnectionFrame:fileDialogSaveCallback fnm="+Pfnm);//~v1EeI~
        FileDialog fd=Pfd;                                         //~v1EeI~
            String fn=fd.getFile();                                //~v1EeI~
            if (fn==null) return 4;                                //~v1EeI~
            Dir=fd.getDirectory();                                 //~v1EeI~
            try                                                    //~v1EeI~
            {   PrintWriter fo;                                    //~v1EeI~
                if (Encoding.equals(""))                           //~v1EeI~
                    fo=                                            //~v1EeI~
                    new PrintWriter(new OutputStreamWriter(        //~v1EeI~
                    new FileOutputStream(fd.getDirectory()+fn)),true);//~v1EeI~
                else fo=                                           //~v1EeI~
                    new PrintWriter(new OutputStreamWriter(        //~v1EeI~
                    new FileOutputStream(fd.getDirectory()+fn),Encoding),true);//~v1EeI~
                Output.save(fo);                                   //~v1EeI~
                fo.close();                                        //~v1EeI~
            }                                                      //~v1EeI~
            catch (IOException ex)                                 //~v1EeI~
            {   System.err.println(Global.resourceString("Error_on__")+fn);//~v1EeI~
                Dump.println(ex,"ConnectionFrame:fileDialogSaveCallback");//~v1EeI~
            	return 4;                                          //~v1EeI~
            }                                                      //~v1EeI~
		return 0;                                                  //~v1EeI~
	}                                                              //~v1EeI~
    //**************************************************************//~v1EeI~
    @Override                                                      //~v1EeI~
	public int fileDialogLoadCallback(FileDialog Pfd,String Pfnm){return 4;}//~v1EeI~
}
