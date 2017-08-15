package com.Ajagoc.java;                                               //~1108R~//~1321R~

import com.Ajagoc.AjagoGMP;
                                                                   //~1327I~
@SuppressWarnings("serial")
public class File extends java.io.File                              //~1516R~
{                                                                  //~1111I~
	public File(String Ppgmname)                                   //+1516R~
    {                                                              //~1510I~
    	super(AjagoGMP.checkDefaultProgram(Ppgmname));             //+1516R~
    }                                                              //~1510I~
}
