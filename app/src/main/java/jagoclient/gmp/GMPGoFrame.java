//*CID://+1B04R~:                             update#=    5;       //~1B04R~
//*************************************************************************//~v106I~
//1B04 130428 Send field is not used on GMP(set visibility:GONE)   //~1B04I~
//V106 1061:121120 add click sound on also GMP match               //~v106I~
//*************************************************************************//~v106I~
package jagoclient.gmp;

import com.Ajagoc.R;
import com.Ajagoc.awt.Component;

import android.view.View;
import jagoclient.Global;
import jagoclient.Dump;
import jagoclient.board.*;
import jagoclient.dialogs.*;
import jagoclient.sound.JagoSound;                                 //~v106M~

public class GMPGoFrame extends ConnectedGoFrame
	implements TimedBoard
{	GMPConnection C;
	boolean WantMove=true;
	int BlackTime=0,WhiteTime=0;
	long CurrentTime;
	GoTimer Timer=null;
	int MyColor;
	
	public GMPGoFrame (GMPConnection c, int size, int color)
	{	super(Global.resourceString("Play_Go"),size,
			Global.resourceString("Remove_groups"),Global.resourceString(""),
			false,false);
		MyColor=color;
		C=c;
        adjustView();                                              //~1B04I~
		Timer=new GoTimer(this,1000);
		CurrentTime=System.currentTimeMillis();
		setVisible(true);
		repaint();
	}
//*******************************************************************//~1B04R~
//set not used view to GONE                                        //~1B04I~
//*******************************************************************//~1B04I~
	private void adjustView()                                      //~1B04I~
    {                                                              //~1B04I~
    	View v;                                                    //~1B04I~
        v=findView(R.id.sendforwardcontainer);	//igs only         //~1B04I~
        Component.setVisible(v,View.GONE);                      //~1B04I~
        v=findView(R.id.sendcontainer);			//igs/partner      //~1B04I~
        Component.setVisible(v,View.GONE);                      //~1B04I~
        v=findView(R.id.BottomInputLine);			//sendfield container//+1B04I~
        Component.setVisible(v,View.GONE);                      //+1B04I~
    }                                                              //~1B04I~
//*******************************************************************//~1B04I~
	public boolean wantsmove ()
	{	return WantMove;
	}
	
	public void gotMove (int color, int i, int j)
	{	synchronized (B)
		{	if (B.maincolor()==color) return;
			updateTime();
			if (color==GMPConnector.WHITE) white(i,j);
			else black(i,j);
			B.showinformation();
			B.copy();
		}
	}
	
	public void gotSet (int color, int i, int j)
	{	updateTime();
		if (color==GMPConnector.WHITE) setwhite(i,j);
		else setblack(i,j);
	}
	
	public void gotPass (int color)
	{	updateTime();
		if (Dump.Y) Dump.println("Opponent passed");               //~1506R~
		B.setpass();
		Message d=new Message(this,Global.resourceString("Pass"));
	}

	public void notepass ()
	{	if (B.maincolor()==MyColor) return;
		updateTime();
		if (Dump.Y) Dump.println("I pass");                        //~1506R~
		C.pass();
		B.setpass();
	}

	public boolean moveset (int i, int j)
	{	if (B.maincolor()==MyColor) return false;
		updateTime();
		if (Dump.Y) Dump.println("Move at "+i+" "+j);              //~1506R~
		C.moveset(i,j);
		updateTime();
		return true;
	}

	public void color (int c)
	{	if (c==GMPConnector.WHITE) super.color(-1);
		else super.color(1);
	}

	public void undo ()
	{	if (B.maincolor()==MyColor) return;
		C.undo();
		doundo(2);
	}

	public void doundo (int n)
	{	B.undo(n);
	}

	public void doAction (String o)	
	{	if (Global.resourceString("Remove_groups").equals(o))
		{	WantMove=false;
			B.score();
		}
		else super.doAction(o);
	}

	public void doclose ()
	{	C.doclose();
		if (Timer!=null && Timer.isAlive()) Timer.stopit();
		super.doclose();
	}

	public void alarm ()
	{	long now=System.currentTimeMillis();
		int BlackRun=BlackTime;
		int WhiteRun=WhiteTime;
		if (B.maincolor()>0)
		{	BlackRun+=(int)((now-CurrentTime)/1000);
		}
		else
		{	WhiteRun+=(int)((now-CurrentTime)/1000);
		}
		if (BigTimer)
		{	BL.setTime(WhiteRun,BlackRun,0,0,0);
			BL.repaint();
		}
	}

	public void updateTime ()
	{	long now=System.currentTimeMillis();
		if (B.maincolor()>0)
		{	BlackTime+=(int)((now-CurrentTime)/1000);
		}
		else
		{	WhiteTime+=(int)((now-CurrentTime)/1000);
		}
		CurrentTime=now;
		alarm();
	}
//** sound for Local and GMP                                       //~v106M~
	public void yourMove (boolean notinpos)                        //~v106M~
	{	if (notinpos) JagoSound.play("yourmove","stone",true);     //~v106M~
		else                                                       //~v106M~
		if (Global.getParameter("sound.everymove",true))           //~v106M~
			JagoSound.play("stone","click",true);                  //~v106M~
		else                                                       //~v106M~
			JagoSound.play("stone","click",false);                 //~v106M~
	}                                                              //~v106M~

}
