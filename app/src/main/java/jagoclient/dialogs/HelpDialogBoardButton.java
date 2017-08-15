//*CID://+1AgbR~: update#= 220;                                    //+1AgbR~
//**********************************************************************//~1107I~
//1Agb 2016/10/10 AxeDialog is 2 two place,com.axe and com.Ajagoc.axe, del later.//+1AgbI~
//1Ag2 2016/10/08 show description of Button on Board(action on board but no need to execute on boardSync thread)//~v1C9I~
//**********************************************************************//~v104I~
//*Menu                                                            //~v104R~
//**********************************************************************//~1107I~
package jagoclient.dialogs;                                        //~v1C9R~

import com.Ajagoc.AG;
import com.Ajagoc.R;
//import com.Ajagoc.axe.AxeDialog;                                 //+1AgbR~
import com.axe.AxeDialog;                                          //+1AgbI~
//**********************************************************************//~1107I~
public class HelpDialogBoardButton extends AxeDialog                           //~1310R~//~v1C9R~
{                                                                  //~0914I~
    static final int LAYOUT=R.layout.dialoghelpboardbutton;        //~v1C9R~
//******************                                               //~1121I~//~v1C9M~
	public HelpDialogBoardButton()                                             //~1107R~//~v1C9R~
    {                                                              //~1107I~//~v1C9M~
        super(LAYOUT);                                             //~v1C9I~
    	String title=AG.resource.getString(R.string.HelpBoardButton);//~v1C9I~
		showDialog(title);     //callback setupDialogExtend()      //~v1C9I~
    }                                                              //~1107I~//~v1C9M~
}//class HelpDialogBoardButton                                     //~v1C9R~
