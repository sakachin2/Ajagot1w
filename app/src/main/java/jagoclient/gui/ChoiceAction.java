//*CID://+dateR~:                             update#=    2;       //~4C13I~
//******************************************************************************//~4C13I~
//v1Ej 2014/12/13 (Asgts)//1A4z 2014/12/09 FileDialog:view file by click twice//~4C13I~
//******************************************************************************//~4C13I~
package jagoclient.gui;

//import java.awt.*;

import com.Ajagoc.awt.Choice;
import com.Ajagoc.awt.Container;
import com.Ajagoc.awt.ItemEvent;
import com.Ajagoc.awt.ItemListener;
//import java.awt.event.*;

import jagoclient.Global;

public class ChoiceAction extends Choice	//ChoiceTranslator as inner class for accessivility from awt.Choice//~1219I~
{                                                                   //~1219I~
public class ChoiceTranslator implements ItemListener
{   DoActionListener C;
    String S;
    public Choice Ch;
    public ChoiceTranslator
        (Choice ch, DoActionListener c, String s)
    {   C=c; S=s; Ch=ch;
    }
    public void itemStateChanged (ItemEvent e)
    {   C.itemAction(S,e.getStateChange()==ItemEvent.SELECTED);
    }
}

/**
This is a choice item, which sets a specified font and translates
events into strings, which are passed to the doAction method of the
DoActionListener.
@see jagoclient.gui.CloseFrame#doAction
@see jagoclient.gui.CloseDialog#doAction
*/

//public class ChoiceAction extends Choice                         //~1219R~
    public ChoiceAction (DoActionListener c, String s)
    {   addItemListener(new ChoiceTranslator(this,c,s));
        setFont(Global.SansSerif);
    }
    public ChoiceAction (DoActionListener c, String s,int Presid)  //+4C13I~
    {                                                              //+4C13I~
    	super((Container)c,Presid);                                //+4C13I~
        addItemListener(new ChoiceTranslator(this,c,s));           //+4C13I~
//      setFont(Global.SansSerif);                                 //+4C13I~
    }                                                              //+4C13I~
                                                          //~4C13I~
}
