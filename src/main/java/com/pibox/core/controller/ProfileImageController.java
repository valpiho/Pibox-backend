package com.pibox.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.pibox.core.constant.FileConstant.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping
public class ProfileImageController {

    @GetMapping(path = "/user/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getUserProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/user/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        return getBytes(username);
    }

    @GetMapping(path = "/group/image/{groupId}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getGroupProfileImage(@PathVariable("groupId") String groupId, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(GROUP_FOLDER + groupId + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/group/image/profile/{groupId}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempGroupProfileImage(@PathVariable("groupId") String groupId) throws IOException {
        return getBytes(groupId);
    }

    private byte[] getBytes(String usernameOrId) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + usernameOrId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
