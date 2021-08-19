package edu.pdx.cs410J.jpersson;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.jpersson.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    EditText editTextOwner;
    EditText editTextStartDateTime;
    EditText editTextEndDateTime;

    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextOwner = view.findViewById(R.id.searchEdittextOwner);
        // ^ Returns the EditText instance tied to the add_edittext_owner View
        editTextStartDateTime = view.findViewById(R.id.searchEdittextStartDateTime);
        editTextEndDateTime= view.findViewById(R.id.searchEdittextEndDateTime);

        EditText mainText = view.findViewById(R.id.searchEdittextResults);

        binding.searchButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

                String owner = editTextOwner.getText().toString();
                String startDateTime = editTextStartDateTime.getText().toString();
                String endDateTime = editTextEndDateTime.getText().toString();

                if(!owner.equals("") && startDateTime.equals("") && endDateTime.equals("") ) {
                    // Only an owner was given show all the owners appointments

                    AppointmentBook ownerBook = MainActivity.appointmentBooks.get(owner);

                    if (ownerBook != null) {
                        sendSnackbarMessage(view, "Appointment book successfully retrieved for " + owner + ".");
                        String str = AppointmentBook.prettyPrintToString(ownerBook);
                        if (str == null) mainText.setText(ErrorMessages.NO_APPOINTMENTS);
                        else mainText.setText(str);
                    } else {
                        sendSnackbarMessage(view, "Could not retrieve an appointment book for " + owner + ".");
                    }

                } else if (!owner.equals("") && !startDateTime.equals("") && !endDateTime.equals("")) {
                    // An owner and dates were given, validate that a book exists for the owner, then the dates

                    AppointmentBook ownerBook = MainActivity.appointmentBooks.get(owner);

                    if(ownerBook == null) {
                        sendSnackbarMessage(view, "Could not retrieve an appointment book for " + owner + ".");
                        return;
                    }
                    if(!ParseUserInput.parseDateTime(startDateTime, null)) {
                        sendSnackbarMessage(view, ErrorMessages.INCORRECT_START_DATETIME);
                        return;
                    }
                    if(!ParseUserInput.parseDateTime(endDateTime, null)) {
                        sendSnackbarMessage(view, ErrorMessages.INCORRECT_END_DATETIME);
                        return;
                    }
                    // Dates were validated. Execute the search.
                    AppointmentBook destBook = new AppointmentBook(owner);

                    Date startDate = Appointment.parseStringToDate(startDateTime);
                    Date endDate = Appointment.parseStringToDate(endDateTime);

                    AppointmentBook.addAppointmentsWithinRangeToBook(ownerBook, startDate, endDate, destBook);

                    if(0 < destBook.getAppointments().size()) {
                        sendSnackbarMessage(view, "Appointments within this time range were found for " + owner + ".");
                        String str = AppointmentBook.prettyPrintToString(destBook);
                        if (str == null) mainText.setText(ErrorMessages.NO_APPOINTMENTS);
                        else mainText.setText(str);
                    } else {
                        sendSnackbarMessage(view, "No appointments in this time range were found for " + owner + ".");
                        mainText.setText(ErrorMessages.NO_APPOINTMENTS);
                    }

                } else {
                    // Not enough information to search was given
                    if(owner.equals("")) {
                        sendSnackbarMessage(view, ErrorMessages.INVALID_OWNER);
                        return;
                    }
                    if(!ParseUserInput.parseDateTime(startDateTime, null)) {
                        sendSnackbarMessage(view, ErrorMessages.INCORRECT_START_DATETIME);
                        return;
                    }
                    if(!ParseUserInput.parseDateTime(endDateTime, null)) {
                        sendSnackbarMessage(view, ErrorMessages.INCORRECT_END_DATETIME);
                    }

                } // END IF-ELSE-IF

            } // END onClick
        }); // END setOnClickListener / search button algorithm

        binding.searchReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SearchFragment.this)
                        .navigate(R.id.action_SearchFragment_to_FirstFragment);
            }
            // ^ Here is where the next button magic is that takes you to the next fragment via an action
        }); // END setOnClickListener


    } // END onViewCreated

    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }


    public void sendSnackbarMessage(View view, String message) {
        Snackbar sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        sb.setAction("Action", null);
        sb.setAnchorView(R.id.searchGuidelineTopResults);
        View sbView = sb.getView();
        TextView sbTextView = (TextView) sbView.findViewById(R.id.snackbar_text);
        sbTextView.setMaxLines(5);
        sb.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}