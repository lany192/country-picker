package com.sahooz.library.countrypicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<AreaHolder> {
    private final LayoutInflater inflater;
    private final Context context;
    private ArrayList<Area> selectedCountries = new ArrayList<>();
    private PickAreaCallback callback = null;
    private int itemHeight = -1;

    public Adapter(Context ctx) {
        inflater = LayoutInflater.from(ctx);
        context = ctx;
    }

    public void setSelectedCountries(ArrayList<Area> selectedCountries) {
        this.selectedCountries = selectedCountries;
        notifyDataSetChanged();
    }

    public void setCallback(PickAreaCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public AreaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AreaHolder(inflater.inflate(R.layout.item_country, parent, false));
    }

    public void setItemHeight(float dp) {
        itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, context.getResources().getDisplayMetrics());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AreaHolder holder, int position) {
        final Area country = selectedCountries.get(position);
        holder.ivFlag.setImageResource(country.flag);
        holder.tvName.setText(country.name + "(" + country.locale + ")");
        holder.tvCode.setText("+" + country.code);
        if (itemHeight != -1) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = itemHeight;
            holder.itemView.setLayoutParams(params);
        }
        holder.itemView.setOnClickListener(v -> {
            if (callback != null) callback.onPick(country);
        });
    }

    @Override
    public int getItemCount() {
        return selectedCountries.size();
    }

}
