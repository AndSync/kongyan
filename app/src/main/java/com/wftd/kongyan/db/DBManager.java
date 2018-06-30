package com.wftd.kongyan.db;


import android.content.Context;

import com.wftd.kongyan.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DBManager {
    private static final String DATABASES_DIR = "/data/data/com.wftd.kongyan/databases";
    private static final String DATABASE_NAME = "kongyan.db";

    public static void copyDatabaseFile(Context context) {

        File dir = new File(DATABASES_DIR);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File dest = new File(dir, DATABASE_NAME);
        if (dest.exists()) {
            return;
        }
        InputStream in = null;
        FileOutputStream out = null;
        try {
            if (dest.exists()) {
                dest.delete();
            }
            dest.createNewFile();
            in = context.getResources().openRawResource(R.raw.kongyan);
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);
            out = new FileOutputStream(dest);
            out.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
