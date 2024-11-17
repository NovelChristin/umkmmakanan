package com.christin.umkmmakanan.Views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.Retrofit.Response;
import com.christin.umkmmakanan.Retrofit.RestApi;
import com.christin.umkmmakanan.Retrofit.User;
import com.christin.umkmmakanan.R;

import retrofit2.Call;
import retrofit2.Callback;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.edit_text_email);
        passwordEditText = findViewById(R.id.edit_text_password);
        signInButton = findViewById(R.id.button_signin);

        TextView textSignUp = findViewById(R.id.textSignUp);
        textSignUp.setOnClickListener(v -> {
            // Pindah ke SignUpActivity
            Utils.openActivity(this,  SignUpActivity.class);
        });

        signInButton.setOnClickListener(v -> processSignIn());
    }

    private void processSignIn() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Validasi input
        if (email.isEmpty() || password.isEmpty()) {
            Utils.show(this, "Email dan password tidak boleh kosong.");
            return;
        }

        // Panggil API untuk sign in
        RestApi apiService = Utils.getClient().create(RestApi.class);
        Call<Response> call = apiService.signin(email, password);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response apiResponse = response.body();
                    if (apiResponse.getSuccess()) {
                        // Simpan informasi pengguna di SharedPreferences
                        saveUserInfo(apiResponse.getUser());
                        Utils.show(SignInActivity.this, apiResponse.getMessage());
                        // Arahkan ke aktivitas berikutnya, misalnya MainActivity
                        Utils.openActivity(SignInActivity.this, MainActivity.class);
                        finish(); // Selesaikan aktivitas ini
                    } else {
                        Utils.show(SignInActivity.this, apiResponse.getMessage());
                    }
                } else {
                    Utils.show(SignInActivity.this, "Gagal memproses permintaan.");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Utils.show(SignInActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    private void saveUserInfo(User user) {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_logged_in", true);
        editor.putString("user_id", user.getId());
        editor.putString("user_name", user.getName());
        editor.putString("user_phone", user.getPhone());
        editor.putString("user_email", user.getEmail());
        editor.apply();
    }
}