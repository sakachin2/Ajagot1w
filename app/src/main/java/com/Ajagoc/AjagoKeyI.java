package com.Ajagoc;                                                //+1401R~
import com.Ajagoc.awt.KeyEvent;                                         //~1114R~

//*************************************************                //~1401I~
//* it can return boolean rc to android KeyListener                //~1401I~
//*************************************************                //~1401I~
public interface AjagoKeyI  extends com.Ajagoc.awt.KeyListener              //~1317R~//+1401R~
{                                                                  //~1112I~
    boolean keyPressedRc(KeyEvent ev);                               //~1317R~
    boolean keyReleasedRc(KeyEvent ev);                              //~1317R~
}                                                                  //~1112I~