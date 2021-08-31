package com.example.vacation.controller;

import com.example.vacation.model.binding.LeaveManagerBindingModel;
import com.example.vacation.model.service.EmployeeServiceModel;
import com.example.vacation.model.service.LeaveManagerServiceModel;
import com.example.vacation.service.LeaveService;
import com.example.vacation.util.Tools;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class LeaveManageController {
    private final LeaveService leaveService;
    private final ModelMapper modelMapper;
    private final Tools tools;

    public LeaveManageController(LeaveService leaveService, ModelMapper modelMapper, Tools tools) {
        this.leaveService = leaveService;
        this.modelMapper = modelMapper;
        this.tools = tools;
    }

    @RequestMapping(value = "/user/apply-leave", method = RequestMethod.GET)
    public ModelAndView applyLeave(ModelAndView mav) {
        mav.addObject("leaveDetails", new LeaveManagerBindingModel());
        mav.setViewName("applyLeave");
        return mav;
    }

    @RequestMapping(value = "/user/apply-leave", method = RequestMethod.POST)
    public ModelAndView submitApplyLeave(ModelAndView mav, @Valid LeaveManagerBindingModel leaveManagerBindingModel,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            mav.setViewName("applyLeave");
        } else {
            leaveService.applyLeave(leaveManagerBindingModel);
            mav.addObject("successMessage", "Your Leave Request is registered!");
            mav.setView(new RedirectView("/user/home"));
        }
        return mav;
    }

    @RequestMapping(value="/user/manage-leaves",method= RequestMethod.GET)
    public ModelAndView manageLeaves(ModelAndView mav) {
        mav.addObject("leavesList", this.leaveService.getAllActiveLeave());
        mav.setViewName("manageLeaves");
        return mav;
    }

    @RequestMapping(value = "/user/manage-leaves/{action}/{id}", method = RequestMethod.GET)
    public ModelAndView acceptOrRejectLeaves(ModelAndView mav, @PathVariable("action") String action,
                                             @PathVariable("id") Long id) {

        LeaveManagerServiceModel leaveDetails = this.leaveService.getLeaveById(id);
        if (action.equals("accept")) {
            leaveDetails.setAcceptRejectFlag(true);
            leaveDetails.setActive(false);
        } else if (action.equals("reject")) {
            leaveDetails.setAcceptRejectFlag(false);
            leaveDetails.setActive(false);
        }
        leaveService.updateLeaveEntity(leaveDetails);
        mav.addObject("successMessage", "Updated Successfully!");
        mav.setView(new RedirectView("/user/manage-leaves"));
        return mav;
    }


    @RequestMapping(value = "/user/my-leaves", method = RequestMethod.GET)
    public ModelAndView showMyLeaves(ModelAndView mav) {
        mav.addObject("leaveDetails", new LeaveManagerBindingModel());
        mav.setViewName("myLeaves");
        return mav;
    }
}
