package com.springcore.springtask.dao;


import com.springcore.springtask.entity.Trainer;
import com.springcore.springtask.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrainerDaoTest {
    @Autowired
    private TrainerDao trainerDao;

    @BeforeEach
    void deleteDataFromDao(){
        for(Trainer trainer : trainerDao.findAll()){
            if(trainerDao.containsKey(trainer.getUserId())){
                trainerDao.delete(trainer.getUserId());
            }
        }
    }
    @Test
    void containsTrainerTest(){
        Trainer trainer = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");

        trainerDao.create(trainer);

        assertTrue(trainerDao.containsKey("john123"));
    }
    @Test
    void selectTrainerTest(){
        Trainer trainer = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");

        trainerDao.create(trainer);

        Trainer result = trainerDao.select("john123");
        assertNotNull(result);
        assertEquals(trainer, result);
    }
    @Test
    void selectTrainerFailTest(){
        assertThrows(IllegalArgumentException.class, () -> trainerDao.select("test"));
    }
    @Test
    void selectAllTrainersTest(){
        Trainer trainer1 = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");
        Trainer trainer2 = new Trainer("George", "Washington", true,
                TrainingType.AGILITY, "george123");

        trainerDao.create(trainer1);
        trainerDao.create(trainer2);

        assertEquals(2, trainerDao.findAll().size());
    }
    @Test
    void createTrainerTest(){
        Trainer trainer1 = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");

        trainerDao.create(trainer1);

        Trainer trainer2 = trainerDao.select("john123");

        assertEquals(trainer1.getFirstName(), trainer2.getFirstName());
        assertEquals(trainer1.getLastName(), trainer2.getLastName());
        assertEquals(trainer1.getActive(), trainer2.getActive());
        assertEquals(trainer1.getUserId(), trainer2.getUserId());
    }
    @Test
    void createTrainerFailTest(){
        Trainer trainer = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, null);

        assertThrows(IllegalArgumentException.class, () -> trainerDao.create(trainer));
    }
    @Test
    void deleteTrainerTest(){
        Trainer trainer = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");

        trainerDao.create(trainer);

        trainerDao.delete("john123");

        assertEquals(0, trainerDao.findAll().size());
    }
    @Test
    void deleteTrainerFailTest(){
        Trainer trainer = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");

        trainerDao.create(trainer);

        assertThrows(IllegalArgumentException.class, () -> trainerDao.delete("john1234"));
    }

    @Test
    void updateTrainerTest(){
        Trainer trainer = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, "john123");
        Trainer newTrainer = new Trainer("Nick", "Johnson", true,
                TrainingType.AGILITY, "john123");

        trainerDao.create(trainer);
        trainerDao.update("john123", newTrainer);

        assertNotEquals(trainer.getFirstName(), trainerDao.select("john123").getFirstName());
        assertNotEquals(trainer.getLastName(), trainerDao.select("john123").getLastName());
        assertEquals(trainer.getSpecialization(), trainerDao.select("john123").getSpecialization());
    }
    @Test
    void updateTrainerFailTest(){
        Trainer trainer = new Trainer("John", "Doe", true,
                TrainingType.AGILITY, null);

        assertThrows(IllegalArgumentException.class, () -> trainerDao.update(trainer.getUserId(), trainer));
    }


}
