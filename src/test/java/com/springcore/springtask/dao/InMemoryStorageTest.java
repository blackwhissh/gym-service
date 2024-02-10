package com.springcore.springtask.dao;

import com.springcore.springtask.entity.Trainee;
import com.springcore.springtask.entity.Trainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InMemoryStorageTest {
    @Autowired
    private InMemoryStorage inMemoryStorage;
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainingDao trainingDao;


    @Test
    void getTrainerRepositoryTest() {
        assertEquals(inMemoryStorage.getTrainerRepository(), trainerDao);
    }

    @Test
    void getTraineeRepositoryTest() {
        assertEquals(inMemoryStorage.getTraineeRepository(), traineeDao);
    }

    @Test
    void getTrainingRepositoryTest() {
        assertEquals(inMemoryStorage.getTrainingRepository(), trainingDao);
    }

    @Test
    void getUsernamesTest() {
        for(Trainee trainee : traineeDao.findAll()){
            traineeDao.delete(trainee.getUserId());
        }
        for(Trainer trainer : trainerDao.findAll()){
            trainerDao.delete(trainer.getUserId());
        }

        assertEquals(0, inMemoryStorage.getUsernames().size());
    }
}