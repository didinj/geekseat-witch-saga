package com.djamware.witchsaga.controller;

import com.djamware.witchsaga.utils.Helper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String submit(@RequestParam Integer maxYear, @RequestParam Integer firstPersonAge, @RequestParam Integer firstPersonDeath, @RequestParam Integer secondPersonAge, @RequestParam Integer secondPersonDeath) {
        // load hashmap of year and sum of killed villagers based on year of villager solve the problem
        HashMap<Integer, Integer> yearAndKilledVillagers = helper.generateYearAndKilledVillagers(maxYear);

        // calculate birth year
        int firstPersonBirth = helper.getBirthYear(firstPersonAge, firstPersonDeath);
        int secondPersonBirth = helper.getBirthYear(secondPersonAge, secondPersonDeath);

        // get the sum of killed villagers from hashmap based on birth
        int firstKilledNumbers = helper.getKilledNumberByBirth(firstPersonBirth, yearAndKilledVillagers);
        int secondKilledNumbers = helper.getKilledNumberByBirth(secondPersonBirth, yearAndKilledVillagers);

        // get average killed villagers from 2 given person birth
        Double averageKills = helper.averageKilled(firstKilledNumbers, secondKilledNumbers);

        return "redirect:/result/" + firstPersonBirth + "/" + secondPersonBirth + "/" + firstKilledNumbers + "/" + secondKilledNumbers + "/" + averageKills;
    }

    // Showing the result of processed action before
    @RequestMapping("/result/{firstPersonBirth}/{secondPersonBirth}/{firstKilledNumbers}/{secondKilledNumbers}/{averageKills}")
    public String show(@PathVariable Map<String, String> pathVarsMap, Model model) {
        model.addAttribute("firstPersonBirth", pathVarsMap.get("firstPersonBirth"));
        model.addAttribute("secondPersonBirth", pathVarsMap.get("secondPersonBirth"));
        model.addAttribute("firstKilledNumbers", pathVarsMap.get("firstKilledNumbers"));
        model.addAttribute("secondKilledNumbers", pathVarsMap.get("secondKilledNumbers"));
        model.addAttribute("averageKills", pathVarsMap.get("averageKills"));
        return "result";
    }
}
