package com.example.test.programmingmama.Utils.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class SuperheroAdaptar extends RecyclerView.Adapter<SuperheroAdaptar.ViewHolder> {
    private  Context cn;
    private List<Achievement> achievements;
    LayoutInflater layoutInflater;

    public SuperheroAdaptar(Context cn, List<Achievement> achievements) {
        this.cn = cn;
        this.achievements = achievements;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater
                .from(cn)
                .inflate(R.layout.achievement_superhero_rec,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Achievement achievement =achievements.get(position);

            holder.superheroname.setText(achievement.getName());



            Glide.with(cn).load(achievement.getIcon())
                    .apply(fitCenterTransform())
                    .into(holder.superheroimg);
        if(achievement.getActive().equals("false")){

                holder.superheroimg.setAlpha(0.4f);

            holder.superheroimg.setOnClickListener(vv->{
                if(achievement.getActive().equals("false")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                    final View dialogView = LayoutInflater.from(cn).inflate(R.layout.indication,null);
                    TextView name =dialogView.findViewById(R.id.popuptitle);
                    TextView indication = dialogView.findViewById(R.id.popupmsg);
                    ImageView popupimg =dialogView.findViewById(R.id.profilebadge);
                    String[]indicate =achievement.getIndication().split("/");
                    name.setText(indicate[0]);
                    indication.setText(indicate[1]);

                    Glide.with(cn).load(achievement.getIcon())
                            .apply(fitCenterTransform())
                            .into(popupimg);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView superheroimg;
        TextView superheroname;

        public ViewHolder(View itemView) {
            super(itemView);
            superheroimg =itemView.findViewById(R.id.superheroimg);
            superheroname = itemView.findViewById(R.id.superheroname);

        }
    }
}
