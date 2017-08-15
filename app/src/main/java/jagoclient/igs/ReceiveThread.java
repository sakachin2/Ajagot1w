//*CID://+v1C5R~:                             update#=    5;       //~v1C5I~
//****************************************************************************//~v1C5I~
//v1C5 2014/08/28 protect wait who/game before login on connectionFrame//~v1C5I~
//****************************************************************************//~v1C5I~
package jagoclient.igs;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;

import java.io.IOException;
import java.io.PrintWriter;

//import rene.viewer.Viewer;                                       //~1214R~
import com.Ajagoc.Ajagoc;
import com.Ajagoc.rene.viewer.Viewer;                              //~1214I~

/**
This thread uses the output of IgsStream to login to server.
After the login it will fall into a loop, and echo all input
to the Output text area of the ConnectionFrame. Some elements
get filtered by this process. But most filtering is done by
IgsStream itself.
<P>
Moreover, it will toggle to client mode, if necessary.
@see jagoclient.igs.IgsStream
*/

public class ReceiveThread extends Thread
{	IgsStream In;
	PrintWriter Out;
	Viewer Output;
	String User,Password;
	boolean FileMode,Proxy;
	boolean TriedClient=false;
	ConnectionFrame CF;
		
	public ReceiveThread (Viewer output, IgsStream in, PrintWriter out,
		String user, String password, boolean proxy, ConnectionFrame cf)
	{	In=in; Out=out;
		Output=output;
		User=user; Password=password;
		FileMode=false;
		Proxy=proxy;
		CF=cf;
        if (Dump.Y) Dump.println("ReceiveThread User="+User+",pasword="+Password);//~1511R~
	}

	public void run ()
	{	boolean Auto=Global.getParameter("automatic",true)&&!User.equals("");
		try
		{	if (!Proxy) Out.println("");
			// try to auto-login into the server
			if (Auto && !Proxy)
			{	while (true)
				{	if (In.readline())
					{	Output.append(In.line()+"\n");
					}
                    if (Dump.Y) Dump.println("ReceiveThread Auto In.line="+In.line());//~4830R~
					if (In.line().startsWith("Login"))
					{	Output.append(Global.resourceString("_____logging_in_n"));
						Out.println(User);
						break;
					}
				}
				if (Dump.Y) Dump.println("--- Leaving Login section ---");//~1506R~
				while (true)
				{	if (In.readline())
					{	if (In.number()==1 && In.commandnumber()==1)
						{	Out.println(Password);
							Output.append(Global.resourceString("_____sending_password_n"));
							break;
						}
						else if (In.number()==1)
						{	Output.append(Global.resourceString("_____enter_commands__n"));
							break;
						}
						else Output.append(In.line()+"\n");
                        if (Dump.Y) Dump.println("ReceiveThread Auto In number="+In.number());//~1511R~//~4830R~
					}
					else
					{	if (In.line().startsWith("#>"))
						{	goclient();
						}
						else if (In.line().startsWith("Password"))
						{	Out.println(Password);
							Output.append(Global.resourceString("_____sending_password_n"));
						}
                        if (Dump.Y) Dump.println("ReceiveThread Auto other In line="+In.line());//~4830I~
					}
				}
				if (Dump.Y) Dump.println("--- Leaving Password section ---");//~1506R~
//              CF.swLogin=true;                                   //~4828I~//~v1C5R~
			}
			// end of autologin and start of loop
			boolean AskPassword=true;
			while (true)
			{	try
				{	if (In.readline())
					{	if (FileMode && In.number()!=100) FileMode=false;
						else if (In.command().equals("File")) FileMode=true;
                        if (Dump.Y) Dump.println("ReceiveThread not Auto In.line="+In.line()+",In.command="+In.command()+",number="+In.number());//~4830R~
						switch (In.number())
						{	case 1 :
								Proxy=false;
								if (!Auto && In.commandnumber()==1 && AskPassword)
								{	Output.append(Global.resourceString("____Enter_Password_____n"));
									AskPassword=false;
//  				                CF.swLogin=true;               //~4828I~//~v1C5R~
									break;
								}
								else if (!Auto && In.commandnumber()==0)
								{	Output.append(Global.resourceString("____Enter_Login_____n"));
									AskPassword=true;
			                        if (Dump.Y) Dump.println("ReceiveThread not Auto login1 In.line="+In.line());//~v1C5I~
									break;
								}
							case 40 :
							case 22 :
							case 2 :
								break;
							default :
								if (FileMode || !In.command().equals(""))
									CF.append(In.command());
						}
			            if (Dump.Y) Dump.println("ReceiveThread Auto="+Auto);//+v1C5I~
						if (!Auto && In.command().startsWith("Login"))
						{	AskPassword=true;
							Output.append(Global.resourceString("____Enter_Login_____n"));
	                        if (Dump.Y) Dump.println("ReceiveThread not Auto login2 In.line="+In.line());//~v1C5R~
						}
						if (In.command().startsWith("#>"))
						{	goclient();
//					        CF.swLogin=true;                       //~4828I~//~v1C5R~
						}
					}
					else if (!Auto && In.line().startsWith("Login"))
					{	Output.append(Global.resourceString("____Enter_Login_____n"));
                        if (Dump.Y) Dump.println("ReceiveThread not Auto login3 In.line="+In.line());//~4830R~//~v1C5R~
                        CF.enterLogin();                           //~v1C5I~
					}
					else if (In.command().startsWith("#>"))
					{	goclient();
//					    CF.swLogin=true;                           //~4828I~//~v1C5R~
                        if (Dump.Y) Dump.println("ReceiveThread not Auto login I.command="+In.command());//~4830R~
					}
					else if (Proxy)
					{	Output.append(In.line());
					}
				}
				catch (IOException e)
				{	throw e;
				}
				catch (Exception e)
				{	if (Dump.Y) Dump.println("Exception (please report to the author)\n"+//~1511R~
						e.toString()+"\n");
					e.printStackTrace();
                    Dump.println(e,"ReaceiveThread Exception @@@@");//~1305I~//~1310R~
				}
			}
		}
		catch (IOException ex) // server has closed connection
		{
			if (Ajagoc.isTerminating())//onDestroy @@@@
			{
				if (Dump.Y) Dump.println("ReceiveThread returns"); //~1511R~
				return; //stop() was depricated;
			}
			if (!CF.hasClosed)
			{	Output.append(Global.resourceString("_____connection_error__n")+ex+"\n");
				new Message(Global.frame(),Global.resourceString("Lost_Connection"));
				try { sleep(10000); } catch (Exception e) {}
				return;
			}
		}
	}
	
	/** 
	Called from outside to go to client mode.
	*/
	public void goclient ()
	{	if (TriedClient) return;
		Output.append(Global.resourceString("____toggle_client_true_n"));
		Out.println("toggle client true");
		TriedClient=true;
	}
	public void resetgoclient()                                    //~v1C5I~
	{                                                              //~v1C5I~
		TriedClient=false;                                         //~v1C5I~
	}                                                              //~v1C5I~

}
