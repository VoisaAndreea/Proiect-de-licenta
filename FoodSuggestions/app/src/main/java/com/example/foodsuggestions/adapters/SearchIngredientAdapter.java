package com.example.foodsuggestions.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.main.SearchIngredientsActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientAdapter extends RecyclerView.Adapter<SearchIngredientAdapter.SearchIngredientViewHolder>{

    private List<SearchIngredientsActivity.IngredientsState> allItems = new ArrayList<>();
    private List<SearchIngredientsActivity.IngredientsState> filterItems = new ArrayList<>();

    public SearchIngredientAdapter(List<SearchIngredientsActivity.IngredientsState> allItems){
        this.allItems.addAll(allItems);
        filterItems.addAll(allItems);
    }

    public void filter(String str){
        filterItems.clear();
        if (str.length() > 0) {
            for(SearchIngredientsActivity.IngredientsState ing : allItems){
                if (ing.ingredientName.contains(str)) {
                    filterItems.add(ing);
                }
            }
        } else {
            filterItems.addAll(allItems);
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedIngredients() {
        ArrayList<String> result = new ArrayList<>();
        for(SearchIngredientsActivity.IngredientsState ing : allItems) {
            if (ing.isCheck) {
                result.add(ing.ingredientName);
            }
        }
        return result;
    }

    @NonNull
    @Override
    public SearchIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CheckedTextView view = (CheckedTextView) layoutInflater.inflate(
                android.R.layout.simple_list_item_multiple_choice,
                parent,
                false
        );
        SearchIngredientViewHolder viewHolder = new SearchIngredientViewHolder(view);
        view.setOnClickListener(v -> {
            int position = viewHolder.getBindingAdapterPosition();
            filterItems.get(position).isCheck = !filterItems.get(position).isCheck;
            notifyItemChanged(position);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchIngredientViewHolder holder, int position) {
        SearchIngredientsActivity.IngredientsState ing = filterItems.get(position);
        holder.bind(ing);
    }

    @Override
    public int getItemCount() {
        return filterItems.size();
    }

    static class SearchIngredientViewHolder extends RecyclerView.ViewHolder {
        private CheckedTextView view;

        public void bind (SearchIngredientsActivity.IngredientsState ing){
            view.setText(ing.ingredientName);
            view.setChecked(ing.isCheck);
        }

        public SearchIngredientViewHolder(@NonNull CheckedTextView itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

}
