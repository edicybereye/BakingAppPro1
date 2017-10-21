package android.edikurniawan.me.bakingapplication.controller.adapter;

import android.content.Context;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.databinding.ItemListDescriptionBinding;
import android.edikurniawan.me.bakingapplication.databinding.ItemListTitleBinding;
import android.edikurniawan.me.bakingapplication.model.RecipeAbstract;
import android.edikurniawan.me.bakingapplication.model.Step;
import android.edikurniawan.me.bakingapplication.utility.Constant;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Edi Kurniawan on 9/25/2017.
 */

public class RecipeDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<RecipeAbstract> mItems;
    OnRecyclerViewInteraction mListener;

    public RecipeDetailRecyclerViewAdapter(Context mContext, List<RecipeAbstract> mItems, OnRecyclerViewInteraction listener) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.getInstance().TYPE_INGREDIENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_description, parent, false);
            return new IngredientsViewHolder(view);
        } else if (viewType == Constant.getInstance().TYPE_STEPS){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_description, parent, false);
            return new StepViewHolder(view);
        } else if (viewType == Constant.getInstance().TYPE_TITLE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_title, parent, false);
            return new TitleViewHolder(view);
        }
        return null;    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IngredientsViewHolder) {
            onIngredientBindViewHolder((IngredientsViewHolder) holder, position);
        } else if (holder instanceof StepViewHolder) {
            onStepBindViewHolder((StepViewHolder) holder, position);
        } else if (holder instanceof TitleViewHolder) {
            onTitleBindViewHolder((TitleViewHolder) holder, position);
        }
    }

    public void onIngredientBindViewHolder(final IngredientsViewHolder holder, final int position) {
        final RecipeAbstract item = mItems.get(position);
        holder.binding.textViewTitle.setText(R.string.list_of_ingredients);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnListIngredientInteraction(item);
            }
        });
    }


    public void onTitleBindViewHolder(final TitleViewHolder holder, final int position) {
        final RecipeAbstract item = mItems.get(position);

        holder.binding.textViewTitle.setText(item.getTitle());

    }

    public void onStepBindViewHolder(final StepViewHolder holder, final int position) {
        final RecipeAbstract item = mItems.get(position);

        holder.binding.textViewTitle.setGravity(Gravity.LEFT);
        holder.binding.textViewTitle.setText(item.getStep().getShortDescription());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnListStepInteraction(item.getStep());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position)!=null) {
            if (mItems.get(position).getIngredients() != null) {
                return Constant.getInstance().TYPE_INGREDIENT;
            } else if (mItems.get(position).getStep() != null) {
                return Constant.getInstance().TYPE_STEPS;
            } else if (mItems.get(position).getTitle() != null) {
                return Constant.getInstance().TYPE_TITLE;
            }
        }
        return super.getItemViewType(position);
    }


    private class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private final ItemListDescriptionBinding binding;
        public IngredientsViewHolder(View itemView) {
            super(itemView);
            binding = ItemListDescriptionBinding.bind(itemView);
        }
    }

    private class StepViewHolder extends RecyclerView.ViewHolder{
        private final ItemListDescriptionBinding binding;

        public StepViewHolder(View itemView) {
            super(itemView);
            binding = ItemListDescriptionBinding.bind(itemView);
        }
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder{
        private final ItemListTitleBinding binding;

        public TitleViewHolder(View itemView) {
            super(itemView);
            binding = ItemListTitleBinding.bind(itemView);
        }
    }

    public interface OnRecyclerViewInteraction {
        // TODO: Update argument type and name
        void OnListIngredientInteraction(RecipeAbstract ingredients);
        void OnListStepInteraction(Step step);

    }

}
