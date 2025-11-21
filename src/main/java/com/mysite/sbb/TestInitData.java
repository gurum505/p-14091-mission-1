package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Profile("test")
@Configuration
@RequiredArgsConstructor
public class TestInitData {
    @Autowired
    @Lazy
    TestInitData self;

    private final QuestionRepository questionRepository;

    @Bean
    ApplicationRunner testInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    void work1() {
        if (questionRepository.count() > 0) return;

        Question q1 = new Question();
        q1.setSubject("subject1");
        q1.setContent("content1");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1); // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("subject2");
        q2.setContent("content1");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2); // 두번째 질문 저장

        q2.addAnswer("answer1");
    }
}

