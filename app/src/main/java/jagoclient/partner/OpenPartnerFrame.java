//*CID://+1A8fR~:                             update#=   29;       //+1A8fR~
//*************************************************************************//~v106I~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8ek2015/03/01 issue warning toast no line selected on OpenPartner list//~1A8eI~
//1B1c 130507 query need  to refresh 2nd time                      //~1b1cI~
//1B15 130504 query open server also when "Servert start" was not done//~1b15I~
//1B13 130502 protect connect to my self from openpartnerlist      //~1b13I~
//v106 1063:121124 menu to display ip address for pertner connection//~v106I~
//*************************************************************************//~v106I~
package jagoclient.partner;

//import android.view.Menu;

import android.widget.TextView;                                    //~v106R~
import com.Ajagoc.AG;                                              //~v106R~
import com.Ajagoc.AjagoUtils;                                      //~v106R~
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;                                               //~v106I~
import com.Ajagoc.URunnable;
import com.Ajagoc.URunnableI;
import com.Ajagoc.awt.BorderLayout;
import com.Ajagoc.awt.MenuBar;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.Menu;
import com.Ajagoc.awt.List;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.Go;
import jagoclient.StopThread;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseFrame;
import jagoclient.gui.MenuItemAction;
import jagoclient.gui.MyLabel;
import jagoclient.gui.MyMenu;
import jagoclient.gui.MyPanel;
import jagoclient.partner.partner.Partner;

//import java.awt.BorderLayout;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.Panel;

import rene.util.list.ListClass;
import rene.util.list.ListElement;

class OpenPartnerFrameUpdate extends StopThread
    implements URunnableI                                          //~1b15I~
{	OpenPartnerFrame OPF;
	private int updatectr;                                         //~1b15I~
	public OpenPartnerFrameUpdate (OpenPartnerFrame f)
	{	OPF=f;
    	updatectr=PartnerServerThread.updatectrOPL;                //~1b15I~
		start();
	}
	public void run ()
	{                                                              //~1b15R~
    	int intvl=1;              //by sec                         //~1b15R~
//  	while (stopped())                                          //~1b15I~
//  	{	try { sleep(30000); } catch (Exception e) {}           //~1b15R~
    	while (!stopped())                                         //~1b15I~
    	{                                                          //~1b15I~
    		try { sleep(intvl*1000); } catch (Exception e) {}      //~1b15I~
	    	if (stopped())                                         //~1b15I~
            	break;                                             //~1b15I~
//			OPF.refresh();                                         //~1b15R~
			if (updatectr!=PartnerServerThread.updatectrOPL)       //~1b15I~
            {                                                      //~1b15I~
                if (Dump.Y) Dump.println("OpenPartnerFrameUpdate upctr old="+updatectr+",new="+PartnerServerThread.updatectrOPL);//~1b15I~
				URunnable.setRunFunc(this,0/*sleep*/,null/*parm*/,0/*parmint*/);//~1b15R~
				updatectr=PartnerServerThread.updatectrOPL;        //~1b15I~
            }                                                      //~1b15I~
		}
	}
//*************************                                        //~@@@@I~//~v110M~//~1b15I~
//*callback from Runnable *                                        //~@@@@I~//~v110M~//~1b15I~
//*************************                                        //~@@@@I~//~v110M~//~1b15I~
    public void runFunc(Object Pparmobj,int Pphase)                //~@@@@I~//~v110M~//~1b15I~
    {                                                              //~@@@@I~//~v110M~//~1b15I~
	    if (!stopped())                                            //~1b15I~
			OPF.refresh();    //on mainthread                      //~1b15R~
    }                                                              //~1b15I~
}

/**
This is a frame, which displays a list of all open partner servers.
It contains buttons to connect to one of one of them and
to refresh the list.
*/

public class OpenPartnerFrame extends CloseFrame
{	Go G;
	/*@@@@java.awt.*/List L;
	OpenPartnerFrameUpdate OPFU;
	public OpenPartnerFrame (Go go)
	{	super(Global.resourceString("Open_Partners"));
		G=go;
		MenuBar mb=new MenuBar();
		setMenuBar(mb);
		Menu m=new MyMenu(Global.resourceString("Options"));
		m.add(new MenuItemAction(this,Global.resourceString("Close")));
		mb.add(m);
		setLayout(new BorderLayout());
		L=new /*@@@@ java.awt.*/List();
		L.setFont(Global.SansSerif);
		refresh();
		add("Center",L);
		Panel bp=new MyPanel();
		bp.add(new ButtonAction(this,Global.resourceString("Connect")));
		bp.add(new ButtonAction(this,Global.resourceString("Refresh")));
		bp.add(new MyLabel(" "));
		bp.add(new ButtonAction(this,Global.resourceString("Close")));
		add("South",bp);
		Global.setwindow(this,"openpartner",300,200);
		seticon("ijago.gif");
        displayIPAddress();                                        //~v106I~
		show();
		OPFU=new OpenPartnerFrameUpdate(this);
		if (Server.PST==null)                                      //~1b15M~
        {                                                          //~1b15M~
            Server.publicServer=false;	//no response to query     //~1b15M~
			Server.PST=new PartnerServerThread(Global.getParameter("serverport",6970)+2);//~1b15M~
        }                                                          //~1b15M~
//      Server.PST.query(); //refresh by OpenPartnerFrameUpdate    //~1b15M~//~1b1cR~
        refresh_query(); //refresh by OpenPartnerFrameUpdate       //~1b1cI~
	}
	public void doAction (String o)
	{	if (o.equals(Global.resourceString("Refresh")))
//  	{	refresh();                                             //~1b15R~
    	{                                                          //~1b15I~
//  		Server.PST.query();	//refresh by OpenPartnerFrameUpdate//~1b15I~//~1b1cR~
    		refresh_query();	//refresh by OpenPartnerFrameUpdate//~1b1cI~
	        displayIPAddress();                                    //~1A8fI~
		}
		else if (o.equals(Global.resourceString("Close")))
		{	doclose();
		}
		else if (o.equals(Global.resourceString("Connect")))
		{	connect();
		}
		else super.doAction(o);
	}
//****************************************                         //~1b1cI~
    public void refresh_query()                                    //~1b1cI~
    {                                                              //~1b1cI~
		Server.PST.query();	//refresh by OpenPartnerFrameUpdate    //~1b1cI~
    }                                                              //~1b1cI~
//****************************************                         //~1b1cI~
    public void refresh ()
	{	ListClass PL=Global.OpenPartnerList;
		L.removeAll();
        if (Dump.Y) Dump.println("refresh Openpartnerlist");//~1b15I~
		if (PL==null) return;
		ListElement le=PL.first();
		while (le!=null)
		{	L.add(((Partner)le.content()).Name);
			le=le.next();
		}
	}
	public void doclose ()
	{	G.OPF=null;
		OPFU.stopit();
		Global.notewindow(this,"openpartner");		
		super.doclose();
	}
	public void connect ()
	{	ListElement le=Global.OpenPartnerList.first();
		String s=L.getSelectedItem();
        if (s.equals(""))                                          //~1A8eI~
        {                                                          //~1A8eI~
			AjagoView.showToast(R.string.ErrSelectItem);           //~1A8eI~
            return;                                                //~1A8eI~
        }                                                          //~1A8eI~
    	if (Go.isAliveOtherSession(AG.AST_IP,true/*dupok*/))          //~1A8gI~
            return;                                                //~1A8gI~
        String myipaddr=AjagoUtils.getIPAddress();                 //~1b13I~
		while (le!=null)
		{	Partner p=(Partner)le.content();
			if (p.Name.equals(s))
//			{	PartnerFrame cf=                                   //~1b15R~
  			{                                                      //~1b15I~
              if (p.Server.equals(myipaddr))                          //~1b13I~//~1b15I~
                AjagoView.showToastLong(R.string.ErrPartnerIsYourself);//~1b13I~//~1b15I~
              else                                                     //~1b13I~//~1b15I~
              {                                                        //~1b13I~//~1b15I~
  				PartnerFrame cf=                                   //~1b15I~
					new PartnerFrame(Global.resourceString("Connection_to_")+p.Name,false);
				Global.setwindow(cf,"partner",500,400);
				new ConnectPartner(p,cf);
              }                                                        //~1b13I~//~1b15I~
				return;
			}
			le=le.next();
		}
	}
    private void displayIPAddress()                                //~v106I~
    {                                                              //~v106I~
        String info=null;                                          //~1A8fI~
    	TextView v=(TextView)(AG.findViewById(R.id.IPAddr));       //~v106R~
//      String ipa=AjagoUtils.getIPAddress();                      //~v106I~//~1A8fR~
//      v.setText("(Your IP-Addr:"+ipa+")");                       //~v106R~//~1A8fR~
      	if (AG.RemoteInetAddressLAN!=null && AG.LocalInetAddressLAN!=null)//~1A8fI~
      	{                                                          //~1A8fI~
      		info="Active Session : "+AG.RemoteInetAddressLAN+"<--"+AG.LocalInetAddressLAN;//~1A8fI~
      	}                                                          //~1A8fI~
      	if (AG.RemoteInetAddress!=null && AG.LocalInetAddress!=null)//~1A8fI~
      	{                                                          //~1A8fI~
	      	String 	info2="   WiFi Direct : "+AG.RemoteInetAddress+"<--"+AG.LocalInetAddress;//~1A8fI~
        	if (info==null)                                        //~1A8fI~
            	info=info2;                                        //~1A8fI~
            else                                                   //~1A8fI~
      			info+="\n"+info2;                                   //~1A8fI~
      	}                                                          //~1A8fI~
        if (info==null)                                            //~1A8fI~
        {                                                          //~1A8fI~
			String ipa=AjagoUtils.getIPAddressAll();               //~1A8fI~
			info="No Active Session (Your IP-Addr : "+ipa+")";     //+1A8fR~
		}                                                          //~1A8fI~
        if (Dump.Y) Dump.println("OpenPartnerFrame:displayIPAddress settext="+info);//~1A8fI~
		v.setText(info);                                           //~1A8fI~
    }                                                              //~v106I~
}
