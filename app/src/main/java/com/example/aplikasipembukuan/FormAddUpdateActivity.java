package com.example.aplikasipembukuan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplikasipembukuan.db.PengeluaranHelper;
import com.example.aplikasipembukuan.entity.Pengeluaran;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

public class FormAddUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    EditText edtBarang, edtHarga, edtJumlah, edtTotal, edtTglPengeluaran;
    Button btnSubmit;
    ImageView btnGetDate;
    public static String EXTRA_PENGELUARAN = "extra_pengeluaran";
    public static String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private Pengeluaran pengeluaran;
    private int position;
    private PengeluaranHelper pengeluaranHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_update);
        edtBarang = (EditText) findViewById(R.id.et_barang);
        edtHarga = (EditText) findViewById(R.id.et_harga);
        edtJumlah = (EditText) findViewById(R.id.et_jumlah);
        edtTotal = (EditText) findViewById(R.id.et_total);
        edtTglPengeluaran = (EditText) findViewById(R.id.et_tgl_pengeluaran);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnGetDate = (ImageView) findViewById(R.id.btn_date);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        btnGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnSubmit.setOnClickListener(this);
        pengeluaranHelper = new PengeluaranHelper(this);
        pengeluaranHelper.open();

        pengeluaran = getIntent().getParcelableExtra(EXTRA_PENGELUARAN);

        if (pengeluaran != null){
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        }

        String actionBarTitle = null;
        String btnTitle = null;

        if(isEdit){
            actionBarTitle = "Ubah";
            btnTitle = "Update";
            edtBarang.setText(pengeluaran.getBarang());
            edtHarga.setText(String.valueOf(pengeluaran.getHarga()));
            edtJumlah.setText(String.valueOf(pengeluaran.getJumlah()));
            edtTotal.setText(String.valueOf(pengeluaran.getTotal()));
            edtTglPengeluaran.setText(pengeluaran.getTglPengeluaran());
        }else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setText(btnTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pengeluaranHelper != null){
            pengeluaranHelper.close();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit){
            String barang = edtBarang.getText().toString().trim();
            String harga_str = edtHarga.getText().toString().trim();
            String jumlah_str = edtJumlah.getText().toString().trim();
            String total_str = edtTotal.getText().toString().trim();
            String tgl_pengeluaran = edtTglPengeluaran.getText().toString().trim();
            boolean isEmpty = false;
            if (isEmpty(barang)){
                isEmpty = true;
                edtBarang.setError("Field can not be blank");
            }
            else if (isEmpty(jumlah_str)){
                isEmpty = true;
                edtJumlah.setError("Field can not be blank");
            }
            else if (isEmpty(harga_str)){
                isEmpty = true;
                edtHarga.setError("Field can not be blank");
            }
            else if (isEmpty(total_str)){
                isEmpty = true;
                edtTotal.setError("Field can not be blank");
            }
            else if (isEmpty(tgl_pengeluaran)){
                isEmpty = true;
                edtTglPengeluaran.setError("Field can not be blank");
            }
            int harga = Integer.parseInt(harga_str);
            int jumlah = Integer.parseInt(jumlah_str);
            int total = Integer.parseInt(total_str);

            if (!isEmpty){
                Pengeluaran newPengeluaran = new Pengeluaran();
                newPengeluaran.setBarang(barang);
                newPengeluaran.setHarga(harga);
                newPengeluaran.setJumlah(jumlah);
                newPengeluaran.setTotal(total);
                newPengeluaran.setTglPengeluaran(tgl_pengeluaran);
                Intent intent = new Intent();
                if (isEdit){
                    newPengeluaran.setId(pengeluaran.getId());
                    pengeluaranHelper.update(newPengeluaran);
                    intent.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_UPDATE, intent);
                    finish();
                }else {
                    pengeluaranHelper.insert(newPengeluaran);
                    setResult(RESULT_ADD);
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    final int ALERT_DIALOG_CLOSE = 10;
    final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type){
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle = null, dialogMessage = null;
        if (isDialogClose){
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        }else{
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Pengeluaran";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int id) {
            if (isDialogClose){
                finish();
            }else{
                pengeluaranHelper.delete(pengeluaran.getId());
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_DELETE, intent);
                    finish();
                }
            }
        })
        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtTglPengeluaran.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}