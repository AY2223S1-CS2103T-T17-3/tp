package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.profile.Profile;

/**
 * A utility class containing a list of {@code Profile} objects to be used in tests.
 */
public class TypicalProfiles {

    public static final Profile ALICE = new ProfileBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withPhone("94351253").build();
    public static final Profile BENSON = new ProfileBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432").build();
    public static final Profile CARL = new ProfileBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").build();
    public static final Profile DANIEL = new ProfileBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").build();
    public static final Profile ELLE = new ProfileBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").build();
    public static final Profile FIONA = new ProfileBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").build();
    public static final Profile GEORGE = new ProfileBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Profile HOON = new ProfileBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").build();
    public static final Profile IDA = new ProfileBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").build();

    // Manually added - Profile's details found in {@code CommandTestUtil}
    public static final Profile AMY = new ProfileBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).build();
    public static final Profile BOB = new ProfileBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).build();


    private TypicalProfiles() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical profiles.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Profile profile : getTypicalProfiles()) {
            ab.addProfile(profile);
        }
        return ab;
    }

    public static List<Profile> getTypicalProfiles() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}