package ua.koziichuk.TemperatureService.util;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String message) {
        super(message);
    }
}
