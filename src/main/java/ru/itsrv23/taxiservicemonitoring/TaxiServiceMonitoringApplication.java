package ru.itsrv23.taxiservicemonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TaxiServiceMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiServiceMonitoringApplication.class, args);
    }

}
