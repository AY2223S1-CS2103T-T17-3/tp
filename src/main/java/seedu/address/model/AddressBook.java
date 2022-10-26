package seedu.address.model;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.UniqueProfileList;
import seedu.address.model.profile.exceptions.ProfileNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProfile comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueProfileList profiles;
    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        profiles = new UniqueProfileList();
        events = new UniqueEventList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Profiles in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the profile list with {@code profiles}.
     * {@code profiles} must not contain duplicate profiles.
     */
    public void setProfiles(List<Profile> profiles) {
        this.profiles.setProfiles(profiles);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setProfiles(newData.getProfileList());
        setEvents(newData.getEventList());
    }

    //// profile-level operations

    /**
     * Returns true if a profile with the same identity as {@code profile} exists in the address book.
     */
    public boolean hasProfile(Profile profile) {
        requireNonNull(profile);
        return profiles.contains(profile);
    }

    /**
     * Adds a profile to the address book.
     * The profile must not already exist in the address book.
     */
    public void addProfile(Profile p) {
        profiles.add(p);
    }

    /**
     * Replaces the given profile {@code target} in the list with {@code editedProfile}.
     * {@code target} must exist in the address book.
     * The profile identity of {@code editedProfile} must not be the same as an existing profile in the address book.
     */
    public void setProfile(Profile target, Profile editedProfile) {
        requireNonNull(editedProfile);

        profiles.setProfile(target, editedProfile);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProfile(Profile key) {
        profiles.remove(key);
    }

    /**
     * Deletes the profile {@code target} from given list of events {@code eventsToEdit}.
     * {@code target} must exist in the address book.
     * Events in {@code eventsToEdit} must also exist in the address book.
     */
    public void removeProfileFromEventsAttending(Profile target, List<Event> eventsToEdit) {
        requireAllNonNull(target, eventsToEdit);
        if (!profiles.contains(target)) {
            throw new ProfileNotFoundException();
        }
        events.removeProfileFromEventsAttending(target, eventsToEdit);
    }

    //// event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event p) {
        events.add(p);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as an existing event in the address book.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);
        events.setEvent(target, editedEvent);
    }

    /**
     * Updates the events {@code target} in list of profiles {@code profilesToSet} to the new edited event
     * {@code editedEvent}.
     */
    public void setEventForAttendees(Event target, Event editedEvent, List<Profile> profilesToEdit) {
        requireAllNonNull(target, editedEvent, profilesToEdit);
        profiles.setEventForAttendees(target, editedEvent, profilesToEdit);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    /**
     * Deletes the event {@code target} from given list of profiles {@code profilesToEdit}.
     * {@code target} must exist in the address book.
     * Profiles in {@code profilesToEdit} must also exist in the address book.
     */
    public void removeEventsFromAttendeesList(Event target, List<Profile> profilesToEdit) {
        requireAllNonNull(target, profilesToEdit);
        if (!events.contains(target)) {
            throw new EventNotFoundException();
        }
        profiles.removeEventsFromAttendeesList(target, profilesToEdit);
    }

    /**
     * Adds profiles in {@code profilesToAdd} to the given event.
     * {@code event} must exist in the address book.
     * Profiles in {@profilesToAdd} must also exist in the address book.
     */
    public void addEventAttendees(Event event, List<Profile> profilesToAdd) {
        requireAllNonNull(event, profilesToAdd);
        events.addEventAttendees(event, profilesToAdd);
    }

    /**
     * Updates the profiles {@code target} in list of events {@code eventsToSet} to the new edited profile
     * {@code editedProfile}.
     */
    public void setProfileForEventsAttending(Profile target, Profile editedProfile, List<Event> eventsToSet) {
        requireAllNonNull(target, editedProfile, eventsToSet);
        if (!profiles.contains(target)) {
            throw new ProfileNotFoundException();
        }

        events.setProfileForEventsAttending(target, editedProfile, eventsToSet);
    }

    /**
     * Adds event {@code event} to the profiles in list of profiles {@code profilesToAddEventTo}.
     * {@code event} must exist in the address book.
     * Profiles in {@profilesToAddEventTo} must also exist in the address book.
     */
    public void addEventToAttendees(Event event, List<Profile> profilesToAddEventTo) {
        requireAllNonNull(event, profilesToAddEventTo);
        if (!events.contains(event)) {
            throw new EventNotFoundException();
        }
        profiles.addEventToAttendees(event, profilesToAddEventTo);
    }

    /**
     * Adds events in {@code eventsToAdd} to the given profile.
     * {@code profile} must exist in the address book.
     * Events in {@eventsToAdd} must also exist in the address book.
     */
    public void addEventsAttending(Profile profile, List<Event> eventsToAdd) {
        requireAllNonNull(profile, eventsToAdd);
        profiles.addEventsAttending(profile, eventsToAdd);
    }

    //// util methods

    @Override
    public String toString() {
        return profiles.asUnmodifiableObservableList().size() + " profiles "
                + "and "
                + events.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<Profile> getProfileList() {
        return profiles.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && profiles.equals(((AddressBook) other).profiles)
                && events.equals(((AddressBook) other).events));
    }

    @Override
    public int hashCode() {
        return hash(profiles, events);
    }
}
