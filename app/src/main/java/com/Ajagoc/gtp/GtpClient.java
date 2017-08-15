//*CID://+v1B6R~:                             update#=   33;
//***********************************************************************
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)
//***********************************************************************
// GtpClient.java
package com.Ajagoc.gtp;                                            //~v1B6I~


import jagoclient.Dump;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
                                                                   //~v1B6I~

/** Interface to a Go program that uses GTP over the standard I/O streams.//~v1B6R~
    <p>                                                            //~v1B6R~
    This class is final because it starts a thread in its constructor which//~v1B6R~
    might conflict with subclassing because the subclass constructor will//~v1B6R~
    be called after the thread is started.                         //~v1B6R~
    </p>                                                           //~v1B6R~
    <p>                                                            //~v1B6R~
    Callbacks can be registered to monitor the input, output and error stream//~v1B6R~
    and to handle timeout and invalid responses.                   //~v1B6R~
    </p> */                                                        //~v1B6R~
public final class GtpClient                                       //~v1B6R~
    extends GtpClientBase                                          //~v1B6R~
{                                                                  //~v1B6R~
    /** The timeout for commands that are expected to be fast.     //~v1B6I~
        GoGui 0.9.4 used 8 sec, but this was not enough on some machines//~v1B6I~
        when starting up engines like Aya in the Wine emulator. */ //~v1B6I~
//    private static final int TIMEOUT = 15000;                    //~v1B6R~
                                                                   //~v1B6I~
    /** Exception thrown if executing a GTP engine failed. */      //~v1B6R~
    public static class ExecFailed                                 //~v1B6R~
        extends GtpError                                           //~v1B6R~
    {                                                              //~v1B6R~
        public String m_program;                                   //~v1B6R~

        public ExecFailed(String program, String message)          //~v1B6R~
        {                                                          //~v1B6R~
            super(message);                                        //~v1B6R~
            m_program = program;                                   //~v1B6R~
        }                                                          //~v1B6R~

        public ExecFailed(String program, IOException e)           //~v1B6R~
        {                                                          //~v1B6R~
            this(program, e.getMessage());                         //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** Callback if a timeout occured. */                          //~v1B6R~
    public interface TimeoutCallback                               //~v1B6R~
    {                                                              //~v1B6R~
        /** Ask for continuation.                                  //~v1B6R~
            If this function returns true, Gtp.send will wait for another//~v1B6R~
            timeout period, if it returns false, the program will be killed. *///~v1B6R~
        boolean askContinue();                                     //~v1B6R~
    }                                                              //~v1B6R~

    /** Callback if an invalid response occured.                   //~v1B6R~
        Can be used to display invalid responses (without a status character)//~v1B6R~
        immediately, because send will not abort on an invalid response//~v1B6R~
        but continue to wait for a valid response line.            //~v1B6R~
        This is necessary for some Go programs with broken GTP implementation//~v1B6R~
        which write debug data to standard output (e.g. Wallyplus 0.1.2). *///~v1B6R~
    public interface InvalidResponseCallback                       //~v1B6R~
    {                                                              //~v1B6R~
        void show(String line);                                    //~v1B6R~
    }                                                              //~v1B6R~

//    /** Callback interface for logging or displaying the GTP stream.
//        Note that some of the callback functions are called from different
//        threads. */
    public interface IOCallback                                    //~v1B6R~
    {                                                              //~v1B6R~
        void receivedInvalidResponse(String s);                    //~v1B6R~

        void receivedResponse(boolean error, String s);            //~v1B6R~

        void receivedStdErr(String s);                             //~v1B6R~

        void sentCommand(String s);                                //~v1B6R~
    }                                                              //~v1B6R~

    /** Constructor.                                               //~v1B6R~
        @param program Command line for program.                   //~v1B6R~
        Will be split into words with respect to " as in StringUtil.tokenize.//~v1B6R~
        If the command line contains the string "%SRAND", it will be replaced//~v1B6R~
        by a random seed. This is useful if the random seed can be set by//~v1B6R~
        a command line option to produce deterministic randomness (the//~v1B6R~
        command returned by getProgramCommand() will contain the actual//~v1B6R~
        random seed used).                                         //~v1B6R~
        @param workingDirectory The working directory to run the program in or//~v1B6R~
        null for the current directory                             //~v1B6R~
        @param log Log input, output and error stream to standard error.//~v1B6R~
        @param callback Callback for external display of the streams. *///~v1B6R~
    public GtpClient(String program, File workingDirectory, boolean log,//~v1B6R~
                       IOCallback callback)                        //~v1B6R~
        throws GtpClient.ExecFailed                                //~v1B6R~
    {                                                              //~v1B6R~
//        if (workingDirectory != null && ! workingDirectory.isDirectory())//~v1B6R~
//            throw new ExecFailed(program,                        //~v1B6R~
//                                 "Invalid working directory \""  //~v1B6R~
//                                 + workingDirectory + "\"");     //~v1B6R~
        m_log = log;                                               //~v1B6R~
        m_callback = callback;                                     //~v1B6R~
        m_wasKilled = false;                                       //~v1B6R~
//        if (program.indexOf("%SRAND") >= 0)                      //~v1B6R~
//        {                                                        //~v1B6R~
//            // RAND_MAX in stdlib.h ist at least 32767           //~v1B6R~
//            int randMax = 32767;                                 //~v1B6R~
//            int rand = (int)(Math.random() * (randMax + 1));     //~v1B6R~
//            program =                                            //~v1B6R~
//                program.replaceAll("%SRAND", Integer.toString(rand));//~v1B6R~
//        }                                                        //~v1B6R~
        m_program = program;                                       //~v1B6R~
		logOut(program);                                           //~v1B6I~
        if (StringUtil.isEmpty(program))                           //~v1B6R~
            throw new ExecFailed(program,                          //~v1B6R~
                                 "Command for invoking Go program must be"//~v1B6R~
                                 + " not empty.");                 //~v1B6R~
        Runtime runtime = Runtime.getRuntime();                    //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            // Create command array with StringUtil::splitArguments//~v1B6R~
            // because Runtime.exec(String) uses a default StringTokenizer//~v1B6R~
            // which does not respect ".                           //~v1B6R~
            String[] cmdArray = StringUtil.splitArguments(program);//~v1B6R~
            // Make file name absolute, if working directory is not current//~v1B6R~
            // directory. With Java 1.5, it seems that Runtime.exec succeeds//~v1B6R~
            // if the relative path is valid from the current, but not from//~v1B6R~
            // the given working directory, but the process is not usable//~v1B6R~
            // (reading from its input stream immediately returns  //~v1B6R~
            // end-of-stream)                                      //~v1B6R~
            if (cmdArray.length > 0)                               //~v1B6R~
            {                                                      //~v1B6R~
                File file = new File(cmdArray[0]);                 //~v1B6R~
                // Only replace if executable is a path to a file, not//~v1B6R~
                // an executable in the exec-path                  //~v1B6R~
                if (file.exists())                                 //~v1B6R~
                    cmdArray[0] = file.getAbsolutePath();          //~v1B6R~
            }                                                      //~v1B6R~
            m_process = runtime.exec(cmdArray, null, workingDirectory);//~v1B6R~
        }                                                          //~v1B6R~
        catch (IOException e)                                      //~v1B6R~
        {                                                          //~v1B6R~
            throw new ExecFailed(program, e);                      //~v1B6R~
        }                                                          //~v1B6R~
        init(m_process.getInputStream(), m_process.getOutputStream(),//~v1B6R~
             m_process.getErrorStream());                          //~v1B6R~
    }                                                              //~v1B6R~

//    /** Constructor for given input and output streams. */
//    public GtpClient(InputStream in, OutputStream out, boolean log,
//                     IOCallback callback)
//        throws GtpError
//    {
//        m_log = log;
//        m_callback = callback;
//        m_program = "-";
//        m_process = null;
//        init(in, out, null);
//    }

    /** Close the output stream to the program.                    //~v1B6R~
        Some engines don't handle closing the command stream without an//~v1B6R~
        explicit quit command well, so the preferred way to terminate a//~v1B6R~
        connection is to send a quit command. Closing the output stream after//~v1B6R~
        a quit is not strictly necessary, but may improve compatibility with//~v1B6R~
        engines that read the input stream in a different thread *///~v1B6R~
    public void close()                                            //~v1B6R~
    {                                                              //~v1B6R~
        m_out.close();                                             //~v1B6R~
    }                                                              //~v1B6R~

    /** Kill the Go program. */                                    //~v1B6R~
    public void destroyProcess()                                   //~v1B6R~
    {                                                              //~v1B6R~
        if (m_process != null)                                     //~v1B6R~
        {                                                          //~v1B6R~
            m_wasKilled = true;                                    //~v1B6R~
            m_process.destroy();                                   //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

//    /** Did the engine ever send a valid response to a command? */
    public boolean getAnyCommandsResponded()                       //~v1B6R~
    {                                                              //~v1B6R~
        return m_anyCommandsResponded;                             //~v1B6R~
    }                                                              //~v1B6R~

    /** Get response to last command sent. */                      //~v1B6R~
    public String getResponse()                                    //~v1B6R~
    {                                                              //~v1B6R~
        return m_response;                                         //~v1B6R~
    }                                                              //~v1B6R~

//    /** Get full response including status and ID and last command. */
//    public String getFullResponse()
//    {
//        return m_fullResponse;
//    }

    /** Get the command line that was used for invoking the Go program.//~v1B6R~
        @return The command line that was given to the constructor. *///~v1B6R~
    public String getProgramCommand()                              //~v1B6R~
    {                                                              //~v1B6R~
        return m_program;                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** Check if program is dead. */                               //~v1B6R~
    public boolean isProgramDead()                                 //~v1B6R~
    {                                                              //~v1B6R~
        return m_isProgramDead;                                    //~v1B6R~
    }                                                              //~v1B6R~

//    /** Send a command.
//        @return The response text of the successful response not including
//        the status character.
//        @throws GtpError containing the response if the command fails. */
    public String send(String command) throws GtpError             //~v1B6R~
    {                                                              //~v1B6R~
        return send(command, -1, null);                            //~v1B6R~
    }                                                              //~v1B6R~

//    public void queryName(long timeout, TimeoutCallback timeoutCallback)
//        throws GtpError
//    {
//        m_name = send("name", timeout, timeoutCallback);
//    }

//    /** Send a command with timeout.
//        @param command The command to send
//        @param timeout Timeout in milliseconds or -1, if no timeout
//        @param timeoutCallback Timeout callback or null if no timeout.
//        @return The response text of the successful response not including
//        the status character.
//        @throws GtpError containing the response if the command fails.
//        @see TimeoutCallback */
    public String send(String command, long timeout,               //~v1B6R~
                       TimeoutCallback timeoutCallback) throws GtpError//~v1B6R~
    {                                                              //~v1B6R~
    	if (Dump.Y) Dump.println("GtpClient:send cmd="+command);   //+v1B6I~
        assert ! command.trim().equals("");                        //~v1B6R~
        assert ! command.trim().startsWith("#");                   //~v1B6R~
        m_timeoutCallback = timeoutCallback;                       //~v1B6R~
        m_fullResponse = "";                                       //~v1B6R~
        m_response = "";                                           //~v1B6R~
        ++m_commandNumber;                                         //~v1B6R~
        if (m_autoNumber)                                          //~v1B6R~
            command = Integer.toString(m_commandNumber) + " " + command;//~v1B6R~
        if (m_log)                                                 //~v1B6R~
            logOut(command);                                       //~v1B6R~
        m_out.println(command);                                    //~v1B6R~
        m_out.flush();                                             //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            if (m_out.checkError())                                //~v1B6R~
            {                                                      //~v1B6R~
                throwProgramDied();                                //~v1B6R~
            }                                                      //~v1B6R~
            if (m_callback != null)                                //~v1B6R~
                m_callback.sentCommand(command);                   //~v1B6R~
            readResponse(timeout);                                 //~v1B6R~
            return m_response;                                     //~v1B6R~
        }                                                          //~v1B6R~
        catch (GtpError e)                                         //~v1B6R~
        {                                                          //~v1B6R~
            e.setCommand(command);                                 //~v1B6R~
            throw e;                                               //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    public void sendPlay(Move move, long timeout,                  //~v1B6R~
                         TimeoutCallback timeoutCallback) throws GtpError//~v1B6R~
    {                                                              //~v1B6R~
        send(getCommandPlay(move), timeout, timeoutCallback);      //~v1B6R~
    }                                                              //~v1B6R~

//    /** Send comment.
//        @param comment comment line (must start with '#'). */
    public void sendComment(String comment)                        //~v1B6R~
    {                                                              //~v1B6R~
        assert comment.trim().startsWith("#");                     //~v1B6R~
        if (m_log)                                                 //~v1B6R~
            logOut(comment);                                       //~v1B6R~
        if (m_callback != null)                                    //~v1B6R~
            m_callback.sentCommand(comment);                       //~v1B6R~
        m_out.println(comment);                                    //~v1B6R~
        m_out.flush();                                             //~v1B6R~
    }                                                              //~v1B6R~

//    /** Enable auto-numbering commands.
//        Every command will be prepended by an integer as defined in the GTP
//        standard, the integer is incremented after each command. */
    public void setAutoNumber(boolean enable)                      //~v1B6R~
    {                                                              //~v1B6R~
        m_autoNumber = enable;                                     //~v1B6R~
    }                                                              //~v1B6R~

//    /** Set the callback for invalid responses.
//        @see InvalidResponseCallback */
    public void setInvalidResponseCallback(InvalidResponseCallback callback)//~v1B6R~
    {                                                              //~v1B6R~
        m_invalidResponseCallback = callback;                      //~v1B6R~
    }                                                              //~v1B6R~

//    public void setIOCallback(GtpClient.IOCallback callback)
//    {
//        m_callback = callback;
//    }

//    /** Set a prefix for logging to standard error.
//        Only used if logging was enabled in the constructor. */
//    public void setLogPrefix(String prefix)
//    {
//        synchronized (this)
//        {
//            m_logPrefix = prefix;
//        }
//    }

//    /** Wait until the process of the program exits. */
    public void waitForExit()                                      //~v1B6R~
    {                                                              //~v1B6R~
        if (m_process == null)                                     //~v1B6R~
            return;                                                //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            m_process.waitFor();                                   //~v1B6R~
            m_errorThread.join();                                  //~v1B6R~
            m_inputThread.join();                                  //~v1B6R~
        }                                                          //~v1B6R~
        catch (InterruptedException e)                             //~v1B6R~
        {                                                          //~v1B6R~
            printInterrupted();                                    //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** More sophisticated version of waitFor with timeout. */     //~v1B6R~
    public void waitForExit(int timeout, TimeoutCallback timeoutCallback)//~v1B6R~
    {                                                              //~v1B6R~
        if (m_process == null)                                     //~v1B6R~
            return;                                                //~v1B6R~
        while (true)                                               //~v1B6R~
        {                                                          //~v1B6R~
            if (ProcessUtil.waitForExit(m_process, timeout))       //~v1B6R~
                break;                                             //~v1B6R~
            if (! timeoutCallback.askContinue())                   //~v1B6R~
            {                                                      //~v1B6R~
                m_process.destroy();                               //~v1B6R~
                return;                                            //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            m_errorThread.join(timeout);                           //~v1B6R~
            m_inputThread.join(timeout);                           //~v1B6R~
        }                                                          //~v1B6R~
        catch (InterruptedException e)                             //~v1B6R~
        {                                                          //~v1B6R~
            printInterrupted();                                    //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** Was program forcefully terminated by calling destroyProcess() *///~v1B6R~
    public boolean wasKilled()                                     //~v1B6R~
    {                                                              //~v1B6R~
        return m_wasKilled;                                        //~v1B6R~
    }                                                              //~v1B6R~

    private static final class Message                             //~v1B6R~
    {                                                              //~v1B6R~
        public Message(String text)                                //~v1B6R~
        {                                                          //~v1B6R~
            m_text = text;                                         //~v1B6R~
        }                                                          //~v1B6R~

        public String m_text;                                      //~v1B6R~
    }                                                              //~v1B6R~

    private class InputThread                                      //~v1B6R~
        extends Thread                                             //~v1B6R~
    {                                                              //~v1B6R~
        InputThread(InputStream in, BlockingQueue<Message> queue)  //~v1B6R~
        {                                                          //~v1B6R~
            m_in = new BufferedReader(new InputStreamReader(in));  //~v1B6R~
            m_queue = queue;                                       //~v1B6R~
        }                                                          //~v1B6R~

        public void run()                                          //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                mainLoop();                                        //~v1B6R~
            }                                                      //~v1B6R~
            catch (Throwable t)                                    //~v1B6R~
            {                                                      //~v1B6R~
                StringUtil.printException(t);                      //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

        private final BufferedReader m_in;                         //~v1B6R~

      private final BlockingQueue<Message> m_queue;                //~v1B6R~

        private final StringBuilder m_buffer = new StringBuilder(1024);//~v1B6R~

        private void appendBuffer(String line)                     //~v1B6R~
        {                                                          //~v1B6R~
            m_buffer.append(line);                                 //~v1B6R~
            m_buffer.append('\n');                                 //~v1B6R~
        }                                                          //~v1B6R~

        private boolean isResponseStart(String line)               //~v1B6R~
        {                                                          //~v1B6R~
            if (line.length() < 1)                                 //~v1B6R~
                return false;                                      //~v1B6R~
            char c = line.charAt(0);                               //~v1B6R~
            return (c == '=' || c == '?');                         //~v1B6R~
        }                                                          //~v1B6R~

        private void mainLoop() throws InterruptedException        //~v1B6R~
        {                                                          //~v1B6R~
            while (true)                                           //~v1B6R~
            {                                                      //~v1B6R~
                String line = readLine();                          //~v1B6R~
                if (line == null)                                  //~v1B6R~
                {                                                  //~v1B6R~
                    putMessage(null);                              //~v1B6R~
                    return;                                        //~v1B6R~
                }                                                  //~v1B6R~
                appendBuffer(line);                                //~v1B6R~
                if (! isResponseStart(line))                       //~v1B6R~
                {                                                  //~v1B6R~
                    if (! line.trim().equals(""))                  //~v1B6R~
                    {                                              //~v1B6R~
                        if (m_callback != null)                    //~v1B6R~
                            m_callback.receivedInvalidResponse(line);//~v1B6R~
                        if (m_invalidResponseCallback != null)     //~v1B6R~
                            m_invalidResponseCallback.show(line);  //~v1B6R~
                    }                                              //~v1B6R~
                    m_buffer.setLength(0);                         //~v1B6R~
                    continue;                                      //~v1B6R~
                }                                                  //~v1B6R~
                while (true)                                       //~v1B6R~
                {                                                  //~v1B6R~
                    line = readLine();                             //~v1B6R~
                    appendBuffer(line);                            //~v1B6R~
                    if (line == null)                              //~v1B6R~
                    {                                              //~v1B6R~
                        putMessage(null);                          //~v1B6R~
                        return;                                    //~v1B6R~
                    }                                              //~v1B6R~
                    if (line.equals(""))                           //~v1B6R~
                    {                                              //~v1B6R~
                        putMessage();                              //~v1B6R~
                        break;                                     //~v1B6R~
                    }                                              //~v1B6R~
                }                                                  //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

        private void putMessage()                                  //~v1B6R~
        {                                                          //~v1B6R~
            // Calling Thread.yield increases the probability that the IO//~v1B6R~
            // callbacks for stderr and stdout are called in the right order//~v1B6R~
            // for the typical use case of a program writing to stderr//~v1B6R~
            // before writing the response. The yield costs some performance//~v1B6R~
            // however and could have a negative effect, if the program//~v1B6R~
            // writes to stderr immediately after the response (e.g. logging//~v1B6R~
            // output during pondering).                           //~v1B6R~
            Thread.yield();                                        //~v1B6R~
            putMessage(m_buffer.toString());                       //~v1B6R~
            m_buffer.setLength(0);                                 //~v1B6R~
        }                                                          //~v1B6R~

        private void putMessage(String text)                       //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                m_queue.put(new Message(text));                    //~v1B6R~
            }                                                      //~v1B6R~
            catch (InterruptedException e)                         //~v1B6R~
            {                                                      //~v1B6R~
                printInterrupted();                                //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

        private String readLine()                                  //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                String line = m_in.readLine();                     //~v1B6R~
                if (m_log && line != null)                         //~v1B6R~
                    logIn(line);                                   //~v1B6R~
                return line;                                       //~v1B6R~
            }                                                      //~v1B6R~
            catch (IOException e)                                  //~v1B6R~
            {                                                      //~v1B6R~
            	Dump.println(e,"GtpClient:readLine");              //~v1B6I~
                return null;                                       //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    private class ErrorThread                                      //~v1B6R~
        extends Thread                                             //~v1B6R~
    {                                                              //~v1B6R~
        public ErrorThread(InputStream in, BlockingQueue<Message> queue)//~v1B6R~
        {                                                          //~v1B6R~
            m_in = new InputStreamReader(in);                      //~v1B6R~
            m_queue = queue;                                       //~v1B6R~
        }                                                          //~v1B6R~

        public void run()                                          //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                char[] buffer = new char[4096];                    //~v1B6R~
                while (true)                                       //~v1B6R~
                {                                                  //~v1B6R~
                    int n;                                         //~v1B6R~
                    try                                            //~v1B6R~
                    {                                              //~v1B6R~
                        n = m_in.read(buffer);                     //~v1B6R~
                    }                                              //~v1B6R~
                    catch (IOException e)                          //~v1B6R~
                    {                                              //~v1B6R~
                        return;                                    //~v1B6R~
                    }                                              //~v1B6R~
                    if (n <= 0)                                    //~v1B6R~
                        return;                                    //~v1B6R~
                    String text = new String(buffer, 0, n);        //~v1B6R~
                    if (m_callback != null)                        //~v1B6R~
                        m_callback.receivedStdErr(text);           //~v1B6R~
                    if (m_log)                                     //~v1B6R~
                        logError(text);                            //~v1B6R~
                }                                                  //~v1B6R~
            }                                                      //~v1B6R~
            catch (Throwable t)                                    //~v1B6R~
            {                                                      //~v1B6R~
                StringUtil.printException(t);                      //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

        private final Reader m_in;                                 //~v1B6R~
    }                                                              //~v1B6R~

    private InvalidResponseCallback m_invalidResponseCallback;     //~v1B6R~

    private boolean m_autoNumber;                                  //~v1B6R~

    private boolean m_anyCommandsResponded;                        //~v1B6R~

    private boolean m_isProgramDead;                               //~v1B6R~

    private boolean m_wasKilled;                                   //~v1B6R~

    private final boolean m_log;                                   //~v1B6R~

    private int m_commandNumber;                                   //~v1B6R~

    private IOCallback m_callback;                                 //~v1B6R~

    private PrintWriter m_out;                                     //~v1B6R~

    private Process m_process;                                     //~v1B6R~

    private String m_fullResponse;                                 //~v1B6R~

    private String m_response;                                     //~v1B6R~

    private String m_logPrefix;                                    //~v1B6R~

    private String m_program;                                      //~v1B6R~

    private BlockingQueue<Message> m_queue;                        //~v1B6R~

    private TimeoutCallback m_timeoutCallback;                     //~v1B6R~

    private InputThread m_inputThread;                             //~v1B6R~

    private ErrorThread m_errorThread;                             //~v1B6R~

    private void init(InputStream in, OutputStream out, InputStream err)//~v1B6R~
    {                                                              //~v1B6R~
        m_out = new PrintWriter(out);                              //~v1B6R~
        m_isProgramDead = false;                                   //~v1B6R~
        m_queue = new ArrayBlockingQueue<Message>(10);             //~v1B6R~
        m_inputThread = new InputThread(in, m_queue);              //~v1B6R~
        if (err != null)                                           //~v1B6R~
        {                                                          //~v1B6R~
            m_errorThread = new ErrorThread(err, m_queue);         //~v1B6R~
            m_errorThread.start();                                 //~v1B6R~
        }                                                          //~v1B6R~
        m_inputThread.start();                                     //~v1B6R~
    }                                                              //~v1B6R~

    private synchronized void logError(String text)                //~v1B6R~
    {                                                              //~v1B6R~
        System.err.print(text);                                    //~v1B6R~
    }                                                              //~v1B6R~

    private synchronized void logIn(String msg)                    //~v1B6R~
    {                                                              //~v1B6R~
        if (m_logPrefix != null)                                   //~v1B6R~
            System.err.print(m_logPrefix);                         //~v1B6R~
        System.err.print("<< ");                                   //~v1B6R~
        System.err.println(msg);                                   //~v1B6R~
    }                                                              //~v1B6R~

    private synchronized void logOut(String msg)                   //~v1B6R~
    {                                                              //~v1B6R~
        if (m_logPrefix != null)                                   //~v1B6R~
            System.err.print(m_logPrefix);                         //~v1B6R~
        System.err.print(">> ");                                   //~v1B6R~
        System.err.println(msg);                                   //~v1B6R~
    }                                                              //~v1B6R~

    /** Print information about occurence of InterruptedException. //~v1B6R~
        An InterruptedException should never happen, because we don't call//~v1B6R~
        Thread.interrupt */                                        //~v1B6R~
    private void printInterrupted()                                //~v1B6R~
    {                                                              //~v1B6R~
        System.err.println("GtpClient: InterruptedException");     //~v1B6R~
        Thread.dumpStack();                                        //~v1B6R~
    }                                                              //~v1B6R~

    private String readResponse(long timeout) throws GtpError      //~v1B6R~
    {                                                              //~v1B6R~
        while (true)                                               //~v1B6R~
        {                                                          //~v1B6R~
            Message message = waitForMessage(timeout);             //~v1B6R~
            String response = message.m_text;                      //~v1B6R~
            if (response == null)                                  //~v1B6R~
            {                                                      //~v1B6R~
                m_isProgramDead = true;                            //~v1B6R~
                throwProgramDied();                                //~v1B6R~
            }                                                      //~v1B6R~
            m_anyCommandsResponded = true;                         //~v1B6R~
            boolean error = (response.charAt(0) != '=');           //~v1B6R~
            m_fullResponse = response;                             //~v1B6R~
            if (m_callback != null)                                //~v1B6R~
                m_callback.receivedResponse(error, m_fullResponse);//~v1B6R~
            assert response.length() >= 3;                         //~v1B6R~
            int index = response.indexOf(' ');                     //~v1B6R~
            int length = response.length();                        //~v1B6R~
            if (index < 0)                                         //~v1B6R~
                m_response = response.substring(1, length - 2);    //~v1B6R~
            else                                                   //~v1B6R~
                m_response = response.substring(index + 1, length - 2);//~v1B6R~
            if (error)                                             //~v1B6R~
                throw new GtpError(m_response);                    //~v1B6R~
            return m_response;                                     //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    private void throwProgramDied() throws GtpError                //~v1B6R~
    {                                                              //~v1B6R~
        m_isProgramDead = true;                                    //~v1B6R~
        String name = m_name;                                      //~v1B6R~
        if (name == null)                                          //~v1B6R~
            name = "The Go program";                               //~v1B6R~
        if (m_wasKilled)                                           //~v1B6R~
            throw new GtpError(name + " terminated.");             //~v1B6R~
        else                                                       //~v1B6R~
            throw new GtpError(name + " terminated unexpectedly.");//~v1B6R~
    }                                                              //~v1B6R~

    private Message waitForMessage(long timeout) throws GtpError   //~v1B6R~
    {                                                              //~v1B6R~
        Message message = null;                                    //~v1B6R~
        if (timeout < 0)                                           //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                message = m_queue.take();                          //~v1B6R~
            }                                                      //~v1B6R~
            catch (InterruptedException e)                         //~v1B6R~
            {                                                      //~v1B6R~
                printInterrupted();                                //~v1B6R~
                destroyProcess();                                  //~v1B6R~
                throwProgramDied();                                //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        else                                                       //~v1B6R~
        {                                                          //~v1B6R~
            message = null;                                        //~v1B6R~
            while (message == null)                                //~v1B6R~
            {                                                      //~v1B6R~
                try                                                //~v1B6R~
                {                                                  //~v1B6R~
                    message = m_queue.poll(timeout, TimeUnit.MILLISECONDS);//~v1B6R~
                }                                                  //~v1B6R~
                catch (InterruptedException e)                     //~v1B6R~
                {                                                  //~v1B6R~
                    printInterrupted();                            //~v1B6R~
                }                                                  //~v1B6R~
                if (message == null)                               //~v1B6R~
                {                                                  //~v1B6R~
                    assert m_timeoutCallback != null;              //~v1B6R~
                    if (! m_timeoutCallback.askContinue())         //~v1B6R~
                    {                                              //~v1B6R~
                        destroyProcess();                          //~v1B6R~
                        throwProgramDied();                        //~v1B6R~
                    }                                              //~v1B6R~
                }                                                  //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        return message;                                            //~v1B6R~
    }                                                              //~v1B6R~
}                                                                  //~v1B6R~
