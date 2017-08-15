//*CID://+v1EcR~: update#= 157;                                    //+v1EcR~
//**********************************************************************//~1107I~
//v1Ec 2014/12/11 (Asgts)//1A4a 2014/11/29 FileDialog:open when selected item is clicked//+v1EcI~
//**********************************************************************//+v1EcI~
//*My ListView Adapter                                                     //~1107I~//~1109R~
//**********************************************************************//~1107I~
package com.Ajagoc.awt;                                         //~1107R~  //~1108R~//~1109R~//~1114R~

import com.Ajagoc.AG;
                                                                   //~1109I~
public class Lister extends Component                                                  //~1114R~//~1220R~
{                                                                  //~1110I~
                                                                   //~1220I~
	private List list;                                             //~1425R~
	private boolean swSetText;                                     //~1425R~
//*****************                                                //~1112I~
    public Lister()                                               //~1112I~//~1114R~//~1220R~
    {                                                              //~1112I~
    	list=new List(AG.viewId_Lister);                           //~1220I~
    }                                                              //~1112I~
//*****************                                                //+v1EcI~
    public Lister(Container Pcontainer)                            //+v1EcI~
    {                                                              //+v1EcI~
    	list=new List(Pcontainer,AG.viewId_Lister);                //+v1EcI~
    }                                                              //+v1EcI~
//*****************                                                //~1220M~
    public void doUpdate(boolean Pshowlast)                        //~1220R~//~1221R~
    {                                         
		if (Pshowlast)                                             //~1221I~
			list.showBottom();                                     //~1221I~
        else                                                       //~1221I~
			list.showList();                                       //~1221R~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void setText(String Ptext)                              //~1220R~
    {                                                              //~1220I~
        list.setText(Ptext);                                       //~1221I~
        swSetText=true;                                            //~1224I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void setFont(Font Pfont)                                //~1220R~
    {                                                              //~1220I~
        list.setFont(Pfont);                                       //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public int getSelectedPos()                                    //~1403I~
    {                                                              //~1403I~
    	return list.getSelectedPos();                                 //~1403I~
    }                                                              //~1403I~
    public String getSelectedItem()                                //~1220I~
    {                                                              //~1220I~
        return list.getSelectedItem();                             //~1220I~
    }                                                              //~1220I~
    public void  setSelection(int Ppos)                            //~1411I~
    {                                                              //~1411I~
        list.select(Ppos);                                  //~1411I~
    }                                                              //~1411I~
//*****************                                                //~1220I~
    public void appendLine(String Pline)                           //~1403I~
    {                                                              //~1403I~
		appendLine(Pline,Color.black);                       //~1403I~
    }                                                              //~1403I~
    public void appendLine(String Pline,Color Pcolor)              //~1220I~
    {                                                              //~1220I~
		if (swSetText)                                             //~1224I~
        {	                                                       //~1224I~
        	swSetText=false;                                       //~1224I~
            list.removeAll();                                      //~1224R~
        }                                                          //~1224I~
        list.add(Pline,Pcolor);                                    //~1221I~
    }                                                              //~1220I~
    public void clearList()                                        //~1403I~
    {                                                              //~1403I~
        list.removeAll();                                          //~1403I~
    }                                                              //~1403I~
//*****************                                                //~1403I~
    public void setBackground(Color Pcolor)                        //~1403I~
    {                                                              //~1403I~
    	list.setBackground(Pcolor);                                //~1403I~
    }                                                              //~1403I~
}//class                                                           //~1109I~
