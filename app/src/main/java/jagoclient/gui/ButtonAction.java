//*CID://+1Ag1R~:                             update#=    6;       //+1Ag1R~
//**************************************************************************//~v1E9I~
//1Ag1 2016/10/06 Change Top panel. set menu panel as tabwidget.   //+1Ag1I~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//2015/07/24 //1Ad0 2015/07/17 for 1Abi,from Asgts:1A10            //~1Ad0I~
//1A8t 2015/04/15 add help to top panel                            //~1A8tI~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//**************************************************************************//~v1E9I~
package jagoclient.gui;

import jagoclient.Dump;
import jagoclient.Global;

//import java.awt.Button;                                          //~1217R~
import android.view.View;

import com.Ajagoc.AG;
import com.Ajagoc.awt.Button;                                      //~1217I~
import com.Ajagoc.awt.Container;

/**
Similar to ChoiceAction but for buttons.
@see jagoclient.gui.ChoiceAction
*/

public class ButtonAction extends Button
{   DoActionListener C;
    String Name;
    ActionTranslator AT;
    public ButtonAction (DoActionListener c, String s, String name)
    {   super(s);
        C=c; Name=name;
	    addActionListener(AT=new ActionTranslator(c,name));
        setFont(Global.SansSerif);
    }
    public ButtonAction (DoActionListener c, String s)
    {   this(c,s,s);
    }
    //****************************************************************************//~v1E9I~
    //*findview by specified resid                                 //~1A8tR~
    //****************************************************************************//~v1E9I~
    public ButtonAction (Container Pcontainer,int Ptextid,int Presid)//~v1E9I~
    {                                                              //~v1E9I~
       	super(Pcontainer,Ptextid,Presid);                          //~v1E9I~
        C=(DoActionListener)Pcontainer;                            //~v1E9I~
        Name=label;  //by textid                                   //~v1E9I~
        addActionListener(AT=new ActionTranslator(C,Name));        //~v1E9I~
        if (Name==null)                                            //~v1E9I~
        {                                                          //~v1E9I~
        	Name=label;		//super:Button:getText()               //~v1E9I~
            AT.Name=label;                                         //~v1E9I~
        }                                                          //~v1E9I~
        setFont(Global.SansSerif);                                 //~v1E9I~
    }                                                              //~v1E9I~
    //****************************************************************************//+1Ag1I~
    //*with setfont option                                         //+1Ag1I~
    //****************************************************************************//+1Ag1I~
    public ButtonAction (Container Pcontainer,int Ptextid,int Presid,boolean Psetfont)//+1Ag1I~
    {                                                              //+1Ag1I~
       	super(Pcontainer,Ptextid,Presid);                          //+1Ag1I~
        C=(DoActionListener)Pcontainer;                            //+1Ag1I~
        Name=label;  //by textid                                   //+1Ag1I~
        addActionListener(AT=new ActionTranslator(C,Name));        //+1Ag1I~
        if (Name==null)                                            //+1Ag1I~
        {                                                          //+1Ag1I~
        	Name=label;		//super:Button:getText()               //+1Ag1I~
            AT.Name=label;                                         //+1Ag1I~
        }                                                          //+1Ag1I~
        if (Psetfont)                                              //+1Ag1I~
	        setFont(Global.SansSerif);                             //+1Ag1I~
    }                                                              //+1Ag1I~
    //****************************************************************************//~1A8tI~
    //*findview by specified layout & resid                        //~1A8tI~
    //****************************************************************************//~1A8tI~
    public ButtonAction (Container Pcontainer,View Pview,int Ptextid,int Presid)//~1A8tI~
    {                                                              //~1A8tI~
       	super(Pcontainer,Pview,Ptextid,Presid);                    //~1A8tI~
        C=(DoActionListener)Pcontainer;                            //~1A8tI~
        Name=label;  //by textid                                   //~1A8tI~
        addActionListener(AT=new ActionTranslator(C,Name));        //~1A8tI~
        if (Name==null)                                            //~1A8tI~
        {                                                          //~1A8tI~
        	Name=label;		//super:Button:getText()               //~1A8tI~
            AT.Name=label;                                         //~1A8tI~
        }                                                          //~1A8tI~
        setFont(Global.SansSerif);                                 //~1A8tI~
    }                                                              //~1A8tI~
    //****************************************************************************//~@@@@I~//~1Ae5I~
    //*set doaction text by string id                              //~@@@@I~//~1Ae5I~
    //****************************************************************************//~@@@@I~//~1Ae5I~
    public void setAction(int Ptextid)                             //~@@@@I~//~1Ae5I~
    {                                                              //~@@@@I~//~1Ae5I~
        String name;                                               //~@@@@I~//~1Ae5I~
    //**********                                                   //~@@@@I~//~1Ae5I~
        if (Dump.Y) Dump.println("BuattonAction:setAction textid="+Ptextid);//~@@@@I~//~1Ae5I~
	    Name=AG.resource.getString(Ptextid);	//doAction string  //~@@@@I~//~1Ae5I~
        AT.Name=Name;                                              //~@@@@I~//~1Ae5I~
        setText(androidButton,Name);	//by component             //~@@@@R~//~1Ae5I~
    }                                                              //~@@@@I~//~1Ae5I~
    //****************************************************************************//~1A10I~//~1Ad0I~
    public void setVisibility(int Pvisible)                        //~1A10I~//~1Ad0I~
    {                                                              //~1A10I~//~1Ad0I~
        if (Dump.Y) Dump.println("BuattonAction:setVisibility name="+Name+",visible="+Pvisible);//~1A10I~//~1Ad0I~
        setVisibility(androidButton,Pvisible);//by component       //~1A10I~//~1Ad0I~
    }                                                              //~1A10I~//~1Ad0I~
    
}
