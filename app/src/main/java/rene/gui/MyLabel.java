package rene.gui;

import com.Ajagoc.awt.Container;
import com.Ajagoc.awt.Graphics;
import com.Ajagoc.awt.Label;

//import java.awt.Container;                                         //+1511R~
//import java.awt.Graphics;                                          //+1511R~
//import java.awt.Label;                                             //+1511R~

/**
A Label with a midifyable Font.
*/

public class MyLabel extends Label                                 //+1511R~
{   public MyLabel (String s)
    {   super(s);                                                  //+1511R~
        if (Global.NormalFont!=null) setFont(Global.NormalFont);   //+1511R~
    }
    public MyLabel (String s, int allign)
    {   super(s,allign);                                           //+1511R~
        if (Global.NormalFont!=null) setFont(Global.NormalFont);   //+1511R~
    }
    /**                                                            //+1511R~
    This is for Java 1.2 on Windows.                               //+1511R~
    */                                                             //+1511R~
    public void paint (Graphics g)                                 //+1511R~
    {	Container c=getParent();
    	if (c!=null) setBackground(c.getBackground());
    	super.paint(g);
    }                                                              //+1511R~
}
