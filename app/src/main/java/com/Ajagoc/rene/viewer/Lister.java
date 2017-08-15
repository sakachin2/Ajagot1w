package com.Ajagoc.rene.viewer;

import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.PopupMenu;



public class Lister extends com.Ajagoc.awt.Lister                  //~1220R~
{
	public Lister()                                                //~1220R~
	{
	}
	public void setPopupMenu(PopupMenu Pmenu)                           //~1220I~
	{                                                              //~1220I~
        Pmenu.setPopupMenu();                                      //~1306I~
	}                                                              //~1220I~
	public void appendLine0(String Pitem,Color Pcolor)             //~1220I~
	{                                                              //~1220I~
    	appendLine(Pitem,Pcolor);                                  //~1220I~
	}                                                              //~1220I~
	public void appendLine(String Pitem)                          //~1220I~
	{                                                              //~1220I~
    	appendLine(Pitem,Color.black);                             //~1220I~
	} 
	public void appendLine0(String Pitem)                          //~1220I~
	{                                                              //~1220I~
    	appendLine(Pitem,Color.black);                             //~1220I~
	}                                 //~1220I~
}
