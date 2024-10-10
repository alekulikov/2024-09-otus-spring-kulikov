package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.QuestionsConverter;
import ru.otus.hw.service.QuestionsConverterImpl;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    @Mock
    private IOService ioService;
    @Mock
    private QuestionDao questionDao;
    @Spy
    private QuestionsConverter questionConverter = new QuestionsConverterImpl();

    @InjectMocks
    private TestServiceImpl testService;

    List<Question> mockQuestions = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mockQuestions.clear();
        mockQuestions.add(getMockQuestion());
        when(questionDao.findAll()).thenReturn(mockQuestions);
    }

    @Test
    @DisplayName("Check integration TestServiceImpl with other services")
    public void testExecuteTest() {
        testService.executeTest();
        InOrder inOrder = inOrder(ioService, questionDao, questionConverter);

        inOrder.verify(ioService).printLine("");
        inOrder.verify(ioService).printFormattedLine("Please answer the questions below%n");
        inOrder.verify(questionDao).findAll();
        inOrder.verify(questionConverter).convert(mockQuestions);
        inOrder.verify(ioService).printFormattedLine("1. Test\n\t1) yes\n\t2) no");
    }

    private Question getMockQuestion() {
        return new Question("Test", List.of(new Answer("yes", true),
                new Answer("no", false)));
    }
}