package com.example.test.programmingmama.Utils.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.programmingmama.Model.pc1Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.VectorDrawableUtils;
import com.example.test.programmingmama.View.Activity.ProgrammingChallenge;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class ProgrammingChallengeAdapter extends RecyclerView.Adapter<ProgrammingChallengeAdapter.ViewHolder> {
    Context cn;
    private static final String TAG = "Programming";
    List<pc1Model> lm;
    private LayoutInflater mLayoutInflater;

    public ProgrammingChallengeAdapter(Context cn, List<pc1Model> lm) {
        this.cn = cn;
        this.lm = lm;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(cn);
        View view;
        view = mLayoutInflater.inflate(R.layout.pchlng, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        pc1Model qm = lm.get(position);
        holder.op1.setText(qm.getOp1());
        holder.op2.setText(qm.getOp2());
        holder.op3.setText(qm.getOp3());

        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
       holder.timelineView.setMarker(VectorDrawableUtils.getDrawable(cn, R.drawable.ic_marker_active, R.color.colorPrimary));
        if(lm.size()==position+1){
            ProgrammingChallenge.setStatus();
        }


            switch (checkedId){
                case R.id.radio_button1:

                        if(qm.getAns()==1){
                            ProgrammingChallenge.CorrectAnswer();
                        }else {
                            ProgrammingChallenge.WrongAnswer();
                        }

                    break;
                case R.id.radio_button2:
                    if(qm.getAns()==2){
                        ProgrammingChallenge.CorrectAnswer();
                    }else {
                        ProgrammingChallenge.WrongAnswer();

                    }
                    break;
                case R.id.radio_button3:
                    if(qm.getAns()==3){
                        ProgrammingChallenge.CorrectAnswer();
                    }else {
                        ProgrammingChallenge.WrongAnswer();
                    }
                    break;
            }
        });


        holder.timelineView.setMarker(VectorDrawableUtils.getDrawable(cn, R.drawable.ic_marker_inactive, android.R.color.darker_gray));

        holder.question.setText(qm.getQuestion());

    }

    @Override
    public int getItemCount() {
        return lm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        RadioButton op1,op2,op3;
        RadioGroup radioGroup;
        TimelineView timelineView;
        public ViewHolder(View view) {
            super(view);
            question =itemView.findViewById(R.id.text_view_question);
            op1 =itemView.findViewById(R.id.radio_button1);
            op2 =itemView.findViewById(R.id.radio_button2);
            op3 =itemView.findViewById(R.id.radio_button3);
                timelineView =itemView.findViewById(R.id.time_marker);
            radioGroup =itemView.findViewById(R.id.radio_group);
        }
    }
}
