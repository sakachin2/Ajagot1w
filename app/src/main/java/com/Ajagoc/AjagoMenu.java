//*CID://+1Ag3R~: update#= 269;                                    //~1Ag3R~
//**********************************************************************//~1107I~
//1Ag3 2016/10/08 set icon to menu                                 //~1Ag3I~
//1Ag1 2016/10/06 Change Top panel. set menu panel as tabwidget.   //~1Ag1I~
//1Ag0 2014/10/05 displaying bot version number on menu delays until next restart(menu itemname is set at start and not changed)//~1Ag0I~
//1Afd 2016/09/24 change menu structure, move "vs computer" to top level//~1AfdI~
//1A8s 2015/04/10 actionbar layout for mdpi                        //~1A8sI~
//v1D1 2014/10/03 actionBAr as alternative of menu button for api>=11//~v1D1I~
//                When requestWindowFeature(FEATURE_LEFT_ICON). onCreateOptionsMenu is not called(No ActionBar on android>=3.0)//~v1D1I~
//v1C9 2014/09/03 change AjagocMenu to Dialog                      //~v1C9I~
//1B32 2013/07/01 one touch mode also on board menue               //~1B32I~
//1B1g 130511 Button on Help Frame for modified Help text for Ajagoc//~1B1gI~
//1B0e 130429 option of one touch mode for local and connected board//~1B0eI~
//1091:121124 Menubar list OutOfBoundsException                    //~v109I~
//1078:121208 add "menu" to option menu if high resolusion         //~v107I~
//1076:121208 drop debugtrace menu item if release version         //~v107I~
//V104:121109 onContextMenuItemClosed sheduled before submenu display,NPE abend//~v104I~
//**********************************************************************//~v104I~
//*Menu                                                            //~v104R~
//**********************************************************************//~1107I~
package com.Ajagoc;                                         //~1107R~  //~1108R~//~1109R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.MainFrame;
import jagoclient.gui.CheckboxMenuItemAction;
import jagoclient.gui.DoActionListener;
import jagoclient.gui.MenuItemAction;
import jagoclient.gui.MyMenu;
//import jagoclient.board.ConnectedGoFrame;                        //~v1C9R~
//import jagoclient.board.GoFrame;                                 //~v1C9R~
import jagoclient.dialogs.Help;

import java.util.ArrayList;



import com.Ajagoc.awt.ActionEvent;
//import com.Ajagoc.awt.Canvas;                                    //~v1C9R~
import com.Ajagoc.awt.CheckboxMenuItem;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.ItemEvent;
import com.Ajagoc.awt.MenuBar;
import com.Ajagoc.awt.Window;
import com.Ajagoc.AjagoAlert;
import com.Ajagoc.AG;



import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TabHost;
import android.widget.TextView;
//**********************************************************************//~1107I~
public class AjagoMenu                                             //~1310R~
		 implements AjagoAlertI,  DoActionListener                 //~1507R~
{                                                                  //~0914I~
	static private ArrayList<MenuBar> menubarlist;                 //~1425R~
	public View menuRegisteredView;                                //~1425R~
	private boolean swPopupSubmenu;                                //~1425R~
	private boolean swShowRequest;                                 //~1427I~
    private boolean listerPopup;                                   //~1425R~
    private MenuBar currentMenuBar;                                //~1425R~
                                                                   //~1314I~
    private             String 	topSubmenuname="";                 //~1Ag1I~
                                                                   //~1Ag1I~
	private static String []menuDesc;                              //~1425R~
    public static final int  MENU_STOP=0;                         //~1314R~//~1326R~
    public static final int  MENU_CLOSE=1;                         //~1326I~
    public static final int  MENU_HELP=2;                          //~1314I~//~1412R~
//    public static final int  MENU_CTR=3;                           //~1314I~//~1412R~//~v107R~
    public static final int  MENU_MENU=3;                          //~v107I~
    public static final int  MENU_CTR=4;                           //~v107I~
    public static final int  MENUMENU_SIZE=700;                    //~v107R~
                                                                   //~1411I~
//    private static final String HELPTEXT_AJAGOC="aboutAjagoc";   //~1B1gR~
    private static final String HELPTEXT_AJAGOC="Ajagoc";          //~1B1gI~
//  private static String HELPITEM_AJAGOC="About Android Version";//~1411I~//~v107R~
    private static String HELPITEM_AJAGOC="Ajagoc?";               //~v107I~
                                                                   //~1328I~
    public static final int  POPUPSUBMENUID=0x80;                  //~1328I~
    public static final int  AJAGOHELPMENUID=0x7f00;               //~1411I~
	private static final int menuId[]={                                  //~1314I~//~v107R~
				MENU_STOP,                                        //~1314R~//~1326R~
				MENU_CLOSE,                                        //~1326I~
				MENU_HELP,                                         //~1314I~
				MENU_MENU,                                         //~v107I~
				MENU_CTR};                                         //~1314I~
	private static final int icons[]={                                   //~1314I~//~v107R~
								android.R.drawable.ic_delete,      //~1412R~
								android.R.drawable.ic_menu_close_clear_cancel,//~1404I~
								android.R.drawable.ic_menu_help,   //~1314I~
								R.drawable.ic_menu_moreoverflow,   //~v107R~
								0							};     //~1314I~
    public String optionsMenuitem;                                 //~v1C9I~
//******************                                               //~1121I~
	public AjagoMenu()                                             //~1107R~
    {                                                              //~1107I~
    	menubarlist=new ArrayList<MenuBar>();                      //~1121I~
		optionsMenuitem=AG.appName+" "+Global.resourceString("Options");//~v1C9I~
    }                                                              //~1107I~
//*****************************                                               //~1121I~//~1122R~
//*from component:setMenu()-->menubar-->                           //~1524R~
//*****************************                                    //~1122I~
    public void registerMenuBar(MenuBar Pmenubar)                  //~1123I~
    {                                                              //~1123I~
        View view;                                                 //~1307I~
    //*********                                                    //~1307I~
    	Frame frame=Pmenubar.frame;                                //~1125I~
        if (Pmenubar.childView!=null)   //from Lister for Who/Games//~1411R~
        	view=Pmenubar.childView;                               //~1307R~
        else                                                       //~1307I~
        {                                                          //~1331I~
        	view=frame.framelayoutview;                                 //~1125I~//~1307R~
        	frame.contextMenuView=view;	//used by showContextMenu from optionmenu                                //~1314I~//~1331R~
        }                                                          //~1331I~
        Pmenubar.relatedView=view;                                 //~1331I~
        Pmenubar.seqno=menubarlist.size();                          //~1123I~
        Pmenubar.frameid=view.getId();                             //~1125I~
        AG.activity.registerForContextMenu(view); //request callback onCreateContextMenu()//~1123I~//~1125R~
        menubarlist.add(Pmenubar);                                 //~1123I~
        if (Dump.Y) Dump.println("Ajagomenu:add menubar newctr="+menubarlist.size()+":"+((Object)Pmenubar).toString());//~1506R~
        if (Dump.Y) Dump.println("Ajagomenu:register contextmenu frame="+frame.framename+",frameresourceid="+Integer.toHexString(frame.framelayoutresourceid)+//~1125I~//~1506R~
        						",registeredView id="+Integer.toString(view.getId())+//~1306R~
                                "menubar.frameid="+Integer.toHexString(Pmenubar.frameid));//~1306I~//~1402R~
        if (Dump.Y) Dump.println("Ajagomenu:register contextmenu view="+view.toString());//~1506R~
    }                                                              //~1123I~
//*****************************                                    //~1402I~
    public void removeMenuBar(MenuBar PmenuBar)                    //~1402I~
    {                                                              //~1402I~
    	int idx;                                                   //~v109I~
    //*********                                                    //~1402I~
        if (Dump.Y) Dump.println("Ajagomenu:remove menubar oldctr="+menubarlist.size()+":"+((Object)PmenuBar).toString());//~1506R~
//  	menubarlist.remove(PmenuBar);                              //~1402I~//~v109R~
		idx=PmenuBar.seqno;                                        //~v109I~
    	menubarlist.set(idx,null);                                 //~v109I~
        if (Dump.Y) Dump.println("Ajagomenu:remove menubar newctr="+menubarlist.size());//~1506R~
    }                                                              //~1402I~
//******************                                               //~1121I~
	private MenuBar findMenuBar(View Pview)                      //~1121I~
    {                                                              //~1121I~
    	MenuBar menubar,menubarfound;                              //~1411R~
        if (Pview instanceof TabHost)   //for Mainframe            //~1123I~
            return menubarlist.get(0);                             //~1123I~
        int viewid=Pview.getId();                                  //~1124I~
        menubarfound=Window.getCurrentFrame().framemenubar;        //~1411R~
        for (int ii=0;ii<menubarlist.size();ii++)                      //~1121I~
        {                                                          //~1121I~
        	menubar=menubarlist.get(ii);                           //~1121I~
            if (menubar==null)                                     //~v109I~
            	continue;                                          //~v109I~
	        if (Dump.Y) Dump.println("AjagoMenu:findView viewId="+Integer.toHexString(viewid)+":menu frameid="+Integer.toHexString(menubar.frameid));//~1124I~//~1125R~//~1506R~
            if (menubar.childView!=null)                           //~1411I~
            {                                                      //~1411I~
                if (menubar.childView==Pview)   //id is both Lister for Who and Games frame//~1411I~
                {                                                  //~1411I~
                    return menubar;                                //~1411I~
                }                                                  //~1411I~
            }                                                      //~1411I~
        }                                                          //~1121I~
        return menubarfound;                                       //~1411R~
    }                                                              //~1121I~
//******************                                               //~1124I~
	private MenuBar findMenuBar(int Pitemid)                       //~1124I~
    {                                                              //~1124I~
        int menubarid;                                             //~1124I~
    //*********************                                        //~1124I~
    	menubarid=Pitemid>>24;                                     //~1124I~
        if (menubarlist.size()<menubarid)                          //~v109R~
        	return null;                                           //~v109I~
        return menubarlist.get(menubarid-1);                       //~1124I~
    }                                                              //~1124I~
//*************************************************                //~1122R~
//*menu created at requested by long pressing                   //~1122I~//~v107R~
//*************************************************                //~1122I~
    public void onCreateContextMenu(ContextMenu Pmenu,View Pview,ContextMenuInfo Pinfo)//~1121I~
    {                                                                  //~1107I~//~1121R~
    	boolean swshow;                                            //~1427I~
    //*************                                                //~1411I~
    	swPopupSubmenu=false;                                      //~1328I~
        swshow=swShowRequest;                                      //~1427I~
        swShowRequest=false;                                       //~1427I~
    	MenuBar menubar=findMenuBar(Pview);                      //~1121I~
        if (menubar==null)                                         //~1121I~
        	return;                                                //~1121I~
		if (Dump.Y) Dump.println("AjagoMenu:onCreateContextMenu View="+((Object)Pview).toString()+",layoutid="+Integer.toHexString(menubar.frame.framelayoutresourceid));                                       //~1306I~//~1506R~
        if (menubar.popupSubmenu!=null)	//show submenu             //~1328I~
        {	                                                       //~1328I~
            com.Ajagoc.awt.Menu submenu=menubar.popupSubmenu;      //~1328I~
            menubar.popupSubmenu=null;	//accepted                 //~1328I~
            createPopupSubmenu(Pmenu,menubar,submenu);             //~1328I~
        }                                                          //~1328I~
        else                                                       //~1328I~
        if (menubar.childView!=null)                               //~1307R~
        	createListerPopupMenu(Pmenu,menubar,Pinfo);             //~1306I~//~1307R~
        else                                                       //~1306I~
        {                                                          //~1307I~
        	if (listerPopup)  //when duplicaed register for child View and Layout//~1307I~//~1331R~
				return;                                            //~1307I~
            if (!swshow)                                           //~1427R~
            {                                                      //~1427I~
				Frame f=menubar.frame;                             //~1427I~
                if (f!=null && f.isBoardFrame)                     //~1427I~
                {                                                  //~1427I~
                	if (Dump.Y) Dump.println("LongPress ContextMenu on Board frame");//~1506R~
                	return;	//ignore on board frame                //~1427I~
                }                                                  //~1427I~
            }                                                      //~1427I~
          if (!topSubmenuname.equals(""))                          //~1Ag1I~
        	createMenuTopSubmenu(Pmenu,menubar);                   //~1Ag1I~
          else                                                     //~1Ag1I~
        	createMenu(Pmenu,menubar);                                 //~1123I~//~1306R~
        }                                                          //~1307I~
        currentMenuBar=menubar;                                    //~1307I~
    }                                                                  //~1107I~//~1121R~
//****************************************************             //~1123I~
    public boolean onContextItemSelected(MenuItem Pitem)           //~1121R~
    {                                                              //~1121I~
    	boolean rc=true;                                           //~1121I~
		Object awtitem;
        MenuBar menubar;                                           //~1307I~
		//************************                                     //~1124I~
    	int itemid=Pitem.getItemId();
    	if (Dump.Y) Dump.println("AjagoMenu:onContextItemSelected menubar itemid="+Integer.toHexString(itemid));//~1121R~     //~1123R~//~1124I~//~1Ag1R~
		menubar=findMenuBar(itemid);                                //~1307I~
        if (menubar==null)	//internal logic err                   //~v109I~
        	return true;                                           //~v109I~
        if ((itemid & AJAGOHELPMENUID)==AJAGOHELPMENUID)           //~1411I~
        {                                                          //~1411I~
        	helpAjagoc();                                          //~1411I~
            return true;                                           //~1411I~
        }                                                          //~1411I~
        awtitem=findItem(menubar,itemid);                          //~1124R~//~1211R~//~1307R~
        if ((itemid & 0xff)==POPUPSUBMENUID)	//3rd level submenu starter//~1328R~
        {                                                          //~1211M~
            if (Dump.Y) Dump.println("AjagoMenu:Sub of Submenu");                  //~1211M~//~1506R~
            popupSubmenu((com.Ajagoc.awt.Menu)awtitem,itemid);            //~1211I~//~1328R~
	    	showContextMenu();	//show submenu                     //~v104I~
	    	if (Dump.Y) Dump.println("AjagoMenu:onContextItemSelected popup rc="+rc);//~1Ag1I~
            return rc;                                             //~v104R~
        }                                                          //~1211M~
        try                                                        //~1309I~
        {                                                          //~1309I~
			rc=itemAction(menubar,Pitem,awtitem);                              //~1211R~//~1307R~//~1309R~
        }                                                          //~1309I~
        catch(Exception e)                                         //~1309I~
        {                                                          //~1309I~
        	Dump.println(e,"AjagoMenu:ContextItemnSelected Exception");       //~1309R~//~1402R~
		}                                                          //~1309I~
    	if (Dump.Y) Dump.println("AjagoMenu:onContextItemSelected rc="+rc);//~1Ag1I~
    	return rc;                                                 //~1121R~
    }                                                              //~1121I~
//****************************************************             //~1211I~
    public boolean itemAction(MenuBar Pmenubar,MenuItem Pitem,Object Pawtitem)      //~1211R~//~1307R~
    {                                                              //~1211I~
    	boolean rc=true;                                           //~1211I~
		CheckboxMenuItem awtchkboxitem;
    //************************                                     //~1211I~
        if (Pitem.isCheckable())                                   //~1211R~
        {                                                          //~1211I~
            awtchkboxitem=(CheckboxMenuItem)Pawtitem;              //~1211R~
        	if (Pitem.isChecked())                                 //~1211R~
            {                                                      //~1211I~
            	Pitem.setChecked(false);                           //~1211R~
                awtchkboxitem.setState(false);                     //~1211R~
            }                                                      //~1211I~
            else                                                   //~1211I~
            {                                                      //~1211I~
            	Pitem.setChecked(true);                            //~1211R~
                awtchkboxitem.setState(true);                      //~1211R~
            }                                                      //~1211I~
            if (awtchkboxitem.checkboxtranslator!=null)//ItemListener defined//~1211R~
            {                                                      //~1211I~
            	awtchkboxitem.checkboxtranslator.itemStateChanged(new ItemEvent());//~1211R~
            }                                                      //~1211I~
        }                                                          //~1211I~
        if (Pawtitem instanceof MenuItemAction)                    //~1211R~
        {                                                          //~1211I~
            if (Dump.Y) Dump.println("AjagoMenu:MenuitemAction");                  //~1211I~//~1506R~
            ActionEvent.actionPerformedMenu(Pmenubar,(com.Ajagoc.awt.MenuItem)Pawtitem);	//execute DoAction//~1408I~
        }                                                          //~1211I~
        if (Pawtitem instanceof MyMenu                             //~v1C9R~
        &&  ((MyMenu)Pawtitem).name.equals(optionsMenuitem)        //~v1C9R~
        )                                                          //~v1C9I~
        {                                                          //~v1C9I~
	        new AjagoOptions();                                    //~v1C9I~
            rc=false;	//bypass popup submenu                     //~v1C9I~
        }                                                          //~v1C9I~
    	return rc;                                                 //~1211I~
    }                                                              //~1211I~
//***********************                                          //~1124I~
	private	Object findItem(MenuBar Pmenubar,int Pitemid)          //~1124R~
    {                                                              //~1124I~
    	int menuid,itemid,nestid;                        //~1124I~
        com.Ajagoc.awt.Menu awtmenu,awtmenunest;                   //~1124I~
        Object awtitem;                                            //~1524R~
    //*******************                                          //~1124I~
    	menuid=(Pitemid>>16) & 0xff;                               //~1124I~
    	itemid=(Pitemid>>8)  & 0xff;                               //~1124I~
    	nestid=Pitemid & 0xff;                                     //~1124I~
        awtmenu=Pmenubar.getMenu(menuid-1);                        //~1124R~
        if (awtmenu.itemDirect!=null)   //on submenu item,select it//~1AfdI~
        	awtitem=awtmenu.itemDirect;   //select direct item     //~1AfdM~
        else                                                       //~1AfdI~
        if (itemid!=0)                                             //~1124I~
        {                                                          //~1124I~
        	awtitem=awtmenu.getItem(itemid-1);                        //~1124I~
            if (nestid!=0 && nestid!=POPUPSUBMENUID)               //~1124I~//~1211R~//~1328R~
            {                                                      //~1124I~//~1211R~//~1328R~
                awtmenunest=(com.Ajagoc.awt.Menu) awtitem;                               //~1124I~//~1211R~//~1328R~
                awtitem=awtmenunest.getItem(nestid-1);             //~1124I~//~1211R~//~1328R~
            }                                                      //~1124I~//~1211R~//~1328R~
        }                                                          //~1124I~
        else                                                       //~1124I~
        	awtitem=awtmenu;                                       //~1124I~
        return awtitem; 
    }//~1124I~
//****************************************************             //~1307I~
    public void onContextMenuClosed(Menu Pmenu)                    //~1307I~
    {                                                              //~1307I~
    	listerPopup=false;                                         //~1307I~
        if (Dump.Y) Dump.println("AjagoMenu onContextMenuClosed swPopupSubmenu="+swPopupSubmenu+",menu="+Pmenu.toString());//~1Ag1I~
        if (swPopupSubmenu)                                        //~1328R~
	    	showContextMenu();	//show submenu                     //~1328I~
//      else                                                       //~v104R~
//  	    currentMenuBar=null;                                       //~1307I~//~v104R~
    }                                                              //~1124I~
//****************************************************             //~1123I~
//*create menu from stacked Menu/submenu/item                      //~1123I~
//*itemid=menubarid(8)+menuid(8)+item(8)+level3 item(8);start from 1//~1123I~
//****************************************************             //~1123I~
	private void createMenu(ContextMenu Pcontextmenu,MenuBar Pmenubar)//~1123I~
    {                                                              //~1123I~
		com.Ajagoc.awt.Menu awtmenu;
		com.Ajagoc.awt.Menu helpMenu;                              //~1411I~
	   	Object awtitem;                                            //~1123I~
        MenuItem androiditem;                                      //~1123I~
        SubMenu  androidsubmenu;                                   //~1123I~
        String itemname;                                           //~1123I~
        int menubarid,menuid,itemctr,itemid,itemid2;               //~1524R~
        int none=android.view.Menu.NONE;                                         //~1123I~
    	boolean swHelpOnly;                                        //~1411I~
//*******************                                              //~1123I~
    	swHelpOnly=Pmenubar.getShowHelpOnly();                     //~1411I~
    	Pmenubar.setShowHelpOnly(false);                           //~1411R~
        helpMenu=Pmenubar.helpMenu;                                //~1411I~
        if (Dump.Y) Dump.println("AjagoMenu:createMenu helponly="+swHelpOnly);//~1506R~
        menubarid=(Pmenubar.seqno+1)<<24;                          //~1123R~
    	for (int ii=0;;ii++)                                       //~1123I~
        {                                                          //~1123I~
        	awtmenu=Pmenubar.getMenu(ii);                  //~1123R~
            if (awtmenu==null)                                     //~1123I~
            	break;                                             //~1123I~
	        menuid=(ii+1)<<16;                                     //~1123R~
            itemname=awtmenu.name;
            itemid=menubarid+menuid;//~1123I~
            if (swHelpOnly)                                        //~1412M~
            {                                                      //~1412M~
            	if (awtmenu!=helpMenu)                             //~1412M~
                	continue;                                      //~1412M~
				createHelpOnlyMenu(Pcontextmenu,Pmenubar,helpMenu,itemid);//~1412R~
            	return;                                            //~1412M~
            }                                                      //~1412M~
            itemid2=1<<8;                                          //~1123I~
            itemctr=awtmenu.getItemCtr();                          //~1412M~
            if (itemctr==0)                                        //~1123I~
            {                                                      //~1123I~
            //*menu item under menubar                             //~1123I~
              if (!itemname.equals(optionsMenuitem))	//menuite is direct call AjagocOptions//~v1C9I~
            	itemid+=itemid2;                                   //~1123I~
            	androiditem=Pcontextmenu.add(none/*group id*/,itemid,none/*order*/,itemname);//~1123I~
//  			setItem(androiditem,awtmenu);                      //~1124I~//~v1C9R~
                if (Dump.Y) Dump.println("AjagoMenu:menuitem "+Integer.toHexString(ii)+"="+itemname);//~1123R~//~1506R~
            }                                                      //~1123I~
            else                                                   //~1123I~
            {                                                      //~1123I~
            //*menu having submenu/menu item                       //~1123R~
            	androidsubmenu=Pcontextmenu.addSubMenu(none/*group id*/,itemid,none,itemname);//~1123I~
				setMenu((Menu)androidsubmenu,awtmenu);             //~1124I~
                if (Dump.Y) Dump.println("submenu "+Integer.toHexString(itemid)+"="+itemname);//~1123R~//~1506R~
		        setAjagocHelpMenuItem(Pcontextmenu,Pmenubar,awtmenu,androidsubmenu,itemid);//~1412R~
                for (int jj=0;;jj++)                               //~1123R~
                {                                                  //~1123I~
                	itemid2=(itemid+((jj+1)<<8));                         //~1123I~
                    awtitem=awtmenu.getItem(jj);                   //~1123R~
                    if (awtitem==null)                             //~1123I~
                        break;                                     //~1123I~
                    if (awtitem instanceof com.Ajagoc.awt.Menu)                  //~1123I~
                    {                                              //~1123I~
                    //*submenu having submenu                      //~1123I~
                        itemname=((com.Ajagoc.awt.Menu)awtitem).name+" +";//~1123I~//~1211R~
	    	            if (Dump.Y) Dump.println("AjagoMenu:menuitem "+Integer.toString(itemid2,16)+"="+itemname);//~1123I~//~1506R~
                        androiditem=androidsubmenu.add(none/*group id*/,itemid2+POPUPSUBMENUID/*sub of sub id*/,none,itemname);//~1123R~//~1211R~//~1328R~
                    }                                              //~1123I~
                    else                                           //~1123I~
                    {                                              //~1123I~
                    //*menuitem under submenu                      //~1123I~
                    	itemname=((com.Ajagoc.awt.MenuItem)awtitem).name;//~1123I~
	    	            if (Dump.Y) Dump.println("Ajagomenu:menuitem "+Integer.toString(itemid2,16)+"="+itemname);//~1123I~//~1506R~
	            		androiditem=androidsubmenu.add(none/*group id*/,itemid2,none,itemname);//~1123R~
						setItem(androiditem,awtitem);              //~1123I~
                    }                                              //~1123I~
                }                                                  //~1123I~
            }                                                      //~1123I~
                                          //~1123I~
        }                                                          //~1123I~
    }                                                              //~1123I~
//****************************************************             //~1328I~
//*3rd level submenu                                               //~1328I~
//****************************************************             //~1328I~
	private void createPopupSubmenu(ContextMenu Pcontextmenu,MenuBar Pmenubar,com.Ajagoc.awt.Menu Psubmenu)//~1328I~
    {                                                              //~1328I~
		com.Ajagoc.awt.Menu awtmenu;                               //~1328I~
		com.Ajagoc.awt.MenuItem awtitem2;                          //~1328I~
	   	MenuItem androiditem;                                      //~1328I~
        String itemname,submenutitle;                                           //~1328I~
        int itemid2,itemid3;//~1328I~
        int none=android.view.Menu.NONE;                           //~1328I~
	//*******************                                          //~1411R~
        awtmenu=Psubmenu;                                          //~1328I~
        itemid2=(Psubmenu.itemid & ~POPUPSUBMENUID);               //~1328R~
        submenutitle=awtmenu.name;                                  //~1328I~
        if (Dump.Y) Dump.println("AjagoMenu:createPopupSubMenu title="+submenutitle);//~1Ag1I~
        Pcontextmenu.setHeaderTitle(submenutitle);                  //~1328I~
        for (int kk=0;;kk++)                                       //~1328I~
        {                                                          //~1328I~
            itemid3=itemid2+kk+1;                                  //~1328I~
            awtitem2=(com.Ajagoc.awt.MenuItem)(awtmenu.getItem(kk));//~1328I~
            if (awtitem2==null)                                    //~1328I~
                break;                                             //~1328I~
            itemname=awtitem2.name;                                //~1328I~
            androiditem=Pcontextmenu.add(none/*groupid*/,itemid3,none/*order*/,itemname);//~1328I~
        	if (Dump.Y) Dump.println("AjagoMenu:createPopupSubMenu itemname="+itemname+",id="+Integer.toHexString(itemid3));//~1Ag1I~
            setItem(androiditem,awtitem2);                         //~1328I~
        }                                                          //~1328I~
    }                                                              //~1328I~
//****************************************************             //~1412I~
//*help submenu only                                               //~1412I~
//****************************************************             //~1412I~
	private void createHelpOnlyMenu(ContextMenu Pcontextmenu,MenuBar Pmenubar,com.Ajagoc.awt.Menu Psubmenu,int Pitemid)//~1412I~
    {                                                              //~1412I~
		com.Ajagoc.awt.Menu awtmenu;                               //~1412I~
		com.Ajagoc.awt.MenuItem awtitem2;                          //~1412I~
	   	MenuItem androiditem;                                      //~1412I~
        String itemname,submenutitle;                              //~1412I~
        int itemid2;                                               //~1412I~
        int none=android.view.Menu.NONE;                           //~1412I~
	//*******************                                          //~1412I~
        awtmenu=Psubmenu;                                          //~1412I~
        submenutitle=awtmenu.name;                                 //~1412I~
        Pcontextmenu.setHeaderTitle(submenutitle);                 //~1412I~
        setAjagocHelpMenuItem(Pcontextmenu,Pmenubar,Psubmenu,null,Pitemid);//~1412I~
        for (int jj=0;;jj++)                                       //~1412I~
        {                                                          //~1412I~
            itemid2=Pitemid+((jj+1)<<8);                           //~1412R~
            awtitem2=(com.Ajagoc.awt.MenuItem)(awtmenu.getItem(jj));//~1412I~
            if (awtitem2==null)                                    //~1412I~
                break;                                             //~1412I~
            itemname=awtitem2.name;                                //~1412I~
            if (Dump.Y) Dump.println("Ajagomenu:createHelpOnlyMenu name="+itemname);//~1506R~
            androiditem=Pcontextmenu.add(none/*groupid*/,itemid2,none/*order*/,itemname);//~1412I~
            setItem(androiditem,awtitem2);                         //~1412I~
        }                                                          //~1412I~
    }                                                              //~1412I~
//****************************************************             //~1411I~
	private void setAjagocHelpMenuItem(ContextMenu Pcontextmenu,MenuBar Pmenubar,com.Ajagoc.awt.Menu Psubmenu,SubMenu Pandroidsubmenu,int Pitemid)//~1412R~
    {                                                              //~1411I~
        int itemid2;                                               //~1411I~
        int none=android.view.Menu.NONE;                           //~1411I~
	//*******************                                          //~1411I~
        if (Pmenubar.seqno!=0 || !Psubmenu.name.equals(Global.resourceString("Help")))//~1412R~
        	return;                                                //~1411I~
        itemid2=Pitemid|AJAGOHELPMENUID;                           //~1412R~
        if (Dump.Y) Dump.println("AjagoMenu:add AjagoHelp itemid="+Integer.toHexString(itemid2));//~1506R~
        if (Pandroidsubmenu!=null)                                 //~1411I~
	        Pandroidsubmenu.add(none/*groupid*/,itemid2,none/*order*/,HELPITEM_AJAGOC);//~1411R~
        else                                                       //~1411I~
	        Pcontextmenu.add(none/*groupid*/,itemid2,none/*order*/,HELPITEM_AJAGOC);//~1411I~
    }                                                              //~1411I~
//****************************************************             //~1306I~
//*create WhoFrame ActionMenu from stacked Menu/submenu/item       //~1306I~
//*itemid=menubarid(8)+menuid(8)+item(8)+level3 item(8);start from 1//~1306I~
//****************************************************             //~1306I~
	private void createListerPopupMenu(ContextMenu Pcontextmenu,MenuBar Pmenubar,ContextMenuInfo Pinfo )//~1306I~//~1307R~
    {                                                              //~1306I~
		com.Ajagoc.awt.Menu awtmenu;                               //~1306I~
	   	Object awtitem;                                            //~1306I~
        MenuItem androiditem;                                      //~1306I~
        String itemname;                                           //~1306I~
        int menubarid,menuid,itemctr,itemid,itemid2;               //~1524R~
        int none=android.view.Menu.NONE;                           //~1306I~
        CharSequence selectedItem=null;                                  //~1307I~
		AdapterContextMenuInfo info;
		TextView tv;//~1307I~
//*******************                                              //~1306I~
        listerPopup=true;                                          //~1307M~
                                                                   //~1307I~
        if (Pinfo!=null)                                           //~1307I~
        {                                                          //+1307I~//~1307I~
            info=(AdapterContextMenuInfo)Pinfo;                    //~1307I~
            if (info.targetView!=null)                             //~1307I~
            {                                                      //~1307I~
                tv=(TextView)info.targetView;                      //~1307I~
                selectedItem=tv.getText();                         //~1307I~
                Pcontextmenu.setHeaderTitle(selectedItem);         //~1307I~
            }                                                      //~1307I~
        }                                                          //~1307I~
        menubarid=(Pmenubar.seqno+1)<<24;                          //~1306I~//~1307R~
        awtmenu=Pmenubar.getMenu(0);                              //~1306I~//~1307R~
        if (awtmenu==null)                                         //~1306I~//~1307R~
            return;                                              //~1306I~//~1307R~
        menuid=1<<16;                                         //~1306I~//~1307R~
        itemctr=awtmenu.getItemCtr();                              //~1306I~//~1307R~
        itemname=awtmenu.name;                                     //~1306I~//~1307R~
        itemid=menubarid+menuid;                                   //~1306I~//~1307R~
        itemid2=1<<8;                                              //~1306I~//~1307R~
        if (itemctr!=0)                                            //~1306I~//~1307R~
        {                                                          //~1306I~//~1307R~
            for (int jj=0;;jj++)                                   //~1306I~//~1307R~
            {                                                      //~1306I~//~1307R~
                itemid2=(itemid+((jj+1)<<8));                      //~1306I~//~1307R~
                awtitem=awtmenu.getItem(jj);                       //~1306I~//~1307R~
                if (awtitem==null)                                 //~1306I~//~1307R~
                    break;                                         //~1306I~//~1307R~
                itemname=((com.Ajagoc.awt.MenuItem)awtitem).name;  //~1306I~//~1307R~
                if (Dump.Y) Dump.println("AjagoMenu:menuitem "+Integer.toHexString(itemid2)+"="+itemname);//~1306I~//~1307R~//~1506R~
                androiditem=Pcontextmenu.add(none/*group id*/,itemid2,none,itemname);//~1306I~//~1307R~
                setItem(androiditem,awtitem);                      //~1306I~//~1307R~
            }                                                      //~1306I~//~1307R~
        }                                                          //~1306I~//~1307R~
    }                                                              //~1306I~
//***********************                                          //~1123I~
	private void setMenu(Menu Pandroiditem,com.Ajagoc.awt.Menu Pmenu)       //~1123I~//~1124R~
    {                                                              //~1123I~
    //*******************                                          //~1124I~
        if (Pmenu.font!=null)                                      //~1124I~
        {                                                          //~1124I~
//        	Pmenu.font.setFont((View)Pandroiditem);                            //~1124I~
//	        Menu dose not support to change font                   //~1124I~
        }                                                          //~1124I~
    }                                                              //~1123I~
//***********************                                          //~1124I~
	private void setItem(MenuItem Pandroiditem,Object Pitem)       //~1124I~
    {                                                              //~1124I~
        com.Ajagoc.awt.MenuItem awtitem;                           //~1124I~
    //*******************                                          //~1124I~
        awtitem=(com.Ajagoc.awt.MenuItem)Pitem;                    //~1124I~
        if (awtitem instanceof CheckboxMenuItem                    //~1124I~
        ||  awtitem instanceof CheckboxMenuItemAction              //~1124I~
        )                                                          //~1124I~
        {                                                          //~1124I~
            Pandroiditem.setCheckable(true);                       //~1124I~
            Pandroiditem.setChecked(((com.Ajagoc.awt.CheckboxMenuItem)awtitem).getState());           //~1124I~
        }                                                          //~1124I~
        if (awtitem.font!=null)                                    //~1124I~
        {                                                          //~1124I~
//        	font.setFont(Pandroiditem);                            //~1124I~
//	        Menu dose not support to change font                   //~1124I~
        }                                                          //~1124I~
        if (awtitem.iconResourceID!=0)      //android did not support icon menu//+1Ag3R~
        {                                                          //~1Ag3I~
//            Pandroiditem.setIcon(awtitem.iconResourceID);        //+1Ag3R~
//            Pandroiditem.setIcon(AG.resource.getDrawable(awtitem.iconResourceID));//+1Ag3R~
        }                                                          //~1Ag3I~
    }                                                              //~1124I~
//***********************                                          //~1211I~
	private void popupSubmenu(com.Ajagoc.awt.Menu Psubmenu,int Pitemid)//~1328I~
    {                                                              //~1328I~
        swPopupSubmenu=true;	//onContextMenuClosed request recursive showContextMenu//~1328I~
        currentMenuBar.popupSubmenu=Psubmenu;	//                 //~1328I~
        Psubmenu.itemid=Pitemid;                                   //~1328I~
        if (Dump.Y) Dump.println("AjagoMenu:popupSubmenu itemid="+Integer.toHexString(Pitemid)+",swPopupSubMenu="+swPopupSubmenu+",submenu="+Psubmenu.name);//~1Ag1I~
    }                                                              //~1328I~
//**********************************************************************//~1211I~
//*callback                                                        //~1211I~
//**********************************************************************//~1211I~
	public int alertButtonAction(int Pbuttonid,int Pitempos)       //~1211R~
    {                                                              //~1211I~
          int rc=1;   //dismiss                                      //~1211I~//~1328R~
          return rc;                                                 //~1211M~//~1328R~
    }                                                              //~1211I~
//****************************************************             //~1411I~
	private void helpAjagoc()                                      //~1411I~
    {                                                              //~1411I~
	//*******************                                          //~1411I~
        new Help(HELPTEXT_AJAGOC);                                     //~1411I~
        if (Dump.Y) Dump.println("AjagoMenu:AjagoHelp");           //~1506R~
    }                                                              //~1411I~
//*********************************************                    //~1314I~
//*Option Menu ********************************                    //~1314I~
//*********************************************                    //~1314I~
//* called only once                                               //~1326I~
 	public  void onCreateOptionMenu(Menu Pmenu)                    //~1314R~
	{                                                              //~1314I~
        String str;                                                //~1314I~
    //********************                                         //~1314I~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~v1D1I~
        {                                                          //~v1D1I~
            onCreateOptionMenu_V11(Pmenu);                         //~v1D1I~
            return;                                                //~v1D1I~
        }                                                          //~v1D1I~
	    menuDesc=AG.resource.getStringArray(R.array.MenuText);  //~1314I~//~1326R~
        for (int ii=0;ii<MENU_CTR;ii++)                            //~1326R~
		{                                                          //~1314I~
        	str=menuDesc[ii];                                      //~1314I~
            int id=menuId[ii];                                     //~1314I~
            MenuItem item=Pmenu.add(0,id,0,str);                    //~1314I~
            item.setIcon(icons[ii]);                               //~1314I~
        }                                                          //~1314I~
    }                                                              //~1314I~
//**************                                                   //~v1D1I~
 	public void onCreateOptionMenu_V11(Menu Pmenu)                 //~v1D1I~
	{                                                              //~v1D1I~
        MenuInflater inf=AG.activity.getMenuInflater();            //~v1D1I~
 	  if (AG.layoutMdpi)                                           //~1A8sR~
        inf.inflate(R.menu.actionbar_mdpi,Pmenu);                  //~1A8sR~
      else                                                         //~1A8sR~
        inf.inflate(R.menu.actionbar,Pmenu);                       //~v1D1I~
    }                                                              //~v1D1I~
//**************                                                   //~v107I~
//* called each time to diaply                                     //~v107I~
 	public  void onPrepareOptionMenu(Menu Pmenu)                   //~v107I~
	{                                                              //~v107I~
    //********************                                         //~v107I~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~v1D1I~
        	return;                                                //~v1D1I~
    /*@@@@                                                         //~1B0eI~
    	if (AG.portrait                                            //~v107R~
//      ||  AG.scrWidth<MENUMENU_SIZE                              //~v107R~
        )                                                          //~v107I~
	    	Pmenu.findItem(MENU_MENU).setVisible(false);        //~v107I~
        else                                                       //~v107I~
     @@@@*/                                                        //~1B0eI~
	    	Pmenu.findItem(MENU_MENU).setVisible(true);            //~v107I~
    }                                                              //~v107I~
//**************                                                   //~1314I~
 	public  int onOptionMenuSelected(MenuItem item)                //~1314I~
	{                                                              //~1314I~
        int itemid=item.getItemId();                               //~1314I~
        if (Dump.Y) Dump.println("AjagoMenu:OptionMenuSelected="+itemid);//~1314I~ //~1326R~//~1506R~
        switch(itemid)                                             //~1314I~
        {                                                          //~1314I~
        case    MENU_STOP:                                       //~1314R~//~1326R~
        case    R.id.MENU_STOP:                                    //~v1D1I~
            stopApp();                                             //~1412I~
            break;                                                 //~1412I~
        case    MENU_CLOSE:                                        //~1326I~
        case    R.id.MENU_CLOSE:                                   //~v1D1I~
            closeFrame();                                          //~1314R~
            break;                                                 //~1314I~
        case    MENU_HELP:                                         //~1314I~
        case    R.id.MENU_HELP:                                    //~v1D1I~
            optionMenuHelp();                                      //~1314R~
            break;                                                 //~1314I~
        case    MENU_MENU:                                         //~v107I~
        case    R.id.MENU_MENU:                                    //~v1D1I~
            optionMenuMenu();                                      //~v107I~
            break;                                                 //~v107I~
        case    R.id.MENU_PREVIOUS:                                //~v1D1I~
            actionbarPrevious();                                   //~v1D1I~
            break;                                                 //~v1D1I~
        case    R.id.MENU_NEXT:                                    //~v1D1I~
            actionbarNext();                                       //~v1D1I~
            break;                                                 //~v1D1I~
        }                                                          //~1314I~
        return 0;                                                  //~1314I~
    }//selected                                                    //~1314I~
//**************                                                   //~1412I~
    public void stopApp()                                          //~1412I~
    {                                                              //~1412I~
        confirmStop();                                             //~1412I~
    }                                                              //~1412I~
//**************                                                   //~1314I~
    public void closeFrame()                                       //~1314R~
    {                                                              //~1314I~
        try                                                        //~1423I~
        {                                                          //~1423I~
            if (AG.isTopFrame())                                   //~1423R~
            {                                                      //~1Ag1R~
	          if (AG.mainframeTag==AjagoView.TABINDEX_TOP)         //~1Ag1R~
                confirmStop();                                     //~1423R~
              else                                                 //~1Ag1R~
              	AG.ajagov.setCurrentTab(AjagoView.TABINDEX_TOP);   //~1Ag1R~
            }                                                      //~1Ag1R~
            else                                                   //~1423R~
            {                                                      //~1423R~
                if (!ActionEvent.optionMenuClose()) //CloseFrame doAction not scheduled//~1423R~
                    Window.popFrame(true);  //close                //~1423R~
            }                                                      //~1423R~
        }                                                          //~1423I~
        catch(Exception e)                                         //~1423I~
        {                                                          //~1423I~
        	Dump.println(e,"AjagoMenu:closeFrame from OptionMenu");//~1423I~
		}                                                          //~1423I~
    }                                                              //~1314I~
//**************                                                   //~1314I~
    public void confirmStop()                                      //~1314I~
    {                                                              //~1314I~
    	int flag=AjagoAlert.BUTTON_POSITIVE|AjagoAlert.BUTTON_NEGATIVE|AjagoAlert.EXIT;//~1314I~
        AjagoAlert.simpleAlertDialog(null/*callback*/,null/*title*/,R.string.Qexit,flag);//~1314I~
    }                                                              //~1314I~
//**************                                                   //~1314I~
    public void showContextMenu()                                  //~1314I~
    {                                                              //~1314I~
        View view=Window.getCurrentFrame().contextMenuView;        //~1314I~
    	if (view==null)                                            //~1314I~
        {                                                          //~1314I~
        	AjagoView.showToast(R.string.NoRegisteredContextMenu); //~1314I~
            return;                                                //~1314I~
        }                                                          //~1314I~
        if (Dump.Y) Dump.println("AjagoMenu showContextMenu view="+view.toString());//~1516M~
		swShowRequest=true;                                        //~1427I~
        view.showContextMenu();                                    //~v104R~
//      AG.activity.openContextMenu(view);                         //~v104R~
    }                                                              //~1314I~
//**************                                                   //~1314I~
    public void optionMenuHelp()                                        //~1314I~
    {                                                              //~1314I~
        Frame frame=Window.getCurrentFrame();                      //~1411I~
        MenuBar menubar=frame.framemenubar;                        //~1411I~
    	if (menubar==null|| menubar.helpMenu==null)                //~1411I~
        {                                                          //~1411I~
        	AjagoView.showToast(R.string.NoRegisteredHelpMenu);    //~1411I~
            return;                                                //~1411I~
        }                                                          //~1411I~
        menubar.setShowHelpOnly(true);
        View view=frame.contextMenuView;//~1411I~
        if (Dump.Y) Dump.println("AjagoMenu optionMenuHelp view="+view.toString());//~1506R~
		swShowRequest=true;                                        //~1427I~
        view.showContextMenu();                                    //~1411I~
    }                                                              //~1314I~
//**************                                                   //~v107I~
    public void optionMenuMenu()                                   //~v107I~
    {                                                              //~v107I~
	    showContextMenu();	//show submenu                         //~v107I~
    }                                                              //~v107I~
//************************************************                 //~1507I~
//*Ajago Option menu add before help of MainFrame menubar          //~1507I~
//************************************************                 //~1507I~
    private com.Ajagoc.awt.Menu ajagocOptions;                             //~1507I~//~v1C9R~
//    private CheckboxMenuItemAction opt1,opt2,opt3;          //~1507I~//~v1C9R~
//    private CheckboxMenuItemAction opt_onetouch_localboard,opt_onetouch_matchboard;//~1B0eI~//~v1C9R~
                                                                   //~1507I~
//  private static final String DIRECTIONKEY="Use DirectionKey as Shortcut";//~1507I~//~1B0eR~
//  private static final String SEARCHKEY   ="Use Search button as Enter";//~1507I~//~1B0eR~
//    private static String DIRECTIONKEY="";                         //~1B0eI~//~v1C9R~
//    private static String SEARCHKEY   ="";                         //~1B0eI~//~v1C9R~
//    private static String ONETOUCH_LOCAL="";                       //~1B0eI~//~v1C9R~
//    private static String ONETOUCH_MATCH="";                       //~1B0eI~//~v1C9R~
//    private static final String DEBUGTRACE  ="DebugTrace";         //~1507I~//~v1C9R~
//    private static final int ID_DIRECTIONKEY   =R.string.UseDirectionKey;//~1B0eI~//~v1C9R~
//    private static final int ID_SEARCHKEY      =R.string.UseSearchKey;//~1B0eI~//~v1C9R~
//    private static final int ID_ONETOUCH_LOCAL =R.string.OneTouchLocalBoard;//~1B0eI~//~v1C9R~
//    private static final int ID_ONETOUCH_MATCH =R.string.OneTouchMatchBoard;//~1B0eI~//~v1C9R~
                                                                   //~1507I~
    public  static final String DIRECTIONKEY_CFGKEY="directionkey";//~1507R~
    public  static final String SEARCHKEY_CFGKEY   ="searchkey";   //~1507R~
    public  static final String DEBUGTRACE_CFGKEY  ="debugtrace";  //~1507R~
    public  static final String ONETOUCH_LOCAL_CFGKEY="onetouchlocal";//~1B0eI~
    public  static final String ONETOUCH_MATCH_CFGKEY="onetouchmatch";//~1B0eI~
                                                                   //~1507I~
    //****************************************************************//~v1C9I~
    //*from MenuBar                                                //~v1C9I~
    //****************************************************************//~v1C9I~
	public void addAjagoOptionMenu(MenuBar Pmenubar)        //~1507I~
    {                                                              //~1507I~
//        boolean flag;                                              //~1507R~//~v1C9R~
        if (ajagocOptions==null)                                   //~1507R~
        {                                                          //~1507R~
//          ajagocOptions=new MyMenu(AG.appName+" "+Global.resourceString("Options"));//~1507R~//~v1C9R~
            ajagocOptions=new MyMenu(optionsMenuitem);             //~v1C9I~
//            DIRECTIONKEY=AG.resource.getString(ID_DIRECTIONKEY);   //~1B0eI~//~v1C9R~
//            SEARCHKEY=AG.resource.getString(ID_SEARCHKEY);        //~1B0eI~//~v1C9R~
//            ONETOUCH_LOCAL=AG.resource.getString(ID_ONETOUCH_LOCAL);//~1B0eI~//~v1C9R~
//            ONETOUCH_MATCH=AG.resource.getString(ID_ONETOUCH_MATCH);//~1B0eI~//~v1C9R~
//            opt1=new CheckboxMenuItemAction(this,DIRECTIONKEY);   //~1507R~//~v1C9R~
//            opt2=new CheckboxMenuItemAction(this,SEARCHKEY);     //~1507R~//~v1C9R~
//            opt3=new CheckboxMenuItemAction(this,DEBUGTRACE);    //~1507R~//~v1C9R~
//            opt_onetouch_localboard=new CheckboxMenuItemAction(this,ONETOUCH_LOCAL);//~1B0eI~//~v1C9R~
//            opt_onetouch_matchboard=new CheckboxMenuItemAction(this,ONETOUCH_MATCH);//~1B0eI~//~v1C9R~
//            ajagocOptions.add(opt1);                                     //~1507R~//~v1C9R~
//            ajagocOptions.add(opt2);                                     //~1507R~//~v1C9R~
//            ajagocOptions.add(opt_onetouch_localboard);            //~1B0eI~//~v1C9R~
//            ajagocOptions.add(opt_onetouch_matchboard);            //~1B0eI~//~v1C9R~
//          if (AG.isDebuggable)                                     //~v107I~//~v1C9R~
//            ajagocOptions.add(opt3);                                     //~1507R~//~v1C9R~
        }                                                          //~1511M~
//        flag=Global.getParameter(DIRECTIONKEY_CFGKEY,false);       //~1511R~//~v1C9R~
//        if (Dump.Y) Dump.println("addAjagoOptionMenu directionkey="+flag);//~1511R~//~v1C9R~
//        opt1.setState(flag);                                       //~1511R~//~v1C9R~
//        flag=Global.getParameter(SEARCHKEY_CFGKEY,true);           //~1511R~//~v1C9R~
//        if (Dump.Y) Dump.println("addAjagoOptionMenu searchkey="+flag);//~1511R~//~v1C9R~
//        opt2.setState(flag);                                       //~1511R~//~v1C9R~
//        flag=Global.getParameter(DEBUGTRACE_CFGKEY,false);         //~1511R~//~v1C9R~
//        if (Dump.Y) Dump.println("addAjagoOptionMenu debugtrace="+flag);//~1511R~//~v1C9R~
//        opt3.setState(flag);                                       //~1511R~//~v1C9R~
//        flag=Global.getParameter(ONETOUCH_LOCAL_CFGKEY,false); //~1B0eI~//~v1C9R~
//        if (Dump.Y) Dump.println("addAjagoOptionMenu ounetouch local="+flag);//~1B0eI~//~v1C9R~
//        opt_onetouch_localboard.setState(flag);                    //~1B0eI~//~v1C9R~
//        flag=Global.getParameter(ONETOUCH_MATCH_CFGKEY,false); //~1B0eI~//~v1C9R~
//        if (Dump.Y) Dump.println("addAjagoOptionMenu ounetouch match="+flag);//~1B0eI~//~v1C9R~
//        opt_onetouch_matchboard.setState(flag);                    //~1B0eI~//~v1C9R~
        Pmenubar.menuList.add(ajagocOptions);                               //~1507R~
    }                                                              //~1507I~
//    //****************************************************************//~1B32I~//~v1C9R~
//    //*from GoFrame/ConnectedGoFrame                             //~v1C9I~
//    //****************************************************************//~v1C9I~
//    public static void addOneTouchModeMenuitem(GoFrame Pgf,com.Ajagoc.awt.Menu Pmenu)//~1B32I~//~v1C9R~
//    {                                                              //~1B32I~//~v1C9R~
//        int id;                                                    //~1B32I~//~v1C9R~
//        String key;                                              //~v1C9R~
//        boolean flag;//~1B32I~                                   //~v1C9R~
//        CheckboxMenuItemAction cbotm;                              //~1B32I~//~v1C9R~
//    //****************************************                     //~1B32I~//~v1C9R~
//        if (Dump.Y) Dump.println("Ajagomenu addOneTouchModeMenuitem");//~1B32I~//~v1C9R~
//        if (Pgf instanceof ConnectedGoFrame)                       //~1B32I~//~v1C9R~
//        {                                                          //~1B32I~//~v1C9R~
//            ONETOUCH_MATCH=AG.resource.getString(ID_ONETOUCH_MATCH);//~1B32I~//~v1C9R~
//            cbotm=new CheckboxMenuItemAction(Pgf,ONETOUCH_MATCH); //~1B32I~//~v1C9R~
//            key=ONETOUCH_MATCH_CFGKEY;                             //~1B32I~//~v1C9R~
//        }                                                          //~1B32I~//~v1C9R~
//        else                                                       //~1B32I~//~v1C9R~
//        {                                                          //~1B32I~//~v1C9R~
//            ONETOUCH_LOCAL=AG.resource.getString(ID_ONETOUCH_LOCAL);//~1B32I~//~v1C9R~
//            cbotm=new CheckboxMenuItemAction(Pgf,ONETOUCH_LOCAL); //~1B32I~//~v1C9R~
//            key=ONETOUCH_LOCAL_CFGKEY;                             //~1B32I~//~v1C9R~
//        }                                                          //~1B32I~//~v1C9R~
//        Pmenu.add(cbotm);                                          //~1B32I~//~v1C9R~
//        flag=Global.getParameter(key,false);                       //~1B32I~//~v1C9R~
//        cbotm.setState(flag);                                      //~1B32I~//~v1C9R~
//    }                                                              //~1B32I~//~v1C9R~
    //****************************************************************//~1B32I~
    //*from GoFrame                                                //~v1C9I~
    //****************************************************************//~v1C9I~
//  public static void chkOneTouchModeOption(String Paction,boolean Pflag)//~1B32I~//~v1C9R~
//  {                                                              //~1B32I~//~v1C9R~
//      if (Dump.Y) Dump.println("Ajagomenu chkOneTouchModeOption action="+Paction+",flag="+Pflag);//~1B32I~//~v1C9R~
//        if (Paction.equals(ONETOUCH_MATCH))                        //~1B32I~//~v1C9R~
//        {                                                          //~1B32I~//~v1C9R~
//            AG.ajagoMenu.itemAction(Paction,Pflag);                //~1B32I~//~v1C9R~
//            AG.ajagoMenu.opt_onetouch_matchboard.setState(Pflag);                //~1B32I~//~v1C9R~
//        }                                                          //~1B32I~//~v1C9R~
//        else                                                       //~1B32I~//~v1C9R~
//        if (Paction.equals(ONETOUCH_LOCAL))                        //~1B32I~//~v1C9R~
//        {                                                          //~1B32I~//~v1C9R~
//            AG.ajagoMenu.itemAction(Paction,Pflag);                //~1B32I~//~v1C9R~
//            AG.ajagoMenu.opt_onetouch_localboard.setState(Pflag);                //~1B32I~//~v1C9R~
//        }                                                          //~1B32I~//~v1C9R~
//  }                                                              //~1B32I~//~v1C9R~
    @Override                                                      //~1507I~
    public void doAction(String o)                                 //~1507I~
    {                                                              //~1507I~
    }                                                              //~1507I~
    @Override                                                      //~1507I~//~v1C9R~
    public void itemAction(String o,boolean flag)                  //~1507I~//~v1C9R~
    {                                                              //~1507I~//~v1C9R~
        if (Dump.Y) Dump.println("Ajagomenu ItemAction for AjagoOption "+o+"="+flag);//~1507I~//~v1C9R~
//        if (o.equals(DIRECTIONKEY))                                //~1507I~//~v1C9R~
//        {                                                          //~1507I~//~v1C9R~
//            Global.setParameter(DIRECTIONKEY_CFGKEY,flag);         //~1507I~//~v1C9R~
//            Canvas.optionChanged(flag);                            //~1507I~//~v1C9R~
//        }                                                          //~1507I~//~v1C9R~
//        else                                                       //~1507I~//~v1C9R~
//        if (o.equals(SEARCHKEY))                                   //~1507I~//~v1C9R~
//        {                                                          //~1507I~//~v1C9R~
//            Global.setParameter(SEARCHKEY_CFGKEY,flag);            //~1507I~//~v1C9R~
//            AjagoKey.optionChanged(flag);                          //~1507I~//~v1C9R~
//        }                                                          //~1507I~//~v1C9R~
//        else                                                       //~1507I~//~v1C9R~
//        if (o.equals(DEBUGTRACE))                                  //~1507I~//~v1C9R~
//        {                                                          //~1507I~//~v1C9R~
//            Global.setParameter(DEBUGTRACE_CFGKEY,flag);           //~1507I~//~v1C9R~
//            Dump.setOption(flag);                                  //~1507I~//~v1C9R~
//        }                                                          //~1507I~//~v1C9R~
//        else                                                       //~1B0eI~//~v1C9R~
//        if (o.equals(ONETOUCH_LOCAL))                              //~1B0eI~//~v1C9R~
//        {                                                          //~1B0eI~//~v1C9R~
//            Global.setParameter(ONETOUCH_LOCAL_CFGKEY,flag);       //~1B0eI~//~v1C9R~
//            Canvas.optionChanged(Canvas.ONETOUCH_LOCALBOARD,flag); //~1B0eI~//~v1C9R~
//        }                                                          //~1B0eI~//~v1C9R~
//        else                                                       //~1B0eI~//~v1C9R~
//        if (o.equals(ONETOUCH_MATCH))                              //~1B0eI~//~v1C9R~
//        {                                                          //~1B0eI~//~v1C9R~
//            Global.setParameter(ONETOUCH_MATCH_CFGKEY,flag);       //~1B0eI~//~v1C9R~
//            Canvas.optionChanged(Canvas.ONETOUCH_MATCHBOARD,flag); //~1B0eI~//~v1C9R~
//        }                                                          //~1B0eI~//~v1C9R~
    }                                                              //~1507I~//~v1C9R~
//**************                                                   //~v1D1I~
    private void actionbarPrevious()                               //~v1D1I~
    {                                                              //~v1D1I~
    	Window.wrapFrameByTouch(-1);                               //~v1D1I~
    }                                                              //~v1D1I~
//**************                                                   //~v1D1I~
    private void actionbarNext()                                   //~v1D1I~
    {                                                              //~v1D1I~
    	Window.wrapFrameByTouch(1);                                //~v1D1I~
    }                                                              //~v1D1I~
//**************                                                   //~1Ag0I~
    public static void updateItemNameGTP(com.Ajagoc.awt.Menu Pmenu,int Pgtpid,String Pitemname)//~1Ag0I~
    {                                                              //~1Ag0I~
    	int idx=Pgtpid-1;                                          //~1Ag0I~
    	if (idx>=0 && idx<Pmenu.getItemCtr())                //~1Ag0I~
        {                                                          //~1Ag0I~
        	com.Ajagoc.awt.MenuItem item=(com.Ajagoc.awt.MenuItem) Pmenu.getItem(idx);                             //~1Ag0I~
            if (Dump.Y) Dump.println("AjagoMenu:updateItemNameGTP idx="+idx+"="+item.name+"-->"+Pitemname);//~1Ag0I~
            item.name=Pitemname;                                  //~1Ag0I~
        }                                                          //~1Ag0I~
    }                                                              //~1Ag0I~
//**************                                                   //~1Ag1I~
    public void openContextMenu(MainFrame F,String Psubmenuname)   //~1Ag1R~
    {                                                              //~1Ag1I~
        if (Dump.Y) Dump.println("AjagoMenu:openContextMenu Psubmenuname="+Psubmenuname);//~1Ag1R~
        if (Psubmenuname.equals(MainFrame.SUBMENUNAME_ONLINEHELP)) //~1Ag1I~
        {                                                          //~1Ag1I~
			MenuBar menubar=Frame.MainFrame.framemenubar;          //~1Ag1I~
        	menubar.setShowHelpOnly(true);                         //~1Ag1I~
        }                                                          //~1Ag1I~
        topSubmenuname=Global.resourceString(Psubmenuname);        //~1Ag1R~
	    showContextMenu();                                         //~1Ag1I~
    }                                                              //~1Ag1I~
//***************************************************************************//~1Ag1R~
	private void createMenuTopSubmenu(ContextMenu Pcontextmenu,MenuBar Pmenubar)//~1Ag1R~
    {                                                              //~1Ag1I~
		com.Ajagoc.awt.Menu awtmenu;                               //~1Ag1I~
		com.Ajagoc.awt.Menu helpMenu;                              //~1Ag1I~
	   	Object awtitem;                                            //~1Ag1I~
        MenuItem androiditem;                                      //~1Ag1I~
        SubMenu  androidsubmenu;                                   //~1Ag1R~
        String itemname;                                           //~1Ag1I~
        int menubarid,menuid,itemctr,itemid,itemid2;               //~1Ag1I~
        int none=android.view.Menu.NONE;                           //~1Ag1I~
    	boolean swHelpOnly;                                        //~1Ag1I~
//*******************                                              //~1Ag1I~
	  try                                                          //~1Ag1I~
      {                                                            //~1Ag1I~
    	swHelpOnly=Pmenubar.getShowHelpOnly();                     //~1Ag1I~
    	Pmenubar.setShowHelpOnly(false);                           //~1Ag1I~
        helpMenu=Pmenubar.helpMenu;                                //~1Ag1I~
        if (Dump.Y) Dump.println("AjagoMenu:createTopSubMenu:createMenu helponly="+swHelpOnly);//~1Ag1R~
        menubarid=(Pmenubar.seqno+1)<<24;                          //~1Ag1I~
    	for (int ii=0;;ii++)                                       //~1Ag1I~
        {                                                          //~1Ag1I~
        	awtmenu=Pmenubar.getMenu(ii);                          //~1Ag1I~
            if (awtmenu==null)                                     //~1Ag1I~
            	break;                                             //~1Ag1I~
	        menuid=(ii+1)<<16;                                     //~1Ag1I~
            itemname=awtmenu.name;                                 //~1Ag1I~
            itemid=menubarid+menuid;                               //~1Ag1I~
            if (swHelpOnly)                                        //~1Ag1I~
            {                                                      //~1Ag1I~
            	if (awtmenu!=helpMenu)                             //~1Ag1I~
                	continue;                                      //~1Ag1I~
				createHelpOnlyMenu(Pcontextmenu,Pmenubar,helpMenu,itemid);//~1Ag1I~
            	return;                                            //~1Ag1I~
            }                                                      //~1Ag1I~
            if (!itemname.equals(topSubmenuname))                  //~1Ag1I~
                continue;                                          //~1Ag1I~
            itemid2=1<<8;                                          //~1Ag1I~
            itemctr=awtmenu.getItemCtr();                          //~1Ag1I~
            if (itemctr==0)                                        //~1Ag1I~
            {                                                      //~1Ag1I~
            //*menu item under menubar                             //~1Ag1I~
//              if (!itemname.equals(optionsMenuitem))    //menuite is direct call AjagocOptions//~1Ag1I~
//                itemid+=itemid2;                                 //~1Ag1I~
//                androiditem=Pcontextmenu.add(none/*group id*/,itemid,none/*order*/,itemname);//~1Ag1I~
                if (Dump.Y) Dump.println("AjagoMenu:createTopSubmenu:menuitem "+Integer.toHexString(ii)+"="+itemname);//~1Ag1R~
                return;                                            //~1Ag1I~
            }                                                      //~1Ag1I~
            else                                                   //~1Ag1I~
            {                                                      //~1Ag1I~
            //*menu having submenu/menu item                       //~1Ag1I~
//          	androidsubmenu=Pcontextmenu.addSubMenu(none/*group id*/,itemid,none,itemname);//~1Ag1I~
//  			setMenu((Menu)androidsubmenu,awtmenu);             //~1Ag1I~
                if (Dump.Y) Dump.println("AjagoMenu:createTopSubMenu submenu "+Integer.toHexString(itemid)+"="+itemname);//~1Ag1R~
                for (int jj=0;;jj++)                               //~1Ag1I~
                {                                                  //~1Ag1I~
                	itemid2=(itemid+((jj+1)<<8));                  //~1Ag1I~
                    awtitem=awtmenu.getItem(jj);                   //~1Ag1I~
                    if (awtitem==null)                             //~1Ag1I~
                        break;                                     //~1Ag1I~
                    if (awtitem instanceof com.Ajagoc.awt.Menu)    //~1Ag1I~
                    {                                              //~1Ag1I~
                    //*submenu having submenu                      //~1Ag1I~
                        itemname=((com.Ajagoc.awt.Menu)awtitem).name+" +";//~1Ag1I~
	    	            if (Dump.Y) Dump.println("AjagoMenu:cretaeTopSubMenu menuitem "+Integer.toString(itemid2,16)+"="+itemname);//~1Ag1R~
//                      androiditem=androidsubmenu.add(none/*group id*/,itemid2+POPUPSUBMENUID/*sub of sub id*/,none,itemname);//~1Ag1I~
                    	androidsubmenu=Pcontextmenu.addSubMenu(none/*group id*/,itemid2,none,itemname);//~1Ag1R~
                        addTopSubmenuItem(androidsubmenu,(com.Ajagoc.awt.Menu)awtitem,itemid2);//~1Ag1I~
                    }                                              //~1Ag1I~
                    else                                           //~1Ag1I~
                    {                                              //~1Ag1I~
                    //*menuitem under submenu                      //~1Ag1I~
                    	itemname=((com.Ajagoc.awt.MenuItem)awtitem).name;//~1Ag1I~
	    	            if (Dump.Y) Dump.println("Ajagomenu:createTopSubMenu menuitem "+Integer.toString(itemid2,16)+"="+itemname);//~1Ag1R~
//              		androiditem=androidsubmenu.add(none/*group id*/,itemid2,none,itemname);//~1Ag1I~
                        androiditem=Pcontextmenu.add(none/*group id*/,itemid2,none,itemname);//~1Ag1I~
						setItem(androiditem,awtitem);              //~1Ag1I~
                    }                                              //~1Ag1I~
                }                                                  //~1Ag1I~
            }                                                      //~1Ag1I~
                                                                   //~1Ag1I~
        }                                                          //~1Ag1I~
      }                                                            //~1Ag1I~
      catch(Exception e)                                           //~1Ag1I~
      {                                                            //~1Ag1I~
        	Dump.println(e,"AjagoMenu:createMenuTopSubmenu");      //~1Ag1I~
	  }                                                            //~1Ag1I~
        topSubmenuname="";                                         //~1Ag1I~
    }//createMenuTopSubmenu                                        //~1Ag1R~
//***************************************************************************//~1Ag1I~
	private void addTopSubmenuItem(SubMenu Psubmenu,com.Ajagoc.awt.Menu Pawtmenu,int Pitemid)//~1Ag1I~
    {                                                              //~1Ag1I~
		Object awtitem;                           //~1Ag1I~
        MenuItem androiditem;                                      //~1Ag1I~
        String itemname;                                           //~1Ag1I~
        int itemid3;                                               //~1Ag1I~
        int none=android.view.Menu.NONE;                           //~1Ag1I~
//*******************                                              //~1Ag1I~
        if (Dump.Y) Dump.println("AjagoMenu:addTopSubmenuItem:itemid="+Integer.toHexString(Pitemid));//~1Ag1I~
        for (int kk=0;;kk++)                                       //~1Ag1I~
        {                                                          //~1Ag1I~
            awtitem=Pawtmenu.getItem(kk);                          //~1Ag1I~
            if (awtitem==null)                                     //~1Ag1I~
                break;                                             //~1Ag1I~
            itemname=((com.Ajagoc.awt.MenuItem)awtitem).name;      //~1Ag1I~
            itemid3=Pitemid+kk+1;                                  //~1Ag1I~
            if (Dump.Y) Dump.println("Ajagomenu:addTopSubMenuItem menuitem "+Integer.toHexString(itemid3)+"="+itemname);//~1Ag1I~
            androiditem=Psubmenu.add(none/*group id*/,itemid3,none,itemname);//~1Ag1I~
            setItem(androiditem,awtitem);                          //~1Ag1I~
        }                                                          //~1Ag1I~
    }//addTopSubmenuItem                                           //~1Ag1I~
}//class AjagoMenu                                                 //~1211R~
