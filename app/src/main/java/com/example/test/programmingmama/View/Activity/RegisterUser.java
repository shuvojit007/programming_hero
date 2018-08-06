package com.example.test.programmingmama.View.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import java.util.Calendar;
import java.util.HashMap;

public class RegisterUser extends AppCompatActivity {
    Toolbar toolbar;

    TextInputLayout reg_name,reg_email,reg_pass,reg_retypepass;
    Button reg_signup;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;

    PrefManager prefManager;
    private ProgressDialog mDialog;
    UserProfileChangeRequest profileChangeRequest;
    Context cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_user);
        mFirebaseAuth = FirebaseAuth.getInstance();
        cn=this;
        init();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void init() {
        prefManager =new PrefManager(cn);
        mDatabase = GetFirebaseRef.GetDbIns().getReference();
        toolbar = findViewById(R.id.tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDialog = new ProgressDialog(cn);
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

                mDialog.setTitle("Registering User");
                mDialog.setMessage("Please wait while we create your account!");
                mDialog.setCanceledOnTouchOutside(false);

                mDialog.show();
                register_user(display_name, email, password);
            }
        });
        findViewById(R.id.reg_signin).setOnClickListener(vv->{
            startActivity(new Intent(cn,LoginUser.class));
            finish();
        });
    }
    private void register_user(final String display_name, String email, String password) {

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Task<AuthResult> task) -> {
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

                        mDialog.dismiss();


                        finish();

                     //  Authenticaiton.AUTHFinish();

                    } else {

                        Log.d("RegisterUser", "register_user: "+ task1.getException().getMessage());

                    }
                });


            } else {
                mDialog.hide();
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


}
