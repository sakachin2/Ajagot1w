package jagoclient.board;

import com.Ajagoc.awt.Component;
import com.Ajagoc.awt.Panel;

//import java.awt.Component;                                       //~1221R~
//import java.awt.Panel;                                           //~1221R~

/**
This panel contains two panels aside. The left panel is kept square.
*/

class BoardCommentPanel extends Panel
{	Component C1,C2;
	Board B;
	public BoardCommentPanel (Component c1, Component c2, Board b)
	{	C1=c1; C2=c2; B=b;
		add(C1);
		add(C2);
	}
	public void doLayout ()
	{	C1.setSize(getSize().width,getSize().height);
		C1.doLayout();
		C1.setSize(B.getSize().height,getSize().height);
		C1.setLocation(0,0);
		C2.setSize(getSize().width-B.getSize().height,
		    getSize().height);
		C2.setLocation(B.getSize().height,0);
		C1.doLayout();
		C2.doLayout();
	}
}

