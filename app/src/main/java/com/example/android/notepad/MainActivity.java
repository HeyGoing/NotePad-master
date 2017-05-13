package com.example.android.notepad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class MainActivity extends Activity {
    private ImageView iv;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        iv = (ImageView)findViewById(R.id.iv);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        // 获取创建的宽度和高度
        getWindowManager().getDefaultDisplay()
                .getRealMetrics(displayMetrics);
        //this.iv = (ImageView) this.findViewById(R.id.iv);
        // 创建一张空白图片
        baseBitmap = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888);
        // 创建一张画布
        canvas = new Canvas(baseBitmap);
        // 画布背景为灰色
        canvas.drawColor(Color.GRAY);
        // 创建画笔
        // 设置画笔的颜色
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        // 设置画笔风格
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        // 反锯齿
        paint.setAntiAlias(true);
        paint.setDither(true);

        iv.setImageBitmap(baseBitmap);

        iv.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取手按下时的坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取手移动后的坐标
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        // 在开始和结束坐标间画一条线
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        // 实时更新开始坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        iv.setImageBitmap(baseBitmap);
                        break;
                }
                return true;
            }
        });
    }
    public void save(View view) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    111+ ".jpg");
            OutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(Environment
                    .getExternalStorageDirectory())));

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
      //  String path=Environment.getExternalStorageDirectory()+"01.jpg";
       /* String filepath = "/sdcard/111.jpg";
        File mFile=new File(filepath);
        //若该文件存在
        if (mFile.exists()) {
            Bitmap bitmap= BitmapFactory.decodeFile(filepath);
            iv.setImageBitmap(bitmap);
        }*/


    }
}
