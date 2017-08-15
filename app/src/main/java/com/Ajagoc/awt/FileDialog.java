//*CID://+v1EjR~:                             update#=   71;       //~v1EjR~
//*************************************************************************//~v105I~
//v1Ek 2014/12/13 prepare Help for FileDialog                      //~v1EjI~
//v1Ej 2014/12/13 (Asgts)//1A4z 2014/12/09 FileDialog:view file by click twice//~v1EjI~
//v1Ef 2014/12/12 construct save filename by whitename and blackname//~v1EfI~
//v1Ee 2014/12/12 FileDialog:NPE at AjagoModal:actionPerforme by v1Ec//~v1EeI~
//                OnListItemClick has no modal consideration like as Button//~v1EeI~
//                FileDialog from LocalGoFrame is on subthread, OnItemClick of List Item scheduled on MainThread//~v1EeI~
//                AjagoModal do not allocalte countdown latch but subthreadModal flag indicate latch.countDown()//~v1EeI~
//                ==>Change FileDialog to from WaitInput(Modal) to Callback method//~v1EeI~
//v1Ed 2014/12/12 FileDialog:when delete then Return,io err for deleted filename//~v1EdI~
//v1Ec 2014/12/11 (Asgts)//1A4a 2014/11/29 FileDialog:open when selected item is clicked//~v1EcI~
//v1Eb 2014/12/11 FileDialog:add function to reset filter          //~v1EbI~
//v1Ea 2014/12/11 FileDialog is not opened by "No Entry" when no member exist in the dir//~v1EaI~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//v1E8 2014/12/11 (Asgts)//1A4p 2014/12/04 FileDialog:keep cursor after deleted 1 entry//~v1E8I~
//v1E0 2014/11/29 (Asgts:1A4c)FileDialog sort:IllegalArgumentException comparizonmethod violate(long to int may change sign)//~v1E0I~
//v1Dn 2014/11/14 change filename timestamp to yyMMDDHHmm(drop yy and ss)//~v1DnI~
//v1Df 2014/11/10 EditText on FileDilaog Color is not same as TextFieldAction//~v1DfI~
//v1Dc 2014/11/10 Bug of v1D0;Label definition required even when not used warning//~v1DcI~
//v1Da 2014/11/09 sort file dialog list                            //~v1DaI~
//v1D3 2014/10/07 set timestamp to filename on filedialog when save(current is *.sgf)//~v1D3I~
//v1D0 2014/10/06 save to private if sdcard is not available       //~v1D0I~
//                FileDialog Prop                                  //~v1D0I~
//1054:121114 filedialog could not traverse dir                    //~v105I~
//1053:121113 exception(wrong thread) when filelist up/down for sgf file read//~v105I~
//*************************************************************************//~v105I~
package com.Ajagoc.awt;                                                //~1108R~//~1109R~

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoProp;
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.CloseFrame;

                                                                   //~1319R~
public class FileDialog extends CloseDialog                        //~1403R~
    implements ListI                                               //~v1EcI~
{                                                                  //~1111I~
    private int mode;                                              //~1319I~
    public final static int LOAD   =0;	//read  permisson          //~1319I~
    public final static int SAVE   =1;	//write permisson          //~1319I~
                                                                   //~1402I~
    private String title;                                          //~1319I~
    private CloseFrame parentFrame;                                //~1319I~//~v1E9R~
    private String dirname="";                                     //~1324R~
    private String filterString=null;                              //~1324R~
    private FilenameFilter filterCB=null;                          //~1407R~
    private ButtonAction buttonAll;                                //~v1EbI~
    public  String[] namelist;                                      //~1319I~//~1402R~
	private File[] filelist;                                       //~1319I~
	private int[]  name2file;                                      //~1330I~
	private File   selectedFile;                                   //~1403R~
	private static String selectedFilename=null;                   //~1319I~//~1330R~
	private static String selectedDirname=null;                    //~1330I~
                          //~1402I~
    private String savefilename="";                                //~1402I~
    private boolean afterDismiss;                                   //~1330I~
    private TextField input;
    private Lister lister;                                         //~1403I~
    private ButtonAction buttonSave;//~v1DaR~
    private Drawable btnBackground;                                //~v1EbI~
    private String updownpath;                                            //~1403I~
    private Color bgcolor=Global.gray;                             //~1403I~
    private boolean swSaveBitmap;                                  //~1404I~
    private boolean swSaveGame=false;                              //~1407I~
    private boolean swSaveTxt=false;                               //~1418I~
    private FileDialogI callback;                           //~v1E9I~
    private boolean swAllFile;                                     //~v1EbI~
	private boolean swOpenByClick;                                 //~v1EcR~
	private boolean swOK;                                          //~v1EdI~
	private boolean swCallbackAfterDismiss;	//false for FileDialogClipboard//~v1EeI~
//*****************************************************************//~v1E9R~
//*from Dialog                                                     //~v1E9I~
//*****************************************************************//~v1E9I~
    public FileDialog(CloseDialog Pdialog,String Ptitle,int Pmode,boolean Pmodal)//~v1E9I~
    {                                                              //~v1E9I~
     	super(Pdialog,Ptitle,R.layout.filedialog,Pmodal/*modal*/,false);//~v1E9R~
        initThis(Ptitle,Pmode,Pmodal);                             //~v1E9I~
    }                                                              //~v1E9I~
//************************************************************************//~v1E9I~
    public FileDialog(CloseFrame  Pframe,String Ptitle,int Pmode)  //~v1E9I~
    {                                                              //~v1E9I~
		this(Pframe,Ptitle,Pmode,true);                            //~v1E9I~
    }                                                              //~v1E9I~
//************************************************************************//~v1E9I~
    public FileDialog(CloseFrame  Pframe,String Ptitle,int Pmode,boolean Pmodal)//~v1E9R~
    { 
//   	super(Pframe,"FileDialog",true/*modal*/);                  //~v1E9R~
     	super(Pframe,"FileDialog",Pmodal);                  //~v1E9R~
        parentFrame=Pframe;                                        //~v1E9I~
	    initThis(Ptitle,Pmode,Pmodal);                             //~v1E9I~
    }                                                              //~v1E9I~
    private void initThis(String Ptitle,int Pmode,boolean Pmodal)      //~v1E9I~
    {                                                              //~v1E9I~
        afterDismiss=isAfterDismiss();
	 	if (Dump.Y) Dump.println("FileDialog:afterdismiss="+afterDismiss);//~1506R~
//      parentFrame=Pframe;                                        //~1403M~//~v1E9R~
        listInterface=this;	//component:ListI                      //~v1EcI~
		title=Ptitle;                                              //~1403M~
        mode=Pmode;                                                //~1403M~
//      if (Pframe instanceof FilenameFilter)                      //~v1E9R~
        if (parentFrame!=null && parentFrame instanceof FilenameFilter)//~v1E9I~
//          filterCB=(FilenameFilter)Pframe;                       //~v1E9R~
            filterCB=(FilenameFilter)parentFrame;                  //~v1E9I~
        else                                                       //~1407I~
        	filterCB=null;                                         //~1407I~
		swSaveBitmap=(Global.resourceString("Save_Bitmap").equals(Ptitle)); // save to bitmap;//~1404I~
                                                                   //~1403I~
	 	if (afterDismiss)                                          //~1403M~
	 		return;                                                //~1403M~
		selectedFilename=null;                                     //~v1EdI~
		selectedDirname=null;                                      //~v1EdI~
                                                                   //~1403I~
        if (mode==LOAD)
			new Label(AG.resource.getString(R.string.FileDialog_LabelCurrDir));
		else new Label(AG.resource.getString(R.string.FileDialog_LabelName));
		input=new TextField("New");                            //~1403I~
        input.setDefaultTextStyle();                               //~v1DfI~
        if (mode==LOAD)                                            //~1403I~
        	input.setEditable(false);                         //~1403I~
        else                                                       //~1403R~
        	input.setEditable(true);                               //~1403R~
		lister=new Lister(this/*ListI*/);                          //~v1EcR~
        buttonAll=new ButtonAction(this,0,R.id.ButtonNullFilter);  //~v1EbI~
        if (mode!=LOAD)                                            //+v1EjI~
        	buttonAll.setVisibility(View.GONE);                     //+v1EjI~
        btnBackground=getBGC(buttonAll.androidButton);             //~v1EbI~
      	new ButtonAction(this,Global.resourceString("Open"));
    	buttonSave=new ButtonAction(this,Global.resourceString("Save"));//~1403I~
      	new ButtonAction(this,Global.resourceString("Delete"));
    	new ButtonAction(this,Global.resourceString("Cancel"));
      	new ButtonAction(this,Global.resourceString("Help"));      //~v1EjM~
        if (mode==LOAD)                                            //~v105I~
        	((Component)buttonSave).setEnabled((Button)buttonSave,false);         //~v105I~
        if (Dump.Y) Dump.println("FileDialog:title="+Ptitle+",selectedFilename="+selectedFilename);//~1324I~//~1506R~
    }                                                              //~1214I~
//***********                                                      //~1319I~
	public FileDialog(CloseFrame Pframe,String Ptitle,int Pmode,String Pdir)//~1319R~
    {                                                              //~1319I~
		this(Pframe,Ptitle,Pmode);                                //~1319R~
		setDirectory(Pdir);                                        //~1319I~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void setDirectory(String Pdir)                          //~1319I~
    {                                                              //~1319I~
        if (Dump.Y) Dump.println("FileDialog setDirectory :"+Pdir);//~1506R~
        dirname="";	//use Preference                               //~1402I~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public String getDirectory()                                   //~1319I~
    {                                                              //~1319I~
    	String path;
		if (afterDismiss)                                          //~1403R~
        	path=selectedDirname;                                  //~1330I~
        else                                                       //~1330I~
        	path="";                                               //~1403R~
        if (Dump.Y) Dump.println("FileDialog getDirectory :"+path+",afterdismiss="+afterDismiss);//~1506R~
    	return path+"/";                                        //~1319I~//~1326R~//~1330R~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void setFile(String Pfile)                              //~1319I~
    {                                                              //~1319I~
        if (Dump.Y) Dump.println("FileDialog:setFile="+Pfile);     //~1506R~
        filterString=Pfile;                                        //~1402I~
        savefilename="";                                           //~1403I~
		swSaveGame=(Pfile.equals("*.sto")); // save to bitmap;     //~1407R~
		swSaveTxt=(Pfile.equals("*.txt")); // save to bitmap;      //~1418I~
        if (mode==SAVE)                                            //~1402M~
        {                                                          //~1402I~
            savefilename=Pfile;                                    //~1404R~
            savefilename=setDefaultSaveFilename(savefilename);     //~v1D3I~
            filterString="";                                       //~1404R~
        }                                                          //~1402I~
    }                                                              //~1319I~
//***********                                                      //~v1EfI~
	public void setSaveFilename(String Pfile,String Pmatchname)    //~v1EfI~
    {                                                              //~v1EfI~
        if (Dump.Y) Dump.println("FileDialog:setFile="+Pfile+","+Pmatchname);//~v1EfI~
		swSaveGame=(Pfile.equals("*.sto")); // save to bitmap;     //~v1EfI~
		swSaveTxt=(Pfile.equals("*.txt")); // save to bitmap;      //~v1EfI~
        savefilename=setTimestampedFilename(Pfile,Pmatchname);     //~v1EfI~
        filterString="";                                           //~v1EfI~
    }                                                              //~v1EfI~
//***********                                                      //~v1E9I~
	public void setSaveFilename(String Pfile) //from filedialogcluipboard//~v1E9I~
    {                                                              //~v1E9I~
        if (Dump.Y) Dump.println("FileDialog:setSaveFilename="+Pfile);//~v1E9I~
        filterString=Pfile;                                        //~v1E9I~
        savefilename="";                                           //~v1E9I~
		swSaveGame=false; // save to bitmap;                       //~v1E9I~
		swSaveTxt=false; // save to bitmap;                        //~v1E9I~
        savefilename=Pfile;                                        //~v1E9I~
        filterString="";                                           //~v1E9I~
    }                                                              //~v1E9I~
//***********                                                      //~1319I~
	public String getFile()                                        //~1319I~
    {                                                              //~1319I~
        if (Dump.Y) Dump.println("FileDialog getFile:"+selectedFilename+",afterdismiss="+afterDismiss);//~1506R~
        if (!afterDismiss)                                         //~1402I~
        	return null;                                           //~1402I~
        if (!swOK)  //openSelected set selectedFilename            //~v1EdI~
        	return null;                                           //~v1EdI~
    	return selectedFilename;                                   //~1319I~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void setFilenameFilter(FilenameFilter Pfilter)         //~1319I~
    {                                                              //~1319I~
    	filterCB=Pfilter;                                          //~1407R~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void setLocation(int Px,int Py)                         //~1319I~
    {                                                              //~1319I~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void show()                                             //~1319I~
    {                                                              //~1319I~
    	if (afterDismiss)                                          //~1324I~
        	return;     //avoid redo dialog doAction schedule      //~1403R~
    	createList(dirname);                                      //~1403I~
    	if (namelist==null)                                        //~1323I~
        	return;                                                //~1323I~
        setTitleFilename(dirname);                                 //~1403M~
    	setList();                                                 //~1403I~
    	super.show();                                              //~1403R~
        afterDismiss=isAfterDismiss();	//true if subthread modal  //~1407I~
        if (Dump.Y) Dump.println("FileDialog:after show,afterDismiss="+afterDismiss);//~1506R~
    }                                                              //~1319I~
//***********                                                      //~v105I~
	public void redraw()                                           //~v105I~
    {                                                              //~v105I~
    	shown=false;	//Dilaog                                       //~v105I~
    	super.show();                                              //~v105I~
        if (Dump.Y) Dump.println("FileDialog:redraw");              //~v105I~
    }                                                              //~v105I~
    //**********************************                           //~1403I~
    //* init ListView                                              //~1403I~
    //**********************************                           //~1403I~
    private void setList()                                         //~1403R~
    {                                                              //~1403I~
        int sz=namelist.length;                                    //~1411R~
        for (int ii=0;ii<sz;ii++)                                      //~1411I~
        {                                                          //~1403I~
        	lister.appendLine(namelist[ii]);                       //~1403R~
        }                                                          //~1403I~
        if (sz>0)                                                  //~1411I~
        {                                                          //~1411I~
	        int pos=(sz==1?0:1);                                   //~1411I~
			lister.setSelection(pos);//current dir for save,top file for load//~1411R~
        }                                                          //~1411I~
        lister.setBackground(bgcolor);	                           //~1403I~
        selectedDirname=dirname;                                   //~1403I~
    }                                                              //~1403I~
    //**********************************                           //~1319I~
    //* file submenu selected;create submenu listview              //~1319I~
    //**********************************                           //~1319I~
    private void createList(String Pdirname)                       //~1402R~
    {                                                              //~1319I~
    	File fileCurDir;                                           //~1403R~
    //************************                                     //~1319I~
    	String path=Pdirname;                                      //~1402R~
        if (Dump.Y) Dump.println("createList path="+path);         //~1506R~
        if (path==null                                             //~1323I~
        ||  path.equals("")                                        //~1323I~
        )                                                          //~1323I~
        {                                                          //~1323I~
        	path=AjagoProp.getPreference(AjagoProp.GAMEFILE,null/*default*/);//~1402I~
            if (path==null)                                        //~1402I~
            	path=AjagoProp.getSDPath("");                          //~1323I~//~1402R~
            if (path==null)                                        //~v1D0I~
            {                                                      //~v1D0I~
	        	path=AjagoProp.getOutputDataFilesPath(AjagoProp.GAMEFILE);//~v1D0I~
            }                                                      //~v1D0I~
            if (path==null)                                        //~1323I~
            {                                                      //~1323I~
            	AjagoView.showToast(R.string.NoSDCard);            //~1403I~
                namelist=null;                                     //~1323I~
                return;                                            //~1323I~
            }                                                      //~1323I~
        }                                                          //~1323I~
        dirname=path;                                              //~1402I~
        try{                                                       //~1319I~
        	if (Dump.Y) Dump.println("FileDialog path="+path);     //~1506R~
            fileCurDir=new File(path);                             //~1403R~
            filelist=fileCurDir.listFiles();   //file and dir      //~1403R~
//          if(filelist==null)                                     //~v1EaR~
            if(filelist==null || filelist.length==0)               //~v1EaI~
            {                                                      //~1319I~
//  			AjagoAlert.simpleAlertDialog(/*AjagoAlertI*/null,path,"No Entry",AjagoAlert.BUTTON_CLOSE);//~v1EaI~
                setEmptyList(path);                                //~v1EaR~
            }                                                      //~1319I~
            else                                                   //~1319I~
            {                                                      //~1319I~
                int count = 1;                                     //~1403M~
//              int filelistctr=0;                                 //~v1DaR~
                String name;                                       //~1403M~
                                                                   //~1403I~
                if (mode==SAVE)                                    //~1403I~
                	count++;                                       //~1403I~
            	name2file=new int[filelist.length+count];          //~1403R~
                                                                   //~1403I~
                for (File file : filelist)                         //~1325I~
                {                                                  //~1325I~
                    name=file.getName();                           //~1325I~
		        	if (Dump.Y) Dump.println("FileDialog file="+name);//~1506R~
                    if(!file.isDirectory())                        //~1404R~
                    {                                              //~1404I~
                      if (!swAllFile)                              //~v1EbI~
                      {                                            //~v1EbI~
                    	if (swSaveBitmap)                          //~1404I~
                        {                                          //~1404I~
							if (!bitmapAccept(fileCurDir,name))	//GoFrame accept() acept sgf or xml only//~1404I~
		                        continue;                          //~1404I~
                        }                                          //~1404I~
                   	 	else                                       //~1404R~
                    	if (swSaveGame)                            //~1407I~
                        {                                          //~1407I~
							if (!saveGameAccept(fileCurDir,name))	//PartnerFrame//~1407I~
		                        continue;                          //~1407I~
                        }                                          //~1407I~
                   	 	else                                       //~1407I~
                    	if (swSaveTxt)                             //~1418I~
                        {                                          //~1418I~
							if (!saveTxtAccept(fileCurDir,name))	//PartnerFrame//~1418I~
		                        continue;                          //~1418I~
                        }                                          //~1418I~
                   	 	else                                       //~1418I~
                        {                                          //~1404I~
                        	if (filterCB!=null && !filterCB.accept(fileCurDir,name))//~1407I~
		                        continue;                          //~1404R~
                        }                                          //~1404I~
                      }                                            //~v1EbI~
                    }                                              //~1404I~
                    count++;                                       //~1325I~
                }                                                  //~1325I~
                namelist=new String[count];            //~1323R~   //~1325R~
                                    //~1319I~
                if (mode==LOAD)                                    //~1403I~
                {                                                  //~1403I~
                	namelist[0]="<--Up";                           //~1403R~
	                count = 1;                                     //~1403I~
                }                                                  //~1403I~
                else                                               //~1403I~
                {                                                  //~1403I~
                	                                               //~1403I~
                	namelist[0]="<--Up";                           //~1403I~
                	namelist[1]="./ ("+dirname+")";                //~1403I~
	                count = 2;                                     //~1403I~
                }                                                  //~1403I~
                Integer[] sortout=sortFileList();                  //~v1DaI~
//              for (File file : filelist)                         //~v1DaR~
                for (int ii=0;ii<sortout.length;ii++)              //~v1DaI~
                {                                                  //~1319I~
                	int idx=sortout[ii].intValue();                //~v1DaI~
                	File file=filelist[idx];                       //~v1DaI~
                    name=file.getName();                           //~1319I~
//                  name2file[count]=filelistctr++;                //~v1DaR~
                    name2file[count]=idx;                          //~v1DaI~
		        	if (Dump.Y) Dump.println("FileDialog file="+name);//~1506R~
                    if(file.isDirectory())                         //~1319I~
                    {                                              //~1319I~
                    	name+="/";	//linux File.pathSeparator;                  //~1319I~//~1325R~
                    }                                              //~1319I~
                    else                                           //~1325I~
                    {                                              //~1402I~
                      if (!swAllFile)                              //~v1EbI~
                      {                                            //~v1EbI~
                    	if (swSaveBitmap)                          //~1404I~
                        {                                          //~1404I~
							if (!bitmapAccept(fileCurDir,name))	//GoFrame accept() acept sgf or xml only//~1404I~
		                        continue;                          //~1404I~
                        }                                          //~1404I~
                   	 	else                                       //~1404I~
                    	if (swSaveGame)                            //~1407I~
                        {                                          //~1407I~
							if (!saveGameAccept(fileCurDir,name))	//PartnerFrame//~1407I~
		                        continue;                          //~1407I~
                        }                                          //~1407I~
                   	 	else                                       //~1407I~
                    	if (swSaveTxt)                             //~1418I~
                        {                                          //~1418I~
							if (!saveTxtAccept(fileCurDir,name))	//PartnerFrame//~1418I~
		                        continue;                          //~1418I~
                        }                                          //~1418I~
                   	 	else                                       //~1418I~
                        {                                          //~1404I~
                    		if (filterCB!=null && !filterCB.accept(fileCurDir,name))//~1407R~
                        		continue;                          //~1404R~
                        }                                          //~1404I~
                      }//!allFile                                  //~v1EbI~
//                      int pos=name.lastIndexOf('.');             //~v1DaR~
//                      if (pos>0)                                 //~v1DaR~
//                      	name=name.substring(0,pos);            //~v1DaR~
                    }                                              //~1402I~
                    namelist[count++]=name;                        //~1319I~
                }                                                  //~1319I~
            }                                                      //~1319I~
        }                                                          //~1319I~
        catch(SecurityException e)                                 //~1319I~
        {                                                          //~1319I~
            AjagoView.showToast(R.string.FailedListDirBySecurity);   //~1403I~
            namelist=null;                                         //~v105I~
        }                                                          //~1319I~
		catch(Exception e)                                         //~1319I~
		{                                                          //~1319I~
            AjagoView.showToast(R.string.FailedListDir);           //~1403I~
            namelist=null;                                         //~v105I~
        }                                                          //~1319I~
    }//createList                                                  //~1402R~
    //*****************************************************************//~v1EaI~
    private void setEmptyList(String Pdirname)                     //~v1EaI~
    {                                                              //~v1EaI~
    //************************                                     //~v1EaI~
    	String path=Pdirname;                                      //~v1EaI~
        if (Dump.Y) Dump.println("setEmptyList path="+path);       //~v1EaI~
        dirname=path;                                              //~v1EaI~
        int count = 1;                                             //~v1EaI~
        if (mode==SAVE)                                            //~v1EaI~
        	count++;                                               //~v1EaI~
        name2file=new int[count];                                  //~v1EaI~
        namelist=new String[count];                                //~v1EaI~
        if (mode==LOAD)                                            //~v1EaI~
        {                                                          //~v1EaI~
            namelist[0]="<--Up";                                   //~v1EaI~
        }                                                          //~v1EaI~
        else                                                       //~v1EaI~
        {                                                          //~v1EaI~
                                                                   //~v1EaI~
            namelist[0]="<--Up";                                   //~v1EaI~
            namelist[1]="./ ("+dirname+")";                        //~v1EaI~
        }                                                          //~v1EaI~
        AjagoView.showToast(R.string.WarningFileDialogNoMember);   //~v1EaI~
    }//createList                                                  //~v1EaI~
    //*****************************************************************//~v1DaI~
    //*sort list by last modified after dir was selected           //~v1DaI~
    //*****************************************************************//~v1DaI~
    private Integer[]  sortFileList()                              //~v1DaI~
    {                                                              //~v1DaI~
    //***********************************************              //~v1DaI~
    	int filectr=filelist.length;                               //~v1DaI~
    	Integer[] idx=new Integer[filectr];                        //~v1DaI~
        for (int ii=0;ii<filectr;ii++)                             //~v1DaI~
        	idx[ii]=ii;                                            //~v1DaI~
    	Arrays.sort(idx,new DataComparator());                     //~v1DaI~
        return idx;                                                //~v1DaI~
    }                                                              //~v1DaI~
    //**********************************                           //~v1DaI~
    class DataComparator implements Comparator<Integer>            //~v1DaI~
    {                                                              //~v1DaI~
        public int compare(Integer P1,Integer P2)                  //~v1DaI~
        {                                                          //~v1DaI~
        	int rc=0;                                              //~v1DaI~
        //*****************************                            //~v1DaI~
        	int i1=P1.intValue();                                  //~v1DaI~
        	int i2=P2.intValue();                                  //~v1DaI~
        	File f1=filelist[i1];                                  //~v1DaI~
        	File f2=filelist[i2];                                  //~v1DaI~
            long dt1=f1.lastModified();                            //~v1DaI~
            long dt2=f2.lastModified();                            //~v1DaI~
            if(f1.isDirectory())                                   //~v1DaI~
            	if (f2.isDirectory())                              //~v1DaI~
                {	                                               //~v1DaI~
                	rc=f1.getName().compareTo(f2.getName());       //~v1DaI~
                }                                                  //~v1DaI~
                else                                               //~v1DaI~
                	rc=-1;	//dir first                            //~v1DaI~
            else                                                   //~v1DaI~
            	if (f2.isDirectory())                              //~v1DaI~
                	rc=1;                                          //~v1DaI~
                else                                               //~v1DaI~
                {                                                  //~v1DaI~
//                	rc=(int)(dt2-dt1);	//reverse seq              //~v1DaI~//~v1E0R~
               	    if (dt2>dt1)	//reverse seq                  //~v1E0I~
                    	rc=1;                                      //~v1E0I~
                    else                                           //~v1E0I~
               	    if (dt2<dt1)	//reverse seq                  //~v1E0I~
                    	rc=-1;                                     //~v1E0I~
                    else                                           //~v1E0I~
                    	rc=0;                                      //~v1E0I~
                }                                                  //~v1DaI~
        	if (rc==0)                                             //~v1DaI~
            	rc=i1-i2;                                          //~v1DaI~
            return rc;                                             //~v1DaI~
        }                                                          //~v1DaI~
    }                                                              //~v1DaI~
    //**********************************                           //~1402I~
    private void updateList(String Pdirname)                    //~1402I~
    {                                                              //~1402I~
    	if (Dump.Y) Dump.println("FileDialog:updateAdapter dir="+Pdirname);//~1506R~
    	createList(Pdirname);                                      //~1402I~
        if (namelist==null)                                        //~1403I~
        	return;                                                //~1403I~
	    setTitleFilename(Pdirname);	//lis will be cleared at next append//~1403I~
        lister.clearList();                                        //~1403I~
    	setList();                                                 //~1403I~
        AjagoProp.putPreference(AjagoProp.GAMEFILE,Pdirname);      //~1403I~
    }//FileList                                                    //~1402R~
	//***************************************                      //~1402I~
    private void setTitleFilename(String Pdirname)                         //~1402I~
    {                                                              //~1402I~
        String dlgtitle;                                           //~1402I~
    //************************                                     //~1402I~
        dlgtitle=title;                                            //~1402I~
        if (mode==LOAD)                                            //~1402I~
        {                                                          //~1402I~
			if (filterString!=null && !filterString.equals(""))               //~1402I~
                dlgtitle+=" ("+filterString+")";                   //~1402I~
			input.setText(dirname);
        }
        else 
        	input.setText(savefilename);//~1402I~
    	setTitle(dlgtitle);  //of Dialog                           //+1403R~                           //~1403I~
    }                                                              //~1402I~
	//***************************************                      //~1402R~
	public void doAction (String o)                                //~1403I~
	{                                                              //~1403I~
    	boolean swshow=false/*,rc*/;                               //~v1D0R~
    //***************************                                  //~v105I~
      try                                                          //~v105I~
      {                                                            //~v105I~
        if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))//~v1EeI~
        {                                                          //~v1EeM~
        	afterDismiss=true;                                     //~v1EeM~
            callbackFDI(true/*afterdismiss*/);                     //~v1EeM~
            return;	//dont clear selectedFilename                  //~v1EeI~
        }                                                          //~v1EeM~
    	selectedFilename=null;                                     //~1403R~
    	selectedDirname="";                                        //~1403I~
		if (o.equals(Global.resourceString("Open")))               //~1403R~
		{                                                          //~1403I~
    		if (openSelected())      //file is selected            //~1403R~
            {                                                      //~v1EdI~
                swOK=true;          //getFile returns selectedFilename//~v1EdI~
	        	dispose();	         //dismiss                     //~1403I~
            }                                                      //~v1EdI~
            else                                                   //~v105I~
            	swshow=true;                                       //~v105I~
            if (Dump.Y) Dump.println("FileDialog Open dir="+dirname+",file="+selectedFilename);//~1506R~
		}                                                          //~1403I~
		else if (o.equals(Global.resourceString("Save")))          //~1403I~
		{                                                          //~1403I~
        	if (mode==LOAD)                                        //~1403I~
 //         	return;	//ignore click                             //~1403I~//~v105R~
            	swshow=true;	//ignore click                     //~v105I~
            else                                                   //~v105I~
        	if (saveSelected()) //valid directory                  //~1403I~
            {                                                      //~v1EdI~
                swOK=true;          //getFile returns selectedFilename//~v1EdI~
	        	dispose();	         //dismiss                     //~1403I~
            }                                                      //~v1EdI~
            else                                                   //~v105I~
            	swshow=true;	//ignore click                     //~v105I~
            if (Dump.Y) Dump.println("FileDialog save dir="+dirname+",file="+selectedFilename);//~1506R~
		}                                                          //~1403I~
		else if (o.equals(Global.resourceString("Delete")))        //~1403I~
		{                                                          //~1403I~
    		deleteSelected();                                       //~1403I~
            if (Dump.Y) Dump.println("FileDialog Delete dir="+dirname+",file="+selectedFilename);//~1506R~
            swshow=true;                                           //~v105I~
		}                                                          //~1403I~
		else if (o.equals(Global.resourceString("Cancel")))        //~1403I~
		{                                                          //~1403I~
            if (Dump.Y) Dump.println("FileDialog Cancel");         //~1506R~
	        dispose();	         //dismiss                         //~1403I~
		}                                                          //~1403I~
		else if (o.equals(Global.resourceString("Help")))          //~v1EjI~
		{                                                          //~v1EjI~
    		new HelpDialog(Global.frame(),R.string.HelpTitle_FileDialog,"FileDialog"/*help text filename*/);//~v1EjR~
		}                                                          //~v1EjI~
		else if (o.equals(AG.resource.getString(R.string.ButtonNullFilter)))//~v1EbI~
		{                                                          //~v1EbI~
    		swapAll();                                             //~v1EbI~
            swshow=true;                                           //~v1EbI~
		}                                                          //~v1EbI~
		else super.doAction(o);	//Cancel                           //~1403I~
      }                                                            //~v105I~
      catch (Exception e)                                          //~v105I~
      {                                                            //~v105I~
      	Dump.println(e,"FileDialog:doAction Exception");           //~v105I~
       	AjagoView.showToast(R.string.Exception);                   //~v105I~
	    dispose();	         //dismiss                             //~v105I~
        swshow=false;                                              //~v105I~
      }                                                            //~v105I~
      if (swshow)                                                  //~v105I~
      	redraw();                                                  //~v105I~
    }                                                              //~1403R~
    //**********************************************************************//~1403I~
    //* open                                                       //~1403I~
    //**********************************************************************//~1403I~
    private boolean openSelected()                                 //~1403I~
    {                                                              //~1403I~
    	int selectedstatus;                                        //~1403I~
        boolean rc=false;                                          //~1403I~
    //***                                                          //~1403I~
    	selectedstatus=chkSelectedFilename();                      //~1403I~
        switch(selectedstatus)                                     //~1403I~
        {                                                          //~1403I~
        case -1:	//no selection                                 //~1403R~
            break;                                                 //~1403I~
        case 0:	//file selected	                                   //~1403I~
		    if (swOpenByClick)                                     //~v1EcI~
            {                                                      //~v1EcI~
    	        selectedDirname=dirname;                           //~v1EjR~
                String fnm=selectedDirname+"/"+selectedFilename;   //~v1EjR~
                openFileViewer(fnm);                               //~v1EjR~
            	break;                                             //~v1EcI~
            }                                                      //~v1EcI~
        	if (mode==LOAD)                                        //~1403I~
            {                                                      //~1403I~
		        selectedDirname=dirname;                           //~1403I~
            	rc=true;	//dismiss                              //~1403R~
            }                                                      //~1403I~
            else                                                   //~1403I~
            	AjagoView.showToast(R.string.NotDir);              //~1403I~
            break;                                                 //~1403I~
        default:	//dir or up                                    //~1403R~
        	if (mode==SAVE)                                        //~1404I~
        		savefilename=input.getText(true);  //may updated on input field//~1411R~
        	updateList(updownpath);                                //~1403I~
        }                                                          //~1403I~
        return rc;                                                 //~1403I~
    }                                                              //~1403I~
    //**********************************************************************//~1402I~
    //* save                                                       //~1403R~
    //**********************************************************************//~1402I~
    private boolean saveSelected()                                 //~1403R~
    {                                                              //~1402I~
    	int selectedstatus;                                        //~1403I~
        String savepath=null;                                      //~1403I~
        boolean mkdir=false;                                       //~1403I~
    //***                                                          //~1403I~
    	selectedstatus=chkSelectedFilename();                      //~1403I~
        switch(selectedstatus)                                     //~1403I~
        {                                                          //~1403I~
        case -1:                                                   //~1403I~
            savepath=dirname;                                      //~1403I~
            break;                                                 //~1403I~
        case 0:	//file selected                                    //~1403I~
            AjagoView.showToast(R.string.NotDir);                  //~1403I~
            return false;                                          //~1403I~                                               //~1403I~
        default:                                                   //~1403I~
        //case 1:	//dir selected                                 //~1403R~
        //case 2:	//up slected                                   //~1403R~
        //case 3:	//current dir                                  //~1403I~
            savepath=updownpath;                                   //~1403I~
        }                                                          //~1403I~
	    if (Dump.Y) Dump.println("FileDialog save savepath="+savepath);//~1506R~
        String newpath=input.getText(true).toString();             //~1411R~
        if (newpath==null || newpath.equals(""))                   //~1403R~
        {                                                          //~v1E9I~
            AjagoView.showToast(R.string.ErrNoInputSaveFileName);  //~v1E9I~
        	return false;                                          //~1403I~
        }                                                          //~v1E9I~
        int pos=newpath.lastIndexOf('/');                          //~1403I~
        if (pos==0)                                                //~1403I~
        {                                                          //~1403I~
            savepath="/";                                          //~1403I~
            selectedFilename=newpath.substring(1);                 //~1403I~
        }                                                          //~1403I~
        else                                                       //~1403I~
        if (pos>0)                                                 //~1403I~
        {                                                          //~1403I~
            savepath+="/"+newpath.substring(0,pos);                //~1403I~
            selectedFilename=newpath.substring(pos+1);             //~1403I~
            if (selectedFilename.length()==0)                            //~1403I~
                return false;                                      //~1403I~
            mkdir=true;                                            //~1403I~
        }                                                          //~1403I~
        else                                                       //~1403I~
            selectedFilename=newpath;                              //~1403I~
        if (Dump.Y) Dump.println("FileDialog save newpath="+savepath+"file="+selectedFilename);//~1506R~
        if (mkdir)                                                 //~1403I~
        {                                                          //~1403R~
            if (!AjagoProp.makePath(savepath))                     //~1403R~
            {                                                      //~1403R~
                AjagoView.showToast(R.string.FailedMkdir);         //~1403R~
                return false;                                      //~1403R~
            }                                                      //~1403R~
        }                                                          //~1403R~
        selectedDirname=savepath;                                  //~1403R~
        if (Dump.Y) Dump.println("FileDialog save path="+selectedDirname+",file="+selectedFilename);//~1506R~
      if (callback!=null)                                          //~v1E9I~
      {                                                            //~v1E9I~
//    	int rc=callback.fileDialogSaveCallback(this,selectedDirname+"/"+selectedFilename);//~v1EeR~
      	int rc=callbackFDI(false/*afterdismiss*/);                  //~v1EeR~
        return (rc==0);	//if err,not dispose                       //~v1E9I~
      }                                                            //~v1E9I~
      else                                                         //~v1E9I~
      {                                                            //~v1E9I~
        AjagoView.showToast(R.string.Saved,selectedDirname+"/"+selectedFilename);//~v105I~
        return true;                                               //~1402I~
      }                                                            //~v1E9I~
    }                                                              //~1402I~
    //**********************************************************************//~1402I~
    //* delete                                                     //~1403R~
    //**********************************************************************//~1402I~
    private boolean deleteSelected()                                  //~1402I~
    {                                                              //~1402I~
    	int selectedstatus=chkSelectedFilename();                      //~1403I~
        boolean rc;
    	switch(selectedstatus)                                     //~1403I~
        {                                                          //~1403I~
        case 0:	//file selected                                    //~1403I~
        	break;                                                 //~1403I~
        case 1:	//dir selected,delete if empty                     //~1403I~
        	File[] fl=selectedFile.listFiles();                         //~1403I~
            if (fl!=null && fl.length!=0)                          //~1403I~
            {                                                      //~1403I~
            	AjagoView.showToast(R.string.NotEmptyDir);         //~1403I~
                return false;                                      //~1403I~
            }                                                      //~1403I~
            break;                                                 //~1403I~
        default:                                                   //~1403I~
            return false;                                          //~1403I~
        }                                                          //~1403I~
    	try                                                        //~1402I~
        {                                                          //~1402I~
        	                                                       //~1403I~
        	int cpos=lister.getSelectedPos();                      //~v1E8I~
        	selectedFile.delete();                                 //~1403R~
    		selectedFilename=null; //avoid open at GoFrame after dismiss by Back button//~v1EdI~
	        if (Dump.Y) Dump.println("FileDialog deleted file="+selectedFile.getName());//~1506R~
		    updateList(dirname);
            setSelected(cpos);                                     //~v1E8I~
		    rc=true;//~1402I~
        }                                                          //~1402I~
        catch (Exception e)                                        //~1402I~
        {                                                          //~1402I~
        	Dump.println(e,"FileDialog:deleteSelected Exception"); //~1402I~
    		if (selectedstatus==0)                                 //~1403I~
            	AjagoView.showToast(R.string.FailedDelete);        //~1403I~
            else                                                   //~1403I~
            	AjagoView.showToast(R.string.FailedRemoveDir);     //~1403I~
        	rc=false;
        }
        return rc;//~1402I~
    }                                                              //~1402I~
    //**********************************************************************//~1319I~
    //*chk list selection                                          //~1403I~
    //*rc:-1:err,0 file selected,1:dir selected,2:"up" selected 3:save to currentdir//~1403R~
    //**********************************************************************//~1403I~
    private int chkSelectedFilename()                              //~1403R~
    {                                                              //~1319I~
        int pos,rc=0;                                              //~1403I~
    //************                                                 //~1403R~
    	selectedFilename=null;                                     //~1403I~
        pos=lister.getSelectedPos();                               //~1403I~
        if (Dump.Y) Dump.println("FileDialog selectedPos="+pos);   //~1506R~
        if (pos<0)                                                 //~1403I~
        	return -1;                                             //~1403I~
        if (pos==0)                                                //~1403R~
        {                                                          //~1403R~
            updownpath=(new File(dirname)).getParent();            //~1403R~
            rc=2;                                                  //~1403I~
        }                                                          //~1403R~
        else                                                       //~1403I~
        if (mode==SAVE && pos==1)	//currentdir                   //~1403I~
        {                                                          //~1403I~
            updownpath=dirname;                                    //~1403I~
        	rc=3;                                                   //~1403I~
        }                                                          //~1403I~
        else                                                       //~1403R~
        {                                                          //~1403R~
            int filelistpos=name2file[pos];                        //~1403R~
            selectedFile=filelist[filelistpos];                             //~1319I~//~1403R~
            if(selectedFile.isDirectory())                                 //~1403R~
            {                                                      //~1403R~
                updownpath=selectedFile.getAbsolutePath();                   //~1319I~//~1324R~//~1403R~
                rc=1;                                              //~1403I~
            }                                                      //~1403R~
            else                                                   //~1403R~
            {                                                      //~1403R~
            	selectedFilename=selectedFile.getName();                   //~1403R~
            }                                                      //~1403R~
        }
        
        if (Dump.Y) Dump.println("FileDialog chkSelectedfile rc="+rc+",name="+selectedFilename);//~1319I~//~1506R~
        return rc;
    }
	public boolean bitmapAccept(File dir, String name)             //~1404I~
	{                                                              //~1404I~
		if (name.endsWith("."+Global.getParameter("extension","bmp"))) return true;//~1404I~
		else return false;                                         //~1404I~
	}                                                              //~1404I~
	public boolean saveGameAccept(File dir,String name)            //~1407I~
	{                                                              //~1407I~
		if (name.endsWith("."+Global.getParameter("extension","sto"))) return true;//~1407I~
		else return false;                                         //~1407I~
	}                                                              //~1407I~
	public boolean saveTxtAccept(File dir,String name)             //~1418I~
	{                                                              //~1418I~
		if (name.endsWith("."+Global.getParameter("extension","txt"))) return true;//~1418I~
		else return false;                                         //~1418I~
	}                                                              //~1418I~
//***********                                                      //~v1D3I~
	private String setDefaultSaveFilename(String Pfile)            //~v1D3I~
    {                                                              //~v1D3I~
        if (Dump.Y) Dump.println("FileDialog setDefaultSAveFilename inp="+Pfile);//~v1D3I~
        String defname,matchtitle;                                  //~v1D3I~
        matchtitle=getMatchTitle(Pfile);                           //~v1D3R~
//      defname=AjagoUtils.getTimeStamp(AjagoUtils.TS_DATE_TIME) //yyyymmdd//~v1DnR~
        defname=AjagoUtils.getTimeStamp(AjagoUtils.TS_YYMMDDHHMM)////~v1DnI~
        		+"."+matchtitle;                                   //~v1D3R~
        return defname;                                            //~v1D3I~
    }                                                              //~v1D3I~
//***********                                                      //~v1EfI~
	private String setTimestampedFilename(String Pfile,String Pmatchname)//~v1EfI~
    {                                                              //~v1EfI~
        if (Dump.Y) Dump.println("FileDialog setTimestampedFilename inp="+Pfile+","+Pmatchname);//~v1EfI~
        String defname;                                            //~v1EfI~
        defname=getTimestampPrefix()+"_"+Pmatchname+Pfile.substring(1);//~v1EfI~
        defname=defname.replace(" ","_");                          //~v1EfI~
        return defname;                                            //~v1EfI~
    }                                                              //~v1EfI~
//***********                                                      //~v1EfI~
	private String getTimestampPrefix()                            //~v1EfI~
    {                                                              //~v1EfI~
        return AjagoUtils.getTimeStamp(AjagoUtils.TS_YYMMDDHHMM);  //~v1EfI~
    }                                                              //~v1EfI~
//***********                                                      //~v1D3I~
	private String getMatchTitle(String Pfile)                     //~v1D3R~
    {                                                              //~v1D3I~
        String matchtitle;                                         //~v1D3I~
        if (!Pfile.startsWith("*."))                               //~v1D3M~
        	return Pfile;                                          //~v1D3M~
        if (Dump.Y) Dump.println("FileDialog getMatchTitle");      //~v1D3I~
//        if (parentFrame instanceof PartnerFrame)                 //~v1D3R~
//        {                                                        //~v1D3R~
//            matchtitle=(String)AG.activity.getTitle();           //~v1D3R~
//        }                                                        //~v1D3R~
//        else                                                     //~v1D3R~
//        if (parentFrame instanceof PartnerFrame)                 //~v1D3R~
//        {                                                        //~v1D3R~
//            matchtitle=(String)AG.activity.getTitle();           //~v1D3R~
//        }                                                        //~v1D3R~
//        else                                                     //~v1D3R~
//            matchtitle=Global.getParameter("local","Local");     //~v1D3R~
		matchtitle=(String)AG.activity.getTitle()+Pfile.substring(1);//~v1D3R~
        matchtitle=matchtitle.replace(" ","_");                    //~v1D3I~
        return matchtitle;                                         //~v1D3I~
    }                                                              //~v1D3I~
    //**************************************                       //~v1E8I~
    private void setSelected(int Ppos)                             //~v1E8I~
    {                                                              //~v1E8I~
    	int pos=Ppos;                                              //~v1E8I~
        if (namelist==null)                                        //~v1E8I~
            return;                                                //~v1E8I~
    	if (Ppos>=namelist.length)                                 //~v1E8I~
        	pos=namelist.length-1;                                 //~v1E8I~
        if (pos<0)                                                 //~v1E8I~
        	pos=0;                                                 //~v1E8I~
    	lister.setSelection(pos);                                  //~v1E8I~
        if (Dump.Y) Dump.println("FileDialog:setSelection Ppos="+Ppos+",pos="+pos+",list="+namelist.length);//~v1E8I~
    }                                                              //~v1E8I~
    //**************************************                       //~v1E9I~
    public void setCallback(FileDialogI Pfdi)                     //~v1E9I~
    {                                                              //~v1E9I~
    	callback=Pfdi;                                             //~v1E9I~
    }                                                              //~v1E9I~
    //**************************************                       //~v1EeI~
    public void setCallback(FileDialogI Pfdi,boolean PcallbackAfterDismiss)//~v1EeI~
    {                                                              //~v1EeI~
    	callback=Pfdi;                                             //~v1EeI~
        swCallbackAfterDismiss=PcallbackAfterDismiss;              //~v1EeI~
        if (swCallbackAfterDismiss)                                //~v1EeI~
	        setDismissActionListener(this/*DoActionListener*/);	//callback from DialogClosed at dismiss//~v1EeM~
    }                                                              //~v1EeI~
    //**************************************                       //~v1EbI~
    private Drawable getBGC(View Pview)                            //~v1EbI~
    {                                                              //~v1EbI~
        Drawable dr=Pview.getBackground();                         //~v1EbI~
        if (Dump.Y) Dump.println("getBGC drawable="+dr.toString());//~v1EbI~
		return dr;                                                 //~v1EbI~
    }                                                              //~v1EbI~
    //**********************************************************************//~v1EbI~
    //* delete multiple                                            //~v1EbI~
    //**********************************************************************//~v1EbI~
    private boolean swapAll()                                      //~v1EbI~
    {                                                              //~v1EbI~
    	swAllFile=!swAllFile;                                      //~v1EbI~
        Color bgcol=Color.yellow;                                  //~v1EbI~
        if (swAllFile)                                             //~v1EbI~
	    	buttonAll.setBackground(buttonAll.androidButton,bgcol);//~v1EbI~
        else                                                       //~v1EbI~
	    	buttonAll.setBackground(btnBackground);      //~v1EbR~
	    if (Dump.Y) Dump.println("FileDialog:swapAll new status="+swAllFile);//~v1EbI~
        if (dirname==null)                                         //~v1EbI~
        	return true;                                           //~v1EbI~
        updownpath=dirname;	//currentdir                           //~v1EbI~
        updateList(updownpath);                                    //~v1EbI~
        return true;                                               //~v1EbI~
    }                                                              //~v1EbI~
	//** Interface:ListI ***************************************   //~v1EcI~
    public void onListItemClicked(int Pcurpos,int Pselectedpos)    //~v1EcI~
    {                                                              //~v1EcI~
    	String o;                                                  //~v1EcI~
    	if (Dump.Y) Dump.println("FileDialog:onListItemClicked cpos="+Pcurpos+",selectedpos="+Pselectedpos);//~v1EcI~
    	if (Pcurpos==Pselectedpos)                                 //~v1EcI~
        {                                                          //~v1EcI~
			o=Global.resourceString("Open");                       //~v1EcI~
		    swOpenByClick=true;                                    //~v1EcI~
			doAction(o);                                           //~v1EcI~
		    swOpenByClick=false;                                   //~v1EcI~
        }                                                          //~v1EcI~
    }                                                              //~v1EcI~
    //******************************************************************//~v1EeI~
    //*after dismiss                                               //~v1EeI~
    //*rc:0 dismiss at callback before dismiss for save            //~v1EeI~
    //******************************************************************//~v1EeI~
    private int callbackFDI(boolean Pdismiss)                      //~v1EeI~
    {                                                              //~v1EeI~
    	int rc=0;                                                  //~v1EeI~
      	if (callback==null)                                        //~v1EeI~
        	return 0;                                              //~v1EeR~
	    if (Pdismiss)	//called at dismiss                        //~v1EeI~
        {                                                          //~v1EeI~
	        if (!swCallbackAfterDismiss)	//not requested callback after dismiss//~v1EeI~
            	return 0;                                          //~v1EeI~
	        if (!swOK)  //OK if dispose at Open/Save               //~v1EeI~
    	        return 0; //dismiss by back button                 //~v1EeI~
        }                                                          //~v1EeI~
        else	//called at doAction                               //~v1EeI~
        {                                                          //~v1EeI~
	        if (swCallbackAfterDismiss)	//requested callback after dismiss//~v1EeI~
            	return 0;                                          //~v1EeI~
        }                                                          //~v1EeI~
        if (mode==LOAD)                                            //~v1EeI~
      		rc=callback.fileDialogLoadCallback(this,selectedDirname+"/"+selectedFilename);//~v1EeI~
        else                                                       //~v1EeI~
      		rc=callback.fileDialogSaveCallback(this,selectedDirname+"/"+selectedFilename);//~v1EeI~
        return rc;	//if err,not dispose                           //~v1EeR~
    }                                                              //~v1EeI~
    //**************************************                       //~v1EjI~
    private void openFileViewer(String Pfnm)                       //~v1EjI~
    {                                                              //~v1EjI~
        if (Dump.Y) Dump.println("FileDialog:openFileViewer:"+Pfnm);//~v1EjI~
        new FileViewer(this,Pfnm);                                 //~v1EjI~
    }                                                              //~v1EjI~
}//class                                                           //~1318R~
