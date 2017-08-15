//*CID://+1Ae5R~: update#= 142;                                    //~1A6tR~//~1Ae5R~
//**********************************************************************//~v106I~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//1A6t 2015/02/18 (BUG)simpleProgress Dialog thread err exception  //~1A6tI~
//1A6e 2015/02/13 runOnUiThread for processingdialog               //~1A6eI~
//**********************************************************************//~1107I~
//*main view                                                       //~1107I~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~//~@@@@R~

import jagoclient.Dump;
import android.app.ProgressDialog;

//**********************************************************************//~1107I~
public class URunnable                                             //~@@@@R~
{                                                                  //~0914I~
	private URunnableI callback;                           //~1214R~//~@@@@R~
	private Object parmObject;                                      //~1214R~//~@@@@R~
	private int delay,parmInt;                                     //~@@@@I~
    private boolean swMainThread,swFromSubthreadRunnable;                  //~@@@@R~
    private boolean swRunOnSubthread;                              //~1Ae5I~
//**********************************                               //~1211I~
	public URunnable(URunnableI Pcallback,int Pdeley,Object Pparmobject,int Pparmint)    //~1214R~//~@@@@R~
    {                                                              //~1214I~
    	callback=Pcallback;                                        //~1214I~
        parmObject=Pparmobject;                                                //~1214R~//~@@@@R~
        parmInt=Pparmint;                                         //~@@@@I~
        delay=Pdeley;                                              //~@@@@I~
        swMainThread=AG.isMainThread();                            //~@@@@I~
    }                                                              //~1214I~
	public URunnable()                                             //~1A6eI~
    {                                                              //~1A6eI~
    }                                                              //~1A6eI~
//===============================================================================//~@@@@I~
//=request callback on UiThread after some delay on subthread      //~@@@@R~
//===============================================================================//~@@@@I~
    public static void setRunFunc(URunnableI Pcallback,int Pdelay,Object Pparmobj,int Pparmint)//~@@@@R~
    {                                                              //~@@@@I~
		URunnable uh=new URunnable(Pcallback,Pdelay,Pparmobj,Pparmint);//~@@@@R~
        CallbackRunnable cbrun=uh.new CallbackRunnable(uh);                            //~@@@@I~
		if (Dump.Y) Dump.println("setRunFunc start delay="+Pdelay);//~@@@@R~
    	if (uh.swMainThread)                                       //~@@@@I~
        {                                                          //~@@@@I~
        	new Thread(cbrun).start();  //callback on UiThread after sleep on subthread//~@@@@I~
        }                                                          //~@@@@I~
        else	//post from subthread                              //~@@@@I~
        {                                                          //~@@@@I~
        	if (Pdelay!=0)                                         //~@@@@I~
	        	AjagoUtils.sleep((long)Pdelay);                         //~@@@@I~
	        AG.activity.runOnUiThread(cbrun);                      //~@@@@I~
        }                                                          //~@@@@I~
		if (Dump.Y) Dump.println("setRunFunc return");             //~@@@@R~
    }                                                              //~@@@@I~
//===============================================================================//~@@@@I~
//=request callback on UiThread without delay                      //~@@@@I~
//=direct call if on mainthread                                    //~@@@@I~
//===============================================================================//~@@@@I~
    public static void setRunFuncDirect(URunnableI Pcallback,Object Pparmobj,int Pparmint)//~@@@@I~
    {                                                              //~@@@@I~
		if (Dump.Y) Dump.println("setRunFuncDirect");              //~@@@@I~
    	if (AG.isMainThread())                                       //~@@@@I~
        {                                                          //~@@@@I~
        	Pcallback.runFunc(Pparmobj,Pparmint);                  //~@@@@I~
        }                                                          //~@@@@I~
        else	//call from subthread                              //~@@@@I~
        {                                                          //~@@@@I~
			URunnable uh=new URunnable(Pcallback,0/*delay*/,Pparmobj,Pparmint);//~@@@@I~
	        CallbackRunnable cbrun=uh.new CallbackRunnable(uh);    //~@@@@I~
	        AG.activity.runOnUiThread(cbrun);                      //~@@@@I~
        }                                                          //~@@@@I~
		if (Dump.Y) Dump.println("setRunFuncDirect return");       //~@@@@R~
    }                                                              //~@@@@I~
//===============================================================================//~v101I~//~1Ae5I~
//=run on subthread                                                //~v101I~//~1Ae5I~
//===============================================================================//~v101I~//~1Ae5I~
    public static void setRunFuncSubthread(URunnableI Pcallback,int Pdelay,Object Pparmobj,int Pparmint)//~v101I~//~1Ae5I~
    {                                                              //~v101I~//~1Ae5I~
		if (Dump.Y) Dump.println("setRunFuncSubThread");           //~v101I~//~1Ae5I~
    	if (!AG.isMainThread())   //alread not UI thread           //~v101I~//~1Ae5I~
        {                                                          //~v101I~//~1Ae5I~
        	if (Pdelay!=0)                                         //~v101I~//~1Ae5I~
				AjagoUtils.sleep((long)Pdelay);                         //~v101I~//~1Ae5I~
        	Pcallback.runFunc(Pparmobj,Pparmint);                  //~v101I~//~1Ae5I~
        }                                                          //~v101I~//~1Ae5I~
        else	//call from mainthread                             //~v101I~//~1Ae5I~
        {                                                          //~v101I~//~1Ae5I~
			URunnable uh=new URunnable(Pcallback,Pdelay,Pparmobj,Pparmint);//~v101I~//~1Ae5I~
            uh.swRunOnSubthread=true;                              //~v101I~//~1Ae5I~
	        CallbackRunnable cbrun=uh.new CallbackRunnable(uh);    //~v101I~//~1Ae5I~
	        (new Thread(cbrun)).start();                           //~v101I~//~1Ae5I~
        }                                                          //~v101I~//~1Ae5I~
		if (Dump.Y) Dump.println("setRunFuncSubthread return");    //~v101I~//~1Ae5I~
    }                                                              //~v101I~//~1Ae5I~
//************************************************************     //~@@@@I~
    class CallbackRunnable	implements Runnable                    //~@@@@I~
    {                                                              //~@@@@I~
    	URunnable uh;                                              //~@@@@R~
    	public CallbackRunnable(URunnable Puh)                     //~@@@@R~
        {                                                          //~@@@@I~
        	uh=Puh;                                                //~@@@@I~
        }                                                          //~@@@@I~
        @Override                                                  //~@@@@I~
        public void run()                                          //~@@@@I~
        {                                                          //~@@@@I~
			if (Dump.Y) Dump.println("URunnable CallbackRunnable start run");//~@@@@R~
        	try                                                    //~@@@@I~
        	{                                                      //~@@@@I~
//          	if (uh.swMainThread && !swFromSubthreadRunnable)	//runnable is on subthread//~@@@@R~//+1Ae5R~
            	if (uh.swMainThread && !swFromSubthreadRunnable && !swRunOnSubthread)	//runnable is on subthread//~@@@@R~//~v101R~//~1Ae5I~
                {                                                  //~@@@@I~
        			if (uh.delay!=0)                               //~@@@@I~
			        	AjagoUtils.sleep((long)uh.delay);               //~@@@@I~
			        CallbackRunnable cbrun=uh.new CallbackRunnable(uh);  //new runnable//~@@@@I~
                    uh.swFromSubthreadRunnable=true;                       //~@@@@I~
	        		AG.activity.runOnUiThread(cbrun);              //~@@@@I~
                    if (Dump.Y) Dump.println("kick UI thread from subthread");//~@@@@I~
                }                                                  //~@@@@I~
                else    //this runnable is on UI thread            //~@@@@I~
                {                                                  //~@@@@I~
                    if (Dump.Y) Dump.println("on the thread main or sub");//~v101I~//~1Ae5I~
                    if (swRunOnSubthread)                          //~v101I~//~1Ae5I~
                    	if (uh.delay!=0)                           //~v101I~//~1Ae5I~
				        	AjagoUtils.sleep((long)uh.delay);           //~v101I~//~1Ae5I~
            		uh.callback.runFunc(uh.parmObject,uh.parmInt); //~@@@@R~
                }                                                  //~@@@@I~
            }                                                      //~@@@@I~
        	catch (Exception e)                                    //~@@@@I~
        	{                                                      //~@@@@I~
        		Dump.println(e,"URunnable:CallbackUiThread");      //~@@@@R~
        	}                                                      //~@@@@I~
			if (Dump.Y) Dump.println("URunnable:CallbackRunnable end run");//~@@@@R~
        }                                                          //~@@@@I~
    }                                                              //~@@@@I~
//****************************************                         //~1A6eI~
    public static void dismissDialog(URunnableData Pdata)          //~1A6tI~
    {                                                              //~1A6tI~
    	if (Pdata==null)                                           //~1A6tI~
        	return;                                                //~1A6tI~
    	android.app.Dialog dlg=Pdata.progressDialog;               //~1A6tI~
		dismissDialog(dlg);                                        //~1A6tI~
    }                                                              //~1A6tI~
    public static void dismissDialog(android.app.Dialog Pdialog)   //~1A6eI~
    {                                                              //~1A6eI~
    	if (Pdialog==null)                                         //~1A6eI~
        	return;                                                //~1A6eI~
        if (Dump.Y) Dump.println("URunnable:DismissRialogI:runOnUiThread="+Pdialog.toString());//~1A6eI~
    	DismissDialogI uithreadi=new URunnable().new DismissDialogI();//~1A6eI~
        AjagoUiThread.runOnUiThread(uithreadi,Pdialog);                 //~1A6eI~
    }                                                              //~1A6eI~
    class DismissDialogI implements AjagoUiThreadI                      //~1A6eI~
    {                                                              //~1A6eI~
    	@Override                                                  //~1A6eI~
		public void runOnUiThread(Object Pparm)                    //~1A6eI~
        {                                                          //~1A6eI~
        	android.app.Dialog dialog=(android.app.Dialog)Pparm;   //~1A6eI~
        	if (Dump.Y) Dump.println("URunnable:DismissDialogI:runOnUiThread="+dialog.toString());//~1A6eI~
	    	if (dialog.isShowing())                                //~1A6eI~
	        	dialog.dismiss();                                  //~1A6eI~
        }                                                          //~1A6eI~
	}                                                              //~1A6eI~
//****************************************                         //~1A6eI~
//  public static ProgressDialog simpleProgressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1A6eI~//~1A6tR~
    public static URunnableData simpleProgressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1A6tI~
    {                                                              //~1A6eI~
//      ProgressDialog dlg=new ProgressDialog(AG.context);         //~1A6eI~//~1A6tR~
        URunnableData dlg=new URunnableData();	//asynchronously filled at uithread execution//~1A6tI~
        if (Dump.Y) Dump.println("URunnable:simpleProgressDialogShow msg="+Pmsg+",dialog="+dlg.toString());//~1A6eI~
	    SimpleProgressDialogShowI uithreadi=new URunnable().new SimpleProgressDialogShowI(Ptitleid,Pmsg,Pindeterminate,Pcancelable);//~1A6eI~
        AjagoUiThread.runOnUiThread(uithreadi,dlg);                     //~1A6eI~
        return dlg;                                                //~1A6eI~
    }                                                              //~1A6eI~
    class SimpleProgressDialogShowI implements AjagoUiThreadI           //~1A6eI~
    {                                                              //~1A6eI~
        int titleid;                                               //~1A6eI~
        String msg;                                                //~1A6eI~
		boolean indeterminate,cancelable;                          //~1A6eI~
        URunnableData data;                                        //~1A6tI~
	    public SimpleProgressDialogShowI(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1A6eI~
        {                                                          //~1A6eI~
        	titleid=Ptitleid; msg=Pmsg; indeterminate=Pindeterminate; cancelable=Pcancelable;//~1A6eI~
        }                                                          //~1A6eI~
        @Override                                                  //~1A6eI~
		public void runOnUiThread(Object Pparm)                    //~1A6eI~
        {                                                          //~1A6eI~
        	if (Dump.Y) Dump.println("URunnable:SimpleProgressDialogShow:runOnUiThread");//~1A6eI~
//          ProgressDialog dlg=(ProgressDialog)Pparm;              //~1A6eI~//~1A6tR~
            data=(URunnableData)Pparm;                             //~1A6tI~
			ProgressDialog dlg=new ProgressDialog(AG.context);     //~1A6tI~
			data.progressDialog=dlg;                               //~1A6tI~
            dlg.setTitle(AG.resource.getString(titleid));          //~1A6eI~
            dlg.setMessage(msg);                                   //~1A6eI~
            dlg.setIndeterminate(indeterminate);                   //~1A6eI~
            dlg.setCancelable(cancelable);                         //~1A6eI~
	        dlg.show();                                            //~1A6eI~
        }                                                          //~1A6eI~
	}                                                              //~1A6eI~
}//class URunnable                                        //~1214R~//~@@@@R~
