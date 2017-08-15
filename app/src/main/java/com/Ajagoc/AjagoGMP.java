//*CID://+1AfsR~:                             update#=   16;       //+1AfsR~
//************************************************************************//~v106I~
//1Afs 2014/09/01 Asset file open err (getAsset().openFD:it is probably compressed) for Agnugo when size over 64k//+1AfsI~
//v1C8 2014/09/01 Agnugo.png-->Agnugo.zip(unzip at first time)     //~v1C8I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//v1B5 2014/07/30 gmp issues errmsg "GMP server respond err message"//~v1B5I~
//1066:121128 GMP connection run exception(gnugo end by AGUNGO_PARM open failed)//~v106I~
//************************************************************************//~v106I~
package com.Ajagoc;                                                 //~1110I~
                                                                   //~1110I~
                                                                   //~1110I~
import java.io.File;
import java.io.IOException;

import jagoclient.Dump;
import jagoclient.Global;

                                                                   //~1110I~
public class AjagoGMP                                              //~1510R~
{                                                                  //~1110I~
	public static final String GMP_PGMID="Agnugo";                 //~1511I~
//  public static final String GMP_PARM="--mode gmp";               //~1511I~//~v106R~
	public static final String GMP_PARM_ENV="AGNUGO_PARM";	//same on Agnugo.c//~1511I~
	public static final String GMP_PARM_FILE="AGNUGO_PARM";	//same on Agnugo.c//~1511I~
    private static final String PREFKEY_GMPSERVER_ZIPSIZE="GMPserverZipSize";//~v1C8R~
                                                                   //~1511I~
	public static void setup()                                     //~1511R~
	{                                                              //~1401I~
        String path,pgm;                                           //~1511R~
        int pgmid;                                                 //~1511R~
        File f;                                                    //~1511I~
    //***************************                                  //~1401I~
        if (Dump.Y) Dump.println("AjagoGMP:setup");                //~1511I~
        path=AjagoProp.getDataFileFullpath(GMP_PGMID);              //~1511I~
		pgm=Global.getParameter("gmpserver",GMP_PGMID);           //~1511I~
        if (pgm.equals(GMP_PGMID))                                 //~1511I~
        {                                                          //~1511I~
            pgmid=1;                                               //~1511I~
            pgm=path;                                              //~1511I~
        }                                                          //~1511I~
        else                                                       //~1511I~
        if (pgm.startsWith(GMP_PGMID+" "))                         //~1511I~
        {                                                          //~1511I~
            pgmid=2;                                               //~1511I~
            pgm=path+pgm.substring(GMP_PGMID.length());             //~1511I~
        }                                                          //~1511I~
        else                                                       //~1511I~
        if (pgm.equals(path))                                      //~1511I~
            pgmid=3;                                               //~1511I~
        else                                                       //~1511I~
        if (pgm.startsWith(path+" "))                              //~1511I~
            pgmid=4;                                               //~1511I~
        else                                                       //~1511I~
        	pgmid=0;                                               //~1511I~
        if (pgmid==0)	                                           //~1511I~
        {                                                          //~1511I~
        	f=new File(pgm);                                       //~1511R~
            if (!f.exists())                                        //~1511I~
            {                                                      //~1511I~
            	pgm=path;                                          //~1511R~
	            pgmid=1;                                           //~1511I~
            }                                                      //~1511I~
        }                                                          //~1511I~
        if (pgmid==1||pgmid==2)                                    //~1511R~
        {                                                          //~1511I~
			Global.setParameter("gmpserver",pgm);  //notify to GMPConnection//~1511R~
	        if (Dump.Y) Dump.println("AjagoGMP:setup path="+pgm);  //~1511R~
        }                                                          //~1511I~
        if (pgmid!=0)	//Agnugo                                   //~1511I~
        {                                                          //~1511I~
        	f=new File(path);                                      //~1511I~
            if (!f.exists())                                       //~1511I~
            {                                                      //~1511I~
            	touchGMP(GMP_PGMID);	//avoid not found error at GMPconnector//~1511R~
            }                                                      //~1511I~
        }                                                          //~1511I~
    }                                                              //~1511I~
	public static String checkDefaultProgram(String Ppgm)          //~1516I~
	{                                                              //~1516I~
        String fpath,pgm;                             //+1516I~                                          //~1516I~
    //***************************                                  //~1516I~
        if (Dump.Y) Dump.println("AjagoGMP:checkDefaultProgram cmd="+Ppgm);//~1516I~
        pgm=Ppgm;                                                  //~1516I~
        fpath=AjagoProp.getDataFileFullpath(GMP_PGMID);            //~1516I~
        if (pgm.equals(GMP_PGMID))                                 //~1516I~
        	pgm=fpath;                                             //~1516I~
        else                                                       //~1516I~
        if (!pgm.equals(fpath))                                    //~1516I~
        {                                                          //~1516I~
	        if (Dump.Y) Dump.println("AjagoGMP:checkDefaultProgram return pgm="+pgm);//~1516I~
        	return pgm;	 //!Agnugo                                 //~1516I~
        }                                                          //~1516I~
        File f=new File(pgm);                                      //~1516I~
        if (!f.exists())                                           //~1516I~
        {                                                          //~1516I~
        	touchGMP(GMP_PGMID);	//avoid not found error at GMPconnector//~1516I~
        }                                                          //~1516I~
        if (Dump.Y) Dump.println("AjagoGMP:checkDefaultProgram return pgm="+pgm);//~1516I~
        return pgm;                                                //~1516I~
	}                                                              //~1516I~
	public static String checkProgram(String Ppgm)                 //~1511R~
	{                                                              //~1511I~
        String path,pgm,assetfnm;                             //~1511R~
        long fsz,fsza;                                             //~1511R~
        String defaultpgm;                                         //~v1C8I~
    //***************************                                  //~1511I~
        if (Dump.Y) Dump.println("AjagoGMP:checkProgram cmd="+Ppgm);//~1516R~
        pgm=Ppgm;	                                               //~1511I~
//      if (pgm.equals(GMP_PGMID))                                 //~1511I~//~v106R~
//      	pgm=AjagoProp.getDataFileFullpath(GMP_PGMID);          //~1515R~
//      	pgm=AjagoProp.getDataFileFullpath(GMP_PGMID)+" "+GMP_PARM;//~1515I~//~v106R~
//      path=AjagoProp.getDataFileFullpath(GMP_PGMID);             //~1511I~//~v1C8R~
        path=AjagoProp.getDataFileFullpath("");                    //~v1C8I~
		defaultpgm=AjagoProp.getDataFileFullpath(GMP_PGMID);       //~v1C8I~
        if (pgm.startsWith(path))                                  //~1511R~
        {                                                          //~1511I~
//      	fsz=AjagoProp.getDataFileSize(GMP_PGMID);              //~1511I~//~v1C8R~
        	long fszunzip=AjagoProp.getDataFileSize(GMP_PGMID);         //~v1C8I~
        	fsz=AjagoProp.getPreference(PREFKEY_GMPSERVER_ZIPSIZE,0);//~v1C8I~
//          assetfnm=GMP_PGMID+".png";                             //~1511I~//~v1C8R~
//          assetfnm=GMP_PGMID+".zip";                             //+1AfsR~
            assetfnm=GMP_PGMID+".png";                             //+1AfsI~
        	fsza=AjagoProp.getAssetFileSize(assetfnm);             //~1511I~
//          if (fsz!=fsza)  //updated                              //~1511R~//~v1C8R~
            if (fsz!=fsza||fszunzip<=0)  //updated or unzip file was deleted//~v1C8I~
            {                                                      //~1511I~
//                if (!AjagoProp.copyAssetToDataDir(assetfnm,GMP_PGMID))//png:avaoid compress(for >1MB file;cause IOException)//~1511R~//~v1C8R~
//                {                                                  //~1511I~//~v1C8R~
//                    AjagoView.showToast(R.string.AgnugoCopyFailed);//~1511I~//~v1C8R~
//                    return pgm;                                    //~1511R~//~v1C8R~
//                }                                                  //~1511I~//~v1C8R~
		        if (Dump.Y) Dump.println("AjagoGMP:copy to datadir success");//~1511I~
//              chmodX(path,"744");                                //~1511R~//~v1C8R~
				AjagoProp.unzipAsset(path,assetfnm,0777);          //~v1C8I~
	        	AjagoProp.putPreference(PREFKEY_GMPSERVER_ZIPSIZE,(int)fsza);//~v1C8I~
            }                                                      //~1511I~
//  		pgm=path;                                              //~1511R~//~v1C8R~
    		pgm=defaultpgm;                                        //~v1C8I~
        }                                                          //~1511I~
                                  //~1511I~
        if (Dump.Y) Dump.println("AjagoGMP:checkProgram normal return cmd="+pgm);//~1511R~
        return pgm;                                                //~1511R~
	}                                                              //~1511I~
//***********************************                              //~1511I~
//  private static boolean chmodX(String Pfname,String Pmask)      //~1511R~//~v1B6R~
    public  static boolean chmodX(String Pfname,String Pmask)      //~v1B6I~
    {                                                              //~1511I~
        String cmd="chmod "+Pmask+" "+Pfname;                      //~1511R~
        return execShell(cmd);                                     //~1511R~
    }                                                              //~1511I~
    private static boolean touchGMP(String Pfname)                 //~1511I~
    {                                                              //~1511I~
		byte[] nulldata=new byte[0];                               //~1511I~
		boolean rc=AjagoProp.writeOutputData(Pfname,nulldata);               //~1511R~
        return rc;                                                 //~1511I~
    }                                                              //~1511I~
//*****************                                                //~1511I~
    private static boolean execShell(String Pcmd)                  //~1511I~
    {                                                              //~1511I~
    	if (Dump.Y) Dump.println("AjagoGMP:execShell cmd"+Pcmd);   //~1511I~
    	Runtime rt=Runtime.getRuntime();                           //~1511I~
        Process p=null;                                            //~1511I~
        boolean rc=false;                                          //~1511I~
        try                                                        //~1511I~
        {                                                          //~1511I~
        	p=rt.exec(Pcmd);                                        //~1511I~
           	p.waitFor();                                           //~1511I~
            rc=true;                                               //~1511I~
        }                                                          //~1511I~
        catch (IOException e)                                      //~1511I~
        {                                                          //~1511I~
           	Dump.println(e,"AjagoGMP:execShell exception:"+Pcmd);  //~1511I~
		}                                                          //~1511I~
		catch (InterruptedException e)                             //~1511I~
		{                                                          //~1511I~
			Dump.println(e,"AjagoGMP:execShell interrupted exception:"+Pcmd);//~1511I~
		}                                                          //~1511I~
       	if (Dump.Y) Dump.println("AjagoGMP:chmod cmd return rc="+rc);//~1511I~
        return rc;                                                 //~1511I~
    }                                                              //~1511I~
    //*****************                                            //~1512I~
    public  static void warning(int Pmsgid,byte[] Pbyte,int Plen)  //~1513R~
    {                                                              //~1512I~
    	int rid=0;                                                 //~1513I~
        if (Dump.Y)	Dump.println("AjagoGMP:warning msgid="+Pmsgid);//~v1B5I~
    	if (Pmsgid==1)	//no Answer                                //~1513I~
        	rid=R.string.GMP_NoAnswer;                             //~1513I~
        else                                                       //~1513I~
    	if (Pmsgid==2)	//sudden deth                              //~1513R~
        	rid=R.string.GMP_Death;                                //~1513I~
        else                                                       //~1514I~
    	if (Pmsgid==3)	//sudden deth                              //~1514I~
        	rid=R.string.GMP_ErrMsg;                             //~1514I~
        if (rid>0)                                                 //~1513I~
        {                                                          //~1513I~
        	String stderrmsg="";                                   //~1513I~
            if (Plen>0)                                            //~1513I~
            {                                                      //~1513I~
                try                                                //~1513R~
                {                                                  //~1513R~
                    stderrmsg=new String(Pbyte,0,Plen,"UTF-8");    //~1513R~
                }                                                  //~1513R~
                catch(Exception e)                                 //~1513R~
                {                                                  //~1513R~
                    Dump.println(e,"AjagoGMP:warning stderr byte2String");//~1513R~
                }                                                  //~1513R~
            }                                                      //~1513I~
//          AjagoView.showToastLong(rid,":"+stderrmsg);            //~1514R~//~v1B5R~
			AjagoAlert.simpleAlertDialog(null/*Pcallback*/,null/*Ptitle*/,AG.resource.getString(rid)+":"+stderrmsg,AjagoAlert.BUTTON_POSITIVE);//~v106I~//~v1B5I~
        }                                                          //~1513I~
    }                                                              //~1512I~
}//class                                                           //~1110I~
