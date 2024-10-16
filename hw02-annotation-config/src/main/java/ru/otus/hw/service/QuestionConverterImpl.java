package ru.otus.hw.service;

import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class QuestionConverterImpl implements QuestionConverter {
    @Override
    public String convert(Question question) {
        return convertQuestion(question);
    }

    private String convertQuestion(Question question) {
        return String.format("%s\n%s", question.text(), convertAnswers(question.answers()));
    }

    private String convertAnswers(List<Answer> answers) {
        return IntStream.range(0, answers.size()).boxed()
                .map(i -> String.format("\t%d) %s", i + 1, answers.get(i).text()))
                .collect(Collectors.joining("\n"));
    }
}
