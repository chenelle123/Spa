package com.example.spa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BookAppointmentActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;
    private EditText nameEditText, emailEditText, dateEditText, timeEditText;
    private ImageView imagePreview;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // Initialize views
        nameEditText = findViewById(R.id.editText_name);
        emailEditText = findViewById(R.id.editText_email);
        dateEditText = findViewById(R.id.editText_date);
        timeEditText = findViewById(R.id.editText_time);
        imagePreview = findViewById(R.id.image_preview);

        Button uploadButton = findViewById(R.id.button_upload_image);
        Button scheduleButton = findViewById(R.id.button_schedule);

        // Upload image button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        // Schedule appointment button
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAppointment();
            }
        });

        // Bottom navigation buttons
        findViewById(R.id.button_home).setOnClickListener(v -> navigateTo(HomeActivity.class));
        findViewById(R.id.button_bookings).setOnClickListener(v -> navigateTo(BookingsActivity.class));
        findViewById(R.id.button_favorites).setOnClickListener(v -> navigateTo(FavoritesActivity.class));
        findViewById(R.id.button_more).setOnClickListener(v -> navigateTo(MoreActivity.class));
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
            imagePreview.setVisibility(View.VISIBLE);
        }
    }

    private void scheduleAppointment() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();

        if (name.isEmpty() || email.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Logic to save appointment
        Toast.makeText(this, "Appointment scheduled for " + name + " on " + date + " at " + time, Toast.LENGTH_LONG).show();

        // Clear fields after scheduling
        nameEditText.setText("");
        emailEditText.setText("");
        dateEditText.setText("");
        timeEditText.setText("");
        imagePreview.setVisibility(View.GONE);
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(BookAppointmentActivity.this, cls);
        startActivity(intent);
    }
}
