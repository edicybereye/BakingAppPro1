package android.edikurniawan.me.bakingapplication.controller.adapter;

import android.content.Context;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.databinding.ItemListIngredientBinding;
import android.edikurniawan.me.bakingapplication.model.Ingredient;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Edi Kurniawan on 9/25/2017.
 */

public class IngredientDetailRecyclerViewAdapter extends RecyclerView.Adapter<IngredientDetailRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Ingredient> mItems;

    public IngredientDetailRecyclerViewAdapter(Context mContext, List<Ingredient> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public IngredientDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ingredient, parent, false);
        return new IngredientDetailRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientDetailRecyclerViewAdapter.ViewHolder holder, int position) {
        Ingredient item = mItems.get(position);
        holder.binding.tvIngredient.setText(mContext.getResources().getText(R.string.ingredient_)+item.getIngredient());
        holder.binding.tvMeasure.setText(mContext.getResources().getText(R.string.measure)+item.getMeasure());
        holder.binding.tvQuantity.setText(mContext.getResources().getText(R.string.quantity)+String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemListIngredientBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemListIngredientBinding.bind(itemView);
        }
    }

}
