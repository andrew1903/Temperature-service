package ua.koziichuk.TemperatureService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sensor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurementS;
}
