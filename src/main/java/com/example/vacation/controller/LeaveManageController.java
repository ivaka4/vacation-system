package com.example.vacation.controller;

import com.example.vacation.model.binding.LeaveManagerBindingModel;
import com.example.vacation.model.service.LeaveManagerServiceModel;
import com.example.vacation.service.LeaveService;
import com.example.vacation.util.Tools;
import javax.validation.Valid;

import org.json.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

@Controller
public class LeaveManageController {
    private final LeaveService leaveService;
    private final ModelMapper modelMapper;
    private final Tools tools;

    @Autowired
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

    @RequestMapping(value = "/user/manage-leaves", method = RequestMethod.GET)
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

    @RequestMapping(value = "/user/get-all-leaves", method = RequestMethod.GET)
    public @ResponseBody String getAllLeaves(@RequestParam(value = "pending", defaultValue = "false") boolean pending,
                        @RequestParam(value = "accepted", defaultValue = "false") boolean accepted,
                        @RequestParam(value = "rejected", defaultValue = "false") boolean rejected) throws Exception {

        Iterator<LeaveManagerServiceModel> iterator = leaveService.getAllLeaves().iterator();
        if (pending || accepted || rejected)
            iterator = leaveService.getAllLeavesOnStatus(pending, accepted, rejected).iterator();
        JSONArray jsonArr = new JSONArray();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        Calendar calendar = Calendar.getInstance();

        while (iterator.hasNext()) {
            LeaveManagerServiceModel leaveDetails = iterator.next();
            calendar.setTime(leaveDetails.getToDate());
            calendar.add(Calendar.DATE, 1);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("title", leaveDetails.getUser().getUsername());
            jsonObj.put("start", dateFormat.format(leaveDetails.getFromDate()));
            jsonObj.put("end", dateFormat.format(calendar.getTime()));
            if (leaveDetails.isActive())
                jsonObj.put("color", "#0878af");
            if (!leaveDetails.isActive() && leaveDetails.isAcceptRejectFlag())
                jsonObj.put("color", "green");
            if (!leaveDetails.isActive() && !leaveDetails.isAcceptRejectFlag())
                jsonObj.put("color", "red");
            jsonArr.put(jsonObj);
        }

        return jsonArr.toString();
    }


    @RequestMapping(value = "/user/my-leaves", method = RequestMethod.GET)
    public ModelAndView showMyLeaves(ModelAndView mav) {
        mav.addObject("leavesList", this.leaveService.getAllLeavesOfEmployee());
        mav.setViewName("myLeaves");
        return mav;
    }

    @RequestMapping(value = "/user/leaves", method = RequestMethod.GET)
    public ModelAndView showEmployeeLeaves(ModelAndView mav) {
        mav.addObject("leavesList", this.leaveService.getAllAcceptedLeaves());
        mav.setViewName("acceptedLeaves");
        return mav;
    }
}
