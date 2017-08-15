package jagoclient.gui;

//import java.awt.*;                                               //+1524R~
//import java.awt.event.*;
import com.Ajagoc.awt.KeyEvent;                                    //+1524M~
import com.Ajagoc.awt.KeyListener;                                 //+1524M~
import com.Ajagoc.awt.MenuItem;                                    //+1524M~
import com.Ajagoc.awt.PopupMenu;                                   //+1524M~

import jagoclient.Dump;
import jagoclient.Global;

import rene.util.list.*;

/**
A TextField, which display the old input, when cursor up is
pressed. The old input is stored in a list. The class is derived
from TextFieldAction.
@see TextFieldAction
*/

public class HistoryTextField extends TextFieldAction              //~1129R~
    implements KeyListener,DoActionListener
{	ListClass H;
	PopupMenu M=null;
	public HistoryTextField (DoActionListener l, String name)
	{	super(l,name);
	    H=new ListClass();
		H.append(new ListElement(""));
		addKeyListener(this);
	}
	public HistoryTextField (DoActionListener l, String name, int s)
	{	super(l,name,s);
	    H=new ListClass();
		H.append(new ListElement(""));
		addKeyListener(this);
	}
	public void keyPressed (KeyEvent ev)
	{                                                              //~1128R~
    	if (Dump.Y) Dump.println("HTF keyPressed cod="+ev.getKeyCode());//~1511R~
		switch (ev.getKeyCode())                                   //~1128I~
		{	case KeyEvent.VK_UP :
			case KeyEvent.VK_DOWN : 
				if (M==null)
				{	M=new PopupMenu();
					ListElement e=H.first();
					while (e!=null)
					{	String t=(String)e.content();
						if (!t.equals(""))
						{	MenuItem item=new MenuItemAction(this,
								t,t);
							M.add(item);
						}
						e=e.next();
					}
					add(M);
				}
				M.show(this,10,10);
				break;
			default : return;
		}
	}
	public void keyReleased (KeyEvent e) {}
	public void keyTyped (KeyEvent e) {}
	String Last;
	public void remember (String s)
	{	if (s.equals(Last)) return;
		deleteFromHistory(s);
		Last=s;
		H.last().content(s);
		H.append(new ListElement(""));
		M=null;
	}
	public void deleteFromHistory (String s)
	{	ListElement e=H.first();
		while (e!=null)
		{	String t=(String)e.content();
			ListElement next=e.next();
			if (t.equals(s))
			{	H.remove(e);
				if (H.first()==null) H.append(new ListElement(""));
			}
			e=next;
		}
	}
	public void remember ()
	{                                                              //~1113R~
//  	remember(getText());                                       //~1113I~
    	remember(getText().toString());                            //~1113I~
	}
	public void saveHistory (String name)
	{	int i,n=Global.getParameter("history.length",10);
		Global.removeAllParameters("history."+name);
		ListElement e=H.last();
		if (e==null) return;
		for (i=0; i<n && e!=null; e=e.previous())
		{	String s=(String)e.content();
			if (!s.equals(""))
			{	i++;
				Global.setParameter("history."+name+"."+i,s);
			}
		}
	}
	public void loadHistory (String name)
	{	int i=1;
		while (Global.haveParameter("history."+name+"."+i))
		{	String s=Global.getParameter("history."+name+"."+i,"");
			if (!s.equals("")) H.prepend(new ListElement(s));
			i++;
		}
	}
	public void doAction (String o)
	{	if (!o.equals(""))
		{	setText(o);
		}
	}
	public void itemAction (String o, boolean flag)
	{
	}
}//class
