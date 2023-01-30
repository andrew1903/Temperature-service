package ua.koziichuk.TemperatureService.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeasurementDTO {
    @NotNull
    @DecimalMax(value = "100.0", message = "Value should be less than 100.0")
    @DecimalMin(value = "-100.0", message = "Value should be greater than -100.0")
    private double value;

    @NotNull
    private boolean raining;

    @NotNull
    private SensorDTO sensor;

    @Override
    public String toString() {
        return "value: " + value +
                "raining: " + raining +
                "sensor: " + sensor.getName();
    }
}
