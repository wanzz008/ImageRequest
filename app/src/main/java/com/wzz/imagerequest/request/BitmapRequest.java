package com.wzz.imagerequest.request;

import android.content.Context;
import android.widget.ImageView;

import com.wzz.imagerequest.litener.RequestListener;
import com.wzz.imagerequest.util.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * 封装请求对象
 */
public class BitmapRequest {

    private Context context;

    private String url;
    private int loadingResId;
    private String urlMd5;
    private RequestListener requestListener;

    // 用软引用
    private SoftReference<ImageView> imageViewSoftReference ;

    public BitmapRequest(Context context){
        this.context = context;
    }

    /**
     * 设置监听对象
     * @param requestListener
     * @return
     */
    public BitmapRequest listener(RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    /**
     * 加载中图片
     * @param loadingResId
     * @return
     */
    public BitmapRequest loading(int loadingResId){
        this.loadingResId = loadingResId ;
        return this;
    }

    /**
     * 设置url 并计算md5值 用来添加tag
     * @param url
     * @return
     */
    public BitmapRequest load(String url){
        this.url = url ;
        this.urlMd5 = MD5Utils.toMD5(this.url);
        return this;
    }

    /**
     * 设置ImageView
     * @param imageView
     */
    public void into(ImageView imageView){

        this.imageViewSoftReference = new SoftReference<>(imageView) ;
        imageView.setTag( this.urlMd5 ); // setTag的值为url的Md5

        /** 将此请求添加到请求队列中 */
        RequestManager.getInstance().addBitmapRequest( this );

    }


    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageViewSoftReference.get();
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public int getLoadingResId() {
        return loadingResId;
    }

    public Context getContext() {
        return context;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }


}
