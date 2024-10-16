package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    @Mock
    private IOService ioService;
    @Mock
    private QuestionDao questionDao;
    @Mock
    private QuestionConverter questionConverter = new QuestionConverterImpl();

    @InjectMocks
    private TestServiceImpl testService;

    private List<Question> mockQuestions = new ArrayList<>();
    private Student mockStudent;

    @BeforeEach
    public void setUp() {
        mockQuestions.clear();
        mockQuestions.add(getMockQuestion());
        mockStudent = getMockStudent();
        when(questionDao.findAll()).thenReturn(mockQuestions);
        when(questionConverter.convert(mockQuestions.get(0))).thenReturn("Converted question");
        when(ioService.readIntForRange(1, mockQuestions.get(0).answers().size(),
                "Incorrect answer number")).thenReturn(1);
    }

    @Test
    @DisplayName("Check integration TestServiceImpl with other services")
    public void testExecuteTestFor() {
        testService.executeTestFor(mockStudent);
        InOrder inOrder = inOrder(ioService, questionDao, questionConverter);

        inOrder.verify(ioService).printLine("");
        inOrder.verify(ioService).printFormattedLine("Please answer the questions below%n");
        inOrder.verify(questionDao).findAll();
        inOrder.verify(questionConverter).convert(mockQuestions.get(0));
        inOrder.verify(ioService).printFormattedLine("Converted question");
        inOrder.verify(ioService).readIntForRange(1, mockQuestions.get(0).answers().size(),
                "Incorrect answer number");
    }

    private Question getMockQuestion() {
        return new Question("Test", List.of(new Answer("yes", true),
                new Answer("no", false)));
    }

    private Student getMockStudent() {
        return new Student("Test", "User");
    }
}