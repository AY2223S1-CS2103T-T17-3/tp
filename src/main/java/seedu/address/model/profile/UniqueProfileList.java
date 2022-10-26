package seedu.address.model.profile;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.profile.exceptions.DuplicateProfileException;
import seedu.address.model.profile.exceptions.ProfileNotFoundException;

/**
 * A list of profiles that enforces uniqueness between its elements and does not allow nulls.
 * A profile is unique by comparing using {@code Profile#isSameProfile(Profile)}. As such, adding and updating of
 * profiles uses Profile#isSameProfile(Profile) for equality so as to ensure that the profile being added or updated is
 * unique in terms of identity in the UniqueProfileList. However, removal of a profile uses Profile#equals(Object) so
 * as to ensure that the profile with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Profile#isSameProfile(Profile)
 */
public class UniqueProfileList implements Iterable<Profile> {

    private final ObservableList<Profile> internalList = FXCollections.observableArrayList();
    private final ObservableList<Profile> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final ObservableList<Profile> unmodifiableSortedList = internalUnmodifiableList.sorted(
            new Comparator<Profile>() {
                @Override
                public int compare(Profile p1, Profile p2) {
                    return p1.compareTo(p2);
                }
            });
    /**
     * Returns true if the list contains an equivalent profile as the given argument.
     */
    public boolean contains(Profile toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProfile);
    }

    /**
     * Adds a profile to the list.
     * The profile must not already exist in the list.
     */
    public void add(Profile toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProfileException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the profile {@code target} in the list with {@code editedProfile}.
     * {@code target} must exist in the list.
     * The profile identity of {@code editedProfile} must not be the same as another existing profile in the list.
     */
    public void setProfile(Profile target, Profile editedProfile) {
        requireAllNonNull(target, editedProfile);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProfileNotFoundException();
        }

        if (!target.isSameProfile(editedProfile) && contains(editedProfile)) {
            throw new DuplicateProfileException();
        }

        internalList.set(index, editedProfile);
    }

    /**
     * Removes the equivalent profile from the list.
     * The profile must exist in the list.
     */
    public void remove(Profile toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProfileNotFoundException();
        }
    }

    /**
     * Adds events in {@code eventsToAdd} to the given profile.
     * The profile must exist in the list.
     */
    public void addEventsAttending(Profile profile, List<Event> eventsToAdd) {
        requireAllNonNull(profile, eventsToAdd);

        int index = internalList.indexOf(profile);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        internalList.get(index).addEventsAttending(eventsToAdd);
    }

    /**
     * Adds profiles in {@code profilesToAdd} to the given event.
     * The event must exist in the list.
     */
    public void addEventAttendees(Event event, List<Profile> profilesToAdd) {
        requireAllNonNull(event, profilesToAdd);

        int index = internalList.indexOf(event);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        //internalList.get(index).addAttendees(profilesToAdd);
    }

    /**
     * Adds event {@code event} to the profiles in list of profiles {@code profilesToAddEventTo}.
     * The event must exist in the list.
     */
    public void addEventToAttendees(Event event, List<Profile> profilesToAddEventTo) {
        requireAllNonNull(event, profilesToAddEventTo);

        int index = internalList.indexOf(event);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        //Event e = internalList.get(index);

    }

    public void setProfiles(UniqueProfileList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code profiles}.
     * {@code profiles} must not contain duplicate profiles.
     */
    public void setProfiles(List<Profile> profiles) {
        requireAllNonNull(profiles);
        if (!profilesAreUnique(profiles)) {
            throw new DuplicateProfileException();
        }

        internalList.setAll(profiles);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Profile> asUnmodifiableObservableList() {
        return unmodifiableSortedList;
    }

    @Override
    public Iterator<Profile> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProfileList // instanceof handles nulls
                        && internalList.equals(((UniqueProfileList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code profiles} contains only unique profiles.
     */
    private boolean profilesAreUnique(List<Profile> profiles) {
        for (int i = 0; i < profiles.size() - 1; i++) {
            for (int j = i + 1; j < profiles.size(); j++) {
                if (profiles.get(i).isSameProfile(profiles.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
