// 代码生成时间: 2025-09-05 04:40:00
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpSession;

// 主题切换控制器
@Controller
@RequestMapping("/theme")
public class ThemeController {

    // GET请求处理主题切换
    @GetMapping("/switch")
    public String switchTheme(@RequestParam(name = "theme", required = false) String theme, Model model, HttpSession session) {
        if (theme == null) {
            // 没有提供主题时，从session中获取
            theme = (String) session.getAttribute("theme");
            if (theme == null) {
                // 如果session中也没有，则默认主题
                theme = "default";
            }
        } else {
            // 提供了主题，更新session
            session.setAttribute("theme", theme);
        }
        model.addAttribute("theme", theme);
        return "theme :: theme";
    }

    // 错误处理方法
    @GetMapping("/error")
    @ResponseBody
    public ResponseEntity<String> handleError() {
        return ResponseEntity.badRequest().body("Error: Invalid theme selection.");
    }

    // 响应式主题切换
    @GetMapping("/switchAsync")
    @ResponseBody
    public String switchThemeAsync(@RequestParam(name = "theme", required = false) String theme, HttpSession session) {
        if (theme == null) {
            theme = (String) session.getAttribute("theme");
            if (theme == null) {
                theme = "default";
            }
        } else {
            session.setAttribute("theme", theme);
        }
        return "Theme switched to: " + theme;
    }
}
