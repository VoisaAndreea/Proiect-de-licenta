package com.example.foodsuggestions.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.adapters.InstructionAdapter;
import com.example.foodsuggestions.models.Instructions;

import java.util.ArrayList;
import java.util.List;

public class InstructionFragment extends Fragment {
    private static final String INSTRUCTION_KEY = "INSTRUCTION_KEY";
    private static final String SUMMARY_KEY = "SUMMARY_KEY";
    private static final String SOURCE_KEY = "SOURCE_KEY";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.idListInstructions);
        TextView textSummary = view.findViewById(R.id.idSummary);
        TextView textSource = view.findViewById(R.id.idSourceUrl);

        Bundle data = getArguments();

        if(data != null){
            if(data.getParcelableArrayList(INSTRUCTION_KEY).isEmpty()){
                textSummary.setVisibility(View.VISIBLE);
                textSource.setVisibility(View.VISIBLE);
                textSummary.setText(data.getString(SUMMARY_KEY));
                textSource.setText(data.getString(SOURCE_KEY));


            }
            else{
                recyclerView.setVisibility(View.VISIBLE);
                InstructionAdapter instructionAdapter = new InstructionAdapter(view.getContext(), data.getParcelableArrayList(INSTRUCTION_KEY));
                RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(instructionAdapter);
                Log.d(InstructionFragment.class.getSimpleName(),"INSTRACTION: " + data.getParcelableArrayList(INSTRUCTION_KEY));

            }

        }
        return view;
    }

    public static InstructionFragment newInstance(
            List<Instructions> instructionsList,
            String summary,
            String sourceUrl
    ){
        Bundle args = new Bundle();
        args.putParcelableArrayList(INSTRUCTION_KEY, new ArrayList<>(instructionsList));
        args.putString(SUMMARY_KEY, summary);
        args.putString(SOURCE_KEY, sourceUrl);
        InstructionFragment fragment = new InstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }
}