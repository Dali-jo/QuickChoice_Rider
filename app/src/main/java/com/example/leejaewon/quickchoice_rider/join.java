package com.example.leejaewon.quickchoice_rider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




public class join extends AppCompatActivity implements View.OnClickListener {

    protected static final int REQUEST_CQPTRUE = 10;
    protected static final int REQUEST_GALLERY = 11;

    File photoFile = null;
    String photoPath = null;
    Uri photoUri = null;
    String fileAddr = null;

    File photoFile2 = null;
    String photoPath2 = null;
    Uri photoUri2 = null;
    String fileAddr2 = null;

    EditText id;
    EditText pw;
    EditText pw_check;
    EditText name;
    EditText phonNumber;

    RadioButton bt_bike;
    RadioButton bt_rabo;
    RadioButton bt_ton;
    RadioButton bt_damas;
    RadioButton bt_ban;

    Button take_picture1;
    Button take_picture2;

    ImageView join_myselca;
    ImageView join_mylicense;

    Button bt_ok;
Button bt_cancle;
    int car;

    RadioGroup group_car;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_join);

                if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/godic.ttf");
        TextView textView1 = (TextView)findViewById(R.id.join_name);
        TextView textView2 = (TextView)findViewById(R.id.join_id);
        TextView textView3 = (TextView)findViewById(R.id.join_pw);
        TextView textView4 = (TextView)findViewById(R.id.join_pw_check);
        TextView textView5 = (TextView)findViewById(R.id.join_phone);
        TextView textView6= (TextView)findViewById(R.id.join_bike);
        TextView textView7 = (TextView)findViewById(R.id.join_damas);
        TextView textView8 = (TextView)findViewById(R.id.join_rabo);
        TextView textView9= (TextView)findViewById(R.id.join_ban);
        TextView textView10 = (TextView)findViewById(R.id.join_ton);

        TextView textView13 = (TextView)findViewById(R.id.join_ok);
        TextView textView14 = (TextView)findViewById(R.id.join_cancle);

        ImageView join_myselca=(ImageView)findViewById(R.id.join_myselca);
        ImageView join_mylicense=(ImageView)findViewById(R.id.join_mylicense);

        take_picture1= (Button)findViewById(R.id.join_getImg1);
        take_picture2= (Button)findViewById(R.id.join_getImg2);



        take_picture1.setOnClickListener(this);
        take_picture2.setOnClickListener(this);






        textView1.setTypeface(typeface1);
        textView2.setTypeface(typeface1);
        textView3.setTypeface(typeface1);
        textView4.setTypeface(typeface1);
        textView5.setTypeface(typeface1);
        textView6.setTypeface(typeface1);
        textView7.setTypeface(typeface1);
        textView8.setTypeface(typeface1);
        textView9.setTypeface(typeface1);
        textView10.setTypeface(typeface1);

        textView13.setTypeface(typeface1);
        textView14.setTypeface(typeface1);

        id=(EditText) findViewById(R.id.join_id);
        pw=(EditText) findViewById(R.id.join_pw);
        pw_check=(EditText) findViewById(R.id.join_pw_check);
        name=(EditText) findViewById(R.id.join_name);
        phonNumber=(EditText) findViewById(R.id.join_phone);
        bt_ban=(RadioButton)findViewById(R.id.join_ban);
        bt_rabo=(RadioButton)findViewById(R.id.join_rabo);
        bt_bike=(RadioButton)findViewById(R.id.join_bike);
        bt_ton=(RadioButton)findViewById(R.id.join_ton);
        bt_damas=(RadioButton)findViewById(R.id.join_damas);
        bt_ok=(Button)findViewById(R.id.join_ok);
        bt_cancle=(Button)findViewById(R.id.join_cancle);

        group_car=(RadioGroup)findViewById(R.id.group_car);
        listener lis = new listener();

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(bt_bike.isChecked()) car=0;
                    if(bt_damas.isChecked()) car=1;
                    if(bt_rabo.isChecked()) car=2;
                    if(bt_ban.isChecked()) car=3;
                    if(bt_ton.isChecked()) car=4;
                    String result;
                    CustomTask task = new CustomTask();
                    result = task.execute(id.getText().toString(),pw.getText().toString(),name.getText().toString(),phonNumber.getText().toString(),String.valueOf(car)).get();
                    Log.i("리턴 값",result);
                    Toast.makeText(getBaseContext(),result, Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                }
            }
        });
        bt_ban.setOnClickListener(lis);
        bt_bike.setOnClickListener(lis);
        bt_damas.setOnClickListener(lis);
        bt_rabo.setOnClickListener(lis);
        bt_ton.setOnClickListener(lis);




    }

    @Override
    public void onClick(View v) {
        if (v.getId() == take_picture1.getId()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;

                try {
                    photoFile = createFile();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "createImageFile Failed", Toast.LENGTH_LONG).show();
                }


                if (photoFile != null) {
                    photoUri = Uri.fromFile(photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, REQUEST_CQPTRUE);


                }
            }

        }
        if (v.getId() == take_picture2.getId()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile2 = null;

                try {
                    photoFile2 = createFile();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "createImageFile Failed", Toast.LENGTH_LONG).show();
                }


                if (photoFile2 != null) {
                    photoUri2 = Uri.fromFile(photoFile2);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri2);
                    startActivityForResult(intent, REQUEST_CQPTRUE);


                }
            }

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CQPTRUE:
                if (resultCode == RESULT_OK) {


                    String imagePath = photoUri.getPath();
                    Toast.makeText(this,"요기", Toast.LENGTH_LONG).show();
                    Bitmap photo = BitmapFactory.decodeFile(imagePath);
                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(photoPath);
                        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//                        int exifDegree = exifOrientationToDegrees(exifOrientation);
                        int exifDegree = 90;
                        Toast.makeText(this, exifOrientation + "," + exifDegree, Toast.LENGTH_LONG).show();
                        photo = rotate(photo, exifDegree);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "취소됨", Toast.LENGTH_LONG).show();
                    }


                    //회전시킨 이미지를 저장
                    saveExifFile(photo, photoPath);


                    //비트맵 메모리 반환
//                    photo.recycle();
//

//                    join_myselca.setScaleType(ImageView.ScaleType.FIT_CENTER);
//
//                    join_myselca.setImageBitmap(photo);


                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(photoUri);
                    Toast.makeText(this, photoUri.toString(), Toast.LENGTH_LONG).show();
                    this.sendBroadcast(mediaScanIntent);

                    String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();

                    Toast.makeText(this, sdPath, Toast.LENGTH_LONG).show();


                    String jspUri = "http://220.122.180.160:8080/riderface.jsp";
                    DoFileUpload(jspUri, fileAddr);


                }
                break;


        }
    }

    class listener implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.join_bike:
                    car=0;
                    break;
                case R.id.join_damas:
                    car=1;
                    break;
                case R.id.join_rabo:
                    car=2;
                    break;
                case R.id.join_ban:
                    car=3;
                    break;
                case R.id.join_ton:
                    car=4;
                    break;
            }
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/riderjoin.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&id="+strings[0]+"&pw="+strings[1]+"&name="+strings[2]+"&phon="+strings[3]+"&car="+strings[4];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }



    }

//    TextWatcher watcher=new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            if(id.isFocusable()){
//                login.CustomTask1 task= new login.CustomTask1();
//
//
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    }

    public File createFile() throws IOException {

        String imageFileName = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        Toast.makeText(this, imageFileName, Toast.LENGTH_LONG).show();
        File storageDir = new File(Environment.getExternalStorageDirectory(), imageFileName);
        photoPath = storageDir.getAbsolutePath();
        return storageDir;


    }

    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    public Bitmap rotate(Bitmap bitmap, int degrees) {
        Bitmap retBitmap = bitmap;

        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    retBitmap = converted;
                    bitmap.recycle();
                    bitmap = null;
                }
            } catch (OutOfMemoryError ex) {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
                Toast.makeText(this, "회전 못했음", Toast.LENGTH_LONG).show();
            }
        }
        return retBitmap;
    }

    public void saveExifFile(Bitmap imageBitmap, String savePath) {
        FileOutputStream fos = null;
        File saveFile = null;

        try {
            saveFile = new File(savePath);
            fos = new FileOutputStream(saveFile);
            //원본형태를 유지해서 이미지 저장
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fileAddr = saveFile.getPath().toString();
            Toast.makeText(this, "경로 : " + saveFile.getPath().toString(), Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            //("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            //("IOException", e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
            }
        }
    }


    public void DoFileUpload(String apiUrl, String absolutePath) {

        HttpFileUpload(apiUrl, "", absolutePath);
        Log.d("캡쳐", "load1");


    }


    public void HttpFileUpload(String urlString, String params, String fileName) {


        String lineEnd = "\r\n";

        String twoHyphens = "--";

        String boundary = "*****";

        try {

            File sourceFile = new File(fileName);

            DataOutputStream dos;

            if (!sourceFile.isFile()) {

                Log.e("uploadFile", "Source File not exist :" + fileName);

            } else {

                FileInputStream mFileInputStream = new FileInputStream(sourceFile);

                URL connectUrl = new URL(urlString);
                Toast.makeText(this, "loding", Toast.LENGTH_LONG).show();

                // open connection

                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();

                conn.setDoInput(true);

                conn.setDoOutput(true);

                conn.setUseCaches(false);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Connection", "Keep-Alive");

                conn.setRequestProperty("ENCTYPE", "multipart/form-data");

                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                conn.setRequestProperty("uploaded_file", fileName);

                // write data

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);


                int bytesAvailable = mFileInputStream.available();

                int maxBufferSize = 1024 * 1024;

                int bufferSize = Math.min(bytesAvailable, maxBufferSize);


                byte[] buffer = new byte[bufferSize];

                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);


                // read image

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);

                    bytesAvailable = mFileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);

                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                }


                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                mFileInputStream.close();

                dos.flush(); // finish upload...

                if (conn.getResponseCode() == 200) {

                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");

                    BufferedReader reader = new BufferedReader(tmp);

                    StringBuffer stringBuffer = new StringBuffer();

                    String line;

                    while ((line = reader.readLine()) != null) {

                        stringBuffer.append(line);

                    }

                }

                mFileInputStream.close();

                dos.close();

                Toast.makeText(this, "완료", Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    public void join_Cancle(View v){
        finish();
    }
}
