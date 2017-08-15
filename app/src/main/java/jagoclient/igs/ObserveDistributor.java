package jagoclient.igs;

import jagoclient.Dump;

/**
This distributor takes moves from the IgsStream and sends them
to a GoObserver, which is opened elswhere.
*/

public class ObserveDistributor extends Distributor
{	GoObserver P;
	boolean Blocked;
	public ObserveDistributor (IgsStream in, GoObserver p, int n)
	{	super(in,15,n,false);
		P=p;
		Blocked=true;
	}
	public void send (String c)
	{	P.receive(c);
	}
	public void remove ()
	{	P.remove();
	}
	public boolean blocked ()
	{	if (Playing) return false;
		else return Blocked;
	}
	public void refresh ()
	{	P.refresh();
	}
	public void allsended ()
	{	P.sended();
	}
	public boolean started ()
	{	return P.started();
	}
	public void set (int i, int j)
	{	if (Dump.Y) Dump.println("Observe Distributor got move at "+i+","+j);//+1506R~
		P.set(i,j);
	}
	public void pass ()
	{	if (Dump.Y) Dump.println("Observe Distributor got a pass");//+1506R~
		P.pass();
	}
	public boolean newmove ()
	{   return P.newmove();
	}
}

