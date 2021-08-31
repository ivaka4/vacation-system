package com.example.vacation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LeaveManageController {

    @RequestMapping(value = "/user/apply-leave", method = RequestMethod.GET)
    public ModelAndView applyLeave(ModelAndView mav) {

        mav.setViewName("applyLeave");
        return mav;
    }

    @RequestMapping(value="/user/manage-leaves",method= RequestMethod.GET)
    public ModelAndView manageLeaves(ModelAndView mav) {

        mav.setViewName("manageLeaves");
        return mav;
    }

    @RequestMapping(value = "/user/my-leaves", method = RequestMethod.GET)
    public ModelAndView showMyLeaves(ModelAndView mav) {

        mav.setViewName("myLeaves");
        return mav;
    }
}
