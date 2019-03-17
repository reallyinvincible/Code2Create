package com.exuberant.code2create.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.exuberant.code2create.AdminBypassBottomSheet;
import com.exuberant.code2create.R;
import com.exuberant.code2create.interfaces.AdminBypassInterface;
import com.exuberant.code2create.models.Scannable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.models.ChirpError;

public class FoodCouponsFragment extends Fragment {

    private static final String TAG = "ChirpTAG";
    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;
    private static final String CHIRP_APP_KEY = "BC9BBD9E355CA7CAF83DD408e";
    private static final String CHIRP_APP_SECRET = "8A9C04Ad084fBb21db1f478aE90ecCDfE3872ccFeD43A52E9b";
    private static final String CHIRP_APP_CONFIG = "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";
    private static AdminBypassInterface adminBypassInterface;

    private ChirpConnect chirp;
    private Context context;

    private Button btnAudio;
    private LottieAnimationView ripple;
    private TextView somethingWrong;
    private TextView titleCoupon1, titleCoupon2, titleCoupon3;
    private TextView timeCoupon1, timeCoupon2, timeCoupon3;
    private ImageView statusCoupon1, statusCoupon2, statusCoupon3;
    private ImageView iconCoupon1, iconCoupon2, iconCoupon3;
    private BottomSheetDialogFragment bottomSheetDialogFragment;

    Scannable scannable1;

    SharedPreferences sharedPreferences;
    FirebaseDatabase mDatabase;
    DatabaseReference mScannablesReference;

    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_coupons, container, false);
        context = this.getContext();
        initialiseViews(view);

        sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", null);

        //---------------------Initialise Chirp------------------------------
        chirp = new ChirpConnect(context, CHIRP_APP_KEY, CHIRP_APP_SECRET);
        ChirpError setConfigError = chirp.setConfig(CHIRP_APP_CONFIG);
        if (setConfigError.getCode() > 0) {
            Log.e(TAG, setConfigError.getMessage());
            Toast.makeText(context, setConfigError.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Log.v(TAG, "SDK service started!");
        }

        adminBypassInterface = new AdminBypassInterface() {
            @Override
            public void bypassScan(String bypassedKey) {
                //TODO: Process the received key
                bottomSheetDialogFragment.dismiss();
            }
        };

        somethingWrong.setOnClickListener(view1 -> {
            bottomSheetDialogFragment = new AdminBypassBottomSheet();
            bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), "AdminBypass");
        });

        //------------Scannable testing-------------
        scannable1 = new Scannable("Lunch", "l1", "lunch1", "1:00 AM", "2:00 AM", "food");

        titleCoupon1.setText(scannable1.getScannableTitle());
        timeCoupon1.setText(String.format("%s - %s", scannable1.getScannableStartTime(), scannable1.getScannableEndTime()));
        statusCoupon1.setImageDrawable(getResources().getDrawable(R.drawable.redeem));
        statusCoupon1.setOnClickListener(view12 -> listen());


        return view;
    }

    private void initialiseViews(View view) {
        ripple = view.findViewById(R.id.lav_ripple);

        titleCoupon1 = view.findViewById(R.id.tv_title_coupon1);
        titleCoupon2 = view.findViewById(R.id.tv_title_coupon2);
        titleCoupon3 = view.findViewById(R.id.tv_title_coupon3);
        timeCoupon1 = view.findViewById(R.id.tv_time_coupon1);
        timeCoupon2 = view.findViewById(R.id.tv_time_coupon2);
        timeCoupon3 = view.findViewById(R.id.tv_time_coupon3);
        statusCoupon1 = view.findViewById(R.id.iv_status_coupon1);
        statusCoupon2 = view.findViewById(R.id.iv_status_coupon2);
        statusCoupon3 = view.findViewById(R.id.iv_status_coupon3);
        iconCoupon1 = view.findViewById(R.id.iv_icon_coupon1);
        iconCoupon2 = view.findViewById(R.id.iv_icon_coupon2);
        iconCoupon3 = view.findViewById(R.id.iv_icon_coupon3);

        somethingWrong = view.findViewById(R.id.tv_something_wrong);
        btnAudio = view.findViewById(R.id.btn_audio);

        mDatabase = FirebaseDatabase.getInstance();
        mScannablesReference = mDatabase.getReference().child("sannables");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        } else {
            ChirpError error = chirp.start();
            if (error.getCode() > 0) {
                Log.e("ChirpError: ", error.getMessage());
            } else {
                Log.v("ChirpSDK: ", "Started ChirpSDK");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ChirpError error = chirp.start();
                    if (error.getCode() > 0) {
                        Log.e("ChirpError: ", error.getMessage());
                    } else {
                        Log.v("ChirpSDK: ", "Started ChirpSDK");
                    }
                }
            }
        }
    }

    private void listen() {
        ripple.playAnimation();
        chirp.start();
        ConnectEventListener chirpEventListener = new ConnectEventListener() {

            @Override
            public void onStateChanged(int i, int i1) {
            }

            @Override
            public void onSent(@NotNull byte[] bytes, int i) {
            }

            @Override
            public void onSending(@NotNull byte[] bytes, int i) {
            }

            @Override
            public void onReceiving(int i) {
            }

            @Override
            public void onReceived(byte[] data, int channel) {
                if (data != null) {
                    String identifier = new String(data);
                    Log.v("ChirpSDK: ", "Received " + identifier);

                    if (!identifier.equals(scannable1.getScannableKey())) {
                        Toast.makeText(context, "Incorrect Key", Toast.LENGTH_SHORT).show();
                    } else {
                        updatePayload(identifier);
                    }
                    ripple.pauseAnimation();
                    ripple.setProgress(0);
                    chirp.stop();
                } else {
                    Log.e("ChirpError: ", "Decode failed");
                }
            }

            @Override
            public void onSystemVolumeChanged(int old, int current) {
            }
        };

        chirp.setListener(chirpEventListener);
    }

    private void updatePayload(final String payload) {
        getActivity().runOnUiThread(() -> {
            TextView textView = Objects.requireNonNull(getView()).findViewById(R.id.tv_something_wrong);
            textView.setText(payload);
            statusCoupon1.setImageDrawable(getResources().getDrawable(R.drawable.redeemed));
            statusCoupon1.setEnabled(false);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        chirp.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chirp.stop();
        try {
            chirp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AdminBypassInterface getAdminBypassInterface() {
        return adminBypassInterface;
    }

    public static void setAdminBypassInterface(AdminBypassInterface adminBypassInterface) {
        FoodCouponsFragment.adminBypassInterface = adminBypassInterface;
    }
}
