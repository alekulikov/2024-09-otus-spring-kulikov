package ru.otus.hw.service;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionsConverterImpl implements QuestionsConverter {
    @Override
    public String convert(List<Question> questions) {
        return IntStream.range(0, questions.size()).boxed()
                .map(i -> convertQuestion(i + 1, questions.get(i)))
                .collect(Collectors.joining("\n"));
    }

    private String convertQuestion(int number, Question question) {
        return String.format("%d. %s\n%s", number, question.text(), convertAnswers(question.answers()));
    }

    private String convertAnswers(List<Answer> answers) {
        return IntStream.range(0, answers.size()).boxed()
                .map(i -> String.format("\t%d) %s", i + 1, answers.get(i).text()))
                .collect(Collectors.joining("\n"));
    }
}
