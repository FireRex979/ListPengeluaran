package com.example.aplikasipembukuan.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Pengeluaran implements Parcelable {
    private int id;
    private String barang;
    private int jumlah;
    private int harga;
    private int total;
    private String tgl_pengeluaran;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getBarang(){
        return barang;
    }

    public void setBarang(String barang){
        this.barang = barang;
    }

    public int getJumlah(){
        return jumlah;
    }

    public void setJumlah(int jumlah){
        this.jumlah = jumlah;
    }

    public int getHarga(){
        return harga;
    }

    public void setHarga(int harga){
        this.harga = harga;
    }

    public int getTotal(){
        return total;
    }

    public void setTotal(int total){
        this.total = total;
    }

    public String getTglPengeluaran(){
        return tgl_pengeluaran;
    }

    public void setTglPengeluaran(String tgl_pengeluaran){
        this.tgl_pengeluaran = tgl_pengeluaran;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.barang);
        dest.writeInt(this.jumlah);
        dest.writeInt(this.harga);
        dest.writeInt(this.total);
        dest.writeString(this.tgl_pengeluaran);
    }

    public Pengeluaran(){
        //
    }

    protected Pengeluaran(Parcel in){
        this.id = in.readInt();
        this.barang = in.readString();
        this.jumlah = in.readInt();
        this.harga = in.readInt();
        this.total = in.readInt();
        this.tgl_pengeluaran = in.readString();
    }

    public static final Parcelable.Creator<Pengeluaran>CREATOR = new Parcelable.Creator<Pengeluaran>(){

        @Override
        public Pengeluaran createFromParcel(Parcel source) {
            return new Pengeluaran(source);
        }

        @Override
        public Pengeluaran[] newArray(int size) {
            return new Pengeluaran[size];
        }
    };
}
