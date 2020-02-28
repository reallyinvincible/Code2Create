package com.exuberant.code2create4.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.FrameLayout;

import com.exuberant.code2create4.R;
import com.exuberant.code2create4.fragments.LoginFragment;
import com.exuberant.code2create4.fragments.SignUpFragment;
import com.exuberant.code2create4.interfaces.LoginActivityInterface;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Stack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class LoginActivity extends AppCompatActivity {

    static LoginActivityInterface loginActivityInterface;
    private FragmentManager mFragmentManager;
    private Stack<Fragment> fragmentStack = new Stack<>();
    private String TAG = "ACM";
    private FirebaseAuth mAuth;
    private FrameLayout mFrameLayout;
    SharedPreferences sharedPreferences;
    Integer loginState;

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() == 1) {
            finish();
        } else {
            fragmentStack.pop();
            fragmentTransition(fragmentStack.peek());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (loginState == 1) {
            launchHome();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();
        fragmentTransition(new LoginFragment());
        fragmentStack.push(new LoginFragment());

        loginActivityInterface = new LoginActivityInterface() {
            @Override
            public void launchHome() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                finish();
                startActivity(intent);
            }

            @Override
            public void switchToSignUp() {
                fragmentStack.push(new SignUpFragment());
                fragmentTransition(fragmentStack.peek());
            }

            @Override
            public void switchToLogin() {
                fragmentStack.pop();
                fragmentTransition(fragmentStack.peek());
            }
        };

    }

    public void launchHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finishAfterTransition();
    }


    void initializeView() {
        mAuth = FirebaseAuth.getInstance();
        mFrameLayout = findViewById(R.id.login_signup_frame);
        mFragmentManager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        loginState = sharedPreferences.getInt("loginState", 0);
    }


    private void fragmentTransition(Fragment fragment) {
        fragment.setEnterTransition(new Fade());
        fragment.setExitTransition(new Fade());
        mFragmentManager.beginTransaction()
                .replace(R.id.login_signup_frame, fragment)
                .commit();
    }

    public static LoginActivityInterface getLoginActivityInterface() {
        return loginActivityInterface;
    }

}




