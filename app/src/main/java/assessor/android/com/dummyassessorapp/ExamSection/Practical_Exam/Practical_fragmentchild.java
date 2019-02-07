package assessor.android.com.dummyassessorapp.ExamSection.Practical_Exam;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import assessor.android.com.dummyassessorapp.ExamSection.Viva_Exam.FragmentChild;
import assessor.android.com.dummyassessorapp.ExamSection.Viva_Exam.ViewPagerAdapter;
import assessor.android.com.dummyassessorapp.LocalDB.DbAutoSave;
import assessor.android.com.dummyassessorapp.R;

public class Practical_fragmentchild extends Fragment implements View.OnClickListener {
    String childname,quename;
    TextView textViewChildName,t1,optiona,optionb,optionc,optiond,titlea,titleb,titlec,titled;
    LinearLayout l1,l2,l3,l4;
    DbAutoSave dbAutoSave;
    String idd,Stuid;
    String query,query1,assessoridd;
    SharedPreferences sharedPreferences,sharedPreferences1;
    public static final String mypreference = "mypref";
    public static final String mypreference1 = "mypref1";
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.practical_fragmentchild, container, false);
        getIDs(view);
        setEvents();

        return view;
    }

    private void getIDs(View view) {
        textViewChildName =view.findViewById(R.id.Quesnoo);
        t1=view.findViewById(R.id.que_practical);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }
        query=t1.getText().toString();
        query1=Stuid;
    }

    private void setEvents() {

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

    }
}
