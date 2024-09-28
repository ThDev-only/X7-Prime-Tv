package com.ultra.vision.utils;

import android.app.Activity;
import android.os.Bundle;
import com.ultra.vision.databinding.ActivityRegisterBinding;

public class RegisterActivity extends Activity {
    
    ActivityRegisterBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        
        setContentView(binding.getRoot());
    }
}
