//*CID://+1A6AR~: update#= 122;                                    //~v1B6R~//~v1B9R~//+1A6AR~
//**********************************************************************//~v106I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing, (Dump.T) for UiThread//+1A6AI~
//v1B9 2014/08/12 modal alertdialog for v1B8                       //~v1B9I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//**********************************************************************//~1107I~
//*main view                                                       //~1107I~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import jagoclient.Dump;

import java.util.concurrent.CountDownLatch;

import android.os.Handler;

//**********************************************************************//~1107I~
public class AjagoUiThread                                         //~1214R~
{                                                                  //~0914I~
	public static final int LID_GMP=0;                             //~v106I~
	private static	CountDownLatch[] Slatch=new CountDownLatch[1]; //~v106I~
                                                                   //~1211I~
	private AjagoUiThreadI callback=null;                           //~1214R~
	private Object parm=null;                                      //~1214R~
	private AjagoAlert alertDialog;                                //~v1B9I~
    
    
//**********************************                               //~1211I~
//*                                                                //~1211R~
//**********************************                               //~1211I~
	public AjagoUiThread(AjagoUiThreadI Pcallback,Object Pparm)    //~1214R~
    {                                                              //~1214I~
    	callback=Pcallback;                                        //~1214I~
        parm=Pparm;                                                //~1214R~
    }                                                              //~1214I~
//**********************************                               //~v1B9I~
//*                                                                //~v1B9I~
//**********************************                               //~v1B9I~
	public AjagoUiThread(AjagoUiThreadI Pcallback,Object Pparm,AjagoAlert PalertDialog)//~v1B9I~
    {                                                              //~v1B9I~
    	this(Pcallback,Pparm);                                     //~v1B9I~
        alertDialog=PalertDialog;                                  //~v1B9I~
    }                                                              //~v1B9I~
//===============================================================================//~1309I~
//=on UI Thread;default NoSync                                     //~1309I~
//===============================================================================//~1309I~
    public static void runOnUiThread(AjagoUiThreadI Pcallback,Object Pparm)//~1309I~
    {                                                              //~1309I~
		runOnUiThreadNoSync(Pcallback,Pparm); //~1309I~
    }                                                              //~1309I~
//===============================================================================//~1310I~
//=on UI Thread;with wait parameter                                //~1310I~
//===============================================================================//~1310I~
    public static void runOnUiThread(boolean Pwait,AjagoUiThreadI Pcallback,Object Pparm)//~1310I~
    {                                                              //~1310I~
    	if (Pwait)                                                 //~1310I~
			runOnUiThreadSync(Pcallback,Pparm);                    //~1310I~
        else                                                       //~1310I~
			runOnUiThreadNoSync(Pcallback,Pparm);                  //~1310I~
    }                                                              //~1310I~
//===============================================================================//~1309I~
//=on UI Thread;wait postback                                      //~1309I~
//===============================================================================//~1309I~
    public static void runOnUiThreadWait(AjagoUiThreadI Pcallback,Object Pparm)//~1309I~
    {                                                              //~1309I~
		runOnUiThreadSync(Pcallback,Pparm);   //~1309I~
    }                                                              //~1309I~
//===============================================================================//~1309I~
//=on UI Thread;no wait postback                                   //~1309I~
//===============================================================================//~1309I~
    public static void runOnUiThreadXfer(AjagoUiThreadI Pcallback,Object Pparm)//~1309I~
    {                                                              //~1309I~
		runOnUiThreadNoSync(Pcallback,Pparm);                      //~1309I~
    }                                                              //~1309I~
//===============================================================================//~v1B9I~
//=on UI Thread;no wait postback                                   //~v1B9I~
//===============================================================================//~v1B9I~
    public static void runOnUiThreadXferAlertDialog(AjagoUiThreadI Pcallback,Object Pparm,AjagoAlert PalertDialog)//~v1B9I~
    {                                                              //~v1B9I~
    	AjagoUiThread aut=new AjagoUiThread(Pcallback,Pparm,PalertDialog);//~v1B9I~
    	aut.runOnUiThreadNoSync2(aut);                             //~v1B9I~
    }                                                              //~v1B9I~
//===============================================================================//~v@@@I~//~1212I~//~1214M~
//=no wait UI Thread end                                           //~1220I~
//===============================================================================//~1220I~
    private static void runOnUiThreadNoSync(AjagoUiThreadI Pcallback,Object Pparm)//~1214R~//~1221R~//~1309R~
    {                                                              //~1214I~
    	AjagoUiThread aut=new AjagoUiThread(Pcallback,Pparm);//~1214R~
    	if (AG.isMainThread())                                     //~1214M~
        {                                                          //~1214M~
        	try                                                    //~1512I~
            {                                                      //~1512I~
        		Pcallback.runOnUiThread(aut.parm);                 //~1512R~
            }                                                      //~1512I~
            catch (Exception e)                                    //~1512I~
            {                                                      //~1512I~
                Dump.println(e,"runOnUiThreadNoSync(Mainthread) Exception");//~1512I~
            }                                                      //~1512I~
            return;                                                //~1214M~
        }                                                          //~1214M~
    	aut.runOnUiThreadNoSync2(aut);                  //~1214I~
    }                                                              //~1214I~
//===============================================================================//~v@@@I~//~1212I~
    private void runOnUiThreadNoSync2(AjagoUiThread Paut)//~1212I~//~1214R~//~1221R~
    {                                                              //~1212I~
        final AjagoUiThread aut=Paut;                                    //~1214I~
        AG.activity.runOnUiThread(              //execute on mainthread after posted//~1214I~
            new Runnable()                                         //~1214I~
            {                                                      //~1214I~
                @Override                                          //~1214I~
                public void run()                                  //~1214I~
                {                                                  //~1214I~
			        if (Dump.T) Dump.println("RunOnUiThreadNoSync2 start");           //~1214I~//~1308R~//~1506R~//+1A6AR~
                    if (aut.callback!=null)                        //~1214R~
                    {                                              //~1214I~
			        	if (Dump.T) Dump.println("RunOnUiThreadNoSync2 parm="+(aut.parm==null?"null":aut.parm.toString()));//~1506R~//+1A6AR~
        				try                                        //~1311I~
        				{                                          //~1311I~
	                    	aut.callback.runOnUiThread(aut.parm);      //~1214R~//~1311R~
                        }                                          //~1311I~
                        catch (Exception e)             //~1311I~
                        {                                          //~1311I~
                            Dump.println(e,"runOnUiThreadNoSync2 Exception");//~1311I~
                        }                                          //~1311I~
                    }                                              //~1214I~
			        if (Dump.T) Dump.println("RunOnUiThreadNoSync2 end");//~1506R~//+1A6AR~
                }                                                  //~1214I~
            }                                                      //~1214I~
                                  );                               //~1214I~
    }                                                              //~1212I~
//===============================================================================//~1214I~
//= wait UI Thread end                                             //~1220I~
//===============================================================================//~1220I~
    private static void runOnUiThreadSync(AjagoUiThreadI Pcallback,Object Pparm)//~1214I~//~1309R~
    {                                                              //~1214I~
    	AjagoUiThread aut=new AjagoUiThread(Pcallback,Pparm);//~1214I~
		if (Dump.T) Dump.println("RunOnUiThreadSync parm="+(Pparm==null?"null":Pparm.toString()));//~1506R~//+1A6AR~
    	if (AG.isMainThread())                                     //~1214I~
        {                                                          //~1214I~
        	try                                                    //~1512I~
            {                                                      //~1512I~
	        	Pcallback.runOnUiThread(aut.parm);                 //~1512R~
            }                                                      //~1512I~
            catch (Exception e)                                    //~1512I~
            {                                                      //~1512I~
                Dump.println(e,"runOnUiThreadSync(Mainthread) Exception");//~1512I~
            }                                                      //~1512I~
            return;                                                //~1214I~
        }                                                          //~1214I~
    	aut.runOnUiThreadSync2();                         //~1214I~
    }                                                              //~1214I~
//===============================================================================//~1214I~
    private void runOnUiThreadSync2()            //~1214I~
    {                                                              //~1214I~
        if (Dump.T) Dump.println("AjagoUIThread:runOnUiThreadSync2");              //~1214I~//~1506R~//+1A6AR~
 //create handler is aval on Main thread only                      //~1214R~
//  	Handler handler=new Handler();                             //~1214I~
		CountDownLatch latch=new CountDownLatch(1/*posted by counddown once*/);
        runOnUiThreadSub sub=new runOnUiThreadSub();                                    //~1214R~
        sub.init(this,latch);                                      //~1214I~
        try                                                        //~1214I~
        {                                                          //~1214I~
	        AG.activity.runOnUiThread(sub);                        //~1513I~
        	latch.await();   //subthread wakeup by dismiss         //~1214I~
	        if (Dump.T) Dump.println("AjagoUiThread posted wait");                     //~1214I~//~1506R~//+1A6AR~
        }                                                          //~1214I~
        catch (InterruptedException e)                             //~1214I~
        {                                                          //~1214I~
        	Dump.println(e,"Modal Thread Interrupted ");//~1214I~//~1308R~//~1311R~
        }                                                          //~1214I~
    }                                                              //~1214I~
//*******************************                                  //~1214I~
// wait/pos by latch                                               //~v106I~
//===============================================================================//~v106I~
    public static void initlatch(int Platchid)                     //~v106I~
    {                                                              //~v106I~
		Slatch[Platchid]=new CountDownLatch(1/*posted by counddown once*/);//~v106I~
    }                                                              //~v106I~
    public static void wait(int Platchid)                          //~v106I~
    {                                                              //~v106I~
		if (Dump.T) Dump.println("AjagoUiThread Wait id="+Platchid);//~v106I~//+1A6AR~
    	if (AG.isMainThread())                                     //~v106I~
        	return;    //not supported                             //~v106I~
        try                                                        //~v106I~
        {                                                          //~v106I~
        	Slatch[Platchid].await();   //subthread wakeup by dismiss//~v106I~
	        if (Dump.T) Dump.println("AjagoUiThread wait:posted"); //~v106I~//+1A6AR~
			Slatch[Platchid]=null;                                 //~v106I~
        }                                                          //~v106I~
        catch (InterruptedException e)                             //~v106I~
        {                                                          //~v106I~
        	Dump.println(e,"wait Thread Interrupted ");            //~v106I~
        }                                                          //~v106I~
    }                                                              //~v106I~
    public static void post(int Platchid)                          //~v106I~
    {                                                              //~v106I~
		if (Dump.T) Dump.println("AjagoUiThread post id="+Platchid);//~v106I~//+1A6AR~
        Slatch[Platchid].countDown();  //count exausted,post latch.await()//~v106I~
	    if (Dump.T) Dump.println("AjagoUiThread post");            //~v106I~//+1A6AR~
    }                                                              //~v106I~
//*******************************                                  //~1214I~
    class runOnUiThreadSub                                  //~1214I~
			implements Runnable                                    //~1214I~
    {                                                              //~1214I~
    	private AjagoUiThread aut;
    	CountDownLatch latch;
    	public runOnUiThreadSub()                                  //~1214R~
        {                                                          //~1214I~
        }                                                          //~1214I~
    	public void init(AjagoUiThread Paut,CountDownLatch Platch) //~1214I~
        {                                                          //~1214I~
        	aut=Paut;                                              //~1214I~
        	latch=Platch;                                          //~1214I~
        }                                                          //~1214I~
        @Override                                                  //~1214I~
        public void run()                                          //~1214I~
        {                                                          //~1214I~
			if (Dump.T) Dump.println("before runOnUiTHreadSub");         //~1214I~//~1506R~//+1A6AR~
        	try                                                    //~1311I~
        	{                                                      //~1311I~
            	aut.callback.runOnUiThread(aut.parm);                  //~1214I~//~1311R~
            }                                                      //~1311I~
        	catch (Exception e)                         //~1311I~
        	{                                                      //~1311I~
        		Dump.println(e,"runOnUiThreadcSub(Sync) Exception");//~1311I~
        	}                                                      //~1311I~
			if (Dump.T) Dump.println("after runOnUiThreadSub");          //~1214I~//~1506R~//+1A6AR~
            latch.countDown();  //count exausted,post latch.await()//~1214I~
        }                                                          //~1214I~
    }                                                              //~1214I~
//**************************************************************************//~v1B6I~
//*schedule Runnable on MainThread                                 //~v1B6I~
//**************************************************************************//~v1B6I~
	public static void invokeLater(Runnable Pcallback)             //~v1B6M~
    {                                                              //~v1B6M~
    	Handler main=new Handler(AG.context.getMainLooper());      //~v1B6M~
        main.post(Pcallback);                                      //~v1B6M~
    }                                                              //~v1B6M~
}//class AjagoUiThread                                             //~1214R~
