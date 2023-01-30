package ua.koziichuk.TemperatureService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SensorDTO {

    @NotEmpty
    @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters")
    private String name;
}
