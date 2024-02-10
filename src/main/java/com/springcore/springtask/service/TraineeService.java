package com.springcore.springtask.service;

import com.springcore.springtask.dao.InMemoryStorage;
import com.springcore.springtask.entity.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static com.springcore.springtask.Utils.generateUsername;
import static com.springcore.springtask.Utils.usernameExists;

@Service
public class TraineeService {
    private InMemoryStorage inMemoryStorage;

    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    public void createTrainee(String firstName, String lastName, Boolean isActive,
                              LocalDate dob, String address, String userId) {
        Trainee trainee = new Trainee(firstName, lastName, isActive, dob, address, userId);
        boolean contains = usernameExists(inMemoryStorage.getUsernames(), trainee);
        trainee.setUsername(generateUsername(trainee.getFirstName(), trainee.getLastName(), contains));
        inMemoryStorage.getTraineeRepository().create(trainee);
    }

    public Trainee selectTrainee(String key) {
        return inMemoryStorage.getTraineeRepository().select(key);
    }

    public void updateTrainee(String key, Trainee newTrainee) {
        Trainee trainee = new Trainee();
        if (!inMemoryStorage.getTraineeRepository().containsKey(key)) {
            throw new NoSuchElementException("Wrong Key, Update Failed!");
        }
        boolean contains = usernameExists(inMemoryStorage.getUsernames(), newTrainee);

        trainee.setUsername(generateUsername(newTrainee.getFirstName(), newTrainee.getLastName(), contains));
        trainee.setPassword(selectTrainee(key).getPassword());
        trainee.setFirstName(newTrainee.getFirstName());
        trainee.setLastName(newTrainee.getLastName());
        trainee.setAddress(newTrainee.getAddress());
        trainee.setDob(newTrainee.getDob());
        trainee.setActive(newTrainee.getActive());

        inMemoryStorage.getTraineeRepository().update(key, trainee);
    }

    public void deleteTrainee(String key) {
        inMemoryStorage.getTraineeRepository().delete(key);
    }
}
