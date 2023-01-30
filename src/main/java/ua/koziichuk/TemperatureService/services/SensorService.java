package ua.koziichuk.TemperatureService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.koziichuk.TemperatureService.model.Sensor;
import ua.koziichuk.TemperatureService.repositories.SensorsRepository;

import java.util.Optional;

@Service
public class SensorService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }


    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
