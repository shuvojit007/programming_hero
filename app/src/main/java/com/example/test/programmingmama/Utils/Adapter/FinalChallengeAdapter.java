package com.example.test.programmingmama.Utils.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.programmingmama.Model.Challenge3.Pchallenge3;
import com.example.test.programmingmama.Model.FinalChallenge.FinalChallenge;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.VectorDrawableUtils;
import com.example.test.programmingmama.View.Activity.Challenge3_Activity;
import com.example.test.programmingmama.View.Activity.Final_Challenge;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;


public class FinalChallengeAdapter extends RecyclerView.Adapter {
    private static Context cn;
    private List<FinalChallenge> pchallenges;
    public static FinalChallengeAdapter context;

    public FinalChallengeAdapter(Context cn, List<FinalChallenge> pchallenges) {
        this.pchallenges = pchallenges;
        this.context=this;
        this.cn = cn;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(cn).inflate(R.layout.final_challenge_quiz, parent, false);
                return new ViewHolder1(view);
            case 1:
                view = LayoutInflater.from(cn).inflate(R.layout.final_challenge_fillintheblanks, parent, false);
                return new ViewHolder2(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        FinalChallenge pc = pchallenges.get(position);
        switch (viewType) {
            case 0:
                ((ViewHolder1) holder).Update(pc);
                break;
            case 1:
                ((ViewHolder2) holder).Update(pc);
                break;
        
        }
    }

    @Override
    public int getItemCount() {
        return pchallenges.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (pchallenges.get(position).getType()) {
            case "quiz":
                return 0;
            case "printsln":
                return 1;
        }
        return 0;
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView question;
        RadioButton op1, op2, op3;
        RadioGroup radioGroup;

        public Button qrun;
        public CodeView codeView;
        String radioText="";

        public ViewHolder1(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            op1 = itemView.findViewById(R.id.radio_button1);
            op2 = itemView.findViewById(R.id.radio_button2);
            op3 = itemView.findViewById(R.id.radio_button3);

            radioGroup = itemView.findViewById(R.id.radio_group);
            codeView =itemView.findViewById(R.id.code_view);
            qrun = itemView.findViewById(R.id.qrun);
        }

        public void Update(FinalChallenge pc) {

            op1.setText(pc.getQuiz().getSet1());
            op2.setText(pc.getQuiz().getSet2());
            op3.setText(pc.getQuiz().getSet3());
            question.setText(Html.fromHtml(pc.getQuestion()));

            if(!pc.getQuiz().getCode().equals("null")){
                codeView.setVisibility(View.VISIBLE);
                codeView.setOptions(Options.Default.get(cn)
                        .withLanguage("python")
                        .withCode(pc.getQuiz().getCode())
                        .withShadows()
                        .withTheme(ColorTheme.MONOKAI));
            }
            qrun.setOnClickListener(v->{
                if(radioText.equals("")){
                    Toast.makeText(cn, "Please Select one option", Toast.LENGTH_SHORT).show();
                }else {
                    Final_Challenge.AnsCounter();

                    if (radioText.equals(pc.getQuiz().getSolution())){
                        Final_Challenge.CorrectAnswer();

                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            radioGroup.getChildAt(i).setEnabled(false);
                        }
                        qrun.setEnabled(false);
                        qrun.setAlpha(0.4f);
                    }else {
                        Final_Challenge.WrongAnswer();

                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            radioGroup.getChildAt(i).setEnabled(false);
                        }
                        qrun.setEnabled(false);
                        qrun.setAlpha(0.4f);
                    }
                }
            });

           radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
               RadioButton rb=itemView.findViewById(checkedId);
                radioText =rb.getText().toString();
            });



        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView question,ctt1,ctt2,ct1,ct2,coutput;
        public View mView;
        public EditText ced;
        public Button crun;


        public ViewHolder2(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.cquestion);
            ctt1 = itemView.findViewById(R.id.ctt1);
            ctt2 = itemView.findViewById(R.id.ctt2);
            ct1 = itemView.findViewById(R.id.ct1);
            ct2 = itemView.findViewById(R.id.ct2);
            coutput = itemView.findViewById(R.id.coutput);
            ced = itemView.findViewById(R.id.ced);
            crun = itemView.findViewById(R.id.crun);
            mView = itemView;
        }


        public void Update(FinalChallenge pc) {
            question.setText(Html.fromHtml(pc.getQuestion()));
            crun.setOnClickListener(v->{

                String Strans = ced.getText().toString();
                if(!Strans.equals("")){
                    Challenge3_Activity.AnsCounter();
                    if(Strans.equals(pc.getBlanks().getSoln())){
                        Challenge3_Activity.CorrectAnswer();

                        ced.setEnabled(false);
                        crun.setEnabled(false);
                        crun.setAlpha(0.4f);
                    }else {
                        Challenge3_Activity.WrongAnswer();
                        ced.setEnabled(false);
                        crun.setEnabled(false);
                        crun.setAlpha(0.4f);
                    }
                }else {
                    Toast.makeText(cn, "Please Add valid Input", Toast.LENGTH_SHORT).show();
                }
            });

            ced.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.d("TEXTCHANGER", "beforeTextChanged: "+s.toString());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d("TEXTCHANGER", "onTextChanged: "+s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("TEXTCHANGER", "afterTextChanged: "+s.toString());
                }
            });


            if(!pc.getBlanks().getTf1().equals("null")){

                ctt1.setVisibility(View.VISIBLE);
                ctt1.setText(Html.fromHtml(pc.getBlanks().getTf1()));
            }else {
                ctt1.setVisibility(View.GONE);
            }

            if(!pc.getBlanks().getTf2().equals("null")){

                ctt2.setVisibility(View.VISIBLE);
                ctt2.setText(Html.fromHtml(pc.getBlanks().getTf2()));
            }else {
                ctt2.setVisibility(View.GONE);
            }

            if(!pc.getBlanks().getTt1().equals("null")){
                ct1.setVisibility(View.VISIBLE);
                ct1.setText(pc.getBlanks().getTt1());
            }else {
                ct1.setVisibility(View.GONE);
            }

            if(!pc.getBlanks().getTt2().equals("null")){
                ct2.setVisibility(View.VISIBLE);
                ct2.setText(pc.getBlanks().getTt2());
            }else {
                ct2.setVisibility(View.GONE);
            }
        }
    }

}
