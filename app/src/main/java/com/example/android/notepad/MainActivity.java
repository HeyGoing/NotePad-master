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
        // ��ȡ�����Ŀ�Ⱥ͸߶�
        getWindowManager().getDefaultDisplay()
                .getRealMetrics(displayMetrics);
        //this.iv = (ImageView) this.findViewById(R.id.iv);
        // ����һ�ſհ�ͼƬ
        baseBitmap = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888);
        // ����һ�Ż���
        canvas = new Canvas(baseBitmap);
        // ��������Ϊ��ɫ
        canvas.drawColor(Color.GRAY);
        // ��������
        // ���û��ʵ���ɫ
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        // ���û��ʷ��
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        // �����
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
                        // ��ȡ�ְ���ʱ������
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // ��ȡ���ƶ��������
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        // �ڿ�ʼ�ͽ�������仭һ����
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        // ʵʱ���¿�ʼ����
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
        //�����ļ�����
        if (mFile.exists()) {
            Bitmap bitmap= BitmapFactory.decodeFile(filepath);
            iv.setImageBitmap(bitmap);
        }*/


    }
}
