package assessor.android.com.dummyassessorapp.ExamSection.Viva_Exam;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by DAT on 8/16/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    private final ArrayList<String> mquelist = new ArrayList<>();
    Context context;

    public ViewPagerAdapter(FragmentManager manager) {
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

    public void addFrag(Fragment fragment, String title,String quee) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mquelist.add(quee);

    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
