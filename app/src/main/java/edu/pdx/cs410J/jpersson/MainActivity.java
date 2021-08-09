package edu.pdx.cs410J.jpersson;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.pdx.cs410J.jpersson.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    public ActivityMainBinding binding;
    public static HashMap<String, AppointmentBook> appointmentBooks = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


// PROJECT 5 DATA PROCESSING ALGORITHMS:

    public static String prettyPrintToString(AppointmentBook book) {
        if (book != null) {
            Vector<Appointment> appts = book.getAppointments();
            if (0 < appts.size()) {
                String allAppointments = "";
                allAppointments = allAppointments + book.getOwnerName() + "\n";
                for (Appointment appt: appts) {
                    allAppointments = allAppointments + appt.prettyPrint() + "\n";
                }
                return allAppointments;
            }
            return null;
        }
        return null;
    }

    public static boolean enterAppointmentToBook(String owner, Appointment appt) {
        if (!owner.equals("") && owner != null && appt != null) {
            AppointmentBook aBook = appointmentBooks.get(owner);
            if (aBook != null) { // Add to the existing book
                aBook.addAppointment(appt);
                return true;
            } else { // Create a new book and add it
                AppointmentBook anotherBook = new AppointmentBook(owner);
                anotherBook.addAppointment(appt);
                appointmentBooks.put(owner, anotherBook);
                if (appointmentBooks.get(owner) == anotherBook) {
                    // ^ This put returns null when you create a new key (a book was not already in the hashmap for this owner)
                    return true;
                }
                return false;
            }
        }
        return false;
    }

}// END mainActivity class