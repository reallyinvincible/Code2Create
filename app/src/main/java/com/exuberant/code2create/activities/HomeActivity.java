package com.exuberant.code2create.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.exuberant.code2create.fragments.ErrorFragment;
import com.exuberant.code2create.interfaces.FragmentSwitchInterface;
import com.exuberant.code2create.R;
import com.exuberant.code2create.fragments.AboutFragment;
import com.exuberant.code2create.fragments.AgendaFragment;
import com.exuberant.code2create.fragments.FoodCouponsFragment;
import com.exuberant.code2create.fragments.PrizesFragment;
import com.exuberant.code2create.fragments.SponsorsFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    static FragmentSwitchInterface fragmentSwitchInterface;
    private MaterialButton navigationButton, bookmarkButton, alertButton, closeButton;
    private LinearLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();

        HomeActivity.getFragmentSwitchInterface().switchToError();

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

            @Override
            public void switchToError() {
                switchFragment(new ErrorFragment());
            }
        };

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetLayout.setBackground(getResources().getDrawable(R.drawable.rounded_corner_bottom_sheet_accent));
                    navigationButton.setVisibility(View.VISIBLE);
                    alertButton.setVisibility(View.VISIBLE);
                    bookmarkButton.setVisibility(View.VISIBLE);
                    closeButton.setVisibility(View.GONE);

                } else {
                    bottomSheetLayout.setBackground(getResources().getDrawable(R.drawable.rounded_corner_bottom_sheet_primary));
                    navigationButton.setVisibility(View.GONE);
                    alertButton.setVisibility(View.GONE);
                    bookmarkButton.setVisibility(View.GONE);
                    closeButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkButton.setIcon(getResources().getDrawable(R.drawable.ic_bookmark_outlined));
                alertButton.setIcon(getResources().getDrawable(R.drawable.ic_alerts_filled));
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkButton.setIcon(getResources().getDrawable(R.drawable.ic_bookmark_filled));
                alertButton.setIcon(getResources().getDrawable(R.drawable.ic_alert_outlined));
            }
        });

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

    void initializeViews(){
        navigationButton = findViewById(R.id.btn_navigation);
        alertButton = findViewById(R.id.btn_alerts);
        bookmarkButton = findViewById(R.id.btn_bookmarks);
        bottomSheetLayout = findViewById(R.id.bottom_sheet);
        closeButton = findViewById(R.id.btn_close_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
    }

}
