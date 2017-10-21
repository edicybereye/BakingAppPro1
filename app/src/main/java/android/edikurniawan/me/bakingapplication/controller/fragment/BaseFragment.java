package android.edikurniawan.me.bakingapplication.controller.fragment;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Edi Kurniawan on 7/1/2017.
 */

public abstract class BaseFragment extends Fragment {

    protected void displayFragment(int id, Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(id, fragment).commitAllowingStateLoss();
    }

    protected void addFragment(int id, Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .add(id, fragment).commitAllowingStateLoss();
    }

    protected List<Fragment> getFragments() {
        return getChildFragmentManager().getFragments();
    }

}
