package ua.koziichuk.TemperatureService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.koziichuk.TemperatureService.model.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}
