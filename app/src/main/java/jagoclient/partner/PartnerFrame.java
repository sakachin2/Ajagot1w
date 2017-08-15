//*CID://+1AecR~:                                   update#=   74; //~1AecR~
//****************************************************************************//~@@@1I~
//1Aec 2015/07/26 set connection type for Server                   //~1AecI~
//1Ae9 2015/07/26 (Ajagoc only)All connection type passes to jagoclient PartnerFrame to (re)start/disconnect//~1Ae9I~
//1Ae4 2015/07/24 addtional to 1Ab1. move setconnectiontype from wifidirect.PartnerFrame to jagoclient.partnerframe//~1Ae4I~
//2015/07/23 //1AbM 2015/07/03 BT:short sleep for BT disconnet fo exchange @@@@end and @@@@!end//~1AbMI~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8dk2015/03/01 Add "Close" button for PartnerFrame              //~1A8dI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//v1Eh 2014/12/12 show partnername on boardquestion                //~v1EhI~
//v1Ef 2014/12/12 construct save filename by whitename and blackname//~v1EfI~
//v1Ee 2014/12/12 FileDialog:NPE at AjagoModal:actionPerforme by v1Ec//~v1EeI~
//                OnListItemClick has no modal consideration like as Button//~v1EeI~
//                FileDialog from LocalGoFrame is on subthread, OnItemClick of List Item scheduled on MainThread//~v1EeI~
//                AjagoModal do not allocalte countdown latch but subthreadModal flag indicate latch.countDown()//~v1EeI~
//                ==>Change FileDialog to from WaitInput(Modal) to Callback method//~v1EeI~
//v1Dp 2014/11/15 write to comment "resign"/"accept"/"recline"     //~v1DpI~
//v1Dm 2014/11/14 BT:use devicename when yourname is same          //~v1DmI~
//v1D3 2014/10/07 set timestamp to filename on filedialog when save(current is *.sgf)//~v1D3I~
//1B1k 130514 (Bug)PartnerMatch:if undoed Block flag was left,could not move.//~1B1kI~
//1B1j 130514 connection faile exception msg by toast              //~1B1jI~
//1109:130126 notify board closed when when stop app               //~v110I~
//1106:130126 Gamequestion popup even after IP connection lost     //~v110I~
//1103:130124 board stop timing(avoid exception)                   //~v110I~
//1081:121213 partner-match:when server requested endgame,Block=true remains and//~v108I~
//            on next match on same connection(same PartnerFrame),client setstone is blocked//~v108I~
//            (                                                    //~v108I~
//            endgame by interrupt @@endgame set Block=true after call EndGameQuestion//~v108I~
//            EndGameQuestion call doendgame/declineendgame(set Block=false)//~v108I~
//            modal dialog is protected to return before reply,so results Block=true)//~v108I~
//            )                                                    //~v108I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//*@@@1 20110430 FunctionKey support                               //~@@@1I~
//****************************************************************************//~@@@1I~
package jagoclient.partner;

import jagoclient.CloseConnection;
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Help;
import jagoclient.dialogs.Message;
import jagoclient.dialogs.Question;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseFrame;
import jagoclient.gui.HistoryTextField;
import jagoclient.gui.MenuItemAction;
import jagoclient.gui.MyMenu;
import jagoclient.gui.MyPanel;
import jagoclient.gui.Panel3D;






//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FileDialog;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.Panel;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
//import android.view.Menu;
import com.Ajagoc.AG;                                              //~v110I~
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.AjagoView;                                       //~v110I~
import com.Ajagoc.ProgDlg;
import com.Ajagoc.R;                                               //~v110I~
import com.Ajagoc.awt.BorderLayout;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.FileDialog;
import com.Ajagoc.awt.FileDialogI;
import com.Ajagoc.awt.KeyEvent;
import com.Ajagoc.awt.KeyListener;
import com.Ajagoc.awt.MenuBar;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.Menu;
import com.Ajagoc.awt.Window;
import rene.util.list.ListClass;
import rene.util.list.ListElement;
import rene.util.parser.StringParser;
import wifidirect.DialogNFC;
import wifidirect.DialogNFCBT;
//import rene.viewer.SystemViewer;                                 //~1318R~
//import rene.viewer.Viewer;                                       //~1318R~
import com.Ajagoc.rene.viewer.Viewer;                              //~1318I~
import com.Ajagoc.rene.viewer.SystemViewer;                        //~1318I~

class PartnerMove
{	public String Type;
	public int P1,P2,P3,P4,P5,P6;
	public PartnerMove (String type, int p1, int p2, int p3, int p4, int p5, int p6)
	{	Type=type; P1=p1; P2=p2; P3=p3; P4=p4; P5=p5; P6=p6;
	}
	public PartnerMove (String type, int p1, int p2, int p3, int p4)
	{	Type=type; P1=p1; P2=p2; P3=p3; P4=p4;
	}
}

/**
The partner frame contains a simple chat dialog and a button
to start a game or restore an old game. This class contains an
interpreters for the partner commands.
*/

public class PartnerFrame extends CloseFrame
	implements KeyListener                                         //~@@@1I~
    ,FileDialogI                                                   //~v1EeI~
//{	BufferedReader In;                                             //~v107R~
{	                                                               //~v107I~
    public  static final int NFC_SERVER=1;    //identify connection type//~1Ae4I~
    public  static final int NFC_CLIENT=2;                         //~1Ae4I~
    public  static final int WD_SERVER=3;                          //~1Ae4I~
    public  static final int WD_CLIENT=4;                          //~1Ae4I~
	public static final String CONN_TITLE_IP="IP ";                //~1A8iI~
	public static final String CONN_TITLE_WD="WD ";                //~1A8iI~
	public static final String CONN_TITLE_BT="BT ";                //~1A8iI~
	public static final String CONN_TITLE_NFC="NFC ";              //~1A8iI~
	public static final String CONN_TITLE_NFC_WD="NFC-WD ";        //~1AecI~
	public static final String CONN_TITLE_NFC_BT="NFC-BT ";        //~1AecI~
	protected BufferedReader In;                                   //~v107I~
//	PrintWriter Out;                                               //~v107R~
  	protected PrintWriter Out;                                      //~v107I~
//	Viewer Output;                                                 //~v107R~
	protected Viewer Output;                                       //~v107I~
//  HistoryTextField Input;                                        //~v107R~
    protected HistoryTextField Input;                              //~v107I~
	Socket Server;
//	PartnerThread PT;                                              //~v107R~
//  protected PartnerThread PT;                                    //~v107I~//~1A8cR~
    public PartnerThread PT;                                       //~1A8cI~
	public PartnerGoFrame PGF;
	boolean Serving;
	boolean Block;
	ListClass Moves;
	String Dir;
  protected                                                        //~1A8cI~
	String Encoding;                                               //~1109I~
	public String partnerName="",myName="";                        //~v1D3R~
    public int connectionType;                                     //~1A6BI~//~1Ae4I~
    private String titleName;                                             //~1A8iI~

	public PartnerFrame (String name, boolean serving)
	{	super(name);
		Panel p=new MyPanel();
		Serving=serving;
		MenuBar menu=new MenuBar();
		setMenuBar(menu);
		Menu help=new MyMenu(Global.resourceString("Help"));
		help.add(new MenuItemAction(this,Global.resourceString("Partner_Connection")));
		menu.setHelpMenu(help);
		p.setLayout(new BorderLayout());
		p.add("Center",Output=Global.getParameter("systemviewer",false)?new SystemViewer():new Viewer());
		Output.setFont(Global.Monospaced);
		p.add("South",Input=new HistoryTextField(this,"Input"));
		add("Center",p);
		Panel p1=new MyPanel();
		p1.add(new ButtonAction(this,Global.resourceString("Game")));
		p1.add(new ButtonAction(this,Global.resourceString("Restore_Game")));
		p1.add(new ButtonAction(this,Global.resourceString("Close")));//~1A8dI~
		add("South",new Panel3D(p1));
		PGF=null;
		Block=false;
		Dir="";
		Moves=new ListClass();
		seticon("iconn.gif");
		addKeyListener(this);                                      //~@@@1I~
        AG.aPartnerFrame=this;                                     //~v110R~
        myName=Global.getParameter("yourname","No Name");          //~v1EfI~
        titleName=name;                                            //~1A8iI~
	}
	public PartnerFrame (String name, boolean serving,String Ptitleprefix)//~1A8iI~
	{                                                              //~1A8iI~
		this(name,serving);                                        //~1A8iI~
        setTitle(Ptitleprefix+titleName);                          //~1A8iI~
	}                                                              //~1A8iI~

	//*************************************************************************//~1109I~
	public boolean connect (String s, int p,String Pencoding)      //~1109I~
    {                                                              //~1109I~
    	Encoding=Pencoding;                                        //~1109I~
        boolean rc=connect(s,p);                                   //~1109I~
        Encoding=null;                                             //~1109I~
        return rc;                                                 //~1109I~
    }                                                              //~1109I~
	//*************************************************************************//~1109I~
	public boolean connect (String s, int p)
	{	if (Dump.Y) Dump.println("Starting partner connection");   //~@@@1R~
		try
		{	Server=new Socket(s,p);
        	boolean encode=(Encoding!=null & !Encoding.equals(""));  //~1B0cI~//~1109I~
          if (encode)                                              //~1B0cI~//~1109I~
          {                                                        //~1B0cI~//~1109I~
          	try                                                    //~1B0cI~//~1109I~
            {                                                      //~1B0cI~//~1109I~
                Out=new PrintWriter(                               //~1B0cI~//~1109I~
                        new OutputStreamWriter(                    //~1B0cI~//~1109I~
                    					new DataOutputStream(Server.getOutputStream()) //~1B0cI~//~1109I~
                                               ,Encoding)          //~1B0cI~//~1109I~
                                    ,true);                        //~1B0cI~//~1109I~
                In=new BufferedReader(new InputStreamReader(       //~1B0cI~//~1109I~
                    					new DataInputStream(Server.getInputStream())//~1109I~
                                               				,Encoding)//~1109I~
									);                             //~1109I~
            }                                                      //~1B0cI~//~1109I~
			catch (UnsupportedEncodingException e)                 //~1B0cI~//~1109I~
			{                                                      //~1B0cI~//~1109I~
            	AjagoView.showToastLong(AG.resource.getString(R.string.ErrEncoding,Encoding));//~1109I~
				return false;                                      //~1109I~
            }                                                      //~1109I~
          }                                                        //~1B0cI~//~1109I~
          else                                                     //~1B0cI~//~1109I~
          {                                                        //~1B0cI~//~1109I~
			Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
          }                                                        //~1109I~
		}
		catch (Exception e)
//		{	return false;                                          //~1B1jR~
  		{                                                          //~1B1jR~
        	if (Dump.Y) Dump.println("cause="+e.getCause()+",msg="+e.getMessage());//~1B1jR~
            String msg=e.getMessage();                             //~1B1jI~
            if (msg!=null)                                         //~1B1jI~
	            AjagoView.showToastLong(msg);                      //~1B1jI~
            Dump.println(e,"PartnerFrame:connect "+s+",port="+p);  //~1B1jR~
  		 	return false;                                          //~1B1jR~
		}
		PT=new PartnerThread(In,Out,Input,Output,this);
		PT.start();
		out("@@name "+                                             //~1A8cR~
			Global.getParameter("yourname","No Name"));
//      myName=Global.getParameter("yourname","No Name");          //~v1EfR~
		show();
		getHostAddr(Server);                                       //~1A8fI~
		return true;
	}

	public boolean connectvia (String server, int port,
		String relayserver, int relayport)
	{	try
		{	Server=new Socket(relayserver,relayport);
			Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
		}
		catch (Exception e)
		{	return false;
		}
		out(server);                                               //~1A8cR~
		out(""+port);                                              //~1A8cR~
		PT=new PartnerThread(In,Out,Input,Output,this);
		PT.start();
		out("@@name "+                                             //~1A8cR~
			Global.getParameter("yourname","No Name"));
        myName=Global.getParameter("yourname","No Name");         //~v1D3I~
		show();
		return true;
	}

	public void open (Socket server)
	{	if (Dump.Y) Dump.println("Starting partner server");       //~@@@1R~
		Server=server;
		try
		{	Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
		}
		catch (Exception e)
		{	if (Dump.Y) Dump.println("---> no connection");        //~@@@1R~
			new Message(this,Global.resourceString("Got_no_Connection_"));
			return;
		}
        if (Dump.Y) Dump.println("PartnetFrame server after open");//~1AbMI~
		PT=new PartnerThread(In,Out,Input,Output,this);
		PT.start();
		getHostAddr(Server);                                       //~1A8fI~
	}

	public void doAction (String o)
	{	if ("Input".equals(o))
		{	out(Input.getText());                                  //~1A8cR~
			Input.remember(Input.getText());
			Output.append(Input.getText()+"\n",Color.green.darker());
			Input.setText("");
		}
		else if (Global.resourceString("Game").equals(o))
//      {   new GameQuestion(this);                                //~v110R~
        {                                                          //~v110I~
          	if (PT!=null && PT.isAlive())                          //~v110I~
            {                                                      //~1AbMI~
            	if (Dump.Y) Dump.println("PartnerFrame new GameQuestion");//~1AbMI~
				new GameQuestion(this);                            //~v110I~
            }                                                      //~1AbMI~
            else                                                   //~v110I~
            	AjagoView.showToast(R.string.NoPartnerConnection); //~v110I~
		}
		else if (Global.resourceString("Restore_Game").equals(o))
		{	if (!Block)
			{	if (PGF==null)
				{	out("@@restore");                              //~1A8cR~
					Block=true;
				}
				else
				{	new Message(this,Global.resourceString(
						"You_have_already_a_game_"));
				}
			}
			
		}
		else if (Global.resourceString("Partner_Connection").equals(o))
		{	new Help("partner");
		}
		else super.doAction(o);
	}

	public void doclose ()
	{	Global.notewindow(this,"partner");	
        if (Dump.Y) Dump.println("jagoclient.PartnerFrame doclose");//~1AbMI~
		out("@@@@end");                                            //~1A8cR~
		Out.close();
		new CloseConnection(Server,In);
		super.doclose();
	}
	public void doclose2()                                         //~1AbMI~
    {                                                              //~1AbMI~
        if (Dump.Y) Dump.println("jagoclient.PartnerFrame doclose2");//~1AbMI~
//  	out("@@@@end");                                            //~1AbMI~
//  	Out.close();                                               //~1AbMI~
		new CloseConnection(Server,In); //Close Serve and In       //~1AbMI~
		super.doclose();                                           //~1AbMI~
	}                                                              //~1AbMI~

	public boolean close ()
	{	if (PT.isAlive())
		{	new ClosePartnerQuestion(this);
			return false;
		}
		else return true;
	}

	/**
	The interpreter for the partner commands (all start with @@).
	*/
	public void interpret (String s)
//  {	if (s.startsWith("@@name"))                                //~v107R~
	{                                                              //~v107I~
        if (Dump.Y) Dump.println("PartnetFrame interpret:"+s);     //~v107I~
		if (s.startsWith("@@nameBT"))                              //~v1D3I~
		{	StringParser p=new StringParser(s);                    //~v1D3I~
			p.skip("@@nameBT");                                    //~v1D3I~
//  		partnerName=p.upto('!');                               //~v1DmR~
    		String pn=p.upto('!').trim();                          //~v1DmR~
        	myName=Global.getParameter("yourname","No Name");      //~v1DmI~
            if (!pn.equals(myName))	//same name                    //~v1DmI~
//          	partnerName=pn;                                    //~v1EfR~
            	partnerName=pn.trim();                            //~v1EfI~
//  		setTitle(Global.resourceString("Connection_to_")+partnerName);//~v1D3I~//~1A8iR~
//  		setTitle(CONN_TITLE_BT+Global.resourceString("Connection_to_")+partnerName);//~1A8iR~//+1AecR~
    		setTitle((AG.isNFCBT ? CONN_TITLE_NFC_BT :CONN_TITLE_BT)//+1AecI~
						+Global.resourceString("Connection_to_")+partnerName);//+1AecI~
        	if (Dump.Y) Dump.println("PartnetFrameBT setTitle:"+Global.resourceString("Connection_to_")+partnerName);//~v1D3I~
//      	myName=Global.getParameter("yourname","No Name");      //~v1DmR~
			out("@@!nameBT "+myName);                      //~v1D3I~//~1A8cR~
		}                                                          //~v1D3I~
		else if (s.startsWith("@@!nameBT"))                        //~v1D3I~
		{	StringParser p=new StringParser(s);                    //~v1D3M~
			p.skip("@@!nameBT");                                   //~v1D3I~
//  		partnerName=p.upto('!');                               //~v1DmR~
    		String pn=p.upto('!').trim();                          //~v1DmR~
        	myName=Global.getParameter("yourname","No Name");      //~v1DmI~
            if (!pn.equals(myName))	//same name                    //~v1DmI~
//          	partnerName=pn;                                    //~v1EfR~
            	partnerName=pn.trim();                            //~v1EfI~
        	if (Dump.Y) Dump.println("PartnetFrame receivedBT:"+s);//~v1D3I~
		}                                                          //~v1D3M~
        else                                                       //~v1D3I~
		if (s.startsWith("@@name"))                                //~v107I~
		{	StringParser p=new StringParser(s);
			p.skip("@@name");
//  		setTitle(Global.resourceString("Connection_to_")+p.upto('!'));//~v1D3R~
//  		partnerName=p.upto('!');                               //~v1EfR~
    		partnerName=p.upto('!').trim();                       //~v1EfI~
//  		setTitle(Global.resourceString("Connection_to_")+partnerName);//~v1D3I~//~1A8iR~
    		setTitle(CONN_TITLE_IP+Global.resourceString("Connection_to_")+partnerName);//~1A8iR~
        	if (Dump.Y) Dump.println("PartnetFrame setTitle:"+Global.resourceString("Connection_to_")+partnerName);//~v1D3R~
		}
		else if (s.startsWith("@@board"))
		{	if (PGF!=null) return;
			StringParser p=new StringParser(s);
			p.skip("@@board");
			String color=p.parseword();
			int C;
			if (color.equals("b")) C=1;
			else C=-1;
			int Size=p.parseint();
			int TotalTime=p.parseint();
			int ExtraTime=p.parseint();
			int ExtraMoves=p.parseint();
			int Handicap=p.parseint();
            if (Dump.Y) Dump.println("PartnerFrame new BoardQuestion");//~1AbMI~
			new BoardQuestion(this,Size,color,Handicap,TotalTime,
//  			ExtraTime,ExtraMoves);                             //~v1EhR~
    			ExtraTime,ExtraMoves,partnerName);                 //~v1EhI~
		}
		else if (s.startsWith("@@!board"))
		{	if (PGF!=null) return;
			StringParser p=new StringParser(s);
			p.skip("@@!board");
			String color=p.parseword();
			int C;
			if (color.equals("b")) C=1;
			else C=-1;
			int Size=p.parseint();
			int TotalTime=p.parseint();
			int ExtraTime=p.parseint();
			int ExtraMoves=p.parseint();
			int Handicap=p.parseint();
			PGF=new PartnerGoFrame(this,Global.resourceString("Partner_Game"),
				C,Size,TotalTime*60,ExtraTime*60,ExtraMoves,Handicap);
			out("@@start");                                        //~1A8cR~
			Block=false;
			Moves=new ListClass();
			Moves.append(new ListElement(
				new PartnerMove("board",C,Size,
					TotalTime,ExtraTime,ExtraMoves,Handicap)));
		}
		else if (s.startsWith("@@-board"))
		{	new Message(this,Global.resourceString("Partner_declines_the_game_"));
			Block=false;
		}		
		else if (s.startsWith("@@start"))
		{	if (PGF==null) return;
			PGF.start();
			out("@@!start");                                       //~1A8cR~
		}
		else if (s.startsWith("@@!start"))
		{	if (PGF==null) return;
			PGF.start();
		}
		else if (s.startsWith("@@move"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@move");
			String color=p.parseword();
			int i=p.parseint(),j=p.parseint();
			int bt=p.parseint(),bm=p.parseint();
			int wt=p.parseint(),wm=p.parseint();
			if (Dump.Y) Dump.println("Move of "+color+" at "+i+","+j);//~@@@1R~
			if (color.equals("b"))
			{	if (PGF.maincolor()<0) return;
				PGF.black(i,j);
				Moves.append(new ListElement
					(new PartnerMove("b",i,j,bt,bm,wt,wm)));
			}
			else
			{	if (PGF.maincolor()>0) return;
				PGF.white(i,j);
				Moves.append(new ListElement
					(new PartnerMove("w",i,j,bt,bm,wt,wm)));
			}
			PGF.settimes(bt,bm,wt,wm);
			out("@@!move "+color+" "+i+" "+j+" "+                  //~1A8cR~
				bt+" "+bm+" "+wt+" "+wm);
		}
		else if (s.startsWith("@@!move"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@!move");
			String color=p.parseword();
			int i=p.parseint(),j=p.parseint();
			int bt=p.parseint(),bm=p.parseint();
			int wt=p.parseint(),wm=p.parseint();
			if (Dump.Y) Dump.println("Move of "+color+" at "+i+","+j);//~@@@1R~
			if (color.equals("b"))
			{	if (PGF.maincolor()<0) return;
				PGF.black(i,j);
				Moves.append(new ListElement
					(new PartnerMove("b",i,j,bt,bm,wt,wm)));
			}
			else
			{	if (PGF.maincolor()>0) return;
				PGF.white(i,j);
				Moves.append(new ListElement
					(new PartnerMove("w",i,j,bt,bm,wt,wm)));
			}
			PGF.settimes(bt,bm,wt,wm);
		}
		else if (s.startsWith("@@pass"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@pass");
			int bt=p.parseint(),bm=p.parseint();
			int wt=p.parseint(),wm=p.parseint();
			if (Dump.Y) Dump.println("Pass");                      //~@@@1R~
			PGF.dopass();
			PGF.settimes(bt,bm,wt,wm);
			Moves.append(new ListElement
				(new PartnerMove("pass",bt,bm,wt,wm)));
			out("@@!pass "+bt+" "+bm+" "+wt+" "+wm);               //~1A8cR~
		}
		else if (s.startsWith("@@!pass"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@!pass");
			int bt=p.parseint(),bm=p.parseint();
			int wt=p.parseint(),wm=p.parseint();
			if (Dump.Y) Dump.println("Pass");                      //~@@@1R~
			PGF.dopass();
			Moves.append(new ListElement
				(new PartnerMove("pass",bt,bm,wt,wm)));
			PGF.settimes(bt,bm,wt,wm);
		}
		else if (s.startsWith("@@endgame"))
		{	if (PGF==null) return;
			PGF.addComment(Global.resourceString("Opponent_said_")+Global.resourceString("End_Game"));//~v1DpI~
  			Block=true;                                            //~v108I~
			new EndGameQuestion(this);
//			Block=true;                                            //~v108R~
		}
		else if (s.startsWith("@@!endgame"))
		{	if (PGF==null) return;
			PGF.addComment(Global.resourceString("Opponent_said_")+Global.resourceString("Yes"));//~v1DpI~
			PGF.doscore();
			Block=false;
		}
		else if (s.startsWith("@@-endgame"))
		{	if (PGF==null) return;
			PGF.addComment(Global.resourceString("Opponent_said_")+Global.resourceString("No"));//~v1DpI~
			new Message(this,"Partner declines game end!");
			Block=false;
		}
		else if (s.startsWith("@@result"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@result");
			int b=p.parseint();
			int w=p.parseint();
			if (Dump.Y) Dump.println("Result "+b+" "+w);           //~@@@1R~
			new ResultQuestion(this,Global.resourceString("Accept_result__B_")+b+Global.resourceString("__W_")+w+"?",b,w);
			Block=true;
		}
		else if (s.startsWith("@@!result"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@!result");
			int b=p.parseint();
			int w=p.parseint();
			if (Dump.Y) Dump.println("Result "+b+" "+w);           //~@@@1R~
			Output.append(Global.resourceString("Game_Result__B_")+b+Global.resourceString("__W_")+w+"\n",Color.green.darker());
			new Message(this,Global.resourceString("Result__B_")+b+Global.resourceString("__W_")+w+" was accepted!");
			Block=false;
		}
		else if (s.startsWith("@@-result"))
		{	if (PGF==null) return;
			new Message(this,Global.resourceString("Partner_declines_result_"));
			Block=false;
		}
		else if (s.startsWith("@@undo"))
		{	if (PGF==null) return;
  			Block=true;	//UndoQuestion is modal on subthread(wait dismiss)//~1B1kI~
			new UndoQuestion(this);
//			Block=true;                                            //~1B1kR~
		}
		else if (s.startsWith("@@-undo"))
		{	if (PGF==null) return;
			new Message(this,Global.resourceString("Partner_declines_undo_"));
			Block=false;
		}
		else if (s.startsWith("@@!undo"))
		{	if (PGF==null) return;
			PGF.undo(2);
			Moves.remove(Moves.last());
			Moves.remove(Moves.last());
			PGF.addothertime(30);
			Block=false;
		}
		else if (s.startsWith("@@adjourn"))
		{	adjourn();
		}
		else if (s.startsWith("@@restore"))
		{	Question Q=new Question(this,
				Global.resourceString("Your_partner_wants_to_restore_a_game_"),
				Global.resourceString("Accept"),null,true);
			Q.show();
			if (Q.result()) acceptrestore();
			else declinerestore();
		}
		else if (s.startsWith("@@-restore"))
		{	new Message(this,Global.resourceString("Partner_declines_restore_"));
			Block=false;
		}
		else if (s.startsWith("@@!restore"))
		{	dorestore();
			Block=false;
		}
		else if (s.startsWith("@@@"))
		{	moveinterpret(s.substring(3),false);
		}
	}

	public boolean moveset (String c, int i, int j, int bt, int bm,
		int wt, int wm)
	{	if (Block) return false;
		out("@@move "+c+" "+i+" "+j+" "+bt+" "+bm+" "+wt+" "+wm);  //~1A8cR~
		return true;
	}

	public void out (String s)
	{	Out.println(s);
    	if (Dump.Y) Dump.println("PartnerFrame out:"+s);           //~1A8cI~
	}

	public void refresh ()
	{
	}

	public void set (int i, int j)
	{
	}

	public void pass (int bt, int bm, int wt, int wm)
	{	out("@@pass "+bt+" "+bm+" "+wt+" "+wm);                    //~1A8cR~
	}

	public void endgame ()
	{	if (Block) return;
		Block=true;
		out("@@endgame");                                          //~1A8cR~
	}

	public void doendgame ()
	{	out("@@!endgame");                                         //~1A8cR~
		PGF.addComment(Global.resourceString("Said__")+Global.resourceString("Yes"));//~v1DpI~
		PGF.doscore();
		Block=false;
	}

	public void declineendgame ()
	{	out("@@-endgame");                                         //~1A8cR~
		PGF.addComment(Global.resourceString("Said__")+Global.resourceString("No"));//~v1DpI~
		Block=false;
	}

	public void doresult (int b, int w)
	{	out("@@!result "+b+" "+w);                                 //~1A8cR~
		Output.append(Global.resourceString("Game_Result__B_")+b+Global.resourceString("__W_")+w+"\n",Color.green.darker());
		Block=false;
	}

	public void declineresult ()
	{	out("@@-result");                                          //~1A8cR~
		Block=false;
	}

	public void undo ()
	{	if (Block) return;
		Block=true;
		out("@@undo");                                             //~1A8cR~
	}

	public void doundo ()
	{	out("@@!undo");                                            //~1A8cR~
		PGF.undo(2);
		Moves.remove(Moves.last());
		Moves.remove(Moves.last());
		Block=false;
		PGF.addtime(30);
	}

	public void declineundo ()
	{	out("@@-undo");                                            //~1A8cR~
		Block=false;
	}

	public void dorequest (int s, String c, int h, int tt, int et, int em)
	{	out("@@board "+c+" "+s+" "+tt+" "+et+" "+em+" "+h);        //~1A8cR~
		Block=true;
	}

	public void doboard (int Size, String C, int Handicap,
			int TotalTime, int ExtraTime, int ExtraMoves)
	{	PGF=new PartnerGoFrame(this,"Partner Game",
			C.equals("b")?-1:1,Size,TotalTime*60,ExtraTime*60,ExtraMoves,Handicap);
		if (C.equals("b")) out("@@!board b"+                       //~1A8cR~
			" "+Size+" "+TotalTime+" "+ExtraTime+" "+ExtraMoves+" "+Handicap);
		else out("@@!board w"+                                     //~1A8cR~
			" "+Size+" "+TotalTime+" "+ExtraTime+" "+ExtraMoves+" "+Handicap);
		Moves=new ListClass();
		Moves.append(new ListElement(
			new PartnerMove("board",C.equals("b")?-1:1,
				Size,TotalTime,ExtraTime,ExtraMoves,Handicap)));
	}

	public void declineboard ()
	{	out("@@-board");                                           //~1A8cR~
	}

	public void boardclosed (PartnerGoFrame pgf)
	{	if (PGF==pgf)
		{	out("@@adjourn");                                      //~1A8cR~
			savemoves();
		}
	}

	public void adjourn ()
	{	new Message(this,Global.resourceString("Your_Partner_closed_the_board_"));
        stopTimer();                                               //~v110I~
		savemoves();
		PGF=null;
	}

	public void savemoves ()
	{	Question Q=new Question(this,
			Global.resourceString("Save_this_game_for_reload_"),
			Global.resourceString("Yes"),null,true);
		Q.show();
		if (Q.result()) dosave();
	}

	public void dosave ()
//  {	FileDialog fd=new FileDialog(this,Global.resourceString("Save_Game"),FileDialog.SAVE);//~v1EeR~
    {                                                              //~v1EeI~
     	FileDialog fd=new FileDialog(this,Global.resourceString("Save_Game"),FileDialog.SAVE,false/*modal*/);//~v1EeI~
    	fd.setCallback(this,true/*PcallbackAfterDismiss*/);        //~v1EeI~
		if (!Dir.equals("")) fd.setDirectory(Dir);
//  	fd.setFile("*.sto");                                       //~v1EfR~
		String matchname=PGF.getMatchName();                       //~v1EfR~
    	fd.setSaveFilename("*.sto",matchname);                     //~v1EfI~
		fd.show();
//        String fn=fd.getFile();                                  //~v1EeR~
//        if (fn==null) return;                                    //~v1EeR~
//        Dir=fd.getDirectory();                                   //~v1EeR~
//        if (fn.endsWith(".*.*")) // Windows 95 JDK bug           //~v1EeR~
//        {   fn=fn.substring(0,fn.length()-4);                    //~v1EeR~
//        }                                                        //~v1EeR~
//        try // print out using the board class                   //~v1EeR~
//        {   PrintWriter fo=                                      //~v1EeR~
//                new PrintWriter(new FileOutputStream(fd.getDirectory()+fn),true);//~v1EeR~
//            ListElement lm=Moves.first();                        //~v1EeR~
//            while (lm!=null)                                     //~v1EeR~
//            {   PartnerMove m=(PartnerMove)lm.content();         //~v1EeR~
//                fo.println(m.Type+" "+m.P1+" "+m.P2+" "+m.P3+" "+m.P4+//~v1EeR~
//                    " "+m.P5+" "+m.P6);                          //~v1EeR~
//                lm=lm.next();                                    //~v1EeR~
//            }                                                    //~v1EeR~
//            fo.close();                                          //~v1EeR~
//        }                                                        //~v1EeR~
//        catch (IOException ex)                                   //~v1EeR~
//        {}                                                       //~v1EeR~
	}
    @Override                                                      //~v1EeI~
	public int fileDialogSaveCallback(FileDialog Pfd,String Pfnm)  //~v1EeI~
	{                                                              //~v1EeI~
        if (Dump.Y) Dump.println("PartnerFrame:fileDialogSaveCallback filename:"+Pfnm);//~v1EeI~
        FileDialog fd=Pfd;                                         //~v1EeI~
        String fn=fd.getFile();                                    //~v1EeI~
        if (fn==null) return 4;                                    //~v1EeI~
        Dir=fd.getDirectory();                                     //~v1EeI~
        if (fn.endsWith(".*.*")) // Windows 95 JDK bug             //~v1EeI~
        {   fn=fn.substring(0,fn.length()-4);                      //~v1EeI~
        }                                                          //~v1EeI~
        try // print out using the board class                     //~v1EeI~
        {   PrintWriter fo=                                        //~v1EeI~
                new PrintWriter(new FileOutputStream(fd.getDirectory()+fn),true);//~v1EeI~
            ListElement lm=Moves.first();                          //~v1EeI~
            while (lm!=null)                                       //~v1EeI~
            {   PartnerMove m=(PartnerMove)lm.content();           //~v1EeI~
                fo.println(m.Type+" "+m.P1+" "+m.P2+" "+m.P3+" "+m.P4+//~v1EeI~
                    " "+m.P5+" "+m.P6);                            //~v1EeI~
                lm=lm.next();                                      //~v1EeI~
            }                                                      //~v1EeI~
            AjagoView.showToast(R.string.InfoSaved,fd.getDirectory()+fn);//~v1EeR~
            fo.close();                                            //~v1EeI~
        }                                                          //~v1EeI~
        catch (IOException ex)                                     //~v1EeI~
        {                                                          //~v1EeI~
            Dump.println(ex,"PartnerFrame:fileDialogSaveCallback filename:"+Pfnm);//~v1EeI~
	        return 4;                                              //~v1EeI~
        }                                                          //~v1EeI~
        return 0;                                                  //~v1EeI~
	}//fileDialogSaveCallback                                      //~v1EeI~

	void acceptrestore ()
	{	out("@@!restore");                                         //~1A8cR~
	}

	void declinerestore ()
	{	out("@@-restore");                                         //~1A8cR~
	}

	void dorestore ()
	{	if (PGF!=null) return;
//  	FileDialog fd=new FileDialog(this,Global.resourceString("Load_Game"),FileDialog.LOAD);//~v1EeR~
    	FileDialog fd=new FileDialog(this,Global.resourceString("Load_Game"),FileDialog.LOAD,false/*modal*/);//~v1EeI~
        fd.setCallback(this,true/*PcallbackAfterDismiss*/);        //~v1EeI~
		if (!Dir.equals("")) fd.setDirectory(Dir);
		fd.setFile("*.sto");
		fd.show();
//        String fn=fd.getFile();                                  //~v1EeR~
//        if (fn==null) return;                                    //~v1EeR~
//        Dir=fd.getDirectory();                                   //~v1EeR~
//        if (fn.endsWith(".*.*")) // Windows 95 JDK bug           //~v1EeR~
//        {   fn=fn.substring(0,fn.length()-4);                    //~v1EeR~
//        }                                                        //~v1EeR~
//        try // print out using the board class                   //~v1EeR~
//        {   BufferedReader fi=new BufferedReader(new InputStreamReader(//~v1EeR~
//                new DataInputStream(new FileInputStream(fd.getDirectory()+fn))));//~v1EeR~
//            Moves=new ListClass();                               //~v1EeR~
//            while (true)                                         //~v1EeR~
//            {   String s=fi.readLine();                          //~v1EeR~
//                if (s==null) break;                              //~v1EeR~
//                StringParser p=new StringParser(s);              //~v1EeR~
//                out("@@@"+s);                            //~v1EeR~//~1A8cR~
//                moveinterpret(s,true);                           //~v1EeR~
//            }                                                    //~v1EeR~
//            if (PGF!=null) out("@@start");               //~v1EeR~//~1A8cR~
//            fi.close();                                          //~v1EeR~
//        }                                                        //~v1EeR~
//        catch (IOException ex)                                   //~v1EeR~
//        {}                                                       //~v1EeR~
	}
    @Override                                                      //~v1EeI~
	public int fileDialogLoadCallback(FileDialog Pfd,String Pfnm)  //~v1EeI~
	{                                                              //~v1EeI~
        if (Dump.Y) Dump.println("PartnerFrame:fileDialogLoadCallback filename:"+Pfnm);//~v1EeI~
        FileDialog fd=Pfd;                                         //~v1EeI~
        String fn=fd.getFile();                                    //~v1EeI~
        if (fn==null) return 4;                                    //~v1EeI~
        Dir=fd.getDirectory();                                     //~v1EeI~
        if (fn.endsWith(".*.*")) // Windows 95 JDK bug             //~v1EeI~
        {   fn=fn.substring(0,fn.length()-4);                      //~v1EeI~
        }                                                          //~v1EeI~
        try // print out using the board class                     //~v1EeI~
        {   BufferedReader fi=new BufferedReader(new InputStreamReader(//~v1EeI~
                new DataInputStream(new FileInputStream(fd.getDirectory()+fn))));//~v1EeI~
            Moves=new ListClass();                                 //~v1EeI~
            while (true)                                           //~v1EeI~
            {   String s=fi.readLine();                            //~v1EeI~
                if (s==null) break;                                //~v1EeI~
                StringParser p=new StringParser(s);                //~v1EeI~
                out("@@@"+s);                              //~v1EeI~//~1A8cR~
                moveinterpret(s,true);                             //~v1EeI~
            }                                                      //~v1EeI~
            if (PGF!=null) out("@@start");                 //~v1EeI~//~1A8cR~
            fi.close();                                            //~v1EeI~
        }                                                          //~v1EeI~
        catch (IOException ex)                                     //~v1EeI~
        {                                                          //~v1EeI~
            Dump.println(ex,"PartnerFrame:fileDialogLoadCallback filename:"+Pfnm);//~v1EeR~
	        return 4;                                              //~v1EeI~
        }                                                          //~v1EeI~
        return 0;                                                  //~v1EeI~
	}                                                              //~v1EeI~

	void moveinterpret (String s, boolean fromhere)
	{	StringParser p=new StringParser(s);
		String c=p.parseword();
		int p1=p.parseint();
		int p2=p.parseint();
		int p3=p.parseint();
		int p4=p.parseint();
		int p5=p.parseint();
		int p6=p.parseint();
		if (c.equals("board"))
		{	PGF=new PartnerGoFrame(this,Global.resourceString("Partner_Game"),
				fromhere?p1:-p1,p2,p3*60,p4*60,p5,p6);
			PGF.setHandicap();
		}
		else if (PGF!=null && c.equals("b"))
		{	PGF.black(p1,p2);
			PGF.settimes(p3,p4,p5,p6);
		}
		else if (PGF!=null && c.equals("w"))
		{	PGF.white(p1,p2);
			PGF.settimes(p3,p4,p5,p6);
		}
		else if (PGF!=null && c.equals("pass"))
		{	PGF.pass();
			PGF.settimes(p1,p2,p3,p4);
		}
		Moves.append(new ListElement(
			new PartnerMove(c,p1,p2,p3,p4,p5,p6)));
	}
//*FunctionKeySupport like as ConnectionFrame *******              //~@@@1I~
	public void keyPressed (KeyEvent e) {	}                      //~@@@1I~
	public void keyTyped (KeyEvent e) {	}                          //~@@@1I~
	public void keyReleased (KeyEvent e)                           //~@@@1I~
	{	String s=Global.getFunctionKey(e.getKeyCode());            //~@@@1I~
		if (s.equals("")) return;                                  //~@@@1I~
		Input.setText(s);                                          //~@@@1I~
	}                                                              //~@@@1I~
//**************************************************************************//~v110I~
    public void stopTimer()                                        //~v110I~
    {                                                              //~v110I~
		if (Dump.Y) Dump.println("PartnerFrame stopTimer");        //~v110I~
    	if (PGF!=null)                                             //~v110I~
        	if (PGF.Timer!=null)                                   //~v110I~
            	if (PGF.Timer.isAlive())                           //~v110I~
                {                                                  //~v110I~
				    if (Dump.Y) Dump.println("PartnerFrame Timer Stop");//~v110R~
                	PGF.Timer.stopit();                            //~v110I~
                }                                                  //~v110I~
    }                                                              //~v110I~
//***************************************************************  //~@@@2I~//~1Ae4R~
//* from BluetoothConnection                                       //~1Ae4I~
//***************************************************************  //~1Ae4I~
    public void disconnect()    //from IPConnection                //~@@@2I~//~1Ae4R~
    {                                                              //~@@@2I~//~1Ae4R~
        if (Dump.Y) Dump.println("PartnerFrame Disconnect");       //~@@@2I~//~1Ae4R~
        if (PGF==null)  //not game started                         //~@@@2I~//~1Ae4R~
        {                                                          //~@@@2I~//~1Ae4R~
            doclose();  //CloseConnection                          //~@@@2I~//~1Ae4R~
        }                                                          //~@@@2I~//~1Ae4R~
        else                                                       //~@@@2I~//~1Ae4R~
        if (PT!=null && PT.isAlive())                              //~@@@2I~//~1Ae4R~
            resign();                                              //~@@@2I~//~1Ae4R~
        else                                                       //~@@@2I~//~1Ae4R~
            doclose();  //CloseConnection                          //~@@@2I~//~1Ae4R~
    }                                                              //~@@@2I~//~1Ae4R~
//***************************************************************  //~1A6BI~//~1Ae4I~
//  private void setConnectionType(int Pcontype)                   //~1A6BI~//~1A8iR~//~1Ae4I~
    public void setConnectionType(int Pcontype)                    //~1A8iI~//~1Ae4I~
    {                                                              //~1A6BI~//~1Ae4I~
    	int ct=Pcontype;                                           //~1A6BI~//~1Ae4I~
//        if (Pcontype==IPConnection.NFC_CLIENT)                     //~1A6BI~//~1Ae4R~
//            ct=IPConnection.NFC_SERVER;                            //~1A6BI~//~1Ae4R~
//        else                                                       //~1A6BI~//~1Ae4R~
//        if (ct==IPConnection.WD_CLIENT)                            //~1A6BI~//~1Ae4R~
//            ct=IPConnection.WD_SERVER;                             //~1A6BI~//~1Ae4R~
        if (Pcontype==NFC_CLIENT)                                  //~1Ae4I~
            ct=NFC_SERVER;                                         //~1Ae4I~
        else                                                       //~1Ae4I~
        if (ct==WD_CLIENT)                                         //~1Ae4I~
            ct=WD_SERVER;                                          //~1Ae4I~
        connectionType=ct;                                         //~1A6BI~//~1Ae4I~
    }                                                              //~1A6BI~//~1Ae4I~
//**************************************************************************//~v110I~
//*from Ajagoc at appStop,send @@adjourn and cose out to notify termination//~v110I~
//**************************************************************************//~v110I~
    public void closeStream()                                      //~v110I~
    {                                                              //~v110I~
	    if (Dump.Y) Dump.println("PartnerFrame close Stream");     //~v110I~
        stopTimer();                                               //~v110M~
        if (Out!=null)                                             //~v110I~
        {                                                          //~v110I~
            if (Out!=null)                                         //~v110I~
            {                                                      //~v110I~
			    if (Dump.Y) Dump.println("out @@adjourn");         //~v110I~
				out("@@adjourn");                          //~v110I~//~1A8cR~
			    if (Dump.Y) Dump.println("out close");             //~v110I~
                Out.close();                                       //~v110I~
            }                                                      //~v110I~
        }                                                          //~v110I~
    }                                                              //~v110I~
//***************************************************************  //~1A8fI~
	private void getHostAddr(Socket Psocket)                       //~1A8fI~
    {                                                              //~1A8fI~
        AG.RemoteInetAddressLAN=AjagoUtils.getRemoteIPAddr(Psocket,null);//~1A8fI~
        AG.LocalInetAddressLAN=AjagoUtils.getLocalIPAddr(Psocket,null);//~1A8fR~
	    if (Dump.Y) Dump.println("PartnerFrame:getHostAddr remote="+AG.RemoteInetAddressLAN+",local="+AG.LocalInetAddressLAN);//~1A8fI~
    }                                                              //~1A8fI~
//***************************************************************  //~@@@2I~//~1Ae4I~
	public static void dismissWaitingDialog()                      //~@@@2R~//~1Ae4I~
    {                                                              //~@@@2I~//~1Ae4I~
        if (Dump.Y) Dump.println("PartnerFrame DismissWaitiingDialog");//~@@@2R~//~1Ae4I~
		ProgDlg.dismiss();                                         //~@@@2I~//~1Ae4I~
//      IPConnection.closeDialog();                                 //~1A6yI~//~1Ae4R~
        BluetoothConnection.closeDialog();                         //~1AbvI~//~1Ae4I~
        DialogNFC.closeDialog();	//close DialogNFC if showing   //~1A6sI~//~1Ae4I~
        DialogNFCBT.closeDialog();	//close DialogNFCBT if showing //~1AbjI~//~1AbjR~//~1Ae4I~
    }                                                              //~@@@2I~//~1Ae4I~
//*resign button pushed ********************                       //~1Ae4I~
	public void resign ()	//from PartnerGoFrame                  //~1Ae4I~
	{                                                              //~1Ae4I~
		new EndGameQuestion(this,AG.resource.getString(R.string.EndgameResign),true);//~1Ae4I~
	}                                                              //~1Ae4I~
//***************************************************************  //~1Ae9I~
//* for start game when Connection exists                          //~1Ae9I~
//***************************************************************  //~1Ae9I~
	public void showPF()	//move frame to front                  //~1Ae9I~
	{                                                              //~1Ae9I~
    	if (Dump.Y) Dump.println("jagoclient.PartnerFrame showPF");//~1Ae9I~
	    Window.showFrame(this);                                    //~1Ae9I~
	}                                                              //~1Ae9I~
//***************************************************************  //~1Ae9I~
//* from AjagoBT client connected                                  //~1Ae9I~
//***************************************************************  //~1Ae9I~
	public void openPartner()                                      //~1Ae9I~
	{                                                              //~1Ae9I~
    	if (Dump.Y) Dump.println("jagoclient.PartnerFrame openPartner");//~1Ae9I~
		dismissWaitingDialog();                                    //~1Ae9I~
		showPF();                                                  //~1Ae9I~
        DialogNFCBT.chkSecureLevelMsg();	//after dismiss,issue alertDialog on GameQuetion dialog//~1Ae9I~
	}                                                              //~1Ae9I~
}

