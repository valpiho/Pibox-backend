package com.pibox.core.service;

import com.pibox.core.constant.FileConstant;
import com.pibox.core.exception.domain.EmailExistException;
import com.pibox.core.exception.domain.NotAnImageFileException;
import com.pibox.core.exception.domain.NotFoundException;
import com.pibox.core.exception.domain.UsernameExistException;
import com.pibox.core.repository.GroupRepository;
import com.pibox.core.repository.UserRepository;
import com.pibox.core.domain.model.Group;
import com.pibox.core.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.pibox.core.constant.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
public class GroupService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group getGroupByGroupId(UUID groupId) {
        return groupRepository.findGroupByGroupId(groupId);
    }

    public List<Group> getAllActivePublicGroups() {
        return groupRepository.findAllByIsActiveIsTrueAndIsPublicIsTrue();
    }

    public List<Group> getAllGroupsByOwnerId(UUID userId) {
        return groupRepository.findAllByGroupOwnerId(userId);
    };

    public Group createNewGroup(UUID groupOwnerUserId, String title, String description, boolean isPublic) {
        Group group = new Group();
        User user = userRepository.findUserByUserId(groupOwnerUserId);

        group.setGroupOwner(user);
        group.setCreatedAt(new Date());
        group.setTitle(title);
        group.setDescription(description);
        group.setPublic(isPublic);
        group.setActive(true);
        group.setGroupImgUrl(getTemporaryGroupProfileImgUrl(user.getUserId().toString()));
        groupRepository.save(group);
        return group;
    }

    public void updateGroup(User user, String title, String description, boolean isPublicGroup, boolean isActiveGroup) {
        // TODO:
    }

    public void deleteGroupByGroupId(UUID groupId) {
        // TODO:
    }

    public Group updateGroupProfileImage(UUID groupId, MultipartFile profileImage)
            throws NotFoundException, IOException, NotAnImageFileException {
        Group group = groupRepository.findGroupByGroupId(groupId);
        if (group == null) {
            throw new NotFoundException("No group found by groupId: " + groupId);
        }
        saveProfileImage(group, profileImage);
        return group;
    }

    private void saveProfileImage(Group group, MultipartFile profileImage)
            throws IOException, NotAnImageFileException {
        if (profileImage != null) {
            if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + FileConstant.NOT_AN_IMAGE_FILE);
            }
            Path groupFolder = Paths.get(GROUP_FOLDER + group.getGroupId().toString()).toAbsolutePath().normalize();
            if(!Files.exists(groupFolder)) {
                Files.createDirectories(groupFolder);
                LOGGER.info(FileConstant.DIRECTORY_CREATED + groupFolder);
            }
            Files.deleteIfExists(Paths.get(groupFolder + group.getGroupId().toString() + FileConstant.DOT + FileConstant.JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), groupFolder.resolve(group.getGroupId().toString() + FileConstant.DOT + FileConstant.JPG_EXTENSION), REPLACE_EXISTING);
            group.setGroupImgUrl(setGroupProfileImgUrl(group.getGroupId().toString()));
            groupRepository.save(group);
            LOGGER.info(FileConstant.FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String getTemporaryGroupProfileImgUrl(String groupId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_GROUP_IMAGE_PATH + groupId).toUriString();
    }

    private String setGroupProfileImgUrl(String groupId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.GROUP_IMAGE_PATH + groupId + FileConstant.FORWARD_SLASH
                + groupId + FileConstant.DOT + FileConstant.JPG_EXTENSION).toUriString();
    }
}
