package com.ultra.vision.movies.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.ultra.vision.databinding.FragmentMylistBinding;
import com.ultra.vision.databinding.FragmentRecommendedBinding;

public class FragmentMyList extends Fragment {

    
    FragmentMylistBinding binding;
    
    public FragmentMyList() {
        // Required empty public constructor
    }
    
    public static FragmentMyList newInstance() {
        return new FragmentMyList();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMylistBinding.inflate(inflater, container, false);

        
        return binding.getRoot();
    }
    
}
