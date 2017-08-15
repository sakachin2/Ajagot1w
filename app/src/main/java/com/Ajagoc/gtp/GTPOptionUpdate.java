//*CID://+v1DhR~:                                   update#=  181; //~v1DhR~
//***********************************************************************//~@@@1I~
//v1Dh 2014/11/12 add predefined parameter settion for pach/fuego match-settiong dialog//~v1DhI~
//***********************************************************************//~@@@1I~
package com.Ajagoc.gtp;                                            //~v1B6R~

//import java.util.*;

import android.view.ViewGroup;
import android.widget.EditText;
import com.Ajagoc.R;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;
//import jagoclient.gui.*;
                                                                   //~v1B6I~
import com.axe.AxeDialog;                                   //~v1B6R~
import com.Ajagoc.AG;                                              //~v1B6I~

public class GTPOptionUpdate extends AxeDialog                       //~v1B6I~//~v1DhR~
{                                                                  //~v1B6R~
 	static final int LAYOUT=R.layout.dialoggtpoptionupdate;  //~v1C1I~//~v1DhR~
    static final int TITLE =R.string.DialogTitle_gtpoptionupdate;//~v1C1I~//~v1DhR~
    static final String PARM_FLAG_TIME="-t ";                       //~v1DhI~
    static final int PARM_FLAG_TIME_SZ=3;                      //~v1DhI~
    static final String PARM_FLAG_THREAD="threads=";               //~v1DhR~
    static final int PARM_FLAG_THREAD_SZ=8;                        //~v1DhI~
    private EditText etOption,etTime,etThread;//~v1C1I~            //~v1DhR~
    private String textparm_time="",textparm_thread="";            //+v1DhR~
    private int postime;                                           //~v1DhI~
	GTPConnection gtpConnection;                                   //~v1DhI~
//*******************                                              //~v1DhI~
	public GTPOptionUpdate(GTPConnection PgtpConnection,EditText PetOption)//~v1DhR~
    {                                                              //~v1B6I~
        super(LAYOUT);                                             //~v1B6I~
        etOption=PetOption;                                        //~v1DhI~
        gtpConnection=PgtpConnection;                              //~v1DhI~
        String title=AG.resource.getString(TITLE);                 //~v1DhI~
		showDialog(title);     //callback setupDialogExtend()      //~v1B6I~
	}
//*********                                                        //~v1B6I~
	@Override                                                      //~v1B6I~
    protected void onDismiss()                                     //~v1B6I~
    {                                                              //~v1B6I~
    }                                                              //~v1B6I~
//*********                                                        //~v1B6I~
	@Override                                                      //~v1B6I~
	protected void setupDialogExtend(ViewGroup PlayoutView)        //~v1B6I~
    {                                                              //~v1B6I~
	    etTime=(EditText)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION_TIME);//~v1B6R~//~v1DhR~
	    etThread=(EditText)PlayoutView.findViewById(R.id.GTP_COMMANDOPTION_THREAD);//~v1B6I~//~v1DhR~
        getCurrentParm();                                          //~v1DhI~
        etTime.setText(textparm_time);                             //~v1DhR~
        etThread.setText(textparm_thread);                         //~v1DhI~
    }//setupDialogExtend                                           //~v1B6R~
    //****************************************                     //~v1B6I~
    //*Button                                                      //~v1B6I~
    //****************************************                     //~v1B6I~
	@Override                                                      //~v1B6I~
    protected boolean onClickClose()                               //~v1DhR~
    {                                                              //~v1B6I~
    	boolean rc;   //not dismiss at return                //~v1B6I~//~v1DhR~
    //****************                                             //~v1B6I~
        rc=updateCommandParm();                                    //~v1DhR~
        return rc;                                                 //~v1B6I~
    }                                                              //~v1B6I~
//******************                                               //~v1B6I~
	@Override                                                      //~v1B6I~
    protected boolean onClickHelp()                                //~v1B6I~
    {                                                              //~v1B6I~
        new HelpDialog(Global.frame(),"pachioptionupdate");                    //~v1C2I~//~v1DhR~
        return false;	//no dismiss                               //~v1B6I~
    }                                                              //~v1B6I~
//************************************************************************//~v1B6I~
	protected String getPgmOption()                                //~v1DhI~
    {                                                              //~v1DhI~
        String option=etOption.getText().toString();               //~v1DhI~
        option=option.trim();                                      //~v1DhI~
        return option;                                             //~v1DhI~
	}                                                              //~v1DhI~
//************************************************************************//~v1DhI~
	protected void getCurrentParm()                                //~v1DhI~
    {                                                              //~v1DhI~
    	String time,thread,opt;                                    //~v1DhI~
        int pos,endpos,posnext;                                    //~v1DhR~
    //****************                                             //~v1DhI~
        opt=getPgmOption().trim();                                 //~v1DhR~
    //* -t                                                         //~v1DhI~
    	postime=-1;                                                //~v1DhI~
    	for (posnext=0;;)                                          //~v1DhR~
        {                                                          //~v1DhI~
            pos=opt.indexOf(PARM_FLAG_TIME,posnext);               //~v1DhR~
            if (pos<0)                                             //~v1DhR~
            	break;                                             //~v1DhI~
            posnext=pos+PARM_FLAG_TIME_SZ;                         //~v1DhI~
            time=opt.substring(posnext).trim();                    //~v1DhR~
            if (time.charAt(0)<'0' || time.charAt(0)>'9')          //~v1DhI~
            	continue;                                          //~v1DhI~
            endpos=time.indexOf(' ');                              //~v1DhR~
            if (endpos>0)                                          //~v1DhR~
                time=time.substring(0,endpos);                     //~v1DhR~
            textparm_time=time;                                    //~v1DhR~
            postime=pos;                                           //~v1DhI~
            break;                                                 //~v1DhI~
        }                                                          //~v1DhI~
    //* threads                                                    //~v1DhR~
        pos=opt.indexOf(PARM_FLAG_THREAD);                         //~v1DhR~
        if (pos>=0)                                                //~v1DhI~
        {                                                          //~v1DhI~
        	thread=opt.substring(pos+PARM_FLAG_THREAD_SZ);         //~v1DhR~
            endpos=thread.indexOf(' ');                            //~v1DhI~
            if (endpos>0)                                          //~v1DhI~
            	thread=thread.substring(0,endpos);                 //~v1DhI~
            textparm_thread=thread;                                //~v1DhI~
        }                                                          //~v1DhI~
	}                                                              //~v1DhI~
//************************************************************************//~v1DhI~
	protected boolean updateCommandParm()                          //~v1DhR~
    {                                                              //~v1DhI~
    	String opt,newtime,newthread,time;                         //~v1DhR~
        int pos,endpos;                                            //~v1DhI~
    //****************                                             //~v1DhI~
        opt=getPgmOption().trim();                                 //~v1DhI~
        newtime=etTime.getText().toString().trim();                            //~v1DhI~
        newthread=etThread.getText().toString().trim();                        //~v1DhI~
    //* -t                                                         //~v1DhI~
        if (!newtime.equals(textparm_time))                        //~v1DhI~
        {                                                          //~v1DhI~
	        if (!newtime.equals(""))                               //~v1DhI~
    	    	newtime=PARM_FLAG_TIME+newtime;                    //~v1DhI~
        	pos=postime;                                           //~v1DhR~
       		if (pos>=0)                                            //~v1DhI~
        	{                                                      //~v1DhI~
	        	time=opt.substring(pos+PARM_FLAG_TIME_SZ).trim();  //~v1DhR~
            	endpos=time.indexOf(' ');                          //~v1DhR~
            	if (endpos>0)                                      //~v1DhR~
            		opt=opt.substring(0,pos)+newtime+time.substring(endpos);//~v1DhR~
                else                                               //~v1DhR~
            		opt=opt.substring(0,pos)+newtime;              //~v1DhR~
            }                                                      //~v1DhI~
            else                                                   //~v1DhI~
            	opt=opt+" "+newtime;                               //~v1DhR~
        }                                                          //~v1DhI~
    //* threads                                                    //~v1DhR~
        if (!newthread.equals(textparm_thread))                    //~v1DhI~
        {                                                          //~v1DhI~
	        if (!newthread.equals(""))                             //~v1DhI~
    	    	newthread=PARM_FLAG_THREAD+newthread;              //~v1DhI~
	        pos=opt.indexOf(PARM_FLAG_THREAD);                     //~v1DhR~
       		if (pos>=0)                                            //~v1DhI~
        	{                                                      //~v1DhI~
            	endpos=opt.indexOf(' ',pos+PARM_FLAG_THREAD_SZ);   //~v1DhR~
            	if (endpos>0)                                      //~v1DhI~
            		opt=opt.substring(0,pos)+newthread+opt.substring(endpos);//~v1DhR~
                else                                               //~v1DhI~
            		opt=opt.substring(0,pos)+newthread;            //~v1DhR~
            }                                                      //~v1DhI~
            else                                                   //~v1DhI~
            	opt=opt+" "+newthread;                             //~v1DhR~
        }                                                          //~v1DhI~
        etOption.setText(opt);                                     //~v1DhI~
        return true;                                               //~v1DhI~
	}                                                              //~v1DhI~
}
