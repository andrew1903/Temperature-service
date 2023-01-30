package ua.koziichuk.TemperatureService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.koziichuk.TemperatureService.model.Sensor;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByName(String name);
}
