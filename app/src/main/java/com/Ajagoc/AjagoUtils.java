//*CID://+1Ac0R~: update#= 144;                                    //~1A86R~//~1A8bR~//+1Ac0R~
//**********************************************************************//~1107I~
//2015/07/23 //1Ac0 2015/07/06 for mutual exclusive problem of IP and wifidirect;try to use connectivityManager API//+1Ac0I~
//1A8fk2015/03/01 display remote IP address                        //~1A8bI~
//1A8bk2015/02/28 (BUG)old pertner communication fail by multiple IP address//~1A8bI~
//1A86 2015/02/26 get IPAddr by MacAddr                            //~1A86I~
//1A6a 2015/02/10 NFC+Wifi support
//1A67 2015/02/05 (kan)                                            //~1A67I~
//v1E7 2014/12/11 (Asgts)//1A4A 2014/12/09 function to show youtube//~v1E7I~
//v1Dn 2014/11/14 change filename timestamp to yyMMDDHHmm(drop yy and ss)//~v1DnI~
//v1Dh 2014/11/12 add predefined parameter settion for pach/fuego match-settiong dialog//~v1DhI~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1b6I~
//1B10 130501 display ip addr on start server menu item            //~1B10I~
//1102:130123 bluetooth became unconnectable after some stop operation//~v110I~
//1075:121207 control dumptrace by manifest debuggable option      //~v105I~
//1063:121124 menu to display ip address for pertner connection    //~v106I~
//**********************************************************************//~1107I~//~v106M~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~


import java.net.Inet6Address;
import java.net.InetAddress;                                       //~v106R~
import java.net.NetworkInterface;                                  //~v106R~
import java.net.Socket;
import java.util.Enumeration;                                      //~v106I~
import java.text.SimpleDateFormat;
import java.util.Date;

import jagoclient.Dump;
import android.content.Context;                                    //~v107R~
import android.content.Intent;
import android.content.pm.ApplicationInfo;                         //~v107R~
import android.content.pm.PackageManager;                          //~v107R~
import android.content.pm.PackageManager.NameNotFoundException;    //~v107R~
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.Ajagoc.AG;

//**********************************************************************//~1107I~
public class AjagoUtils                                            //~1309R~
{                                                                  //~0914I~
    public static final String	IPA_NA="N/A";                      //~1A05I~//~1A86I~
//    static boolean finished=false;                                 //~1309I~//~v110R~
//**********************************                               //~v110I~
//*from Alert,replyed Yes                                          //~v110I~
//**********************************                               //~v110I~
	public static void stopFinish()		//from Alert by Stop:Yes   //~v110I~
    {                                                              //~v110I~
    	if (Dump.Y) Dump.println("AjagoUtils stopFinish");         //~v110I~
        try                                                        //~v110I~
        {                                                          //~v110I~
        	AG.ajagoc.destroyClose();                               //~v110I~
        }                                                          //~v110I~
        catch (Exception e)                                        //~v110I~
        {                                                          //~v110I~
        	Dump.println(e,"stopFinish");                          //~v110I~
            finish();                                              //~v110I~
        }                                                          //~v110I~
    }                                                              //~v110I~
//**********************************                               //~1211I~
	public static void exit(int Pexitcode)                         //~1309I~
    {                                                              //~1309I~
    	if (Dump.Y) Dump.println("AjagoUtils exit() code="+Pexitcode);//~1309I~//~1506R~//~v110R~
		finish();                                                  //~1309R~
    }                                                              //~1309I~
	public static void exit(int Pexitcode,boolean Pkill)           //~1329I~
    {                                                              //~1329I~
    	if (Dump.Y) Dump.println("AjagoUtils kill exit() code="+Pexitcode);//~1506R~//~v110R~
        if (Pkill)                                                 //~1329I~
        {                                                          //~1503I~
    		if (Dump.Y) Dump.println("AjagoUtils kill exit() killProcess");//~v110I~
            Dump.close();                                          //~1503I~
//          System.exit(Pexitcode);                                //~1329I~//~v110R~
			android.os.Process.killProcess(android.os.Process.myPid());//~v110I~
        }                                                          //~1503I~
//      exit(Pexitcode);                                           //~1329I~//~v110R~
    }                                                              //~1329I~
//    public static void finish()                                         //~1309I~//~v110R~
//    {                                                              //~1309I~//~v110R~
//        if (Dump.Y) Dump.println("AjagoUtils finish requested "+finished);//~1506R~//~v110R~
//        if (finished)                                              //~1309M~//~v110R~
//            return ;                                               //~1309M~//~v110R~
//        AG.ajagoc.finish();                                        //~1309I~//~v110R~
//        if (Dump.Y) Dump.println("AjagoUtils context finish request");//~1506R~//~v110R~
//        sleep(1200);//wait subtread termination  1.2sec            //~1503R~//~v110R~
//        if (Dump.Y) Dump.println("AjagoUtils context finish request after sleep 1200");//~1506R~//~v110R~
//        Dump.close();                                              //~1503I~//~v110R~
//        finished=true;                                             //~1309I~//~v110R~
//    }                                                              //~1309I~//~v110R~
//**********************************                               //~@@@@I~//~v110I~
//*from Alert,replyed Yes                                          //~@@@@I~//~v110I~
//**********************************                               //~@@@@I~//~v110I~
    public static void finish()                                    //~@@@@I~//~v110I~
    {                                                              //~@@@@I~//~v110I~
    	if (Dump.Y) Dump.println("AjagoUtils finish");             //~@@@@I~//~v110I~
//        closeFinish();                                             //~@@@@I~//~v110R~
        AG.ajagoc.finish();	//schedule onDestroy                   //~v110I~
    }                                                              //~@@@@I~//~v110I~
//    public static void closeFinish()                               //~@@@@I~//~v110R~
//    {                                                              //~@@@@I~//~v110R~
//        if (Dump.Y) Dump.println("AjagoUtils closeFinish finished="+finished);//~@@@@M~//~v110R~
//        if (finished)                                              //~@@@@I~//~v110R~
//            return ;                                               //~@@@@I~//~v110R~
//        AG.ajagoc.destroyClose();   //close stream the finish      //~@@@@I~//~v110R~
//    }                                                              //~@@@@I~//~v110R~
//    public static void closedFinish()                              //~@@@@I~//~v110R~
//    {                                                              //~@@@@I~//~v110R~
//        if (Dump.Y) Dump.println("AjagoUtils closedFinish finished="+finished);//~@@@@I~//~v110R~
//        if (finished)                                              //~@@@@I~//~v110R~
//            return ;                                               //~@@@@I~//~v110R~
//        AG.ajagoc.finish(); //schedule onDestroy                   //~@@@@I~//~v110R~
//        if (Dump.Y) Dump.println("AjagoUtils context finish request");//~@@@@I~//~v110R~
//        sleep(200);//wait subtread termination  1.2sec             //~@@@@R~//~v110R~
//        if (Dump.Y) Dump.println("AjagoUtils context finish request after sleep 1200");//~@@@@I~//~v110R~
//        Dump.close();                                              //~@@@@I~//~v110R~
//        finished=true;                                             //~@@@@I~//~v110R~
//    }                                                              //~@@@@I~//~v110R~
	public static void sleep(long Pmilisec)                        //~1503I~
    {                                                              //~1503I~
        try                                                        //~1503I~
        {                                                          //~1503I~
        	Thread.sleep(Pmilisec);//wait subtread termination  1.2sec//~1503I~
        }                                                          //~1503I~
        catch(InterruptedException e)                              //~1503I~
		{                                                          //~1503I~
        	Dump.println(e,"sleep interrupted Exception");         //~1503I~
		}                                                          //~1503I~
    }                                                              //~1503I~
//**********************************                               //~1412I~
//*elapsed time calc                                               //~1412I~
//**********************************                               //~1412I~
	public static final int TSID_TITLE_TOUCH=0;                   //~1412I~
	private static final int TSID_MAX        =1;                   //~1412I~
	private static long[] Stimestamp=new long[TSID_MAX];                                 //~1412I~
	public static long setTimeStamp(int Pid)                       //~1412I~
    {                                                              //~1412I~
        if (Pid>=TSID_MAX)                                         //~1412I~
            return 0;                                              //~1412I~
        long t=System.currentTimeMillis();                         //~1412I~
        Stimestamp[Pid]=t;                                         //~1412I~
    	if (Dump.Y) Dump.println("AjagoUtils setTimeStamp id="+Pid+",ts="+Long.toHexString(t));//~1506R~
        return t;                                                  //~1412I~
    }                                                              //~1412I~
	public static int getElapsedTimeMillis(int Pid)                //~1412I~
    {                                                              //~1412I~
        if (Pid>=TSID_MAX)                                         //~1412I~
            return 0;                                              //~1412I~
        if (Stimestamp[Pid]==0)                                    //~1413I~
            return 0;                                              //~1413I~
        long t=System.currentTimeMillis();                         //~1412I~
    	if (Dump.Y) Dump.println("AjagoUtils getElapsed now id="+Pid+",ts="+Long.toHexString(t));//~1506R~
        int  elapsed=(int)(t-Stimestamp[Pid]);                     //~1412I~
    	if (Dump.Y) Dump.println("AjagoUtils getElapsetTimeMillis id="+Pid+",ts="+Integer.toHexString(elapsed));//~1506R~
        Stimestamp[Pid]=0;                                         //~1413I~
        return elapsed;                                            //~1412I~
    }                                                              //~1412I~
//**********************************                               //~1425I~
//*edit date/time                                                  //~1425I~
//**********************************                               //~1425I~
	public static final int TS_DATE_TIME=1;                        //~1425I~
	public static final int TS_MILI_TIME=2;                        //~1425I~
	public static final int TS_YYMMDDHHMM=3;                       //~v1DnI~
	private static final SimpleDateFormat fmtdt=new SimpleDateFormat("yyyyMMdd-HHmmss");//~1425I~
	private static final SimpleDateFormat fmtdtshort=new SimpleDateFormat("yyMMdd-HHmm");//~v1DnI~
	private static final SimpleDateFormat fmtms=new SimpleDateFormat("HHmmss.SSS");//~1425I~
	public static String getTimeStamp(int Popt)                    //~1425I~
    {                                                              //~1425I~
        SimpleDateFormat f;                                        //~1425I~
    //**********************:                                      //~1425I~
    	switch(Popt)                                               //~1425I~
        {                                                          //~1425I~
        case TS_DATE_TIME:                                         //~1425I~
        	f=fmtdt;                                               //~1425I~
            break;                                                 //~1425I~
        case TS_MILI_TIME:                                         //~1425I~
        	f=fmtms;                                               //~1425I~
            break;                                                 //~1425I~
        case TS_YYMMDDHHMM:                                        //~v1DnI~
        	f=fmtdtshort;                                          //~v1DnI~
            break;                                                 //~v1DnI~
        default:                                                   //~1425I~
        	return null;                                           //~1425I~
        }                                                          //~1425I~
        return f.format(new Date());                               //~1425I~
    }                                                              //~1425I~
//**********************************                               //~1425I~
//* Digit Thread ID                                                //~1425I~
//**********************************                               //~1425I~
	public static String getThreadId()                             //~1425I~
    {                                                              //~1425I~
    //**********************:                                      //~1425I~
    	int tid=(int)Thread.currentThread().getId();               //~1425I~
        if (tid<10)                                                //~1425I~
        	return "0"+tid;                                        //~1425I~
        return Integer.toString(tid);                              //~1425I~
    }                                                              //~1425I~
//**********************************                               //~1425I~
	public static String getThreadTimeStamp()                      //~1425I~
    {                                                              //~1425I~
    //**********************:                                      //~1425I~
    	String tidts=getThreadId()+":"+getTimeStamp(TS_MILI_TIME);  //~1425I~
        return tidts;                                              //~1425I~
    }                                                              //~1425I~
//**********************************                               //~v106I~
    public static String getIPAddress()                            //~v106I~
    {                                                              //~1B10I~
    	return getIPAddress(true);                                 //~1B10I~
    }                                                              //~1B10I~
//***************************************************************************//~1A8bI~
	private static int SswDirect;                                  //~1A8bR~
    private static final int MAC_LOCAL_ADDRESS=0x02;	//if off global address//~1A8bI~
//***********                                                      //~1A8bI~
    public static String getIPAddressDirect()                      //~1A8bR~
    {                                                              //~1A8bI~
    	SswDirect=1; //local only                                  //~1A8bR~
    	String ipa=getIPAddress(false);                            //~1A8bI~
    	SswDirect=0;                                               //~1A8bR~
        return ipa;                                                //~1A8bI~
    }                                                              //~1A8bI~
//***********                                                      //~1A8bI~
    public static String getIPAddressAll()                         //~1A8bI~
    {                                                              //~1A8bI~
    	SswDirect=2; //both global and local                       //~1A8bI~
    	String ipa=getIPAddress(false);                            //~1A8bI~
    	SswDirect=0;                                               //~1A8bI~
        return ipa;                                                //~1A8bI~
    }                                                              //~1A8bI~
//***************************************************************************//~1A8bI~
    public static String getIPAddress(boolean Pipv6)               //~1B10I~
    {                                                              //~v106I~
//  	String ipa="N/A";                                          //~v106R~//~1A86R~
    	String ipa=IPA_NA;                                         //~1A86I~
    	String ipv6="";                                           //~v106I~
        int ctr=0;                                                 //~1A67I~
    //**********************:                                      //~v106I~
        try                                                        //~v106I~
        {                                                          //~v106I~
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();//~v106I~
            while(interfaces.hasMoreElements())                    //~v106I~
            {                                                      //~v106I~
                NetworkInterface network = interfaces.nextElement();//~v106I~
    		    if (Dump.Y) Dump.println("getIPAddress mac="+getMacString(network.getHardwareAddress()));//~1A67I~
    		    byte[] bmac=network.getHardwareAddress();          //~1A8bR~
                if (bmac!=null)                                       //~1A8bI~
                {                                                  //~1A8bI~
                    if ((bmac[0] & MAC_LOCAL_ADDRESS)!=0)          //~1A8bR~
                    {                                              //~1A8bR~
                        if (SswDirect==0) //global only            //~1A8bR~
                            continue;                              //~1A8bR~
                    }                                              //~1A8bR~
                    else    //global                               //~1A8bR~
                    {                                              //~1A8bR~
                        if (SswDirect==1)   //local only           //~1A8bR~
                            continue;                              //~1A8bR~
                    }                                              //~1A8bR~
                }                                                  //~1A8bI~
    		    if (Dump.Y) Dump.println("isPont2point="+network.isPointToPoint()+",isUp="+network.isUp());//~1A67I~
    		    if (Dump.Y) Dump.println("name="+network.getName()+",displayName="+network.getDisplayName());//~1A67I~
    		    if (Dump.Y) Dump.println("toString="+network.toString());//~1A67I~
                Enumeration<InetAddress> addresses = network.getInetAddresses();//~v106I~
                while(addresses.hasMoreElements())                 //~v106I~
                {                                                  //~v106I~
                    InetAddress na=addresses.nextElement();        //~v106R~
                    String ipa2=na.getHostAddress();               //~v106I~
                    if (na.isLoopbackAddress()                     //~v106R~
                    ||  na.isLinkLocalAddress()                    //~v106R~
//                  ||  na.isSiteLocalAddress()                    //~v106R~
                    )                                              //~v106I~
                    	continue;                                  //~v106I~
                    if (na instanceof Inet6Address)//ipv6          //~v106M~
                    {                                              //~v106I~
                    	ipv6=ipa2;                                 //~v106I~
                    	continue;                                  //~v106M~
                    }                                              //~v106I~
			        if (Dump.Y) Dump.println("getIPAddress:"+ipa2);//~v106R~
                  if (ctr++==0)                                    //~1A67I~
                    ipa=ipa2;                                      //~v106R~
				  else                                             //~1A67I~
                    ipa+=";"+ipa2;                                 //~1A67I~
                    break;                                         //~v106R~
                }                                                  //~v106I~
            }                                                      //~v106I~
        }                                                          //~v106I~
        catch(Exception e)                                         //~v106I~
        {                                                          //~v106I~
        	Dump.println(e,"getIPAddress");                        //~v106I~
        }                                                          //~v106I~
        if (!Pipv6 || ipv6.equals(""))                             //~1B10R~
	        return ipa;                                            //~1B10I~
        return ipa+","+ipv6;                                       //~v106R~
    }                                                              //~v106I~
//**********************************                               //~1A86I~
    public static String getIPAddressFromMacAddr(String Pmacaddr)  //~1A86I~
    {                                                              //~1A86I~
    	String ipa=IPA_NA;                                         //~1A86R~
//  	String ipv6="";                                            //~1A86R~
        int ctr=0;                                                 //~1A86I~
    //**********************:                                      //~1A86I~
        try                                                        //~1A86I~
        {                                                          //~1A86I~
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();//~1A86I~
            while(interfaces.hasMoreElements())                    //~1A86I~
            {                                                      //~1A86I~
                NetworkInterface network = interfaces.nextElement();//~1A86I~
                String macaddr=getMacString(network.getHardwareAddress());//~1A86I~
                if (Dump.Y) Dump.println("getIPAddressFromMacaddr mac="+macaddr+",parm="+Pmacaddr);//~1A86I~
    		    if (Dump.Y) Dump.println("isPont2point="+network.isPointToPoint()+",isUp="+network.isUp());//~1A86I~
    		    if (Dump.Y) Dump.println("name="+network.getName()+",displayName="+network.getDisplayName());//~1A86I~
    		    if (Dump.Y) Dump.println("toString="+network.toString());//~1A86I~
                if (!macaddr.equals(Pmacaddr))                     //~1A86I~
                	continue;                                      //~1A86I~
                Enumeration<InetAddress> addresses = network.getInetAddresses();//~1A86I~
                while(addresses.hasMoreElements())                 //~1A86I~
                {                                                  //~1A86I~
                    InetAddress na=addresses.nextElement();        //~1A86I~
                    String ipa2=na.getHostAddress();               //~1A86I~
                    if (na.isLoopbackAddress()                     //~1A86I~
                    ||  na.isLinkLocalAddress()                    //~1A86I~
//                  ||  na.isSiteLocalAddress()                    //~1A86I~
                    )                                              //~1A86I~
                    	continue;                                  //~1A86I~
                    if (na instanceof Inet6Address)//ipv6          //~1A86I~
                    {                                              //~1A86I~
//                    	ipv6=ipa2;                                 //~1A86I~
                    	continue;                                  //~1A86I~
                    }                                              //~1A86I~
			        if (Dump.Y) Dump.println("getIPAddress:"+ipa2);//~1A86I~
                  if (ctr++==0)                                    //~1A86I~
                    ipa=ipa2;                                      //~1A86I~
				  else                                             //~1A86I~
                    ipa+=";"+ipa2;                                 //~1A86I~
                    break;                                         //~1A86I~
                }                                                  //~1A86I~
            }                                                      //~1A86I~
        }                                                          //~1A86I~
        catch(Exception e)                                         //~1A86I~
        {                                                          //~1A86I~
        	Dump.println(e,"getIPAddressFromMacAddr");             //~1A86I~
        }                                                          //~1A86I~
//      if (!Pipv6 || ipv6.equals(""))                             //~1A86I~
//          return ipa;                                            //~1A86I~
//      return ipa+","+ipv6;                                       //~1A86I~
        return ipa;                                                //~1A86I~
    }                                                              //~1A86I~
//***********************************************************************//~v107R~
    public static boolean isDebuggable(Context ctx)                //~v107R~
    {                                                              //~v107R~
        PackageManager manager = ctx.getPackageManager();          //~v107R~
        ApplicationInfo appInfo = null;                            //~v107R~
        try                                                        //~v107R~
        {                                                          //~v107R~
            appInfo = manager.getApplicationInfo(ctx.getPackageName(), 0);//~v107R~
        }                                                          //~v107R~
        catch (NameNotFoundException e)                            //~v107R~
        {                                                          //~v107R~
            return false;                                          //~v107R~
        }                                                          //~v107R~
        if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE)//~v107R~
            return true;                                           //~v107R~
        return false;                                              //~v107R~
    }                                                              //~v107R~
//***********************************************************************//~v1b6I~
    public static int str2int(String Pstr,int Pdefault)            //~v1b6I~
    {                                                              //~v1b6I~
    	int v;                                                     //~v1b6I~
    	try                                                        //~v1b6I~
        {                                                          //~v1b6I~
			v=Integer.parseInt(Pstr);                              //~v1b6I~
        }                                                          //~v1b6I~
		catch (NumberFormatException ex)                           //~v1b6I~
		{                                                          //~v1b6I~
			v=Pdefault;                                            //~v1b6I~
		}                                                          //~v1b6I~
        return v;                                                  //~v1b6I~
    }                                                              //~v1b6I~
//***********************************************************************//~v1b6I~
    public static double str2double(String Pstr,double Pdefault)   //~v1b6I~
    {                                                              //~v1b6I~
    	double v;                                                  //~v1b6I~
    	try                                                        //~v1b6I~
        {                                                          //~v1b6I~
			v=Double.parseDouble(Pstr);                            //~v1b6I~
        }                                                          //~v1b6I~
		catch (NumberFormatException ex)                           //~v1b6I~
		{                                                          //~v1b6I~
			v=Pdefault;                                            //~v1b6I~
		}                                                          //~v1b6I~
        return v;                                                  //~v1b6I~
    }                                                              //~v1b6I~
    //*********************************                            //~v1DhI~
    public static int strspnc(String Pstr,int Ppos,char Pch)                     //~v1DhI~
    {                                                              //~v1DhI~
    	int ii;
    	for (ii=Ppos;ii<Pstr.length();ii++)                    //~v1DhI~
        {                                                          //~v1DhI~
        	if (Pstr.charAt(ii)!=Pch)                              //~v1DhI~
            	break;                                             //~v1DhI~
        }                                                          //~v1DhI~
    	return ii-Ppos;                                            //~v1DhI~
    }                                                              //~v1DhI~
    //*********************************                            //~v1DhI~
    public static int strpbrk(String Pstr,int Ppos,String Pkey)                  //~v1DhI~
    {                                                              //~v1DhI~
    	int rc=-1,ii;                                                 //~v1DhI~
        for (ii=0;ii<Pkey.length();ii++)                           //~v1DhI~
        {                                                          //~v1DhI~
        	char ch=Pkey.charAt(ii);                               //~v1DhI~
            int offs=Pstr.indexOf(ch,Ppos);                         //~v1DhI~
            if (offs>=0)                                           //~v1DhI~
            	if (rc<0)                                          //~v1DhI~
                	rc=offs;                                       //~v1DhI~
                else                                               //~v1DhI~
                if (offs<rc)                                       //~v1DhI~
                	rc=offs;                                       //~v1DhI~
        }                                                          //~v1DhI~
    	return rc;                                                 //~v1DhI~
    }                                                              //~v1DhI~
//***********************************************************      //~v1E7I~
	public static void showWebSite(String Purl)                    //~v1E7I~
	{                                                              //~v1E7I~
		if (Dump.Y) Dump.println("Utils:showWebSite url="+Purl);   //~v1E7I~
        Intent intent=new Intent(Intent.ACTION_VIEW);              //~v1E7I~
        intent.setData(Uri.parse(Purl));                           //~v1E7I~
        AG.activity.startActivity(intent);                         //~v1E7I~
	}                                                              //~v1E7I~
//***********************************************************************//~1A67I~//~1A6aI~
    public static String getMacString(byte[] Pbytemacaddr)                //~1A67R~//~1A6aI~
    {                                                              //~1A67R~//~1A6aI~
        if (Pbytemacaddr==null)                                    //~1A6aI~
            return "";                                             //~1A6aI~
        StringBuilder sb=new StringBuilder("");                      //~1A67R~//~1A6aI~
        int sz=Pbytemacaddr.length;                                //~1A67R~//~1A6aI~
        for (int ii=0;ii<sz;ii++)                                  //~1A67R~//~1A6aI~
        {                                                          //~1A67R~//~1A6aI~
            sb.append(String.format("%s%02x",((ii==0) ? "" : ":"),Pbytemacaddr[ii]));//~1A67R~//~1A6aI~
        }                                                          //~1A67R~//~1A6aI~
        return new String(sb);                                     //~1A67R~//~1A6aI~
    }                                                              //~1A67R~//~1A6aI~
//***********************************************************************//~1A6aI~
	public static String getRemoteIPAddr(Socket Psocket,String Pnullopt)                       //~@@@2I~//~1A8bI~
    {                                                              //~@@@2I~//~1A8bI~
    	String ipa=null;                                           //~1A8bI~
        InetAddress ia=Psocket.getInetAddress();                   //~@@@2I~//~1A8bI~
        if (ia!=null)                                              //~@@@2M~//~1A8bI~
        {                                                          //~@@@2M~//~1A8bI~
	        ipa=ia.getHostAddress();              //~@@@2R~        //~1A8bI~
	        if (Dump.Y) Dump.println("AjagoUtils:getRemoteIPAddr="+ipa+",name="+ia.getHostName());//~@@@2I~//~1A8bI~
        }                                                          //~@@@2M~//~1A8bI~
        if (ipa==null)                                              //~1A8bI~
        {                                                          //~1A8bI~
        	ipa=Pnullopt;                                          //~1A8bI~
        }                                                          //~1A8bI~
        return ipa;                                                //~1A8bI~
    }                                                              //~@@@2I~//~1A8bI~
//***********************************************************************//~1A8bI~
	public static String getLocalIPAddr(Socket Psocket,String Pnullopt)//~1A8bI~
    {                                                              //~1A8bI~
    	String ipa=null;                                           //~1A8bI~
        InetAddress ia=Psocket.getLocalAddress();                  //~1A8bI~
        if (ia!=null)                                              //~1A6sI~//~1A8bI~
        {                                                          //~1A6sI~//~1A8bI~
	        ipa=ia.getHostAddress();               //~1A6sI~       //~1A8bI~
	        if (Dump.Y) Dump.println("AjagoUtils:getLocalIPAddr="+ipa+",name="+ia.getHostName());//~1A8bI~
        }                                                          //~1A6sI~//~1A8bI~
        if (ipa==null)                                              //~1A8bI~
        {                                                          //~1A8bI~
        	ipa=Pnullopt;                                          //~1A8bI~
        }                                                          //~1A8bI~
        return ipa;                                                //~1A8bI~
    }                                                              //~1A8bI~
//***********************************************************************//+1Ac0I~
    public static void chkNetwork()                                //+1Ac0I~
    {                                                              //+1Ac0I~
        ConnectivityManager cm=getCM();                            //+1Ac0I~
        NetworkInfo[] infos=cm.getAllNetworkInfo();                //+1Ac0I~
        if (Dump.Y) Dump.println("Utils:chkNetwork ctr="+infos.length);//+1Ac0I~
        for (NetworkInfo ni:infos)                                 //+1Ac0I~
        {                                                          //+1Ac0I~
        	String typename=ni.getTypeName();                      //+1Ac0I~
        	String subtypename=ni.getSubtypeName();                //+1Ac0I~
        	boolean connected=ni.isConnected();                    //+1Ac0I~
            if (Dump.Y) Dump.println("Utils:chkNetwork :type="+typename+",subtype="+subtypename+",connected="+connected+",tostring="+ni.toString());//+1Ac0I~
        }                                                          //+1Ac0I~
    }                                                              //+1Ac0I~
//***********************************************************************//+1Ac0I~
    public static ConnectivityManager getCM()                      //+1Ac0I~
    {                                                              //+1Ac0I~
        return (ConnectivityManager)AG.context.getSystemService(Context.CONNECTIVITY_SERVICE);//+1Ac0I~
    }                                                              //+1Ac0I~
}//class AjagoUtils                                                //~1309R~
