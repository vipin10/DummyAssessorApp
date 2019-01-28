package assessor.android.com.dummyassessorapp.ExamSection;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;
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
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref1";
    String currentatten;
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
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        namee1=sharedPreferences.getString("StuName","");
        //setEvents();
        aa.add("1");
        aa.add("2");
        aa.add("3");
        aa.add("4");
        aa.add("5");
        bb.add("Where is capital of India.");
        bb.add("Who is Prime minister of india.");
        bb.add("who is chief Minister of Delhi.");
        bb.add("Under whose captaincy India won world Cup 1983.");
        bb.add("Who wrote Ramayana.");
        options.add(title);
        options.add(title1);
        options.add(title2);
        options.add(title3);
        options.add(title4);
        currentatten=sharedPreferences.getString("currentatten","");
        for (int ii=0;ii<=aa.size()-1;ii++) {

            fragmentParent.addPage(aa.get(ii) + "",bb.get(ii));

        }
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
                                dbAutoSave.insertddd(namee1,currentatten,"1");
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
