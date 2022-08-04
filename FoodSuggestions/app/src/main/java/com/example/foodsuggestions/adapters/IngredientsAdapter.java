package com.example.foodsuggestions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.model.Ingredients;
import com.example.foodsuggestions.R;

import java.util.ArrayList;

public class  IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    public ArrayList<Ingredients> ingredientsArrayList;

    public LayoutInflater mInflater;

    public IngredientsAdapter(Context context, ArrayList<Ingredients> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.row_ingredients,parent,false);

        return new IngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.amoundText.setText(ingredientsArrayList.get(position).getAmount());
        holder.unitText.setText(ingredientsArrayList.get(position).getUnit());
        holder.ingredientText.setText(ingredientsArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return ingredientsArrayList == null ? 0 : ingredientsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView amoundText;
        TextView unitText;
        TextView ingredientText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amoundText = (TextView) itemView.findViewById(R.id.idAmount);
            unitText = (TextView) itemView.findViewById(R.id.idUnit);
            ingredientText = (TextView) itemView.findViewById(R.id.idNameIngredient);
        }
    }
}
