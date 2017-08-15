package com.Ajagoc.awt;                                                //~1108R~//~1109R~

import jagoclient.Dump;
import jagoclient.gui.DoActionListener;

import java.util.ArrayList;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoAlert;
import com.Ajagoc.AjagoAlertI;
import android.widget.ArrayAdapter;
import android.widget.ListView;
                                                                   //~1128I~
//*********************************************************          //~1427I~
//*For HistoryTextField, Lister(GamesFrame,WhosFrame)                //~1427I~
//*********************************************************          //~1427I~
public class PopupMenu extends Panel                          //~1128R~//~1129R~//~1213R~
	implements AjagoAlertI
{                                                                  //~1128I~
    DoActionListener doactionlistener;                      //~1128I~//~1427R~
    ArrayList<String> items=new ArrayList<String>();         //~1128R~//~1129R~
    ArrayAdapter<String> adapter;                              //~1128R~//~1129R~
    ListView listview;                                           //~1128R~//~1129R~
    AjagoAlert dialog;                                          //~1128R~//~1427R~
    int selecteditemno=0;                                        //~1128R~//~1129R~
    public int listerFrameId;   //ref from Lister                  //~1427R~
    public MenuBar menuBar;                                        //~1306I~//~1427R~
    public Menu    menu;                                           //~1427R~
    public PopupMenu()                                      //~1128R~//~1129R~
    {                                                            //~1128R~//~1129R~
//** for Lister popup                                              //~1307I~
        int frameid=0;                                             //~1306I~
        if (AG.currentFrame!=null)                                 //~1306I~
    		frameid=AG.currentFrame.framelayoutresourceid;         //~1306I~
        if (frameid==AG.frameId_WhoFrame||frameid==AG.frameId_GamesFrame)//~1306I~
        {                                                          //~1306I~
	        if (Dump.Y) Dump.println("PopupMenu listerframeid="+Integer.toString(listerFrameId,16));//~1511R~
			listerFrameId=frameid;	//use MenuBar processing       //~1306I~
        	listview=(ListView)AG.findViewById(AG.viewId_Lister);	//listview on WhoFrame//~1307I~
        	menuBar=new MenuBar(listview);                                //~1306I~//~1307R~
        	menu=new Menu("Selected Item");                        //~1307R~
        	menuBar.add(menu);                                     //~1307I~
            return;                                                //~1306I~
        }                                                          //~1306I~
        adapter=new ArrayAdapter<String>(AG.context,AG.listViewRowId,items);//~1128R~//~1129R~
        listview=new ListView(AG.context);                       //~1128R~//~1129R~
        if (Dump.Y) Dump.println("new Listview id="+Integer.toString(listview.getId()));//~1511R~
        listview.setAdapter(adapter);                            //~1128R~//~1129R~
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);     //~1128R~//~1129R~
        listview.setFocusableInTouchMode(true);                  //~1128R~//~1129R~
                                                                 //~1128R~//~1129R~
    }                                                            //~1128R~//~1129R~
    public void add(MenuItem Pitem)                              //~1128R~//~1129R~
    {                                                              //~1111I~//~1128R~//~1129R~
        if (Dump.Y) Dump.println("PopupMenu Menuitem add "+Pitem.name+",listerframeid="+Integer.toString(listerFrameId,16));            //~1306M~//~1511R~
//** for Lister popup                                              //~1307I~
        if (listerFrameId!=0)                                      //~1306I~
        	menu.add(Pitem);                                    //~1306I~//~1307R~
        else                                                       //~1306I~
        	adapter.add(Pitem.name);                                        //~1128R~//~1129R~//~1306R~
    }                                                            //~1128R~//~1129R~
//***********************                                          //~1307I~
//*from Lister                                                     //~1307I~
//***********************                                          //~1307I~
    public void setPopupMenu()                                     //~1307I~
    {                                                              //~1307I~
        if (menuBar==null)                                         //~1307I~
        	return;                                                //~1307I~
        AG.ajagoMenu.registerMenuBar(menuBar);                     //~1307I~
    }                                                              //~1307I~
//***********************                                          //~1307I~
//*from HistoryTextField                                           //~1427I~
//***********************                                          //~1427I~
    public void show(DoActionListener Pdal,int Px,int Py)          //~1128R~//~1129R~
    {                                                            //~1128R~//~1129R~
    	if (dialog==null)                                          //~1129I~
        {                                                          //~1129I~
            doactionlistener=Pdal;                                 //~1427I~
			dialog=new AjagoAlert(this);                           //~1427I~
			int flag=AjagoAlert.BUTTON_NEGATIVE;                   //~1427R~
			dialog.createAltertDialog("History List",flag,items);  //~1427R~
        }                                                          //~1129I~
        listview.setSelection(selecteditemno>=0?selecteditemno:0);                                //~1128R~//~1129I~
        dialog.show();                      //~1128R~//~1129R~
    }                                                            //~1128R~//~1129R~
//**********************************************************************//~1128R~//~1129R~
                  //~1128R~//~1129R~
    public void itemAction(String o,boolean flag)                //~1128R~//~1129R~
    {                                                            //~1128R~//~1129R~
    }                                                            //~1128R~//~1129R~
                                                                   //~1129I~
//**********************************************************************//~1128I~//~1129R~
    public void callbackDoAction(int Ppos)                         //~1128I~//~1129R~
    {                                                              //~1128I~//~1129R~
    	if (Ppos<0 || Ppos>=adapter.getCount())               //~1129I~
        	return;                                                //~1129I~
        String itemname=adapter.getItem(Ppos);                     //~1128I~//~1129R~
        if (Dump.Y) Dump.println("callback Doaction name="+itemname);//~1511R~
        doactionlistener.doAction(itemname);                   //~1128I~//~1129R~
    }                                                              //~1128I~//~1129R~
                                                      //~1128I~    //~1129R~
    public void addSeparator()                                     //~1213R~
    {                                                              //~1213I~
    }                                                              //~1213I~
//**********************************************************************//~1427I~
    @Override                                                      //~1427I~
	public int alertButtonAction(int Pbuttonid,int Pitempos)       //~1427I~
    {                                                              //~1427I~
    	int rc=0;                                                   //~1427I~
    	if (Dump.Y) Dump.println("PopupMenu id="+Pbuttonid+",pos="+Pitempos);//~1506R~
        if (Pbuttonid==AjagoAlert.ITEM_SELECTED)                              //~1427I~
        {                                                          //~1427I~
			callbackDoAction(Pitempos);                            //~1427I~
        	rc=1; 	//dismiss                                      //~1427I~
        }                                                          //~1427I~
        return rc;                                                 //~1427I~
    }                                                              //~1427I~
}                                                                  //~1128I~
