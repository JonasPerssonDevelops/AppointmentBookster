package edu.pdx.cs410J.jpersson;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import edu.pdx.cs410J.jpersson.databinding.FragmentReadmeBinding;

public class ReadmeFragment extends Fragment {

    String stringReadme;

    private FragmentReadmeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // BEGIN: Reading the readme file into a string
        Context context = getContext();
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open("README.txt");
        } catch (IOException e) {

        }
        // Begin building
        StringBuilder sb = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {

        }
        // Close the stream
        try {
            is.close();
        } catch (IOException e) {

        }
        stringReadme = sb.toString();

        binding = FragmentReadmeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mainText = getActivity().findViewById(R.id.readmeTextviewMaintext);
        mainText.setText(stringReadme);
        mainText.setMovementMethod(new ScrollingMovementMethod());


        binding.readmeReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ReadmeFragment.this)
                        .navigate(R.id.action_ReadmeFragment_to_FirstFragment);
            }
            // ^ Here is where the next button magic is that takes you to the next fragment via an action
        }); // END setOnClickListener

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void sendSnackbarMessage(View view, String message) {
        Snackbar sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        sb.setAction("Action", null);
        sb.setAnchorView(R.id.snackbarGuideline);
        View sbView = sb.getView();
        TextView sbTextView = (TextView) sbView.findViewById(R.id.snackbar_text);
        sbTextView.setMaxLines(5);
        sb.show();
    }


} // End ReadmeFragment class definition



/*


// Testing button
        binding.readmeTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        }); // END onClickListener


// Best EditText solution: displays readable text with EditText
// Put this in onViewCreated:

        EditText mainText = view.findViewById(R.id.readmeEdittextMaintext);
        mainText.setText(stringReadme);
        mainText.setShowSoftInputOnFocus(false); // Prevents the keyboard from popping up


// Working solution. Put this in onViewCreated and then the setText will work and update
// the view before it is rendered

        TextView mainText = getActivity().findViewById(R.id.readmeTextviewMaintext);
        mainText.setText(stringReadme);


//This apparently is deprecated and doesn't do anything anymore
        mainText.setShowSoftInputOnFocus(false);

// EditText solution
        EditText mainText = view.findViewById(R.id.readmeEdittextMaintext);
        mainText.setText(stringReadme);
        mainText.setEnabled(false);
        mainText.setFocusable(true);
        mainText.setFocusableInTouchMode(true);

// Non-working approach that fails in oncreateview

        View inflatedview = inflater.inflate(R.layout.fragment_readme, container, false);
        TextView mainText = (TextView) inflatedview.findViewById(R.id.readmeTextviewMaintext);

        mainText.setText(stringReadme);


// Almost working version but setText fails

        // BEGIN: Reading the readme file into a string
        Context context = getContext();
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open("README.txt");
        } catch (IOException e) {

        }
        // Begin building
        StringBuilder sb = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {

        }
        // Close the stream
        try {
            is.close();
        } catch (IOException e) {

        }
        String readme = sb.toString();

        View inflatedview = inflater.inflate(R.layout.fragment_readme, container, false);
        TextView mainText = (TextView) inflatedview.findViewById(R.id.readmeTextviewMaintext);

        //TextView mainText = getActivity().findViewById(R.id.readmeTextviewMaintext);
        mainText.setText(readme);















 */













