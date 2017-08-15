package com.Ajagoc.rene.gui;                                       //~1404R~

import jagoclient.gui.ButtonAction;
import jagoclient.gui.MyLabel;

import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.Panel;

//*********************************************************        //~1404I~
//* for Warning only                                               //~1404I~
//* add addhelp() for alternative of rene.gui.CloseDialog *        //~1404I~
//* jagoclient.gui.CloseDialog-->this-->rene.dialog.Warning        //~1404R~
//*********************************************************        //~1404I~
 

public class CloseDialog extends jagoclient.gui.CloseDialog        //~1404R~
{                                                                  //~1404R~
	Frame F;	                                                   //~1404I~
	public String Subject="";                                      //~1404I~
                                                                   //~1404I~
	public CloseDialog (Frame f, String s, boolean modal)          //~1404I~
	{	super(f,s,modal);                                          //~1404I~
		F=f;                                                       //~1404I~
		if (Global.ControlBackground!=null)                        //~1404I~
			setBackground(Global.ControlBackground);               //~1404I~
//      addWindowListener(this);                                   //~1404I~
//      addKeyListener(this);                                      //~1404I~
//      addFocusListener(this);                                    //~1404I~
	}                                                              //~1404I~

	/**                                                            //~1404I~
	 * To add a help button to children.                           //~1404I~
	 * @param p                                                    //~1404I~
	 * @param subject                                              //~1404I~
	 */                                                            //~1404I~
	public void addHelp (Panel p, String subject)                  //~1404I~
	{	p.add(new MyLabel(""));                                    //~1404I~
		p.add(new ButtonAction(this,Global.name("help"),"Help"));  //~1404I~
		Subject=subject;                                           //~1404I~
	}                                                              //~1404I~
	public void center()          //for tre,gui.Global             //+1404I~
    {                                                              //+1404I~
    }                                                              //+1404I~
}
