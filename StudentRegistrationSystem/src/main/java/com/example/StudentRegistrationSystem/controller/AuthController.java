package com.example.StudentRegistrationSystem.controller;

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

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

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

}

