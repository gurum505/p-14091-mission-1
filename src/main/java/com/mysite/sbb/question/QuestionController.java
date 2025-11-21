package com.mysite.sbb.question;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;

//    @GetMapping("/question/list")
//    @ResponseBody
//    public String list(){
//        List<Question> questions =  questionRepository.findAll();
//        String questionLi = questions.stream().map(s->"<li class=\"text-red-500 font-bold p-2 m-2\" >%s</li>".formatted(s.getSubject())).collect(Collectors.joining("\n"));
//        return  """
//                <!DOCTYPE html>
//                <html>
//                <head>
//                    <meta charset="UTF-8">
//                    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
//                    <style>
//                        @import url("https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/variable/pretendardvariable-dynamic-subset.min.css");
//
//                        html {
//                          font-family: "Pretendard Variable";
//                        }
//                    </style>
//                    <title>질문 목록</title>
//                </head>
//                <body>
//                    <ul class=\"flex justify-center\">
//                        %s
//                    </ul>
//                </body>
//                </html>
//                """.formatted(questionLi);
//    }


    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questions =  questionRepository.findAll();
        model.addAttribute("questions",questions);

        return "question_list";
    }

}
