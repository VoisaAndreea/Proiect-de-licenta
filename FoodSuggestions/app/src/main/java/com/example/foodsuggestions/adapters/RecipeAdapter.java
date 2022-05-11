package com.example.foodsuggestions.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.main.ShowRecipeActivity;
import com.example.foodsuggestions.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>  {

    public List<Recipe> recipe;

    public LayoutInflater mInflater;

    public RecipeAdapter(Context context, List<Recipe> recipe) {
        this.recipe = recipe;
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


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =  viewHolder.getBindingAdapterPosition();
                Intent intent = ShowRecipeActivity.getIntent(recipe.get(position), parent.getContext());
                parent.getContext().startActivity(intent);
            }
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
        return recipe == null ? 0 : recipe.size();
    }

    String getItem(int id) {
        return recipe.get(id).getImage();
    }


}
