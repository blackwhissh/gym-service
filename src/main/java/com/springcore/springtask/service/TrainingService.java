package com.springcore.springtask.service;

import com.springcore.springtask.dao.InMemoryStorage;
import com.springcore.springtask.entity.Trainee;
import com.springcore.springtask.entity.Trainer;
import com.springcore.springtask.entity.Training;
import com.springcore.springtask.entity.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TrainingService {
    private InMemoryStorage inMemoryStorage;
    private TraineeService traineeService;
    private TrainerService trainerService;
    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }
    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }
    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }
    public void createTraining(String traineeId, String trainerId, String trainingName,
                               TrainingType trainingType, LocalDate trainingDate, Integer trainingDuration){
        Trainee trainee = traineeService.selectTrainee(traineeId);
        Trainer trainer = trainerService.selectTrainer(trainerId);
        Training training = new Training(trainee, trainer, trainingName,
                trainingType, trainingDate, trainingDuration);
        inMemoryStorage.getTrainingRepository().create(training);
    }
    public Training selectTraining(String key){
        return inMemoryStorage.getTrainingRepository().select(key);
    }

}
