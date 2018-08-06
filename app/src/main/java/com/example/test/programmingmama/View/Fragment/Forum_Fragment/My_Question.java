package com.example.test.programmingmama.View.Fragment.Forum_Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.Comment_Model;
import com.example.test.programmingmama.Model.My_Comment_Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.Utils.TimeUtils;
import com.example.test.programmingmama.View.Activity.Authenticaiton;
import com.example.test.programmingmama.View.Activity.Comments_List;
import com.example.test.programmingmama.View.Activity.Reply_Activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class My_Question extends Fragment {

    Toolbar toolbar;

    RecyclerView my_question_rec;
    EditText comment_add_et;

    int i;
    int id;
    int ListId;
    private ProgressDialog progressDialog;
    String Uid;
    DatabaseReference mUser, mUserRef, mComment, mRootRef, mcmntRply;
    TextView text;

    FirebaseRecyclerAdapter<My_Comment_Model, My_Comment> fr;

    static Context cn;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.my_question, container, false);
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


        progressDialog = new ProgressDialog(cn);

        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FireBaseInstanceSetup();


        my_question_rec = v.findViewById(R.id.my_question_rec);
        my_question_rec.setHasFixedSize(true);
        my_question_rec.setLayoutManager(new LinearLayoutManager(cn));
        my_question_rec.setItemAnimator(new DefaultItemAnimator());
        my_question_rec.addItemDecoration(new ItemDecoration(0, 1, 0, 1));

        SetUpFirebaseRec();

    }

    private void FireBaseInstanceSetup() {
        mUser = GetFirebaseRef.GetDbIns().getReference()
                .child("Users")
                .child(Uid)
                .child("Comments");

    }

    private void SetUpFirebaseRec() {

        FirebaseRecyclerOptions<My_Comment_Model> options =
                new FirebaseRecyclerOptions.Builder<My_Comment_Model>()
                        .setQuery(mUser, My_Comment_Model.class)
                        .build();


        fr = new FirebaseRecyclerAdapter<My_Comment_Model, My_Comment>(options) {
            @Override
            public My_Comment onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_cmnt_rec_layout, parent, false);

                return new My_Comment(view);
            }

            @Override
            protected void onBindViewHolder(My_Comment holder, int position, My_Comment_Model model) {
                holder.setUpTextView(model.getModulename());
                holder.SetUpOnclick(model);
                holder.SetUpImage(model);

            }
        };
        my_question_rec.setAdapter(fr);
    }


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


    public class My_Comment extends RecyclerView.ViewHolder {
        TextView my_module_name;
        ImageView my_module_img;
        CardView my_question_card;
        View mView;

        public My_Comment(View itemView) {
            super(itemView);
            mView = itemView;
            my_question_card = itemView.findViewById(R.id.my_question_card);
            my_module_name = itemView.findViewById(R.id.my_module_name);
            my_module_img = itemView.findViewById(R.id.my_module_img);

        }

        public void setUpTextView(String modulename) {
            my_module_name.setText(modulename);
        }

        public void SetUpOnclick(My_Comment_Model modle) {
            my_question_card.setOnClickListener(v -> {
                if (modle.getListid() == 0) {

                    startActivity(new Intent(cn, Comments_List.class)
                            .putExtra("i", 0)
                            .putExtra("id", modle.getModuleid())
                            .putExtra("name", modle.getModulename()));
                } else {
                    startActivity(new Intent(cn, Comments_List.class)
                            .putExtra("i", 1)
                            .putExtra("id", modle.getModuleid())
                            .putExtra("ListId", modle.getListid())
                            .putExtra("name", modle.getModulename()));
                }
            });
        }

        public void SetUpImage(My_Comment_Model modle) {
            String img = RealmService.FindSectionImage(modle.getModuleid());
            Glide.with(cn).load(img).into(my_module_img);

        }

    }
}
