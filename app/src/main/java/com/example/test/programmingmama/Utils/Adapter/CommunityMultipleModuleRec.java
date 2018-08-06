package com.example.test.programmingmama.Utils.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.programmingmama.Model.NewModel.List;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.View.ViewPager.CommunityMultipleModuleListViewPager;

public class CommunityMultipleModuleRec extends RecyclerView.Adapter {
    Context cn;
    java.util.List<List> lm;
    int id;


    public CommunityMultipleModuleRec(Context cn, java.util.List<List> lm, int id) {
        this.cn = cn;
        this.lm = lm;
        this.id = id;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(cn).inflate(R.layout.multiple_module_lock_layout, parent, false);
                return new ViewHolder1(view);
            case 1:
                view = LayoutInflater.from(cn).inflate(R.layout.multiple_module_unlock_layout, parent, false);
                return new ViewHolder2(view);
            case 2:
                view = LayoutInflater.from(cn).inflate(R.layout.multiple_module_layout, parent, false);
                return new ViewHolder3(view);
            case 3:
                view = LayoutInflater.from(cn).inflate(R.layout.multiple_module_lock_premium, parent, false);
                return new ViewHolder4(view);
        }
        return null;

    }

//    @Override
//    public CommunityMultipleModuleRec.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(cn);
//        View itemView = inflater.inflate(R.layout.multiple_module_layout, parent, false);
//        ViewHolder viewHolder = new ViewHolder(itemView);
//        return viewHolder;
//    }

//    @Override
//    public void onBindViewHolder( CommunityMultipleModuleRec.ViewHolder holder, int position) {
//        List data =lm.get(position);
//        holder.title.setText(data.getMtitle());
//
//
//        holder.itemView.setOnClickListener(v -> {
//
//            int count = (int) data.getMcount();
//            if(count!=0){
//                cn.startActivity(new Intent(cn,CommunityMultipleModuleListViewPager.class)
//                        .putExtra("count",count)
//                        .putExtra("id",id)
//                        .putExtra("parcel",  data));
//            }
//        });
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        List data = lm.get(position);

        switch (viewType) {
            case 0:
                ((CommunityMultipleModuleRec.ViewHolder1) holder).Update(data);
                break;
            case 1:
                ((CommunityMultipleModuleRec.ViewHolder2) holder).Update(data);
                break;
            case 2:
                ((CommunityMultipleModuleRec.ViewHolder3) holder).Update(data);
                break;
            case 3:
                ((CommunityMultipleModuleRec.ViewHolder4) holder).Update(data);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return lm.size();
    }


    @Override
    public int getItemViewType(int position) {
        switch (lm.get(position).getStatus()) {
            case "lock":
                return 0;
            case "open":
                return 1;
            case "completed":
                return 2;
            case "premium":
                return 3;

        }
        return 0;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public TextView title;
        public View mView;

        public ViewHolder1(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.modulelist_name);
            mView = itemView;
        }

        public void Update(List data) {
            title.setText(data.getMtitle());
            itemView.setOnClickListener(vv -> Toast
                    .makeText(cn, "Please Complete the previous module first", Toast.LENGTH_LONG)
                    .show());
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView title;
        public View mView;

        public ViewHolder2(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.modulelist_name);
            mView = itemView;
        }

        public void Update(List data) {
            title.setText(data.getMtitle());
            itemView.setOnClickListener(v -> {

                int count = (int) data.getMcount();
                if (count != 0) {
                    cn.startActivity(new Intent(cn, CommunityMultipleModuleListViewPager.class)
                            .putExtra("count", count)
                            .putExtra("id", id)
                            .putExtra("parcel", data));
                }
            });
        }

    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {
        public TextView title;
        public View mView;
        public RatingBar module_rating;

        public ViewHolder3(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.modulelist_name);
            module_rating = itemView.findViewById(R.id.modulelist_rating);
            mView = itemView;
        }

        public void Update(List data) {
            float result = ((float) data.getResult() / data.getTotal()) * 5;
            module_rating.setRating((int) Math.round(result));
            title.setText(data.getMtitle());
            itemView.setOnClickListener(v -> {

                int count = (int) data.getMcount();
                if (count != 0) {
                    cn.startActivity(new Intent(cn, CommunityMultipleModuleListViewPager.class)
                            .putExtra("count", count)
                            .putExtra("id", id)
                            .putExtra("parcel", data));
                }
            });
        }
    }

    public class ViewHolder4 extends RecyclerView.ViewHolder {
        public TextView title;
        public View mView;
        public CardView card;

        public ViewHolder4(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.modulelist_name);
            card = itemView.findViewById(R.id.card);
            mView = itemView;
        }

        public void Update(List data) {
            title.setText(data.getMtitle());
            card.setOnClickListener(vv -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                final View dialogView = LayoutInflater.from(cn).inflate(R.layout.premium_module_popup, null);
                Button coffee = dialogView.findViewById(R.id.coffee);
                Button rfr = dialogView.findViewById(R.id.rfr);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();

                coffee.setOnClickListener(vvv->{
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(cn);
                    final View dialogView2 = LayoutInflater.from(cn).inflate(R.layout.pickupcoffee, null);
                    builder2.setView(dialogView2);
                    final AlertDialog dialog2 = builder2.create();
                    dialog2.setOnShowListener(dialogInterface -> {
                        View view = dialog2.getWindow().getDecorView();
                        ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0.0f).start();
                    });
                    Window window = dialog2.getWindow();
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    window.setBackgroundDrawableResource(android.R.color.transparent);
                    dialog2.show();
                    dialog.dismiss();
                    dialog2.setOnDismissListener(dialog1 -> {
                        dialog.show();
                    });
                });

                rfr.setOnClickListener(v -> {
                    String body = cn.getResources().getString(R.string.email_body_share_text) +
                            "\n"
                            + "https://play.google.com/store/apps/details?id=" + cn.getResources().getString(R.string.app_package_name);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Install Master Planner Pro App");
                    intent.putExtra(Intent.EXTRA_TEXT, body);
                    cn.startActivity(Intent.createChooser(intent, "Share Master Planner Pro using"));
                });
                dialog.show();
            });
        }
       /*
        public void ByUSaCoffee(){
            AlertDialog.Builder builder2 = new AlertDialog.Builder(cn);
            final View dialogView2 = LayoutInflater.from(cn).inflate(R.layout.pickupcoffee, null);
            builder2.setView(dialogView2);
            final AlertDialog dialog2 = builder2.create();
            dialog2.setOnShowListener(dialogInterface -> {
                View view = dialog2.getWindow().getDecorView();
                ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0.0f).start();
            });
            Window window = dialog2.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            dialog2.show();
            dialog.dismiss();
            dialog2.setOnDismissListener(dialog1 -> {
                dialog.show();
            });
        }*/
    }

}
