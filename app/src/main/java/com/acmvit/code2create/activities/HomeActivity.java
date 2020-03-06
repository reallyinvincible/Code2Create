package com.acmvit.code2create.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.transition.Fade;

import com.acmvit.code2create.R;
import com.acmvit.code2create.bottomsheets.WiFiDetailsBottomSheet;
import com.acmvit.code2create.fragments.AboutFragment;
import com.acmvit.code2create.fragments.AgendaFragment;
import com.acmvit.code2create.fragments.AlertFragment;
import com.acmvit.code2create.fragments.ErrorFragment;
import com.acmvit.code2create.fragments.FaqsFragment;
import com.acmvit.code2create.fragments.FoodCouponsFragment;
import com.acmvit.code2create.fragments.PrizesFragment;
import com.acmvit.code2create.fragments.SponsorsFragment;
import com.acmvit.code2create.interfaces.FragmentSwitchInterface;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    static FragmentSwitchInterface fragmentSwitchInterface;
    private MaterialButton navigationButton, wifiButton, alertButton, closeButton;
    private LinearLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;
    View viewShadow;
    private LinearLayout aboutContainer, agendaContainer, faqContainer, couponsContainer, prizesContainer, sponsorsContainer, logoutContainer;
    private ImageView aboutImageView, agendaImageView, faqImageView, couponsImageView, prizesImageView, sponsorsImageView;
    private TextView aboutTextView, agendaTextView, faqTextView, couponsTextView, prizesTextView, sponsorsTextView, noInternetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_home);

        initializeViews();

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
                switchFragment(new FaqsFragment());
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

            @Override
            public void switchToAlerts() {
                switchFragment(new AlertFragment());
            }

            @Override
            public void openWiFiBottomSheet() {
                BottomSheetDialogFragment wifiBottomSheet = new WiFiDetailsBottomSheet();
                wifiBottomSheet.show(getSupportFragmentManager(), "WiFi Details");
            }

            @Override
            public void showInternetCard() {
                HomeActivity.this.showInternetCard();
            }

            @Override
            public void hideInternetCard() {
                HomeActivity.this.hideInternetCard();
            }
        };

        setupNetworkListener();

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetLayout.setBackground(getResources().getDrawable(R.drawable.rounded_corner_bottom_sheet_accent));
                    navigationButton.setVisibility(View.VISIBLE);
                    alertButton.setVisibility(View.VISIBLE);
                    wifiButton.setVisibility(View.VISIBLE);
                    closeButton.setVisibility(View.GONE);
                    viewShadow.setVisibility(View.INVISIBLE);

                } else {
                    bottomSheetLayout.setBackground(getResources().getDrawable(R.drawable.rounded_corner_bottom_sheet_primary));
                    navigationButton.setVisibility(View.GONE);
                    alertButton.setVisibility(View.GONE);
                    wifiButton.setVisibility(View.GONE);
                    closeButton.setVisibility(View.VISIBLE);
                    viewShadow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        setupBottomAppBarButtonListeners();

        setupMenuListeners();

        clearAllTints();
        agendaContainer.setBackground(getResources().getDrawable(R.drawable.menu_selection_background));
        agendaImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_agenda_tint));
        agendaTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        fragmentSwitchInterface.switchToAgenda();
    }

    void switchFragment(Fragment fragment) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        fragment.setEnterTransition(new Fade());
        fragment.setExitTransition(new Fade());
        FragmentManager fragmentManager = getSupportFragmentManager();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        fragmentManager.beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
    }

    public static FragmentSwitchInterface getFragmentSwitchInterface() {
        return fragmentSwitchInterface;
    }

    void initializeViews() {
        navigationButton = findViewById(R.id.btn_navigation);
        alertButton = findViewById(R.id.btn_alerts);
        wifiButton = findViewById(R.id.btn_wifi);
        bottomSheetLayout = findViewById(R.id.bottom_sheet);
        closeButton = findViewById(R.id.btn_close_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        aboutContainer = findViewById(R.id.ll_about_container);
        aboutImageView = findViewById(R.id.iv_about);
        aboutTextView = findViewById(R.id.tv_about);
        agendaContainer = findViewById(R.id.ll_agenda_container);
        agendaImageView = findViewById(R.id.iv_agenda);
        agendaTextView = findViewById(R.id.tv_agenda);
        faqContainer = findViewById(R.id.ll_faqs_container);
        faqImageView = findViewById(R.id.iv_faqs);
        faqTextView = findViewById(R.id.tv_faqs);
        noInternetTextView = findViewById(R.id.tv_no_internet);
        couponsContainer = findViewById(R.id.ll_coupons_container);
        couponsImageView = findViewById(R.id.iv_coupons);
        couponsTextView = findViewById(R.id.tv_coupons);
        prizesContainer = findViewById(R.id.ll_prizes_container);
        prizesImageView = findViewById(R.id.iv_prizes);
        prizesTextView = findViewById(R.id.tv_prizes);
        sponsorsContainer = findViewById(R.id.ll_sponsors_container);
        sponsorsImageView = findViewById(R.id.iv_sponsors);
        sponsorsTextView = findViewById(R.id.tv_sponsors);
        logoutContainer = findViewById(R.id.ll_logout_container);
        viewShadow = findViewById(R.id.view_shadow);
    }

    void setupBottomAppBarButtonListeners() {

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
                clearAllTints();
                alertButton.setIcon(getResources().getDrawable(R.drawable.ic_alerts_filled));
                fragmentSwitchInterface.switchToAlerts();
            }
        });

        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllTints();
                wifiButton.setIcon(getResources().getDrawable(R.drawable.ic_wifi));
                fragmentSwitchInterface.openWiFiBottomSheet();
            }
        });
    }

    void setupMenuListeners() {
        aboutContainer.setOnClickListener(view -> {
            clearAllTints();
            aboutContainer.setBackground(getResources().getDrawable(R.drawable.menu_selection_background));
            aboutImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_about_tint));
            aboutTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            fragmentSwitchInterface.switchToAbout();
        });

        agendaContainer.setOnClickListener(view -> {
            clearAllTints();
            agendaContainer.setBackground(getResources().getDrawable(R.drawable.menu_selection_background));
            agendaImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_agenda_tint));
            agendaTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            fragmentSwitchInterface.switchToAgenda();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        faqContainer.setOnClickListener(view -> {
            clearAllTints();
            faqContainer.setBackground(getResources().getDrawable(R.drawable.menu_selection_background));
            faqImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_faqs_tint));
            faqTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            fragmentSwitchInterface.switchToFaq();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        couponsContainer.setOnClickListener(view -> {
            clearAllTints();
            couponsContainer.setBackground(getResources().getDrawable(R.drawable.menu_selection_background));
            couponsImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_coupons_tint));
            couponsTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            fragmentSwitchInterface.switchToCoupons();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        prizesContainer.setOnClickListener(view -> {
            clearAllTints();
            prizesContainer.setBackground(getResources().getDrawable(R.drawable.menu_selection_background));
            prizesImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_prizes_tint));
            prizesTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            fragmentSwitchInterface.switchToPrizes();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        sponsorsContainer.setOnClickListener(view -> {
            clearAllTints();
            sponsorsContainer.setBackground(getResources().getDrawable(R.drawable.menu_selection_background));
            sponsorsImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_sponsors_tint));
            sponsorsTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            fragmentSwitchInterface.switchToSponsors();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        logoutContainer.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_prefs_name), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finishAfterTransition();
        });
    }

    void clearAllTints() {
        aboutContainer.setBackgroundResource(0);
        aboutImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_about));
        aboutTextView.setTextColor(getResources().getColor(R.color.textColor));
        agendaContainer.setBackgroundResource(0);
        agendaImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_agenda));
        agendaTextView.setTextColor(getResources().getColor(R.color.textColor));
        faqContainer.setBackgroundResource(0);
        faqImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_faqs));
        faqTextView.setTextColor(getResources().getColor(R.color.textColor));
        couponsContainer.setBackgroundResource(0);
        couponsImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_coupons));
        couponsTextView.setTextColor(getResources().getColor(R.color.textColor));
        prizesContainer.setBackgroundResource(0);
        prizesImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_prizes));
        prizesTextView.setTextColor(getResources().getColor(R.color.textColor));
        sponsorsContainer.setBackgroundResource(0);
        sponsorsImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_sponsors));
        sponsorsTextView.setTextColor(getResources().getColor(R.color.textColor));
        wifiButton.setIcon(getResources().getDrawable(R.drawable.ic_wifi));
        alertButton.setIcon(getResources().getDrawable(R.drawable.ic_alert_outlined));
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void setupNetworkListener() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                getFragmentSwitchInterface().hideInternetCard();
            }

            @Override
            public void onLost(Network network) {
                getFragmentSwitchInterface().showInternetCard();
            }

            @Override
            public void onUnavailable() {
                getFragmentSwitchInterface().showInternetCard();
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback);
        }
    }

    private void showInternetCard() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                noInternetTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideInternetCard() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                noInternetTextView.setVisibility(View.GONE);
            }
        });
    }
}
