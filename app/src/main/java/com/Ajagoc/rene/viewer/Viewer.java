//*CID://+dateR~: update#=  55;                                    //~1107R~
//**********************************************************************//~1107I~
//*TextArea width Vertical ScrollView                              //~1214R~
//**********************************************************************//~1107I~
package com.Ajagoc.rene.viewer;                                         //~1107R~  //~1108R~//~1109R~//~1214R~

import java.io.PrintWriter;

import jagoclient.gui.CloseFrame;

import com.Ajagoc.AG;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Dialog;

public class Viewer extends com.Ajagoc.awt.Viewer                                               //~1215R~
{                                                              //~1111I~//~1112I~
    com.Ajagoc.awt.Viewer awtviewer;                          //~1215I~
	CloseFrame closeframe=null;                                    //~1214I~
	Dialog     dialog=null;                                        //~1215I~
	String name=null;                                              //~1214I~
//*********************                                            //~1216I~
    public Viewer()                                                //~1214I~
    {                                                              //~1214I~
//*for dialog                                                      //~1215I~
        super();                                                   //~1214I~
        dialog=AG.getCurrentDialog();                              //~1216R~
    }                                                              //~1214I~
//******************                                               //~1215I~
    public  Viewer(String Pname)	//from Lister                  //~1220R~
    {                                                              //~1215I~
    	super(Pname);                                              //~1216R~
    }                                                              //~1215I~
//******************                                               //~1220I~
    public  Viewer(CloseFrame Pcf,String Pname)                    //~1220I~
    {                                                              //~1220I~
    	super(Pname);                                              //~1220I~
	    initViewer(Pcf,Pname);                                     //~1220I~
                                                                   //~1220I~
    }                                                              //~1220I~
//******************                                               //~1215I~
    public void initViewer(CloseFrame Pcf,String Pname)            //~1215I~
    {                                                              //~1215I~
        closeframe=Pcf;                                            //~1215I~
        name=Pname;                                                //~1215I~
    }                                                              //~1215I~
//******************                                               //~1215I~
    public void setText(String Ptext)                              //~1215R~
    {                                                              //~1215I~
		if (dialog!=null)                                          //~1216I~
    		dialog.setViewerText(this,Ptext);                     //~1215R~//~1216R~
    }                                                              //~1215I~
//******************                                               //~1326I~
    public void appendLine(String Ptext)                           //~1326I~
    {                                                              //~1326I~
		append(Ptext,Color.black);                                 //~1326R~
    }                                                              //~1326I~
//******************                                               //+1418I~
    public void save(PrintWriter Ppw)//from  ConnectionFrame       //+1418I~
    {                                                              //+1418I~
		super.save(Ppw);                                           //+1418I~
    }                                                              //+1418I~
}//class                                                              //~1111I~//~1112I~
