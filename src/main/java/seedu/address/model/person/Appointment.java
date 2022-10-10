package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;


/**
 * Represents the patient's appointments' details.
 */
public class Appointment {
    public static final String REASON_MESSAGE_CONSTRAINTS = "Reason should not be empty";
    public static final String DATE_MESSAGE_CONSTRAINTS = "Date should contain YYYY-MM-DD and HH:MM values";
    public static final DateTimeFormatter DATE_FORMATTER =
            new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    .appendOptional(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd")).toFormatter();

    public static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String reason;
    private final LocalDateTime dateTime;
    private boolean isMarked;
    private Person patient;

    private final DateTimeFormatter stringFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");


    /**
     * Creates an appointment object with the given reason, dateTime string, and status.
     *
     * @param reason The given reason for appointment.
     * @param dateTime The given time to book the appointment.
     * @param isMarked Status of the appointment.
     */
    public Appointment(String reason, String dateTime, boolean isMarked) {
        requireNonNull(reason);
        requireNonNull(dateTime);
        checkArgument(isValidReason(reason), REASON_MESSAGE_CONSTRAINTS);
        checkArgument(isValidDateTime(dateTime), DATE_MESSAGE_CONSTRAINTS);
        this.reason = reason;
        String str = String.join(" ", dateTime.split("\\s+", 2));
        this.dateTime = LocalDateTime.parse(str, DATE_FORMATTER);
        this.isMarked = isMarked;
    }

    /**
     * Creates an appointment object with the given reason, LocalDateTime dateTime, and status.
     *
     * @param reason The given reason for appointment.
     * @param dateTime The given time to book the appointment.
     * @param isMarked Status of the appointment.
     */
    public Appointment(String reason, LocalDateTime dateTime, boolean isMarked) {
        this.reason = reason;
        this.dateTime = dateTime;
        this.isMarked = isMarked;
    }

    /**
     * Checks whether the given appointment has the same time.
     *
     * @param other The given appointment.
     * @return The result of the equals test.
     */
    public boolean isSameTime(Appointment other) {
        return other.dateTime.equals(dateTime);
    }

    /**
     * Checks whether the given string is a valid reason.
     *
     * @param test The string to test.
     * @return The result of the equals test.
     */
    public static boolean isValidReason(String test) {
        return !test.equals("");
    }

    /**
     * Checks whether the given string is a valid DateTime.
     *
     * @param test The string to test.
     * @return The result of the LocalDateTime parse test.
     */
    public static boolean isValidDateTime(String test) {
        try {
            String str = String.join(" ", test.split("\\s+", 2));
            LocalDateTime.parse(str, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFormattedDateTime() {
        return dateTime.format(stringFormatter);
    }

    public String getReason() {
        return reason;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void mark() {
        this.isMarked = true;
    }

    public void unmark() {
        this.isMarked = false;
    }

    public void setPatient(Person patient) {
        this.patient = patient;
    }

    public Person getPatient() {
        return patient;
    }

    public String getPatientName() {
        return this.patient.getName().fullName;
    }

    public String getStatus() {
        return "[" + getStateIcon() + "]";
    }


    @Override
    public String toString() {
        return getStatus() + " " + getFormattedDateTime() + " for " + reason;
    }

    private String getStateIcon() {
        String markedIcon = "✅";
        String unmarkedIcon = "❌";
        return isMarked ? markedIcon : unmarkedIcon;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;

        return otherAppointment.patient.getName().equals(patient.getName())
                && otherAppointment.reason.equals(reason)
                && otherAppointment.dateTime.equals(dateTime)
                && (otherAppointment.isMarked == isMarked);
    }

    /**
     * Returns true if both appointments have the same reason, date, time and status.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment appointment) {
        return appointment.reason.equals(reason)
                && appointment.dateTime.equals(dateTime)
                && (appointment.isMarked == isMarked);
    }
}