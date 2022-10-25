package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.profile.Profile;
import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final Title title;
    private final DateTime startDateTime;
    private final DateTime endDateTime;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Attendees attendees;

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, DateTime startDateTime, DateTime endDateTime, Set<Tag> tags) {
        requireAllNonNull(title, startDateTime, endDateTime, tags);
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.tags.addAll(tags);
        attendees = new Attendees();
    }

    public Title getTitle() {
        return title;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Attendees getAttendees() {
        return attendees;
    }

    /**
     * Adds the profiles in {@code profilesToAdd} to the event's list of attendees if
     * they have not already been added.
     */
    public void addAttendees(List<Profile> profilesToAdd) {
        requireNonNull(profilesToAdd);

        profilesToAdd.forEach(profile -> {
            if (!attendees.hasAttendee(profile)) {
                attendees.add(profile);
            }
        });
    }

    /**
     * Returns true if the specified profile is in the event's list of attendees.
     */
    public boolean hasAttendee(Profile profile) {
        return attendees.hasAttendee(profile);
    }

    /**
     * Returns true if both events have the same title, start, and end times.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getTitle().equals(getTitle())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime());
    }

    /**
     * Returns true if both event have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return isSameEvent(otherEvent)
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, startDateTime, endDateTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title: ")
                .append(getTitle())
                .append("; Start: ")
                .append(getStartDateTime())
                .append("; End: ")
                .append(getEndDateTime());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        builder.append(System.lineSeparator())
                .append(attendees);

        return builder.toString();
    }
}
