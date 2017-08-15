//*CID://+1B1bR~: update#= 194;                                    //+1B1bR~
//**********************************************************************//~1107I~
//1B1b 130507 GetFontSize example width was not of sample window   //+1B1bI~
//**********************************************************************//+1B1bI~
//*Canvas for Example Dialog(Color,Font)                                  //~1225R~//~1331R~
//*  APP:ExampleCanvas:repaint()-->this-->ImageCanvas:invalidate() //~1331R~
//*    @OnDraw()-->APP:paint():g.drawText()                        //~1331I~
//**********************************************************************//~1107I~
package com.Ajagoc.jagoclient.getfontsize;                                         //~1107R~  //~1108R~//~1109R~//~1117R~//~1414R~

import com.Ajagoc.AG;
import com.Ajagoc.AjagoKey;
import com.Ajagoc.AjagoKeyI;
import com.Ajagoc.awt.Component;
import com.Ajagoc.awt.Dimension;
import com.Ajagoc.awt.Font;
import com.Ajagoc.awt.KeyEvent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import jagoclient.Dump;


public class Canvas extends Component                              //~1331R~
	implements AjagoKeyI                                           //~1401R~
{                                                                  //~0914I~
	private static Graphics graphics;		//Ajagoc.Graphic       //~1425R~
	private Font font;                                             //~1425R~
	private DialogCanvas imageCanvas;                          //~1226R~//~1425R~
    private int canvash,canvasw;
    private EditText inputField;//~1331I~
    private View layoutView;                                       //+1B1bR~
//******************************                                   //~1217I~
	public Canvas()                                                //~1117R~
    {                                                              //~1217R~
    //************************                                     //~1221I~
            //~1310I~//~1311R~
        FrameLayout layout=(FrameLayout)AG.findViewById(AG.viewId_ExampleCanvas);//~1217I~//~1221M~//~1331R~
        layoutView=layout;                                         //+1B1bR~
        ViewGroup.LayoutParams lp0=layout.getLayoutParams();                   //~1331I~
        canvash=lp0.height;            //by xml                    //~1331R~
        canvasw=(AG.scrWidth*90)/100;  //screen size*0.9           //~1331R~
        if (Dump.Y) Dump.println("Example canvas w="+canvasw+",h="+canvash);//~1506R~
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(canvasw,canvash);//~1221I~//~1331R~
        imageCanvas=new DialogCanvas(this);	//customized View      //~1331R~
        layout.addView(imageCanvas,lp);                                //~1118M~//~1121R~//~1217I~//~1226R~//~1331R~
        initImageView();                                           //~1217I~//~1331R~
        graphics=new Graphics();                               //~1224I~//~1331R~
//*intercept IntField OnKeyListener                                //~1401I~
//*Avoid that GetFontSize DoListener is disposed by Enter Key      //~1401I~
        View v=AG.findViewById(AG.viewId_ContainerFontSizeField);
        if (v!=null)
        {
        	inputField=(EditText)(((LinearLayout)v).getChildAt(0));//~1401R~
        	AjagoKey kl=AjagoKey.addKeyListener(inputField);           //~1401R~
        	kl.setKeyListener(this);
        }//~1401R~
    }                                                              //~1118M~//~1121R~//~1217I~
		                                                           //~1217I~
//******************
	public Dimension getSize()                                     //~1117I~
    {                                                              //~1117I~
        canvasw=layoutView.getWidth();                             //+1B1bR~
        canvash=layoutView.getHeight();                            //+1B1bR~
      	return new Dimension(canvasw,canvash);                     //~1221I~//~1331R~
    }                                                              //~1117I~
//******************************************                       //~1216I~
    private void initImageView()
    {
    	if (Dump.Y) Dump.println("init Canvas ImageView id="+Integer.toString(imageCanvas.getId(),16));//~1226R~//~1506R~
		imageCanvas.setWillNotDraw(false);	//reset default "don't call onDraw"//~1226R~//~1331R~
       	imageCanvas.setFocusable(false);                                        //~0A05R~//~1120I~//~1226R~//~1331R~
       	imageCanvas.setFocusableInTouchMode(false);                 //~1226R~//~1331R~
    }
    public void repaint()  //from GetFontSize                                        //~1117I~//~1331R~
    {                                                              //~1117I~
        if (Dump.Y) Dump.println("Canvas:repaint");                //~1506R~
        if (imageCanvas!=null)                                     //~1313I~//~1331R~
        	imageCanvas.repaint();                                 //~1313I~//~1331R~
    }                                                              //~1117I~
    public void setFont(Font Pfont)                                //~1213I~
    {                                                              //~1213I~
    	font=Pfont;                                                //~1213I~
    }                                                              //~1213I~
    public Font getFont()                                //~1213I~
    {                                                              //~1213I~
    	return font;                                               //~1213I~
    }                                                              //~1213I~
    public void paint(Graphics Pgraphics)                          //~1331I~
    {                                                              //~1331I~
    	//Overrided by ExampleCanvas                               //~1331I~
    }                                                              //~1331I~
//****************************                                     //~1401I~
    @Override                                                      //~1401I~
    public boolean keyPressedRc(KeyEvent ev)                         //~1401R~
	{                                                              //~1401I~
    	return false;                                              //~1401I~
	}                                                              //~1401I~
    @Override                                                      //~1401I~
    public boolean keyReleasedRc(KeyEvent Pev)                       //~1401R~
	{                                                              //~1401I~
  		int keycode=Pev.getKeyCode();                              //~1401I~
        boolean rc=false;                                          //~1401I~
        if (keycode==KeyEvent.VK_ENTER)                                     //~1430R~
        {                                                          //~1401I~
        	repaint();	//draw Example Text                        //~1401I~
        	rc=true;                                               //~1401R~
        }
        return rc;                                                 //~1401R~
	}                                                              //~1401I~
//************:                                                    //~1401I~
    @Override                                                      //~1401I~
    public void keyTyped(KeyEvent ev){}                            //~1401I~
	@Override                                                      //~1401M~
	public void keyPressed(KeyEvent ev){}                          //~1401I~
	@Override                                                      //~1401M~
	public void keyReleased(KeyEvent ev){}                         //~1401I~
//**********************************************************       //~1217I~
//*to get android Canvas and get control of onDraw                 //~1217I~
//**********************************************************       //~1217I~
    class DialogCanvas extends ImageView                            //~1216I~//~1217R~//~1331R~
    {
        private Canvas canvas;                                     //~1331R~
        int posx,posy;                                             //~1331I~
        Paint paint; 
    //**********************************//~1331I~
       	public DialogCanvas(Canvas Pcanvas)                        //~1331R~
    	{
    		super(AG.context);
            canvas=Pcanvas;                                        //~1331I~
    	}
       	//************************************************         //~1313I~
		//*from Board:repaint()                                    //~1313I~
        //************************************************         //~1313I~
    	public void repaint()                                      //~1313I~
        {                                                          //~1313I~
    		if (AG.isMainThread())                                 //~1313I~
            {                                                      //~1313I~
            	this.invalidate();                                 //~1313I~
	            if (Dump.Y) Dump.println("ExsampleCanvds UI Invalidate");        //~1313I~//~1506R~
            }                                                      //~1313I~
        	else                                                   //~1313I~
            {                                                      //~1313I~
            	this.postInvalidate();	//call paint(),draw from ActiveImage//~1313I~
	            if (Dump.Y) Dump.println("ExampleCanvds postInvalidate");       //~1313I~//~1506R~
            }                                                      //~1313I~
        }                                                          //~1313I~
        //************************************************         //~1304I~
        @Override                                                      //~0914R~//~1216I~
        protected void onDraw(android.graphics.Canvas PandroidCanvas)                           //~0914I~//~1117R~//~1216I~//~1331R~
        {                                                          //~1216I~
	        if (Dump.Y) Dump.println("imageCanvas @onDraw system");         //~1227I~//~1313I~//~1506R~
            graphics.androidCanvas=PandroidCanvas;                 //~1331I~
            canvas.paint(graphics);  //overrided method by ExampleCanvas//~1331R~
        }                                                              //~0914I~//~1216I~
    }//Example                                                  //~1317R~//~1331R~

}//class Canvas                                                    //~1213R~
