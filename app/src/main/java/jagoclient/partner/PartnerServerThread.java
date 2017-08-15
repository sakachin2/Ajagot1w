//*CID://+v1EqR~:                             update#=   39;
//*************************************************************************//~1B12I~
//v1Eq 2014/12/15 display partner list name on open partnerlsit    //~v1EqI~
//v1E1 2014/12/02 NetworkOnMAinThreadException:from android3 dose not allow network process on main-thread//~v1E1I~
//1B1d 130507 do not list myself on opended-partner if not public  //~1B1dI~
//1B19 130505 for 1B15;xp server dose not respond to query; use open with private(requester dose not spread open)//~1B19I~
//1B15 130504 query open server also when "Servert start" was not done//~1B15I~
//1B12 130501 WrongThreadException:PartnerFrame from ServerThread  //~1B12I~
//            (target Server.java send 127.0.0.1 as my ipaddr.     //~1B12I~
//            So requester's accept respond to socket() by itself. //~1B12I~
//            PartnerFrame is duplicatedly created)                //~1B12I~
//*************************************************************************//~1B12I~
package jagoclient.partner;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.datagram.DatagramMessage;
import jagoclient.partner.partner.Partner;

//import java.net.InetAddress;
import java.net.InetAddress;
import java.util.Vector;

import com.Ajagoc.AjagoUtils;

import rene.util.list.ListClass;
import rene.util.list.ListElement;

/**
The PartnerServerThread handles datagrams from other servers. It
will relay the such messages to other known servers, unless they
are private.
*/

class PartnerServerThread extends Thread
{	int Port;
    private static final String SEND_QUERY="send_query";           //~v1E1I~
	public static int updatectrOPL;                                //~1B15R~
	public PartnerServerThread (int port)
	{	Port=port; Global.OpenPartnerList=new ListClass(); start();
        updatectrOPL++;                                            //~1B15I~
	}
	
	public void run ()
	{	DatagramMessage M=new DatagramMessage();
		while (true)
		{	// wait for a datagram
			if (Dump.Y) Dump.println("*** Datagram receive port="+Port);//~1B1dI~
			Vector v=M.receive(Port);
			// got one
			if (Dump.Y) Dump.println("*** Datagram received ***"); //~1506R~
			int i;
			for (i=0; i<v.size(); i++)
				if (Dump.Y) Dump.println((String)v.elementAt(i));  //~1506R~
			if (Dump.Y) Dump.println("*************************");	//~1506R~
          try                                                      //~1B15I~
          {                                                        //~1B15I~
			interpret(v);
          }                                                        //~1B15I~
          catch(Exception e)                                        //~1B15I~
          {                                                        //~1B15I~
        	Dump.println(e,"PartnerServerThread:run interpret");   //~1B15I~
          }                                                        //~1B15I~
		}
	}
	
	void interpret (Vector v)
	{	if (v.size()<1) return; // empty datagram
		String arg=((String)v.elementAt(0));
		if (arg.equals("open")) 
		// a server opened, send him an openinfo datagram
		{// try                                                    //~1B15R~
			{	open(
					(String)v.elementAt(1),
					(String)v.elementAt(2),
					Integer.parseInt((String)v.elementAt(3)),
					Integer.parseInt((String)v.elementAt(4)));
                if (Dump.Y) Dump.println("PartnerServerThread interpret Busy="+Global.Busy);//~1B1dI~
          	  if (Global.Busy) //if opened send openinfo of myself //~1B1dR~
                closeinfo(                                         //~1B1dR~
                    (String)v.elementAt(2),                        //~1B1dR~
                    Integer.parseInt((String)v.elementAt(3)));     //~1B1dR~
              else                                                 //~1B1dI~
				openinfo(
					(String)v.elementAt(2),
					Integer.parseInt((String)v.elementAt(3)));
			}
		//  catch (Exception e) {}                                 //~1B15R~
		}
		else if (arg.equals("spreadopen"))
		// a server notifies about opening of another server
		{// try                                                    //~1B15R~
			{	open(
					(String)v.elementAt(1),
					(String)v.elementAt(2),
					Integer.parseInt((String)v.elementAt(3)),
					Integer.parseInt((String)v.elementAt(4)));
			}
		//  catch (Exception e) {}                                 //~1B15R~
		}
		else if (arg.equals("openinfo"))
		// a server sent me his list of open servers
		{	openinfo(v);
		}
		else if (arg.equals("close") ||
			arg.equals("spreadclose"))
		// a server closed or notifies about the closing of another server
		{// try                                                    //~1B15R~
			{	close((String)v.elementAt(1),(String)v.elementAt(2));
			}
		//  catch (Exception e) {}                                 //~1B15R~
		}
//        else if (arg.equals("query"))                              //~1B15I~//~1B19R~
//        {                                                          //~1B15I~//~1B19R~
//            query_received(v);                                     //~1B15R~//~1B19R~
//        }                                                          //~1B15I~//~1B19R~
//        else if (arg.equals("query_reply"))                        //~1B15R~//~1B19R~
//        {                                                          //~1B15R~//~1B19R~
//            query_reply_received(v);                               //~1B15R~//~1B19R~
//        }                                                          //~1B15R~//~1B19R~
        else if (arg.equals(SEND_QUERY))                           //~v1E1I~
        {                                                          //~v1E1I~
            if (Dump.Y) Dump.println("PartnerServerThread received send_query");//~v1E1I~
            sendQuery();                                           //~v1E1I~
        }                                                          //~v1E1I~
	}
	
	/**
	open is called, when another server opened and this message
	is to be spread to all other servers. If the server is private,
	the message will not be spread. If the open message is not
	public, it will be spread as private. Circularity is prevented
	by spreading only those openings, which are not already known
	to be opened.
	*/
	void open (String name, String address, int port, int state)
	{	ListElement e=find(name,address);
        if (Dump.Y) Dump.println("PartnerServerThread:open name="+name+",addr="+address+",port="+port+",state="+state);//~v1E1I~
		if (e!=null) return; // we got that already
		// append to my list of open servers
		Global.OpenPartnerList.append(new ListElement(
//  		new Partner(name,address,port,state)));                //~v1EqR~
    		new Partner(getNameOfPartnerList(name,address),address,port,state)));//~v1EqI~
        updatectrOPL++;                                            //~1B15I~
		// return, if the server is private
  		if (state<Partner.PRIVATE) return;
		// spread to all other open servers
		ListElement pe=Global.PartnerList.first();
		DatagramMessage d=new DatagramMessage();
		d.add("spreadopen");
		d.add(name);
		d.add(address);
		d.add(""+port);
		d.add(""+(state<Partner.PUBLIC?Partner.PRIVATE:Partner.PUBLIC));
		while (pe!=null)
		{	Partner p=(Partner)pe.content();
			if (!p.Name.equals(name)) d.send(p.Server,p.Port+2);
			pe=pe.next();
		}
	}
	
	/**
	close is called, when a datagram is received about a closing of
	a server. this is spread to all known partner servers.
	*/
	void close (String name, String address)
	{	// find the server in my list of open servers
		ListElement e=find(name,address);
		if (e!=null) // only, if the server was open before
		{	// remove the server from the list
			Global.OpenPartnerList.remove(e);
    	    updatectrOPL++;                                        //~1B15I~
			// spread the closing to all partners
			DatagramMessage d=new DatagramMessage();
			ListElement pe=Global.PartnerList.first();
			d.add("spreadclose");
			d.add(name);
			d.add(address);
			while (pe!=null)
			{	Partner p=(Partner)pe.content();
				if (!p.Name.equals(name)) d.send(p.Server,p.Port+2);
				pe=pe.next();
			}
		}
	}

	/**
	Notify all other servers about all known open servers. This is
	sent in response to an open message from this server so that
	the server is up to date about open servers.
	*/	
	void openinfo (String address, int port)
	{	ListElement e=Global.OpenPartnerList.first();
		DatagramMessage d;
		int count=0;
		d=new DatagramMessage();
		d.add("openinfo");
		d.add(Global.getParameter("yourname","Unknown"));
        if (Dump.Y) Dump.println("PartnerServerThread:openinfo name="+Global.getParameter("yourname","Unknown"));//~v1E1I~
		try
//  	{	String s=InetAddress.getLocalHost().toString();        //~1B12R~
    	{                                                          //~1B12R~
    		String s=AjagoUtils.getIPAddress(false/*no ipv6 append*/);//~1B12R~
			d.add(s.substring(s.lastIndexOf('/')+1));
	        if (Dump.Y) Dump.println("PartnerServerThread:openinfo name="+Global.getParameter("yourname","Unknown")+",addr="+s.substring(s.lastIndexOf('/')+1));//~v1E1I~
		}
		catch (Exception ex) { d.add("Unknown Host"); }
		d.add(""+Global.getParameter("serverport",6970));
		d.add(""+Partner.PRIVATE);
		count++;
		while (e!=null)
		{	if (count==0)
			{	d=new DatagramMessage();
			}
			Partner p=(Partner)e.content();
			if (p.State>Partner.PRIVATE)
			{	d.add("openinfo");
				d.add(p.Name);
				d.add(p.Server);
				d.add(""+p.Port);
				d.add(""+p.State);
	        	if (Dump.Y) Dump.println("PartnerServerThread:openinfo add name="+p.Name+",server="+p.Server+",port="+p.Port);//~v1E1I~
			}
			count++;
			if (count>=10)
			{	d.send(address,port+2);
				count=0;
			}
			e=e.next();
		}
		if (count>0) d.send(address,port+2);
	}

	/**
	openinfo is called, when an openinfo datagram is received
	from any other server. It will add all the noted servers
	to my open server list.
	*/
	void openinfo (Vector v)
	{	int i=0;
		String name,server;
		int port,state;
	//  try                                                        //~1B15R~
		{	if (!((String)v.elementAt(i)).equals("openinfo")) return;
			i++;
			name=(String)v.elementAt(i); i++;
			server=(String)v.elementAt(i); i++;
			port=Integer.parseInt((String)v.elementAt(i)); i++;
			state=Integer.parseInt((String)v.elementAt(i)); i++;
			ListElement le=find(name,server);
	        if (Dump.Y) Dump.println("PartnerServerThread:openinfo list name="+name+",server="+server+",port="+port);//~v1E1I~
			if (le==null)
			{	Global.OpenPartnerList.append(
//  				new ListElement(new Partner(name,server,port,state)));//~v1EqR~
    				new ListElement(new Partner(getNameOfPartnerList(name,server),server,port,state)));//~v1EqI~
	    	    updatectrOPL++;                                    //~1B15I~
			}
		}
	//  catch (Exception e) {}                                     //~1B15R~
	}

	ListElement find (String name, String address)
//  {	String n=name+address;                                     //~v1EqR~
	{                                                              //~v1EqI~
        String n=getNameOfPartnerList(name,address)+address;       //~v1EqI~
		ListElement e=Global.OpenPartnerList.first();
		while (e!=null)
		{	Partner p=(Partner)e.content();
			if ((p.Name+p.Server).equals(n)) return e;
			e=e.next();
		}
		return null;
	}
//***********************************************************      //~1B15I~
//*request to subthread                                            //~v1E1I~
//***********************************************************      //~v1E1I~
	public void query ()                                  //~1B15I~
    {                                                              //~v1E1I~
		SendDatagramThread sdt=new SendDatagramThread();           //~v1E1I~
        sdt.start();                                               //~v1E1I~
    }                                                              //~v1E1I~
//***********************************************************      //~v1E1I~
    private void sendQuery()                                      //~v1E1I~
	{                                                              //~1B15I~
		String name=Global.getParameter("yourname","Unknown");    //~1B15I~
    	String address=AjagoUtils.getIPAddress(false/*no ipv6 append*/);//~1B15I~
		int port=Global.getParameter("serverport",6970);          //~1B15I~
                                                                   //~1B15I~
		ListElement pe=Global.PartnerList.first();                 //~1B15I~
		DatagramMessage d=new DatagramMessage();                   //~1B15I~
//  	d.add("query");                                            //~1B15I~//~1B19R~
    	d.add("open");                                             //~1B19I~
		d.add(name);                                               //~1B15I~
		d.add(address);                                            //~1B15I~
		d.add(""+port);                                            //~1B15I~
//  	d.add(""+(!Server.publicServer?Partner.PRIVATE:Partner.PUBLIC));//~1B15R~//~1B19R~
    	d.add(""+Partner.SILENT);                                  //~1B19R~
	    if (Dump.Y) Dump.println("PartnerServerThread:sendQuery name="+name+",address="+address+",port="+port);//~v1E1I~
		while (pe!=null)                                           //~1B15I~
		{	Partner p=(Partner)pe.content();                       //~1B15I~
			if (!p.Name.equals(name))                              //~1B15R~
            {                                                      //~1B15I~
				ListElement e=find(p.Name,p.Server);               //~1B15I~
				if (e!=null) // only, if the server was open before//~1B15I~
                {                                                  //~1B15I~
					Global.OpenPartnerList.remove(e);	//append when replyed//~1B15I~
                    if (Dump.Y) Dump.println("PartnerServerThread query remove "+p.Name+",ip="+p.Server);//~1B1dI~
		    	    updatectrOPL++;                                //~1B15I~
                }                                                  //~1B15I~
              if (isValidTarget(p.Server,address))                 //~1B1dI~
				d.send(p.Server,p.Port+2);                         //~1B15I~
            }                                                      //~1B15I~
			pe=pe.next();                                          //~1B15I~
		}                                                          //~1B15I~
	}                                                              //~1B15I~
//***********************************************************      //~1B1dI~
	private boolean isValidTarget(String Ptgt,String Pmyaddr)              //~1B1dI~
    {                                                              //~1B1dI~
    	boolean rc=false;                                          //~1B1dI~
		try                                                        //~1B1dI~
		{                                                          //~1B1dI~
			InetAddress na=InetAddress.getByName(Ptgt);            //~1B1dR~
            rc=!(na.isLoopbackAddress()||na.isLinkLocalAddress());  //~1B1dI~
	        if (!rc)   //to myself                                 //~1B1dI~
            	rc=Server.publicServer; //append myself if opened as public//~1B1dI~
		}                                                          //~1B1dI~
  		catch (Exception e)                                        //~1B1dI~
        {                                                          //~1B1dI~
        	Dump.println(e,"DatagramMessage send");                //~1B1dI~
        }                                                          //~1B1dI~
        if (Dump.Y) Dump.println("PartnerServerThread isValidTarget tgt="+Ptgt+",myaddr="+Pmyaddr+",publicServer="+Server.publicServer+",rc="+rc);//~1B1dR~
        return rc;                                                 //~1B1dI~
	}                                                              //~1B1dI~
//***********************************************************      //~1B1dI~
	private void closeinfo (String address, int port)                       //~1B1dI~
	{	ListElement e=Global.OpenPartnerList.first();              //~1B1dI~
		DatagramMessage d;                                         //~1B1dI~
		d=new DatagramMessage();                                   //~1B1dI~
		d.add("close");                                            //~1B1dI~
		d.add(Global.getParameter("yourname","Unknown"));          //~1B1dI~
		try                                                        //~1B1dI~
    	{                                                          //~1B1dI~
    		String s=AjagoUtils.getIPAddress(false/*no ipv6 append*/);//~1B1dI~
			d.add(s.substring(s.lastIndexOf('/')+1));              //~1B1dI~
		}                                                          //~1B1dI~
		catch (Exception ex) { d.add("Unknown Host"); }            //~1B1dI~
		d.add(""+Global.getParameter("serverport",6970));          //~1B1dI~
		d.add(""+Partner.PRIVATE);                                 //~1B1dI~
		d.send(address,port+2);                                    //~1B1dI~
	}                                                              //~1B1dI~
////***********************************************************      //~1B15I~//~1B19R~
//    void query_received (Vector v)                                 //~1B15R~//~1B19R~
//    {   int i=0;                                                   //~1B15I~//~1B19R~
//        String name,server;                                        //~1B15I~//~1B19R~
//        int port,state;                                            //~1B15I~//~1B19R~
//        try                                                        //~1B15I~//~1B19R~
//        {   if (!((String)v.elementAt(i)).equals("query")) return; //~1B15I~//~1B19R~
//            if (Dump.Y) Dump.println("PartnerServerThread query_reply busy="+Global.Busy+",public="+Server.publicServer);//~1B15I~//~1B19R~
//            if (Global.Busy) //closed                              //~1B15I~//~1B19R~
//                return;                                            //~1B15I~//~1B19R~
//            if (!Server.publicServer)                //~1B15I~   //~1B19R~
//                return;                                            //~1B15I~//~1B19R~
//            i++;                                                   //~1B15I~//~1B19R~
//            name=(String)v.elementAt(i); i++;                      //~1B15R~//~1B19R~
//            server=(String)v.elementAt(i); i++;                    //~1B15R~//~1B19R~
//            port=Integer.parseInt((String)v.elementAt(i)); i++;    //~1B15R~//~1B19R~
//            state=Integer.parseInt((String)v.elementAt(i)); i++;   //~1B15R~//~1B19R~
//            query_sendreply(server,port);                          //~1B15R~//~1B19R~
//        }                                                          //~1B15I~//~1B19R~
//        catch (Exception e)                                        //~1B15R~//~1B19R~
//        {                                                          //~1B15I~//~1B19R~
//            Dump.println(e,"query_reply");                         //~1B15I~//~1B19R~
//        }                                                          //~1B15I~//~1B19R~
//    }                                                              //~1B15I~//~1B19R~
////***********************************************************      //~1B15I~//~1B19R~
//    void query_sendreply(String address, int port)                     //~1B15I~//~1B19R~
//    {                                                              //~1B15I~//~1B19R~
//        DatagramMessage d;                                         //+1B15I~        String address="";                                         //~1B15I~//~1B19R~
//        d=new DatagramMessage();                                   //~1B15I~//~1B19R~
//        d.add("query_reply");                                      //~1B15R~//~1B19R~
//        d.add(Global.getParameter("yourname","Unknown"));          //~1B15I~//~1B19R~
//        String s=AjagoUtils.getIPAddress(false/*no ipv6 append*/); //~1B15I~//~1B19R~
//        d.add(s.substring(s.lastIndexOf('/')+1));                  //~1B15I~//~1B19R~
//        d.add(""+Global.getParameter("serverport",6970));          //~1B15I~//~1B19R~
//        d.add(""+Partner.PUBLIC);                                  //~1B15R~//~1B19R~
//        d.send(address,port+2);                                    //~1B15I~//~1B19R~
//    }                                                              //~1B15I~//~1B19R~
////***********************************************************      //~1B15R~//~1B19R~
//    void query_reply_received (Vector v)                           //~1B15R~//~1B19R~
//    {                                                              //~1B15R~//~1B19R~
//        String name=(String)v.elementAt(1);                        //~1B15R~//~1B19R~
//        String address=(String)v.elementAt(2);                     //~1B15R~//~1B19R~
//        int port=Integer.parseInt((String)v.elementAt(3));         //~1B15R~//~1B19R~
//        int state=Integer.parseInt((String)v.elementAt(4));        //~1B15R~//~1B19R~
//                                                                   //~1B15R~//~1B19R~
//        ListElement e=find(name,address);                          //~1B15R~//~1B19R~
//        if (e!=null) return;                                       //~1B15R~//~1B19R~
//        Global.OpenPartnerList.append(new ListElement(             //~1B15R~//~1B19R~
//            new Partner(name,address,port,state)));                //~1B15R~//~1B19R~
//        updatectrOPL++;                                            //~1B15I~//~1B19R~
//        if (Dump.Y) Dump.println("PartnerServerThread reply_received append name="+name+",addr="+address+",updatectr="+updatectrOPL);//~1B15I~//~1B19R~
//    }                                                              //~1B15R~//~1B19R~
	class SendDatagramThread extends Thread                        //~v1E1I~
	{                                                              //~v1E1I~
		public SendDatagramThread()                                //~v1E1I~
		{                                                          //~v1E1I~
		}                                                          //~v1E1I~
		public void run ()                                         //~v1E1I~
		{                                                          //~v1E1I~
          	try                                                    //~v1E1I~
          	{                                                      //~v1E1I~
                String name=Global.getParameter("yourname","Unknown");//~v1E1I~
                String address=AjagoUtils.getIPAddress(false/*no ipv6 append*/);//~v1E1I~
                int port=Global.getParameter("serverport",6970);   //~v1E1I~
                                                                   //~v1E1I~
                DatagramMessage d=new DatagramMessage();           //~v1E1I~
                d.add(SEND_QUERY);                                 //~v1E1I~
                d.add(name);                                       //~v1E1I~
                d.add(address);                                    //~v1E1I~
                d.add(""+port);                                    //~v1E1I~
                d.add(""+Partner.SILENT);                          //~v1E1I~
                d.send(address,port+2);                            //~v1E1I~
			    if (Dump.Y) Dump.println("SendDatagramThread:run name="+name+",address="+address+",port="+port);//~v1E1I~
          	}                                                      //~v1E1I~
          	catch(Exception e)                                     //~v1E1I~
          	{                                                      //~v1E1I~
        		Dump.println(e,"PartnerServerThread:SendDatagramThread");//~v1E1I~
          	}                                                      //~v1E1I~
		}                                                          //~v1E1I~
	}                                                              //~v1E1I~
    private String getNameOfPartnerList(String Ppartneryourname,String Pserver)//~v1EqI~
    {                                                              //~v1EqI~
  		String partnerlistname=findPartnerList(Pserver);           //~v1EqR~
  		if (partnerlistname==null)                                 //~v1EqI~
        	return Ppartneryourname;                               //~v1EqI~
        if (partnerlistname.equals(Ppartneryourname))              //~v1EqI~
        	return Ppartneryourname;                               //~v1EqI~
        return partnerlistname+"("+Ppartneryourname+")";           //~v1EqI~
    }                                                              //~v1EqI~
	public String findPartnerList(String Pserver)                  //~v1EqR~
	{                                                              //~v1EqI~
		ListElement lc=Global.PartnerList.first();                 //~v1EqI~
		Partner c;                                                 //~v1EqI~
		while (lc!=null)                                           //~v1EqI~
		{	c=(Partner)lc.content();                               //~v1EqI~
			if (c.Server.equals(Pserver))                          //~v1EqR~
				return c.Name;                                     //~v1EqI~
			lc=lc.next();                                          //~v1EqI~
		}                                                          //~v1EqI~
		return null;                                               //~v1EqI~
	}                                                              //~v1EqI~
}
