//*CID://+v1DeR~: update#= 109;                                    //+v1DeR~
//**********************************************************************//~1107I~
//*Button Click Listener                                           //~1112R~
//**********************************************************************//~1107I~
//v1De 2014/11/10 (BUG)if FileDialog dismissed by Back button,xubthread latch wait is never posted//+v1DeI~
//v1B9 2014/08/12 modal alertdialog for v1B8                       //~v1B9I~
//1101:130117 add catch exception for dismiss listener             //~v110I~
//*****************************************************************//~v110I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import java.util.concurrent.CountDownLatch;

import jagoclient.Dump;
import jagoclient.gui.DoActionListener;                            //~1112R~
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import com.Ajagoc.AjagoUiThread;
import com.Ajagoc.AjagoUiThreadI;
import com.Ajagoc.AjagoModalI;                                     //~1330I~
import com.Ajagoc.awt.ActionEvent;
import com.Ajagoc.awt.Dialog;


//**********************************************************************//~1127I~
// Modal from UiThread                                             //~1410R~
//      UI Thread                                Sub Thread        //~1410I~
//         showModal                                               //~1127I~
//                            ---------->        runOnSubThread    //~1127I~
//                                                 Async Xfer to UI thread//~1311R~
//                            <----------                          //~1311R~
//         runOnUiThread                                          //~1127I~//~1311R~
//           show                                                  //~1127I~
//                                                                 //~1127I~
//         onDismiss                                               //~1127I~
//           countDown        ----------->                         //~1127I~
//                                                   await         //~1127I~
//                                                   afterDismiss()//~1311R~
// Modal from SubThread                                            //~1410I~
//      UI Thread                                Sub Thread        //~1410I~
//                                                                 //~1410I~
//                                               runOnSubThread    //~1410I~
//                                                 ASync Xfer to UI thread//~1410I~
//                            <----------                          //~1410I~
//         runOnUiThread                                           //~1410I~
//           show                                                  //~1410I~
//         ButtonAction                                            //~1410I~
//               ActionEvent  ---------->          await           //~1410I~
//                                                 DoACtion()(diapose())//~1410I~
//                            <----------                          //~1410I~
//         dismiss()                                               //~1410I~
//           (no DismissListener setup)                            //~1410I~
//**********************************************************************//~1127I~
public class AjagoModal implements AjagoUiThreadI                                            //~1126R~//~1214R~
{                                                              //~1111I~//~1112I~
//*******************************                                  //~1126M~
//*******************************                                  //~1126M~
//*******************************                        
	public static final int FREE        =0;                        //~1127I~
	public static final int SHOWING     =1;                        //~1127I~
	public static final int DISMISS     =2;                        //~1127I~
	static private int modalstatus=FREE;                           //~1127R~
                                                                   //~1326I~
	DoActionListener doactionlistener=null;
	AjagoModalI dismissListener;                                   //~1330I~
	String actioncmd=null;//~1126M~
    private Dialog dialog=null;
    RunOnSubThread subthread=null;//~1215I~
    private boolean modalOnSubthread=false;                        //~1410R~
    private CountDownLatch latch;                                  //~1410I~
    private ActionEvent actionEvent;                               //~1410I~
    private AjagoAlert dialogAjagoAlert=null;                          //~v1B9I~
    private boolean swDismissByNotButton;                          //+v1DeI~
//**************                                                   //~1215I~
	private AjagoModal(boolean PisMainThread,Dialog Pdialog,AjagoModalI PdismissListener)//~1410R~
    {                                                              //~1330I~
    	dialog=Pdialog;                                            //~1330I~
    	dismissListener=PdismissListener;                          //~1330I~
		modalOnSubthread=!PisMainThread;                           //~1410I~
        if (PisMainThread)                                         //~1410I~
    		subthread=new RunOnSubThread();                        //~1410R~
    }                                                              //~1330I~
//**************                                                   //~v1B9I~
	private AjagoModal(boolean PisMainThread,AjagoAlert Pdialog,AjagoModalI PdismissListener)//~v1B9I~
    {                                                              //~v1B9I~
    	dialogAjagoAlert=Pdialog;                                  //~v1B9I~
    	dismissListener=PdismissListener;                          //~v1B9I~
		modalOnSubthread=!PisMainThread;                           //~v1B9I~
//      if (PisMainThread)                                         //~v1B9I~
//  		subthread=new RunOnSubThread();                        //~v1B9I~
    }                                                              //~v1B9I~
    public static AjagoModal show(Dialog Pdialog,AjagoModalI PdismissListener)//~1410R~
    {                                                              //~1330I~
    	boolean isMainThread=AG.isMainThread();                    //~1410I~
    	AjagoModal ajagomodal=new AjagoModal(isMainThread,Pdialog,PdismissListener);//~1410R~
    	if (isMainThread)                                          //~1410R~
        {                                                          //~1330I~
        	if (Dump.Y) Dump.println("AjagoModal:start show dialog on mainthread");//~1506R~
        	ajagomodal.subthread.init(ajagomodal);                 //~1330I~
        	new Thread(ajagomodal.subthread).start();              //~1330I~
        }                                                          //~1330I~
        else	//already in sub thread                            //~1330I~
        {                                                          //~1330I~
        	if (Dump.Y) Dump.println("AjagoModal:start show dialog on subthread");//~1506R~
		    ajagomodal.showOnUiThread();                           //~1330I~
        }                                                          //~1330I~
        return ajagomodal;	//waiting until dismiss                //~1410R~
    }                                                              //~1330I~
//*********************************************************        //~v1B9I~
//*modal alert dialog from subthread                               //~v1B9I~
//*********************************************************        //~v1B9I~
    public static AjagoModal showAlertDialogFromSubthread(AjagoAlert Pdialog,AjagoModalI PdismissListener)//~v1B9I~
    {                                                              //~v1B9I~
//  	boolean isMainThread=AG.isMainThread();                    //~v1B9I~
    	boolean isMainThread=false;                                //~v1B9I~
    	AjagoModal ajagomodal=new AjagoModal(isMainThread,Pdialog,PdismissListener);//~v1B9I~
//        if (isMainThread)                                        //~v1B9I~
//        {                                                        //~v1B9I~
//            if (Dump.Y) Dump.println("AjagoModal:start show dialog on mainthread");//~v1B9I~
//            ajagomodal.subthread.init(ajagomodal);               //~v1B9I~
//            new Thread(ajagomodal.subthread).start();            //~v1B9I~
//        }                                                        //~v1B9I~
//        else    //already in sub thread                          //~v1B9I~
        {                                                          //~v1B9I~
        	if (Dump.Y) Dump.println("AjagoModal:start show dialog on subthread");//~v1B9I~
//  	    ajagomodal.showOnUiThread();                           //~v1B9I~
    	    ajagomodal.showOnUiThreadAlertDialog();                //~v1B9I~
        }                                                          //~v1B9I~
        return ajagomodal;	//waiting until dismiss                //~v1B9I~
    }                                                              //~v1B9I~
//*******************************                                  //~1126M~//~1215M~
//*sub thread start point                                          //~1126M~//~1215M~
//*******************************                                  //~1126M~//~1215M~
    public class RunOnSubThread                                     //~1126M~//~1127R~//~1215M~
			implements Runnable                                    //~1126M~//~1215M~
    {                                                              //~1215M~
    	AjagoModal ajagomodal; 
    	public RunOnSubThread()
    	{
    	}
    	public void init(AjagoModal Pajagomodal)     //~1127R~//~1215I~
    	{                                                          //~1215M~
    		ajagomodal=Pajagomodal;//~1126M~                       //~1215I~
    	}                                                          //~1215M~
        @Override                                                  //~1126M~//~1215M~
        public void run()                                          //~1126M~//~1215M~
        {                                                          //~1126M~//~1215M~
			ajagomodal.showOnUiThread();                                    //~1127I~//~1215I~
        }                                                          //~1126M~//~1215M~
    }                                                              //~1126M~//~1215M~
//***********************************************                  //~1215I~
//*show dialog on UI thread and wait dismiss                       //~1215I~
//***********************************************                  //~1215I~
    private void showOnUiThread()                                  //~1215I~
    {                                                              //~1215I~
		if (modalOnSubthread)                                      //~1411R~
        	dialog.onSubthreadDoAction(this);	//notfy AjagoModal to Dialog to DoAction berfor ActionPerformed//~1411I~
        else                                                       //~1411I~
        	if (modalstatus==SHOWING)	//duplicate modal req                                     //~1215I~//~1407R~
            {                                                      //~1408I~
            	if (Dump.Y) Dump.println("duplicated Modal req");  //~1506R~
            	return;                                            //~1407R~
            }                                                      //~1408I~
        latch=new CountDownLatch(1/*posted by counddown once*/);   //~1410R~
        actionEvent=null;                                          //~1410I~
    	AjagoUiThread.runOnUiThreadXfer(this,latch); //request callback runOnUiThread and wait post
        try                                                        //~1215I~
        {                                                          //~1215I~
        	latch.await();   //subthread wakeup by dismiss or ActionEvent(when SubThreadModal)//~1410R~
	        if (Dump.Y) Dump.println("AjagoModal:posted wait");    //~1506R~
        }                                                          //~1215I~
        catch (InterruptedException e)                             //~1215I~
        {                                                          //~1215I~
        	Dump.println(e,"AjagoModal Thread Interrupted ");      //~1410R~
        }                                                          //~1215I~
		if (modalOnSubthread)                                      //~1410I~
        {                                                          //~1410I~
        	                                                       //~1411I~
        	if (actionEvent!=null)                                 //~1410I~
            {                                                      //~1411I~
	        	ActionEvent.actionPerform(0/*ActionTranslator*/,actionEvent);//~1411R~
            }                                                      //~1411I~
            else                                                   //+v1DeI~
            	swDismissByNotButton=true;                         //+v1DeI~
            afterDoAction();                                       //~1410I~
        }                                                          //~1410I~
        else                                                       //~1410I~
        	afterDismiss();                                        //~1410R~
    }                                                              //~1215I~
//***********************************************                  //~v1B9I~
//*show AlertDialog on UI thread and wait dismiss                  //~v1B9I~
//***********************************************                  //~v1B9I~
    private void showOnUiThreadAlertDialog()                       //~v1B9R~
    {                                                              //~v1B9I~
//  	if (modalOnSubthread)                                      //~v1B9I~
//      	dialog.onSubthreadDoAction(this);	//notfy AjagoModal to Dialog to DoAction berfor ActionPerformed//~v1B9I~
//      else                                                       //~v1B9I~
//      	if (modalstatus==SHOWING)	//duplicate modal req      //~v1B9I~
//          {                                                      //~v1B9I~
//          	if (Dump.Y) Dump.println("duplicated Modal req");  //~v1B9I~
//          	return;                                            //~v1B9I~
//          }                                                      //~v1B9I~
        latch=new CountDownLatch(1/*posted by counddown once*/);   //~v1B9I~
//      actionEvent=null;                                          //~v1B9I~
    	AjagoUiThread.runOnUiThreadXferAlertDialog(this,latch,dialogAjagoAlert); //request callback runOnUiThread and wait post//~v1B9R~
        try                                                        //~v1B9I~
        {                                                          //~v1B9I~
        	latch.await();   //subthread wakeup by dismiss or ActionEvent(when SubThreadModal)//~v1B9I~
	        if (Dump.Y) Dump.println("AjagoModal:posted wait");    //~v1B9I~
        }                                                          //~v1B9I~
        catch (InterruptedException e)                             //~v1B9I~
        {                                                          //~v1B9I~
        	Dump.println(e,"AjagoModal Thread Interrupted ");      //~v1B9I~
        }                                                          //~v1B9I~
//  	if (modalOnSubthread)                                      //~v1B9I~
//      {                                                          //~v1B9R~
//                                                                 //~v1B9R~
//      	if (actionEvent!=null)                                 //~v1B9R~
//          {                                                      //~v1B9R~
//          	ActionEvent.actionPerform(0/*ActionTranslator*/,actionEvent);//~v1B9R~
//          }                                                      //~v1B9R~
//          afterDoAction();                                       //~v1B9R~
//      }                                                          //~v1B9R~
//      else                                                       //~v1B9I~
//      	afterDismiss();                                        //~v1B9I~
		dismissListener.onDismissModalDialog(true);                //~v1B9R~
    }                                                              //~v1B9I~
//************************************************************************//~v1B9I~
//*from AjagoUiThread                                              //~v1B9I~
//************************************************************************//~v1B9I~
    @Override                                                      //~1215I~
    public void runOnUiThread(Object Pparm)//callback from RunOnUiThread//~1407R~
    {   
    	if (dialogAjagoAlert!=null)                                     //~v1B9I~
        {                                                          //~v1B9I~
		    runOnUiThreadAlertDialog(Pparm);//callback from RunOnUiThread//~v1B9I~
            return;                                                //~v1B9I~
        }                                                          //~v1B9I~
    	android.app.Dialog androidDialog=dialog.androidDialog;//~1215I~
    	if (Pparm instanceof CountDownLatch)                       //~1220I~
        {                                                          //~1220I~
            CountDownLatch latch=(CountDownLatch)Pparm;                //~1215I~//~1220R~
			if (!modalOnSubthread)                                 //~1410I~
            	androidDialog.setOnDismissListener(new dismissListener(latch));   //~1215I~//~1410R~
            else                                                   //+v1DeI~
            	androidDialog.setOnDismissListener(new dismissListener(latch));//+v1DeI~
            dialog.setModalStatus_Show(modalOnSubthread);           //~1410I~
            androidDialog.show();                                             //~1215I~//~1220R~
            modalstatus=SHOWING;                                       //~1215I~//~1220R~
        }                                                          //~1220I~
        else                                                       //~1330R~
    	if (Pparm instanceof AjagoModalI)                          //~1330I~
        {                                                          //~1330I~
			dismissListener.onDismissModalDialog(modalOnSubthread);//~1407R~
        }                                                          //~1330I~
    }                                                              //~1215I~
//************************************************************************//~v1B9I~
//*from AjagoUiThread:                                             //~v1B9I~
//************************************************************************//~v1B9I~
    private void runOnUiThreadAlertDialog(Object Pparm)//callback from RunOnUiThread//~v1B9I~
    {                                                              //~v1B9I~
        dialogAjagoAlert.runOnUiThread(dialogAjagoAlert);                    //~v1B9I~
        dialogAjagoAlert.pdlg.setOnDismissListener(new dismissListener(latch));//countdown when dismiss//~v1B9I~
    }                                                              //~v1B9I~
//****************************************************             //~1410I~
//*EventAction request on SubThread from ActionEvent               //~1410I~
//****************************************************             //~1410I~
    public void actionPerforme(ActionEvent Pae)                    //~1410R~
    {                                                              //~1410I~
        actionEvent=Pae;                                           //~1410I~
		if (Dump.Y) Dump.println("ActionPerformed for subthreadModal");//~1506R~
        latch.countDown();  //count exausted,post latch.await()    //~1410I~
    }                                                              //~1410I~
//*******************************                                  //~1126M~//~1215M~
//*dismiss listener                                                //~1126I~//~1215M~
//*******************************                                  //~1126I~//~1215M~
    public class dismissListener                                   //~1126M~//~1215M~
    		implements OnDismissListener                           //~1126M~//~1215M~
	{                                                              //~1126M~//~1215M~
    	CountDownLatch latch;                                      //~1126M~//~1215M~
	    public dismissListener(CountDownLatch Platch)              //~1126M~//~1215M~
        {                                                          //~1126M~//~1215M~
	    	latch=Platch;                                          //~1126M~//~1215M~
        }                                                          //~1126M~//~1215M~
        @Override                                                  //~1126M~//~1215M~
        public void onDismiss(DialogInterface Pdialog)             //~1126M~//~1215M~
        {                                                          //~1126M~//~1215M~
			if (Dump.Y) Dump.println("AjagoModal dismiss scheduled,latch count down");        //~1127I~//~1506R~
          try                                                      //~v110I~
          {                                                        //~v110I~
            latch.countDown();  //count exausted,post latch.await()//~1126M~//~1215M~
          }                                                        //~v110I~
          catch(Exception e)                                       //~v110I~
          {                                                        //~v110I~
        	Dump.println(e,"AjagoModal:onDismiss:");               //~v110I~
          }                                                        //~v110I~
        }                                                          //~1126M~//~1215M~
    }                                                              //~1126M~//~1215M~
//**********************************                               //~1126I~//~1127M~//~1215M~
//*show dialog then wait dismiss                                   //~1126I~//~1127M~//~1215M~
//**********************************                               //~1126I~//~1127M~//~1215M~
	private void afterDismiss()                                   //~1126I~//~1127M~//~1410R~
    {                                                              //~1127M~//~1215M~
        AG.setDialogClosed();                                      //~1326I~
        setAfterDismiss();                                         //~1410R~
		if (Dump.Y) Dump.println("afterDismiss cancelflag="+dialog.canceled);                        //~1127I~//~1215M~//~1506R~
		if (Dump.Y) Dump.println("callback doAction");               //~1127I~//~1215M~//~1506R~
//      AjagoUiThread.runOnUiThreadXfer(this,doactionlistener); //excute DoAction On UI thread//~1220R~//~1330R~
        AjagoUiThread.runOnUiThreadXfer(this,dismissListener); //excute DoAction On UI thread//~1330I~
    }                                                              //~1126I~//~1127M~//~1215M~
//**********************************                               //~1410I~
//*show dialog then wait dismiss                                   //~1410I~
//**********************************                               //~1410I~
	private void afterDoAction()                                   //~1410I~
    {                                                              //~1410I~
        AG.setDialogClosed();                                      //~1410I~
	    resetAfterDismiss();                                        //~1410I~
		if (Dump.Y) Dump.println("afterDismiss cancelflag="+dialog.canceled);//~1506R~
		if (Dump.Y) Dump.println("callback doAction");             //~1506R~
		dismissListener.onDismissModalDialog(modalOnSubthread);    //~1410I~
    }                                                              //~1410I~
//********************                                             //~1215I~
//**********************************                               //~1127I~//~1215R~
    private void setAfterDismiss()                           //~1127I~//~1215R~//~1326R~
    {                                                              //~1127I~//~1215R~
        modalstatus=DISMISS;                              //~1127I~//~1215R~//~1326R~
    }                                                              //~1127I~//~1215R~
    public static void resetAfterDismiss()                         //~1324I~
    {                                                              //~1324I~
        modalstatus=FREE;	//for the case recursive modal dialog from afterdismiss DoActionListener//~1324I~
    }                                                              //~1324I~
    public boolean isModalOnSubthread()                            //~1410I~
    {                                                              //~1410I~
		return modalOnSubthread;                                   //~1410I~
    }                                                              //~1410I~
}//class                                                              //~1111I~//~1112I~
