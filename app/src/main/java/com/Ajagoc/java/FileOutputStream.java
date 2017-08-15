package com.Ajagoc.java;                                               //~1108R~//~1321R~

import java.io.FileNotFoundException;

import com.Ajagoc.AjagoProp;
//*********************************************                    //~1327R~
//* from PrintWriter used on Go.java                               //~1327I~
//*********************************************                    //~1327I~
                                                                   //~1327I~
public class FileOutputStream extends java.io.FileOutputStream                                     //~1317R~//+1401R~
{                                                                  //~1111I~
    public FileOutputStream(String Pfile) throws FileNotFoundException                           //~1111I~//~1124R~//~1317R~
    {                                                              //~1111I~
        super(AjagoProp.getOutputFilenameData(Pfile));       //~1317I~//+1401R~
    }                                                              //~1111I~
}
