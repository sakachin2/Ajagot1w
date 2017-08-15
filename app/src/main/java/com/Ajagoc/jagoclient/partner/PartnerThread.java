//*CID://+1AbMR~:                                   update#=   34; //~1A8gR~//~1AbMR~
//******************************************************************************************************************//~v107I~
//2015/07/23 //1AbM 2015/07/03 BT:short sleep for BT disconnet fo exchange @@@@end and @@@@!end//~1AbMI~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//v1Dj 2014/11/13 (Bug)BT connection did not show "Opponent said" on comment area//~v1DjI~
//v1Db 2014/11/09 partner thread NPE-->ACR; set catch              //~v1DbI~
//1B0f 130429 popup shutdown msg is requred because connection frame is hidden for partner connection//~1B0fI~
//1102:130123 bluetooth became unconnectable after some stop operation//~v110I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//******************************************************************************************************************//~v107I~
//package jagoclient.partner;                                      //~v107R~
package com.Ajagoc.jagoclient.partner;                             //~v107I~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;


//import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.awt.TextField;

//import rene.viewer.Viewer;                                       //~1318R~
import com.Ajagoc.rene.viewer.Viewer;                              //~1318I~

/**
A thrad to expect input from a partner. The input is checked here
for commands (starting with @@).
*/

//public class PartnerThread extends Thread                        //~v107R~
public class PartnerThread extends jagoclient.partner.PartnerThread        //~v107I~
{   BufferedReader In;                                           
    PrintWriter Out;                                             
    Viewer T;                                                    
    PartnerFrame PF;                                             
    TextField Input;                                             
    int swThrow=0;                                                 //~v110R~
    public PartnerThread (BufferedReader in, PrintWriter out,    
        TextField input, Viewer t, PartnerFrame pf)              
//  {   In=in; Out=out; T=t; PF=pf; Input=input;                   //~v107R~
    {                                                              //~v107I~
        super(in,out,input,t,pf);                                  //~v107I~
        In=in; Out=out; T=t; PF=pf; Input=input;                   //~v107I~
    }                                                            
	public void run ()
//  {	try                                                        //~1A8gR~
    {                                                              //~1A8gI~
        AG.activeSessionType=AG.AST_BT;                            //~1A8gI~
    	try                                                        //~1A8gI~
		{	while (true)
			{	String s=In.readLine();
				if (Dump.Y) Dump.println("PartnerThread read from Partner: "+(s==null?"null":s));//~v110I~
//  			if (s==null || s.equals("@@@@end")) throw new IOException();//~v110R~
    			if (s==null)                                       //~v110I~
                {                                                  //~v110I~
                    swThrow=0;                                  //~v110I~
    				throw new IOException();                       //~v110I~
                }                                                  //~v110I~
                else                                               //~v110I~
    			if (s.equals("@@@@end"))	//partner close req    //~v110R~
                {                                                  //~v110I~
                	PF.out("@@@@!end");     //close timing of partner//~v110I~
                    swThrow=1;	                                   //~v110R~
                    AjagoUtils.sleep(100);	//time to receive @@@@!end before close//~1AbMI~
					throw new IOException();                       //~v110I~
                }                                                  //~v110I~
                else                                               //~v110I~
    			if (s.equals("@@@@!end"))                          //~v110I~
                {                                                  //~v110I~
                    swThrow=2;                                     //~v110R~
					throw new IOException();                       //~v110I~
                }                                                  //~v110I~
				if (Dump.Y) Dump.println("From Partner: "+s);      //~1506R~
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
						Dump.println(e,"BT:PartnerThread interpret:"+s);//~v1DbI~
						throw new IOException();                   //~v1DbI~
                    }                                              //~v1DbI~
                }                                                  //~v1DbI~
				else
				{	T.append(s+"\n");
					Input.requestFocus();
                    PGF=PF.PGF;                                    //~v1DjI~
					if (PGF!=null)                                 //~v1DjI~
						PGF.addComment(Global.resourceString("Opponent_said_")+s);//~v1DjI~
				}
			}
		}
		catch (IOException e)
		{	T.append(Global.resourceString("_____Connection_Error")+"\n");
            PF.stopTimer();                                        //~v110I~
//          Out.close();                                           //~v110I~
            closeIO();                                             //~v110I~
        	if (swThrow!=0)                                        //~v110R~
            {                                                      //~v110I~
	    		Dump.println(e,"PartnerThread IOException");       //~v110I~
                if (swThrow==1)	//partner closed                   //~v110I~
                {                                                  //~v110I~
					new Message(Global.frame(),Global.resourceString("Lost_Connection"));//~v110I~
                }                                                  //~v110I~
            }                                                      //~v110I~
            else                                                   //~v110I~
	    		if (Dump.Y) Dump.println("PartnerThread thrown IOException");//~v110I~
        	AG.ajagoBT.connectionLost();                              //~v107I~
            if (PF.PGF!=null)	//not closed                       //~1B0fI~
			{                                                      //~1B0fI~
            	new Message(Global.frame(),Global.resourceString("Lost_Connection"));//~1B0fI~
            	try { sleep(10000); } catch (Exception i) {}       //~1B0fI~
            }                                                      //~1B0fI~
		}
        AG.RemoteStatus=0;                                         //+1AbMI~
        AG.activeSessionType=0;                                    //~1A8gI~
        if (Dump.Y) Dump.println("com.PartnerThread return");       //+1AbMI~
	}
//*****************************************                        //~v110I~
//*close stream before sockt is prefreable                         //~v110I~
//*****************************************                        //~v110I~
    private void closeIO()                                         //~v110I~
    {                                                              //~v110I~
        PF.stopTimer();                                            //~v110I~
	    if (Dump.Y) Dump.println("PartnerThread closeIO");         //~v110I~
        try                                                        //~v110I~
        {                                                          //~v110I~
        	if (Out!=null)                                         //~v110I~
            {                                                      //~v110I~
	    		Out.close();                                       //~v110I~
	    		if (Dump.Y) Dump.println("PartnerThread closed Out");//~v110I~
            }                                                      //~v110I~
	        if (In!=null)                                          //~v110I~
            {                                                      //~v110I~
		        In.close();                                        //~v110I~
	    		if (Dump.Y) Dump.println("PartnerThread closed In");//~v110I~
            }                                                      //~v110I~
        }                                                          //~v110I~
        catch(Exception e)                                         //~v110I~
        {                                                          //~v110I~
        	Dump.println(e,"partnerThread:closeIO()");             //~v110I~
        }                                                          //~v110I~
                                                                   //~v110I~
	    if (Dump.Y) Dump.println("PartnerThread closeIO end");     //~v110I~
    }                                                              //~v110I~
}

