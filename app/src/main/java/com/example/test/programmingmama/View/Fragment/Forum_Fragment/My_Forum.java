package com.example.test.programmingmama.View.Fragment.Forum_Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.All_Forum_Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.TimeUtils;
import com.example.test.programmingmama.View.Activity.Reply_Activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class My_Forum extends Fragment {
    View v;
    RecyclerView my_forum_rec;
    LinearLayout votecount;

    private ProgressDialog progressDialog;
    String Uid;
    DatabaseReference mForumRef, mUserRef, mUserLike, mUserDislike, mRootRef, mFrmRply;
    FirebaseRecyclerAdapter<All_Forum_Model, My_Forum_VH> fr;
    Context cn;

    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.my_forum, container, false);
        cn = this.getContext();
        text=v.findViewById(R.id.text);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            text.setVisibility(View.GONE);
            init();
        }else {
            text.setVisibility(View.VISIBLE);
        }

        return v;
    }

    private void init() {
        votecount = v.findViewById(R.id.votecount);

        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        progressDialog = new ProgressDialog(cn);
        my_forum_rec = v.findViewById(R.id.my_forum_rec);
        my_forum_rec.setHasFixedSize(true);
        my_forum_rec.setLayoutManager(new LinearLayoutManager(cn));
        my_forum_rec.setItemAnimator(new DefaultItemAnimator());
        my_forum_rec.addItemDecoration(new ItemDecoration(0, 1, 0, 1));
     /*
        my_forum_rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    votecount.setVisibility(View.GONE);
                } else {
                    votecount.setVisibility(View.VISIBLE);
                }
            }
        });*/


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

        SetUpFbRec();
    }

    private void SetUpFbRec() {
        progressDialog.setTitle("Fetching Data....");
        progressDialog.setMessage("Please wait while we upload and process your data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FirebaseRecyclerOptions<All_Forum_Model> options =
                new FirebaseRecyclerOptions.Builder<All_Forum_Model>()
                        .setQuery(mForumRef.orderByChild("userId").equalTo(Uid), All_Forum_Model.class)
                        .build();

        fr = new FirebaseRecyclerAdapter<All_Forum_Model, My_Forum_VH>(options) {
            @Override
            public My_Forum_VH onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.all_forum_rec_layout, parent, false);

                return new My_Forum_VH(view);
            }

            @Override
            protected void onBindViewHolder(My_Forum_VH holder, int position, All_Forum_Model model) {
                holder.af_rec_post.setText(model.getComment());
                holder.af_rec_time.setText(TimeUtils.getTimeDate(model.getTime()));
                holder.af_rec_dislike.setText("(" + model.getDislikes() + ")");
                holder.af_rec_like.setText("(" + model.getLikes() + ")");

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
                    startActivity(new Intent(cn, Reply_Activity.class)
                            .putExtra("frmId", model.getFrmId())
                            .putExtra("i", 2));

                });


                mRootRef.child(model.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            String image = dataSnapshot.child("badge").getValue().toString();
                            if (!image.equals("") || !image.equals(null) || !image.equals("null")) {
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
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        };

        my_forum_rec.setAdapter(fr);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            fr.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            fr.stopListening();
        }
    }


    private void UserCmntUpvote(All_Forum_Model model, My_Forum_VH holder) {
        mUserLike.child(model.getFrmId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    String liked = ds.child("liked").getValue().toString();
                    if (liked.equals("false")) {
                        holder.af_rec_like_lin.setOnClickListener(vv -> {
                            HashMap map = new HashMap();
                            map.put("likes", model.getLikes() + 1);
                            mForumRef.child(model.getFrmId()).updateChildren(map);
                            HashMap umap = new HashMap();
                            umap.put("liked", "true");
                            mUserLike.child(model.getFrmId()).setValue(umap);
                        });
                    } else {
                        holder.af_rec_like.setTextColor(R.color.like);
                        holder.af_rec_like_lin.setOnClickListener(vv -> {
                            HashMap map = new HashMap();
                            map.put("likes", model.getLikes() - 1);
                            mForumRef.child(model.getFrmId()).updateChildren(map);

                            HashMap umap = new HashMap();
                            umap.put("liked", "false");
                            mUserLike.child(model.getFrmId()).setValue(umap);
                        });
                    }
                } else {
                    holder.af_rec_like_lin.setOnClickListener(vv -> {
                        HashMap map = new HashMap();
                        map.put("likes", model.getLikes() + 1);
                        mForumRef.child(model.getFrmId()).updateChildren(map);
                        HashMap umap = new HashMap();
                        umap.put("liked", "true");
                        mUserLike.child(model.getFrmId()).setValue(umap);
                    });
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
                            HashMap map = new HashMap();
                            map.put("dislikes", model.getDislikes() + 1);
                            mForumRef.child(model.getFrmId()).updateChildren(map);
                            HashMap umap = new HashMap();
                            umap.put("disliked", "true");
                            mUserDislike.child(model.getFrmId()).setValue(umap);
                        });
                    } else {
                        holder.af_rec_dislike.setTextColor(R.color.like);
                        holder.af_rec_dislike_lin.setOnClickListener(vv -> {
                            HashMap map = new HashMap();
                            map.put("dislikes", model.getDislikes() - 1);
                            mForumRef.child(model.getFrmId()).updateChildren(map);

                            HashMap umap = new HashMap();
                            umap.put("disliked", "false");
                            mUserDislike.child(model.getFrmId()).setValue(umap);
                        });
                    }
                } else {
                    holder.af_rec_dislike_lin.setOnClickListener(vv -> {
                        HashMap map = new HashMap();
                        map.put("dislikes", model.getDislikes() + 1);
                        mForumRef.child(model.getFrmId()).updateChildren(map);
                        HashMap umap = new HashMap();
                        umap.put("disliked", "true");
                        mUserDislike.child(model.getFrmId()).setValue(umap);
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public static class My_Forum_VH extends RecyclerView.ViewHolder {
        TextView af_rec_post, af_rec_time, af_rec_name, af_rec_like, af_rec_dislike, af_rec_rply;
        ImageView af_rec_img, af_rec_popup;

        LinearLayout af_rec_like_lin, af_rec_dislike_lin, af_rec_rply_lin;
        View mView;

        public My_Forum_VH(View itemView) {
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
