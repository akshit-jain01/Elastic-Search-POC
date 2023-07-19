package com.ELK_POC.elasticSearch.config;

import com.ELK_POC.elasticSearch.entity.Car;
import com.ELK_POC.elasticSearch.repository.CarElasticRepository;
import com.ELK_POC.elasticSearch.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Component
public class CarElasticDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(CarElasticDataSource.class);

    @Autowired
    private CarElasticRepository carRepository;

    @Autowired
    @Qualifier("randomCarService")
    private CarService carService;

    @Autowired
    @Qualifier("webClientElasticsearch")
    private WebClient webClient;

    @EventListener(ApplicationReadyEvent.class)
    public void populateData() {
//        var response = webClient.delete()
//                .uri("/elastic-search")
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();

//        LOG.info("End delete with response {}", response);

        var cars = new ArrayList<Car>();
        for (int i = 0; i < 10_000; i++) {
            cars.add(carService.generateCar());
        }

        carRepository.saveAll(cars);
        LOG.info("Saved {} cars", carRepository.count());
    }
}