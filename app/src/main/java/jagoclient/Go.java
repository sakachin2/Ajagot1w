//*CID://+1AgcR~:                             update#=   68;       //+1AgcR~
//************************************************************************//~1A84I~
//1Agc 2016/10/11 avoid to show nfc dialog when nfc is not attached, and set visibility=gone to nfc button//+1AgcI~
//1Aga 2016/10/09 1Ag9 has effect, but avaoi duplicated board generation//~1AgaI~
//1Ag1 2016/10/06 Change Top panel. set menu panel as tabwidget.   //~1Ag1I~
//1Afp 2016/09/30 duplicate check of open goframe                  //~1AfpI~
//1Afa 2016/07/11 Delete main function to avoid selected main as entrypoint for eclips starter//~1AfaI~
//1Af9 2016/07/11 Additional to Server/Partner List update fuction, up/down/undelete.//~1Af9I~
//1Aeo 2015/08/09 (Bug)Bluetooth button was not effective          //~1AeoI~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//2015/07/24 //1Ad3 2015/07/19 Bypass NFCSelect, by NFC-WD and NFC-BT button directly.//~1Ad3I~
//2015/07/23 //1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8I~
//2015/07/23 //1Ab7 2015/05/03 NFC Bluetooth handover v2           //~1Ab7I~
//2015/07/23 //1Ab6 2015/05/03 NFC Bluetooth handover change(once delete 1Ab1)//~1Ab1I~
//2015/07/23 //1Ab1 2015/05/03 NFC Bluetooth handover              //~1Ab1I~
//1A8t 2015/04/15 add help to top panel                            //~1A8tI~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//1A8h 2015/03/05 popup OpenPartnerFrame by "Open?" button if hidden//~1A8hI~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1A84 2015/02/25 WiFiDirect from partnerTab                       //~1A84I~
//************************************************************************//~1A84I~
package jagoclient;

/*
This file contains the Go applet and its main method.
*/

import jagoclient.board.Board;                                     //~1420R~
import jagoclient.board.GoFrame;
import jagoclient.board.WoodPaint;
import jagoclient.dialogs.GetParameter;
import jagoclient.dialogs.HelpDialog;
import jagoclient.dialogs.Message;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CardPanel;
import jagoclient.gui.CloseFrame;                                                           //~1112I~
import jagoclient.gui.DoActionListener;
import jagoclient.gui.MyLabel;
import jagoclient.gui.MyPanel;
import jagoclient.igs.Connect;
import jagoclient.igs.ConnectionFrame;
import jagoclient.igs.connection.Connection;
import jagoclient.igs.connection.EditConnection;
import jagoclient.partner.BluetoothConnection;
import jagoclient.partner.ConnectPartner;
import jagoclient.partner.OpenPartnerFrame;
import jagoclient.partner.PartnerFrame;
import jagoclient.partner.partner.EditPartner;
import jagoclient.partner.partner.Partner;
import jagoclient.sound.JagoSound;









//import android.widget.Button;                                      //~1109I~
//import java.awt.Component;                                       //~1318R~
//import java.awt.Frame;                                           //~1318R~
//import java.awt.Panel;                                           //~1318R~
//import java.awt.event.ActionEvent;                               //~1318R~
//import java.awt.event.ActionListener;                            //~1318R~
import java.io.BufferedReader;

import android.view.View;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoMenu;
import com.Ajagoc.AjagoOptions;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;
import com.Ajagoc.java.FileOutputStream;

import java.io.PrintWriter;                                        //~1401R~
import java.util.Locale;

import com.Ajagoc.awt.ActionEvent;
import com.Ajagoc.awt.ActionListener;
import com.Ajagoc.awt.Applet;
import com.Ajagoc.awt.BorderLayout;
import com.Ajagoc.awt.Component;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.List;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.Button;
import com.Ajagoc.awt.Window;

import rene.util.list.ListClass;
import rene.util.list.ListElement;
import wifidirect.DialogNFC;
import wifidirect.DialogNFCBT;
import wifidirect.DialogNFCSelect;
import wifidirect.IPConnection;                                    //~1A84R~
import wifidirect.WDANFC;

/**
To get the password, when there is none (but a user name) and a server
connection is requested. Some users do not like their password to
be permanently written to the server.cfg file.
*/

class GetPassword extends GetParameter
{	Connection C;
	Go G;
//  public String Password;                                        //~1325R~
    public String Password="";	//@@@@ for modal process;avoid NPE //~1325I~
	public GetPassword (Frame f, Go g, Connection c)
	{	super(f,Global.resourceString("Enter_Password_"),          //~1524R~
			Global.resourceString("Password"),g,'*',true);
		C=c; G=g;
		show();
	}
	public boolean tell (Object o, String s)
	{	Password=s;
		return true;
	}
}

/**
The Go applet is an applet, which resides in a frame opened
from the main() method of Go (or generated by the WWW applet
that starts Go). This frame is of class MainFrame.
<P>
It contains a card panel with two panels: the server connections and
the partner connections.
<P>
This applet handles all the buttons in the server and the
partner panels.
<P>
Several private dialogs are used to edit the connection parameters,
get the password (if there is none in server.cfg, but automatic login is
requested), ask for closing the Jago application altogether and
read in other parameters.
@see MainFrame
*/

public class Go extends Applet
	implements DoActionListener, ActionListener
{	int Test=0;
//  java.awt.List L,PL;                                            //~1524I~
/*@@@@                                                             //~3505I~
    private List L,PL;                                        //~1109I~//~1111R~//~1112R~//~1114R~
@@@@*/                                                             //~3505I~
    public  List L,PL;                                             //~3505I~
	ListClass ConnectionList,PartnerList;
  	private ListElement deleted_server,deleted_partner,deleted_server_prev,deleted_partner_prev;//~1Af9I~
	Button CConnect,CEdit,CAdd,CDelete,                           //~1524R~
		PConnect,PEdit,PAdd,PDelete,POpen;
	Button CUnDelete,CListUp,CListDown;                            //~1Af9I~
	static Go go;
	String Server="",MoveStyle="",Encoding="";
	int Port;
	public OpenPartnerFrame OPF=null;
	/** Constructor for use with a specific server and port */
	public Go (String server, int port, String movestyle, String encoding)
	{	Server=server; Port=port;  MoveStyle=movestyle; Encoding=encoding;
	}

	public Go ()
	{	Server=""; Port=0; MoveStyle=""; Encoding="";
	}

	public void actionPerformed (ActionEvent e)                    //~1524R~
	{	if (e.getSource()==L)                                      //~1524R~
		{	doAction("ConnectServer");                             //~1524R~
		}
		if (e.getSource()==PL)
		{	doAction("ConnectPartner");                            //~1524R~
		}
		else doAction(e.getActionCommand());                       //~1524R~
	}

	/**
	This init routines has two flavours. One is for specific servers
	(as used when the applet is on a Web page of the go server) and
	one for general servers.
	<P>
	The general setup will create a server and a partner panel. Those
	will be put into a card panel using the CardPanel class. The class
	completely builds these two panels int this version.
	@see jagoclient.gui.CardPanel
	*/
	
    public void init ()
	{	setLayout(new BorderLayout());                             //~1524R~
		if (Server.equals("")) // general setup
		{	// create a card panel
			CardPanel cardp=new CardPanel();                       //~1524R~
			// Server connections panel                            //~1524I~
			Panel p1=new MyPanel();                                //~1524R~
			p1.setLayout(new BorderLayout());                      //~1524R~
			// add north label                                     //~1524R~
			p1.add("North",                                        //~1524R~
				new MyLabel(Global.resourceString("Server_Connections__")));//~1524R~
			// add button panel
			Panel p=new MyPanel();                                 //~1524R~
			Global.setcomponent((Component)p);                     //~1524R~
			p.add(CConnect=new ButtonAction(this,                  //~1524R~
				Global.resourceString("Connect"),"ConnectServer"));//~1524R~
			p.add(new MyLabel(" "));                               //~1524R~
			p.add(CEdit=new ButtonAction(this,                     //~1524R~
				Global.resourceString("Edit"),"EditServer"));      //~1524R~
			p.add(CAdd=new ButtonAction(this,                      //~1524R~
				Global.resourceString("Add"),"AddServer"));        //~1524R~
			p.add(CDelete=new ButtonAction(this,                   //~1524R~
				Global.resourceString("Delete"),"DeleteServer"));  //~1524R~
			p.add(CUnDelete=new ButtonAction(this,                 //~1Af9I~
				Global.resourceString("UnDelete"),"UnDeleteServer"));//~1Af9I~
			p.add(CListUp=new ButtonAction(this,                   //~1Af9I~
				Global.resourceString("ListUp"),"ListUpServer"));  //~1Af9I~
			p.add(CListUp=new ButtonAction(this,                   //~1Af9I~
				Global.resourceString("ListDown"),"ListDownServer"));//~1Af9I~
			p1.add("South",p);                                     //~1524R~
			// add center list with servers
//  		L=new java.awt.List();                                 //~1524I~
			L=new List();                                          //~1524R~
			L.addActionListener(this);                             //~1524R~
			L.setFont(Global.Monospaced);                          //~1524R~
			L.setBackground(Global.gray);                          //~1524R~
			Connection c;
			String l;
			ConnectionList=new ListClass();
			try // read servers from server.cfg
			{	BufferedReader in=Global.getStream(".server.cfg");
				while (true)
				{	l=in.readLine();
                	if (Dump.Y) Dump.println("server.cfg read:"+l);//~1Af9R~
					if (l==null || l.equals("")) break;
					c=new Connection(l);
                	if (Dump.Y) Dump.println("server.cfg valid"+c.valid());//~1Af9R~
					if (c.valid())
					{	L.add(c.Name);
						ConnectionList.append(new ListElement(c));
					}
					else break;
				}
				in.close();
			}
			catch (Exception ex) {                                 //~1329R~
            	Dump.println(ex,"server.cfg read");                 //~1329I~
			}                                                      //~1329I~
                                                                   //~1108I~
                                                                   //~1108I~
			if (L.getItemCount()>0) L.select(0);                   //~1524R~
			p1.add("Center",L);                                    //~1524R~
			// add the partner panel to the card panel
			cardp.add(p1,Global.resourceString("Server_Connections"));//~1524I~
			// partner connections panel
			Panel p2=new MyPanel();                                //~1524R~
			p2.setLayout(new BorderLayout());                      //~1524R~
			// north label                                         //~1524R~
			p2.add("North",                                        //~1524R~
				new MyLabel(Global.resourceString("Partner_Connections__")));//~1108R~//~1524R~
			// list class for partner connections
//  		PL=new java.awt.List();                                //~1524I~
			PL=new List();                                         //~1524R~
			PL.addActionListener(this);                            //~1524R~
			PL.setFont(Global.Monospaced);                         //~1524R~
			PL.setBackground(Global.gray);                         //~1524R~
			Partner cp;
			PartnerList=new ListClass();
			try // read connections from partner.cfg
			{	BufferedReader in=Global.getStream(".partner.cfg");
				while (true)
				{	l=in.readLine();
					if (l==null || l.equals("")) break;
					cp=new Partner(l);
					if (cp.valid())
					{	PL.add(cp.Name);
						PartnerList.append(new ListElement(cp));
					}
					else break;
				}
				in.close();
			}
			catch (Exception ex) {}
			if (PL.getItemCount()>0) PL.select(0);                 //~1524R~
			Global.PartnerList=PartnerList;
			p2.add("Center",PL);                                   //~1524R~
			// button panel
			Panel pp=new MyPanel();                                //~1524R~
			pp.add(PConnect=new ButtonAction(this,                 //~1524R~
				Global.resourceString("Connect"),"ConnectPartner"));//~1524R~
			pp.add(new MyLabel(" "));                              //~1524R~
			pp.add(PEdit=new ButtonAction(this,                    //~1524R~
				Global.resourceString("Edit"),"EditPartner"));     //~1524R~
			pp.add(PAdd=new ButtonAction(this,                     //~1524R~
				Global.resourceString("Add"),"AddPartner"));       //~1524R~
			pp.add(PDelete=new ButtonAction(this,                  //~1524R~
				Global.resourceString("Delete"),"DeletePartner")); //~1524R~
//  		pp.add(new MyLabel(" "));
			p.add(CUnDelete=new ButtonAction(this,                 //~1Af9I~
				Global.resourceString("UnDelete"),"UnDeletePartner"));//~1Af9I~
			p.add(CListUp=new ButtonAction(this,                   //~1Af9I~
				Global.resourceString("ListUp"),"ListUpPartner")); //~1Af9I~
			p.add(CListUp=new ButtonAction(this,                   //~1Af9I~
				Global.resourceString("ListDown"),"ListDownPartner"));//~1Af9I~
			pp.add(POpen=new ButtonAction(this,                    //~1524R~
				Global.resourceString("Open_")));                  //~1524R~
			p2.add("South",pp);                                    //~1524R~
			cardp.add(p2,Global.resourceString("Partner_Connections"));//~1524R~
                                                                   //~1A84I~//~1b1cM~//~1A84M~
//          if (AG.osVersion>=AG.ICE_CREAM_SANDWICH)  //android4   //~1A84I~//~1AeoR~
            {                                                      //~1A84I~
                ButtonAction ba;                                   //~1AeoI~
                boolean swNONFC=(WDANFC.getAdapter()==null);      //+1AgcI~
              ba=                                                  //~1AeoI~
				new ButtonAction(this,R.string.WiFiDirectButton,R.id.WiFiDirectButton);//~1A84R~
                if (AG.osVersion<AG.ICE_CREAM_SANDWICH)            //~1AeoI~
                	ba.setEnabled(false);                          //~1AeoI~
              ba=                                                  //~1AeoI~
				new ButtonAction(this,R.string.WiFiNFCButton,R.id.WiFiNFCButton);//~1A84R~
              if (swNONFC)                                         //+1AgcI~
                	ba.setVisibility(View.GONE);                   //+1AgcI~
              else                                                 //+1AgcI~
                if (AG.osVersion<AG.ICE_CREAM_SANDWICH)            //~1AeoI~
                	ba.setEnabled(false);                          //~1AeoI~
              ba=                                                  //~1AeoI~
				new ButtonAction(this,R.string.BTNFCButton,R.id.BTNFCButton);//~1Ab1I~
              if (swNONFC)                                         //+1AgcI~
                	ba.setVisibility(View.GONE);                   //+1AgcI~
              else                                                 //+1AgcI~
                if (AG.osVersion<AG.ICE_CREAM_SANDWICH)            //~1AeoI~
                	ba.setEnabled(false);                          //~1AeoI~
				new ButtonAction(this,R.string.BluetoothButton,R.id.BluetoothButton);//~1Ae5I~
            }                                                      //~1A84I~
//*add 3rd tabwidget                                               //~1Ag1M~
    		addTopPanel();                                         //~1Ag1R~
            View lv=AG.mainframe.framelayoutview;            //~1A8tI~
			new ButtonAction(this,lv,R.string.Close,R.id.Close); //setup Help button listener//~1Ag1I~
			new ButtonAction(this,lv,R.string.Help,R.id.Help); //setup Help button listener//~1A8tR~
		}
		else // specific server setup
		{	// similar to the above, but simpler and using a single panel only
			Panel p1=new MyPanel();                                //~1524R~
			p1.setLayout(new BorderLayout());                      //~1524R~
			p1.add("North",                                        //~1524R~
				new MyLabel(Global.resourceString("Server_Connection__")));//~1524R~
			Panel p=new MyPanel();                                 //~1524R~
			Global.setcomponent((Component)p);                     //~1524R~
			p.add(CConnect=new ButtonAction(this,                  //~1524R~
				Global.resourceString("Connect"),"ConnectServer"));//~1524R~
			p1.add("South",p);                                     //~1524R~
//  		L=new java.awt.List();                                 //~1524I~
			L=new List();                                          //~1524R~
			L.setFont(Global.Monospaced);                          //~1524R~
			L.setBackground(Global.gray);                          //~1524R~
			L.addActionListener(this);                             //~1524R~
			Connection c;
			String l;
			ConnectionList=new ListClass();
			ConnectionList.append(new ListElement(
				new Connection("["+Server+"] ["+Server+"] ["+Port+"] ["+"] ["+"] ["+MoveStyle+"] ["+Encoding+"]")));
			L.add(Server);                                         //~1524R~
			if (L.getItemCount()>0) L.select(0);                   //~1524R~
			p1.add("Center",L);                                    //~1524R~
			add("Center",p1);                                      //~1524R~
		}
	}
    
  	public void doAction (String o)
  	{	if ("ConnectServer".equals(o))
//  		{	String s=L.getSelectedItem();                      //~1AfpR~
  		{                                                          //~1AfpI~
            if (Window.IsThereAnyOpenedGoFrame())                  //~1AfpI~
            	return;                                            //~1AfpI~
  			String s=L.getSelectedItem();                          //~1AfpI~
  			if (s==null) return;
  			Connection c=find(L.getSelectedItem());
  			if (c!=null) // try to connect, if not already tried
  			{	if (c.Trying)
  				{	new Message(Global.frame(),                    //~1524R~
  						Global.resourceString("Already_trying_this_connection"));
  					return;
  				}
  				if (c.Password.equals("") && !c.User.equals("")
  					&& Global.getParameter("automatic",true)) 
  				// get password, if there is a user name, but no password,
  				// and automatic login
  				{	GetPassword GP=new GetPassword(F,this,c);      //~1524R~
                    if (Dump.Y) Dump.println("Password="+GP.Password);   //~1127I~//~1Af9R~
  					if (GP.Password.equals("")) return;
  					// create a connection frame and connect via
	  				// the connect class
  					ConnectionFrame cf=
  						new ConnectionFrame(
  							Global.resourceString("Connection_to_")+c.Name+
  							Global.resourceString("_as_")+
	  					c.User,c.Encoding);
  					Global.setwindow(cf,"connection",500,400);
	  				new Connect(c,GP.Password,cf);
  				}
  				else
  				{	// create a connection frame and connect via
	  				// the connect class
  					ConnectionFrame cf=
  						new ConnectionFrame(
  							Global.resourceString("Connection_to_")+c.Name+
  							Global.resourceString("_as_")+
	  					c.User,c.Encoding);
  					Global.setwindow(cf,"connection",500,400);
	  				new Connect(c,cf);
	  			}
  				return;
  			}
  		}
  		else if ("ConnectPartner".equals(o))
// 		{	String s=PL.getSelectedItem();                         //~1524R~//~1A8gR~
		{                                                          //~1A8gI~
            if (Window.IsThereAnyOpenedGoFrame())                  //~1AfpI~
            	return;                                            //~1AfpI~
    		if (isAliveOtherSession(AG.AST_IP,true))               //~1A8gI~
            	return;                                            //~1A8gI~
			String s=PL.getSelectedItem();                         //~1A8gI~
  			if (s==null) return;
  			Partner c=pfind(PL.getSelectedItem());                 //~1524R~
  			if (c!=null) // try connecting to this partner server, if not trying already
  			{	if (c.Trying)
  				{	new Message(Global.frame(),Global.resourceString("Already_trying_this_connection"));
  					return;
  				}
  				// create a PartnerFrame and connect via ConnectPartner class
  				PartnerFrame cf=
  					new PartnerFrame(
//						Global.resourceString("Connection_to_")+c.Name,false);//~1A8iR~
  						Global.resourceString("Connection_to_")+c.Name,false,//~1A8iI~
                        jagoclient.partner.PartnerFrame.CONN_TITLE_IP);         //~1A8iI~
				Global.setwindow(cf,"partner",500,400);
  				new ConnectPartner(c,cf);
  			}
  		}
  		else if ("EditServer".equals(o))
  		{	String s=L.getSelectedItem();                          //~1524R~
  			if (s==null) return;
  			Connection c=find(L.getSelectedItem());                //~1524R~
  			if (c!=null)
  			{	new EditConnection(F,ConnectionList,c,this);
  			}
  		}
  		else if ("EditPartner".equals(o))
  		{	String s=PL.getSelectedItem();                         //~1524R~
  			if (s==null) return;
  			Partner c=pfind(PL.getSelectedItem());                 //~1524R~
  			if (c!=null)
  			{	new EditPartner(F,PartnerList,c,this);
  			}
  		}
  		else if ("AddServer".equals(o))
  		{	new EditConnection(F,ConnectionList,this);
  		}
  		else if ("AddPartner".equals(o))
  		{	new EditPartner(F,PartnerList,this);
  		}
  		else if ("DeleteServer".equals(o) && L.getSelectedItem()!=null)//~1111R~//~1114R~
  		{	ListElement lc=ConnectionList.first();
  			Connection co;
  			while (lc!=null)
  			{	co=(Connection)lc.content();
  				if (co.Name.equals(L.getSelectedItem()))
//				{	ConnectionList.remove(lc);                     //~1Af9R~
  				{	                                               //~1Af9I~
                	deleted_server=lc;                             //~1Af9I~
                    deleted_server_prev=lc.previous();             //~1Af9R~
  					lc=lc.next(); //set selection                  //~1Af9M~
  					ConnectionList.remove(deleted_server);         //~1Af9R~
                    break;                                         //~1Af9I~
  				}
  				lc=lc.next();
  			}
  			updatelist();
			setSelection(L,ConnectionList,lc);                     //~1Af9I~
  		}
  		else if ("UnDeleteServer".equals(o) && L.getSelectedItem()!=null)//~1Af9I~
  		{                                                          //~1Af9I~
            if (deleted_server==null)                              //~1Af9I~
            {                                                      //~1Af9I~
            	AjagoView.showToast(R.string.NoDeletedEntry);      //~1Af9I~
            }                                                      //~1Af9I~
            else                                                   //~1Af9I~
            {                                                      //~1Af9I~
                ConnectionList.insert(deleted_server,deleted_server_prev);//~1Af9R~
                updatelist();                                      //~1Af9I~
				setSelection(L,ConnectionList,deleted_server);                 //~1Af9R~
                deleted_server=null;                               //~1Af9M~
            }                                                      //~1Af9I~
  		}                                                          //~1Af9I~
  		else if ("ListUpServer".equals(o) && L.getSelectedItem()!=null)//~1Af9I~
  		{	ListElement lc=ConnectionList.first();                 //~1Af9I~
  			Connection co;                                         //~1Af9I~
  			while (lc!=null)                                       //~1Af9I~
  			{	co=(Connection)lc.content();                       //~1Af9I~
  				if (co.Name.equals(L.getSelectedItem()))           //~1Af9I~
  				{                                                  //~1Af9I~
					ConnectionList.moveUp(lc);                     //~1Af9I~
                    break;                                         //~1Af9I~
  				}                                                  //~1Af9I~
  				lc=lc.next();                                      //~1Af9I~
  			}                                                      //~1Af9I~
  			updatelist();                                          //~1Af9I~
			setSelection(L,ConnectionList,lc);                     //~1Af9R~
  		}                                                          //~1Af9I~
  		else if ("ListDownServer".equals(o) && L.getSelectedItem()!=null)//~1Af9I~
  		{	ListElement lc=ConnectionList.first();                 //~1Af9I~
  			Connection co;                                         //~1Af9I~
  			while (lc!=null)                                       //~1Af9I~
  			{	co=(Connection)lc.content();                       //~1Af9I~
  				if (co.Name.equals(L.getSelectedItem()))           //~1Af9I~
  				{                                                  //~1Af9I~
					ConnectionList.moveDown(lc);                   //~1Af9I~
                    break;                                         //~1Af9I~
  				}                                                  //~1Af9I~
  				lc=lc.next();                                      //~1Af9I~
  			}                                                      //~1Af9I~
  			updatelist();                                          //~1Af9I~
			setSelection(L,ConnectionList,lc);                     //~1Af9R~
  		}                                                          //~1Af9I~
  		else if ("DeletePartner".equals(o) && PL.getSelectedItem()!=null)//~1111R~//~1114R~
  		{	ListElement lc=PartnerList.first();
  			Partner co;
  			while (lc!=null)
  			{	co=(Partner)lc.content();
  				if (co.Name.equals(PL.getSelectedItem()))          //~1111R~//~1114R~
//				{	PartnerList.remove(lc);                        //~1Af9R~
  				{                                                  //~1Af9I~
                	deleted_partner=lc;                            //~1Af9I~
                    deleted_partner_prev=lc.previous();            //~1Af9I~
  					lc=lc.next(); //set selection                  //~1Af9I~
  					PartnerList.remove(deleted_partner);           //~1Af9I~
                    break;                                         //~1Af9I~
  				}
  				lc=lc.next();
  			}
  			updateplist();
			setSelection(PL,PartnerList,lc);                       //~1Af9I~
  		}
  		else if ("UnDeletePartner".equals(o) && PL.getSelectedItem()!=null)//~1Af9I~
  		{                                                          //~1Af9I~
            if (deleted_partner==null)                             //~1Af9I~
            {                                                      //~1Af9I~
            	AjagoView.showToast(R.string.NoDeletedEntry);      //~1Af9I~
            }                                                      //~1Af9I~
            else                                                   //~1Af9I~
            {                                                      //~1Af9I~
                PartnerList.insert(deleted_partner,deleted_partner_prev);//~1Af9I~
	  			updateplist();                                     //~1Af9I~
				setSelection(PL,PartnerList,deleted_partner);      //~1Af9I~
                deleted_partner=null;                              //~1Af9I~
            }                                                      //~1Af9I~
  		}                                                          //~1Af9I~
  		else if ("ListUpPartner".equals(o) && PL.getSelectedItem()!=null)//~1Af9I~
  		{	ListElement lc=PartnerList.first();                    //~1Af9I~
  			Partner co;                                            //~1Af9I~
  			while (lc!=null)                                       //~1Af9I~
  			{	co=(Partner)lc.content();                          //~1Af9I~
  				if (co.Name.equals(PL.getSelectedItem()))          //~1Af9I~
  				{                                                  //~1Af9I~
					PartnerList.moveUp(lc);                        //~1Af9I~
                    break;                                         //~1Af9I~
  				}                                                  //~1Af9I~
  				lc=lc.next();                                      //~1Af9I~
  			}                                                      //~1Af9I~
	  		updateplist();                                         //~1Af9I~
			setSelection(PL,PartnerList,lc);                       //~1Af9I~
  		}                                                          //~1Af9I~
  		else if ("ListDownPartner".equals(o) && PL.getSelectedItem()!=null)//~1Af9R~
  		{	ListElement lc=PartnerList.first();                    //~1Af9I~
  			Partner co;                                            //~1Af9I~
  			while (lc!=null)                                       //~1Af9I~
  			{	co=(Partner)lc.content();                          //~1Af9I~
  				if (co.Name.equals(PL.getSelectedItem()))          //~1Af9I~
  				{                                                  //~1Af9I~
					PartnerList.moveDown(lc);                      //~1Af9I~
                    break;                                         //~1Af9I~
  				}                                                  //~1Af9I~
  				lc=lc.next();                                      //~1Af9I~
  			}                                                      //~1Af9I~
	  		updateplist();                                         //~1Af9I~
			setSelection(PL,PartnerList,lc);                       //~1Af9I~
  		}                                                          //~1Af9I~
  		else if (Global.resourceString("Open_").equals(o))
  		{	if (OPF==null) OPF=new OpenPartnerFrame(this);
//			else OPF.refresh();                                    //~3507R~
//			else OPF.refresh_query();                              //~3507I~//~1A8hR~
  			else                                                   //~1A8hI~
            {                                                      //~1A8hI~
            	Window.showFrame(OPF);	//put on top panel         //~1A8hI~
  				OPF.refresh_query();                               //~1A8hI~
            }                                                      //~1A8hI~
  		}
  		else if (AG.resource.getString(R.string.WiFiDirectButton).equals(o))//~1A84I~
  		{                                                          //~1A84I~
  			startWiFiDirect();                                     //~1A84I~
  		}                                                          //~1A84I~
  		else if (AG.resource.getString(R.string.WiFiNFCButton).equals(o))//~1A84I~
  		{                                                          //~1A84I~
//			startWiFiNFC();                                        //~1A84I~//~1Ab1R~
//        if (AG.swNFCBT)                                             //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ad3I~
    		selectNFCHandover();                                   //~1Ab1I~//~1Ab6R~//~1Ab7R~
//        else                                                     //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ad3I~
//  		prepareNFC();                                          //~1A6sI~//~1Ad3I~
  		}                                                          //~1A84I~
  		else if (AG.resource.getString(R.string.BTNFCButton).equals(o))//~1Ad3R~
  		{                                                          //~1Ad3R~
    		selectNFCHandoverBT();                                 //~1Ad3R~
  		}                                                          //~1Ad3R~
  		else if (AG.resource.getString(R.string.BluetoothButton).equals(o))//~1Ae5I~
  		{                                                          //~1Ae5I~
    		startRemoteGame();                                     //~1Ae5I~
  		}                                                          //~1Ae5I~
  		else if (AG.resource.getString(R.string.Help).equals(o))   //~1A8tI~
  		{                                                          //~1A8tI~
  			topHelp();                                             //~1A8tI~
  		}                                                          //~1A8tI~
  		else if (AG.resource.getString(R.string.Close).equals(o))  //~1Ag1I~
  		{                                                          //~1Ag1I~
  			topClose();                                            //~1Ag1I~
  		}                                                          //~1Ag1I~
        else                                                       //~1Ag1I~
  		{                                                          //~1Ag1I~
  			topMenuButtons(o);                                     //~1Ag1I~
  		}                                                          //~1Ag1I~
  	}
  	public void itemAction (String o, boolean flag) {}

	/** search a specific connection by name */
	public Connection find (String s)
	{	ListElement lc=ConnectionList.first();
		Connection c;
		while (lc!=null)
		{	c=(Connection)lc.content();
			if (c.Name.equals(s)) return c;
			lc=lc.next();
		}
		return null;
	}
	
	/** find a specific partner server by name */
	public Partner pfind (String s)
	{	ListElement lc=PartnerList.first();
		Partner c;
		while (lc!=null)
		{	c=(Partner)lc.content();
			if (c.Name.equals(s)) return c;
			lc=lc.next();
		}
		return null;
	}
	
	/** The frame containing the Go applet */
	public static MainFrame F=null;
	
	/**
	This is the main method for the JagoClient application. It
	is normally envoced via "java Go".
	<P>
	It opens a frame of class MainFrame, initializes the parameter
	list and starts a dump on request in the command line. If
	a game name is entered in the command line, it will also
	open a GoFrame displaying the game.
	<P>
	An important point is that the application will write
	its setup to go.cfg.
	<P>
	Available arguments are
	<ul>
	<li> -h sets the home directory for the application
	<li> -d starts a session dump to dump.dat
	<li> -t dumps tp the termimal too
	<li> another argument opens a local SGF file immediately
	</ul>
	
	*/
//  public static void main (String args[])                        //~1524R~//~1AfaR~
    public static void GoMain (String args[])                      //~1AfaI~
	{	// scan arguments
		int na=0;
		boolean homefound=false;
		String localgame="";
		while (args.length>na)
		{	if (args.length-na>=2 && args[na].startsWith("-l"))
			{	Locale.setDefault(new Locale(args[na+1],"")); na+=2;
			}
			else if (args.length-na>=2 && args[na].startsWith("-h"))
			{	Global.home(args[na+1]); na+=2;
				homefound=true;
			}
			else if (args[na].startsWith("-d"))
			{	Dump.open("dump.dat"); na++;
			}
			else if (args[na].startsWith("-t"))
			{	Dump.terminal(true); na++;
			}
			else
			{	localgame=args[na]; na++;
			}
		}
		// initialize some Global things
		Global.setApplet(false);
		if (!homefound)	Global.home(System.getProperty("user.home"));
		Global.version51();
		Global.readparameter(".go.cfg"); // read setup
		Global.createfonts();
		CloseFrame CF;
		Global.frame(CF=new CloseFrame("Global")); // a default invisible frame
		CF.seticon("ijago.gif");
		// create a MainFrame
		F=new MainFrame(Global.resourceString("_Jago_"));
		// add a go applet to it and initialize it
		F.add("Center",go=new Go());                               //~1524R~
		go.init();
		go.start();
		F.setVisible(true);
		Global.loadmessagefilter(); // load message filters, if available
		JagoSound.play("high","",true); // play a welcome sound
		if (!localgame.equals("")) openlocal(localgame);
			// open a SGF file, if there was a parameter
		else 
			if (Global.getParameter("beauty",false))
				// start a board painter with the last known
				// board dimensions
			{	Board.woodpaint=new WoodPaint(F);
			}
	}
	/** update the list of servers */
	public void updatelist ()
	{	if (Global.isApplet()) return;
		try
		{	PrintWriter out=new PrintWriter(
				new FileOutputStream(Global.home()+".server.cfg"));
			ListElement lc=ConnectionList.first();
			L.removeAll();
			while (lc!=null)
			{	Connection c=(Connection)lc.content();
				L.add(c.Name);
				c.write(out);
				lc=lc.next();
			}
			out.close();
		}
		catch (Exception e)
		{	if (F!=null) new Message(F,Global.resourceString("Could_not_write_to_server_cfg"));
		}
	}
//set selection for undelete,listup,listdown **********************************//~1Af9I~
	private void setSelection(List Plist,ListClass Plc,ListElement Pentry)//~1Af9R~
	{                                                              //~1Af9I~
    	if (Pentry==null)                                          //~1Af9I~
        	return;                                                //~1Af9I~
		ListElement lc=Plc.first();                                //~1Af9R~
        int pos=0;                                                 //~1Af9I~
		while (lc!=null)                                           //~1Af9I~
		{                                                          //~1Af9I~
        	if (lc==Pentry)                                        //~1Af9I~
            {                                                      //~1Af9I~
				Plist.select(pos);                                 //~1Af9I~
                break;                                             //~1Af9I~
			}                                                      //~1Af9I~
            pos++;                                                 //~1Af9I~
			lc=lc.next();                                          //~1Af9I~
        }                                                          //~1Af9I~
	}                                                              //~1Af9I~

	/** update the list of partners */
	public void updateplist ()
	{	if (Global.isApplet()) return;
		try
		{	PrintWriter out=new PrintWriter(
				new FileOutputStream(Global.home()+".partner.cfg"));
			ListElement lc=PartnerList.first();
			PL.removeAll();
			while (lc!=null)
			{	Partner c=(Partner)lc.content();
				PL.add(c.Name);
				c.write(out);
				lc=lc.next();
			}
			out.close();
		}
		catch (Exception e)
		{	if (F!=null) new Message(F,
				Global.resourceString("Could_not_write_to_partner_cfg"));
		}
	}

	/** open a local game window (called from main) */
	static void openlocal (String file)
	{	GoFrame gf=new GoFrame(new Frame(),"Local");
		gf.load(file);
	}
//*****************************************************************************//~1A84I~
    private void startWiFiDirect()                                 //~1A84I~
    {                                                              //~1A84I~
    	if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))          //~1A8gR~
            return;                                                //~1A8gI~
        if (Dump.Y) Dump.println("Go:startWiFiDirect");     //~1A84I~//~1Ab1R~
        new IPConnection();                                         //~1A84R~
  	}                                                              //~1A84I~
    private void startWiFiNFC()                                    //~1A84I~
    {                                                              //~1A84I~
    	if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))               //~1A8gR~
            return;                                                //~1A8gI~
        if (Dump.Y) Dump.println("Go:startWiFiNFC");        //~1A84I~//~1Ab1R~
        DialogNFC.showDialog();                                    //~1A84I~
  	}                                                              //~1A84I~
//*****************************************************************************//~1A8gI~
//*sessiontype:1:LAN,2:WifiDirect,3:BT                             //~1A8gI~
//*****************************************************************************//~1A8gI~
    public static boolean isAliveOtherSession(int Psessiontype,boolean Pduperr)//~1A8gR~
    {                                                              //~1A8gI~
    	int active=AG.activeSessionType;                            //~1A8gI~
        if (active!=0)                                             //~1A8gR~
            if (active!=Psessiontype||Pduperr)                     //~1A8gI~
            {                                                      //~1A8gR~
                String msg=AG.resource.getString(R.string.ErrOtherActiveSession);//~1A8gR~
                new com.Ajagoc.gtp.MessageDialogs().showError(null/*frame*/,msg,""/*optionalmsg*/,false/*critical*/);//~1A8gI~
                return true;                                       //~1A8gR~
            }                                                      //~1A8gR~
        return false;                                              //~1A8gI~
  	}                                                              //~1A8gI~
//*****************************************************************************//~1A8tI~
//*sessiontype:1:LAN,2:WifiDirect,3:BT                             //~1A8tI~
//*****************************************************************************//~1A8tI~
    private void topHelp()                                              //~1A8tI~
    {                                                              //~1A8tI~
//  	new HelpDialog(Global.frame(),"Top");                      //~1Ag1R~
        String help;                                               //~1Ag1I~
        if (AG.mainframeTag==AjagoView.TABINDEX_SERVER)              //~1Ag1I~
	    	help="topserver";                                      //~1Ag1R~
        else                                                       //~1Ag1I~
        if (AG.mainframeTag==AjagoView.TABINDEX_PARTNER)             //~1Ag1I~
	    	help="toppartner";                                     //~1Ag1R~
        else                                                       //~1Ag1I~
        	help="Top";                                            //~1Ag1I~
    	new HelpDialog(Global.frame(),help);                       //~1Ag1I~
  	}                                                              //~1A8tI~
//*****************************************************************************//~1Ab1I~//~1Ab6R~//~1Ab7R~
//*select WiFiDirect or Bluetooth for NFC handover                 //~1Ab1I~//~1Ab6R~//~1Ab7R~
//*****************************************************************************//~1Ab1I~//~1Ab6R~//~1Ab7R~
    private void selectNFCHandover()                               //~1Ab1I~//~1Ab6R~//~1Ab7R~
    {                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
//      DialogNFCSelect.showDialog(this);                          //~1Ab1R~//~1Ab6R~//~1Ab7R~//~1Ad3R~
    	prepareNFCAhsv();                                          //~1Ad3I~
    }                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
    private void selectNFCHandoverBT()                             //~1Ad3I~
    {                                                              //~1Ad3I~
    	prepareNFCBTAhsv();                                        //~1Ad3I~
    }                                                              //~1Ad3I~
    public void selectedNFCHandover(int PhandoverType)           //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~
    {                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
        if (PhandoverType==DialogNFCSelect.NFCTYPE_IP)                                           //~1Ab1I~//~1Ab6R~//~1Ab7R~
            prepareNFC();                                          //~1Ab1I~//~1Ab6R~//~1Ab7R~
        else                                                       //~1Ab1I~//~1Ab6R~//~1Ab7R~
            prepareNFCBT(PhandoverType);                                        //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~
    }                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
    //***********************************************************************//~1A6sI~//~1Ad3I~
    private void prepareNFC()                                      //~1A6sI~//~1Ad3I~
    {                                                              //~1A6sI~//~1Ad3I~
        if ((AG.RemoteStatus & AG.RS_BT)!=0)                       //~1A6sI~//~1Ad3I~
        {                                                          //~1A6sI~//~1Ad3I~
            new Message(/*this*/Global.frame(),R.string.ErrNowBTConnected);          //~1A6sI~//~1Ad3I~
            return;                                                //~1A6sI~//~1Ad3I~
        }                                                          //~1A6sI~//~1Ad3I~
    	if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))         //~1A8gR~//~1Ad3I~
            return;                                                //~1A8gR~//~1Ad3I~
    	DialogNFC.showDialog();                                           //~1A6sR~//~1Ad3I~
    }                                                              //~1A6sI~//~1Ad3I~
    //***********************************************************************//~1Ab1I~//~1Ab6R~//~1Ab7R~
    private void prepareNFCBT(int PhandoverType)                                    //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~
    {                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
        if ((AG.RemoteStatus & AG.RS_IP)!=0)                       //~1Ab1I~//~1Ab6R~//~1Ab7R~
        {                                                          //~1Ab1I~//~1Ab6R~//~1Ab7R~
            new Message(/*this*/Global.frame(),R.string.ErrNowIPConnected);          //~1Ab1I~//~1Ab6R~//~1Ab7R~
            return;                                                //~1Ab1I~//~1Ab6R~//~1Ab7R~
        }                                                          //~1Ab1I~//~1Ab6R~//~1Ab7R~
//      if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))         //~1Ab1I~//~1Ab6R~//~1Ab7R~
//          return;                                                //~1Ab1I~//~1Ab6R~//~1Ab7R~
//      DialogNFCBT.showDialog(this,PhandoverType);                                  //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~//~1Ae5R~
        DialogNFCBT.showDialog(F/*MainFrame*/,PhandoverType);      //~1Ae5I~
    }                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
    //***********************************************************************//~1Ad3I~
    private void prepareNFCAhsv()                                  //~1Ad3I~
    {                                                              //~1Ad3I~
        if ((AG.RemoteStatus & AG.RS_BT)!=0)                       //~1Ad3I~
        {                                                          //~1Ad3I~
            new Message(/*this*/Global.frame(),R.string.ErrNowBTConnected);          //~1Ad3I~
            return;                                                //~1Ad3I~
        }                                                          //~1Ad3I~
    	if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))         //~1Ad3I~
            return;                                                //~1Ad3I~
        DialogNFCSelect.showDialogNFCWD(this);                     //~1Ad3I~
    }                                                              //~1Ad3I~
    //***********************************************************************//~1Ad3I~
    private void prepareNFCBTAhsv()                                //~1Ad3I~
    {                                                              //~1Ad3I~
        if ((AG.RemoteStatus & AG.RS_IP)!=0)                       //~1Ad3I~
        {                                                          //~1Ad3I~
            new Message(/*this*/Global.frame(),R.string.ErrNowIPConnected);          //~1Ad3I~
            return;                                                //~1Ad3I~
        }                                                          //~1Ad3I~
        DialogNFCSelect.showDialogNFCBT(this);                     //~1Ad3M~
    }                                                              //~1Ad3I~
    //***********************************************************************//~1Ae5I~
    private void startRemoteGame()                                 //~1Ae5I~
    {                                                              //~1Ae5I~
        if ((AG.RemoteStatus & AG.RS_IP)!=0)                       //~1Ae5I~
        {                                                          //~1Ae5I~
        	new Message(/*this*/Global.frame(),R.string.ErrNowIPConnected);          //~1Ae5I~
        	return;                                                //~1Ae5I~
        }                                                          //~1Ae5I~
		new BluetoothConnection(F/*MainFrame*/);	//open dialog              //~1Ae5I~
    }                                                              //~1Ae5I~
    //***********************************************************************//~1Ag1I~
    private void addTopPanel()                                     //~1Ag1I~
    {                                                              //~1Ag1I~
        AG.ajagov.initMainFrameTab(Frame.MainFrame);    //set AG.currentLayout=maintop//~1Ag1R~
        new ButtonAction(this,R.string.TB_Local,R.id.TB_Local,false/*do not setfont*/);//~1Ag1R~
        new ButtonAction(this,R.string.TB_Robot,R.id.TB_Robot,false);//~1Ag1R~
        new ButtonAction(this,R.string.TB_Server,R.id.TB_Server,false);//~1Ag1R~
        new ButtonAction(this,R.string.TB_Partner,R.id.TB_Partner,false);//~1Ag1R~
        new ButtonAction(this,R.string.TB_Option,R.id.TB_Option,false);//~1Ag1R~
        new ButtonAction(this,R.string.TB_AjagocOption,R.id.TB_AjagocOption,false);//~1Ag1R~
        new ButtonAction(this,R.string.TB_OnlineHelp,R.id.TB_OnlineHelp,false);//~1Ag1R~
    }                                                              //~1Ag1I~
//*****************************************************************************//~1Ag1I~
    private void topClose()                                        //~1Ag1I~
    {                                                              //~1Ag1I~
    	if (Dump.Y) Dump.println("Go:topClose tabid="+AG.mainframeTag);//~1Ag1I~
		AG.ajagoMenu.closeFrame();                                 //~1Ag1R~
  	}                                                              //~1Ag1I~
//*****************************************************************************//~1Ag1I~
//* return false when not processed                                //~1Ag1I~
//*****************************************************************************//~1Ag1I~
    private boolean topMenuButtons(String o)                       //~1Ag1I~
    {                                                              //~1Ag1I~
    	boolean rc=true;                                           //~1Ag1I~
		try                                                        //~1Ag1I~
		{                                                          //~1Ag1I~
            if (AG.resource.getString(R.string.TB_Local).equals(o))//~1Ag1R~
            {                                                      //~1Ag1R~
	          if (!Window.IsThereAnyOpenedGoFrame())               //~1AgaI~
                F.doAction(Global.resourceString("Local_Board"));  //~1Ag1R~
            }                                                      //~1Ag1R~
            else if (AG.resource.getString(R.string.TB_Robot).equals(o))//~1Ag1R~
            {                                                      //~1Ag1R~
	          if (!Window.IsThereAnyOpenedGoFrame())               //~1AgaI~
                AG.ajagoMenu.openContextMenu(F,MainFrame.SUBMENUNAME_ROBOT);//~1Ag1R~
            }                                                      //~1Ag1R~
            else if (AG.resource.getString(R.string.TB_Server).equals(o))//~1Ag1R~
            {                                                      //~1Ag1R~
	          if (!Window.IsThereAnyOpenedGoFrame())               //~1AgaI~
                AG.ajagov.setCurrentTab(AjagoView.TABINDEX_SERVER);          //~1Ag1R~
            }                                                      //~1Ag1R~
            else if (AG.resource.getString(R.string.TB_Partner).equals(o))//~1Ag1R~
            {                                                      //~1Ag1R~
	          if (!Window.IsThereAnyOpenedGoFrame())               //~1AgaI~
                AG.ajagov.setCurrentTab(AjagoView.TABINDEX_PARTNER);//~1Ag1R~
            }                                                      //~1Ag1R~
            else if (AG.resource.getString(R.string.TB_Option).equals(o))//~1Ag1R~
            {                                                      //~1Ag1R~
                AG.ajagoMenu.openContextMenu(F,MainFrame.SUBMENUNAME_OPTION);//~1Ag1R~
            }                                                      //~1Ag1R~
            else if (AG.resource.getString(R.string.TB_AjagocOption).equals(o))//~1Ag1R~
            {                                                      //~1Ag1R~
                new AjagoOptions();                                //~1Ag1R~
            }                                                      //~1Ag1R~
            else if (AG.resource.getString(R.string.TB_OnlineHelp).equals(o))//~1Ag1I~
            {                                                      //~1Ag1I~
                AG.ajagoMenu.openContextMenu(F,MainFrame.SUBMENUNAME_ONLINEHELP);//~1Ag1I~
            }                                                      //~1Ag1I~
            else                                                   //~1Ag1R~
                rc=false;                                          //~1Ag1R~
                                                                  //~1Ag1I~
        }                                                          //~1Ag1I~
		catch (Exception e)                                        //~1Ag1I~
		{                                                          //~1Ag1I~
            Dump.println(e,"topMenuButtons");                     //~1Ag1I~
		}                                                          //~1Ag1I~
    	return rc;                                                 //~1Ag1I~
    }                                                              //~1Ag1I~
}                                                                  //~1A84R~
