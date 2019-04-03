package com.wzz.imagerequest.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.wzz.imagerequest.MyApplication;
import com.wzz.imagerequest.cache.DoubleLruCache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

public class BitmapDispatcher extends Thread {

    private LinkedBlockingQueue<BitmapRequest> queue ;
    private Handler handler = new Handler(Looper.getMainLooper());

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> linkedBlockingQueue){
        this.queue = linkedBlockingQueue ;
    }

    @Override
    public void run() {

        //   阻塞队列
        while ( !interrupted()){
            try {

                /** 从队列不断轮询对象，进行处理 */
                BitmapRequest bitmapRequest = queue.take();

                Log.d("wzz-----", "run: 获取到quest.... " );
                if ( bitmapRequest != null ){
                    // 先展示 loading
                    showImageLoading(bitmapRequest);
                    // 从三级缓存里查找图片
                    Bitmap bitmap = requestBitmap(bitmapRequest);
                    // 显示UI
                    showImage(bitmapRequest , bitmap);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImage(final BitmapRequest bitmapRequest, final Bitmap bitmap) {

        final ImageView imageView = bitmapRequest.getImageView();

        // 防止图片错位
        if ( imageView != null && bitmap != null && imageView.getTag().equals( bitmapRequest.getUrlMd5() )){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap( bitmap );
                }
            });
        }

        /**
         * 监听图片网络请求是否成功
         */
        if (bitmapRequest.getRequestListener() != null) {
            if (bitmap != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bitmapRequest.getRequestListener().onResourceReady(bitmap);
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bitmapRequest.getRequestListener().onException();
                    }
                });
            }
        }
    }

    DoubleLruCache mCache = DoubleLruCache.getInstance(MyApplication.getInstance());
    /**
     * 从三级缓存里查找图片
     * @param bitmapRequest
     * @return
     */
    private Bitmap requestBitmap(BitmapRequest bitmapRequest) {
        // 先从缓存取
        Bitmap bitmap = mCache.get(bitmapRequest);
        if ( bitmap != null ){
            return bitmap;
        }
        // 网络 下载一张图片 bitmap  bitmap
        bitmap = downloadImage(bitmapRequest.getUrl());

        // 添加到缓存
        if ( bitmap != null ){
            mCache.put( bitmapRequest , bitmap );
        }

        return bitmap;
    }


    /**
     * 下载图片
     * @param uri
     * @return
     */
    private Bitmap downloadImage(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try
        {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap= BitmapFactory.decodeStream(is);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
            }

            try
            {
                if (fos != null)
                    fos.close();
            } catch (IOException e)
            {
            }
        }
        return bitmap;
    }

    /**
     * 加载显示中的图片
     * @param bitmapRequest
     */
    private void showImageLoading( BitmapRequest bitmapRequest ) {

        final int loadingResId = bitmapRequest.getLoadingResId();
        if ( loadingResId > 0){
            final ImageView imageView = bitmapRequest.getImageView();
            if ( imageView != null ){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource( loadingResId );
                    }
                });
            }

        }

    }
}
