package com.project.simi.mock.diary;

import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.domain.EmotionOfEpisode;
import com.project.simi.domain.diary.domain.EmotionType;
import com.project.simi.domain.diary.repository.command.DiaryCommandRepository;
import com.project.simi.domain.user.domain.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;

@TestComponent
public class MockDiaryFactory extends SuperIntegrationTest {

    @Autowired
    private DiaryCommandRepository diaryCommandRepository;

    @Autowired
    @Qualifier("mockDefaultUser")
    private User mockDefaultUser;
    @Bean(name = "mockDefaultDiary")
    public Diary mockDefaultDiary() {
        return diaryCommandRepository.save(
            Diary.createOf(
                "episode",
                "thoughtOfEpisode",
                new EmotionOfEpisode(EmotionType.ANGRY, List.of("details")),
                "resultOfEpisode",
                "empathyResponse",
                mockDefaultUser.getId()
            )
        );

    }
}
