//*CID://+v1EbR~:                             update#=   29;       //~v1EbR~
//**********************************************************************//~v105I~
//v1Eb 2014/12/11 FileDialog:add function to reset filter          //~v1EbI~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//v1Dq 2014/11/15 comment area scroll hide last 1 line             //~v1DqI~
//v1D1 2014/10/03 actionBar as alternative of menu button for api>=11//~v1D1I~
//1B43 2013/07/18 match fail to show go board(inflate exception for edittext.//~1B43I~
//1B04 130428 Send field is not used on GMP(set visibility:GONE)   //~1B04I~
//1089:121117 confirm requestFocus runOnUiThread                   //~v108I~
//1086:121216 avoid automatic ime popup for ConnectionFrame and PartnerGoFrame IgsGoFrame//~v108I~
//1084:121215 connection frame input field is untachable when restored after Who frame//~v108I~
//1053:121113 exception(wrong thread) when filelist up/down for sgf file read//~v105I~
//**********************************************************************//~v105I~
package com.Ajagoc.awt;                                            //~1112I~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.Go;
import jagoclient.gui.DoActionListener;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoUiThread;
import com.Ajagoc.AjagoUiThreadI;
import com.Ajagoc.awt.Canvas;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
//import android.widget.RadioButton;                               //~v105R~
import android.widget.ScrollView;
import android.widget.TextView;
// Object-->awt.Component-->Container-->Panel                      //~1116I~
//                                   -->Window-->Frame             //~1116I~
                                                                   //~1112I~
public class Component// extends LinearLayout                        //~1120R~//~1216R~
	implements AjagoUiThreadI, ImageObserver                                      //~1221I~//~1308R~
{                                                                  //~1112I~
	private int caseUiThread;                                      //~1425R~
    private final static int CASE_APPEND      =1;                  //~1221I~
    private final static int CASE_SETTEXT     =2;                  //~1221R~
    private final static int CASE_SHOWBOTTOM  =3;                  //~1221I~
    private final static int CASE_TITLE       =4;                  //~1221I~
    private final static int CASE_BGCOLOR     =5;                  //~1310I~
    private final static int CASE_FONT        =6;                  //~1310I~
    private final static int CASE_BGFGCOLOR   =7;                  //~1312I~
    private final static int CASE_SETICON     =8;                  //~1313I~
    private final static int CASE_ENABLE      =9;                  //~1322I~
    private final static int CASE_SETCHECK    =10;                 //~1322I~
    private final static int CASE_FOCUS       =11;                 //~1405I~
    private final static int CASE_SETITEMCHECKED=12;               //~v105I~
    private final static int CASE_FOCUSTOUCH   =13;                //~v108I~
    private final static int CASE_SETVISIBILITY=14;                //~@@@@I~//~1B04I~
    private final static int CASE_INFLATE      =15;                //~1B43I~
    private final static int CASE_BGDRAWABLE   =16;                //~v1EbI~
    private String line;                                              //~1221R~//~1425R~
    private Color color;                                              //~1221R~//~1425R~
    private int   iconresid;                                      //~1313I~
    public View inflatedView;                                      //~1B43I~
                                                                   //~1310I~
    public  int  componentType=0;        //control UI sync/async   //~1310R~
    public  static final int COMPONENT_FRAME=1;                    //~1310I~
    public  static final int COMPONENT_DIALOG=2;                   //~1310I~
    public  Frame  frame;                                          //~1311R~
    public  Window parentWindow;                                   //~1425R~
    public Container parentContainer;                              //~v1E9I~
    public  Dialog dialog;                                         //~1310I~
                                                                   //~1310I~
    protected Font  font;                                            //~1310I~
    private Color bgcolor,fgcolor;                                         //~1310R~
    ScrollView scrollview;                                         //~1425R~
    int pos=0;                                                     //~1221I~
                                                                   //~1221I~
	public View layout;                                            //~1425R~
	public View componentView;	//frame,textfield,button for requestFocus//~1425R~
    public Component directParentComponent;                        //~1425R~
    public Color componentBackground; 
    private boolean buttonState;
    private boolean listItemState;                                 //~v105I~
    private int drawableResourceId;                                //~2B13I~
    private int visibility;//~2B13I~                               //~1B04I~
    private Drawable bgDrawable;                                   //~v1EbI~
//*************                                                    //~1310I~
    public Component()                                                   //~1112I~//~1113R~
    {                                                              //~1112I~
        setParent();                                               //~1311I~
    }                                                              //~1112I~
    public Component(Container Pcontainer)                         //~v1E9I~
    {                                                              //~v1E9I~
        this();	//set parentWindow                                 //~v1E9I~
		parentContainer=Pcontainer; //frame or dialog              //~v1E9I~
    }                                                              //~v1E9I~
    public void setComponentType(Frame Pframe)                                 //~1310I~
    {                                                              //~1310I~
    	componentType=COMPONENT_FRAME;                              //~1310I~
        frame=Pframe;                                              //~1310I~
		AG.setCurrentFrame(Pframe);                                    //~1408I~
    }                                                              //~1310I~
    public void setComponentType(Dialog Pdialog)                               //~1310I~
    {                                                              //~1310I~
    	componentType=COMPONENT_DIALOG;                             //~1310I~
        dialog=Pdialog;                                            //~1310I~
		AG.setCurrentDialog(Pdialog);                               //~1408I~
    }                                                              //~1310I~
    public void setParent()                                        //~1311R~
    {                                                              //~1311I~
    	if (AG.currentIsDialog)                                    //~1311I~
        	parentWindow=AG.currentDialog;                         //~1408R~
        else                                                       //~1311I~
        	parentWindow=AG.currentFrame;                          //~1408R~
    }                                                              //~1311I~
    private void setDirectParent(Component Padded)                 //~1417I~
    {                                                              //~1417I~
    	Padded.directParentComponent=this;                         //~1417I~
    }                                                              //~1417I~
    public Component getDirectParent()                             //~1417I~
    {                                                              //~1417I~
    	return directParentComponent;                              //~1417I~
    }                                                              //~1417I~
//******************                                               //~1310I~
    public void add(View Pview)                                    //~1112I~
    {
    }                                                              //~1112I~
    public void add(String Pname,Component Pcomponent)    //from CardPanel//~1217R~
    {                                                              //~1124I~
        if (Dump.Y) Dump.println("Component add:"+Pname);          //~1506R~
	    setDirectParent(Pcomponent);                               //~1417I~
        if (Pname.equals(Global.resourceString("Server_Connections")))//~1217I~
			AG.ajagov.initMainFrameTab(Frame.MainFrame);           //~1217I~
                                                                   //~1217I~
    }                                                              //~1124I~
    public void add(String Pname,Viewer Pviewer)    //from ConnectionFrame//~1219I~
    {                                                              //~1219I~
    }                                                              //~1219I~
    public void add(String Pname,List Plist)    //from SystemLister//~1220I~
    {                                                              //~1220I~
    }                                                              //~1220I~
    public void add(Component Pcomponent)    //from Panel3D; Pcompnent is panel containing Button//~1217I~
    {                                                              //~1217I~
	    setDirectParent(Pcomponent);                               //~1417I~
    }                                                              //~1217I~
    public void add(String s,Canvas Pcanvas)     //Canvas-->Object //~1116I~
    {                                                              //~1116I~
	    setDirectParent(Pcanvas);                                  //~1417I~
    }                                                              //~1116I~
    public void add(String s,Go Pgo)                               //~1125I~
    {                                                              //~1125I~
    }                                                              //~1125I~
    public void add(jagoclient.gui.ButtonAction Pbuttonaction)        //~1126I~
    {                                                              //~1126I~
	    setDirectParent(Pbuttonaction);                            //~1417I~
    }                                                              //~1126I~
    public void remove(Component P1) //for GoFrame                 //~1216R~
    {                                                              //~1216I~
    }                                                              //~1216I~
    public Component(View P1,double P2,View P3,double P4)  //GoFrame->SimplePanel//~1216R~
    { 

    }                                                              //~1216I~
//*****************                                                //~1121I~
    public void show()                                             //~1117I~
    {                                                              //~1117I~
//  	AG.ajagoc.setContentView(layout);                         //~1117I~//~1125R~
    }                                                              //~1117I~
//*****************                                                //~1122I~
    public Image createImage(MemoryImageSource Ppixel)                       //~1117I~//~1120I~
    {                                                              //~1227R~
    	return Image.createImage(Ppixel,this);                          //~1117R~//~1227R~
    }                                                              //~1117I~//~1120I~
//***************** from Global                                    //~1417I~
    public Image createImage(int Pw,int Ph)                        //~1417I~
    {                                                              //~1417I~
    	if (Dump.Y) Dump.println("Component.createImage return null w="+Pw+",h="+Ph);//~1506R~
    	return null;                                               //~1417I~
    }                                                              //~1417I~
//*****************                                                //~1122I~
// for Global                                                      //~1417I~
//*****************                                                //~1417I~
    public void pack()                                             //~1417R~
    {                                                              //~1122I~
    }                                                              //~1122I~
    public Dimension getSize()                                     //~1417I~
    {                                                              //~1417I~
        return new Dimension(0,0);                                 //~1417I~
    }                                                              //~1417I~
    public void setBounds(int Px,int Py,int Pw,int Ph)             //~1417I~
    {                                                              //~1417I~
    }                                                              //~1417I~
//***********                                                      //~1417I~
    public void setLayout(FlowLayout P1)	                       //~1124M~//~1125R~
    {                                                              //~1124M~
    }                                                              //~1124M~//~1125R~
    public void setLayout(GridLayout P1)                          //~1124M~//~1125R~
    {                                                              //~1124M~
    }                                                              //~1124M~
    public void setLayout(BorderLayout P1)                        //~1124M~//~1125R~
    {                                                              //~1124M~
    }                                                              //~1124M~
    public void setLayout(CardLayout P1)                           //~1125I~
    {                                                              //~1125I~
    }                                                              //~1125I~
    public void doLayout()                                         //~1221I~
    {                                                              //~1221I~
    }                                                              //~1221I~
//*for rene.Lister                                                 //~1216I~
    public void addKeyListener(KeyListener Pkl)                    //~1216I~
    {                                                              //~1216I~
    }                                                              //~1216I~
//*for TextDisplay,Panel3D                                         //~1417R~
    public void setBackground(Color Pcolor)                        //~1127I~//~1212I~//~1216I~
    {                                                              //~1127I~//~1212I~//~1216I~
      componentBackground=Pcolor;                                             //~1212I~//~1213R~//~1216I~
    }                                                              //~1127I~//~1212I~//~1216I~
//from TextComponent                                               //~1310R~
    public void setBackground(View Pview,Color Pcolor)             //~1310I~
    {                                                              //~1310I~
    	bgcolor=Pcolor;                                            //~1310I~
    	runOnUiThread(CASE_BGCOLOR,Pview);   //by Component        //~1310I~
    }                                                              //~1310I~
    public void setBackground(View Pview,Color Pcolor,Color Ptextcolor)//~1312I~
    {                                                              //~1312I~
    	bgcolor=Pcolor;                                            //~1312I~
    	fgcolor=Ptextcolor;                                        //~1312I~
    	runOnUiThread(CASE_BGFGCOLOR,Pview);   //by Component      //~1312I~
    }                                                              //~1312I~
    public void setBackgroundUI(Object Pparm)                     //~1310I~
    {                                                              //~1310I~
    	bgcolor.setBackground((View)Pparm);                        //~1310I~
    }                                                              //~1310I~
//******************************************************************//~v1EbI~
//button background drawable from Button                           //~v1EbI~
    public void setBackground(View Pview,Drawable Pdrawable)       //~v1EbI~
    {                                                              //~v1EbI~
    	bgDrawable=Pdrawable;                                      //~v1EbI~
    	runOnUiThread(CASE_BGDRAWABLE,Pview);   //by Component     //~v1EbI~
    }                                                              //~v1EbI~
    public void setBackgroundDrawableUI(Object Pparm)              //~v1EbI~
    {                                                              //~v1EbI~
    	((View)Pparm).setBackgroundDrawable(bgDrawable);                   //~v1EbI~
    }                                                              //~v1EbI~
//******************************************************************//~v1EbI~
    public void setBGFGUI(Object Pparm)                            //~1312I~
    {                                                              //~1312I~
    	bgcolor.setBackground((View)Pparm);                        //~1312I~
    	fgcolor.setTextColor((View)Pparm);                         //~1312I~
    }                                                              //~1312I~
    public void setFont(View Pview,Font Pfont)                     //~1310I~
    {                                                              //~1310I~
    	font=Pfont;                                                //~1310I~
    	runOnUiThread(CASE_FONT,Pview);   //by Component           //~1310I~
    }                                                              //~1310I~
    public void setFontUI(Object Pparm)                            //~1310I~
    {                                                              //~1310I~
    	font.setFont((TextView)Pparm);                                 //~1310I~
    }                                                              //~1310I~
//***                                                              //~1310I~
    public void resized()                                          //~1212I~
    {                                                              //~1212I~
    	                  //~1212I~
    }                                                              //~1212I~
//*for rene.CloseFrame                                             //~1213I~
	public Point getLocation()                                     //~1213I~
    {                                                              //~1213I~
    	return new Point(0,0);                                     //~1213I~
    }                                                              //~1213I~
	public void setLocation(int Px,int Py)                         //~1213I~
    {                                                              //~1213I~
    }                                                              //~1213I~
	public void setSize(int Pw,int Ph)                             //~1213I~
    {                                                              //~1213I~
    }                                                              //~1213I~
	public Toolkit getToolkit()                                       //~1213I~
    {                                                              //~1213I~
    	return new Toolkit();                                      //~1213I~
    }                                                              //~1213I~
    public void addMouseListener(MouseListener Pml)                //~1213I~
    {                                                              //~1213I~
    	if (Pml instanceof DoActionListener)
		 {
		}
    }                                                              //~1213I~
    public void addMouseListener(MouseAdapter Pma) //rene.lister.ListerPanel//~1214I~
    {                                                              //~1214I~
    }                                                              //~1214I~
    public void addMouseMotionListener(MouseMotionListener Pmml)   //~1213I~
    {
    }                                                              //~1213I~
    public void addMouseWheelListener(Wheel Pw)                    //~1213R~
    {                                                              //~1213I~
    }                                                              //~1213I~
    public void addActionListener(ActionListener Pal)              //~1213I~
    {                                                              //~1213I~
    }                                                              //~1213I~
    public Dimension getMinimumSize()	//for Panel3D              //~1214R~
    {                                                              //~1214I~
    	return new Dimension(0,0);                                 //~1214I~
    }                                                              //~1214I~
    public Dimension getPreferredSize()                            //~1214R~
    {                                                              //~1214I~
    	return new Dimension(10,10);                               //~1214I~
    }                                                              //~1214I~
    public Color getBackground()                                   //~1214R~
    {                                                              //~1214I~
    	return componentBackground;                                //~1214I~
    }                                                              //~1214I~
    public void addFocusListener(FocusListener Pfl)                //~1213I~//~1214I~
    {                                                              //~1213I~//~1214I~
    }                                                              //~1213I~//~1214I~
//****************                                                 //~1405I~
    public void requestFocus()  //for Board ,moved to Canvas       //~1405R~
    {                                                              //~1405R~
    	Frame f=AG.getCurrentFrame();                              //~v108R~
        if (f!=null)                                               //~v108R~
        {                                                          //~v108I~
        	int fid=f.framelayoutresourceid;                       //~v108I~
            if (fid==AG.frameId_ConnectionFrame)  //"Input" TextField//~v108R~
                return;                                            //~v108R~
            if (fid==AG.frameId_ConnectedGoFrame) //"ExtraSendField"//~v108R~
                return;                                            //~v108R~
        }                                                          //~v108I~
        if (componentView!=null)                                   //~1405R~
	    	runOnUiThread(CASE_FOCUS,null);                        //~1405I~
    }                                                              //~1405R~
//****************                                                 //~v108I~
    public void requestFocus(View Pview)  //for connectedGoFrame   //~v108R~
    {                                                              //~v108I~
        if (Pview!=null)                                           //~v108R~
	    	runOnUiThread(CASE_FOCUS,Pview);                       //~v108R~
    }                                                              //~v108I~
//****************                                                 //~v108I~
    public void requestFocusFromTouch()                            //~v108I~
    {                                                              //~v108I~
        if (componentView!=null)                                   //~v108I~
	    	runOnUiThread(CASE_FOCUSTOUCH,null);                   //~v108I~
    }                                                              //~v108I~
//****************                                                 //~v108I~
    private void requestFocusUI(Object Pparm)                      //~1405I~
    {                                                              //~1405I~
    	if (Pparm!=null)                                           //~v108I~
        {                                                          //~v108I~
	    	if (Dump.Y) Dump.println("Componet.requestFocus view="+Pparm.toString());//~v108I~
        	((View)Pparm).requestFocus();                          //~v108I~
        }                                                          //~v108I~
    	if (Dump.Y) Dump.println("Componet.requestFocus view="+componentView.toString());//~1506R~
        if (Dump.Y) Dump.println("focusable="+componentView.isFocusable()+",touch="+componentView.isFocusableInTouchMode()+",isfocused="+componentView.isFocused());//~1506R~
        componentView.requestFocus();                              //~1405I~
        if (Dump.Y) Dump.println("after focusable="+componentView.isFocusable()+",touch="+componentView.isFocusableInTouchMode()+",isfocused="+componentView.isFocused());//~1506R~
    }                                                              //~1405I~
//****************                                                 //~v108I~
    private void requestFocusFromTouchUI(Object Pparm)                 //~v108I~
    {                                                              //~v108I~
    	if (Dump.Y) Dump.println("Componet.requestFocusFromTouch view="+componentView.toString());//~v108I~
        componentView.requestFocusFromTouch();                     //~v108I~
    }                                                              //~v108I~
//****************                                                 //~1405I~
    public void validate()  //for Frame and Dialog                                       //~1125I~//~1215I~//~1216I~
    {                                                              //~1125I~//~1215I~//~1216I~
    }                                                              //~1125I~//~1215I~//~1216I~
    public void paint(Graphics Pg)                                    //~1216I~//~1219I~
    {                                                              //~1216I~//~1219I~
    }                                                              //~1216I~//~1219I~
//********************************                                 //~1221I~
//* support All runOnUiThread                                      //~1221I~
//********************************                                 //~1221I~
    public void runOnUiThread(int Pcase,Object Pparm)              //~1221I~
    {                                                              //~1221I~
    	caseUiThread=Pcase;                                        //~1221I~
        boolean wait=waitmode(Pcase);                                   //~1310I~
		AjagoUiThread.runOnUiThread(wait,this,Pparm);               //~1221I~//~1310R~
    }                                                              //~1221I~
    boolean waitmode(int Pcase)                                    //~1310I~
    {                                                              //~1310I~
    	boolean waitmode=true;                                     //~1310I~
//        if ((parentWindow!=null)                                 //~1425R~
//        &&  (parentWindow instanceof Frame)                      //~1425R~
//        &&  ((Frame)parentWindow).componentType==COMPONENT_FRAME                         //~1310I~//~1425R~
//        &&  ((Frame)parentWindow).isBoardFrame                   //~1425R~
//        )                                                        //~1425R~
//            waitmode=false; //avoid deadlock on BoardFrame                                        //~1310I~//~1425R~
//		now Board operation executed on subtread, so wait dose not lock main threasd//~1425I~
        return waitmode;                                           //~1310I~
    }                                                              //~1310I~
//************                                                     //~1310I~
	@Override                                                      //~1221I~
    public void runOnUiThread(Object Pparm)                        //~1221I~
    {                                                              //~1221I~
        if (Dump.Y) Dump.println("Component runOnUi case="+caseUiThread);//~1506R~
        switch(caseUiThread)                                       //~1221I~
        {                                                          //~1221I~
        case CASE_APPEND:                                          //~1221I~
        	appendUI(Pparm);                                       //~1221I~
            break;                                                 //~1221I~
        case CASE_SETTEXT:                                         //~1221I~
        	setTextUI(Pparm);                                      //~1221I~
            break;                                                 //~1221I~
        case CASE_SHOWBOTTOM:                                      //~1221I~
        	showBottomUI(Pparm);                                   //~1221I~
            break;                                                 //~1221I~
        case CASE_TITLE:                                           //~1221I~
        	setTitleUI(Pparm);                                     //~1221I~
            break;                                                 //~1221I~
        case CASE_BGCOLOR:                                         //~1310I~
        	setBackgroundUI(Pparm);                                //~1310I~
            break;                                                 //~1310I~
        case CASE_BGFGCOLOR:                                       //~1312I~
        	setBGFGUI(Pparm);                                      //~1312I~
            break;                                                 //~1312I~
        case CASE_SETICON:                                         //~1313I~
        	setIconImageUI(Pparm);                                 //~1417R~
            break;                                                 //~1313I~
        case CASE_ENABLE:                                          //~1322I~
        	setEnabledUI(Pparm);                                   //~1322I~
            break;                                                 //~1322I~
        case CASE_SETCHECK:                                        //~1322I~
        	setCheckedUI(Pparm);                                   //~1322I~
            break;                                                 //~1322I~
        case CASE_FOCUS:                                        //~1405I~
        	requestFocusUI(Pparm);                                 //~1405I~
            break;                                                 //~1405I~
        case CASE_FOCUSTOUCH:                                      //~v108I~
        	requestFocusFromTouchUI(Pparm);                        //~v108I~
            break;                                                 //~v108I~
        case CASE_SETITEMCHECKED:                                  //~v105I~
        	setItemCheckedUI(Pparm);                               //~v105I~
            break;                                                 //~v105I~
        case CASE_SETVISIBILITY:                                   //~@@@@I~//~1B04I~
        	setVisibilityUI(Pparm);                                //~@@@@I~//~1B04I~
            break;                                                 //~@@@@I~//~1B04I~
        case CASE_INFLATE:                                         //~1B43I~
        	inflateUI(Pparm);                                      //~1B43I~
            break;                                                 //~1B43I~
        case CASE_BGDRAWABLE:                                      //~v1EbI~
        	setBackgroundDrawableUI(Pparm);                        //~v1EbI~
            break;                                                 //~v1EbI~
        }                                                          //~1221I~
    }                                                              //~1221I~
//*****************                                                //~1221I~
    public void append(TextView Ptextview,ScrollView Pscrollview,String Pline,Color Pcolor)//~1221R~
    {                                                              //~1221I~
    	line=Pline;                                                //~1221I~
        color=Pcolor;                                              //~1221I~
        scrollview=Pscrollview;                                    //~1221I~
    	runOnUiThread(CASE_APPEND,Ptextview);                      //~1221I~
    }                                                              //~1221I~
    private void appendUI(Object Pparm)                            //~1221I~
    {                                                              //~1221I~
    	TextView textview=(TextView)Pparm;                         //~1221I~
        textview.append(line);                                     //~1221I~
        if (Dump.Y) Dump.println("Component appendUI view="+textview.toString()+",line="+line);//~v1D1R~
        if (color!=null)                                           //~1221I~
        {                                                          //~1221I~
            textview.setTextColor(color.getRGB());                 //~1221I~
		}                                                          //~1221I~
        if (scrollview!=null)                                     //~1221I~
        {                                                          //~1221I~
            if (Dump.Y) Dump.println("Component appendUI scrollto bottom");//~v1DqI~
//      	scrollview.scrollTo(0/*x*/,32760/*y:Bottom*/);         //~v1DqR~
        	scrollview.fullScroll(ScrollView.FOCUS_DOWN);          //~v1DqI~
		}                                                          //~1221I~
    }                                                              //~1221I~
//*****************                                                //~1221I~
    public void setText(TextView Ptextview,String Pline)           //~1221I~
    {                                                              //~1221I~
    	line=Pline;                                                //~1221I~
    	runOnUiThread(CASE_SETTEXT,Ptextview);                //~1221I~
    }                                                              //~1221I~
    private void setTextUI(Object Pparm)                           //~1221I~
    {                                                              //~1221I~
    	TextView textview=(TextView)Pparm;                         //~1221I~
        if (Dump.Y) Dump.println("Component setTextUI view="+textview.toString()+",line="+line);//~v1D1I~
        textview.setText(line);                                    //~1221I~
    }                                                              //~1221I~
//*****************                                                //~1221I~
    public void showList(ListView Plistview,int Ppos)            //~1221R~
    {                                                              //~1221I~
    	if (Dump.Y) Dump.println("Component:showBottom pos="+pos); //~1506R~
    	pos=Ppos;                                                  //~1221I~
    	runOnUiThread(CASE_SHOWBOTTOM,Plistview);                  //~1221I~
    }                                                              //~1221I~
    private void showBottomUI(Object Pparm)                        //~1221I~
    {                                                              //~1221I~
    	if (Dump.Y) Dump.println("Component:showBottomUI pos="+pos);//~1506R~
    	ListView listview=(ListView)Pparm;                         //~1221I~
        ListAdapter adapter=listview.getAdapter();                 //~1221I~
        ((BaseAdapter)adapter).notifyDataSetChanged();
        if (pos<0) //keep currenttop
        	pos=listview.getFirstVisiblePosition();//~1221R~
        listview.setSelectionFromTop(pos,0);                       //~1221I~
    }                                                              //~1221I~
//*****************                                                //~v105I~
    public void setItemChecked(ListView Plistview,int Ppos,boolean Pstate)//~v105I~
    {                                                              //~v105I~
    	if (Dump.Y) Dump.println("Component:setItemChecked pos="+Ppos);//~v105I~
    	pos=Ppos;                                                  //~v105I~
    	listItemState=Pstate;                                      //~v105I~
    	runOnUiThread(CASE_SETITEMCHECKED,Plistview);              //~v105I~
    }                                                              //~v105I~
    private void setItemCheckedUI(Object Pparm)                    //~v105I~
    {                                                              //~v105I~
    	if (Dump.Y) Dump.println("Component:setItemChecked pos="+pos);//~v105I~
    	ListView listview=(ListView)Pparm;                         //~v105I~
        listview.setItemChecked(pos,listItemState);                //~v105I~
    }                                                              //~v105I~
//*****************                                                //~1221I~
    public void setTitle(String Ptitle)                            //~1221I~
    {                                                              //~1221I~
    	runOnUiThread(CASE_TITLE,Ptitle);                          //~1221I~
    }                                                              //~1221I~
    private void setTitleUI(Object Pparm)                          //~1221I~
    {                                                              //~1221I~
	    AG.activity.setTitle((String)Pparm);                       //~1221I~
    }                                                              //~1221I~
//************************                                         //~1417I~
//*from CloseFrame:seticon, through Hashtable                      //~1417I~
//************************                                         //~1417I~
    public void setIconImage(Image Pimage)                         //~1417I~
    {                                                              //~1417I~
    	this.seticonImage(Pimage.iconFilename);                    //~1417R~
    }                                                              //~1417I~
//************************                                                //~1221I~//~1312R~
//*set title bar  icon                                             //~1312I~
//************************                                         //~1312I~
    public void seticonImage(String Pfilename)                     //~1417R~
    {                                                              //~1312I~
    	int resid;                                                 //~1326I~
    	if (Pfilename==null)                             //~1312I~ //~1326R~
        	resid=iconresid;	//restore                          //~1326I~
        else                                                       //~1326I~
    		resid=AG.findIconId(Pfilename);                             //~1312I~//~1326R~
        if (resid<=0)                                              //~1312I~
        	return;                                                //~1312I~
        iconresid=resid;                                           //~1313I~
    	runOnUiThread(CASE_SETICON,null);                          //~1313I~
    }                                                              //~1312I~
    public void setIconImageUI(Object Pparm)                       //~1417R~
    {                                                              //~1313I~
      if (AG.osVersion<AG.HONEYCOMB)  //<android3=api-11           //~v1D1I~
      {                                                            //~v1D1I~
    	android.view.Window w; 
                   //~0914R~//~0915R~//~0A09R~//~1312I~//~1329R~//~1331I~
    	w=AG.activity.getWindow();                                 //~1313I~
    	w.setFeatureDrawableResource(android.view.Window.FEATURE_LEFT_ICON,iconresid);//~1313I~
      }                                                            //~v1D1I~
//      else                                                       //~v1D1R~
//      {                                                          //~v1D1R~
//        ActionBar ab;                                            //~v1D1R~
//        ab=AG.activity.getActionBar();                           //~v1D1R~
//        ab.setIcon(iconresid);    //setIcon is from API-14(abdroid-4)//~v1D1R~
//      }                                                          //~v1D1R~
    }                                                              //~1313I~
//*************************                                        //~1322I~
//*IconBar Button                                                  //~1322I~
//************************                                         //~1322I~
//*setEnabled requires Mainthread*                                 //~1425I~
    public void setEnabled(Button Pbutton,boolean Pstate)          //~1322I~
    {                                                              //~1322I~
    	if (Dump.Y) Dump.println("Component setenabled="+Pbutton.toString());//~1506R~
    	buttonState=Pstate;                                        //~1322I~
    	runOnUiThread(CASE_ENABLE,Pbutton);                        //~1425R~
    }                                                              //~1322I~
    private void setEnabledUI(Object Pparm)                           //~1322I~
    {                                                              //~1322I~
    	Button button=(Button)Pparm;                               //~1322I~
        button.androidButton.setEnabled(buttonState);                            //~1322I~
    }                                                              //~1322I~
//*****************                                                //~1312I~
//    public void setChecked(RadioButton Pbutton,boolean Pstate)     //~1322I~//~2B13R~
//    {                                                              //~1322I~//~2B13R~
//        buttonState=Pstate;                                        //~1322I~//~2B13R~
//        runOnUiThread(CASE_SETCHECK,Pbutton);                      //~1322I~//~2B13R~
//    }                                                              //~1322I~//~2B13R~
//************                                                     //~2B13I~
    public void setChecked(android.widget.Button Pbutton,int PdrawableId)//Iconbar Toggle button//~2B13R~
    {                                                              //~2B13I~
    	if (PdrawableId==0)                                        //~2B13I~
        	return;                                                //~2B13I~
    	drawableResourceId=PdrawableId;                            //~2B13I~
    	runOnUiThread(CASE_SETCHECK,Pbutton);                      //~2B13I~
    }                                                              //~2B13I~
    private void setCheckedUI(Object Pparm)                          //~1322I~
    {                                                              //~1322I~
//  	RadioButton button=(RadioButton)Pparm;                     //~1322I~//~2B13R~
//      button.setChecked(buttonState);                            //~1322I~//~2B13R~
	    android.widget.Button button=(android.widget.Button)Pparm; //~2B13I~
        button.setBackgroundResource(drawableResourceId);          //~2B13I~
    }                                                              //~1322I~
//*****************                                                //~1322I~
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
//ImageObserver Interface for EmptyPaint:save()
		return false;
	}
//***********************************************************                                                //~1A2dI~//~1B04I~
	public static void setVisible(View Pview,int Pvisible)         //~1A2dI~//~1B04I~
    {                                                              //~1A2dI~//~1B04I~
		(new Component()).setVisibility(Pview,Pvisible);           //~1A2dI~//~1B04I~
	}                                                              //~1A2dI~//~1B04I~
    public void setVisibility(View Pview,int Pvisibility)          //~@@@@I~//~1B04I~
    {                                                              //~@@@@I~//~1B04I~
    	visibility=Pvisibility;                                    //~@@@@I~//~1B04I~
    	runOnUiThread(CASE_SETVISIBILITY,Pview);   //by Component  //~@@@@I~//~1B04I~
    }                                                              //~@@@@I~//~1B04I~
//*****************                                                //~@@@@I~//~1B04I~
    private void setVisibilityUI(Object Pparm)                       //~@@@@I~//~1B04I~
    {                                                              //~@@@@I~//~1B04I~
	    View v=(View)Pparm;                                        //~@@@@I~//~1B04I~
        v.setVisibility(visibility);                               //~@@@@I~//~1B04I~
    }                                                              //~@@@@I~//~1B04I~
//*****************                                                //~1B43I~
    public void inflate(int Presid)                                //~1B43I~
    {                                                              //~1B43I~
        if (Dump.Y) Dump.println("Component:inflate id="+Integer.toHexString(Presid));//~1B43I~//+v1EbR~
    	Integer inflateResid=new Integer(Presid);                   //~1B43I~
    	runOnUiThread(CASE_INFLATE,inflateResid);                  //~1B43I~
    }                                                              //~1B43I~
//*****************                                                //~1B43I~
    private void inflateUI(Object Pparm)                           //~1B43I~
    {                                                              //~1B43I~
	    int resid=((Integer)Pparm).intValue();                     //~1B43I~
    	inflatedView=AG.inflater.inflate(resid,null);              //~1B43I~
        if (Dump.Y) Dump.println("inflatUI id="+Integer.toHexString(resid)+",view="+inflatedView.toString());//~1B43I~
    }                                                              //~1B43I~
}//class                                                           //~1112I~
