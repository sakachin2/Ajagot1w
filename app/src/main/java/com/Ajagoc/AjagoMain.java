//*CID://+1AfaR~: update#=  82;                                    //+1AfaR~
//**********************************************************************//~1107I~
//1Afa 2016/07/11 Delete main function to avoid selected main as entrypoint for eclips starter//+1AfaI~
//**********************************************************************//+1AfaI~
//*main view                                                       //~1107I~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import rene.util.list.ListClass;
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.Go;
//**********************************************************************//~1107I~
public class AjagoMain                                             //~1107R~
{                                                                  //~0914I~
	public AjagoMain()                             //~1107I~//~1108R~
    {                                                              //~1107I~
    //************                                                 //~1122I~
    }                                                              //~1107I~
//******************                                               //~1122I~
//******************                                               //~1107I~
    public void startMain()                                          //~1108I~//~1122R~
    {                                                                  //~1108I~//+1122R~                                                                 //~1122R~
    	boolean dump=false;     //release version;set true up to go.cfg was read,later control is by Ajago option//~1507R~
    	AG.mainThread=Thread.currentThread();                      //~1126I~
        init();                                                    //~1407I~
    	AG.go=new Go();                                                //~1108R~//~1109R~//~1122R~
        String[] args=new String[4];                               //~1506R~
        args[0]="-h";                                              //~1401I~
        args[1]="";                                                //~1401I~
        if (dump)                                                  //~1401I~
        {                                                          //~1506I~
        	Dump.Y=true;                                           //~1506R~
            args[2]="-d";     //dump                               //~1506R~
            args[3]="-t";     //terminal                           //~1506I~
        }                                                          //~1506I~
        else                                                       //~1401I~
        {                                                          //~1506I~
            args[2]="";                                            //~1401I~
            args[3]="";                                            //~1506I~
        }                                                          //~1506I~
//      Go.main(args);                                                 //~1108R~//~1109R~//~1122R~//~1401R~//+1AfaR~
        Go.GoMain(args);                                           //+1AfaI~
    }                                                                  //~1108I~//~1122R~
//******************                                               //~1108I~
	private void init()                                                  //~1407R~
	{                                                                  //~1109I~//~1407R~
		Global.OpenPartnerList=new ListClass();		//avaoid abend at OpenPartnerFrame.connect()//~1407I~
    }                                                              //~1109I~//~1407R~
}//class AjagoMain                                                 //~dataR~//~1107R~
