//*CID://+v1E3R~:                             update#=   10;       //~v1E3R~
//***************************************************************************//~v1DkI~
//v1E3 2014/12/08 (Asgts:1A4x) (Bug)Clipboard (String)item.getText() may cause invalid cast exception; .toString() is valid//~v1E3I~
//v1Dk 2014/11/13 (Bug)Save to Clipboard fails by RunTimeException(Can't create handler inside thread that was not called looper...)//~v1DkI~
//                (text.ClipboardManger was deplicated at api11(content.Clipboardmanager is new)//~v1DkI~
//***************************************************************************//~v1DkI~
package com.Ajagoc.awt;                                                //~1108R~//~1109R~

import jagoclient.Dump;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoUiThread;
import com.Ajagoc.AjagoUiThreadI;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
//import android.text.ClipboardManager;                            //~v1DkR~
import android.content.ClipboardManager;                           //~v1DkI~
import android.os.Build;
                                                                   //~1212I~
public class Clipboard                    //~1212R~
	implements AjagoUiThreadI                                      //~v1DkI~
{   
//  ClipboardManager cm=null;                                      //~v1DkR~
    Object cmgr;  //content.ClipboardManager or text.ClipboardManager//~v1DkI~
    String data=null;                                              //~1401I~
	public Clipboard()
	{
//  	cm=(ClipboardManager)AG.ajagoc.getSystemService(Context.CLIPBOARD_SERVICE);//~v1DkR~
    	getcm();                                                   //~v1DkI~
	}
//*for GoFrame                                                     //~1401I~
	public StringSelection getContents(ClipboardOwner Pco)         //~1401I~
	{                                                              //~1401I~
//        data=(String)cm.getText();                                         //~1401I~//~v1DkR~
//        if (Dump.Y) Dump.println("Clipboard getText"+data);        //~1506R~//~v1DkR~
//        return new StringSelection(data);                          //~1401I~//~v1DkR~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~v1DkM~
 			return getContents_V11(Pco);                           //~v1DkR~
        else                                                      //~v1DkI~
			return getContents_deprecated(Pco);                    //~v1DkR~
	}                                                              //~1401I~
    //*******************************************************************//~v1DkI~
	public void setContents(StringSelection Pss,ClipboardOwner Pco)     //~1212I~//~1401M~
	{                                                              //~1212I~//~1401M~
//  	((Android.text.ClipboardManager)cm).setText(Pss.getTransferData(null));                      //~1401I~//~v1DkR~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~v1DkM~
            setContents_V11(Pss,Pco);                              //~v1DkM~
        else                                                       //~v1DkI~
            setContents_deprecated(Pss,Pco);                       //~v1DkI~
	}                                                              //~1212I~//~1401M~
    //*******************************************************************//~v1DkI~
    @SuppressWarnings("deprecation")                               //~v1DkI~
	public StringSelection getContents_deprecated(ClipboardOwner Pco)//~v1DkI~
	{                                                              //~v1DkI~
		android.text.ClipboardManager cm=(android.text.ClipboardManager)cmgr;//~v1DkR~
//      data=(String)cm.getText();                                 //~v1E3R~
        data=cm.getText().toString();                              //~v1E3I~
        if (Dump.Y) Dump.println("Clipboard getText"+data);        //~v1DkI~
		return new StringSelection(data);                          //~v1DkI~
	}                                                              //~v1DkI~
    //*******************************************************************//~v1DkI~
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)                      //~v1DkI~
	private StringSelection getContents_V11(ClipboardOwner Pco)               //~v1DkI~
	{                                                              //~v1DkI~
    	ClipData.Item item;                                        //~v1DkI~
		ClipboardManager cm=(ClipboardManager)cmgr;                //~v1DkI~
        if (cm.hasPrimaryClip())                                   //~v1DkI~
        {                                                          //~v1DkI~
    		item=cm.getPrimaryClip().getItemAt(0);                 //~v1DkI~
//          data=(String)item.getText();                           //~v1E3R~
            data=item.getText().toString();                        //~v1E3I~
        }                                                          //~v1DkI~
        else                                                       //~v1DkI~
        	data="";                                               //~v1DkI~
        if (Dump.Y) Dump.println("Clipboard getText"+data);        //~v1DkI~
		return new StringSelection(data);                          //~v1DkI~
	}                                                              //~v1DkI~
    //*******************************************************************//~v1DkI~
    @SuppressWarnings("deprecation")
	private void setContents_deprecated(StringSelection Pss,ClipboardOwner Pco)                   //~v1DkI~
    {                                                              //~v1DkI~
		android.text.ClipboardManager cm=(android.text.ClipboardManager)cmgr;//~v1DkR~
		cm.setText(Pss.getTransferData(null));                     //~v1DkR~
    }                                                              //~v1DkI~
    //*******************************************************************//~v1DkI~
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setContents_V11(StringSelection Pss,ClipboardOwner Pco)//~v1DkR~
	{                                                              //~v1DkI~
    	ClipData.Item item=new ClipData.Item(Pss.getTransferData(null));	//StringSelection:=String//~v1DkR~
        String[] mymetype=new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN};//~v1DkI~
        ClipData cd=new ClipData("data",mymetype,item);             //~v1DkI~
		ClipboardManager cm=(ClipboardManager)cmgr;                //~v1DkI~
		cm.setPrimaryClip(cd);                       //~v1DkI~
	}                                                              //~v1DkI~

//********************************                                 //~v1DkI~
//* support All runOnUiThread                                      //~v1DkI~
//********************************                                 //~v1DkI~
	private int caseUiThread;                                      //~v1DkI~
    private final static int CASE_GETCM       =1;                  //~v1DkI~
    private void runOnUiThread(int Pcase,Object Pparm)             //~v1DkI~
    {                                                              //~v1DkI~
    	caseUiThread=Pcase;                                        //~v1DkI~
        boolean wait=waitmode(Pcase);                              //~v1DkI~
		AjagoUiThread.runOnUiThread(wait,this,Pparm);              //~v1DkI~
    }                                                              //~v1DkI~
//********************************                                 //~v1DkI~
    private boolean waitmode(int Pcase)                            //~v1DkI~
    {                                                              //~v1DkI~
    	boolean waitmode=true;                                     //~v1DkI~
        return waitmode;                                           //~v1DkI~
    }                                                              //~v1DkI~
//************                                                     //~v1DkI~
	@Override   //interface AjagoUiThread                          //~v1DkI~
    public void runOnUiThread(Object Pparm)                        //~v1DkI~
    {                                                              //~v1DkI~
        if (Dump.Y) Dump.println("Clipboard runOnUi case="+caseUiThread);//~v1DkI~
        switch(caseUiThread)                                     //~v1DkI~
        {                                                          //~v1DkI~
        case CASE_GETCM:                                           //~v1DkI~
        	getcmUI(Pparm);                                        //~v1DkI~
            break;                                                 //~v1DkI~
        }                                                          //~v1DkI~
    }                                                              //~v1DkI~
//**getcm************************************************          //~v1DkI~
    private void getcm()                               //~v1DkI~
    {                                                              //~v1DkI~
    	runOnUiThread(CASE_GETCM,null);                             //~v1DkI~
    }                                                              //~v1DkI~
//********                                                         //~v1DkI~
    private void getcmUI(Object Pparm)                             //~v1DkI~
    {                                                              //~v1DkI~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~v1DkI~
			getcmUI_V11();                                         //~v1DkI~
        else                                                       //~v1DkI~
			getcmUI_deprecated();                                  //~v1DkI~
    }                                                              //~v1DkI~
//********                                                         //~v1DkI~
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)                      //~v1DkI~
    private void getcmUI_V11()                         //~v1DkI~
    {                                                              //~v1DkI~
    	ClipboardManager cm=(ClipboardManager)AG.ajagoc.getSystemService(Context.CLIPBOARD_SERVICE);//~v1DkI~
        cmgr=cm;                                                   //~v1DkI~
    }                                                              //~v1DkI~
//********                                                         //~v1DkI~
    @SuppressWarnings("deprecation")                               //~v1DkI~
    private void getcmUI_deprecated()                  //~v1DkI~
    {                                                              //~v1DkI~
		android.text.ClipboardManager cm=(android.text.ClipboardManager)AG.ajagoc.getSystemService(Context.CLIPBOARD_SERVICE);//~v1DkI~
        cmgr=cm;                                                   //~v1DkI~
    }                                                              //~v1DkI~
//********************************************************         //+v1E3I~
	public static String getClipboardText()                        //+v1E3I~
	{                                                              //+v1E3I~
        String text=null;                                          //+v1E3I~
	 	try                                                        //+v1E3I~
		{                                                          //+v1E3I~
			Clipboard clipboard=new Clipboard();                             //+v1E3I~
			StringSelection ss=clipboard.getContents(null/*clipboardOwner interface*/);//+v1E3I~
			text=ss.getTransferData(null);                         //+v1E3I~
		}                                                          //+v1E3I~
		catch (Exception e)                                        //+v1E3I~
		{                                                          //+v1E3I~
        	Dump.println(e,"loadClipboard ");                      //+v1E3I~
		}                                                          //+v1E3I~
        return text;                                               //+v1E3I~
	}                                                              //+v1E3I~
}//class                                                           //~1212R~
