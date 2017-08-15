//*CID://+v1EeR~:                             update#=   11;       //~v1EeR~
//*****************************************************************//~1A24I~
//v1Ee 2014/12/12 FileDialog:NPE at AjagoModal:actionPerforme by v1Ec//~v1EeI~
//                OnListItemClick has no modal consideration like as Button//~v1EeI~
//                FileDialog from LocalGoFrame is on subthread, OnItemClick of List Item scheduled on MainThread//~v1EeI~
//                AjagoModal do not allocalte countdown latch but subthreadModal flag indicate latch.countDown()//~v1EeI~
//                ==>Change FileDialog to from WaitInput(Modal) to Callback method//~v1EeI~
//v1E9 2014/12/11 (Asgts)//1A4s 2014/12/06 utilize clipboard(view dialog)//~v1E9I~
//*****************************************************************//~1A24I~
package com.Ajagoc.awt;                                              //~2C26R~//~3213R~//~1A24R~
                                                                   //~3324I~
public interface FileDialogI                                       //~v1E9R~
{                                                                  //~1112I~//~3324I~
//*scheduled after FileDialog dismissed*************               //~1A24I~
    int fileDialogSaveCallback(FileDialog Pfd,String Pfilename);                                 //~3324I~//+v1EeR~
    int fileDialogLoadCallback(FileDialog Pfd,String Pfilename);   //+v1EeR~
}                                                                  //~1112I~//~3324I~