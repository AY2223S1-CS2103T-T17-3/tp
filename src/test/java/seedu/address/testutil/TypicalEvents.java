package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event PRESENTATION = new EventBuilder().withTitle("Discuss presentation")
            .withStartDateTime("11/10/2022 09:00").withEndDateTime("11/10/2022 10:00")
            .withTags("CS2103T").build();
    public static final Event PRACTICE = new EventBuilder().withTitle("Practice")
            .withStartDateTime("11/10/2022 13:00").withEndDateTime("11/10/2022 14:00")
            .withTags("CCA").build();
    public static final Event PROBLEM_SET = new EventBuilder().withTitle("Finish problem set")
            .withStartDateTime("12/10/2022 16:00").withEndDateTime("12/10/2022 17:00")
            .withTags("CS2109S").build();
    public static final Event DINNER = new EventBuilder().withTitle("Formal dinner")
            .withStartDateTime("12/10/2022 19:00").withEndDateTime("12/10/2022 23:00")
            .withTags("RC").build();

    private TypicalEvents() {} // prevents instantiation

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(PRESENTATION, PRACTICE, PROBLEM_SET, DINNER));
    }

}
