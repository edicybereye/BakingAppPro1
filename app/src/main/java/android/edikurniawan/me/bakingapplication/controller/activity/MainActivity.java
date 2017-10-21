package android.edikurniawan.me.bakingapplication.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.controller.adapter.RecipeRecylerViewAdapter;
import android.edikurniawan.me.bakingapplication.databinding.ActivityMainBinding;
import android.edikurniawan.me.bakingapplication.handler.ApiClient;
import android.edikurniawan.me.bakingapplication.model.Recipe;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends BaseActivity
                                    implements RecipeRecylerViewAdapter.OnRecyclerViewInteraction{
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecyclerView(binding.contentMain.recyclerView);
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {

        if (isNetworkAvailable()){
            ApiClient.getInstance().getListRecipe()
                    .getAsObjectList(Recipe.class, new ParsedRequestListener<List<Recipe>>() {
                        @Override
                        public void onResponse(List<Recipe> response) {
                            recyclerView.setAdapter(new RecipeRecylerViewAdapter(MainActivity.this,response,MainActivity.this));
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(MainActivity.this,getString(R.string.error).concat(anError.getErrorDetail()),Toast.LENGTH_LONG).show();
                        }
                    });
        }else{
            Snackbar.make(binding.coordinatorLayout,getString(R.string.internetnotavailable),Snackbar.LENGTH_LONG).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnListItemInteraction(Recipe recipe) {
        Timber.e(new Gson().toJson(recipe));
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.EXTRA1, recipe);

        startActivity(intent);
    }
}
