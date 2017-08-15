//*CID://+v1B6R~: update#=   1;                                    //+v1B6I~
//*********************************                                //+v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//+v1B6I~
//*********************************                                //+v1B6I~
// GtpError.java
package com.Ajagoc.gtp;                                            //+v1B6I~


/** Exception indicating the failure of a GTP command. */
public class GtpError
    extends ErrorMessage
{
    public GtpError(String s)
    {
        super(s);
    }

    /** The command that caused the error.
        Can return null, if the command is not known. */
    public String getCommand()
    {
        return m_command;
    }

    public void setCommand(String command)
    {
        m_command = command;
    }

    private String m_command;
}
