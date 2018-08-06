package com.example.test.programmingmama.View.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginUser extends AppCompatActivity {

    private EditText mLoginEmail;
    Toolbar toolbar;
    private ProgressDialog mProgress;
    private int lastLengthOfEmail = 0;
    private EditText mLoginPass;

    PrefManager prefManager;
    ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    Context cn;

    SignInButton googlesignin;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_user);


        cn = this;
        init();

        findViewById(R.id.forgetpass).setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(cn);
            AlertDialog.Builder builder = new AlertDialog.Builder(cn);
            final View dialogView = inflater.inflate(R.layout.forget_pass_dialog, null);
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();
            TextInputLayout forgetemail = dialogView.findViewById(R.id.forgetpassed);
            dialogView.findViewById(R.id.forgetpassbtn).setOnClickListener(vv -> {
                String email = forgetemail.getEditText().getText().toString();

                if (!email.equals("") || !email.equals(null)) {
                    mProgress.setMessage("Please Wait");
                    mProgress.show();
                    PasswordRest(email);
                } else {
                    Toast.makeText(this, "Please add valid data", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        });
    }

    private void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    private void autoCompliteEmailForm() {
        String text = mLoginEmail.getText().toString();
        int length = text.length();
        if (lastLengthOfEmail < length) {
            if (length > 1) {
                text = text.substring(length - 2);
                text = text.toLowerCase();
                char[] lastChars = text.toCharArray();
                if (lastChars[0] == '@') {
                    if (lastChars[1] == 'g') {
                        mLoginEmail.append("mail.com");
                    } else if (lastChars[1] == 'h') {
                        mLoginEmail.append("otmail.com");
                    } else if (lastChars[1] == 'l') {
                        mLoginEmail.append("ive.com");
                    } else if (lastChars[1] == 'y') {
                        mLoginEmail.append("ahoo.com");
                    }
                }
            }
        }
        lastLengthOfEmail = length;
    }

    private void PasswordRest(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mProgress.dismiss();
                        Toast.makeText(cn, "Please check your email.", Toast.LENGTH_SHORT).show();
                    } else {
                        mProgress.dismiss();
                        Toast.makeText(cn, "Email address not found.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void init() {
        prefManager = new PrefManager(cn);
        mDatabase = GetFirebaseRef.GetDbIns().getReference();
        mProgressBar = findViewById(R.id.progress);
        toolbar = findViewById(R.id.tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        GoogleOAuthFunc();

        googlesignin = findViewById(R.id.googlesignin);
        googlesignin.setOnClickListener(v -> signIn());

        setGooglePlusButtonText(googlesignin, "Continue With Gmail");

        findViewById(R.id.signup).setOnClickListener(vv -> {
            startActivity(new Intent(cn, RegisterUser.class));
            //   finish();
        });
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mLoginEmail = findViewById(R.id.mLoginEmail);
        mLoginPass = findViewById(R.id.mLoginPass);
        findViewById(R.id.login_btn).setOnClickListener(v -> {

            String email = Objects.requireNonNull(mLoginEmail).getText().toString();
            String pass = Objects.requireNonNull(mLoginPass).getText().toString();
            if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)) {
//                mProgress.setTitle("Logging in");
//                mProgress.setMessage("Please wait while we check your credentials");
//                mProgress.setCanceledOnTouchOutside(false);
//                mProgress.show();
                mProgressBar.setVisibility(View.VISIBLE);
                LoginUser(email, pass);
            }
        });
        mLoginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                autoCompliteEmailForm();


            }
        });

    }

    private void GoogleOAuthFunc() {


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()

                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        mProgressBar.setVisibility(View.VISIBLE);

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String DeviceToken = FirebaseInstanceId.getInstance().getToken();
                        OneSignal.sendTag("id",user.getEmail());
                        OneSignal.setSubscription(true);

                        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                if (snapshot.hasChild(user.getUid())) {
                                    //ARiWl5JvVfVIVz4SreTe9r4H5023

                                    mDatabase.child("Push").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {
                                                String appid = dataSnapshot.child("appid").getValue().toString();
                                                String token = dataSnapshot.child("token").getValue().toString();
                                                prefManager.SetAppID(appid);
                                                prefManager.SetToken(token);
                                                finish();
                                            }else {
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                } else {
                                    HashMap<String, String> UserMap = new HashMap<>();
                                    UserMap.put("name", user.getDisplayName());
                                    UserMap.put("deviceToken", DeviceToken);
                                    UserMap.put("email", user.getEmail());
                                    UserMap.put("badge", "null");
                                    UserMap.put("member_since", "" + Calendar.getInstance().get(Calendar.YEAR));

                                    mDatabase.child("Users").child(user.getUid()).setValue(UserMap).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            mDatabase.child("Push").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists()) {
                                                        String appid = dataSnapshot.child("appid").getValue().toString();
                                                        String token = dataSnapshot.child("token").getValue().toString();
                                                        prefManager.SetAppID(appid);
                                                        prefManager.SetToken(token);
                                                        mProgressBar.setVisibility(View.GONE);
                                                        finish();
                                                    }else {
                                                        mProgressBar.setVisibility(View.GONE);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });


                                        } else {
                                            mProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(cn, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(cn, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void LoginUser(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mProgressBar.setVisibility(View.GONE);
                //   mProgress.dismiss();
                OneSignal.sendTag("id",email);
                OneSignal.setSubscription(true);

                mDatabase.child("Push").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            String appid = dataSnapshot.child("appid").getValue().toString();
                            String token = dataSnapshot.child("token").getValue().toString();
                            prefManager.SetAppID(appid);
                            prefManager.SetToken(token);
                            finish();
                        }else {
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                // Authenticaiton.AUTHFinish();

            } else {
                mProgressBar.setVisibility(View.GONE);
                // mProgress.hide();
                Toast.makeText(cn, task.getException().getMessage() + "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();

            }

        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

