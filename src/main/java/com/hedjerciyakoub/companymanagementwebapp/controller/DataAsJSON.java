package com.hedjerciyakoub.companymanagementwebapp.controller;


import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import com.hedjerciyakoub.companymanagementwebapp.entitys.WorkOn;
import com.hedjerciyakoub.companymanagementwebapp.security.services.SocialUsersServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.DepartmentServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.EmployeeServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.LocalStorageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class DataAsJSON {


    private final LocalStorageServiceImpl localStorageServiceImpl;
    private final DepartmentServiceImpl departmentServiceImpl;
    private final EmployeeServiceImpl employeeServiceImpl;
    private final SocialUsersServiceImpl socialUsersServiceImpl;


    @GetMapping("/department/list/projects")
    public Map<String, List<String>> getDepartmentsWithThierProject() {
        List<Department> departments = departmentServiceImpl.findAll();
        try {
            return localStorageServiceImpl.upToDateDepartmentApi(departments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/employee/list/projects")
    public Map<Integer, List<String>> getEmployeesWithThierProjects(){

        Map<Integer, List<String>> employeesIdWithThierProjectName = new HashMap<Integer, List<String>>();

        for (Employee employee : employeeServiceImpl.findAll()) {
            List<String> projectOfEmployeeNames = new ArrayList<String>();
            for (WorkOn wo : employee.getWorkOns()) {
                projectOfEmployeeNames.add(wo.getProject().getName());
            }
            employeesIdWithThierProjectName.put(employee.getEmpId(), projectOfEmployeeNames);
        }
        return employeesIdWithThierProjectName ;

    }
    @GetMapping("/principal/username")
    public String getCurrentUsername(Principal principal){
        return "";//socialUsersServiceImpl.setPrincipalUsername(principal);
    }


}
