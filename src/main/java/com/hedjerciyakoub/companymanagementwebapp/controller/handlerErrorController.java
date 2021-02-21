package com.hedjerciyakoub.companymanagementwebapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class handlerErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handelError(HttpServletRequest request, Model model){

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status!=null){
                model.addAttribute("errorNumber",Integer.parseInt(status.toString()));
        }


        return "error";
    }



    @Override
    public String getErrorPath() {
        return null;
    }
}
