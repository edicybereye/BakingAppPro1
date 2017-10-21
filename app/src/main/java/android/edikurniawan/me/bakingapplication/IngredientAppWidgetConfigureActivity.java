package android.edikurniawan.me.bakingapplication;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.edikurniawan.me.bakingapplication.controller.adapter.RecipeRecylerViewAdapter;
import android.edikurniawan.me.bakingapplication.handler.ApiClient;
import android.edikurniawan.me.bakingapplication.model.Ingredient;
import android.edikurniawan.me.bakingapplication.model.Recipe;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * The configuration screen for the {@link IngredientAppWidget IngredientAppWidget} AppWidget.
 */
public class IngredientAppWidgetConfigureActivity extends Activity implements RecipeRecylerViewAdapter.OnRecyclerViewInteraction{

    private static final String PREFS_NAME = "android.rezkyaulia.com.bakingapplication.IngredientAppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public IngredientAppWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.ingredient_app_widget_configure);
        setupRecyclerView((RecyclerView)findViewById(R.id.recyclerView));

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }


    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(mLayoutManager);

        ApiClient.getInstance().getListRecipe()
                .getAsObjectList(Recipe.class, new ParsedRequestListener<List<Recipe>>() {
                    @Override
                    public void onResponse(List<Recipe> response) {
                        Timber.e("SUCCESS : "+new Gson().toJson(response));
                        Timber.e("SIZE  : "+response.size());

                        recyclerView.setAdapter(new RecipeRecylerViewAdapter(IngredientAppWidgetConfigureActivity.this,response,IngredientAppWidgetConfigureActivity.this));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Timber.e("ERR : "+anError.getMessage());
                    }
                });
    }

    @Override
    public void OnListItemInteraction(Recipe recipe) {
        final Context context = IngredientAppWidgetConfigureActivity.this;

        // When the button is clicked, store the string locally
        String body = "";
        int i = 0;
        for (Ingredient ingredient : recipe.getIngredients()){
            i++;
            body = body.concat(recipe.getName().concat("\n\n"+String.valueOf(i)).
                    concat(context.getString(R.string.point)+" ").concat(ingredient.getIngredient())
                    .concat(context.getString(R.string.body2)).concat(" "+String.valueOf(ingredient.getQuantity()))
                    .concat(context.getString(R.string.body3)).concat(ingredient.getMeasure()).concat(context.getString(R.string.body4)));
        }


        saveTitlePref(context, mAppWidgetId, body);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        IngredientAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}

