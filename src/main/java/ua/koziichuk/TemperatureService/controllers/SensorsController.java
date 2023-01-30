package ua.koziichuk.TemperatureService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.koziichuk.TemperatureService.dto.SensorDTO;
import ua.koziichuk.TemperatureService.model.Sensor;
import ua.koziichuk.TemperatureService.services.SensorService;
import ua.koziichuk.TemperatureService.util.SensorErrorResponse;
import ua.koziichuk.TemperatureService.util.SensorNotCreatedException;
import ua.koziichuk.TemperatureService.util.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> sensorRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult) {

        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMessage.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(), //"Person with this name is already exist",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
