package android.edikurniawan.me.bakingapplication.controller.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.controller.fragment.IngredientDetailFragment;
import android.edikurniawan.me.bakingapplication.controller.fragment.StepDetailFragment;
import android.edikurniawan.me.bakingapplication.databinding.ActivityItemDetailBinding;
import android.edikurniawan.me.bakingapplication.model.Recipe;
import android.edikurniawan.me.bakingapplication.model.RecipeAbstract;
import android.edikurniawan.me.bakingapplication.model.Step;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import timber.log.Timber;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {
    public static final String EXTRA1 = "EXTRA1";
    public static final String EXTRA2 = "EXTRA2";
    public static final String EXTRA3 = "EXTRA3";

    ActivityItemDetailBinding binding;

    Recipe recipe;
    Step step;
    RecipeAbstract recipeAbstract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_detail);

        setSupportActionBar(binding.detailToolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            Timber.e("SavedInstanceState == null");
            // Create the detail fragment and add it to the activity
            if (getIntent().getParcelableExtra(EXTRA1) instanceof Step) {
                step = (Step)getIntent().getParcelableExtra(EXTRA1);
                StepDetailFragment fragment = StepDetailFragment.newInstance(step);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else if (getIntent().getParcelableExtra(EXTRA1) instanceof RecipeAbstract) {
                recipeAbstract = (RecipeAbstract)getIntent().getParcelableExtra(EXTRA1);
                IngredientDetailFragment fragment = IngredientDetailFragment.newInstance(recipeAbstract);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            }


            recipe = getIntent().getParcelableExtra(EXTRA2);


        }else{
            Timber.e("ONSAVEDINSTANCE != NULL");
            step = savedInstanceState.getParcelable(ItemDetailActivity.EXTRA1);
            recipe = savedInstanceState.getParcelable(ItemDetailActivity.EXTRA2);
            recipeAbstract = savedInstanceState.getParcelable(ItemDetailActivity.EXTRA3);

        }

        initButton();

    }

    private void initButton(){

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeAbstract = null;
                if (step != null) {
                    Timber.e("STEP 1");
                    int i = 0;
                    Timber.e("STEP : "+new Gson().toJson(step));
                    for (Step item : recipe.getSteps()){
                        Timber.e("i : "+i+" | "+ new Gson().toJson(item));
                        if (item.getId() == step.getId()){
                            Timber.e("GET : "+new Gson().toJson(item));
                            if (i < recipe.getSteps().size()-1){

                                step = recipe.getSteps().get(i+1);
                                StepDetailFragment fragment = StepDetailFragment.newInstance(step);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.item_detail_container, fragment)
                                        .commit();
                            }else{
                                Snackbar.make(binding.coordinatorLayout,"You're in the last page",Snackbar.LENGTH_LONG).show();
                            }

                            break;
                        }
                        i++;
                    }

                } else{
                    Timber.e("STEP 2");
                    step = recipe.getSteps().get(0);
                    StepDetailFragment fragment = StepDetailFragment.newInstance(step);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                }
            }
        });


        binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step != null) {
                    int i = 0;
                    for (Step item : recipe.getSteps()){
                        if (item.getId() == step.getId()){
                            if (i > 0){

                                step = recipe.getSteps().get(i-1);
                                StepDetailFragment fragment = StepDetailFragment.newInstance(step);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.item_detail_container, fragment)
                                        .commit();
                            }else{
                                step = null;
                                recipeAbstract = new RecipeAbstract(recipe.getIngredients());
                                IngredientDetailFragment fragment = IngredientDetailFragment.newInstance(recipeAbstract);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.item_detail_container, fragment)
                                        .commit();                            }

                            break;
                        }
                        i++;
                    }

                } else{
                    if (recipeAbstract == null){
                        recipeAbstract = new RecipeAbstract(recipe.getIngredients());
                        IngredientDetailFragment fragment = IngredientDetailFragment.newInstance(recipeAbstract);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    }else{
                        Snackbar.make(binding.coordinatorLayout,"You're in the first page",Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ItemDetailActivity.EXTRA1, step);
        outState.putParcelable(ItemDetailActivity.EXTRA2, recipe);
        outState.putParcelable(ItemDetailActivity.EXTRA3, recipeAbstract);
        Timber.e("ONSAVEINSTANCE");
    }


}
