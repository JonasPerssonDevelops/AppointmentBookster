package edu.pdx.cs410J.jpersson;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.pdx.cs410J.jpersson.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    public ActivityMainBinding binding;
    // Project 5 variables:
    public static Map<String, AppointmentBook> appointmentBooks = new HashMap<String, AppointmentBook>();
    public static File appDataPath;
    public static String booksObjectFilename = "aBooksJP.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Context currContext = getApplicationContext();
        appDataPath = currContext.getFilesDir();

        readObjectFromFile(appDataPath, booksObjectFilename);

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
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

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
                writeObjectToFile(appointmentBooks, appDataPath, booksObjectFilename);
                return true;
            } else { // Create a new book and add it
                AppointmentBook anotherBook = new AppointmentBook(owner);
                anotherBook.addAppointment(appt);
                appointmentBooks.put(owner, anotherBook);
                // ^ This put returns null when you create a new key (a book was not already in the hashmap for this owner)
                if (appointmentBooks.get(owner) == anotherBook) {
                    writeObjectToFile(appointmentBooks, appDataPath, booksObjectFilename);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static String writeObjectToFile(Object obj, File filePath, String fileName) {

        if (filePath != null && fileName != null) { // Check if a valid path and filename were provided

            File fullPath = new File(filePath, fileName);

            try (FileOutputStream fileOStream = new FileOutputStream(fullPath);
                    ObjectOutputStream objectOStream = new ObjectOutputStream(fileOStream)) {
                objectOStream.writeObject(obj);
            } catch (FileNotFoundException e) {
                return "Could not write the file with the path given.";
            } catch (IOException e) {
                return "General IO Exception.";
            }
            return ( "Wrote to the file.\nappDataPath.getPath():" + filePath.getPath() );
        }

        return ("getExternalFilesDir(null) returned null\n appDataPath.getPath():" + filePath.getPath());

    }


    public static String readObjectFromFile(File filePath, String fileName) {

        if (filePath != null && fileName != null) { // Check if a valid path and filename were provided

            File fullPath = new File(filePath, fileName);

            try(FileInputStream fileIStream = new FileInputStream(fullPath);
                    ObjectInputStream objectIStream = new ObjectInputStream(fileIStream)) {
               appointmentBooks = (Map<String, AppointmentBook>) objectIStream.readObject();
            } catch (FileNotFoundException e) {
                return "File could not be found.";
            } catch (IOException e) {
                return "There was an IO exception";
            } catch (ClassNotFoundException e) {
                return "Could not identify the object";
            }
            return "The object was read from the file.";
        }
        return "No filepath or file name given in the parameters.";
    }


}// END mainActivity class



/*


// Working version:


    public static String writeAppointmentBooksToFile(HashMap<String, AppointmentBook> obj) {

        if (appDataPath != null) { // We got the path to this apps data folder
            File appFullPath = new File(appDataPath, "aBooksJP.ser");

            try (FileOutputStream fileOStream = new FileOutputStream(appFullPath)) {
                ObjectOutputStream objectOStream = new ObjectOutputStream(fileOStream);
                objectOStream.writeObject(appointmentBooks);
                objectOStream.close();
            } catch (FileNotFoundException e) {
                return "Could not write the file with the .";
            } catch (IOException e) {
                return "IO Exception.";
            }
            return ( "Wrote to the file.\nappDataPath.getPath():" + appDataPath.getPath() );
        }
        return ("getExternalFilesDir(null) returned null\n appDataPath.getPath():" + appDataPath.getPath());

    }






// Gets the internal data path:

        Context currContext = getApplicationContext();
        appDataPath = currContext.getFilesDir();

//Gets the external data path:

        // appDataPath = this.getExternalFilesDir(null);





 */




















