package edu.pdx.cs410J.jpersson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.pdx.cs410J.jpersson.databinding.FragmentReadmeBinding;

public class ReadmeFragment extends Fragment {

    private FragmentReadmeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentReadmeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.readmeReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ReadmeFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
            // ^ Here is where the next button magic is that takes you to the next fragment via an action
        }); // END setOnClickListener


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}