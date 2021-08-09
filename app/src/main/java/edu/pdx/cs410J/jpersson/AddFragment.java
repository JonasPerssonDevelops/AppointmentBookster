package edu.pdx.cs410J.jpersson;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import edu.pdx.cs410J.jpersson.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {

    public static final String INCORRECT_START_DATETIME = "ERROR: The start date and time must be in the format MM/DD/YYYY HH:MM am/pm.";
    public static final String INCORRECT_END_DATETIME = "ERROR: The end date and time must be in the format MM/DD/YYYY HH:MM am/pm.";
    public static final String INVALID_OWNER = "ERROR: The owner field cannot be left blank.";
    public static final String INVALID_DESCRIPTION = "ERROR: The description field cannot be left blank.";
    public static final String ERROR_ADDING_APPOINTMENT = "ERROR: Could not add this appointment to the owners book.";

    EditText editTextOwner;
    EditText editTextDescription;
    EditText editTextStartDateTime;
    EditText editTextEndDateTime;

    String ownerTestVariable;

    private FragmentAddBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextOwner = view.findViewById(R.id.add_edittext_owner);
        // ^ Returns the EditText instance tied to the add_edittext_owner View
        editTextDescription = view.findViewById(R.id.add_edittext_description);
        editTextStartDateTime = view.findViewById(R.id.add_edittext_startDateTime);
        editTextEndDateTime= view.findViewById(R.id.add_edittext_endDateTime);

        binding.addFragmentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // Do this for every click:
                Date startDate = null;
                Date endDate = null;
                String message = "";
                String owner = editTextOwner.getText().toString();
                String description = editTextDescription.getText().toString();
                String startDateTime = editTextStartDateTime.getText().toString();
                String endDateTime = editTextEndDateTime.getText().toString();

                if(owner.equals("")) {
                    sendSnackbarMessage(view, INVALID_OWNER);
                    return;
                }
                if(description.equals("")) {
                    sendSnackbarMessage(view, INVALID_DESCRIPTION);
                    return;
                }
                if(!parseDateTime(startDateTime, startDate)) {
                    sendSnackbarMessage(view, INCORRECT_START_DATETIME);
                    return;
                }
                if(!parseDateTime(endDateTime, endDate)) {
                    sendSnackbarMessage(view, INCORRECT_END_DATETIME);
                    return;
                }
                // ^ When these checks for errors fail the data is valid and an appointment can be entered
                Appointment appt = new Appointment();
                appt.processAppointment(description, startDateTime, endDateTime);
/*
                AppointmentBook anotherBook = new AppointmentBook(owner);
                anotherBook.addAppointment(appt);
                AppointmentBook returnValue = MainActivity.appointmentBooks.put(owner, anotherBook);
                sendSnackbarMessage(view, "anotherBook printed value:" + anotherBook + "\nput return value:" + returnValue );
*/


                if (MainActivity.enterAppointmentToBook(owner, appt)) {
                    sendSnackbarMessage(view, "Appointment successfully entered for " + owner + ":\n" + appt.prettyPrint());
                } else {
                    sendSnackbarMessage(view, ERROR_ADDING_APPOINTMENT);
                }

                ownerTestVariable = owner;         // Testing variable
                editTextDescription.setText("");

            }
        }); // END onClickListener

        // Testing button
        binding.fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppointmentBook aBook = MainActivity.appointmentBooks.get(ownerTestVariable);
                String str;

                if (aBook != null) {
                    sendSnackbarMessage(view, "Appointments successfully retrieved for " + MainActivity.prettyPrintToString(aBook));
                } else {
                    sendSnackbarMessage(view, "Could not retrieve an appointment book for " + ownerTestVariable + ".");
                }
            }
        }); // END onClickListener


    } // END onViewCreated()

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void sendSnackbarMessage(View view, String message) {
        Snackbar sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        sb.setAction("Action", null);
        sb.setAnchorView(R.id.add_edittext_endDateTime);
        View sbView = sb.getView();
        TextView sbTextView = (TextView) sbView.findViewById(R.id.snackbar_text);
        sbTextView.setMaxLines(5);
        sb.show();
    }

    public static boolean parseDateTime(String str, Date date) { // date is returned or null if false
        // ^ Gets the text in the TextEdit boxes as the strings str
        String [] dateTime = str.trim().split("\\s+");

        if (!validateDate(dateTime[0])) { // The date was malformatted
            return false;
        }
        date = Appointment.parseStringToDate(str);
        if (date == null) {
            return false;
        }
        return true;
    }

    public static boolean validateDate(String str) {
        int strLength = str.length();
        if (strLength < 8) {
            return false;
        }

        if (str.contains("/")) {
            String [] splitDate = str.split("/");
            if (splitDate.length == 3) {
                if (((splitDate[0].length() == 1) || (splitDate[0].length() == 2))
                        && ((splitDate[1].length() == 1) || (splitDate[1].length() == 2))
                        && (splitDate[2].length() == 4)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {  // The string given for a date doesn't have the 3 arguments required: month, date, and year
                return false;
            }
        }
        return false;
    }



} // End of AddFragment



/*


// Fully working snackbar:

                    Snackbar sb = Snackbar.make(view, "ERROR: The start date and time have not been entered correctly.\nPlease enter them in the format MM/DD/YYYY HH:MM am/pm.", Snackbar.LENGTH_LONG);
                    sb.setAction("Action", null);
                    sb.setAnchorView(R.id.add_edittext_endDateTime);
                    View sbView = sb.getView();
                    TextView sbTextView = (TextView) sbView.findViewById(R.id.snackbar_text);
                    sbTextView.setMaxLines(5);
                    sb.show();





// Broken implementation
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);

                    sb.show();




// Access mainActivity variables test code:

        MainActivity.mainInt += 5;
        String parsedInt = Integer.toString(MainActivity.mainInt);

// Proof of concept for checks and balances:

    EditText editTextOwner;
        editTextOwner = view.findViewById(R.id.add_edittext_owner);
                String str;
                if (editTextOwner != null) {
                    Editable e = editTextOwner.getText();
                    if (e != null) {
                        str = e.toString();
                    } else {
                        str = "getText did not return a Char sequence";
                    }
                } else {
                    str = "Could not locate the EditText object for owner;";
                }


// Code that creates a popup message for the Button with id fab2

        binding.fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This button was pressed ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

// Original "NEXT" button from the First Fragment:
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_AddFragment_to_FirstFragment);
            }
            // ^ Here is where the next button magic is that takes you to the second fragment
        });



*/
