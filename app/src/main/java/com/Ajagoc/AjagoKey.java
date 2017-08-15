//*CID://+1Ag1R~:                             update#=    1;       //+1Ag1I~
//*********************************************************************//+1Ag1I~
//1Ag1 2016/10/06 Change Top panel. set menu panel as tabwidget.   //+1Ag1I~
//*********************************************************************//+1Ag1I~
package com.Ajagoc;

import jagoclient.Dump;
import jagoclient.Global;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import com.Ajagoc.awt.ActionEvent;
import com.Ajagoc.awt.ActionListener;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.KeyEvent;
import com.Ajagoc.awt.KeyListener;
import com.Ajagoc.awt.Window;
                                                                   //~1401I~
import com.Ajagoc.AjagoKeyI;                                       //~1401R~

public class  AjagoKey                                                      //~1114I~//~1212R~
    implements android.view.View.OnKeyListener 
{                                                              //~1113I~//~1114I~
	public static final int KEYCODE_BACK   =android.view.KeyEvent.KEYCODE_BACK;   //back//~v@@@R~//~1212I~
//***                                                              //~1212I~
    private KeyListener kl;                                        //~1507R~
    private ActionListener al;                                     //~1507R~
    private static boolean SsearchEnter=true;                      //~1507I~
    private static boolean Soptionchecked;                         //~1507I~
                                                                   //~1507I~
    @Override                                                      //~1116I~
    public boolean onKey(View Pview,int Pkeycode,android.view.KeyEvent Pevent)//~v@@@I~//~1116I~
    {                                                      //~v@@@I~//~1116I~
        int action;                                //~v@@@I~//~1116I~
        boolean rc=false;                                          //~1317I~
        //**********************                           //~v@@@I~//~1116I~
        try                                                        //~1430I~
        {                                                          //~1430I~
            if (Dump.Y) Dump.println("Onky keycode="+Pkeycode+",viewid="+Integer.toString(Pview.getId(),16)+",isfocus="+Pview.isFocused()); //~v@@@I~//~1116I~//~1506R~
            action=Pevent.getAction();                          //~v@@@R~//~1430R~
            switch(action)                                     //~v@@@I~//~1430R~
            {                                                  //~v@@@I~//~1430R~
            case android.view.KeyEvent.ACTION_UP:                           //~v@@@I~//~1430R~
                rc=onKeyUp(Pview,Pkeycode,Pevent);                 //~1430R~
                break;                                         //~v@@@I~//~1430R~
            case android.view.KeyEvent.ACTION_DOWN:                //~1430R~
                rc=onKeyDown(Pview,Pkeycode,Pevent);               //~1430R~
                break;                                             //~1430R~
            default:                                           //~v@@@I~//~1430R~
            }                                                  //~v@@@I~//~1430R~
        }                                                          //~1430I~
        catch(Exception e)                                         //~1430I~
        {                                                          //~1430I~
            Dump.println(e,"AkagoKey.OnKey exception");            //~1430I~
        }                                                          //~1430I~
        return rc;           //~v@@@I~                             //~1317R~
    }                                                      //~v@@@I~//~1116I~
//************                                                     //~1114I~
    public static void optionChanged(boolean Pflag)                //~1507I~
    {                                                              //~1507I~
        Soptionchecked=true;                                       //~1507I~
		SsearchEnter=Pflag;                                        //~1507I~
	}                                                              //~1507I~
//************                                                     //~1507I~
    static public AjagoKey addKeyListener(View Pv)             //~1114I~//~1116R~//~1212R~
    {                                                              //~1114I~
    	if (!Soptionchecked)                                       //~1507I~
        {                                                          //~1507I~
        	Soptionchecked=true;                                   //~1507I~
			SsearchEnter=Global.getParameter(AjagoMenu.SEARCHKEY_CFGKEY,true);//~1507I~
        }                                                          //~1507I~
    	AjagoKey ajagoKey=new AjagoKey();                          //~1114I~
        Pv.setOnKeyListener(ajagoKey);                              //~1116I~
        Pv.setFocusableInTouchMode(true);	//DOC says "FocusableInTouch then Focusable"//~1325I~
        Pv.setFocusable(true);                                     //~1127I~
        addFocusChangeListener(Pv);                                //~1129I~
        if (Dump.Y) Dump.println("addKeyListener viewid="+Integer.toString(Pv.getId(),16)+",isfocus="+Pv.isFocused());//~1506R~
        return ajagoKey;                                            //~1114I~//~1212R~
    }                                                              //~1114I~
//************                                                     //~1129I~
    static public void addFocusChangeListener(View Pv)             //~1129I~
    {                                                              //~1129I~
        if (Dump.Y) Dump.println("addFocuschange Listener viewid="+Integer.toString(Pv.getId(),16));//~1506R~
    	Pv.setOnFocusChangeListener(                                //~1129I~
        	new OnFocusChangeListener()                       //~1129I~
            	{                                                  //~1129I~
                	@Override                                      //~1129I~
                    public void onFocusChange(View Pview,boolean Phasfocus)//~1129I~
                    {                                              //~1129I~
				        if (Dump.Y) Dump.println("focus changed "+Phasfocus+",viewid="+Integer.toString(Pview.getId(),16));//~1506R~
                    }                                              //~1129I~
                }                                                  //~1129I~
                				);                                 //~1129I~
    }                                                              //~1129I~
//************                                                     //~1114I~
    static public AjagoKey addKeyListener(View Ptv,KeyListener Pkl) //~1127I~//~1212R~
    {                                                              //~1114I~
    	AjagoKey ajagoKey=addKeyListener(Ptv);                               //~1114I~//~1212R~
        ajagoKey.setKeyListener(Pkl);                               //~1114I~//~1212R~
        return ajagoKey;                                            //~1114I~//~1212R~
    }                                                              //~1114I~
//************                                                     //~1114I~
    public void setKeyListener(KeyListener Pkl)                    //~1114R~
    {                                                              //~1114I~
    	kl=Pkl;                                                    //~1114I~
    }                                                              //~1114I~
//************                                                     //~1114I~
    public void setActionListener(ActionListener Pal)              //~1114R~
    {                                                              //~1114I~
        if (Dump.Y) Dump.println("AjagoKey addActionListener="+Pal.toString());//~1506R~
    	al=Pal;                                                    //~1114I~
    }                                                              //~1114I~
//************                                                     //~1114I~
    private boolean actionPreform(View v,int keycode,android.view.KeyEvent ev)//~1114I~
    {                                                              //~1114I~
        if (keycode==KeyEvent.VK_ENTER                             //~1507R~
        ||  (keycode==KeyEvent.KEY_ENTER2 && SsearchEnter)         //~1507I~
        )                                                          //~1507I~
        {                                                          //~1114I~
            ActionEvent.actionPerformedKey(al,v,ev);               //~1408I~
        }                                                          //~1114I~
        return false;                                              //~1114I~
    }                                                              //~1114I~
//************                                                     //~1114I~
    public boolean onKeyDown(View v,int keycode,android.view.KeyEvent ev)//~1116I~
    {                                                          //~1113I~//~1114I~//~1116R~
    	boolean rc=false;                                          //~1317I~
    	if (Dump.Y) Dump.println("keyDown code="+keycode);                 //~1114I~//~1506R~
        if (kl!=null)                                       //~1113I~//~1114I~//~1116R~
        {                                                      //~1113I~//~1114I~//~1116R~
            try                                                    //~1114I~//~1116R~
            {                                                      //~1114I~//~1116R~
                KeyEvent kev=new KeyEvent(ev);                     //~1428R~
                if (kev.exausted)	//checking long press for Fn   //~1428I~
                    rc=true;                                       //~1428I~
                kl.keyTyped(kev);                         //~1114R~//~1427R~
                if (kl instanceof AjagoKeyI)                //~1317I~//~1401R~
                	rc=((AjagoKeyI)kl).keyPressedRc(kev);       //~1317I~//~1427R~
                else                                               //~1317I~
                	kl.keyPressed(kev);                                 //~1113R~//~1114R~//~1427R~
                frameKeyPress(kl,kev);                     //~1427I~
            }                                                      //~1114I~//~1116R~
            catch(Exception e)                                     //~1114I~//~1116R~
            {                                                      //~1114I~//~1116R~
                e.printStackTrace();                                   //~0A21I~//~1114I~//~1116R~
                Dump.println(e,"OnKeyDown:OnClickButtonAction exception");   //~v@@@I~//~1114I~//~1116R~//~1328R~
            }                                                      //~1114I~//~1116R~
        }                                                      //~1113I~//~1114I~//~1116R~
    	if (Dump.Y) Dump.println("keyDown rc="+rc);                //~1506R~
        return rc;   //android process                      //~1113R~//~1114I~//~1317R~
    }                                                          //~1113I~//~1114I~//~1116R~
//************                                                     //~1427I~
    public void frameKeyPress(KeyListener Pkl,KeyEvent Pev)        //~1427I~
    {                                                              //~1427I~
    	if (AG.currentIsDialog())                                  //~1427I~
        	return;                                                //~1427I~
    	Frame f=AG.getCurrentFrame();                              //~1427I~
        KeyListener kl=f.framekeylistener;                         //~1427I~
        if (kl==null||kl==Pkl)                                     //~1427I~
        	return;                                                //~1427I~
        if (Dump.Y) Dump.println("Ajagokey call frame KeyListener press");//~1506R~
        kl.keyPressed(Pev);                                        //~1427I~
    	return;                                                    //~1427I~
    }                                                              //~1427I~
//************                                                     //~1114I~//~1116R~//~1212R~
    public boolean onKeyUp(View v,int keycode,android.view.KeyEvent ev)//~1116I~
    {                                                          //~1113I~//~1114I~//~1116R~
    	boolean rc=false;                                          //~1317I~
    	if (Dump.Y) Dump.println("keyUp code="+keycode);           //~1506R~
        try                                                        //~1114I~//~1116R~
        {
            KeyEvent kev=new KeyEvent(v,ev);   //use v to chk Fn(n+enter by soft kbd)//~1428R~
            if (kl!=null)                                          //~1114R~//~1116R~
            {                                                      //~1114R~//~1116R~
                    //~1427I~
                if (kl instanceof AjagoKeyI)                //~1317I~//~1401R~
                	rc=((AjagoKeyI)kl).keyReleasedRc(kev);         //~1317I~//~1427R~
                else                                               //~1317I~
                	kl.keyReleased(kev);                                //~1113R~//~1114R~//~1427R~
            }                                                      //~1114R~//~1116R~
            frameKeyRelease(kl,kev);                       //~1427I~
            if (al!=null)                                          //~1114R~//~1116R~
	            if (!kev.enterFn)	//Enterkey is encloser of Fn   //~1428I~
                {                                                  //~1430I~
        			if (Dump.Y) Dump.println("AjagoKey call ActionListener="+al.toString());//~1506R~
    	            actionPreform(v,keycode,ev);                       //~1114R~//~1428R~
                }                                                  //~1430I~
            if (kev.exausted)  //Enter key treated as Fn encloser  //~1428R~
                rc=true;                                           //~1428R~
        }                                                          //~1114I~//~1116R~
        catch(Exception e)                                         //~1114I~//~1116R~
        {                                                          //~1114I~//~1116R~
            e.printStackTrace();                                   //~1114I~//~1116R~
            if (Dump.Y) Dump.println("OnClickButtonAction exception"+e.toString());//~1114I~//~1506R~
        }                                                          //~1114I~//~1116R~
        if (Dump.Y) Dump.println("OnKeyUp rc="+rc);               //~1506R~
        return rc;   //android process                      //~1113I~//~1114I~//~1317R~
    }                                                          //~1113I~//~1114I~//~1116R~
//************                                                     //~1427I~
    public void frameKeyRelease(KeyListener Pkl,KeyEvent Pev)      //~1427I~
    {                                                              //~1427I~
    	if (AG.currentIsDialog())                                  //~1427I~
        	return;                                                //~1427I~
    	Frame f=AG.getCurrentFrame();                              //~1427I~
        KeyListener kl=f.framekeylistener;                         //~1427I~
        if (kl==null||kl==Pkl)                                     //~1427I~
        	return;                                                //~1427I~
        if (Dump.Y) Dump.println("Ajagokey call frame KeyListener Release");//~1506R~
        kl.keyReleased(Pev);                                       //~1427I~
    	return;                                                    //~1427I~
    }                                                              //~1427I~
//*******************************************                      //~1212I~
//*OnKeyDown from Ajagoc (regadless View)                          //~1212I~//~1309R~
//*******************************************                      //~1212I~
    public static boolean onKeyDown(int Pkeycode,android.view.KeyEvent Pev)//~1212I~
    {                                                              //~1212I~
    	boolean rc=false;                                          //~1212I~
    //********************                                         //~1212I~
    	if (Dump.Y) Dump.println("OnkeyDown(No View) code="+Pkeycode+",Unicode="+Integer.toString(Pev.getUnicodeChar(),16));//~1506R~
        switch(Pkeycode)                                                  //~0102I~//~1212M~
        {                                                              //~0102I~//~1212M~
        case KEYCODE_BACK: //back key                                 //~v@@@R~//~1212M~
            if (Frame.popFrame()==null)                            //~1212I~//~1309R~//~1314R~
            {                                                      //~1212I~
//              AG.ajagoMenu.confirmStop();                        //+1Ag1R~
                AG.ajagoMenu.closeFrame();//Tab switch             //+1Ag1I~
            }                                                      //~1212I~
			rc=true;	//bypass android process                   //~1212I~
        	break;                                                     //~v@@@I~//~1212M~
        case KeyEvent.KEY_ENTER2: //ignore SEARCH                  //~1430R~
        	if (SsearchEnter)                                      //~1507I~
    			rc=true;	//bypass android process to get KeyUp  //~1507R~
        	break;                                                 //~1430R~
        }                                                          //~1212M~
        return rc;   //android process                             //~1212R~
    }                                                              //~1212I~
//*******************************************                      //~1212I~
//*OnKeyUp from Ajagoc (regadress View)                            //~1212I~
//*******************************************                      //~1212I~
    public static boolean onKeyUp(int Pkeycode,android.view.KeyEvent Pev) //~1212I~
    {                                                              //~1212I~
    	boolean rc=false;                                          //~1212I~
    //********************                                         //~1212I~
    	if (Dump.Y) Dump.println("OnkeyUp(No View) code="+Pkeycode);//~1506R~
        if (Pkeycode==KeyEvent.KEY_ENTER2)//ignore SEARCH          //~1430I~
        	if (SsearchEnter)                                      //~1507I~
            	rc=true;                                           //~1507R~
        return rc;   //android process                             //~1212R~
    }                                                              //~1212I~
//*******************************************                      //~1212I~
//*OnKeyUp from Ajagoc (regadress View)                            //~1212I~
//*******************************************                      //~1212I~
    public static boolean onTouch(int Paction,Point Ppoint)        //~1212I~
    {                                                              //~1212I~
        int xx,yy,xxmenu,titlebarheight;                           //~1422R~
        Point titlebarpos;                                         //~1413R~
    //****************                                             //~1412I~
    	if (Dump.Y) Dump.println("OnTouch(No View) action="+Paction+" point=("+Ppoint.x+","+Ppoint.y+")");//~1506R~
    	xx=Ppoint.x; yy=Ppoint.y;                                  //~1412I~
        titlebarpos=AjagoView.getTitleBarPosition();              //~1413I~
        titlebarheight=titlebarpos.y-titlebarpos.x;                //~1422I~
        if (yy>=titlebarpos.x && yy<titlebarpos.y)                 //~1413R~
        {                                                          //~1412I~
        	if (Paction==MotionEvent.ACTION_UP)	//Up               //~1413R~
            {                                                      //~1412I~
				int elapsed=AjagoUtils.getElapsedTimeMillis(AjagoUtils.TSID_TITLE_TOUCH);//~1412I~
                boolean swLongPress=elapsed>AG.TIME_LONGPRESS;     //~1412I~
                if (swLongPress)                                   //~1412I~
                {                                                  //~1412I~
                	AG.activity.openOptionsMenu();                             //~1412I~
                	return true;                                   //~1412I~
                }                                                  //~1412I~
                xxmenu=titlebarheight*2;                           //~1422R~
                if (xx<xxmenu)                                     //~1412R~
                    Window.wrapFrameByTouch(-1);                   //~1412R~
                else                                               //~1412R~
                if (xx>=AG.scrWidth-xxmenu*2)                      //~1504R~
                {                                                  //~1504I~
                	if (xx>AG.scrWidth-xxmenu                      //~1504I~
                    &&  !AG.portrait                               //~1504R~
                    &&  AG.getCurrentFrame().framelayoutresourceid==AG.frameId_LocalViewer//~1504I~
                    )                                              //~1504I~
                    	;	//avoid near the setstone button       //~1504I~
                    else                                           //~1504I~
	                    Window.wrapFrameByTouch(1);                //~1504I~
                }                                                  //~1504I~
                else                                               //~1412R~
                    AG.ajagoMenu.showContextMenu();                //~1412R~
            }                                                      //~1412I~
            else                                                   //~1412I~
        	if (Paction==MotionEvent.ACTION_DOWN)	//Down         //~1413R~
            {                                                      //~1412I~
				AjagoUtils.setTimeStamp(AjagoUtils.TSID_TITLE_TOUCH);         //~1412I~
            }                                                      //~1412I~
        }                                                          //~1412I~
        return false;   //android process                          //~1212I~
    }                                                              //~1212I~
}//class                                                           //~1114I~

