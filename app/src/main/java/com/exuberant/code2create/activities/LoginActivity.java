package com.exuberant.code2create.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exuberant.code2create.R;
import com.exuberant.code2create.fragments.LoginFragment;
import com.exuberant.code2create.fragments.SignUpFragment;
import com.exuberant.code2create.interfaces.LoginActivityInterface;
import com.exuberant.code2create.models.Agenda;
import com.exuberant.code2create.models.AgendaModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static androidx.core.app.ActivityCompat.finishAfterTransition;
import static com.exuberant.code2create.UtilsInterface.transformString;


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




