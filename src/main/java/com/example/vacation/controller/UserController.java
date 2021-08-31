package com.example.vacation.controller;

import com.example.vacation.model.binding.EmployeeBindingModel;
import com.example.vacation.model.binding.EmployeeUpdateBinding;
import com.example.vacation.model.entity.Employee;
import com.example.vacation.model.service.EmployeeServiceModel;
import com.example.vacation.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController extends BaseController {
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public UserController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

    /**
     * Opens the registration page to register a new user.
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(Model model) {
        ModelAndView mav = new ModelAndView("registration");
        if (!model.containsAttribute("userInfo")) {
            mav.addObject("userInfo", new EmployeeBindingModel());
        }
        return mav;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(ModelAndView mav, @Valid EmployeeBindingModel employeeBindingModel, BindingResult bindResult, RedirectAttributes redirectAttributes) {

        mav.addObject("successMessage");
        if (bindResult.hasErrors() || !employeeBindingModel.getPassword().equals(employeeBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userInfo", employeeBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userInfo", bindResult);
            return super.redirect("/registration");
        } else {
            this.employeeService.saveUser(employeeBindingModel);
            mav.addObject("successMessage", "User registered successfully! Awaiting for Manager approval!!");
        }
        return super.redirect("/login");
    }

    @PreAuthorize("hasAnyAuthority('CEO', 'MANAGER')")
    @RequestMapping(value = "/user/manage-users", method = RequestMethod.GET)
    public ModelAndView showManageUsers(ModelAndView mav) {
        List<EmployeeServiceModel> employeeList = this.employeeService.getEmployees();
        mav.addObject("users", employeeList);
        mav.setViewName("manageUsers");
        return mav;
    }

    @PreAuthorize("hasAnyAuthority('CEO', 'MANAGER')")
    @RequestMapping(value = "/user/manage-users/{action}/{id}", method = RequestMethod.GET)
    public ModelAndView manageUsers(ModelAndView mav, @PathVariable("action") String action,
                                    @PathVariable("id") Long id) {


        if (action.equals("edit")) {
            EmployeeServiceModel bindingModel = employeeService.findEmployeeById(id);
            mav.addObject("userInfo", bindingModel);
            mav.setViewName("editUser");

        } else if (action.equals("delete")) {
            employeeService.deleteEmployee(id);
            mav.addObject("successMessage", "User removed successfully!!");
            mav.setView(new RedirectView("/user/manage-users"));
        } else if (action.equals("block")) {
            employeeService.blockEmployee(id);
            mav.addObject("successMessage", "User blocked successfully!!");
            mav.setView(new RedirectView("/user/manage-users"));
        } else if (action.equals("unblock")) {
            employeeService.unBlockEmployee(id);
            mav.addObject("successMessage", "User is active now!!");
            mav.setView(new RedirectView("/user/manage-users"));
        }

        return mav;
    }

    @PreAuthorize("hasAnyAuthority('CEO', 'MANAGER')")
    @RequestMapping(value = "/user/manage-users/save-user-edit", method = RequestMethod.POST)
    public ModelAndView saveUserEdit(ModelAndView mav, @Valid EmployeeUpdateBinding userInfo, BindingResult bindResult, RedirectAttributes redirectAttributes) {

        EmployeeServiceModel employeeServiceModel = this.employeeService.findUserByUsername(userInfo.getUsername());

        if (bindResult.hasErrors()) {
            mav.addObject("errorField", bindResult.getFieldError().getField());
            mav.addObject("errorMessage", bindResult.getFieldError().getDefaultMessage());
            mav.setView(new RedirectView("/user/manage-users/edit/" + employeeServiceModel.getId() + ""));
        } else {

            this.employeeService.updateUser(userInfo);
            mav.addObject("successMessage", "Employee Details updated successfully!!");
            mav.setView(new RedirectView("/user/manage-users"));
        }
        return mav;
    }

    @RequestMapping(value = "/user/change-password", method = RequestMethod.GET)
    public ModelAndView changePasswordForm(ModelAndView mav) {

        mav.setViewName("changePassword");
        return mav;
    }


}
