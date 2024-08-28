package com.bluestarfish.blueberry.user.controller;

import com.bluestarfish.blueberry.common.dto.ApiSuccessResponse;
import com.bluestarfish.blueberry.user.dto.JoinRequest;
import com.bluestarfish.blueberry.user.dto.PasswordResetRequest;
import com.bluestarfish.blueberry.user.dto.UserUpdateRequest;
import com.bluestarfish.blueberry.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.bluestarfish.blueberry.common.handler.ResponseHandler.handleSuccessResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/whoami")
    public ApiSuccessResponse<?> whoami(
            @CookieValue(name = "Authorization") String accessToken
    ) {

        return handleSuccessResponse(userService.getUserByToken(accessToken), HttpStatus.OK);
    }

    @PostMapping
    public ApiSuccessResponse<?> join(
            @RequestBody JoinRequest joinRequest
    ) {
        userService.join(joinRequest);
        return handleSuccessResponse(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ApiSuccessResponse<?> findById(
            @PathVariable("userId") Long id
    ) {
        return handleSuccessResponse(userService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ApiSuccessResponse<?> update(
            @PathVariable("userId") Long id,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        userService.update(id, userUpdateRequest);
        return handleSuccessResponse(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ApiSuccessResponse<?> withdraw(
            @PathVariable("userId") Long id
    ) {
        userService.withdraw(id);
        return handleSuccessResponse(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/nickname")
    public ApiSuccessResponse<?> validateNickname(
            @RequestParam("nickname") String nickname
    ) {
        userService.validateNickname(nickname);
        return handleSuccessResponse(HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ApiSuccessResponse<?> resetPassword(
            @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        userService.resetPassword(passwordResetRequest);
        return handleSuccessResponse(HttpStatus.NO_CONTENT);
    }
}
