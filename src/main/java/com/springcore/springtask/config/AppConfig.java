package com.springcore.springtask.config;

import com.springcore.springtask.dao.InMemoryStorage;
import com.springcore.springtask.dao.TraineeDao;
import com.springcore.springtask.dao.TrainerDao;
import com.springcore.springtask.dao.TrainingDao;
import com.springcore.springtask.service.GymServiceFacade;
import com.springcore.springtask.service.TraineeService;
import com.springcore.springtask.service.TrainerService;
import com.springcore.springtask.service.TrainingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean(initMethod = "init")
    public TrainerDao trainerDao(){
        return new TrainerDao();
    }
    @Bean(initMethod = "init")
    public TraineeDao traineeDao(){
        return new TraineeDao();
    }
    @Bean(initMethod = "init")
    public TrainingDao trainingDao(){
        return new TrainingDao();
    }
    @Bean
    public InMemoryStorage inMemoryStorage(){
        return new InMemoryStorage();
    }
    @Bean
    public TraineeService traineeService(){
        return new TraineeService();
    }
    @Bean
    public TrainerService trainerService(){
        return new TrainerService();
    }
    @Bean
    public TrainingService trainingService(){
        return new TrainingService();
    }
    @Bean
    public GymServiceFacade gymServiceFacade(){
        return new GymServiceFacade(traineeService(),trainerService(),trainingService());
    }
}
