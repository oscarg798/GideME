package com.gideme.presentation.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gideme.R;

/**
 * Created by ogallonr on 05/04/2016.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView txtCategoryName;
    private ImageView ivCategoryIcon;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        setTxtCategoryName((TextView) itemView.findViewById(R.id.txt_category_name));
        setIvCategoryIcon((ImageView) itemView.findViewById(R.id.iv_category_icon));
    }

    public TextView getTxtCategoryName() {
        return txtCategoryName;
    }

    private void setTxtCategoryName(TextView txtCategoryName) {
        this.txtCategoryName = txtCategoryName;
    }

    public ImageView getIvCategoryIcon() {
        return ivCategoryIcon;
    }

    private void setIvCategoryIcon(ImageView ivCategoryIcon) {
        this.ivCategoryIcon = ivCategoryIcon;
    }
}
