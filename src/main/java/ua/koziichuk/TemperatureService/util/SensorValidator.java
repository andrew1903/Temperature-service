package ua.koziichuk.TemperatureService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.koziichuk.TemperatureService.dto.SensorDTO;
import ua.koziichuk.TemperatureService.model.Sensor;
import ua.koziichuk.TemperatureService.services.SensorService;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        Optional<Sensor> sensor = sensorService.findByName(sensorDTO.getName());

        if (sensor.isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name is already exist!");
        }
    }
}
