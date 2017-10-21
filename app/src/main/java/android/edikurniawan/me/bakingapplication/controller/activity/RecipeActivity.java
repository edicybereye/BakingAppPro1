package android.edikurniawan.me.bakingapplication.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.controller.adapter.RecipeDetailRecyclerViewAdapter;
import android.edikurniawan.me.bakingapplication.controller.fragment.IngredientDetailFragment;
import android.edikurniawan.me.bakingapplication.controller.fragment.StepDetailFragment;
import android.edikurniawan.me.bakingapplication.databinding.ActivityRecipeBinding;
import android.edikurniawan.me.bakingapplication.model.Recipe;
import android.edikurniawan.me.bakingapplication.model.RecipeAbstract;
import android.edikurniawan.me.bakingapplication.model.Step;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Edi Kurniawan on 9/25/2017.
 */

public class RecipeActivity extends BaseActivity
                                    implements RecipeDetailRecyclerViewAdapter.OnRecyclerViewInteraction{
    public static final String EXTRA1 = "EXTRA1";

    private boolean mTwoPane;

    Recipe recipe;

    ActivityRecipeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(getTitle());
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Timber.e("ONCREATE");
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            recipe = (Recipe) getIntent().getParcelableExtra(RecipeActivity.EXTRA1);
        }else{
            recipe = (Recipe) savedInstanceState.getParcelable(RecipeActivity.EXTRA1);

        }


        setupRecyclerView(binding.contentRecipe.recyclerViewRecipe);
        if (binding.contentRecipe.itemDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeActivity.EXTRA1, recipe);
        Timber.e("ONSAVEINSTANCE");
    }



    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
        List<RecipeAbstract> mitems = new ArrayList<>();

        if (recipe.getIngredients().size()>0){
            mitems.add(new RecipeAbstract(getString(R.string.ingredient)));
            mitems.add(new RecipeAbstract(recipe.getIngredients()));
        }
        mitems.add(new RecipeAbstract(getString(R.string.steps)));
        for(Step item : recipe.getSteps()){
            mitems.add(new RecipeAbstract(item));
        }
        recyclerView.setAdapter(new RecipeDetailRecyclerViewAdapter(this,mitems,this));
    }

    @Override
    public void OnListIngredientInteraction(RecipeAbstract ingredients) {
        if (mTwoPane) {
            IngredientDetailFragment fragment = IngredientDetailFragment.newInstance(ingredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra(ItemDetailActivity.EXTRA1, ingredients);
            intent.putExtra(ItemDetailActivity.EXTRA2, recipe);
            startActivity(intent);
        }
    }

    @Override
    public void OnListStepInteraction(Step step) {
        if (mTwoPane) {
            StepDetailFragment fragment = StepDetailFragment.newInstance(step);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra(ItemDetailActivity.EXTRA1, step);
            intent.putExtra(ItemDetailActivity.EXTRA2, recipe);
            startActivity(intent);
        }
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

}
