//*CID://+1Ag9R~:                             update#=   11;       //~1Ag9R~
//*******************************************************************//~v1E4I~
//1Ag9 2016/10/09 Bitmap OutOfMemory;JNI Global reference remains. //~1Ag9I~
//                try to clear ref to bitmap from Image:fieldBitmap, Graphics:targetBitmap, android.Graphics.Canvas(<--Image:androidCanvas, Graphics:androidCanvas)//~1Ag9I~
//1Ag7 2016/10/09 1Ag6 has no effect,avoid bitmap duplicated load  //~1Ag7I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing, (Dump.T) for UiThread//~1A6AI~
//v1E4 2014/12/08 (Asgts)//1A4h 2014/12/03 catch OutOfMemory       //~v1E4I~
//*******************************************************************//~v1E4I~
package com.Ajagoc.awt;

import jagoclient.Dump;

import com.Ajagoc.AG;
import com.Ajagoc.awt.Graphics;
import com.Ajagoc.awt.MemoryImageSource;
import com.Ajagoc.awt.Component;

import android.annotation.TargetApi;
import android.graphics.Bitmap;

//Image:for Board                                                  //~1117R~
public class Image                                                 //~1421R~
{                                                                  //~1112I~
    public static final int BOARD  =0;                             //~1227I~
    public static final int WOOD   =1;                             //~1227I~
    public static final int STONE  =2;                             //~1227I~
    public static final int OTHER  =3;                             //~1414I~
	private Graphics graphics;                                     //~1224R~
    public int width,height;                                       //~1425R~
	public  android.graphics.Canvas androidCanvas;                 //~1228I~
    public Bitmap bitmap;                                          //~1425R~
    public String iconFilename;                                    //~1425R~
    private boolean swNoRecycle;                                   //~1Ag7I~
//****************                                                 //~1417I~
    public Image(int Pw,int Ph)                                    //~1224R~
    {                                                              //~1117I~
    	width=Pw;
    	height=Ph;                                        //~1117I~
    	bitmap=createBitmap(null,width,height);                    //~1421I~
        androidCanvas=new android.graphics.Canvas(bitmap);        //~1226I~//~1312R~
    	graphics=new Graphics(this);                               //~1228R~
    	if (Dump.Y) Dump.println("Bitmap w="+bitmap.getWidth()+",h="+bitmap.getHeight()+",byte="+getByteCount(bitmap));//~1506R~//~1Ag7R~
    }                                                              //~1117I~
//****************                                                 //~1417I~
//from CloseFrame.seticon-->Hashtable                              //~1417I~
//****************                                                 //~1417I~
    public Image(String Pfilename)                                 //~1417I~
    {                                                              //~1417I~
        if (Dump.C) Dump.println("Image Iconfilename="+Pfilename); //~1506R~//~1A6AR~
        iconFilename=Pfilename;                                    //~1417I~
    }                                                              //~1417I~
//****************                                                 //~1312I~
    public Image(int Pw,int Ph,Bitmap Pbitmap)                     //~1312I~
    {                                                              //~1312I~
    	width=Pw;                                                  //~1312I~
    	height=Ph;                                                 //~1312I~
    	bitmap=Pbitmap;                                            //~1312I~
        androidCanvas=null;	//immutable                            //~1312R~
    	graphics=new Graphics(this);                               //~1312I~
    	if (Dump.C) Dump.println("Bitmap from image file w="+bitmap.getWidth()+",h="+bitmap.getHeight());//~1506R~//~1A6AR~
    }                                                              //~1312I~
//*from recycleImage                                               //~1227I~
    private Image(int Pimagetype,int Pw,int Ph)                    //~1227I~
    {                                                              //~1227I~
    	width=Pw;                                                  //~1227I~
    	height=Ph;                                                 //~1227I~
        androidCanvas=null;	//immutable                            //~1227I~
        graphics=null;                                             //~1227I~
    }                                                              //~1227I~
//**********                                                       //~1227I~
//from Canvas<--Board for board                                    //~1227I~
    public static Image createImage(int Pw,int Ph,Canvas Pcanvas)  //~1227R~
    {                                                              //~1227I~
    	int width=Pw;                                                  //~1227I~
    	int height=Ph;                                                 //~1227I~
        Image image;                                               //~1308I~
    //******************:                                          //~1308I~
      image=new Image(BOARD,width,height);                         //~1524R~
        image.bitmap=createBitmap(null,width,height);//~1227I~     //~1524R~
        if (Dump.C) Dump.println("Board Bitmap created w="+Pw+",h="+Ph);//~1227I~//~1524R~//~1A6AR~
        image.androidCanvas=new android.graphics.Canvas(image.bitmap);//~1524R~
        image.androidCanvas.setBitmap(image.bitmap); //required?     //~1301I~//~1524R~
        image.graphics=new Graphics(image);                    //~1228R~//~1524R~
        if (Dump.Y) Dump.println("Image:CreateImage Board bitmap="+((Object)image.bitmap).toString()+",androidCanvas="+((Object)image.androidCanvas).toString());//~1228I~//~1524R~//~1Ag7R~
    	if (Dump.Y) Dump.println("Image:creatreImage Bitmap w="+image.bitmap.getWidth()+",h="+image.bitmap.getHeight()+",byte="+getByteCount(image.bitmap));//~1506R~//~1Ag7R~
        if (Dump.Y) Dump.println("Image:createImage Pcanvas"+((Object)image.bitmap).toString());//~1506R~//~1Ag7R~
        return image;                                              //~1227I~
    }                                                              //~1227I~
//for BigTimer                                                     //~1414I~
    public static Image createImage(int Pw,int Ph)                 //~1414I~
    {                                                              //~1414I~
    	int width=Pw;                                              //~1414I~
    	int height=Ph;                                             //~1414I~
        Image image;                                               //~1414I~
    //******************:                                          //~1414I~
		image=new Image(OTHER,Pw,Ph);                              //~1414I~
    	image.bitmap=createBitmap(null,width,height);              //~1421R~
        if (Dump.Y) Dump.println("BigTimer Bitmap created w="+Pw+",h="+Ph+",bytecount="+getByteCount(image.bitmap));//~1506R~//~1Ag7R~
        image.androidCanvas=new android.graphics.Canvas(image.bitmap);//~1414I~
        image.androidCanvas.setBitmap(image.bitmap); //required?   //~1414I~
        image.graphics=new Graphics(image);                        //~1414I~
        if (Dump.C) Dump.println("CreateImage BigTimer bitmap="+image.bitmap.toString()+",androidCanvas="+image.androidCanvas.toString());//~1506R~//~1A6AR~
        return image;                                              //~1414I~
    }                                                              //~1414I~
//from Canvas<--Board for stone or woodpaint                       //~1227R~
    public static Image createImage(MemoryImageSource Pmis,Canvas Pcanvas)	//for stone//~1227R~
    {                                                              //~1227I~
    	int width=Pmis.width;                                      //~1227R~
    	int height=Pmis.height;                                    //~1227R~
        Image image;                                               //~1308I~
    //**********                                                   //~1308I~
        if (width>AG.scrWidth/2)	//woodpaint                    //~1227I~
        	return createImage(Pmis,(Component)Pcanvas);	//for Woodpaint//~1227I~
    //*stone                                                       //~1227I~
        int [] bitmapsrc=Pmis.pixel;                               //~1227I~
        if (Dump.C) Dump.println("MemorySourceImage stone width="+width);//~1506R~//~1A6AR~
        image=new Image(STONE,width,height);                       //~1524R~
        image.bitmap=createBitmap(bitmapsrc,width,height);         //~1524R~
        if (Dump.Y) Dump.println("Stone Bitmap created w="+width+",h="+height+",byte="+getByteCount(image.bitmap));//~1227I~//~1524R~//~1Ag7R~
    	if (Dump.Y) Dump.println("Bitmap w="+image.bitmap.getWidth()+",h="+image.bitmap.getHeight());//~1227I~//~1506R~//~1Ag7R~
        if (Dump.Y) Dump.println("createImage@@@@ stone image="+((Object)image).toString()+",bitmap="+((Object)image.bitmap).toString());//~1228R~//~1506R~//~1Ag7R~
        if (Dump.Y) Dump.println("createImage@@@@ stone bitmap="+((Object)(image.bitmap)).toString()+",bitmap="+((Object)image.bitmap).toString());//~1506R~//~1Ag7R~
        return image;                                              //~1227I~
    }                                                              //~1227I~
//from Component<--woodpaint ***                                   //~1227I~
    public static Image createImage(MemoryImageSource Pmis,Component Pcomconent)	//for Woodpaint//~1227R~
    {
    	int width=Pmis.width;                                      //~1227R~
    	int height=Pmis.height;                                    //~1227R~
        int [] bitmapsrc=Pmis.pixel;                              //~1120I~
        Image image;                                               //~1308I~
    //************                                                 //~1308I~
    	if (Dump.Y) Dump.println("Woodpaint Bitmap w="+width+",height="+height);//~1506R~//~1Ag7R~
        if (Dump.Y) Dump.println("MemorySourceImage createwood width="+width);//~1506R~//~1Ag7R~
        image=new Image(WOOD,width,height);                        //~1524R~
        image.bitmap=createBitmap(bitmapsrc,width,height);         //~1524R~
        if (Dump.Y) Dump.println("Woodpaint created w="+width+",h="+height+",byte="+getByteCount(image.bitmap));//~1227I~//~1524R~//~1Ag7R~
    	if (Dump.Y) Dump.println("Bitmap w="+image.bitmap.getWidth()+",h="+image.bitmap.getHeight());//~1120I~//~1506R~//~1Ag7R~
        if (Dump.Y) Dump.println("createImage@@@@ Wood image ="+((Object)(image)).toString());//~1227I~//~1506R~//~1Ag7R~
        if (Dump.Y) Dump.println("createImage@@@@ Wood bitmap="+((Object)(image.bitmap)).toString());//~1506R~//~1Ag7R~
        return image;                                              //~1227I~
    }                                                              //~1120I~
//*****************                                                //~1227R~
    public Graphics getGraphics()  //from BigLabel,Lister,TextDisplay//~1224R~
    {                                                              //~1224R~
        return graphics;    //Ajagoc.Graphics;null if immutable    //~1224R~
    }                                                              //~1224R~
    public int getWidth(Object PimageObserver)                     //~1120R~
    {                                                              //~1120I~
    	return width;                                              //~1120I~
    }                                                              //~1120I~
    public int getHeight(Object PimageObserver)                             //~1214R~//~1215R~
    {                                                              //~1214I~
    	return height;                                             //~1214I~
    }                                                              //~1214I~
    //*******************************************************************//~1Ag9I~
    public void recycle()                                          //~1227I~
    {                                                              //~1227I~
        if (Dump.Y) Dump.println("Image:recycle "+this.toString());//~1506R~//~1A6AR~
        if (bitmap==null)                                          //~1422I~
        {                                                          //~1A6AI~
        	if (Dump.Y) Dump.println("Image:recycle bitmap=null"); //~1A6AI~
        	return;                                                //~1422I~
        }                                                          //~1A6AI~
        if (swNoRecycle)                                           //~1Ag7I~
        {                                                          //~1Ag7I~
        	if (Dump.Y) Dump.println("Image:recycle NoRecycle flag:true bitmap="+bitmap.toString());//~1Ag7R~
        	return;                                                //~1Ag7I~
        }                                                          //~1Ag7I~
        if (!bitmap.isRecycled())                                  //~1422I~
        {                                                          //~1422I~
         	if (Dump.Y) Dump.println("Image:recycle w="+bitmap.getWidth()+",h="+bitmap.getHeight()+",byte="+getByteCount(bitmap)+"="+bitmap.toString());//~v1E4I~//~1A6AR~
         	if (Dump.Y) Dump.println("Image:recycle bitmap="+bitmap.toString());//~1A6AR~
	    	bitmap.recycle();                                      //~1422R~
        }                                                          //~1422I~
        if (Dump.Y) Dump.println("Image:recycle bitmap ref cleared="+bitmap.toString());//~1Ag9I~
        bitmap=null;                                               //~1Ag9I~
		if (graphics!=null)                                        //~1Ag9I~
        {                                                          //~1Ag9I~
        	graphics.recycle();  //clear tgtbitmap                 //+1Ag9R~
        }                                                          //~1Ag9I~
    }                                                              //~1227I~
    private static Bitmap createBitmap(int [] Pbitmapsrc,int Pw,int Ph)//~1421R~
    {                                                              //~1421I~
    	Bitmap bm;                                                 //~1421I~
    	if (Pbitmapsrc==null)                                      //~1421I~
    		bm=android.graphics.Bitmap.createBitmap(Pw,Ph,Bitmap.Config.ARGB_8888);//~1421I~
        else                                                       //~1421I~
    		bm=android.graphics.Bitmap.createBitmap(Pbitmapsrc,Pw,Ph,Bitmap.Config.ARGB_8888);//~1421I~
        if (Dump.Y) Dump.println("Image:createBitmap w="+Pw+",h="+Ph+"byte="+getByteCount(bm)+"="+bm.toString());//~v1E4I~//~1Ag7R~
        return bm;
    }                                                              //~1421I~
    //*******************************************                  //~v1E4I~
    static public int getByteCount(Bitmap Pbm)                     //~v1E4I~
    {                                                              //~v1E4I~
    	if (Pbm==null)                                             //~v1E4I~
        	return 0;                                              //~v1E4I~
        if (AG.osVersion>=AG.HONEYCOMB_MR1)  //android3.1          //~v1E4I~
            return getByteCount_V12(Pbm);                          //~v1E4I~
        return 0;                                                  //~v1E4I~
    }                                                              //~v1E4I~
	@TargetApi(AG.HONEYCOMB_MR1)                                   //~v1E4I~
    static private int getByteCount_V12(Bitmap Pbm)                //~v1E4I~
	{                                                              //~v1E4I~
        return Pbm.getByteCount();                                 //~v1E4I~
    }                                                              //~v1E4I~
    public void setNoRecycle(boolean Pnorecycle)                   //~1Ag7I~
	{                                                              //~1Ag7I~
        swNoRecycle=Pnorecycle;                                    //~1Ag7I~
    }                                                              //~1Ag7I~
    //*******************************************************************//~1Ag9I~
    //*from Board for Empty,EmptyShadow,ActiveImage                //~1Ag9I~
    //*******************************************************************//~1Ag9I~
    public void recycle(boolean PclearCanvas)                      //~1Ag9I~
    {                                                              //~1Ag9I~
		if (graphics!=null)                                        //~1Ag9I~
        {                                                          //~1Ag9I~
        	graphics.recycle(PclearCanvas);                        //~1Ag9I~
        	graphics=null;                                         //~1Ag9I~
        }                                                          //~1Ag9I~
        recycle();                                                 //~1Ag9I~
        if (PclearCanvas)                                          //~1Ag9I~
        {                                                          //~1Ag9I~
		    if (Dump.Y) Dump.println("Image:recycle bitmap+clearCanvas androidCanvas="+androidCanvas.toString());//~1Ag9I~
        	androidCanvas=null;                                    //~1Ag9I~
        }                                                          //~1Ag9I~
    }                                                              //~1Ag9I~
}//class                                                           //~1112I~
