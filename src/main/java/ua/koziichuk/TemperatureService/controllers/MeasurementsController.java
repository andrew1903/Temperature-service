package ua.koziichuk.TemperatureService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.koziichuk.TemperatureService.dto.MeasurementDTO;
import ua.koziichuk.TemperatureService.model.Measurement;
import ua.koziichuk.TemperatureService.services.MeasurementsService;
import ua.koziichuk.TemperatureService.util.MeasurementNotCreatedException;
import ua.koziichuk.TemperatureService.util.MeasurementValidator;
import ua.koziichuk.TemperatureService.util.SensorErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final ModelMapper modelMapper;
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(ModelMapper modelMapper,
                                  MeasurementsService measurementsService,
                                  MeasurementValidator measurementValidator) {
        this.modelMapper = modelMapper;
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {

        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(errorMessage.toString());
        }

        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementsService.enrichMeasurement(measurement);

        measurementsService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public Long rainyDaysCount() {
        return measurementsService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(MeasurementNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(), //"Person with this name is already exist",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
