package com.example.test.programmingmama.View.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.Comment_Model;
import com.example.test.programmingmama.Model.Reply_Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.HideKeyboard;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.OneSignalUtils;
import com.example.test.programmingmama.Utils.TimeUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.HashMap;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class Reply_Activity extends AppCompatActivity {


    Toolbar toolbar;

    String userID="";

    String FRuserID="";
    FrameLayout rplyquesframe;
    TextView rplyques;

    RecyclerView rply_rec;
    EditText rply_add_et;
    ImageView rply_add_icon;
    private ProgressDialog progressDialog;

    String cmntId;

    DatabaseReference mRootRef;
    DatabaseReference mUserRef;
    Context cn;

    FirebaseRecyclerAdapter<Reply_Model, Reply_VH> fr;

    int i;
    int id;
    int Listid;

    String frmId;

    String Ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reply);


        i = getIntent().getIntExtra("i", 0);
        Ques =  getIntent().getStringExtra("ques");
        if (i == 0) {
            cmntId = getIntent().getStringExtra("cmntId");
            id = getIntent().getIntExtra("id", 0);
        } else if (i == 1) {
            cmntId = getIntent().getStringExtra("cmntId");
            id = getIntent().getIntExtra("id", 0);
            Listid = getIntent().getIntExtra("Listid", 0);
            Log.d("Reply Acitiviy", "ListID  : "+Listid);
        } else {
            frmId = getIntent().getStringExtra("frmId");
            FRuserID =  getIntent().getStringExtra("userId");

        }
        cn = this;
        rplyquesframe = findViewById(R.id.rplyquesframe);
        rplyques = findViewById(R.id.rplyques);
        rplyques.setText(Ques);

        if (i == 2) {
            init2();
        }
        else {
            init();
        }



    }

    private void init() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }

        mUserRef = GetFirebaseRef.GetDbIns().getReference()
                .child("Users");
        if (i == 0) {
            mRootRef = GetFirebaseRef
                    .GetDbIns()
                    .getReference()
                    .child("Reply")
                    .child("Comments")
                    .child(String.valueOf(id))
                    .child(cmntId);
        } else {

            Log.d("Reply Acitiviy", "ListID 2 : "+Listid);
            mRootRef = GetFirebaseRef
                    .GetDbIns()
                    .getReference()
                    .child("Reply")
                    .child("Comments")
                    .child(String.valueOf(id))
                    .child(String.valueOf(Listid))
                    .child(cmntId);
        }


        toolbar = findViewById(R.id.tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(cn);


        rply_add_et = findViewById(R.id.rply_add_et);
        rply_add_icon = findViewById(R.id.rply_add_icon);

        rply_add_icon.setOnClickListener(v -> {
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                HideKeyboard.hideKeyboard((Activity) cn);
                String data = rply_add_et.getText().toString();
                if (data.equals("") || data.equals(null)) {
                    Toast.makeText(cn, "Invalid Data", Toast.LENGTH_SHORT).show();
                } else {
                    StoreReplyOnFireBase(data);
                }
            }else {
                startActivity(new Intent(cn,LoginUser.class));
            }

        });
        rply_rec = findViewById(R.id.rply_rec);
        LinearLayoutManager lin = new LinearLayoutManager(this);

        rply_rec.setHasFixedSize(true);
        rply_rec.setLayoutManager(lin);
        rply_rec.setItemAnimator(new DefaultItemAnimator());
        rply_rec.addItemDecoration(new ItemDecoration(2, 0, 2, 2));
        rply_rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//
                if (dy > 0) {
                    rplyquesframe.setVisibility(View.GONE);
                } else {
                    rplyquesframe.setVisibility(View.VISIBLE);
                }
            }
        });

        SetUpFireBaseRecAdp();


    }

    private void SetUpFireBaseRecAdp() {

        FirebaseRecyclerOptions<Reply_Model> options =
                new FirebaseRecyclerOptions.Builder<Reply_Model>()
                        .setQuery(mRootRef, Reply_Model.class)
                        .build();

        fr = new FirebaseRecyclerAdapter<Reply_Model, Reply_VH>(options) {
            @Override
            public Reply_VH onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.reply_rec_layout, parent, false);

                return new Reply_VH(view);
            }

            @Override
            protected void onBindViewHolder(Reply_VH holder, int position, Reply_Model model) {
                holder.rply_rec_post.setText(model.getReply());
                holder.rply_rec_time.setText(TimeUtils.getTimeDate(model.getTime()));


                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    if (model.getUid().equals(userID)) {
                        holder.rply_rec_popup.setVisibility(View.VISIBLE);
                    }
                    holder.rply_rec_popup.setOnClickListener(v -> {
                        PopupMenu popup = new PopupMenu(cn, v);

                        popup.getMenuInflater().inflate(R.menu.cmnt, popup.getMenu());


                        popup.setOnMenuItemClickListener(item -> {
                            Toast.makeText(cn, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        });

                        popup.show();
                    });

                }


                mUserRef.child(model.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            String name = dataSnapshot.child("name").getValue().toString();
                            holder.rply_rec_name.setText(name);
                            String image = dataSnapshot.child("badge").getValue().toString();
                            if (!image.equals("") || !image.equals(null) || !image.equals("null")) {
                                Glide.with(cn).load(image).apply(fitCenterTransform()).into(holder.rply_rec_img);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };
        rply_rec.setAdapter(fr);

    }


    private void init2() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }

        mUserRef = GetFirebaseRef.GetDbIns().getReference()
                .child("Users");

        mRootRef = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Reply")
                .child("Forums")
                .child(frmId);


        toolbar = findViewById(R.id.tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(cn);


        rply_add_et = findViewById(R.id.rply_add_et);
        rply_add_icon = findViewById(R.id.rply_add_icon);

        rply_add_icon.setOnClickListener(v -> {
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                HideKeyboard.hideKeyboard((Activity) cn);
                String data = rply_add_et.getText().toString();
                if (data.equals("") || data.equals(null)) {
                    Toast.makeText(cn, "Invalid Data", Toast.LENGTH_SHORT).show();
                } else {
                    StoreReplyOnFireBase(data);
                }
            }else {
                startActivity(new Intent(cn,LoginUser.class));
            }

        });
        rply_rec = findViewById(R.id.rply_rec);
        LinearLayoutManager lin = new LinearLayoutManager(this);

        rply_rec.setHasFixedSize(true);
        rply_rec.setLayoutManager(lin);
        rply_rec.setItemAnimator(new DefaultItemAnimator());
        rply_rec.addItemDecoration(new ItemDecoration(2, 0, 2, 2));
        rply_rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//
                if (dy > 0) {
                    rplyquesframe.setVisibility(View.GONE);
                } else {
                    rplyquesframe.setVisibility(View.VISIBLE);
                }
            }
        });

        SetUpFireBaseRecAdp2();

    }

    private void SetUpFireBaseRecAdp2() {
        FirebaseRecyclerOptions<Reply_Model> options =
                new FirebaseRecyclerOptions.Builder<Reply_Model>()
                        .setQuery(mRootRef, Reply_Model.class)
                        .build();



        fr = new FirebaseRecyclerAdapter<Reply_Model, Reply_VH>(options) {
            @Override
            public Reply_VH onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.reply_rec_layout, parent, false);

                return new Reply_VH(view);
            }

            @Override
            protected void onBindViewHolder(Reply_VH holder, int position, Reply_Model model) {
                holder.rply_rec_post.setText(model.getReply());
                holder.rply_rec_time.setText(TimeUtils.getTimeDate(model.getTime()));


                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    if (model.getUid().equals(userID)) {
                        holder.rply_rec_popup.setVisibility(View.VISIBLE);
                    }
                    holder.rply_rec_popup.setOnClickListener(v -> {
                        PopupMenu popup = new PopupMenu(cn, v);

                        popup.getMenuInflater().inflate(R.menu.cmnt, popup.getMenu());


                        popup.setOnMenuItemClickListener(item -> {
                            Toast.makeText(cn, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        });

                        popup.show();
                    });

                }

                mUserRef.child(model.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            String name = dataSnapshot.child("name").getValue().toString();
                            holder.rply_rec_name.setText(name);
                            String image = dataSnapshot.child("badge").getValue().toString();
                            if (!image.equals("") || !image.equals(null) || !image.equals("null")) {
                                Glide.with(cn).load(image).apply(fitCenterTransform()).into(holder.rply_rec_img);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };
        rply_rec.setAdapter(fr);
    }

    private void StoreReplyOnFireBase(String data) {


        progressDialog.setTitle("Uploading Data....");
        progressDialog.setMessage("Please wait while we upload and process your data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (i == 2) {
            String key = mRootRef.push().getKey();
            HashMap CmntMap = new HashMap<>();
            CmntMap.put("reply", data);
            CmntMap.put("time", ServerValue.TIMESTAMP);
            CmntMap.put("Uid", userID);



            mRootRef.child(key).setValue(CmntMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("UserId", "Forum "+ FRuserID);
                  mUserRef.child(FRuserID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        Log.d("UserId", "Forum  true"+ FRuserID);
                                        String name =FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                                        String msg = name+" is commented on your forum post";
                                        String email =dataSnapshot.child("email").getValue().toString();
                                        try {
                                            OneSignalUtils.netWorkCall(cn,msg,email);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }else {
                                        Log.d("UserId", "Forum  ds false"+ FRuserID);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d("UserId", "Forum  ds false"+ databaseError.getMessage());
                                }
                            });


//                    String name =FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
//                    String msg = name+" is commented on your forum post";
//                    try {
//                        OneSignalUtils.netWorkCall(cn,msg,FRuserID);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    progressDialog.dismiss();
                    rply_add_et.getText().clear();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(cn, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            String key = mRootRef.push().getKey();
            HashMap CmntMap = new HashMap<>();
            CmntMap.put("reply", data);
            CmntMap.put("time", ServerValue.TIMESTAMP);
            CmntMap.put("Uid", userID);


            mRootRef.child(key).setValue(CmntMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    rply_add_et.getText().clear();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(cn, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void onStart() {
        super.onStart();
        fr.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fr.stopListening();
    }


    public static class Reply_VH extends RecyclerView.ViewHolder {
        TextView rply_rec_post, rply_rec_time, rply_rec_name;
        ImageView rply_rec_popup, rply_rec_img;
        View mView;

        public Reply_VH(View itemView) {
            super(itemView);
            mView = itemView;
            rply_rec_post = itemView.findViewById(R.id.rply_rec_post);
            rply_rec_time = itemView.findViewById(R.id.rply_rec_time);
            rply_rec_name = itemView.findViewById(R.id.rply_rec_name);
            rply_rec_popup = itemView.findViewById(R.id.rply_rec_popup);
            rply_rec_img = itemView.findViewById(R.id.rply_rec_img);
        }
    }
}