//*CID://+v1EjR~:                             update#=    2;       //+v1EjR~
//**************************************************************************//~1B0bI~
//v1Ej 2014/12/13 (Asgts)//1A4z 2014/12/09 FileDialog:view file by click twice//+v1EjI~
//1B0b 130429 Ajagoc not supported match type                      //~1B0bI~
//**************************************************************************//~1B0bI~
package com.Ajagoc.awt;                                            //~1112I~

import com.Ajagoc.AG;

import jagoclient.gui.ChoiceAction;

//**drop down list ***                                                                  //~1112I~//~1219R~
public class Choice extends Component                               //~1112R~//~1216R~//~1219R~
{                                                                  //~1112I~
	private Spinner spinner;                                       //~1219I~
//*******************************                                  //~1216I~
    public Choice()                                                 //~1112R~//~1219R~
    {                                                              //~1112I~
    	spinner=new Spinner();                                     //~1219I~
    }                                                              //~1112I~
//***************                                                  //+v1EjI~
    public Choice(Container Pcontainer,int Presid)                 //+v1EjI~
    {                                                              //+v1EjI~
    	spinner=new Spinner(Pcontainer,Presid);                    //+v1EjI~
    }                                                              //+v1EjI~
//***************                                                  //~1220I~
	public void add(String Pentry)                                 //~1220I~
    {                                                              //~1220I~
    	spinner.add(Pentry);                                       //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
	public String getSelectedItem()                                //~1220I~
    {                                                              //~1220I~
    	return spinner.getSelectedItem();                                 //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
	public int getItemCount()                                      //~1220I~
    {                                                              //~1220I~
    	return spinner.getItemCount();                                    //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
	public String getItem(int Ppos)                                //~1220I~
    {                                                              //~1220I~
    	return spinner.getItem(Ppos);                                     //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
    public void setFont(Font Pfont)                                //~1219I~
    {                                                              //~1219I~
    	spinner.setFont(Pfont);                                    //~1220I~
    }                                                              //~1219I~
//***************                                                  //~1220I~
    public void addItemListener(ChoiceAction.ChoiceTranslator Pct)              //~1220I~
    {                                                              //~1220I~
    	spinner.addItemListener(Pct);                              //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1306I~
    public void select(int Pindex)                                 //~1306I~
    {                                                              //~1306I~
    	spinner.setSelection(Pindex);                   //~1306R~
    }                                                              //~1306I~
//***************                                                  //~1314I~
    public void select(String Psearchstring)                       //~1314I~
    {                                                              //+1314I~                             //~1314I~
    	spinner.setSelection(Psearchstring);                       //~1314I~
    }                                                              //~1314I~
//***************                                                  //~1306I~
    public int getSelectedIndex()                                  //~1306R~
    {                                                              //~1306I~
    	return spinner.getSelectedItemPosition();                  //~1306R~
    }                                                              //~1306I~
//*****************************************************************************//~1B0bI~
    public void setArray(int Presid)                                //~1B0bI~
    {                                                              //~1B0bI~
	    String[] sa=AG.resource.getStringArray(Presid);  //~1314I~//~1326R~//~1B0bI~
        for (int ii=0;ii<sa.length;ii++)                            //~1326R~//~1B0bI~
		{                                                          //~1314I~//~1B0bI~
        	add(sa[ii]);                                      //~1314I~//~1B0bI~
        }                                                          //~1314I~//~1B0bI~
    }                                                              //~1B0bI~
}//class                                                           //~1112I~
