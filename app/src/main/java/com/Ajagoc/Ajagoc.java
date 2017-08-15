//*CID://+1AgdR~:                             update#=   31;       //+1AgdR~
//**********************************************************************//~v107I~
//1Agd 2016/10/11 drop gree by preparing top menu panel.           //+1AgdI~
//2015/07/23 //1Abk 2015/06/16 NFC:errmsg when intent is for other side of NFC/NFCBT//~1AbkI~
//2015/07/23 //1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8I~
//2015/07/23 //1Ab7 2015/05/03 NFC Bluetooth handover v2
//2015/03/06 1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//2015/03/06 1A6j 2015/02/14 set dummy NFC function for main Activity to avoid current activity re-created.//~1A6jI~
//2015/03/06 1A6a 2015/02/10 NFC+Wifi support                      //~1A6aI~
//2015/03/06 1A65 2015/01/29 implement Wifi-Direct function(>=Api14:android4.0)//~1A65I~
//v1D1 2014/10/03 actionBAr as alternative of menu button for api>=11//~v1D1I~
//                When requestWindowFeature(FEATURE_LEFT_ICON). onCreateOptionsMenu is not called(No ActionBar on android>=3.0)//~v1D1I~
//                Amain Amenu                                      //~v1D1I~
//1B03 130427 (Bug) properties was not written to go.cfg when exit not by mainframe menu-close//~1B03I~
//1102:130123 bluetooth became unconnectable after some stop operation//~v110I~
//1078:121208 add "menu" to option menu if landscape               //~v107R~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//**********************************************************************//~v107I~
package com.Ajagoc;

import wifidirect.DialogNFC;
import wifidirect.DialogNFCSelect;
import wifidirect.WDA;
import wifidirect.WDANFC;

import com.Ajagoc.BT.BTControl;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.Go;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;                                          //~1109I~
                                                                   //~1109I~
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;                                      //~1109I~
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.OnTabChangeListener;
import android.view.Window.Callback;
                                                                   //~1109I~
//********************************************************************//~1212I~
//* Android Jago Client  EntryPoint **********************************//~1212I~
//********************************************************************//~1212I~
public class Ajagoc                                                //~1122R~
//                    extends TabActivity                            //~1122I~
                      extends Activity
                      implements OnTabChangeListener
                                 ,Callback//~1122I~
                                 ,URunnableI                       //~v110I~
                                                                   //~1122I~
{                                                                  //~1109I~
    private static boolean destroying=false;                              //~1218I~
//  private static final int GREETING_LONG=5;                      //+1AgdR~
//  private static final int GREETING_SHORT=20;                    //+1AgdR~
//*************************                                        //~1109I~
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)                //~1329R~
    {                                                              //~1329I~
		int msgidGreeting;                                         //~v1D1I~
        try                                                        //~1329I~
        {                                                          //~1329I~
            super.onCreate(savedInstanceState);                    //~1329R~
//          requestWindowFeature(Window.FEATURE_LEFT_ICON);             //~0914R~//~0915R~//~0A09R~//~1312I~//~v1D1R~
            AG.init(this);                                         //~1402I~
            if (AG.osVersion<AG.HONEYCOMB)  //<android3=api-11     //~v1D1I~
            {                                                      //~v1D1I~
            	requestWindowFeature(Window.FEATURE_LEFT_ICON);    //~v1D1I~
//				msgidGreeting=R.string.Greeting;                   //~v1D1I~
            }                                                      //~v1D1I~
//          else                                                   //~v1D1I~
//				msgidGreeting=R.string.Greeting11;                 //~v1D1I~
                                                                   //~1329I~
            Dump.openEx("dump.dat");	//write exception only     //~1B03R~
                                                                   //~1329I~
            AG.ajagov=new AjagoView();                             //~1329R~
            AG.ajagoMenu=new AjagoMenu();                              //~1125I~//~1329R~
            AG.ajagoBT=AjagoBT.createAjagoBT();                    //~v107R~
            AG.ajagoMain=new AjagoMain();                              //~1122I~//~1329R~
            AG.ajagov.startMain();                                     //~1122R~//~1329R~
            AG.status=AG.STATUS_MAINFRAME_OPEN;                   //~1120M~//~1212R~//~1329R~
//          if (AG.startupCtr<GREETING_LONG)                       //+1AgdR~
////          AjagoView.showToastLong(R.string.Greeting);                    //~1314I~//~1329R~//+1AgdR~
//            AjagoView.showToastLong(msgidGreeting);              //+1AgdR~
//          else                                                   //+1AgdR~
//          if (AG.startupCtr<GREETING_SHORT)                      //+1AgdR~
////          AjagoView.showToast(R.string.Greeting);              //+1AgdR~
//            AjagoView.showToast(msgidGreeting);                  //+1AgdR~
        }                                                          //~1329I~
        catch(Exception e)                                         //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"onCreate");                            //~1329I~
        }                                                          //~1329I~
    }
//*************************                                        //~1A65I~
    @Override                                                      //~v107I~
    public synchronized void onResume() {                          //~v107I~
        super.onResume();                                          //~v107I~
      try                                                          //~1A65I~
      {                                                            //~1A65I~
        if(Dump.Y) Dump.println("+ ON RESUME +");                  //~v107I~
        // Performing this check in onResume() covers the case in which BT was//~v107I~
        // not enabled during onStart(), so we were paused to enable it...//~v107I~
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.//~v107I~
        if (AG.ajagoBT!=null)                                      //~v107I~
			AG.ajagoBT.resume();                                   //~v107R~
        if (WDA.SWDA!=null)                                        //~1A65I~
			WDA.SWDA.onResume();                                   //~1A65I~
//       if (AG.swNFCBT)                                             //~1Ab7I~//~1Ab8R~
//        DialogNFCSelect.onResume();                                //~1Ab7I~//~1Ab8R~
//       else                                                        //~1Ab7I~//~1Ab8R~
		WDANFC.onResume();                                         //~1A6sR~
      }                                                            //~1A65I~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
      	Dump.println(e,"AMain:onResume");                          //~1A65I~
      }                                                            //~1A65I~
    }                                                              //~v107I~
//*************************                                        //~1A65I~
    @Override                                                      //~1A65I~
    public synchronized void onPause() {                           //~1A65I~
        super.onPause();                                           //~1A65I~
    	try                                                        //~1A65I~
        {                                                          //~1A65I~
        	if(Dump.Y) Dump.println("+ ON PAUSE +");               //~1A65I~
        	if (WDA.SWDA!=null)                                    //~1A65I~
				WDA.SWDA.onPause();                                //~1A65I~
//           if (AG.swNFCBT)                                         //~1Ab7I~//~1Ab8R~
//             DialogNFCSelect.onPause();                            //~1Ab7I~//~1Ab8R~
//           else                                                    //~1Ab7I~//~1Ab8R~
			WDANFC.onPause();                                      //~1A6sR~
        }                                                          //~1A65I~
        catch(Exception e)                                         //~1A65I~
        {                                                          //~1A65I~
        	Dump.println(e,"AMain:onPause");                       //~1A65I~
        }                                                          //~1A65I~
    }                                                              //~1A65I~
//*************************                                        //~1413I~
    @Override                                                      //~1413I~
    public void onDestroy()                                        //~1413I~
	{                                                              //~1413I~
        super.onDestroy();                                         //~1413I~
        if (Dump.Y) Dump.println("OnDestroy");                     //~v110I~
//        destroying=true;                                           //~1413I~//~v110R~
//        try                                                        //~1513I~//~v110R~
//        {                                                          //~1513I~//~v110R~
//            if (Dump.Y) Dump.println("OnDestroy");                 //~1513R~//~v110R~
//            destroyClose(); //nop if after closeFinish() was done  //~@@@@R~//~v110R~
//            if (Go.F!=null) //MainFrame instance                   //~1513R~//~v110R~
//                Go.F.doclose(); //write option parameter then exit //~1513R~//~v110R~
////            if (AG.ajagoBT!=null)                                  //~v107I~//~v110R~
////                AG.ajagoBT.destroy();                              //~v107R~//~v110R~
//        }                                                          //~1513I~//~v110R~
//        catch(Exception e)                                         //~1513I~//~v110R~
//        {                                                          //~1513I~//~v110R~
//            Dump.println(e,"onDestroy");                           //~1513I~//~v110R~
//        }                                                          //~1513I~//~v110R~
//        Dump.close();                                              //~1413I~//~v110R~
        AjagoUtils.exit(0,true);	//System.exit() to kill myself to clear static variable//~1413I~
    }                                                              //~1413I~
//*************************                                        //~@@@@I~//~v110I~
//*************************                                        //~1120I~
    @Override                                                      //~1120I~
    public void onConfigurationChanged(Configuration Pcfg)         //~1120I~
	{                                                              //~1120I~
        super.onConfigurationChanged(Pcfg);                        //~1120I~//~1413R~
    	try                                                        //~1513I~
        {                                                          //~1513I~
            if (Dump.Y) Dump.println("Ajoagc configuration changed");        //~1120I~//~1513R~
            AG.ajagov.getScreenSize();                             //~1513R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onConfiurationChanged");               //~1513I~
        }                                                          //~1513I~
	}                                                              //~1120I~
////*************************                                        //~1109I~//~v107R~
//    protected void onSizeChaged(int ww,int hh,int oldww,int oldhh) //~1109I~//~v107R~
//    {                                                              //~1109I~//~v107R~
//        AG.scrW=ww;                                                //~1109R~//~v107R~
//        AG.scrH=hh;                                                //~1109R~//~v107R~
//        if (Dump.Y) Dump.println("Ajoagc:onSizeChanged w="+ww+",h="+hh);//~1506R~//~v107R~
//        AG.landscape=(ww>hh);                                      //~1109I~//~v107R~
//    }                                                              //~1109I~//~v107R~
//**ContextMenu***********************                             //~1121I~
    @Override                                                      //~1121I~
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo info)//~1121R~
    {                                                              //~1121I~
	    super.onCreateContextMenu(menu,view,info);                 //~1121I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
	        AG.ajagoMenu.onCreateContextMenu(menu,view,info);      //~1513R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onCreteContextMenu");                  //~1513I~
        }                                                          //~1513I~
    }                                                              //~1121I~
//**************                                                   //~1307I~
    @Override                                                      //~1307I~
    public void onContextMenuClosed(Menu Pmenu)                    //~1307I~
    {                                                              //~1307I~
	    super.onContextMenuClosed(Pmenu);                          //~1307I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
	        AG.ajagoMenu.onContextMenuClosed(Pmenu);               //~1513R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onContextMenuClosed");                 //~1513I~
        }                                                          //~1513I~
    }                                                              //~1307I~
//*************************                                      //~1121I~//~1128R~
    @Override                                                      //~1121I~
    public boolean onContextItemSelected(MenuItem item)            //~1121I~
    {                                                              //~1121I~
        boolean rc=false;                                                //~1513I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
            rc=AG.ajagoMenu.onContextItemSelected(item);           //~1513R~
            if (!rc)    //not processed                            //~1513R~
                rc=super.onContextItemSelected(item);              //~1513R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onContextItemChanged");                //~1513I~
        }                                                          //~1513I~
        return rc;                                                 //~1121I~
    }                                                              //~1121I~
//*************************                                      //~1122I~//~1128R~
    @Override                                                      //~1122I~
    public void onTabChanged(String Ptabid)                        //~1122I~
    {                                                              //~1122I~
    	if (Dump.Y) Dump.println("onTabChanged "+Ptabid);          //~1506R~
        if (AG.ajagov==null)                                      //~1329R~
            return;                                                //~1329I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
    		AG.ajagov.onTabChanged(Ptabid);                        //~1513R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onContextItemChanged");                //~1513I~
        }                                                          //~1513I~
    }
//*************************                                        //~1128I~
    @Override
    public void onWindowFocusChanged(boolean Phasfocus)
    {
    	try                                                        //~1513I~
        {                                                          //~1513I~
            if (Dump.Y) Dump.println("WindowFocusChanged "+Phasfocus);//~1513R~//~1B03M~
            if (AG.status<AG.STATUS_MAINFRAME_OPEN)                //~1513R~
                return;                                            //~1513R~
            AG.ajagov.windowFocusChanged(Phasfocus);               //~1513R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onWindowFocusChanged");                //~1513I~
        }                                                          //~1513I~
    }
//*************************                                        //~1128I~
//  @Override apilevel5                                            //~1128I~
    public void onAttachedToWindow()                               //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("OnAttachedToWindow");            //~1506R~
    }                                                              //~1128I~
//  @Override qapilevel5                                           //~1128I~
    public void onDetachedFromWindow()                             //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("OnDetachedFromWindow");          //~1506R~
    }                                                              //~1128I~
    @Override                                                      //~1128I~
    public void onContentChanged()                                 //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("OnContextChanged");              //~1506R~
    }                                                              //~1128I~
    //~1122I~
//*********************************************                                               //~0914I~//~1212I~
//* callback after OnKey if the view has focus                     //~1212I~
//*********************************************                    //~1212I~
    @Override                                                      //~0914I~//~1212I~
    public boolean onKeyDown(int keyCode,KeyEvent event)           //~1212I~
	{                                                              //~1212I~
        if (AG.status<AG.STATUS_MAINFRAME_OPEN)                    //~1329I~
            return false;                                                //~1329I~
        try                                                        //~1329I~
        {                                                          //~1329I~
        	if (AjagoKey.onKeyDown(keyCode,event))  //true//~0A05I~//~1212I~//~1329R~
            	return true;                                       //~0A05I~//~1212I~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"Ajagoc:OnKeyDown");                      //~1329I~
        }                                                          //~1329I~
        return super.onKeyDown(keyCode,event);                     //~0914I~//~1212I~
    }                                                              //~0914I~//~1212I~
//******************                                               //~0914I~//~1212I~
    @Override                                                      //~0914I~//~1212I~
    public boolean onKeyUp(int keyCode,KeyEvent event)             //~1212I~
	{                                                              //~1212I~
        if (AG.status<AG.STATUS_MAINFRAME_OPEN)                    //~1329I~
            return false;                                                //~1329I~
        try                                                        //~1329I~
        {                                                          //~1329I~
        	if (AjagoKey.onKeyUp(keyCode,event))    //true//~1212I~//~1329R~
            	return true;                                       //~1212I~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"Ajagoc:OnKeyUp");                        //~1329I~
        }                                                          //~1329I~
        return super.onKeyUp(keyCode,event);                       //~0914I~//~1212I~
    }                                                              //~0914I~//~1212I~
//******************                                               //~0914I~//~1212I~
    @Override                                                      //~0914I~//~1212I~
    public boolean onTouchEvent(MotionEvent event)                 //~1212I~
	{                                                              //~1212I~
    	int flag,action;                                               //~@@@@I~//~1212I~
    	Point point=new Point();                                           //~@@@@I~//~1212I~
    //********************                                         //~@@@@I~//~1212I~
        if (AG.status<AG.STATUS_MAINFRAME_OPEN)                    //~1329I~
            return false;                                                //~1329I~
    	if (Dump.Y) Dump.println("Ajagoc:OnTouch action="+event.getAction()+",x="+event.getX()+",y="+event.getY());//~1506R~
        try                                                        //~1329I~
        {                                                          //~1329I~
            if (AG.status>0)                                           //~1212I~//~1329R~
            {                                                          //~@@@@I~//~1212I~//~1329R~
                point.x=(int)event.getX();                             //~@@@@I~//~1212I~//~1329R~
                point.y=(int)event.getY();                             //~@@@@I~//~1212I~//~1329R~
                action=event.getAction();                              //~@@@@R~//~1212I~//~1329R~
                if (action!=MotionEvent.ACTION_OUTSIDE)                //~@@@@I~//~1212I~//~1329R~
                {                                                      //~@@@@I~//~1212I~//~1329R~
                    if (action==MotionEvent.ACTION_DOWN)              //~@@@@R~//~1212I~//~1329R~
                        flag=1;                                        //~@@@@I~//~1212I~//~1329R~
                    else                                               //~@@@@I~//~1212I~//~1329R~
                    if (action==MotionEvent.ACTION_UP)                //~@@@@I~//~1212I~//~1329R~
                        flag=0;                                        //~@@@@I~//~1212I~//~1329R~
                    else                                               //~@@@@I~//~1212I~//~1329R~
                        flag=-1;                                       //~@@@@I~//~1212I~//~1329R~
                    if (flag>=0)                                       //~@@@@I~//~1212I~//~1329R~
                        if (AjagoKey.onTouch(action,point))         //~@@@@R~//~1212R~//~1413R~
                            return true;                                               //~0914I~//~@@@@R~//~1212I~//~1329R~
                }                                                      //~@@@@I~//~1212I~//~1329R~
            }                                                          //~@@@@I~//~1212I~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"Ajagoc:OnTouch");                        //~1329I~
        }                                                          //~1329I~
        return false;                                              //~@@@@I~//~1212I~
    }                                                              //~0914I~//~1212I~
//*******************                                              //~1314I~
//* Option Menu *****                                              //~1314I~
//*******************                                              //~1314I~
//*** called only once***                                          //~1326I~
    @Override                                                      //~1314I~
    public boolean onCreateOptionsMenu(Menu Pmenu)                 //~1314R~
	{                                                              //~1314I~
        try                                                        //~1329I~
        {                                                          //~1329I~
	        AG.ajagoMenu.onCreateOptionMenu(Pmenu);                    //~1314R~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"Ajagoc:OnCreateOptionMenu");             //~1329I~
        }                                                          //~1329I~
    	return super.onCreateOptionsMenu(Pmenu);                          //~1314R~//~1326I~
	}                                                              //~1314I~
//*** called each time to display                                  //~v107I~
    @Override                                                      //~v107I~
    public boolean onPrepareOptionsMenu(Menu Pmenu)                //~v107I~
	{                                                              //~v107I~
        try                                                        //~v107I~
        {                                                          //~v107I~
	        AG.ajagoMenu.onPrepareOptionMenu(Pmenu);               //~v107I~
        }                                                          //~v107I~
        catch (Exception e)                                        //~v107I~
        {                                                          //~v107I~
        	Dump.println(e,"Ajagoc:OnPrepareOptionMenu");          //~v107I~
        }                                                          //~v107I~
    	return super.onPrepareOptionsMenu(Pmenu);                  //~v107I~
	}                                                              //~v107I~
//**                                                               //~1314I~
    @Override                                                      //~1314I~
    public boolean onOptionsItemSelected(MenuItem item)            //~1314I~
	{                                                              //~1314I~
        try                                                        //~1329I~
        {                                                          //~1329I~
	    	AG.ajagoMenu.onOptionMenuSelected(item);		//setup menu//~1314I~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"Ajagoc:OnOptionItemSelected");           //~1329I~
        }                                                          //~1329I~
    	return true;                                               //~1314I~
	}                                                              //~1314I~
//****************                                                 //~1218I~
//****************                                                 //~1314I~
//****************                                                 //~1314I~
    public static boolean isTerminating()                          //~1218I~
    {                                                              //~1218I~
    	return destroying;                                         //~1218I~
    }                                                              //~1218I~
//****************                                                 //~1411I~
//*Hide StatusBar                                                  //~1412I~
//    getWindow().addFlag(Windowmanager.LayoutParams.FLAG_FULLSCREEN);//~1412I~
//    getWindow().clearFlag(Windowmanager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);//~1412I~
//***************************************************************************//~v107I~
	@Override                                                      //~v107I~
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//~v107I~
        if(Dump.Y) Dump.println("onActivityResult req="+requestCode+",result="+ resultCode);//~v107I~
		if (AG.ajagoBT!=null)                                      //~v107I~
			AG.ajagoBT.activityResult(requestCode,resultCode,data);//~v107R~
//  	if (AG.aWDANFC!=null)                                      //~1A6aI~//~1A6sR~
//  		AG.aWDANFC.activityResult(requestCode,resultCode,data);//~1A6aI~//~1A6sR~
    }                                                              //~v107I~
//***************************************************************************//~v110I~
    public void destroyClose()                                     //~@@@@I~//~v110M~
    {                                                              //~@@@@I~//~v110M~
        if (Dump.Y) Dump.println("destroyClose");//~@@@@I~         //~v110I~
//        if (AjagoUtils.finished)                                        //~@@@@I~//~v110I~
//            return;                                                //~@@@@I~//~v110I~
        AG.status=AG.STATUS_STOPFINISH;                            //~v110I~
	    URunnable.setRunFunc(this/*RunnableI*/,0/*sleep*/,null/*parm*/,0/*phase*/);//~@@@@R~//~v110M~
    }                                                              //~@@@@I~//~v110M~
//*************************                                        //~@@@@I~//~v110M~
//*callback from Runnable *                                        //~@@@@I~//~v110M~
//*************************                                        //~@@@@I~//~v110M~
    public void runFunc(Object Pparmobj,int Pphase)                //~@@@@I~//~v110M~
    {                                                              //~@@@@I~//~v110M~
        int wait=0,wait2=100;                                      //~v110I~
    //*********************                                        //~v110I~
    	if (Dump.Y) Dump.println("Ajagoc runfunc phase="+Pphase);   //~@@@@I~//~v110I~
    	if (Pphase==0)	//initial call,close socket streamIO       //~@@@@I~//~v110M~
        {                                                          //~@@@@I~//~v110M~
			Global.writeparameter(".go.cfg");                      //~1B03I~
	    	if (Dump.Y) Dump.println("destroyClose runfunc phase=0 closeStream");//~v110I~
        	if (AG.aPartnerFrame!=null)                        //~@@@2I~//~@@@@I~//~v110M~
            {                                                      //~@@@@I~//~v110M~
            	AG.aPartnerFrame.closeStream(); //before close socket//~@@@2I~//~@@@@R~//~v110M~
            }                                                      //~@@@@I~//~v110M~
            URunnable.setRunFunc(this/*RunnableI*/,wait2/*sleep ms*/,null/*parm*/,1/*phase*/);//~@@@@R~//~v110I~
            return;                                            //~@@@@I~//~v110I~
        }                                                          //~@@@@I~//~v110M~
    	if (Pphase==1)	//BT                                       //~v110I~
        {                                                          //~v110I~
    		if (Dump.Y) Dump.println("destroyClose runfunc phase=1 stop BT");//~v110I~
	        if (AG.ajagoBT!=null)                                      //~v110I~
            {                                                      //~v110I~
    	        if (AG.ajagoBT.destroy())                          //~v110R~
	            	wait=wait2;                                    //~v110I~
	            URunnable.setRunFunc(this/*RunnableI*/,wait/*sleep ms*/,null/*parm*/,2/*phase*/);//~v110R~
    	        return;                                            //~v110I~
            }                                                      //~v110I~
        }                                                          //~v110I~
    	if (Dump.Y) Dump.println("destroyClose call finish()");    //~v110I~
		finish();	//shedule ondestroy                            //~v110I~
    }                                                              //~@@@@I~//~v110M~
	//*************************************************************************//~1A6jI~
    @Override                                                      //~1A6jI~
    public void onNewIntent(Intent intent)                         //~1A6jI~
    {                                                              //~1A6jI~
        if (Dump.Y) Dump.println("Amain:onNewIntent");             //~1A6jI~
       if (AG.swNFCBT)                                             //~1Ab7I~
       {                                                           //~1Ab7I~
        if (DialogNFCSelect.onNewIntent(intent))                   //~1Ab7I~
        	return;                                                //~1Ab7I~
       }                                                           //~1Ab7I~
       else                                                        //~1Ab7I~
       {                                                           //~1Ab7I~
        if (DialogNFC.onNewIntent(intent))                         //~1A6sR~
        	return;                                                //~1A6sR~
       }                                                           //~1Ab7I~
//  	AjagoView.showToast(R.string.WarningIgnoredNewIntent);         //~1A6jI~//~1AbkR~
    	AjagoView.showToastLong(R.string.WarningIgnoredNewIntent); //~1AbkI~
    }                                                              //~1A6jI~
}//class                                                           //~1109R~