package com.example.matchteam.controller.user.authenticate;

import com.example.matchteam.dto.email.MailDto;
import com.example.matchteam.service.user.UserService;
import com.example.matchteam.service.user.authenticate.AuthenticateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AuthenticateController {

    private final AuthenticateService authenticateService;
    private final UserService userService;
    private int authenticateCode;

    @GetMapping("/authenticate")
    public int authenticateCode(@RequestParam String address) {
        authenticateCode = createAuthenticateCode();
        MailDto mailDto = new MailDto();
        mailDto.setTitle("[Project Matching] 앱 인증 코드입니다");
        mailDto.setAddress(address);
        mailDto.setMessage("앱 인증 코드 : " + authenticateCode);
        boolean sendMailIsSuccess = authenticateService.sendEmail(mailDto);
        if (sendMailIsSuccess) {
            return authenticateCode;
        }
        return -1;
    }

    @PostMapping("/authenticate")
    public boolean authenticateCodeConfirm(@RequestBody int authenticateCode) {
        if (this.authenticateCode == authenticateCode)
            return true;
        return false;
    }
    @GetMapping("/users/password")
    public UserEmailAuthenticateDto findEmail(@RequestParam String email) {
        authenticateCode = createAuthenticateCode();
        UserEmailAuthenticateDto dto = new UserEmailAuthenticateDto(authenticateCode);
        MailDto mailDto = new MailDto();
        mailDto.setTitle("[Project Matching] 계정 인증 코드입니다");
        mailDto.setAddress(email);
        mailDto.setMessage("계정 인증 코드 : " + dto.getAuthenticateCode());
        boolean sendMailIsSuccess = authenticateService.sendEmail(mailDto);
        if (sendMailIsSuccess) {
            return dto;
        }
        return null;
    }
    @PostMapping("/users/password")
    public boolean authenticate(@RequestBody int authenticateCode) {
        if (this.authenticateCode == authenticateCode)
            return true;
        return false;
    }
    @GetMapping("/users/password/{email}")
    public String getUserPassword(@PathVariable("email") String email) {
        return userService.getPassword(email);
    }

    private int createAuthenticateCode() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
}
