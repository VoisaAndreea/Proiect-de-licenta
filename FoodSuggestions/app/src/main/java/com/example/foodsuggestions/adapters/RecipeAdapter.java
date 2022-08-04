package com.example.foodsuggestions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.data.model.Recipe;
import com.squareup.picasso.Picasso;

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

        TextView nameRecipe;
        ImageView imageRecipe;
        RelativeLayout continer;

        public ViewHolder(View itemView) {
            super(itemView);

            continer = itemView.findViewById(R.id.action_container);
            nameRecipe = itemView.findViewById(R.id.idTitleRe);
            imageRecipe = itemView.findViewById(R.id.idImageRe);
        }

    }

    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.row_recipe,parent,false);
        RecipeAdapter.ViewHolder viewHolder = new ViewHolder(view);


        viewHolder.itemView.setOnClickListener(v-> {
                int position =  viewHolder.getBindingAdapterPosition();
                listener.onRecipeSelected(recipe.get(position));
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {

        holder.continer.setAnimation(AnimationUtils.loadAnimation(mInflater.getContext(), R.anim.scale_animation));
        holder.nameRecipe.setText(recipe.get(position).getTitle());
        Picasso.with(mInflater.getContext()).load(recipe.get(position).getImage()).into(holder.imageRecipe);

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
