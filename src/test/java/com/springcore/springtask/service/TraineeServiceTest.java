package com.springcore.springtask.service;

import com.springcore.springtask.entity.Trainee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TraineeServiceTest {
    @Autowired
    private TraineeService traineeService;
    @Test
    void selectTraineeTest(){
        traineeService.createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah123");

        assertEquals("Sarah", traineeService.selectTrainee("sarah123").getFirstName());
    }

    @Test
    void createTraineeWithSimilarUsernameTest(){
        traineeService.createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah123");
        traineeService.createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah1234");

        assertEquals("Sarah.Doe0",
                traineeService.selectTrainee("sarah1234").getUsername());
    }
    @Test
    void createTraineeWithDifferentUsernameTest(){
        traineeService.createTrainee("John", "Doe", true,
                LocalDate.now(), "Georgia", "john123");
        traineeService.createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah1234");

        assertNotEquals(traineeService.selectTrainee("john123").getUsername(),
                traineeService.selectTrainee("sarah1234").getUsername());
    }
    @Test
    void updateTraineeTest(){
        traineeService.createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah123");

        Trainee newTrainee = new Trainee("James", "Doe", "James.Doe", "password", true,
                LocalDate.now(), "Russia");

        traineeService.updateTrainee("sarah123", newTrainee);

        assertEquals("James", traineeService.selectTrainee("sarah123").getFirstName());
    }
    @Test
    void updateTraineeFailTest(){
        assertThrows(NoSuchElementException.class, () -> traineeService.updateTrainee("test", new Trainee()));
    }
    @Test
    void deleteTraineeTest(){
        traineeService.createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah123");

        traineeService.deleteTrainee("sarah123");

        assertThrows(IllegalArgumentException.class, () -> traineeService.selectTrainee("sarah123"));
    }
}
