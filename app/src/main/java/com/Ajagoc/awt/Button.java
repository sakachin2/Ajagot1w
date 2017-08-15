//*CID://+1AfbR~:                             update#=   28;       //+1AfbR~
//*************************************************************************//~v106I~
//1Afb 2016/07/11 (Bug)EditConnection/EditPartner dialog button label is not japanese//+1AfbI~
//1Ae5 2015/07/25 Bluetooth dialog(from not menu but button on partner tab)//~1Ae5I~
//1A8t 2015/04/15 add help to top panel                            //~1A8tI~
//v1Eb 2014/12/11 FileDialog:add function to reset filter          //~v1E9I~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9R~
//1B1g 130511 Button on Help Frame for modified Help text for Ajagoc//~1B1gI~
//1087:121217 accept long click for icon button                    //~v108I~
//1052:121112 Iconbar2 button sensibility;RadioButton could not be scaled.//~v106I~
//            change to Button(no need to be RadioButton, status is controled by GoFrame)//~v106I~
//*************************************************************************//~v106I~
package com.Ajagoc.awt;

import com.Ajagoc.AG;
import com.Ajagoc.R;
import com.Ajagoc.rene.gui.IconBar;                                //~v108I~


import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
//import android.widget.ToggleButton;                              //~v106R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.gui.ActionTranslator;

public class Button extends Component                              //~1217R~
{                                                                  //~1217R~
	public android.widget.Button androidButton=null;                             //~1217I~//~1321R~
                                                                   //~1217I~
    private String name;                                           //~1425R~
    private int resId;                                             //~v1E9R~
    private View parentLayoutView;                                 //~1A8tI~
//  private String label;                                          //~v1E9R~
    protected String label;   //for ButtonActon                    //~v1E9R~
    private ActionTranslator AT;                                   //~1425R~
    private	static final int[] toggleIconId={                          //~v105I~//~v106I~
        	R.id.mark,                                             //~v105I~//~v106M~
        	R.id.square,                                           //~v105I~//~v106M~
        	R.id.triangle,                                         //~v105I~//~v106M~
        	R.id.circle,                                           //~v105I~//~v106M~
        	R.id.letter,                                           //~v105I~//~v106M~
        	R.id.text,                                             //~v105I~//~v106M~
        	R.id.black,                                            //~v105I~//~v106M~
        	R.id.white,                                            //~v105I~//~v106M~
        	R.id.setblack,                                         //~v105I~//~v106M~
        	R.id.setwhite,                                         //~v105I~//~v106M~
        	R.id.delete                                            //~v105I~//~v106M~
        };                                                          //~v105I~//~v106I~
    private	static final int[] drawableOn={                            //~v105I~//~v106I~
        	R.drawable.mark_on,                                       //~v105I~//~v106R~
        	R.drawable.square_on,                                     //~v105I~//~v106R~
        	R.drawable.triangle_on,                                   //~v105I~//~v106R~
        	R.drawable.circle_on,                                     //~v105I~//~v106R~
        	R.drawable.letter_on,                                     //~v105I~//~v106R~
        	R.drawable.text_on,                                       //~v105I~//~v106R~
        	R.drawable.black_on,                                      //~v105I~//~v106R~
        	R.drawable.white_on,                                      //~v105I~//~v106R~
        	R.drawable.setblack_on,                                   //~v105I~//~v106R~
        	R.drawable.setwhite_on,                                   //~v105I~//~v106R~
        	R.drawable.delete_on                                      //~v105I~//~v106R~
        };                                                          //~v105I~//~v106I~
    	static final int[] drawableOff={                           //~v105I~//~v106I~
        	R.drawable.mark_enable,                                      //~v105I~//~v106M~
        	R.drawable.square_enable,                                    //~v105I~//~v106M~
        	R.drawable.triangle_enable,                                  //~v105I~//~v106M~
        	R.drawable.circle_enable,                                    //~v105I~//~v106M~
        	R.drawable.letter_enable,                                    //~v105I~//~v106M~
        	R.drawable.text_enable,                                      //~v105I~//~v106M~
        	R.drawable.black_enable,                                     //~v105I~//~v106M~
        	R.drawable.white_enable,                                     //~v105I~//~v106M~
        	R.drawable.setblack_enable,                                  //~v105I~//~v106M~
        	R.drawable.setwhite_enable,                                  //~v105I~//~v106M~
        	R.drawable.delete_enable                                     //~v105I~//~v106M~
        };                                                          //~v105I~//~v106I~
//***************                                                  //~1114I~
    public Button(String Plabel)                                   //~1217R~
    {                                                            //~1126R~//~1217R~
    	label=Plabel;                                              //~1217I~
//*CardPanel dose not call addActionListener(ActionTranslator) but AddActionListener(ActionListener)//~1331I~
        if (label.equals(Global.resourceString("Server_Connections"))) //~1217I~
        {                                                          //~1331I~
        	androidButton=AG.ajagov.getTabButton(0);	//clicklistener by TabHost//~1217I~//~1306R~
            setText((TextView)androidButton,label);                //~1331I~
        }                                                          //~1331I~
        else                                                       //~1217I~
        if (label.equals(Global.resourceString("Partner_Connections")))//~1217I~
        {                                                          //~1331I~
        	androidButton=AG.ajagov.getTabButton(1);                      //~1217I~//~1306R~
            setText((TextView)androidButton,label);                //~1331I~
        }                                                          //~1331I~
    }                                                              //~1217I~
//*********************************                                //~v1E9R~
//**find view by layoutView and resid                              //~v1E9R~
//*********************************                                //~v1E9R~
    public Button(Container Pcontainer,int Ptextid,int Presid)     //~v1E9R~
    {                                                              //~v1E9R~
    	super(Pcontainer);	//component                            //~v1E9R~
        if (Ptextid==0)                                            //~v1E9R~
	        label=null;                                            //~v1E9R~
        else                                                       //~v1E9R~
	        label=AG.resource.getString(Ptextid);	//doAction string//~v1E9R~
    	resId=Presid;                                              //~v1E9R~
    }                                                              //~v1E9R~
//*********************************                                //~1A8tI~
//**find view by layoutView and resid                              //~1A8tI~
//*********************************                                //~1A8tI~
    public Button(Container Pcontainer,View Pview,int Ptextid,int Presid)//~1A8tI~
    {                                                              //~1A8tI~
	    this(Pcontainer,Ptextid,Presid);                           //~1A8tI~
    	parentLayoutView=Pview;                                    //~1A8tI~
    }                                                              //~1A8tI~
//**************                                                   //~1217I~
    public void addActionListener(ActionTranslator Pat)                 //~1217I~
    {                                                              //~1217I~
    	AT=Pat;                                                    //~1217I~
    	name=Pat.Name;                                             //~1217I~
        init();                                                    //~1217I~
    }                                                              //~1217I~
//**************                                                   //~1217I~
    public void addActionListener(ActionListener Pal)	//from gui.CardPanel,changed to TabLayout//~1217I~
    {                                                              //~1217I~
    }
    public void addKeyListener(KeyListener Pkl)	//from gui.CardPanel,changed to TabLayout//~1217I~
    {                                                              //~1217I~
    }                                                                                  //~1217I~
//**************                                                   //~1217I~
    private void init()                                            //~1217I~
    {                                                              //~1217I~
      if (resId!=0)                                                //~v1E9R~
      {                                                            //~v1E9R~
        if (parentLayoutView!=null)                                //~1A8tI~
        	androidButton=(android.widget.Button)AG.findViewById(parentLayoutView,resId);//~1A8tI~
        else                                                       //~1A8tI~
        if (parentContainer!=null && parentContainer.containerLayoutView!=null)//~v1E9R~
        	androidButton=(android.widget.Button)parentContainer.findViewById(resId);//~v1E9R~
        else                                                       //~v1E9R~
        	androidButton=(android.widget.Button)AG.findViewById(resId);//~v1E9R~
      }                                                            //~v1E9R~
      else                                                         //~v1E9R~
    	androidButton=(android.widget.Button)AG.findViewByName(name);	//search by Action name not by label                                     //~1217I~//~1306R~//~1331R~
        if (androidButton==null)   //specified label(=name) only   //~1331R~
        {                                                          //~1317I~
        	androidButton=(android.widget.Button)AG.findButtonView();//~1306R~
            if (( AG.currentLayoutId==AG.dialogId_EditConnection   //~1318R~
                ||AG.currentLayoutId==AG.dialogId_EditPartner      //~1318I~
                )                                                  //~1318I~
            &&  androidButton.getId()==R.id.Button1                //~1317I~
            &&  name.equals(Global.resourceString("Add"))   //AddServer:(Set),Add,cancel//~1317I~
            )                                                      //~1317I~
            {                                                      //~1317I~
        		androidButton.setVisibility(View.GONE);	//disable Set  //~1317I~
        		androidButton.setClickable(false);  //             //~1317I~
	        	androidButton=(android.widget.Button)AG.findButtonView(); //1st visble is Add//~1317I~
            }                                                      //~1317I~
        }                                                          //~1317I~
        componentView=androidButton;                               //~1405I~
        if (label!=null)                                           //~1331I~
        {                                                          //~1401I~
//        	if (AG.getCurrentDialog()==null && !AG.getCurrentFrame().isBoardFrame)	//not icon button//~1401I~//+1AfbR~
          	if (AG.getCurrentDialog()!=null || !AG.getCurrentFrame().isBoardFrame)	//not icon button(Dialog or !Bord panel)//+1AfbI~
            	setText((TextView)androidButton,label);                //~1331I~//~1401R~
        }                                                          //~1401I~
        else                                                       //~v1E9R~
        	label=androidButton.getText().toString();   //for ActionString//~v1E9R~
      if (name!=null)                                              //~v1E9R~
        if (name.equals("Help")		//for rene.CloseDialog,rene.dialog.GetParameter,rene.dialog.Warning//~1404R~
        ||  name.equals(Global.resourceString("Help"))	//for GetParameter(GetPassword)//~1404I~
        )	                                            //Help button is optional//~1404I~
        	androidButton.setVisibility(View.VISIBLE);	//reset visibility="gone" on xml if accessed//~1404R~
        androidButton.setOnClickListener(                                 //~1217I~//~1306R~
			 new OnClickListener()                                 //~1217I~
                    {                                      //~1114I~//~1217I~
                        @Override                          //~1114I~//~1217I~
                        public void onClick(View Pv)            //~1114I~//~1217I~
                        {                                  //~1114I~//~1217I~
                            onClickButtonAction(Pv);       //~1114I~//~1217I~
                        }                                  //~1114I~//~1217I~
                    }                                      //~1114I~//~1217I~
                                 );                                       //~1114I~//~1217I~
    }                                                              //~1217I~
//*************                                                    //~1114M~
	public void onClickButtonAction(View Pview)                    //~1114M~
    {                                                              //~1114M~
    	if (Dump.Y) Dump.println("onClickButtonAction button="+name);//~1506R~
		if (AT!=null)                                              //~1114M~
        {                                                          //~1114I~
//          android.widget.Button b=null;                          //~1B1gR~
//          b.setText("AA");    //@@@@testACRA                     //~1B1gR~
            try                                                    //~1B1gI~
            {                                                      //~1B1gI~
        	ActionEvent.actionPerformed(AT,this);                             //~1114R~//~1124R~//~1524R~
            }                                                      //~1B1gI~
            catch(Exception e)                                     //~1B1gI~
            {                                                      //~1B1gI~
                Dump.println(e,"OnClickButton name="+name);        //~1B1gI~
            }                                                      //~1B1gI~
        }                                                          //~1114I~
    }                                                              //~1114M~
//*************************************                            //~v108R~
//*from Ajagoc/rene/gui/IconBar                                    //~v108I~
//*************************************                            //~v108I~
    public void addLongClickListener()                             //~v108I~
    {                                                              //~v108I~
        androidButton.setOnLongClickListener(                      //~v108M~
			 new View.OnLongClickListener()                        //~v108M~
                    {                                              //~v108M~
                        @Override                                  //~v108M~
                        public boolean onLongClick(View Pv)        //~v108M~
                        {                                          //~v108M~
                            return onLongClickButtonAction(Pv);    //~v108M~
                        }                                          //~v108M~
                    }                                              //~v108M~
                                 );                                //~v108M~
    }                                                              //~v108I~
//*************                                                    //~v108I~
	public boolean onLongClickButtonAction(View Pview)                //~v108I~
    {                                                              //~v108I~
    	boolean rc=false;                                                //~v108I~
    	if (Dump.Y) Dump.println("onLongClickButtonAction button="+name);//~v108I~
        if (AT!=null)                                              //~v108R~
        {                                                          //~v108I~
        	onClickButtonAction(Pview);                            //~v108I~
            rc=true;                                               //~v108I~
        }                                                          //~v108I~
        return rc;                                                 //~v108I~
    }                                                              //~v108I~
//************                                                     //~1126I~
    public void setFont(Font Pfont)                              //~1126I~
    {                                                              //~1126I~
    	Pfont.setFont(androidButton);                               //~1126I~//~1217R~//~1306R~
    }                                                              //~1126I~
//************                                                     //~v106I~
    public static boolean getToggleButtonDrawableId(android.widget.Button Pview,int [] Pids)                        //~v105I~//~v106I~
    {                                                              //~v105I~//~v106I~
    	boolean rc=false;                                          //~v106R~
    //****************                                             //~v106I~
    	int id=Pview.getId();                                      //~v105I~//~v106I~
        for (int ii=0;ii<toggleIconId.length;ii++)                 //~v105I~//~v106I~
        	if (id==toggleIconId[ii])                              //~v105I~//~v106I~
            {                                                      //~v105I~//~v106I~
            	Pids[0]=drawableOn[ii];                          //~v105I~//~v106I~
            	Pids[1]=drawableOff[ii];                        //~v105I~//~v106I~
                rc=true;                                           //~v106I~
                break;                                             //~v105I~//~v106I~
            }                                                      //~v105I~//~v106I~
        return rc;                                                 //~v106I~
    }                                                              //~v105I~//~v106I~
//**************************************************************************//~1B1gI~
    public void setVisibility(int Pvisibility)                     //~1B1gI~
    {                                                              //~1B1gI~
    	setVisibility(androidButton,Pvisibility);	//Component    //~1B1gI~
    }                                                              //~1B1gI~
    public void setText(String Ptext)                              //~1B1gI~
    {                                                              //~1B1gI~
    	setText((TextView)androidButton,Ptext);                    //~1B1gI~
    }                                                              //~1B1gI~
    public void setBackground(Drawable Pdrawable)                  //~v1EbR~
    {                                                              //~v1EbR~
    	setBackground(androidButton,Pdrawable);                    //~v1EbR~
    }                                                              //~v1EbR~
    //****************************************************************************//~@@@@I~//~1Ae5I~
    public void setEnabled(boolean Penable)                        //~@@@@I~//~1Ae5I~
    {                                                              //~@@@@I~//~1Ae5I~
        super.setEnabled(this,Penable);	//by component         //~@@@@I~//~1Ae5I~
    }                                                              //~@@@@I~//~1Ae5I~
}//class                                                           //~1121R~
