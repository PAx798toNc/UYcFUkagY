// 代码生成时间: 2025-09-13 20:27:00
package com.example.themeswitcher;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;

@Service
public class ThemeSwitcherService {

    private final HttpSession httpSession;

    @Autowired
    public ThemeSwitcherService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * Switches the theme for the current session.
     * 
     * @param themeName The name of the theme to switch to.
     * @return A ResponseEntity indicating success or failure.
     */
    public ResponseEntity<String> switchTheme(String themeName) {
        try {
            if (themeName == null || themeName.isEmpty()) {
                throw new IllegalArgumentException("Theme name cannot be null or empty");
            }
            // Store the theme in session for persistence across requests
            httpSession.setAttribute("theme