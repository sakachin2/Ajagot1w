//*CID://+v1EeR~:                             update#=  186;       //~v1EeR~
//*************************************************************************//~v105I~
//v1Ee 2014/12/12 FileDialog:NPE at AjagoModal:actionPerforme by v1Ec//~v1EeI~
//                OnListItemClick has no modal consideration like as Button//~v1EeI~
//                FileDialog from LocalGoFrame is on subthread, OnItemClick of List Item scheduled on MainThread//~v1EeI~
//                AjagoModal do not allocalte countdown latch but subthreadModal flag indicate latch.countDown()//~v1EeI~
//                ==>Change FileDialog to from WaitInput(Modal) to Callback method//~v1EeI~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//*************************************************************************//~v105I~
package com.Ajagoc.awt;                                                //~1108R~//~1109R~//~v1E9R~

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.Ajagoc.AG;                                              //~v1E9R~
import com.Ajagoc.AjagoView;                                           //~v1E9R~
import com.Ajagoc.R;                                               //~v1E9R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.MyTextArea;

                                                                   //~1319R~
public class FileDialogClipboard extends CloseDialog               //~1A4sR~
{                                                                  //~1111I~
    private boolean swOK;                                          //~1A4sI~
    private String clipboardText="";                               //~v1E9R~
    private MyTextArea textArea;                                   //~1A4sI~
    private FileDialogClipboardI FDCI;                             //~v1E9I~
    private static final int TITLEID=R.string.Title_FileDialogClipboard;//~v1E9I~
    private static final String CLIPBOARD_SAVE_FILENAME="Clipboard";//~v1E9I~
    private FileDialogI FDI;                                       //~v1E9I~
    private String saveEncoding;                                   //~v1E9I~
//***********                                                      //~1319I~
    public FileDialogClipboard(Frame Pframe,FileDialogClipboardI Pcallback,String Ptitle)//~v1E9R~
    { 
     	super(Pframe,                                              //~v1E9R~
     		(Ptitle==null ? AG.resource.getString(TITLEID) : Ptitle),//~v1E9I~
     		(AG.screenDencityMdpi ? R.layout.filedialogclipboard_mdpi : R.layout.filedialogclipboard),//~1A4sI~
     		false/*modal*/,false/*wait input*/);                   //~1A4sI~
        FDCI=Pcallback;                                            //~v1E9I~
     	if (Dump.Y) Dump.println("FileDialogClipboard");           //~1A4sR~
		setClipboardContents();                                    //~1A4sI~
		getSaveCallback();                                         //~v1E9I~
        new ButtonAction(this,0,R.id.OK);                          //~1A4sR~
        new ButtonAction(this,0,R.id.Save);                        //~1A4sI~
        new ButtonAction(this,0,R.id.Paste);                       //~1A4sI~
        new ButtonAction(this,0,R.id.Cancel);                      //~1A21R~
        new ButtonAction(this,0,R.id.Help);                        //~1A21R~
        setDismissActionListener(this/*DoActionListener*/);	//callback from DialogClosed at dismiss//~1A4sI~
        show();                                                    //~v1E9I~
    }                                                              //~1214I~
    //*****************************************************************//~1A4sI~
	private void setClipboardContents()                            //~1A4sR~
    {                                                              //~1A21I~
    	String text=getClipboardText();                                   //~v1E9R~
        if (text==null || text.equals(""))                         //~1A4sI~
        {                                                          //~1A4sI~
        	text="";                                               //~1A4sI~
       		AjagoView.showToast(R.string.ErrClipboardNoText);          //~1A4sI~
        }                                                          //~1A4sI~
        if (textArea==null)                                        //~1A4sI~
    		textArea=new MyTextArea(this,text,R.id.ClipboardContents,0/*row*/,0/*col*/,TextArea.SCROLLBARS_VERTICAL_ONLY);//~1A4sR~
        else                                                       //~1A4sI~
    		textArea.setText(text);                                //~1A4sI~
    }                                                              //~1A21I~
	//***************************************                      //~1402R~
	public void doAction (String o)                                //~1403I~
	{                                                              //~1403I~
    //***************************                                  //~v105I~
      	try                                                        //~1A4sR~
      	{                                                          //~1A4sR~
			if (o.equals(AG.resource.getString(R.string.OK)))               //~1403R~//~1A4sR~
			{                                                      //~1A4sR~
    			if (fileOK())      //file is selected              //~1A4sR~
                {                                                  //~1A4sI~
                	swOK=true;                                     //~1A4sI~
	        		dispose();	         //dismiss                 //~1A4sR~
                }                                                  //~1A4sI~
			}                                                      //~1A4sR~
			else if (o.equals(AG.resource.getString(R.string.Save)))//~1A4sI~
			{                                                      //~1A4sI~
    			fileSave();      //file is selected                 //~1A4sI~
			}                                                      //~1A4sI~
			else if (o.equals(AG.resource.getString(R.string.Paste)))//~1A4sI~
			{                                                      //~1A4sI~
    			filePaste();      //file is selected                //~1A4sI~
			}                                                      //~1A4sI~
			else if (o.equals(AG.resource.getString(R.string.Cancel)))//~1A4sR~
			{                                                      //~1A4sR~
	        	dispose();	         //dismiss                     //~1A4sR~
			}                                                      //~1A4sR~
			else if (o.equals(AG.resource.getString(R.string.Help)))//~1A4sR~
			{                                                      //~1A4sR~
    			new HelpDialog(Global.frame(),R.string.HelpTitle_FileDialogClipboard,"FileDialogClipboard"/*help text filename*/);//+v1EeR~
			}                                                      //~1A4sR~
        	else if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))//~v1E9R~
        	{                                                      //~v1E9R~
            	callback();                                        //~v1E9R~
        	}                                                      //~1A4sR~
			else super.doAction(o);	//Cancel                       //~1A4sR~
      	}                                                          //~1A4sR~
      	catch (Exception e)                                        //~1A4sR~
      	{                                                          //~1A4sR~
      		Dump.println(e,"FileDialogClipboard:doActionException:"+o);//~1A4sR~
       		AjagoView.showToast(R.string.Exception);                   //~v105I~//~1A4sR~
	    	dispose();	         //dismiss                         //~1A4sR~
      	}                                                          //~1A4sR~
    }                                                              //~1403R~
    //**********************************************************************//~1A4sI~
    //*                                                            //~1A4sI~
    //**********************************************************************//~1A4sI~
    private boolean fileOK()                                       //~1A4sI~
    {                                                              //~1A4sR~
    	clipboardText=textArea.getText().toString();               //~v1E9I~
        if (clipboardText==null)                                   //~v1E9I~
        	clipboardText="";                                      //~v1E9I~
        return true;                                               //~v1E9R~
    }                                                              //~1A4sI~
    //************************                                     //~1A4sI~
    private void filePaste()                                       //~1A4sI~
    {                                                              //~1A4sI~
		setClipboardContents();                                    //~1A4sI~
    }                                                              //~1A4sI~
    //************************                                     //~1A4sI~
    private void fileSave()                                        //~1A4sM~
    {                                                              //~1A4sM~
    	if (Dump.Y) Dump.println("FileDialogClipboard:saveDialog");
    	FileDialog fd=new FileDialog(this/*frame or dialog*/,AG.resource.getString(R.string.Title_SaveFile),FileDialog.SAVE,false/*mpdal*/);//~v1E9R~
        fd.setCallback(FDI);                                       //~v1E9I~
		String ext=(Global.getParameter("xml",false)?"xml":"sgf"); //~v1E9I~
        fd.setSaveFilename(CLIPBOARD_SAVE_FILENAME+"."+ext);       //~v1E9R~
        fd.show();                                                 //~1A4sI~
    }                                                              //~1A4sI~
    //**********************************************************************//~1A4sI~
    private String getClipboardText()                              //~1A4sI~
    {                                                              //~1A4sI~
		String text=Clipboard.getClipboardText();                  //~v1E9R~
        return text;                                               //~1A4sI~
    }                                                              //~1A4sI~
    //******************************************************************//~1A21I~
    //*after dismiss                                               //~1A22I~
    //******************************************************************//~1A22I~
    private void callback()                                        //~v1E9R~
    {                                                              //~1A21I~
    	try                                                        //~v1E9I~
        {                                                          //~v1E9I~
    		if (swOK)                                              //~v1E9I~
	        	FDCI.fileDialogClipboardLoaded(clipboardText);     //~v1E9I~
        }                                                          //~v1E9I~
      	catch (Exception e)                                        //~v1E9I~
      	{                                                          //~v1E9I~
      		Dump.println(e,"FileDialogClipboard:callback:");       //~v1E9I~
       		AjagoView.showToast(R.string.Exception);               //~v1E9I~
      	}                                                          //~v1E9I~
    }                                                              //~v1E9I~
    //******************************************************************//~v1E9I~
    private void getSaveCallback()                                 //~v1E9I~
    {                                                              //~v1E9I~
    	FDI=new FileDialogI()                                      //~v1E9I~
        	{                                                      //~v1E9I~
//          	public int fileDialogSaveCallback(String Pfnm)     //~v1EeR~
            	public int fileDialogSaveCallback(FileDialog Pfd,String Pfnm)//~v1EeI~
                {                                                  //~v1E9I~
                	return saveToFile(Pfnm);                       //~v1E9R~
                }                                                  //~v1E9I~
            	public int fileDialogLoadCallback(FileDialog Pfd,String Pfnm){return 0;}//~v1EeR~
            };                                                     //~v1E9I~
    }                                                              //~v1E9I~
    //******************************************************************//~v1E9I~
    private int saveToFile(String Pfnm)                            //~v1E9R~
    {                                                              //~v1E9I~
    	PrintWriter fo;                                            //~v1E9I~
        int rc=0;                                                  //~v1E9I~
		clipboardText=textArea.getText().toString();               //~v1E9R~
        if (clipboardText==null || clipboardText.equals(""))       //~v1E9R~
        {                                                          //~v1E9I~
       		AjagoView.showToast(R.string.ErrClipboardNoText);      //~v1E9I~tWriter fo;                                            //~v1E9I~
        	return 4;                                              //~v1E9R~
        }                                                          //~v1E9I~
    	try                                                        //~v1E9I~
		{                                                          //~v1E9I~
        	if (saveEncoding==null)                                //~v1E9I~
				fo=new PrintWriter(new OutputStreamWriter(new FileOutputStream(Pfnm)));//~v1E9I~
            else                                                   //~v1E9I~
				fo=new PrintWriter(new OutputStreamWriter(new FileOutputStream(Pfnm),saveEncoding));//~v1E9I~
            fo.println(clipboardText);                             //~v1E9I~
			fo.close();                                            //~v1E9I~
       		AjagoView.showToast(R.string.InfoClipboardSaved,Pfnm); //~v1E9I~
        }                                                          //~v1E9I~
        catch(Exception e)                                         //~v1E9I~
        {                                                          //~v1E9I~
      		Dump.println(e,"FileDialogClipboard:saveToFile filename="+Pfnm);//~v1E9I~
       		AjagoView.showToast(R.string.ErrClipboardSaveFailed,Pfnm);   //~v1E9I~
        	return 4;                                              //~v1E9I~
        }
        return rc;//~v1E9I~
    }                                                              //~v1E9I~
}//class                                                           //~1318R~
