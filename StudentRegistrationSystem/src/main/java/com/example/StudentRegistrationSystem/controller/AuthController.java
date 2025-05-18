package com.example.StudentRegistrationSystem.controller;

import com.example.StudentRegistrationSystem.service.PasswordResetService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import com.example.StudentRegistrationSystem.model.User;
import com.example.StudentRegistrationSystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String loginSuccess(HttpSession session, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.getUserByUsername(username);
            if (user == null) {
                return "redirect:/auth/login?error=true";
            }

            session.setAttribute("loggedInUser", user);
            model.addAttribute("user", user);

            return "login-success";
        } catch (Exception e) {
            return "redirect:/auth/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String confirmLogout() {
        return "logout-confirm";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("username") String username,
                                        Model model) {
        String token = passwordResetService.generateResetToken(username);
        if (token != null) {
            // For development purposes, we'll show the reset link directly
            model.addAttribute("resetLink", "/auth/reset-password?token=" + token);
            return "forgot-password-confirmation";
        }
        model.addAttribute("error", "Username not found");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token,
                                    Model model) {
        if (!passwordResetService.validateToken(token)) {
            model.addAttribute("error", "Invalid or expired token");
            return "error";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processPasswordReset(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model) {
        if (passwordResetService.resetPassword(token, password)) {
            model.addAttribute("message", "Password has been reset successfully");
            return "redirect:/auth/login?reset=success";
        }
        model.addAttribute("error", "Password reset failed");
        return "reset-password";
    }

    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String processPasswordChange(@RequestParam("currentPassword") String currentPassword,
                                        @RequestParam("newPassword") String newPassword,
                                        Principal principal,
                                        Model model) {
        User user = userService.getUserByUsername(principal.getName());
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(user);
            return "redirect:/auth/login?changed=success";
        }
        model.addAttribute("error", "Current password is incorrect");
        return "change-password";
    }



}

