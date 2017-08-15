package com.Ajagoc.awt;                                            //~1112I~

import jagoclient.Dump;

import com.Ajagoc.AG;
import android.content.DialogInterface;

public class AlertDialog                                           //~1126R~
{
	public AlertDialog()                                                  //~1126I~
    {                                                              //~1126I~
        if (Dump.Y) Dump.println("start AlerDialog");              //+1506R~
        android.app.AlertDialog.Builder dlg=new android.app.AlertDialog.Builder(AG.context);//~1126I~
        dlg.setMessage("Ptext");                                   //~1126I~
        dlg.setTitle("Ptitle");                                    //~1126I~
        dlg.setPositiveButton("Close",new DialogInterface.OnClickListener()//~1126I~
                                    {                              //~1126I~
                                                                   //~1126I~
                                        public void onClick(DialogInterface dlg,int buttonID)//~1126I~
                                        {                          //~1126I~
                                            dlg.dismiss();         //~1126I~
                                        }                          //~1126I~
                                    }                              //~1126I~
                              );                                   //~1126I~
        dlg.create();                                              //~1126I~
        dlg.show();                                                //~1126I~
        if (Dump.Y) Dump.println("exit AlerDialog");               //+1506R~
    }                                                              //~1126I~
}//class                                                           //~1112I~
