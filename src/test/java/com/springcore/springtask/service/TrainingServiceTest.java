package com.springcore.springtask.service;

import com.springcore.springtask.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TrainingServiceTest {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private GymServiceFacade gymServiceFacade;

    @Test
    void createTrainingTest() {
        gymServiceFacade.getTraineeService().createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah123");

        gymServiceFacade.getTrainerService().createTrainer("James", "Doe", true,
                TrainingType.AGILITY, "james123");

        assertDoesNotThrow(() -> trainingService.createTraining("sarah123", "james123", "myFirstTraining",
                TrainingType.AGILITY, LocalDate.now(), 5));
    }

    @Test
    void selectTrainingTest() {
        gymServiceFacade.getTraineeService().createTrainee("Sarah", "Doe", true,
                LocalDate.now(), "Georgia", "sarah123");

        gymServiceFacade.getTrainerService().createTrainer("James", "Doe", true,
                TrainingType.AGILITY, "james123");

        trainingService.createTraining("sarah123", "james123", "myFirstTraining",
                TrainingType.AGILITY, LocalDate.now(), 5);

        assertEquals("myFirstTraining", trainingService.selectTraining("myFirstTraining").getTrainingName());
    }
}