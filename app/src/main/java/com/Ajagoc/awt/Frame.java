//*CID://+1Af9R~:                             update#=   28;       //+1Af9R~
//**********************************************************************//~v107I~
//1Af9 2016/07/11 Additional to Server/Partner List update fuction, up/down/undelete.//~1Af9I~
//1Aen 2015/08/09 mdpi title for portrait could not show and right arrow and more button//~1AenI~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//1B04 130428 Send field is not used on GMP(set visibility:GONE)   //~1B04I~
//1084:121215 connection frame input field is untachable when restored after Who frame//~v108I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//**********************************************************************//~v107I~
package com.Ajagoc.awt;                                                //~1108R~//~1109R~
                                                                   //~1221I~
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.gmp.GMPConnection;
import jagoclient.gui.CloseFrame;
import jagoclient.gui.DoActionListener;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoGMP;
import com.Ajagoc.AjagoKey;
import com.Ajagoc.AjagoView;
import com.Ajagoc.awt.Window;

import android.view.View;
//import android.view.inputmethod.InputMethodManager;              //~v108R~

import android.widget.LinearLayout;

public class Frame extends Window //skip Window of Container-->Window-->Frame                                                 //~1111R~//~1116R~//~1124R~
{                                                                  //~1111I~

	public static final int MAXIMIZED_BOTH  =3;                    //~1213R~
	public static Frame MainFrame;                                 //~1420R~

    public  String framename;                                      //~1420R~
    public  View framelayoutview;                                  //~1420R~
    public  int framelayoutresourceid;                           //~1125R~//~1420R~
    private boolean setcontentview_at_show;                        //~1420R~
                                                                   //~1217I~
    public  int frametopviewid;                                    //~1420R~
    public MenuBar framemenubar;                                   //~1420R~
    public  View contextMenuView;                                  //~1420R~
    public  WindowListener windowlistener;                         //~1420R~
    public  FocusListener focuslistener;                           //~1420R~
    public MouseListener mouselistener;                            //~1420R~
    public DoActionListener frameDoActionListener;                 //~1420R~
    public int framestatus;	//update by Window.java                                     //~1128R~//~1420R~
    public boolean isBoardFrame;                                   //~1420R~
    public boolean isDestroyed;                                    //~1429I~
    public boolean isObserveGame;                                  //~1503I~
    public Canvas boardCanvas;                                     //~1420R~
    public String currentTitle;                                    //~1420R~
    public Dialog modalDialog_beforeDismiss;                       //~1420R~
    public KeyListener framekeylistener;                           //~1427I~
    private boolean disposed;                                      //~1516I~
//*********                                                        //~1111I~
//*for warning                                                     //~1111I~
//*********                                                        //~1111I~
    public Frame()                                                 //~1111I~
    {                                                              //~1111I~
    	super();                                               //~1310R~
        setComponentType(this);                             //~1310I~
    }                                                              //~1111I~
//*********                                                        //~1113I~
    public Frame(String s)                                         //~1113I~
    {                                                              //~1113I~
    	this();                                                    //~1310I~
    	if (s.equals(""))                                          //~1217I~
        	return;                                                //~1217I~
    	setFrameLayout(s);                                              //~1217I~//~1218R~
    }                                                              //~1113I~
//*********************************************************        //~1217I~
//*CloseFrame constructer is super("") then setTitle(s)             //~1217I~
//*********************************************************        //~1217I~
    public void setTitle(String s)                                 //~1217R~
    {                                                              //~1217I~
    	if (framename==null && s.startsWith(Global.resourceString("_Jago_")))//~1506R~
        {                                                          //~1506I~
    	  if (AG.screenDencityMdpiSmallH) //horizonrally short(narrow width)//~1AenR~
            s+=" ("+AG.appVersion+")";                             //+1Af9R~
          else                                                     //~1AenI~
            s+=" ("+AG.appName+AG.appVersion+")";                  //+1Af9R~
        }                                                          //~1506I~
    	currentTitle=s;                                            //~1330I~
    	if (framename==null)                                       //~1217I~
    		setFrameLayout(s);                                          //~1217I~//~1218R~
        if (framelayoutview!=null)                                 //~1217I~
        {                                                          //~1221I~
	        super.setTitle(s);	//Component:                               //~1217I~//~1221R~
        }                                                          //~1221I~
    }                                                              //~1217I~
//*********                                                        //~1217I~
//*for search then layout and inflate                              //~1217I~
//*********                                                        //~1217I~
    private void setFrameLayout(String s)                               //~1217I~//~1218R~
    {                                                              //~1217I~
    	int resid=0;                                               //~1217I~
    //************                                                 //~1217I~
        framename=s;                                               //~1217I~
    	if (s.startsWith(Global.resourceString("_Jago_")))         //~1217R~
        {                                                          //~1506I~
        	AjagoGMP.setup();	//set GMPserver pgm name after readparameter(go.cfg)//~1511I~
        	Dump.checkOption();	//AjagoOption check after go.cfg was read//~1507I~
            if (AG.layoutMdpi)                                     //~1Af9I~
            	AG.frameId_MainFrame=AG.frameId_MainFrame_mdpi;	//tab layout//~1Af9I~
            resid=AG.frameId_MainFrame;	//tab layout
        }                                                          //~1506I~
        else                                                       //~1218I~
    	if (s.startsWith(Global.resourceString("Connection_to_")))//~1217I~
        {                                                          //~1318I~
//  		if (s.startsWith(Global.resourceString("Connection_to_")+com.Ajagoc.jagoclient.partner.PartnerFrame.CONNECT_TO_BT))//~v107R~//~1A8iR~
//              resid=AG.frameId_PartnerFrame;   //Bluetooth connection frame//~v107I~//~1A8iR~
//          else                                                   //~v107I~//~1A8iR~
    		if (AG.mainframeTag==0) //Cardpanel:ServerConnection   //~1318R~
	            resid=AG.frameId_ConnectionFrame;                  //~1318I~
            else                                                   //~1318I~
	            resid=AG.frameId_PartnerFrame;   //Go*2            //~1405R~
        }                                                          //~1318I~
        else                                                       //~1217I~
    	if (s.startsWith(Global.resourceString("Server")))         //~1405I~
	        resid=AG.frameId_PartnerFrame;  //partner.Server       //~1405I~
        else                                                       //~1405I~
    	if (s.equals(Global.resourceString("Peek_game"))     //igs.ConnectionFrame//~1405R~
    	||  s.equals("Peek game")     //igs.ConnectionFrame        //~1501I~
        ||  s.equals(Global.resourceString("Play_game"))           //~1217I~
        ||  s.equals("Play game")							//IgsStream//~1501R~
        ||  s.equals(Global.resourceString("Partner_Game"))  //PartnerFrame//~1405I~
        ||  s.equals("Partner Game")                         //PartnerFrame//~1501R~
        )                                                          //~1217I~
            resid=AG.frameId_ConnectedGoFrame;                     //~1217I~
        else                                                       //~1503I~
        if (s.equals(Global.resourceString("Observe_game"))        //~1503I~
        ||  s.equals("Observe game")                               //~1503M~
        )                                                          //~1503I~
        {                                                          //~1503I~
            resid=AG.frameId_ConnectedGoFrame;                     //~1503I~
    		isObserveGame=true;                                    //~1503I~
        }                                                          //~1503I~
        else                                                       //~1217I~
        if (s.equals(Global.resourceString("_Games_")))            //~1217I~
            resid=AG.frameId_GamesFrame;                           //~1217I~
        else                                                       //~1306I~
        if (s.equals(Global.resourceString("_Who_")))              //~1306I~
            resid=AG.frameId_WhoFrame;                             //~1306I~
        else                                                       //~1310I~
        if (s.equals(Global.resourceString("_Message_")))          //~1310I~
            resid=AG.frameId_MessageDialog;                        //~1310I~
        else                                                       //~1311I~
        if (s.equals(Global.resourceString("Say")))                //~1311I~
            resid=AG.frameId_SayDialog;                            //~1311I~
        else                                                       //~1323I~
        if (s.equals(Global.resourceString("Local_Viewer")))       //~1323I~
            resid=AG.frameId_LocalViewer;                          //~1323I~
        else                                                       //~1326I~
        if (s.equals(Global.resourceString("Help")))               //~1326I~
            resid=AG.frameId_Help;                                 //~1326I~
        else                                                       //~1331I~
        if (s.equals(Global.resourceString("Message_Filter")))     //~1331I~
            resid=AG.frameId_MessageFilter;                        //~1331I~
        else                                                       //~1404I~
        if (s.equals(Global.resourceString("Play_Go")))            //~1404I~
        {                                                          //~1512I~
	        if (windowlistener instanceof GMPConnection)      //CloseFrame pass "this" as WindowListener//~1512I~
            	resid=AG.frameId_GMPConnection;                    //~1512R~
            else                                                   //~1512I~
            	resid=AG.frameId_ConnectedGoFrame;                 //~1512I~
        }                                                          //~1512I~
        else                                                       //~1405I~
        if (s.equals(Global.resourceString("Open_Partners")))      //~1405I~
            resid=AG.frameId_OpenPartners;     //partner.OpenPartnerFrame//~1405I~
        else                                                       //~1405I~
        if (s.equals(Global.resourceString("Send")))               //~1405I~
            resid=AG.frameId_PartnerSendQuestion;    		 //partner.PartnerSendQuestion;//~1405I~
        if (Dump.Y) Dump.println("Frame name="+s+",layoutid="+Integer.toString(resid,16));//~1506R~
        if (resid!=0)                                              //~1217I~
        {                                                          //~1217I~
        	framelayoutresourceid=resid;                           //~1217I~
        	framelayoutview=AjagoView.inflateView(resid);	//no setContentView until show()//~1217I~
            setGoFrameVisibility();                                //~1415R~
	        componentView=framelayoutview;	//for Component.requestFocus;//~1405I~
	        setContainerLayoutView(framelayoutview);	//for findViewById     //~@@@2I~//~v1E9I~
            if (framelayoutresourceid==AG.frameId_MainFrame)       //~1218I~
				initMainFrame();                                   //~1218I~
            else                                                   //~1218I~
            	setcontentview_at_show=true;	//push at show                           //~1217I~//~1218R~
        }                                                          //~1217I~
    }                                                              //~1217I~
//***************************                                      //~1415I~
//*set IconBar Visible                                             //~1415I~
//***************************                                      //~1415I~
    private void setGoFrameVisibility()                            //~1415R~
    {                                                              //~1415I~
    	boolean visibleIB=true,visibleNavi=true;                   //~1415R~
    //***********	                                               //~1415I~
        if (framelayoutresourceid==AG.frameId_ConnectedGoFrame)    //~1415R~
        {                                                          //~1415I~
			visibleIB=Global.getParameter("showbuttonsconnected",true);//~1415R~
        }                                                          //~1415I~
        else                                                       //~1415I~
        if (framelayoutresourceid==AG.frameId_LocalViewer)         //~1415I~
        {                                                          //~1415I~
			visibleIB=Global.getParameter("showbuttons",true);     //~1415R~
			visibleNavi=Global.getParameter("shownavigationtree",true);//~1415M~
        }                                                          //~1415I~
		if (!visibleIB)                                            //~1415R~
        {                                                          //~1415R~
        	View v=AG.findViewById(AG.viewId_IconBar);            //~1415R~
            if (v!=null)                                           //~1415R~
            {                                                      //~1415I~
				v.setVisibility(View.GONE);	//disappear            //~1415R~
                if (Dump.Y) Dump.println("Frame:setGoFrameVisibility set GONE layoutid(IconBar)="+Integer.toHexString(framelayoutresourceid));//~1506R~
            }                                                      //~1415I~
        }                                                          //~1415I~
		if (!visibleNavi)                                          //~1415I~
        {                                                          //~1415I~
        	View v=AG.findViewById(AG.viewId_NavigationPanel);     //~1415I~
            if (v!=null)                                           //~1415I~
            {                                                      //~1415I~
				v.setVisibility(View.GONE);	//disappear            //~1415I~
                if (Dump.Y) Dump.println("Frame:setGoFrameVisibility set GONE layoutid(NavigationPanel)="+Integer.toHexString(framelayoutresourceid));//~1506R~
            }                                                      //~1415I~
        }                                                          //~1415I~
    }                                                              //~1415I~
//*********                                                        //~1111I~
//*for dialog                                                      //~1111I~
//*********                                                        //~1111I~
    public Frame(int Pid)                                          //~1111R~
    {                                                              //~1111I~
        layout=(LinearLayout)AG.inflater.inflate(Pid,null);                //~1111I~//~1113R~
    }                                                              //~1111I~
//*********                                                        //~1311I~
    public void setFrameType(Canvas Pcanvas)                   //~1310I~//~1311I~
    {                                                              //~1310I~//~1311M~
    	boardCanvas=Pcanvas;                                            //~1310I~//~1311I~
        isBoardFrame=true;                                         //~1311I~
        if (Dump.Y) Dump.println("Frame:Canvas created="+((Object)Pcanvas).toString());//~1506R~
    }                                                              //~1310I~//~1311M~
//******************************************************************//~1217I~
//* from Ajagoview for mainFrame 1st Tab                           //~1217R~
//*****************************************************************//~1217I~
    private void initMainFrame()                          //~1125I~//~1217R~//~1218R~
    {                                                              //~1125I~//~1217R~
    //***********                                                  //~1125I~//~1217R~
    	MainFrame=this;                                            //~1218I~
        Window.pushFrame(this);                                    //~1218I~
        AG.ajagov.initMainFrameTab(this);                          //~1218I~
        if (framemenubar!=null)        //null when setLayout then setMenuBar//~1125R~//~1217R~//~1218R~
        {                                                      //~1125I~//~1217R~//~1218R~
            framemenubar.setMenuBar(this);	//registerMenuBar                     //~1125I~//~1217R~//~1218R~//~1402R~
        }                                                          //~1125I~//~1217R~//~1218R~
    }                                                              //~1217R~
//******************************************************************//~1125I~
//* menu                                                           //~1125I~
//******************************************************************//~1125I~
    public void setMenuBar(MenuBar Pmenubar)                       //~1125I~
    {                                                              //~1125I~
        if (Pmenubar!=null) //null when from closeframe:doclose()  //~1125R~
        {                                                          //~1125I~
        	framemenubar=Pmenubar;                                 //~1125I~
            if (framelayoutview!=null)  //null when setMenu then setLayout//~1125R~
	            Pmenubar.setMenuBar(this);                         //~1125R~
        }                                                          //~1125I~
    }                                                              //~1125I~
    public void addWindowListener(WindowListener Pwl)              //~1125I~
    {                                                              //~1125I~
    	windowlistener=Pwl;                                        //~1128I~
        if (Pwl instanceof CloseFrame)      //CloseFrame pass "this" as WindowListener//~1330R~
        {                                                          //~1325I~//~1330R~
            frameDoActionListener=(DoActionListener)Pwl;                //~1325I~//~1330R~
        }                                                          //~1325I~//~1330R~
    }                                                              //~1125I~
    public void addKeyListener(KeyListener Pkl)                    //~1127I~
    {                                                              //~1127I~
    	if (framelayoutview!=null)                                       //~1127I~
        {                                                          //~1127I~
        	AjagoKey.addKeyListener(framelayoutview,Pkl);           //~1127I~
            framekeylistener=Pkl;   //call component listenr the frame listener like as awt//~1427I~
        }                                                          //~1127I~
    }                                                              //~1127I~
	public void show()                                             //~1128I~
    {                                                              //~1128I~
    	if (setcontentview_at_show)                                //~1217I~
        {                                                          //~1217I~
    		setcontentview_at_show=false;                          //~1217I~
        	Window.pushFrame(this);	//inflate and push             //~1218I~
            if (Dump.Y) Dump.println("Frame:show push frame");     //~1506R~
        }                                                          //~1217I~
    	setVisible(true);                                           //~1128I~
    }                                                              //~1128I~
	public void setVisible(boolean Pvisible)                       //~1128I~
    {                                                              //~1128I~
    	if (Pvisible)                                              //~1327I~
            if (setcontentview_at_show) //not yet shown                //~1221I~//~1327R~
            {                                                          //~1221I~//~1327R~
                if (Dump.Y) Dump.println("Frame:setvisible push frame");     //~1221I~//~1506R~
                show();                 //set ContentView              //~1221I~//~1327R~
                return;                                                //~1221I~//~1327R~
            }                                                          //~1221I~//~1327R~
    	Window.setVisible(this,Pvisible);                          //~1128I~
    }                                                              //~1128I~
	public void dispose()                                          //~1124I~//~1128I~
    {                                                              //~1124I~//~1128I~
        if (Dump.Y) Dump.println("Frame:dispose current dispose status="+disposed+",frame="+this.toString());//~1516R~
        if (disposed)                                              //~1516I~
        	return;                                                //~1516I~
        disposed=true;                                             //~1516I~
        if (Dump.Y) Dump.println("Frame:dispose current at show status="+setcontentview_at_show);//~1516I~
    	if (!setcontentview_at_show)	//already setContentView   //~1217I~
    		Window.popFrame(this);		//callback onDestroy();frame may be intermediate(GMPConnection)                                       //~1124I~//~1128I~//~1217R~//~1314R~//~1512R~
    }                                                              //~1124I~//~1128I~
//*********************************************                    //~1503I~
//*from window after set contentview by Back key                   //~1503I~
//*********************************************                    //~1503I~
	public void onRestore()                                        //~1503I~
    {                                                              //~1503I~
    	View v;                                                    //~1503I~
    //*****************                                            //~1503I~
    	if (Dump.Y) Dump.println("Frame.onRestore layoutid="+Integer.toHexString(framelayoutresourceid));//~1506R~
        switch(framelayoutresourceid)                              //~1504R~
        {                                                          //~1504R~
        case AG.frameId_ConnectionFrame:                           //~1504R~
//          v=AG.findViewById(R.id.TextField1);                    //~1504R~//~v108R~
//          if (Dump.Y) Dump.println("ConnFrame focusable="+v.isFocusable()+",touch="+v.isFocusableInTouchMode()+",isfocus="+v.isFocused());//~1506R~//~v108R~
//          v.requestFocus();                                    //~1504R~//~v108R~
//          framelayoutview.requestFocus();                        //~v108R~
            requestFocus(); //component:OnUiThread                 //~v108M~
            requestFocusFromTouch();    //run on unithread         //~v108R~
//          v=AG.findViewById(framelayoutview,R.id.Viewer);        //~v108R~
//          if (v!=null)                                           //~v108R~
//              v.requestFocus(); //connectionFrame:windowOpened call requestFocus to InputField//~v108R~
            break;                                                 //~1504R~
        case AG.frameId_ConnectedGoFrame:                          //~v108I~
            requestFocus();		//component:OnUiThread             //~v108R~
            requestFocusFromTouch();    //run on unithread         //~v108I~
            break;                                                 //~v108I~
        }                                                          //~1504R~
		if (windowlistener!=null)                                  //~1503I~
        {                                                          //~1503I~
			setVisible(this,true);	//restore=open;execute requestFocus()//~1503R~
        }                                                          //~1503R~
    }                                                              //~1503I~
//*********************************************                    //~1424I~
//*from window before contentview updated                          //~1424R~
	public void onDestroy()                                        //~1402I~
    {                                                              //~1402I~
        isDestroyed=true;                                          //~1503M~
        if (Dump.Y) Dump.println("Frame:onDestroy this="+this.toString());//~1513I~
        if (boardCanvas!=null)                                     //~1422R~
        	boardCanvas.stopThread();                              //~1422I~
        if (windowlistener!=null                                   //~1512I~
	    &&  windowlistener instanceof GMPConnection                //~1512I~
        )                                                          //~1512I~
        {                                                          //~1512I~
        	Dialog dlg=AG.currentDialog;                           //~1512I~
        	if (dlg!=null                                           //~1512I~
            &&  dlg.parentFrame==this                              //~1512I~
            &&  dlg.shown                                          //~1512I~
            )                                                      //~1512I~
            	dlg.dismiss();	                                   //~1512I~
        }                                                          //~1512I~
    }                                                              //~1402I~
//*********************************************                    //~1424I~
//*from window after contentview updated                           //~1424I~
	public void onDestroy2()                                       //~1424I~
    {                                                              //~1424I~
        if (framemenubar!=null)        //null when setLayout then setMenuBar//~1424M~
        {                                                          //~1424M~
            framemenubar.resetMenuBar(this);	//remove from menubarlist//~1424M~
		    framemenubar=null;                                     //~1424M~
        }                                                          //~1424M~
    }                                                              //~1424I~
//*********************************************                    //~1424I~
//*for rene.CloseFrame                                             //~1213I~
	public int getExtendedState()                                  //~1213I~
    {                                                              //~1213I~
    	return 0;                                                  //~1213I~
    }                                                              //~1213I~
	public void setExtendedState(int Pmaximized)                   //~1213I~
    {                                                              //~1213I~
    }                                                              //~1213I~
	public void toFront()                                          //~1213I~
    {                                                              //~1213I~
    }                                                              //~1213I~
    public void repaint()                                          //~1215I~
    {                                                              //~1215I~
    }                                                              //~1215I~
	public Dimension getSize()                                     //~1117I~//~1216I~
    {                                                              //~1117I~//~1216I~
    	int x,y;                                                   //~1216I~
    	if (framelayoutview==null)                                 //~1216I~
        	x=y=0;                                                 //~1216I~
        else                                                       //~1216I~
        {                                                          //~1216I~
    		x=framelayoutview.getMeasuredWidth();                  //~1216R~
    		y=framelayoutview.getMeasuredHeight();                 //~1216I~
        }                                                          //~1216I~
        if (Dump.Y) Dump.println("Frame getsize x="+x+",y="+y);    //~1506R~
    	return new Dimension(x,y);                                 //~1216I~
    }                                                              //~1117I~//~1216I~
    //***************************************************************************//~1B04I~
    //*find view in layoutview                                     //~1B04I~
    //***************************************************************************//~1B04I~
	public View findView(int Presid)                               //~1B04I~
    {                                                              //~1B04I~
        View v;                                                    //~1B04I~
    	if (framelayoutview==null)                                 //~1B04I~
        	v=null;                                                //~1B04I~
        else                                                       //~1B04I~
        	v=AG.findViewById(framelayoutview,Presid);             //~1B04I~
        if (Dump.Y) Dump.println("Frame:findView id="+Integer.toHexString(Presid)+",v="+v.toString());//~1B04I~
    	return v;                                                  //~1B04I~
    }                                                              //~1B04I~
}//class                                                           //~1415R~
