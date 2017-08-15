//*CID://+v1EcR~:                             update#=    2;       //+v1EcR~
//***************************************************************************//~v1E9I~
//v1Ec 2014/12/11 (Asgts)//1A4a 2014/11/29 FileDialog:open when selected item is clicked//+v1EcI~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//***************************************************************************//~v1E9I~
package com.Ajagoc.awt;                                            //~1112I~

import android.view.View;
import jagoclient.Dump;

                                                             //~1112I~
public class Container extends Component                           //~1116R~
{                                                                  //~1112I~
	public Color backgroundColor=null;                                     //~1216I~
    public View containerLayoutView;                               //~v1E9I~
    public ListI listInterface;                                    //+v1EcI~
    public Container()                                             //~1118R~
    {                                                              //~1118I~
    	super();                                                   //~1118R~
    }                                                              //~1118I~
    public void doLayout()                                              //~1216I~
    {                                                              //~1216I~
    }                                                              //~1216I~
//****************                                                 //~v1E9I~
    public void setContainerLayoutView(View Pview)                 //~v1E9I~
    {                                                              //~v1E9I~
        if(Dump.Y) Dump.println("Container setContainerLayoutView v="+Pview.toString());//~v1E9I~
		containerLayoutView=Pview;                                 //~v1E9I~
    }                                                              //~v1E9I~
//****************                                                 //~v1E9I~
	public View findViewById(int Presid)                           //~v1E9I~
    {                                                              //~v1E9I~
        View v=containerLayoutView.findViewById(Presid);           //~v1E9I~
        if(Dump.Y) Dump.println("Container findViewById layoutview="+containerLayoutView.toString()+",res="+Integer.toHexString(Presid)+",v="+(v==null?"null":v.toString()));//~v1E9I~
        return v;                                                  //~v1E9I~
    }                                                              //~v1E9I~
}//class                                                           //~1112I~
