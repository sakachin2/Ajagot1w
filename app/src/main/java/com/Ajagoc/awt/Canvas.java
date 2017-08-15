//*CID://+1Aj2R~: update#= 375;                                    //~1Ag9R~//+1Aj2R~
//**********************************************************************//~1107I~
//1Aj2 2017/02/04 When release version, board is drawn as bellow half is black sqaure. try insert sleep.//+1Aj2I~
//1Ag9 2016/10/09 Bitmap OutOfMemory;JNI Global reference remains. //~1Ag9I~
//                try to clear ref to bitmap from Image:fieldBitmap, Graphics:targetBitmap, android.Graphics.Canvas(<--Image:androidCanvas, Graphics:androidCanvas)//~1Ag9I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing, (Dump.T) for UiThread//~1A6AI~
//v1Em 2014/12/15 memory leak:boardSync did not stoped             //~v1EmI~
//v1Ba 2014/08/14 Canvas enqRequest callback for gtp callback gotMoved//~v1B7I~
//v1B7 2014/08/11 display komi for "local count";(stackoverflowerror may occur if no stone set)//~v1B7I~
//1B33 2013/07/01 draw cursor is duplicated when showTarget() is true//~1B33I~
//1B0g 130430 catch OutOfMemoryError                               //~1B0gI~
//1B0e 130429 option of one touch mode for local and connected board//~1B0eI~
//1073:121207 NPE at Canvas:enqRequest                             //~v107I~
//v101:120514 (Axe)android3(honeycomb) tablet has System bar at bottom that hide xe button line with 48pix height//~v101I~
//**********************************************************************//~v101I~
//*Canvas for Board                                                //~1225R~
//*  AWT APP:repaint()-->( update() )-->paint()                    //~1225I~
//**********************************************************************//~1107I~
package com.Ajagoc.awt;                                         //~1107R~  //~1108R~//~1109R~//~1117R~

import java.util.LinkedList;                                       //~1420I~
                                                                   //~1420I~
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.StopThread;
import jagoclient.board.Board;
import jagoclient.board.GoFrame;                                   //~1B33I~
import jagoclient.board.ConnectedBoard;                            //~1B0eI~
import jagoclient.gui.ActionTranslator;
import jagoclient.gui.BigLabel;
import jagoclient.gui.DoActionListener;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoKey;
import com.Ajagoc.AjagoKeyI;                                       //~1401I~
import com.Ajagoc.AjagoMenu;
import com.Ajagoc.AjagoUiThreadI;
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;
import com.Ajagoc.awt.Dimension;
import com.Ajagoc.awt.Image;
import com.Ajagoc.awt.KeyListener;                                 //~1117I~
import com.Ajagoc.awt.FontMetrics;
import com.Ajagoc.awt.Component;


import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Canvas extends Component	//for createwood(Component:Board)//~1120R~//~1121R~
        implements AjagoUiThreadI                                  //~1425R~
{                                                                  //~0914I~
	private static final String ACTION_SETSTONE="SetStone";        //~1424R~
	private static final int ENQ_OVERFLOW_LIMIT=5;                 //~1427R~
	private Graphics graphics;		//Ajagoc.Graphic               //~1504R~
	private Font font;                                             //~1421R~
	private ImageCanvas imageCanvas;                               //~1421R~
    private int boardsz;                                               //~1221I~                        //~1227I~
    public Image 	boardImageCopy;                                //~1421R~
    public Image 	activeImage;                                   //~1421I~
    public boolean  drawBoardImageCopy;                            //~1421R~
    public Rect     boardRect;                                     //~1421I~
    private KeyListener boardKeyListener_a,boardKeyListener_b;     //~1503R~
    private MouseListener boardMouseListener;                      //~1421R~
    private MouseMotionListener boardMouseMotionListener;          //~1421R~
    public int paternSize,boxSize,boardOrigin,boardEdge;           //~1419R~
    private int curPosX,curPosY;                                   //~1421R~
    private static final int cursorBitmapSize=5;                   //~1318R~
    private static final int cursorBitmapSize2=cursorBitmapSize/2;  //~1317I~
    protected boolean swPainting=false;                              //~1318I~
	private	Paint cursorPaint=new Paint(Paint.ANTI_ALIAS_FLAG);    //~1317I~
	private	Paint cursorPaintStroke=new Paint(Paint.ANTI_ALIAS_FLAG);//~1B33I~
	private	static final Color cursorColor=Color.white;            //~1318R~                                                //~1415I~
	BoardSync boardSync;                                           //~1421I~
    Board  board;                                                  //~1422I~
    private boolean	swCloseRequested;                              //~1424I~
    public static long trackball_oldmovetime;                      //~1426R~
    public static float trackball_accumX,trackball_accumY;         //~1426R~
    protected Frame canvasFrame;                                     //~1503I~
    private static boolean SshortcutDirectionKey;                  //~1507R~
    private static int Sonetouchmode;                              //~1B0eI~
    public static final int  ONETOUCH_LOCALBOARD =0x01;            //~1B0eI~
    public static final int  ONETOUCH_MATCHBOARD =0x02;            //~1B0eI~
    private boolean swLocalBoard;                                  //~1B0eI~
//******************************                                   //~1217I~
	public Canvas()                                                //~1117R~
    {                                                              //~1217R~
    //************************                                     //~1221I~
    	canvasFrame=AG.currentFrame;                               //~1503R~
    	canvasFrame.setFrameType(this);	//for UI thread ctl        //~1503I~
        FrameLayout layout=(FrameLayout)AG.findViewById(AG.viewId_BoardPanel);//~1217I~//~1221M~
        if (Dump.C) Dump.println("Canvas:framelayout="+layout.toString());//~1506R~//~1A6AR~
		boardsz=getBoardSize();                                    //~1414R~
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(boardsz,boardsz);//~1221I~
        boardImageCopy=createImage(boardsz,boardsz);               //~1421I~
        if (boardImageCopy==null)                                  //~1B0gI~
        {                                                          //~1B0gI~
            return;                                                //~1B0gI~
        }                                                          //~1B0gI~
        if (Dump.C) Dump.println("Canvas:init boardImageCopy="+boardImageCopy.toString());//~1506R~//~1A6AR~
        graphics=boardImageCopy.getGraphics();                          //~1421I~
        graphics.setCanvas(this);	//request callback drawBitmap()//~1421I~
                                                                   //~1421I~
        imageCanvas=new ImageCanvas(this);                         //~1226R~
        componentView=imageCanvas;	//for requestFocus()           //~1420I~
        if (Dump.C) Dump.println("Canvas:imageCanvas="+imageCanvas.toString());//~1506R~//~1A6AR~
        layout.addView(imageCanvas,lp);                                //~1118M~//~1121R~//~1217I~//~1226R~
        initImageView();                                           //~1217I~
        if (Dump.C) Dump.println("Canvas:graphics="+graphics.toString());//~1506R~//~1A6AR~
        cursorPaint.setColor(cursorColor.getRGB());                //~1318R~
        cursorPaintStroke.setStyle(Style.STROKE);                  //~1B33I~
	    boardSync=new BoardSync(this);                             //~1422R~
		SshortcutDirectionKey=Global.getParameter(AjagoMenu.DIRECTIONKEY_CFGKEY,false);//~1507R~
		if (Global.getParameter(AjagoMenu.ONETOUCH_LOCAL_CFGKEY,false))//~1B0eI~
        	Sonetouchmode|=ONETOUCH_LOCALBOARD;                    //~1B0eI~
		if (Global.getParameter(AjagoMenu.ONETOUCH_MATCH_CFGKEY,false))//~1B0eI~
        	Sonetouchmode|=ONETOUCH_MATCHBOARD;                    //~1B0eI~
        swLocalBoard=!(this instanceof ConnectedBoard);            //~1B0eI~
    }                                                              //~1118M~//~1121R~//~1217I~
//******************                                               //~1507I~
//*from Ajago option menu                                          //~1507I~
//******************                                               //~1507I~
	public static void optionChanged(boolean Pflag)                //~1507I~
    {                                                              //~1507I~
		SshortcutDirectionKey=Pflag;                               //~1507I~
    }                                                              //~1507I~
	public static void optionChanged(int Popt,boolean Pflag)       //~1B0eI~
    {                                                              //~1B0eI~
    	if (Pflag)                                                 //~1B0eI~
        	Sonetouchmode|=Popt;                                   //~1B0eI~
        else                                                       //~1B0eI~
        	Sonetouchmode&=~Popt;                                  //~1B0eI~
        if (Dump.C) Dump.println("Canvas onetouchmode="+Sonetouchmode);//~1B0eI~//~1A6AR~
    }                                                              //~1B0eI~
//******************                                               //~1507I~
	public static int getBoardSize()		                       //~1504R~
    {                                                              //~1414I~
    	int boardsz;                                             //~1414I~
        if (AG.portrait)                                           //~1414I~
        	boardsz=AG.scrWidth;                                   //~1414I~
        else                                                       //~1414I~
        {                                                          //~1414I~
//          boardsz=AG.scrHeight-AjagoView.getFramePosition();   //remaining of titlebar hight//~1502I~//~v101R~
            boardsz=AG.scrHeight-AjagoView.getMargin();   //remaining of titlebar hight//~v101I~
        }                                                          //~1414I~
	    if (Dump.C) Dump.println("Canvas:getBoardSize boardsize="+boardsz);//~1506R~//~1A6AR~
        return boardsz;                                          //~1414I~
    }                                                              //~1414I~
//******************
	public Dimension getSize()                                     //~1117I~
    {                                                              //~1117I~
      	return new Dimension(boardsz,boardsz);                     //~1221I~
    }                                                              //~1117I~
//******************************************                       //~1216I~
    private void initImageView()
    {
    	if (Dump.C) Dump.println("init Canvas ImageView id="+Integer.toString(imageCanvas.getId(),16));//~1506R~//~1A6AR~
		imageCanvas.setWillNotDraw(false);	//reset default "don't call onDraw"//~1226R~
       	imageCanvas.setFocusableInTouchMode(true);                 //~1226R~
       	imageCanvas.setClickable(true);                            //~1426I~
    }
//******************************************                       //~1216I~
    public void addKeyListener(KeyListener Pkl)                    //~1116I~//~1117M~
    {                                                              //~1116I~//~1117M~                                     //~1216I~
        if (imageCanvas==null)  //when outofmemory                 //~1B0gI~
            return;                                                //~1B0gI~
        AjagoKey akl=AjagoKey.addKeyListener(imageCanvas);                   //~1116I~//~1226R~
        akl.setKeyListener(imageCanvas);                                   //~1116I~//~1317R~
        if (boardKeyListener_a==null)                              //~1503I~
        {                                                          //~1503I~
        	boardKeyListener_a=Pkl;                                //~1503R~
    	    if (Dump.C) Dump.println("CAnvas:addKLeyListenr_a"+Pkl.toString());//~1506R~//~1A6AR~
        }                                                          //~1503I~
        else                                                       //~1503I~
        {                                                          //~1503I~
        	boardKeyListener_b=Pkl;       //call a then callb      //~1503I~
    	    if (Dump.C) Dump.println("CAnvas:addKLeyListenr_b"+Pkl.toString());//~1506R~//~1A6AR~
        }                                                          //~1503I~
    }                                                              //~1116I~//~1117M~
//******************************************                       //~1216I~
    public void addMouseListener(MouseListener Pml)                //~1216I~
    {                                                              //~1216I~
        boardMouseListener=Pml;                                    //~1317I~
        board=(Board)Pml;                                          //~1422I~
    }                                                              //~1216I~
//******************************************                       //~1216I~
    public void addMouseMotionListener(MouseMotionListener Pmml)   //~1216I~
    {                                                              //~1216I~
        boardMouseMotionListener=Pmml;                             //~1413I~
    }                                                              //~1216I~
    private static int repaintctr;                                             //~1425I~
    public void repaint()                                          //~1117I~
    {                                                              //~1117I~
        if (Dump.C) Dump.println("Canvas:repaint ctr="+repaintctr);//~1506R~//~1A6AR~
        if (Dump.Y) Dump.println("Canvas:repaint ctr="+repaintctr);//~1Ag9I~
        if (imageCanvas!=null)                                     //~1313I~
        	imageCanvas.repaint();                                 //~1313I~
    }                                                              //~1117I~
    public void repaintBitmap(Image Pimage)	//from Graphics.drawImage();Board.paint()/copy() updated screen(BoardImageCopy)//~1421R~
    {                                                              //~1421I~
	    drawBoardImageCopy=true;                                   //~1421I~
       if (Dump.Y) Dump.println("repaintBitmap set drawBoardImageCopy=true");//~1Ag9I~
        activeImage=Pimage;                                        //~1421I~
        repaint();				//invalidate                       //~1421I~
    }                                                              //~1421I~
    public Image createImage(int Pw,int Ph) //OffscreenCanvas,ActiveImage,Empty,EmptyShadow//~1422R~
    {                                                              //~1117I~
	    if (Dump.Y) Dump.println("Canvas createImage Board("+Pw+","+Ph+")");//~1506R~//~1A6AR~
      try                                                          //~1B0gI~
      {                                                            //~1B0gI~
    	Image image=Image.createImage(Pw,Ph,this);	//board        //~1415R~
	    if (Dump.Y) Dump.println("Canvas:createImage bitmap="+image.bitmap.toString());//~1A6AI~
//      parentWindow.recyclePrepare(image.bitmap);	//recycle at window close//~1Ag9R~
        parentWindow.recyclePrepare(image);	//recycle at window close//~1Ag9I~
        return image;                                              //~1415I~
      }                                                            //~1B0gI~
      catch(OutOfMemoryError me)                                   //~1B0gI~
      {                                                            //~1B0gI~
		Dump.println(me,"Canvas:createImage:OffScreen");           //~1B0gI~
        AjagoView.memoryShortage("Canvas:createImage:OffScreen");  //~1B0gI~
      }                                                            //~1B0gI~
      return null;                                                 //~1B0gI~
    }                                                              //~1117I~
    public Image createImage(MemoryImageSource Pmis) //WhiteStone,BlackStone//~1422R~
    {                                                              //~1120I~
      try                                                          //~1B0gI~
      {                                                            //~1B0gI~
	    if (Dump.C) Dump.println("Canvas createImage ImageSrc="+((Object)Pmis).toString());//~1506R~//~1A6AR~
    	Image image=Image.createImage(Pmis,this);//stone paint     //~1415R~
//      parentWindow.recyclePrepare(image.bitmap);	//recycle at window close//~1Ag9R~
        parentWindow.recyclePrepare(image);	//recycle at window close//~1Ag9I~
    	return image;                                              //~1415I~
      }                                                            //~1B0gI~
      catch(OutOfMemoryError me)                                   //~1B0gI~
      {                                                            //~1B0gI~
		Dump.println(me,"Canvas:createImage:Stone");               //~1B0gI~
        AjagoView.memoryShortage("Canvas:createImage:Stone");      //~1B0gI~
      }                                                            //~1B0gI~
      return null;                                                 //~1B0gI~
    }                                                              //~1120I~
    public static FontMetrics getFontMetrics(Font Pfont)                  //~1504R~
    {                                                              //~1117M~
        Paint painttext=new Paint(Paint.ANTI_ALIAS_FLAG);          //~1117M~
        painttext.setTypeface(Pfont.getTypefaceStyle());           //~1117M~
        painttext.setTextSize(Pfont.getSize());                    //~1117M~
        return new FontMetrics(Pfont,painttext.getFontMetrics());        //~1117M~
    }                                                              //~1117M~
    public Graphics getGraphics()                                  //~1504R~
    {                                                              //~1120I~
        if (Dump.C) Dump.println("Canvas:getGraphic this="+this.toString()+",G="+graphics.toString());//~1506R~//~1A6AR~
    	return graphics;	//foreground canvas                    //~1224R~
    }                                                              //~1120I~
    public void dispose()                                          //~1226I~
    {                                                              //~1226I~
        if (Dump.C) Dump.println("Canvas:dispose()");              //~1506R~//~1A6AR~
    }                                                              //~1226I~
//*************************************                            //~1212R~
//*for TextDisplay ********************                            //~1212I~
//*************************************                            //~1212I~
//Canvas.getToolkit().getSystemClipboard()-->Clipboard
    public void addComponentListener(ComponentListener l)          //~1212I~
    {                                                              //~1212I~
    }                                                              //~1212I~
    public void setFont(Font Pfont)                                //~1213I~
    {                                                              //~1213I~
    	font=Pfont;                                                //~1213I~
    }                                                              //~1213I~
    public Font getFont()                                //~1213I~
    {                                                              //~1213I~
    	return font;                                               //~1213I~
    }                                                              //~1213I~
//*************************************                            //~1317I~
    public void setGeometry(int PpaternSize,int PboxSize,int PboardOrigin)//~1317I~
    {                                                              //~1317I~
    	paternSize=PpaternSize;                                    //~1317I~
    	boxSize=PboxSize;                                          //~1317I~
    	boardOrigin=PboardOrigin;                                  //~1317I~
    	boardEdge=PboardOrigin+boxSize*(paternSize-1);             //~1419I~
        curPosX=paternSize/2;  //initialy cursor set to center     //~1318I~
        curPosY=paternSize/2;                                      //~1318I~
        if (Dump.C) Dump.println("Canvas:geometry origin="+PboardOrigin);//~1506R~//~1A6AR~
    }                                                              //~1317I~
//**********************************************************       //~1422I~
//*From Board:set recycle for update old Image Pointer             //~1422I~
//**********************************************************       //~1422I~
    public void recycle(Image Pimage)                             //~1422I~
    {                                                              //~1422I~
    	if (Pimage!=null)                                          //~1422I~
        {                                                          //~1A6AI~
	    	if (Dump.Y) Dump.println("Canvas:recycle Image="+Pimage.toString()+",bitmap="+Pimage.bitmap.toString());//~1A6AR~
    		Pimage.recycle();                                      //~1422I~
        }                                                          //~1A6AI~
        else                                                       //~1A6AI~
        {                                                          //~1A6AI~
	    	if (Dump.Y) Dump.println("Canvas:recycle Image=null"); //~1A6AI~
        }                                                          //~1A6AI~
    }                                                              //~1422I~
//**********************************************************       //~1513I~
//*From biglabelPanel                                              //~1513I~
//**********************************************************       //~1513I~
    public void drawBigLabel(BigLabel Plabel,Graphics Pgraphics)   //~1513I~
    {                                                              //~1513I~
        enqRequest(new BoardRequest(BOARD_DRAW_BIGLABEL,Plabel,Pgraphics));//~1513I~
    }                                                              //~1513I~
//**********************************************************       //~v1B7I~
//*From GTPGoFrame                                                 //~v1B7I~
//**********************************************************       //~v1B7I~
    public void requestCallback(GoFrame Pgoframe,Object Pparm)     //~v1B7R~
    {                                                              //~v1B7I~
        enqRequest(new BoardRequest(BOARD_CALLBACK,Pgoframe,Pparm));//~v1B7R~
    }                                                              //~v1B7I~
//**********************************************************       //~1217I~
//*InnerClass                                                      //~1425R~
//**********************************************************       //~1425I~
//**********************************************************       //~1425I~
//*to get android Canvas and get control of onDraw                 //~1425I~
//**********************************************************       //~1425I~
    class ImageCanvas extends ImageView                            //~1216I~//~1217R~
    		implements MouseListener,AjagoKeyI,DoActionListener                   //~1317R~//~1424R~
    {                                                              //~1216I~
        private Canvas ajagoCanvas;                                //~1426R~
        private Board  board;                                      //~1426R~
        private Graphics graphics;                                 //~1426R~
    	private static final long TRACKBALL_MOVE_CHKTIME=1000;	//ignore remaining compiled movement value after 1second//~1426R~
    	//************                                                 //~1226I~
    	public ImageCanvas(Canvas Pcanvas)                         //~1216I~
        {   
    		super(AG.context);//~1216I~
            ajagoCanvas=Pcanvas;                                   //~1408I~
            board=(Board)Pcanvas;                                  //~1408I~
            graphics=Pcanvas.graphics;                             //~1408I~
	        initSetStoneButton();                                  //~1424I~
        }                                                          //~1216I~
//**************************************************************************//~1424I~
//* Label Lm is used as Button to setStone(alternative of mouse push)//~1424I~
//**************************************************************************//~1424I~
        private void initSetStoneButton()                          //~1424I~
        {                                                          //~1424I~
            if (Dump.C) Dump.println("init SetStoneButton");       //~1506R~//~1A6AR~
            Button buttonSetStone=new Button("--");                //~1424I~
            ActionTranslator at=new ActionTranslator(this,ACTION_SETSTONE);//~1424I~
            buttonSetStone.addActionListener(at);                                //~1424I~
        }                                                          //~1424I~
//*********************                                            //~1424I~
        public void doAction (String o)                            //~1424I~
        {   if (o.equals(ACTION_SETSTONE))                         //~1424I~
            {                                                      //~1424I~
	            if (Dump.C) Dump.println("ImageCAnvas:DoAction:setStone");//~1506R~//~1A6AR~
            	setStone();                                        //~1424I~
            }                                                      //~1424I~
        }                                                          //~1424I~
	    public void itemAction (String o, boolean flag){};         //~1424I~
        //************************************************         //~1313I~
		//*from Board:repaint(), offscreen updated                 //~1421R~
        //************************************************         //~1313I~
    	public void repaint()                                      //~1313I~
        {                                                          //~1313I~
	        if (Dump.C) Dump.println("imageCanvas repaint ctr="+repaintctr+",this="+this.toString());//~1506R~//~1A6AR~
	        if (Dump.Y) Dump.println("imageCanvas repaint invalidate swPainting="+swPainting+",ctr="+repaintctr+",this="+this.toString());//~1Ag9R~
            if (swPainting)                                        //+1Aj2R~
            {                                                      //+1Aj2R~
            	AjagoUtils.sleep(10/*ms*/);	                       //+1Aj2R~
            }                                                      //+1Aj2R~
    		if (AG.isMainThread())                                 //~1313I~
            {                                                      //~1313I~
            	this.invalidate();                                 //~1313I~
	            if (Dump.C) Dump.println("ImageCanvds UI Invalidate");//~1506R~//~1A6AR~
            }                                                      //~1313I~
        	else                                                   //~1313I~
            {                                                      //~1313I~
            	this.postInvalidate();	//call paint(),draw from ActiveImage//~1313I~
	            if (Dump.C) Dump.println("ImageCanvds postInvalidate");//~1506R~//~1A6AR~
            }                                                      //~1313I~
        }                                                          //~1313I~
        //************************************************         //~1304I~
        @Override                                                      //~0914R~//~1216I~
        protected void onDraw(android.graphics.Canvas Pcanvas)                           //~0914I~//~1117R~//~1216I~
        {                                                          //~1216I~
        	try                                                    //~1430I~
            {                                                      //~1430I~
                if (Dump.C) Dump.println("imageCanvas @onDraw ctr="+repaintctr+",this="+this.toString());//~1506R~//~1A6AR~
                if (Dump.C) Dump.println("imageCanvas @onDraw system canvas="+Pcanvas.toString());//~1506R~//~1A6AR~
                repaintctr--;                                      //~1430R~
                if (swCloseRequested)   //protect to use recycled Bitmap(boardImageCopy)//~1430R~
                    return;                                        //~1430R~
                if (Dump.Y) Dump.println("imageCanvas @onDraw drawBoardImageCopy="+drawBoardImageCopy);//~1Ag9I~
                if (!drawBoardImageCopy)                           //~1430R~
                {                                                  //~1430R~
                      enqRequest(new BoardRequest(BOARD_PAINT,graphics,null));//~1430R~
                }                                                  //~1430R~
                else                                               //+1Aj2R~
                {                                                  //~1Ag9I~
        	        drawBoardImageCopy=false;                      //+1Aj2R~
	                if (Dump.Y) Dump.println("imageCanvas @onDraw set drawBoardImageCopy=false");//~1Ag9I~
                }                                                  //~1Ag9I~
                                                                   //~1Ag9I~
                if (Dump.C) Dump.println("imageCanvas @onDraw BoardImageCopy:"+boardImageCopy.toString());//~1506R~//~1A6AR~
                if (Dump.C) Dump.println("imageCanvas @onDraw BoardImageCopy:"+boardImageCopy.bitmap.toString());//~1506R~//~1A6AR~
//              drawBoardImageCopy=false;                          //~1503I~//+1Aj2R~
                if (Dump.C) Dump.println("imageCanvas @onDraw frame current="+AG.currentFrame.toString()+",board frame="+ajagoCanvas.canvasFrame);//~1506R~//~1A6AR~
				if (ajagoCanvas.canvasFrame==AG.currentFrame)	//android canvas is the same for 2 board//~1503I~
                {                                                  //~1503I~
                    Pcanvas.drawBitmap(boardImageCopy.bitmap,0f,0f,null);//~1503R~
                    if (Dump.C) Dump.println("imageCanvas @onDraw BoardImageCopy:drawCursor");//~1506R~//~1A6AR~
                    if (Dump.Y) Dump.println("imageCanvas @onDraw BoardImageCopy:drawCursor");//~1Ag9I~
                    drawCursor(Pcanvas);                           //~1503R~
                }                                                  //~1503I~
                if (Dump.C) Dump.println("imageCanvas @onDraw system end");//~1506R~//~1A6AR~
            }                                                      //~1430I~
            catch(Exception e)                                     //~1430I~
            {                                                      //~1430I~
                Dump.println(e,"Canvas.ODraw exception");          //~1430I~
            }                                                      //~1430I~
                                                                   //~1425I~
                                                                   //~1224I~
        }                                                              //~0914I~//~1216I~
    //*********                                                    //~1317R~
        @Override                                                  //~1317R~
        public boolean keyPressedRc(KeyEvent e)                      //~1317R~
        {                                                          //~1317R~
        	boolean rc=false;                                            //~1317I~
            int poskeyid=getPositionChangeKeyId(e);                //~1317R~
            if (poskeyid<0)
            {                                                      //~1420I~
	        	enqRequest(new BoardRequest(BOARD_KEY_PRESS,e,null));//~1524R~
            }                                                      //~1420I~
            else                                                   //~1317I~
            	rc=true;                                           //~1317I~
            return rc;                                             //~1317I~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        @Override                                                  //~1317I~
        public boolean keyReleasedRc(KeyEvent e)                       //~1317R~
        {                                                          //~1317R~
            boolean rc=false;
        	int poskeyid=getPositionChangeKeyId(e);                //~1317R~
            if (poskeyid<0)  
            {                                                      //~1503I~
//              ajagoCanvas.boardKeyListener.keyReleased(e);       //~1420R~
            	;   //Booard has no special process for keyReleased//~1503R~
            }                                                      //~1503I~
            else
            {                                                      //~1317R~
            	if (poskeyid==0)                                   //~1317R~
                	setStone();                                    //~1317R~
            	else                                               //~1317R~
                	moveCursor(poskeyid);                          //~1317R~
              	rc=true;                                           //~1317R~
            }
            return rc;                                           //~1317I~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        @Override                                                  //~1317I~
        public void keyTyped (KeyEvent e)                          //~1317R~
        {                                                          //~1317R~
//          ajagoCanvas.boardKeyListener.keyTyped(e);              //~1420R~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        private int getPositionChangeKeyId(KeyEvent e)             //~1317R~
        {                                                          //~1317R~
            int rc=-1;                                             //~1317R~
            int keycode=e.getKeyCode();                            //~1317R~
            if (Dump.C) Dump.println("Canvas:getPositionChangeKeyId keycode="+keycode);//~1506R~//~1A6AR~
            switch(keycode)                                        //~1317R~
            {                                                      //~1317R~
                case KeyEvent.KEY_CENTER:                          //~1430R~
                case KeyEvent.VK_ENTER:                            //~1427I~
                    rc=0;                                          //~1317R~
                    break;                                         //~1317R~
                case KeyEvent.VK_DOWN :                            //~1317R~
                case KeyEvent.VK_UP :                              //~1317R~
                case KeyEvent.VK_LEFT :                            //~1317R~
                case KeyEvent.VK_RIGHT :                           //~1317R~
                	if (SshortcutDirectionKey)	//key used as shortcut on board//~1507I~
	                    break;		//return -1;                   //~1507I~
                    rc=keycode;                                    //~1317R~
                    break;                                         //~1317R~
            }                                                      //~1317R~
            if (Dump.C) Dump.println("Canvas:getPositionChangeKeyId rc="+rc);//~1506R~//~1A6AR~
            return rc;                                             //~1317R~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        private void moveCursor(int Pposkeyid)                     //~1317R~
        {                                                          //~1317R~
            int xx=curPosX,yy=curPosY;                             //~1317R~
            switch(Pposkeyid)                                      //~1317R~
            {                                                      //~1317R~
            case KeyEvent.VK_DOWN :                                //~1317R~
                yy++;                                              //~1317R~
                if (yy>=paternSize)                                //~1317I~
                {                                                  //~1317I~
                	yy=0;                                          //~1317I~
                    xx++;                                          //~1317I~
                    if (xx>=paternSize)                            //~1317I~
                    	xx=0;                                      //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            case KeyEvent.VK_UP :                                  //~1317R~
                yy--;                                              //~1317R~
                if (yy<0)                                          //~1317I~
                {                                                  //~1317I~
                	yy=paternSize-1;                               //~1317I~
                    xx--;                                          //~1317I~
                    if (xx<0)                                      //~1317I~
                    	xx=paternSize-1;                           //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            case KeyEvent.VK_LEFT :                                //~1317R~
                xx--;                                              //~1317R~
                if (xx<0)                                          //~1317I~
                {                                                  //~1317I~
                	xx=paternSize-1;                               //~1317I~
                	yy--;                                          //~1317I~
                    if (yy<0)                                      //~1317I~
                    	yy=paternSize-1;                           //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            case KeyEvent.VK_RIGHT :                               //~1317R~
                xx++;                                              //~1317R~
                if (xx>=paternSize)                                //~1317I~
                {                                                  //~1317I~
                	xx=0;                                          //~1317I~
                    yy++;                                          //~1317I~
                    if (yy>=paternSize)                            //~1317I~
                    	yy=0;                                      //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            }                                                      //~1317R~
            curPosX=xx;                                            //~1317R~
            curPosY=yy;                                            //~1317R~
			mouseMoved(xx,yy);	//draw board:LabelM                //~1421I~
        }                                                          //~1317R~
    //*********                                                    //~1318I~
        private boolean moveCursor(int Px,int Py)                  //~1318I~
        {                                                          //~1318I~
            int xx=curPosX,yy=curPosY;                             //~1318I~
        //***************                                          //~1318I~
        	if (Dump.C) Dump.println("moveCursor old="+xx+","+yy+",add="+Px+","+Py);//~1506R~//~1A6AR~
            xx+=Px;                                                //~1318I~
            yy+=Py;                                                //~1318I~
            if (xx<0)                                              //~1318I~
            	xx=0;                                              //~1318I~
            if (yy<0)                                              //~1318I~
            	yy=0;                                              //~1318I~
            if (xx>=paternSize)                                    //~1318I~
            	xx=paternSize-1;                                   //~1318I~
            if (yy>=paternSize)                                    //~1318I~
            	yy=paternSize-1;                                   //~1318I~
        	if (Dump.C) Dump.println("moveCursor new="+xx+","+yy); //~1506R~//~1A6AR~
            if (xx==curPosX && yy==curPosY)                        //~1318I~
                return false;                                      //~1318I~
            curPosX=xx;                                            //~1318I~
            curPosY=yy;                                            //~1318I~
			mouseMoved(xx,yy);	//draw board:LabelM                //~1421I~
            return true;                                           //~1318I~
        }                                                          //~1318I~
        //************                                             //~1317I~
        private void invalidateBoard()                                  //~1317I~
        {                                                          //~1317I~
            if (Dump.C) Dump.println("Canvas:invalidateBoard");    //~1506R~//~1A6AR~
            if (Dump.Y) Dump.println("Canvas:invalidateBoard");    //~1Ag9I~
            if (AG.isMainThread())                                 //~1317I~
                this.invalidate();                                 //~1317I~
            else                                                   //~1317I~
                this.postInvalidate();                             //~1317I~
        }                                                          //~1317I~
    //*********                                                    //~1317R~
        private void drawCursor(android.graphics.Canvas Pcanvas)   //~1317R~
        {                                                          //~1317R~
            if (Dump.C) Dump.println("Canvas:drawCursor curPosX="+curPosX+",curPosY="+curPosY);//~1506R~//~1A6AR~
            drawCursor(Pcanvas,true,curPosX,curPosY);              //~1317R~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        private boolean drawCursor(android.graphics.Canvas Pcanvas,boolean Pshow,int Pposx,int Pposy)//~1317R~
        {                                                          //~1317R~
            if (Pposx<0||Pposx>=paternSize)                        //~1317R~
                return false;                                      //~1317R~
            if (Pposy<0||Pposy>=paternSize)                        //~1317R~
                return false;                                      //~1317R~
            if (Dump.C) Dump.println("Canvas:drawCursor curPosX="+curPosX+",curPosY="+curPosY);//~1506R~//~1A6AR~
            int xx=boardOrigin+Pposx*boxSize;                      //~1317R~
            int yy=boardOrigin+Pposy*boxSize;                      //~1317R~
          if (!(((GoFrame)(board.GF)).showTarget()))               //~1B33M~
          {                                                        //~1B33I~
            Rect rect=new Rect(xx-cursorBitmapSize2,yy-cursorBitmapSize2,xx+cursorBitmapSize2+1,yy+cursorBitmapSize2+1);//~1407R~
            Pcanvas.drawRect(rect,cursorPaint);                    //~1407R~
            if (Dump.C) Dump.println("Canvas:drawCursor rect="+rect.toString());//~1506R~//~1A6AR~
          }                                                        //~1B33I~
          else                                                     //~1B33I~
          	board.drawTarget(Pcanvas,cursorPaintStroke,xx,yy);     //~1B33R~
            return true;                                           //~1407R~
        }                                                          //~1317R~
		private void setStone()                                    //~1317I~
		{                                                          //~1317I~
            int xx=boardOrigin+curPosX*boxSize;                      //~1317I~
            int yy=boardOrigin+curPosY*boxSize;                      //~1317I~
            if (Dump.C) Dump.println("Canvas:setstone xx="+xx+",yy="+yy);//~1506R~//~1A6AR~
        	MouseEvent e=new MouseEvent(xx,yy);                    //~1317I~
	        enqRequest(new BoardRequest(BOARD_MOUSE_UPDOWN,e,null));//~1425I~
        }                                                          //~1317I~
//********************************************************************//~1404R~
//*no setStone by mouse but by key-press because screen is too small//~1404I~
//*******************************************************************//~1404I~
		int touchDownX,touchDownY,touchMoveX,touchMoveY;           //~1430I~
		boolean touchSlide;                                        //~1430I~
        @Override                                                  //~1318I~
        public boolean onTouchEvent(MotionEvent event)             //~1318I~
        {                                                          //~1318I~
        	int xx,yy,action,rc;                                   //~1524R~
        //********************                                     //~1318I~
        	try                                                    //~1430I~
            {                                                      //~1430I~
                action=event.getAction();                          //~1430R~
                xx=(int)event.getX();                              //~1430R~
                yy=(int)event.getY();                              //~1430R~
                if (Dump.C) Dump.println("Canvas:onTouch action="+action+",x="+xx+",y="+yy);//~1506R~//~1A6AR~
                if (action!=MotionEvent.ACTION_OUTSIDE)            //~1430R~
                {                                                  //~1430R~
                    if (action==MotionEvent.ACTION_DOWN)           //~1430R~
                    {                                              //~1430R~
                        touchDownX=xx;                             //~1430R~
                        touchDownY=yy;                             //~1430R~
                        touchSlide=false;                          //~1430R~
                        requestFocus();    //done by ImageView;required for following kbd event//~1501R~
                        return true;                               //~1430R~
                    }                                              //~1430R~
                    if (action==MotionEvent.ACTION_MOVE)           //~1430R~
                    {                                              //~1430R~
                        Point movement=new Point();                //~1430R~
                        if (!getSlideMovement(xx,yy,movement))     //~1430R~
                            return true;                           //~1430R~
                        touchSlide=true;                           //~1430R~
                        touchDownX=xx;  //new calc point           //~1430R~
                        touchDownY=yy;                             //~1430R~
                        moveCursor(movement.x,movement.y);         //~1430R~
                    }                                              //~1430R~
                    if (action==MotionEvent.ACTION_UP)             //~1430R~
                    {                                              //~1430R~
                        if (touchSlide)                            //~1430R~
                        {                                          //~1430R~
                            return true;                           //~1430R~
                        }                                          //~1430R~
                        rc=coord2Pos(xx,yy);                       //~1430R~
                        switch(rc)                                 //~1430R~
                        {                                          //~1430R~
                        case 1:   //click on the same position     //~1430R~
                            setStone();                            //~1430R~
                            break;                                 //~1430R~
                        }                                          //~1430R~
                        return true;                                               //~0914I~//~1430R~
                    }                                              //~1430R~
                }                                                  //~1430R~
            }                                                      //~1430I~
            catch(Exception e)                                     //~1430I~
            {                                                      //~1430I~
                Dump.println(e,"Canvas.OnTouchEvent exception");   //~1430I~
            }                                                      //~1430I~
            return false;                                          //~1318I~
        }                                                          //~1318I~
//*******************************************                      //~1430I~
        public boolean getSlideMovement(int Px,int Py,Point Pmovement)//~1430I~
        {                                                          //~1430I~
                                                                   //~1430I~
            int xx,yy,axx,ayy,limit;                               //~1430I~
                                                                   //~1430I~
       //******************                                        //~1430I~
            if (Dump.C) Dump.println("getSlideMovement x="+Px+"<--"+touchDownX+",y="+Py+"<--"+touchDownY+",boxsize="+boxSize);//~1506R~//~1A6AR~
            xx=Px-touchDownX;                                      //~1430I~
            yy=Py-touchDownY;                                      //~1430I~
            if (xx<0)   axx=-xx;    else    axx=xx;   //abs        //~1430I~
            if (yy<0)   ayy=-yy;    else    ayy=yy;                //~1430I~
            limit=boxSize;                                         //~1430I~
            if (axx>limit)	axx=1;	else 	axx=0;                 //~1430R~
            if (ayy>limit)	ayy=1;	else 	ayy=0;                 //~1430R~
            if (axx==0 && ayy==0)                                  //~1430R~
                return false;                                      //~1430I~
            if (xx<0)                                              //~1430R~
				axx=-axx;                                          //~1430I~
            if (yy<0)                                              //~1430I~
				ayy=-ayy;                                          //~1430I~
            Pmovement.x=axx;                                       //~1430R~
            Pmovement.y=ayy;                                       //~1430R~
            if (Dump.C) Dump.println("getSlideMovement moved x="+axx+",y="+ayy);//~1506R~//~1A6AR~
            return true;                                           //~1430I~
        }                                                          //~1430I~
//*******************************************************************//~1419I~
//*rc:0 :pos in the board                                          //~1419I~
//*rc:1 :pos not changed on board                                  //~1419I~
//*rc:2 :pos out of board(ACTION_UP)                               //~1419I~
////*rc:3 :pos changed by push out of board(ACTION_UP)             //~1430R~
//*rc:-1:ignore                                                    //~1419I~
//*******************************************************************//~1419I~
        private int coord2Pos(int Px,int Py)                       //~1430R~
        {                                                          //~1318I~
            int posX,posY;                                         //~1419R~
            int redundancy=boxSize/4;                              //~1419I~
            int roundup=boxSize/2;                                 //~1419I~
		//********                                                 //~1419I~
            posX=Px-boardOrigin;                                   //~1419R~
            posY=Py-boardOrigin;                                                       //~1419I~
            if (posX<-redundancy)                                  //~1419R~
            	posX=-1;                                           //~1419I~
            else                                                   //~1419I~
            if (posX>boardEdge+redundancy)                         //~1419I~
            	posX=paternSize;                                   //~1419I~
            else                                                   //~1419I~
            	posX=(posX+roundup)/boxSize;                       //~1419R~
            if (posY<-redundancy)                                  //~1419I~
            	posY=-1;                                           //~1419I~
            else                                                   //~1419I~
            if (posY>boardEdge+redundancy)                         //~1419I~
            	posY=paternSize;                                   //~1419I~
            else                                                   //~1419I~
            	posY=(posY+roundup)/boxSize;                       //~1419I~
                                                                   //~1419I~
            if (Dump.C) Dump.println("Canvas.coord2Pos x="+Px+",y="+Py+",pos=("+posX+","+posY+")");//~1506R~//~1A6AR~
            if (posX>=0 && posX<paternSize                         //~1318I~
            &&  posY>=0 && posY<paternSize)                        //~1318I~
            {                                                      //~1318I~
//on board                                                         //~1419I~
				if (posX==curPosX && posY==curPosY)                //~1419M~
                	return 1;                                      //~1419M~
                curPosX=posX;                                      //~1318I~
                curPosY=posY;                                      //~1318I~
				mouseMoved(posX,posY);	//draw board:LabelM        //~1422I~
                if (swLocalBoard)                                  //~1B0eI~
                {                                                  //~1B0eI~
                	if ((Sonetouchmode & ONETOUCH_LOCALBOARD)!=0)  //~1B0eR~
	                	return 1;                                  //~1B0eI~
                }                                                  //~1B0eI~
                else                                               //~1B0eI~
                if ((Sonetouchmode & ONETOUCH_MATCHBOARD)!=0)      //~1B0eI~
                {                                                  //~1B0eI~
	                return 1;                                      //~1B0eR~
                }                                                  //~1B0eI~
                return 0;                                          //~1419R~
            }                                                      //~1318I~
            return 2;                                              //~1419R~
        }                                                          //~1318I~
//*******************************************************************//~1419I~
//        private Point pos2Coord(int Pposx,int Pposy)             //~1524R~
//        {                                                        //~1524R~
//            int xx=boardOrigin+Pposx*boxSize+boxSize/2;          //~1524R~
//            int yy=boardOrigin+Pposy*boxSize+boxSize/2;          //~1524R~
//            if (Pposx>=0 && Pposx<paternSize                     //~1524R~
//            &&  Pposy>=0 && Pposy<paternSize)                    //~1524R~
//            {                                                    //~1524R~
//                xx=boardOrigin+Pposx*boxSize+boxSize/2;          //~1524R~
//                yy=boardOrigin+Pposy*boxSize+boxSize/2;          //~1524R~
//                Point p=new Point(xx,yy);                        //~1524R~
//                return p;                                        //~1524R~
//            }                                                    //~1524R~
//            return new Point(0,0);                               //~1524R~
//        }                                                        //~1524R~
//**********************                                           //~1318I~
//*trackball************                                           //~1318R~
//**********************                                           //~1318I~
        @Override 
        public boolean onTrackballEvent(MotionEvent event)            //~0914I~//~1318R~
        {                                                          //~1318I~
                                        //~1318I~
            int xx=0,yy=0,action;                                      //~1426R~
            Point movement=new Point();                              //~1426I~
       //******************                                       //~1426I~
       		try                                                    //~1430I~
            {                                                      //~1430I~
                action=event.getAction();                              //~0914I~//~1430R~
                if (Dump.C) Dump.println("trackball action="+action);//~1506R~//~1A6AR~
                if (action==MotionEvent.ACTION_MOVE)               //~1430R~
                {                                                  //~1430R~
                    if (!getMovement(event,movement))              //~1430R~
                        return true;                               //~1430R~
                    xx=movement.x;                                 //~1430R~
                    yy=movement.y;                                 //~1430R~
                }                                                  //~1430R~
                switch(action)                                     //~1430R~
                {                                                  //~1430R~
                case MotionEvent.ACTION_MOVE:                      //~1430R~
                    requestFocus();    //by ImageView;required for following kbd event//~1501I~
                    moveCursor(xx,yy);                             //~1430R~
                    break;                                         //~1430R~
                case MotionEvent.ACTION_DOWN:                      //~1430R~
                    break;                                         //~1430R~
                case MotionEvent.ACTION_UP:                        //~1430R~
                    coord2Pos(xx,yy);//use latest position         //~1430R~
                    setStone();                                    //~1430R~
                }                                                  //~1430R~
            }                                                      //~1430I~
            catch(Exception e)                                     //~1430I~
            {                                                      //~1430I~
                Dump.println(e,"Canvas.OnTrackballEvent exception");//~1430I~
            }                                                      //~1430I~
            return true;                                               //~0914I~//~1318R~
        }                                                              //~0914I~//~1318R~
//********************                                             //~1426I~
        public boolean getMovement(MotionEvent event,Point Pmovement)//~1426I~
        {                                                          //~1426I~
                                                                   //~1426I~
            int xx,yy;                                             //~1426I~
            float fx,fy,afx,afy;                                   //~1426I~
            long eventtime,elapsed; //cut compiled movement when time expired//~1430R~
       //******************                                        //~1426I~
            eventtime=event.getEventTime();                        //~1426I~
            elapsed=eventtime-trackball_oldmovetime;
            if (Dump.C) Dump.println("trackball move eventtime="+Long.toHexString(eventtime)+",elapsed="+Long.toString(elapsed)+",x="+event.getX()+",y="+event.getY());//~1506R~//~1A6AR~
            if (elapsed>TRACKBALL_MOVE_CHKTIME)	//cut remaining of previous span//~1426I~
            {                                                      //~1426I~
	            trackball_accumX=0;                                //~1426I~
    	        trackball_accumY=0;                                //~1426I~
		        trackball_oldmovetime=eventtime;                   //~1426I~
            }                                                      //~1426I~
            fx=event.getX();    //getX nomarized by DpadKey 1 press//~1426I~
            fy=event.getY();                                       //~1426I~
            trackball_accumX+=fx;                                  //~1426I~
            trackball_accumY+=fy;                                  //~1426I~
            fx=trackball_accumX;                                   //~1426I~
            fy=trackball_accumY;                                   //~1426I~
            if (Dump.C) Dump.println("trackball accum x="+fx+",y="+fy);//~1506R~//~1A6AR~
            if (fx<0)   afx=-fx;    else    afx=fx;   //abs        //~1426I~
            if (fy<0)   afy=-fy;    else    afy=fy;                //~1426I~
            if (afx>=1.0f)	xx=1;	else 	xx=0;                  //~1426R~
            if (afy>=1.0f)	yy=1;	else 	yy=0;                  //~1426R~
            if (xx==0 && yy==0)                                     //~1426I~
                return false;                                      //~1426I~
            if (fx<0)                                              //~1426I~
                xx=-xx;                                            //~1426I~
            if (fy<0)                                              //~1426I~
                yy=-yy;                                            //~1426I~
            Pmovement.x=xx;                                        //~1426I~
            Pmovement.y=yy;                                        //~1426I~
            trackball_accumX=0;                                    //~1426I~
            trackball_accumY=0;                                    //~1426I~
            trackball_oldmovetime=eventtime;                        //~1426I~
            return true;                                           //~1426I~
        }                                                          //~1426I~
//** mouse                                                         //~1317I~
        @Override                                                  //~1317I~
		public void mouseClicked(MouseEvent e)                     //~1317I~
		{                                                          //~1317I~
//      	ajagoCanvas.boardMouseListener.mouseClicked(e);        //~1420R~
        }                                                          //~1317I~
        @Override                                                  //~1317I~
        public void mousePressed(MouseEvent e)                     //~1420R~
        {                                                          //~1317I~
	        enqRequest(new BoardRequest(BOARD_MOUSE_PRESS,e,null));//~1524R~
        }                                                          //~1317I~
        @Override                                                  //~1317I~
		public void mouseReleased(MouseEvent e)                    //~1420R~
		{                                                          //~1317I~
	        enqRequest(new BoardRequest(BOARD_MOUSE_RELEASE,e,null));//~1524R~
		}                                                          //~1317I~
        @Override                                                  //~1317I~
		public void mouseEntered(MouseEvent e)                     //~1317I~
		{                                                          //~1317I~
        	ajagoCanvas.boardMouseListener.mouseEntered(e); //not synchronized function//~1420R~
		}                                                          //~1317I~
        @Override                                                  //~1317I~
		public void mouseExited(MouseEvent e)                      //~1317I~
		{                                                          //~1317I~
        	ajagoCanvas.boardMouseListener.mouseExited(e);  //not synchronized function//~1420R~
		}                                                          //~1317I~
		@Override
		public void keyPressed(KeyEvent ev) {
			//will not called from AjagoKey
		}
		@Override
		public void keyReleased(KeyEvent ev) {
			//not called from AjagoKey
		}
		//***********************                                  //~1413I~
		public void mouseMoved(int Px,int Py)                      //~1413R~
        {                                                          //~1413I~
            int xx=boardOrigin+Px*boxSize;                         //~1421I~
            int yy=boardOrigin+Py*boxSize;                         //~1421I~
            MouseEvent ev=new MouseEvent(xx,yy);                   //~1413R~
	        enqRequest(new BoardRequest(BOARD_MOUSE_MOVE,ev,null));//~1524R~
        	invalidateBoard();	//Board.mouseMoved dose not request paint()//~1422I~
		}                                                          //~1413I~
    }//ImageClase                                                  //~1317R~
//**********************************************************       //~1420R~
//*Board synchronization                                           //~1420I~
//*  do Board operation on subthread to avoid deadlock             //~1420R~
//*  Android limitation of GUI-API should execute on Mainthread    //~1420I~
//*  Board operation trigger comes from Igs receive thread/partner Thread/Main Thread(loval Viewer)//~1420I~
//*    deadlock scenario                                           //~1420I~
//*            sub thread                             main thread  //~1420I~
//*            -----------------------------------------------------------------//~1420I~
//*            synchronized                                        //~1420I~
//*              heavy function shch as updateall                  //~1420I~
//*                                                   call synchronized Board function//~1420I~
//*                                                     by Mouse/Keyboard operation//~1420I~
//*                                                     -->wait other synchronied function complete//~1420I~
//*              reqest GUI-API execution on MainThread            //~1420I~
//*                  -->wait MainThread free                       //~1420I~
//**********************************************************       //~1420R~
    //**********************                                       //~1420I~
	public class BoardRequest                                      //~1420I~
    {                                                              //~1420I~

		int opid;                                                  //~1420I~
        Object parm1,parm2;                                        //~1420I~
        public BoardRequest(int Popid,Object Pparm1,Object Pparm2)//~1420I~
        {                                                          //~1420I~
        	opid=Popid;                                       //~1420I~
            parm1=Pparm1;                                          //~1420I~
            parm2=Pparm2;                                          //~1420I~
        }                                                          //~1420I~
    }                                                              //~1420I~
    private LinkedList<BoardRequest> requestList=new LinkedList<BoardRequest>(); //~1420M~
    public static final int BOARD_MOUSE_PRESS  =1;                 //~1420I~
    public static final int BOARD_MOUSE_RELEASE=2;                 //~1420I~
    public static final int BOARD_MOUSE_MOVE   =3;                 //~1420I~
    public static final int BOARD_KEY_PRESS    =4;                 //~1420I~
    public static final int BOARD_PAINT        =5;                 //~1420I~
    public static final int BOARD_DOACTION     =6;                 //~1421I~
    public static final int BOARD_STOP         =7;                 //~1422I~
    public static final int BOARD_MOUSE_UPDOWN =8;                 //~1425I~
    public static final int BOARD_DRAW_BIGLABEL=9;                 //~1513I~
    public static final int BOARD_CALLBACK     =10;                //~v1B7I~
    public int paintenqctr;                                        //~1513R~
    public int paintbytimerctr;                                    //~1513I~
    private boolean swDead=false;                                  //~v1B7I~
    private StackOverflowError ovferr;                             //~v1B7I~
    //*******************************************************      //~1424R~
    public void enqRequest(BoardRequest Preq)                      //~1420I~
    {                                                              //~1420I~
    	int ctr,ctr2,paintctr;                                     //~1427R~
    //**********************************                           //~1427I~
    	if (boardSync==null)                                       //~1503I~
        {                                                          //~v1B7I~
	        boolean swRestart=false;                               //~v1B7I~
        	if (swDead)	//stackOverflow                            //~v1B7M~
            {                                                      //~v1B7I~
    			if (Preq.opid==BOARD_MOUSE_UPDOWN                  //~v1B7R~
    			||  Preq.opid==BOARD_MOUSE_MOVE                    //~v1B7I~
    			||  Preq.opid==BOARD_DOACTION                      //~v1B7I~
                )                                                  //~v1B7I~
                {                                                  //~v1B7I~
	    			if (Preq.opid==BOARD_DOACTION)                 //~v1B7I~
                    {                                              //~v1B7I~
	                    ActionEvent ev=(ActionEvent)(Preq.parm1);  //~v1B7I~
                    	ActionTranslator at=ev.scheduledAT;        //~v1B7I~
                    	String actionname=at.Name;                 //~v1B7I~
                        if (actionname.equals(Global.resourceString("Close")))//~v1B7I~
                        	swRestart=true;	//accept only close    //~v1B7I~
                    }                                              //~v1B7I~
		            if (swRestart)                                 //~v1B7I~
                    {                                              //~v1B7I~
	                    boardSync=new BoardSync(this);             //~v1B7M~
                        swDead=false;                              //~v1B7M~
                        ovferr=null;                               //~v1B7M~
                    }                                              //~v1B7I~
                    else                                           //~v1B7I~
		        		AjagoView.showToastLong(R.string.Err_CanvasDead,ovferr.toString());//~v1B7I~
                }                                                  //~v1B7I~
            }//dead                                                //~v1B7R~
            if (!swRestart)                                        //~v1B7I~
	            return;                                            //~v1B7I~
        }//null                                                    //~v1B7R~
    	paintctr=paintenqctr;                                       //~1427I~
        if (Preq.opid==BOARD_PAINT)                                //~1427I~
        	paintenqctr++;	                                       //~1427I~
        if (Preq.opid==BOARD_DRAW_BIGLABEL)                        //~1513I~
        	paintbytimerctr++;                                     //~1513I~
    	synchronized(requestList)	                               //~1420I~
        {                                                          //~1420I~
        	requestList.add(Preq);	//enq to last                  //~1420I~
            ctr=requestList.size();                                //~1421I~
            requestList.notifyAll();    //pos wait                 //~1421I~
        }                                                          //~1420I~
    	if (boardSync==null)     //after stopThread                //~v107I~
        	return;                                                //~v107I~
        if (boardSync.swSleep)                                   //~1427I~
            ctr2=ctr+1;                                            //~1427I~
        else                                                       //~1427I~
            ctr2=ctr;                                              //~1427I~
        if (paintctr>1 && (ctr2-paintbytimerctr)>ENQ_OVERFLOW_LIMIT)//~1513R~
        {                                                          //~1421I~
        	String ovfmsg=ctr+".";                                 //~1421I~
        	AjagoView.showToast(R.string.BoardQWaiting,ovfmsg);    //~1421I~
        }                                                          //~1421I~
        if (Dump.C) Dump.println("Canvas:BoardSync enqRequest id="+Preq.opid+",ctr="+ctr+",board="+this.toString());//~1514R~//~1A6AR~
    }                                                              //~1420I~
    //**********************                                       //~1422I~
    public void  stopThread()                                      //~1422I~
    {                                                              //~1422I~
    	swCloseRequested=true;                                     //~1424I~
    	if (boardSync!=null)                                       //~1422I~
        {                                                          //~1422I~
        	boardSync.stopit();                                    //~1422I~
        	enqRequest(new BoardRequest(Canvas.BOARD_STOP,this,null));  //post wait//~1503R~
    		boardSync=null;	//of Frame                             //~1503M~
        }                                                          //~1422I~
    }                                                              //~1422I~
    //***********************************************              //~1Ag9I~
    //*from BoardSync at stop thread                               //~1Ag9I~
    //***********************************************              //~1Ag9I~
    private void onDestroy()                                            //~1Ag9I~
    {                                                              //~1Ag9I~
    	if (Dump.Y) Dump.println("Canvas:onDestroy");              //~1Ag9I~
    	board.onDestroy();                                         //~1Ag9I~
        boardImageCopy.recycle(true/*crear ref to android.Canvas*/);//~1Ag9I~
    }                                                              //~1Ag9I~
    //******************************************************       //~1424R~
    //*subthread Class                                             //~1424I~
    //******************************************************       //~1424I~
    public class BoardSync extends StopThread                      //~1420I~
    {                                                              //~1420I~
    	private Canvas ajagoCanvas;                                //~1421R~
        private Board board;                                       //~1421R~
        private Graphics graphics;                                 //~1424I~
        public boolean swSleep;                                    //~1422R~
	    private LinkedList<BoardRequest> requestList;              //~1424I~
    //**************************                                   //~1420I~
        BoardSync(Canvas Pcanvas)                                  //~1420R~
        {                                                          //~1420I~
			ajagoCanvas=Pcanvas;                                   //~1420R~
	        board=(Board)Pcanvas;                                  //~1420I~
	        graphics=Pcanvas.graphics;	//java confuse outer class valiable when multiple instance//~1424I~
	        requestList=Pcanvas.requestList;                       //~1424I~
            start();                                               //~1420I~
        }                                                          //~1420I~
        public void run ()                                         //~1420I~
        {                                                          //~1420I~
	        if (Dump.C) Dump.println("BoardSync run started");     //~1506R~//~1A6AR~
        	while(true)                                            //~1420I~
            {                                                      //~1420I~
                try                                                //~1420R~
                {                                                  //~1420R~
                    int opid=doRequest();                          //~1503R~
                    if (opid==BOARD_STOP)                          //~1503R~
                    {                                              //~1422I~
	                    if (Dump.C) Dump.println("BoardSync return by stopped after recycleBitmap");//~1506R~//~1A6AR~
                        return;                                    //~1422M~
                    }                                              //~1422I~
                }                                                  //~1420R~
                catch (Exception e)                                //~1420R~
                {                                                  //~1420R~
                    Dump.println(e,"BoardSync Exception");         //~1420R~
                }                                                  //~1420R~
                catch (StackOverflowError e)                       //~v1B7I~
                {                                                  //~v1B7I~
                	boardSync=null;                                //~v1B7M~
                	ovferr=e;                                      //~v1B7I~
                	swDead=true;                                   //~v1B7I~
                }                                                  //~v1B7I~
            }                                                      //~1420I~
        }                                                          //~1420I~
        private  BoardRequest deqRequest()                         //~1420I~
        {                                                          //~1420I~
            BoardRequest req;                                      //~1420I~
            synchronized(requestList)                              //~1420I~
            {                                                      //~1420I~
				while(requestList.size()==0)                       //~1421I~
                {                                                  //~1421I~
	                swSleep=true;                                  //~1422I~
                    try                                            //~1422R~
					{                                              //~1422I~
						requestList.wait();
					}                                              //~1422R~
					catch (InterruptedException e)                 //~1422I~
					{
						Dump.println(e,"requestList wait interrupted");
						return null;
					}//wait notufy              //~1421I~
	        		if (Dump.C) Dump.println("Canvas:BoardSync subthread posted residual ctr="+requestList.size()+",q="+requestList.toString());//~1506R~//~1A6AR~
                }                                                  //~1421I~
                req=requestList.getFirst(); //get top              //~1422R~
                swSleep=false;                                     //~1422I~
	            requestList.remove(req);//deq                      //~1422R~
            }                                                      //~1420I~
	        if (Dump.C) Dump.println("Canvas:BoardSync deqRequest id="+req.opid+",residual ctr="+requestList.size()+",board="+this.toString());//~1514R~//~1A6AR~
            return req;                                            //~1420I~
        }                                                          //~1420I~
        protected int doRequest()                                  //~1503R~
        {                                                          //~1420I~
            BoardRequest req;
            MouseEvent mev;
            KeyEvent kev;//~1420I~
            int opid=0;                                            //~1503I~
            while(true)                                            //~1420I~
            {                                                      //~1420I~
                req=deqRequest();                                  //~1420I~
                if (req==null)                                     //~1420I~
                    break;                                         //~1420I~
	            if (Dump.C) Dump.println("Canvas:BoardSync start id="+req.opid);//~1506R~//~1A6AR~
                opid=req.opid;                                     //~1503I~
                if (stopped())                                     //~1503I~
                {                                                  //~1514I~
                	if (Dump.C) Dump.println("BoardSync thread stopped");//~1514I~//~1A6AR~
                	if (opid!=BOARD_STOP)                          //~1503I~
                    	break;                                     //~1503I~
                }                                                  //~1514I~
                switch(opid)                                       //~1503R~
                {                                                  //~1420I~
                case BOARD_MOUSE_PRESS:                            //~1420I~
                    mev=(MouseEvent)(req.parm1);         //~1420I~
	        		ajagoCanvas.boardMouseListener.mousePressed(mev);//~1420I~
                    break;                                         //~1420I~
                case BOARD_MOUSE_RELEASE:                          //~1420I~
                    mev=(MouseEvent)(req.parm1);         //~1420I~
	        		ajagoCanvas.boardMouseListener.mouseReleased(mev);//~1420I~
                    break;                                         //~1420I~
                case BOARD_MOUSE_UPDOWN:                           //~1425I~
                    mev=(MouseEvent)(req.parm1);                   //~1425I~
	        		ajagoCanvas.boardMouseListener.mousePressed(mev);//~1425I~
	        		ajagoCanvas.boardMouseListener.mouseReleased(mev);//~1425I~
                    break;                                         //~1425I~
                case BOARD_MOUSE_MOVE:                             //~1420I~
                    mev=(MouseEvent)(req.parm1);         //~1420I~
	        		ajagoCanvas.boardMouseMotionListener.mouseMoved(mev);//~1420I~
                    break;                                         //~1420I~
                case BOARD_KEY_PRESS:                              //~1420I~
                    kev=(KeyEvent)(req.parm1);             //~1420I~
                    if (Dump.C) Dump.println("Canvas board keyPress Listener_a");//~1506R~//~1A6AR~
            		ajagoCanvas.boardKeyListener_a.keyPressed(kev);//~1503R~
                    if (ajagoCanvas.boardKeyListener_b!=null)      //~1503I~
                    {                                              //~1503I~
                    	if (Dump.C) Dump.println("Canvas board keyPress Listener_b");//~1506R~//~1A6AR~
	            		ajagoCanvas.boardKeyListener_b.keyPressed(kev);//~1503I~
                    }                                              //~1503I~
                    break;                                         //~1420I~
                case BOARD_PAINT:                                  //~1420I~
		        	paintenqctr--;                                 //~1427I~
                    ajagoCanvas.swPainting=true; //drawBitmap to android Canvas//~1420I~
		            if (Dump.C) Dump.println("Canvas:BoardSync call paint board="+board.toString()+",graphics="+graphics.toString());//~1506R~//~1A6AR~
                    board.paint(graphics);	//paint() callback drawBoard()//~1420I~
                    ajagoCanvas.swPainting=false;                  //~1420I~
                    break;                                         //~1420I~
                case BOARD_DOACTION:                               //~1421I~
                    ActionEvent ev=(ActionEvent)(req.parm1);       //~1421I~
                    ev.scheduledAT.actionPerformed(ev);             //~1421I~
                    break;                                         //~1421I~
                case BOARD_STOP:                                   //~1503I~
					Frame f=ajagoCanvas.canvasFrame;               //~1503I~
                    if (f.isObserveGame)                           //~1503I~
                    {                                              //~1503I~
                    	if (Dump.C) Dump.println("BoardSync stop wait 10sec for Observe game");//~1506R~//~1A6AR~
                        AjagoUtils.sleep(10000);	//see GoObserver,unchain distributor after 10sec wait//~1503R~
                    	if (Dump.C) Dump.println("BoardSync stop waken");//~1506R~//~1A6AR~
                    }                                              //~1503I~
                    else                                           //~v107I~
                        AjagoUtils.sleep(200);	//for safety,wait drawCursor complete//~v107I~
					f.recycleBitmap();                             //~1503I~
                    onDestroy();	//clear bitmap reference       //~1Ag9I~
                    break;                                         //~1503I~
                                                                 //~1420I~
                case BOARD_DRAW_BIGLABEL:                          //~1513I~
                    if (Dump.C) Dump.println("BoardSync BigLabel paint");//~1513I~//~1A6AR~
                    BigLabel label=(BigLabel)(req.parm1);          //~1513I~
                    Graphics g=(Graphics)(req.parm2);              //~1513I~
                    label.paint(g);	                               //~1513I~
                    break;                                         //~1513I~
                case BOARD_CALLBACK:                               //~v1B7I~
                    if (Dump.C) Dump.println("BoardSync Callback");//~v1B7I~//~1A6AR~
                    GoFrame gf=(GoFrame)(req.parm1);               //~v1B7I~
                    Object parm=req.parm2;	//CanvascallbackParm   //~v1B7R~
                    gf.canvasCallback(parm);                       //~v1B7R~
                    break;                                         //~v1B7I~
                }                                                  //~1513I~
	            if (Dump.C) Dump.println("Canvas:BoardSync end="+req.opid);//~1506R~//~1A6AR~
                if (opid==BOARD_STOP)                              //~v1EmI~
                    break;  //thread return                        //~v1EmI~
            }                                                      //~1420I~
            return opid;                                           //~1503R~
        }                                                          //~1420I~
    }                                                              //~1420I~
                                                                   //~1420I~
}//class Canvas                                                    //~1213R~
