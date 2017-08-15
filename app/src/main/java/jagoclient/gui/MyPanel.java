package jagoclient.gui;

//import java.awt.*;

import com.Ajagoc.awt.Graphics;
import com.Ajagoc.awt.Panel;

import jagoclient.Global;

public class MyPanel extends Panel                                 //~1112R~
{	public MyPanel ()
	{                                                              //~1112R~
    	super();                                                   //~1112R~
		if (Global.ControlBackground!=null)                        //~1112I~
			setBackground(Global.ControlBackground);               //~1112R~
		repaint();
	}
	public void paint (Graphics g)
	{	super.paint(g);
		getToolkit().sync();
	}
}
