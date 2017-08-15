//*CID://+v1B6R~:                             update#=    1;       //+v1B6I~
//***************************************************************************//+v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//+v1B6I~
//***************************************************************************//+v1B6I~
package com.Ajagoc.gtp;                                            //+v1B6I~

public interface GTPInterface                                      //+v1B6R~
{	public int getHandicap ();
	public int getColor ();
	public int getRules ();
	public int getBoardSize ();
	public void gotMove (int color, int pos);
	public void gotOk ();
	public void gotAnswer (int a);
}
