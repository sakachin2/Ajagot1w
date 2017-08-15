//*CID://+v106R~:                             update#=    5;       //~v106I~
//*************************************************************************//~v106I~
//1062:121121 Warning:MediaPlayer finalized without being released //~v106I~
//*************************************************************************//~v106I~
package com.Ajagoc.rene.util.sound;

//import java.awt.BorderLayout;
//import java.awt.Button;
//import java.awt.Panel;
//import java.awt.Toolkit;

import jagoclient.Dump;

import com.Ajagoc.AG;
import com.Ajagoc.awt.Toolkit;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
/*@@@@
class SoundElement extends ListElement
{	Sound S;
	public SoundElement (String name)
	{	super(name);
		S=null;
		S=new Sound13(name);
	}
	public String name ()
	{	return (String)content();
	}
	public void play ()
	{	S.start();
	}
}
@@@@*/
/**
This is a Sound class to play and store sounds from resources. The class
keeps a list of loaded sounds.
*/

/*@@@@                                                             //~1327I~
public class SoundList implements Runnable
@@@@*/                                                             //~1327I~
public class SoundList implements MediaPlayer.OnCompletionListener //~1327I~
{                                                                  //~1327R~
/*@@@@                                                             //~1327I~
	ListClass SL;                                                  //~1327I~
	Thread T;
@@@@*/                                                             //~1327I~
	boolean Busy;

	@SuppressLint("ParserError")
	private static int playCtr,releaseCtr;                                 //~v106R~
                                                          //~1327I~
	String Name,Queued;
                                                            //~1327I~
	
	public SoundList ()
	{                                                              //~1327R~
/*@@@@                                                             //~1327I~
		SL=new ListClass();                                        //~1327I~
		T=new Thread(this);
		T.start();
		try { Thread.sleep(0); } catch (Exception e) {}
@@@@*/                                                             //~1327I~
	}
/*@@@@                                                             //~1327I~
	public void run ()
	{	Busy=false;
		while (true)
		{	try 
			{	synchronized(this) { wait(); }
			}
			catch (Exception e)
			{	Dump.println(e,"SoundList Exception");             //~1506R~
			}
			Busy=true;
			while (Name!=null)
			{	playNow(Name);
				synchronized (this)
				{	Name=Queued;
					Queued=null;
				}
			}
			Busy=false;
		}
	}
@@@@*/                                                             //~1327I~
	static synchronized public void beep ()
	{	Toolkit.getDefaultToolkit().beep();
	}
/*@@@@                                                             //~1327I~
	public SoundElement find (String name)
	{	SoundElement se=(SoundElement)SL.first();
		while (se!=null)
		{	if (se.name().equals(name))
			{	return se;
			}
			se=(SoundElement)se.next();
		}
		return null;
	}
	public SoundElement add (String name)
	{	SoundElement e=new SoundElement(name);
		SL.append(e);
		return e;
	}
	public void playNow (String name)
	{	SoundElement e=find(name);
		if (e==null) e=add(name);
		e.play();
	}
@@@@*/                                                             //~1327I~
	public synchronized void play (String name)
	{	if (busy())
		{                                                          //~1327R~
/*@@@@                                                             //~1327I~
			synchronized(this) { Queued=name; }                    //~1327I~
@@@@*/                                                             //~1327I~
                                  //~1327I~
			return;
		}                                                           //~1327I~
		Name=name;
/*@@@@
		synchronized(this) { notify(); }
@@@@*/ 
		playSound();//~1327I~
	}
	public boolean busy ()
//  {	return Busy;                                               //+v106R~
    {                                                              //+v106I~
    	Busy=playCtr>releaseCtr;                                   //+v106M~
//      System.out.println("Busy="+Busy+",create="+playCtr+",release="+releaseCtr);//+v106I~
		return Busy;                                               //+v106M~
	}
//***************************                                      //~1327I~
//*Mediaplayer              *                                      //~1327I~
//***************************                                      //~1327I~
    MediaPlayer player=null;                                       //~1327I~
    synchronized                                                   //~1327I~
    private void playSound()                           //~1327I~
    {                                                              //~1327I~
		int id=getResourceId(Name);                               //~1327I~
        if (id<0)                                                  //~1327I~
        	return;                                                //~1327I~
        try                                                        //~1327I~
        {                                                          //~1327I~
//      	System.out.println("playSounde create="+(playCtr+1)+",release="+releaseCtr);//+v106R~
        	player=MediaPlayer.create(AG.context,id);              //~1327I~
            playCtr++;                                           //~v106I~
        	player.seekTo(0);                                      //~1327I~
        	player.start();                                         //~1327I~
            player.setOnCompletionListener(this);                  //~1327I~
        }                                                          //~1327I~
        catch(Exception e)                                          //~1327I~
        {                                                          //~1327I~
        	Dump.println(e,Name+"Sound Play Exception");          //~1327I~
        }                                                          //~1327I~
    }                                                              //~1327I~
	//*******************                                          //~1327I~
    @Override                                                      //~1327I~
    public void onCompletion(MediaPlayer Pplayer)                  //~1327I~
    {                                                              //~1327I~
        stopSound();                                               //~1327I~
    }                                                              //~1327I~
	//*******************                                          //~1327I~
    synchronized                                                   //~1327I~
    private void stopSound()                                       //~1327I~
    {                                                              //~1327I~
    	if (player==null)                                          //~1327I~
        	return;                                                 //~1327I~
        try                                                        //~1327I~
        {                                                          //~1327I~
        	player.stop();                                         //~1327I~
        	player.reset();                                        //~v106I~
            player.setOnCompletionListener(null);                  //~1327I~
        	player.release();                                      //~1327I~
            releaseCtr++;                                          //~v106I~
            player=null;                                           //~1327I~
        }                                                          //~1327I~
        catch(Exception e)                                          //~1327I~
        {                                                          //~1327I~
        	Dump.println(e,Name+"Sound Stop Exception");          //~1327I~
        }                                                          //~1327I~
    }                                                              //~1327I~
	//*******************                                          //~1327I~
    private int getResourceId(String Pname)                        //~1327I~
    {                                                              //~1327I~
    	String basename=Pname;                                     //~1327I~
    	int pos,sz;                                                //~1327I~
    //*********                                                    //~1327I~
    	pos=Pname.lastIndexOf('/');                                 //~1327I~
        if (pos>0)                                                 //~1327I~
        	basename=basename.substring(pos+1);                    //~1327I~
    	pos=basename.lastIndexOf('.');                                 //~1327I~
        if (pos>0)                                                 //~1327I~
        	basename=basename.substring(0,pos);                    //~1327I~
        return AG.findSoundId(basename);                           //~1327I~
    }                                                              //~1327I~
/*@@@@                                                             //~1327I~
	static SoundList L=new SoundList();
	static public void main (String args[])
	{	if (Dump.Y) Dump.println("Java Version "+Global.getJavaVersion());//~1506R~
		String Sounds[]={"high","message","click","stone","wip","yourmove","game"};
		rene.gui.CloseFrame F=new rene.gui.CloseFrame()
			{	public void doAction (String o)
				{	if (Global.getJavaVersion()>=1.3)
						L.play("/jagoclient/au/"+o+".wav");
					else
						L.play("/jagoclient/au/"+o+".au");
				}
				public void doclose ()
				{	System.exit(0);
				}
			};
		F.setLayout(new BorderLayout());
		Panel p=new Panel();
		F.add("Center",p);
		for (int i=0; i<Sounds.length; i++)
		{	Button b=new Button(Sounds[i]);
			b.addActionListener(F);
			p.add(b);
		}
		F.pack();
		F.setVisible(true);
	}
@@@@*/                                                             //~1327I~
}

