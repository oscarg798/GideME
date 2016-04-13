package com.gideme.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gideme.R;
import com.gideme.controllers.PlacesByCategoryController;
import com.gideme.entities.dto.LocationDTO;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.entities.model.Place;
import com.gideme.entities.utils.CoupleParams;
import com.gideme.presentation.adapters.PlaceAdapter;
import com.gideme.presentation.listeners.RecyclerItemOnClickListener;
import com.gideme.presentation.listeners.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PlacesByCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private PlacesByCategoryController placesByCategoryController;
    private LocationDTO locationDTO = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placesByCategoryController = new PlacesByCategoryController(this);
        initViewComponents();
    }

    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final String category = getIntent().getExtras().getString(getApplicationContext()
                .getString(R.string.category_key));

        locationDTO = (LocationDTO) getIntent().getExtras().getSerializable(getString(R.string.location_key));

        placesByCategoryController
                .getPlacesByCategory(category, "4000", locationDTO);


    }


    public void showPlacesByCategory(final List<PlaceDTO> placeDTOList) {

        LinearLayoutManager mLinearLayoutManager;
        mLinearLayoutManager = new LinearLayoutManager(this);

        Place.getInstance().orderPlaceListByDistanceToUserLocation(placeDTOList,
                locationDTO);


        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        List<CoupleParams> coupleParamsList =
                                new ArrayList<>();
                        coupleParamsList.add(new CoupleParams.CoupleParamBuilder(getApplicationContext()
                                .getString(R.string.place_key)).nestedObject(placeDTOList.get(position))
                                .createCoupleParam());
                        placesByCategoryController.changeActivity(PlaceDetailActivity.class,
                                coupleParamsList);
                    }
                }));

        PlaceAdapter placeAdapter = new PlaceAdapter(placeDTOList, getApplicationContext());
        recyclerView.setAdapter(placeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long|
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
