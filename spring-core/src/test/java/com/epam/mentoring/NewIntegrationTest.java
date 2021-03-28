package com.epam.mentoring;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.mentoring.controller.EventController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(EventController.class)
public class NewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findEventByIdShouldReturnEventFromPreLoadedBean() throws Exception {
        this.mockMvc.perform(get("/events/?event-id=11")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("March Event")));
    }

    @Test
    public void findEventByTitleShouldReturnThreeEventsFromPreLoadedBean() throws Exception {
        this.mockMvc.perform(get("/events/title?title=Event&page-size=10&page-num=0")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("January Event")))
                .andExpect(content().string(containsString("March Event")))
                .andExpect(content().string(containsString("October Event")));
    }

    @Test
    public void findEventByDayShouldReturnEventFromPreLoadedBean() throws Exception {
        this.mockMvc.perform(get("/events/day?day=2022-01-01&page-size=10&page-num=0")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("January Event")));
    }


}
