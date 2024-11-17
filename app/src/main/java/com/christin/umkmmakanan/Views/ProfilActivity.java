package com.christin.umkmmakanan.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;

public class ProfilActivity extends AppCompatActivity {

    private TextView textUserName, textUserEmail, textUserPhone;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        textUserName = findViewById(R.id.text_user_name);
        textUserEmail = findViewById(R.id.text_user_email);
        textUserPhone = findViewById(R.id.text_user_phone);

        // Ambil data pengguna dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = prefs.getString("user_name", "Tidak ada nama");
        String userEmail = prefs.getString("user_email", "Tidak ada email");
        String userPhone = prefs.getString("user_phone", "Tidak ada telepon");

        // Tampilkan data pengguna
        textUserName.setText("Nama: " + userName);
        textUserEmail.setText("Email: " + userEmail);
        textUserPhone.setText("Telepon: " + userPhone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            // Proses logout
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // Hapus data pengguna dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        // Kembali ke SignInActivity
        Utils.openActivity(this, DashboardActivity.class);
        finish();
    }
}
