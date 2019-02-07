package assessor.android.com.dummyassessorapp.ExamSection.Viva_Exam;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Locale;
import java.util.Map;

import assessor.android.com.dummyassessorapp.GlobalAccess.MyNetwork;
import assessor.android.com.dummyassessorapp.LocalDB.DbAutoSave;
import assessor.android.com.dummyassessorapp.R;
import assessor.android.com.dummyassessorapp.StudentsAttenandList.StudentsListAct;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 600000;
    private android.os.CountDownTimer CountDownTimer;
    private boolean TimerRunning;
    private long TimeLeftInMillis;
    private long EndTime;
    FragmentParent fragmentParent;
    TextView textView,finalSubmitbutton;
    DbAutoSave dbAutoSave;
    String namee1;
    ProgressDialog pdd;
    Cursor cr;
    SharedPreferences sharedPreferences,spp;
    public static final String mypreference = "mypref1";
    public static final String mypreferences1= "mypref";
    String currentatten,assessoridd,batchidd1;
    ArrayList<String> aa=new ArrayList<>();
     ArrayList<String> bb=new ArrayList<>();
     ArrayList<String[]> options=new ArrayList<>();
     String[] title = {
            "New Delhi",
            "Mumbai",
            "Bangalore",
            "Ahmedabad",
    };
    String[] title1 = {
            "Narendra Modi",
            "Jawahar Lal Nehru",
            "Karamchand Ghandhi",
            "Anil Kapoor",
    };
    String[] title2 = {
            "Shiela Dixit",
            "Arvind Kejriwal",
            "Manish Shishodia",
            "Rajat Sharma",
    };
    String[] title3 = {
            "Imraan Khan",
            "Kapil Dev",
            "Mahendra Singh Dhoni",
            "Ravindra Jadeja",
    };
    String[] title4 = {
            "Ved Vyas",
            "TulsiDas",
            "Ramanand sagar",
            "Vishwamitra",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIDs();
        dbAutoSave = new DbAutoSave(getApplicationContext());
        spp=getSharedPreferences(mypreferences1,Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        namee1=sharedPreferences.getString("StuName","");
        assessoridd=spp.getString("assessorid","");
        batchidd1=spp.getString("batch_id","");
        //setEvents();
        /*aa.add("1");
        aa.add("2");
        aa.add("3");
        aa.add("4");
        aa.add("5");
        bb.add("Where is capital of India.");
        bb.add("Who is Prime minister of india.");
        bb.add("who is chief Minister of Delhi.");
        bb.add("Under whose captaincy India won world Cup 1983.");
        bb.add("Who wrote Ramayana.");*/
        options.add(title);
        options.add(title1);
        options.add(title2);
        options.add(title3);
        options.add(title4);
        currentatten=sharedPreferences.getString("currentatten","");
    }

    private void getIDs() {
        fragmentParent = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);
        View vv=findViewById(R.id.count_down_strip);
        textView=vv.findViewById(R.id.timer);
        finalSubmitbutton=vv.findViewById(R.id.finish);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Questionlist();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        TimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        TimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();

        if (TimerRunning) {
            EndTime = prefs.getLong("endTime", 0);
            TimeLeftInMillis = EndTime - System.currentTimeMillis();

            if (TimeLeftInMillis < 0) {
                TimeLeftInMillis = 0;
                TimerRunning = false;
                updateCountDownText();
                updateButtons();
                resetTimer();
            } else {
                startTimer();
            }
        }

        finalSubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertbox = new AlertDialog.Builder(v.getContext())
                        .setMessage("Click Yes to schedule Test for Final Submission.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                resetTimer();
                                dbAutoSave.updateD(assessoridd,namee1,currentatten,"1");
                                //dbAutoSave.insertddd(assessoridd,namee1,currentatten,"1");
                                //resetTimer();
                                Intent ii=new Intent(MainActivity.this, StudentsListAct.class);
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
        startTimer();
    }


    private void startTimer() {
        EndTime = System.currentTimeMillis() + TimeLeftInMillis;

        CountDownTimer = new CountDownTimer(TimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
              /*  AlertDialog alertbox = new AlertDialog.Builder(getApplicationContext())
                        .setMessage("Click Yes to schedule Test for Final Submission.")
                         .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        })

                        .show();*/

               // resetTimer();
                textView.setText("done!");
                TimerRunning = false;
                updateButtons();
            }
        }.start();

        TimerRunning = true;
        updateButtons();
    }

    private void pauseTimer() {
        CountDownTimer.cancel();
        TimerRunning = false;
        updateButtons();
    }

    private void resetTimer() {
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {
        int minutes = (int) (TimeLeftInMillis / 1000) / 60;
        int seconds = (int) (TimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textView.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (TimerRunning) {
          /*  ButtonReset.setVisibility(View.INVISIBLE);
            ButtonStartPause.setText("Pause");*/
        } else {
            /*ButtonStartPause.setText("Start");*/

            if (TimeLeftInMillis < 1000) {
                /*ButtonStartPause.setVisibility(View.INVISIBLE);*/
            } else {
                /*ButtonStartPause.setVisibility(View.VISIBLE);*/
            }

            if (TimeLeftInMillis < START_TIME_IN_MILLIS) {
                /*ButtonReset.setVisibility(View.VISIBLE);*/
            } else {
                /*ButtonReset.setVisibility(View.INVISIBLE);*/
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", TimeLeftInMillis);
        editor.putBoolean("timerRunning", TimerRunning);
        editor.putLong("endTime", EndTime);

        editor.apply();

        if (CountDownTimer != null) {
            CountDownTimer.cancel();
        }
    }

    private void Questionlist() {
        pdd = new ProgressDialog(MainActivity.this);
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
                         /*   Studentname.add(c.getString("student_id"));
                            mob.add(c.getString("mobile"));
                            Email.add(c.getString("email"));
                            Dob.add(c.getString("dob"));*/
                        }
                        //Toast.makeText(getApplicationContext(),"data is"+aa+bb,Toast.LENGTH_LONG).show();
                        for (int ii=0;ii<=aa.size()-1;ii++) {
                            fragmentParent.addPage(aa.get(ii) + "",bb.get(ii));

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
                        //finish();

                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


}
