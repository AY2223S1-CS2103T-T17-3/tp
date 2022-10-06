package seedu.address.logic.commands.profile;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROFILES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Email;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Phone;
import seedu.address.model.profile.Profile;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing profile in the address book.
 */
public class EditProfileCommand extends ProfileCommand {

    public static final String COMMAND_OPTION = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_OPTION + COMMAND_OPTION
            + ": Edits the details of the profile identified "
            + "by the index number used in the displayed profile list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_OPTION + COMMAND_OPTION + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_TAG + "CS2103T";

    public static final String MESSAGE_EDIT_PROFILE_SUCCESS = "Edited Profile: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROFILE = "This profile already exists in the address book.";

    private final Index index;
    private final EditProfileDescriptor editProfileDescriptor;

    /**
     * @param index of the profile in the filtered profile list to edit
     * @param editProfileDescriptor details to edit the profile with
     */
    public EditProfileCommand(Index index, EditProfileDescriptor editProfileDescriptor) {
        requireNonNull(index);
        requireNonNull(editProfileDescriptor);

        this.index = index;
        this.editProfileDescriptor = new EditProfileDescriptor(editProfileDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Profile> lastShownList = model.getFilteredProfileList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROFILE_DISPLAYED_INDEX);
        }

        Profile profileToEdit = lastShownList.get(index.getZeroBased());
        Profile editedProfile = createEditedProfile(profileToEdit, editProfileDescriptor);

        if (!profileToEdit.isSameProfile(editedProfile) && model.hasProfile(editedProfile)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROFILE);
        }

        model.setProfile(profileToEdit, editedProfile);
        model.updateFilteredProfileList(PREDICATE_SHOW_ALL_PROFILES);
        return new CommandResult(String.format(MESSAGE_EDIT_PROFILE_SUCCESS, editedProfile));
    }

    /**
     * Creates and returns a {@code Profile} with the details of {@code profileToEdit}
     * edited with {@code editProfileDescriptor}.
     */
    private static Profile createEditedProfile(Profile profileToEdit, EditProfileDescriptor editProfileDescriptor) {
        assert profileToEdit != null;

        Name updatedName = editProfileDescriptor.getName().orElse(profileToEdit.getName());
        Phone updatedPhone = editProfileDescriptor.getPhone().orElse(profileToEdit.getPhone());
        Email updatedEmail = editProfileDescriptor.getEmail().orElse(profileToEdit.getEmail());
        Set<Tag> updatedTags = editProfileDescriptor.getTags().orElse(profileToEdit.getTags());

        return new Profile(updatedName, updatedPhone, updatedEmail, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProfileCommand)) {
            return false;
        }

        // state check
        EditProfileCommand e = (EditProfileCommand) other;
        return index.equals(e.index)
                && editProfileDescriptor.equals(e.editProfileDescriptor);
    }

    /**
     * Stores the details to edit the profile with. Each non-empty field value will replace the
     * corresponding field value of the profile.
     */
    public static class EditProfileDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Tag> tags;

        public EditProfileDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditProfileDescriptor(EditProfileDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditProfileDescriptor)) {
                return false;
            }

            // state check
            EditProfileDescriptor e = (EditProfileDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getTags().equals(e.getTags());
        }
    }
}