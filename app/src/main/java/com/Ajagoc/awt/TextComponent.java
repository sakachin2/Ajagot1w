//*CID://+v1E9R~:                             update#=    1;       //+v1E9I~
//****************************************************************************//+v1E9I~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//+v1E9I~
//****************************************************************************//+v1E9I~
package com.Ajagoc.awt;                                            //~1112I~

import android.widget.ScrollView;
import android.widget.TextView;

public class TextComponent extends Component                       //~1216R~
{                                                                  //~1112I~
                                                                   //~1218I~
	public TextView textView;	//for TextField                    //~1425R~
	public ScrollView scrollView;	//for TextField                //~1425R~
                                                                   //~1218I~
//***********************************                              //~1218I~
	public TextComponent()                                           //~1216I~
    {                                                              //~1216I~
    }                                                              //~1216I~
//***********************************                              //+v1E9I~
	public TextComponent(Container Pcontainer)                     //+v1E9I~
    {                                                              //+v1E9I~
    	super(Pcontainer);                                         //+v1E9I~
    }                                                              //+v1E9I~
//***********************************                              //+v1E9I~
    public void setText(String Ptext)                              //~1216I~
    {                                                              //~1216I~
    	setText(textView,Ptext);	//by Component                                    //~1216I~//~1221R~//~1310R~
    }                                                              //~1216I~
//*****************                                                //~1218I~
    public void append(String Pline)        //~1218R~              //~1219R~
    {                                                              //~1218I~
		append(Pline,null);     //by Component                     //~1221R~
    }                                                              //~1218I~
    public void append(String Pline,Color Pcolor)                  //~1219I~
    {                                                              //~1219I~
		append(textView,scrollView,Pline,Pcolor);                  //~1221R~
    }                                                              //~1219I~
//***********************                                          //~1310I~
    public void setBackground(Color Pcolor)                        //~1310I~
    {                                                              //~1310I~
    	setBackground(textView,Pcolor);   //by Component           //~1310I~
    }                                                              //~1310I~
//***********************                                          //~1312I~
    public void setBackground(Color Pcolor,Color Ptextcolor)       //~1312I~
    {                                                              //~1312I~
    	setBackground(textView,Pcolor,Ptextcolor);   //by Component//~1312I~
    }                                                              //~1312I~
//***********************                                          //~1310I~
    public void setFont(Font Pfont)                                //~1310I~
    {                                                              //~1310I~
    	setFont(textView,Pfont);   //by Component                  //~1310I~
    }                                                              //~1310I~
                                                                   //~1310I~
}//class                                                           //~1112I~
