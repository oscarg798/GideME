package com.gideme.presentation.view_holders;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gideme.R;

/**
 * Created by ogallonr on 05/04/2016.
 */
public class PlaceListViewHolder extends RecyclerView.ViewHolder {
    private TextView txtName;
    private TextView txtType;
    private ImageView ivPlaceIcon;
    public PlaceListViewHolder(View itemView) {
        super(itemView);
        this.setTxtName((TextView) itemView.findViewById(R.id.txt_place_name));
        this.setTxtType((TextView) itemView.findViewById(R.id.txt_place_type));
        this.setIvPlaceIcon((ImageView) itemView.findViewById(R.id.iv_place_icon));

    }

    public TextView getTxtName() {
        return txtName;
    }

    private void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtType() {
        return txtType;
    }

    private void setTxtType(TextView txtType) {
        this.txtType = txtType;
    }

    public ImageView getIvPlaceIcon() {
        return ivPlaceIcon;
    }

    private void setIvPlaceIcon(ImageView ivPlaceIcon) {
        this.ivPlaceIcon = ivPlaceIcon;
    }
}
