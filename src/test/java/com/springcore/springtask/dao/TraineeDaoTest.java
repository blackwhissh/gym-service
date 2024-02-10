package com.springcore.springtask.dao;

import com.springcore.springtask.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TraineeDaoTest {
    @Autowired
    private TraineeDao traineeDao;

    @BeforeEach
    void deleteDataFromDao(){
        for(Trainee trainee : traineeDao.findAll()){
            if(traineeDao.containsKey(trainee.getUserId())){
                traineeDao.delete(trainee.getUserId());
            }
        }
    }
    @Test
    void containsTraineeTest(){
        Trainee trainee = new Trainee("John", "Doe", "John.Doe",
                "password", true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");

        traineeDao.create(trainee);

        assertTrue(traineeDao.containsKey("john123"));
    }
    @Test
    void selectTraineeTest(){
        Trainee trainee = new Trainee("John", "Doe", "John.Doe",
                "password", true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");

        traineeDao.create(trainee);

        Trainee result = traineeDao.select("john123");
        assertNotNull(result);
        assertEquals(trainee, result);
    }
    @Test
    void selectTraineeFailTest(){
        assertThrows(IllegalArgumentException.class, () -> traineeDao.select("test"));
    }
    @Test
    void selectAllTraineesTest(){
        Trainee trainee1 = new Trainee("John", "Doe",
                true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");
        Trainee trainee2 = new Trainee("James", "Webb",
                true, LocalDate.of(2003,8,8),
                "Tbilisi", "james123");

        traineeDao.create(trainee1);
        traineeDao.create(trainee2);

        assertEquals(2, traineeDao.findAll().size());
    }
    @Test
    void createTraineeTest(){
        Trainee trainee1 = new Trainee("John", "Doe",
                true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");

        traineeDao.create(trainee1);

        Trainee trainee2 = traineeDao.select("john123");

        assertEquals(trainee1.getAddress(), trainee2.getAddress());
        assertEquals(trainee1.getFirstName(), trainee2.getFirstName());
        assertEquals(trainee1.getLastName(), trainee2.getLastName());
        assertEquals(trainee1.getActive(), trainee2.getActive());
        assertEquals(trainee1.getDob(), trainee2.getDob());
        assertEquals(trainee1.getUserId(), trainee2.getUserId());
    }
    @Test
    void createTraineeFailTest(){
        Trainee trainee = new Trainee("John", "Doe",
                true, LocalDate.of(2003,8,8),
                "Tbilisi", null);

        assertThrows(IllegalArgumentException.class, () -> traineeDao.create(trainee));
    }
    @Test
    void deleteTraineeTest(){
        Trainee trainee = new Trainee("John", "Doe", "John.Doe",
                "password", true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");

        traineeDao.create(trainee);

        traineeDao.delete("john123");

        assertEquals(0, traineeDao.findAll().size());
    }
    @Test
    void deleteTraineeFailTest(){
        Trainee trainee = new Trainee("John", "Doe", "John.Doe",
                "password", true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");

        traineeDao.create(trainee);

        assertThrows(IllegalArgumentException.class, () -> traineeDao.delete("john1234"));
    }

    @Test
    void updateTraineeTest(){
        Trainee trainee = new Trainee("John", "Doe", "John.Doe",
                "password", true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");
        Trainee newTrainee = new Trainee("Nick", "Dave", "Nick.Dave",
                "password", true, LocalDate.of(2003,8,8),
                "Tbilisi", "john123");

        traineeDao.create(trainee);
        traineeDao.update("john123", newTrainee);

        assertNotEquals(trainee.getFirstName(), traineeDao.select("john123").getFirstName());
        assertNotEquals(trainee.getLastName(), traineeDao.select("john123").getLastName());
        assertEquals(trainee.getDob(), traineeDao.select("john123").getDob());
    }
    @Test
    void updateTraineeFailTest(){
        Trainee trainee = new Trainee("John", "Doe", "John.Doe",
                "password", true, LocalDate.of(2003,8,8),
                "Tbilisi", null);

        assertThrows(IllegalArgumentException.class, () -> traineeDao.update(trainee.getUserId(), trainee));
    }

}
