package com.notissu.Util;

import android.content.Context;
import android.widget.Toast;

import com.notissu.R;

/**
 * Created by forhack on 2016-10-21.
 */
public class ResString {
    //숭실대 공지사항 String array의 name
    public static final String RES_SSU_NOTICES = "ssu_notices";
    public static final String RES_SETTINGS_LIST = "settings_list";
    public static final String RES_WITH_US_IMG_NAME= "with_us_img_name";
    public static final String RES_WITH_US_NAME= "with_us_name";
    public static final String RES_WITH_US_POSITION= "with_us_position";
    public static final String RES_WITH_US_EMAIL= "with_us_email";

    private static final String RESOURCE_STRING = "string";
    private static final String RESOURCE_STRING_ARRAY = "array";
    private Context context;

    private static ResString ourInstance = new ResString();


    public static ResString getInstance() {
        return ourInstance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private ResString() {
    }

    //String 으로 resourceId 얻어내는 메소드
    private int getResourceId(String pVariableName, String pResourcename)
    {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //Resource에 있는 String Array를 불러들인다.
    public String[] getStringArray(String name) {
        int resourceID = getResourceId(name,RESOURCE_STRING_ARRAY);
        return context.getResources().getStringArray(resourceID);
    }

    //Resource에 있는 String Array를 불러들인다.
    public String getString(String name) {
        int resourceID = getResourceId(name,RESOURCE_STRING);
        return context.getResources().getString(resourceID);
    }
}
