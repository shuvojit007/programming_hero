package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;
import java.util.HashSet;

public class FeedBack_Module extends AppCompatActivity {

    Context cn;
    Toolbar toolbar;

    Button Submit;

    SmileRating smile_rating;
    RadioGroup r1,r2;
    EditText e2a,e2b,e2c,e3a,e3b,e3c,e4a,e4b,e4c;

    DatabaseReference mDb = GetFirebaseRef.GetDbIns().getReference().child("Feedback").child("module");
    FirebaseAuth mAuth;


    String smile ="";
    String radiotext1="";
    String radiotext2="";

    String ans1a ="",ans1b ="",ans1c="";
    String ans2a ="",ans2b ="",ans2c="";
    String ans3a ="",ans3b ="",ans3c="";

    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back__module);
        cn =this;


        init();
    }

    private void init() {

        progress = findViewById(R.id.progress);
        mAuth =FirebaseAuth.getInstance();
        toolbar= findViewById(R.id.feed_tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Feedback");

        Submit = findViewById(R.id.submit);
        smile_rating = findViewById(R.id.smile_rating);

        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);

        e2a = findViewById(R.id.e2a);
        e2b = findViewById(R.id.e2b);
        e2c = findViewById(R.id.e2c);

        e3a = findViewById(R.id.e3a);
        e3b = findViewById(R.id.e3b);
        e3c = findViewById(R.id.e3c);

        e4a = findViewById(R.id.e4a);
        e4b = findViewById(R.id.e4b);
        e4c = findViewById(R.id.e4c);


        smile_rating.setOnSmileySelectionListener((smiley, reselected) -> {

            switch (smiley) {
                case SmileRating.BAD:
                    smile="BAD";
                    break;
                case SmileRating.GOOD:
                    smile="GOOD";
                    break;
                case SmileRating.GREAT:
                    smile="GREAT";
                    break;
                case SmileRating.OKAY:
                    smile="OKAY";
                    break;
                case SmileRating.TERRIBLE:
                    smile="TERRIBLE";
                    break;
            }
        });
        r1.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton rb= findViewById(checkedId);
            radiotext1=rb.getText().toString();
        }));
        r2.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton rb= findViewById(checkedId);
            radiotext2=rb.getText().toString();
        }));

        Submit.setOnClickListener(vv->{
            progress.setVisibility(View.VISIBLE);
            SubmitData();
        });
    }

    private void SubmitData() {

        new StringBuilder().append(" A. "+e2a.getText().toString()+ "\n B. "+e2b.getText().toString()+ "\n C. "+e2c.getText().toString());

        HashMap  map = new HashMap();
        map.put("rating",smile);
        map.put("How Likely will you Recommend this app?",radiotext1);
        map.put("How you heard about this app?",radiotext2);
        map.put("What are the three things you have liked?",
                new StringBuilder().append(" A. "
                        +e2a.getText().toString()+ "\n B. "
                        +e2b.getText().toString()+ "\n C. "
                        +e2c.getText().toString()).toString());
        map.put("What are the three things we should improve",
                new StringBuilder().append(" A. "
                        +e2a.getText().toString()+ "\n B. "
                        +e2b.getText().toString()+ "\n C. "
                        +e2c.getText().toString()).toString());
        map.put("We are planning to make more apps and more content Which three we should do first",
                new StringBuilder().append(" A. "
                        +e2a.getText().toString()+ "\n B. "
                        +e2b.getText().toString()+ "\n C. "
                        +e2c.getText().toString()).toString());


        String PushKey =mDb.push().getKey();

        if(mAuth.getCurrentUser()!=null){
            mDb.child(mAuth
                    .getCurrentUser()
                    .getUid())
                    .child(PushKey)
                    .setValue(map)
                    .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(cn, "Sucessfully Store your Feedback", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(cn,MainActivity.class));
                    finishAffinity();
                }else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(cn, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    Log.d("MFeed", task.getException().getMessage());

                }
            });

        }else {
            mDb.child("Anonymous")
                    .child(PushKey)
                    .setValue(map)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            progress.setVisibility(View.GONE);
                            Toast.makeText(cn, "Sucessfully Store your Feedback", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(cn,MainActivity.class));
                            finishAffinity();
                        }else {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(cn, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            Log.d("MFeed", task.getException().getMessage());
                        }
                    });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(cn, MainActivity.class));
        finishAffinity();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
