package com.example.test.programmingmama.View.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class Authenticaiton extends AppCompatActivity {
   static Context cn;

   Button loginbtn,signupbtn;
   RelativeLayout rlsignup,rllogin;

    //-----Generic------//
    PrefManager prefManager;
    ProgressBar mProgressBar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    //-----Generic------//


    //========Login=============//
    private EditText mLoginEmail;
    private int lastLengthOfEmail = 0;
    private EditText mLoginPass;
    SignInButton googlesignin;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    //========Login=============//


    //========Register=============//
    TextInputLayout reg_name,reg_email,reg_pass,reg_retypepass;
    Button reg_signup;
    UserProfileChangeRequest profileChangeRequest;
    //========Register=============//

//
//    private static final String TAG = "GoogleActivity";
//    private static final int RC_SIGN_IN = 9001;
//    private GoogleSignInClient mGoogleSignInClient;
//    private FirebaseAuth mAuth;
//
//    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_screen);

        cn=this;

        init();


//        GoogleOAuthFunc();
//        findViewById(R.id.googlesignin).setOnClickListener(v->signIn());
//        findViewById(R.id.cancel).setOnClickListener(v->finish());
//
//        findViewById(R.id.signin).setOnClickListener(vv->startActivity(new Intent(cn,LoginUser.class)));
//        findViewById(R.id.signup).setOnClickListener(vv->startActivity(new Intent(cn,RegisterUser.class)));

    }

    private void init() {
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn =  findViewById(R.id.signupbtn);
        rlsignup = findViewById(R.id.rlsignup);
        rllogin =  findViewById(R.id.rllogin);

        loginbtn.setOnClickListener(vv->{
            rllogin.setVisibility(View.VISIBLE);
            rlsignup.setVisibility(View.GONE);
            loginbtn.setAlpha(0.6f);
            signupbtn.setAlpha(1f);
            signupbtn.setClickable(true);
            loginbtn.setClickable(false);
        });
        signupbtn.setOnClickListener(vv->{
            rllogin.setVisibility(View.GONE);
            rlsignup.setVisibility(View.VISIBLE);
            signupbtn.setAlpha(0.6f);
            loginbtn.setAlpha(1f);
            signupbtn.setClickable(false);
            loginbtn.setClickable(true);
        });



        //-----Generic------//

        prefManager = new PrefManager(cn);
        mProgressBar = findViewById(R.id.progress);
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = GetFirebaseRef.GetDbIns().getReference();
        //-----Generic------//


        //========Login=============//
        GoogleOAuthFunc();
        googlesignin = findViewById(R.id.googlesignin);
        googlesignin.setOnClickListener(v -> signIn());
        setGooglePlusButtonText(googlesignin, "Continue With Gmail");
//        findViewById(R.id.signup).setOnClickListener(vv -> {
//            startActivity(new Intent(cn, RegisterUser.class));
//            //   finish();
//        });
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
        //========Login=============//

        //========Register=============//
        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_pass = findViewById(R.id.reg_pass);
        reg_retypepass = findViewById(R.id.reg_retypepass);

        reg_signup = findViewById(R.id.reg_signup);
        reg_signup.setOnClickListener(v -> {
            String display_name = reg_name.getEditText().getText().toString();
            String email = reg_email.getEditText().getText().toString();

            String password = reg_pass.getEditText().getText().toString();
            String password_retype = reg_retypepass.getEditText().getText().toString();

            if (display_name.equals("") || email.equals("") || password.equals("") || password_retype.equals("")) {

                Toast.makeText(cn, "Please enter a valid data ", Toast.LENGTH_SHORT).show();

            } else if (password.length() < 6) {

                Toast.makeText(cn, "Password Length is too small", Toast.LENGTH_SHORT).show();

            } else if (!password.equals(password_retype)) {

                Toast.makeText(cn, "The Retype password does not match", Toast.LENGTH_SHORT).show();

            } else if (password.equals(password_retype)) {
                mProgressBar.setVisibility(View.VISIBLE);
//                mDialog.setTitle("Registering User");
//                mDialog.setMessage("Please wait while we create your account!");
//                mDialog.setCanceledOnTouchOutside(false);
//
//                mDialog.show();
                register_user(display_name, email, password);
            }
        });
        findViewById(R.id.reg_signin).setOnClickListener(vv->{
            rllogin.setVisibility(View.VISIBLE);
            rlsignup.setVisibility(View.GONE);
            loginbtn.setAlpha(0.6f);
            signupbtn.setAlpha(1f);
            signupbtn.setClickable(true);
            loginbtn.setClickable(false);
        });
        //========Register=============//

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


    private void register_user(final String display_name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Task<AuthResult> task) -> {
            if (task.isSuccessful()) {

                FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                String Uid = currentuser.getUid();

                OneSignal.sendTag("id",currentuser.getEmail());
                OneSignal.setSubscription(true);

                profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(display_name).build();
                currentuser.updateProfile(profileChangeRequest);


                String DeviceToken = FirebaseInstanceId.getInstance().getToken();
                HashMap<String, String> UserMap = new HashMap<>();
                UserMap.put("name", display_name);
                UserMap.put("deviceToken", DeviceToken);
                UserMap.put("email", currentuser.getEmail());
                UserMap.put("badge", "null");
                UserMap.put("member_since", "" + Calendar.getInstance().get(Calendar.YEAR));



                mDatabase.child("Users")
                        .child(Uid)
                        .setValue(UserMap)
                        .addOnCompleteListener(task1 -> {

                            if (task1.isSuccessful()) {

                                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                                    mDatabase.child("Push").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange( DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()){
                                                String appid =dataSnapshot.child("appid").getValue().toString();
                                                String token =dataSnapshot.child("token").getValue().toString();
                                                prefManager.SetAppID(appid);
                                                prefManager.SetToken(token);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                                currentuser.sendEmailVerification()
                                        .addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                Toast.makeText(this, "A verification mail has been sent to "+email+". Please verify your email address.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                if (currentuser.getEmail() != null)
//                            OneSignalUtils.setEmail(currentuser.getEmail());

                                    mProgressBar.setVisibility(View.GONE);


                                finish();

                                //  Authenticaiton.AUTHFinish();

                            } else {

                                Log.d("RegisterUser", "register_user: "+ task1.getException().getMessage());

                            }
                        });


            } else {
                mProgressBar.setVisibility(View.GONE);
                String error = "";
                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    error = "Weak Password!";
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    error = "Invalid Email";
                } catch (FirebaseAuthUserCollisionException e) {
                    error = "Existing account!";
                } catch (Exception e) {

//                    Log.d(TAG, "onComplete: Exception: " + e.getMessage());
                    error = "Unknow error!";
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        });

    }


//    private void GoogleOAuthFunc() {
//
//
//
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        mAuth = FirebaseAuth.getInstance();
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//
//            }
//        }
//    }
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        String DeviceToken = FirebaseInstanceId.getInstance().getToken();
//                        mDatabase = GetFirebaseRef.GetDbIns().getReference().child("Users");
//                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot snapshot) {
//                                if (snapshot.hasChild(user.getUid())) {
//                                    //ARiWl5JvVfVIVz4SreTe9r4H5023
//                                    finish();
//                                }else {
//                                    HashMap<String, String> UserMap = new HashMap<>();
//                                    UserMap.put("name", user.getDisplayName());
//                                    UserMap.put("deviceToken", DeviceToken);
//                                    UserMap.put("email", user.getEmail());
//                                    UserMap.put("badge", "null");
//                                    UserMap.put("member_since", "" + Calendar.getInstance().get(Calendar.YEAR));
//
//                                    mDatabase.child(user.getUid()).setValue(UserMap).addOnCompleteListener(task1 -> {
//                                        if (task1.isSuccessful()) {
//                                            finish();
//                                        } else{
//                                            Toast.makeText(Authenticaiton.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//                            @Override
//                            public void onCancelled( DatabaseError databaseError) {
//
//                            }
//                        });
//                    } else {
//                        Toast.makeText(Authenticaiton.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    public static void AUTHFinish(){
//        Activity activity = (Activity) cn;
//        activity.finish();
//    }
}
