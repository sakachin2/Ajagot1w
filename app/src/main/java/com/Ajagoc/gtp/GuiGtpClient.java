//*CID://+v1B6R~:                             update#=   35;       //~v1B6I~
//*************************************************************************//~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*************************************************************************//~v1B6I~
// GuiGtpClient.java

//package net.sf.gogui.gui;                                        //~v1B6R~
package com.Ajagoc.gtp;                                            //~v1B6I~

//import java.awt.Component;                                       //~v1B6R~

import java.text.MessageFormat;                                  //~v1B6R~
//import javax.swing.SwingUtilities;                               //~v1B6R~
import com.Ajagoc.AjagoAlert;
import com.Ajagoc.AjagoAlertI;
import com.Ajagoc.AjagoUiThread;
import com.Ajagoc.R;
import com.Ajagoc.gtp.TimeSettings;                                //~v1B6R~
//import com.Ajagoc.gtp.ConstBoard;                                //~v1B6R~
import com.Ajagoc.gtp.Komi;                                        //~v1B6R~
//import com.Ajagoc.gtp.Move;                                      //~v1B6R~
import com.Ajagoc.gtp.GtpClient;                                   //~v1B6R~
import com.Ajagoc.gtp.GtpClientBase;                               //~v1B6R~
import com.Ajagoc.gtp.GtpError;                                    //~v1B6R~
import com.Ajagoc.gtp.GtpSynchronizer;                             //~v1B6R~
//import com.Ajagoc.gtp.GTPConnector;                              //~v1B6R~
//import static net.sf.gogui.gui.I18n.i18n;                        //~v1B6R~
import static com.Ajagoc.gtp.GoGui.Rstr;                           //~v1B6I~

/** Wrapper around gtp.GtpClient to be used in a GUI environment.
    Allows to send fast commands with the GtpClientBase.send() function
    immediately in the event dispatch thread and potentially slow commands in
    a separate thread with a callback in the event thread after the command
    finished.
    Fast commands are ones that the Go engine is supposed to answer quickly
    (like boardsize, play and undo), however they have a timeout to
    prevent the GUI to hang, if the program does not respond.
    After the timeout a dialog is opened that allows to kill the program or
    continue to wait.
    This class also contains a GtpSynchronizer. */
public class GuiGtpClient
    extends GtpClientBase
{
//  public GuiGtpClient(GtpClient gtp, Component owner,            //~v1B6R~
    public GuiGtpClient(GtpClient gtp, Object owner,               //~v1B6R~
                        GtpSynchronizer.Listener listener,
                        MessageDialogs messageDialogs)             //~v1B6R~
                                                               //~v1B6I~
    {
        m_gtp = gtp;
//      m_owner = owner;                                           //~v1B6R~
        m_owner = (GoGui)owner;                                    //~v1B6I~
//      m_messageDialogs = messageDialogs;                         //~v1B6R~
        m_gtpSynchronizer = new GtpSynchronizer(this, listener, false);
        Thread thread = new Thread() {
                public void run() {
                    synchronized (m_mutex)
                    {
                        boolean firstWait = true;
                        while (true)
                        {
                            try
                            {
                                if (m_command == null || ! firstWait)
                                    m_mutex.wait();
                            }
                            catch (InterruptedException e)
                            {
                                System.err.println("Interrupted");
                            }
                            firstWait = false;
                            m_response = null;
                            m_exception = null;
                            try
                            {
                                m_response = m_gtp.send(m_command);
                            }
                            catch (GtpError e)
                            {
                                m_exception = e;
                            }
//                          SwingUtilities.invokeLater(m_callback);//~v1B6R~
							if (m_callback!=null)                  //~v1B6I~
	                            AjagoUiThread.invokeLater(m_callback);//~v1B6R~
                        }
                    }
                }
            };
        thread.start();
    }

    public void close()
    {
        if (! isProgramDead())
        {
            m_gtp.close();
            TimeoutCallback timeoutCallback = new TimeoutCallback(null);
            m_gtp.waitForExit(TIMEOUT, timeoutCallback);
        }
    }

    public void destroyGtp()
    {
        m_gtp.destroyProcess();
    }

    public boolean getAnyCommandsResponded()
    {
        return m_gtp.getAnyCommandsResponded();
    }

    /** Get exception of asynchronous command.
        You must call this before you are allowed to send new a command. */
    public GtpError getException()
    {
        synchronized (m_mutex)
        {
//            assert SwingUtilities.isEventDispatchThread();       //~v1B6R~
            assert m_commandInProgress;
            m_commandInProgress = false;
            return m_exception;
        }
    }

    public String getProgramCommand()
    {
        return m_gtp.getProgramCommand();
    }

    /** Get response to asynchronous command.
        You must call getException() first. */
    public String getResponse()
    {
        synchronized (m_mutex)
        {
//            assert SwingUtilities.isEventDispatchThread();       //~v1B6R~
            assert ! m_commandInProgress;
            return m_response;
        }
    }

//  public void initSynchronize(ConstBoard board, Komi komi,       //~v1B6R~
//                              TimeSettings timeSettings) throws GtpError//~v1B6R~
    public void initSynchronize(int Psize,Komi komi,TimeSettings timeSettings) throws GtpError//~v1B6R~
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
        assert ! m_commandInProgress;
//      m_gtpSynchronizer.init(board, komi, timeSettings);         //~v1B6R~
        m_gtpSynchronizer.init(Psize,komi,timeSettings);           //~v1B6R~
    }

    public boolean isCommandInProgress()
    {
        return m_commandInProgress;
    }

    public boolean isOutOfSync()
    {
        return m_gtpSynchronizer.isOutOfSync();
    }

    public boolean isProgramDead()
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
        return m_gtp.isProgramDead();
    }

    /** Send asynchronous command. */
    public void send(String command, Runnable callback)
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        if (!AG.isMainThread())                                  //~v1B6R~
//        {                                                        //~v1B6R~
//            sendUI(command,callback);                            //~v1B6R~
//            return;                                              //~v1B6R~
//        }                                                        //~v1B6R~
        assert ! m_commandInProgress;
        synchronized (m_mutex)
        {
            m_command = command;
            m_callback = callback;
            m_commandInProgress = true;
            m_mutex.notifyAll();
        }
    }
//    private void sendUI(final String command, final Runnable callback)//~v1B6R~
//    {                                                            //~v1B6R~
//        AjagoUiThread.runOnUiThread(new AjagoUiThreadI()         //~v1B6R~
//                        {                                        //~v1B6R~
//                            String UIcommand=command;            //~v1B6R~
//                            Runnable UIcallback=callback;        //~v1B6R~
//                            public void runOnUiThread(Object Pany)//~v1B6R~
//                            {                                    //~v1B6R~
//                                try                              //~v1B6R~
//                                {                                //~v1B6R~
//                                    send(UIcommand,UIcallback);  //~v1B6R~
//                                }                                //~v1B6R~
//                                catch(Exception e)               //~v1B6R~
//                                {                                //~v1B6R~
//                                    Dump.println(e,"sendUI");    //~v1B6R~
//                                }                                //~v1B6R~
//                            }                                    //~v1B6R~
//                        },this/*Pany*/);                         //~v1B6R~

//    }                                                            //~v1B6R~
    public void sendComment(String comment)
    {
        m_gtp.sendComment(comment);
    }

    /** Send command in event dispatch thread. */
    public String send(String command) throws GtpError
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
        assert ! m_commandInProgress;
        TimeoutCallback timeoutCallback = new TimeoutCallback(command);
        return m_gtp.send(command, TIMEOUT, timeoutCallback);
    }

    public void setAutoNumber(boolean enable)
    {
        m_gtp.setAutoNumber(enable);
    }

//    public void synchronize(ConstBoard board, Komi komi,         //~v1B6R~
//                            TimeSettings timeSettings) throws GtpError//~v1B6R~
//    {                                                            //~v1B6R~
////        assert SwingUtilities.isEventDispatchThread();         //~v1B6R~
//        assert ! m_commandInProgress;                            //~v1B6R~
//        m_gtpSynchronizer.synchronize(board, komi, timeSettings);//~v1B6R~
//    }                                                            //~v1B6R~

//    public void updateAfterGenmove(ConstBoard board)             //~v1B6R~
//    {                                                            //~v1B6R~
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        assert ! m_commandInProgress;                            //~v1B6R~
//        m_gtpSynchronizer.updateAfterGenmove(board);             //~v1B6R~
//    }                                                            //~v1B6R~

//    public void updateHumanMove(ConstBoard board, Move move) throws GtpError//~v1B6R~
//    {                                                            //~v1B6R~
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        assert ! m_commandInProgress;                            //~v1B6R~
//        m_gtpSynchronizer.updateHumanMove(board, move);          //~v1B6R~
//    }                                                            //~v1B6R~

    public void waitForExit()
    {
        m_gtp.waitForExit();
    }

    public boolean wasKilled()
    {
        return m_gtp.wasKilled();
    }

    private class TimeoutCallback
        implements GtpClient.TimeoutCallback
    {
        TimeoutCallback(String command)
        {
            m_command = command;
        }

        public boolean askContinue()
        {
//          String mainMessage = i18n("MSG_PROGRAM_NOT_RESPONDING");//~v1B6R~
//          String mainMessage = Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING);//~v1B6R~
            String optionalMessage;                                //~v1B6R~
            if (!m_owner.swInitialized)                            //+v1B6R~
            {                                                      //~v1B6I~
                optionalMessage=MessageFormat.format(Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING_4),m_command);//~v1B6I~
                AjagoAlertI cb=new AjagoAlertI()                   //~v1B6I~
                				{                                  //~v1B6I~
                                	public int alertButtonAction(int Pbuttonid,int Pitemois)//~v1B6I~
                                    {                              //~v1B6I~
                                    	if (Pbuttonid==AjagoAlert.BUTTON_POSITIVE)//~v1B6I~
                                        { 
					                        m_gtp.destroyProcess();//~v1B6I~
                                        }                          //~v1B6I~
                                        return Pbuttonid;          //~v1B6I~
                                    }                              //~v1B6I~
                                };                                  //~v1B6I~
	    		int flag=AjagoAlert.BUTTON_YNC|AjagoAlert.BUTTON_POSITIVE|AjagoAlert.BUTTON_NEGATIVE|AjagoAlert.SHOW_DIALOG;//~v1B6I~
        		AjagoAlert.simpleAlertDialog(cb/*callback*/,R.string.DialogTitle_gtpconnection,optionalMessage,flag);//notify only//~v1B6I~
            	return true;                                       //~v1B6I~
            }                                                      //~v1B6I~
//          String destructiveOption;                              //~v1B6R~
            if (m_command == null)                                 //~v1B6R~
            {                                                      //~v1B6R~
//              optionalMessage = i18n("MSG_PROGRAM_NOT_RESPONDING_2");//~v1B6R~
                optionalMessage = Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING_2);//~v1B6I~
//              destructiveOption ="";// Rstr(R.string.GtpLB_FORCE_QUIT_PROGRAM);//~v1B6R~
            }                                                      //~v1B6R~
            else                                                   //~v1B6R~
            {                                                      //~v1B6R~
                optionalMessage =                                  //~v1B6R~
                    MessageFormat.format(Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING_3),m_command);//~v1B6R~
//              destructiveOption ="";// Rstr(R.string.GtpLB_TERMINATE_PROGRAM);//~v1B6R~
            }                                                      //~v1B6R~
            //rc:false=destroy                                     //~v1B6I~
//          return ! m_messageDialogs.showWarningQuestion(null, m_owner,//~v1B6R~
//                                                        mainMessage,//~v1B6R~
//                                                        optionalMessage,//~v1B6R~
//                                                        destructiveOption,//~v1B6R~
//                                                        Rstr(R.string.GtpLB_WAIT),//~v1B6R~
//                                                        true);   //~v1B6R~
	    	int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;//~v1B6R~
        	AjagoAlert.simpleAlertDialog(null/*callback*/,R.string.DialogTitle_gtpconnection,optionalMessage,flag);//notify only//~v1B6R~
            return true;                                           //~v1B6I~
        }

        private final String m_command;
    };

    /** The timeout for commands that are expected to be fast.
        GoGui 0.9.4 used 8 sec, but this was not enough on some machines
        when starting up engines like Aya in the Wine emulator. */
    private static final int TIMEOUT = 15000;

    private boolean m_commandInProgress;

//  private final GtpClient m_gtp;                                 //~v1B6R~
    public  final GtpClient m_gtp;                                 //~v1B6I~

    private GtpError m_exception;

//  private final GtpSynchronizer m_gtpSynchronizer;               //~v1B6R~
    public  final GtpSynchronizer m_gtpSynchronizer;               //~v1B6I~

//    private final Component m_owner;                             //~v1B6R~
    private final GoGui m_owner;                                   //~v1B6I~

//  private final MessageDialogs m_messageDialogs;                 //~v1B6R~

    private final Object m_mutex = new Object();

    private Runnable m_callback;

    private String m_command;

    private String m_response;
}
