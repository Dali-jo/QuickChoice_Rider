package com.example.leejaewon.quickchoice_rider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;


public class picture extends Activity implements View.OnClickListener{
//사진으로 전송시 되돌려 받을 번호
    static int REQUEST_PICTURE=1;
//앨범으로 전송시 돌려받을 번호
    static int REQUEST_PHOTO_ALBUM=2;
//첫번째 이미지 아이콘 샘플
    static String SAMPLEIMG="ic_launcher.png";

    ImageView iv;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//여기에 일단 기본적인 이미지파일 하나를 가져옴
        iv=(ImageView) findViewById(R.id.imgView);

//가져올 사진의 이름을 정함
        findViewById(R.id.getCustom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
//첫번째로 사진가져오기를 클릭하면 또다른 레이아웃것을 다이어로그로 출력해서 선택하게끔 함
        if(v.getId()==R.id.getCustom){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            View customLayout=View.inflate(this,R.layout.content_dialog,null);
            builder.setView(customLayout);

            customLayout.findViewById(R.id.camera).setOnClickListener(this);
            customLayout.findViewById(R.id.photoAlbum).setOnClickListener(this);

            dialog=builder.create();
            dialog.show();
        }else if(v.getId()==R.id.camera){
            dialog.dismiss();
            takePicture();

        }else if(v.getId()==R.id.photoAlbum){
            dialog.dismiss();
            photoAlbum();
        }
    }

    void takePicture() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    File file=new File(Environment.getExternalStorageDirectory(),SAMPLEIMG);

intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    startActivityForResult(intent,REQUEST_PICTURE);
}

    void photoAlbum(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_PHOTO_ALBUM);

    }

    Bitmap loadPicture(){
        File file=new File(Environment.getExternalStorageDirectory(),SAMPLEIMG);

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=4;

        return BitmapFactory.decodeFile(file.getAbsolutePath(),options);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_PICTURE){
                iv.setImageBitmap(loadPicture());
            }
            if(requestCode==REQUEST_PHOTO_ALBUM){
                iv.setImageURI(data.getData());

            }
        }
    }
}
