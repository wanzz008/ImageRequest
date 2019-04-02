package com.wzz.imagerequest;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wzz.imagerequest.litener.RequestListener;

public class MainActivity extends AppCompatActivity {

    private LinearLayout scrooll_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrooll_line = findViewById(R.id.scrooll_line);

    }



    public void single(View view) {
        showImage();
    }

    public void more(View view) {
        showImage50();
    }

    private void showImage() {

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView( imageView );

        Glide.with(this ).load("https://10.url.cn/eth/ajNVdqHZLLAtBGoU7nQM4hYUAx3PCE7TZYCMExkDibpHgCun6hTpQSRyibSILfh5euJ8xf4gQDtDU/")
                .listener(new RequestListener() {
                    @Override
                    public boolean onException() {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource) {

                        Toast.makeText(MainActivity.this, "自定义处理图片（比如设置圆角）"
                                , Toast.LENGTH_SHORT).show();

                        return false;
                    }
                })
                .loading(R.drawable.loading).into( imageView );

    }

    private void showImage50() {

        for ( int i =0 ; i< 10 ; i++ ){

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scrooll_line.addView( imageView );

            Glide.with(this ).load("https://10.url.cn/eth/ajNVdqHZLLAtBGoU7nQM4hYUAx3PCE7TZYCMExkDibpHgCun6hTpQSRyibSILfh5euJ8xf4gQDtDU/")
                    .loading(R.drawable.loading).into( imageView );

        }

    }


}
