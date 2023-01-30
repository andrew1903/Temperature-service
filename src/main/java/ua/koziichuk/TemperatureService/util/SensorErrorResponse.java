package ua.koziichuk.TemperatureService.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SensorErrorResponse {

    private String message;
    private long timestamp;
}
