package ua.koziichuk.TemperatureService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "measurement")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMax(value = "100.0", message = "Value should be less than 100.0")
    @DecimalMin(value = "-100.0", message = "Value should be greater than -100.0")
    private double value;

    @NotNull
    private boolean raining;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;
}
