//*CID://+1Ag9R~: update#= 140;                                    //+1Ag9R~
//**********************************************************************//~1107I~
//1Ag9 2016/10/09 Bitmap OutOfMemory;JNI Global reference remains. //+1Ag9I~
//                try to clear ref to bitmap from Image:fieldBitmap, Graphics:targetBitmap, android.Graphics.Canvas(<--Image:androidCanvas, Graphics:androidCanvas)//+1Ag9I~
//1Ag8 2016/10/09 no effect of 1Ag7, recycling StaticImage/staticShadowImage is better because it was used to draw Empty/EmptyStatic and no more used.//~1Ag8I~
//1Ag7 2016/10/09 1Ag6 has no effect,avoid bitmap duplicated load  //~1Ag7I~
//1Ag6 2016/10/09 It is cause of OutOfMemory? AjagoEmpty missed to close input stream//~1Ag6I~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import jagoclient.Dump;
import jagoclient.board.Board;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Image;





public class AjagoEmpty                                            //~1312R~
{                                                                  //~0914I~
	public Image staticImage;                                      //~1514R~
	public Image staticShadowImage;                                //~1514R~
    private static Integer lockword=new Integer(0);                //~1514R~
    private static String savedFilename;                           //~1Ag7R~
    private static Image savedImage,savedShadowImage;                    //~1Ag7R~
	public AjagoEmpty()                                //~0914R~//~dataR~//~1107R~//~1111R~//~1312R~
    {                                                              //~0914I~
    }                                                              //~0914I~
//*************************                                        //~1109I~//~1111I~//~1122M~
	public boolean load(int Pw,int Ph,Color Pc,boolean Pshadows,int Pox,int Poy,int Pd)//~1514R~
    {                                                              //~1120I~//~1122M~
    	InputStream is;                                            //~1312I~
        Bitmap bm;                                                 //~1312I~
        String filename=getFilename(Pw,Ph,Pc,Pox,Poy,Pd);           //~1312I~
        if (Dump.Y) Dump.println("AjagoEmpty:load Bitmap-load start:"+filename);//~6A09R~
//  	if (reuseBitmap(filename,Pshadows))                        //~1Ag8R~
//      	return true;                                           //~1Ag8R~
        if (Pshadows)                                              //~1312I~
        {                                                          //~1312I~
        	synchronized(lockword)                                 //~1514I~
            {                                                      //~1514I~
                is=AjagoProp.openInputData(filename+"-Shadow");    //~1514R~
                if (is==null)                                      //~1514R~
                    return false;                                  //~1514R~
                bm=BitmapFactory.decodeStream(is);                 //~1514R~
            }                                                      //~1514I~
            try                                                    //~1Ag6I~
            {                                                      //~1Ag6I~
                is.close();                                        //~1Ag6I~
            }                                                      //~1Ag6I~
            catch(IOException e)                                   //~1Ag6I~
            {                                                      //~1Ag6I~
                Dump.println(e,"AjagoEmpty:load close failed"+filename);//~1Ag6I~
            }                                                      //~1Ag6I~
            if (bm==null)                                               //~1312I~
            	return false;                                      //~1312I~
        	if (Dump.Y) Dump.println("Bitmap-load shadow end:"+filename+",bitmap="+((Object)bm).toString());//~1506R~
            staticShadowImage=new Image(Pw,Ph,bm);                 //~1312I~
//          staticShadowImage.setNoRecycle(true);                  //~1Ag8R~
            savedShadowImage=staticShadowImage;                    //~1Ag7I~
        }                                                          //~1312I~
        synchronized(lockword)                                     //~1514I~
        {                                                          //~1514I~
        	is=AjagoProp.openInputData(filename);                  //~1514R~
        	if (is==null)                                          //~1514R~
            	return false;                                      //~1514R~
        	bm=BitmapFactory.decodeStream(is);                     //~1514R~
        }                                                          //~1514I~
        try                                                        //~1Ag6I~
        {                                                          //~1Ag6I~
            is.close();                                            //~1Ag6I~
        }                                                          //~1Ag6I~
        catch(IOException e)                                       //~1Ag6I~
        {                                                          //~1Ag6I~
            Dump.println(e,"AjagoEmpty:load close failed"+filename);//~1Ag6I~
        }                                                          //~1Ag6I~
        if (bm==null)                                                   //~1312I~
            return false;                                          //~1312I~
        staticImage=new Image(Pw,Ph,bm);                           //~1312I~
//      staticImage.setNoRecycle(true);                            //~1Ag8R~
        savedImage=staticImage;                                    //~1Ag7I~
        savedFilename=filename;                                    //~1Ag7I~
        if (Dump.Y) Dump.println("Bitmap-load end:"+filename+",bitmap="+((Object)bm).toString());//~1506R~
        return true;                                               //~1312R~
    }                                                              //~1120I~//~1122M~
//*************************                                        //~1312I~
	public static void save(Board Pboard,Image PstaticShadow,Image Pstatic,int Pw,int Ph,Color Pc,boolean Pshadows,int Pox,int Poy,int Pd)//~1514R~
    {                                                              //~1312I~
		byte[] bytedata;                                           //~1312I~
        String filename=getFilename(Pw,Ph,Pc,Pox,Poy,Pd);           //~1312I~
        if (Dump.Y) Dump.println("AjagoEmpty:save Bitmap-save start:"+filename);//~6A09R~
        if (Pshadows)                                               //~1312I~
        {                                                          //~1312I~
			bytedata=bmp2byte(PstaticShadow.bitmap);               //~1312I~
	        synchronized(lockword)                                 //~1514I~
    	    {                                                      //~1514I~
    			AjagoProp.writeOutputData(filename+"-Shadow",bytedata);//~1514R~
        	}                                                      //~1514R~
//          Pboard.parentWindow.recyclePrepare(PstaticShadow.bitmap);	//recycled at trywood,for the case closed before it//+1Ag9R~
            Pboard.parentWindow.recyclePrepare(PstaticShadow);	//recycled at trywood,for the case closed before it//+1Ag9I~
        }                                                          //~1514I~
        bytedata=bmp2byte(Pstatic.bitmap);                         //~1312I~
	    synchronized(lockword)                                     //~1514I~
    	{                                                          //~1514I~
	        AjagoProp.writeOutputData(filename,bytedata);          //~1514R~
        }                                                          //~1514I~
//      Pboard.parentWindow.recyclePrepare(Pstatic.bitmap);	       //+1Ag9R~
        Pboard.parentWindow.recyclePrepare(Pstatic);               //+1Ag9I~
        if (Dump.Y) Dump.println("Bitmap-save end:"+filename+",obj="+((Object)Pstatic.bitmap).toString());//~1506R~
    }                                                              //~1312I~
//*************************                                        //~1312I~
	public static String getFilename(int Pw,int Ph,Color Pc,int Pox,int Poy,int Pd)//~1312I~
    {                                                              //~1312I~
        return ("Emptybitmap.W"+Pw+"H"+Ph+"C"+Integer.toHexString(Pc.getRGB())//~1312R~
				+"Ox"+Pox+"Oy"+Poy+"D"+Pd);                        //~1312R~
    }                                                              //~1312I~
//*************************                                        //~1312I~
	public static byte[] bmp2byte(Bitmap Pbitmap)                         //~1312I~
    {                                                              //~1312I~
    	Bitmap.CompressFormat fmt=Bitmap.CompressFormat.PNG;       //~1312I~
        int quality=100;       //100% no meaning for PNG           //~1312I~
    	ByteArrayOutputStream os=new ByteArrayOutputStream();      //~1312I~
        Pbitmap.compress(fmt,quality,os);                          //~1312I~
        return os.toByteArray();                                   //~1312I~
    }                                                              //~1312I~
//*************************                                        //~1Ag7I~
	private boolean reuseBitmap(String Pfilename,boolean Pshadows)                  //~1Ag7I~
    {                                                              //~1Ag7I~
    	boolean rc=false;                                          //~1Ag7I~
        for (;;)                                                   //~1Ag7I~
        {                                                          //~1Ag7I~
        	if (savedFilename==null)                               //~1Ag7R~
            {                                                      //~1Ag7I~
        		if (Dump.Y) Dump.println("AjagoEmpty reuseBitmap false savedFilename==null");//~1Ag7I~
            	break;                                             //~1Ag7I~
            }                                                      //~1Ag7I~
        	if (!Pfilename.equals(savedFilename))	//duplicated   //~1Ag7I~
            {                                                      //~1Ag7I~
        		if (Dump.Y) Dump.println("AjagoEmpty reuseBitmap false savedFilename!=Pfilename");//~1Ag7I~
        		if (Dump.Y) Dump.println("AjagoEmpty reuseBitmap savedFilename"+savedFilename);//~1Ag7I~
        		if (Dump.Y) Dump.println("AjagoEmpty reuseBitmap Pfilename    "+Pfilename);//~1Ag7I~
            	break;                                             //~1Ag7I~
            }                                                      //~1Ag7I~
	        if (Pshadows)                                          //~1Ag7I~
            	if (savedShadowImage==null)                        //~1Ag7I~
                {                                                  //~1Ag7I~
        			if (Dump.Y) Dump.println("AjagoEmpty reuseBitmap false savedShadowImage=null");//~1Ag7I~
                	break;                                         //~1Ag7I~
                }                                                  //~1Ag7I~
            if (savedImage==null)                                  //~1Ag7I~
            {                                                      //~1Ag7I~
        		if (Dump.Y) Dump.println("AjagoEmpty reuseBitmap false savedImage=null");//~1Ag7I~
                break;                                             //~1Ag7I~
            }                                                      //~1Ag7I~
            rc=true;                                               //~1Ag7I~
            break;                                                 //~1Ag7I~
        }                                                          //~1Ag7I~
        if (!rc)	//not reusable                                 //~1Ag7I~
        {                                                          //~1Ag7I~
        	if (savedShadowImage!=null)                            //~1Ag7I~
            {                                                      //~1Ag7I~
            	savedShadowImage.setNoRecycle(false);               //~1Ag7I~
            	savedShadowImage.recycle();                         //~1Ag7I~
                savedShadowImage=null;                             //~1Ag7I~
            }                                                      //~1Ag7I~
        	if (savedImage!=null)                                  //~1Ag7I~
            {                                                      //~1Ag7I~
            	savedImage.setNoRecycle(false);                     //~1Ag7I~
            	savedImage.recycle();                               //~1Ag7I~
                savedImage=null;                                   //~1Ag7I~
            }                                                      //~1Ag7I~
        }                                                          //~1Ag7I~
        else                                                       //~1Ag7I~
        {                                                          //~1Ag7I~
	    	if (Pshadows)                                          //~1Ag7I~
    			staticShadowImage=savedShadowImage;                //~1Ag7I~
        	staticImage=savedImage;                                //~1Ag7I~
        }                                                          //~1Ag7I~
        if (Dump.Y) Dump.println("AjagoEmpty reuseBitmap rc="+rc+",filename="+Pfilename);//~1Ag7R~
        return rc;                                                 //~1Ag7I~
	}//reuseBitmap                                                 //~1Ag7I~
}//class AjagoView                                                 //~dataR~
