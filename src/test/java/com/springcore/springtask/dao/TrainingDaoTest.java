package com.springcore.springtask.dao;

import com.springcore.springtask.entity.Trainee;
import com.springcore.springtask.entity.Trainer;
import com.springcore.springtask.entity.Training;
import com.springcore.springtask.entity.TrainingType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrainingDaoTest {
    @Autowired
    private TrainingDao trainingDao;

    @BeforeEach
    void deleteDataFromDao(){
        for(Training training : trainingDao.findAll()){
            if(trainingDao.containsKey(training.getTrainingName())){
                trainingDao.delete(training.getTrainingName());
            }
        }
    }

    @Test
    void containsTrainingTest(){
        Trainee trainee = new Trainee();
        trainee.setUserId("trainee");

        Trainer trainer = new Trainer();
        trainer.setUserId("trainer");
        Training training = new Training(trainee, trainer,
                "myFirstTraining", TrainingType.AGILITY, LocalDate.now(), 5);

        trainingDao.create(training);

        assertTrue(trainingDao.containsKey("myFirstTraining"));
    }
    @Test
    void createTrainingTest(){
        Trainee trainee = new Trainee();
        trainee.setUserId("trainee");

        Trainer trainer = new Trainer();
        trainer.setUserId("trainer");
        Training training = new Training(trainee, trainer,
                "myFirstTraining", TrainingType.AGILITY, LocalDate.now(), 5);

        trainingDao.create(training);

        Training newTraining = trainingDao.select(training.getTrainingName());

        assertEquals(training.getTrainingName(), newTraining.getTrainingName());
        assertEquals(training.getTrainingDuration(), newTraining.getTrainingDuration());
        assertEquals(training.getTraineeId(), newTraining.getTraineeId());
        assertEquals(training.getTrainerId(), newTraining.getTrainerId());
    }
    @Test
    void createTrainingFailTest(){
        Trainee trainee = new Trainee();
        trainee.setUserId("trainee");

        Trainer trainer = new Trainer();
        Training training = new Training(trainee, trainer,
                "myFirstTraining", TrainingType.AGILITY, LocalDate.now(), 5);

        assertThrows(IllegalArgumentException.class, () -> trainingDao.create(training));
    }
    @Test
    void selectTrainingTest(){
        Trainee trainee = new Trainee();
        trainee.setUserId("trainee");

        Trainer trainer = new Trainer();
        trainer.setUserId("trainer");
        Training training = new Training(trainee, trainer,
                "myFirstTraining", TrainingType.AGILITY, LocalDate.now(), 5);

        trainingDao.create(training);

        Training expected = trainingDao.select(training.getTrainingName());

        assertNotNull(expected);
        assertEquals(expected, training);
    }
    @Test
    void selectTrainingFailTest(){
        assertThrows(IllegalArgumentException.class, () -> trainingDao.select("test"));
    }
    @Test
    void selectAllTraining(){
        Trainee trainee = new Trainee();
        trainee.setUserId("trainee");

        Trainer trainer = new Trainer();
        trainer.setUserId("trainer");
        Training training1 = new Training(trainee, trainer,
                "myFirstTraining", TrainingType.AGILITY, LocalDate.now(), 5);
        Training training2 = new Training(trainee, trainer,
                "mySecondTraining", TrainingType.AGILITY, LocalDate.now(), 5);

        trainingDao.create(training1);
        trainingDao.create(training2);

        assertEquals(2, trainingDao.findAll().size());
    }
    @Test
    void deleteTrainingFailTest(){
        assertThrows(IllegalArgumentException.class, () -> trainingDao.delete("test"));
    }
}
