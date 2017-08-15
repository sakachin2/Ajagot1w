//*CID://+v104R~:                             update#=    5;       //~v104I~
//*******************************************************          //~v104I~
//v104:121108 hung(ToneGenerator init fail)                        //~v104I~
//*******************************************************          //~v104I~
package com.Ajagoc.awt;                                                //~1108R~//~1109R~

import jagoclient.Dump;

import java.util.Properties;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;

public class Toolkit                                        //~1213R~
{                                                                  //~1111I~
    static ToneGenerator tg=new ToneGenerator(AudioManager.STREAM_ALARM,ToneGenerator.MAX_VOLUME);//~v104I~
//**********************                                           //~1128I~
	public static Toolkit getToolkit()                             //~1213R~
    {                                                              //~1124I~
      try                                                          //~v104I~
      {                                                            //~v104I~
		if (tg==null)                                              //~v104I~
			tg=new ToneGenerator(AudioManager.STREAM_ALARM,ToneGenerator.MAX_VOLUME);//~v104I~
      }                                                            //~v104I~
      catch(Exception e)                                           //~v104I~
      {                                                            //~v104I~
          Dump.println(e,"getToolKit");                            //~v104I~
      }                                                            //~v104I~
    	return new Toolkit();                                               //~1213I~
    }
	public static Toolkit getDefaultToolkit()
	{
		return getToolkit();
	}
	public Dimension getScreenSize()                               //~1524R~
	{
		return new Dimension(AG.scrWidth,AG.scrHeight);//~1124I~
	}
    public Clipboard getSystemClipboard()                          //~1212I~//~1213M~
    {                                                              //~1213M~
    	return new Clipboard();                           //~1212I~//~1213M~
    }                                                              //~1212I~//~1213M~
    public PrintJob getPrintJob(Frame Pf,String Jobname,Properties Pprintref)//~1215I~
    {                                                              //~1215I~
        AjagoView.showToast(R.string.NoPrintSupport);              //~1404I~
        return null;                                               //~1404I~
    }                                                              //~1215I~
    public String[] getFontList()                                  //~1331I~
    {                                                              //~1331I~
    	return null;	//fot GetFontSize, no additional font for android//~1331I~
        				//@@@@ android graphics has sans_seriff,serif,monospace only//~1331R~
    }                                                              //~1331I~
//***********                                                      //~1219I~
    public void beep()                                             //~1219I~
    {                                                              //~1219I~
      try                                                          //~v104I~
      {                                                            //~v104I~
//    	ToneGenerator tg=new ToneGenerator(AudioManager.STREAM_ALARM,ToneGenerator.MAX_VOLUME);//~v104R~
       if (tg!=null)                                               //~v104I~
       {                                                           //~v104I~
    	tg.startTone(ToneGenerator.TONE_PROP_BEEP);   //Beep:35ms   //~1219R~
    	tg.stopTone();                                             //~v104I~
//  	tg.release();                                              //+v104R~
       }                                                           //~v104I~
      }                                                            //~v104I~
      catch(Exception e)                                           //~v104I~
      {                                                            //~v104I~
          Dump.println(e,"beep");                                  //~v104I~
      }                                                            //~v104I~
    }                                                              //~1219I~
//***********                                                      //~1416I~
    public void sync()      //force update screen                  //~1416I~
    {                                                              //~1416I~
    }                                                              //~1416I~
//***********                                                      //~1417I~
    public Image createImage(byte[] Pbyte,int Poffs,int Plen)      //~1417R~
    {                                                              //~1417I~
    	return new Image(0,0);                                     //~1417I~
    }                                                              //~1417I~
}
