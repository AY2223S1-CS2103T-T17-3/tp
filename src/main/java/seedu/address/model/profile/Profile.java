package seedu.address.model.profile;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Represents a Profile in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Profile implements Comparable<Profile> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Telegram telegram;
    private final Set<Tag> tags = new HashSet<>();
    private final EventsAttending eventsToAttend;

    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, Phone phone, Email email, Telegram telegram, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.tags.addAll(tags);
        eventsToAttend = new EventsAttending();
    }

    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, Phone phone, Email email, Telegram telegram, Set<Tag> tags, EventsAttending eventsToAttend) {
        requireAllNonNull(name, phone, email, tags, eventsToAttend);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.tags.addAll(tags);
        this.eventsToAttend = eventsToAttend;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public EventsAttending getEventsToAttend() {
        return eventsToAttend;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both profiles have the same name.
     * This defines a weaker notion of equality between two profiles.
     */
    public boolean isSameProfile(Profile otherProfile) {
        if (otherProfile == this) {
            return true;
        }

        return otherProfile != null
                && otherProfile.getName().equals(getName());
    }

    /**
     * Returns true if both profiles have the same identity and data fields.
     * This defines a stronger notion of equality between two profiles.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Profile)) {
            return false;
        }

        Profile otherProfile = (Profile) other;
        return otherProfile.getName().equals(getName())
                && otherProfile.getPhone().equals(getPhone())
                && otherProfile.getEmail().equals(getEmail())
                && otherProfile.getTelegram().equals(getTelegram())
                && otherProfile.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags);
    }

    @Override
    public int compareTo(Profile other) {
        return this.getName().compareTo(other.getName());
    }

    /**
     * Adds the events in {@code eventsToAdd} to the profile's list of eventsAttending if
     * they have not already been added.
     */
    public void addEventsAttending(List<Event> eventsToAdd) {
        requireNonNull(eventsToAdd);

        eventsToAdd.forEach(event -> {
            if (!eventsToAttend.hasEventAttending(event)) {
                eventsToAttend.add(event);
            }
        });
    }

    /**
     * Removes the events in {@code eventsToRemove} from the profile's list of eventsAttending if
     * they exit.
     */
    public void removeEventsAttending(List<Event> eventsToRemove) {
        requireNonNull(eventsToRemove);

        eventsToRemove.forEach(event -> {
            if (eventsToAttend.hasEventAttending(event)) {
                eventsToAttend.remove(event);
            }
        });
    }

    /**
     * Returns true if the specified event is in the profile's list of eventsAttending.
     */
    public boolean hasEventAttending(Event event) {
        return eventsToAttend.hasEventAttending(event);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail());

        if (!getTelegram().isEmpty()) {
            builder.append("; Telegram: ")
                    .append(getTelegram());
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
