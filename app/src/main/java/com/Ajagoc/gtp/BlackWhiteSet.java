//*CID://+v1B6R~: update#=   1;                                    //+v1B6I~
//*********************************                                //+v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//+v1B6I~
//*********************************                                //+v1B6I~
// BlackWhiteSet.java

//package net.sf.gogui.go;                                         //+v1B6R~
package com.Ajagoc.gtp;                                            //+v1B6I~

import static com.Ajagoc.gtp.GoColor.BLACK;                        //+v1B6R~
import static com.Ajagoc.gtp.GoColor.WHITE;                        //+v1B6R~
import com.Ajagoc.gtp.ObjectUtil;                                  //+v1B6R~

/** A set containing one element for Black and one for White. */
public class BlackWhiteSet<T>
{
    public BlackWhiteSet()
    {
    }

    public BlackWhiteSet(T elementBlack, T elementWhite)
    {
        m_elementBlack = elementBlack;
        m_elementWhite = elementWhite;
    }

    public boolean equals(Object object)
    {
        if (object == null || object.getClass() != getClass())
            return false;
        BlackWhiteSet set = (BlackWhiteSet)object;
        return (ObjectUtil.equals(set.m_elementBlack, m_elementBlack)
                && ObjectUtil.equals(set.m_elementWhite, m_elementWhite));
    }

    public T get(GoColor c)
    {
        if (c == BLACK)
            return m_elementBlack;
        else
        {
            assert c == WHITE;
            return m_elementWhite;
        }
    }

    public int hashCode()
    {
        int hashCode = 0;
        if (m_elementBlack != null)
            hashCode |= m_elementBlack.hashCode();
        if (m_elementWhite != null)
            hashCode |= m_elementWhite.hashCode();
        return hashCode;
    }

    public void set(GoColor c, T element)
    {
        if (c == BLACK)
            m_elementBlack = element;
        else
        {
            assert c == WHITE;
            m_elementWhite = element;
        }
    }

    private T m_elementBlack;

    private T m_elementWhite;
}
