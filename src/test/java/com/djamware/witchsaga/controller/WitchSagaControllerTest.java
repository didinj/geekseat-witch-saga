package com.djamware.witchsaga.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = WitchSagaController.class)
public class WitchSagaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test the home controller
    @Test
    public void main() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));

        MvcResult mvcResult = resultActions.andReturn();
        ModelAndView mv = mvcResult.getModelAndView();
    }

    // Test submit controller with given input values age A=10, death A=12, age B=13, death B=17
    // So it will give the result as parameters for next result controller
    @Test
    public void processTheAverageKills() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstPersonAge", String.valueOf(10));
        requestParams.add("firstPersonDeath", String.valueOf(12));
        requestParams.add("secondPersonAge", String.valueOf(13));
        requestParams.add("secondPersonDeath", String.valueOf(17));

        mockMvc.perform(post("/submit").params(requestParams))
                .andExpect(status().is3xxRedirection());
    }

    // Test result controller with the result parameter from submit controller
    // 2 = death A - age A, 4 = death B - age B, 2 = number killed in year 2,
    // 7 = number killed in year 4
    // 4.5 = average killed in those 2 years (2x7)/2
    @Test
    public void getResult() throws Exception {
        mockMvc.perform(get("/result/2/4/2/7/4.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("firstPersonBirth", equalTo("2")))
                .andExpect(model().attribute("secondPersonBirth", equalTo("4")))
                .andExpect(model().attribute("firstKilledNumbers", equalTo("2")))
                .andExpect(model().attribute("secondKilledNumbers", equalTo("7")))
                .andExpect(model().attribute("averageKills", equalTo("4.5")));
    }
}
