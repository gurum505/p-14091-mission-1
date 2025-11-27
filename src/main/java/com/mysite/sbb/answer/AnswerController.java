package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable  Integer id,
                               @RequestParam(value = "content") String content) {
        Question q = this.questionService.getQuestion(id);
        this.answerService.create(q,content);
        return "redirect:/question/detail/%s".formatted(id);
    }

    @PostMapping("/create/{id}")
    public String createAnswer(
            Model model,
            @PathVariable int id,
            @Valid AnswerForm answerForm, BindingResult bindingResult
    ) {
        Question question = this.questionService.getQuestion(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question,answerForm.getContent());
        return "redirect:/question/detail/%s".formatted(id);
    }



}

