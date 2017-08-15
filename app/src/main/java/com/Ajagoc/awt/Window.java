//*CID://+1Ag9R~:                             update#=    9;       //+1Ag9R~
//*******************************************************************************//~1A8hI~
//1Ag9 2016/10/09 Bitmap OutOfMemory;JNI Global reference remains. //+1Ag9I~
//                try to clear ref to bitmap from Image:fieldBitmap, Graphics:targetBitmap, android.Graphics.Canvas(<--Image:androidCanvas, Graphics:androidCanvas)//+1Ag9I~
//1Afp 2016/09/30 duplicate check of open goframe                  //~1AfpI~
//1A8h 2015/03/05 popup OpenPartnerFrame by "Open?" button if hidden//~1A8hI~
//*******************************************************************************//~1A8hI~
package com.Ajagoc.awt;                                                //~1108R~//~1109R~

import jagoclient.Dump;
import jagoclient.board.GoFrame;

import java.util.ArrayList;
import java.util.LinkedList;

import android.graphics.Bitmap;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;

public class Window extends Container //Container-->Window-->Frame                                                 //~1111R~//~1116R~//~1124R~
{                                                                  //~1111I~
    static private LinkedList<Frame> framestack=new LinkedList<Frame>();  //~1314R~
    public static final int WINDOW_CREATED=0;                      //~1128I~
    public static final int WINDOW_OPENED=1;                       //~1128I~
    public static final int WINDOW_ACTIVE=2;                       //~1128R~
    public static final int WINDOW_INACTIVE=3;                     //~1128R~
    public static final int WINDOW_CLOSING=4;                      //~1516R~
    public static final int WINDOW_CLOSED=5;                       //~1516I~
    public static final int WINDOW_RESTORE=6;                      //~1516R~
                                                                   //~1408I~
	public ActionEvent scheduledAE;                                //~1425R~
	public ActionEvent scheduledDialogAE;                          //~1425R~
//  private ArrayList<Bitmap> recycleBitmaps;                      //+1Ag9R~
    private ArrayList<Image> recycleBitmaps;                       //+1Ag9I~
//****************************************                         //~1128I~
//*control frame for windowsListener                               //~1128I~
//****************************************                         //~1128I~
	static public void setVisible(Object Pobject,boolean Pvisible) //~1128I~
    {                                                              //~1128I~
    	int status;                                                //~1128I~
        WindowListener listener;                                   //~1128R~
        Frame frame;                                               //~1128I~
        Dialog dialog;                                             //~1128I~
    //**************                                               //~1128I~
    	if (Dump.Y) Dump.println("Window:setVisible="+Pvisible+",object="+Pobject.toString());//~1511R~
    	if (Pobject instanceof Frame)                              //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible for Frame");//~1506R~
            frame=(Frame)Pobject;                                  //~1128I~
            status=frame.framestatus;                              //~1128R~
        	listener=frame.windowlistener;                         //~1128I~
        }                                                          //~1128I~
        else                                                       //~1128I~
    	if (Pobject instanceof Dialog)                             //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible for Dialog");//~1506R~
            dialog=(Dialog)Pobject;                                //~1128I~
            status=dialog.dialogstatus;                            //~1128I~
        	listener=dialog.windowlistener;                        //~1128I~
        }                                                          //~1128I~
        else                                                       //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible for Unknown");//~1506R~
        	return;                                                //~1128I~
        }                                                          //~1128I~
        if (listener==null)                                        //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible no listener defined");//~1506R~
        	return;                                                //~1128I~
        }                                                          //~1128I~
        if (Pvisible)                                              //~1128I~
        {                                                          //~1128I~
        	if (status==WINDOW_CREATED)                            //~1128I~
            {                                                      //~1128I~
            	status=WINDOW_OPENED;                        //~1128I~
		    	if (Dump.Y) Dump.println("Window:setVisible call windowOpened");//~1506R~
			    kickWindowListener(status,listener);               //~1128I~
            }                                                      //~1128I~
            else                                                   //~1128I~
            if (status!=WINDOW_ACTIVE)                             //~1503R~
            {                                                      //~1503R~
                if (Dump.Y) Dump.println("Window:setVisible call windowActivated");//~1506R~
                status=WINDOW_ACTIVE;                          //~1128R~//~1503R~
                kickWindowListener(status,listener);               //~1503R~
            }                                                      //~1503R~
            else    //reopen                                       //~1503I~
            {                                                      //~1503I~
		    	if (Dump.Y) Dump.println("Window:setVisible on Active:ReOpen");//~1506R~
			    kickWindowListener(WINDOW_RESTORE,listener);       //~1503I~
            }                                                      //~1503I~
        }                                                          //~1128I~
        else                                                       //~1128I~
        {                                                          //~1128I~
        	if (status==WINDOW_ACTIVE||status==WINDOW_OPENED)      //~1128I~
            {                                                      //~1128I~
            	status=WINDOW_INACTIVE;                      //~1128I~
		    	if (Dump.Y) Dump.println("Window:setVisible call windowInactivated");//~1506R~
                kickWindowListener(status,listener);               //~1128I~
            }                                                      //~1128I~
        }                                                          //~1128I~
    }                                                              //~1128I~
//******************                                               //~1128I~
	static public void pushFrame(Frame Pframe)                     //~1128R~
    {                                                              //~1128I~
        if (Pframe.framelayoutview==null)                          //~1218I~
			Pframe.framelayoutview=AjagoView.inflateView(Pframe.framelayoutresourceid);//~1218I~
		if (framestack.size()==0)                                  //~1314I~
        	AG.mainframe=Pframe;                                   //~1314I~
        framestack.add(0,Pframe);                                  //~1314I~
        listStack();                                               //~1329I~
        AG.ajagov.setContentView(Pframe);	//set android context//~1128I~
    	if (Dump.Y) Dump.println("Window pushed ctr="+framestack.size()+",name="+Pframe.framename);                       //~1217I~//~1218R~//~1506R~
    }                                                              //~1128I~
//*********                                                        //~1329I~
	static public void listStack()                     //~1329I~
    {                                                              //~1329I~
		int ctr=framestack.size();                                 //~1329I~
        for (int ii=0;ii<ctr;ii++)                                     //~1329I~
        	if (Dump.Y) Dump.println("listframestack "+framestack.get(ii).framename);//~1506R~
    }                                                              //~1329I~
//******************                                               //~1314I~
	static public Frame getCurrentFrame()                          //~1314I~
    {                                                              //~1314I~
        return framestack.getFirst();                              //~1420R~
    }                                                              //~1314I~
//******************                                               //~1128I~
	static public Frame popFrame(boolean Pclose)                   //~1314I~
    {                                                              //~1314I~
    	return popupFrame(Pclose);                                        //~1314I~
    }                                                              //~1314I~
	static public Frame popFrame(Frame Pframe)                     //~1512I~
    {                                                              //~1512I~
        if (Pframe!=framestack.getFirst())                         //~1512I~
        {                                                          //~1512I~
        	if (!framestack.remove(Pframe))       //not exist      //~1512I~
            	return null;                                       //~1512I~
	    	framestack.addFirst(Pframe);		//push to Top      //~1512I~
        }                                                          //~1512I~
	    return popupFrame(true);                                   //~1512I~
    }                                                              //~1512I~
	static public Frame popFrame()                                 //~1128R~
    {                                                              //~1128I~
    	return popupFrame(false);                                         //~1314I~
    }                                                              //~1314I~
	static public Frame wrapFrameByTouch(int Pdestination)         //~1412I~
    {                                                              //~1412I~
		if (AG.getCurrentDialog()!=null)	//dialog open              //~1412I~
        	return null;				//ignore touch action      //~1412I~
    	if (Pdestination<0)		//back to prev                 //~1412I~
    		return popupFrame(false);                              //~1412I~
        return pushLastFrame();                                    //~1412I~
    }                                                              //~1412I~
                            //~1314I~
	static public Frame popupFrame(boolean Pclose)                                 //~1314I~
    {                                                              //~1314I~
        Frame frame,closeFrame;                                    //~1424R~
        int ctr;                                                   //~1314I~
    //********************:                                        //~1217I~
		ctr=framestack.size();                                     //~1314I~
    	if (Dump.Y) Dump.println("Window pop request ctr="+ctr);                  //~1217I~//~1506R~
    	if (ctr<=1)                                                //~1314R~
        {                                                          //~1330I~
            if (Pclose)                                            //~1330I~
	            AjagoUtils.finish();//~1212I~//~1309R~             //~1330I~
        	return null;   //1:last frame                          //~1128I~//~1314R~
        }                                                          //~1330I~
        frame=framestack.getFirst();	//get top                  //~1424M~
        if (Pclose)                                                //~1424I~
        {                                                          //~1424I~
        	closeFrame=frame;                                      //~1424I~
        	closeFrame.onDestroy();          //before contentview active//~1424R~
        }
        else 
        	closeFrame=null;//~1424I~
        framestack.remove(frame);    //pick from list               //~1314I~
        if (!Pclose)                                               //~1314I~
	        framestack.add(frame);		//wrap to last             //~1314R~
        frame=framestack.getFirst();	//new top                  //~1420R~
		AG.ajagov.setContentView(frame);           //~1128I~
        if (closeFrame!=null)                                                //~1424I~
        	closeFrame.onDestroy2();         //after contentview updated//~1424R~
        frame.onRestore();                                         //~1503R~
    	if (Dump.Y) Dump.println("Window poped old ctr="+ctr+",new top="+(frame.framename==null?"null":frame.framename));                        //~1217I~//~1506R~
		return frame;                                              //~1128I~
    }                                                              //~1128I~
	static public Frame pushLastFrame()                            //~1412I~
    {                                                              //~1412I~
        Frame frame;                                               //~1412I~
        int ctr;                                                   //~1412I~
    //********************:                                        //~1412I~
		ctr=framestack.size();                                     //~1412I~
    	if (Dump.Y) Dump.println("Window pushLast ctr="+ctr);      //~1506R~
    	if (ctr<=1)                                                //~1412I~
        {                                                          //~1412I~
        	return null;   //1:last frame                          //~1412I~
        }                                                          //~1412I~
        frame=framestack.get(ctr-1);	//get last                 //~1412I~
        framestack.remove(frame);       //pick from list           //~1416R~
	    framestack.addFirst(frame);		//push to Top              //~1416R~
		AG.ajagov.setContentView(frame);                           //~1412I~
        frame.onRestore();                                         //~1504I~
    	if (Dump.Y) Dump.println("Window pushLast new top="+(frame.framename==null?"null":frame.framename));//~1506R~
		return frame;                                              //~1412I~
    }                                                              //~1412I~
//******************                                               //~1128I~
	static public void windowClosed(Frame Pframe)                   //~1128I~
    {                                                              //~1128I~
        WindowListener listener=Pframe.windowlistener;              //~1128I~
        if (listener==null)                                        //~1128I~
        	return;                                                //~1128I~
        if (Dump.Y) Dump.println("WindowClosed");                  //~1506R~
        Pframe.framestatus=WINDOW_CLOSED;                           //~1128I~
        kickWindowListener(Pframe.framestatus,listener);            //~1128I~
    }                                                              //~1128I~
//******************                                               //~1128I~
	static public void onFocusChanged(boolean Phasfocus)           //~1513R~
    {                                                              //~1128I~
        if (framestack.isEmpty())                                  //~1314I~
        	return;                                                //~1128I~
    	Frame frame=(Frame)framestack.getFirst();  //get top       //~1420R~
        WindowListener listener=frame.windowlistener;               //~1128I~
        if (listener==null)                                        //~1128I~
        	return;                                                //~1128I~
        if (Dump.Y) Dump.println("Window:onFocusChange Act/Inact="+Phasfocus);//~1506R~
        if (Phasfocus)                                            //~1128I~
        {                                                          //~1128I~
        	if (frame.framestatus==WINDOW_INACTIVE)           //~1128R~
            {                                                      //~1128I~
		    	if (Dump.Y) Dump.println("Window:onFocusChanged call windowActivated");//~1513R~
            	frame.framestatus=WINDOW_ACTIVE;                    //~1128I~
                kickWindowListener(frame.framestatus,listener);    //~1128I~
         	}                                                      //~1128I~
        }                                                          //~1128I~
        else                                                       //~1128I~
        {                                                          //~1128I~
		    if (Dump.Y) Dump.println("Window:onFocusChanged call windowInActivated");//~1513R~
            frame.framestatus=WINDOW_INACTIVE;                      //~1128I~
            kickWindowListener(frame.framestatus,listener);        //~1128I~
        }                                                          //~1128I~
    }                                                              //~1128I~
//***********                                                      //~1128I~
    public static void kickWindowListener(final int Pcase,final WindowListener Plistener)//~1128I~
    {                                                              //~1128I~
        if (AG.isMainThread())                                     //~1128I~
        {                                                          //~1128I~
    		callWindowListener(Pcase,Plistener);  //~1128I~
        	return;                                                //~1128I~
        }                                                          //~1128I~
        AG.activity.runOnUiThread(              //execute on mainthread after posted//~1128I~
            new Runnable()                                         //~1128I~
            {                                                      //~1128I~
                @Override                                          //~1128I~
                public void run()                                  //~1128I~
                {                                                  //~1128I~
			        if (Dump.Y) Dump.println("WindowListener on UIThread");//~1506R~
		    		callWindowListener(Pcase,Plistener);//~1128I~
                }                                                  //~1128I~
            }                                                      //~1128I~
                                  );                               //~1128I~
    }                                                              //~1128I~
    public static void callWindowListener(int Pcase,WindowListener Plistener)//~1128I~
    {                                                              //~1128I~
        WindowEvent ev=new WindowEvent();                          //~1516R~
        switch(Pcase)                                              //~1128I~
        {                                                          //~1128I~
        case WINDOW_OPENED:                                        //~1128I~
            Plistener.windowOpened(ev);                            //~1128I~
            break;                                                 //~1128I~
        case WINDOW_RESTORE:                                       //~1503I~
            Plistener.windowOpened(ev);                            //~1503I~
            break;                                                 //~1503I~
        case WINDOW_ACTIVE:                                     //~1128I~
            Plistener.windowActivated(ev);                         //~1128I~
            break;                                                 //~1128I~
        case WINDOW_INACTIVE:                                   //~1128I~
            Plistener.windowDeactivated(ev);                       //~1128I~
            break;                                                 //~1516I~
        case WINDOW_CLOSING:                                       //~1516R~
            Plistener.windowClosing(ev);                           //~1516R~
            break;                                                 //~1516I~
        case WINDOW_CLOSED:                                        //~1516I~
            Plistener.windowClosed(ev);                            //~1516I~
            break;                                                 //~1128I~
        }                                                          //~1128I~
    }                                                              //~1128I~
//*****************************************************            //~1415I~
//*recycle bitmap to avoid OutOfMemory ****************            //~1415I~
//*****************************************************            //~1415I~
//  public void recyclePrepare(Bitmap Pbitmap)                     //+1Ag9R~
    public void recyclePrepare(Image  Pbitmap)                     //+1Ag9I~
    {                                                              //~1415I~
    	if (Dump.Y) Dump.println("Window:recyclePrepare Bitmap="+Pbitmap.toString());//~1506R~
        if (recycleBitmaps==null)                                  //~1415I~
//      	recycleBitmaps=new ArrayList<Bitmap>();                //+1Ag9R~
        	recycleBitmaps=new ArrayList<Image>();                 //+1Ag9I~
    	recycleBitmaps.add(Pbitmap);                             //~1415I~
    }                                                              //~1415I~
    public void recycleBitmap()                                   //~1415I~
    {                                                              //~1415I~
    	if (Dump.Y) Dump.println("Window:recycleBitmap");          //~1506R~
    	if (recycleBitmaps==null)                                  //~1415I~
        	return;                                                //~1415I~
        int ctr=recycleBitmaps.size();                             //~1415I~
    	if (Dump.Y) Dump.println("Window:recycleBitmap ctr="+ctr); //~1506R~
    	if (ctr==0)                                                //~1415I~
        	return;                                                //~1415I~
        for (int ii=0;ii<ctr;ii++)                                 //~1415I~
        {                                                          //~1415I~
//      	Bitmap bitmap=recycleBitmaps.get(ii);                  //+1Ag9R~
        	Image  image=recycleBitmaps.get(ii);                   //+1Ag9I~
//    if (false)  //@@@@test                                       //~1AfpR~
//          if (!bitmap.isRecycled())                              //+1Ag9R~
//          {                                                      //+1Ag9R~
//  			if (Dump.Y) Dump.println("Window:recycleBitmap ii="+ii+",Bitmap="+bitmap.toString()+",byte="+Image.getByteCount(bitmap));//+1Ag9R~
//          	bitmap.recycle();                                  //+1Ag9R~
//  			if (Dump.Y) Dump.println("Window:recycleBitmap after recycle isrecycled="+bitmap.isRecycled());//+1Ag9R~
//          }                                                      //+1Ag9R~
            image.recycle();                                       //+1Ag9I~
        }                                                          //~1415I~
//    if (false)  //@@@@test                                       //~1AfpR~
        recycleBitmaps.clear();                                    //~1415I~
//    else                                                         //~1AfpR~
//  	SrecycleBitmaps=recycleBitmaps;      //@@@@test            //~1AfpR~
    }                                                              //~1415I~
	private static ArrayList<Bitmap> SrecycleBitmaps;              //~1AfpI~
//*****************************************************            //~1A8hI~
//*show frame on top                                               //~1A8hI~
//*****************************************************            //~1A8hI~
    public static void showFrame(Frame Pframe)                     //~1A8hI~
    {                                                              //~1A8hI~
        Frame frame=null;                                               //~1A8hI~
        int ctr,ii;                                                //~1A8hI~
    //********************:                                        //~1A8hI~
		ctr=framestack.size();                                     //~1A8hI~
    	if (Dump.Y) Dump.println("Window showFrame Pframe="+Pframe.framename+",stackctr="+ctr);//~1A8hI~
        for (ii=0;ii<ctr;ii++)                                     //~1A8hI~
        {                                                          //~1A8hI~
        	frame=framestack.getFirst();	//get top              //~1A8hI~
	    	if (Dump.Y) Dump.println("Window showFrame stack frame="+frame.framename);//~1A8hI~
            if (Pframe==frame)                                     //~1A8hI~
            	break;                                             //~1A8hI~
	        framestack.remove(frame);    //pick from list          //~1A8hI~
	        framestack.add(frame);		//wrap to last             //~1A8hI~
        }                                                          //~1A8hI~
        if (ii>=ctr)  //not found                                  //~1A8hI~
        	return;                                                //~1A8hI~
		AG.ajagov.setContentView(frame);                           //~1A8hI~
        frame.onRestore();                                         //~1A8hI~
    }                                                              //~1A8hI~
    //***************************************************************************//~1AfpI~
    //*duplicate GoFrame chk                                       //~1AfpI~
    //***************************************************************************//~1AfpI~
	static public boolean IsThereAnyOpenedGoFrame()                //~1AfpI~
    {                                                              //~1AfpI~
    	boolean rc=false;                                          //~1AfpI~
		int ctr=framestack.size();                                 //~1AfpI~
        for (int ii=0;ii<ctr;ii++)                                 //~1AfpI~
        {                                                          //~1AfpI~
        	Frame f=framestack.get(ii);                            //~1AfpI~
        	if (Dump.Y) Dump.println("Window:IsThereAnyOpenedGoFrame ii="+ii+",name="+f.framename);//~1AfpI~
            if (f instanceof GoFrame)                              //~1AfpI~
            {                                                      //~1AfpI~
            	AjagoView.showToast(R.string.DuplicatedGoFrameOpened);//~1AfpI~
	        	if (Dump.Y) Dump.println("Window:IsThereAnyOpenedGoFrame instanceOf GoFrame:true");//~1AfpI~
                rc=true;                                           //~1AfpI~
                break;                                             //~1AfpI~
            }                                                      //~1AfpI~
        }                                                          //~1AfpI~
        if (Dump.Y) Dump.println("Frame:IsThereAnyOpenedGoGrame rc="+rc);//~1AfpI~
    	return rc;                                                 //~1AfpI~
    }                                                              //~1AfpI~
    //***************************************************************************//~1AfpI~
    //*duplicate GoFrame chk                                       //~1AfpI~
    //***************************************************************************//~1AfpI~
	static public boolean IsDuplicatedFrame(String Pframename)     //~1AfpI~
    {                                                              //~1AfpI~
    	boolean rc=false;                                          //~1AfpI~
		int ctr=framestack.size();                                 //~1AfpI~
        for (int ii=0;ii<ctr;ii++)                                 //~1AfpI~
        {                                                          //~1AfpI~
        	Frame f=framestack.get(ii);                            //~1AfpI~
        	if (Dump.Y) Dump.println("Window:IsDuplicatedFrame Pframename="+Pframename+",ii="+ii+",name="+f.framename);//~1AfpI~
            if (Pframename.equals(f.framename))                    //~1AfpI~
            {                                                      //~1AfpI~
            	AjagoView.showToast(R.string.DuplicatedFrameOpened);//~1AfpI~
	        	if (Dump.Y) Dump.println("Window:IsDuplicatedFrame exists");//~1AfpI~
                rc=true;                                           //~1AfpI~
                break;                                             //~1AfpI~
            }                                                      //~1AfpI~
        }                                                          //~1AfpI~
        if (Dump.Y) Dump.println("Frame:IsDuplicatedFrame rc="+rc);//~1AfpI~
    	return rc;                                                 //~1AfpI~
    }                                                              //~1AfpI~
}//class                                                           //~1415R~
