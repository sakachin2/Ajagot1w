//*CID://+1Ag1R~: update#= 181;                                    //~1Ag1R~
//**********************************************************************//~1107I~
//1Ag1 2016/10/06 Change Top panel. set menu panel as tabwidget.   //~1Ag1I~
//1Af9 2016/07/11 Additional to Server/Partner List update fuction, up/down/undelete.//~1Af9I~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//2015/03/06 1A6p 2015/02/16 display.getWidth()/getHeight() was deprecated at api13,use getSize(Point)//~1A6pI~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320(Ahsv:1A50,Ajagot1w:v1D4)//~1A40I~
//v1D2 2014/10/07 orientation for tbi11m is 0 degree for landscape, switch to reverse orientation//~v1D2I~
//1B43 2013/07/18 match fail to show go board(inflate exception for edittext.//~1B43I~
//                   android.view.InflateException: Binary XML file line #405: Error inflating class <unknown>//~1B43I~
//                   Caused by: java.lang.reflect.InvocationTargetException//~1B43I~
//                   Caused by: java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()//~1B43I~
//                inflate suould be done on UI thread from v4.2.2(Samsun galaxy?)//~1B43I~
//1B18 130505 xoom(Android3.1) listview touch is ignored when back from OpenPartnerFrame//~1B18I~
//1B0g 130430 catch OutOfMemoryError                               //~1B0gI~
//1B0c 130429 Encoding support for partner connection              //~1B0cI~
//1104:130126 fix orientation                                      //~v110I~
//1065:121124 PartnerConnection;FinishGame-->EndGame:send EndGEm req and if responsed, allog RemoveGroup.//~v106I~
//            Igs and GMP is FinishGame-->Remove groupe/prisoner   //~v106I~
//            change Menu Item Text for partner connection(send End game request)//~v106I~
//            Isue reply msg and notify "Remove Prisoner" avalable //~v106I~
//v101:120514 (Axe)android3(honeycomb) tablet has System bar at bottom that hide xe button line with 48pix height//~v101I~
//**********************************************************************//~v101I~
//*main view                                                       //~1107I~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import jagoclient.Dump;

import com.Ajagoc.awt.Component;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.Window;


import android.annotation.TargetApi;
import android.content.Context;                                    //~0913I~
import android.content.pm.ActivityInfo;                            //~v110I~
import android.content.res.Configuration;                          //~v110I~
import android.graphics.Point;
import android.graphics.Rect;

import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import android.widget.TextView;
import android.view.WindowManager;


public class AjagoView extends View                                  //~0914R~//~dataR~
	implements View.OnClickListener, AjagoUiThreadI
{                                                                  //~0914I~
	public  static final int TABINDEX_SERVER=0;                    //+1Ag1R~
	public  static final int TABINDEX_PARTNER=1;                   //+1Ag1R~
	public  static final int TABINDEX_TOP=2;                       //+1Ag1R~
	public  static final int TABINDEX_MAX=3;                       //+1Ag1R~
//  private static final String[] Stab_tags={"MainFrame_tag_Servers","MainFrame_tag_Partners"};//~1Ag1R~
    private static final String[] Stab_tags={"MainFrame_tag_Servers","MainFrame_tag_Partners","MainFrame_tag_Top"};//~1Ag1I~
//  private Button[] tabbtns=new Button[2];                        //~1Ag1R~
    private Button[] tabbtns=new Button[TABINDEX_MAX];             //~1Ag1I~
    private TabHost tabhost;                                       //~1122I~
    static private View contentView;                               //~1425R~
    private int tabctr;                                            //~1425R~
    private static boolean idLong;                                               //~1514I~
	public AjagoView()                                //~0914R~//~dataR~//~1107R~//~1111R~
    {                                                              //~0914I~
    	super(AG.context);                                         //~1111I~
        if (Dump.Y) Dump.println("AjagoView Constructor");         //~1506R~
    }                                                              //~0914I~
    //******************************************                   //~v110I~
    //*from Canvas when landscape                                  //~v110I~
    //******************************************                   //~v110I~
	public void startMain()                                       //~1120I~//~1122I~
    {                                                              //~1120I~//~1122M~
        try                                                        //~1109I~//~1120M~//~1122M~
        {                                                          //~1109I~//~1120M~//~1122M~
			fixOrientation(true);                                  //~v110I~
	    	getScreenSize();                                       //~1122I~
//	    	initMainFrame();                                       //~1122I~
	        AG.ajagoMain.startMain();                              //~1122I~
        }                                                          //~1109I~//~1120M~//~1122M~
        catch(Exception e)                                         //~1109I~//~1120M~//~1122M~
        {                                                          //~1109I~//~1120M~//~1122M~
    		Dump.println(e,"AjagoView startMain exception");//~1109I~//~1120M~//~1122M~//~1329R~
        }                                                          //~1109I~//~1120M~//~1122M~
    }                                                              //~1120I~//~1122M~
//*************************                                        //~1122M~
	public void getScreenSize()                                    //~1122M~
    {                                                              //~1122M~
		Display display=((WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~1122M~
//      AG.scrWidth=display.getWidth();                            //~1122M~//~1A6pR~
//      AG.scrHeight=display.getHeight();                          //~1122M~//~1A6pR~
        Point p=new Point();                                       //~1A6pI~
        getDisplaySize(display,p);                                 //~1A6pI~
        AG.scrWidth=p.x;	//by pixel                             //~1A6pI~
        AG.scrHeight=p.y;   //                                     //~1A6pI~
        if (Dump.Y) Dump.println("AView: getScreenSize w="+p.x+",h="+p.y);//~1506R~//~@@@@R~//~1A6pI~
        AG.dip2pix=AG.resource.getDisplayMetrics().density;        //~1428I~
        if (Dump.Y) Dump.println("AjagoView: dp2pix="+AG.dip2pix); //~1506R~
        AG.portrait=(AG.scrWidth<AG.scrHeight);                    //~1223R~
        AG.screenDencityMdpiSmallH=(AG.screenDencityMdpi && AG.scrWidth<=320);//~1A40I~
        AG.screenDencityMdpiSmallV=(AG.screenDencityMdpi && AG.scrHeight<=320);//~1A40I~
        AG.layoutMdpi=(AG.screenDencityMdpiSmallH || AG.screenDencityMdpiSmallV);//~1A6cR~//~1A6hI~//~1A40I~
        getTitleBarHeight();                                       //~1413M~
    }                                                              //~1122M~
    public static void getTitleBarHeight()                         //~1413R~
    {                                                              //~1413M~
        Rect rect=new Rect();                                      //~1413M~
        android.view.Window w=AG.activity.getWindow();                                 //~1413M~
        View v=w.getDecorView();                                   //~1413M~
        v.getWindowVisibleDisplayFrame(rect);                      //~1413M~
        if (Dump.Y) Dump.println("Ajagoc DecorView rect="+rect.toString());//~1506R~
        v=w.findViewById(android.view.Window.ID_ANDROID_CONTENT);               //~1413M~
        AG.titleBarTop=rect.top;                                   //~1413M~
        AG.titleBarBottom=v.getTop();                              //~1413M~
        if (Dump.Y) Dump.println("Ajagoc TitleBar top="+AG.titleBarTop+",bottom="+AG.titleBarBottom);//~1506R~
    }                                                              //~1413M~
    public static Point getTitleBarPosition()                      //~1413I~
    {                                                              //~1413I~
    	if (AG.titleBarBottom==0)                                  //~1413I~
        	getTitleBarHeight();                                   //~1413I~
        return new Point(AG.titleBarTop,AG.titleBarBottom);        //~1413I~
    }                                                              //~1413I~
    public static int getFramePosition()                         //~1413I~
    {                                                              //~1413I~
    	if (AG.titleBarBottom==0)                                  //~1413I~
        	getTitleBarHeight();                                   //~1413I~
        return AG.titleBarBottom;                                  //~1413I~
    }                                                              //~1413I~
    public static int getMargin()                                  //~v101I~
    {                                                              //~v101I~
    	int top=getFramePosition();                                //~v101I~
        return top+AG.bottomSpaceHeight;                           //~v101I~
    }                                                              //~v101I~
//******************                                               //~1326I~
    public void setContentView(Frame Pframe)                       //~1326I~
    {                                                              //~1326I~
        AjagoUiThread.runOnUiThreadWait(this,Pframe);              //~1326I~
    }                                                              //~1326I~
    public void runOnUiThread(Object Pparm)                        //~1326I~
    {                                                              //~1326I~
    	if (Pparm instanceof Frame)                                //~1513I~
        {                                                          //~1513I~
            Frame frame=(Frame)Pparm;                              //~1513R~
            View  view=frame.framelayoutview;                      //~1513R~
            if (Dump.Y) Dump.println("setContentView start id="+Integer.toHexString(view.getId()));//~1513R~
            if (Dump.Y) Dump.println("frame name="+frame.framename+"view="+view.toString());//~1513R~
            AG.ajagoc.setContentView(view);                        //~1513R~
            if (frame.currentTitle!=null)                          //~1513R~
                frame.setTitle(frame.currentTitle);     //update also title which may be changed//~1513R~
            else                                                   //~1513R~
                frame.setTitle(frame.framename);                           //~1326I~//~1513R~
            frame.seticonImage(null);//restore icon                //~1513R~
            contentView=view;    //save current for layouting      //~1513R~
            AG.setCurrentFrame(frame);                             //~1513R~
            if (frame==AG.mainframe)                               //~1B18R~
            	if (tabhost!=null)                                 //~1B18I~
					tabhost.clearFocus();                          //~1B18R~
            if (Dump.Y) Dump.println("setContentView end id="+Integer.toHexString(contentView.getId()));//~1513R~
            return;                                                //~1513I~
        }                                                          //~1513I~
    	if (Pparm instanceof String) 	//toast                    //~1513I~
        {                                                          //~1513I~
        	String msg=(String)Pparm;                              //~1513I~
	    	if (Dump.Y) Dump.println("UIshowToast msg="+msg);       //~1513I~
            if (idLong)                                            //~1514I~
		        Toast.makeText(AG.context,msg,Toast.LENGTH_LONG).show();//~1514I~
            else                                                   //~1514I~
		        Toast.makeText(AG.context,msg,Toast.LENGTH_SHORT).show();//~1514R~
            return;                                                //~1513I~
        }                                                          //~1513I~
    }                                                              //~1326I~

//*************************                                        //~1128I~
	static public View inflateView(int Presid)                     //~1128I~
    {                                                              //~1128I~
		View layoutview=inflateLayout(Presid);                     //~1128I~
        return layoutview;                                         //~1128I~
    }                                                              //~1128I~
//******************                                               //~1124I~//~1216M~
	static private View inflateLayout(int Presid)                   //~1122I~//~1216I~
    {                                                              //~1122I~//~1216M~
        if (!AG.isMainThread())                                    //~1B43R~
			return inflateLayoutBG(Presid);                        //~1B43R~
    	View layoutView=AG.inflater.inflate(Presid,null);          //~1122I~//~1216M~
    	AG.setCurrentView(Presid,layoutView);                      //~1216I~
        return layoutView;                                         //~1122I~//~1216M~
    }                                                              //~1122I~//~1216M~
//******************                                               //~1B43I~
	static private View inflateLayoutBG(int Presid)                //~1B43I~
    {                                                              //~1B43I~
		Component comp=new Component();                            //~1B43I~
    	comp.inflate(Presid);   	//inflate                      //~1B43I~
    	View layoutView=comp.inflatedView;                         //~1B43I~
    	AG.setCurrentView(Presid,layoutView);                      //~1B43I~
        return layoutView;                                         //~1B43I~
    }                                                              //~1B43I~
//**************************************************************   //~1410I~
//*from Dialog,reuse old layout for redo modalDialog Action        //~1410I~
//**************************************************************   //~1410I~
	static public View inflateLayout(int Presid,View PlayoutView) //~1410I~
    {                                                              //~1410I~
    	AG.setCurrentView(Presid,PlayoutView);                     //~1410I~
        return PlayoutView;                                        //~1410I~
    }                                                              //~1410I~
//******************                                               //~1124I~//~1128M~
	static public View getContentView()                            //~1122I~//~1128M~
    {                                                              //~1122I~//~1128M~
        return contentView;                                        //~1122I~//~1128M~
    }                                                              //~1122I~//~1128M~
//******************                                               //~1122I~
//* MainFrameTab ***                                               //~1122I~
//******************                                               //~1122I~
	public void initMainFrameTab(Frame Pmainframe) //from Applet(Go)//~1125R~
    {                                                              //~1122M~
    //************                                                 //~1122M~
    	if (Dump.Y) Dump.println("AjagoView:initMainFrameTab tabctr="+tabctr);//~1506R~
        if (tabctr==0)                                             //~1125R~
        {                                                          //~1125I~
        	initTab();                                                 //~1122I~//~1125R~
        }                                                          //~1125I~
        initCardPanelLayout(tabctr);                               //~1125R~
        tabctr++;                                                  //~1125I~
    }                                                              //~1122M~
//******************                                               //~1122I~
	public void initTab()                                          //~1122I~
    {                                                              //~1122I~
        TabSpec tab;                                               //~1122I~
        Button btn;                                                //~1122I~
    //************                                                 //~1122I~
        tabhost=(TabHost)(contentView.findViewById(android.R.id.tabhost));             //~1122I~//~1124R~s
    	if (Dump.Y) Dump.println("AjagoView:initTab tabhost="+((Object)tabhost).toString());//~1506R~
        tabhost.setup();            //method by without TabActibity//~1124R~
        tab=tabhost.newTabSpec(Stab_tags[0]);                      //~1122R~
        btn=new Button(AG.context);                                //~1122R~
        btn.setText(AG.tabName_ServerConnections);                 //~1122R~
        tabbtns[0]=btn;                                            //~1122I~
        tab.setIndicator(btn);                                     //~1122R~
        tab.setContent(AG.TabLayoutID_Servers);                    //~1122R~
                                                                   //~1122I~
        tabhost.addTab(tab);                                       //~1122I~
                                                                   //~1122I~
        tab=tabhost.newTabSpec(Stab_tags[1]);                      //~1122R~
        btn=new Button(AG.context);                                //~1122R~
        btn.setText(AG.tabName_PartnerConnections);                //~1122I~
        tabbtns[1]=btn;                                            //~1122I~
        tab.setIndicator(btn);                                     //~1122R~
        tab.setContent(AG.TabLayoutID_Partners);                   //~1122I~
                                                                   //~1122I~
        tabhost.addTab(tab);                                       //~1122I~
                                                                   //~1Ag1I~
        tab=tabhost.newTabSpec(Stab_tags[TABINDEX_TOP]);           //~1Ag1I~
        btn=new Button(AG.context);                                //~1Ag1I~
        btn.setText(AG.tabName_Top);                               //~1Ag1I~
        tabbtns[TABINDEX_TOP]=btn;                                 //~1Ag1I~
        tab.setIndicator(btn);                                     //~1Ag1I~
        tab.setContent(AG.TabLayoutID_Top);                        //~1Ag1I~
                                                                   //~1Ag1I~
        tabhost.addTab(tab);                                       //~1Ag1I~
                                                                   //~1Ag1I~
        AG.mainframeTag=TABINDEX_TOP;                              //~1Ag1R~
        tabhost.setCurrentTab(AG.mainframeTag);
        tabhost.setOnTabChangedListener(AG.ajagoc);  //~1122R~
    }                                                              //~1122I~
//******************                                               //~1122I~
	public void onTabChanged(String Ptag)                          //~1122I~
    {                                                              //~1122I~
    //************                                                 //~1122I~
    	if (Ptag.equals(Stab_tags[TABINDEX_TOP]))                  //~1Ag1I~
        	AG.mainframeTag=TABINDEX_TOP;                          //~1Ag1I~
        else                                                       //~1Ag1I~
    	if (Ptag.equals(Stab_tags[0]))                              //~1122I~
        	AG.mainframeTag=0;                                     //~1122I~
        else                                                       //~1122I~
        	AG.mainframeTag=1;                                     //~1122I~
        if (Dump.Y) Dump.println("AjagoView:onTabChanged Ptag="+Ptag+",idx="+AG.mainframeTag);//~1Ag1R~
    }                                                              //~1122I~
//******************                                               //~1122I~
	public void initCardPanelLayout(int Ppanelctr)                 //~1125R~
    {                                                              //~1122I~
    	int layoutid;                                              //~1125I~
    //****************                                             //~1125I~
        if (AG.layoutMdpi)                                         //~1Af9I~
        {                                                          //~1Af9I~
            AG.frameId_ServerConnections=AG.frameId_ServerConnections_mdpi;//~1Af9I~
            AG.frameId_PartnerConnections=AG.frameId_PartnerConnections_mdpi;//~1Af9I~
            AG.frameId_Top=AG.frameId_Top_mdpi;                    //~1Ag1I~
        }                                                          //~1Af9I~
        if (Ppanelctr==TABINDEX_TOP)                               //~1Ag1I~
            layoutid=AG.frameId_Top;                               //~1Ag1I~
        else                                                       //~1Ag1I~
        if (Ppanelctr==0)                                          //~1125R~
            layoutid=AG.frameId_ServerConnections;                 //~1125I~
        else
            layoutid=AG.frameId_PartnerConnections;                //~1125I~
        View layoutview=inflateView(layoutid);               //~1125I~//~1128R~
	    setTabLayout(layoutid,layoutview);                     //~1122R~//~1125R~
	}                                                              //~1125I~
//******************                                               //~1125I~
	public void setTabLayout(int Playoutid,View Playout)           //~1125I~
    {                                                              //~1125I~
    	int containerID;                                           //~1125I~
        LinearLayout container;                                    //~1125I~
    //************                                                 //~1122I~
    	if (Dump.Y) Dump.println("AjagoView:setTabLayoutt Playoutid="+Integer.toHexString(Playoutid));//~1506R~
        if (Playoutid==AG.frameId_Top)                             //~1Ag1R~
        {                                                          //~1Ag1I~
	        containerID=AG.TabLayoutID_Top;                        //~1Ag1R~
        }                                                          //~1Ag1I~
        else                                                       //~1Ag1I~
        if (Playoutid==AG.frameId_ServerConnections)               //~1125I~
        {                                                          //~1122I~
	        containerID=AG.TabLayoutID_Servers;                    //~1122I~
        }                                                          //~1122I~
        else                                                       //~1122I~
        {                                                          //~1122I~
	        containerID=AG.TabLayoutID_Partners;
        }                                                          //~1122I~
        FrameLayout framelayout=tabhost.getTabContentView();       //~1122R~
        container=(LinearLayout)framelayout.findViewById(containerID);       //~1122I~
        container.addView(Playout,0/*pos*/);                       //~1122R~
    }                                                              //~1122I~
//*************************                                        //~1120I~
    @Override                                                      //~1111I~
    public void onClick(View Pview)                                 //~1109I~//~1111I~
	{                                                              //~1109I~//~1111I~
//        AG.ajagoMain.onClick(Pview);                                //~1109I~//~1111I~//~1112R~
	}                                                              //~1109I~//~1111I~
//************                                                     //~1113I~
    static public void setEnabled(View Playout,int Presid)         //~1113I~
    {                                                              //~1113I~
    	View view=(View)Playout.findViewById(Presid);              //~1113I~
    	view.setEnabled(true);                                     //~1113I~
    }                                                              //~1113I~
//************                                                     //~1114I~
    static public void setText(View Playout,int Presid,String Ptext)//~1114I~
    {                                                              //~1114I~
    	TextView tv=(TextView)Playout.findViewById(Presid);            //~1114I~
    	tv.setText(Ptext);                                         //~1114I~
    }                                                              //~1114I~
//************                                                     //~1128I~
    public void windowFocusChanged(boolean Phasfocus)              //~1128R~
    {                                                              //~1128R~
        if (Dump.Y) Dump.println("AjagoView OnFocusChangeListener focus="+Phasfocus);//~1506R~
        Window.onFocusChanged(Phasfocus);                          //~1513R~
    }                                                              //~1128R~
//************                                                     //~1217I~
    public Button getTabButton(int PbuttonNo)	//from Button<--CardPanel//~1217I~
    {                                                              //~1217I~
        if (Dump.Y) Dump.println("getTabButton from CardButton ="+PbuttonNo);//~1506R~
        return tabbtns[PbuttonNo];                                 //~1217I~
    }                                                              //~1217I~
//**********************************************************       //~1314I~
    public static void showToast(int Presid)                       //~1314I~
    {                                                              //~1314I~
		showToast(Presid,"");                                      //~1513I~
    }                                                              //~1314I~
//**********************************************************       //~1514I~
    public static void showToastLong(int Presid)                   //~1514I~
    {                                                              //~1514I~
		showToastLong(Presid,"");                                  //~1514I~
    }                                                              //~1514I~
//**********************************************************       //~1421I~
    public static void showToast(int Presid,String Ptext)          //~1421I~
    {                                                              //~1421I~
        String msg=AG.resource.getString(Presid)+Ptext;            //~1421I~
    	if (Dump.Y) Dump.println("showToast msg="+msg);             //~1513I~
        idLong=false;                                              //~1514I~
    	AjagoUiThread.runOnUiThreadXfer(AG.ajagov,msg);                 //~1513I~
    }                                                              //~1421I~
//**********************************************************       //~1514I~
    public static void showToastLong(int Presid,String Ptext)      //~1514I~
    {                                                              //~1514I~
        String msg=AG.resource.getString(Presid)+Ptext;            //~1514I~
    	if (Dump.Y) Dump.println("showToastLong msg="+msg);        //~1514I~
        idLong=true;                                               //~1514I~
    	AjagoUiThread.runOnUiThreadXfer(AG.ajagov,msg);            //~1514I~
    }                                                              //~1514I~
//**********************************************************       //~@@@@I~//~1Ae5I~
    public static void showToast(String Ptext)                     //~@@@@I~//~1Ae5I~
    {                                                              //~@@@@I~//~1Ae5I~
    	if (Dump.Y) Dump.println("showToast msg="+Ptext);          //~@@@@I~//~1Ae5I~
        if (AG.status==AG.STATUS_STOPFINISH)                          //~@@@@I~//~1Ae5I~
            return;                                                //~@@@@I~//~1Ae5I~
        idLong=false;                                              //~@@@@I~//~1Ae5I~
    	AjagoUiThread.runOnUiThreadXfer(AG.ajagov,Ptext);                //~@@@@I~//~1Ae5I~
    }                                                              //~@@@@I~//~1Ae5I~
//**********************************************************       //~1B0cI~
    public static void showToastLong(String Ptext)                 //~1B0cI~
    {                                                              //~1B0cI~
    	if (Dump.Y) Dump.println("showToastLong msg="+Ptext);      //~1B0cI~
        idLong=true;                                               //~1B0cI~
    	AjagoUiThread.runOnUiThreadXfer(AG.ajagov,Ptext);          //~1B0cI~
    }                                                              //~1B0cI~
//**********************************                               //~v106I~
    public static void endGameConfirmed()                          //~v106I~
    {                                                              //~v106I~
    	showToastLong(R.string.endGameConfirmed);                   //~v106R~
	}                                                              //~v106I~
//**********************************                               //~v106I~
    public static void lockContention(String Ptext)                //~v106I~
    {                                                              //~v106I~
    	showToastLong(R.string.lockContention,Ptext);              //~v106I~
	}                                                              //~v106I~
//**********************************                               //~1B0gI~
    public static void memoryShortage(String Ptext)                //~1B0gI~
    {                                                              //~1B0gI~
    	showToastLong(R.string.ErrOutOfMemory,Ptext);                //~1B0gI~
	}                                                              //~1B0gI~
//*********************************************                    //~v110I~
	public void fixOrientation(boolean Pfix)                       //~v110I~
    {                                                              //~v110I~
        int ori2=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;      //~v110I~
    	if (Pfix)                                                  //~v110I~
        {                                                          //~v110I~
            int ori=AG.resource.getConfiguration().orientation;    //~v110I~
//            int rot=AG.activity.getWindowManager().getDefaultDisplay().getOrientation();//~v1D2R~
//            if (rot==Surface.ROTATION_0||rot==Surface.ROTATION_90)//~v1D2R~
//                if (ori==Configuration.ORIENTATION_LANDSCAPE)    //~v1D2R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;//~v1D2R~
//                else                                             //~v1D2R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;//~v1D2R~
//            else                                                 //~v1D2R~
//                if (ori==Configuration.ORIENTATION_LANDSCAPE)    //~v1D2R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;//~v1D2R~
//                else                                             //~v1D2R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;//~v1D2R~
            if (ori==Configuration.ORIENTATION_LANDSCAPE)          //~v1D2I~
                ori2=ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;//~v1D2I~
            else                                                   //~v1D2I~
                ori2=ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;//~v1D2I~
        }                                                          //~v110I~
        AG.activity.setRequestedOrientation(ori2);                 //~v110I~
    }                                                              //~v110I~
//**********************************                               //~1A6pI~
    public static void getDisplaySize(Display Pdisplay,Point Ppoint)//~1A6pI~
    {                                                              //~1A6pI~
        if (AG.osVersion<AG.HONEYCOMB_MR2)  //android3.2=api-13    //~1A6pI~
			getDisplaySize_deprecated(Pdisplay,Ppoint);            //~1A6pI~
        else                                                       //~1A6pI~
			getDisplaySize_new(Pdisplay,Ppoint);                   //~1A6pI~
    }                                                              //~1A6pI~
	@TargetApi(AG.HONEYCOMB_MR2)                                   //~1A6pI~
    private static void getDisplaySize_new(Display Pdisplay,Point Ppoint)//~1A6pI~
    {                                                              //~1A6pI~
        Pdisplay.getSize(Ppoint);                                  //~1A6pI~
    }                                                              //~1A6pI~
    @SuppressWarnings("deprecation")                               //~1A6pI~
    private static void getDisplaySize_deprecated(Display Pdisplay,Point Ppoint)//~1A6pI~
    {                                                              //~1A6pI~
		Ppoint.x=Pdisplay.getWidth();                              //~1A6pI~
		Ppoint.y=Pdisplay.getHeight();                             //~1A6pI~
    }                                                              //~1A6pI~
//**********************************                               //~1Ag1I~
    public void setCurrentTab(int Ptabindex)                       //~1Ag1I~
    {                                                              //~1Ag1I~
    	int idx;                                                   //~1Ag1I~
    	if (Ptabindex<0)	//swap 0(server) and 1(partner)        //~1Ag1I~
        	if (AG.mainframeTag==TABINDEX_SERVER)                  //~1Ag1R~
	        	idx=TABINDEX_PARTNER;                              //~1Ag1R~
        	else                                                   //~1Ag1I~
        	if (AG.mainframeTag==TABINDEX_PARTNER)                 //~1Ag1I~
        		idx=TABINDEX_TOP;                                  //~1Ag1R~
            else                                                   //~1Ag1I~
	        	idx=TABINDEX_SERVER;                               //~1Ag1I~
        else                                                       //~1Ag1I~
        	idx=Ptabindex;                                         //~1Ag1I~
    	if (Dump.Y) Dump.println("AjagoView:setCurrentTab Ptabindex="+Ptabindex+",new idx="+idx);//~1Ag1I~
	    tabhost.setCurrentTab(idx);                                //~1Ag1R~
    }//switchTab                                                   //~1Ag1I~
}//class AjagoView                                                 //~dataR~
