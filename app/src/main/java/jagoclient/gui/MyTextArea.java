//*CID://+v1E9R~:                             update#=    2;       //~v1E9I~
//**************************************************************************//~v1E9I~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//**************************************************************************//~v1E9I~
package jagoclient.gui;

//import java.awt.*;                                               //~1524R~
import java.io.*;
                                                                   //~1524I~
import com.Ajagoc.awt.Container;
import com.Ajagoc.awt.TextArea;
import jagoclient.Global;
import rene.util.list.*;

/**
A text area that takes care of the maximal length imposed by Windows
and other OSs. This should be replaced by jagoclient.viewer.Viewer
<p>
The class works much like TextArea, but takes care of its length.
@see jagoclient.viewer.Viewer
*/

public class MyTextArea extends TextArea
{   ListClass L;
	public int MaxLength;
	int Length=0;
	public MyTextArea ()
    {	setFont(Global.Monospaced);
        setBackground(Global.gray);
    	L=new ListClass();
    	MaxLength=Global.getParameter("maxlength",10000);
    }
	public MyTextArea (String s, int x, int y, int f)
    {	super(s,x,y,f);
    	setFont(Global.Monospaced);
        setBackground(Global.gray);
    	L=new ListClass();
    	MaxLength=Global.getParameter("maxlength",10000);
    	setText(s);
    }
    //*******************************************************************//~v1E9I~
    //*find view by res id*                                        //~v1E9I~
    //*******************************************************************//~v1E9I~
	public MyTextArea (String s,int Presid, int x, int y, int f)   //~v1E9I~
    {	super(s,Presid,x,y,f);                                     //~v1E9I~
    	setFont(Global.Monospaced);                                //~v1E9I~
        setBackground(Global.gray);                                //~v1E9I~
    	L=new ListClass();                                         //~v1E9I~
    	MaxLength=Global.getParameter("maxlength",10000);          //~v1E9I~
    	setText(s);                                                //~v1E9I~
    }                                                              //~v1E9I~
    //*******************************************************************//+v1E9I~
	public MyTextArea (Container Pcontainer,String s,int Presid, int x, int y, int f)//+v1E9I~
    {                                                              //+v1E9I~
    	super(Pcontainer,s,Presid,x,y,f);                          //+v1E9I~
    	setFont(Global.Monospaced);                                //+v1E9I~
        setBackground(Global.gray);                                //+v1E9I~
    	L=new ListClass();                                         //+v1E9I~
    	MaxLength=Global.getParameter("maxlength",10000);          //+v1E9I~
    	setText(s);                                                //+v1E9I~
    }                                                              //+v1E9I~
    public void append (String s)
    {	Length+=s.length();
    	L.append(new ListElement(s));
		if (Length>MaxLength)
		{	setVisible(false);
			super.setText("");
			ListElement e=L.last();
			Length=0;
			while (Length<MaxLength/4)
			{	Length+=((String)e.content()).length();
				if (e.previous()==null) break;
				e=e.previous();
			}
			while (e!=null)
			{	super.append((String)e.content());
				e=e.next();
			}
			setVisible(true);
		}
    	else super.append(s);
    }
    public void save (PrintWriter s)
    {	ListElement e=L.first();
    	while (e!=null)
    	{	s.print((String)e.content());
    		e=e.next();
    	}
    }
    public void setText (String s)
    {	Length=s.length();
    	super.setText(s);
    	L=new ListClass();
    	L.append(new ListElement(s));
    }
    public void setEditable (boolean flag)
    {	super.setEditable(flag);
    	if (!flag) setBackground(Global.gray.brighter());
    }
}
