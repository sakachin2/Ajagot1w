//*CID://+1AekR~:                                   update#=   45; //+1AekR~
//******************************************************************************************************************//~v107I~
//1Aek 2015/08/08 (Bug)REmotedevicename was not show to (NFC)BT    //+1AekI~
//1Aec 2015/07/26 set connection type for Server                   //~1AecI~
//1Ae2 2015/07/24 addtional to 1Ab1 for Ajagoc                     //~1Ae2I~
//2015/07/23 //1AbM 2015/07/03 BT:short sleep for BT disconnet fo exchange @@@@end and @@@@!end//~1AbMI~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//1A85 2015/02/25 close each time partnerframe for IP Connection   //~1A85I~
//v1Dm 2014/11/14 BT:use devicename when yourname is same          //~v1DmI~
//v1D3 2014/10/07 set timestamp to filename on filedialog when save(current is *.sgf)//~v1D3I~
//1102:130123 bluetooth became unconnectable after some stop operation//~v110I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//******************************************************************************************************************//~v107I~
//*@@@1 20110430 FunctionKey support                               //~@@@1I~
//****************************************************************************//~@@@1I~
package com.Ajagoc.jagoclient.partner;                             //~v107R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;


//import jagoclient.gui.HistoryTextField;
//import jagoclient.partner.PartnerGoFrame;
//import jagoclient.partner.PartnerThread;                         //~v107R~
import com.Ajagoc.AG;
import com.Ajagoc.URunnable;
import com.Ajagoc.URunnableI;
import com.Ajagoc.jagoclient.partner.PartnerThread;                //~v107I~



//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FileDialog;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.Panel;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.bluetooth.BluetoothSocket;

//import rene.util.list.ListClass;
//import rene.viewer.SystemViewer;                                 //~1318R~
//import rene.viewer.Viewer;                                       //~1318R~
//import com.Ajagoc.rene.viewer.Viewer;                              //~1318I~

/**
The partner frame contains a simple chat dialog and a button
to start a game or restore an old game. This class contains an
interpreters for the partner commands.
*/

//public class PartnerFrame extends CloseFrame                     //~v107R~
public class PartnerFrame extends jagoclient.partner.PartnerFrame  //~v107R~
    implements URunnableI                                          //~1AbMI~
{                                                                  //~v107R~
//  Socket Server;                                                 //~v107R~
    private BluetoothSocket Server;                                        //~v107I~
    private String localDeviceName,remoteDeviceName;                                //~v107R~
//  public static final String CONNECT_TO_BT="BT:";	//chk frameid at awt/Frame//~v107I~//~1A8iR~

	public PartnerFrame (String name,String Premotedevicename,String Plocaldevicename,boolean serving,BluetoothSocket PBTSocket)//~v107R~
    {                                                              //~v107I~
//  	super(name+CONNECT_TO_BT+Premotedevicename,serving);       //~v107R~//~1A8iR~
//  	super(name+Premotedevicename,serving,jagoclient.partner.PartnerFrame.CONN_TITLE_BT);//~1A8iI~//~1AecR~
    	super(name+Premotedevicename,serving,                      //~1AecI~
        		(AG.isNFCBT ? jagoclient.partner.PartnerFrame.CONN_TITLE_NFC_BT : jagoclient.partner.PartnerFrame.CONN_TITLE_BT));//~1AecI~
        localDeviceName=Plocaldevicename;                          //~v107R~
        remoteDeviceName=Premotedevicename;                        //~v107I~
        partnerName=remoteDeviceName;   //for also server          //~v1DmI~
        AG.RemoteDeviceName=Premotedevicename;                     //~@@@2I~//+1AekI~
		Server=PBTSocket;                                          //~v107R~
        AG.aPartnerFrame=this;                                     //~v110I~
        AG.aPartnerFrameIP=null;	//set by super()               //~@@@2I~//~1A85I~
		if (Dump.Y) Dump.println("Ajagoc:PartnerFrame constructor namd="+name+",remote="+Premotedevicename+",local="+Plocaldevicename);//~v107R~
	}

//****************************************************************************//~v107I~
//*from BTControl                                                  //~v107I~
//****************************************************************************//~v107I~
	public boolean connect ()                                      //~v107R~
	{                                                              //~v107R~
		if (Dump.Y) Dump.println("Ajagoc:PartnerFrame connect localdevicename="+localDeviceName);//~v107R~
		try
//  	{	Server=new Socket(s,p);                                //~v107R~
    	{                                                          //~v107R~
			Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
		}
		catch (Exception e)
//  	{   return false;                                          //~v107R~
    	{                                                          //~v107I~
            Dump.println(e,"Ajagoc:PartnerFrame:connect");         //~v107I~
            return false;                                          //~v107I~
		}
		PT=new PartnerThread(In,Out,Input,Output,this);
		PT.start();
        myName=Global.getParameter("yourname","No Name");          //~v1D3R~
//  	Out.println("@@name "+                                     //~v1D3R~
//  		Global.getParameter("yourname","No Name"));            //~v1D3R~
//  		localDeviceName);                                      //~v1D3R~
    	Out.println("@@nameBT "+                                   //~v1D3R~
            myName);                                               //~v1D3R~
//      partnerName=remoteDeviceName;                              //~v1DmR~
		show();
        AG.RemoteStatus=AG.RS_BTCONNECTED;                         //~@@@2I~//~1Ae2I~
		return true;
	}

	public boolean connectvia (String server, int port,
		String relayserver, int relayport)
	{                                                              //~v107R~
		return false;	//userelay is off                          //~v107R~
	}

//****************************************************************************//~v107I~
//*from Server.java                                                //~v107I~
//****************************************************************************//~v107I~
//  public void open (Socket server)                               //~v107R~
    public void open (BluetoothSocket server)                      //~v107I~
	{	if (Dump.Y) Dump.println("Starting partner server");       //~@@@1R~
		Server=server;
	    if (Dump.Y) Dump.println("Ajagoc:PartnerFrame:open BTSocket="+Server);//~v107I~
		try
		{	Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
		}
		catch (Exception e)
		{	if (Dump.Y) Dump.println("---> no connection");        //~@@@1R~
			new Message(this,Global.resourceString("Got_no_Connection_"));
			return;
		}
		PT=new PartnerThread(In,Out,Input,Output,this);
		PT.start();
        AG.RemoteStatus=AG.RS_BTCONNECTED;                         //~@@@2I~//~1Ae2I~
	}


	public void doclose ()
	{	Global.notewindow(this,"partner");	
      try	                                                       //~v107I~
      {                                                            //~v107I~
		Out.println("@@@@end");
//  	Utils.sleep(200);    //wait receive @@@@!end and partners.IN IOexception//~1AbMI~
        if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose after sent @@@@end");//~1AbMI~
        URunnable.setRunFunc(this/*URunnableI*/,500/*delay*/,this/*objparm*/,0/*int parm*/);//~1A2jR~//~1AbMI~
//        Out.close();                                             //~1AbMR~
////      new CloseConnection(Server,In);                            //~v107R~//~1AbMR~
//        if (In!=null)                                              //~v107I~//~1AbMR~
//            In.close();                                            //~v107I~//~1AbMR~
//        if (Server!=null)                                          //~v107I~//~1AbMR~
//        {                                                          //~v107I~//~1AbMR~
//            if (Dump.Y) Dump.println("Ajagoc:PartnerFrame:doclose close BTSocket="+Server);//~v107I~//~1AbMR~
//            Server.close();                                        //~v107I~//~1AbMR~
//        }                                                          //~v107I~//~1AbMR~
//        super.doclose();                                         //~1AbMR~
      }                                                            //~v107I~
      catch(Exception e)                                          //~v107I~
      {                                                            //~v107I~
      	Dump.println(e,"Ajagoc:partnerFrame:doclose");             //~v107I~
      }                                                            //~v107I~
	}
//***************************************************************  //~v110R~
//*ReadLine blocks close() so interupt then close               *  //~v110R~
//***************************************************************  //~v110R~
    public void closeStream()                                      //~v110R~
    {                                                              //~v110R~
    	if (PGF!=null)                                             //~v110I~
        	if (PGF.Timer!=null)                                   //~v110I~
            	if (PGF.Timer.isAlive())                          //~v110I~
                {                                                  //~v110I~
				    if (Dump.Y) Dump.println("PartnerFrame Timet Stop");//~v110I~
                	PGF.Timer.stopit();                            //~v110I~
                }                                                  //~v110I~
	    if (Dump.Y) Dump.println("PartnerFrame close Stream");     //~v110R~
        if (PT.isAlive())                                          //~v110R~
        {                                                          //~v110R~
		    if (Dump.Y) Dump.println("PartnerFrame put @@@@end");  //~v110R~
            if (Out!=null)                                         //~v110R~
            {                                                      //~v110R~
                out("@@@@end"); //partnerthread chk this and throw IOException//~v110R~
            }                                                      //~v110R~
        }                                                          //~v110R~
    }                                                              //~v110R~
//***************************************************************  //~1AbMI~
	@Override                                                      //~1AbMI~
	public void runFunc(Object parmObj,int parmInt/*0*/)                        //~1214R~//~@@@@R~//~1AbMI~
    {                                                              //~1AbMI~
    	PartnerFrame pf=(PartnerFrame)parmObj;                     //~1AbMI~
        if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose runFunc");//~1AbMI~
      	try                                                        //~1AbMI~
      	{                                                          //~1AbMI~
            Out.close();  //Out.close() cause IOException on my PartnerThread,Close by Pathnerthread at @@@@!end//~1AbMI~
            if (Dump.Y) Dump.println("Asgts:PartnerFrame:runFunc Out closed Out="+Out);//~1AbMI~
//          if (In!=null)                                          //~1AbMI~
//  			In.close();                                        //~1AbMI~
//          if (Server!=null)                                      //~1AbMI~
//          {                                                      //~1AbMI~
//              if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose close BTSocket="+Server);//~1AbMI~
//              Server.close();                                    //~1AbMI~
//          }                                                      //~1AbMI~
//  	    super.doclose();                                       //~1AbMI~
    	    doclose2();  //do not out @@@@end,CloseConnection close Server and In//~1AbMI~
      	}                                                          //~1AbMI~
      	catch(Exception e)                                         //~1AbMI~
      	{                                                          //~1AbMI~
      		Dump.println(e,"Asgts:partnerFrame:runfunc");          //~1AbMI~
      	}                                                          //~1AbMI~
    }                                                              //~1AbMI~

}

