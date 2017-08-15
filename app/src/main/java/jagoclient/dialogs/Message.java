//*CID://+dateR~:                             update#=    1;       //+5224I~
//*****************************************************************//+5224I~
//1A82 2015/02/24 Message support stringId                         //+5224I~
//*****************************************************************//+5224I~
package jagoclient.dialogs;

import com.Ajagoc.AG;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.Panel;

import jagoclient.Global;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.MyPanel;
import jagoclient.gui.Panel3D;
//import jagoclient.gui.Panel3D;

//import java.awt.Frame;
//import java.awt.Panel;

//import rene.viewer.SystemViewer;                                 //~1214R~
//import rene.viewer.Viewer;                                       //~1214R~
import com.Ajagoc.rene.viewer.SystemViewer;                        //~1214I~
import com.Ajagoc.rene.viewer.Viewer;                              //~1214I~

/**
This class is used to display messages from the go server.
The message can have several lines.
*/

public class Message extends CloseDialog
{	Viewer T;
    public Message (Frame f, int Presid)                           //+5224I~
    {                                                              //+5224I~
        this(f,AG.resource.getString(Presid));                     //+5224I~
    }                                                              //+5224I~
	public Message (Frame f, String m)
	{	super(f,Global.resourceString("Message"),false);
		add("Center",T=
			Global.getParameter("systemviewer",false)?
				new SystemViewer():new Viewer());
		T.setFont(Global.Monospaced);
		Panel p=new MyPanel();
		p.add(new ButtonAction(this,Global.resourceString("OK")));
		add("South",new Panel3D(p));
		Global.setwindow(this,"message",300,150);
		validate();
		show();
		T.setText(m);
	}
	public void doAction (String o)
	{	Global.notewindow(this,"message");	
		if (Global.resourceString("OK").equals(o))
		{	setVisible(false); dispose();
		}
		else super.doAction(o);
	}
}

