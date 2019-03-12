package com.exuberant.code2create.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.exuberant.code2create.FragmentSwitchInterface;
import com.exuberant.code2create.R;
import com.exuberant.code2create.fragments.AboutFragment;
import com.exuberant.code2create.fragments.AgendaFragment;
import com.exuberant.code2create.fragments.FoodCouponsFragment;
import com.exuberant.code2create.fragments.PrizesFragment;
import com.exuberant.code2create.fragments.SponsorsFragment;

public class HomeActivity extends AppCompatActivity {

    static FragmentSwitchInterface fragmentSwitchInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //HomeActivity.getFragmentSwitchInterface().switchToSponsors();

        fragmentSwitchInterface = new FragmentSwitchInterface() {
            @Override
            public void switchToAbout() {
                switchFragment(new AboutFragment());
            }

            @Override
            public void switchToAgenda() {
                switchFragment(new AgendaFragment());
            }

            @Override
            public void switchToFaq() {
                switchFragment(new AgendaFragment());
            }

            @Override
            public void switchToCoupons() {
                switchFragment(new FoodCouponsFragment());
            }

            @Override
            public void switchToPrizes() {
                switchFragment(new PrizesFragment());
            }

            @Override
            public void switchToSponsors() {
                switchFragment(new SponsorsFragment());
            }
        };

    }

    void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
    }

    public static FragmentSwitchInterface getFragmentSwitchInterface() {
        return fragmentSwitchInterface;
    }

    public static void setFragmentSwitchInterface(FragmentSwitchInterface fragmentSwitchInterface) {
        HomeActivity.fragmentSwitchInterface = fragmentSwitchInterface;
    }
}
