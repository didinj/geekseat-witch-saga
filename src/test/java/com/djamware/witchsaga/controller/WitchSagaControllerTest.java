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

import java.math.BigDecimal;
import java.math.BigInteger;

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
    // So it will give the right result as model
    @Test
    public void testRightTheAverageKills() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstPersonAge", String.valueOf(10));
        requestParams.add("firstPersonDeath", String.valueOf(12));
        requestParams.add("secondPersonAge", String.valueOf(13));
        requestParams.add("secondPersonDeath", String.valueOf(17));

        mockMvc.perform(post("/submit").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(model().attribute("firstPersonBirth", equalTo(Integer.valueOf(2))))
                .andExpect(model().attribute("secondPersonBirth", equalTo(Integer.valueOf(4))))
                .andExpect(model().attribute("firstKilledNumbers", equalTo(BigInteger.valueOf(2))))
                .andExpect(model().attribute("secondKilledNumbers", equalTo(BigInteger.valueOf(7))))
                .andExpect(model().attribute("averageKills", equalTo(BigDecimal.valueOf(4.5))));
    }

    // Test submit controller with given input values age A=12, death A=10, age B=17, death B=13
    // So it will give the invalid (-1) result as model
    @Test
    public void testInvalidTheAverageKillsByLessBirth() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstPersonAge", String.valueOf(12));
        requestParams.add("firstPersonDeath", String.valueOf(10));
        requestParams.add("secondPersonAge", String.valueOf(17));
        requestParams.add("secondPersonDeath", String.valueOf(13));

        mockMvc.perform(post("/submit").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(model().attribute("firstPersonBirth", equalTo(Integer.valueOf(-2))))
                .andExpect(model().attribute("secondPersonBirth", equalTo(Integer.valueOf(-4))))
                .andExpect(model().attribute("firstKilledNumbers", equalTo(BigInteger.valueOf(-1))))
                .andExpect(model().attribute("secondKilledNumbers", equalTo(BigInteger.valueOf(-1))))
                .andExpect(model().attribute("averageKills", equalTo(BigDecimal.valueOf(-1))));
    }

    // Test submit controller with given input values age A=1, death A=99, age B=2, death B=101
    // So it will give the invalid (-1) result as model
    @Test
    public void testInvalidTheAverageKillsByOverBirth() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstPersonAge", String.valueOf(1));
        requestParams.add("firstPersonDeath", String.valueOf(99));
        requestParams.add("secondPersonAge", String.valueOf(2));
        requestParams.add("secondPersonDeath", String.valueOf(102));

        mockMvc.perform(post("/submit").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(model().attribute("firstPersonBirth", equalTo(Integer.valueOf(98))))
                .andExpect(model().attribute("secondPersonBirth", equalTo(Integer.valueOf(100))))
                .andExpect(model().attribute("firstKilledNumbers", equalTo(BigInteger.valueOf(-1))))
                .andExpect(model().attribute("secondKilledNumbers", equalTo(BigInteger.valueOf(-1))))
                .andExpect(model().attribute("averageKills", equalTo(BigDecimal.valueOf(-1))));
    }
}
