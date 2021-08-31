package com.example.vacation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @RequestMapping(value = "/user/home", method = RequestMethod.GET)
    public ModelAndView home(ModelAndView mav, @AuthenticationPrincipal
            UserDetails user) throws Exception {
        mav.setViewName("home");
        return mav;

    }
}
