package com.wzz.imagerequest;

import android.content.Context;

import com.wzz.imagerequest.request.BitmapRequest;
import com.wzz.imagerequest.request.RequestManager;

public class Glide {

    public static BitmapRequest with(Context context){
        BitmapRequest bitmapRequest = new BitmapRequest(context);

        return bitmapRequest;
    }
}
