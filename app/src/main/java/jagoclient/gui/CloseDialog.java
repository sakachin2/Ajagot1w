//*CID://+v1E9R~:                             update#=    2;       //~v1E9I~
//***************************************************************************//~v1E9I~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//***************************************************************************//~v1E9I~
package jagoclient.gui;

//import java.awt.*;                                               //~1112R~
//import java.awt.event.*;                                         //~1112R~

//import android.app.Dialog;


import android.graphics.Point;

import com.Ajagoc.awt.ActionEvent;
import com.Ajagoc.awt.ActionListener;
import com.Ajagoc.awt.Dialog;
import com.Ajagoc.awt.Dimension;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.KeyEvent;
import com.Ajagoc.awt.KeyListener;
import com.Ajagoc.awt.WindowEvent;
import com.Ajagoc.awt.WindowListener;

import jagoclient.Global;

/**
A dialog, which can be closed by clicking on the close window
field (a cross on the top right corner in Windows 95). This
dialog does also simplify event processing by implementing
a DoActionListener. The "Close" resource is reserved to close
the dialog. The escape key will close the dialog too.
*/
public class CloseDialog extends Dialog
    implements WindowListener, ActionListener, DoActionListener, KeyListener//~1112R~
{	public CloseDialog (Frame f, String s, boolean modal)
	{	super(f,"",modal);
	    addWindowListener(this);
	    setTitle(s);
	}
    //**********************                                       //~v1E9I~
	public CloseDialog (Frame f, String s, int Presid, boolean modal,boolean waitInput)//~v1E9I~
	{	super(f,s,Presid,modal,waitInput);                         //~v1E9I~
	    addWindowListener(this);                                   //~v1E9I~
	}                                                              //~v1E9I~
    //**********************                                       //+v1E9I~
	public CloseDialog (CloseDialog Pdialog, String s, int Presid, boolean modal,boolean waitInput)//+v1E9I~
	{	super(Pdialog,s,Presid,modal,waitInput);                   //+v1E9I~
	    addWindowListener(this);                                   //+v1E9I~
	}                                                              //+v1E9I~
    //**********************                                       //~v1E9I~
	public void windowActivated (WindowEvent e) {}
	public void windowClosed (WindowEvent e) {}
	public void windowClosing (WindowEvent e)
	{   if (close()) { setVisible(false); dispose(); }
	}
	public void windowDeactivated (WindowEvent e) {}
	public void windowDeiconified (WindowEvent e) {}
	public void windowIconified (WindowEvent e) {}
	public void windowOpened (WindowEvent e) {}
	/**
	May be overwritten to ask for permission to close this dialog.
	@return true if the dialog is allowed to close.
	*/
	public boolean close ()
	{	return true;
	}
	public boolean escape ()
	{	return close();
	}
	public void actionPerformed (ActionEvent e)
	{   doAction(e.getActionCommand());
	}
	public void doAction (String o)
	{   if (Global.resourceString("Close").equals(o) && close())
		{	setVisible(false); dispose();
		}
		else if ("Close".equals(o) && close())
		{	setVisible(false); dispose();
		}	
	}
	public void center (Frame f)
	{	Dimension 
			si=f.getSize(),
			d=getSize(),
			dscreen=getToolkit().getScreenSize();
		Point lo=f.getLocation();
		int x=lo.x+si.width/2-d.width/2;
		int y=lo.y+si.height/2-d.height/2;
		if (x+d.width>dscreen.width) x=dscreen.width-d.width-10;
		if (x<10) x=10;
		if (y+d.height>dscreen.height) y=dscreen.height-d.height-10;
		if (y<10) y=10;
		setLocation(x,y);
	}
	public void itemAction (String o, boolean flag) {}
	public void keyPressed (KeyEvent e)
	{	if (e.getKeyCode()==KeyEvent.VK_ESCAPE && close()) dispose();
	}
	public void keyReleased (KeyEvent e) {}
	public void keyTyped (KeyEvent e) {}
}

