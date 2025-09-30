package com.shuvo.ttit.bridgeculvert.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shuvo.ttit.bridgeculvert.R;
import com.shuvo.ttit.bridgeculvert.arraylist.PhotoList;
import com.shuvo.ttit.bridgeculvert.dialogue.PicDialogue;


import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PHOTOHOLDER> {

    private final ArrayList<PhotoList> mCategoryItem;
    private final Context myContext;


    public PhotoAdapter(ArrayList<PhotoList> mCategoryItem, Context myContext) {
        this.mCategoryItem = mCategoryItem;
        this.myContext = myContext;
    }

    public class PHOTOHOLDER extends RecyclerView.ViewHolder {


        public ImageView imageView;
        public TextView uploadDate;
        public TextView stage;
        public Button refresh;

        public PHOTOHOLDER(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_from);
            uploadDate = itemView.findViewById(R.id.upload_date_text);
            stage = itemView.findViewById(R.id.working_stage_text);
            refresh = itemView.findViewById(R.id.refresh_picture);

            imageView.setOnClickListener(view -> {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                String url = mCategoryItem.get(getAdapterPosition()).getPhotoName();
                Bitmap bitmap = mCategoryItem.get(getAdapterPosition()).getBitmap();
                PicDialogue picDialogue = new PicDialogue(bitmap, url);
                picDialogue.show(activity.getSupportFragmentManager(),"PICTURE");
            });

            refresh.setOnClickListener(view -> Glide.with(myContext)
                    .load(mCategoryItem.get(getAdapterPosition()).getPhotoName())
                    .error(R.drawable.loading_error)
                    .placeholder(R.drawable.loading)
                    .listener(new RequestListener<>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            refresh.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            refresh.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView));

        }
    }

    @NonNull
    @Override
    public PHOTOHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.photo_list_view, parent, false);
        return new PHOTOHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PHOTOHOLDER holder, int position) {
        PhotoList categoryItem = mCategoryItem.get(position);

        holder.uploadDate.setText(categoryItem.getUploadDate());
        holder.stage.setText(categoryItem.getStage());
        holder.refresh.setVisibility(View.GONE);
        Glide.with(myContext)
                .load(categoryItem.getPhotoName())
                .error(R.drawable.loading_error)
                .placeholder(R.drawable.loading)
                .listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (categoryItem.getBitmap() != null) {
                            Glide.with(myContext)
                                    .load(categoryItem.getBitmap())
                                    .error(R.drawable.loading_error)
                                    .placeholder(R.drawable.loading)
                                    .listener(new RequestListener<>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            holder.refresh.setVisibility(View.VISIBLE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            holder.refresh.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .into(holder.imageView);
                        } else {
                            // If bitmap is also null, let Glide show the error drawable
                            holder.imageView.setImageResource(R.drawable.loading_error);
                            holder.refresh.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.refresh.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mCategoryItem.size();
    }
}
