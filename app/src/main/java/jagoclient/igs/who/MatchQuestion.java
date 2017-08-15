//*CID://+1B0bR~:                             update#=    2;       //~1B0bI~
//**************************************************************************//~1B0bI~
//1B0b 130429 Ajagoc not supported match type                      //~1B0bI~
//**************************************************************************//~1B0bI~
package jagoclient.igs.who;

import com.Ajagoc.R;
import com.Ajagoc.awt.Choice;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.GridLayout;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.TextField;

import jagoclient.Global;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.GrayTextField;
import jagoclient.gui.IntField;
import jagoclient.gui.MyLabel;
import jagoclient.gui.MyPanel;
import jagoclient.gui.Panel3D;
import jagoclient.igs.ConnectionFrame;
//import java.awt.Choice;                                          //~1417R~
//import java.awt.Frame;                                           //~1417R~
//import java.awt.GridLayout;                                      //~1417R~
//import java.awt.Panel;                                           //~1417R~
//import java.awt.TextField;                                       //~1417R~
/**
Ask to match the chosen user.
*/

public class MatchQuestion extends CloseDialog
{	ConnectionFrame F;
	TextField T;
	TextField User;
	IntField BoardSize,TotalTime,ExtraTime;
	Choice ColorChoice;
	Choice MatchTypeChoice;                                        //~1B0bI~
	/**
	@param f the ConnectionFrame, which holds the connection.
	*/
	public MatchQuestion (Frame fr, ConnectionFrame f, String user)
	{	super(fr,Global.resourceString("Match"),false);
		F=f;
		Panel pp=new MyPanel();
		pp.setLayout(new GridLayout(0,2));
		pp.add(new MyLabel(Global.resourceString("Opponent")));
		pp.add(User=new GrayTextField(user));
		pp.add(new MyLabel(Global.resourceString("Board_Size")));
		pp.add(BoardSize=new IntField(this,"BoardSize",19));
		pp.add(new MyLabel(Global.resourceString("Your_Color")));
		pp.add(ColorChoice=new Choice());
		ColorChoice.setFont(Global.SansSerif);
		ColorChoice.add(Global.resourceString("Black"));
		ColorChoice.add(Global.resourceString("White"));
		ColorChoice.select(0);
		pp.add(new MyLabel(Global.resourceString("Time__min_")));
		pp.add(TotalTime=new IntField(this,"TotalTime",Global.getParameter("totaltime",10)));
		pp.add(new MyLabel(Global.resourceString("Extra_Time")));
		pp.add(ExtraTime=new IntField(this,"ExtraTime",Global.getParameter("extratime",10)));
		add("Center",new Panel3D(pp));
		Panel p=new MyPanel();
		p.add(new ButtonAction(this,Global.resourceString("Match")));
		p.add(new ButtonAction(this,Global.resourceString("Cancel")));
		add("South",new Panel3D(p));
		Global.setpacked(this,"match",200,150,f);
        setupMatchType();                                          //~1B0bI~
		validate(); show();
	}
	public void doAction (String o)
	{	Global.setParameter("matchwidth",getSize().width);
		Global.setParameter("matchheight",getSize().height);
		if (Global.resourceString("Match").equals(o))
		{	String s="b";
			if (ColorChoice.getSelectedIndex()==1) s="w";
            String matchCmd=getMatchType();                        //+1B0bI~
//  		F.out("match "+User.getText()+" "+s+" "+BoardSize.value(5,29)+" "+//+1B0bR~
    		F.out(matchCmd+" "+User.getText()+" "+s+" "+BoardSize.value(5,29)+" "+//+1B0bI~
				TotalTime.value(0,6000)+" "+
				ExtraTime.value(0,6000));
			Global.setParameter("totaltime",TotalTime.value(0,6000));
			Global.setParameter("extratime",ExtraTime.value(0,6000));
			setVisible(false); dispose();
		}
		else if (Global.resourceString("Cancel").equals(o))
		{	setVisible(false); dispose();
		}
		else super.doAction(o);
	}
//****************************************************************************//~1B0bI~
	private void setupMatchType()                                  //~1B0bI~
    {                                                              //~1B0bI~
		MatchTypeChoice=new Choice();                               //~1B0bI~
		MatchTypeChoice.setFont(Global.SansSerif);                 //~1B0bI~
		MatchTypeChoice.setArray(R.array.MatchingType);            //+1B0bR~
		MatchTypeChoice.select(0);                                 //~1B0bI~
    }                                                              //~1B0bI~
//****************************************************************************//+1B0bI~
	private static final String[] matchingCmd={                    //+1B0bI~
	    "match","ematch","fmatch","fematch","gmatch","lmatch"};    //+1B0bI~
	private String getMatchType()                                  //+1B0bI~
    {                                                              //+1B0bI~
		int idx=MatchTypeChoice.getSelectedIndex();                //+1B0bI~
        if (idx<0 || idx>=matchingCmd.length)                       //+1B0bI~
        	idx=0;                                                 //+1B0bI~
        return matchingCmd[idx];                                    //+1B0bI~
    }                                                              //+1B0bI~
}
