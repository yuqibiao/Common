package com.afid.utils;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;

import com.smartdevice.aidl.IZKCService;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/6
 */
public class PrinterManager {

    private static final String TAG = "PrinterManager";

    private static IZKCService mIzkcService;

    private  PrinterManager(){

    }

    private static class SingletonHolder{
        public static PrinterManager INSTANCE = new PrinterManager();
    }

    public static  PrinterManager getInstance(IZKCService izkcService){
        mIzkcService = izkcService;
        try {
            mIzkcService.setModuleFlag(0);//打印
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return SingletonHolder.INSTANCE;
    }



    public String getPrinterStatus(){
        try {
            return mIzkcService.getPrinterStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "printText: ==="+e.getMessage() );
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打印文字
     *
     * @param text
     */
    public void printText(String text) {
        printText(text ,0 , 0);
    }

    public void printText(String text , int type , int size){
        try {
            mIzkcService.printTextWithFont(text , type , size);
        } catch (RemoteException e) {
            Log.e(TAG, "printText: ==="+e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * 打印条形码
     *
     * @param text
     * @param codeFormat
     * @param width
     * @param height
     * @param displayText
     */
    public void printBarCode(String text ,int codeFormat, int width , int height , boolean displayText){
        try {
            Bitmap barCode = mIzkcService.createBarCode(text, codeFormat, width, height, displayText);
            mIzkcService.printImageGray(barCode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printBarCode(String text){
        printBarCode(text , 1, 384, 120, true);
    }

    /**
     * 打印二维码
     *
     * @param text
     */
    public void printQRCode(String text , int width , int height) {
        try {
            Bitmap qrCode = mIzkcService.createQRCode(text, width, height);
            mIzkcService.printImageGray(qrCode);
        } catch (RemoteException e) {
            Log.e(TAG, "printText: ==="+e.getMessage() );
            e.printStackTrace();
        }
    }

    public void printQRCode(String text){
        printQRCode(text , 350 ,350);
    }


}
