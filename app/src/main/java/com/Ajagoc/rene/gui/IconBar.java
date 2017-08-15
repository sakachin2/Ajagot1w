//*CID://+v108R~:                             update#=   14;       //+v108R~
//********************************************************************//~v105I~
//1087:121217 accept long click for icon button                    //+v108I~
//1052:121112 Iconbar2 button sensibility;RadioButton could not be scaled.//~v105I~
//            change to Button(no need to be RadioButton, status is controled by GoFrame)//~v105R~
//********************************************************************//~v105I~
package com.Ajagoc.rene.gui;

import jagoclient.Dump;                                            //~v105I~
import android.view.View;
//import android.widget.RadioButton;                               //~v105R~
import android.widget.Button;                                      //~v105I~

import com.Ajagoc.AG;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Image;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.Window;                                      //~1221I~
                                                                   //~1221I~
import rene.gui.IconBarListener;                                   //~1221I~


import jagoclient.gui.ButtonAction;
import jagoclient.gui.DoActionListener;

//import java.io.*;                                                //~1221R~
import java.util.*;                                              //~1221R~

/*@@@@                                                             //~1221I~
class SaveColor extends Color
{	public SaveColor (int red, int green, int blue)
	{	super(red>0?red:0,green>0?green:0,blue>0?blue:0);
	}
}
@@@@*/                                                             //~1221I~

/**
These are the common things to Separators and Incons.
*/

interface IconBarElement
{                                                                  //~1322R~
/*@@@@                                                             //~1322M~
 	public int width ();                                           //~1322I~
	public void setPosition (int x, int y);
	public Point getPosition ();
@@@@*/                                                             //~1321I~
	public void setEnabled (boolean flag);
	public String getName ();
}

/**
A simple separator between icons.
*/

/*@@@@                                                             //~1221I~
class Separator extends Panel 
	implements IconBarElement
{	final int Size=6;
	public Separator (IconBar bar)
	{	if (bar.Vertical)
			setSize(BasicIcon.Size,Size);
		else
			setSize(Size,BasicIcon.Size);	
	}
	public int width ()
	{	return Size;
	}
	public void setPosition (int x, int y)
	{	setLocation(x,y);
	}
	public Point getPosition () { return new Point(0,0); }
	public void setEnabled (boolean flag) {}
	public String getName () { return ""; }
	public void paint (Graphics g)
	{	g.setColor(getBackground());
		if (Global.getParameter("iconbar.showseparators",false))
			g.fill3DRect(1,1,getSize().width-1,getSize().height-1,false);
		else
			g.fillRect(1,1,getSize().width-1,getSize().height-1);
	}
}

@@@@*/                                                             //~1221I~
/**
 * @author Rene
 * This is the most basic icon, handling mouse presses
 * and display in activated, pressed, unset or disabled state.
 */
class BasicIcon extends Panel
/*@@@@
	implements MouseListener,IconBarElement,Runnable
@@@@*/
	implements               IconBarElement,DoActionListener       //~1321I~
{	IconBar Bar;
	String Name;
	boolean Enabled; // Icon cannot be changed by user action.
	boolean On; // On or off are the basic stated of icons.
	boolean Focus=false;
	public static int Size=22; // the size of icons
	boolean MouseOver,MouseDown; // for display states during mouse action
	boolean Unset; // Unknown State!
    public ButtonAction buttonAction;                              //~1321R~

	public BasicIcon (IconBar bar, String name)
	{	Bar=bar; Name=name; Enabled=true; On=false;
/*@@@@                                                             //~1321I~
		addMouseListener(this);
		enableEvents(AWTEvent.KEY_EVENT_MASK);
		setSize(Size,Size);
@@@@*/                                                             //~1321M~
		buttonAction=new ButtonAction(this,name);     //~1321M~
		buttonAction.addLongClickListener();                       //+v108I~
	}
/*@@@@                                                             //~1221M~
	public void processKeyEvent (KeyEvent e)
	{	Bar.getKey(e);
	}

@@@@*/                                                             //~1221I~
	@Override                                                      //~1321I~
	public void doAction(String Pname)                             //~1321I~
    {                                                              //~1321I~
    	Bar.L.iconPressed(Pname);                                  //~1321I~
    }                                                              //~1321I~
	/**
	Paint a button with an image
	*/
/*@@@@                                                             //~1221I~
	public void paint (Graphics g)
	{	if (MouseDown)
		{	g.setColor(getBackground());
			g.fill3DRect(0,0,Size,Size,false);
		}
		else
		{	if (MouseOver)
			{	if (On)
				{	Color c=getBackground();
					g.setColor(
						new SaveColor(c.getRed()-30,c.getGreen()-30,c.getBlue()));
				}
				else g.setColor(getBackground());
				g.fill3DRect(0,0,Size,Size,true);
			}
			else
			{	if (On)
				{	Color c=getBackground();
					g.setColor(c);
					g.fillRect(0,0,Size,Size);
					g.setColor(
						new SaveColor(c.getRed()-100,c.getGreen()-100,c.getBlue()));
					g.fillRect(3,3,Size-2,Size-2);
					g.setColor(
						new SaveColor(c.getRed()-50,c.getGreen()-50,c.getBlue()));
					g.fillRect(1,1,Size-2,Size-2);
				}
				else
				{	g.setColor(getBackground());
					g.fillRect(0,0,Size,Size);
				}
			}
		}
		dopaint(g);	
		if (Unset)
		{	Color c=getBackground();
			g.setColor(
				new SaveColor(c.getRed()-100,c.getGreen(),c.getBlue()));
			g.drawLine(0,0,Size,Size);
		}	
		if (Focus) showFocus(g);
	}
	
	public void showFocus (Graphics g)
	{	g.setColor(Color.white);
		g.drawRect(4,4,1,1);
		g.drawRect(Size-5,4,1,1);
		g.drawRect(4,Size-5,1,1);
		g.drawRect(Size-5,Size-5,1,1);
	}
	
	public void dopaint (Graphics g)
	{
	}
	
	public void update (Graphics g)
	{	paint(g);
	}
	
@@@@*/                                                             //~1221I~
	/**
	* User pressed the mouse key over this button.
	*/
/*@@@@                                                             //~1221I~
	public void mousePressed (MouseEvent e)
	{	if (!Enabled) return;
		MouseDown=true; repaint();
	}

@@@@*/                                                             //~1221I~
	/**
	* User released the mouse key again.
	*/
/*@@@@                                                             //~1221I~
	public void mouseReleased (MouseEvent e)
	{	if (!Enabled) return;
		MouseDown=false;
		Dimension d=getSize();
		if (e.getX()<0 || e.getX()>d.width ||
			e.getY()<0 || e.getY()>d.height) 
		{	repaint(); return;
		}
		Unset=false;
		pressed(e); // call method for children to change states etc.
		repaint();
		T=null; // stop icon help thread
		// Notify Iconbar about activation:
		long time=System.currentTimeMillis();
		Bar.iconPressed(Name,e.isShiftDown(),e.isControlDown());
		// Necessary, since Java 1.4 does not report
		// MouseExited, if a modal dialog is active:
		time=System.currentTimeMillis()-time;
		if (MouseOver && time>1000) 
		{	MouseOver=false;
			repaint();
		}
	}
	
@@@@*/                                                             //~1221I~
	/**
	 * Overwrite for children!
	 * @param e Mouse event for determining right button etc.
	 */
/*@@@@
	public void pressed (MouseEvent e)
	{
	}
	
	public void mouseClicked (MouseEvent e) {}
	
	Thread T;
	boolean Control;
@@@@*/	
	/**
	Start a thread, that waits for one second, then tells
	the icon bar to display the proper help text.
	*/
/*@@@@                                                             //~1221I~
	public synchronized void mouseEntered (MouseEvent e)
	{	if (T!=null) return;
		if (Enabled) MouseOver=true;
		repaint();
		if (!Global.getParameter("iconbar.showtips",true)) return;
		Control=e.isControlDown();
		T=new Thread(this);
		T.start();
	}
@@@@*/                                                             //~1221I~
	
	/**
	 * A thread to display an icon help.
	 */
/*@@@@                                                             //~1221I~
	public void run ()
	{	try
		{	Thread.sleep(1000);
		}
		catch (Exception e) {}
		if (T!=null)
		{	synchronized(this)
			{	try
				{	Point P=getLocationOnScreen();
					String help=Global.name("iconhelp."+Name,"");
					if (help.equals("") && Name.length()>1)
					{	help=Global.name("iconhelp."+
							Name.substring(0,Name.length()-1)+"?","");
					}
					if (help.equals(""))
						help=Bar.getHelp(Name);
					if (help.equals(""))
						help=Global.name("iconhelp.nohelp","No help available");
					if (Control)
					{	String hc=Global.name("iconhelp.control."+Name,"");
						if (!hc.equals("")) help=hc;
					}
					Bar.displayHelp(this,help);
				}
				catch (Exception e) {}
			}
			try
			{	Thread.sleep(5000);
			}
			catch (Exception e) {}
			if (T!=null) Bar.removeHelp();
			T=null;
		}
	}
@@@@*/                                                             //~1221I~

	/**
	Tell the run method, that display is no longer necessary,
	and remove the help text.
	*/
/*@@@@                                                             //~1221I~
	public synchronized void mouseExited (MouseEvent e)
	{	T=null;
		MouseOver=false;
		repaint();
		Bar.removeHelp();
	}
@@@@*/	                                                           //~1321R~
	// for the IconBarElement interface
	
/*@@@@	                                                           //~1322M~
	public int width ()
	{	return Size;
	}
	public void setPosition (int x, int y)
	{	setLocation(x,y);
	}
	
	public Point getPosition ()
	{	return getLocationOnScreen();
	}
@@@@*/	
	public void setEnabled (boolean flag) 
	{                                                              //~1322R~
/*@@@@                                                             //~1322I~
	 	if (Enabled==flag) return;                                 //~1322I~
		Enabled=flag;
		repaint();
@@@@*/                                                             //~1322I~
        setEnabled(buttonAction,flag);	//by component             //~1322R~
	}
	
	public String getName ()
	{	return Name;
	}
/*@@@@                                                             //~1321R~
	public boolean hasFocus () { return Focus; }
	public void setFocus (boolean flag) { Focus=flag; repaint(); }	
	
	// needs to be removed:
	
	public boolean isSet ()
	{	return !Unset;
	}	
@@@@*/                                                             //~v105I~
	public void unset (boolean flag)
	{	Unset=flag;
		repaint();
	}
	public void unset ()
	{	unset(true);
		repaint();
	}
/*@@@@                                                             //~v105I~
	
	public void setOn (boolean flag)
	{	On=flag;
		repaint();
	}
@@@@*/                                                             //~1221I~
	@Override
	public void itemAction(String o, boolean flag)
	{	
	}

}

/**
 * @author Rene
 * A primitive icon that displays a GIF image.
 */
class IconWithGif extends BasicIcon
{	Image I;
	Color C;
	int W,H,X,Y;
	/**
	* Initialize the icon and load its image.
	* By changing the global parameter "icontype", png can be used too.
	*/
	public IconWithGif (IconBar bar, String file)
	{                                                              //~1319R~
		super(bar,file);                                           //~1319I~
/*@@@@                                                             //~1319I~
		String iconfile=getDisplay(file);
		if (!iconfile.equals("")) file=iconfile;
		try
		{	InputStream in=getClass().getResourceAsStream(
				Bar.Resource+file+"."+Global.getParameter("icontype","gif"));
			int pos=0;
			int n=in.available();
			byte b[]=new byte[20000];
			while (n>0)
			{	int k=in.read(b,pos,n);
				if (k<0) break;
				pos+=k;
				n=in.available();
			}
			in.close();
			I=Toolkit.getDefaultToolkit().createImage(b,0,pos);
			MediaTracker T=new MediaTracker(bar);
			T.addImage(I,0);
			T.waitForAll();
		}
		catch (Exception e)
		{	try
			{	I=getToolkit().getImage(file+"."+Global.getParameter("icontype","gif")); 
				MediaTracker mt=new MediaTracker(this); 
				mt.addImage(I,0); 
				mt.waitForID(0); 
				if (!(mt.checkID(0) && !mt.isErrorAny()))
					throw new Exception("");
			}
			catch (Exception ex) { I=null; return;}
		}
		W=I.getWidth(this);
		H=I.getHeight(this);
		X=Size/2-W/2;
		Y=Size/2-H/2;
@@@@*/                                                             //~1319I~
	}
/*@@@@                                                             //~1319I~
	public String getDisplay (String name)
	{	if (!name.endsWith(")")) return "";
		int n=name.lastIndexOf('(');
		if (n<0) return "";
		return name.substring(n+1,name.length()-1);
	}
	
	public IconWithGif (IconBar bar, String name, Color color)
	{	super(bar,name);                                           //~1319R~
		C=color;
	}
	
	public void dopaint (Graphics g)	
	{	if (I!=null)
		{	if (W>getSize().width) g.drawImage(I,1,1,Size-2,Size-2,this);
			else g.drawImage(I,X,Y,this);
		}
		else if (C!=null)
		{	g.setColor(C);
			g.fillRect(3,3,Size-6,Size-6);
		}
		else
		{	g.setFont(new Font("Courier",Font.BOLD,Size/3));
			FontMetrics fm=getFontMetrics(getFont());
			String s=getDisplay(Name);
			if (s.length()>3) s=s.substring(0,3);
			int w=fm.stringWidth(s);
			int h=fm.getHeight();
			g.setColor(this.getForeground());
			Graphics2D G=(Graphics2D)g;
			G.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			G.drawString(s,Size/2-w/2,Size/2-h/2+fm.getAscent());
		}
	}
@@@@*/                                                             //~1321M~
	
}

/**
 * @author Rene
 * A primitive icon that displays one of several GIF images.
 */
/*@@@@                                                             //~1319M~
class MultipleIcon extends BasicIcon
{	int N;
	Image I[];
	int Selected;
	int X[],Y[],W[],H[];
	
	public MultipleIcon (IconBar bar, String name, int number)
	{	super(bar,name);
		N=number;
		I=new Image[N];
		X=new int[N];
		Y=new int[N];
		W=new int[N];
		H=new int[N];
		MediaTracker T=new MediaTracker(bar);
		try
		{	for (int i=0; i<N; i++)
			{	try
				{	InputStream in=getClass().getResourceAsStream(
						Bar.Resource+name+i+"."+Global.getParameter("icontype","gif"));
					int pos=0;
					int n=in.available();
					byte b[]=new byte[20000];
					while (n>0)
					{	int k=in.read(b,pos,n);
						if (k<0) break;
						pos+=k;
						n=in.available();
					}
					in.close();
					I[i]=Toolkit.getDefaultToolkit().createImage(b,0,pos);
					T.addImage(I[i],i);
				}
				catch (Exception e)
				{	I[i]=null;
				}
			}
			T.waitForAll();
			for (int i=0; i<N; i++)
			{	W[i]=I[i].getWidth(this);
				H[i]=I[i].getHeight(this);
				X[i]=Size/2-W[i]/2;
				Y[i]=Size/2-H[i]/2;
			}
		}
		catch (Exception e)
		{	for (int i=0; i<N; i++) I[i]=null;
		}
	}

	public MultipleIcon (IconBar bar, String name)
	{	super(bar,name);
		Selected=0;
	}
@@@@*/                                                             //~1319M~

	/**
	Paint a button with an image
	*/
/*@@@@                                                             //~1221I~
	public void dopaint (Graphics g)
	{	if (I[Selected]!=null)
		{	if (W[Selected]>getSize().width) g.drawImage(I[Selected],1,1,Size-2,Size-2,this);
			else g.drawImage(I[Selected],X[Selected],Y[Selected],this);
		}
	}
@@@@*/                                                             //~1221I~
	
	/**
	 * Go up and down the pictures.
	 */
/*@@@@                                                             //~1221I~
	public void pressed (MouseEvent e)
	{	if (e.isMetaDown())
		{	Selected--; 
			if (Selected<0) Selected=N-1;
		}
		else
		{	Selected++; 
			if (Selected>=N) Selected=0;
		}
	}

	public void setSelected (int s)
	{	if (Selected==s) return;
		Selected=s;
		repaint();
	}
	
	public int getSelected ()
	{	return Selected;
	}
}
@@@@*/                                                             //~1321M~

/**
 * @author Rene
 * An MultipleIcon that can be enabled externally.
 */
/*@@@@                                                             //~1221I~
class MultipleToggleIcon extends MultipleIcon
{	public MultipleToggleIcon (IconBar bar, String name, int number)
	{	super(bar,name,number);
	}
	public void setState (boolean flag)
	{	On=flag;
		repaint();
	}
}
@@@@*/                                                             //~1221I~

/**
 * @author Rene
 * A toggle icon for several colors.
 */
/*@@@@                                                             //~1221I~
class ColorIcon extends MultipleIcon
{	Color Colors[];
	public ColorIcon (IconBar bar, String name, Color colors[])
	{	super(bar,name);
		N=colors.length;
		Colors=colors;
	}
	public void dopaint (Graphics g)
	{	g.setColor(Colors[Selected]);
		g.fill3DRect(5,5,Size-9,Size-9,true);
	}	
}
@@@@*/                                                             //~1221I~

/**
 * One icon, which can display one color
 * @author Rene Grothmann
 */
/*@@@@                                                             //~1221I~
class ColoredIcon extends BasicIcon
{	Color C;
	public ColoredIcon (IconBar bar, String name, Color c)
	{	super(bar,name);
		C=c;
	}
	public void dopaint (Graphics g)
	{	g.setColor(C);
		g.fill3DRect(5,5,Size-9,Size-9,true);		
	}
	public Color getColor ()
	{	return C;
	}
	public void setColor (Color c)
	{	C=c;
	}
}
@@@@*/                                                             //~1221I~

/**
 * @author Rene
 * A toggle icon for several strings.
 */
/*@@@@                                                             //~1221I~
class MultipleStringIcon extends MultipleIcon
{	String S[];
	
	public MultipleStringIcon (IconBar bar, String name, String s[])
	{	super(bar,name);
		S=s;
		N=S.length;
	}
	
	public void dopaint (Graphics g)
	{	g.setColor(getForeground());
		Font font=new Font("Dialog",Font.PLAIN,Size*2/3);
		g.setFont(font);
		FontMetrics fm=getFontMetrics(font);
		int w=fm.stringWidth(S[Selected]);
		g.drawString(S[Selected],(Size-w)/2,Size-fm.getDescent());
	}
	
}
@@@@*/                                                             //~1221I~

/**
 * Button to get all icons, when there is not too much space.
 */
/*@@@@                                                             //~1221I~
class OverflowButton extends Panel
{	IconBar IB;
	boolean Left=true;
	
	public OverflowButton (IconBar ib, boolean left)
	{	IB=ib; Left=left;
		addMouseListener(new MouseAdapter()
				{	public void mouseClicked (MouseEvent e)
					{	IB.setShifted(!Left);	
					}
				}
			);
	}

	public void paint (Graphics g)
	{	int size=BasicIcon.Size;
		g.setColor(getBackground());
		g.fill3DRect(0,0,10,size,true);
		g.setColor(getForeground());
		int x[]=new int[3],y[]=new int[3];
		if (Left)
		{	x[0]=2; x[1]=x[2]=8;
			y[0]=size/2; y[1]=y[0]-6; y[2]=y[0]+6;
		}
		else
		{	x[0]=8; x[1]=x[2]=2;
			y[0]=size/2; y[1]=y[0]-6; y[2]=y[0]+6;
		}
		g.fillPolygon(x,y,3);
	}
}

class PopupIcon extends BasicIcon
{	
	public PopupIcon (IconBar bar, String name[])
	{	super(bar,name[0]);
	}
}
@@@@*/                                                             //~1221I~


/**
 * @author Rene
 * An action icon for one click.
 */
class ToggleIcon extends IconWithGif                               //~1319I~
{	boolean State;
	private IconGroup G;
//  private RadioButton radioButton;                               //~1321I~//~v105R~
    private Button radioButton;                                    //~v105R~
    private int imageIdOn,imageIdOff;                              //~v105I~

	public ToggleIcon (IconBar bar, String file, IconGroup g)
	{	super(bar,file);
		State=false; G=g;
//      radioButton=(RadioButton)buttonAction.androidButton;//@@@@ //~1322R~//~v105R~
        radioButton=(Button)buttonAction.androidButton;//@@@@      //~v105R~
//  	setState(State);	//@@@@                                 //~1322I~//~v105R~
		int ids[]=new int[2];                                      //~v105R~
		if (com.Ajagoc.awt.Button.getToggleButtonDrawableId(radioButton,ids))//~v105R~
        {                                                          //~v105I~
        	imageIdOn=ids[0];                                      //~v105I~
            imageIdOff=ids[1];                                     //~v105I~
        }                                                          //~v105I~
	}
/*@@@@
	public ToggleIcon (IconBar bar, String file, Color c, IconGroup g)
	{	super(bar,file,c);
		State=false; G=g;
	}
@@@@*/
	public ToggleIcon (IconBar bar, String file)
	{	this(bar,file,null);
	}
/*@@@@                                                             //~1319I~
	public void pressed (MouseEvent e)
	{	setState(!On);
	}
@@@@*/                                                             //~1321I~
	public boolean getState ()                                     //~1321R~
	{                                                              //~1321I~
///*@@@@                                                             //~1321I~//~v105R~
		return State;                                              //~1321I~
//@@@@*/                                                             //~1321I~//~v105R~
//    	return radioButton.isChecked();                            //~1322R~//~v105R~
    }                                                              //~1321I~
	
	public void setState (boolean state)
	{                                                              //~1321R~
///*@@@@                                                             //~1321I~//~v105R~
		if (G!=null) G.toggle(this);                               //~1321I~
		else
		{	if (On==state) { State=state; return; }
			On=State=state;
			repaint();
		}
//@@@@*/                                                             //~1321I~//~v105R~
//    	setChecked(radioButton,state);   //by Component            //~1322R~//~v105R~
	}
/*@@@@	                                                           //~1321R~
	public void unselect ()
	{	if (G!=null) G.unselect();
	}
@@@@*/                                                             //~v105R~
	public void setStateInGroup (boolean state)
	{                                                              //~v105R~
		if (Dump.Y) Dump.println("setStateInGroup Name="+Name+",Oldstate="+State+"OldOn="+On+"NewState="+state);//~v105I~
		if (On==state) { State=state; return; }                    //~v105I~
		On=State=state;
		repaint();
        setChecked(radioButton,On?imageIdOn:imageIdOff);//by component//~v105R~
	}
/*@@@@                                                             //~v105R~
	public int countPeers ()
	{	if (G==null) return 0;
		return G.getN();
	}
	
	public void unset ()
	{	if (G!=null) G.unset(true);
		else super.unset();
	}	
@@@@*/                                                             //~v105I~
	public void dounset (boolean flag)
	{	super.unset(flag);
	}
/*@@@@                                                             //~v105I~
	public void set ()
	{	if (G!=null) G.unset(false);
		else super.unset(false);
	}	
	public void doset ()
	{	super.unset(false);
	}
@@@@*/                                                             //~1319I~
}

/**
 * @author Rene
 * An icon to display on/off state.
 */
/*@@@@                                                             //~1221I~
class OnOffIcon extends ToggleIcon
{	static int LampSize=4;
	public OnOffIcon (IconBar bar, String file)
	{	super(bar,file,null);
	}
	public void pressed (MouseEvent e)
	{	State=On=!On;
	}
}
@@@@*/                                                             //~1221I~

/**
This class can add several ToggleItems and will enable only one
of them.
*/

class IconGroup
{	String Files[],Breaks[];
	IconBar Bar;
	int N;
	ToggleIcon Icons[];
	public IconGroup (IconBar bar, String files[], String breaks[])
	{	Files=files; Breaks=breaks; Bar=bar;
		init();
	}
	public IconGroup (IconBar bar, String files[])
	{	this(bar,files,files);
	}
	public void init ()
	{	N=0;
		for (int i=0; i<Files.length; i++)
			if (!Files[i].equals("")) N++;
		Icons=new ToggleIcon[N];
		int k=0;
		for (int i=0; i<Files.length; i++)
		{	if (!Files[i].equals(""))
			{	Icons[k++]=new ToggleIcon(Bar,Files[i],this);
			}
		}		
	}
/*@@@@                                                             //~1322M~
	public IconGroup (IconBar bar, String name, int n)
	{	Breaks=Files=new String[n]; 
		for (int i=0; i<n; i++)
		{	Files[i]=name+i;
		}
		Bar=bar;
		init();
	}
	public IconGroup (IconBar bar, String name, Color colors[])
	{	N=colors.length;
		Breaks=Files=new String[N];
		for (int i=0; i<N; i++)
		{	Files[i]=name+i;
		}
		Bar=bar;
		Icons=new ToggleIcon[N];
		for (int i=0; i<N; i++)
		{	Icons[i]=new ToggleIcon(Bar,Files[i],colors[i],this);
		}		
	}
@@@@*/
	public void addLeft ()
	{                                                              //~1306R~
		int i=0;                                                   //~1306I~
		for (int k=0; k<Files.length; k++)
			if (Files[k].equals("")) Bar.addSeparatorLeft();
			else
			{	if (Breaks[k].startsWith("!")) Bar.addSeparatorLeft();
				Bar.addLeft(Icons[i++]);
			}
	}
/*@@@@                                                             //~1319I~
	public void addRight ()
	{	int i=0;
		for (int k=0; k<Files.length; k++)
			if (Files[k].equals("")) Bar.addSeparatorRight();
			else 
			{	if (Breaks[k].startsWith("!")) Bar.addSeparatorRight();
				Bar.addRight(Icons[i++]);
			}
	}
@@@@*/                                                             //~v105I~
	public void toggle (ToggleIcon icon)
	{	for (int i=0; i<N; i++)
		{	if (Icons[i]==icon) icon.setStateInGroup(true);
			else Icons[i].setStateInGroup(false);
			Icons[i].unset(false);
		}
	}
/*@@@@                                                             //~v105I~
	public void unselect ()
	{	for (int i=0; i<N; i++)
		{	Icons[i].setStateInGroup(false);
			Icons[i].unset(false);
		}
	}
	public int getN () { return N; }
@@@@*/                                                             //~v105I~
	public void unset (boolean flag)
	{	for (int i=0; i<N; i++)
		{	Icons[i].dounset(flag);
		}
	}
/*@@@@                                                             //~v105I~
@@@@*/                                                             //~1319I~
}

/**
An state display. Loads two images from a resource and display either
of them, depending on the enabled state.
*/

/*@@@@                                                             //~1221I~
class StateDisplay extends BasicIcon
{	Image IOn,IOff;
	int W,H,X,Y;
@@@@*/	                                                           //~1321R~
	/**
	Initialize the icon and load its image.
	*/
/*@@@@
	public StateDisplay (IconBar bar, String file)
	{	super(bar,file);
		try
		{	InputStream in=getClass().getResourceAsStream(
				Bar.Resource+file+"on"+"."+Global.getParameter("icontype","gif"));
			int pos=0;
			int n=in.available();
			byte b[]=new byte[20000];
			while (n>0)
			{	int k=in.read(b,pos,n);
				if (k<0) break;
				pos+=k;
				n=in.available();
			}
			in.close();
			IOn=Toolkit.getDefaultToolkit().createImage(b,0,pos);
			MediaTracker T=new MediaTracker(bar);
			T.addImage(IOn,0);
			in=getClass().getResourceAsStream(
				Bar.Resource+file+"off"+"."+Global.getParameter("icontype","gif"));
			pos=0;
			n=in.available();
			byte b1[]=new byte[20000];
			while (n>0)
			{	int k=in.read(b1,pos,n);
				if (k<0) break;
				pos+=k;
				n=in.available();
			}
			in.close();
			IOff=Toolkit.getDefaultToolkit().createImage(b1,0,pos);
			T.addImage(IOff,1);
			T.waitForAll();
			W=IOn.getWidth(this);
			H=IOn.getHeight(this);
			if (Bar.Vertical) X=Size/2-W/2;
			else X=0;
			Y=Size/2-H/2;
		}
		catch (Exception e)
		{	IOn=IOff=null;
		}
	}
@@@@*/
	/**
	Paint a button with an image
	*/
/*@@@@
	public void paint (Graphics g)
	{	if (Enabled && IOn!=null)
		{	if (W>getSize().width) g.drawImage(IOn,1,1,Size-2,Size-2,this);
			else g.drawImage(IOn,X,Y,this);
		}
		else if (!Enabled && IOff!=null)
		{	if (W>getSize().width) g.drawImage(IOff,1,1,Size-2,Size-2,this);
			else g.drawImage(IOff,X,Y,this);
		}
	}

	public void mousePressed (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) { T=null; }
	public void mouseClicked (MouseEvent e) {}	
}
@@@@*/                                                             //~1221I~

/**
This panel displays icons and reacts on mouse actions.
It can also interpret key strokes to traverse the icons.
*/

public class IconBar extends Panel
/*@@@@                                                             //~1319I~
	implements KeyListener, FocusListener, IconBarListener
@@@@*/                                                             //~1319I~
{                                                                  //~1319R~
 	Vector Left=new Vector(),Right=new Vector();                   //~1319I~
	int W;
	Window F;
	public final int Offset=2;
	public String Resource="/";
	int Focus=0;
	public boolean TraverseFocus=true;
	public boolean UseSize=true;
	public boolean Vertical=false;
//  private View androidView,androidView1,androidView2;            //~1324R~//~v105R~
	
	public IconBar (Window f, boolean traversefocus)
	{                                                              //~1221R~
//  	androidView=AG.findViewById(AG.viewId_IconBar);            //~1322I~//~v105R~
//  	androidView1=AG.findViewById(AG.viewId_IconBar1);          //~1324I~//~v105R~
//  	androidView2=AG.findViewById(AG.viewId_IconBar2);          //~1324I~//~v105R~
		F=f; TraverseFocus=traversefocus;                          //~1221I~
/*@@@@                                                             //~1322M~//~1324M~
		if (Global.ControlBackground!=null)
			setBackgroundIB(Global.ControlBackground);
		else setBackgroundIB(SystemColor.menu);
		Resource=Global.getParameter("iconpath","");
		BasicIcon.Size=Global.getParameter("iconsize",20);
		setLayout(null);
		W=Offset*2;                                                             //~1321I~
		addKeyListener(this);
		if (TraverseFocus) addFocusListener(this);
@@@@*/                                                             //~1321I~
	}
	public IconBar (Window f)
	{	this(f,true);
	}
	/**
	 * Do not know, if this is necessary. But sometimes the icons do not
	 * repaint after an update.
	 */
/*@@@@                                                             //~1221I~
	public void forceRepaint ()
	{	super.repaint();
		Enumeration e=Left.elements();
		while (e.hasMoreElements())
		{	BasicIcon i=(BasicIcon)e.nextElement();
			i.repaint();
		}
		e=Right.elements();
		while (e.hasMoreElements())
		{	BasicIcon i=(BasicIcon)e.nextElement();
			i.repaint();
		}
	}
@@@@*/                                                             //~1221I~
/*@@@@                                                             //~1319M~

	public void keyPressed (KeyEvent e) {}
	public void keyReleased (KeyEvent e)
	{                                                              //~1221R~
		switch (e.getKeyCode())                                    //~1221I~
		{	case KeyEvent.VK_RIGHT :
				setFocus(Focus,false);
				Focus++;
				if (Focus>=Left.size()+Right.size()) Focus=0;
				while (!(getIcon(Focus) instanceof BasicIcon))
				{	Focus++;
					if (Focus>=Left.size()+Right.size())
					{	Focus=0; break;
					}
				}
				setFocus(Focus,true);
				break;
			case KeyEvent.VK_LEFT :
				setFocus(Focus,false);
				Focus--;
				if (Focus<0) Focus=Left.size()+Right.size()-1;
				while (!(getIcon(Focus) instanceof BasicIcon))
				{	Focus--;
					if (Focus<0)
					{	Focus=Left.size()+Right.size()-1;
						break;
					}
				}
				setFocus(Focus,true);
				break;
			case KeyEvent.VK_SPACE :
				try
				{	BasicIcon icon=(BasicIcon)getIcon(Focus);
					icon.mouseReleased(new MouseEvent(this,
						MouseEvent.MOUSE_RELEASED,0,0,0,0,1,false));
				}
				catch (Exception ex) {}
				break;
		}
	}
@@@@*/                                                             //~1319M~
/*@@@@                                                             //~1319I~
	public void keyTyped (KeyEvent e) {}
@@@@*/                                                             //~1319I~
	
	/*
	public boolean isFocusTraversable ()
	{	return TraverseFocus;
	}
	*/
	
/*@@@@                                                             //~1221I~
	public Object getIcon (int n)
	{	if (n<Left.size()) return Left.elementAt(n);
		else return Right.elementAt(Right.size()-1-(n-Left.size()));
	}
@@@@*/	                                                           //~1221R~
/*@@@@                                                             //~1319M~
	public void focusGained (FocusEvent e)
	{                                                              //~1221R~
		if (TraverseFocus) setFocus(Focus,true);                   //~1221I~
	}
@@@@*/                                                             //~1319M~
/*@@@@                                                             //~1319M~
	public void focusLost (FocusEvent e)
	{                                                              //~1221R~
		if (TraverseFocus) setFocus(Focus,false);                  //~1221I~
	}
@@@@*/                                                             //~1319M~
		
/*@@@@                                                             //~1221I~
	public void setFocus (int n, boolean flag)
	{	if (!TraverseFocus) return;
		try
		{	if (n<Left.size())
			{	BasicIcon icon=(BasicIcon)Left.elementAt(n);
				icon.setFocus(flag);
			}
			else
			{	BasicIcon icon=(BasicIcon)Right.elementAt(
					Right.size()-1-(n-Left.size()));
				icon.setFocus(flag);
			}
		}
		catch (Exception e) {}
	}
@@@@*/                                                             //~1221I~
	
	/**
	Add an icon
	*/
	public void addLeft (String name)
	{                                                              //~1306R~
		addLeft(new IconWithGif(this,name));                       //~1306I~
	}
	public void addLeft (BasicIcon i)
	{                                                              //~1221R~
		Left.addElement(i);                                        //~1221I~
		add(i);
/*@@@@
		W+=i.width()+Offset;
@@@@*/
	}
	/**
	Add an icon at the right end
	*/
/*@@@@
	public void addRight (String name)
	{	addRight(new IconWithGif(this,name));
	}
	public void addRight (BasicIcon i)
	{	Right.addElement(i);
		add(i);
		W+=i.width()+Offset;
	}
@@@@*/
	/**
	Add a toggle icon
	*/
/*@@@@                                                             //~1221I~
	public void addToggleLeft (String name)
	{	addLeft(new ToggleIcon(this,name));
	}
	public void addToggleRight (String name)
	{	addRight(new ToggleIcon(this,name));
	}
@@@@*/                                                             //~1221I~
	
	/**
	Add a toggle icon
	*/
/*@@@@                                                             //~1221I~
	public void addOnOffLeft (String name)
	{	addLeft(new OnOffIcon(this,name));
	}
	public void addOnOffRight (String name)
	{	addRight(new OnOffIcon(this,name));
	}
@@@@*/                                                             //~1221I~
	
	/**
	Add a complete groupe of toggle items.
	*/
	public void addToggleGroupLeft (String names[], String breaks[])
	{                                                              //~1221R~
		IconGroup g=new IconGroup(this,names,breaks);              //~1221I~
		g.addLeft();
	}
/*@@@@                                                             //~1321M~
	public void addToggleGroupRight (String names[], String breaks[])
	{	IconGroup g=new IconGroup(this,names,breaks);
		g.addRight();
	}
@@@@*/                                                             //~1321I~
	public void addToggleGroupLeft (String names[])
	{                                                              //~1221R~
		addToggleGroupLeft(names,names);                           //~1221I~
	}
/*@@@@                                                             //~1221I~
	public void addToggleGroupRight (String names[])
	{	addToggleGroupRight(names,names);
	}
	public void addToggleGroupLeft (String name, int n)
	{	IconGroup g=new IconGroup(this,name,n);
		g.addLeft();
	}
	public void addToggleGroupRight (String name, int n)
	{	IconGroup g=new IconGroup(this,name,n);
		g.addRight();
	}
	public void addToggleGroupLeft (String name, Color colors[])
	{	IconGroup g=new IconGroup(this,name,colors);
		g.addLeft();
	}
	public void addToggleGroupRight (String name, Color colors[])
	{	IconGroup g=new IconGroup(this,name,colors);
		g.addRight();
	}
@@@@*/                                                             //~1221I~

	/**
	Add a separator
	*/
	public void addSeparatorLeft ()
	{                                                              //~1221R~
/*@@@@                                                             //~1321M~
		if (Left.size()==0) return;                                //~1221I~
		if (Left.lastElement() instanceof Separator) return;
		Separator s=new Separator(this);
		Left.addElement(s);
		add(s);
		W+=s.width()+Offset;
@@@@*/                                                             //~1321M~
	}
/*@@@@                                                             //~1221M~
	public void addSeparatorRight ()
	{	if (Right.size()==0) return;
		if (Right.lastElement() instanceof Separator) return;
		Separator s=new Separator(this);
		Right.addElement(s);
		add(s);
		W+=s.width()+Offset;
	}
@@@@*/                                                             //~1221I~

	/**
	Add a multiple icon (can toggle between the icons)
	*/
/*@@@@                                                             //~1221I~
	public void addMultipleIconLeft (String name, int number)
	{	addLeft(new MultipleIcon(this,name,number));
	}
	public void addMultipleIconRight (String name, int number)
	{	addRight(new MultipleIcon(this,name,number));
	}
@@@@*/                                                             //~1221I~
	
	/**
	Add a multiple icon (can toggle between the icons)
	*/
/*@@@@                                                             //~1221I~
	public void addMultipleStringIconLeft (String name, String s[])
	{	addLeft(new MultipleStringIcon(this,name,s));
	}
	public void addMultipleStringIconRight (String name, String s[])
	{	addRight(new MultipleStringIcon(this,name,s));
	}
@@@@*/                                                             //~1221I~
	
	/**
	Add a multiple icon (can toggle between the icons)
	*/
/*@@@@                                                             //~1221I~
	public void addMultipleToggleIconLeft (String name, int number)
	{	addLeft(new MultipleToggleIcon(this,name,number));
	}
	public void addMultipleToggleIconRight (String name, int number)
	{	addRight(new MultipleToggleIcon(this,name,number));
	}
@@@@*/                                                             //~1221I~
	
	/**
	Add a multiple icon (can toggle between the colors)
	*/
/*@@@@                                                             //~1221I~
	public void addColorIconLeft (String name, Color colors[])
	{	addLeft(new ColorIcon(this,name,colors));
	}
	public void addColorIconRight (String name, Color colors[])
	{	addRight(new ColorIcon(this,name,colors));
	}
@@@@*/                                                             //~1221I~

	/**
	Add a colored icon
	*/
/*@@@@                                                             //~1221I~
	public void addColoredIconLeft (String name, Color c)
	{	addLeft(new ColoredIcon(this,name,c));
	}
	public void addColoredIconRight (String name, Color c)
	{	addRight(new ColoredIcon(this,name,c));
	}
@@@@*/                                                             //~1221I~

	/**
	Add a state display at the left end.
	*/
/*@@@@                                                             //~1221I~
	public void addStateLeft (String name)
	{	addLeft(new StateDisplay(this,name));
	}
	public void addStateRight (String name)
	{	addRight(new StateDisplay(this,name));
	}
	
	boolean Overflow=false,Shifted=false;
	OverflowButton OB;
	int OverflowX;
	
@@@@*/                                                             //~1221I~
	/**
	Override the layout and arrange the icons from the
	left and the right.
	*/
/*@@@@                                                             //~1221I~
	public void doLayout ()
	{	if (OB!=null)
		{	remove(OB); OB=null;
		}
		if (Vertical)	
		{	int x;
			x=getSize().height;
			for (int k=0; k<Right.size(); k++)
			{	IconBarElement i=(IconBarElement)Right.elementAt(k);
				x-=i.width();
				i.setPosition(2,x);
				x-=Offset;
			}
			int xmax=x;
			x=0;
			for (int k=0; k<Left.size(); k++)
			{	IconBarElement i=(IconBarElement)Left.elementAt(k);
				i.setPosition(2,x);
				x+=i.width();
				x+=Offset;
				if (x+IconWithGif.Size>xmax) x=-1000;
			}
		}
		else
		{	int x;
			x=getSize().width;
			for (int k=0; k<Right.size(); k++)
			{	IconBarElement i=(IconBarElement)Right.elementAt(k);
				x-=i.width();
				i.setPosition(x,2);
				x-=Offset;
			}
			int xmax=x;
			x=0;
			for (int k=0; k<Left.size(); k++)
			{	IconBarElement i=(IconBarElement)Left.elementAt(k);
				i.setPosition(x,2);
				x+=i.width();
				x+=Offset;
				if (x+IconWithGif.Size>xmax-10 && k<Left.size()-1)
				{	Overflow=true; OverflowX=x;
					OB=new OverflowButton(this,Shifted);
					add(OB);
					OB.setSize(10,BasicIcon.Size);
					OB.setLocation(xmax-10-Offset,2);
					if (!Shifted)
					{	x=-1000;	
					}
					else
					{	x=xmax-10-2*Offset;
						for (int l=Left.size()-1; l>=0; l--)
						{	i=(IconBarElement)Left.elementAt(l);
							x-=i.width();
							i.setPosition(x,2);
							x-=Offset;
							if (x-IconWithGif.Size<0) x-=1000;
						}
						break;
					}
				}
			}
		}
	}
	
	public void setShifted (boolean flag)
	{	Shifted=flag;
		doLayout();
	}
@@@@*/                                                             //~1221I~
	
	/**
	Override the preferred sizes.
	*/
/*@@@@                                                             //~1221I~
	public Dimension getPreferredSize ()
	{	if (Vertical)
		{	if (!UseSize) return new Dimension(IconWithGif.Size+4,10);
			return new Dimension(IconWithGif.Size+4,W+10);
		}
		else
		{	if (!UseSize) return new Dimension(10,IconWithGif.Size+4);
			return new Dimension(W+10,IconWithGif.Size+4);
		}
	}
	public Dimension getMinimumSize ()
	{	return getPreferredSize();
	}
	
	// The IconBar can notify one IconBarListener on icon
	// clicks.
	
@@@@*/                                                             //~1221M~
	IconBarListener L=null;
	                                                               //~1221R~
	public void setIconBarListener (IconBarListener l)
	{                                                              //~1221R~
		L=l;                                                       //~1221I~
	}
	
/*@@@@                                                             //~1221I~
	public void removeIconBarListener (IconBarListener l)
	{	L=null;
	}
	
	boolean Shift,Control;
	
	public void iconPressed (String name, boolean shift, boolean control)
	{	Shift=shift; Control=control;
		removeHelp();
		if (L!=null) L.iconPressed(name);
	}
	
	public boolean isShiftPressed () { return Shift; }
	public boolean isControlPressed () { return Control; }
	public void clearShiftControl () { Shift=Control=false; }
	
	// The tool tip help, initiated by the icons.
	
	Window WHelp=null;
	
	public synchronized void displayHelp (IconBarElement i, String text)
	{	if (F==null || WHelp!=null) return;
		Point P=i.getPosition();
		WHelp=new Window(F);
		Panel p=new Panel();
		StringTokenizer t=new StringTokenizer(text,"+");
		p.setLayout(new GridLayout(0,1));
		while (t.hasMoreTokens())
		{	p.add(new MyLabel(t.nextToken()));
		}
		WHelp.add("Center",p);
		WHelp.pack();
		Dimension d=WHelp.getSize();
		Dimension ds=getToolkit().getScreenSize();
		int x=P.x,y=P.y+i.width()+10;
		if (x+d.width>ds.width) x=ds.width-d.width;
		if (y+d.height>ds.height) y=P.y-i.width()-d.height;
		WHelp.setLocation(x,y);
		WHelp.setBackground(new Color(255,255,220));
		WHelp.setForeground(Color.black);
		WHelp.setVisible(true);
	}
	
	public synchronized void removeHelp ()
	{	if (WHelp==null) return;
		WHelp.setVisible(false);
		WHelp.dispose();
		WHelp=null;
	}
@@@@*/                                                             //~1321M~
	
	private BasicIcon find (String name)
	{	int k;
		for (k=0; k<Left.size(); k++)
		{	try
			{	BasicIcon i=(BasicIcon)Left.elementAt(k);
				if (i.getName().equals(name)) return i;
			}
			catch (Exception e) {}
		}
		for (k=0; k<Right.size(); k++)
		{	try
			{	BasicIcon i=(BasicIcon)Right.elementAt(k);
				if (i.getName().equals(name)) return i;
			}
			catch (Exception e) {}
		}
		return null;
	}

	/**
	Enable the tool with the specified name.
	*/
	public void setEnabled (String name, boolean flag)
	{	BasicIcon icon=find(name);
		if (icon==null) return;
		icon.setEnabled(flag);
	}

	/**
	Select
	*/
/*@@@@                                                             //~1221I~
	public void toggle (String name)
	{	BasicIcon icon=find(name);
		if (icon==null) return;
		if (icon instanceof ToggleIcon) ((ToggleIcon)icon).setState(true);
	}
@@@@*/                                                             //~1221I~
	
	/**
	Have an Icon?
	*/
/*@@@@                                                             //~1221I~
	public boolean have (String name)
	{	return find(name)!=null;
	}
@@@@*/                                                             //~1221I~
	
	/**
	Deselect all icons in the group of an icon
	*/
/*@@@@                                                             //~1221I~
	public void unselect (String name)
	{	BasicIcon icon=find(name);
		if (icon==null) return;
		if (icon instanceof ToggleIcon) ((ToggleIcon)icon).unselect();
	}
@@@@*/                                                             //~1221I~
	
	/**
	Toggle an item of an item group (known by name and number).
	*/
/*@@@@                                                             //~1221I~
	public void toggle (String name, int n)
	{	toggle(name+n);
	}
@@@@*/                                                             //~1221I~
	
	/**
	 * Set the state of an icon
	*/
/*@@@@                                                             //~1221I~
	public void set (String name, boolean flag)
	{	BasicIcon icon=find(name);
		if (icon==null) return;
		icon.setOn(flag);
	}
@@@@*/                                                             //~1221I~
	
	/**
	Set the state of a single toggle icon.
	*/
	public void setState (String name, boolean flag)
	{	BasicIcon icon=find(name);
		if (icon!=null && (icon instanceof ToggleIcon))
			((ToggleIcon)icon).setState(flag);
/*@@@@                                                             //~1321I~
		if (icon!=null && (icon instanceof MultipleToggleIcon))
			((MultipleToggleIcon)icon).setState(flag);
@@@@*/                                                             //~1321I~
	}

	/**
	Get the state of the specified toggle icon
	*/
/*@@@@                                                             //~1221I~
	public boolean getState (String name)
	{	BasicIcon icon=find(name);
		if (icon==null || !(icon instanceof ToggleIcon)) return false;
		return ((ToggleIcon)icon).getState();
	}
@@@@*/                                                             //~1221I~
	
	/**
	Return the state of a toggle icon.
	*/
/*@@@@                                                             //~1221I~
	public int getToggleState (String name)
	{	BasicIcon icon=find(name+0);
		if (icon==null || !(icon instanceof ToggleIcon)) return -1;
		int n=((ToggleIcon)icon).countPeers();
		for (int i=0; i<n; i++)
		{	if (getState(name+i)) return i;
		}
		return -1;
	}

	public void setColoredIcon (String name, Color c)
	{	BasicIcon icon=find(name);
		if (icon==null || !(icon instanceof ColoredIcon)) return;
		((ColoredIcon)icon).setColor(c);
	}

	public Color getColoredIcon (String name)
	{	BasicIcon icon=find(name);
		if (icon==null || !(icon instanceof ColoredIcon)) return Color.black;
		return ((ColoredIcon)icon).getColor();
	}
@@@@*/                                                             //~1221I~

	/**
	Get the state of the specified multiple icon
	*/
/*@@@@                                                             //~1221I~
	public int getMultipleState (String name)
	{	int k;
		for (k=0; k<Left.size(); k++)
		{	IconBarElement i=(IconBarElement)Left.elementAt(k);
			if (i.getName().equals(name) && i instanceof MultipleIcon)
			{	return ((MultipleIcon)i).getSelected();
			}
		}
		for (k=0; k<Right.size(); k++)
		{	IconBarElement i=(IconBarElement)Right.elementAt(k);
			if (i.getName().equals(name) && i instanceof MultipleIcon)
			{	return ((MultipleIcon)i).getSelected();
			}
		}
		return -1;
	}
@@@@*/                                                             //~1221I~

	/**
	Set the state of the specified multiple icon
	*/
/*@@@@                                                             //~1221I~
	public void setMultipleState (String name, int state)
	{	int k;
		for (k=0; k<Left.size(); k++)
		{	IconBarElement i=(IconBarElement)Left.elementAt(k);
			if (i.getName().equals(name) && i instanceof MultipleIcon)
			{	((MultipleIcon)i).setSelected(state);
			}
		}
		for (k=0; k<Right.size(); k++)
		{	IconBarElement i=(IconBarElement)Right.elementAt(k);
			if (i.getName().equals(name) && i instanceof MultipleIcon)
			{	((MultipleIcon)i).setSelected(state);
			}
		}
	}
@@@@*/                                                             //~1221I~

	/**
	See, if the specific icon has been set.
	*/
/*@@@@                                                             //~1221I~
	public boolean isSet (String name)
	{	BasicIcon icon=find(name);
		if (icon==null) return false;
		return icon.isSet();
	}
	
@@@@*/                                                             //~1221I~
	/**
	Set the specific icon to unset.
	*/
/*@@@@                                                             //~1221I~
	public void unset (String name)
	{	BasicIcon icon=find(name);
		if (icon!=null) icon.unset();
	}
	
	public void getKey (KeyEvent e)
	{	processKeyEvent(e);
	}
	
	public void setSize (int size)
	{	BasicIcon.Size=size;
	}
	
	public void removeAll ()
	{	Enumeration e=Left.elements();
		while (e.hasMoreElements())
		{	remove((BasicIcon)e.nextElement());
		}
		e=Right.elements();
		while (e.hasMoreElements())
		{	remove((BasicIcon)e.nextElement());
		}
		Left.removeAllElements();
		Right.removeAllElements();
	}
@@@@*/                                                             //~1221I~
	
	/**
	 * Overwrite in children!
	 * @param name
	 * @return Help text
	 */
/*@@@@                                                             //~1221I~
	public String getHelp (String name)
	{	return "";
	}
	
	public static void main (String args[])
	{	CloseFrame f=new CloseFrame("Test");
		IconBar IA=new IconBar(f);
		IA.Vertical=true;
		IA.setSize(30);
		IA.Resource="/icons/"; 
		IA.addLeft("back"); 
		IA.addLeft("undo"); 
		IA.addSeparatorLeft(); 
		IA.addOnOffLeft("grid"); 
		IA.addSeparatorLeft(); 
		IA.addToggleLeft("delete"); 
		IA.addSeparatorLeft();
		String tg[]={"zoom","draw","","rename","edit"}; 
		IA.addToggleGroupLeft(tg); 
		IA.addSeparatorLeft(); 
		IA.addMultipleToggleIconLeft("macro",3); 
		IA.addSeparatorLeft(); 
		String tga[]={"zoom","draw","rename","edit"}; 
		IA.addLeft(new PopupIcon(IA,tga));
		IA.addSeparatorLeft(); 
		String st[]={"A","B","C","D"}; 
		IA.addMultipleStringIconLeft("strings",st); 
		Color col[]={Color.BLACK,Color.RED,Color.GREEN};
		IA.addStateLeft("needsave");
		IA.addColorIconLeft("color",col); 
		f.add("Center",new IconBarPanel(
			new Panel3D(IA),new Panel3D(new Panel())));
		f.pack();	
		f.center();
		IA.setIconBarListener(IA);
		f.setVisible(true);
	}

@@@@*/                                                             //~1221M~
/*@@@@                                                             //~1321I~
	public void iconPressed(String name)
	{	if (Dump.Y) Dump.println(name);                            //~1506R~
	}
@@@@*/                                                             //~1321R~
}
