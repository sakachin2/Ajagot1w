//*CID://+v1B6R~: update#=   1;                                    //+v1B6I~
//*********************************                                //+v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//+v1B6I~
//*********************************                                //+v1B6I~
// ErrorMessage.java
package com.Ajagoc.gtp;                                            //+v1B6I~


/** Error with error message.
    ErrorMessage are exceptions with a message meaningful for presentation
    to the user. */
public class ErrorMessage
    extends Exception
{
    /** Constructor.
        @param message The error message text. */
    public ErrorMessage(String message)
    {
        super(message);
    }
}
