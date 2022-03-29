package com.djamware.witchsaga.controller;

import com.djamware.witchsaga.utils.Helper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WitchSagaController {

    Helper helper =  new Helper();

    // Landing page to home.html
    @GetMapping("/")
    public String main() {
        return "home"; //view
    }

    // Processing find the average killed villagers based on given persons age and death year
    @RequestMapping("/submit")
    public String submit(@RequestParam Integer firstPersonAge, @RequestParam Integer firstPersonDeath, @RequestParam Integer secondPersonAge, @RequestParam Integer secondPersonDeath, Model model) {
        // calculate birth year
        int firstPersonBirth = helper.getBirthYear(firstPersonAge, firstPersonDeath);
        int secondPersonBirth = helper.getBirthYear(secondPersonAge, secondPersonDeath);

        // get killed villagers based on birth year in biginteger
        BigInteger firstKilledNumbers = helper.getKilledOnYear(firstPersonBirth);
        BigInteger secondKilledNumbers = helper.getKilledOnYear(secondPersonBirth);

        // get average killed villagers from 2 given person birth in biginteger
        BigDecimal averageKills = helper.averageKilled(firstKilledNumbers, secondKilledNumbers);

        model.addAttribute("firstPersonBirth", firstPersonBirth);
        model.addAttribute("secondPersonBirth", secondPersonBirth);
        model.addAttribute("firstKilledNumbers", firstKilledNumbers);
        model.addAttribute("secondKilledNumbers", secondKilledNumbers);
        model.addAttribute("averageKills", averageKills);

        return "home";
    }

    // Showing the result of processed action before
//    @RequestMapping("/result/{firstPersonBirth}/{secondPersonBirth}/{firstKilledNumbers}/{secondKilledNumbers}/{averageKills}")
//    public String show(@PathVariable Map<String, String> pathVarsMap, Model model) {
//        model.addAttribute("firstPersonBirth", pathVarsMap.get("firstPersonBirth"));
//        model.addAttribute("secondPersonBirth", pathVarsMap.get("secondPersonBirth"));
//        model.addAttribute("firstKilledNumbers", pathVarsMap.get("firstKilledNumbers"));
//        model.addAttribute("secondKilledNumbers", pathVarsMap.get("secondKilledNumbers"));
//        model.addAttribute("averageKills", pathVarsMap.get("averageKills"));
//        return "result";
//    }
}
