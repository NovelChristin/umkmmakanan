package com.christin.umkmmakanan.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.Retrofit.Response;
import com.christin.umkmmakanan.Retrofit.RestApi;
import com.christin.umkmmakanan.R;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, phoneEditText, passwordEditText, passwordConfirmationEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = findViewById(R.id.edit_text_name);
        emailEditText = findViewById(R.id.edit_text_email);
        phoneEditText = findViewById(R.id.edit_text_phone);
        passwordEditText = findViewById(R.id.edit_text_password);
        passwordConfirmationEditText = findViewById(R.id.edit_text_password_confirmation);
        signupButton = findViewById(R.id.button_signup);

        TextView textSignIn = findViewById(R.id.textSignIn);
        textSignIn.setOnClickListener(v -> {
            // Pindah ke SignInActivity
            Utils.openActivity(this, SignInActivity.class);
        });

        signupButton.setOnClickListener(v -> processSignUp());
    }

    private void processSignUp() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirmation = passwordConfirmationEditText.getText().toString();

        // Validasi input
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Utils.show(this, "Semua field harus diisi.");
            return;
        }

        // Panggil API untuk sign up
        RestApi apiService = Utils.getClient().create(RestApi.class);
        Call<Response> call = apiService.signup(name, email, phone, password, passwordConfirmation);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response apiResponse = response.body();
                    if (apiResponse.getSuccess()) {
                        Utils.show(SignUpActivity.this, apiResponse.getMessage());
                        // Arahkan ke halaman login setelah signup berhasil
                        Utils.openActivity(SignUpActivity.this, SignInActivity.class);
                        finish(); // Selesaikan aktivitas ini
                    } else {
                        Utils.show(SignUpActivity.this, apiResponse.getMessage());
                    }
                } else {
                    Utils.show(SignUpActivity.this, "Gagal memproses permintaan.");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Utils.show(SignUpActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
}