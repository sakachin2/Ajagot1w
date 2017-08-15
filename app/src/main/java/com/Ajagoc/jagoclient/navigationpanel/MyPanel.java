//*CID://+dateR~: update#= 224;                                    //~1107R~
//**********************************************************************//~1107I~
//*for GoFrame.NavigationPanel                                     //~1416R~
//**********************************************************************//~1107I~
package com.Ajagoc.jagoclient.navigationpanel;                                  //~1107R~  //~1108R~//~1109R~//~1117R~//~1416R~

import com.Ajagoc.AG;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Component;
import com.Ajagoc.awt.Dimension;
import com.Ajagoc.awt.Graphics;
import com.Ajagoc.awt.Image;
import jagoclient.Dump;
import android.view.View;
import android.widget.ImageView;


public class MyPanel extends jagoclient.gui.MyPanel                //~1416R~
{                                                                  //~0914I~
	private ImageView view;                                        //~1502R~
    private int panelW,panelH;                                 //+1416R~//~1502R~
    private Image image;                                           //~1502R~
    private Graphics graphics;                                     //~1502R~
    private Dimension dimension;                                   //~1416I~
//******************************                                   //~1217I~
	public MyPanel()                                               //~1416R~
    {                                                              //~1217R~
    	super();                                                   //~1416I~
        view=(ImageView)(AG.findViewById(AG.viewId_NavigationPanel));//~1416I~
        view.setVisibility(View.VISIBLE);	//reset visibility="gone" on xml of ConnectedGoFrame if !TimerInTitle//~1416I~
        initPanel();                                               //~1417R~
	}                                                              //~1414I~
	public void initPanel()                                        //~1417R~
    {                                                              //~1416I~
    	if (view==null)                                            //~1416I~
        	return;	//MayPanel constructor call repaint()          //~1416I~
        panelH=view.getMeasuredHeight();                           //~1416R~
        panelW=view.getMeasuredWidth();                            //~1416R~
        if (Dump.Y) Dump.println("Navigation-Panel getSize x="+panelW+",y="+panelH);//~1506R~
        image=initImage(panelW,panelH);                            //~1416R~
        dimension=new Dimension(panelW,panelH);                    //~1416I~
    }                                                              //~1416I~
    public Image initImage(int Pw,int Ph)                          //~1416I~
    {                                                              //~1416I~
	    if (Dump.Y) Dump.println("NavigationPanel:MyPanel createImage ("+Pw+","+Ph+")");//~1506R~
        if (Pw==0||Ph==0)                                          //~1502R~
        	return null;                                                //~1416I~
    	image=Image.createImage(Pw,Ph);//setup androidCanvas for Bitmap update//~1416I~
    	graphics=image.getGraphics();                              //~1416I~
	    if (Dump.Y) Dump.println("NavigationPanel:MyPanel createImage image="+image.toString()+",g="+graphics.toString());//~1506R~
        Component c=getDirectParent();                              //~1417I~
        Color bg=c.getBackground();                                //~1417I~
        if (bg!=null)                                              //~1417I~
	        graphics.setBackground(bg);                            //~1417R~
    	return image;                                              //~1416I~
    }                                                              //~1416I~
                 //~1416I~
//******************************                                   //~1416I~
    public Dimension getSize()                                           //~1416I~
    {                                                              //~1416I~
    	return dimension;                                          //~1416I~
    }                                                              //~1416I~
//******************************                                   //~1416I~
//*from GoFrame                                                    //~1416I~
//*GoFrame: Panel3D(NavigationPanel)                               //~1416I~
//******************************                                   //~1416I~
    public void repaint()                                          //~1416I~
    {                                                              //~1416I~
	    if (Dump.Y) Dump.println("Ajagoc:NavigationPanel repaint");//~1506R~
        if (panelH==0||panelW==0)                                     //~1502R~
        {                                                          //~1416I~
        	initPanel();                                           //~1417R~
	        if (panelH==0||panelW==0)                              //~1502R~
      		  	return;                                            //~1416I~
        }                                                          //~1416I~
	    if (Dump.Y) Dump.println("Ajagoc:NavigationPanel repaint g="+graphics.toString());//~1506R~
    	paint(graphics);                                           //~1417R~
    	graphics.setBitmap(view,image);	//set to ImageView         //~1417I~
    }                                                              //~1416I~
}//class MyPanel                                                   //~1416R~
