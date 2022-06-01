package com.github.aavolchek.restaurantvoting.web.voice;

import com.github.aavolchek.restaurantvoting.model.Voice;
import com.github.aavolchek.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.aavolchek.restaurantvoting.web.voice.VoiceTestData.REST1_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"time-limit.voice=23:59:59"})
class UserVoiceControllerBeforeTimeLimitTest extends UserVoiceControllerTest {
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        Voice updated = VoiceTestData.getUpdated();
        perform(MockMvcRequestBuilders
                        .put(REST_URL + VoiceTestData.VOICE_ADMIN_ID)
                        .param("restaurantId", Integer.toString(REST1_ID)))
                        .andDo(print())
                        .andExpect(status().isNoContent());
        VoiceTestData.VOICE_MATCHER.assertMatch(voiceRepository.getById(VoiceTestData.VOICE_ADMIN_ID), updated);
    }
}
