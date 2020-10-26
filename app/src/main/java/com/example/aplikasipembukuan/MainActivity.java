package com.example.aplikasipembukuan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.aplikasipembukuan.adapter.PengeluaranAdapter;
import com.example.aplikasipembukuan.db.PengeluaranHelper;
import com.example.aplikasipembukuan.entity.Pengeluaran;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.example.aplikasipembukuan.FormAddUpdateActivity.REQUEST_UPDATE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvPengeluaran;
    ProgressBar proggressBar;
    FloatingActionButton fabAdd;

    private LinkedList<Pengeluaran> list;
    private PengeluaranAdapter adapter;
    private PengeluaranHelper pengeluaranHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Catatan Pengeluaran");
        rvPengeluaran = (RecyclerView) findViewById(R.id.rv_pengeluaran);
        rvPengeluaran.setLayoutManager(new LinearLayoutManager(this));
        rvPengeluaran.setHasFixedSize(true);
        proggressBar = (ProgressBar) findViewById(R.id.progressbar);
        fabAdd = (FloatingActionButton) findViewById(R.id.add_item);
        fabAdd.setOnClickListener(this);

        pengeluaranHelper = new PengeluaranHelper(this);
        pengeluaranHelper.open();

        list = new LinkedList<>();

        adapter = new PengeluaranAdapter(this);
        adapter.setListPengeluarans(list);

        rvPengeluaran.setAdapter(adapter);
        new LoadPengeluaranAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_item){
            Intent intent = new Intent(MainActivity.this, FormAddUpdateActivity.class);
            startActivityForResult(intent, FormAddUpdateActivity.REQUEST_ADD);
        }
    }

    private class LoadPengeluaranAsync extends AsyncTask<Void, Void, ArrayList<Pengeluaran>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            proggressBar.setVisibility(View.VISIBLE);
            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<Pengeluaran> doInBackground(Void... voids) {
            return pengeluaranHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<Pengeluaran> pengeluarans) {
            super.onPostExecute(pengeluarans);
            proggressBar.setVisibility(View.GONE);
            list.addAll(pengeluarans);
            adapter.setListPengeluarans(list);
            adapter.notifyDataSetChanged();
            if (list.size() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FormAddUpdateActivity.REQUEST_ADD){
            if (resultCode == FormAddUpdateActivity.RESULT_ADD){
                new LoadPengeluaranAsync().execute();
                showSnackbarMessage("Satu item berhasil ditambahkan");
                // rvNotes.getLayoutManager().smoothScrollToPosition(rvNotes, new RecyclerView.State(), 0);
            }
        }
        else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormAddUpdateActivity.RESULT_UPDATE) {
                new LoadPengeluaranAsync().execute();
                showSnackbarMessage("Satu item berhasil diubah");
                // int position = data.getIntExtra(FormAddUpdateActivity.EXTRA_POSITION, 0);
                // rvNotes.getLayoutManager().smoothScrollToPosition(rvNotes, new RecyclerView.State(), position);
            }
            else if (resultCode == FormAddUpdateActivity.RESULT_DELETE) {
                int position = data.getIntExtra(FormAddUpdateActivity.EXTRA_POSITION, 0);
                list.remove(position);
                adapter.setListPengeluarans(list);
                adapter.notifyDataSetChanged();
                showSnackbarMessage("Satu item berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pengeluaranHelper != null){
            pengeluaranHelper.close();
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvPengeluaran, message, Snackbar.LENGTH_SHORT).show();
    }

}