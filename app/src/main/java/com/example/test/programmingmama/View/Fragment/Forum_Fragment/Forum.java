package com.example.test.programmingmama.View.Fragment.Forum_Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.All_Forum_Model;
import com.example.test.programmingmama.Model.Comment_Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.OneSignalUtils;
import com.example.test.programmingmama.Utils.TimeUtils;
import com.example.test.programmingmama.View.Activity.Authenticaiton;
import com.example.test.programmingmama.View.Activity.Comments_List;
import com.example.test.programmingmama.View.Activity.LoginUser;
import com.example.test.programmingmama.View.Activity.Reply_Activity;
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

public class Forum extends Fragment {
    View v;

    ProgressBar progress;

    RecyclerView all_forum_rec;
    private ProgressDialog progressDialog;
    String Uid = "";
    DatabaseReference mForumRef, mUserRef, mUserLike, mUserDislike, mRootRef, mFrmRply;
    FloatingActionButton fab;

    FirebaseRecyclerAdapter<All_Forum_Model, ALL_Forum_VH> fr;
    Context cn;

    FirebaseUser mFirebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.all_forum, container, false);
        cn = this.getContext();

        init();
        return v;
    }

    private void init() {
        progress =v.findViewById(R.id.progress);
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mFirebaseUser!=null){
            Uid=mFirebaseUser.getUid();
        }
        fab = v.findViewById(R.id.all_forum_fab);

        mFrmRply = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Reply")
                .child("Forums");

        mRootRef = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Users");
        mRootRef.keepSynced(true);

        mForumRef = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Forums");
        mForumRef.keepSynced(true);

        mUserRef = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Users")
                .child(Uid)
                .child("Forums");
        mUserRef.keepSynced(true);

        mUserLike = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Users")
                .child(Uid)
                .child("Forums")
                .child("likes");
        mUserLike.keepSynced(true);



        mUserDislike = GetFirebaseRef
                .GetDbIns()
                .getReference()
                .child("Users")
                .child(Uid)
                .child("Forums")
                .child("dislikes");
        mUserDislike.keepSynced(true);

        progressDialog = new ProgressDialog(cn);
        all_forum_rec = v.findViewById(R.id.all_forum_rec);
        all_forum_rec.setHasFixedSize(true);
        all_forum_rec.setLayoutManager(new LinearLayoutManager(cn));
        all_forum_rec.setItemAnimator(new DefaultItemAnimator());
        all_forum_rec.addItemDecoration(new ItemDecoration(0, 1, 0, 1));
        all_forum_rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//
                if (dy > 0) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
        fab.setOnClickListener(vv -> {

                if(mFirebaseUser==null){
                    startActivity(new Intent(cn, Authenticaiton.class));
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                    final View dialogView = getLayoutInflater().inflate(R.layout.ask_question_popup, null);

                    EditText ed = dialogView.findViewById(R.id.ed);
                    ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(600)});

                    TextView remainingtext = dialogView.findViewById(R.id.remainingtext);

                    final TextWatcher mTextEditorWatcher = new TextWatcher() {
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //This sets a textview to the current length
                            remainingtext.setText("You have reamaining " + String.valueOf(600 - s.length()));
                        }

                        public void afterTextChanged(Editable s) {
                        }
                    };

                    ed.addTextChangedListener(mTextEditorWatcher);
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
                            StoreForumOnFireBase(data);
                        }
                    });
                    cancel.setOnClickListener(vvv -> dialog.dismiss());
                    dialog.show();
            }




        });


        SetUpFbRec();
    }


    @Override
    public void onStart() {
        super.onStart();
        fr.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fr.stopListening();
    }

    private void StoreForumOnFireBase(String data) {
//        progressDialog.setTitle("Uploading Data....");
//        progressDialog.setMessage("Please wait while we upload and process your data");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        progress.setVisibility(View.VISIBLE);
        String key = mForumRef.push().getKey();
        HashMap ForumMap = new HashMap<>();
        ForumMap.put("comment", data);
        ForumMap.put("time", ServerValue.TIMESTAMP);
        ForumMap.put("userId", Uid);
        ForumMap.put("likes", 0);
        ForumMap.put("dislikes", 0);
        ForumMap.put("frmId", key);


        mForumRef.child(key).setValue(ForumMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap UserMap = new HashMap<>();
                UserMap.put("frmId", key);

                String userkey = mUserRef.push().getKey();
                mUserRef.child(userkey).setValue(UserMap).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                     //   progressDialog.dismiss();
                        Toast.makeText(cn, "Successfully store data", Toast.LENGTH_SHORT).show();

                    } else {
                        progress.setVisibility(View.GONE);
                     //   progressDialog.dismiss();
                        Toast.makeText(cn, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                progress.setVisibility(View.GONE);
              //  progressDialog.dismiss();
                Toast.makeText(cn, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetUpFbRec() {
        progress.setVisibility(View.VISIBLE);
       // progressDialog.setTitle("Fetching Data....");
       // progressDialog.setMessage("Please wait while we upload and process your data");
      //  progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.show();
        FirebaseRecyclerOptions<All_Forum_Model> options =
                new FirebaseRecyclerOptions.Builder<All_Forum_Model>()
                        .setQuery(mForumRef, All_Forum_Model.class)
                        .build();

        fr = new FirebaseRecyclerAdapter<All_Forum_Model, ALL_Forum_VH>(options) {
            @Override
            public ALL_Forum_VH onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.all_forum_rec_layout, parent, false);

                return new ALL_Forum_VH(view);
            }

            @Override
            protected void onBindViewHolder(ALL_Forum_VH holder, int position, All_Forum_Model model) {
                holder.af_rec_post.setText(model.getComment());
                holder.af_rec_time.setText(TimeUtils.getTimeDate(model.getTime()));
                holder.af_rec_dislike.setText("(" + model.getDislikes() + ")");
                holder.af_rec_like.setText("(" + model.getLikes() + ")");

                if(mFirebaseUser!=null){
                    if (!model.getUserId().equals(Uid)) {
                        holder.af_rec_popup.setVisibility(View.GONE);
                    }
                    holder.af_rec_popup.setOnClickListener(v -> {
                        PopupMenu popup = new PopupMenu(cn, v);
                        popup.getMenuInflater().inflate(R.menu.cmnt, popup.getMenu());
                        popup.setOnMenuItemClickListener(item -> {
                            mUserRef.child(model.getFrmId()).setValue(null).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    mForumRef.child(model.getFrmId()).setValue(null).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            mFrmRply.child(model.getFrmId()).setValue(null).addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful())
                                                    Toast.makeText(cn, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                            });
                                        }
                                    });
                                }
                            });
                            return true;
                        });

                        popup.show();
                    });

                }else {
                    holder.af_rec_popup.setVisibility(View.GONE);
                }


                mFrmRply.child(model.getFrmId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        holder.af_rec_rply.setText("(" + dataSnapshot.getChildrenCount() + ")");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                holder.af_rec_rply_lin.setOnClickListener(vv -> {
                    Log.d("UserId", "Forum "+ model.getUserId());
                    startActivity(new Intent(cn, Reply_Activity.class)
                            .putExtra("frmId", model.getFrmId())
                            .putExtra("userId", model.getUserId())
                            .putExtra("i", 2)
                        .putExtra("ques",model.getComment()));
                });

                holder.af_rec_post.setOnClickListener(vv -> {

                    Log.d("UserId", "Forum "+ model.getUserId());
                    startActivity(new Intent(cn, Reply_Activity.class)
                            .putExtra("frmId", model.getFrmId())
                            .putExtra("userId", model.getUserId())
                            .putExtra("i", 2)
                            .putExtra("ques",model.getComment()));
                });


                mRootRef.child(model.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            String image = dataSnapshot.child("badge").getValue().toString();
                            if (!image.equals("") ||!image.equals("null")) {
                                Glide.with(cn).load(image).apply(fitCenterTransform()).into(holder.af_rec_img);
                            }


                                UserCmntUpvote(model, holder);


                            holder.af_rec_name.setText(name);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            @Override
            public void onDataChanged() {
                progress.setVisibility(View.GONE);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        };

        all_forum_rec.setAdapter(fr);

    }

    private void UserCmntUpvote(All_Forum_Model model, ALL_Forum_VH holder) {
        mUserLike.child(model.getFrmId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    String liked = ds.child("liked").getValue().toString();
                    if (liked.equals("false")) {
                        holder.af_rec_like_lin.setOnClickListener(vv -> {
                            if(mFirebaseUser==null){
                                startActivity(new Intent(cn,Authenticaiton.class));
                            }
                            else {
                                PushNotificaiotn(model.getUserId(),"like");
                                HashMap map = new HashMap();
                                map.put("likes", model.getLikes() + 1);
                                mForumRef.child(model.getFrmId()).updateChildren(map);
                                HashMap umap = new HashMap();
                                umap.put("liked", "true");
                                mUserLike.child(model.getFrmId()).setValue(umap);

                            }

                        });
                    } else {

                        holder.af_rec_like.setTextColor(R.color.like);
                        holder.af_rec_like_lin.setOnClickListener(vv -> {
                            if(mFirebaseUser==null){
                                startActivity(new Intent(cn,Authenticaiton.class));
                            }else {
                                HashMap map = new HashMap();
                                map.put("likes", model.getLikes() - 1);
                                mForumRef.child(model.getFrmId()).updateChildren(map);

                                HashMap umap = new HashMap();
                                umap.put("liked", "false");
                                mUserLike.child(model.getFrmId()).setValue(umap);
                            }

                        });
                    }
                } else {
                    holder.af_rec_like_lin.setOnClickListener(vv -> {
                        if(mFirebaseUser==null){
                            startActivity(new Intent(cn,Authenticaiton.class));
                        }else {
                            PushNotificaiotn(model.getUserId(),"like");
                            HashMap map = new HashMap();
                            map.put("likes", model.getLikes() + 1);
                            mForumRef.child(model.getFrmId()).updateChildren(map);
                            HashMap umap = new HashMap();
                            umap.put("liked", "true");
                            mUserLike.child(model.getFrmId()).setValue(umap);
                        }

                    });
                }

                progress.setVisibility(View.GONE);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mUserDislike.child(model.getFrmId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    String liked = ds.child("disliked").getValue().toString();
                    if (liked.equals("false") || liked.equals(null)) {
                        holder.af_rec_dislike_lin.setOnClickListener(vv -> {
                            if(mFirebaseUser==null){
                                startActivity(new Intent(cn,Authenticaiton.class));
                            }else {
                                PushNotificaiotn(model.getUserId(),"dislike");
                                HashMap map = new HashMap();
                                map.put("dislikes", model.getDislikes() + 1);
                                mForumRef.child(model.getFrmId()).updateChildren(map);
                                HashMap umap = new HashMap();
                                umap.put("disliked", "true");
                                mUserDislike.child(model.getFrmId()).setValue(umap);
                            }
                        });
                    } else {
                        holder.af_rec_dislike.setTextColor(R.color.like);
                        holder.af_rec_dislike_lin.setOnClickListener(vv -> {
                            if(mFirebaseUser==null){
                                startActivity(new Intent(cn,Authenticaiton.class));
                            }else {

                                HashMap map = new HashMap();
                                map.put("dislikes", model.getDislikes() - 1);
                                mForumRef.child(model.getFrmId()).updateChildren(map);

                                HashMap umap = new HashMap();
                                umap.put("disliked", "false");
                                mUserDislike.child(model.getFrmId()).setValue(umap);
                            }
                        });
                    }
                } else {
                    holder.af_rec_dislike_lin.setOnClickListener(vv -> {
                        if(mFirebaseUser==null){
                            startActivity(new Intent(cn,Authenticaiton.class));
                        }else {
                            PushNotificaiotn(model.getUserId(),"dislike");
                            HashMap map = new HashMap();
                            map.put("dislikes", model.getDislikes() + 1);
                            mForumRef.child(model.getFrmId()).updateChildren(map);
                            HashMap umap = new HashMap();
                            umap.put("disliked", "true");
                            mUserDislike.child(model.getFrmId()).setValue(umap);
                        }
                    });
                }
                progress.setVisibility(View.GONE);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void PushNotificaiotn(String UserId, String type)
    {
        mRootRef.child(UserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String msg;

                            Log.d("UserId", "Forum  true"+ UserId);
                            String name =FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                            String email =dataSnapshot.child("email").getValue().toString();

                            if(type.equals("like")){
                                msg= name+" is Upvote on your forum post";
                            }else {
                                msg = name+" is DownVote on your forum post";
                            }
                            try {

                                OneSignalUtils.netWorkCall(cn,msg,email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Log.d("UserId", "Forum  ds false"+ UserId);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("UserId", "Forum  ds false"+ databaseError.getMessage());
                    }
                });
    }



    public static class ALL_Forum_VH extends RecyclerView.ViewHolder {
        TextView af_rec_post, af_rec_time, af_rec_name, af_rec_like, af_rec_dislike, af_rec_rply;
        ImageView af_rec_img, af_rec_popup;

        LinearLayout af_rec_like_lin, af_rec_dislike_lin, af_rec_rply_lin;
        View mView;

        public ALL_Forum_VH(View itemView) {
            super(itemView);
            mView = itemView;

            af_rec_dislike = itemView.findViewById(R.id.af_rec_dislike);
            af_rec_post = itemView.findViewById(R.id.af_rec_post);
            af_rec_time = itemView.findViewById(R.id.af_rec_time);
            af_rec_name = itemView.findViewById(R.id.af_rec_name);
            af_rec_like = itemView.findViewById(R.id.af_rec_like);
            af_rec_rply = itemView.findViewById(R.id.af_rec_rply);

            af_rec_img = itemView.findViewById(R.id.af_rec_img);
            af_rec_popup = itemView.findViewById(R.id.af_rec_popup);

            af_rec_like_lin = itemView.findViewById(R.id.af_rec_like_lin);
            af_rec_dislike_lin = itemView.findViewById(R.id.af_rec_dislike_lin);
            af_rec_rply_lin = itemView.findViewById(R.id.af_rec_rply_lin);

        }


    }
}
