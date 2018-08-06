package com.example.test.programmingmama.View.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.Comment_Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.TimeUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.instabug.library.core.eventbus.coreeventbus.SDKCoreEvent;

import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class Comments_List extends AppCompatActivity {

    Toolbar toolbar;
    TextView text;

    RecyclerView commentrec;
    EditText comment_add_et;

    int i;
    int id;
    int ListId;
    private ProgressDialog progressDialog;
    String Uid="";
    DatabaseReference mUser, mUserRef, mComment, mRootRef,mcmntRply;

    String modulename;

    FirebaseRecyclerAdapter<Comment_Model, Comments_VH> fr;
    FirebaseUser mFirebaseUser;
    Context cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments__list);
        i = getIntent().getIntExtra("i", 0);

        modulename=getIntent().getStringExtra("name");
        if (i == 0) {
            id = getIntent().getIntExtra("id", 0);
        } else {
            id = getIntent().getIntExtra("id", 0);
            ListId = getIntent().getIntExtra("Listid", 0);
            Log.d("CommentList", "ListID : "+ListId);
        }
        cn = this;

        init();
    }


    private void init() {

        text= findViewById(R.id.text);

        toolbar = findViewById(R.id.tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(cn);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mFirebaseUser!=null){
            Uid=mFirebaseUser.getUid();
        }

        mRootRef = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Users");

        mUserRef = GetFirebaseRef.GetDbIns().getReference()
                .child("Users")
                .child(Uid)
                .child("Comments")
                .child("likes");

        mUser = GetFirebaseRef.GetDbIns().getReference()
                .child("Users")
                .child(Uid)
                .child("Comments");

        if (i == 0) {
//            mUser = GetFirebaseRef.GetDbIns().getReference()
//                    .child("Users")
//                    .child(Uid)
//                    .child("Comments")
//                    .child(String.valueOf(id));

            mcmntRply = GetFirebaseRef
                    .GetDbIns()
                    .getReference()
                    .child("Reply")
                    .child("Comments")
                    .child(String.valueOf(id));

          //  mUser.keepSynced(true);
            mComment = GetFirebaseRef.GetDbIns().getReference()
                    .child("Comments")
                    .child(String.valueOf(id));
            mComment.keepSynced(true);
        } else {
            Log.d("CommentList 2", "ListID : "+ListId);
//            mUser = GetFirebaseRef.GetDbIns().getReference()
//                    .child("Users")
//                    .child(Uid)
//                    .child("Comments")
//                    .child(String.valueOf(id))
//                    .child(String.valueOf(ListId));

            mcmntRply = GetFirebaseRef
                    .GetDbIns()
                    .getReference()
                    .child("Reply")
                    .child("Comments")
                    .child(String.valueOf(id))
                    .child(String.valueOf(ListId));
          //  mUser.keepSynced(true);


            mComment = GetFirebaseRef.GetDbIns().getReference()
                    .child("Comments")
                    .child(String.valueOf(id))
                    .child(String.valueOf(ListId));
            mComment.keepSynced(true);
        }


        comment_add_et = findViewById(R.id.comment_add_et);

        findViewById(R.id.cmnt_fab).setOnClickListener(v -> {
            if(mFirebaseUser==null){
                startActivity(new Intent(cn, Authenticaiton.class));
            }else {

                AlertDialog.Builder builder = new AlertDialog.Builder(Comments_List.this);
                final View dialogView = getLayoutInflater().inflate(R.layout.ask_question_popup, null);

                EditText ed = dialogView.findViewById(R.id.ed);
                ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(600)});

                TextView cancel = dialogView.findViewById(R.id.cancel);
                TextView post = dialogView.findViewById(R.id.post);

                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                post.setOnClickListener(v1 -> {
                    String data = ed.getText().toString();
                    if (data.equals("") || data.equals(null)) {
                        Toast.makeText(cn, "Invalid Data", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        StoreDataOnFireBase(data);
                    }
                });
                cancel.setOnClickListener(vv -> dialog.dismiss());
                dialog.show();
            }
        });


        commentrec = findViewById(R.id.commentrec);
        commentrec.setHasFixedSize(true);
        commentrec.setLayoutManager(new LinearLayoutManager(cn));
        commentrec.setItemAnimator(new DefaultItemAnimator());
        commentrec.addItemDecoration(new ItemDecoration(0, 1, 0, 1));






        SetUpRecylerView();
    }

    private void SetUpRecylerView() {
        FirebaseRecyclerOptions<Comment_Model> options =
                new FirebaseRecyclerOptions.Builder<Comment_Model>()
                        .setQuery(mComment, Comment_Model.class)
                        .build();


        fr = new FirebaseRecyclerAdapter<Comment_Model, Comments_VH>(options) {
            @Override
            public Comments_VH onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cmnt_rec_layout, parent, false);

                return new Comments_VH(view);
            }

            @Override
            protected void onBindViewHolder(Comments_VH holder, int position, Comment_Model model) {
                holder.cmnt_rec_post.setText(model.getComment());
                holder.cmnt_rec_time.setText(TimeUtils.getTimeDate(model.getTime()));
                if(mFirebaseUser!=null){
                    if (!model.getUserId().equals(Uid)) {
                        holder.cmnt_rec_popup.setVisibility(View.GONE);
                    }
                    holder.cmnt_rec_popup.setOnClickListener(v -> {
                        PopupMenu popup = new PopupMenu(cn, v);
                        popup.getMenuInflater().inflate(R.menu.cmnt, popup.getMenu());
                        popup.setOnMenuItemClickListener(item -> {
                            mRootRef.child(Uid).child("Comments").child(model.getCmntId()).setValue(null).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    mComment.child(model.getCmntId()).setValue(null).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            mcmntRply.child(model.getCmntId()).setValue(null).addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful())
                                                    Toast.makeText(cn, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                            });
                                        }
                                    });
                                }
                            });

                            Toast.makeText(cn, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        });

                        popup.show();
                    });

                }else {
                    holder.cmnt_rec_popup.setVisibility(View.GONE);
                }

//                if (!model.getUserId().equals(Uid)){
//                    holder.cmnt_rec_popup.setVisibility(View.GONE);
//                }
//                holder.cmnt_rec_popup.setOnClickListener(v -> {
//                    PopupMenu popup = new PopupMenu(cn, v);
//                    popup.getMenuInflater().inflate(R.menu.cmnt, popup.getMenu());
//                    popup.setOnMenuItemClickListener(item -> {
//                        mRootRef.child(Uid).child("Comments").child(model.getCmntId()).setValue(null).addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                mComment.child(model.getCmntId()).setValue(null).addOnCompleteListener(task1 -> {
//                                    if (task1.isSuccessful()) {
//                                        mcmntRply.child(model.getCmntId()).setValue(null).addOnCompleteListener(task2 -> {
//                                            if (task2.isSuccessful())
//                                                Toast.makeText(cn, "Successfully Deleted", Toast.LENGTH_SHORT).show();
//                                        });
//                                    }
//                                });
//                            }
//                        });
//
//                        Toast.makeText(cn, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
//                        return true;
//                    });
//
//                    popup.show();
//                });
                holder.cmnt_rec_like.setText("Upvote (" + model.getLikes() + ")");
                holder.cmnt_rec_rply_lin.setOnClickListener(vv -> {

                    if (i == 0) {
                        startActivity(new Intent(cn, Reply_Activity.class)
                                .putExtra("cmntId", model.getCmntId())
                                .putExtra("i", i)
                                .putExtra("id", id)
                                .putExtra("ques",model.getComment()));
                    } else {
                        startActivity(new Intent(cn, Reply_Activity.class)
                                .putExtra("cmntId", model.getCmntId())
                                .putExtra("i", i)
                                .putExtra("id", id)
                                .putExtra("Listid", ListId)
                                .putExtra("ques",model.getComment()));
                    }


                });


                mcmntRply.child(model.getCmntId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        holder.cmnt_rec_cmnt.setText("Reply (" + dataSnapshot.getChildrenCount() + ")");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mRootRef
                        .child(model.getUserId())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    String name = dataSnapshot.child("name").getValue().toString();
                                    String image = dataSnapshot.child("badge").getValue().toString();
                                    if (!image.equals("") && !image.equals("null")) {
                                        Glide.with(cn).load(image).apply(fitCenterTransform()).into(holder.cmnt_rec_img);
                                    }


                                    UserCmntUpvote(model,holder);



                                    holder.cmnt_rec_name.setText(name);
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        };
        commentrec.setAdapter(fr);

        if (fr.getItemCount() < 0){
            text.setVisibility(View.VISIBLE);
        }
    }

    private void UserCmntUpvote(Comment_Model model, Comments_VH holder) {
        mUserRef.child(model.getCmntId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    String liked = ds.child("liked").getValue().toString();
                    if (liked.equals("false")) {

                        holder.cmnt_rec_like_lin.setOnClickListener(vv -> {
                            if(mFirebaseUser==null){
                                startActivity(new Intent(cn,Authenticaiton.class));
                            }
                            else {
                                HashMap map = new HashMap();
                                map.put("likes", model.getLikes() + 1);
                                mComment.child(model.getCmntId()).updateChildren(map);
                                HashMap umap = new HashMap();
                                umap.put("liked", "true");
                                mUserRef.child(model.getCmntId()).setValue(umap);
                            }

                        });
                    } else {
                        holder.cmnt_rec_like.setTextColor(R.color.like);
                        holder.cmnt_rec_like_lin.setOnClickListener(vv -> {
                            if(mFirebaseUser==null){
                                startActivity(new Intent(cn,Authenticaiton.class));
                            }
                            else {
                                HashMap map = new HashMap();
                                map.put("likes", model.getLikes() - 1);
                                mComment.child(model.getCmntId()).updateChildren(map);

                                HashMap umap = new HashMap();
                                umap.put("liked", "false");
                                mUserRef.child(model.getCmntId()).setValue(umap);
                            }

                        });
                    }
                } else {
                    holder.cmnt_rec_like_lin.setOnClickListener(vv -> {
                        if(mFirebaseUser==null){
                            startActivity(new Intent(cn,Authenticaiton.class));
                        }
                        else {
                            HashMap map = new HashMap();
                            map.put("likes", model.getLikes() + 1);
                            mComment.child(model.getCmntId()).updateChildren(map);
                            HashMap umap = new HashMap();
                            umap.put("liked", "true");
                            mUserRef.child(model.getCmntId()).setValue(umap);
                        }


                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        fr.startListening();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fr.stopListening();
    }

    private void StoreDataOnFireBase(String data) {
        progressDialog.setTitle("Uploading Data....");
        progressDialog.setMessage("Please wait while we upload and process your data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String key = mComment.push().getKey();
        HashMap CmntMap = new HashMap<>();
        CmntMap.put("comment", data);
        CmntMap.put("time", ServerValue.TIMESTAMP);
        CmntMap.put("userId", Uid);
        CmntMap.put("likes", 0);
        CmntMap.put("cmntId", key);


        mComment.child(key).setValue(CmntMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap UserMap = new HashMap<>();
                if (i==0){
                    UserMap.put("cmntId", key);
                    UserMap.put("modulename", modulename);
                    UserMap.put("moduleid", id);
                    UserMap.put("listid", 0);
                }else {
                    UserMap.put("cmntId", key);
                    UserMap.put("modulename", modulename);
                    UserMap.put("moduleid", id);
                    UserMap.put("listid", ListId);
                }


                mUser.child(key).setValue(UserMap).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(cn, "Successfully store data", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(cn, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(cn, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class Comments_VH extends RecyclerView.ViewHolder {
        TextView cmnt_rec_post, cmnt_rec_time, cmnt_rec_name, cmnt_rec_like, cmnt_rec_cmnt;
        ImageView cmnt_rec_popup, cmnt_rec_img;
        View mView;

        LinearLayout cmnt_rec_like_lin,  cmnt_rec_rply_lin;
        public Comments_VH(View itemView) {
            super(itemView);
            mView = itemView;
            cmnt_rec_post = itemView.findViewById(R.id.cmnt_rec_post);
            cmnt_rec_time = itemView.findViewById(R.id.cmnt_rec_time);
            cmnt_rec_name = itemView.findViewById(R.id.cmnt_rec_name);
            cmnt_rec_popup = itemView.findViewById(R.id.cmnt_rec_popup);
            cmnt_rec_like = itemView.findViewById(R.id.cmnt_rec_like);
            cmnt_rec_img = itemView.findViewById(R.id.cmnt_rec_img);


            cmnt_rec_cmnt = itemView.findViewById(R.id.cmnt_rec_cmnt);


            cmnt_rec_like_lin = itemView.findViewById(R.id.cmnt_rec_like_lin);

            cmnt_rec_rply_lin = itemView.findViewById(R.id.cmnt_rec_rply_lin);

        }


    }
}
