package jagoclient.igs;

import jagoclient.Dump;
import jagoclient.Global;

import java.io.PrintWriter;

/**
The PlayDistributor is opened with a ConnectionFrame to display
tha board. When its game method is invoked, it will start a Player
object and send output to it.
@see jagoclient.igs.Player
*/

public class PlayDistributor extends Distributor
{	Player P;
	IgsStream In;
	PrintWriter Out;
	ConnectionFrame F;
	public PlayDistributor (ConnectionFrame f, IgsStream in, PrintWriter out)
	{	super(in,15,-1,false);
		F=f;
		In=in; Out=out;
		P=null;
		Playing=true;
	}
	public void send (String c)
	{	if (P!=null) P.receive(c);
	}
	public void remove ()
	{	if (P!=null) P.remove();
		out("adjourn "+G);
	}
	/**
	This method opens an IgsGoFrame and a Player to handle the
	moves.
	*/
	public void game (int n)
	{	if (Dump.Y) Dump.println("Opening go frame for game "+n);  //+1506R~
		IgsGoFrame gf=new IgsGoFrame(F,Global.resourceString("Play_game"));
		gf.distributor(this);
		gf.Playing.setState(true);
		gf.setVisible(true); gf.repaint();
		P=new Player(gf,In,Out,this);
		G=n;
		P.game(n);
		new PlayDistributor(F,In,Out);
	}
	/** called from the goframe to set a move (passed to Player) */
	public void set (int i, int j, int sec)
	{	if (Dump.Y) Dump.println("Play Distributor got move at "+i+","+j);//+1506R~
		P.set(i,j,sec);
	}
	/** called from the goframe to pass (passed to Player) */
	public void pass ()
	{	if (Dump.Y) Dump.println("Play Distributor got a pass");   //+1506R~
		P.pass();
	}
	/** called from the goframe to refresh the board (passed to Player) */
	public void refresh ()
	{	P.refresh();
	}
	public boolean started ()
	{	return P.started();
	}
}

