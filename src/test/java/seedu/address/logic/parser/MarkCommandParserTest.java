package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkCommand;

class MarkCommandParserTest {

    private final MarkCommandParser parser = new MarkCommandParser();
    private final String parseExceptionInvalidCommandMessage =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);

    @Test
    public void parse_validArgs1_returnsMarkCommand() {
        assertParseSuccess(parser, "1", new MarkCommand(INDEX_FIRST_APPOINTMENT));
    }

    @Test
    public void parse_validArgs2_returnsMarkCommand() {
        assertParseSuccess(parser, "  3   ", new MarkCommand(INDEX_THIRD_APPOINTMENT));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, "", parseExceptionInvalidCommandMessage);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", parseExceptionInvalidCommandMessage);
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        assertParseFailure(parser, "1 2", parseExceptionInvalidCommandMessage);
    }
}