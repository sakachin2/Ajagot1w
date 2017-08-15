//*CID://+1Ac3R~:                             update#=   29;       //~1Ac3R~
//**************************************************************************//~1B08I~
//2015/07/23 //1Ac3 2015/07/06 WD:Unpare after active session was closed//~1Ac3I~
//2015/07/23 //1Ac1 2015/07/06 WD:try for exclusivity of WiFi and WiFiDirect,like as BT:1AbM exchange @@@@end & @@@@!end before close//~1Ac1I~
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
//package jagoclient.partner;                                      //~1A8cR~
package wifidirect;                                                //~1A8cI~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;
import jagoclient.partner.PartnerGoFrame;


//import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.Ajagoc.AG;
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.awt.TextField;

//import rene.viewer.Viewer;                                       //~1318R~
import com.Ajagoc.rene.viewer.Viewer;                              //~1318I~

import wifidirect.PartnerFrame;                                    //~1A8cI~

/**
A thrad to expect input from a partner. The input is checked here
for commands (starting with @@).
*/

//public class PartnerThread extends Thread                        //~1A8cR~
public class PartnerThread extends jagoclient.partner.PartnerThread//~1A8cI~
//{	BufferedReader In;                                             //~1A8cR~
{                                                                  //~1A8cI~
//  BufferedReader In;                                             //~1A8cI~
//  PrintWriter Out;                                               //~1A8cR~
//  Viewer T;                                                      //~1A8cR~
//  PartnerFrame PF;                                               //~1A8cR~
//	private PartnerGoFrame PGF;                                    //~1B08I~//~v1DjR~
  	protected PartnerGoFrame PGF;                                  //~v1DjI~
	TextField Input;
	private boolean swUnpair;                                      //~1Ac3I~
	public PartnerThread (BufferedReader in, PrintWriter out,
		TextField input, Viewer t, PartnerFrame pf)
//  {	In=in; Out=out; T=t; PF=pf; Input=input;                   //~1A8cR~
    {                                                              //~1A8cI~
        super(in,out,input,t,pf);                                                   //~1A8cI~
	}
	public void run ()
//  {	try                                                        //~1A8gR~
    {                                                              //~1A8gR~
    	int swEnd=0;                                               //~1Ac1I~
        AG.activeSessionType=AG.AST_WD;                            //~1A8gR~
    	try                                                        //~1A8gR~
		{	while (true)
//  		{	String s=In.readLine();                            //~1A84R~
    		{                                                      //~1A84I~
				if (Dump.Y) Dump.println("wifidirect:PartnerThread readLine");//~1A84I~//~1A8gR~
    		 	String s=In.readLine();                            //~1A84I~
				if (Dump.Y) Dump.println("wifidirect:From Partner: "+s);      //~1506R~//~v1DjM~//~1A8gR~
//  			if (s==null || s.equals("@@@@end")) throw new IOException();//~1Ac1R~
    			if (s==null) throw new IOException();              //~1Ac1I~
				if (s.equals("@@@@end"))                           //~1Ac1I~
                {                                                  //~1Ac1I~
                	PF.out("@@@@!end");	//                         //~1Ac1I~
                	swEnd=1;                                       //~1Ac1I~
                    AjagoUtils.sleep(100);	//time to receive @@@@!end before close//~1Ac1I~
					throw new IOException();                       //~1Ac1I~
                }                                                  //~1Ac1I~
				if (s.equals("@@@@!end"))	//close request        //~1Ac1I~
                {                                                  //~1Ac1I~
                	swEnd=2;                                       //~1Ac1I~
					throw new IOException();                       //~1Ac1I~
                }                                                  //~1Ac1I~
				if (Dump.Y) Dump.println("From Partner:"+s+";");   //+1Ac3I~
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
//  	{	T.append(Global.resourceString("_____Connection_Error")+"\n");//~1Ac1R~
    	{                                                          //~1Ac1I~
			if (Dump.Y) Dump.println("PartnerThread IOException"); //~v1DjI~
            if (swEnd==1)  //received @@@@end(partner terminated),replyed @@@@!end//~1Ac1I~
            {                                                      //~1Ac1I~
	        	if (Dump.Y) Dump.println("wifidirect.PartnerThread ioe after sent @@@@end");//~1Ac1I~
            }                                                      //~1Ac1I~
            else                                                   //~1Ac1I~
            if (swEnd==2)	//received @@@@!end,I'm closing        //~1Ac1I~
            {                                                      //~1Ac1I~
	        	if (Dump.Y) Dump.println("wifidirect.PartnerThread ioe after receive @@@@!end");//~1Ac1I~
            }                                                      //~1Ac1I~
            else                                                   //~1Ac1I~
	        	Dump.println(e,"wifidirect.PartnerThread");        //~1Ac1I~
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
        if (swUnpair)                                              //~1Ac3I~
        	WDA.unpairFromPT();                                    //~1Ac3I~
        if (Dump.Y) Dump.println("wifidirect.PartnerThread===== Run Terminated");  //~3120I~//~@@@9R~//~1Ac3I~
//        if (PF.connectionType!=0)                                  //~1A84I~//~1A8cR~
	        AG.RemoteStatus=0;                                             //~3131I~//~1A84I~
        AG.RemoteInetAddress=null;                                 //~1A8fI~
        AG.LocalInetAddress=null;                                  //~1A8fI~
        AG.activeSessionType=0;                                    //~1A8gR~
	}
    //****************************************************         //~1Ac3I~
    public void unpair(boolean Punpair)                            //~1Ac3I~
    {                                                              //~1Ac3I~
        if (Dump.Y) Dump.println("wifidirect.PartnerThread unpair="+Punpair);//~1Ac3I~
        swUnpair=Punpair;                                          //~1Ac3I~
    }                                                              //~1Ac3I~
}

