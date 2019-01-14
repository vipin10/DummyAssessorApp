package assessor.android.com.dummyassessorapp.StudentsAttenandList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import assessor.android.com.dummyassessorapp.R;
import assessor.android.com.dummyassessorapp.TestPack.TestInstruction;

public class StudentAttenAct extends AppCompatActivity {

    Button atten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_atten);
        atten=findViewById(R.id.AttendenceDone);
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(StudentAttenAct.this,TestInstruction.class);
                startActivity(ii);
            }
        });
    }
}
