package com.example.aplikasipembukuan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasipembukuan.CustomOnItemClickListener;
import com.example.aplikasipembukuan.FormAddUpdateActivity;
import com.example.aplikasipembukuan.R;
import com.example.aplikasipembukuan.entity.Pengeluaran;

import java.util.LinkedList;

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.PengeluaranViewholder> {
    private LinkedList<Pengeluaran> listpengeluarans;
    private Activity activity;

    public PengeluaranAdapter(Activity activity){
        this.activity = activity;
    }

    public LinkedList<Pengeluaran> getListPengeluarans(){
        return listpengeluarans;
    }

    public void setListPengeluarans(LinkedList<Pengeluaran> listpengeluarans){
        this.listpengeluarans = listpengeluarans;
    }

    @NonNull
    @Override
    public PengeluaranAdapter.PengeluaranViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengeluaran, parent, false);
        return new PengeluaranViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengeluaranAdapter.PengeluaranViewholder holder, int position) {
        holder.tvTglPengeluaran.setText(getListPengeluarans().get(position).getTglPengeluaran());
        holder.tvBarang.setText(getListPengeluarans().get(position).getBarang());
        holder.tvJumlah.setText(String.valueOf(getListPengeluarans().get(position).getJumlah()));
        holder.tvHarga.setText(String.valueOf(getListPengeluarans().get(position).getHarga()));
        holder.tvTotal.setText(String.valueOf(getListPengeluarans().get(position).getTotal()));
        holder.cvPengeluaran.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FormAddUpdateActivity.class);
                intent.putExtra(FormAddUpdateActivity.EXTRA_POSITION, position);
                intent.putExtra(FormAddUpdateActivity.EXTRA_PENGELUARAN, getListPengeluarans().get(position));
                activity.startActivityForResult(intent, FormAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getListPengeluarans().size();
    }

    public class PengeluaranViewholder extends RecyclerView.ViewHolder{
        TextView tvTglPengeluaran, tvBarang, tvHarga, tvJumlah, tvTotal;
        CardView cvPengeluaran;
        public PengeluaranViewholder(@NonNull View itemView) {
            super(itemView);
            tvTglPengeluaran = (TextView) itemView.findViewById(R.id.tv_tgl_pengeluaran);
            tvBarang = (TextView) itemView.findViewById(R.id.tv_barang);
            tvHarga = (TextView) itemView.findViewById(R.id.tv_harga);
            tvJumlah = (TextView) itemView.findViewById(R.id.tv_jumlah);
            tvTotal = (TextView) itemView.findViewById(R.id.tv_total);
            cvPengeluaran = (CardView) itemView.findViewById(R.id.cv_item_pengeluaran);
        }
    }

}
