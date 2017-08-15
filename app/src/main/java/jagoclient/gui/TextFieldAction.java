//*CID://+v1EjR~:                             update#=    1;       //+v1EjI~
//******************************************************************************//+v1EjI~
//v1Ej 2014/12/13 (Asgts)//1A4z 2014/12/09 FileDialog:view file by click twice//+v1EjI~
//******************************************************************************//+v1EjI~
package jagoclient.gui;

import com.Ajagoc.awt.Container;
import jagoclient.gui.DoActionListener;
import com.Ajagoc.awt.TextField;
import jagoclient.Global;

//import java.awt.TextField;                                       //~1112R~

/**
A TextField. The background and the font are set from global
properties. The class uses a TextFieldActionListener to listen
to returns and notify the DoActionListener passing the a
string (name) to its doAction method.
@see TextFieldActionListener
*/

public class TextFieldAction extends TextField
{	TextFieldActionListener T;
    public TextFieldAction (DoActionListener t, String name, String s)
	{	super();
		setBackground(Global.gray);
		setFont(Global.SansSerif);
		T=new TextFieldActionListener(t,name);
		addActionListener(T);
		setText(s);
	}
	public TextFieldAction (DoActionListener t, String name)
	{	setBackground(Global.gray);
		setFont(Global.SansSerif);
		T=new TextFieldActionListener(t,name);
		addActionListener(T);
	}
	public TextFieldAction (DoActionListener t, String name, int n)
	{	super(n);
		setBackground(Global.gray);
		setFont(Global.SansSerif);
		T=new TextFieldActionListener(t,name);
		addActionListener(T);
	}
	public TextFieldAction (DoActionListener t, String name, String s, int n)
	{	super(s,n);
		setFont(Global.SansSerif);
		setBackground(Global.gray);
		T=new TextFieldActionListener(t,name);
		addActionListener(T);
	}
	public TextFieldAction (Container Pparent,DoActionListener t,int PresId,String name/*actionName*/,String PsetText,int Prow,int Pcol)//+v1EjI~
	{                                                              //+v1EjI~
		super(Pparent,PsetText,PresId,Prow,Pcol);                  //+v1EjI~
		setFont(Global.SansSerif);                                 //+v1EjI~
		setBackground(Global.gray);                                //+v1EjI~
		T=new TextFieldActionListener(t,name);                     //+v1EjI~
		addActionListener(T);                                      //+v1EjI~
	}                                                              //+v1EjI~
}
