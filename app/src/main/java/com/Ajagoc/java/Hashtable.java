package com.Ajagoc.java;                                               //~1108R~//~1321R~

import com.Ajagoc.awt.Image;

//*********************************************                    //~1327R~
//* from CloseFrame for Icon file loading                          //+1417R~
//*********************************************                    //~1327I~
                                                                   //~1327I~
public class Hashtable                                             //+1417R~
{                                                                  //~1111I~
    public Hashtable()                                             //+1417R~
    {                                                              //~1111I~
    }                                                              //~1111I~
    public Image get(String Pfile)                                       //+1417I~
    {                                                              //+1417I~
    	return new Image(Pfile);	//dummy!=null                  //+1417I~
    }
    public void put(String Pfilename,Image Pimage)
    {
    }
}
