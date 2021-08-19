package edu.pdx.cs410J.jpersson;

import java.util.Date;

public class ParseUserInput {


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




}
