package com.ultra.vision.movies.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.ultra.vision.databinding.FragmentAccountBinding;
import com.ultra.vision.databinding.FragmentRecommendedBinding;

public class FragmentAccount extends Fragment {

    
    FragmentAccountBinding binding;
    
    public FragmentAccount() {
        // Required empty public constructor
    }
    
    public static FragmentAccount newInstance() {
        return new FragmentAccount();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        
        return binding.getRoot();
    }
    
     
}
