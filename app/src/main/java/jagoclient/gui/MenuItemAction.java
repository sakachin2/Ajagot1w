//*CID://+1Ag2R~:                             update#=    2;       //~1Ag2I~
//*********************************************************************************//~1Ag2I~
//1Ag2 2016/10/08 show description of Button on Board(action on board but no need to execute on boardSync thread)//~1Ag2I~
//*********************************************************************************//~1Ag2I~
package jagoclient.gui;

import com.Ajagoc.awt.MenuItem;

import jagoclient.Global;

//import java.awt.MenuItem;

//import com.Ajagoc.awt.Menu;

/**
A menu item with a specified font.
*/

public class MenuItemAction extends MenuItem                       //~1122R~
{   ActionTranslator AT;
	public MenuItemAction (DoActionListener c, String s, String name)
    {   super(s);
	    addActionListener(AT=new ActionTranslator(c,name));
        setFont(Global.SansSerif);
    }
    public MenuItemAction (DoActionListener c, String s)
    {	this(c,s,s);
    }
    public MenuItemAction (DoActionListener c, String s,boolean Pmainthread)//~1Ag2I~
    {	this(c,s,s);                                               //~1Ag2I~
    	AT.swMainthread=Pmainthread;                               //+1Ag2R~
    }                                                              //~1Ag2I~
    public void setString (String s)
    {	AT.setString(s);
    }
}
