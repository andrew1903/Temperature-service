package ua.koziichuk.TemperatureService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.koziichuk.TemperatureService.model.Measurement;
import ua.koziichuk.TemperatureService.model.Sensor;
import ua.koziichuk.TemperatureService.repositories.MeasurementRepository;
import ua.koziichuk.TemperatureService.util.MeasurementNotCreatedException;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementsService {

    private final SensorService sensorService;
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementsService(SensorService sensorService, MeasurementRepository measurementRepository) {
        this.sensorService = sensorService;
        this.measurementRepository = measurementRepository;
    }

    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public void enrichMeasurement(Measurement measurement) {
        String name = measurement.getSensor().getName();
        Optional<Sensor> sensor = sensorService.findByName(name);

        if (sensor.isEmpty())
            throw new MeasurementNotCreatedException("Sensor with this name not found");

        measurement.setSensor(sensor.get());
    }
}
