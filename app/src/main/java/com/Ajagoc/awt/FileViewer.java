//*CID://+v1EjR~:                             update#=  194;       //~v1EjR~
//*************************************************************************
//v1Ej 2014/12/13 (Asgts)//1A4z 2014/12/09 FileDialog:view file by click twice//~1A4zI~
//*************************************************************************
package com.Ajagoc.awt;                                            //~v1EjR~

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoProp;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.ChoiceAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.DoActionListener;
import jagoclient.gui.MyTextArea;
import jagoclient.gui.TextFieldAction;


public class FileViewer extends CloseDialog
{
    private final static String PKEY_FILEENCODING_VIEWER="ViewerFileEncoding";
    private final static String ITEMACTION_FILEENCODING="FileEncoding";
    private final static String ITEMACTION_FILEENCODING_SPINNER="FileEncodingSpinner";//~v1EjI~
    private final static String GLOBAL_ENCODING="encoding";        //~v1EjI~
    private final static int    DIALOG_TITLEID=R.string.Title_FileViewer;
    private final static int    DIALOG_TITLEID_FILENAME=R.string.Title_FileViewerFilename;
    private final static int    DIALOG_LAYOUTID=R.layout.fileviewer;
    private final static int    DIALOG_LAYOUTID_MDPI=R.layout.fileviewer_mdpi;
    private final static int    VIEWER_MAXSIZE=4096*16;
    private final static int    TEXTVIEWID=R.id.FileContents;
    private String[] encodingList={"Encoding",                     //~v1EjI~
    								"UTF-8",                       //~v1EjI~
                                    "Shift_JIS",                    //~v1EjI~
                                    "ISO8859_1",                   //~v1EjI~
                                  };                               //~v1EjI~
//  private ChoiceAction ChoiceEncoding;                           //~v1EjR~
    private MyTextArea textArea;
    private String fileName;
    private String fileEncoding="";
    private TextFieldAction etEncoding;                            //~v1EjI~
    private ChoiceAction choiceEncoding;                           //~v1EjI~
    private boolean swLoadOK;                                      //~v1EjI~
    private boolean swInitEnd;                                     //~v1EjI~
//***********
    public FileViewer(CloseDialog Pdialog,String Pfnm)
    { 
     	super(Pdialog,AG.resource.getString(DIALOG_TITLEID),
     		(AG.screenDencityMdpi ? DIALOG_LAYOUTID_MDPI : DIALOG_LAYOUTID),
     		false/*modal*/,false/*wait input*/);
     	if (Dump.Y) Dump.println("FileViewer:"+Pfnm);
        fileName=Pfnm;
        etEncoding=new TextFieldAction((Container)this/*container*/,(DoActionListener)this/*doActionListener*/,R.id.FileEncoding,ITEMACTION_FILEENCODING/*doActionListener:string*/,""/*setText*/,0,0);//~v1EjR~
		choiceEncoding=new ChoiceAction(this,ITEMACTION_FILEENCODING_SPINNER,R.id.FileEncodingSpinner);//~v1EjI~
    	textArea=new MyTextArea(this,""/*text*/,TEXTVIEWID,0/*row*/,0/*col*/,TextArea.SCROLLBARS_VERTICAL_ONLY);//~v1EjI~
        setupEncoding(); 
        new ButtonAction(this,0,R.id.Close);
        new ButtonAction(this,0,R.id.Help);
        showDialog();
	    swInitEnd=true;                                            //~v1EjI~
    }
    //*****************************************************************
	private void showDialog()
    {
        setTitleFilename();
		setFileContents(true/*init*/);                             //~v1EjR~
    	super.show();
        if (Dump.Y) Dump.println("FileViwer:after show");
    }
    //*****************************************************************
	private void setupEncoding()
    {
        String res=Global.resourceString(GLOBAL_ENCODING);         //~v1EjR~
        if (res.equals(GLOBAL_ENCODING))                           //~v1EjI~
        	res="";                                                //~v1EjI~
        String ep=AjagoProp.getPreference(PKEY_FILEENCODING_VIEWER,res);//~v1EjR~
        fileEncoding=editEncoding(ep);                             //~v1EjI~
        etEncoding.setText(fileEncoding);                          //~v1EjI~
                                                                   //~v1EjI~
        for (int ii=0;ii<encodingList.length;ii++)                 //~v1EjI~
        	choiceEncoding.add(encodingList[ii]);                  //~v1EjI~
		choiceEncoding.select(0);                                  //~v1EjI~
    }
	private void getEncodingParameter()                            //~v1EjI~
    {                                                              //~v1EjI~
    	String ep=etEncoding.getText().toString();                 //~v1EjI~
        fileEncoding=editEncoding(ep);                             //~v1EjI~
    	etEncoding.setText(fileEncoding);                          //~v1EjR~
    }                                                              //~v1EjI~
    private String editEncoding(String Pparm)                      //~v1EjI~
    {                                                              //~v1EjI~
        String ep=Pparm.replace("\n","");                          //~v1EjR~
    	return ep.trim();                                          //~v1EjR~
    }                                                              //~v1EjI~
    //*****************************************************************
	private boolean setFileContents(boolean Pswinit)               //~v1EjR~
    {
    	swLoadOK=false;                                            //~v1EjI~
    	String text=getFileContents();
        if (!swLoadOK)                                             //~v1EjI~
        {                                                          //~v1EjI~
	        if (Pswinit)                                           //~v1EjI~
    			textArea.setText("");                              //~v1EjI~
    	    return false;	//dont dispose                         //~v1EjI~
        }                                                          //~v1EjI~
        if (text==null)
        	text="";
        else
        if (text.equals(""))
       		AjagoView.showToast(R.string.ErrFileViewerNoText);
    	textArea.setText(text);                                    //~v1EjR~
        return true;                                               //~v1EjI~
    }
	//***************************************
	public void doAction (String o)
	{
    	boolean swdispose=true;
    //***************************
      	try
      	{
			if (o.equals(AG.resource.getString(R.string.Help)))
			{
    			new HelpDialog(Global.frame(),R.string.HelpTitle_FileViewer,"FileViewer"/*help text filename*/);//+v1EjR~
                swdispose=false;
			}
            else
			if (o.equals(ITEMACTION_FILEENCODING))                         //~v1EjI~
			{                                                      //~v1EjI~
            	getEncodingParameter();                            //~v1EjI~
				if (setFileContents(false/*not init*/))	//no exception             //~v1EjR~
			        AjagoProp.putPreference(PKEY_FILEENCODING_VIEWER,fileEncoding);//~v1EjR~
	            swdispose=false;                                   //~v1EjR~
			}                                                      //~v1EjI~
            else                                                   //~v1EjI~
				super.doAction(o);	//Cancel
            if (swdispose)
	        	dispose();           //dismiss
      	}
      	catch (Exception e)
      	{
      		Dump.println(e,"FileViewer:doActionException:"+o);
       		AjagoView.showToast(R.string.Exception);
	        dispose();           //dismiss
      	}
    }
    //**************************************                       //~v1EjI~
    //*Spinner(ChoiceAction)                                       //~v1EjR~
    //**************************************                       //~v1EjI~
    @Override                                                      //~v1EjI~
	public void itemAction (String s, boolean flag)                //~v1EjI~
	{                                                              //~v1EjI~
		String item="";                                            //~v1EjI~
    //******************                                           //~v1EjI~
		if (Dump.Y) Dump.println("FileViewer itemAction string="+s+",flag="+flag);//~v1EjI~
        if (!swInitEnd)                                            //~v1EjI~
        	return;                                                //~v1EjI~
        if (s.equals(ITEMACTION_FILEENCODING_SPINNER))             //~v1EjI~
        {                                                          //~v1EjI~
        	int pos=choiceEncoding.getSelectedIndex();             //~v1EjI~
            if (pos!=0)                                            //~v1EjI~
            {                                                      //~v1EjI~
				item=choiceEncoding.getSelectedItem();             //~v1EjI~
                if (!item.equals(fileEncoding)) //changed          //~v1EjR~
                {                                                  //~v1EjR~
                    fileEncoding=item;                             //~v1EjR~
                    etEncoding.setText(fileEncoding);              //~v1EjR~
                    setFileContents(false);                        //~v1EjR~
                }                                                  //~v1EjR~
            }                                                      //~v1EjI~
        }                                                          //~v1EjI~
	}                                                              //~v1EjI~
    //**********************************************************************
    private void setTitleFilename()
    {
        String fnm=new File(fileName).getName();
        String dlgtitle=AG.resource.getString(DIALOG_TITLEID_FILENAME,fnm);
    	setTitle(dlgtitle);  //of Dialog
    }
    //**********************************************************************
    private String getFileContents()
    {
    	if (Dump.Y) Dump.println("FileViewer getFileContents encoding="+fileEncoding);//~v1EjI~
        BufferedReader br=getEncodingStream(fileName,fileEncoding);
        if (br==null)
        	return null;
        StringBuffer sb=new StringBuffer("");
        readFile(br,VIEWER_MAXSIZE,sb);
        return new String(sb);
    }
    //*******************************************************
	private BufferedReader getEncodingStream(String Pfnm,String Pencoding)
	{
		InputStreamReader isr;
		BufferedReader br=null;
		try
		{
			FileInputStream fis=new FileInputStream(Pfnm);
        	if (Pencoding.equals(""))
				isr=new InputStreamReader(fis);
            else
				isr=new InputStreamReader(fis,Pencoding);
			br=new BufferedReader(isr);
		}
		catch (UnsupportedEncodingException e)
		{
//      	Dump.println(e,"NotesFmt:getEncodingStream"+Pfnm+",encoding="+Pencoding);//~v1EjR~
            AjagoView.showToastLong(AG.resource.getString(R.string.ErrFileViewerNotSupportedEncoding,Pencoding));//~v1EjR~
		}
		catch (Exception e)
		{
        	Dump.println(e,"FileViewer:getEncodingStream"+Pfnm+",encoding="+Pencoding);//~3406I~//+1A4zI~            if (!swTest)//~v1EjR~
            AjagoView.showToastLong(AG.resource.getString(R.string.ErrFileViewerOpen,Pfnm));
		}
        return br;
	}
    //******************************************************************************
	private int readFile(BufferedReader Pbr,int Pmaxsize,StringBuffer Pbuff)
    {
        String line;
    //************************************
        BufferedReader br=Pbr;
    	try
        {
            for (;;)
            {
            	line=br.readLine();
                if (line==null)  //eof
                	break;
                if (Pbuff.length()+line.length()>Pmaxsize)
                {
		            AjagoView.showToastLong(AG.resource.getString(R.string.ErrFileViewerTooLarge,Pmaxsize));
                	return 4;
                }
                Pbuff.append(line+"\n");
            }
            br.close();
            swLoadOK=true;                                         //~v1EjI~
        }
		catch (Exception e)
		{
        	Dump.println(e,"FileViewer:readFile:"+fileName);
            AjagoView.showToastLong(AG.resource.getString(R.string.ErrFileViewerRead));
		}
		return 0;
	}
}//class
