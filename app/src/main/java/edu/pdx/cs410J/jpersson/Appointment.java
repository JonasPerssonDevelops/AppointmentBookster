package edu.pdx.cs410J.jpersson;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *  An object of this class stores all the information about a single appointment
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment> {
    public String description;
    public String begin;
    public String end;
    public Date beginDate;
    public Date endDate;


    public Appointment() {

    }

    /**
     * Overloaded constructor for taking information about an appointment stored in String
     * format and storing it in the object
     * @param description - about the appointment
     * @param begin - begin date and time of the appointment
     * @param end - end date and time of the appointment
     * @param newBeginDate - begin date and time of the appointment
     * @param newEndDate - end date and time of the appointment
     */
    public Appointment(String description, String begin, String end, Date newBeginDate, Date newEndDate) {
        this.description = description;
        this.begin = begin;
        this.end = end;
        beginDate = newBeginDate;
        endDate = newEndDate;
    }


    /**
     * Overloaded constructor for taking information about an appointment stored in String
     * format and storing it in the object
     * @param description - about the appointment
     * @param begin - begin date and time of the appointment
     * @param end - end date and time of the appointment
     */
    public Appointment(String description, String begin, String end) {
        this.description = description;
        this.begin = begin;
        this.end = end;
    }

    /**
     * Overloaded constructor for taking information about an appointment stored in String
     * format and storing it in the object
     * @param description - about the appointment
     * @param newBeginD - begin date and time of the appointment
     * @param newEndDate - end date and time of the appointment
     */
    public Appointment(String description, Date newBeginD, Date newEndDate) {
        this.description = description;
        beginDate = newBeginD;
        endDate = newEndDate;
    }

    public String prettyPrint() {
        if( description != null && begin != null && end != null ) {
            return (description + " from " + begin + " to " + end);
        }
        return null;
    }

    /**
     * Validates the appointment data passed. If the time and date information is not
     * in a valid format returns false. If in a valid format true.
     * @param newDescription description of the appointment
     * @param newBegin begin date and time for the appointment
     * @param newEnd end date and time for the appointment
     * @return true if the dates and times are given in the correct format, false otherwise
     */
    public boolean processAppointment(String newDescription, String newBegin, String newEnd) {

        description = newDescription;

        beginDate = Appointment.parseStringToDate(newBegin);
        if (beginDate == null) {
            return false;
        }
        endDate = Appointment.parseStringToDate(newEnd);
        if (endDate == null) {
            return false;
        }
        begin = newBegin;
        end = newEnd;

        return true;
    }


    /**
     * Method that implements the natural ordering of Appointments required by the
     * Comparable interface
     * @param appt The object that this will be compared to
     * @return The int value of the comparison
     */
    public int compareTo(Appointment appt) { // The comparison is in regards to this object / this
        int beginComparison = beginDate.compareTo(appt.beginDate);
        if (beginComparison == 0) { // Same begin day and time
            int endComparison = endDate.compareTo(appt.endDate);
            if (endComparison == 0) { // We need to compare the descriptions
                int descriptionComparison = description.compareTo(appt.description);
                if (descriptionComparison == 0) {
                    return 0;
                }
                return descriptionComparison;
            }
            return endComparison;
        }
        return beginComparison;

        // 0 if this and the appt date are equal
        // <0 if appt is greater than this date (I am less than this date passed in)
        // 0< if appt is less than this date
    }

    public static Date parseStringToDate(String s) {
        Date date;
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        try {
            date = formatter.parse(s);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }


    @Override
    public Date getBeginTime() {
        return beginDate;
    }

    @Override
    public Date getEndTime() {
        return endDate;
    }

    /**
     * Getter for begin date and time
     * @return the date and time as a string
     */
    @Override
    public String getBeginTimeString() {
        if (beginDate != null) {
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
            return dateFormatter.format(beginDate);
        }
        return begin;
    }

    /**
     * Getter for end date and time
     * @return the date and time as a string
     */
    @Override
    public String getEndTimeString() {
        if (endDate != null) {
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
            return dateFormatter.format(endDate);
        }
        return end;
    }

    /**
     * Getter for what the appointment is about
     * @return the description of the appointment
     */
    @Override
    public String getDescription() {
        return description;
    }


}

