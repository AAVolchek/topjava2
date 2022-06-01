package com.github.aavolchek.restaurantvoting.web.voice;

import com.github.aavolchek.restaurantvoting.model.Voice;
import com.github.aavolchek.restaurantvoting.util.JsonUtil;
import com.github.aavolchek.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"time-limit.voice=00:00:00"})
class UserVoiceControllerAfterTimeLimitTest extends UserVoiceControllerTest {
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        Voice updated = VoiceTestData.getUpdated();
        perform(MockMvcRequestBuilders
                .put(REST_URL + VoiceTestData.VOICE_ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(
                        containsString("It is impossible to vote for the restaurant. The voting ended at")));
    }
}
