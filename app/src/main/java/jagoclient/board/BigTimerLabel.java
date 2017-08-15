//*CID://+dateR~:                             update#=    2;       //~3430I~
//***********************************************************************//~3430I~
//1B0h 130430 (Bug)bigtimer spacing                                //~3430I~
//***********************************************************************//~3430I~
package jagoclient.board;

import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.FontMetrics;
import com.Ajagoc.awt.Graphics;

import jagoclient.Global;
import jagoclient.gui.BigLabel;

//import java.awt.Color;
//import java.awt.FontMetrics;
//import java.awt.Graphics;

public class BigTimerLabel extends BigLabel
{	int White=0,Black=0,Col=0,MWhite=-1,MBlack=-1;
	FontMetrics ctrFM;                                             //~3430I~
	public BigTimerLabel ()
	{	super(Global.BigMonospaced);
		ctrFM=getFontMetrics(Global.Monospaced);                   //~3430I~
	}
	static char a[]=new char[32];
	public void drawString (Graphics g, int x, int y, FontMetrics fm)
	{	int delta=fm.charWidth('m')/4;
		if (Global.BigMonospaced!=null) g.setFont(Global.BigMonospaced);
		if (White<0) g.setColor(Color.blue);
		else if (White<30 && Col<0) g.setColor(Color.red);
		else if (White<60 && Col<0) g.setColor(Color.red.darker());
		else if (Col<0) g.setColor(Color.green.darker());
		else g.setColor(Color.black);
		int n=OutputFormatter.formtime(a,White);
		g.drawChars(a,0,n,x,y);
		x+=fm.charsWidth(a,0,n)+delta;
		g.setFont(Global.Monospaced);
		if (MWhite>=0)
		{	a[0]=(char)('0'+(MWhite%100)/10);
			a[1]=(char)('0'+MWhite%10);
		}
		else a[0]=a[1]=' ';
		g.setColor(Color.black);
		g.drawChars(a,0,2,x,y);
//  	x+=fm.charsWidth(a,0,2)+delta;                             //~3430R~
    	x+=ctrFM.charsWidth(a,0,2)+delta+delta;                    //+3430R~
		if (Global.BigMonospaced!=null) g.setFont(Global.BigMonospaced);
		if (Black<0) g.setColor(Color.blue);
		else if (Black<30 && Col>0) g.setColor(Color.red);
		else if (Black<60 && Col>0) g.setColor(Color.red.darker());
		else if (Col>0) g.setColor(Color.green.darker());
		else g.setColor(Color.black);
		n=OutputFormatter.formtime(a,Black);
		g.drawChars(a,0,n,x,y);
		x+=fm.charsWidth(a,0,n)+delta;
		g.setFont(Global.Monospaced);
		if (MBlack>=0)
		{	a[0]=(char)('0'+(MBlack%100)/10);
			a[1]=(char)('0'+MBlack%10);
		}
		else a[0]=a[1]=' ';
		g.setColor(Color.black);
		g.drawChars(a,0,2,x,y);
		if (Global.BigMonospaced!=null) g.setFont(Global.BigMonospaced);
	}
	public void setTime (int w, int b, int mw, int mb, int col)
	{	White=w; Black=b; MWhite=mw; MBlack=mb; Col=col;
	}
}
