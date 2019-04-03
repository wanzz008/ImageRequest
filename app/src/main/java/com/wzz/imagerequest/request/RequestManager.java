package com.wzz.imagerequest.request;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class RequestManager {
    private static RequestManager manager;
    public static RequestManager getInstance(){
        if ( manager == null){
            synchronized (RequestManager.class){
                if ( manager == null ){
                    manager = new RequestManager();
                }
            }
        }
        return manager ;
    }

    // 请求队列
    private LinkedBlockingQueue<BitmapRequest> linkedBlockingQueue = new LinkedBlockingQueue<>();

    private BitmapDispatcher[] dispatchers;

    private RequestManager(){
        start();
    }

    /**
     * 将请求对象添加到请求队列
     * @param request
     */
    public void addBitmapRequest(BitmapRequest request){

        if ( ! linkedBlockingQueue.contains( request )){

            try {
                linkedBlockingQueue.put( request );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            Log.e("addBitmapRequest-----","BitmapRequest任务已存在，不用再次添加..." );
        }

    }

    /**
     * 初始化的时候开始所有线程
     */
    private void start() {
        stop();
        startAll();
    }

    public void startAll(){
        // 获取支持的最大线程数
        int count = Runtime.getRuntime().availableProcessors();

        Log.d("wzz-----", "startAll: count:" + count );

        dispatchers = new BitmapDispatcher[count];
        for (int i = 0; i < dispatchers.length; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(linkedBlockingQueue);
            bitmapDispatcher.start();
            dispatchers[i] = bitmapDispatcher;
        }
    }

    /**
     * 停止所有线程
     */
    public void stop(){
        if ( dispatchers != null && dispatchers.length > 0){
            for (BitmapDispatcher dispatcher : dispatchers) {
                if ( !dispatcher.isInterrupted()){
                    dispatcher.interrupt();
                }
            }
        }
    }

}
