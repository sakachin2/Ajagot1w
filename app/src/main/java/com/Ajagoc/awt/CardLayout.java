package com.Ajagoc.awt;                                            //~1112I~

import com.Ajagoc.AG;

import android.view.View;
                                                                   //~1416I~
//*from ConnectedGoFrame only for CommentPanellayout               //~1416R~
                                                                   //~1416I~
public class CardLayout                                            //~1112R~
{                                                                  //~1112I~
    public CardLayout()                                            //~1112R~
    {                                                              //~1112I~
    }                                                              //~1112I~
    public void show(Panel Ppanel,String Pname)                    //~1217R~
    {                                                              //~1217I~
    	int id;                                                    //~1416I~
        View layout=AG.getCurrentFrame().framelayoutview;            //~1416I~
        id=AG.viewId_Comment;                                      //~1416I~
        View v1=AG.findViewById(layout,id);                        //~1416I~
        id=AG.viewId_AllComments;                                  //~1416I~
        View v2=AG.findViewById(layout,id);                        //~1416I~
        if (Pname.equals("AllComments"))                           //~1416R~
        {                                                          //~1416I~
        	v1.setVisibility(View.GONE);                           //+1416R~
        	v2.setVisibility(View.VISIBLE);                        //~1416I~
        }                                                          //~1416I~
        else            //"Comment"                                //~1416I~
        {                                                          //~1416I~
        	v1.setVisibility(View.VISIBLE);                        //~1416I~
        	v2.setVisibility(View.GONE);                           //+1416R~
        }                                                          //~1416I~
    }                                                              //~1217I~
}//class                                                           //~1112I~
