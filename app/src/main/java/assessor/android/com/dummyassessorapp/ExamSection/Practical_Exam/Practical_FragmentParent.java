package assessor.android.com.dummyassessorapp.ExamSection.Practical_Exam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import assessor.android.com.dummyassessorapp.LocalDB.DbAutoSave;
import assessor.android.com.dummyassessorapp.R;

public class Practical_FragmentParent extends Fragment {
    // private TabLayout tabLayout;
    private ViewPager viewPager1;
    private ViewpagerAdapt adapter1;
    TextView ttv;
    DbAutoSave dbAutoSave;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.practical_fragmentparent, container, false);
        dbAutoSave=new DbAutoSave(getContext());

        getIDs(view);

        //setEvents();
        return view;
    }

    private void getIDs(View view) {
        viewPager1 = (ViewPager) view.findViewById(R.id.my_viewpager1);
        adapter1 = new ViewpagerAdapt(getFragmentManager());
        viewPager1.setAdapter(adapter1);
        //viewPager.setCurrentItem(adapter.);
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            Boolean first = true;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!first && positionOffset == 0 && positionOffsetPixels == 0){
                    onPageSelected(0);
                    first = false;
                }
            }
            @Override
            public void onPageSelected(int position) {
                int i=viewPager1.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    public void addPage1(String pagename, String que) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        bundle.putString("daa",que);
        Practical_fragmentchild fragmentChild1 = new Practical_fragmentchild();
        fragmentChild1.setArguments(bundle);
        adapter1.addFrag1(fragmentChild1, pagename,que);
        adapter1.notifyDataSetChanged();
        if (adapter1.getCount() > 0)
            viewPager1.setCurrentItem(0);
    }
}
