package assessor.android.com.dummyassessorapp.Assesment_Que;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import assessor.android.com.dummyassessorapp.R;

public class Question_List extends AppCompatActivity {
   private int seconds= 0;
   private boolean running;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question__list);
        textView=findViewById(R.id.timerr);
        runTimer();
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

}
