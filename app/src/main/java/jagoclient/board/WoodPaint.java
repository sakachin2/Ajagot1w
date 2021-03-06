package jagoclient.board;

import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Frame;

import jagoclient.Global;
//import jagoclient.StopThread;
import jagoclient.StopThread;

//import java.awt.Color;
//import java.awt.Frame;

/**
This is a thread to create an empty board.
@see jagoclient.board.EmptyPaint
*/

public class WoodPaint extends StopThread
{	int W,H,Ox,Oy,D;
	Color C;
	Frame F;
	boolean Shadows;
	public WoodPaint (Frame f)
	{	F=f;
		setPriority(getPriority()-1);
		start();
	}
	public void run ()
	{	EmptyPaint.createwood(this,F,
						Global.getParameter("sboardwidth",0),
						Global.getParameter("sboardheight",0),
						Global.getColor("boardcolor",170,120,70),
						Global.getParameter("shadows",true),
						Global.getParameter("sboardox",5),
						Global.getParameter("sboardoy",5),
						Global.getParameter("sboardd",10));
	}
}

