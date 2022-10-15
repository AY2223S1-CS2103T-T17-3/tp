package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;


/**
 * Edits a given appointment's details.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String DESCRIPTOR_WORD = "appts";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment for %1$s: %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + DESCRIPTOR_WORD
            + ": Edits the appointment details of the person identified "
            + "by the patientIndex number used in the displayed person list and the appointmentIndex. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REASON + "REASON] "
            + "[" + PREFIX_DATE + "DATE] "
            + "Example: " + COMMAND_WORD + " " + DESCRIPTOR_WORD + " 1 "
            + PREFIX_REASON + "Sore Throat "
            + PREFIX_DATE + "2022-10-12 16:30";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "This person already booked an appointment at this time";
    private final EditAppointmentDescriptor editAppointmentDescriptor;
    private final Index index;

    /**
     * Creates an EditAppointmentCommand with the given appointment index,
     * and the editAppointmentDescriptor.
     *
     * @param indexOfAppointment The index of the appointment to edit.
     * @param editAppointmentDescriptor The descriptor that contains the edited details.
     */
    public EditAppointmentCommand(Index indexOfAppointment,
                                  EditAppointmentDescriptor editAppointmentDescriptor) {
        this.index = indexOfAppointment;
        this.editAppointmentDescriptor = editAppointmentDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Appointment> appointmentList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= appointmentList.size()) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment targetAppointment = appointmentList.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(targetAppointment, editAppointmentDescriptor);
        if (hasSameTime(appointmentList, targetAppointment, editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.setAppointment(targetAppointment, editedAppointment);
        List<Appointment> currentAppts = editedAppointment.getPatient().getAppointments();
        currentAppts.set(currentAppts.indexOf(targetAppointment), editedAppointment);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                editedAppointment.getPatient().getName(), editedAppointment));
    }

    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        String reason = editAppointmentDescriptor.getReason().orElse(appointmentToEdit.getReason());
        LocalDateTime dateTime = editAppointmentDescriptor.getDateTime().orElse(appointmentToEdit.getDateTime());
        Appointment editedAppointment = new Appointment(reason, dateTime, appointmentToEdit.isMarked());
        editedAppointment.setPatient(appointmentToEdit.getPatient());
        return editedAppointment;
    }

    private boolean hasSameTime(List<Appointment> appointments, Appointment originalAppointment,
                                Appointment appointmentToCheck) {
        List<Appointment> appointmentsToCheck = new ArrayList<>(appointments);
        appointmentsToCheck.remove(originalAppointment);
        return appointmentsToCheck.stream().anyMatch(x -> x.isSameTime(appointmentToCheck));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand)) {
            return false;
        }

        // state check
        EditAppointmentCommand e = (EditAppointmentCommand) other;
        return index.equals(e.index) && editAppointmentDescriptor.equals(e.editAppointmentDescriptor);
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private String reason;
        private LocalDateTime dateTime;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         *
         * @param toCopy The editAppointmentDescriptor to copy the details.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setReason(toCopy.reason);
            setDateTime(toCopy.dateTime);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(reason, dateTime);
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Optional<String> getReason() {
            return Optional.ofNullable(reason);
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;
            return getReason().equals(e.getReason()) && getDateTime().equals(e.getDateTime());
        }
    }
}
