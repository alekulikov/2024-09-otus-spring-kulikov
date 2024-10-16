package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {

    private static final String FILE_NAME = "test.csv";

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        when(fileNameProvider.getTestFileName()).thenReturn(FILE_NAME);
    }

    @Test
    @DisplayName("Check integration CsvQuestionDao with filesystem")
    void testFindAll() {
        var questions = csvQuestionDao.findAll();
        assertEquals(3, questions.size());
    }

    @Test
    @DisplayName("Check error message when file not found")
    public void testFindAllWithFileNotFound() {
        when(fileNameProvider.getTestFileName()).thenReturn("wrongFileName.csv");
        assertThrowsExactly(IllegalArgumentException.class,
                () -> csvQuestionDao.findAll(),
                "file not found! wrongFileName.csv");
    }
}
