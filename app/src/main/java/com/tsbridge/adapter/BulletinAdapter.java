package com.tsbridge.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsbridge.R;
import com.tsbridge.fragment.SendFragment;
import com.tsbridge.utils.Utils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BulletinAdapter extends RecyclerView.Adapter<BulletinAdapter.BulletinViewHolder> {
    private Context mContext;
    private List<Map<String, String>> mBulletins;

    public BulletinAdapter(Context mContext, List<Map<String, String>> mBulletins) {
        this.mContext = mContext;
        this.mBulletins = mBulletins;
    }

    @Override
    public BulletinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.bulletin_item, parent, false);

        BulletinViewHolder viewHolder = new BulletinViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BulletinViewHolder holder, int position) {
//        调试用，直接显示应用图标，还得自定义一个圆形图标的View类
        holder.mBulletinImage.setImageResource(R.drawable.bulletin_image);
//        new QueryImageTask(holder.mBulletinImage).execute(mBulletins.get(position)
//            .get(Utils.BULLETIN_IMAGE_KEY).trim());
        holder.mBulletinName.setText(mBulletins.get(position).get(Utils.BULLETIN_NAME_KEY).trim());
        holder.mBulletinTime.setText(mBulletins.get(position).get(Utils.BULLETIN_TIME_KEY).trim());
        holder.mBulletinContent.setText(mBulletins.get(position).get(Utils.BULLETIN_CONTENT_KEY).trim());
//        check if this is the last child, if yes then hide the divider, remember -1
        if(position == getItemCount()-1)
            holder.mBulletinDivider.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mBulletins == null ? 0 : mBulletins.size();
    }

    protected class BulletinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView mBulletinImage = null;
        protected TextView mBulletinName = null;
        protected TextView mBulletinTime = null;
        protected TextView mBulletinContent = null;
        protected View mBulletinDivider = null;
        protected View mContentImage = null;

        public BulletinViewHolder(View itemView) {
            super(itemView);

            mBulletinImage = (ImageView) itemView.findViewById(R.id.bulletin_image);
            mBulletinName = (TextView) itemView.findViewById(R.id.bulletin_name);
            mBulletinTime = (TextView) itemView.findViewById(R.id.bulletin_time);
            mBulletinContent = (TextView) itemView.findViewById(R.id.bulletin_content);
            mBulletinDivider = (View) itemView.findViewById(R.id.bulletin_divider);
            mContentImage = (ImageView) itemView.findViewById(R.id.bulletin_content_image);
            mContentImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.bulletin_content_image:
                    ShowFullImage();
                    break;
                default:
                    break;
            }
        }

        private void ShowFullImage() {
            Dialog dialog = new Dialog(mContext, R.style.DialogTitle);
            dialog.setContentView(R.layout.bulletin_image);
            dialog.setCanceledOnTouchOutside(true);
            ImageView imageView = (ImageView) dialog.findViewById(R.id.bulletin_image_full);
            imageView.setImageBitmap(BitmapFactory.decodeFile(SendFragment.picturePath));

            dialog.show();
        }
    }

    private class QueryImageTask extends AsyncTask<String, Void, File> {
        private ImageView mBulletinImage = null;

        public QueryImageTask(ImageView bulletinImage) {
            mBulletinImage = bulletinImage;
        }

        @Override
        protected File doInBackground(String... params) {
            String imageUrl = params[0];
            int imageSize = mContext.getResources().getDimensionPixelSize(R.dimen.bulletin_image);
            try {
                return Glide.with(mContext)
                        .load(imageUrl)
                        .downloadOnly(imageSize, imageSize)
                        .get();
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                Glide.with(mContext).load(R.drawable.bulletin_image).into(mBulletinImage);
                return;
            }
            Utils.ShowLog(result.getAbsolutePath());
            Glide.with(mContext).load(result).into(mBulletinImage);
        }
    }
}
