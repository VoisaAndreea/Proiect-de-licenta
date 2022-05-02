package com.example.foodsuggestions.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.InstructionAdapter;
import com.example.foodsuggestions.models.Instructions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class InstructionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.idListInstructions);

        Bundle data = getArguments();


        if(data != null){
            ArrayList<Instructions> gson =
                    new Gson().fromJson(data.getString("step"),
                    new TypeToken<ArrayList<Instructions>>(){}.getType());
            Log.d("tag","AAAAAAAAAAA: " + gson.size());

            if(gson.isEmpty()){
                Toast.makeText(view.getContext(), "IS EMPTY", Toast.LENGTH_SHORT).show();
//            ArrayAdapter<String> listAdapter =
//                    new ArrayAdapter<>(view.getContext(), R.layout.row_instruction,
//                            Collections.singletonList(data.getString("summary")));
//            listView.setAdapter(listAdapter);

//            InstructionAdapter instructionAdapter = new InstructionAdapter(view.getContext(), data.getString("summary"));
//            RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
//            recyclerView.setLayoutManager(manager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setAdapter(instructionAdapter);
            }
            else{
                InstructionAdapter instructionAdapter = new InstructionAdapter(view.getContext(), gson);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(instructionAdapter);
            }

        }
        return view;
    }
}