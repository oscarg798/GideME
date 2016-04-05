package com.gideme.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gideme.R;
import com.gideme.controllers.abstracts.PlacesByCategoryController;
import com.gideme.entities.dto.LocationDTO;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.presentation.adapters.PlaceAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  FloatingActionButton fab;
    private  Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewComponents();
    }

    public void initViewComponents(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        fab  = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationDTO locationDTO = new LocationDTO(6.181851, -75.591253);
                PlacesByCategoryController.getInstance(MainActivity.this)
                        .getPlacesByCategory("bank","1000",locationDTO);
            }
        });
    }

    public void showPlacesByCategory(List<PlaceDTO> placeDTOList){

        LinearLayoutManager mLinearLayoutManager;
        mLinearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(mLinearLayoutManager);
        PlaceAdapter placeAdapter = new PlaceAdapter(placeDTOList,getApplicationContext());
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
