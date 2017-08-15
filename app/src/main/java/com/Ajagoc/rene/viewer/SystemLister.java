package com.Ajagoc.rene.viewer;

import jagoclient.Dump;

import com.Ajagoc.rene.viewer.Lister;

//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.List;
//import java.awt.PopupMenu;

public class SystemLister extends Lister
{                                                                  //~1220R~
	public SystemLister()                                          //~1220R~
    {                                                              //~1220R~
		super();                                                   //~1220I~
        if (Dump.Y) Dump.println("SystemLister constructor");      //+1506R~
    }
}
