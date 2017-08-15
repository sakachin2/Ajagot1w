//*CID://+1Ag2R~:                             update#=    1;       //+1Ag2I~
//************************************************************************************//+1Ag2I~
//1Ag2 2016/10/08 show description of Button on Board(action on board but no need to execute on boardSync thread)//+1Ag2I~
//************************************************************************************//+1Ag2I~
package jagoclient.gui;

import jagoclient.Dump;

import com.Ajagoc.awt.ActionEvent;

//import java.awt.event.ActionEvent;                               //~1112R~
//import java.awt.event.ActionListener;                            //~1112R~

//public class ActionTranslator implements ActionListener          //~1112R~
public class ActionTranslator                                      //~1112I~
{                                                                  //~1217R~
//  String Name;                                                   //~1217I~
    public String Name; //for Ajagoc.awt.Button @@@@               //~1217I~
    public boolean swMainthread;	//exceute on mainthread even if action on board//+1Ag2I~
    DoActionListener C;                                            //~1325R~//~1330R~
    public ActionTranslator (DoActionListener c, String name)
    {   Name=name; C=c;
    }
    public void actionPerformed (ActionEvent e)                    //~1112R~//~1114R~
    {                                                              //~1324R~
    	if (Dump.Y) Dump.println("ActionTranslator cmd="+Name);    //~1506R~
        e.setActionCommand(Name);                 //@@@@ for AjagoModal//~1524R~
		C.doAction(Name);                                          //~1324I~
    }
    public void setString (String s)
    {	Name=s;
    }
}
