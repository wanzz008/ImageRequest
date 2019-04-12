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

    // LinearLayout
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
        showImage10();
    }

    private void showImage() {

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrooll_line.addView( imageView );


        Glide.with(this ).load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3943723512,1898909937&fm=27&gp=0.jpg")
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

    private void showImage10() {

        scrooll_line.removeAllViews();

        for ( int i =0 ; i< 10 ; i++ ){

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scrooll_line.addView( imageView );

            Glide.with(this )
                    .load("https://10.url.cn/eth/ajNVdqHZLLAtBGoU7nQM4hYUAx3PCE7TZYCMExkDibpHgCun6hTpQSRyibSILfh5euJ8xf4gQDtDU/")
//                    .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555046033070&di=20b705a3568b6d7519e1e81571a246c0&imgtype=0&src=http%3A%2F%2Fpic2.16pic.com%2F00%2F11%2F70%2F16pic_1170466_b.jpg")
//                    .loading(R.drawable.loading)
                    .into( imageView );

        }

    }


}
