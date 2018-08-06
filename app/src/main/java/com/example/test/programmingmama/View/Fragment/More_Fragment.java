package com.example.test.programmingmama.View.Fragment;


import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.View.Activity.About_Us;
import com.example.test.programmingmama.View.Activity.Authenticaiton;
import com.example.test.programmingmama.View.Activity.LoginUser;
import com.example.test.programmingmama.View.Activity.WelcomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.instabug.library.Instabug;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;

public class More_Fragment extends Fragment {
    Context cn;
    private ProgressDialog mProgress;
    Realm realm;


    TextView signouttext;
    ImageView signoutimg;

    CardView AddpromoCard;

    PrefManager pref;

    ScrollView morescroll;

    FirebaseDatabase fDB = GetFirebaseRef.GetDbIns();

    DatabaseReference mDB = fDB.getReference().child("Users");

    TextView promotxt, refrcounter;
    LinearLayout promo;


    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.more_fragment, container, false);
        cn = this.getContext();
        realm = Realm.getDefaultInstance();
        init();
        return v;
    }

    private void PasswordRest() {
        mProgress.setTitle("Resseting your password");
        mProgress.setMessage("Please wait while we check your credentials");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mProgress.dismiss();
                        Toast.makeText(cn, "A Password reset link mail has been sent to your email.", Toast.LENGTH_SHORT).show();


                    }
                });
    }


    private void init() {
        AddpromoCard = v.findViewById(R.id.addpromo);
        pref = PrefManager.GetPrefManager(cn);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            GetFirebaseRef.GetDbIns().getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("reffered").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        AddpromoCard.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {
            if (pref.GetPROMOSTATUS()) {
                AddpromoCard.setVisibility(View.GONE);
            }
        }


        promotxt = v.findViewById(R.id.promotxt);
        promo = v.findViewById(R.id.promo);
        refrcounter = v.findViewById(R.id.refrcounter);


        mProgress = new ProgressDialog(cn);


        signouttext = v.findViewById(R.id.signouttext);
        signoutimg = v.findViewById(R.id.signoutimg);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            signouttext.setText("Sign In");
            signoutimg.setImageResource(R.drawable.login);
        }

        v.findViewById(R.id.reportusbug).setOnClickListener(vv -> {

            Instabug.invoke();
        });

        v.findViewById(R.id.taketour).setOnClickListener(vv -> {

            startActivity(new Intent(cn, WelcomeActivity.class).putExtra("welcome", 1));
        });
        v.findViewById(R.id.sharethisapp).setOnClickListener(vv -> {
            String body = this.getResources().getString(R.string.email_body_share_text) +
                    "\n"
                    + "https://play.google.com/store/apps/details?id=" + this.getResources().getString(R.string.app_package_name);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Install Master Planner Pro App");
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share Master Planner Pro using"));

        });

        v.findViewById(R.id.sharepremium).setOnClickListener(vv -> {
            String body = this.getResources().getString(R.string.email_body_share_text) +
                    "\n"
                    + "https://play.google.com/store/apps/details?id=" + this.getResources().getString(R.string.app_package_name);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Install Master Planner Pro App");
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share Master Planner Pro using"));

        });

        v.findViewById(R.id.feedback).setOnClickListener(vv -> {

            String body = null;
            try {
                body = cn.getPackageManager().getPackageInfo(cn.getPackageName(), 0).versionName;
                body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                        Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                        "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
            } catch (PackageManager.NameNotFoundException e) {
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{cn.getResources().getString(R.string.crux_email)});
            intent.putExtra(Intent.EXTRA_SUBJECT, cn.getResources().getString(R.string.crux_email_subject_help_feedback));
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, cn.getString(R.string.choose_email_client)));
        });

        v.findViewById(R.id.aboutus).setOnClickListener(v -> startActivity(new Intent(cn, About_Us.class)));

        v.findViewById(R.id.resetprogress).setOnClickListener(v -> ResetRealm());

        v.findViewById(R.id.signout).setOnClickListener(vv -> {

            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mUser != null) {
                FirebaseAuth.getInstance().signOut();


                GoogleSignIn.getClient(cn, new GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build())
                        .revokeAccess();

                OneSignal.logoutEmail();

                signouttext.setText("Sign In");
                signoutimg.setImageResource(R.drawable.login);

                Toast.makeText(cn, "Successfully Logout ", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(cn, Authenticaiton.class));
            }
        });

        v.findViewById(R.id.resetpass).setOnClickListener(vv -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                PasswordRest();
            } else {
                Toast.makeText(cn, "Please Sign In first.", Toast.LENGTH_SHORT).show();
            }
        });

        v.findViewById(R.id.buyuscoffe).setOnClickListener(vv -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(cn);
            final View dialogView = getLayoutInflater().inflate(R.layout.pickupcoffee, null);
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(dialogInterface -> {
                View view = dialog.getWindow().getDecorView();
                //for enter from left
                //ObjectAnimator.ofFloat(view, "translationX", -view.getWidth(), 0.0f).start();

                //for enter from bottom
                ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0.0f).start();
            });
            Window window = dialog.getWindow();

            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        });

        v.findViewById(R.id.becomepremiumlearner).setOnClickListener(vv -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(cn);
            final View dialogView = LayoutInflater.from(cn).inflate(R.layout.premium_module_popup, null);
            Button coffee = dialogView.findViewById(R.id.coffee);
            Button rfr = dialogView.findViewById(R.id.rfr);
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                fDB.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reffered")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    int i = (int) (5 - dataSnapshot.getChildrenCount());
                                    rfr.setText("Recommend to " + i + " friends.");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }


            coffee.setOnClickListener(vvv -> {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(cn);
                final View dialogView2 = LayoutInflater.from(cn).inflate(R.layout.pickupcoffee, null);
                builder2.setView(dialogView2);
                final AlertDialog dialog2 = builder2.create();
                dialog2.setOnShowListener(dialogInterface -> {
                    View view = dialog2.getWindow().getDecorView();
                    ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0.0f).start();
                });
                Window window = dialog2.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);
                dialog2.show();
                dialog.dismiss();
                dialog2.setOnDismissListener(dialog1 -> {
                    dialog.show();
                });
            });

            rfr.setOnClickListener(v -> {
                String body = cn.getResources().getString(R.string.email_body_share_text) +
                        "\n"
                        + "https://play.google.com/store/apps/details?id=" + cn.getResources().getString(R.string.app_package_name);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Install Master Planner Pro App");
                intent.putExtra(Intent.EXTRA_TEXT, body);
                cn.startActivity(Intent.createChooser(intent, "Share Master Planner Pro using"));
            });
            dialog.show();
        });


    }

    public boolean validateEmail(String email) {

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public void ResetRealm() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(cn, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(cn);
        }
        builder.setTitle("Warning!")
                .setMessage("All App Data Will be Deleted after Pressing “OK”")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // continue with delete
                    realm.executeTransaction(realm1 -> realm1.deleteAll());

                    // Reboot app
                    Intent mStartActivity = new Intent(cn.getApplicationContext(), MainActivity.class);
                    int mPendingIntentId = 123456;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(cn.getApplicationContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) cn.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        mgr.setExact(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    } else {
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    }
                    System.exit(0);
                    this.getActivity().finishAffinity();


                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            signouttext.setText("Sign In");
            signoutimg.setImageResource(R.drawable.login);
        } else {
            signouttext.setText("Sign Out");
            signoutimg.setImageResource(R.drawable.logout);
        }

        v.findViewById(R.id.addpromo).setOnClickListener(vv -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                    final View dialogView = getLayoutInflater().inflate(R.layout.user_promo_popup, null);
                    builder.setView(dialogView);
                    ProgressBar progress = dialogView.findViewById(R.id.progress);
                    TextInputLayout referemail = dialogView.findViewById(R.id.referemail);
                    Button submit = dialogView.findViewById(R.id.submit);
                    final AlertDialog dialog = builder.create();

                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        submit.setOnClickListener(v -> {

                            Toast.makeText(cn, " null call", Toast.LENGTH_SHORT).show();
                            String data = referemail.getEditText().getText().toString();
                            if (data.equals("") || data.equals(null)) {
                                Toast.makeText(cn, "Please add valid data", Toast.LENGTH_SHORT).show();
                            } else if (!validateEmail(data)) {
                                Toast.makeText(cn, "Please add valid email address", Toast.LENGTH_SHORT).show();
                            } else {
                                progress.setVisibility(View.VISIBLE);
                                //Find the User by useremail
                                GetFirebaseRef.GetDbIns()
                                        .getReference()
                                        .child("Users")
                                        .orderByChild("email").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                //Store Reffered user id
                                                String pushkey = fDB.getReference().child("Users").push().getKey();
                                                fDB.getReference().child("Users").child(ds.getKey()).child("reffered").child(pushkey)
                                                        .setValue("Anonymous")
                                                        .addOnCompleteListener(task -> {
                                                            if (task.isSuccessful()) {
                                                                fDB.getReference().child("Users").child(ds.getKey()).child("reffered")
                                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot.getChildrenCount() >= 5) {
                                                                                    fDB.getReference().child("Users").child(ds.getKey()).child("accsts")
                                                                                            .setValue("true").addOnCompleteListener(task2 -> {
                                                                                        progress.setVisibility(View.GONE);
                                                                                        AddpromoCard.setVisibility(View.GONE);
                                                                                        pref.SetPROMOSTATUS(true);
                                                                                        dialog.dismiss();
                                                                                    });
                                                                                } else {
                                                                                    AddpromoCard.setVisibility(View.GONE);
                                                                                    pref.SetPROMOSTATUS(true);
                                                                                    dialog.dismiss();
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                Log.d("PROMO", "onCancelled: 01 " + databaseError.getMessage());
                                                                            }
                                                                        });
                                                            } else {
                                                                progress.setVisibility(View.GONE);
                                                                Log.d("PROMO", "onCancelled: 01 " + task.getException().getMessage());
                                                            }
                                                        });
                                            }
                                        } else {
                                            Toast.makeText(cn, "No Data Found Based on this Email Address", Toast.LENGTH_LONG).show();

                                        }
                                        progress.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.d("PROMO", "onCancelled: " + databaseError.getMessage().toString());
                                    }
                                });
                            }
                        });
                    } else {
                        submit.setOnClickListener(v -> {
                            String data = referemail.getEditText().getText().toString();
                            if (data.equals("") || data.equals(null)) {
                                Toast.makeText(cn, "Please add valid data", Toast.LENGTH_SHORT).show();
                            } else if (!validateEmail(data)) {
                                Toast.makeText(cn, "Please add valid email address", Toast.LENGTH_SHORT).show();
                            } else if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(data)) {
                                Toast.makeText(cn, "Please Enter your friend Email Address", Toast.LENGTH_SHORT).show();
                            } else {
                                progress.setVisibility(View.VISIBLE);
                                //Find the User by useremail
                                mDB.orderByChild("email").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot datasnap) {
                                        if (datasnap.exists()) {
                                            for (DataSnapshot ds : datasnap.getChildren()) {
                                                //Store Reffered user id
                                                String pushkey = fDB.getReference().child("Users").push().getKey();
                                                fDB.getReference().child("Users").child(ds.getKey()).child("reffered").child(pushkey).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .addOnCompleteListener(task -> {
                                                            if (task.isSuccessful()) {
                                                                Map<String, Object> updates = new HashMap<>();
                                                                updates.put("refby", ds.getKey());
                                                                updates.put("refsts", true);
                                                                fDB.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(updates)
                                                                        .addOnCompleteListener(task1 -> {
                                                                            if (task1.isSuccessful()) {
                                                                                fDB.getReference().child("Users").child(ds.getKey()).child("reffered")
                                                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                if (dataSnapshot.getChildrenCount() >= 5) {
                                                                                                    fDB.getReference().child("Users").child(ds.getKey()).child("accsts")
                                                                                                            .setValue("true").addOnCompleteListener(task2 -> {
                                                                                                        progress.setVisibility(View.GONE);
//                                                                                                        promo.setVisibility(View.GONE);
//                                                                                                        promotxt.setVisibility(View.GONE
                                                                                                        AddpromoCard.setVisibility(View.GONE);
                                                                                                        dialog.dismiss();
                                                                                                    });
                                                                                                } else {
//
//                                                                                          /*          promo.setVisibility(View.GONE);
//                                                                                                    promotxt.setVisibility(View.GONE);*/
//
                                                                                                    AddpromoCard.setVisibility(View.GONE);
                                                                                                    dialog.dismiss();
                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(DatabaseError databaseError) {

                                                                                            }
                                                                                        });
                                                                            } else {
                                                                                progress.setVisibility(View.GONE);
                                                                            }
                                                                        });

                                                            } else {
                                                                progress.setVisibility(View.GONE);
                                                            }
                                                        });
                                            }
                                        } else {
                                            Toast.makeText(cn, "No Data Found Based on this Email Address", Toast.LENGTH_LONG).show();
                                        }
                                        progress.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.d("Promo", "onCancelled: " + databaseError.getMessage().toString());
                                    }
                                });
                            }
                        });
                    }
                    dialog.show();
                }
        );


        //todo cmnt here
//        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//            v.findViewById(R.id.addpromo).setOnClickListener(vv -> {
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(cn);
//                final View dialogView = getLayoutInflater().inflate(R.layout.user_promo_popup, null);
//                builder.setView(dialogView);
//                ProgressBar progress = dialogView.findViewById(R.id.progress);
//                TextInputLayout referemail = dialogView.findViewById(R.id.referemail);
//                Button submit = dialogView.findViewById(R.id.submit);
//                final AlertDialog dialog = builder.create();
//
//                submit.setOnClickListener(v -> {
//                    String data = referemail.getEditText().getText().toString();
//                    if (data.equals("") || data.equals(null)) {
//                        Toast.makeText(cn, "Please add valid data", Toast.LENGTH_SHORT).show();
//                    } else if (!validateEmail(data)) {
//                        Toast.makeText(cn, "Please add valid email address", Toast.LENGTH_SHORT).show();
//                    } else {
//                        progress.setVisibility(View.VISIBLE);
//                        //Find the User by useremail
//                        mDB.orderByChild("email").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                        //Store Reffered user id
//                                        String pushkey = fDB.getReference().child("Users").push().getKey();
//                                        fDB.getReference().child("Users").child(ds.getKey()).child("reffered").child(pushkey)
//                                                .setValue("Anonymous")
//                                                .addOnCompleteListener(task -> {
//                                                    if (task.isSuccessful()) {
//                                                        fDB.getReference().child("Users").child(ds.getKey()).child("reffered")
//                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.getChildrenCount() >= 5) {
//                                                                            fDB.getReference().child("Users").child(ds.getKey()).child("accsts")
//                                                                                    .setValue("true").addOnCompleteListener(task2 -> {
//                                                                                progress.setVisibility(View.GONE);
//                                                                                AddpromoCard.setVisibility(View.GONE);
//                                                                                pref.SetPROMOSTATUS(true);
//                                                                                dialog.dismiss();
//                                                                            });
//                                                                        } else {
//                                                                            AddpromoCard.setVisibility(View.GONE);
//                                                                            pref.SetPROMOSTATUS(true);
//                                                                            dialog.dismiss();
//                                                                        }
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(DatabaseError databaseError) {
//                                                                        Log.d("PROMO", "onCancelled: 01 " + databaseError.getMessage());
//                                                                    }
//                                                                });
//                                                    } else {
//                                                        progress.setVisibility(View.GONE);
//                                                    }
//                                                });
//                                    }
//                                } else {
//                                    Toast.makeText(cn, "No Data Found Based on this Email Address", Toast.LENGTH_LONG).show();
//                                }
//                                progress.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                Log.d("PROMO", "onCancelled: " + databaseError.getMessage().toString());
//                            }
//                        });
//                    }
//                });
//                dialog.show();
//
//            });
//        } else {
//            v.findViewById(R.id.addpromo).setOnClickListener(vv -> {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(cn);
//                final View dialogView = getLayoutInflater().inflate(R.layout.user_promo_popup, null);
//                builder.setView(dialogView);
//                ProgressBar progress = dialogView.findViewById(R.id.progress);
//                TextInputLayout referemail = dialogView.findViewById(R.id.referemail);
//                Button submit = dialogView.findViewById(R.id.submit);
//                final AlertDialog dialog = builder.create();
//
//                submit.setOnClickListener(v -> {
//                    String data = referemail.getEditText().getText().toString();
//                    if (data.equals("") || data.equals(null)) {
//                        Toast.makeText(cn, "Please add valid data", Toast.LENGTH_SHORT).show();
//                    } else if (!validateEmail(data)) {
//                        Toast.makeText(cn, "Please add valid email address", Toast.LENGTH_SHORT).show();
//                    } else if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(data)) {
//                        Toast.makeText(cn, "Please Enter your friend Email Address", Toast.LENGTH_SHORT).show();
//                    } else {
//                        progress.setVisibility(View.VISIBLE);
//                        //Find the User by useremail
//                        mDB.orderByChild("email").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot datasnap) {
//                                if (datasnap.exists()) {
//                                    for (DataSnapshot ds : datasnap.getChildren()) {
//                                        //Store Reffered user id
//                                        String pushkey = fDB.getReference().child("Users").push().getKey();
//                                        fDB.getReference().child("Users").child(ds.getKey()).child("reffered").child(pushkey).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                .addOnCompleteListener(task -> {
//                                                    if (task.isSuccessful()) {
//                                                        Map<String, Object> updates = new HashMap<>();
//                                                        updates.put("refby", ds.getKey());
//                                                        updates.put("refsts", true);
//                                                        fDB.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(updates)
//                                                                .addOnCompleteListener(task1 -> {
//                                                                    if (task1.isSuccessful()) {
//                                                                        fDB.getReference().child("Users").child(ds.getKey()).child("reffered")
//                                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                    @Override
//                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                                        if (dataSnapshot.getChildrenCount() >= 5) {
//                                                                                            fDB.getReference().child("Users").child(ds.getKey()).child("accsts")
//                                                                                                    .setValue("true").addOnCompleteListener(task2 -> {
//                                                                                                progress.setVisibility(View.GONE);
////                                                                                                        promo.setVisibility(View.GONE);
////                                                                                                        promotxt.setVisibility(View.GONE
//                                                                                                AddpromoCard.setVisibility(View.GONE);
//                                                                                                dialog.dismiss();
//                                                                                            });
//                                                                                        } else {
////
////                                                                                          /*          promo.setVisibility(View.GONE);
////                                                                                                    promotxt.setVisibility(View.GONE);*/
////
//                                                                                            AddpromoCard.setVisibility(View.GONE);
//                                                                                            dialog.dismiss();
//                                                                                        }
//                                                                                    }
//
//                                                                                    @Override
//                                                                                    public void onCancelled(DatabaseError databaseError) {
//
//                                                                                    }
//                                                                                });
//                                                                    } else {
//                                                                        progress.setVisibility(View.GONE);
//                                                                    }
//                                                                });
//
//                                                    } else {
//                                                        progress.setVisibility(View.GONE);
//                                                    }
//                                                });
//                                    }
//                                } else {
//                                    Toast.makeText(cn, "No Data Found Based on this Email Address", Toast.LENGTH_LONG).show();
//                                }
//                                progress.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                Log.d("Promo", "onCancelled: " + databaseError.getMessage().toString());
//                            }
//                        });
//                    }
//                });
//                dialog.show();
//
//            });
//            //todo refrcounter
//            fDB.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reffered")
//                    .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                int i = (int) (5 - dataSnapshot.getChildrenCount());
//                                refrcounter.setText(i + " remaining...");
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//        }


    }
}
