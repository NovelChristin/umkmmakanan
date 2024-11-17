package com.christin.umkmmakanan.Views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.christin.umkmmakanan.Helpers.OrderProductAdapter;
import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.Response;
import com.christin.umkmmakanan.Retrofit.RestApi;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class DetailOrderActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private String orderId;
    private String adminId;
    private TextView textInvoiceId, textUserName, textUserPhone, textTotalPayment, textStatus, text_invoice_date, text_payment_method;
    private RecyclerView recyclerView;
    private Button buttonUploadPaymentProof;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private Uri imageUri;
    private Bitmap bitmap;
    private ImageView imagePaymentProof;
    Button buttonRate;

    private Button buttonOrderReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        initViews();

        // Mendapatkan data Order dari Intent
        Order order = (Order) getIntent().getSerializableExtra("ORDER_KEY");
        if (order != null) {
            orderId = order.get_id();
            adminId = order.getAdminId();
            displayOrderDetails(order);
        }

        buttonOrderReceived = findViewById(R.id.button_order_received);

        buttonOrderReceived.setOnClickListener(v -> {
            // Logic untuk menandai pesanan sebagai diterima
            markOrderAsReceived();
        });

        buttonUploadPaymentProof.setOnClickListener(v -> {
            try {
                // Meminta izin kamera dan penyimpanan
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    openCamera();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void markOrderAsReceived() {
        // Menggunakan Retrofit untuk mengupload bukti pembayaran
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), orderId);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "Pesanan Diterima");

        RestApi apiService = Utils.getClient().create(RestApi.class);
        Call<Response> call = apiService.updateOrderStatus(id, status, null);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response apiResponse = response.body();
                    if (apiResponse.getSuccess()) {
                        textStatus.setText("Pesanan Diterima");
                        buttonRate.setVisibility(View.VISIBLE);
                        ratingBar.setVisibility(View.VISIBLE);
                        Utils.show(DetailOrderActivity.this, "Konfirmasi sukses!");
                    } else {
                        Utils.show(DetailOrderActivity.this, "Gagal konfirmasi");
                    }
                } else {
                    Utils.show(DetailOrderActivity.this, "Gagal memproses permintaan.");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Utils.show(DetailOrderActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, inisialisasi tampilan
                try {
                    openCamera();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Izin ditolak
                Utils.show(this, "Izin kamera dan penyimpanan diperlukan.");
            }
        }
    }

    private void initViews() {
        ratingBar = findViewById(R.id.ratingBar);
        buttonRate = findViewById(R.id.button_rate);
        textInvoiceId = findViewById(R.id.text_invoice_id);
        textUserName = findViewById(R.id.text_user_name);
        textUserPhone = findViewById(R.id.text_user_phone);
        textTotalPayment = findViewById(R.id.text_total_payment);
        textStatus = findViewById(R.id.text_status);
        text_invoice_date = findViewById(R.id.text_invoice_date);
        text_payment_method = findViewById(R.id.text_payment_method);
        recyclerView = findViewById(R.id.recycler_view_order_products);
        buttonUploadPaymentProof = findViewById(R.id.button_upload_payment_proof);
        buttonOrderReceived = findViewById(R.id.button_order_received);
        imagePaymentProof = findViewById(R.id.image_payment_proof);


        buttonRate.setOnClickListener(v -> {
            int rating = (int) ratingBar.getRating();
            submitRating(orderId, adminId, String.valueOf(rating));
        });
    }

    private void openCamera() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Buat file untuk menyimpan foto
        File photoFile = createImageFile();
        imageUri = FileProvider.getUriForFile(this,
                "com.christin.umkmmakanan.fileprovider",
                photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    private File createImageFile() {
        // Buat nama file gambar
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if (imageUri != null) {
                try {
                    bitmap = decodeSampledBitmapFromUri(imageUri, 800, 800);

                    try {
                        bitmap = rotateImageIfRequired(bitmap, imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    uploadPaymentProof();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {
        ExifInterface ei = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ei = new ExifInterface(getContentResolver().openInputStream(selectedImage));
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
    }

    private Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth, int reqHeight) throws FileNotFoundException {
        // Pertama, dapatkan ukuran gambar
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // Hanya mendapatkan ukuran
        BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);

        // Hitung faktor skala untuk mengubah ukuran gambar
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false; // Sekarang kita ingin memuat gambar

        return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Ukuran gambar asli
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Hitung faktor skala yang sesuai
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void uploadPaymentProof() {
        // Menggunakan Retrofit untuk mengupload bukti pembayaran
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), orderId);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "Konfirmasi Pembayaran");

        // Convert Bitmap ke ByteArray
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            // Resize bitmap jika diperlukan (misal, untuk mengurangi ukuran)
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true); // Atur ukuran sesuai kebutuhan
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else {
            Log.e("Bitmap Error", "Bitmap is null");
            Utils.show(this, "Kami perlu photo KTP anda");
            return; // Hentikan proses jika bitmap null
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Cek apakah byteArray tidak kosong
        if (byteArray.length == 0) {
            Log.e("Compression Error", "Compression resulted in zero bytes");
            Utils.show(this, "Compression resulted in zero bytes");
            return; // Hentikan jika ukuran byte array adalah 0
        }

        // ** Mengirim gambar menggunakan Retrofit **
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
        MultipartBody.Part paymentProof = MultipartBody.Part.createFormData("paymentProof", "photo.jpg", requestFile); // Bagian ini dirubah


        RestApi apiService = Utils.getClient().create(RestApi.class);
        Call<Response> call = apiService.updateOrderStatus(id, status, paymentProof);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response apiResponse = response.body();
                    if (apiResponse.getSuccess()) {
                        textStatus.setText("Konfirmasi Pembayaran");
                        Utils.show(DetailOrderActivity.this, "Bukti pembayaran berhasil diupload!");
                    } else {
                        Utils.show(DetailOrderActivity.this, "Gagal mengupload bukti pembayaran: " + apiResponse.getMessage());
                    }
                } else {
                    Utils.show(DetailOrderActivity.this, "Gagal memproses permintaan.");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Utils.show(DetailOrderActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    private void submitRating(String orderId, String adminId, String rating) {
        RestApi apiService = Utils.getClient().create(RestApi.class);
        Call<Response> call = apiService.ratingOrder(orderId, adminId, rating);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response apiResponse = response.body();
                    if (apiResponse.getSuccess()) {
                        Utils.show(DetailOrderActivity.this, "Rating berhasil diberikan!");
                    } else {
                        Utils.show(DetailOrderActivity.this, "Gagal memberikan rating: " + apiResponse.getMessage());
                    }
                } else {
                    Utils.show(DetailOrderActivity.this, "Gagal memproses permintaan.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Utils.show(DetailOrderActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayOrderDetails(Order order) {
        textInvoiceId.setText("Invoice ID: " + order.getInvoiceId());
        textUserName.setText("User Name: " + order.getUserName());
        textUserPhone.setText("User Phone: " + order.getUserPhone());
        textTotalPayment.setText("Total Payment: " + order.getTotalPayment());
        textStatus.setText("Status: " + order.getStatus());
        ratingBar.setRating(order.getRating());

        // Cek apakah paymentProof kosong atau tidak
        if (order.getPaymentProof() != null && !order.getPaymentProof().isEmpty()) {
            buttonUploadPaymentProof.setVisibility(View.GONE); // Sembunyikan tombol jika sudah ada bukti pembayaran
            imagePaymentProof.setVisibility(View.VISIBLE); // Tampilkan gambar
            loadPaymentProof(order.getPaymentProof()); // Load gambar
        } else {
            buttonUploadPaymentProof.setVisibility(View.VISIBLE); // Tampilkan tombol jika bukti pembayaran belum ada
            imagePaymentProof.setVisibility(View.GONE); // Sembunyikan gambar
        }

        if ("Pesanan Diterima".equals(order.getStatus())) {
            buttonRate.setVisibility(View.VISIBLE); // Tampilkan button
            ratingBar.setVisibility(View.VISIBLE); // Tampilkan button
        } else {
            buttonRate.setVisibility(View.GONE); // Sembunyikan button
            ratingBar.setVisibility(View.GONE); // Sembunyikan button
        }


        if ("Pesanan Dikirim".equals(order.getStatus())) {
            buttonOrderReceived.setVisibility(View.VISIBLE); // Tampilkan button
        } else {
            buttonOrderReceived.setVisibility(View.GONE); // Sembunyikan button
        }

        text_invoice_date.setText("Invoice Date: " + order.getCreatedAt());
        text_payment_method.setText("Payment Method: " + (order.getCOD() ? "Cash on Delivery" : "Online Payment"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(order.getProduct());
        recyclerView.setAdapter(orderProductAdapter);

        textUserName.setVisibility(View.GONE);
        textUserPhone.setVisibility(View.GONE);
    }

    private void loadPaymentProof(String imagePath) {
        // Ganti dengan URL server Anda
        String fullPath = Utils.base_url + imagePath;

        // Menggunakan Picasso untuk memuat gambar
        Picasso.get()
                .load(fullPath)
                .placeholder(R.drawable.image_not_found)
                .into(imagePaymentProof);
    }
}


