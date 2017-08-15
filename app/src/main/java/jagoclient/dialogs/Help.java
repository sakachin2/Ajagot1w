//*CID://+v1E7R~:                             update#=   12;       //~v1E7R~
//**************************************************************************//~1B1gI~
//v1E7 2014/12/11 (Asgts)//1A4A 2014/12/09 function to show youtube//~v1E7I~
//v1C7 2014/08/30 no Dump.println(e,..) for helpfile not found for helpfile for update for Ajagocrve//~v1C7R~
//1B1g 130511 Button on Help Frame for modified Help text for Ajagoc//~1B1gI~
//**************************************************************************//~1B1gI~
package jagoclient.dialogs;

//import java.awt.*;                                               //~1115R~
import java.io.*;                                                //~1115R~

//import android.R;

import android.view.View;

import com.Ajagoc.AG;
import com.Ajagoc.AjagoUtils;
import com.Ajagoc.AjagoView;
import com.Ajagoc.R;
import com.Ajagoc.awt.Panel;
import com.Ajagoc.rene.viewer.SystemViewer;
import com.Ajagoc.rene.viewer.Viewer;
import java.net.*;                                               //~1115R~//~1326R~
//import com.Ajagoc.awt.URL;                                       //~1326I~

import jagoclient.gui.*;
import jagoclient.Dump;
import jagoclient.Global;

//import rene.viewer.*;

/**
<P>
A dialog class for displaying help texts. The help texts
are in ASCII text files with ending .txt in the root resource
directory. If the parameter HELP_SUFFIX is in the properties
file, it will appended for local language help (such as about_de.txt).
<P>
The text will either be loaded from a file, from an URL or a ressource 
using the getStream method of Global.
@see jagoclient.Global#getStream
*/

public class Help extends CloseFrame implements Runnable
{	Viewer V; // The viewer
	/**
	Display the help from subject.txt,Global.url()/subject.txt
	or from the ressource /subject.txt.
	*/
    private final static String SUFFIX_AJAGOC="Ajagoc";            //~1B1gI~
    private boolean updateForAjagoc;                               //~1B1gI~
    private String actionAjagoc,subjectAjagoc="";                     //~1B1gI~
    private String actionYouTube;                                  //~v1E7I~
    private boolean swYouTube;                                     //~v1E7I~
    private static final String HELP_YOUTUBE="Ajagoc";             //~v1E7I~
	public Help (String subject)
	{	super(Global.resourceString("Help"));
		seticon("ihelp.gif");
		V=Global.getParameter("systemviewer",false)?new SystemViewer():new Viewer();
		V.setFont(Global.Monospaced);
		V.setBackground(Global.gray);
        swYouTube=subject.equals(HELP_YOUTUBE);	                   //~v1E7I~
        updateForAjagoc=isExistUpdateForAjagoc(subject);           //~1B1gI~
        actionAjagoc=AG.resource.getString(R.string.HelpForAjagoc);         //~1B1gI~
        actionYouTube=AG.resource.getString(R.string.ButtonYouTube);//~v1E7I~
		try
		{	BufferedReader in;
			String s;
			try
			{	in=Global.getEncodedStream(
					"jagoclient/helptexts/"+subject+Global.resourceString("HELP_SUFFIX")+".txt");
				s=in.readLine();
			}
			catch (Exception e)
			{                                                      //~1326R~
            	Dump.println(e,"Help excetion1");                 //~1326I~
				try                                                //~1326I~
				{	in=Global.getEncodedStream(
						subject+Global.resourceString("HELP_SUFFIX")+".txt");
					s=in.readLine();
				}
				catch (Exception ex)
				{                                                  //~1326R~
            		Dump.println(e,"Help excetion2");             //~1326I~
					in=Global.getEncodedStream("jagoclient/helptexts/"+subject+".txt");//~1326I~
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
				Global.resourceString("Could_not_find_the_help_file_"));
			doclose();
			return;
		}
		display();
	}
	
	/**
	This constructor is used to get the about.txt file from
	our homeserver (address is hard coded into the run method).
	A thread is used to wait for connection.
	*/
	public Help ()
	{   super(Global.resourceString("Help"));
		seticon("ihelp.gif");
		V=Global.getParameter("systemviewer",false)?new SystemViewer():new Viewer();
		V.setFont(Global.Monospaced);
		V.setBackground(Global.gray);
		new Thread(this).start();
	}                                                            //~1326R~
	public void run ()
//  {	String H="";                                               //~v1E7R~
    {                                                              //~v1E7I~
		try
		{	BufferedReader in;
			in=new BufferedReader(new InputStreamReader(
			    new DataInputStream(
				new URL("http://www.rene-grothmann.de/jago/about.txt").openStream())));
			while (true)
			{	String s=in.readLine();
				if (s==null) break;
				V.appendLine(s);
			}
			in.close();
		}
		catch (Exception e)
		{                                                          //~1326R~
            Dump.println(e,"Help excetion3");                     //~1326I~
			new Message(Global.frame(),                            //~1326I~
				Global.resourceString("Could_not_find_the_help_file_"));
			doclose();
			return;
		}
		display();

	}
	void display ()
	{	Global.setwindow(this,"help",500,400);
		add("Center",V);
		Panel p=new MyPanel();
		p.add(new ButtonAction(this,Global.resourceString("Close")));
        if (updateForAjagoc)                                       //~1B1gI~
        {                                                          //~1B1gI~
        	ButtonAction morehelp=new ButtonAction(this,actionAjagoc);//~1B1gI~
			morehelp.setVisibility(View.VISIBLE);                  //~1B1gR~
        }                                                          //~1B1gI~
        if (swYouTube)                                             //~v1E7I~
        {                                                          //~v1E7I~
        	ButtonAction startYouTube=new ButtonAction(this,actionYouTube);//~v1E7I~
			startYouTube.setVisibility(View.VISIBLE);              //~v1E7I~
        }                                                          //~v1E7I~
		add("South",new Panel3D(p));
		setVisible(true);
	}
	public void doAction (String o)
	{	Global.notewindow(this,"help");
    	if (o.equals(actionAjagoc))                                //~1B1gI~
        {                                                          //~1B1gI~
        	new HelpDialog(this,subjectAjagoc);                    //~1B1gI~
        }                                                          //~1B1gI~
        else                                                       //~1B1gI~
    	if (o.equals(actionYouTube))                              //~v1E7I~
        {                                                          //~v1E7I~
        	showMovie();                                           //~v1E7R~
        }                                                          //~v1E7I~
		super.doAction(o);
	}
    //***********************************************************  //~1B1gI~
	private boolean isExistUpdateForAjagoc(String subject)         //~1B1gI~
	{                                                              //~1B1gI~
    	boolean rc=false;                                          //~1B1gI~
    	subjectAjagoc=subject+SUFFIX_AJAGOC;                       //~1B1gI~
		String fnm="jagoclient/helptexts/"+subjectAjagoc+".txt";  //~1B1gI~
		BufferedReader in;                                         //~1B1gI~
    	AG.chkUpdateHelp=true;	//do not print FileNotFound Dump(e)//~v1C7R~
		try                                                        //~1B1gI~
		{                                                          //~1B1gI~
			in=Global.getEncodedStream(fnm);                       //~1B1gI~
			in.close();                                            //~1B1gI~
            rc=true;                                               //~1B1gI~
		}                                                          //~1B1gI~
		catch (Exception e)                                        //~1B1gI~
		{                                                          //~1B1gI~
		}                                                          //~1B1gR~
    	AG.chkUpdateHelp=false;                                    //~v1C7R~
        return rc;                                                 //~1B1gI~
	}                                                              //~1B1gI~
	//***************************************                      //~v1E7I~
//  private static final String URL_MOVIELIST_J="https://www.youtube.com/watch?v=nUvaTV7lHGA&list=PL2clNB_BpXSXgLu7dGC8EtGH9xrGLn058";//~v1E7R~
//  private static final String URL_MOVIELIST_E="https://www.youtube.com/watch?v=q5PgAf_rji0&list=PL2clNB_BpXSXl46OnjdelaBe78XbFXVWR";//~v1E7R~
    private static final String URL_MOVIELIST_J="https://www.youtube.com/playlist?list=PL2clNB_BpXSXgLu7dGC8EtGH9xrGLn058";//+v1E7R~
    private static final String URL_MOVIELIST_E="https://www.youtube.com/playlist?list=PL2clNB_BpXSXl46OnjdelaBe78XbFXVWR";//+v1E7R~
	public void showMovie()                                        //~v1E7I~
	{                                                              //~v1E7I~
        String url="";                                             //~v1E7I~
      	try                                                        //~v1E7I~
      	{                                                          //~v1E7I~
			boolean jp=Global.resourceString("HELP_SUFFIX").equals("_ja");//~v1E7R~
        	if (jp)                                                //~v1E7R~
        		url=URL_MOVIELIST_J;                               //~v1E7I~
            else                                                   //~v1E7I~
        		url=URL_MOVIELIST_E;                               //~v1E7I~
        	AjagoUtils.showWebSite(url);                                //~v1E7I~
      	}                                                          //~v1E7I~
      	catch (Exception e)                                        //~v1E7I~
      	{                                                          //~v1E7I~
      		Dump.println(e,"HelpDialog:showMovie Exception url="+url);//~v1E7I~
       		AjagoView.showToast(R.string.Exception);                   //~v1E7I~
	    }                                                          //~v1E7I~
    }                                                              //~v1E7I~
}
