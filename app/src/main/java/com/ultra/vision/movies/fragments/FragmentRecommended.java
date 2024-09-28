package com.ultra.vision.movies.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.ultra.vision.databinding.FragmentRecommendedBinding;

public class FragmentRecommended extends Fragment {

    
    FragmentRecommendedBinding binding;
    
    public FragmentRecommended() {
        // Required empty public constructor
    }
    
    public static FragmentRecommended newInstance() {
        return new FragmentRecommended();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecommendedBinding.inflate(inflater, container, false);

        
        return binding.getRoot();
    }
}
