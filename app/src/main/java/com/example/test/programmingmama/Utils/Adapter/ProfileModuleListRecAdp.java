package com.example.test.programmingmama.Utils.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.PrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class ProfileModuleListRecAdp extends RecyclerView.Adapter<ProfileModuleListRecAdp.ViewHolder> {
    private static Context cn;
    private List<Home> home;



    public ProfileModuleListRecAdp(Context cn, List<Home> home) {
        this.cn = cn;
        this.home = home;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new ViewHolder( LayoutInflater
                 .from(cn)
                 .inflate(R.layout.profile_module_rec,
                         parent,
                         false)
      );

    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Home hm =home.get(position);
        if(hm.getStatus().equals("completed")){
            holder.pro_module_name.setText(hm.getTitle());
            float result =((float) hm.getResult()/hm.getTotal())*5;
            holder.pro_module_rating.setRating( (int)Math.round(result));
            Glide.with(cn).load(hm
                    .getSectionImage())
                    .apply(fitCenterTransform())
                    .into(holder.pro_module_img);

            holder.pro_module_point.setText("Point: "+hm.getResult()+"/"+hm.getTotal());

        }else {
            holder.pro_module_rating.setVisibility(View.GONE);
            holder.pro_module_point.setVisibility(View.GONE);
            holder.pro_module_name.setText(hm.getTitle());

            Glide.with(cn).load(hm
                    .getSectionImage())
                    .apply(fitCenterTransform())
                    .into(holder.pro_module_img);

        }
    }

    @Override
    public int getItemCount() {
        return home.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pro_module_img;
        TextView pro_module_name;
        TextView pro_module_point;
        RatingBar pro_module_rating;
        public ViewHolder(View itemView) {
            super(itemView);
            pro_module_img =itemView.findViewById(R.id.pro_module_img);
            pro_module_name = itemView.findViewById(R.id.pro_module_name);
            pro_module_point = itemView.findViewById(R.id.pro_module_point);
            pro_module_rating = itemView.findViewById(R.id.pro_module_rating);
        }
    }
}
