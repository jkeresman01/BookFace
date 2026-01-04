package com.keresman.bookface.user.controller;

import com.keresman.bookface.user.dto.request.CreateUserReq;
import com.keresman.bookface.user.dto.request.UpdateUserReq;
import com.keresman.bookface.user.dto.response.UserDTO;
import com.keresman.bookface.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok()
                .body(userService.getAllUsers());
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        Optional<UserDTO> userDTO = userService.getUserById(userId);

        return userDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserReq createUserReq) {
        Long userId = userService.createUser(createUserReq);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()   // /users
                .path("/{id}")          // /users/{id}
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserReq userUpdateRequest
    ) {
        userService.updateUserById(userId, userUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserWithId(userId);
    }

    @PostMapping(
            value = "{userId}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadProfileImage(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        //TODO tinky winky lala poo and the party
        //TODO don't care for this now
    }

    @GetMapping(
            value = "{userId}/profile-image",
            consumes = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getProfileImage() {
        //TODO don't care for this now
        byte[] profileImage = userService.getProfileImage();
        return ResponseEntity.ok(profileImage);
    }
}
