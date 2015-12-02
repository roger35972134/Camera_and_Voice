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
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainList extends Activity {
    static final int REQUEST_TAKE_PHOTO = 1;
    String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/HW/Photo";
    MyRecycler myRecycler;
    ArrayList<String> Myfiles = new ArrayList<>();
    ArrayList<String> Files = new ArrayList<>();
    File f;
    boolean flag = false;
    //@Bind(R.id.list) ListView listView;
    @Bind(R.id.id_recyclerview_horizontal) RecyclerView recyclerView;
    String mCurrentPhotoPath;

    @OnClick(R.id.captureimage)
    void take_photo() {
        f = dispatchTakePictureIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        File file = new File(filepath);
        if (!file.exists())
            file.mkdirs();
        File[] files = file.listFiles();
        for (File mCurrentFile : files) {
            if (mCurrentFile.isFile() && mCurrentFile.getName().contains("jpg")) {
                Myfiles.add(mCurrentFile.getName());
                Files.add(mCurrentFile.getAbsolutePath());
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myRecycler = new MyRecycler(this, Files, Myfiles, this);
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Myfiles.add(f.getName());
            Files.add(f.getAbsolutePath());
            myRecycler.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + "/HW/Photo/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

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
                Toast toast = Toast.makeText(this, "Error!", Toast.LENGTH_LONG);
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


