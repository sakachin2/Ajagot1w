package com.Ajagoc.awt;

public class AdjustmentEvent                                       //~1213R~
{                                                                  //~1112I~
    public static final int UNIT_INCREMENT   =1;                   //~1213I~
    public static final int UNIT_DECREMENT   =2;                   //~1213R~
    public static final int BLOCK_INCREMENT   =3;                  //~1213R~
    public static final int BLOCK_DECREMENT   =4;                  //~1213R~
    private Scrollbar scrollbar=null;                              //~1331R~
//****************************                                     //~1331I~
    public AdjustmentEvent(Scrollbar Pscrollbar)                   //~1331R~
    {                                                              //~1331I~
    	scrollbar=Pscrollbar;                                      //~1331R~
    }                                                              //~1331I~
//**for rene.viewer.Viewer************ compile only                //~1331R~
    public Scrollbar getSource()                                   //~1213I~//~1331R~
    {                                                              //~1213I~//~1331R~
        return scrollbar;                                          //~1213I~//~1331R~
    }                                                              //~1213I~//~1331R~
    public int getAdjustmentType()                                 //~1213I~//~1331R~
    {                                                              //~1213I~//~1331R~
        return 0;	//request getValue()                           //~1331R~
    }                                                              //~1213I~//~1331R~
}                                                                  //~1112I~