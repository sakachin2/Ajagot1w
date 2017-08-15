package com.Ajagoc.awt;                                                //~1108R~//~1109R~

public class PixelGrabber                                          //~1404R~
{                                                                  //~1215I~
	Image image;int x,y,w,h,offs,stride;                           //~1404I~
	int[] pixels;                                                  //~1404I~
//**********************                                           //~1128I~
	public PixelGrabber(Image Pimage,int Px,int Py,int Pw,int Ph,int[] Ppixels,int Poffs,int Pstride)//~1404R~
    {                                                              //~1124I~
		image=Pimage;x=Px;y=Py;w=Pw;h=Ph;pixels=Ppixels;offs=Poffs;stride=Pstride;//~1404I~
    }
	public void grabPixels() throws InterruptedException            //~1404R~
    {                                                              //~1404R~
    	image.bitmap.getPixels(pixels,offs,stride,x,y,w,h);        //+1404R~
    }                                                              //~1215I~
}
