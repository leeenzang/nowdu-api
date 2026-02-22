package com.tauceti.nowdu.user.controller;

import com.tauceti.nowdu.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/users")
public class UserController {

    @Operation(summary = "내 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserInfoResponse.from(user));
    }

    @Getter
    @Builder
    static class UserInfoResponse {
        private Long id;
        private String email;
        private String name;
        private String profileImage;

        static UserInfoResponse from(User user) {
            return UserInfoResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .profileImage(user.getProfileImage())
                    .build();
        }
    }
}