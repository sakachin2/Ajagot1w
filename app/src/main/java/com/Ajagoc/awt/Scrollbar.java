package com.Ajagoc.awt;                                            //~1112I~

import com.Ajagoc.AG;
import com.Ajagoc.AjagoKey;

import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.LinearLayout;

//************************************                             //~1331I~
//*support ColorEdit                 *                             //~1331I~
//************************************                             //~1331I~

public class Scrollbar extends Component                           //~1331R~
	implements OnSeekBarChangeListener, KeyListener                //~1401R~
{                                                                  //~1112I~
	public static final int HORIZONTAL   =1;                       //~1213I~
	public static final int VERTICAL     =2;                       //~1213I~
                                                                   //~1331I~
    private EditText tvColor=null;                                 //~1401R~
    private AdjustmentListener al;                                 //~1401I~
    private SeekBar seekbar=null;                                  //~1331R~
    private int orientation;                                       //~1331I~
                                                                   //~1213I~
    public Scrollbar(int Porientation,int PinitValue,int Pthumbsize,int Pmin,int Pmax)        //~1213I~//~1331R~
    {                                                              //~1213I~
    	orientation=Porientation;                                  //~1331I~
    	seekbar=(SeekBar)AG.findSeekBarView();                              //~1331I~
    	seekbar.setProgress(PinitValue);                           //~1331I~
    	seekbar.setOnSeekBarChangeListener(this); //request callback this//~1331R~
    }                                                              //~1213I~
    public void addAdjustmentListener(AdjustmentListener Pal)        //~1213R~//~1331R~
    {                                                              //~1213I~
        int id,idtext;                                                    //~1401I~
    //********************                                         //~1401I~
    	al=Pal;                                                    //~1331I~
        id=seekbar.getId();                                        //~1401I~
        if (id==AG.viewId_SeekBarRed)                              //~1401I~
        	idtext=AG.viewId_ContainerRed;                         //~1401I~
        else                                                       //~1401I~
        if (id==AG.viewId_SeekBarGreen)                            //~1401I~
        	idtext=AG.viewId_ContainerGreen;                       //~1401I~
        else                                                       //~1401I~
        if (id==AG.viewId_SeekBarBlue)                             //~1401I~
        	idtext=AG.viewId_ContainerBlue;                        //~1401I~
        else                                                       //~1401I~
            idtext=0;                                              //~1401I~
        if (idtext!=0)                                             //~1401I~
        {                                                          //~1401I~
            tvColor=(EditText)((LinearLayout)AG.findViewById(idtext)).getChildAt(0);//~1401R~
            AjagoKey kl=AjagoKey.addKeyListener(tvColor);                    //~1401I~
            kl.setKeyListener(this);                               //~1401I~
        }                                                          //~1401I~
    }                                                              //~1213I~
    public int getValue()                                          //~1213I~
    {                                                              //~1213I~
    	return seekbar.getProgress();                              //~1331R~
    }                                                              //~1213I~
    public void setValue(int Pvalue)                                   //~1213I~
    {                                                              //~1213I~
    	seekbar.setProgress(Pvalue);                        //~1331I~
    }                                                              //~1213I~
//* for compile rene.viewer.Viewer                                 //~1331I~
    public void setValues(int Pvalue,int Pthumbsize,int Pmin,int Pmax)             //~1213I~//~1331R~
    {                                                              //~1213I~//~1331R~
    	seekbar.setProgress(Pvalue);                               //~1331I~
    	seekbar.setMax(Pmax);                                      //~1331I~
    }                                                              //~1213I~//~1331R~
//****************************                                     //~1331I~
    @Override                                                      //~1331I~
    public void onProgressChanged(SeekBar PseekBar,int Pvalue,boolean PfromUser)//~1331I~
    {                                                              //~1331I~
    	AdjustmentEvent ev=new AdjustmentEvent(this);              //~1331R~
    	al.adjustmentValueChanged(ev);                                 //~1331I~
    }                                                              //~1331I~
    @Override                                                      //~1331I~
    public void onStartTrackingTouch(SeekBar PseekBar)                         //~1331I~
    {                                                              //~1331I~
    }                                                              //~1331I~
    @Override                                                      //~1331I~
    public void onStopTrackingTouch(SeekBar PseekBar)                          //~1331I~
    {                                                              //~1331I~
    }                                                              //~1331I~
//****************************                                     //~1401I~
    @Override                                                      //~1401I~
    public void keyPressed(KeyEvent ev){}                                 //~1401I~
    @Override                                                      //~1401I~
    public void keyReleased(KeyEvent ev)                                  //~1401I~
	{                                                              //~1401I~
    	if (tvColor==null)                                         //~1401I~
        	return;                                                //~1401I~
        String text=tvColor.getText().toString();                  //~1401R~
        int value;                                                 //~1401I~
		try                                                        //~1401I~
		{	                                                       //~1401I~
        	value=Integer.parseInt(text);                          //~1401I~
		}                                                          //~1401I~
		catch (NumberFormatException e)                            //~1401I~
		{                                                          //~1401I~
        	value=-1;                                              //~1401I~
		}                                                          //~1401I~
        if (value<0||value>255)                                        //~1401I~
        	return;                                                //~1401I~
        setValue(value);                                           //~1401I~
    	onProgressChanged(seekbar,value,false);                    //~1401I~
	}                                                              //~1401I~
    @Override                                                      //~1401I~
    public void keyTyped(KeyEvent ev){}                                   //~1401I~
}//class                                                           //~1112I~
