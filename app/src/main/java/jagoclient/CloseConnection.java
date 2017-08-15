package jagoclient;

import java.net.*;
import java.io.*;
import jagoclient.Dump;                                            //+5226I~

public class CloseConnection extends Thread
{	Socket S;
	BufferedReader In;
	public CloseConnection (Socket s, BufferedReader in)
	{	S=s; In=in;
		start();
        if (Dump.Y) Dump.println("CloseConnection");               //+5226I~
	}
	public void run ()
	{	try
		{	if (S!=null)                                           //+5226R~
        	{                                                      //+5226I~
				S.close();                                         //+5226I~
		        if (Dump.Y) Dump.println("CloseConnection close S="+S);//+5226I~
            }                                                      //+5226I~
			if (In!=null)                                          //+5226R~
			{                                                      //+5226I~
				In.close();                                        //+5226I~
		        if (Dump.Y) Dump.println("CloseConnection close In="+In);//+5226I~
            }                                                      //+5226I~
		}
		catch (Exception e) {}
	}
}