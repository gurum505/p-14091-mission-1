package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("findAll")
    void t1() {
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("subject1", q.getSubject());
    }
    @Test
    @DisplayName("findById")
    void t2(){
        Optional<Question> oq = questionRepository.findById(1);
        assertEquals("subject1",oq.get().getSubject());

    }
    @Test
    @DisplayName("findBySubject")
    void t3() {
        Question question = questionRepository.findBySubject("subject1").get();
        // SELECT * FROM question WHERE subject = 'sbb가 무엇인가요?'
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectAndContent")
    void t4() {
        Question question = questionRepository.findBySubjectAndContent("subject1", "content1").get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("수정")
    @Rollback(false)
    void t6() {
        Question question = questionRepository.findById(1).get();
        question.setSubject("수정된 제목");

        TestTransaction.flagForCommit(); // 다음 트랜잭션을 커밋하도록 설정
        TestTransaction.end();

        Question question2 = questionRepository.findById(1).get();

        assertThat(question2.getSubject()).contains("수정된 제목");

        // 검증 후 다시 롤백
        TestTransaction.start(); // 새 트랜잭션 시작
        question2.setSubject("subject1"); // 또는 원래 값으로 복원
        TestTransaction.flagForCommit();
        TestTransaction.end();

    }

    @Test
    @DisplayName("답변 생성")
    void t8() {
        Question question = questionRepository.findById(2).get();

        Answer answer = new Answer();
        answer.setContent("네 자동으로 생성됩니다.");
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);

        assertThat(answer.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("답변 생성 by oneToMany")
    void t9() {
        Question question = questionRepository.findById(2).get();

        int beforeCount = question.getAnswers().size();

        Answer newAnswer = question.addAnswer("answer1");

        // 트랜잭션이 종료된 이후에 DB에 반영되기 때문에 현재는 일단 0으로 설정된다.
        assertThat(newAnswer.getId()).isEqualTo(0);

        int afterCount = question.getAnswers().size();

        assertThat(afterCount).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("답변 조회")
    void t10() {
        Answer answer = answerRepository.findById(1).get();

        assertThat(answer.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("답변 조회 by oneToMany")
    void t11() {
        Question question = questionRepository.findById(2).get();

        List<Answer> answers = question.getAnswers();
        assertThat(answers).hasSize(1);

        Answer answer = answers.get(0);
        assertThat(answer.getContent()).isEqualTo("answer1");
    }

    @Test
    @DisplayName("findAnswer by question")
    void t12() {
        Question question = questionRepository.findById(2).get();

        Answer answer1 = question.getAnswers().get(0);

        assertThat(answer1.getId()).isEqualTo(1);
    }


}
