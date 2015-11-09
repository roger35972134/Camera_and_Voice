package com.example.roger.project_hw;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainList extends Activity {
    ListView listView;
    String filepath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures";
    File file=new File(filepath);
    MyAdapter adapter;
    ArrayList<String> Myfiles=new ArrayList<>();
    ArrayList<String> Files=new ArrayList<>();
    File[] files=file.listFiles();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        listView=(ListView)findViewById(R.id.list);
        for(File mCurrentFile:files){
            if(mCurrentFile.isFile()&& mCurrentFile.getName().contains("jpg"))
            {
                Myfiles.add(mCurrentFile.getName());
                Files.add(mCurrentFile.getAbsolutePath());
            }
        }

        adapter=new MyAdapter(this,Myfiles,Files);
        listView.setAdapter(adapter);

        ImageView camera=(ImageView)findViewById(R.id.captureimage);
        camera.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                File f=dispatchTakePictureIntent();
                Myfiles.add(f.getName());
                Files.add(f.getAbsolutePath());
                adapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View v,int position,long id)
            {
                //String sel=parent.getItemAtPosition(position).toString();
                Intent intent=new Intent();
                intent.setClass(MainList.this,MyPhoto.class);
                Bundle bundle=new Bundle();
                bundle.putString("PATH",Files.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
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


