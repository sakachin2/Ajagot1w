package com.Ajagoc.awt;                                            //~1112I~

import com.Ajagoc.AG;

import android.view.View;
import android.widget.TextView;                                    //~1112R~

                                                                   //~1112I~
public class Label extends Component                               //~1112R~//~1216R~
                 //~1216I~
{                                                                  //~1112I~
    public TextView textView=null;                                        //~1216R~//~1401R~
    public String label;                                           //~1401I~
    private Container parent=null;                                         //~1216I~
    static int passwordctr=0;                                      //~1216I~
//*******************************                                  //~1216I~
    public Label()                                                 //~1112R~
    {                                                              //~1112I~
        init(null,null);                                           //~1216R~
    }                                                              //~1112I~
    public Label(String Pname)                                         //~1112I~
    {                                                              //~1112I~
        init(findView(Pname),Pname);                                    //~1216R~//~1221R~
    }                                                              //~1112I~
    public Label(String Pname, int align)    //from rene.MyLabel    //~1511I~
    {                                                              //~1511I~
        this(Pname);                                               //~1511I~
    }                                                              //~1511I~
    public TextView findView(String Pname)                                     //~1216R~//~1221R~
    {                                                              //~1216I~
    	if (Pname==null || Pname.equals(""))                       //~1221I~
        	return null;                                           //~1221I~
        return (TextView)AG.findLabelView();                       //~1216R~
    }                                                              //~1216I~
    private void init(TextView PtextView,String Pname)             //~1216R~
    {   
    	if (PtextView==null)                                       //~1216R~
	        return;                                                //~1216R~
        textView=PtextView;                                        //~1216R~
    	label=Pname;                                               //~1401I~
        if (Pname!=null)                                           //~1216I~
        	setText(Pname);                                        //~1216R~
        textView.setVisibility(View.VISIBLE);	//reset visibility="gone" on xml of ConnectedGoFrame if !TimerInTitle
    }                                                              //~1216I~
//************                                                     //~1216I~
    public void setText(String Ptext)                              //~1216I~//~1221R~
    {                                                              //~1216I~//~1221R~
        setText(textView,Ptext);	//Component:                                   //~1216I~//~1221R~
    }                                                              //~1216I~//~1221R~
//**for MyLabel                                                    //~1216I~//~1221R~
    public void setFont(Font Pfont)                                     //~1216I~
    {                                                              //~1216I~
    	if (Pfont!=null && textView!=null)                                           //~1216I~//~1221R~
        	Pfont.setFont(textView);                               //~1216R~
    }                                                              //~1216I~
    public void setBackground(Color Pcolor)                             //~1216I~
    {                                                              //~1216I~
    	if (Pcolor!=null)                                          //~1216I~
        	Pcolor.setBackground(textView);                        //~1216R~
    }                                                              //~1216I~
    public Container getParent()                                   //~1216I~
    {                                                              //~1216I~
    	if (parent==null)                                          //~1216I~
        	parent=new Container();                                //~1216I~
        return parent;                                             //~1216I~
    }                                                              //~1216I~

}//class                                                           //~1112I~
