package com.example.demo.controllers;

import com.example.demo.dto.LoginForm;
import com.example.demo.entities.Loadout;
import com.example.demo.entities.User;
import com.example.demo.model.LoadoutRequest;
import com.example.demo.model.UserRequest;
import com.example.demo.services.ToAssembleLoadout;
import com.example.demo.services.UserServiceImp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@NoArgsConstructor
@RequestMapping("/")
public class JsxController {
    @Autowired
    private UserServiceImp userService;
    @Autowired
    private ToAssembleLoadout toAssembleLoadout;
    public JsxController(ToAssembleLoadout toAssembleLoadout){
        this.toAssembleLoadout = toAssembleLoadout;
    }
    public JsxController(UserServiceImp userService) {
        this.userService = userService;
    }
    @GetMapping("/main")
    String showMainPage( Model model){
        model.addAttribute("pageTitle", "Home - main");
        return "index.html";
    }
    @GetMapping("/login")
    public String ShowLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "thymeleaf/login";
        }
        return "redirect:/";
    }
//    @PostMapping("/login")
//    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model){
//        if((loginForm.getEmail().equals("admin")&& loginForm.getPassword().equals("admin"))){
//            return "/main";
//        }
//        model.addAttribute("invalidCredentials", true);
//
//        return "/login";
//    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        User user = new User();
        model.addAttribute("user", user);
            return "thymeleaf/register";
    }

    @PostMapping("/register")
    public String submitRegister(@ModelAttribute UserRequest user, HttpSession session){
        System.out.println(user);
        userService.saveUser(user);
        session.setAttribute( "message", "User Registered Successfully");
        return "thymeleaf/successRegister";
    }

    @GetMapping("/update")
    public String showUpdatePage(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "thymeleaf/update";
    }

    @PostMapping("/update")
    public String submitUpdate(@ModelAttribute User user, HttpSession session){
        userService.updateUser(user);
        session.setAttribute( "message", "User Updated Successfully");
        return "thymeleaf/successUpdate";
    }

    @GetMapping("/list")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        return "thymeleaf/usersList";
    }

    @GetMapping("/createLoadout")
    public String showCreateLoadout(Model model){
        Loadout loadout = new Loadout();
        model.addAttribute("loadout", loadout);
        return "thymeleaf/createLoadout";
    }

    @PostMapping("/createLoadout")
    public String submitCreate(@ModelAttribute LoadoutRequest loadout, HttpSession session){
        System.out.println(loadout);
        toAssembleLoadout.createLoadout(loadout);
        session.setAttribute( "message", "Loadout Created Successfully");
        return "thymeleaf/successCreateLoadout";
    }
    @GetMapping("/listLoadouts")
    public String showLoadouts(Model model){
        model.addAttribute("loadouts", toAssembleLoadout.getLoadouts());
        return "thymeleaf/listLoadouts";
    }

    @GetMapping("/updateLoadout")
    public String showUpdatePage1(Model model){
        Loadout loadout = new Loadout();
        model.addAttribute("loadout", loadout);
        return "thymeleaf/updateLoadout";
    }

    @PostMapping("/updateLoadout")
    public String submitUpdate1(@ModelAttribute Loadout loadout, HttpSession session){
        toAssembleLoadout.updateLoadout(loadout);
        session.setAttribute( "message", "Loadous Updated Successfully");
        return "thymeleaf/successUpdateLoadout";
    }
//    @PostMapping("/login")
//    public String postLoginPage(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model) {
//
//        if("USER".equals(loginForm.getEmail())==userRequest.getPassword()){
//            return "index.html";
//        }
//        return "login";
//    }

//    @RequestMapping(value="/login", method= RequestMethod.GET)
//    public String getLoginForm(){
//        return "login";
//    }
//    @RequestMapping(value = "/login", method=RequestMethod.POST)
//    public String login(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
//            return "login";
//        }
//        return "redirect:/";
//    }


}
