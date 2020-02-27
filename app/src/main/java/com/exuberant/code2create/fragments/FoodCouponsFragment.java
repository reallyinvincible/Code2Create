package com.exuberant.code2create.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.exuberant.code2create.R;
import com.exuberant.code2create.bottomsheets.AdminBypassBottomSheet;
import com.exuberant.code2create.interfaces.AdminBypassInterface;
import com.exuberant.code2create.models.CouponsUser;
import com.exuberant.code2create.models.Scannable;
import com.exuberant.code2create.models.ScannableModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.models.ChirpError;

import static com.exuberant.code2create.UtilsInterface.compareDates;
import static com.exuberant.code2create.UtilsInterface.getDateObject;

public class FoodCouponsFragment extends Fragment {

    private static final String TAG = "ChirpTAG";
    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;
    private static final String CHIRP_APP_KEY = "BC9BBD9E355CA7CAF83DD408e";
    private static final String CHIRP_APP_SECRET = "8A9C04Ad084fBb21db1f478aE90ecCDfE3872ccFeD43A52E9b";
    private static final String CHIRP_APP_CONFIG = "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";
    private static AdminBypassInterface adminBypassInterface;

    private ChirpConnect chirp;
    private Context context;

    private LottieAnimationView ripple;
    private TextView somethingWrong;
    private TextView titleCoupon1, titleCoupon2, titleCoupon3;
    private TextView timeCoupon1, timeCoupon2, timeCoupon3;
    private ImageView statusCoupon1, statusCoupon2, statusCoupon3;
    private ImageView iconCoupon1, iconCoupon2, iconCoupon3;
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private ConstraintLayout constraintLayout;
    SkeletonScreen skeletonScreen1, skeletonScreen2, skeletonScreen3;
    CardView cardView1, cardView2, cardView3;

    ConstraintLayout foodContainer;
    private Scannable currentScannable;
    private List<String> currentUserList;

    private SharedPreferences sharedPreferences;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mScannablesReference;
    private DatabaseReference mAttendanceReference;
    private String uid;
    private FirebaseAuth mAuth;

    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_coupons, container, false);
        context = this.getContext();
        initialiseViews(view);

       /* sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", null);
        */

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
                if (bypassedKey.equals(currentScannable.getScannableKey())) {

                    if (currentUserList == null) {
                        currentUserList = new ArrayList<>();
                        currentUserList.add(uid);
                    } else if (!currentUserList.contains(uid)) {
                        currentUserList.add(uid);
                    } else if (currentUserList.contains(uid)) {
                        Toast.makeText(context, "Already Scanned!", Toast.LENGTH_SHORT).show();
                    }

                    if (titleCoupon1.getText().toString().equals(currentScannable.getScannableTitle())) {
                        setRedeemedState(statusCoupon1);
                    } else if (titleCoupon2.getText().toString().equals(currentScannable.getScannableTitle())) {
                        setRedeemedState(statusCoupon2);
                    } else if (titleCoupon3.getText().toString().equals(currentScannable.getScannableTitle())) {
                        setRedeemedState(statusCoupon3);
                    }

                    mAttendanceReference.child(currentScannable.getScannableValue()).setValue(new CouponsUser(currentUserList));

                } else {
                    Toast.makeText(context, "Incorrect Key", Toast.LENGTH_SHORT).show();
                }

                ripple.pauseAnimation();
                ripple.setProgress(0);
                chirp.stop();

                bottomSheetDialogFragment.dismiss();
            }
        };

        somethingWrong.setOnClickListener(view1 -> {
            bottomSheetDialogFragment = new AdminBypassBottomSheet();
            bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), "AdminBypass");
        });


        mScannablesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ScannableModel scannableModel = dataSnapshot.getValue(ScannableModel.class);
                List<Scannable> scannableList = scannableModel.getScannableList();
                findAppropriateList(scannableList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void setRedeemState(ImageView imageView) {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.redeem));
        imageView.setOnClickListener(view -> listen(imageView));
    }

    private void setRedeemedState(ImageView imageView) {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.redeemed));
        imageView.setEnabled(false);
    }

    private void setInvisibleState(ImageView imageView) {
        imageView.setVisibility(View.INVISIBLE);
        imageView.setEnabled(false);
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
        cardView1 = view.findViewById(R.id.cv_coupon1);
        cardView2 = view.findViewById(R.id.cv_coupon2);
        cardView3 = view.findViewById(R.id.cv_coupon3);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        foodContainer=view.findViewById(R.id.container_food);

        skeletonScreen1 = Skeleton.bind(cardView1)
                .shimmer(true)
                .angle(20)
                .duration(1200)
                .load(R.layout.skeleton_item_agenda)
                .show();

        skeletonScreen2 = Skeleton.bind(cardView2)
                .shimmer(true)
                .angle(20)
                .duration(1200)
                .load(R.layout.skeleton_item_agenda)
                .show();

        skeletonScreen3 = Skeleton.bind(cardView3)
                .shimmer(true)
                .angle(20)
                .duration(1200)
                .load(R.layout.skeleton_item_agenda)
                .show();

        constraintLayout = view.findViewById(R.id.container_coupons);

        somethingWrong = view.findViewById(R.id.tv_something_wrong);

        mDatabase = FirebaseDatabase.getInstance();
        mScannablesReference = mDatabase.getReference().child("scannables").child("list");
        mAttendanceReference = mDatabase.getReference().child("scannables").child("attendance");
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

    private void listen(ImageView imageView) {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ripple.getLayoutParams();
        params.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        ripple.setLayoutParams(params);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.btn_audio, ConstraintSet.START, R.id.btn_audio, ConstraintSet.END, 0);
        constraintSet.connect(R.id.btn_audio, ConstraintSet.TOP, R.id.btn_audio, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(constraintLayout);


        ripple.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
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

                    if (!identifier.equals(currentScannable.getScannableKey())) {
                        Toast.makeText(context, "Incorrect Key", Toast.LENGTH_SHORT).show();
                    } else {
                        updatePayload(identifier, imageView);
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

    private void updatePayload(final String payload, ImageView imageView) {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(context, "Enjoy your meal", Toast.LENGTH_SHORT).show();
            setRedeemedState(imageView);
            currentUserList.add(uid);
            mAttendanceReference.child(currentScannable.getScannableValue()).setValue(new CouponsUser(currentUserList));
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

    private void processScannable(List<Scannable> scannableList) {

        Scannable scannable1 = scannableList.get(0);
        Scannable scannable2 = scannableList.get(1);
        Scannable scannable3 = scannableList.get(2);

        mAttendanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CouponsUser couponsUser1 = dataSnapshot.child(scannable1.getScannableValue()).getValue(CouponsUser.class);
                List<String> userList1 = new ArrayList<>();
                List<String> userList2 = new ArrayList<>();
                List<String> userList3 = new ArrayList<>();

                if (couponsUser1 != null) {
                    userList1 = couponsUser1.getCouponsUserList();
                }

                CouponsUser couponsUser2 = dataSnapshot.child(scannable2.getScannableValue()).getValue(CouponsUser.class);
                if (couponsUser2 != null) {
                    userList2 = couponsUser2.getCouponsUserList();
                }

                CouponsUser couponsUser3 = dataSnapshot.child(scannable3.getScannableValue()).getValue(CouponsUser.class);
                if (couponsUser3 != null) {
                    userList3 = couponsUser3.getCouponsUserList();
                }

                setData(scannableList, userList1, userList2, userList3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setData(List<Scannable> scannableList, List<String> userList1, List<String> userList2, List<String> userList3) {

        Scannable scannable1 = scannableList.get(0);
        Scannable scannable2 = scannableList.get(1);
        Scannable scannable3 = scannableList.get(2);


        skeletonScreen1.hide();
        skeletonScreen2.hide();
        skeletonScreen3.hide();

        titleCoupon1.setText(scannableList.get(0).getScannableTitle());
        timeCoupon1.setText(String.format("%s - %s", scannable1.getScannableStartTime(), scannable1.getScannableEndTime()));
        setTypeIcon(iconCoupon1, scannable1.getScannableType());

        titleCoupon2.setText(scannableList.get(1).getScannableTitle());
        timeCoupon2.setText(String.format("%s - %s", scannable2.getScannableStartTime(), scannable2.getScannableEndTime()));
        setTypeIcon(iconCoupon2, scannable2.getScannableType());

        titleCoupon3.setText(scannableList.get(2).getScannableTitle());
        timeCoupon3.setText(String.format("%s - %s", scannable3.getScannableStartTime(), scannable3.getScannableEndTime()));
        setTypeIcon(iconCoupon3, scannable3.getScannableType());

        Date date11 = getDateObject(scannable1.getScannableDate(), scannable1.getScannableStartTime());
        Date date12 = getDateObject(scannable1.getScannableDate(), scannable1.getScannableEndTime());
        Date date21 = getDateObject(scannable2.getScannableDate(), scannable2.getScannableStartTime());
        Date date22 = getDateObject(scannable2.getScannableDate(), scannable2.getScannableEndTime());
        Date date31 = getDateObject(scannable3.getScannableDate(), scannable3.getScannableStartTime());
        Date date32 = getDateObject(scannable3.getScannableDate(), scannable3.getScannableEndTime());

        if (compareDates(date11) == 1 && compareDates(date12) == -1) {
            currentScannable = scannable1;
            currentUserList = userList1;
            if (currentUserList == null || !currentUserList.contains(uid)) {
                setRedeemState(statusCoupon1);
            } else if (currentUserList.contains(uid)) {
                setRedeemedState(statusCoupon1);
            }
        } else {
            setInvisibleState(statusCoupon1);
        }

        if (compareDates(date21) == 1 && compareDates(date22) == -1) {
            currentScannable = scannable2;
            currentUserList = userList2;
            if (currentUserList == null || !currentUserList.contains(uid)) {
                setRedeemState(statusCoupon2);
            } else if (currentUserList.contains(uid)) {
                setRedeemedState(statusCoupon2);
            }
        } else {
            setInvisibleState(statusCoupon2);
        }

        if (compareDates(date31) == 1 && compareDates(date32) == -1) {
            currentScannable = scannable3;
            currentUserList = userList3;
            if (currentUserList == null || !currentUserList.contains(uid)) {
                setRedeemState(statusCoupon3);
            } else if (currentUserList.contains(uid)) {
                setRedeemedState(statusCoupon3);
            }
        } else {
            setInvisibleState(statusCoupon3);
        }
    }

    private void findAppropriateList(List<Scannable> scannableList) {
        int i = 0;
        List<Scannable> selectedScannable = new ArrayList<>();
        for (i = 0; i < scannableList.size(); i++) {
            Scannable scannable = scannableList.get(i);
            Date date = getDateObject(scannable.getScannableDate(), scannable.getScannableEndTime());
            if (compareDates(date) == -1) {
                foodContainer.setBackground(Drawable.createFromPath("@drawable/ic_error"));
            }
        }

        if (i == 0) {
            selectedScannable.add(scannableList.get(i));
            selectedScannable.add(scannableList.get(i + 1));
            selectedScannable.add(scannableList.get(i + 2));
            processScannable(selectedScannable);
        } else if (i == (scannableList.size() - 1)) {
            selectedScannable.add(scannableList.get(i - 2));
            selectedScannable.add(scannableList.get(i - 1));
            selectedScannable.add(scannableList.get(i));
            processScannable(selectedScannable);
        } else {
            selectedScannable.add(scannableList.get(i - 1));
            selectedScannable.add(scannableList.get(i));
            selectedScannable.add(scannableList.get(i + 1));
            processScannable(selectedScannable);
        }

    }

    private void setTypeIcon(ImageView imageView, String type) {
        switch (type) {
            case "food":
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                break;
            case "swags":
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_notification));
                break;
            case "reg":
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_regs));
                break;
            default:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_info));
        }
    }
}
