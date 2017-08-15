//*CID://+v1E9R~:                             update#=   10;       //+v1E9R~
//************************************************************************//~v106I~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//+v1E9I~
//v1Dq 2014/11/15 comment area scroll hide last 1 line             //~v1DqI~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//************************************************************************//~v106I~
package com.Ajagoc.awt;                                            //~1112I~

import jagoclient.Dump;
import android.view.View;
import android.widget.ScrollView;

                                                                   //~1112I~
public class TextArea extends TextField                            //~1116R~
                                                                   //~1118R~
{                                                                  //~1112I~
	public static final int SCROLLBARS_VERTICAL_ONLY=1;	//not used defined on layout xml//~1118R~
	public TextArea()                                              //~1116I~
    {                                                              //~1116I~
    	super(0,0);   //Row,Col                                                //~1116I~//~1216R~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~v106I~
        {                                                          //~v106I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~v106I~
        	return;                                                //~v106I~
        }                                                          //~v106I~
        if (textView.getParent() instanceof ScrollView)            //~1311I~
        	scrollView=(ScrollView)textView.getParent();           //~1311I~
    }                                                              //~1116I~
	public TextArea(String s,int Prow,int Pcols,int Pscrollbarflag)                    //~1116I~//~1216R~
    {                                                              //~1116I~
    	super(s,Prow,Pcols);                                                  //~1116I~//~1216R~
    	if (Dump.Y) Dump.println("TextArea:name="+s+",row="+Prow+",col="+Pcols+",textView="+(textView==null?"@@@@@null":textView));//~v106I~
//  	if (Dump.Y) Dump.println("AG.currentlayout="+Integer.toHexString(AG.currentLayoutId)+",currentFrame="+AG.currentFrame.framename);//~v106R~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~v106I~
        {                                                          //~v106I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~v106I~
        	return;                                                //~v106I~
        }                                                          //~v106I~
//      if (textView.getParent() instanceof ScrollView)            //~v1DqI~
//      	scrollView=(ScrollView)textView.getParent();           //~v1DqI~
        View pv=(View)textView.getParent();                              //~v1DqI~
        if (pv instanceof ScrollView)                              //~v1DqI~
        	scrollView=(ScrollView)pv;                             //~v1DqR~
        else                                                       //~v1DqI~
        {                                                          //~v1DqI~
	        pv=(View)pv.getParent();                                     //~v1DqI~
        	if (pv!=null && pv instanceof ScrollView)              //~v1DqI~
	        	scrollView=(ScrollView)pv;                         //~v1DqI~
        }                                                          //~v1DqI~
    }                                                              //~1116I~                              //~1116I~
    //*****************************************************************//+v1E9I~
    //*find view by res id                                         //+v1E9I~
    //*****************************************************************//+v1E9I~
	public TextArea(String s,int Presid,int Prow,int Pcols,int Pscrollbarflag)//+v1E9I~
    {                                                              //+v1E9I~
    	super(s,Presid,Prow,Pcols);                                //+v1E9I~
    	if (Dump.Y) Dump.println("TextArea:name="+s+",row="+Prow+",col="+Pcols+",textView="+(textView==null?"@@@@@null":textView));//+v1E9I~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//+v1E9I~
        {                                                          //+v1E9I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//+v1E9I~
        	return;                                                //+v1E9I~
        }                                                          //+v1E9I~
        if (textView.getParent() instanceof ScrollView)            //+v1E9I~
        	scrollView=(ScrollView)textView.getParent();           //+v1E9I~
    }                                                              //+v1E9I~
    //*****************************************************************//+v1E9I~
    //*find view by res id                                         //+v1E9I~
    //*****************************************************************//+v1E9I~
	public TextArea(Container Pcontainer,String s,int Presid,int Prow,int Pcols,int Pscrollbarflag)//+v1E9I~
    {                                                              //+v1E9I~
    	super(Pcontainer,s,Presid,Prow,Pcols);                     //+v1E9I~
    	if (Dump.Y) Dump.println("TextArea:name="+s+",row="+Prow+",col="+Pcols+",textView="+(textView==null?"@@@@@null":textView));//+v1E9I~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//+v1E9I~
        {                                                          //+v1E9I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//+v1E9I~
        	return;                                                //+v1E9I~
        }                                                          //+v1E9I~
        if (textView.getParent() instanceof ScrollView)            //+v1E9I~
        	scrollView=(ScrollView)textView.getParent();           //+v1E9I~
    }                                                              //+v1E9I~
//***************                                                  //~1216I~
	public void repaint()                                          //~1125I~
    {                                                              //~1125I~
    }                                                              //~1125I~
//***************                                                  //~1216I~
    public void setVisible(boolean Pvisible)                            //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("@@@@ TextArea:setVisble ="+Pvisible);//~1506R~
    }                                                              //~1128I~
//***************                                                  //~1219I~
    public void appendText(String Ptext)  //deplicated but         //~1219I~
    {                                                              //~1219I~
    	append(Ptext);                                             //~1219I~
    }                                                              //~1219I~
}//class                                                           //~1112I~
