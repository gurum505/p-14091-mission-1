package com.mysite.sbb.question;


import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private LocalDateTime createDate;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    //PERSIST는 안쓰는게 더 좋다
    @OneToMany(mappedBy = "question",cascade = {CascadeType.REMOVE,CascadeType.PERSIST} )
    private List<Answer> answers = new ArrayList<>(); // 초기화안하면 new Quest().addAnswer()에서 오류가능성

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    private Set<SiteUser> voter;

    public Answer addAnswer(String s) {
        Answer answer = new Answer();
        answer.setContent(s);
        answer.setQuestion(this);
        answer.setCreateDate(LocalDateTime.now());
        answers.add(answer);
        return answer;
    }
}
