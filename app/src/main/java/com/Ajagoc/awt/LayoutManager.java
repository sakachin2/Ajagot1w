package com.Ajagoc.awt;

public interface LayoutManager                                     //+1213I~
{                                                                  //+1213I~
    void addLayoutComponent(String name, Component comp);          //+1213I~
    void removeLayoutComponent(Component comp);                    //+1213I~
    Dimension preferredLayoutSize(Container parent);               //+1213I~
    Dimension minimumLayoutSize(Container parent);                 //+1213I~
    void layoutContainer(Container parent);                        //+1213I~
}                                                                  //+1213I~
