package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.profile.Profile;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Profile> filteredProfiles;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredProfiles = new FilteredList<>(this.addressBook.getProfileList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //========== Profiles ====================================================================================

    @Override
    public boolean hasEmail(Profile profile) {
        requireNonNull(profile);
        return addressBook.hasEmail(profile);
    }

    @Override
    public boolean hasPhone(Profile profile) {
        requireNonNull(profile);
        return addressBook.hasPhone(profile);
    }

    @Override
    public boolean hasTelegram(Profile profile) {
        requireNonNull(profile);
        return addressBook.hasTelegram(profile);
    }

    @Override
    public void deleteProfile(Profile target) {
        removeProfileFromEventsAttending(target, target.getEventsToAttend().getEventsList());
        addressBook.removeProfile(target);
    }

    @Override
    public void removeProfileFromEventsAttending(Profile target, List<Event> eventsToEdit) {
        requireAllNonNull(target, eventsToEdit);
        addressBook.removeProfileFromEventsAttending(target, eventsToEdit);
    }

    @Override
    public void addProfile(Profile profile) {
        addressBook.addProfile(profile);
        updateFilteredProfileList(PREDICATE_SHOW_ALL_PROFILES);
    }

    @Override
    public void setProfile(Profile target, Profile editedProfile) {
        requireAllNonNull(target, editedProfile);
        setProfileForEventsAttending(target, editedProfile, target.getEventsToAttend().getEventsList());
        addressBook.setProfile(target, editedProfile);
    }

    @Override
    public void setProfileForEventsAttending(Profile target, Profile editedProfile, List<Event> eventsToSet) {
        requireAllNonNull(target, editedProfile, eventsToSet);
        addressBook.setProfileForEventsAttending(target, editedProfile, eventsToSet);
    }

    //========== Events ======================================================================================

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        removeEventsFromAttendeesList(target, target.getAttendees().getAttendeesList());
        addressBook.removeEvent(target);
    }

    @Override
    public void removeEventsFromAttendeesList(Event target, List<Profile> profilesToEdit) {
        requireAllNonNull(target, profilesToEdit);
        addressBook.removeEventsFromAttendeesList(target, profilesToEdit);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        setEventForAttendees(target, editedEvent, target.getAttendees().getAttendeesList());
        addressBook.setEvent(target, editedEvent);
    }

    @Override
    public void setEventForAttendees(Event target, Event editedEvent, List<Profile> profilesToEdit) {
        requireAllNonNull(target, editedEvent, profilesToEdit);
        addressBook.setEventForAttendees(target, editedEvent, profilesToEdit);
    }

    @Override
    public void addEventAttendees(Event event, List<Profile> profilesToAdd) {
        requireAllNonNull(event, profilesToAdd);
        addEventToAttendees(event, profilesToAdd);
        addressBook.addEventAttendees(event, profilesToAdd);
    }

    @Override
    public void deleteEventAttendees(Event event, List<Profile> profilesToDelete) {
        requireAllNonNull(event, profilesToDelete);
        addressBook.deleteEventAttendees(event, profilesToDelete);
    }

    @Override
     public void addEventToAttendees(Event event, List<Profile> profilesToAddEventTo) {
        requireAllNonNull(event, profilesToAddEventTo);
        addressBook.addEventToAttendees(event, profilesToAddEventTo);
    }

    //=========== Filtered Profile List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Profile} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Profile> getFilteredProfileList() {
        return filteredProfiles;
    }

    @Override
    public void updateFilteredProfileList(Predicate<Profile> predicate) {
        requireNonNull(predicate);
        filteredProfiles.setPredicate(predicate);
    }

    //=========== Filtered Event List Accessors =============================================================

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredProfiles.equals(other.filteredProfiles)
                && filteredEvents.equals(other.filteredEvents);
    }

}
