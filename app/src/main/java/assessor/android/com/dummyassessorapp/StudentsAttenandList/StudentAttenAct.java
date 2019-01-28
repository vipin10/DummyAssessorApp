package assessor.android.com.dummyassessorapp.StudentsAttenandList;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import assessor.android.com.dummyassessorapp.LocalDB.DbAutoSave;
import assessor.android.com.dummyassessorapp.R;
import assessor.android.com.dummyassessorapp.TestPack.TestInstruction;

public class StudentAttenAct extends AppCompatActivity{

    Button atten,presentt,absentt;
    TextView txtname,mobb;
    LinearLayout imguploadd;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    SharedPreferences ssp;
    final String mypreference = "mypref1";
    String namee,mobilee;
    String attenstatus;
    String attenfinalstatus;
    DbAutoSave dbAutoSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_atten);
        atten=findViewById(R.id.AttendenceDone);
        presentt=findViewById(R.id.presentbutton);
        absentt=findViewById(R.id.absentbutton);
        imguploadd=findViewById(R.id.imgupload);
        imageView=findViewById(R.id.studentimg);
        txtname=findViewById(R.id.stuname);
        mobb=findViewById(R.id.mobi);
        dbAutoSave=new DbAutoSave(getApplicationContext());
        ssp=getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        namee=ssp.getString("StuName","");
        mobilee=ssp.getString("phone","");
        txtname.setText(namee);
        mobb.setText(mobilee);
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = ssp.edit();
                editor.putString("currentatten",attenstatus);
                editor.commit();
                dbAutoSave.insertddd(namee,attenstatus,"0");
                Intent ii=new Intent(StudentAttenAct.this,TestInstruction.class);
                startActivity(ii);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });

        presentt.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                attenstatus="1";
                absentt.setBackground(getDrawable(R.drawable.button_default));
                presentt.setBackground(getDrawable(R.drawable.button_pressed));

            }

        });

        absentt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                attenstatus="0";
                presentt.setBackground(getDrawable(R.drawable.button_default));
                absentt.setBackground(getDrawable(R.drawable.button_disabled));


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
            }
        }


   /* @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.presentbutton:
                absentt.setBackground(getDrawable(R.drawable.button_disabled));
                presentt.setBackground(getDrawable(R.drawable.button_default));

            case R.id.absentbutton:
                presentt.setBackground(getDrawable(R.drawable.button_pressed));
                absentt.setBackground(getDrawable(R.drawable.button_default));
            case R.id.imgupload:

        }

    }*/

    }

