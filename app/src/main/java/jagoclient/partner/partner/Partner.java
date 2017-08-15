//*CID://+1B0cR~:                             update#=    3;       //~1B0cI~
//**************************************************************************//~1B0cI~
//1B0c 130429 Encoding support for partner connection              //~1B0cI~
//**************************************************************************//~1B0cI~
package jagoclient.partner.partner;

import java.io.*;

import rene.util.parser.*;

public class Partner
{	public String Name, Server;
	public int Port;
	public boolean Valid,Trying;
	public int State=PRIVATE;
	public String Encoding="";                                     //~1B0cI~
    private static final String ENCODING_PREFIX=" Encoding=[";     //~1B0cM~
	public static final int SILENT=0,PRIVATE=1,LOCAL=2,PUBLIC=3;
	public Partner (String line)
	{	StringParser p=new StringParser(line);
		Valid=false;
		Trying=false;
		p.skip("["); Name=p.upto(']');
		p.skip("]"); p.skipblanks(); if (p.error()) return;
		p.skip("["); Server=p.parseword(']');
		p.skip("]"); p.skipblanks(); if (p.error()) return;
		p.skip("["); Port=p.parseint(']');
		p.skip("]"); p.skipblanks();
		if (p.skip("["))
		{	int s=p.parseint(']');
			if (!p.error()) State=s;
            p.skip("]");                                           //+1B0cI~
		}
		if (p.skip(ENCODING_PREFIX))                               //~1B0cI~
		{                                                          //~1B0cI~
			Encoding=p.parseword(']');                             //~1B0cI~
		}                                                          //~1B0cI~
		Valid=true;
	}
	public Partner (String name, String server, int port, int state)
	{	Valid=true; Trying=false;
		Name=name; Server=server; Port=port; State=state;
	}
	public void write (PrintWriter out)
	{	out.println("["+Name+"] ["+Server+"] ["+
//  			Port+"] ["+State+"]");                             //~1B0cR~
    			Port+"] ["+State+"]"                               //~1B0cI~
                +ENCODING_PREFIX+Encoding+"]"                       //~1B0cI~
                );                                                 //~1B0cI~
	}
	public boolean valid () { return Valid; }
}

