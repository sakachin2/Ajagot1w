//*CID://+1Ae4R~:                                   update#=  100; //+1Ae4R~
//****************************************************************************//~@@@1I~
//1Aec 2015/07/26 set connection type for Server                   //~1AecI~
//1Ae9 2015/07/26 (Ajagoc only)All connection type passes to jagoclient PartnerFrame to (re)start/disconnect//~1Ae9I~
//1Ae4 2015/07/24 addtional to 1Ab1. move setconnectiontype from wifidirect.PartnerFrame to jagoclient.partnerframe//~1Ae4I~
//2015/07/23 //1Ac3 2015/07/06 WD:Unpare after active session was closed//~1Ac3I~
//2015/07/23 //1Ac0 2015/07/06 for mutual exclusive problem of IP and wifidirect;try to use connectivityManager API//~1Ac0I~
//1A8o 2015/04/09 (BUG)disconnect is nop by aPartnetFrameIP==null for server size PartnerFrame//~1A8oI~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//1A8ak2015/02/28 (BUG) partnername was null                       //~1A8aI~
//1A84 2015/02/25 WiFiDirect from partnerTab                       //~1A84I~
//1A6B 2015/02/25 Asgts:2015/02/21 IP game title;identify IP and WifiDirect(WD)
//1A6s 2015/02/25 Asgts:2015/02/17 move NFC starter from WifiDirect dialog to MainFrame
//1A85 2015/02/25 close each time partnerframe for IP Connection   //~1A85I~
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
//package jagoclient.partner;                                      //~1A8cR~
package wifidirect;                                                //~1A8cI~

import jagoclient.CloseConnection;
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;



//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FileDialog;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.Panel;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import com.Ajagoc.URunnable;
import com.Ajagoc.URunnableI;

import rene.util.parser.StringParser;
import wifidirect.DialogNFC;
import wifidirect.PartnerThread;                                   //~1A8cI~

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

//public class PartnerFrame extends CloseFrame                     //~1A8cR~
public class PartnerFrame extends jagoclient.partner.PartnerFrame  //~1A8cI~
//    implements KeyListener                                         //~@@@1I~//~1A8cR~
//    ,FileDialogI                                                   //~v1EeI~//~1A8cR~
    implements URunnableI                                          //~1Ac0I~
//{	BufferedReader In;                                             //~v107R~
{	                                                               //~v107I~
//  public  static final int NFC_SERVER=1;    //identify connection type//~1A6BI~//~1Ae4R~
//  public  static final int NFC_CLIENT=2;                         //~1A6BI~//~1Ae4R~
//  public  static final int WD_SERVER=3;                          //~1A6BI~//~1Ae4R~
//  public  static final int WD_CLIENT=4;                          //~1A6BI~//~1Ae4R~
    private static final String CONTYPE_PREFIX=";";                //~1A6BI~
//    protected BufferedReader In;                                   //~v107I~//~1A8cR~
////  PrintWriter Out;                                               //~v107R~//~1A8cR~
//    protected PrintWriter Out;                                      //~v107I~//~1A8cR~
////  Viewer Output;                                                 //~v107R~//~1A8cR~
//    protected Viewer Output;                                       //~v107I~//~1A8cR~
////  HistoryTextField Input;                                        //~v107R~//~1A8cR~
//    protected HistoryTextField Input;                              //~v107I~//~1A8cR~
    Socket Server;
//  PartnerThread PT;                                              //~v107R~
//  protected PartnerThread PT;                                    //~v107I~//~1A6BR~
//    public PartnerThread PT;                                       //~1A6BI~//~1A8cR~
//    public PartnerGoFrame PGF;                                   //~1A8cR~
//    boolean Serving;                                             //~1A8cR~
//    boolean Block;                                               //~1A8cR~
//    ListClass Moves;                                             //~1A8cR~
//    String Dir;                                                  //~1A8cR~
//    String Encoding;                                               //~1109I~//~1A8cR~
//    public String partnerName="",myName="";                        //~v1D3R~//~1A8cR~
//  public int connectionType;                                     //~1A6BI~//+1Ae4R~

    public PartnerFrame (String name, boolean serving)           //~1A8cR~
	{                                                              //~1A8cI~
    	super(name,serving);                                       //~1A8cI~
        AG.aPartnerFrameIP=this;                                   //~1A8oI~
    }                                                            //~1A8cR~
	public PartnerFrame (String name, boolean serving,int Pconnectiontype)//~1A6BI~
    {                                                              //~1A6BI~
//      this(name,serving);                                        //~1A6BI~//~1AecR~
        super(name,serving,                                        //~1AecI~
        	(Pconnectiontype==NFC_SERVER||Pconnectiontype==NFC_CLIENT ? CONN_TITLE_NFC_WD : CONN_TITLE_WD)//~1AecI~
        	);                                                     //~1AecI~
        AG.aPartnerFrameIP=this;                                   //~1A8cI~
		if (Dump.Y) Dump.println("wifidirect:PartnerFrame constructor");   //~1A6BI~//~1A8cR~
		connectionType=Pconnectiontype;                            //~1A6BI~
	}                                                              //~1A6BI~

	//*************************************************************************//~1109I~
    @Override                                                      //~1A8cI~
	public boolean connect (String s, int p)
	{	if (Dump.Y) Dump.println("wifidirect:PartnerFrame:connect Starting partner connection");   //~@@@1R~//~1A8cR~
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
        	if (Dump.Y) Dump.println("wifidirect.PartnerFrame:connect cause="+e.getCause()+",msg="+e.getMessage());//~1B1jR~//~1A8cR~
            String msg=e.getMessage();                             //~1B1jI~
            if (msg!=null)                                         //~1B1jI~
	            AjagoView.showToastLong(msg);                      //~1B1jI~
            Dump.println(e,"wifidirect.PartnerFrame:connect "+s+",port="+p);  //~1B1jR~//~1A8cR~
  		 	return false;                                          //~1B1jR~
		}
        if (Dump.Y) Dump.println("wifidirect.PartnetFrame client after open");//~1Ac0I~
//      Utils.chkNetwork();        //@@@@test                      //~1Ac0I~//~1Ac3R~
		PT=new PartnerThread(In,Out,Input,Output,this);
		PT.start();
      if (connectionType!=0)                                       //~1A6BI~
		out("@@nameWD "+                                           //~1A6BR~
    		Global.getParameter("yourname","No Name")              //~1A6BR~
    		+CONTYPE_PREFIX+connectionType);                       //~1A6BI~
      else                                                         //~1A6BI~
		out("@@name "+                                             //~1A6BR~
    		Global.getParameter("yourname","No Name"));            //~1A6BI~
//      myName=Global.getParameter("yourname","No Name");          //~v1EfR~
		show();
        AG.RemoteStatus=AG.RS_IPCONNECTED;                                 //~@@@2I~//~1A6BI~
		getHostAddr(Server);                                       //~@@@2I~//~1A6BI~
		dismissWaitingDialog();                                    //~1A84I~
		return true;
	}

    @Override                                                      //~1A8cR~
	public void open (Socket server)
	{	if (Dump.Y) Dump.println("wifidirect.PartnerFrame:open Starting partner server");       //~@@@1R~//~1A8cR~
		Server=server;
		try
		{	Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
		}
		catch (Exception e)
		{	if (Dump.Y) Dump.println("wifidirect.PartnerFrame:open ---> no connection");        //~@@@1R~//~1A8cR~
			new Message(this,Global.resourceString("Got_no_Connection_"));
			return;
		}
        if (Dump.Y) Dump.println("wifidirect.PartnetFrame server after open");//~1Ac0I~
//      Utils.chkNetwork();        //@@@@test                      //~1Ac0I~//~1Ac3R~
		PT=new PartnerThread(In,Out,Input,Output,this);
		PT.start();
        AG.RemoteStatus=AG.RS_IPCONNECTED;                                 //~@@@2I~//~1A6BI~
		getHostAddr(Server);                                       //~@@@2I~//~1A6BI~
	}


	public void doclose ()
	{	Global.notewindow(this,"partner");	
      try                                                          //~1Ac0I~
      {                                                            //~1Ac0I~
        if (Dump.Y) Dump.println("wifidirect.PartnerFrame:doclose()");        //~1A6BI~//~1A8cR~
//        out("@@@@end");                                            //~1A6BR~//~1Ac0R~
//        if (Dump.Y) Dump.println("wifidirect.PartnerFrame:doclose issue Out.close()");//~1A6BI~//~1A8cR~//~1Ac0R~
//        Out.close();                                             //~1Ac0R~
//        new CloseConnection(Server,In);                          //~1Ac0R~
//        super.doclose();                                         //~1Ac0R~
        URunnable.setRunFunc(this/*URunnableI*/,500/*delay*/,this/*objparm*/,0/*int parm*/);//~1Ac0I~
      }                                                            //~1Ac0I~
      catch(Exception e)                                           //~1Ac0I~
      {                                                            //~1Ac0I~
      	Dump.println(e,"wifidirect.partnerFrame:doclose");         //~1Ac0I~
      }                                                            //~1Ac0I~
	}

	/**
	The interpreter for the partner commands (all start with @@).
	*/
    @Override                                                      //~1A8cI~
	public void interpret (String s)
	{                                                              //~v107I~
        if (Dump.Y) Dump.println("wifidirect.PartnerFrame:interpret:"+s);     //~v107I~//~1A8cR~
		if (s.startsWith("@@nameWD"))                              //~1A6BI~
		{	StringParser p=new StringParser(s);                    //~1A6BI~
			p.skip("@@nameWD");                                    //~1A6BI~
//          String partnerName=p.upto(CONTYPE_PREFIX.charAt(0)).trim();//~1A6BI~//~1A8aR~
            partnerName=p.upto(CONTYPE_PREFIX.charAt(0)).trim();   //~1A8aI~
//  		setTitle(Global.resourceString("Connection_to_")+partnerName);//~1A6BI~//~1A8iR~
            p.advance();                                           //~1A6BI~
            int ct=p.parseint();                                   //~1A6BI~
            setConnectionType(ct);                                 //~1A6BI~
    		setTitle(getConnectionTypeTitleString(ct)+Global.resourceString("Connection_to_")+partnerName);//~1A8iI~
        	if (Dump.Y) Dump.println("wifidirect.PartnerFrame:interpret setTitle:"+Global.resourceString("Connection_to_")+partnerName);//~1A6BI~//~1A8cR~
        	if (Dump.Y) Dump.println("wifidirect.PartnerFrame:interpret connectiontype="+connectionType);//~1A6BI~//~1A8cR~
        	myName=Global.getParameter("yourname","No Name");      //~1A6BI~
			out("@@!nameWD "+myName);                              //~1A6BI~
		}                                                          //~1A6BI~
		else if (s.startsWith("@@!nameWD"))                        //~1A6BI~
		{	StringParser p=new StringParser(s);                    //~1A6BI~
//  		p.skip("@@!nameBT");                                   //~1A6BI~//~1A8aR~
    		p.skip("@@!nameWD");                                   //~1A8aI~
    		String pn=p.upto('!').trim();                          //~1A6BI~
        	myName=Global.getParameter("yourname","No Name");      //~1A6BI~
            if (!pn.equals(myName))	//same name                    //~1A6BI~
            	partnerName=pn.trim();                             //~1A6BI~
//  		setTitle(Global.resourceString("Connection_to_")+partnerName);//~1A8aI~//~1A8iR~
    		setTitle(getConnectionTypeTitleString(connectionType)+Global.resourceString("Connection_to_")+partnerName);//~1A8iI~
        	if (Dump.Y) Dump.println("wifidirect.PartnerFrame:interpret receivedWD:partnername="+partnerName);//~1A6BI~//~1A8aR~//~1A8cR~
		}                                                          //~1A6BI~
		else if (s.startsWith("@@board"))                          //~1A8cI~
		{	if (PGF!=null) return;                                 //~1A8cI~
            dismissWaitingDialog();                                //~1A8cI~
			super.interpret(s);                                    //~1A8cI~
		}                                                          //~1A8cI~
        else                                                       //~1A8cI~
			super.interpret(s);                                    //~1A8cR~
	}

//***************************************************************  //~@@@2I~
	public void disconnect()	//from IPConnection                //~@@@2I~
    {                                                              //~@@@2I~
        if (Dump.Y) Dump.println("wifidirect.PartnerFrame:disconnect");       //~@@@2I~//~1A8cR~
        if (PGF==null)	//not game started                         //~@@@2I~
        {                                                          //~@@@2I~
			doclose();	//CloseConnection                          //~@@@2I~
        }                                                          //~@@@2I~
//      else                                                       //~@@@2I~//~1A6BR~
//  	if (PT!=null && PT.isAlive())                              //~@@@2I~//~1A6BR~
//  		resign();   //resign not implemented                  //~@@@2I~//~1A6BR~
        else                                                       //~@@@2I~
			doclose();	//CloseConnection	                       //~@@@2I~
    }                                                              //~@@@2I~
//***************************************************************  //~1Ac3I~
	public void disconnect(boolean Punpair)	//from IPConnection    //~1Ac3I~
    {                                                              //~1Ac3I~
        if (Dump.Y) Dump.println("PartnerFrame Disconnect Punpair="+Punpair);//~1Ac3I~
    	if (PT!=null)                                              //~1Ac3I~
			((wifidirect.PartnerThread)PT).unpair(Punpair);        //~1Ac3I~
		doclose();	//CloseConnection                              //~1Ac3I~
    }                                                              //~1Ac3I~
//***************************************************************  //~@@@2I~
	public static void dismissWaitingDialog()                      //~@@@2R~
    {                                                              //~@@@2I~
        if (Dump.Y) Dump.println("wifidirect.PartnerFrame:dismissWaitiingDialog");//~@@@2R~//~1A8cR~
		ProgDlg.dismiss();                                         //~@@@2I~
//      IPConnection.closeDialog();                                //~1A6yI~
        DialogNFC.closeDialog();	//close DialogNFC if showing   //~1A6sI~
    }                                                              //~@@@2I~
//***************************************************************  //~@@@2I~
	private void getHostAddr(Socket Psocket)                       //~@@@2I~
    {                                                              //~@@@2I~
//        InetAddress ia=Psocket.getInetAddress();                   //~@@@2I~//~1A8fR~
//        if (ia!=null)                                              //~@@@2M~//~1A8fR~
//        {                                                          //~@@@2M~//~1A8fR~
//            if (Dump.Y) Dump.println("wifidirect.PartnerFrame:getHostAddr inet address="+ia.getHostAddress()+",name="+ia.getHostName());//~@@@2I~//~1A8cR~//~1A8fR~
//            if (Dump.Y) Dump.println("wifidirect.PartnerFrame:getHostAddr server inet address="+ia.toString());//~@@@2I~//~1A8cR~//~1A8fR~
//            AG.RemoteInetAddress=ia.getHostAddress();              //~@@@2R~//~1A8fR~
//        }                                                          //~@@@2M~//~1A8fR~
////      if (Dump.Y) Dump.println("wifidirect.PartnerFrame:getHostAddr client local address="+Server.getLocalAddress().toString());//~@@@2M~//~1A6sR~//~1A8cR~//~1A8fR~
//        ia=Psocket.getLocalAddress();                              //~1A6sI~//~1A8fR~
//        if (ia!=null)                                              //~1A6sI~//~1A8fR~
//        {                                                          //~1A6sI~//~1A8fR~
//            AG.LocalInetAddress=ia.getHostAddress();               //~1A6sI~//~1A8fR~
//            if (Dump.Y) Dump.println("wifidirect.PartnerFrame:getHostAddr server inet localaddress="+AG.LocalInetAddress);//~1A6sI~//~1A8cR~//~1A8fR~
//        }                                                          //~1A6sI~//~1A8fR~
        AG.RemoteInetAddress=AjagoUtils.getRemoteIPAddr(Psocket,null);//~1A8fI~
        AG.LocalInetAddress=AjagoUtils.getLocalIPAddr(Psocket,null);//~1A8fR~
	    if (Dump.Y) Dump.println("wifidirect.PartnerFrame:getHostAddr remote="+AG.RemoteInetAddressLAN+",local="+AG.LocalInetAddressLAN);//~1A8fI~
    }                                                              //~@@@2I~
////***************************************************************  //~1A6BI~//~1Ae4R~
//    private void setConnectionType(int Pcontype)                   //~1A6BI~//~1Ae4R~
//    {                                                              //~1A6BI~//~1Ae4R~
//        int ct=Pcontype;                                           //~1A6BI~//~1Ae4R~
//        if (Pcontype==NFC_CLIENT)                     //~1A6BI~  //~1Ae4R~
//            ct=NFC_SERVER;                            //~1A6BI~  //~1Ae4R~
//        else                                                       //~1A6BI~//~1Ae4R~
//        if (ct==WD_CLIENT)                            //~1A6BI~  //~1Ae4R~
//            ct=WD_SERVER;                             //~1A6BI~  //~1Ae4R~
//        connectionType=ct;                                         //~1A6BI~//~1Ae4R~
//    }                                                              //~1A6BI~//~1Ae4R~
//***************************************************************  //~1A8iI~
    private String getConnectionTypeTitleString(int Pct)           //~1A8iI~
    {                                                              //~1A8iI~
    	if (Pct==NFC_CLIENT||Pct==NFC_SERVER)                      //~1A8iI~
//      	return jagoclient.partner.PartnerFrame.CONN_TITLE_NFC; //~1A8iR~//~1AecR~
        	return jagoclient.partner.PartnerFrame.CONN_TITLE_NFC_WD;//~1AecI~
        return jagoclient.partner.PartnerFrame.CONN_TITLE_WD;      //~1A8iR~
    }                                                              //~1A8iI~
//***************************************************************  //~1Ac0I~
	@Override                                                      //~1Ac0I~
	public void runFunc(Object parmObj,int parmInt/*0*/)           //~1Ac0I~
    {                                                              //~1Ac0I~
//   	PartnerFrame pf=(PartnerFrame)parmObj;                     //~1Ac0I~
        if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose runFunc");//~1Ac0I~
      	try                                                        //~1Ac0I~
      	{                                                          //~1Ac0I~
        	Out.close();                                           //~1Ac0I~
          	if (Dump.Y) Dump.println("wifidirect.PartnerFrame:doclose issue Out.close()");//~1Ac0I~
            new CloseConnection(Server,In);                        //~1Ac0I~
    	    doclose2();  //do not out @@@@end,CloseConnection close Server and In//~1Ac0I~
      	}                                                          //~1Ac0I~
      	catch(Exception e)                                         //~1Ac0I~
      	{                                                          //~1Ac0I~
      		Dump.println(e,"wifidirect.partnerFrame:runfunc");     //~1Ac0I~
      	}                                                          //~1Ac0I~
    }                                                              //~1Ac0I~
//***************************************************************  //~1Ae4I~
//*from IPConnection;open jagoclient PartnerFrame                  //~1Ae9I~
//***************************************************************  //~1Ae9I~
	public void openPF()                                           //~1Ae9R~
    {                                                              //~1Ae4I~
        if (Dump.Y) Dump.println("wifidirect.PartnerFrame:openPF");//~1Ae4I~//~1Ae9R~
    }                                                              //~1Ae4I~
}

