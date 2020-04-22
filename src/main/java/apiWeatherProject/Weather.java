package apiWeatherProject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames=false)
public class Weather {

    @ToString.Exclude
    private int temp;

    @ToString.Exclude
    private int temp_max;

    @ToString.Exclude
    private int averageTemperature;

    private String clouds;

    private String wind;

    private String pressure;

    private String visibility;

    private String weatherDescription;


}