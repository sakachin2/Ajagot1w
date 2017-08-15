//*CID://+1Ae5R~:                             update#=    9;       //+1Ae5I~
//*****************************************************************************//~1A1cI~//+1Ae5I~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//+1Ae5I~
//1A1d 2013/03/17 popup at partner when decliened resign           //~1A1dI~//+1Ae5I~
//*****************************************************************************//~1A1cI~//+1Ae5I~
package jagoclient.partner;

import com.Ajagoc.AG;
import com.Ajagoc.R;

import jagoclient.Global;
import jagoclient.dialogs.Question;

/**
Question to end the game, or decline that.
*/

public class EndGameQuestion extends Question
{	PartnerFrame G;
    boolean resign;                                                //~@@@@I~//+1Ae5I~
	public EndGameQuestion (PartnerFrame g)
	{	super(g,Global.resourceString("End_the_game_"),Global.resourceString("End"),g,true); G=g;
		show();
	}
	public EndGameQuestion (PartnerFrame g,String Pmsg,boolean Presign)//~@@@@I~//+1Ae5I~
	{	super(g,Pmsg,AG.resource.getString(R.string.Title_EndGameQuestion),g,true/*,false*/);//~@@@@R~//+1Ae5I~
		G=g;                                                       //~@@@@I~//+1Ae5I~
    	resign=Presign;                                            //~@@@@R~//+1Ae5I~
		show();                                                    //~@@@@I~//+1Ae5I~
	}                                                              //~@@@@I~//+1Ae5I~
	public void tell (Question q, Object o, boolean f)
	{	q.setVisible(false); q.dispose();
	    if (f) G.doendgame();
		else G.declineendgame();
	}
	public boolean close ()
	{	G.declineendgame();
		return true;
	}
}

