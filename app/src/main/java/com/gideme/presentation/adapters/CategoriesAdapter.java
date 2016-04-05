package com.gideme.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gideme.R;
import com.gideme.entities.dto.CategoryDTO;
import com.gideme.presentation.view_holders.CategoryViewHolder;
import com.gideme.presentation.view_holders.PlaceListViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ogallonr on 05/04/2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private List<CategoryDTO> categoryDTOList;
    private Context context;

    public CategoriesAdapter(List<CategoryDTO> categoryDTOList, Context context) {
        this.categoryDTOList = categoryDTOList;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorie_item, parent, false);
        return new CategoryViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryDTO categoryDTO = this.categoryDTOList.get(position);
        holder.getTxtCategoryName().setText(categoryDTO.getName());
        Picasso.with(this.context).load(categoryDTO.getCategoryIconURL())
                .into(holder.getIvCategoryIcon());
    }

    @Override
    public int getItemCount() {
        return this.categoryDTOList.size();
    }
}
