//*CID://+v1EiR~:                             update#=    5;       //~v1EiR~
//*************************************************************************//~v107I~
//v1Ei 2014/12/12 Partner:start Searver:NetworkOnMainThreadException when Datagram send//~v1EiI~
//                additional to v1E1 was required                  //~v1EiI~
//1B15 130504 query open server also when "Servert start" was not done//~1B15I~
//1B12 130501 WrongThreadException:PartnerFrame from ServerThread  //~1B12I~
//            (target Server.java send 127.0.0.1 as my ipaddr.     //~1B12I~
//            So requester's accept respond to socket() by itself. //~1B12I~
//            PartnerFrame is duplicatedly created)                //~1B12I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//*************************************************************************//~v107I~
package jagoclient.partner;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.datagram.DatagramMessage;
import jagoclient.partner.partner.Partner;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.Ajagoc.AjagoUtils;

import rene.util.list.ListElement;

/**
This is the server thread for partner connections. If anyone connects
to the server, a new PartnerFrame will open to handle the connection.
If the server starts, it will open a new PartnerServerThread, which
checks for datagrams that announce open partners.
*/

public class Server extends Thread
{	int Port;
	boolean Public;
    static boolean publicServer;                                    //~1B15I~
	static public PartnerServerThread PST=null;
	ServerSocket SS;
	/**
	@param p the server port
	@param publ server is public or not
	*/
	public Server (){}  //for extended(Ajagoc/jagoclient/partner/Server//~v107I~
	public Server (int p, boolean publ)
	{	Port=p; Public=publ;
    	publicServer=publ;                                          //~1B15I~
		start();
	}
	public void run ()
	{	if (PST==null) 
			PST=new PartnerServerThread(Global.getParameter("serverport",6970)+2);
		try { sleep(1000); } catch (Exception e) {}
		try
		{	SS=new ServerSocket(Port);
			while (true)
			{	Socket S=SS.accept();
				if (Global.Busy) // user set the busy checkbox
				{	PrintWriter o=new PrintWriter(
						new DataOutputStream(S.getOutputStream()),true);
					o.println("@@busy");
					S.close();
					continue;
				}
				PartnerFrame cf=
					new PartnerFrame(Global.resourceString("Server"),true);
				Global.setwindow(cf,"partner",500,400);
				cf.show();
				cf.open(S);
			}
		}
		catch (Exception e)
		{	Dump.println(e,"Server Error");//@@@@ add e            //~1506R~
		}
	}
	
	/**
	This is called, when the server is opened. It will announce
	the opening to known servers by a datagram.
	*/
    //****************************                                 //~v1EiI~
	public void open ()
    {                                                              //~v1EiI~
		subthreadNetwork sdt=new subthreadNetwork(this,true/*open*/);//+v1EiR~
        sdt.start();                                               //~v1EiI~
    }                                                              //~v1EiI~
    //****************************                                 //~v1EiI~
    public void subthreadOpen()                                    //+v1EiR~
	{	if (Public)
		{	ListElement pe=Global.PartnerList.first();
			while (pe!=null)
			{	Partner p=(Partner)pe.content();
				if (p.State>0)
				{	DatagramMessage d=new DatagramMessage();
					d.add("open");
					d.add(Global.getParameter("yourname","Unknown"));
					try
//  				{	String s=InetAddress.getLocalHost().toString();//~1B12R~
    				{                                              //~1B12I~
    					String s=AjagoUtils.getIPAddress(false/*no ipv6 append*/);//~1B12I~
						d.add(s.substring(s.lastIndexOf('/')+1));
					}
					catch (Exception e) { d.add("Unknown Host"); }
					d.add(""+Global.getParameter("serverport",6970));
					d.add(""+p.State);
					d.send(p.Server,p.Port+2);
				}
				pe=pe.next();
			}
		}
		Global.Busy=false;
	}
	/**
	This is called, when the server is closed. It will announce
	the closing to known servers by a datagram.
	*/
    //****************************                                 //~v1EiI~
	public void close ()
    {                                                              //~v1EiI~
		subthreadNetwork sdt=new subthreadNetwork(this,false/*close*/);//+v1EiR~
        sdt.start();                                               //~v1EiI~
    }                                                              //~v1EiI~
    //****************************                                 //~v1EiI~
    public void subthreadClose()                                   //+v1EiR~
	{	if (!Public) return;
		ListElement pe=Global.PartnerList.first();
		DatagramMessage d=new DatagramMessage();
		d.add("close");
		d.add(Global.getParameter("yourname","Unknown"));
		try
//		{	String s=InetAddress.getLocalHost().toString();        //~1B12R~
  		{                                                          //~1B12I~
    		String s=AjagoUtils.getIPAddress(false/*no ipv6 append*/);//~1B12I~
			d.add(s.substring(s.lastIndexOf('/')+1));
		}
		catch (Exception e) { d.add("Unknown Host"); }
		while (pe!=null)
		{	Partner p=(Partner)pe.content();
			if (p.State>0) d.send(p.Server,p.Port+2);
			pe=pe.next();
		}
		Global.Busy=true;
	}
    //********************************************                 //~v1EiI~
	class subthreadNetwork extends Thread                                  //~v1EiI~
	{                                                              //~v1EiI~
        boolean swOpen;                                            //~v1EiI~
        Server sv;                                                 //+v1EiI~
		public subthreadNetwork(Server Pserver,boolean Pswopen)    //+v1EiR~
		{                                                          //~v1EiI~
            sv=Pserver;                                            //+v1EiR~
            swOpen=Pswopen;                                        //+v1EiI~
		}                                                          //~v1EiI~
		public void run ()                                         //~v1EiI~
		{                                                          //~v1EiI~
          	try                                                    //~v1EiI~
          	{                                                      //~v1EiI~
                if (swOpen)                                        //~v1EiI~
                    sv.subthreadOpen();                            //+v1EiR~
                else                                               //~v1EiI~
                    sv.subthreadClose();                           //+v1EiR~
          	}                                                      //~v1EiI~
          	catch(Exception e)                                     //~v1EiI~
          	{                                                      //~v1EiI~
        		Dump.println(e,"Server:subthreadNetwork");         //~v1EiI~
          	}                                                      //~v1EiI~
		}                                                          //~v1EiI~
	}                                                              //~v1EiI~
}
