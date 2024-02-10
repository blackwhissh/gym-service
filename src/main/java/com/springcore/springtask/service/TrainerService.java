package com.springcore.springtask.service;

import com.springcore.springtask.dao.InMemoryStorage;
import com.springcore.springtask.entity.Trainer;
import com.springcore.springtask.entity.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.springcore.springtask.Utils.generateUsername;
import static com.springcore.springtask.Utils.usernameExists;

@Service
public class TrainerService {
    private InMemoryStorage inMemoryStorage;

    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    public void createTrainer(String firstName, String lastName, Boolean isActive,
                              TrainingType specialization, String userId) {
        Trainer trainer = new Trainer(firstName, lastName, isActive, specialization, userId);
        boolean contains = usernameExists(inMemoryStorage.getUsernames(), trainer);
        trainer.setUsername(generateUsername(trainer.getFirstName(), trainer.getLastName(), contains));
        inMemoryStorage.getTrainerRepository().create(trainer);
    }

    public Trainer selectTrainer(String key) {
        return inMemoryStorage.getTrainerRepository().select(key);
    }

    public void updateTrainer(String key, Trainer newTrainer) {
        Trainer trainer = new Trainer();
        if (!inMemoryStorage.getTrainerRepository().containsKey(key)) {
            throw new NoSuchElementException("Wrong Key, Update Failed!");
        }
        boolean contains = usernameExists(inMemoryStorage.getUsernames(), newTrainer);

        trainer.setUsername(generateUsername(newTrainer.getFirstName(), newTrainer.getLastName(), contains));
        trainer.setPassword(selectTrainer(key).getPassword());
        trainer.setFirstName(newTrainer.getFirstName());
        trainer.setLastName(newTrainer.getLastName());
        trainer.setSpecialization(newTrainer.getSpecialization());
        trainer.setActive(newTrainer.getActive());

        inMemoryStorage.getTrainerRepository().update(key, trainer);
    }

}
