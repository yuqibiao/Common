/**
 * 
 */
package com.smartdevice.aidltestdemo.common;

import java.io.File;

import android.os.Environment;

public class ClientGlobal {
    
    public static class Path {
        public static final String SDCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();

        public static final String ClientDir = SDCardDir + File.separator + "ZkcService";
    }

}
