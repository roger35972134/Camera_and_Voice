package com.example.roger.project_hw;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainList extends Activity {
    String filepath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/HW/Photo";
    MyAdapter adapter;
    MyRecycler myRecycler;
    ArrayList<String> Myfiles=new ArrayList<>();
    ArrayList<String> Files=new ArrayList<>();

    //@Bind(R.id.list) ListView listView;
    @Bind(R.id.id_recyclerview_horizontal) RecyclerView recyclerView;

    @OnClick(R.id.captureimage) void take_photo(){
        File f=dispatchTakePictureIntent();
        Myfiles.add(f.getName());
        Files.add(f.getAbsolutePath());
        myRecycler.notifyDataSetChanged();
    }
    /*@OnItemClick(R.id.list) void itemClick(AdapterView<?> parent, View v, int position, long id){
        Intent intent = new Intent();
        intent.setClass(MainList.this, MyPhoto.class);
        Bundle bundle = new Bundle();
        bundle.putString("PATH", Files.get(position));
        bundle.putString("NAME", Myfiles.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        File file=new File(filepath);
        if(!file.exists())
            file.mkdirs();
        File[] files=file.listFiles();
        for(File mCurrentFile:files){
            if(mCurrentFile.isFile()&& mCurrentFile.getName().contains("jpg"))
            {
                Myfiles.add(mCurrentFile.getName());
                Files.add(mCurrentFile.getAbsolutePath());
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myRecycler=new MyRecycler(this,Files,Myfiles,this);
        myRecycler.setOnItemClickLitener(new MyRecycler.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(MainList.this, MyPhoto.class);
                Bundle bundle = new Bundle();
                bundle.putString("PATH", Files.get(position));
                bundle.putString("NAME", Myfiles.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myRecycler);
        //adapter=new MyAdapter(this,Myfiles,Files,this);
        //listView.setAdapter(adapter);
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + "/HW/Photo/");
        /*if(!storageDir.exists())
            storageDir.mkdirs();*/
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;

    private File dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast toast=Toast.makeText(this,"Error!",Toast.LENGTH_LONG);
                toast.show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile)); //return "file://.....
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                return photoFile;
            }
        }
        return null;
    }

}


