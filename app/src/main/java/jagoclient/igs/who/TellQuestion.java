package jagoclient.igs.who;

import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.KeyAdapter;
import com.Ajagoc.awt.KeyEvent;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.TextField;
import com.Ajagoc.awt.WindowEvent;

import jagoclient.Global;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.GrayTextField;
import jagoclient.gui.MyLabel;
import jagoclient.gui.MyPanel;
import jagoclient.gui.Panel3D;
import jagoclient.gui.SimplePanel;
import jagoclient.gui.TextFieldAction;
import jagoclient.igs.ConnectionFrame;
//import java.awt.Frame;                                           //+1417R~
//import java.awt.Panel;                                           //+1417R~
//import java.awt.TextField;                                       //+1417R~
//import java.awt.event.KeyAdapter;                                //+1417R~
//import java.awt.event.KeyEvent;                                  //+1417R~
//import java.awt.event.WindowEvent;                               //+1417R~
/**
Ask to tell the chosen user something, using the IGS tell command.
*/

public class TellQuestion extends CloseDialog
{	ConnectionFrame F;
	TextField T;
	TextField User;
	/**
	@param f the connection frame, which is used to send the output to IGS.
	@param user the user name of the person, which is to receive the message.
	*/
	public TellQuestion (Frame fr, ConnectionFrame f, String user)
	{	super(fr,Global.resourceString("Tell"),false);
		F=f;
		add("North",new SimplePanel(
			new MyLabel(Global.resourceString("To_")),0.4,
			User=new GrayTextField(user),0.6));
		add("Center",T=new TextFieldAction(this,Global.resourceString("Tell")));
		T.addKeyListener(new KeyAdapter ()
			{	public void keyReleased (KeyEvent e)
				{	String s=Global.getFunctionKey(e.getKeyCode());
					if (s.equals("")) return;
					T.setText(s); 
				}
			}
		);
		Panel p=new MyPanel();
		p.add(new ButtonAction(this,Global.resourceString("Tell")));
		p.add(new ButtonAction(this,Global.resourceString("Message")));
		p.add(new ButtonAction(this,Global.resourceString("Cancel")));
		add("South",new Panel3D(p));
		Global.setpacked(this,"tell",200,150,f);
		validate(); show();
	}
	public void windowOpened (WindowEvent e)
	{	T.requestFocus();
	}
	public void doAction (String o)
	{	Global.notewindow(this,"tell");   
		if (Global.resourceString("Tell").equals(o))
		{	if (!T.getText().equals(""))
			{	F.out("tell "+User.getText()+" "+T.getText());
			}
			setVisible(false); dispose();
		}
		else if (Global.resourceString("Message").equals(o))
		{	if (!T.getText().equals(""))
			{	F.out("message "+User.getText()+" "+T.getText());
			}
			setVisible(false); dispose();
		}
		else if (Global.resourceString("Cancel").equals(o))
		{	setVisible(false); dispose();
		}
		else super.doAction(o);
	}
}
