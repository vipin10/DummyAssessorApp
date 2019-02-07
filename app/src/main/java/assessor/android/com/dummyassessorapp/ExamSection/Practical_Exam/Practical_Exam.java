package assessor.android.com.dummyassessorapp.ExamSection.Practical_Exam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import assessor.android.com.dummyassessorapp.GlobalAccess.MyNetwork;
import assessor.android.com.dummyassessorapp.LocalDB.DbAutoSave;
import assessor.android.com.dummyassessorapp.R;
import assessor.android.com.dummyassessorapp.StudentsAttenandList.StudentsListAct;

public class Practical_Exam extends AppCompatActivity {

    Practical_FragmentParent fragmentParent1;
    TextView finalSubmitbutton1;
    DbAutoSave dbAutoSave;
    String namee;
    ProgressDialog pdd;
    SharedPreferences sharedPreferences,spp;
    public static final String mypreference = "mypref1";
    public static final String mypreferences1= "mypref";
    String currentatten,assessoridd,batchidd1;
    ArrayList<String> aa=new ArrayList<>();
    ArrayList<String> bb=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical__exam);
        getIDs();
        dbAutoSave = new DbAutoSave(getApplicationContext());
        spp=getSharedPreferences(mypreferences1, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        namee=sharedPreferences.getString("StuName","");
        assessoridd=spp.getString("assessorid","");
        batchidd1=spp.getString("batch_id","");

        currentatten=sharedPreferences.getString("currentatten","");
    }

    private void getIDs() {
        fragmentParent1 = (Practical_FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent1);
        View vv=findViewById(R.id.count_down_strip1);
        finalSubmitbutton1=vv.findViewById(R.id.finishpracticals);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Questionlist();


        finalSubmitbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertbox = new AlertDialog.Builder(v.getContext())
                        .setMessage("Click Yes to schedule Test for Final Submission.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                                dbAutoSave.updateD(assessoridd,namee,currentatten,"1");
                                //dbAutoSave.insertddd(assessoridd,namee1,currentatten,"1");
                                //resetTimer();
                                Intent ii=new Intent(Practical_Exam.this, StudentsListAct.class);
                                startActivity(ii);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        })
                        .show();
            }
        });

    }




    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

    }

    private void Questionlist() {
        pdd = new ProgressDialog(Practical_Exam.this);
        pdd.setMessage("Loading...");
        pdd.show();
        String serverURL = "https://www.skillassessment.org/ssc/android_connect/batch_questions.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),"Details are"+response,Toast.LENGTH_LONG).show();
                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("questions");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            aa.add(c.getString("question_id"));
                            bb.add(c.getString("question"));

                        }
                        for (int ii=0;ii<=aa.size()-1;ii++) {
                            fragmentParent1.addPage1(aa.get(ii) + "",bb.get(ii));

                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
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
                map.put("batch_id", batchidd1);
                map.put("language","en");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("The exam will continue and Timer will keep running.Are you sure you want to exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);



                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

}
