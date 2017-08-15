//*CID://+v1C4R~:                             update#=   51;       //~v1C4R~
//*************************************************************************//~v106I~
//v1C4 2014/08/24 (BUG)extramove count inclease after loose by timeout//~v1C4I~
//v1Ba 2014/08/14 Canvas enqRequest callback for gtp callback gotMoved//~v1BaI~
//v1B8 2014/08/12 gtp process menu:resign                          //~v1B8R~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//1B04 130428 Send field is not used on GMP(set visibility:GONE)   //~1B04I~
//V106 1061:121120 add click sound on also GMP match               //~v106I~
//*************************************************************************//~v106I~
package com.Ajagoc.gtp;                                            //~v1B6R~

import com.Ajagoc.AG;
import com.Ajagoc.AjagoAlert;
import com.Ajagoc.AjagoAlertI;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;
import com.Ajagoc.awt.Component;

import android.view.View;
import jagoclient.Global;
import jagoclient.Dump;
import jagoclient.board.*;
import jagoclient.dialogs.*;
import jagoclient.sound.JagoSound;                                 //~v106M~

public class GTPGoFrame extends ConnectedGoFrame                   //~v1B6R~
	implements TimedBoard
{	GTPConnection C;                                               //~v1B6R~
	boolean WantMove=true;
	int BlackTime=0,WhiteTime=0;
	int BlackMoves,WhiteMoves;                                     //~v1B6I~
	int BlackRun=0,WhiteRun=0;                                     //~v1B6M~
	long CurrentTime;
	GoTimer Timer=null;
	int MyColor;
	int ExtraMove=0,ExtraTime=0;                                   //~v1B6M~
    boolean BlackTimeout,WhiteTimeout;                             //~v1B6M~
    int colorWinner=0;                                             //~v1B6I~
    int BlackTimeLimit,WhiteTimeLimit,BlackTimeoutMoves,WhiteTimeoutMoves;                   //~v1B6I~
	
	public GTPGoFrame (GTPConnection c, int size, int color)       //~v1B6R~
	{	super(Global.resourceString("Play_Go"),size,
			Global.resourceString("Remove_groups"),Global.resourceString(""),
			false,false);
//*MyColor:computer color                                          //~v1B6I~
		MyColor=color;
		C=c;
        setRule();                                                 //~v1B6I~
        adjustView();                                              //~1B04I~
		Timer=new GoTimer(this,1000);
		CurrentTime=System.currentTimeMillis();
		setVisible(true);
		repaint();
	}
//*******************************************************************//~1B04R~
//set not used view to GONE                                        //~1B04I~
//*******************************************************************//~1B04I~
	private void adjustView()                                      //~1B04I~
    {                                                              //~1B04I~
    	View v;                                                    //~1B04I~
        v=findView(R.id.sendforwardcontainer);	//igs only         //~1B04I~
        Component.setVisible(v,View.GONE);                      //~1B04I~
        v=findView(R.id.sendcontainer);			//igs/partner      //~1B04I~
        Component.setVisible(v,View.GONE);                      //~1B04I~
        v=findView(R.id.BottomInputLine);			//sendfield container//~1B04I~
        Component.setVisible(v,View.GONE);                      //~1B04I~
    }                                                              //~1B04I~
//*******************************************************************//~1B04I~
	public boolean wantsmove ()
	{	return WantMove;
	}
//**************************************************************** //~v1B6I~
//*computer set stone                                              //~v1B6I~
//**************************************************************** //~v1B6I~
	public void gotMove (int color, int i, int j)
	{	synchronized (B)
		{	if (B.maincolor()==color) return;
			updateTime();
//			if (color==GTPConnector.WHITE) white(i,j);             //~v1B6R~
//  		else black(i,j);                                       //~v1B6I~
  			if (color==GTPConnector.WHITE)                         //~v1B6I~
            {                                                      //~v1B6I~
            	updateWhiteMoves();                                 //~v1B6I~
				white(i,j);                                        //~v1B6I~
            }                                                      //~v1B6I~
    		else                                                   //~v1B6R~
            {                                                      //~v1B6I~
            	updateBlackMoves();                                 //~v1B6I~
				black(i,j);                                        //~v1B6I~
            }                                                      //~v1B6I~
			B.showinformation();
			B.copy();
		}
	}
//**************************************************************** //~v1B6R~
//*human set stone                                                 //~v1B6I~
//**************************************************************** //~v1B6I~
	public void gotSet (int color, int i, int j)
	{	updateTime();
//  	if (color==GTPConnector.WHITE) setwhite(i,j);              //~v1B6R~
//  	else setblack(i,j);                                        //~v1B6R~
        if (color==GTPConnector.WHITE)                             //~v1B6I~
        {                                                          //~v1B6I~
            updateWhiteMoves();                                     //~v1B6I~
            setwhite(i,j);                                         //~v1B6I~
        }                                                          //~v1B6I~
        else                                                       //~v1B6R~
        {                                                          //~v1B6I~
            updateBlackMoves();                                     //~v1B6I~
            setblack(i,j);                                         //~v1B6I~
        }                                                          //~v1B6I~
	}
	
	public void gotPass (int color)
	{	updateTime();
		if (Dump.Y) Dump.println("Opponent passed");               //~1506R~
		B.setpass();
		/*Message d=*/new Message(this,Global.resourceString("Pass"));
	}

	public void notepass ()
	{	if (B.maincolor()==MyColor) return;
		updateTime();
		if (Dump.Y) Dump.println("I pass");                        //~1506R~
		C.pass();
		B.setpass();
	}

	public boolean moveset (int i, int j)
	{	if (B.maincolor()==MyColor) return false;
		updateTime();
		if (Dump.Y) Dump.println("Move at "+i+" "+j);              //~1506R~
		C.moveset(i,j);
		updateTime();
		return true;
	}

	public void color (int c)
	{	if (c==GTPConnector.WHITE) super.color(-1);                //~v1B6R~
		else super.color(1);
	}

	public void undo ()
	{	if (B.maincolor()==MyColor) return;
      boolean rc=                                                  //~v1B8I~
		C.undo();
      if (rc)                                                      //~v1B8I~
		doundo(2);
	}

	public void doundo (int n)
	{	B.undo(n);
	}

	public void doAction (String o)	
	{	if (Global.resourceString("Remove_groups").equals(o))
		{	WantMove=false;
			B.score();
		}
        else                                                       //~v1B8R~
	 	if (Global.resourceString("Resign").equals(o))             //~v1B8R~
		{                                                          //~v1B8R~
			doresign();                                            //~v1B8R~
		}                                                          //~v1B8R~
		else super.doAction(o);
	}

	public void doclose ()
	{	C.doclose();
		if (Timer!=null && Timer.isAlive()) Timer.stopit();
		super.doclose();
	}

	public void alarm ()
	{	long now=System.currentTimeMillis();
    	int BlackRun=BlackTime;                                    //~v1B6R~
    	int WhiteRun=WhiteTime;                                    //~v1B6R~
//      Dump.exceptionOnly=false;//@@@@test                        //~v1B6R~
//      Dump.println("alarm Btime="+BlackTime+",CurrTime="+CurrentTime+",add="+(int)((now-CurrentTime)/1000)+",BlackLimit="+BlackTimeLimit);//@@@@TEST//~v1B6R~
		if (B.maincolor()>0)
		{	BlackRun+=(int)((now-CurrentTime)/1000);
//  		if (B.MyColor>0) beep(BlackTime-BlackRun);             //~v1B6R~
            if ( BlackTimeLimit!=0)                                //~v1B6I~
                if (BlackTimeLimit<BlackRun)                       //~v1B6R~
                {                                                  //~v1B6R~
                    beep(BlackTime-BlackRun);                      //~v1B6R~
                    if (!BlackTimeout)  //in first Timeout         //~v1B6R~
                    {                                              //~v1B6R~
                        BlackTimeout=true;                         //~v1B6I~
                        if (ExtraTime>0)                           //~v1B6R~
                        {                                          //~v1B6I~
                            BlackRun-=BlackTimeLimit;           //~v1B6R~
                            BlackTimeLimit=ExtraTime;              //~v1B6M~
                            BlackTime=BlackRun;                    //~v1B6I~
				        	BlackTimeoutMoves=ExtraMove;           //~v1C4I~
							CurrentTime=System.currentTimeMillis();//new base of Runtime(current time is basically moved time)//~v1B6I~
		                    warning_time(1);                       //~v1B6I~
                        }                                          //~v1B6I~
                        else                                       //~v1B6I~
		                    warning_time(2);                       //~v1B6I~
                    }                                              //~v1B6R~
                    else                                           //~v1B6I~
		            	warning_time(2);                           //~v1B6I~
                }                                                  //~v1B6R~
		}
		else
		{	WhiteRun+=(int)((now-CurrentTime)/1000);
//  		if (B.MyColor<0) beep(WhiteTime-WhiteRun);             //~v1B6R~
    		if ( WhiteTimeLimit!=0)                                //~v1B6I~
            {                                                      //~v1B6I~
                if (WhiteTimeLimit<WhiteRun)                       //~v1B6R~
                {                                                  //~v1B6R~
                    beep(WhiteTime-WhiteRun);                      //~v1B6R~
                    if (!WhiteTimeout)  //in first Timeout         //~v1B6R~
                    {                                              //~v1B6R~
                        WhiteTimeout=true;                         //~v1B6I~
                        if (ExtraTime>0)                           //~v1B6R~
                        {                                          //~v1B6I~
                            WhiteRun-=WhiteTimeLimit;              //~v1B6R~
                            WhiteTimeLimit=ExtraTime;              //~v1B6M~
                            WhiteTime=WhiteRun;                    //~v1B6I~
				        	WhiteTimeoutMoves=ExtraMove;           //+v1C4R~
							CurrentTime=System.currentTimeMillis();//~v1B6I~
		                    warning_time(1);                  //~v1B6I~
                        }                                          //~v1B6I~
                        else                                       //~v1B6I~
		                    warning_time(2);                       //~v1B6I~
                    }                                              //~v1B6R~
                    else                                           //~v1B6I~
		            	warning_time(2);                           //~v1B6I~
                }                                                  //~v1B6R~
            }                                                      //~v1B6I~
		}
//      Dump.println("alarm setTime BlackTime="+(BlackTimeLimit-BlackRun));//@@@@TEST//~v1B6R~
		if (BigTimer)
//  	{	BL.setTime(WhiteRun,BlackRun,0,0,0);                   //~v1B6R~
    	{                                                          //~v1B6I~
    	 	BL.setTime(WhiteTimeLimit-WhiteRun,BlackTimeLimit-BlackRun,WhiteTimeoutMoves,BlackTimeoutMoves,B.maincolor());//~v1B6R~
			BL.repaint();
		}
	}

	public void updateTime ()
	{	long now=System.currentTimeMillis();
		if (B.maincolor()>0)
		{	BlackTime+=(int)((now-CurrentTime)/1000);
		}
		else
		{	WhiteTime+=(int)((now-CurrentTime)/1000);
		}
		CurrentTime=now;
		alarm();
	}
//** sound for Local and GTP                                       //~v106M~//~v1B6R~
	public void yourMove (boolean notinpos)                        //~v106M~
	{	if (notinpos) JagoSound.play("yourmove","stone",true);     //~v106M~
		else                                                       //~v106M~
		if (Global.getParameter("sound.everymove",true))           //~v106M~
			JagoSound.play("stone","click",true);                  //~v106M~
		else                                                       //~v106M~
			JagoSound.play("stone","click",false);                 //~v106M~
	}                                                              //~v106M~
//*************************************************************************//~v1B6I~
//*from GTPConnection                                              //~v1B6I~
//*************************************************************************//~v1B6I~
	public void settime(int Ppresec,int Paftersec,int Paftermove)  //~v1B6R~
	{                                                              //~v1B6I~
		BlackTimeLimit=Ppresec;   //until byoyomi                  //~v1B6R~
		WhiteTimeLimit=Ppresec;   //until byoyomi                  //~v1B6R~
		ExtraMove=Paftermove;                                      //~v1B6I~
		ExtraTime=Paftersec;                                       //~v1B6R~
		CurrentTime=System.currentTimeMillis();                    //~v1B6I~
	}                                                              //~v1B6I~
//*************************************************************************//~v1B6I~
	int lastbeep=0;                                                //~v1B6I~
	public void beep (int s)                                       //~v1B6I~
	{	if (s<0 || !Global.getParameter("warning",true)) return;   //~v1B6I~
		else if (s<31 && s!=lastbeep)                              //~v1B6I~
		{	if (s%10==0)                                           //~v1B6I~
			{	getToolkit().beep();                               //~v1B6I~
				lastbeep=s;                                        //~v1B6I~
			}                                                      //~v1B6I~
		}                                                          //~v1B6I~
	}                                                              //~v1B6I~
//*************************************************************************//~v1B6I~
//*reset timeout limit if moves reached ExtraMove                  //~v1B6I~
//*************************************************************************//~v1B6I~
	public void updateBlackMoves()                                 //~v1B6I~
	{                                                              //~v1B6I~
		if (!BlackTimeout)   //not in ExtraTime                    //~v1B6I~
        	return;                                                //~v1B6I~
        if (colorWinner<0)	//black lost by timeout                //~v1C4R~
        	return;                                                //~v1C4I~
        if (Dump.Y) Dump.println("updateBlackMove="+BlackTimeoutMoves);//~v1C4I~
		BlackTimeoutMoves--;                                       //~v1B6R~
        if (BlackTimeoutMoves<=0)                                 //~v1B6R~
        {                                                          //~v1B6I~
        	BlackTimeoutMoves=ExtraMove;                           //~v1B6R~
            BlackTimeLimit=ExtraTime;                              //~v1B6I~
            BlackTime=0;                                           //~v1B6I~
        }                                                          //~v1B6I~
	}                                                              //~v1B6I~
	public void updateWhiteMoves()                                 //~v1B6I~
	{                                                              //~v1B6I~
		if (!WhiteTimeout)   //not in ExtraTime                    //~v1B6I~
        	return;                                                //~v1B6I~
        if (colorWinner>0)	//white lost by timeout                //~v1C4R~
        	return;                                                //~v1C4I~
		WhiteTimeoutMoves++;                                       //~v1B6I~
        if (WhiteTimeoutMoves<=0)                                  //~v1B6R~
        {                                                          //~v1B6I~
        	WhiteTimeoutMoves=ExtraMove;                          //~v1B6R~
            WhiteTimeLimit=ExtraTime;                              //~v1B6I~
            WhiteTime=0;                                           //~v1B6I~
        }                                                          //~v1B6I~
	}                                                              //~v1B6I~
//*************************************************************************//~v1B6I~
//* timeout warning                                                //~v1B6I~
//*************************************************************************//~v1B6I~
	private void warning_time(int Pcase)                           //~v1B6I~
    {                                                              //~v1B6I~
		int color=B.maincolor();                                   //~v1B6I~
        int msgid,colid;                                                 //~v1B6I~
        String strmsg;                                    //~v1B6I~
        if (color>0)                                               //~v1B6I~
        	colid=R.string.ColorBlack;   //~v1B6I~
        else                                                       //~v1B6I~
        	colid=R.string.ColorWhite;   //~v1B6I~
        switch (Pcase)                                             //~v1B6I~
        {                                                          //~v1B6I~
        case 1:                                                    //~v1B6I~
        	msgid=R.string.GtpErr_EnteredExtraTime;                //~v1B6I~
            break;                                                 //~v1B6I~
        case 2:                                                    //~v1B6I~
        	if (colorWinner!=0)                                    //~v1B6I~
            	return;                                            //~v1B6I~
            colorWinner=(color>0?-1:1);                             //~v1B6I~
        	msgid=R.string.GtpErr_LooseByTimeout;                  //~v1B6I~
            break;                                                 //~v1B6I~
        default:                                                   //~v1B6I~
        	return;                                                //~v1B6I~
        }                                                          //~v1B6I~
        strmsg=AG.resource.getString(msgid);                       //~v1B6I~
        AjagoView.showToastLong(colid," "+strmsg);                 //~v1B6R~
    }//~v1B6I~
//*************************************************************************//~v1B6I~
//* setRule                                                        //~v1B6I~
//*************************************************************************//~v1B6I~
	private void setRule()                                         //~v1B6I~
    {                                                              //~v1B6I~
    	int boardrule=0;                                           //~v1B6I~
        int rule=C.Rules;                                          //~v1B6I~
        if (rule==GTPConnection.RULE_JAPANESE)                     //~v1B6I~
        	boardrule=Board.RULE_JAPANESE;                         //~v1B6I~
        else                                                       //~v1B6I~
        if (rule==GTPConnection.RULE_CHINESE)                      //~v1B6I~
        	boardrule=Board.RULE_CHINESE;                          //~v1B6I~
        B.countRule=boardrule;                                     //~v1B6I~
    }                                                              //~v1B6I~
//*************************************************************************//~v1B6I~
//* human resigned                                                 //~v1B6I~
//*************************************************************************//~v1B6I~
	private void doresign()                                        //~v1B8R~
    {                                                              //~v1B6I~
        if (!confirmResign())                                      //~v1B8R~
           	return;                                                //~v1B8R~
        resign();                                                  //~v1B8R~
    }                                                              //~v1B6I~
//*************                                                    //~v1B8R~
	private void resign()                                          //~v1B8R~
    {                                                              //~v1B8R~
    	C.humanresign(B.maincolor());                              //~v1B8R~
        B.resigned();                                              //~v1B8R~
    }                                                              //~v1B8R~
//*************                                                    //~v1B8R~
//*rc:true:continue resign                                         //~v1B8R~
//*************                                                    //~v1B8R~
	private boolean confirmResign()                                //~v1B8R~
    {                                                              //~v1B8R~
	    int flag;                                                  //~v1B8I~
    	if (B.resignColor!=0)	//already resigned                 //~v1B8R~
        {                                                          //~v1B8I~
        	AjagoView.showToast(R.string.GtpErr_Already_Resigned);  //~v1B8I~
        	return true;                                           //~v1B8R~
        }                                                          //~v1B8I~
//	    flag=AjagoAlert.BUTTON_POSITIVE|AjagoAlert.BUTTON_NEGATIVE|AjagoAlert.SHOW_DIALOG|AjagoAlert.KEEP_CB_THREAD;       //~1604I~//~v1B6I~//~v1B8R~
  	    flag=AjagoAlert.BUTTON_POSITIVE|AjagoAlert.BUTTON_NEGATIVE|AjagoAlert.SHOW_DIALOG;//~v1B8I~
        AjagoAlertI callback=new AjagoAlertI(){                    //~v1B8R~
        	@Override                                              //~v1B8R~
            public int alertButtonAction(int Pbuttonid,int Pitempos)//~v1B8R~
            {                                                      //~v1B8R~
            	if (Pbuttonid==AjagoAlert.BUTTON_POSITIVE)         //~v1B8R~
                {                                                  //~v1B8I~
                	resign();
                }                                                  //~v1B8I~
            	return 0;                                          //~v1B8R~
            }                                                      //~v1B8R~
        };                                                         //~v1B8R~
        AjagoAlert.simpleAlertDialog(callback,AG.resource.getString(R.string.GtpErr_Confirmation),//~v1B8R~
        			AG.resource.getString(R.string.GtpErr_Resign_Confirmation),flag);//~v1B6R~//~v1B8R~
        return false;                                              //~v1B8R~
    }                                                              //~v1B8R~
//*******************************************************************//~v1BaI~
//*callback request:run on BoardSync thread                        //~v1BaR~
//*******************************************************************//~v1BaI~
	public void requestCallback(Object Pobj/*CanvascallbackParm*/)  //~v1BaR~
    {                                                              //~v1BaI~
    	B.requestCallback(this,Pobj);	                           //~v1BaR~
    }                                                              //~v1BaI~
//*******************************************************************//~v1BaI~
//*callback from Canvas                                            //~v1BaI~
//*******************************************************************//~v1BaI~
	@Override                                                      //~v1BaI~
	public void canvasCallback(Object Pobj)                        //~v1BaR~
    {                                                              //~v1BaI~
    	if (Dump.Y) Dump.println("GTPGoFrame;canvascallback");     //~v1BaR~
    	C.C.canvasCallback(Pobj);           //~v1BaR~
    }                                                              //~v1BaI~
}
