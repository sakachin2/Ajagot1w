package jagoclient.gui;

//import java.awt.*;                                               //~1112R~

import com.Ajagoc.awt.Container;
import com.Ajagoc.awt.Graphics;
import com.Ajagoc.awt.Label;

import jagoclient.Global;

/**
A label in a specified font.
*/

public class MyLabel extends Label                                 //~1112R~
{   public MyLabel (String s)
    {   super(s);                                                  //~1112R~
        setFont(Global.SansSerif);                                 //~1112R~//+1216R~
    }
    public void paint (Graphics g)                               //~1112R~//+1216R~
    {	Container c=getParent();
    	if (c!=null) setBackground(c.getBackground());
    	super.paint(g);
    }                                                            //~1112R~//+1216R~
}
