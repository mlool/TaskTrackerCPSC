package model.tasks;

import persistence.Writable;
import org.json.JSONObject;

import java.time.LocalDate;

// A day class that represents day in year, month, and day
public class Date implements Writable {
    private int year;
    private int month;
    private int day;

    // constructs a date which has the day that it has been constructed
    public Date() {
        this.year = LocalDate.now().getYear();
        this.month = LocalDate.now().getMonthValue();
        this.day = LocalDate.now().getDayOfMonth();
    }

    // constructs a date which has the specified date
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    // returns the year
    public int getYear() {
        return year;
    }

    // returns the month
    public int getMonthValue() {
        return month;
    }

    // returns the day
    public int getDayOfMonth() {
        return day;
    }


    // converts date to a json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("year", year);
        json.put("month", month);
        json.put("day", day);
        return json;
    }
}
