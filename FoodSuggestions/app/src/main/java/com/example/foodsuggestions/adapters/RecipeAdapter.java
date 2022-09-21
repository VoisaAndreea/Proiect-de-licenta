package com.example.foodsuggestions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.example.data.model.Recipe;
import com.example.foodsuggestions.R;
import com.example.foodsuggestions.databinding.RowRecipeBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>  {

    private List<Recipe> recipe = new ArrayList<>();
    private RecipeListener listener;
    private final LayoutInflater mInflater;

    public void setListener(RecipeListener listener){
        this.listener = listener;
    }
    public void updateRecipes(List<Recipe> recipe){
        this.recipe.clear();
        this.recipe.addAll(recipe);
        notifyDataSetChanged();
    }

    public RecipeAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }
    //Pentru a stoca informatii despre vizualizarea unui singur element din lista
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final RowRecipeBinding binding;

        public ViewHolder(RowRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Recipe recipe) {
            this.binding.setRecipe(recipe);
            binding.getRoot().setAnimation(AnimationUtils.loadAnimation(mInflater.getContext(), R.anim.scale_animation));
        }

    }

    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowRecipeBinding binding  = RowRecipeBinding.inflate(mInflater, parent, false);
        RecipeAdapter.ViewHolder viewHolder = new ViewHolder(binding);


        viewHolder.itemView.setOnClickListener(v-> {
                int position =  viewHolder.getBindingAdapterPosition();
                listener.onRecipeSelected(recipe.get(position));
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        holder.bind(recipe.get(position));
    }


    @Override
    public int getItemCount() {
        return recipe.size();
    }

    String getItem(int id) {
        return recipe.get(id).getImage();
    }


    public interface RecipeListener{
        void onRecipeSelected(Recipe recipe);
    }
}
