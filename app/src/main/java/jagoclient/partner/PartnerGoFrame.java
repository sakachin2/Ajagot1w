//*CID://+1A74R~:                                   update#=   27; //+1A74R~
//****************************************************************************//~@@@1I~
//2015/03/06 1A74 2015/02/23 toast for delayed response(Game not started,already end)//+1A74I~
//v1Ef 2014/12/12 construct save filename by whitename and blackname//~v1EfI~
//v1Dp 2014/11/15 write to comment "resign"/"accept"/"recline"     //~v1DoI~
//v1Do 2014/11/14 if extrasendfield is null,popup sayquestiondisalog//~v1DoI~
//v1D3 2014/10/07 set timestamp to filename on filedialog when save(current is *.sgf)//~v1D3I~
//1B08 130428 Partner Connection:display msg to comment area by send icon button//~1B05I~
//            even when ExtraSendField option is off like as PartnerSendQuetion.//~1B05I~
//            And also received msg.                               //~1B05I~
//            (receive msg is displayed on ConnectionFrame, but it is back of GoFrame)//~1B05I~
//1B05 130428 SendForward is IGS only(set visibility:GONE for PartnerConnection)//~1B05I~
//1103:130124 board stop timing(avoid exception)                   //~v110I~
//107b:121209 (OriginalBug)PartnerFrame did not stop GoTimer       //~v107I~
//1065:121124 PartnerConnection;FinishGame-->EndGame:send EndGEm req and if responsed, allog RemoveGroup.//~v106I~
//            Igs and GMP is FinishGame-->Remove groupe/prisoner   //~v106I~
//            change Menu Item Text for partner connection(send End game request)//~v106I~
//            Isue reply msg and notify "Remove Prisoner" avalable //~v106I~
//v106:1064:121124 beep also on partner connection(no beep even when beeponly for partnerconnection)//~v106I~
//*@@@1 20110430 change GoTimer interval 100-->1000ms              //~@@@1I~
//*@@@1 20110430 FunctionKey support                               //~@@@1I~
//*@@@1 20110430 add "resign" to FinishGame menu                   //~@@@1I~
//****************************************************************************//~@@@1I~
//****************************************************************************//~@@@1I~
package jagoclient.partner;

import android.view.View;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;
import com.Ajagoc.awt.Component;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.KeyEvent;
import com.Ajagoc.awt.KeyListener;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.board.ConnectedGoFrame;
import jagoclient.board.GoTimer;
import jagoclient.board.OutputFormatter;
import jagoclient.board.TimedBoard;
import jagoclient.dialogs.Message;
import jagoclient.sound.JagoSound;

/**
The go frame for partner connections.
*/

public class PartnerGoFrame extends ConnectedGoFrame 
	implements TimedBoard
    , KeyListener                                                  //~@@@1I~
{	String BlackName,WhiteName;
	int BlackTime,WhiteTime,BlackMoves,WhiteMoves;
	int BlackRun,WhiteRun;
  public                                                           //~v110I~
	GoTimer Timer;
	long CurrentTime;
	PartnerFrame PF;
	int Col,TotalTime,ExtraTime,ExtraMoves;
    public boolean Started,Ended;                                //~v107R~
	int Handicap;
	
	public PartnerGoFrame (PartnerFrame pf, String s,
		int col, int si, int tt, int et, int em, int ha)
	{	super(s,si,Global.resourceString("End_Game"),Global.resourceString("Count"),false,false);
		PF=pf;
		Col=col; TotalTime=tt; ExtraTime=et; ExtraMoves=em;
		BlackTime=TotalTime; WhiteTime=TotalTime;
		Handicap=ha;
		BlackRun=0; WhiteRun=0;
		Started=false; Ended=false;
//        if (Col==1) BlackName=Global.resourceString("You");      //~v1D3R~
//        else BlackName=Global.resourceString("Opponent");        //~v1D3R~
//        if (Col==-1) WhiteName=Global.resourceString("You");     //~v1D3R~
//        else WhiteName=Global.resourceString("Opponent");        //~v1D3R~
        if (Col==1)   //I'm black                                  //~v1D3I~
        {                                                          //~v1D3I~
        	if (PF.myName.equals(""))                              //~v1D3I~
        		BlackName=Global.resourceString("You");            //~v1D3I~
            else                                                   //~v1D3I~
        		BlackName=PF.myName;                               //~v1D3I~
        }                                                          //~v1D3I~
        else	//opponent is black                                //~v1D3I~
        {                                                          //~v1D3I~
        	if (PF.partnerName.equals(""))                         //~v1D3I~
		        BlackName=Global.resourceString("Opponent");       //~v1D3I~
            else                                                   //~v1D3I~
        		BlackName=PF.partnerName;                          //~v1D3I~
        }                                                          //~v1D3I~
        if (Col==-1)    //I'm white                                //~v1D3I~
        {                                                          //~v1D3I~
        	if (PF.myName.equals(""))                              //~v1D3I~
        		WhiteName=Global.resourceString("You");            //~v1D3I~
            else                                                   //~v1D3I~
        		WhiteName=PF.myName;                               //~v1D3I~
        }                                                          //~v1D3I~
        else	//opponent is white                                //~v1D3I~
        {                                                          //~v1D3I~
        	if (PF.partnerName.equals(""))                         //~v1D3I~
		        WhiteName=Global.resourceString("Opponent");       //~v1D3I~
            else                                                   //~v1D3I~
        		WhiteName=PF.partnerName;                          //~v1D3I~
        }                                                          //~v1D3I~
//      String bc=AG.resource.getString(R.string.SideBlack);       //~v1D3R~
//      String wc=AG.resource.getString(R.string.SideWhite);       //~v1D3R~
//      String matchTitle=wc+WhiteName+" vs "+bc+BlackName;        //~v1D3R~
        String matchTitle=WhiteName+"-"+BlackName;                 //~v1D3R~
        if (Dump.Y) Dump.println("partnerGoFrame title="+matchTitle);//~v1D3I~
        setTitle(matchTitle);                                      //~v1D3R~
        adjustView();                                              //~1B05I~
		addKeyListener(this);                                      //~@@@1I~
		setVisible(true);
		repaint();
	}
//*******************************************************************//~1B05I~
//set not used view to GONE                                        //~1B05I~
//*******************************************************************//~1B05I~
	private void adjustView()                                      //~1B05I~
    {                                                              //~1B05I~
    	View v;                                                    //~1B05I~
        v=findView(R.id.sendforwardcontainer);	//igs only         //~1B05I~
        Component.setVisible(v,View.GONE);                         //~1B05I~
		if (!ExtraSendField)                                       //~1B05I~
        {                                                          //~1B05I~
        	v=findView(R.id.BottomInputLine);			//sendfield container//~1B05I~
        	Component.setVisible(v,View.GONE);                     //~1B05I~
        }                                                          //~1B05I~
    }                                                              //~1B05I~
//*******************************************************************//~1B05I~

	public void doAction (String o)
	{	if (Global.resourceString("Send").equals(o) || (ExtraSendField && Global.resourceString("ExtraSendField").equals(o)))
		{	if (ExtraSendField)
			{	if (!SendField.getText().equals(""))
				{	PF.out(SendField.getText());
					addComment(Global.resourceString("Said__")+SendField.getText());//~1B05I~
					SendField.remember(SendField.getText());
					SendField.setText("");
				}
                else                                               //~v1DoR~
					new PartnerSendQuestion(this,PF);              //~v1DoR~
				return;
			}
			else
			{	new PartnerSendQuestion(this,PF);
				return;
			}
		}
		else if (Global.resourceString("End_Game").equals(o))
		{	if (Col!=B.maincolor()) return;
			addComment(Global.resourceString("Said__")+o);         //~v1DpI~
			PF.endgame();
			return;
		}
		else if (Global.resourceString("Count").equals(o))
		{	if (Ended || !B.ismain())
			{	String s=B.done();
				if (s!=null) new Message(this,s);
			}
			return;
		}
		else if (Global.resourceString("Undo").equals(o))
		{	if (Ended || !B.ismain()) B.undo();
			else
			{	if (Col!=B.maincolor()) return;
				B.undo();
			}
			return;
		}
		else if (Global.resourceString("Undo_Adding_Removing").equals(o))
		{	B.clearremovals();
			return;
		}
		else if (Global.resourceString("Resign").equals(o))        //~@@@1I~
		{                                                          //~@@@1I~
			addComment(Global.resourceString("Said__")+o);         //~v1DpR~
			PF.out("resign");                                      //~@@@1I~
			PF.endgame();                                          //~@@@1I~
			return;                                                //~@@@1I~
		}                                                          //~@@@1I~
		else super.doAction(o);
	}

	public boolean blocked ()
	{	return false;
	}

	public boolean wantsmove ()
	{	return true;
	}

	public boolean moveset (int i, int j)
//	{	if (!Started || Ended) return false;                       //+1A74R~
    {                                                              //+1A74I~
        if (!Started || Ended)                                     //+1A74I~
        {                                                          //+1A74I~
        	if (!Started)                                          //+1A74I~
	        	AjagoView.showToast(R.string.InfoGameNotStarted);      //+1A74I~
            else                                                   //+1A74I~
	        	AjagoView.showToast(R.string.InfoGameAlreadyEnded);    //+1A74I~
        	return false;                                          //+1A74I~
        }                                                          //+1A74I~
		String color;
		if (B.maincolor()!=Col) return false;
		if (B.maincolor()>0) color="b";
		else color="w";
		if (Timer.isAlive()) alarm();
		int bm=BlackMoves,wm=WhiteMoves;
		if (Col>0) { if (BlackMoves>0) BlackMoves--; }
		else { if (WhiteMoves>0) WhiteMoves--; }
		if (!PF.moveset(color,i,j,BlackTime-BlackRun,BlackMoves,
			WhiteTime-WhiteRun,WhiteMoves))
		{	BlackMoves=bm; WhiteMoves=wm;
			if (Timer.isAlive()) alarm();
			return false;
		}
		return true;
	}

	public void movepass ()
	{	if (!Started || Ended) return;
		if (B.maincolor()!=Col) return;
		if (Timer.isAlive()) alarm();
		if (Col>0) { if (BlackMoves>0) BlackMoves--; }
		else { if (WhiteMoves>0) WhiteMoves--; }		
		PF.pass(BlackTime-BlackRun,BlackMoves,
			WhiteTime-WhiteRun,WhiteMoves);
	}

	public void dopass ()
	{	B.setpass();
	}

	public void undo () { PF.undo(); }

	public void undo (int n)
	{	B.undo(n);
	}

	public void settimes (int bt, int bm, int wt, int wm)
	{	BlackTime=bt; BlackRun=0;
		WhiteTime=wt; WhiteRun=0;
		WhiteMoves=wm; BlackMoves=bm;
		CurrentTime=System.currentTimeMillis();
		settitle();
	}

	public void doclose ()
	{	setVisible(false); dispose();
		if (Timer!=null && Timer.isAlive()) Timer.stopit();        //~v107I~
		PF.toFront();
		PF.boardclosed(this);
		PF.PGF=null;
	}

	String OldS="";
	void settitle ()
	{	String S;
		if (BigTimer) 
			S=WhiteName+" "+formmoves(WhiteMoves)+" - "+
			BlackName+" "+formmoves(BlackMoves);
		else
			S=WhiteName+" "+formtime(WhiteTime-WhiteRun)+" "+formmoves(WhiteMoves)+" - "+
			BlackName+" "+formtime(BlackTime-BlackRun)+" "+formmoves(BlackMoves);
		if (!S.equals(OldS))
		{	if (!TimerInTitle) TL.setText(S);
			else setTitle(S);
			OldS=S;
		}
		if (BigTimer)
		{	BL.setTime(WhiteTime-WhiteRun,BlackTime-BlackRun,WhiteMoves,BlackMoves,Col);
			BL.repaint();
		}
		if (Col>0 && B.maincolor()>0) beep(BlackTime-BlackRun);
		if (Col<0 && B.maincolor()<0) beep(WhiteTime-WhiteRun);
	}

	char form[]=new char[32];

	String formmoves (int m)
	{	if (m<0) return "";
		form[0]='(';
		int n=OutputFormatter.formint(form,1,m);
		form[n++]=')';
		return new String(form,0,n);
	}
	
	String formtime (int sec)
	{	int n=OutputFormatter.formtime(form,sec);
		return new String(form,0,n);
	}

	public void alarm ()
	{	long now=System.currentTimeMillis();
		if (B.maincolor()>0) BlackRun=(int)((now-CurrentTime)/1000);
		else WhiteRun=(int)((now-CurrentTime)/1000);
		if (Col>0 && BlackTime-BlackRun<0)
		{	if (BlackMoves<0)
			{	BlackMoves=ExtraMoves;
				BlackTime=ExtraTime; BlackRun=0;
				CurrentTime=now;
			}
			else if (BlackMoves>0)
			{	new Message(this,Global.resourceString("Black_looses_by_time_"));
				Timer.stopit();
			}
			else
			{	BlackMoves=ExtraMoves;
				BlackTime=ExtraTime; BlackRun=0;
				CurrentTime=now;
			}
		}
		else if (Col<0 && WhiteTime-WhiteRun<0)
		{	if (WhiteMoves<0)
			{	WhiteMoves=ExtraMoves;
				WhiteTime=ExtraTime; WhiteRun=0;
				CurrentTime=now;
			}
			else if (WhiteMoves>0)
			{	new Message(this,Global.resourceString("White_looses_by_time_"));
				Timer.stopit();
			}
			else
			{	WhiteMoves=ExtraMoves;
				WhiteTime=ExtraTime; WhiteRun=0;
				CurrentTime=now;
			}
		}
		settitle();
	}

	int lastbeep=0;
	public void beep (int s)
	{	if (s<0 || !Global.getParameter("warning",true)) return;
		else if (s<31 && s!=lastbeep)
		{	if (s%10==0)
			{	getToolkit().beep();
				lastbeep=s;
			}
		}
	}

	int maincolor ()
	{	return B.maincolor();
	}

	void start ()
	{	Started=true; Ended=false;
		CurrentTime=System.currentTimeMillis();
		BlackRun=0; WhiteRun=0;
		BlackMoves=-1; WhiteMoves=-1;
//  	Timer=new GoTimer(this,100);         //100ms is too busy   //~@@@1R~
    	Timer=new GoTimer(this,1000);        //same as IgsGoFrame  //~@@@1I~
		if (Handicap>0) B.handicap(Handicap);
	}
	
	void setHandicap ()
	{	if (Handicap>0) B.handicap(Handicap);
		Handicap=0;
	}

	void doscore ()
	{	B.score();
		Timer.stopit();
		Ended=true;
        AjagoView.endGameConfirmed();                              //~v106R~
	}

	public void result (int b, int w)
	{	PF.out("@@result "+b+" "+w);
	}

	public void addtime (int s)
	{	if (Col>0) BlackTime+=s;
		else WhiteTime+=s;
		settitle();
	}

	public void addothertime (int s)
	{	if (Col>0) WhiteTime+=s;
		else BlackTime+=s;
		settitle();
	}

	public void yourMove (boolean notinpos)
	{	if (notinpos) JagoSound.play("yourmove","stone",true);
//  	else JagoSound.play("stone","click",false);                //~v106R~
    	else JagoSound.play("stone","click",true);                 //~v106I~
	}
//*FunctionKey support                                             //~@@@1I~
	public void keyPressed (KeyEvent e) {}                         //~@@@1I~
	public void keyTyped (KeyEvent e) {}                           //~@@@1I~
	public void keyReleased (KeyEvent e)                           //~@@@1I~
	{	String s;                                                  //~@@@1I~
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) s="";              //~@@@1I~
		else                                                       //~@@@1I~
		{	s=Global.getFunctionKey(e.getKeyCode());               //~@@@1I~
			if (s.equals("") || !ExtraSendField) return;           //~@@@1I~
		}                                                          //~@@@1I~
		SendField.setText(s);                                      //~@@@1I~
	}                                                              //~@@@1I~
    //******************************************                   //~v1EfI~
	public String getMatchName()                                   //~v1EfI~
	{                                                              //~v1EfI~
    	return WhiteName+"-"+BlackName;                            //~v1EfI~
	}                                                              //~v1EfI~
}

