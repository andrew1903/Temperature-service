package ua.koziichuk.TemperatureService.util;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String message) {
        super(message);
    }
}
