package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.TitleContainsKeywordsPredicate;

import java.util.function.Predicate;

/**
 * Finds and lists all events in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindEventCommand extends EventCommand {

    public static final String COMMAND_OPTION = "f";
    public static final String MESSAGE_EVENT_LISTED_OVERVIEW = "1 event listed!";
    public static final String MESSAGE_NO_MATCH = "There are no matching results!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_OPTION + COMMAND_OPTION
            + ": Finds all events whose start dates contain any of "
            + "the specified dates (if dates are provided) or events whose title "
            + "contains any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_OPTION + COMMAND_OPTION + " alice bob charlie";

    public static final String MESSAGE_HELP = "Finds events matching the dates or keywords. "
            + "Keywords are case-insensitive and will return partial matches.\n"
            + "Format: " + COMMAND_WORD + " " + PREFIX_OPTION + COMMAND_OPTION + " KEYWORDS_OR_DATE [MORE]";

    private final Predicate<Event> predicate;

    public FindEventCommand(Predicate<Event> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        int size = model.getFilteredEventList().size();
        switch (size) {
        case 0:
            return new CommandResult(MESSAGE_NO_MATCH);
        case 1:
            return new CommandResult(MESSAGE_EVENT_LISTED_OVERVIEW);
        default:
            return new CommandResult(String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, size));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}