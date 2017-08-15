//*CID://+1Ae7R~: update#= 192;                                    //~1Ae7R~
//**********************************************************************//~1107I~
//1Ae7 2015/07/25 missing ListData of  //1A6f 2015/02/13 support custom layout of ListView for BluetoothConnection to show available/paired status//~1Ae7I~
//2015/07/23 //1AbS 2015/07/03 BT:LeastRecentlyUsed List for once conneceted device list//~1AbSI~
//v1Ec 2014/12/11 (Asgts)//1A4a 2014/11/29 FileDialog:open when selected item is clicked//~v1EcI~
//v1E2 2014/12/02 set textSize depending height of textrowlist     //~v1E2I~
//1B41 2013/07/18 onItmSelectd is shedule with index of page top.  //~1B41I~
//                It occurs at longpress after when message dialog popup then closed.//~1B41I~
//                Popup menu title is of long press,               //~1B41I~
//                but at action selected selectedpos was changed   //~1B41I~
//1079:121208 Nexus7(Android4.2)listview touch dose not call getView(),litview item is not highlightened//~v107I~
//1053:121113 exception(wrong thread) when filelist up/down for sgf file read//~v105I~
//**********************************************************************//~v105I~
//*My ListView Adapter                                                     //~1107I~//~1109R~
//**********************************************************************//~1107I~
package com.Ajagoc.awt;                                         //~1107R~  //~1108R~//~1109R~//~1114R~

import jagoclient.Dump;

import java.util.ArrayList;

import com.Ajagoc.AG;
import com.Ajagoc.R;
import com.Ajagoc.awt.ActionListener;
import com.Ajagoc.awt.Color;
import com.Ajagoc.awt.Font;

import android.view.MotionEvent;
import android.view.View;                                          //~1109I~
import android.view.View.OnTouchListener;
import android.view.ViewGroup;                                     //~1109I~
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
                                                                   //~1109I~
//class ListData                                                     //~1220I~//~1Ae7R~
//{                                                                  //~1220I~//~1Ae7R~
//    public String itemtext;                                        //~1220I~//~1Ae7R~
//    public Color  itemcolor;                                       //~1220I~//~1Ae7R~
//    public ListData(String Pitem,Color Pcolor)                     //~1220I~//~1Ae7R~
//    {                                                              //~1220I~//~1Ae7R~
//        itemtext=Pitem;                                            //~1220I~//~1Ae7R~
//        itemcolor=Pcolor;                                          //~1220I~//~1Ae7R~
//    }                                                              //~1220I~//~1Ae7R~
//}                                                                  //~1220I~//~1Ae7R~
public class List                                                  //~1114           //~1220I~
{                                                                  //~1110I~
	public ListView listview;                                      //~1425R~
	private ArrayAdapter<ListData> adapter;                        //~1425R~
//  private ArrayList<ListData> arrayData;                         //~1425R~//~1Ae7R~
    public ArrayList<ListData> arrayData;                          //~1A6fR~//~1Ae7I~
//  private int resourceid=AG.viewId_ListView;                     //~1220I~//~1Ae7R~
    protected int resourceid=AG.viewId_ListView;                   //~1A6fI~//~1Ae7I~
    private Component component;                                                                //~1220I~
	protected Font font;                                           //~1425R~
	private ActionListener listener;                               //~1425R~
//  private int selectedpos=AdapterView.INVALID_POSITION;          //~1118I~//~1Ae7R~
    protected int selectedpos=AdapterView.INVALID_POSITION;        //~1Ae7I~
//  private int rowId=AG.listViewRowId;                            //~1220R~//~1Ae7R~
    protected int rowId=AG.listViewRowId;                          //~1A6fI~//~1Ae7I~
//  private static final int rowTextViewId=R.id.ListViewLine;      //~v1E2I~//~1Ae7R~
	protected Color bgColor=Color.white;                                      //~1112I~//~1219R~
	protected Color bgColorSelected=Color.blue.darker().darker();  //~1219R~
    private View oldItemView;                                                               //~1220I~//~v107R~
    private Container container;                                   //~v1EcI~
                                                                   //~v107I~
//*****************                                                //~1112I~
    public List()                                               //~1112I~//~1114R~
    {                                                              //~1112I~
    	init();                                                    //~1220R~
    }                                                              //~1112I~
//*****************                                                //~1Ae7I~
    public List(Container Pcontainer)                              //~v101I~//~1Ae7I~
    {                                                              //~1112I~//~1Ae7I~
    	container=Pcontainer;                                      //~v101I~//~1Ae7I~
    	init();                                                    //~1220R~//~1Ae7I~
    }                                                              //~1112I~//~1Ae7I~
//*****************                                                //~v1EcI~
    public List(Container Pcontainer,int Presid)                   //~v1EcI~
    {                                                              //~v1EcI~
    	container=Pcontainer;                                      //~v1EcI~
    	resourceid=Presid;                                         //~v1EcI~
    	init();                                                    //~v1EcI~
    }                                                              //~v1EcI~
//*****************                                                //~1220I~
    public List(int Presid)                                        //~1220I~
    {                                                              //~1220I~
    	resourceid=Presid;                                         //~1220I~
    	init();                                                    //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~//~1Ae7I~
    public List(Container Pcontainer,int Presid,int Prowresid)     //~v101I~//~1Ae7I~
    {                                                              //~1220I~//~1Ae7I~
    	container=Pcontainer;                                      //~v101I~//~1Ae7I~
	    rowId=Prowresid;                                           //~v101I~//~1Ae7I~
    	resourceid=Presid;                                         //~1220I~//~1Ae7I~
    	init();                                                    //~1220I~//~1Ae7I~
    }                                                              //~1220I~//~1Ae7I~
//*****************                                                //~1220I~
    public void init()                                   //~1220I~
    {   
    	component=new Component();//~1220I~
    	arrayData=new ArrayList<ListData>();                       //~1220R~
    	setAdapter();                                              //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1112I~
    public void add(String Pitem)                                   //~1112I~//~1220R~
    {                                                              //~1112I~
    	add(Pitem,Color.black);                                        //~1112I~//~1403R~
    }                                                              //~1112I~
//*****************                                                //~1A6fI~//~1Ae7I~
    public void add(String Pitem,String Pitem2,int Pint)           //~1A6fR~//~1Ae7I~
    {                                                              //~1A6fI~//~1Ae7I~
    	ListData ld=add(Pitem,Color.black);                        //~1A6fI~//~1Ae7I~
        ld.itemtext2=Pitem2;                                       //~1A6fR~//~1Ae7I~
        ld.itemint=Pint;                                           //~1A6fI~//~1Ae7I~
    }                                                              //~1A6fI~//~1Ae7I~
//*****************                                                //~1220I~
//  public void add(String Pitem,Color Pcolor)                     //~1220I~//~1Ae7R~
    public ListData add(String Pitem,Color Pcolor)                 //~1Ae7I~
    {                                                              //~1220I~
//  	arrayData.add(new ListData(Pitem,Pcolor));                   //~1220I~//~1Ae7R~
    	ListData ld=new ListData(Pitem,Pcolor);                    //~1A6fI~//~1Ae7I~
    	arrayData.add(ld);                                         //~1A6fI~//~1Ae7I~
        if (!(listview.isFocusable()))	//setFocusable is effective when not empty)//~1403R~
        {                                                          //~1403I~
            if (Dump.Y) Dump.println("List add selected position="+listview.getSelectedItemPosition());//~1506R~
			listview.setSelection(0);	//#### setSelection() is ignored if isInTouchMode()//~1403I~
            listview.setItemChecked(0,true); //call getView();setSelection() may move cursor//~1403I~
            if (Dump.Y) Dump.println("List add isFocusable="+listview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("List add isFocusableInTouchMode="+listview.isFocusableInTouchMode());//~1506R~
            listview.setFocusableInTouchMode(true);   //##### set desiredfocusableInTouchMode state if empty(set also focusable setting)//~1403R~
                                                      //#### setFocusableInToyuchMode() means also setFocusable()//~1403R~
            if (Dump.Y) Dump.println("List add selected position="+listview.getSelectedItemPosition());//~1506R~
            if (Dump.Y) Dump.println("List add isFocusable="+listview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("List add isFocusableInTouchMode="+listview.isFocusableInTouchMode());//~1506R~
        	listview.requestFocus();                               //~1403I~
    		listview.setItemChecked(selectedpos,true);             //~1403I~
        }                                                          //~1403I~
        return ld;                                                 //~1A6fI~//~1Ae7I~
    }                                                              //~1220I~
//*****************                                                //~1A6fI~//~1Ae7I~
    public ListData update(String Pitem,String Pitem2)             //~1A6fI~//~1Ae7I~
    {                                                              //~1A6fI~//~1Ae7I~
    	int sz=arrayData.size();                                   //~1A6fI~//~1Ae7I~
        for (int ii=0;ii<sz;ii++)                                  //~1A6fI~//~1Ae7I~
        {                                                          //~1A6fI~//~1Ae7I~
    		ListData ld=arrayData.get(ii);                         //~1A6fI~//~1Ae7I~
            if (Pitem.equals(ld.itemtext))                         //~1A6fR~//~1Ae7I~
            {                                                      //~1A6fI~//~1Ae7I~
            	ld.itemtext2=Pitem2;                               //~1A6fI~//~1Ae7I~
                return ld;                                         //~1A6fI~//~1Ae7I~
            }                                                      //~1A6fI~//~1Ae7I~
        }                                                          //~1A6fI~//~1Ae7I~
        return null;                                               //~1A6fI~//~1Ae7I~
    }                                                              //~1A6fI~//~1Ae7I~
//*****************                                                //~1AbSI~
    public void remove(int Pindex)                                 //~1AbSI~
    {                                                              //~1AbSI~
    	ArrayList<ListData> newlist=new ArrayList<ListData>();     //~1AbSI~
    	int sz=arrayData.size();                                   //~1AbSI~
        for (int ii=0;ii<sz;ii++)                                  //~1AbSI~
        {                                                          //~1AbSI~
        	if (ii==Pindex)                                        //~1AbSI~
            	continue;                                          //~1AbSI~
    		ListData ld=arrayData.get(ii);                         //~1AbSI~
	    	newlist.add(ld);                                       //~1AbSI~
        }                                                          //~1AbSI~
    	setAdapter(newlist);                                       //~1AbSI~
    }                                                              //~1AbSI~
//*****************                                                //~1112I~
    public void setFont(Font Pfont)                                //~1112I~
    {                                                              //~1112I~
    	font=Pfont;                                                //~1112I~                                //~1219I~
    	font=adjustSize(font);                                      //~v1E2I~
    }                                                              //~1112I~
//*****************                                                //~1220I~
    public void setText(String Ptext)                              //~1220I~
    {                                                              //~1220I~
        removeAll(); //removeRange is protected                    //~1220R~
    	add(Ptext);                                                //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1112I~
    public void setBackground(Color Pcolor)                        //~1112R~
    {                                                              //~1112I~
    	bgColor=Pcolor;                                              //~1112I~//~1219R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public void addActionListener(ActionListener Plistener)        //~1112I~
    {                                                              //~1112I~
    	listener=Plistener;             //@@@@ not used?           //~1324R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    private void setAdapter()            //~1112I~//~1114R~     //~1219R~//~1220R~
    {                                                              //~1112I~
      if (container!=null)                                         //+1Ae7I~
        listview=(ListView)container.findViewById(resourceid);     //+1Ae7M~
      else                                                         //+1Ae7I~
        listview=(ListView)AG.findViewById(resourceid);    //~1219I~//~1220R~//+1Ae7R~
        adapter=new ListArrayAdapter(arrayData);//~1112I~//~1114R~  //~1219R~//~1220R~
        listview.setAdapter(adapter);                                    //~1112I~//~1114R~
    	setMode(listview);                                         //~1219I~
    }                                                              //~1112I~
//*****************                                                //~1AbSI~
    private void setAdapter(ArrayList<ListData> Plistdata)         //~1AbSI~
    {                                                              //~1AbSI~
    	arrayData=Plistdata;                                       //~1AbSI~
        adapter=new ListArrayAdapter(arrayData);                   //~1AbSI~
        listview.setAdapter(adapter);                              //~1AbSI~
    }                                                              //~1AbSI~
//*****************                                                //~1112I~
    private void setMode(ListView Plv)                             //~1112I~
    {                                                              //~1112I~
        if (Dump.Y) Dump.println("List setMode isFocusable="+Plv.isFocusable());//~1506R~
        if (Dump.Y) Dump.println("List setmode isFocusableInTouchMode="+Plv.isFocusableInTouchMode());//~1506R~
        Plv.setFocusableInTouchMode(true); //fail if Array is empty//~1403R~
        if (Dump.Y) Dump.println("List setMode isFocusable="+Plv.isFocusable());//~1506R~
        if (Dump.Y) Dump.println("List setmode isFocusableInTouchMode="+Plv.isFocusableInTouchMode());//~1506R~
        Plv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);            //~1112I~
		Plv.setItemsCanFocus(true);                                 //~1115I~
        OnItemClickListener licl=new ListItemClickListener();          //~1115I~
        Plv.setOnItemClickListener(licl);                            //~1115I~
        OnItemLongClickListener lilcl=new ListItemLongClickListener();//~1307I~
        Plv.setOnItemLongClickListener(lilcl);                     //~1307I~
        OnItemSelectedListener lisl=new ListItemSelectedListener();//~1118I~
        Plv.setOnItemSelectedListener(lisl);                       //~1118I~
        if (Dump.Y) Dump.println("List setMode isFocusable="+Plv.isFocusable());//~1506R~
        if (Dump.Y) Dump.println("List setmode isFocusableInTouchMode="+Plv.isFocusableInTouchMode());//~1506R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public int getSelectedPos()                                    //~1403R~
    {                                                              //~1403I~
    	return selectedpos;                                        //~1403I~
    }                                                              //~1403I~
//*****************                                                //~v101I~//~1Ae7I~
    public int getValidSelectedPos()                               //~v101I~//~1Ae7I~
    {                                                              //~v101I~//~1Ae7I~
    	int pos=selectedpos;                                       //~v101I~//~1Ae7I~
        if (pos==AdapterView.INVALID_POSITION||pos>=arrayData.size())//~v101I~//~1Ae7I~
        	return -1;                                             //~v101I~//~1Ae7I~
        return pos;                                                //~v101I~//~1Ae7I~
    }                                                              //~v101I~//~1Ae7I~
    public String getSelectedItem()                                //~1112I~
    {                                                              //~1112I~
    	int pos;                                                   //~1118R~
    	pos=listview.getCheckedItemPosition();                     //~1118I~
        if (Dump.Y) Dump.println("Listview getCheckedItem pos="+pos);//~1506R~
    	pos=listview.getSelectedItemPosition();	//trackball selection//~1115I~//~1118R~
        if (Dump.Y) Dump.println("Listview getSelectedItem pos="+pos);//~1506R~
        if (Dump.Y) Dump.println("selected item="+listview.getSelectedItem());//~1506R~
        pos=selectedpos;                                            //~1118I~
        if (Dump.Y) Dump.println("Listview selectedpos="+pos);     //~1506R~
        if (pos==AdapterView.INVALID_POSITION||pos>=arrayData.size())                       //~1114I~//~1118R~//~1220R~
        	return "";                                             //~1114I~
//      String item=adapter.getItem(pos);                          //~1118R~
        String item=arrayData.get(pos).itemtext;                             //~1118I~//~1220R~
        if (Dump.Y) Dump.println("Listview selectedpos="+item);    //~1506R~
        return item;                                               //~1118R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public int getItemCount()                                      //~1112I~
    {                                                              //~1112I~
        return arrayData.size();                                  //~1112I~//~1220R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public void select(int Ppos)                                 //~1112I~
    {                                                              //~1112I~
    	selectedpos=Ppos;                                          //~1118M~
        if (Dump.Y) Dump.println("Listview select(setSlectedItem) req="+Ppos+",slected="+listview.getSelectedItemPosition());//~1506R~
        if (Dump.Y) Dump.println("Listview select(setItemChecked) pos="+listview.getCheckedItemPosition());//~1506R~
        listview.requestFocus();                                   //~1118I~
//    	listview.setItemChecked(Ppos,true);                        //~1118I~//~1403I~//~v105R~
        setItemChecked(Ppos,true);                                 //~v105I~
        if (Dump.Y) Dump.println("Listview after requestfocus id="+Integer.toString(listview.getId(),16)+",isfocused="+listview.isFocused());//~1118I~//~1506R~
    }                                                              //~1112I~
//*****************                                                //~v105I~
    public void setItemChecked(int Ppos,boolean Pstate)            //~v105I~
    {                                                              //~v105I~
        component.setItemChecked(listview,Ppos,Pstate);            //~v105I~
    }                                                              //~v105I~
//*****************                                                //~1215I~
    public void removeAll()	//for messageFilter                    //~1215I~
    {                                                              //~1215I~
        arrayData.clear();  //removeRange is protected             //~1220I~
		selectedpos=AdapterView.INVALID_POSITION;                  //~1411I~
    	if (AG.isMainThread())                                     //~1317I~
			adapter.notifyDataSetChanged(); //delete "deleted entry" from list shown//~1317I~
    }                                                              //~1215I~
//*****************                                                //~1220I~
    public void showBottom()                                       //~1220I~
    {                                                              //~1220I~
        component.showList(listview,arrayData.size()-1);             //~1221I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void showList()                                         //~1220I~//~1221R~
    {                                                              //~1220I~
        component.showList(listview,-1/*keep currentpos*/);          //~1221I~
    }                                                              //~1220I~
//**********************************************************************//~1111I~
//*ArrayAdapter class                                              //~1111I~
//**********************************************************************//~1111I~
    class ListArrayAdapter extends ArrayAdapter<ListData>           //~1111I~//~1112R~//~1114R~
    {                                                              //~1111I~
        public ListArrayAdapter(ArrayList<ListData> ParrayData)//~1111I~//~1112R~//~1114R~//~1219R~
        {                                                          //~1111I~
            super(AG.context,rowId,ParrayData);         //~1111I~//~1211R~//~1219R~
        }                                                          //~1111I~
        @Override                                                  //~1111I~
        public View getView(int Ppos, View Pview,ViewGroup Pparent)//~1111I~
        {                                                          //~1111I~
	        TextView tv;                                           //~1115I~
                                         //~1219I~
        //*******************                                      //~1115I~
            if (Dump.Y) Dump.println("List:ListArrayAdapter getview Ppos="+Ppos+"CheckedItemPos="+((ListView)Pparent).getCheckedItemPosition());//~1506R~//~v107R~
            View customView=getViewCustom(Ppos,Pview,Pparent);     //~1A6fI~//~1Ae7I~
            if (customView!=null) 	//overidden by ListCustom      //~1A6fI~//~1Ae7I~
            	return customView;                                 //~1A6fI~//~1Ae7I~
            tv=(TextView)super.getView(Ppos,Pview,Pparent);
//          if (Dump.Y) Dump.println("ListAdapter getview Pview==null");//~1115M~//~1219R~//~1506R~//~v107R~
            if (font!=null)                                        //~1111I~//~1115M~//~1219R~//~1220R~
                font.setFont(tv);
            ListData ld=arrayData.get(Ppos);//~1111I~//~1115M~//~1219R~//~1220R~
            tv.setText(ld.itemtext);
            if (Ppos==selectedpos)                             //~1219I~//~1220R~
            {                                                  //~1219I~//~1220R~
                tv.setBackgroundColor(bgColorSelected.getRGB());//~1219R~//~1220R~
                tv.setTextColor(bgColor.getRGB());            //~1219R~//~1220R~
            }                                                  //~1219I~//~1220R~
            else                                               //~1219I~//~1220R~
            {                                                  //~1219I~//~1220R~
                tv.setBackgroundColor(bgColor.getRGB());       //~1219I~//~1220R~
                tv.setTextColor(ld.itemcolor.getRGB());           //~1219I~//~1220R~
            }                                                  //~1219I~//~1220R~
			getViewAdjust(Ppos,tv,Pparent,ld,selectedpos);         //~1A6fR~//~1Ae7I~
            return tv;                                             //~1111I~
        }                                                          //~1111I~
    }//inner class                                                 //~1111I~
//**********************************************************************//~1115I~
//*itemclicklistener                                               //~1115I~
//**********************************************************************//~1115I~
    class ListItemClickListener implements OnItemClickListener     //~1115I~
    {                                                              //~1115I~
    	@Override                                                  //~1115I~
        public void onItemClick(AdapterView<?> Plistview,View Ptextview,int Ppos,long Pid)//~1115R~
        {                                                              //~v@@@I~//~1115I~
          try                                                      //~v107I~
          {                                                        //~v107I~
            if (Dump.Y) Dump.println("List OnItemClick pos="+Ppos);                //~v@@@R~//~1506R~
                                //~1115I~
            if (Dump.Y) Dump.println("Listview OnItemClick getCheckedItemPos="+Plistview.getSelectedItemPosition());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusable="+Plistview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusableInTouchMode="+Plistview.isFocusableInTouchMode());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick textview isInTouchMode="+Ptextview.isInTouchMode());//~1506R~
            Plistview.requestFocusFromTouch();                     //~1118R~
//          listview.setItemChecked(Ppos,true); //call getView();setSelection() may move cursor//~v105I~//~v107R~
			((BaseAdapter)Plistview.getAdapter()).notifyDataSetChanged(); //invalidate is not effective to call getView()//~v107I~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusable="+Plistview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusableInTouchMode="+Plistview.isFocusableInTouchMode());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick textview isInTouchMode="+Ptextview.isInTouchMode());//~1506R~
//*how avoid listview scroll by setSelectionAfterHeader() after setSelection()?//~1118R~
            if (container!=null)                                   //~v1EcI~
            {                                                      //~v1EcI~
            	if (container.listInterface!=null)                 //~v1EcR~
	            	container.listInterface.onListItemClicked(Ppos,selectedpos);//~v1EcR~
            }                                                      //~v1EcI~
          }                                                        //~v107I~
          catch(Exception e)                                       //~v107I~
          {                                                        //~v107I~
          	Dump.println(e,"List:onItemClick");                    //~v107I~
          }                                                        //~v107I~
            selectedpos=Ppos;                                      //~1118I~
        }                                                          //~1115I~

    }//inner class                                                 //~1115I~
//**********************************************************************//~1307I~
//*itemclicklistener  LONG                                         //~1307R~
//**********************************************************************//~1307I~
    class ListItemLongClickListener implements OnItemLongClickListener//~1307I~
    {                                                              //~1307I~
    	@Override                                                  //~1307I~
        public boolean onItemLongClick(AdapterView<?> Plistview,View Ptextview,int Ppos,long Pid)//~1307I~
        {                                                          //~1307I~
            if (Dump.Y) Dump.println("List OnItemClick pos="+Ppos);//~1506R~
                                                                   //~1307I~
            if (Dump.Y) Dump.println("Listview OnItemLongClick getCheckedItemPos="+Plistview.getSelectedItemPosition());//~1506R~
            Plistview.requestFocusFromTouch();                     //~1307I~
            if (Dump.Y) Dump.println("Listview OnItemLongClick listview requestfocusfromtouch="+Plistview.requestFocusFromTouch());//~1506R~
//          Plistview.setSelection(Ppos);                          //~1B41R~
//          selectedpos=Ppos;   //setSelction() cause scroll       //~1B41R~
            Plistview.setSelection(Ppos);                          //~1B41I~
//          Ptextview.invalidate();                                //~1307R~
			((BaseAdapter) Plistview.getAdapter()).notifyDataSetChanged(); //invalidate is not effective to call getView()//~1307I~
            return false;	//continue to ContextMenu processing   //~1307I~
        }                                                          //~1307I~
                                                                   //~1307I~
    }//inner class                                                 //~1307I~
//**********************************************************************//~1118I~
//*itemcselectedlistener  for keyboard up/down                     //~1403R~
//**********************************************************************//~1118I~
    class ListItemSelectedListener implements OnItemSelectedListener//~1118I~
    {                                                              //~1118I~
    	@Override                                                  //~1118I~
        public void onItemSelected(AdapterView<?> Plistview,View Ptextview,int Ppos,long Pid)//~1118I~
        {                                                          //~1118I~
            if (Dump.Y) Dump.println("List OnItemSelected pos="+Ppos);//~1506R~
            selectedpos=Ppos;                                      //~1118I~
            listview.setItemChecked(Ppos,true); //call getView();setSelection() may move cursor//~1403R~
        }                                                          //~1118I~
    	@Override
    	public void onNothingSelected(AdapterView<?> arg0) {
    		if (Dump.Y) Dump.println("List OnItemSelected Nothing");//~1506R~
    	}
    }//inner class                                                 //~1118I~
//**********************************************************************//~1115I~
//*itemclicklistener                                               //~1115I~
//**********************************************************************//~1115I~
    class ListTouchListener implements OnTouchListener             //~1115I~
    {                                                              //~1115I~
    	@Override                                                  //~1115I~
        public boolean onTouch(View view,MotionEvent event)        //~1115I~
        {                                                          //~1115I~
            if (Dump.Y) Dump.println("List OnTouch");              //~1506R~
            return false;                                              //~@@@@I~//~1115I~
        }                                                              //~0914I~//~1115I~
    }//ListTouchListener                                           //~1115I~
//**********************************************************************//~v1E2I~
    private Font adjustSize(Font Psetfont)                         //~v1E2R~
    {                                                              //~v1E2I~
	    int sp=getTextSizeParameter();                             //~v1E2I~
        Font newfont=Psetfont.changeSize(sp);              //~v1E2R~
        return newfont;
    }                                                              //~v1E2I~
    private int getTextSizeParameter()                             //~v1E2I~
    {                                                              //~v1E2I~
        TextView tv=(TextView)AG.inflater.inflate(rowId,null);     //~v1E2R~
        float px=tv.getTextSize();                                   //~v1E2I~
        float sd=AG.resource.getDisplayMetrics().scaledDensity;    //~v1E2I~
        int sp=(int)(px/sd);                                        //~v1E2I~
        if (Dump.Y) Dump.println("List:getTxetSizeParameter px="+px+",sd="+sd+",sp="+sp);//~v1E2I~
        return sp;                                                 //~v1E2I~
    }                                                              //~v1E2I~
//**********************************************************************//~1A6fI~//~1Ae7I~
//*overridden by ListCustom                                        //~1A6fI~//~1Ae7I~
//**********************************************************************//~1A6fI~//~1Ae7I~
	protected View getViewCustom(int Ppos, View Pview,ViewGroup Pparent)//~1A6fI~//~1Ae7I~
    {                                                              //~1A6fI~//~1Ae7I~
    	return null;                                               //~1A6fI~//~1Ae7I~
    }                                                              //~1A6fI~//~1Ae7I~
//**********************************************************************//~1A6fI~//~1Ae7I~
	protected void getViewAdjust(int Ppos, TextView Pview, ViewGroup Pparent,ListData Plistdata,int Pselectedpos)//~1A6fR~//~1Ae7I~
	{                                                              //~1A6fI~//~1Ae7I~
	}                                                              //~1Ae7I~
}//class                                                           //~1109I~
