package ua.social.network.userservice.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.social.network.exception.SnException;
import ua.social.network.exception.UserServiceExceptionDetails;
import ua.social.network.oauth2.principal.SnPrincipal;
import ua.social.network.storage.domain.FileMetadata;
import ua.social.network.storage.service.StorageService;
import ua.social.network.userservice.dto.CreateUserRequest;
import ua.social.network.userservice.dto.ModifyUserRequest;
import ua.social.network.userservice.dto.UpdateAvatarResponse;
import ua.social.network.userservice.entity.ProfilePicture;
import ua.social.network.userservice.entity.Role;
import ua.social.network.userservice.entity.User;
import ua.social.network.userservice.repository.ProfilePictureRepository;
import ua.social.network.userservice.repository.UserRepository;
import ua.social.network.userservice.service.UserService;

/**
 * @author Mykola Yashchenko
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ProfilePictureRepository profilePictureRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public String create(final CreateUserRequest request) {
        final User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    public void modify(final ModifyUserRequest request, final SnPrincipal principal) {
        final int count = userRepository.modify(principal.userId, request.getName());
        if (count == 0) {
            throw new SnException(UserServiceExceptionDetails.NOT_FOUND, User.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public UpdateAvatarResponse updateAvatar(final FileMetadata fileMetadata, final SnPrincipal principal) {
        if (!isImage(StringUtils.defaultString(fileMetadata.fileName))) {
            throw new SnException(UserServiceExceptionDetails.IMAGE_TYPE_IS_NOT_SUPPORTED);
        }

        final String url = storageService.store(fileMetadata);
        final ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setUrl(url);
        profilePicture.setUser(new User(principal.userId));
        profilePictureRepository.save(profilePicture);
        return new UpdateAvatarResponse(url);
    }

    // todo extend supportable image types
    private boolean isImage(final String filename) {
        if (StringUtils.endsWith(filename.toLowerCase(), ".jpg")) {
            return true;
        }

        return StringUtils.endsWith(filename.toLowerCase(), ".png");
    }
}
