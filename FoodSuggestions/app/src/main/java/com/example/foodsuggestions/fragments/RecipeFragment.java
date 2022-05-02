package com.example.foodsuggestions.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.foodsuggestions.R;
import com.squareup.picasso.Picasso;


public class RecipeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        TextView textView = view.findViewById(R.id.idTextFrag);
        ImageView imageView = view.findViewById(R.id.idImageFrag);
        Bundle data = getArguments();

        if(data != null){
            textView.setText(data.getString("titleRet"));
            Picasso.with(imageView.getContext()).load(data.getString("imageRet")).into(imageView);
        }
        return view;
    }

}