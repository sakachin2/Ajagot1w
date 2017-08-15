package com.Ajagoc.awt;                                                //~1108R~//~1109R~

import jagoclient.Dump;
                                                                   //~1401I~
//*for GoFrame:Clipboard processing                                //~1401I~
                                                                   //~1401I~
public class StringSelection implements Transferable               //~1212R~//~1401R~
{                                                                  //~1212I~
	private String stringSelection;                                 //~1212I~//~1401R~
	public StringSelection(String Pstr)                            //~1212I~
    {                                                              //~1212I~
    	stringSelection=Pstr;                                      //~1212I~//~1401R~
    }                                                              //~1212I~
	public String getTransferData(DataFlavor Pdf)                  //~1401I~
    {                                                              //~1401I~
        if (Dump.Y) Dump.println("Clipboard getTransferData"+stringSelection);//+1506R~
    	return stringSelection;                                    //~1401I~
    }                                                              //~1401I~
}//class                                                           //~1212R~
