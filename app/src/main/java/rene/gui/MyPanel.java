package rene.gui;

import com.Ajagoc.awt.Panel;

//import java.awt.Graphics;
//import java.awt.Panel;

public class MyPanel extends Panel                               //~1111R~//+1112R~
{	public MyPanel ()
	{                                                              //~1111R~
    	super();                                         //~1111I~ //+1112R~
		if (Global.ControlBackground!=null)                        //~1111I~
			setBackground(Global.ControlBackground);               //~1111R~
//        repaint();                                               //~1111R~
	}
//    public void paint (Graphics g)                               //~1111R~
//    {   super.paint(g);                                          //~1111R~
//        getToolkit().sync();                                     //~1111R~
//    }                                                            //~1111R~
}
