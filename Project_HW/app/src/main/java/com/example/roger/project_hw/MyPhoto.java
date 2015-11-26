package com.example.roger.project_hw;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MyPhoto extends Activity{
    String path,name;
    String filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/HW/Voice/";
    ImageView img;
    int[] Xaxis,Yaxis;
    int i=0,count=0,endrecord=0;
    float Scale=1;
    boolean longclick=false,recording=false,end=false;
    Button btn;
    Timer timer=null;
    MediaRecorder mediaRecorder;
    RelativeLayout relativeLayout;
    MediaPlayer mediaPlayer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);

        ImageView rotate=(ImageView)findViewById(R.id.rotate);
        ImageView plus=(ImageView)findViewById(R.id.plus);
        ImageView minus=(ImageView)findViewById(R.id.minus);
        relativeLayout=(RelativeLayout)findViewById(R.id.relative);
        img=(ImageView)findViewById(R.id.photo);
        /*File file=new File(filepath);
        if(!file.exists())
            file.mkdirs();*/

        plus.setOnClickListener(new ImageView.OnClickListener(){
            public void onClick(View v){
                Scale*=1.1;
                img.setImageBitmap(resize(Scale));
            }
        });
        minus.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                Scale*=0.9;
                img.setImageBitmap(resize(Scale));
            }
        });

        rotate.setOnClickListener(new ImageView.OnClickListener(){
            public void onClick(View v)
            {
                i+=90;
                img.setImageBitmap(rotating());
            }
        });

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        path=bundle.getString("PATH");
        name=bundle.getString("NAME");
        File floder=new File(filepath+name);
        if(!floder.exists())
            floder.mkdirs();

        img.setImageBitmap(BitmapFromFile(path));
        img.setOnTouchListener(new ImageView.OnTouchListener() {
            int x=0,y=0;
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(!recording)
                        {
                            timer=new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    longclick=true;
                                }
                            },1000);
                        }
                        else
                        {
                            endrecord++;
                            if(endrecord==2)
                            {
                                endrecord=0;
                                end=true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                            if(longclick){
                                longclick=false;
                                x = (int) event.getX();
                                y = (int) event.getY();
                                recorder(x,y);
                                timer.cancel();
                            }
                            if(end)
                            {
                                endrecording();
                                end = false;
                                btn = new Button(MyPhoto.this);
                                btn.setId(count);
                                btn.setText("R" + count);
                                btn.setX(x);
                                btn.setY(y+200);
                                btn.setOnClickListener(new Button.OnClickListener() {
                                    public void onClick(View v) {

                                        String audiopath = filepath+name+"/"+v.getX()+"_"+v.getY()+".amr";
                                        mediaPlayer = new MediaPlayer();
                                        try {
                                            mediaPlayer.reset();
                                            mediaPlayer.setDataSource(audiopath);
                                            mediaPlayer.prepare();
                                            mediaPlayer.start();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                count++;
                                relativeLayout.addView(btn);
                            }
                            break;

                }
                return true;
            }
        });
    }
    public void recorder(int x,int y)
    {
        recording=true;
        Toast toast=Toast.makeText(MyPhoto.this,"recording",Toast.LENGTH_LONG);
        toast.show();
        try{
            String filename=x+"_"+y+".amr";
            mediaRecorder=new MediaRecorder();
            File record=new File(filepath+name+"/"+filename);
            //Source
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //Format
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //Encoder
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //path
            mediaRecorder.setOutputFile(record.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void endrecording() {
            Toast toast=Toast.makeText(MyPhoto.this,"end recording",Toast.LENGTH_LONG);
            toast.show();
            mediaRecorder.stop();
            mediaRecorder.release();
            recording=false;
    }
    public Bitmap resize(float Scale)
    {
        Bitmap bmp=BitmapFromFile(path);
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        Matrix matrix=new Matrix();
        matrix.postScale(Scale,Scale);

        return Bitmap.createBitmap(bmp,0,0,width,height,matrix,true);
    }
    public Bitmap rotating()
    {
        Bitmap bmp=BitmapFromFile(path);
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        Matrix matrix=new Matrix();
        matrix.setRotate(i%360);
        return Bitmap.createBitmap(bmp,0,0,width,height,matrix,true);
    }
    public Bitmap BitmapFromFile(String name)
    {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(name, option);

        //The new size to decode to
        final int NEW_SIZE=100;
        //Now we have image width and height. We should find the correct scale value. (power of 2)

        int width=option.outWidth;
        int height=option.outHeight;
        int scale=1;
        while(true){
            if(width/2<NEW_SIZE || height/2<NEW_SIZE)
                break;
            width/=2;
            height/=2;
            scale++;

        }

        //Decode again with inSampleSize

        option = new BitmapFactory.Options();

        option.inSampleSize=scale;

        return BitmapFactory.decodeFile(name, option);
    }
}
