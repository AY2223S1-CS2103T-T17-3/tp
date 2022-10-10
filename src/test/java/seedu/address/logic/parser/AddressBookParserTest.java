package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BookCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.commands.EditPatientCommand;
import seedu.address.logic.commands.EditPatientCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentUtil;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws ParseException {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws ParseException {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_mark() throws ParseException {
        MarkCommand command = (MarkCommand) parser.parseCommand(
                MarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + INDEX_SECOND_APPOINTMENT.getOneBased());
        assertEquals(new MarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_APPOINTMENT), command);
    }

    @Test
    public void parseCommand_unmark() throws ParseException {
        UnmarkCommand command = (UnmarkCommand) parser.parseCommand(
                UnmarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + INDEX_SECOND_APPOINTMENT.getOneBased());
        assertEquals(new UnmarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_APPOINTMENT), command);
    }

    @Test
    public void parseCommand_delete() throws ParseException {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_book() throws ParseException {
        Appointment appointment = new Appointment("Cough", "2022-12-16 17:30", false);
        String str = AppointmentUtil.getBookCommand(appointment);
        BookCommand command = (BookCommand) parser.parseCommand(str);
        assertEquals(new BookCommand(INDEX_FIRST_PERSON, appointment), command);
    }

    @Test
    public void parseCommand_editPatient() throws ParseException {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPatientCommand command = (EditPatientCommand) parser.parseCommand(EditPatientCommand.COMMAND_WORD
                + " " + EditPatientCommand.DESCRIPTOR_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPatientCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editAppointment() throws ParseException {
        Appointment appointment = new Appointment("Cough", "2022-12-16 17:30", false);
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(appointment).build();
        EditAppointmentCommand command = (EditAppointmentCommand)
                parser.parseCommand(EditAppointmentCommand.COMMAND_WORD
                + " " + EditAppointmentCommand.DESCRIPTOR_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + INDEX_FIRST_APPOINTMENT.getOneBased()
                + " " + AppointmentUtil.getEditAppointmentDescriptorDetails(descriptor));
        assertEquals(new EditAppointmentCommand(INDEX_FIRST_PERSON, INDEX_FIRST_APPOINTMENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws ParseException {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws ParseException {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws ParseException {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws ParseException {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " patients") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " appts") instanceof ListCommand);
    }

    @Test
    public void parseCommand_exitCommandCaseInsensitive_success() throws ParseException {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.toLowerCase()) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.toUpperCase()) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
