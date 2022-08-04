package com.example.foodsuggestions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsuggestions.R;
import com.example.data.model.Instructions;

import java.util.ArrayList;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ViewHolder>{

    public ArrayList<Instructions> instructions;
    public LayoutInflater mInflater;

    public InstructionAdapter(Context context, ArrayList<Instructions> instructions) {
        this.instructions = instructions;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.row_instruction,parent,false);

        return new InstructionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textInstr.setText(instructions.get(0).getSteps().get(position).getStep());
    }

    @Override
    public int getItemCount() {
        return instructions == null ? 0 : instructions.get(0).getSteps().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textInstr;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textInstr = itemView.findViewById(R.id.rowInstruction);
        }
    }
}
