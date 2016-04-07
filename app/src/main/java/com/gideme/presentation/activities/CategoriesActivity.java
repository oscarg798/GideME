package com.gideme.presentation.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gideme.R;
import com.gideme.controllers.abstracts.CategoryController;
import com.gideme.entities.dto.CategoryDTO;
import com.gideme.entities.utils.CoupleParams;
import com.gideme.presentation.adapters.CategoriesAdapter;
import com.gideme.presentation.listeners.RecyclerItemOnClickListener;
import com.gideme.presentation.listeners.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private List<CategoryDTO> categoriesList;
    private Toolbar toolbar;
    private CategoryController categoriesController;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categoriesController = new CategoryController(this);
        initViewComponents();
    }


    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getCategoriesArray();
        createCategoriesView();
    }


    public void getCategoriesArray() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.categories);
        categoriesList = new ArrayList<>();
        String[] auxStringArray = null;
        CategoryDTO categoryDTO = null;
        for (int i = 0; i < typedArray.length(); i++) {
            auxStringArray = getResources().getStringArray(typedArray.getResourceId(i, 0));
            categoryDTO = new CategoryDTO(auxStringArray[0], auxStringArray[1], auxStringArray[2]);
            categoriesList.add(categoryDTO);
        }
        typedArray.recycle();
        categoryDTO = null;
        auxStringArray = null;

    }

    public void createCategoriesView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoriesList, getApplicationContext());
        recyclerView.setAdapter(categoriesAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position){
                CategoryDTO categoryDTO = categoriesList.get(position);
                List<CoupleParams> coupleParamses = new ArrayList<CoupleParams>();
              coupleParamses.add(new CoupleParams.CoupleParamBuilder("category")
                      .nestedParam(categoryDTO.getCategoryKey()).createCoupleParam());
                categoriesController.changeActivityWithExtrasList(MainActivity.class, coupleParamses);
            }
        }));


    }

}
