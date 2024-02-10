package com.springcore.springtask.service;

import com.springcore.springtask.entity.Trainee;
import com.springcore.springtask.entity.Trainer;
import com.springcore.springtask.entity.TrainingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TrainerServiceTest {
    @Autowired
    private TrainerService trainerService;

    @Test
    void selectTrainerTest(){
        trainerService.createTrainer("Sarah", "Doe", true,
                TrainingType.AGILITY, "sarah123");

        assertEquals("Sarah", trainerService.selectTrainer("sarah123").getFirstName());
    }
    @Test
    void createTrainerWithSimilarUsernameTest() {
        trainerService.createTrainer("Sarah", "Doe", true,
                TrainingType.AGILITY, "sarah123");
        trainerService.createTrainer("Sarah", "Doe", true,
                TrainingType.AGILITY, "sarah1234");

        assertEquals("Sarah.Doe0", trainerService.selectTrainer("sarah1234").getUsername());
    }
    @Test
    void createTrainerWithDifferentUsernameTest() {
        trainerService.createTrainer("Sarah", "Doe", true,
                TrainingType.AGILITY, "sarah123");
        trainerService.createTrainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");

        assertNotEquals(trainerService.selectTrainer("sarah123").getUsername(),
                trainerService.selectTrainer("john123").getUsername());
    }

    @Test
    void updateTrainerTest() {
        trainerService.createTrainer("Sarah", "Doe", true,
                TrainingType.AGILITY, "sarah123");

        Trainer newTrainee = new Trainer("James", "Doe", "James.Doe", "password", true,
                TrainingType.BALANCE);

        trainerService.updateTrainer("sarah123", newTrainee);

        assertEquals("James", trainerService.selectTrainer("sarah123").getFirstName());
    }
    @Test
    void updateTrainerFailTest(){
        assertThrows(NoSuchElementException.class, () -> trainerService.updateTrainer("test", new Trainer()));
    }
}