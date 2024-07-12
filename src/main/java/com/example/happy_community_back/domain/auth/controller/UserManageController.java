package com.example.happy_community_back.domain.auth.controller;

import com.example.happy_community_back.domain.auth.dto.request.CheckDuplicateReqDto.CheckDuplicateEmailReqDto;
import com.example.happy_community_back.domain.auth.dto.request.CheckDuplicateReqDto.CheckDuplicateNicknameReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyPasswordReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserReqDto.UserModifyNicknameReqDto;
import com.example.happy_community_back.domain.auth.dto.response.UserResDto;
import com.example.happy_community_back.domain.auth.service.UserManageService;
import com.example.happy_community_back.global.common.ApiResponseEntity;
import com.example.happy_community_back.global.common.ResponseText;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserManageController {

    private final UserManageService userManageService;

    /**
     * 회원 정보 조회 API
     */
    @PostMapping()
    public ResponseEntity<UserResDto> getInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userManageService.getUserInfo(userDetails.getUsername()));
    }

    /**
     * 이메일 중복 체크 API
     */
    @PostMapping("/check-duplicate-email")
    public ResponseEntity<ApiResponseEntity<String>> checkDuplicateEmail(@RequestBody CheckDuplicateEmailReqDto emailReqDto) {
        boolean isDuplicate = userManageService.checkDuplicateEmail(emailReqDto.email());

        return ResponseEntity.ok(ApiResponseEntity.of(isDuplicate ? ResponseText.DUPLICATE : ResponseText.OK));
    }

    /**
     * 닉네임 중복 체크 API
     */
    @PostMapping("/check-duplicate-nickname")
    public ResponseEntity<ApiResponseEntity<String>> checkDuplicateNickname(@RequestBody CheckDuplicateNicknameReqDto nicknameReqDto) {
        boolean isDuplicate = userManageService.checkDuplicateNickname(nicknameReqDto.nickname());

        return ResponseEntity.ok(ApiResponseEntity.of(isDuplicate ? ResponseText.DUPLICATE : ResponseText.OK));
    }

    /**
     * 비밀번호 수정 API
     */
    @PutMapping("/password")
    public ResponseEntity<ApiResponseEntity<String>> modifyPassword(@Valid @RequestBody UserModifyPasswordReqDto userModifyPasswordReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        userManageService.modifyPassword(userModifyPasswordReqDto, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_MODIFY_PASSWORD));
    }

    /**
     * 닉네임 수정 API
     */
    @PutMapping("/nickname")
    public ResponseEntity<ApiResponseEntity<String>> modifyNickname(@Valid @RequestBody UserModifyNicknameReqDto userModifyNicknameReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        userManageService.modifyNickname(userModifyNicknameReqDto, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_MODIFY_NICKNAME));
    }

}
