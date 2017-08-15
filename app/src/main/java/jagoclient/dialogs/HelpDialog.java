//*CID://+1Ad2R~:                             update#=   11;       //+1Ad2R~
//**************************************************************************//~1B1gI~
//2015/07/24 //1Ad2 2015/07/17 HelpDialog by helptext              //+1Ad2I~
//2015/07/24 //1Ad2 2015/07/17 HelpDialog by helptext              //+1Ad2I~
//2015/03/06 1A73 2015/02/23 apply 1A68 to Help dialog(fill screen width)//~1A73I~
//2015/03/06 1A72 2015/02/23 use smaller help dialog font size for mdpi//~1A72I~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//v1E5 2014/12/11 (Asgts)//1A4t 2014/12/06 show filename on HelpFileNotFound dialog//~v1E5I~
//v1C7 2014/08/30 no Dump.println(e,..) for helpfile not found for helpfile for update for Ajagocrve//~v1C7I~
//1B1g 130511 Button on Help Frame for modified Help text for Ajagoc//~1B1gI~
//**************************************************************************//~1B1gI~
package jagoclient.dialogs;

//import java.awt.*;                                               //~1418R~
import java.io.*;

import android.view.View;

import com.Ajagoc.AG;
import com.Ajagoc.R;
import com.Ajagoc.awt.Font;
import com.Ajagoc.awt.Frame;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.rene.viewer.SystemViewer;                        //~1327I~
import com.Ajagoc.rene.viewer.Viewer;                              //~1327I~

import jagoclient.gui.*;
import jagoclient.Global;

//import rene.viewer.*;                                            //~1327R~

/**
The same as Help.java but as a dialog. This is for giving help in
modal dialogs. 
@see jagoclient.dialogs.Help
*/

public class HelpDialog extends CloseDialog
{	Viewer V; // The viewer
	Frame F;
    private final static String SUFFIX_AJAGOC="Ajagoc";            //~1B1gI~
    private boolean updateForAjagoc;                               //~1B1gI~
    private String actionAjagoc,subjectAjagoc="";                  //~1B1gI~
	/**
	Display the help from subject.txt,Global.url()/subject.txt
	or from the ressource /subject.txt.
	*/
    public HelpDialog (Frame f,int Ptitleid,String subject)        //~v1E9I~
    {                                                              //~v1E9I~
    	this(f,subject);                                           //~v1E9I~
        String title=AG.resource.getString(Ptitleid);                     //~v1E9I~
        setTitle(title);                                           //~v1E9I~
    }                                                              //~v1E9I~
	public HelpDialog (Frame f, String subject)
	{	super(f,Global.resourceString("Help"),true);
        setWindowSize(90/*W:90%*/,0/*H=wrap content*/,!AG.layoutMdpi/*for landscape,use ScrHeight for width limit if not mdpi*/);//~1A73I~
		F=f;
		V=Global.getParameter("systemviewer",false)?new SystemViewer():new Viewer();
      if (AG.layoutMdpi)                                           //~1A72I~
      {                                                            //~1A72I~
        int fontsz=12;                                             //~1A72I~
        Font helpfont=Global.createfont("monospaced","Monospaced",fontsz);//~1A72I~
		V.setFont(helpfont);                                       //~1A72I~
      }                                                            //~1A72I~
      else                                                         //~1A72I~
		V.setFont(Global.Monospaced);
		V.setBackground(Global.gray);
        updateForAjagoc=isExistUpdateForAjagoc(subject);           //~1B1gI~
        actionAjagoc=AG.resource.getString(R.string.HelpForAjagoc);//~1B1gI~
        String fnms="";                                            //~v1E5I~
        String fnm;                                                //~v1E5I~
		try
		{	BufferedReader in;
			String s;
			AG.tryHelpFileOpen=true;                               //~1A41R~//+1Ad2I~
    		fnm="jagoclient/helptexts/"+subject+Global.resourceString("HELP_SUFFIX")+".txt";//~v1E5I~
            fnms+="\n"+fnm;                                        //~v1E5I~
			try
//@@@@  	{	in=Global.getStream(                               //~1327R~
        	{	in=Global.getEncodedStream(        //like as Help.java;required to display locale language//~1327I~
//  				"jagoclient/helptexts/"+subject+Global.resourceString("HELP_SUFFIX")+".txt");//~v1E5R~
    				fnm);                                          //~v1E5I~
				s=in.readLine();
			}
			catch (Exception e)
//  		{	try                                                //~v1E5R~
    		{                                                      //~v1E5I~
    			fnm=subject+Global.resourceString("HELP_SUFFIX")+".txt";//~v1E5I~
	            fnms+="\n"+fnm;                                    //~v1E5I~
    		 	try                                                //~v1E5I~
//@@@@ 			{	in=Global.getStream(                           //~1327R~
    			{	in=Global.getEncodedStream(                    //~1327I~
//  					subject+Global.resourceString("HELP_SUFFIX")+".txt");//~v1E5R~
    					fnm);                                      //~v1E5I~
					s=in.readLine();
				}
				catch (Exception ex)
//@@@@  		{	in=Global.getStream("jagoclient/helptexts/"+subject+".txt");//~1327R~
//  			{	in=Global.getEncodedStream("jagoclient/helptexts/"+subject+".txt");//~v1E5R~
    			{                                                  //~v1E5I~
					AG.tryHelpFileOpen=false;                      //~1A41R~//+1Ad2I~
    				fnm="jagoclient/helptexts/"+subject+".txt";    //~v1E5I~
            		fnms+="\n"+fnm;                                //~v1E5I~
    			 	in=Global.getEncodedStream(fnm);              //~v1E5I~
					s=in.readLine();
				}
			}
			while (s!=null)
			{	V.appendLine(s);
				s=in.readLine();
			}
			in.close();
		}
		catch (Exception e)
		{	new Message(Global.frame(),
//  			Global.resourceString("Could_not_find_the_help_file_"));//~v1E5R~
    			Global.resourceString("Could_not_find_the_help_file_")+fnms);//~v1E5I~
			doclose();
			return;
		}
		AG.tryHelpFileOpen=false;                                  //~1A41R~//+1Ad2I~
		display();
	}
	
	public void doclose ()
	{	setVisible(false); dispose();
	}
	
	void display ()
	{	Global.setwindow(this,"help",500,400);
		add("Center",V);
		Panel p=new MyPanel();
//  	p.add(new ButtonAction(this,Global.resourceString("Close")));//~1B1gR~
        String closelabel=Global.resourceString("Close");          //~1B1gI~
        ButtonAction closebutton=new ButtonAction(this,closelabel);    //~1B1gI~
    	p.add(closebutton);                                        //~1B1gI~
		closebutton.setText(closelabel); 	//Button dose not set if AG.currentDialog!=null//~1B1gI~
        if (updateForAjagoc)                                       //~1B1gI~
        {                                                          //~1B1gI~
        	ButtonAction morehelp=new ButtonAction(this,actionAjagoc);//~1B1gI~
			morehelp.setVisibility(View.VISIBLE);                  //~1B1gI~
			morehelp.setText(actionAjagoc); 	//Button dose not set if AG.currentDialog!=null//~1B1gI~
        }                                                          //~1B1gI~
		add("South",new Panel3D(p));
		setVisible(true);
	}
	public void doAction (String o)
	{	Global.notewindow(this,"help");
    	if (o.equals(actionAjagoc))                                //~1B1gI~
        {                                                          //~1B1gI~
        	new HelpDialog(new Frame(),subjectAjagoc);             //~1B1gI~
        }                                                          //~1B1gI~
        else                                                       //~1B1gI~
		super.doAction(o);
	}
    //***********************************************************  //~1B1gI~
	private boolean isExistUpdateForAjagoc(String subject)         //~1B1gI~
	{                                                              //~1B1gI~
    	boolean rc=false;                                          //~1B1gI~
    	subjectAjagoc=subject+SUFFIX_AJAGOC;                       //~1B1gI~
		String fnm="jagoclient/helptexts/"+subjectAjagoc+".txt";   //~1B1gI~
		BufferedReader in;                                         //~1B1gI~
    	AG.chkUpdateHelp=true;	//do not print FileNotFound Dump(e)//~v1C7I~
		try                                                        //~1B1gI~
		{                                                          //~1B1gI~
			in=Global.getEncodedStream(fnm);                       //~1B1gI~
			in.close();                                            //~1B1gI~
            rc=true;                                               //~1B1gI~
		}                                                          //~1B1gI~
		catch (Exception e)                                        //~1B1gI~
		{                                                          //~1B1gI~
		}                                                          //~1B1gI~
    	AG.chkUpdateHelp=false;                                    //~v1C7I~
        return rc;                                                 //~1B1gI~
	}                                                              //~1B1gI~
}
