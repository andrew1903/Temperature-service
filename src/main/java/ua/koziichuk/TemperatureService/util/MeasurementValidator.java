package ua.koziichuk.TemperatureService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.koziichuk.TemperatureService.dto.MeasurementDTO;
import ua.koziichuk.TemperatureService.model.Sensor;
import ua.koziichuk.TemperatureService.services.SensorService;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        String sensorName = measurementDTO.getSensor().getName();

        Optional<Sensor> sensor = sensorService.findByName(sensorName);

        if (sensor.isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with this name isn`t exist!");
        }
    }
}
