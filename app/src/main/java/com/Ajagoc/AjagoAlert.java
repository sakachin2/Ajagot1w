//*CID://+v1B9R~: update#= 106;                                    //~v1B9R~
//**********************************************************************//~v110I~
//v1B9 2014/08/12 modal alertdialog for v1B8                       //~v1B9I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//v1B5 2014/07/30 gmp issues errmsg "GMP server respond err message"//~v1B5I~
//1102:130123 bluetooth became unconnectable after some stop operation//~v110I~
//**********************************************************************//~1107I~
//*AjagoAlert                                                      //~v110R~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import jagoclient.Dump;

import java.util.ArrayList;


//+1211I~
import com.Ajagoc.AjagoAlert;                                      //~1211I~
import com.Ajagoc.AG;
import com.Ajagoc.AjagoModal;
import android.app.AlertDialog;
import com.Ajagoc.AjagoModalI;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
//**********************************************************************//~1107I~
public class AjagoAlert                                            //~1107R~//~1211R~
	implements AjagoUiThreadI                                      //~v1B5I~
{                                                                  //~0914I~
	public static final int BUTTON_POSITIVE =0x01;                      //~1211I~//~1212R~
	public static final int BUTTON_NEGATIVE =0x02;                      //~1211I~//~1212R~
	public static final int BUTTON_CLOSE    =0x04;                         //~1211I~//~1212R~
	public static final int SHOW_DIALOG     =0x08;                 //~1212R~
	public static final int ITEM_SELECTED   =0x10;                       //~1211I~//~1212R~
	public static final int MULTIPLE_CHOICE =0x20;                     //~1211I~//~1212R~
	public static final int EXIT            =0x40;                 //~1212I~
	public static final int NO_TITLE        =0x80;                 //~1212I~
	public static final int BUTTON_YNC      =0x0100;               //~v1B6I~
	public static final int KEEP_CB_THREAD  =0x0200;    //shedule callback on caller thread//~v1B9I~
	
	private AjagoAlertI callback;                                  //~1425R~
	private View selectedView;                                     //~1425R~
    private ListView listview;                                     //~1425R~
    private int flag;                                              //~1425R~
    private boolean uicallback=false;                              //~v1B5I~
    private String uititle;                                        //~v1B5I~
    private String uitext;                                         //~v1B5I~
    private int uiflag;                                                               //~1211I~
	public AlertDialog pdlg;                                              //~1425R~//~v1B9R~
	private boolean swSubthreadModal;                              //~v1B9I~
    int selectedPos=-1;                                            //~1211R~
    private int buttonClicked;                                         //~v1B9I~
    private AjagoModal ajagoModal;                                 //~v1B9I~
//******************                                               //~1121I~
//**********************************                               //~1211I~
//*                                                                //~1211R~
//**********************************                               //~1211I~
	public AjagoAlert()                                           //~1211I~
    {                                                              //~1211I~
    }                                                              //~1211I~
	public AjagoAlert(AjagoAlertI Pcallback)                       //~1211I~
    {                                                              //~1211I~
    	callback=Pcallback;                                        //~1211I~
    }                                                              //~1211I~
//===============================================================================//~v@@@I~//~1212I~
//simple alertdialog                                               //~v@@@I~//~1212I~
//===============================================================================//~v@@@I~//~1212I~
    public static void simpleAlertDialog(AjagoAlertI Pcallback,String Ptitle,String Ptext,int Pflag)//~1212I~
    {                                                              //~1212I~
    //***********                                                  //~1212I~
    	if (Dump.Y) Dump.println("simpleAlertDialog text="+Ptext); //~v1B5I~
    	AjagoAlert ajagoalert=new AjagoAlert(Pcallback);           //~1212I~
    	if (!AG.isMainThread())                                    //~v1B5I~
        {                                                          //~v1B5I~
        	ajagoalert.uicallback=true;                            //~v1B5I~
        	ajagoalert.uititle=Ptitle;                             //~v1B5I~
        	ajagoalert.uitext=Ptext;                               //~v1B5I~
        	ajagoalert.uiflag=Pflag;                               //~v1B5I~
          if ((Pflag & KEEP_CB_THREAD)!=0)    //shedule callback on caller thread//~v1B9I~
			ajagoalert.subthreadModal();                           //~v1B9I~
          else                                                     //~v1B9I~
        	AjagoUiThread.runOnUiThread(ajagoalert,ajagoalert);          //~v1B5I~
            return;                                                //~v1B5I~
        }                                                          //~v1B5I~
        ajagoalert.createAlertDialog(Ptitle,Ptext,Pflag);          //~1212I~
    }                                                              //~1212I~
//===============================================================================//~v1B5I~
    public void runOnUiThread(Object Pparm)                        //~v1B5I~
    {                                                              //~v1B5I~
    	AjagoAlert ajagoalert=(AjagoAlert)Pparm;                   //~v1B5I~
        ajagoalert.createAlertDialog(ajagoalert.uititle,ajagoalert.uitext,ajagoalert.uiflag);//~v1B5I~
    }                                                              //~v1B5I~
//===============================================================================//~v1B9I~
    private void subthreadModal()                                  //~v1B9I~
    {                                                              //~v1B9I~
    	swSubthreadModal=true;                                     //~v1B9I~
        AjagoModalI modaldismisscallback=new AjagoModalI()           //~v1B9I~
                                        {                          //~v1B9I~
                                      	    @Override              //~v1B9I~
											public void onDismissModalDialog(boolean PmodalOnSubthred)                        //~1214R~//~1407R~//~v1B9I~
                                            {                      //~v1B9I~
                                            	if (Dump.Y) Dump.println("AjagoAlert Ajagomodal dismisslistener entry");//~v1B9I~
												subthreadModalPosted();//~v1B9I~
                                            }                      //~v1B9I~
                                        };                         //~v1B9I~
        ajagoModal=AjagoModal.showAlertDialogFromSubthread(this,modaldismisscallback);//~v1B9I~
    }                                                              //~v1B9I~
//===============================================================================//~v1B9I~
    private void subthreadModalPosted()                            //~v1B9I~
    {                                                              //~v1B9I~
        if (Dump.Y) Dump.println("AjagoAlertsubthreadModalPosted");//~v1B9I~
		callback.alertButtonAction(buttonClicked,selectedPos);     //~v1B9I~
    }                                                              //~v1B9I~
//===============================================================================//~1212I~
    public static void simpleAlertDialog(AjagoAlertI Pcallback,String Ptitle,int Ptextid,int Pflag)//~1212I~
    {                                                              //~1212I~
    //***********                                                  //~1212I~
    	String text=AG.resource.getString(Ptextid);                //~1212I~
	    simpleAlertDialog(Pcallback,Ptitle,text,Pflag);            //~1212I~
    }                                                              //~1212I~
//===============================================================================//~v1B9I~
    public static void simpleAlertDialog(AjagoAlertI Pcallback,int Ptitleid,int Ptextid,int Pflag)//~v1B9I~
    {                                                              //~v1B9I~
    //***********                                                  //~v1B9I~
    	String title=AG.resource.getString(Ptitleid);              //~v1B9I~
    	String text=AG.resource.getString(Ptextid);                //~v1B9I~
	    simpleAlertDialog(Pcallback,title,text,Pflag);             //~v1B9I~
    }                                                              //~v1B9I~
//===============================================================================//+v1B9I~
    public static void simpleAlertDialog(AjagoAlertI Pcallback,int Ptitleid,String Ptext,int Pflag)//+v1B9I~
    {                                                              //+v1B9I~
    //***********                                                  //+v1B9I~
    	String title=AG.resource.getString(Ptitleid);              //+v1B9I~
	    simpleAlertDialog(Pcallback,title,Ptext,Pflag);            //+v1B9I~
    }                                                              //+v1B9I~
//**********************************                               //~1211I~
//*simple msg popup                                                //~1211I~
//**********************************                               //~1211I~
	private void createAlertDialog(String Ptitle,String Ptext,int Pflag)//~1211R~//~1212R~
    {                                                              //~1211I~
		AlertDialog.Builder builder=new AlertDialog.Builder(AG.context);//~1211I~
    	builder.setMessage(Ptext);                                         //~v@@@I~//~1211I~
        setButton(builder,Pflag);                                  //~1212I~
    	pdlg=builder.create();                                                  //~v@@@I~//~1211I~
    	if (Ptitle!=null)                                              //~v@@@I~//~1211I~//~1212M~
    		pdlg.setTitle(Ptitle);                                      //~v@@@I~//~1211I~//~1212I~
        else                                                       //~1212M~
            if ((Pflag & NO_TITLE)!=0)                             //~1212M~
	        	pdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);//~1212I~
            else                                                   //~1212M~
	    		pdlg.setTitle(AG.resource.getString(R.string.app_name));//~1212M~
    	pdlg.show();                                                    //~v@@@I~//~1211I~//~1212R~
    }                                                              //~1211I~
//**********************************                               //~1212I~
	private void setButton(AlertDialog.Builder Pbuilder,int Pflag) //~1212I~
    {                                                              //~1212I~
    	String label;                                              //~v1B6I~
    	flag=Pflag;                                                //~1212I~
        if ((Pflag & BUTTON_POSITIVE)!=0)                          //~1212I~
        {                                                          //~1212I~
        	label=((Pflag & BUTTON_YNC)!=0?"Yes":"OK");                //~v1B6I~
//          Pbuilder.setPositiveButton("OK",new DialogInterface.OnClickListener()//~1212I~//~v1B6R~
            Pbuilder.setPositiveButton(label,new DialogInterface.OnClickListener()//~v1B6I~
                                        {                          //~1212I~
                                                                   //~1212I~
                                            public void onClick(DialogInterface Pdlg,int buttonID)//~1212I~
                                            {                      //~1212I~
                                            	buttonClicked=BUTTON_POSITIVE;//~v1B9I~
                                                pdlg.dismiss();    //~1212I~
												if (swSubthreadModal)//~v1B9I~
                                                	return;        //~v1B9I~
                                                if (callback!=null)//~1212I~
	                                            	callback.alertButtonAction(BUTTON_POSITIVE,selectedPos);//~1212R~
                                                if ((flag & EXIT)!=0)//~1212I~
//                                                    AjagoUtils.finish();//~1212I~//~1309R~//~v110R~
                                                    AjagoUtils.stopFinish();                                         }//~v110I~
                                        }                          //~1212I~
                                     );                            //~1212I~
        }                                                          //~1212I~
        if ((Pflag & BUTTON_CLOSE)!=0)                             //~1212I~
        {                                                          //~1212I~
        	label=((Pflag & BUTTON_YNC)!=0?"Cancel":"Close");          //~v1B6I~
//          Pbuilder.setPositiveButton("Close",new DialogInterface.OnClickListener()//~1212I~//~v1B6R~
            Pbuilder.setPositiveButton(label,new DialogInterface.OnClickListener()//~v1B6I~
                                        {                          //~1212I~
                                                                   //~1212I~
                                            public void onClick(DialogInterface Pdlg,int buttonID)//~1212I~
                                            {                      //~1212I~
                                            	buttonClicked=BUTTON_CLOSE;//~v1B9I~
                                                pdlg.dismiss();    //~1212I~
												if (swSubthreadModal)//~v1B9I~
                                                	return;        //~v1B9I~
                                                if (callback!=null)//~1212I~
		                                            callback.alertButtonAction(BUTTON_CLOSE,selectedPos);//~1212R~
                                            }                      //~1212I~
                                        }                          //~1212I~
                                     );                            //~1212I~
        }                                                          //~1212I~
        if ((Pflag & BUTTON_NEGATIVE)!=0)                          //~1212I~
        {                                                          //~1212I~
        	label=((Pflag & BUTTON_YNC)!=0?"No":"Cancel");             //~v1B6I~
//          Pbuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()//~1212I~//~v1B6R~
            Pbuilder.setNegativeButton(label,new DialogInterface.OnClickListener()//~v1B6I~
                                        {                          //~1212I~
                                                                   //~1212I~
                                            public void onClick(DialogInterface Pdlg,int buttonID)//~1212I~
                                            {                      //~1212I~
                                            	buttonClicked=BUTTON_NEGATIVE;//~v1B9I~
                                                pdlg.dismiss();    //~1212I~
												if (swSubthreadModal)//~v1B9I~
                                                	return;        //~v1B9I~
                                                if (callback!=null)//~1212I~
		                                            callback.alertButtonAction(BUTTON_NEGATIVE,selectedPos);//~1212R~
                                            }                      //~1212I~
                                        }                          //~1212I~
                                     );                            //~1212I~
        }                                                          //~1212I~
    }//setButton                                                   //~1212I~
//**********************************                               //~1211I~
//*listview popup                                                  //~1211I~
//**********************************                               //~1211I~
	public void createAltertDialog(String Ptitle,int Pflag,ArrayList<String> Pstrarray)//~1211I~
    {                                                              //~1211I~
        ArrayAdapter<String> adapter;                              //~1211I~
    //**********************************                           //~1211I~
		AlertDialog.Builder builder=new AlertDialog.Builder(AG.context);//~0A21I~//~1211I~
        listview=new ListView(AG.context);                         //~1211R~
        if ((Pflag & MULTIPLE_CHOICE)!=0)                            //~1211I~//~1212R~
        {                                                          //~1211I~
        	adapter=new ArrayAdapter<String>(AG.context,AG.listViewRowIdMultipleChoice,Pstrarray);//~1211I~//~1212R~
        }                                                          //~1211I~
        else                                                       //~1211I~
        {                                                          //~1211I~
        	adapter=new ArrayAdapter<String>(AG.context,AG.listViewRowId,Pstrarray);//~1211R~
        }                                                          //~1211I~
        listview.setAdapter(adapter);                              //~1211I~
        if ((Pflag & MULTIPLE_CHOICE)!=0)                           //~1211I~//~1212R~
        {                                                          //~1211I~
        	listview.setItemsCanFocus(false);	//chkbox focus:disable-->text enable onClick//~1211I~
        	listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);  //~1211I~
        }                                                          //~1211I~
        else                                                       //~1211I~
        {                                                          //~1211I~
        	adapter=new ArrayAdapter<String>(AG.context,AG.listViewRowId,Pstrarray);//~1211I~
        }                                                          //~1211I~
        setItemListener(listview);                                 //~1211I~
        LinearLayout layout=new LinearLayout(AG.context);          //~1211I~
        layout.setOrientation(LinearLayout.VERTICAL);              //~1211I~
        layout.addView(listview);                                  //~1211I~
        builder.setView(layout);                                   //~1211I~
        setButton(builder,Pflag);                                  //~1212I~
	    pdlg=builder.create();                                     //~1211I~//~1212M~
    	if (Ptitle!=null)                                          //~1211I~
    		pdlg.setTitle(Ptitle);                              //~1211I~//~1212R~
        else                                                       //~1211I~
            if ((Pflag & NO_TITLE)!=0)                             //~1212I~
	        	pdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);    //~1211I~//~1212R~
            else                                                   //~1212I~
	    		pdlg.setTitle(AG.resource.getString(R.string.app_name));//~1212R~
        if ((Pflag & SHOW_DIALOG)!=0)                               //~1211I~//~1212R~
        	show();                                                //~1211R~
    }//createAlertDialog                                           //~1211R~
//**********************************************************************//~1211I~
	public void show()                                             //~1211I~
    {                                                              //~1211I~
		pdlg.show();                                               //~1211I~
    }                                                              //~1211I~
//**********************************************************************//~1211I~
	public ListView getListView()                                  //~1211I~
    {                                                              //~1211I~
		return listview;                                           //~1211I~
    }                                                              //~1211I~
//**********************************************************************//~v@@@I~//~1211I~
//*setup ItemListener class                                              //~v@@@I~//~1211I~
//**********************************************************************//~v@@@I~//~1211I~
	private void setItemListener(ListView Plistview)                   //~1211I~
    {                                                              //~1211I~
    	Plistview.setOnItemClickListener(                              //~1211I~
        	new AdapterView.OnItemClickListener()                              //~1211I~
				{                                                  //~1211I~
                	@Override                                      //~1211I~
					public void onItemClick(AdapterView<?> arg0,View Plv, int Ppos, long arg3)//~1211R~
					{                                              //~1211I~
                    	int rc;                                    //~1211I~
                        if (Dump.Y) Dump.println("submenu onitemclisck itemno="+Ppos);//~1506R~
                        selectedPos=Ppos;                          //~1211I~
                        selectedView=Plv;                          //~1211I~
	                    rc=callback.alertButtonAction(ITEM_SELECTED,selectedPos);//~1211I~//~1212R~
                        if (rc==1)	//close dialog                 //~1211I~
                            pdlg.dismiss();                        //~1211I~
                    }
				}
        	                             );//~1211I~
	}                                                              //~1211I~
//**********************************************************************//~1211I~
//*setup ItemListener class                                        //~1211I~
//**********************************************************************//~1211I~
	public ListView getSelectedView()                             //~1211I~
    {                                                              //~1211I~
    	return (ListView) selectedView;                                       //~1211I~
	}                                                              //~1211I~
}//class AjagoAlert                                                //~1211R~
