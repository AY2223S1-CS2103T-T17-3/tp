package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.profile.Address;
import seedu.address.model.profile.Email;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Phone;
import seedu.address.model.profile.Profile;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Profile objects.
 */
public class ProfileBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code ProfileBuilder} with the default details.
     */
    public ProfileBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ProfileBuilder with the data of {@code profileToCopy}.
     */
    public ProfileBuilder(Profile profileToCopy) {
        name = profileToCopy.getName();
        phone = profileToCopy.getPhone();
        email = profileToCopy.getEmail();
        address = profileToCopy.getAddress();
        tags = new HashSet<>(profileToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Profile} that we are building.
     */
    public ProfileBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Profile} that we are building.
     */
    public ProfileBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Profile} that we are building.
     */
    public ProfileBuilder withAddress(String address) {
        this.address = new Address(address);
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
        return new Profile(name, phone, email, address, tags);
    }

}
