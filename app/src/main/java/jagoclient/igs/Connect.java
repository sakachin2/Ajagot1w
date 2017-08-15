//*CID://+v1EpR~:                             update#=    1;       //+v1EpI~
//*************************************************************************//+v1EpI~
//v1Ep 2014/12/15 after connection fail, sllep 10 sec. i dont know why.//+v1EpI~
//                But it may be consideration for network deley    //+v1EpI~
//                trying flag is not cleared until 10sec elapsed and errmsg "trying connect" is issued.//+v1EpI~
//*************************************************************************//+v1EpI~
package jagoclient.igs;

import com.Ajagoc.AG;
import com.Ajagoc.R;

import jagoclient.Global;
import jagoclient.dialogs.Message;                                                          //~1112I~
import jagoclient.igs.connection.Connection;

/**
A thread, which tries to connect to a server. It will open
a ConnectionFrame to display the connection on success.
<P>
If it fails, it will display an error message for 10 seconds.
*/

public class Connect extends Thread
{	Connection C;
	private static final int WAIT_TIME=5;                          //+v1EpI~
	ConnectionFrame CF;
	String S;
	public Connect (Connection c, ConnectionFrame cf)
	{	C=c; CF=cf; S="";
		start();
	}
	public Connect (Connection c, String s, ConnectionFrame cf)
	{	C=c; CF=cf; S=s;
		start();
	}
	public void run ()
	{	C.Trying=true;
		CF.movestyle(C.MoveStyle);
		if (Global.getParameter("userelay",false))
		{	if (!CF.connectvia(C.Server,C.Port,C.User,
				S.equals("")?C.Password:S,
				Global.getParameter("relayserver","localhost"),
				Global.getParameter("relayport",6971)))
			{	CF.setVisible(false); CF.dispose();
				new Message(Global.frame(),Global.resourceString("No_connection_to_")+C.Server+"!");
				try
				{	sleep(10000);
				}
				catch (Exception e)
				{	C.Trying=false;	
				}
			}
		}
		else if (!CF.connect(C.Server,C.Port,C.User,
			S.equals("")?C.Password:S,C.Port==23?true:false))
		{	CF.setVisible(false); CF.dispose();
//  		new Message(Global.frame(),Global.resourceString("No_connection_to_")+C.Server+"!");//+v1EpR~
    		new Message(Global.frame(),Global.resourceString("No_connection_to_")+C.Server+"!"+"\n"+AG.resource.getString(R.string.InfoRetryConnectAfter,WAIT_TIME));//+v1EpI~
			try
//  		{	sleep(10000);                                      //+v1EpR~
    		{                                                      //+v1EpI~
    		 	sleep(WAIT_TIME*1000);                             //+v1EpI~
			}
			catch (Exception e)
			{	C.Trying=false;	
			}
		}
		C.Trying=false;
	}
}

