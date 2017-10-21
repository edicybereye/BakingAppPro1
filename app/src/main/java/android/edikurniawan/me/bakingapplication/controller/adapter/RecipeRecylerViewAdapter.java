package android.edikurniawan.me.bakingapplication.controller.adapter;

import android.content.Context;
import android.edikurniawan.me.bakingapplication.R;
import android.edikurniawan.me.bakingapplication.databinding.ItemListRecipeBinding;
import android.edikurniawan.me.bakingapplication.model.Recipe;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Edi Kurniawan on 9/23/2017.
 */

public class RecipeRecylerViewAdapter extends RecyclerView.Adapter<RecipeRecylerViewAdapter.ViewHolder> {
    List<Recipe> mItems;
    Context mContext;
    OnRecyclerViewInteraction mListener;

    private int animationCount = 0;
    private int lastPosition = -1;

    public RecipeRecylerViewAdapter(Context mContext,List<Recipe> mItems, OnRecyclerViewInteraction listener) {
        this.mItems = mItems;
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recipe, parent, false);
        return new RecipeRecylerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe mItem = mItems.get(position);
        holder.binding.titleRecipe.setText(mItem.getName());

        if (mItem.getImage().isEmpty()){
            mItem.setImage(null);
            Picasso.with(mContext)
                    .load(mItem.getImage())
                    .placeholder(R.drawable.ic_image) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.ic_image)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.binding.imagePoster);
        }else{
            Picasso.with(mContext)
                    .load(mItem.getImage())
                    .placeholder(R.drawable.ic_image) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.ic_image)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.binding.imagePoster);
        }


        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnListItemInteraction(mItem);
            }
        });

        setAnimation(holder.binding.getRoot(), position);
        holder.binding.executePendingBindings();   // update the view now
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(final View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            final Animation animation = AnimationUtils.loadAnimation(
                    viewToAnimate.getContext(), android.R.anim.fade_in);
            animationCount++;
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animationCount--;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animation.setDuration(300);
            viewToAnimate.setVisibility(View.GONE);
            viewToAnimate.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewToAnimate.setVisibility(View.VISIBLE);
                    viewToAnimate.startAnimation(animation);
                }
            }, animationCount * 100);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.binding.getRoot().setVisibility(View.VISIBLE);
        holder.binding.getRoot().clearAnimation();
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemListRecipeBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemListRecipeBinding.bind(itemView);
        }
    }


    public interface OnRecyclerViewInteraction {
        // TODO: Update argument type and name
        void OnListItemInteraction(Recipe recipe);

    }
}
