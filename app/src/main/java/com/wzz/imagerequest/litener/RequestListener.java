package com.wzz.imagerequest.litener;

import android.graphics.Bitmap;

public interface RequestListener {
    boolean onException();
    boolean onResourceReady(Bitmap resource);
}
