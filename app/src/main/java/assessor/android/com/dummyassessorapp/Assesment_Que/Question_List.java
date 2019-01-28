package assessor.android.com.dummyassessorapp.Assesment_Que;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import assessor.android.com.dummyassessorapp.R;

public class Question_List extends AppCompatActivity {
   private int seconds= 0;
   private boolean running;
    TextView textView;
    private static final long START_TIME_IN_MILLIS = 600000;
    private android.os.CountDownTimer CountDownTimer;
    private boolean TimerRunning;
    private long TimeLeftInMillis;
    private long EndTime;


    // Not being used as of now

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question__list);
        textView=findViewById(R.id.timerr);
        //runTimer();
        startTimer();
    }

    public void onclickstart(View view){
        running = true;
    }

    private void runTimer(){
        int hours= seconds/3600;
        int minutes= (seconds%3600)/60;
        int secs= seconds%60;
        String time= String.format("%d:%02d:%02d",hours,minutes,secs);
        textView.setText(time);
   // if (running){
        seconds++;
   // }
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

    }
}
