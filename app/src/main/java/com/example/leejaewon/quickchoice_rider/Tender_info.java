package com.example.leejaewon.quickchoice_rider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LeeJaeWon on 2017-05-25.
 */

public class Tender_info extends AppCompatActivity {
    protected static final int REQUEST_CQPTRUE = 1;
    protected static final int REQUEST_GALLERY = 2;
    File photoFile = null;
    String photoPath = null;
    Uri photoUri = null;
    String fileAddr = null;
    private TextView start;
    private TextView desti;
    private TextView pickup;
    private TextView category;
    private TextView money;

    private Button tender;

    private String id;
    private String no;
    private TMapView tMapView;
    MapView locationview;

    private Double startlati=0.0;
    private Double startlongi=0.0;
    private Double destinationlati=0.0;
    private Double destinationlongi=0.0;
    private String dis="0";

    Button sendpicture;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tender_info);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        start = (TextView) findViewById(R.id.tender_info_start);
        desti = (TextView) findViewById(R.id.tender_info_desti);
        pickup = (TextView) findViewById(R.id.tender_info_pickup);
        category = (TextView) findViewById(R.id.tender_info_category);
        money = (TextView) findViewById(R.id.tender_info_money);
        tender = (Button) findViewById(R.id.tender_info_tdbutton);
        sendpicture=(Button)findViewById(R.id.send_picture);


        Intent intent=this.getIntent();
        id=intent.getStringExtra("id");
        no=intent.getStringExtra("no");
        start.setText(intent.getStringExtra("start"));
        desti.setText(intent.getStringExtra("desti"));
        category.setText(intent.getStringExtra("category"));
        pickup.setText(intent.getStringExtra("pickup"));
        money.setText(intent.getStringExtra("money"));
        startlati=Double.valueOf(intent.getStringExtra("startlati"));
        startlongi=Double.valueOf(intent.getStringExtra("startlongi"));
        destinationlati=Double.valueOf(intent.getStringExtra("destinationlati"));
        destinationlongi=Double.valueOf(intent.getStringExtra("destinationlongi"));
        dis=intent.getStringExtra("disable");
        sendpicture.setVisibility(View.GONE);
        if(dis.equals("1")) {
            sendpicture.setVisibility(View.VISIBLE);
        }
        tender.setVisibility(View.GONE);




        listener lis = new listener();
        tender.setOnClickListener(lis);
        sendpicture.setOnClickListener(lis);

        locationview=(MapView)findViewById(R.id.mapView);
        tMapView= new TMapView(this);
//        RelativeLayout mapView = new RelativeLayout(this);
        tMapView.setSKPMapApiKey("8c430cbd-0174-365a-879a-98909c5e6f73"); //발급받은 api 키


        tMapView.setCompassMode(false);
        tMapView.setIconVisibility(false);
        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setTrackingMode(false);
        tMapView.setSightVisible(true);
        tMapView.setCenterPoint(startlongi, startlati);

        TMapMarkerItem tourMarkerItem = new TMapMarkerItem();
        TMapPoint start = new TMapPoint(startlati,startlongi);
        tourMarkerItem.setTMapPoint(start);
        tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);

        TMapMarkerItem tourMarkerItem2 = new TMapMarkerItem();
        TMapPoint desti = new TMapPoint(destinationlati,destinationlongi);
        tourMarkerItem2.setTMapPoint(desti);
        tourMarkerItem2.setVisible(TMapMarkerItem.VISIBLE);
        locationview.addView(tMapView);
        tMapView.addMarkerItem("destination",tourMarkerItem2);
        tMapView.addMarkerItem("start", tourMarkerItem);


    }

    class listener implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.tender_info_tdbutton:
                    Intent intent = new Intent(getBaseContext(),bid.class);
                    intent.putExtra("no",no);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                    break;

            }

            if (v.getId() == sendpicture.getId()) {
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




        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CQPTRUE:
                if (resultCode == RESULT_OK) {


                    String imagePath = photoUri.getPath();
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




                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(photoUri);
                    Toast.makeText(this, photoUri.toString(), Toast.LENGTH_LONG).show();
                    this.sendBroadcast(mediaScanIntent);

                    String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();

                    Toast.makeText(this, sdPath, Toast.LENGTH_LONG).show();


                    String jspUri = "http://220.122.180.160:8080/finalcheck.jsp";
                    DoFileUpload(jspUri, fileAddr);


                }
                break;


        }
    }

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




}
