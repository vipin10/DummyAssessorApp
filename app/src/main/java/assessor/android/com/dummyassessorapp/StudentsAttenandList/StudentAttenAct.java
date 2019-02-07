package assessor.android.com.dummyassessorapp.StudentsAttenandList;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import assessor.android.com.dummyassessorapp.GlobalAccess.MyNetwork;
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
    SharedPreferences ssp,ssp1;
    final String mypreference = "mypref1";
    final String mypreference1="mypref";
    String namee,mobilee,assessorid;
    String attenstatus,stuidcheck,batchid1;
    DbAutoSave dbAutoSave;
    Context ctx;
    ProgressDialog pdd;
    String encoded1;
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
        ctx=getApplicationContext();
        dbAutoSave=new DbAutoSave(getApplicationContext());
        ssp=getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        ssp1=getSharedPreferences(mypreference1,Context.MODE_PRIVATE);
        assessorid=ssp1.getString("assessorid","");
        namee=ssp.getString("StuName","");
        mobilee=ssp.getString("phone","");
        batchid1=ssp1.getString("batch_id","");
        stuidcheck=dbAutoSave.getDataOfAtten(namee);
        txtname.setText(namee);
        mobb.setText(mobilee);
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentAttendance();

                SharedPreferences.Editor editor = ssp.edit();
                editor.putString("currentatten",attenstatus);
                editor.commit();
                if (stuidcheck!=null){
                    dbAutoSave.updateD(assessorid,namee,attenstatus,null);
                }else{
                dbAutoSave.insertddd(assessorid,namee,attenstatus,null);
                }
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
                int currentBitmapWidth = photo.getWidth();
                int currentBitmapHeight = photo.getHeight();

                int ivWidth = imageView.getWidth();
                int ivHeight = imageView.getHeight();
                int newWidth = ivWidth;

                int newHeight = (int) Math.floor((double) currentBitmapHeight *( (double) ivWidth / (double) currentBitmapWidth));

                Bitmap newbitMap = Bitmap.createScaledBitmap(photo, newWidth, newHeight, true);

                //imageView.setImageBitmap(newbitMap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                encoded1 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            }
        }

    private void studentAttendance() {
        pdd = new ProgressDialog(StudentAttenAct.this);
        pdd.setMessage("Loading...");
        pdd.show();
        String serverURL = "https://www.skillassessment.org/ssc/android_connect/save_student_attendance.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),"Details are"+response,Toast.LENGTH_LONG).show();
                    String status= jobj.getString("status");
                    String message= jobj.getString("msg");

                    if (encoded1!=null) {
                        Intent ii = new Intent(StudentAttenAct.this, TestInstruction.class);
                        startActivity(ii);
                    }else {
                        Toast.makeText(getApplicationContext(),"You can't Continue without Uploading your Photo",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (pdd.isShowing()) {
                    pdd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pdd.isShowing()) {
                    pdd.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Error: Please try again Later", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("student_id", namee);
                map.put("student_image",encoded1);
                map.put("location","Bhikaji cama place");
                map.put("attendance","PRESENT");
                map.put("batch_id",batchid1);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    }

