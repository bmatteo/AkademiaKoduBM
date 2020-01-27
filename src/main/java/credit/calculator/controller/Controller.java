package credit.calculator.controller;


import credit.calculator.model.User;
import credit.calculator.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    Service service;

    @RequestMapping(value = "/firstpage", method = RequestMethod.GET)
    public String firstPage() {
        return "firstpage";
    }

    @RequestMapping(value = "/secondpage", method = RequestMethod.GET)
    public String secondPage() {
        return "secondpage";
    }

    @RequestMapping(value = "toomuch", method = RequestMethod.GET)
    public String toomuch() {
        return "toomuch";
    }

    @RequestMapping(value = "/tooyoung", method = RequestMethod.GET)
    public String tooYoung() {
        return "tooyoung";
    }

    @RequestMapping(value = "/toopoor", method = RequestMethod.GET)
    public String tooPoor() {
        return "toopoor";
    }


    @RequestMapping(value = "/firstpage/{formNumber}", method = RequestMethod.POST)
    public String initialData(@RequestParam(required = false) Double incomeParameter,
                              @RequestParam(required = false) String contractParameter,
                              @RequestParam(required = false) Integer ageParameter,
                              @PathVariable int formNumber
    ) {

        if(formNumber == 1) {
            System.out.println("asdfgsdfgsdfg");
        } else {
            System.out.println("dwa");
            return "redirect:/secondpage";
        }

        if (!contractParameter.equals("Contract work") &&
                !contractParameter.equals("Contract of mandate") &&
                !contractParameter.equals("Contract of employment")) {
            return "firsterror";
        } else {

            User newUser = new User();
            newUser.setIncome(incomeParameter);
            newUser.setTypeOfContract(contractParameter);
            newUser.setAge(ageParameter);
            service.getUsersListCommon().add(newUser);

            return "redirect:/secondpage";
        }
    }


    @RequestMapping(value = "/secondpage", method = RequestMethod.POST)
    public String detailedData(@RequestParam() Long amountParameter,
                               @RequestParam() Long timeParameter
    ) {
        User finalUser = new User();
        for (int i = 0; i < service.getUsersListCommon().size(); i++) {
            finalUser.setIncome(service.getUsersListCommon().get(i).getIncome());
            finalUser.setTypeOfContract(service.getUsersListCommon().get(i).getTypeOfContract());
            finalUser.setAge(service.getUsersListCommon().get(i).getAge());
        }
        if (finalUser.getAge() < 18) {
            return "tooyoung";
        } else if (finalUser.getIncome() < 2000) {
            return "toopoor";
        } else if (amountParameter > 10000) {
            return "redirect:/toomuch";
        } else if (timeParameter > 35) { // zmień tu potem - wstępna walidacja tu zostaje - tooyoung, toopoor,etc... A jezeli sie wszystko zgadza - to jeb obliczenia z controllera
            return "toolong";
        } else if (finalUser.getIncome() >= 2000 &&
                finalUser.getTypeOfContract().equals("Contract work") &&
                finalUser.getAge() <= 50 &&
                amountParameter <= 10000 &&
                amountParameter > 0 &&
                timeParameter <= 35) {
            return "granted";
        } else if (finalUser.getIncome() >= 2000 &&
                finalUser.getTypeOfContract().equals("Contract mandate") &&
                finalUser.getAge() <= 50 &&
                amountParameter <= 10000 &&
                amountParameter > 0 &&
                timeParameter <= 35) {
            return "granted";
        } else if (finalUser.getIncome() >= 2000 &&
                finalUser.getTypeOfContract().equals("Contract of employment") &&
                finalUser.getAge() <= 50 &&
                amountParameter <= 10000 &&
                amountParameter > 0 &&
                timeParameter <= 35) {
            return "granted";
        } else {
            return "requirementsnotmet";
        }
    }

    @RequestMapping(value = "/firstpage2", method = RequestMethod.POST)
    public String hsdkgf(@RequestParam String param) {

        System.out.println("Parametr: " + param);

        return "redirect:/secondpage";
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
    public String comment(@PathVariable int postId) {
        return "firspage";
    }


}


