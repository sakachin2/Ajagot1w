//*CID://+v1D5R~:                             update#=    8;       //~v1D5R~
//*************************************************************************//~v107I~
//v1D5 2014/11/10 issue err when partner list name is space which could not select to delete(Asgts:1A45)//+v1D5R~
//1B0c 130429 Encoding support for partner connection              //~1B09I~
//1B09 130429 avoid ime popup initially by requestFocus            //~1B09I~
//1072:121206 partner add diaplay default is 6970,but set 6969     //~v107I~
//*************************************************************************//~v107I~
package jagoclient.partner.partner;

//import java.awt.*;

import com.Ajagoc.AG;
import com.Ajagoc.R;
import com.Ajagoc.AjagoView;                                       //~v1D5I~
import com.Ajagoc.awt.Choice;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.GridLayout;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.awt.TextField;

import jagoclient.gui.*;
import jagoclient.dialogs.*;
import jagoclient.Go;
import jagoclient.Global;

import rene.util.list.*;

public class EditPartner extends CloseDialog
{	ListClass PList;
	Partner C;
	TextField Name, Server, Port;
	TextField Encoding;                                            //~1B0cI~
	Go G;
	Choice State;
	Frame F;
	public EditPartner (CloseFrame f, ListClass plist, Partner c,
		Go go)
	{	super(f,Global.resourceString("Edit_Connection"),true);
		G=go; F=f;
		PList=plist; C=c;
		Panel p1=new MyPanel();
		p1.setLayout(new GridLayout(0,2));
		p1.add(new MyLabel(Global.resourceString("Name")));
		p1.add(Name=new FormTextField(""+C.Name));
		p1.add(new MyLabel(Global.resourceString("Server")));
		p1.add(Server=new FormTextField(C.Server));
		p1.add(new MyLabel(Global.resourceString("Port")));
		p1.add(Port=new FormTextField(""+C.Port));
		p1.add(new MyLabel(Global.resourceString("State")));
		p1.add(State=new Choice());
		p1.add(new MyLabel(Global.resourceString("Encoding")));    //~1B0cI~
		State.setFont(Global.SansSerif);
		State.add(Global.resourceString("silent"));
		State.add(Global.resourceString("private"));
		State.add(Global.resourceString("local"));
		State.add(Global.resourceString("public"));
		State.select(C.State);
		add("Center",new Panel3D(p1));
		Panel p=new MyPanel();
		p.add(new ButtonAction(this,Global.resourceString("Set")));
		p.add(new ButtonAction(this,Global.resourceString("Add")));
		p.add(new ButtonAction(this,Global.resourceString("Cancel")));
		p.add(new MyLabel(" "));
		p.add(new ButtonAction(this,Global.resourceString("Help")));
		add("South",new Panel3D(p));
		Global.setpacked(this,"editp",300,200,F);
		Encoding=new FormTextField(""+C.Encoding);                 //~1B0cI~
		validate(); show();
      if (false)                                                   //~1B09I~
		Name.requestFocus();
	}
	public EditPartner (CloseFrame f, ListClass plist,
		Go go)
	{	super(f,Global.resourceString("Edit_Connection"),true);
		G=go; F=f;
		PList=plist;
		Panel p1=new MyPanel();
		p1.setLayout(new GridLayout(0,2));
		p1.add(new MyLabel(Global.resourceString("Name")));
		p1.add(Name=new FormTextField(Global.resourceString("Name_for_list")));
		p1.add(new MyLabel(Global.resourceString("Server")));
		p1.add(Server=new FormTextField(Global.resourceString("Internet_server_name")));
		p1.add(new MyLabel(Global.resourceString("Port")));
		p1.add(Port=new FormTextField("Port (default 6970)"));
		p1.add(new MyLabel(Global.resourceString("State")));
		p1.add(State=new Choice());
		p1.add(new MyLabel(Global.resourceString("Encoding")));    //~1B0cI~
		State.setFont(Global.SansSerif);
		State.add(Global.resourceString("silent"));
		State.add(Global.resourceString("private"));
		State.add(Global.resourceString("local"));
		State.add(Global.resourceString("public"));
		State.select(1);
		add("Center",new Panel3D(p1));
		Panel p=new MyPanel();
		p.add(new ButtonAction(this,Global.resourceString("Add")));
		p.add(new ButtonAction(this,Global.resourceString("Cancel")));
		p.add(new MyLabel(" "));
		p.add(new ButtonAction(this,Global.resourceString("Help")));
		add("South",new Panel3D(p));
		Global.setpacked(this,"editp",300,200,F);
		Encoding=new FormTextField("");                      //~1B0cR~
		validate(); show();
      if (false)                                                   //~1B09I~
		Name.requestFocus();
	}
  	public void doAction (String o)
  	{	Global.notewindow(this,"editp");
  		if (Global.resourceString("Set").equals(o))
//		{	C.Name=Name.getText();                                 //~v1D5R~
  		{                                                          //~v1D5I~
  		 	String name=Name.getText();                            //~v1D5I~
            if (name.equals(""))                                   //~v1D5I~
            {                                                      //~v1D5I~
        		AjagoView.showToast(R.string.ErrNameIsSpace);      //~v1D5I~
                return;                                            //~v1D5I~
            }                                                      //~v1D5I~
  		 	C.Name=name;                                           //~v1D5I~
  			C.Server=Server.getText();
  			C.State=State.getSelectedIndex();
  			C.Encoding=Encoding.getText();                         //~1B0cI~
  			try
  			{	C.Port=Integer.parseInt(Port.getText());
  			}
  			catch (NumberFormatException ex)
  			{	C.Port=6970;
  			}
  			finally
  			{	G.updateplist();
  				setVisible(false); dispose();
  			}
  		}
  		else if (Global.resourceString("Add").equals(o))
  		{	Partner C=new Partner("[?] [?] [?]");
//			C.Name=Name.getText();                                 //~v1D5R~
            String name=Name.getText();                            //~v1D5I~
            if (name.equals(""))                                   //~v1D5I~
            {                                                      //~v1D5I~
        		AjagoView.showToast(R.string.ErrNameIsSpace);      //~v1D5I~
                return;                                            //~v1D5I~
            }                                                      //~v1D5I~
            C.Name=name;                                           //~v1D5I~
  			C.Server=Server.getText();
  			C.State=State.getSelectedIndex();
  			C.Encoding=Encoding.getText();                         //~1B0cI~
  			try
  			{	C.Port=Integer.parseInt(Port.getText());
  			}
  			catch (NumberFormatException ex)
//			{	C.Port=6969;                                       //~v107R~
  			{	C.Port=6970;                                       //~v107I~
  			}
  			finally
  			{	if (G.pfind(C.Name)!=null)
  				{	C.Name=C.Name+" DUP";
  				}
  				PList.append(new ListElement(C));
  				G.updateplist();
  				setVisible(false); dispose();
  			}
  		}
  		else if (Global.resourceString("Cancel").equals(o))
  		{	setVisible(false); dispose(); 
  		}
  		else if (Global.resourceString("Help").equals(o))
  		{	new HelpDialog(F,"confpartner"); 
  		}
		else super.doAction(o);
  	}
}

