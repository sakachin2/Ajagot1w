//*CID://+dateR~:                             update#=    2;       //~4C15I~
//*****************************************************************************//~4C15I~
//v1En 2014/12/15 Datagram send short string for japanese(partnername was cut)//~4C15I~
//*****************************************************************************//~4C15I~
package jagoclient.datagram;

import jagoclient.Dump;

import java.net.*;
import java.util.*;

/**
This class may be used to send a datagram to some internet
address or to receive datagrams from some internet address.
The datagram contains text lines, which are converted to
and from byte arrays.
*/

public class DatagramMessage 
{	byte B[];
	final int MAX=4096;
	int Used;
	public DatagramMessage ()
	{	B=new byte[MAX];
		Used=0;
	}
	/**
	Add a new line to the datagram.
	*/
	public void add (String s)
	{	try
		{	byte B1[]=s.getBytes();
            if (Dump.Y)	for (int ii=0;ii<B1.length;ii++) if (Dump.Y) Dump.println("Datagram:add B1=ii="+ii+"="+Integer.toHexString(B1[ii]&0xff));//+4C15R~
//  	    for (int i=0; i<s.length(); i++) B[Used+i]=B1[i];      //~4C15R~
    	    for (int i=0; i<B1.length; i++) B[Used+i]=B1[i];       //~4C15I~
//  		Used+=s.length();                                      //~4C15R~
    		Used+=B1.length;                                     //~4C15I~
			B[Used]=0; Used++;
            if (Dump.Y) Dump.println("DatagramMessage:add used="+Used+",str="+s);//~3501I~
		}
//		catch (Exception e) {}                                     //~3504R~
  		catch (Exception e)                                        //~3504I~
        {                                                          //~3504I~
        	Dump.println(e,"DatagramMessage add");                 //~3504I~
        }                                                          //~3504I~
	}
	/**
	Send the datagram to the specified address and port.
	*/
	public void send (String adr, int port)
	{	if (Used==0) return;
		try
		{	InetAddress ia=InetAddress.getByName(adr);
			DatagramPacket dp=new DatagramPacket(B,Used,ia,port);
			DatagramSocket ds=new DatagramSocket();
            if (Dump.Y) Dump.println("DatagramMessage:send addr="+adr+",port="+port+",msg="+new String(B,0,Used));//~3501I~
			ds.send(dp);
			ds.close();
		}
//		catch (Exception e) {}                                     //~3504R~
  		catch (Exception e)                                        //~3504I~
        {                                                          //~3504I~
        	Dump.println(e,"DatagramMessage send");                //~3504I~
        }                                                          //~3504I~
	}
	/**
	@return a vector of lines, containing the received datagram.
	*/
	public Vector receive (int port)
	{	Vector v=new Vector();
		try
		{	DatagramPacket dp=new DatagramPacket(B,MAX);
			DatagramSocket ds=new DatagramSocket(port);
            if (Dump.Y) Dump.println("DatagramMessage:before receive port="+port);//~3511I~
			ds.receive(dp);
			int l=dp.getLength(),i=0;
            if (Dump.Y) Dump.println("DatagramMessage:after receive len="+l);//~4C15R~
            if (Dump.Y)	for (int ii=0;ii<l;ii++) if (Dump.Y) Dump.println("DatagramMessage B=ii="+ii+"="+Integer.toHexString(B[ii]&0xff));//+4C15R~
			while (i<l)
			{	int j=i; while (B[i]!=0) i++;
				if (i>j) v.addElement(new String(B,j,i-j));
				else v.addElement(new String(""));
				i++;
			}
			ds.close();
		}
//		catch (Exception e) { return v; }                          //~3504R~
  		catch (Exception e)                                        //~3504I~
        {                                                          //~3504I~
        	Dump.println(e,"DatagramMessage receive");             //~3504I~
			return v;                                              //~3504I~
        }                                                          //~3504I~
		return v;
	}
}
