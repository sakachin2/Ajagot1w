//*CID://+1AehR~:                             update#=    6;       //~1AehR~
//******************************************************************//~1B06I~
//1Aeh 2015/07/31 add "server opened?" to msg of IP connection failed//~1AehI~
//v1Ep 2014/12/15 after connection fail, sllep 10 sec. i dont know why.//~v1EpI~
//                But it may be consideration for network deley    //~v1EpI~
//                trying flag is not cleared until 10sec elapsed and errmsg "trying connect" is issued.//~v1EpI~
//1B0c 130429 Encoding support for partner connection              //~1B0cI~
//1B06 130428 Warning userrelay option is on when partner connection failed//~1B06I~
//******************************************************************//~1B06I~
package jagoclient.partner;

import com.Ajagoc.AG;
import com.Ajagoc.R;

import jagoclient.Global;
import jagoclient.dialogs.Message;
import jagoclient.partner.partner.Partner;

/**
A thread, which will try to connect to a go partner.
If it is successfull, a Partner Frame will open.
Otherwise, an error message will appear.
*/

public class ConnectPartner extends Thread
{	Partner P;
	PartnerFrame PF;
    private static final int WAIT_TIME=5;	//seconds to retry connect after//~v1EpI~
	public ConnectPartner (Partner p, PartnerFrame pf)
	{	P=p; PF=pf;
		PF.partnerName=p.Name;//BT send partner name @@!nameBT,IP use definition on partner list//~1B0cI~
		start();
	}
	public void run ()
	{	P.Trying=true;
		if (Global.getParameter("userelay",false))
		{	if (!PF.connectvia(P.Server,P.Port,
				Global.getParameter("relayserver","localhost"),
				Global.getParameter("relayport",6971)))
			{	PF.setVisible(false); PF.dispose();
				new Message(Global.frame(),
//  				Global.resourceString("No_connection_to_")+P.Server);//~1B06R~
    				Global.resourceString("No_connection_to_")+P.Server+"\n"//~1B06I~
                    +AG.resource.getString(R.string.WarningUseRelay));//~1B06I~
				try
				{	sleep(10000);
				}
				catch (Exception e)
				{	P.Trying=false;
				}
			}
		}
//  	else if (!PF.connect(P.Server,P.Port))                     //~1B0cR~
    	else if (!PF.connect(P.Server,P.Port,P.Encoding))          //~1B0cI~
		{	PF.setVisible(false); PF.dispose();
			new Message(Global.frame(),
//  			Global.resourceString("No_connection_to_")+P.Server);//~v1EpR~
//  			Global.resourceString("No_connection_to_")+P.Server+"\n"+AG.resource.getString(R.string.InfoRetryConnectAfterPartner,WAIT_TIME));//~v1EpI~//~1AehR~
    			Global.resourceString("No_connection_to_")+P.Server+"\n"+//~1AehI~
                				(PF.connectionType==0              //~1AehI~
									? AG.resource.getString(R.string.InfoRetryConnectAfterPartner,WAIT_TIME)//~1AehI~
									: AG.resource.getString(R.string.InfoRetryConnectAfterPartnerWD,WAIT_TIME)//~1AehI~
								));                                 //~1AehI~
			try
//  		{	sleep(10000);                                      //~v1EpR~
    		{                                                      //~v1EpI~
    		 	sleep(WAIT_TIME*1000);                             //~v1EpI~
			}
			catch (Exception e)
			{	P.Trying=false;
			}
		}
		P.Trying=false;
	}
}

