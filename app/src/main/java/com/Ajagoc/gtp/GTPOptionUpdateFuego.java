//*CID://+v1DhR~:                                   update#=  200; //~v1DhR~
//***********************************************************************//~@@@1I~
//v1Dh 2014/11/12 add predefined parameter settion for pach/fuego match-settiong dialog//~v1DhI~
//***********************************************************************//~@@@1I~
package com.Ajagoc.gtp;                                            //~v1B6R~

//import java.util.*;

import android.view.ViewGroup;
import android.widget.EditText;

import com.Ajagoc.AjagoUtils;
import com.Ajagoc.R;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;
//import jagoclient.gui.*;
                                                                   //~v1B6I~
import com.axe.AxeDialog;                                   //~v1B6R~
import com.Ajagoc.AG;                                              //~v1B6I~

public class GTPOptionUpdateFuego extends AxeDialog                       //~v1B6I~//~v1DhR~
{                                                                  //~v1B6R~
 	static final int LAYOUT=R.layout.dialoggtpoptionupdate;  //~v1C1I~//~v1DhR~
    static final int TITLE =R.string.DialogTitle_gtpoptionupdateFuego;//~v1C1I~//~v1DhR~
    static final String PARM_FLAG_CONFIG="--config ";              //~v1DhI~
    static final int PARM_FLAG_CONFIG_SZ=9;                        //~v1DhI~
    static final String PARM_FLAG_TIME_GROUP="go_param ";          //~v1DhI~
    static final int PARM_FLAG_TIME_GROUP_SZ=9;                    //~v1DhI~
    static final String PARM_FLAG_TIME="timelimit ";               //~v1DhI~
    static final int PARM_FLAG_TIME_SZ=10;                         //~v1DhR~
    static final String PARM_FLAG_THREAD_GROUP="uct_param_search ";//~v1DhR~
    static final int PARM_FLAG_THREAD_GROUP_SZ=17;                 //~v1DhI~
    static final String PARM_FLAG_THREAD="number_threads ";        //~v1DhI~
    static final int PARM_FLAG_THREAD_SZ=15;                       //~v1DhR~
    private EditText etOption,etTime,etThread;//~v1C1I~            //~v1DhR~
    private String textparm_time="",textparm_thread="",cfgdata=""; //~v1DhR~
    private int postime=-1,posthread=-1,endpostime,endposthread,poscfg,poscfgdata=-1,endposcfgdata;//~v1DhR~
	GTPConnection gtpConnection;                                   //~v1DhI~
//*******************                                              //~v1DhI~
	public GTPOptionUpdateFuego(GTPConnection PgtpConnection,EditText PetOption)//~v1DhR~
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
        new HelpDialog(Global.frame(),"fuegooptionupdate");                    //~v1C2I~//~v1DhR~
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
	protected void getCurrentParm()                                //~v1DhR~
    {                                                              //~v1DhI~
    	String opt;                                           //~v1DhR~
        int pos,endpos,posnext;                                    //~v1DhR~
    //****************                                             //~v1DhI~
        opt=getPgmOption().trim();                                 //~v1DhR~
    //* --config                                                   //~v1DhR~
        poscfg=opt.indexOf(PARM_FLAG_CONFIG);                      //~v1DhI~
        if (poscfg<0)                                              //~v1DhI~
        	return;                                                //~v1DhI~
        pos=opt.indexOf('\"',poscfg+PARM_FLAG_CONFIG_SZ);          //~v1DhI~
        if (pos<0)                                                 //~v1DhI~
            return;                                                //~v1DhI~
        poscfgdata=pos+1;                                          //~v1DhI~
        endpos=opt.indexOf('\"',poscfgdata);                       //~v1DhI~
        if (endpos<0)                                              //~v1DhI~
            return;                                                //~v1DhI~
        endposcfgdata=endpos;                                      //~v1DhI~
        cfgdata=opt.substring(poscfgdata,endposcfgdata).trim();    //~v1DhI~
	//timelimit                                                    //~v1DhI~
    	for (posnext=0;;)                                          //~v1DhI~
        {                                                          //~v1DhI~
			postime=cfgdata.indexOf(PARM_FLAG_TIME_GROUP,posnext); //~v1DhR~
            if (postime<0)                                         //~v1DhI~
            	break;                                             //~v1DhI~
            posnext=postime+PARM_FLAG_TIME_GROUP_SZ;               //~v1DhR~
            posnext+=AjagoUtils.strspnc(cfgdata,posnext,' ');      //~v1DhI~
            if (!cfgdata.startsWith(PARM_FLAG_TIME,posnext))       //~v1DhI~
            	continue;                                          //~v1DhI~
            posnext+=PARM_FLAG_TIME_SZ;                            //~v1DhR~
            posnext+=AjagoUtils.strspnc(cfgdata,posnext,' ');      //~v1DhI~
            endpostime=AjagoUtils.strpbrk(cfgdata,posnext,";");   //~v1DhI~
            if (endpostime>=0)                                     //~v1DhI~
	        	textparm_time=cfgdata.substring(posnext,endpostime);//~v1DhR~
            else                                                   //~v1DhI~
	        	textparm_time=cfgdata.substring(posnext);         //~v1DhR~
            break;                                                 //~v1DhI~
        }                                                          //~v1DhI~
	//thread                                                       //~v1DhI~
    	for (posnext=0;;)                                          //~v1DhI~
        {                                                          //~v1DhI~
			posthread=cfgdata.indexOf(PARM_FLAG_THREAD_GROUP,posnext);//~v1DhI~
            if (posthread<0)                                       //~v1DhI~
            	break;                                             //~v1DhI~
            posnext=posthread+PARM_FLAG_THREAD_GROUP_SZ;           //~v1DhR~
            posnext+=AjagoUtils.strspnc(cfgdata,posnext,' ');      //~v1DhI~
            if (!cfgdata.startsWith(PARM_FLAG_THREAD,posnext))     //~v1DhI~
            	continue;                                          //~v1DhI~
            posnext+=PARM_FLAG_THREAD_SZ;                          //~v1DhI~
            posnext+=AjagoUtils.strspnc(cfgdata,posnext,' ');      //~v1DhI~
            endposthread=AjagoUtils.strpbrk(cfgdata,posnext,";");  //~v1DhI~
            if (endposthread>=0)                                   //~v1DhI~
	        	textparm_thread=cfgdata.substring(posnext,endposthread);//~v1DhI~
            else                                                   //~v1DhI~
	        	textparm_thread=cfgdata.substring(posnext);       //~v1DhI~
            break;                                                 //~v1DhI~
        }                                                          //~v1DhI~
	}                                                              //~v1DhI~
//************************************************************************//~v1DhI~
	protected boolean updateCommandParm()                          //~v1DhR~
    {                                                              //~v1DhI~
    	String opt,newtime,newthread;                         //~v1DhR~
    //****************                                             //~v1DhI~
        opt=getPgmOption().trim();                                 //~v1DhI~
        newtime=etTime.getText().toString().trim();                            //~v1DhI~
        newthread=etThread.getText().toString().trim();                        //~v1DhI~
        if (postime>=0 && posthread>=0 && postime<posthread)       //~v1DhI~
        {                                                          //~v1DhI~
            repthread(newthread);                                  //~v1DhI~
            reptime(newtime);                                      //~v1DhI~
        }                                                          //~v1DhI~
        else                                                       //~v1DhI~
        {                                                          //~v1DhI~
            reptime(newtime);                                      //~v1DhR~
            repthread(newthread);                                  //~v1DhM~
        }                                                          //~v1DhI~
        if (cfgdata.endsWith(";"))                                 //~v1DhI~
        	cfgdata=cfgdata.substring(0,cfgdata.length()-1).trim();//~v1DhI~
        if (poscfgdata>=0)                                         //~v1DhI~
        	if (cfgdata.equals(""))                                //~v1DhI~
        		if (opt.substring(endposcfgdata).length()>1)       //~v1DhI~
	        		opt=opt.substring(0,poscfg)+opt.substring(endposcfgdata);//~v1DhI~
                else                                               //~v1DhI~
    	    		opt=opt.substring(0,poscfg);                   //~v1DhI~
            else                                                   //~v1DhI~
        		opt=opt.substring(0,poscfgdata)+cfgdata+opt.substring(endposcfgdata);//~v1DhR~
        else                                                       //~v1DhI~
        	opt+=" "+PARM_FLAG_CONFIG+"\""+cfgdata+"\"";           //~v1DhI~
        etOption.setText(opt);                                     //~v1DhI~
        return true;                                               //~v1DhI~
	}                                                              //~v1DhI~
    //*************************************                        //~v1DhI~
    private void reptime(String newtime)                           //~v1DhI~
    {                                                              //~v1DhI~
        if (!newtime.equals(textparm_time))                        //~v1DhI~
        {                                                          //~v1DhI~
	        if (!newtime.equals(""))                               //~v1DhI~
               	newtime=PARM_FLAG_TIME_GROUP+PARM_FLAG_TIME+newtime;//~v1DhI~
       		if (postime>=0)                                        //~v1DhI~
            {                                                      //~v1DhI~
            	if (endpostime>=0)                                 //~v1DhI~
		        	cfgdata=cfgdata.substring(0,postime)+newtime+cfgdata.substring(endpostime);//~v1DhI~
                else                                               //~v1DhI~
		        	cfgdata=cfgdata.substring(0,postime)+newtime;  //~v1DhI~
                if (cfgdata.startsWith(";"))                       //~v1DhI~
                    cfgdata=cfgdata.substring(1).trim();           //~v1DhI~
            }                                                      //~v1DhI~
            else                                                   //~v1DhI~
            {                                                      //~v1DhI~
	        	if (!newtime.equals(""))                           //~v1DhI~
    	    		if (cfgdata.equals(""))                        //+v1DhR~
    	    			cfgdata+=newtime;                          //+v1DhI~
                    else                                           //+v1DhI~
                    if (cfgdata.endsWith(";"))                     //+v1DhI~
    	    			cfgdata+=newtime;                          //+v1DhI~
                    else                                           //+v1DhI~
    	    			cfgdata+=";"+newtime;                      //+v1DhI~
            }                                                      //~v1DhI~
        }                                                          //~v1DhI~
	}                                                              //~v1DhI~
    //*************************************                        //~v1DhI~
    private void repthread(String newthread)                       //~v1DhI~
    {                                                              //~v1DhI~
        if (!newthread.equals(textparm_thread))                    //~v1DhI~
        {                                                          //~v1DhI~
            if (!newthread.equals(""))                             //~v1DhI~
                newthread=PARM_FLAG_THREAD_GROUP+PARM_FLAG_THREAD+newthread;//~v1DhI~
       		if (posthread>=0)                                      //~v1DhI~
            {                                                      //~v1DhI~
            	if (endposthread>=0)                               //~v1DhI~
		        	cfgdata=cfgdata.substring(0,posthread)+newthread+cfgdata.substring(endposthread);//~v1DhI~
                else                                               //~v1DhI~
		        	cfgdata=cfgdata.substring(0,posthread)+newthread;//~v1DhI~
                if (cfgdata.startsWith(";"))                       //~v1DhI~
                    cfgdata=cfgdata.substring(1).trim();           //~v1DhI~
            }                                                      //~v1DhI~
            else                                                   //~v1DhI~
            {                                                      //~v1DhI~
	        	if (!newthread.equals(""))                         //+v1DhI~
    	    		if (cfgdata.equals(""))                        //+v1DhI~
    	    			cfgdata+=newthread;                        //+v1DhI~
                    else                                           //+v1DhI~
                    if (cfgdata.endsWith(";"))                     //+v1DhI~
    	    			cfgdata+=newthread;                        //+v1DhI~
                    else                                           //+v1DhI~
    	    			cfgdata+=";"+newthread;                    //+v1DhI~
            }                                                      //~v1DhI~
        }                                                          //~v1DhI~
	}                                                              //~v1DhI~
}
