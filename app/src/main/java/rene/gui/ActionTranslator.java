package rene.gui;

import com.Ajagoc.awt.ActionEvent;

//import java.awt.event.ActionEvent;                               //~1111R~
//import java.awt.event.ActionListener;                            //~1111R~

/**
A translator for Actions.
*/

//public class ActionTranslator implements ActionListener          //~1111R~
public class ActionTranslator                                      //~1111I~
{   String Name;
    DoActionListener C;
    ActionEvent E;
    public ActionTranslator (DoActionListener c, String name)
    {   Name=name; C=c;
    }
    public void actionPerformed (ActionEvent e)
    {   E=e;
    	C.doAction(Name);
    }
    public void trigger ()
    {	C.doAction(Name);
    }
}
