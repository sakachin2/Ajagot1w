//*CID://+1AgbR~: update#= 218;                                    //+1AgbR~
//**********************************************************************//~1107I~
//1Agb 2016/10/10 AxeDialog is 2 two place,com.axe and com.Ajagoc.axe, del later.//+1AgbI~
//v1C9 2014/09/03 change AjagocMenu to Dialog                      //~v1C9I~
//1B32 2013/07/01 one touch mode also on board menue               //~1B32I~
//1B1g 130511 Button on Help Frame for modified Help text for Ajagoc//~1B1gI~
//1B0e 130429 option of one touch mode for local and connected board//~1B0eI~
//1091:121124 Menubar list OutOfBoundsException                    //~v109I~
//1078:121208 add "menu" to option menu if high resolusion         //~v107I~
//1076:121208 drop debugtrace menu item if release version         //~v107I~
//V104:121109 onContextMenuItemClosed sheduled before submenu display,NPE abend//~v104I~
//**********************************************************************//~v104I~
//*Menu                                                            //~v104R~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;

import com.Ajagoc.awt.Canvas;
import com.Ajagoc.AG;
//import com.Ajagoc.axe.AxeDialog;                                 //+1AgbR~
import com.axe.AxeDialog;                                          //+1AgbI~


import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
//**********************************************************************//~1107I~
public class AjagoOptions extends AxeDialog                           //~1310R~//~v1C9R~
{                                                                  //~0914I~
    static final int LAYOUT=R.layout.dialogajagocoptions;         //~v1C9I~
                                                                   //~v1C9I~
    private CheckBox  cbDirectionKey,cbSearchKey,cbOneTouchLocal,cbOneTouchNonLocal,cbTrace;//~v1C9I~
                                                                   //~1507I~
//******************                                               //~1121I~//~v1C9M~
	public AjagoOptions()                                             //~1107R~//~v1C9I~
    {                                                              //~1107I~//~v1C9M~
        super(LAYOUT);                                             //~v1C9I~
        String title=AG.appName+" "+Global.resourceString("Options");//~v1C9I~
		showDialog(title);     //callback setupDialogExtend()      //~v1C9I~
    }                                                              //~1107I~//~v1C9M~
//*********                                                        //~v1C9I~
	@Override                                                      //~v1C9I~
	protected void setupDialogExtend(ViewGroup PlayoutView)        //~v1C9I~
    {                                                              //~v1C9I~
	    boolean flag;
		cbDirectionKey=(CheckBox)PlayoutView.findViewById(R.id.AJAGOC_OPTION_USE_DIRECTION_KEY);//~v1C9I~
	    cbSearchKey=(CheckBox)PlayoutView.findViewById(R.id.AJAGOC_OPTION_USE_SEARCH_KEY);//~v1C9I~
	    cbOneTouchLocal=(CheckBox)PlayoutView.findViewById(R.id.AJAGOC_OPTION_ONE_TOUCH_LOCAL_BOARD);//~v1C9I~
	    cbOneTouchNonLocal=(CheckBox)PlayoutView.findViewById(R.id.AJAGOC_OPTION_ONE_TOUCH_MATCH_BOARD);//~v1C9I~
	    cbTrace=(CheckBox)PlayoutView.findViewById(R.id.AJAGOC_OPTION_DEBUG_TRACE);//~v1C9I~
	    if (!AG.isDebuggable)                                      //~v1C9I~
        {                                                          //~v1C9I~
        	cbTrace.setVisibility(View.GONE);                      //~v1C9I~
        }                                                          //~v1C9I~
        flag=Global.getParameter(AjagoMenu.DIRECTIONKEY_CFGKEY,false);//~v1C9R~
        cbDirectionKey.setChecked(flag);                           //~v1C9I~
        flag=Global.getParameter(AjagoMenu.SEARCHKEY_CFGKEY,true); //~v1C9R~
        cbSearchKey.setChecked(flag);                              //~v1C9I~
        flag=Global.getParameter(AjagoMenu.ONETOUCH_LOCAL_CFGKEY,false);//~v1C9R~
        cbOneTouchLocal.setChecked(flag);                            //~v1C9I~
        flag=Global.getParameter(AjagoMenu.ONETOUCH_MATCH_CFGKEY,false);//~v1C9R~
        cbOneTouchNonLocal.setChecked(flag);                         //~v1C9I~
	    if (AG.isDebuggable)                                       //~v1C9R~
        {                                                          //~v1C9I~
//          flag=Global.getParameter(AjagoMenu.DEBUGTRACE_CFGKEY,false);//~v1C9R~
//          flag=AjagoProp.getPreference(AjagoMenu.DEBUGTRACE_CFGKEY,false);//~v1C9R~
            flag=Dump.Y;                                           //~v1C9I~
    	    cbTrace.setChecked(flag);                                //~v1C9I~
        }                                                          //~v1C9I~
    }//setupDialogExtend                                           //~v1C9I~
    //****************************************                     //~v1C9I~
    //*Button                                                      //~v1C9I~
    //****************************************                     //~v1C9I~
	@Override                                                      //~v1C9I~
    protected boolean onClickOther(int PbuttonId)                  //~v1C9I~
    {                                                              //~v1C9I~
    	boolean rc=false;   //not dismiss at return                //~v1C9I~
    //****************                                             //~v1C9I~
        if (Dump.Y) Dump.println("GTPConnection onClickOther buttonid="+Integer.toHexString(PbuttonId));//~v1C9I~
        switch(PbuttonId)                                          //~v1C9I~
        {                                                          //~v1C9I~
        case R.id.OK:                                              //~v1C9I~
	       	rc=onOK();                                             //~v1C9I~
            break;                                                 //~v1C9I~
        }                                                          //~v1C9I~
        return rc;                                                 //~v1C9I~
    }                                                              //~v1C9I~
    //****************************************                     //~v1C9I~
    //*Button OK                                                   //~v1C9I~
    //*rc :true:dismiss                                            //~v1C9I~
    //****************************************                     //~v1C9I~
    private boolean onOK()                              //~1821R~//~1826R~//~v1C9I~
    {                                                              //~1821M~//~v1C9I~
    	boolean rc=true,flag;	//dismiss                              //~1821I~//~v1C9I~
     //****************                                             //~1821M~//~v1C9I~
        flag=cbDirectionKey.isChecked();                           //~v1C9I~
        Global.setParameter(AjagoMenu.DIRECTIONKEY_CFGKEY,flag);   //~v1C9R~
        Canvas.optionChanged(flag);                                //~v1C9R~
                                                                   //~v1C9I~
        flag=cbSearchKey.isChecked();                              //~v1C9I~
        Global.setParameter(AjagoMenu.SEARCHKEY_CFGKEY,flag);      //~v1C9R~
        AjagoKey.optionChanged(flag);                              //~v1C9R~
                                                                   //~v1C9I~
        flag=cbOneTouchLocal.isChecked();                          //~v1C9I~
        Global.setParameter(AjagoMenu.ONETOUCH_LOCAL_CFGKEY,flag); //~v1C9R~
        Canvas.optionChanged(Canvas.ONETOUCH_LOCALBOARD,flag);     //~v1C9R~
                                                                   //~v1C9I~
        flag=cbOneTouchNonLocal.isChecked();                       //~v1C9I~
        Global.setParameter(AjagoMenu.ONETOUCH_MATCH_CFGKEY,flag); //~v1C9R~
        Canvas.optionChanged(Canvas.ONETOUCH_MATCHBOARD,flag);     //~v1C9R~
                                                                   //~v1C9I~
	    if (AG.isDebuggable)                                       //~v1C9R~
        {                                                          //~v1C9I~
        	flag=cbTrace.isChecked();                              //~v1C9I~
        	Global.setParameter(AjagoMenu.DEBUGTRACE_CFGKEY,flag);//Dump do it//~v1C9R~
			Dump.setOption(flag);  //set also AjagoProp for startup before read go.cfg//~v1C9R~
        }                                                          //~v1C9I~
        return rc;                                                 //~1821I~//~v1C9I~
    }                                                              //~1821M~//~v1C9I~
//******************                                               //~v1C9I~
	@Override                                                      //~v1C9I~
    protected boolean onClickHelp()                                //~v1C9I~
    {                                                              //~v1C9I~
        new HelpDialog(Global.frame(),"AjagocOptions");                    //~v1C9I~
        return false;	//no dismiss                               //~v1C9I~
    }                                                              //~v1C9I~
                                                                   //~1507I~
}//class AjagoOptions                                              //~v1C9R~
