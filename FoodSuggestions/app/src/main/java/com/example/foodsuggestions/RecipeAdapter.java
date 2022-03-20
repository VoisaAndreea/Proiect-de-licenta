package com.example.foodsuggestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    public List<Recipe> recipe;

    public LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RecipeAdapter(Context context, List<Recipe> recipe) {
        this.recipe = recipe;
        this.mInflater = LayoutInflater.from(context);
    }


    //Pentru a stoca informatii despre vizualizarea unui singur element din lista
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameRecipe;
        ImageView imageRecipe;

        RelativeLayout continer;

        public ViewHolder(View itemView) {
            super(itemView);

            continer = itemView.findViewById(R.id.action_container);

            nameRecipe = (TextView) itemView.findViewById(R.id.idTitleRe);
            imageRecipe = (ImageView) itemView.findViewById(R.id.idImageRe);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.row_recipe,parent,false);

        return new ViewHolder(view);
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
        return recipe.get(id).getTitle();
    }

    //metoda pentru a memora item-ul pe care se face click-ul
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    //interfata pentru a implementa metoda onItemClick-pentru a raspunde la evenimentele de click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
