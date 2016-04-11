package com.gideme.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gideme.R;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.presentation.view_holders.PlaceListViewHolder;
import com.gideme.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ogallonr on 05/04/2016.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceListViewHolder> {

    private List<PlaceDTO> placeDTOList;
    private Context context;

    public PlaceAdapter(List<PlaceDTO> placeDTOList, Context context) {
        this.placeDTOList = placeDTOList;
        this.context = context;
    }

    @Override
    public PlaceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_list_item, parent, false);
        return new PlaceListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceListViewHolder holder, int position) {
        PlaceDTO placeDTO = this.placeDTOList.get(position);
        holder.getTxtName().setText(placeDTO.getName());

        holder.getTxtDistanceToPlace().setText(String.format(context.getString(R.string.distance_label)
                        + context.getString(R.string.two_poins)
                        + context.getString(R.string.white_space)
                        + context.getString(R.string.double_formater_key)
                        + context.getString(R.string.kilometers_abreviation_label),
                placeDTO.getDistanceFromUserLocationToPlace()));

        if (placeDTO.getIconURL() != null) {
            Picasso.with(context).load(placeDTO.getIconURL())
                    .into(holder.getIvPlaceIcon());
        }
    }

    @Override
    public int getItemCount() {
        return this.placeDTOList.size();
    }
}
