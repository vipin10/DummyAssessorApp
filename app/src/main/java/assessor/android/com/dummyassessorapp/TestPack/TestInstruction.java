package assessor.android.com.dummyassessorapp.TestPack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import assessor.android.com.dummyassessorapp.ExamSection.Practical_Exam.Practical_Exam;
import assessor.android.com.dummyassessorapp.ExamSection.Viva_Exam.MainActivity;
import assessor.android.com.dummyassessorapp.R;

public class TestInstruction extends AppCompatActivity {
Button testinstructproceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_instruction);
        testinstructproceed=findViewById(R.id.testinstructproceed);
        testinstructproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(TestInstruction.this, MainActivity.class);
                startActivity(ii);
            }
        });
    }
}
