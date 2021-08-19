package edu.pdx.cs410J.jpersson;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;


/**
 * AppointmentBook class
 * An object of this type holds all the appointments a given ownerFromFile has
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment> {
    final public String owner;
    public Vector<Appointment> appointments = new Vector<Appointment>();

    AppointmentBook(){
        owner = "dummyOwnerName";
    }

    /**
     * Overloaded constructor that specifies the name of the ownerFromFile
     * @param owner
     */
    AppointmentBook(String owner){
        this.owner = owner;
    }

    /**
     * Getter for returning a collection containing all of the owners appointments
     * @return
     */
    public Vector<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Adds an appointment to an instantiated address book
     * @param var1
     */
    public void addAppointment(Appointment var1) {
        appointments.add(var1);
        Collections.sort(appointments);
    }

    /**
     * Getter for the name of the ownerFromFile of the address book
     * @return
     */
    public String getOwnerName() {
        return owner;
    }

    public static boolean addAppointmentsWithinRangeToBook(AppointmentBook sourceBook,
                                                           Date startDateTime, Date endDateTime, AppointmentBook destinationBook) {
        if (sourceBook != null) {
            Vector<Appointment> appointments = sourceBook.getAppointments();
            if(0 < appointments.size()) {
                Date beginDate;
                for (Appointment appt: appointments) {
                    beginDate = appt.beginDate;
                    if ( (0 <= beginDate.compareTo(startDateTime)) && (beginDate.compareTo(endDateTime) <=0) ) {
                        destinationBook.addAppointment(appt);
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

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


}
