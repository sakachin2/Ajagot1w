//*CID://+dateR~: update#= 181;                                    //~1107R~
//**********************************************************************//~1107I~
//*Canvas for Example Dialog(Color,Font)                                  //~1225R~//~1331R~
//**********************************************************************//~1107I~
package com.Ajagoc.jagoclient.getfontsize;                                         //~1107R~  //~1108R~//~1109R~//~1117R~//~1414I~

import com.Ajagoc.awt.Font;
import com.Ajagoc.awt.FontMetrics;

import android.graphics.Paint;
import jagoclient.Dump;


//*************************************************                //~1331I~
//*Graphics for ExampleCanvas                                      //~1331R~
//*************************************************                //~1331I~
public class Graphics                                              //~1331R~
{                                                                  //~1331R~
                          //~1331R~
    private Font font;                                             //~1331R~
    private Paint textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);    //~1317I~//~1331R~
    public  android.graphics.Canvas androidCanvas;                 //~1331R~
    private FontMetrics fm;                                        //~1502I~
//******************************                                   //~1331R~
    //*****************************                                //~1331R~
    public void setFont(Font Pfont)                                //~1331R~
    {                                                              //~1331R~
        font=Pfont;                                                //~1331R~
    }                                                              //~1331R~
    //*****************************                                //~1331R~
    public FontMetrics getFontMetrics()           //~1117M~        //~1331R~
    {                                                              //~1117M~//~1331R~
        textPaint.setTypeface(font.getTypefaceStyle());           //~1117M~//~1331R~
        textPaint.setTextSize(font.getSize());                    //~1117M~//~1331R~
        fm=new FontMetrics(font,textPaint.getFontMetrics());       //~1502R~
        if (Dump.Y) Dump.println("ExampleCanvas GetFomtPretrics"+fm.toString());//+1506R~
        return fm;                                                 //~1331R~
    }                                                              //~1117M~//~1331R~
    //*****************************                                //~1331R~
    //* Py from GetFontSize is coordinate of top of char           //~1502I~
    //*****************************                                //~1502I~
    public void drawString(String s,int Px,int Py)                 //~1117I~//~1331R~
    {                                                              //~1117I~//~1331R~
    	int yy=Py+fm.getAscent();                                  //~1502I~
        if (Dump.Y) Dump.println("ExampleCanvas Graphics:DrawText:"+s+",x="+Px+",y="+Py+",yy="+yy);//+1506R~
        androidCanvas.drawText(s,Px,yy,textPaint);          //~1117I~//~1120R~//~1502R~
    }                                                              //~1117I~//~1331R~
}//Graphics                                                        //~1331R~
