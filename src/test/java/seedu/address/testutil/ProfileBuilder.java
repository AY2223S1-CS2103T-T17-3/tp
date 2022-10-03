package seedu.address.testutil;

import seedu.address.model.profile.Email;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Phone;
import seedu.address.model.profile.Profile;

/**
 * A utility class to help with building Profile objects.
 */
public class ProfileBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;

    /**
     * Creates a {@code ProfileBuilder} with the default details.
     */
    public ProfileBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
    }

    /**
     * Initializes the ProfileBuilder with the data of {@code profileToCopy}.
     */
    public ProfileBuilder(Profile profileToCopy) {
        name = profileToCopy.getName();
        phone = profileToCopy.getPhone();
        email = profileToCopy.getEmail();
    }

    /**
     * Sets the {@code Name} of the {@code Profile} that we are building.
     */
    public ProfileBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Profile} that we are building.
     */
    public ProfileBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Profile} that we are building.
     */
    public ProfileBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Profile build() {
        return new Profile(name, phone, email);
    }

}