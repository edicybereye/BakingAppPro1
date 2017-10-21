package android.edikurniawan.me.bakingapplication.controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.controller.adapter.IngredientDetailRecyclerViewAdapter;
import android.edikurniawan.me.bakingapplication.databinding.FragmentDetailIngredientBinding;
import android.edikurniawan.me.bakingapplication.model.RecipeAbstract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 * Created by Edi Kurniawan on 9/25/2017.
 */

public class IngredientDetailFragment extends BaseFragment {
    public static final String EXTRA1 = "EXTRA1";

    RecipeAbstract recipeAbstract;

    FragmentDetailIngredientBinding binding;

    public static IngredientDetailFragment newInstance(RecipeAbstract category) {
        IngredientDetailFragment fragment = new IngredientDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA1, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            recipeAbstract = getArguments().getParcelable(EXTRA1);
        }
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_detail_ingredient,container,false); // LayoutInflater.from(context).inflate(R.layout.content_progressbar,view,false);

        if (recipeAbstract != null){
            setupRecyclerView();
        }
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        Timber.e("TIDAK NULL");
        binding.recycleviewIngredient.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientDetailRecyclerViewAdapter adapter = new IngredientDetailRecyclerViewAdapter(getContext(),recipeAbstract.getIngredients());
        binding.recycleviewIngredient.setAdapter(adapter);

    }
}
