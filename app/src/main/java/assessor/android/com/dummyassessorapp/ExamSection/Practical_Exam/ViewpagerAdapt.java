package assessor.android.com.dummyassessorapp.ExamSection.Practical_Exam;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewpagerAdapt extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    private final ArrayList<String> mquelist = new ArrayList<>();
    Context context;

    public ViewpagerAdapt(FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag1(Fragment fragment, String title,String quee) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mquelist.add(quee);

    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}

