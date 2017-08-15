//*CID://+1Ab9R~:                                   update#=    9; //+1Ab9R~
//***********************************************                  //~@@@1I~
//2015/07/23 //1Ab9 2015/05/09 Dump byte[]                         //+1Ab9I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing, (Dump.T) for UiThread//~1A6AI~
//1B0g 130430 catch OutOfMemoryError                               //~1B0gI~
//1A30 2013/04/06 kif,ki2 format support                           //~1A30I~
//1075:121207 control dumptrace by manifest debuggable option      //~v107I~
//@@@1 prepend timestamp/threadid                                  //~@@@1I~
//     exception dump                                              //~@@@1I~
//     optional dump by ajago menu option                          //~@@@1I~
//***********************************************                  //~@@@1I~
package jagoclient;

import java.io.*;
import java.util.*;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoMenu;
import com.Ajagoc.AjagoProp;
import com.Ajagoc.AjagoUtils;

/**
A class to generate debug information in a dump file.
It is a class with all static members.
*/

public class Dump
{	static PrintWriter Out=null;
	static boolean Terminal=false;                                           //~1228I~
	static boolean noTid=false;                                    //~1A30I~
	static public boolean exceptionOnly=false;                     //~1507R~
	static public boolean Y;   //test before call Dump to avoid memory for parameter String//~1506I~
	static public boolean C;   //split Canvas drawing              //~1A6AI~
	static public boolean T;   //split UiTHread trace              //~1A6AI~
	/** 
	Open a dump file. If this is not called there will be no
	file dumps.
	*/
	public static void openEx (String file)                        //~1504I~
	{                                                              //~1504I~
		open(file);                                                //~1504I~
      if (AG.isDebuggable                                          //~v107R~
      && Out!=null                                                 //~v107R~
      && AjagoProp.getPreference(AjagoMenu.DEBUGTRACE_CFGKEY,false))//~v107R~
      {                                                            //~v107R~
        Y=true;                                                    //~v107R~
      }                                                            //~v107R~
      else                                                         //~v107R~
      {                                                            //~v107R~
    	exceptionOnly=true;                                        //~1506M~
        Y=false;//dont call Dump except case of Exceoption         //~1506I~
      }                                                            //~v107R~
    }                                                              //~1504I~
	public static void open (String file)
	{                                                              //~1329R~
    	exceptionOnly=false;//not exception only                   //~1506I~
        if (file==null)                                            //~1A30I~
        {	                                                       //~1A30I~
        	Out=null;                                              //~1A30I~
            Terminal=true;                                         //~1A30I~
            noTid=true;                                            //~1A30I~
            Y=true;                                                //~1A30I~
            return;                                                //~1A30I~
        }                                                          //~1A30I~
    	if (Out!=null)                                             //~1329I~
        	return;                                                //~1329I~
		try                                                        //~1329I~
		{                                                          //~1227R~
//@@@@  	Out=new PrintWriter(new FileOutputStream(file),true);  //~1227I~
//      	OutputStream out=AG.context.openFileOutput(file,Context.MODE_PRIVATE);//~1227I~//~1313R~
        	OutputStream out=AjagoProp.openOutputData("",file);	//SD card//~1329R~
        	if (out!=null)
        	{//~1313R~
        		Out=new PrintWriter(new OutputStreamWriter(out,"UTF-8"),true/*autoFlash*/);//~1227I~//~1309R~
        		Out.println("Locale: "+Locale.getDefault()+"\n");
                Y=true; //call Dump                                //~1506I~
                Terminal=true;                                     //~1511I~
        	}
		}
		catch (IOException e)
		{	Out=null;
            System.out.println("Dump open failed");                //~1329I~
		}
	}
	/** dump a string in a line */
	public synchronized static void println (String s)             //~1305R~
	{                                                              //~1228R~
    	if (exceptionOnly)                                         //~1504I~
        	return;                                                //~1504I~
                                                                   //~1504I~
	    String tidts=null,tid;                                     //~v@@@I~//~1A30I~
//		if (Out!=null) Out.println(s);                             //~1228I~
  		if (Out!=null)                                             //~1425R~
        {                                                          //~1425I~
	    	tidts=AjagoUtils.getThreadTimeStamp();          //~1425I~
			Out.println(tidts+":"+s);                              //~1425I~
        }                                                          //~1425I~
//		if (Terminal) System.out.println(s);                       //~1228R~
  		if (Terminal)                                              //~1511R~
        {                                                          //~1425I~
          if (noTid)                                               //~1A30I~
          	tid="";                                                //~1A30I~
          else                                                     //~1A30I~
          {                                                        //~1A30I~
//      	String tid=AjagoUtils.getThreadId();                   //~1425I~//~1A30R~
        	if (tidts==null)                                       //~v@@@I~//~1A30I~
            	tid=AjagoUtils.getThreadId();                   //~1425I~//~v@@@R~//~1A30I~
            else                                                   //~v@@@I~//~1A30I~
            	tid=tidts;                                         //~v@@@I~//~1A30I~
          }                                                        //~1A30I~
		 	System.out.println(tid+":"+s);                         //~1425I~
        }                                                          //~1425I~
	}
    private static void byte2string(StringBuffer Psb,int Poutoffs,byte[] Pbytes,int Pinpoffs,int Plen)//+1Ab9I~
    {                                                              //+1Ab9I~
    	String s;                                                  //+1Ab9I~
		try {                                                      //+1Ab9I~
			s = new String(Pbytes,Pinpoffs,Plen,"US-ASCII");       //+1Ab9I~
		} catch (UnsupportedEncodingException e) {                 //+1Ab9I~
			e.printStackTrace();                                   //+1Ab9I~
            return;                                                //+1Ab9I~
		}                                                          //+1Ab9I~
        for (int ii=0;ii<16;ii++)                                  //+1Ab9I~
        {                                                          //+1Ab9I~
            if (ii<Plen)                                           //+1Ab9I~
            {                                                      //+1Ab9I~
        		char ch=s.charAt(ii);                              //+1Ab9I~
//              System.out.println("ch="+ch);                      //+1Ab9I~
            	if (ch<' '|| ch>=0x7f)                             //+1Ab9I~
	            	Psb.setCharAt(ii+Poutoffs,'.');                //+1Ab9I~
            	else                                               //+1Ab9I~
            		Psb.setCharAt(ii+Poutoffs,ch);                 //+1Ab9I~
            }                                                      //+1Ab9I~
            else                                                   //+1Ab9I~
	            Psb.setCharAt(ii+Poutoffs,' ');                    //+1Ab9I~
        }                                                          //+1Ab9I~
    }                                                              //+1Ab9I~
//                                       00 00 00 00 - 00 00 00 00 - 00 00 00 00 - 00 00 00 00 - *0123456789abcdef*//+1Ab9I~
    private static final String dumpfmt="            -             -             -               *                *";//+1Ab9I~
	public synchronized static void println (String Ptitle,byte[] Pbytes)//+1Ab9I~
    {                                                              //+1Ab9I~
    	println(Ptitle,Pbytes,0,Pbytes.length);                    //+1Ab9I~
    }                                                              //+1Ab9I~
	public synchronized static void println (String Ptitle,byte[] Pbytes,int Poffs,int Plen)//+1Ab9I~
	{                                                              //+1Ab9I~
    	if (exceptionOnly)                                         //+1Ab9I~
        	return;                                                //+1Ab9I~
	    println(Ptitle+" : size="+Pbytes.length+"=0x"+Integer.toHexString(Pbytes.length)+",offs=0x"+Integer.toHexString(Poffs)+",len=0x"+Integer.toHexString(Plen));//+1Ab9I~
        StringBuffer sb=new StringBuffer(dumpfmt);                 //+1Ab9I~
        int intch,intch2;                                          //+1Ab9I~
        int fillsz=((Plen+15)/16)*16;                              //+1Ab9I~
        int lastinpoffs=0;                                         //+1Ab9I~
        for (int ii=0,offs=0;ii<fillsz;ii++,offs+=3)               //+1Ab9I~
        {                                                          //+1Ab9I~
        	if (ii!=0)                                             //+1Ab9I~
            {                                                      //+1Ab9I~
                if (ii%16==0)                                      //+1Ab9I~
                {                                                  //+1Ab9I~
                    byte2string(sb,(3*4+2)*4+1,Pbytes,Poffs+ii-16,16);//+1Ab9I~
                    String s=sb.toString();                        //+1Ab9I~
                    if (Out!=null)                                 //+1Ab9I~
                        Out.println(s);                            //+1Ab9I~
                    if (Terminal)                                  //+1Ab9I~
                        System.out.println(s);                     //+1Ab9I~
	                offs=0;                                        //+1Ab9I~
                    lastinpoffs=ii;                                //+1Ab9I~
                }                                                  //+1Ab9I~
                else                                               //+1Ab9I~
                if (ii%4==0)                                       //+1Ab9I~
                    offs+=2;                                       //+1Ab9I~
            }                                                      //+1Ab9I~
            if (ii<Plen)                                           //+1Ab9I~
            {                                                      //+1Ab9I~
            	intch=(Pbytes[Poffs+ii] & 0xff);                   //+1Ab9I~
            	intch2=intch/16;                                   //+1Ab9I~
                if (intch2<10)                                     //+1Ab9I~
            		sb.setCharAt(offs,(char) ('0'+intch2));        //+1Ab9I~
                else                                               //+1Ab9I~
            		sb.setCharAt(offs,(char) ('a'+intch2-10));     //+1Ab9I~
            	intch2=intch%16;                                   //+1Ab9I~
                if (intch2<10)                                     //+1Ab9I~
            		sb.setCharAt(offs+1,(char) ('0'+intch2));      //+1Ab9I~
                else                                               //+1Ab9I~
            		sb.setCharAt(offs+1,(char) ('a'+intch2-10));   //+1Ab9I~
            }                                                      //+1Ab9I~
            else                                                   //+1Ab9I~
            {                                                      //+1Ab9I~
            	sb.setCharAt(offs,' ');                            //+1Ab9I~
            	sb.setCharAt(offs+1,' ');                          //+1Ab9I~
            }                                                      //+1Ab9I~
        }                                                          //+1Ab9I~
        if (lastinpoffs<Plen)	//remaining char dump              //+1Ab9I~
        {                                                          //+1Ab9I~
	    	byte2string(sb,(3*4+2)*4+1,Pbytes,lastinpoffs,Plen-lastinpoffs);//+1Ab9I~
            String s=sb.toString();                                //+1Ab9I~
            if (Out!=null)                                         //+1Ab9I~
            	Out.println(s);                                    //+1Ab9I~
            if (Terminal)                                          //+1Ab9I~
            	System.out.println(s);                             //+1Ab9I~
        }                                                          //+1Ab9I~
	}                                                              //+1Ab9I~
//** Exception Dump                                                //~1309I~
	public synchronized static void println(Exception e,String s)  //~1309I~
	{                                                              //~1309I~
//      String tidts=AjagoUtils.getThreadTimeStamp();              //~1425I~//~1A30R~
 	    String tidts;                                              //~1A30I~
      	if (noTid)                                                 //~1A30I~
          	tidts="";                                              //~1A30I~
      	else                                                       //~1A30I~
	    	tidts=AjagoUtils.getThreadTimeStamp();                      //~1A30I~
        System.out.println(tidts+":"+s);                                 //~1309I~//~1425R~
        e.printStackTrace();                                   //~1309I~//~1329I~
  		if (Out!=null)                                             //~1309I~
        {                                                          //~1309I~
			Out.println(tidts+":"+s+" Exception:"+e.toString());   //~1425R~
            StringWriter sw=new StringWriter();                    //~1311I~
            PrintWriter pw= new PrintWriter(sw);                   //~1311I~
            e.printStackTrace(pw); 
			Out.println(tidts+":"+sw.toString());                  //~1425R~
			Out.flush(); 
			pw.close();//~1309I~
        }                                                          //~1309I~
	}                                                              //~1309I~
    //**************************************************************//~1B0gI~
	public synchronized static void println(OutOfMemoryError e,String s)//~1B0gI~
	{                                                              //~1B0gI~
	    String tidts=AjagoUtils.getThreadTimeStamp();              //~1B0gI~
        System.out.println(tidts+":"+s);                           //~1B0gI~
        e.printStackTrace();                                       //~1B0gI~
  		if (Out!=null)                                             //~1B0gI~
        {                                                          //~1B0gI~
			Out.println(tidts+":"+s+" Exception:"+e.toString());   //~1B0gI~
            StringWriter sw=new StringWriter();                    //~1B0gI~
            PrintWriter pw= new PrintWriter(sw);                   //~1B0gI~
            e.printStackTrace(pw);                                 //~1B0gI~
			Out.println(tidts+":"+sw.toString());                  //~1B0gI~
			Out.flush();                                           //~1B0gI~
			pw.close();                                            //~1B0gI~
        }                                                          //~1B0gI~
	}                                                              //~1B0gI~
	/** dump a string without linefeed */
	public static void print (String s)
	{                                                              //~1504R~
    	if (exceptionOnly)                                         //~1504I~
        	return;                                                //~1504I~
		if (Out!=null) Out.print(s);                               //~1504I~
		if (Terminal) System.out.print(s);
	}
	/** close the dump file */
	public static void close ()
	{                                                              //~1503R~
		if (Out!=null)                                             //~1503I~
        {                                                          //~1503I~
        	println("Dump closed");                               //~1503I~
			Out.close();                                           //~1503I~
        }                                                          //~1503I~
    	Out=null;                                                  //~1425I~
	}
	/** determine terminal dumps or not */
	public static void terminal (boolean flag)
	{	Terminal=flag;
	}
//******************************                                   //~1507I~
//*chk Ajago option:debugtrace after go.cfg was read               //~1507I~
//******************************                                   //~1507I~
	public static void checkOption()                               //~1507I~
	{                                                              //~1507I~
        boolean flag=Global.getParameter(AjagoMenu.DEBUGTRACE_CFGKEY,false);//~1507I~
        setOption(flag);                                            //~1507I~
	}                                                              //~1507I~
	public static void setOption(boolean Pflag)                    //~1507I~
	{                                                              //~1507I~
        println("DumpOption Changed to"+Pflag);                    //~1507I~
        Y=Pflag;	//debug dump                                   //~1507I~
        exceptionOnly=!Pflag;                                      //~1507I~
        AjagoProp.putPreference(AjagoMenu.DEBUGTRACE_CFGKEY,Pflag);//~v107R~
	}                                                              //~1507I~
}
