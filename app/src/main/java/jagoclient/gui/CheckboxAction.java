package jagoclient.gui;

//import java.awt.*;
//import java.awt.event.*;

import com.Ajagoc.awt.Checkbox;
import com.Ajagoc.awt.ItemEvent;
import com.Ajagoc.awt.ItemListener;

import jagoclient.Global;

public class CheckboxAction extends Checkbox                       //~1215M~
{                                                                  //~1215I~
//made to inner class to avoid seperate public class to its own class//~1124I~//+1215I~
public class CheckboxActionTranslator implements ItemListener
{   DoActionListener C;
    String S;
    public Checkbox CB;
    public CheckboxActionTranslator
        (Checkbox cb, DoActionListener c, String s)
    {   C=c; S=s; CB=cb;
    }
    public void itemStateChanged (ItemEvent e)
    {   C.itemAction(S,CB.getState());
    }
}

/**
Similar to ChoiceAction, but for checkboxes.
@see jagoclient.gui.ChoiceAction
*/

    public CheckboxAction (DoActionListener c, String s)           //~1215I~
    {   super(s);
        addItemListener(new CheckboxActionTranslator(this,c,s));
        setFont(Global.SansSerif);
    }                                                              //~1215R~
    public CheckboxAction (DoActionListener c, String s, String h)
    {   super(s);
        addItemListener(new CheckboxActionTranslator(this,c,h));
    }
}
