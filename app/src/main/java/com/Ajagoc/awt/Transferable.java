package com.Ajagoc.awt;                                                //~1108R~//~1109R~

import java.io.IOException;

public interface Transferable                                      //~1212I~
{                                                                  //~1212I~
//    public DataFlavor[] getTransferDataFlavors();                //~1212R~
//    public boolean isDataFlavorSupported(DataFlavor flavor);     //~1212R~
//    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException;//~1212R~//+1401R~
      public Object getTransferData(DataFlavor flavor) throws UnsupportedOperationException, IOException;//+1401I~
}//interface                                                       //~1212R~
