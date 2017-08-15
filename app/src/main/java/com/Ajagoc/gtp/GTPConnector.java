//*CID://+1AfmR~: update#=  125;                                   //~1AfmR~
//*********************************                                //~@@@1I~
//1Afm 2016/09/26 fuego;not genmove but reg_genmove is required for timelimit per move.//~1AfmI~
//v1Ba 2014/08/14 Canvas enqRequest callback for gtp callback gotMoved, to prevent deadlock wait on main thread//~v1BaI~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//@@@1 20110503:read stderr show by Toast                          //~@@@1R~
//*********************************                                //~@@@1I~
package com.Ajagoc.gtp;                                            //~v1B6R~

import java.io.*;
//import java.util.ArrayList;

import jagoclient.Dump;
//import static java.text.MessageFormat.format;                      //~v1B6I~

import com.Ajagoc.gtp.GTPConnection;                               //~v1B6R~
import com.Ajagoc.gtp.GtpClient;                                   //~v1B6I~
//import com.Ajagoc.gtp.GtpSynchronizer;                             //~v1B6I~
//import com.Ajagoc.gtp.GtpClient.TimeoutCallback;

class GTPCloser extends Thread
{	GTPConnector C;
	public GTPCloser (GTPConnector c)
	{	C=c;
    	if (Dump.Y) Dump.println("GTPCloser");                      //~1511I~
		start();
	}
	public void run ()
	{   try                                                        //~@@@1R~
        {                                                          //~1512I~
			C.destroy();                                           //~1512I~
        }                                                          //~1512I~
		catch (Exception e)                                        //~1512I~
        {                                                          //~1512I~
        	Dump.println(e,"GTPCloser run exception");             //~1512I~
        }                                                          //~1512I~
	}
}
////**********************************                               //~@@@1I~//~v1B6R~
////*trace stderr                                                    //~@@@1I~//~v1B6R~
////**********************************                               //~@@@1I~//~v1B6R~
//class GTPErrReader extends Thread                                  //~@@@1I~//~v1B6R~
//{                                                                  //~@@@1I~//~v1B6R~
//    GTPConnector C;                                                //~@@@1I~//~v1B6R~
//    InputStream  GTPErr;                                           //~@@@1I~//~v1B6R~
//    public GTPErrReader(GTPConnector c)                            //~@@@1I~//~v1B6R~
//    {   C=c;                                                       //~@@@1I~//~v1B6R~
//        if (Dump.Y) Dump.println("GTPErrReader");                  //~@@@1I~//~v1B6R~
//        GTPErr=C.Err;                                              //~@@@1I~//~v1B6R~
//        start();                                                   //~@@@1I~//~v1B6R~
//    }                                                              //~@@@1I~//~v1B6R~
//    int readErr()                                                  //~@@@1I~//~v1B6R~
//    {                                                              //~@@@1I~//~v1B6R~
//        int i=0;                                                   //~@@@1I~//~v1B6R~
//        try                                                        //~@@@1I~//~v1B6R~
//        {                                                          //~@@@1I~//~v1B6R~
//            i=GTPErr.read();                                       //~@@@1I~//~v1B6R~
//        }                                                          //~@@@1I~//~v1B6R~
//        catch(Exception e)                                         //~@@@1I~//~v1B6R~
//        {                                                          //~@@@1I~//~v1B6R~
//            if (Dump.Y) Dump.println(e,"GTPErrReader read");       //~@@@1I~//~v1B6R~
//            i=-1;                                                  //~@@@1I~//~v1B6R~
//        }                                                          //~@@@1I~//~v1B6R~
//        return i;                                                  //~@@@1I~//~v1B6R~
//    }                                                              //~@@@1I~//~v1B6R~
//    public void run ()                                             //~@@@1I~//~v1B6R~
//    {                                                              //~@@@1I~//~v1B6R~
////      char[] chs=new char[1];                                    //~@@@1R~//~v1B6R~
//        try                                                        //~@@@1I~//~v1B6R~
//        {                                                          //~@@@1I~//~v1B6R~
//            if (Dump.Y) Dump.println("GTPErrReader run");          //~@@@1R~//~v1B6R~
//            while (true)                                           //~@@@1R~//~v1B6R~
//            {                                                      //~@@@1R~//~v1B6R~
//                int ch=readErr();                                  //~@@@1R~//~v1B6R~
//                if (Dump.Y) Dump.println("GTPErrReader ch="+ch+",hex="+Integer.toHexString(ch));//~@@@1R~//~v1B6R~
//                if (ch<0)                                          //~@@@1R~//~v1B6R~
//                    return;                                        //~@@@1R~//~v1B6R~
////              chs[0]=(char)ch;                                   //~@@@1R~//~v1B6R~
//                if (C.errLinesz<GTPConnector.MAX_ERRLINESZ)        //~@@@1R~//~v1B6R~
//                {                                                  //~@@@1R~//~v1B6R~
//                    if (ch==0x0a)   //eol                          //~@@@1I~//~v1B6R~
//                    {                                              //~@@@1I~//~v1B6R~
//                        int rid=R.string.GtpErr_StdErrMsg;                             //~1514I~//~v1B6R~
//                        AjagoAlert.simpleAlertDialog(null/*Pcallback*/,null/*Ptitle*/,AG.resource.getString(rid)+":"+C.errLine.toString(),AjagoAlert.BUTTON_POSITIVE);//~v1B6R~
//                        C.errLinesz=0;                             //~@@@1I~//~v1B6R~
//                    }                                              //~@@@1I~//~v1B6R~
//                    else                                           //~@@@1I~//~v1B6R~
//                    {                                              //~@@@1I~//~v1B6R~
//                        C.errLine[C.errLinesz]=(byte)ch;           //~@@@1R~//~v1B6R~
//                        C.errLinesz++;                             //~@@@1R~//~v1B6R~
//                    }                                              //~@@@1I~//~v1B6R~
//                    if (Dump.Y) Dump.println("GTPErrReader line="+new String(C.errLine,0,C.errLinesz,"UTF-8"));//~@@@1R~//~v1B6R~
//                }                                                  //~@@@1R~//~v1B6R~
//            }                                                      //~@@@1R~//~v1B6R~
//        }                                                          //~@@@1I~//~v1B6R~
//        catch(Exception e)                                         //~@@@1I~//~v1B6R~
//        {                                                          //~@@@1I~//~v1B6R~
//            Dump.println(e,"GTPErrReader run");                    //~@@@1R~//~v1B6R~
//        }                                                          //~@@@1I~//~v1B6R~
//    }                                                              //~@@@1I~//~v1B6R~
//}                                                                  //~@@@1I~//~v1B6R~

/**
<P>This class opens a connection to an external program, and
communicates with using pipes.</P>
<P>The communication handling is a bit difficult to understand, for it
is done asynchronically in a separate thread. The external program
sends command, which are automatically handled or answered by this
class. To be able to do this, the class needs an GTPInterface
object, which provides the necessary informations to answer
questions (such as the board size), and handles commands (such as
a move). The interface is set with setGTPInterface(). </P>
<P> Of course, commands to be sent to the external program can be
sent via the methods send(), move() etc. directly and asynchronically.
</P>
<P> The GTP protocol is a binary protocol, which is not human
readable. Moreover, it has design flaws, when the programs
disagree about the board size, handicap etc. In our case, we expect
the external program to ask for these values. If it does not ask,
a problem will arise. The communication might fail in this case. </P>
*/

public class GTPConnector
  	implements Runnable                                            //~v1B6R~
{	String Program;
	Process P;
	InputStream In,Err;
	OutputStream Out;
//    boolean log;                                                 //~v1B6R~
    public static final int MAX_ERRLINESZ=200;                     //~@@@1I~
    int errLinesz;                                                 //~@@@1I~
    byte[] errLine=new byte[MAX_ERRLINESZ];                        //~@@@1R~
	public  GtpClient gtpclient;                                   //~v1B6R~
	public GTPConnection gtpconnection;                            //~v1B6R~
	public GoGui gogui;                                            //~v1B6I~
	private String strTimesettings;                                //~v1B6R~
    private int statusGameover;                                    //~v1B6I~
    public static final int GO_COMPUTER_RESIGNED=1;                //~v1B6R~
    private boolean reg_genmove;                                   //+1AfmR~
                                                                   //~@@@1I~
	/**
	@param program The GTP program's file name.
	*/	
//  public GTPConnector (String program)                           //~v1B6R~
    public GTPConnector (String program,GTPConnection Pconnection)  //~v1B6I~
	{	Program=program;
//  	if (Dump.Y)                                                //~v1B6R~
//            log=true;                                            //~v1B6R~
        gtpconnection=Pconnection;                                 //~v1B6I~
//      m_gtpCommand = gtpCommand;                                 //~v1B6I~
	}                                                              //~v1B6I~
    public GTPConnector (String program,GTPConnection Pconnection,boolean Preg_genmove)//~1AfmI~
	{                                                              //~1AfmI~
		Program=program;                                           //~1AfmI~
        gtpconnection=Pconnection;                                 //~1AfmI~
        reg_genmove=Preg_genmove;                                  //~1AfmI~
	}                                                              //~1AfmI~
	
	/**
	Connect to the GTP program and open pipe streams to it (mainly In
	and Out). Then start the communication thread.
	*/
	public void connect()                                          //~v1BaR~
		throws IOException
      {                                                            //~v1B6R~
//          Runtime R=Runtime.getRuntime();                        //~v1B6R~
//          P=R.exec(Program);                                     //~v1B6R~
//          In=P.getInputStream();                                 //~v1B6R~
//          Err=P.getErrorStream();                                //~v1B6R~
//          Out=P.getOutputStream();                               //~v1B6R~
////        GtpClient.IOCallback ioCallback=createIOCallback();    //~v1B6R~
////        try                                                    //~v1B6R~
////        {                                                      //~v1B6R~
////            GtpClient gtpclient=new GtpClient(In,Out,log,ioCallback);//~v1B6R~
////        }                                                      //~v1B6R~
////        catch (GtpError e)                                     //~v1B6R~
////        {                                                      //~v1B6R~
////            if (Dump.Y) Dump.println(e,"new GtpClient");       //~v1B6R~
////        }                                                      //~v1B6R~
		strTimesettings=gtpconnection.strTimesettings;             //~v1B6M~
        if (strTimesettings!=null && strTimesettings.equals(""))   //~v1B6M~
        	strTimesettings=null;                                  //~v1B6M~
        new Thread(this).start();                                  //~v1B6R~
//        new GTPErrReader(this);       //thread read stderr         //~@@@1I~//~v1B6R~
	}
	
//    /**                                                          //~v1B6R~
//    Send a single command to Out.                                //~v1B6R~
//    @param mine See the send() function for this.                //~v1B6R~
//    @param c The command number (see the answer() function).     //~v1B6R~
//    @param a A command argument.                                 //~v1B6R~
//    */                                                           //~v1B6R~
//    public synchronized void send (boolean mine, boolean his, int c, int a)//~v1B6R~
//        throws IOException                                       //~v1B6R~
//    {   int b1=(mine?1:0);                                       //~v1B6R~
//        b1|=(his?2:0);                                           //~v1B6R~
//        int b3=makeCommandByte1(c,a);                            //~v1B6R~
//        int b4=makeCommandByte2(a);                              //~v1B6R~
//        int checksum=computeChecksum(b1,b3,b4);                  //~v1B6R~
//        Out.write((byte)b1);                                     //~v1B6R~
//        Out.write((byte)checksum);                               //~v1B6R~
//        Out.write((byte)b3);                                     //~v1B6R~
//        Out.write((byte)b4);                                     //~v1B6R~
//        Out.flush();                                             //~v1B6R~
////      Dump.println("sent "+format(b1)+" "+format(checksum)       //~@@@1R~//~v1B6R~
//        if (Dump.Y) Dump.println("sent "+format(b1)+" "+format(checksum)//~1506R~//~v1B6R~
//            +" "+format(b3)+" "+format(b4)+" = "+c+","+a);       //~v1B6R~
//    }                                                            //~v1B6R~

//    byte makeCommandByte1 (int c, int a)                         //~v1B6R~
//    {   a=a&0x000003FF;                                          //~v1B6R~
//        return (byte)((0x0080)|(c<<4)|(a>>7));                   //~v1B6R~
//    }                                                            //~v1B6R~
//                                                                 //~v1B6R~
//    byte makeCommandByte2 (int a)                                //~v1B6R~
//    {   return (byte)((0x0080)|(a&0x0000007F));                  //~v1B6R~
//    }                                                            //~v1B6R~
//                                                                 //~v1B6R~
//    byte computeChecksum (int a, int b, int c)                   //~v1B6R~
//    {   int cs=lower(a)+lower(b)+lower(c);                       //~v1B6R~
//        return (byte)((0x0080)|cs);                              //~v1B6R~
//    }                                                            //~v1B6R~
	
//    int lower (int a)                                            //~v1B6R~
//    {   return a&0x007F;                                         //~v1B6R~
//    }                                                            //~v1B6R~
	
	boolean Sequence=false;
	
	/**
	Send a single command. Switches the Sequence bit with each
	command. The Sequence bit is resent in answers to the command.
	*/
//    public void send (int c, int a)                              //~v1B6R~
//        throws IOException                                       //~v1B6R~
//    {   Sequence=!Sequence;                                      //~v1B6R~
//        if (Dump.Y) Dump.println("GTPConnector send Sequence="+Sequence);//~1512I~//~v1B6R~
//        send(Sequence,His,c,a);                                  //~v1B6R~
//    }                                                            //~v1B6R~
	
	boolean End,MineAnswer=true,His=false;
	int Command,Argument;
	
//    /**                                                          //~v1B6R~
//    Waits for an answer by reading four bites and interpreting   //~v1B6R~
//    the input. The received command number is stored in the      //~v1B6R~
//    Command variable, and the argument in the Argument variable. //~v1B6R~
//    The sequence bit is also stored. The answer bit is checked   //~v1B6R~
//    if this is really an answer to my last command.              //~v1B6R~
//    */                                                           //~v1B6R~
//    public boolean getAnswer ()                                  //~v1B6R~
//        throws IOException                                       //~v1B6R~
//    {   int b1=read();                                           //~v1B6R~
//        while ((b1&0x0080)!=0) b1=In.read();                     //~v1B6R~
//        int cs=read();                                           //~v1B6R~
//        int b3=read();                                           //~v1B6R~
//        int b4=read();                                           //~v1B6R~
////      Dump.println("rcvd "+format(b1)+" "+format(cs)+            //~@@@1R~//~v1B6R~
//        if (Dump.Y) Dump.println("GTPConnector:getAnswer rcvd "+format(b1)+" "+format(cs)+//~1506R~//~@@@1R~//~v1B6R~
//            " "+format(b3)+" "+format(b4));                      //~v1B6R~
//        if ((b1&0x0080)!=0 || (cs&0x0080)==0 || (b3&0x0080)==0 || (b4&0x0080)==0)//~v1B6R~
//            return false;                                        //~v1B6R~
//        if (computeChecksum((byte)b1,(byte)b3,(byte)b4)!=(byte)cs) return false;//~v1B6R~
//        Command=(b3&0x0070)>>4;                                  //~v1B6R~
//        Argument=((b3&0x0007)<<7) |(b4&0x007F);                  //~v1B6R~
//        boolean his=((b1&0x0001)!=0);                            //~v1B6R~
//        if (his==His) return false;                              //~v1B6R~
//        His=his;                                                 //~v1B6R~
//        if (Command!=0)                                          //~v1B6R~
//        {   MineAnswer=(b1&0x0002)!=0;                           //~v1B6R~
//            if (Dump.Y) Dump.println("GTPConnector getAnswer MineAnswer="+MineAnswer+",Seq="+Sequence);//~1512I~//~v1B6R~
//            if (MineAnswer!=Sequence)                            //~v1B6R~
//            {                                                      //~1512I~//~v1B6R~
//                AjagoGTP.warning(1,errLine,errLinesz);             //~@@@1R~//~v1B6R~
//                errLinesz=0;                                       //~@@@1I~//~v1B6R~
//                throw new IOException("no answer");              //~v1B6R~
//            }                                                      //~1512I~//~v1B6R~
//        }                                                        //~v1B6R~
//        return true;                                             //~v1B6R~
//    }                                                            //~v1B6R~
//                                                                 //~v1B6R~
//    int read ()                                                  //~v1B6R~
//        throws IOException                                       //~v1B6R~
//    {   int i=In.read();                                           //~@@@1R~//~v1B6R~
////      if (i<0) AjagoGTP.warning(2,errLine,errLinesz);            //~@@@1R~//~v1B6R~
//        if (i<0)                                                   //~@@@1I~//~v1B6R~
//        {                                                          //~@@@1I~//~v1B6R~
//            AjagoGTP.warning(2,errLine,errLinesz);                 //~@@@1I~//~v1B6R~
//            errLinesz=0;                                           //~@@@1I~//~v1B6R~
//        }                                                          //~@@@1I~//~v1B6R~
//        if (i<0) throw new IOException("sudden death");          //~v1B6R~
//        return i;                                                //~v1B6R~
//    }                                                            //~v1B6R~
	
//    static String format (int i)                                 //~v1B6R~
//    {   String s="";                                             //~v1B6R~
//        for (int k=0; k<8; k++)                                  //~v1B6R~
//        {   if (i%2!=0) s="1"+s;                                 //~v1B6R~
//            else s="0"+s;                                        //~v1B6R~
//            i=i>>1;                                              //~v1B6R~
//        }                                                        //~v1B6R~
//        return s;                                                //~v1B6R~
//    }                                                            //~v1B6R~

//    /**                                                          //~v1B6R~
//    Send OK.                                                     //~v1B6R~
//    */                                                           //~v1B6R~
//    public void ok ()                                            //~v1B6R~
//        throws IOException                                       //~v1B6R~
//    {   send(Sequence,His,0,0x03FF);                             //~v1B6R~
//    }                                                            //~v1B6R~
	
	static public final int BLACK=2,WHITE=1;
	static public final int JAPANESE=1,SST=2;
	static public final int EVEN=1;
	
	/**
	Send a move.
	@param color The color of the move (BLACK,WHITE).
	@param pos The board position from 0 to 360.
	 * @throws GtpError 
	*/
	public void move(int color, int pos) throws GtpError           //~v1B6R~
//  	throws IOException                                         //~v1B6R~
    {                                                              //~v1B6R~
//        int c;                                                   //~v1B6I~
//        if (color==BLACK) c=0;                                   //~v1B6R~
//        else c=0x0200;                                           //~v1B6R~
//        send(5,c|pos);                                           //~v1B6R~
		if (statusGameover!=0)                                     //~v1B6I~
        {                                                          //~v1B6I~
        	gtpconnection.gameovered(statusGameover);              //~v1B6I~
            return;                                                //~v1B6I~
        }                                                          //~v1B6I~
        GoColor c=color2GoColor(color);                            //~v1B6R~
        GoPoint p=null;                                            //~v1B6I~
        if (pos!=0) //pass                                         //~v1B6I~
        	p=pos2GoPoint(pos);                                    //~v1B6I~
		Move m=Move.get(c,p);                                      //~v1B6I~
	    gtpconnection.gtpclient.sendPlay(m);                       //~v1B6R~
	}
//*****************************                                    //~v1B6I~
	
	/**
	Take back n moves.
	@param n Number of moves to take back.
	*/
//  public void takeback (int n)                                   //~v1B6R~
    public boolean takeback (int n)                                //~v1B6I~
//  	throws IOException                                         //~v1B6R~
		throws GtpError                                            //~v1B6I~
//  {   send(6,n);                                                 //~v1B6R~
    {                                                              //~v1B6I~
	    return gogui.m_gtp.m_gtpSynchronizer.undo(n);               //~v1B6R~
	}

	GTPInterface I=null;
	public void setGTPInterface (GTPInterface i) { I=i; }
	public void removeGTPInterface (GTPInterface i) { I=null; }

//    /**                                                          //~v1B6R~
//    <P>Automatically answer the incomming command. These commands may//~v1B6R~
//    be questions, others, moves or OK. </P>                      //~v1B6R~
//    <P> Questions are answered in the following way: The game parameters//~v1B6R~
//    are taken from the GTPInterface interface, which needs to provide//~v1B6R~
//    the necessary information and is set by setGTPInterface. I have//~v1B6R~
//    implemented ony answers to the necessary commands. Everything else//~v1B6R~
//    is answered with OK. </P>                                    //~v1B6R~
//    <P> A move or OK are sent directly to the GTPinterface. </P> //~v1B6R~
//    */                                                           //~v1B6R~
//    public synchronized void answer ()                           //~v1B6R~
//        throws IOException                                       //~v1B6R~
////  {   Dump.println(Command+" "+Argument);                        //~@@@1I~//~v1B6R~
//    {   if (Dump.Y) Dump.println("GTPConnector:answer cmd="+Command+",Argument="+Argument);//~1511R~//~v1B6R~
//        if (Command==3) // Questions                             //~v1B6R~
//        {   switch (Argument)                                    //~v1B6R~
//            {   case 7 : // question for rule set                //~v1B6R~
//                    if (I!=null) send(4,I.getRules());           //~v1B6R~
//                    else send(4,1);                              //~v1B6R~
//                    break;                                       //~v1B6R~
//                case 9 : // question for board size              //~v1B6R~
//                    if (I!=null) send(4,I.getBoardSize());       //~v1B6R~
//                    else send(4,19);                             //~v1B6R~
//                    break;                                       //~v1B6R~
//                case 8 : // question for handicap                //~v1B6R~
//                    if (I!=null) send(4,I.getHandicap());        //~v1B6R~
//                    else send(4,1);                              //~v1B6R~
//                    break;                                       //~v1B6R~
//                case 11 : // question for board color of myself  //~v1B6R~
//                    if (I!=null) send(4,I.getColor());           //~v1B6R~
//                    else send(4,WHITE);                          //~v1B6R~
//                    break;                                       //~v1B6R~
//                default : send(4,0); break;                      //~v1B6R~
//            }                                                    //~v1B6R~
//        }                                                        //~v1B6R~
//        else if (Command==4) // Other commands                   //~v1B6R~
//        {   if (I!=null) I.gotAnswer(Argument);                  //~v1B6R~
//        }                                                        //~v1B6R~
//        else if (Command==5) // Got move                         //~v1B6R~
//        {   ok(); // acknowledge                                 //~v1B6R~
//            if (I!=null)                                         //~v1B6R~
//            {   int pos=Argument&0x01FF;                         //~v1B6R~
//                if ((Argument&0x0200)!=0) I.gotMove(WHITE,pos);  //~v1B6R~
//                else I.gotMove(BLACK,pos);                       //~v1B6R~
//            }                                                    //~v1B6R~
//        }                                                        //~v1B6R~
//        else if (Command==0) // OK                               //~v1B6R~
//        {   if (I!=null) I.gotOk();                              //~v1B6R~
////          Dump.println("Got OK");                                //~@@@1I~//~v1B6R~
//            if (Dump.Y) Dump.println("Got OK");                    //~1506R~//~v1B6R~
//        }                                                        //~v1B6R~
//        else ok();                                               //~v1B6R~
//    }                                                            //~v1B6R~
//                                                                 //~v1B6R~
    /**                                                            //~v1B6R~
    Start the IO thread. I.e., continually get something from the  //~v1B6R~
    program, and auto treat it in the answer() function.           //~v1B6R~
    */                                                             //~v1B6R~
    public void run()                                              //~v1B6R~
    {   try                                                        //~v1B6R~
        {                                                          //~v1B6R~
//            while (true)                                         //~v1B6R~
//            {   getAnswer();                                     //~v1B6R~
//                answer();                                        //~v1B6R~
//            }                                                    //~v1B6R~
          	createGoGui();                                         //~v1B6I~
        }                                                          //~v1B6R~
        catch (Exception e)                                        //~1511R~//~v1B6R~
        {                                                          //~1511I~//~v1B6R~
            Dump.println(e,"GTPConnector run exception");          //~1511I~//~v1B6R~
        }                                                          //~1511I~//~v1B6R~
    }                                                              //~v1B6R~
	
	public void doclose ()
	{	new GTPCloser(this);
	}

	/**
	Destroy the program and close streams.
	*/	
	public void destroy ()
    {                                                              //~v1B6R~
//        try                                                      //~v1B6I~
//        {   P.destroy();                                         //~v1B6R~
//            P.waitFor();                                         //~v1B6R~
//            In.close();                                          //~v1B6R~
//            Out.close();                                         //~v1B6R~
//            Err.close();                                         //~v1B6R~
//        }                                                        //~v1B6R~
//        catch (Exception e)                                        //~1511R~//~v1B6R~
//        {                                                          //~1511I~//~v1B6R~
//            Dump.println(e,"GTPConnector.destroy exception");      //~1511I~//~v1B6R~
//        }                                                          //~1511I~//~v1B6R~
        if (gogui!=null)                                           //~v1B6I~
            gogui.actionQuit();                                    //~v1B6I~
	}

//    /**                                                          //~v1B6R~
//    Test program "main", which tries to open a connection to gnugo.exe//~v1B6R~
//    and communicates with the process until it dies.             //~v1B6R~
//    */                                                           //~v1B6R~
//    static public void main (String args[])                      //~v1B6R~
//    {   Dump.terminal(true);                                     //~v1B6R~
//        try                                                      //~v1B6R~
//        {   GTPConnector c=new GTPConnector("gnugo.exe");        //~v1B6R~
//            c.connect();                                         //~v1B6R~
//            c.P.waitFor();                                       //~v1B6R~
//        }                                                        //~v1B6R~
//        catch (Exception e)                                      //~v1B6R~
//        {   System.out.println(e);                               //~v1B6R~
//        }                                                        //~v1B6R~
//    }                                                            //~v1B6R~
//*****************************                                    //~v1B6I~
    GoColor color2GoColor(int color)                               //~v1B6R~
    {                                                              //~v1B6I~
    	if (color==BLACK)                                          //~v1B6I~
    		return GoColor.BLACK;                                  //~v1B6I~
    	return GoColor.WHITE;                                      //~v1B6I~
    }                                                              //~v1B6I~
//**************************************************************   //~v1B6I~
//*from move                                                       //~v1B6I~
//*pos(botom-left start) to cordinate  of bottom-left origin       //~v1B6I~
//**************************************************************   //~v1B6I~
    private GoPoint pos2GoPoint(int Ppos)                          //~v1B6R~
    {                                                              //~v1B6R~
        int boardSize=getBoardSize();                              //~v1B6R~
        int i=(Ppos-1)%boardSize;                                  //~v1B6R~
        int j=(Ppos-1)/boardSize;                                  //~v1B6R~
        if (Dump.Y) Dump.println("pos2Point pos="+Ppos+"=>i="+i+",j="+j);//~v1B6R~
        return GoPoint.get(i,j);                                   //~v1B6R~
    }                                                              //~v1B6R~
//**************************************************************   //~v1B6R~
//*cordinate to position by bottom-left origin(start=1)            //~v1B6I~
//**************************************************************   //~v1B6I~
    private int point2Pos(GoPoint Ppoint)                              //~v1B6I~
    {                                                              //~v1B6I~
    	int boardSize=getBoardSize();                              //~v1B6I~
    	int i=Ppoint.getX();                                       //~v1B6R~
    	int j=Ppoint.getY();                                       //~v1B6R~
    	int pos=j*boardSize+i+1;                                   //~v1B6R~
        if (Dump.Y) Dump.println("point2Pos pos="+pos+"<==i="+i+",j="+j);//~v1B6R~
        return pos;                                                //~v1B6I~
    }                                                              //~v1B6I~
//*****************************                                    //~v1B6I~
    public int getBoardSize()                                      //~v1B6I~
    {                                                              //~v1B6I~
    	int boardSize=gtpconnection.BoardSize;                     //~v1B6I~
    	return  boardSize;                                         //~v1B6R~
    }                                                              //~v1B6I~
    //*************************************************            //~v1B6I~
    private void createGoGui()                                     //~v1B6R~
    {                                                              //~v1B6I~
    	int move=0;                                                //~v1B6I~
    	boolean verbose=gtpconnection.Verbose;                     //~v1B6R~
    	boolean initComputerColor=true;                            //~v1B6I~
    	boolean computerBlack;                                     //~v1B6I~
    	boolean computerWhite;                                     //~v1B6I~
        if (gtpconnection.MyColor==BLACK)                          //~v1B6I~
        {                                                          //~v1B6I~
        	computerBlack=false;                                   //~v1B6I~
        	computerWhite=true;                                    //~v1B6I~
        }                                                          //~v1B6I~
        else                                                       //~v1B6I~
        {                                                          //~v1B6I~
        	computerBlack=true;                                    //~v1B6I~
        	computerWhite=false;                                   //~v1B6I~
        }                                                          //~v1B6I~
    	String gtpFile=null;                                       //~v1BaR~
    	String gtpCommand="";                                      //~v1BaR~
        File analyzeCommandsFile=null;                             //~v1BaR~
//        try   //GoGui has catch block                            //~v1B6I~//~v1BaR~
//        {                                                          //~v1B6I~//~v1BaR~
    		gogui=new GoGui(this,Program,move,strTimesettings,verbose,initComputerColor,//~v1B6R~
//  				computerBlack,computerWhite,gtpFile,gtpCommand,analyzeCommandsFile);//+1AfmR~
    				computerBlack,computerWhite,gtpFile,gtpCommand,analyzeCommandsFile,reg_genmove);//+1AfmI~
//        }                                                          //~v1B6I~//~v1BaR~
//        catch(Exception e)                                         //~v1B6I~//~v1BaR~
//        {                                                          //~v1B6I~//~v1BaR~
//            Dump.println(e,"createGoGui");                       //~v1BaR~
//        }                                                          //~v1B6I~//~v1BaR~
    }                                                              //~v1B6I~
    //*************************************************            //~v1B6I~
    public Komi getKomi()                                          //~v1B6R~
    {                                                              //~v1B6I~
//        try                                                      //~v1B6R~
//        {                                                        //~v1B6R~
//      	if (strKomi!=null)                                     //~v1B6R~
//              return Komi.parseKomi(strKomi);                    //~v1B6R~
//            return Komi.parseKomi(s);                            //~v1B6R~
//        }                                                        //~v1B6R~
//        catch (InvalidKomiException e)                           //~v1B6R~
//        {                                                        //~v1B6R~
//        }                                                        //~v1B6R~
//        return new Komi((double)KOMI_DEFAULTV);                  //~v1B6R~
        int intkomi=gtpconnection.Komi;                            //~v1B6I~
        double komi=(intkomi==0 ? 0.0 : intkomi+0.5);              //~v1B6I~
        return new Komi(komi);                                     //~v1B6I~
    }                                                              //~v1B6I~
	public void gotOk()                                                 //~v1B6I~
    {                                                              //~v1B6I~
		if (Dump.Y) Dump.println("GTPConnector hotOk");            //~v1B6I~
		if (I!=null)  //GTPConnection                              //~v1B6I~
			 I.gotOk();                                            //~v1B6I~
    }                                                              //~v1B6R~
//*****************************************************************//~v1B6I~
//*from GoGui:computerMoved                                        //~v1B6I~
//*to GTPConnection:gotMove                                        //~v1B6I~
//*****************************************************************//~v1B6I~
	private class CanvasCallbackParm{                              //~v1BaI~
    	public GoPoint point;                                      //~v1BaI~
        public boolean resign;                                     //~v1BaI~
        public int funcid;                                         //~v1BaI~
        public CanvasCallbackParm(int Pfuncid,GoPoint Ppoint,boolean Presign)//~v1BaI~
        {                                                          //~v1BaI~
        	funcid=Pfuncid;point=Ppoint;resign=Presign;            //~v1BaI~
        }                                                          //~v1BaI~
    }                                                              //~v1BaI~
    private final static int FUNCID_GOTMOVED=1;                    //~v1BaI~
    public void gotMoved(GoPoint Ppoint,boolean Presign)           //~v1B6R~
    {                                                              //~v1B6I~
    	CanvasCallbackParm p=new CanvasCallbackParm(FUNCID_GOTMOVED,Ppoint,Presign);//~v1BaI~
        gtpconnection.F.requestCallback(p);                        //~v1BaI~
    }                                                              //~v1BaI~
    public void canvasCallback(Object Pobj)                        //~v1BaI~
    {                                                              //~v1BaI~
    	CanvasCallbackParm parm=(CanvasCallbackParm)Pobj;          //~v1BaI~
    	GoPoint point=parm.point;                                  //~v1BaI~
        boolean resign=parm.resign;                                //~v1BaI~
        int funcid=parm.funcid;                                    //~v1BaI~
        if (funcid==FUNCID_GOTMOVED)                               //~v1BaI~
        	callbackGotMoved(point,resign);                        //~v1BaI~
    }                                                              //~v1BaI~
    public void callbackGotMoved(GoPoint Ppoint,boolean Presign)   //~v1BaI~
    {                                                              //~v1BaI~
    	int pos;
    	if (Presign)                                               //~v1B6I~
        {                                                          //~v1B6I~
			statusGameover=GO_COMPUTER_RESIGNED;                   //~v1B6R~
        	gtpconnection.resign();                                 //~v1B6I~
            return;                                                //~v1B6I~
        }                                                          //~v1B6I~
    	if (Ppoint==null)//pass                                    //~v1B6R~
        	pos=-1;                                                //~v1B6I~
        else                                                       //~v1B6I~
        	pos=point2Pos(Ppoint);                                  //~v1B6I~
    	if (I!=null)                                               //~v1B6I~
        {                                                          //~v1B6I~
        	if (gtpconnection.MyColor==BLACK)                      //~v1B6R~
				I.gotMove(WHITE,pos);                              //~v1B6I~
            else                                                   //~v1B6R~
                I.gotMove(BLACK,pos);                              //~v1B6I~
        }
    }//~v1B6I~
}
