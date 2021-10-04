package com.rudrashisdutta.sqlitetrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rudrashisdutta.sqlitetrial.databinding.ActivityValueBinding;

public class Value extends AppCompatActivity {
    
    private TextView title;
    private TextView details;
    
    private ActivityValueBinding binding;

    public static final String KEY = "KEY";
    public static final String VALUE = "VALUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityValueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.title = binding.keyFocused;
        this.details = binding.valueFocused;

        Intent intent = getIntent();
        String title = intent.getStringExtra(KEY);
        String details = intent.getStringExtra(VALUE);

        this.title.setText(title);
        this.details.setText(details);
    }
}