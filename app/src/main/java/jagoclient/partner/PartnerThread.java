//*CID://+1Ae9R~:                             update#=   19;       //~1A8gR~//+1Ae9R~
//**************************************************************************//~1B08I~
//1Ae9 2015/07/26 (Ajagoc only)All connection type passes to jagoclient PartnerFrame to (re)start/disconnect//+1Ae9I~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//1A84 2015/02/25 WiFiDirect from partnerTab                       //~1A84I~
//v1Dj 2014/11/13 (Bug)BT connection did not show "Opponent said" on comment area//~v1DjI~
//v1Db 2014/11/09 partner thread NPE-->ACR; set catch              //~v1DbI~
//1B0f 130429 popup shutdown msg is requred because connection frame is hidden for partner connection//~1B0fI~
//1B08 130428 Partner Connection:display msg to comment area by send icon button//~1B08I~
//            even when ExtraSendField option is off like as PartnerSendQuetion.//~1B08I~
//            And also received msg.                               //~1B08I~
//            (receive msg is displayed on ConnectionFrame, but it is back of GoFrame)//~1B08I~
//**************************************************************************//~1B08I~
package jagoclient.partner;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;

//import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.Ajagoc.AG;
import com.Ajagoc.awt.TextField;

//import rene.viewer.Viewer;                                       //~1318R~
import com.Ajagoc.rene.viewer.Viewer;                              //~1318I~

/**
A thrad to expect input from a partner. The input is checked here
for commands (starting with @@).
*/

public class PartnerThread extends Thread
//{	BufferedReader In;                                             //~1A8cR~
{                                                                  //~1A8cI~
  protected                                                        //~1A8cI~
  	BufferedReader In;                                             //~1A8cI~
  protected                                                        //~1A8cI~
	PrintWriter Out;
  protected                                                        //~1A8cI~
	Viewer T;
  protected                                                        //~1A8cI~
	PartnerFrame PF;
//	private PartnerGoFrame PGF;                                    //~1B08I~//~v1DjR~
  	protected PartnerGoFrame PGF;                                  //~v1DjI~
	TextField Input;
	public PartnerThread (BufferedReader in, PrintWriter out,
		TextField input, Viewer t, PartnerFrame pf)
	{	In=in; Out=out; T=t; PF=pf; Input=input;
	}
	public void run ()
//  {	try                                                        //~1A8gR~
    {                                                              //~1A8gR~
        AG.activeSessionType=AG.AST_IP;                               //~1A8gR~
    	try                                                        //~1A8gR~
		{	while (true)
//  		{	String s=In.readLine();                            //~1A84R~
    		{                                                      //~1A84I~
				if (Dump.Y) Dump.println("PartnerThread readLine");//~1A84I~
    		 	String s=In.readLine();                            //~1A84I~
				if (Dump.Y) Dump.println("From Partner: "+s);      //~1506R~//~v1DjM~
				if (s==null || s.equals("@@@@end")) throw new IOException();
				if (s.startsWith("@@busy"))
				{	T.append(Global.resourceString("____Server_is_busy____"));
					return;
				}
//  			else if (s.startsWith("@@")) PF.interpret(s);      //~v1DbR~
    			else if (s.startsWith("@@"))                       //~v1DbI~
                {                                                  //~v1DbI~
                	try                                            //~v1DbI~
                    {                                              //~v1DbI~
    					PF.interpret(s);                           //~v1DbI~
                    }                                              //~v1DbI~
					catch (Exception e)                            //~v1DbI~
					{                                              //~v1DbI~
						Dump.println(e,"IP:PartnerThread interpret:"+s);//~v1DbI~
						throw new IOException();                   //~v1DbI~
                    }                                              //~v1DbI~
                }                                                  //~v1DbI~
				else
				{	T.append(s+"\n");
					Input.requestFocus();
                    PGF=PF.PGF;	                                   //~1B08I~
					if (PGF!=null)                                 //~1B08I~
						PGF.addComment(Global.resourceString("Opponent_said_")+s);//~1B08I~
                }                                                  //~1B08I~
			}
		}
		catch (IOException e)
		{	T.append(Global.resourceString("_____Connection_Error")+"\n");
			if (Dump.Y) Dump.println("PartnerThread IOException"); //~v1DjI~
            PF.stopTimer();                                        //~3127I~
            if (PF.PGF!=null)	//not closed                       //~1B0fI~
			{                                                      //~1B0fI~
              	new Message(Global.frame(),Global.resourceString("Lost_Connection"));//~1B0fR~
              	try { sleep(10000); } catch (Exception i) {}       //~1B0fR~
            }                                                      //~1B0fI~
		}
		catch (Exception e)                                        //~v1DjI~
		{                                                          //~v1DjI~
			Dump.println(e,"PartnerThread run");                   //~v1DjI~
		}                                                          //~v1DjI~
        AG.RemoteInetAddressLAN=null;                              //~1A8fI~
        AG.LocalInetAddressLAN=null;                               //~1A8fI~
        AG.activeSessionType=0;                                    //~1A8gR~
        if (Dump.Y) Dump.println("jagoclient.PartnerThread return");//~1A84I~//~1A8cR~//+1Ae9R~
//        if (PF.connectionType!=0)                                  //~1A84I~//~1A8cR~
//            AG.RemoteStatus=0;                                             //~3131I~//~1A84I~//~1A8cR~
        AG.RemoteStatus=0;                                         //+1Ae9I~
	}
}

