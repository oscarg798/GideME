package com.gideme.presentation.view_holders;

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
    private TextView txtDistanceToPlace;
    private ImageView ivPlaceIcon;
    private ImageView ivGoToPlaceDetails;

    public PlaceListViewHolder(View itemView) {
        super(itemView);
        this.setTxtName((TextView) itemView.findViewById(R.id.txt_place_name));
        this.setTxtDistanceToPlace((TextView) itemView.findViewById(R.id.txt_distance_to_place));
        this.setIvPlaceIcon((ImageView) itemView.findViewById(R.id.iv_place_icon));
        this.setIvGoToPlaceDetails((ImageView) itemView.findViewById(R.id.iv_go_to_place_detail));

    }

    public TextView getTxtName() {
        return txtName;
    }

    private void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtDistanceToPlace() {
        return txtDistanceToPlace;
    }

    private void setTxtDistanceToPlace(TextView txtDistanceToPlace) {
        this.txtDistanceToPlace = txtDistanceToPlace;
    }

    public ImageView getIvPlaceIcon() {
        return ivPlaceIcon;
    }

    private void setIvPlaceIcon(ImageView ivPlaceIcon) {
        this.ivPlaceIcon = ivPlaceIcon;
    }

    public ImageView getIvGoToPlaceDetails() {
        return ivGoToPlaceDetails;
    }

    public void setIvGoToPlaceDetails(ImageView ivGoToPlaceDetails) {
        this.ivGoToPlaceDetails = ivGoToPlaceDetails;
    }
}
