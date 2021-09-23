package pl.bromanowski.airportapplication.external.controller;

import org.junit.jupiter.api.Test;
import pl.bromanowski.airportapplication.domain.model.WeightUnit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlightControllerTest extends ControllerTest {

    private static final String GET_FLIGHT_LOAD_WEIGHT_ENDPOINT = "/flights/{flightNumber}/weight";

    @Test
    public void getFlightLoadWeightWithoutProvidingDateParameter() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFlightLoadWeightWithWrongDateParameter() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 1L)
                        .param("date", "01.01.2000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFlightLoadWeightWithWrongWeightUnitParameter() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 1L)
                        .param("date", "2020-01-01")
                        .param("weightUnit", "xyz"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFlightLoadWeightWhenDataIsNotFound() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 1L)
                        .param("date", "2020-01-01"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFlightLoadWeightWhenFlightWithoutWeightIsFound() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 1L)
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(7))))
                .andExpect(jsonPath("$.baggageWeight.value").value("0.0"))
                .andExpect(jsonPath("$.baggageWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.cargoWeight.value").value("0.0"))
                .andExpect(jsonPath("$.cargoWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.totalWeight.value").value("0.0"))
                .andExpect(jsonPath("$.totalWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(status().isOk());
    }

    @Test
    public void getFlightLoadWeightWhenFlightWithBaggageButWithoutCargoIsFound() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 2L)
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(5))))
                .andExpect(jsonPath("$.baggageWeight.value").value("4.6"))
                .andExpect(jsonPath("$.baggageWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.cargoWeight.value").value("0.0"))
                .andExpect(jsonPath("$.cargoWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.totalWeight.value").value("4.6"))
                .andExpect(jsonPath("$.totalWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(status().isOk());
    }

    @Test
    public void getFlightLoadWeightWhenFlightWithCargoButWithoutBaggageIsFound() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 3L)
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(3))))
                .andExpect(jsonPath("$.baggageWeight.value").value("0.0"))
                .andExpect(jsonPath("$.baggageWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.cargoWeight.value").value("6.89"))
                .andExpect(jsonPath("$.cargoWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.totalWeight.value").value("6.89"))
                .andExpect(jsonPath("$.totalWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(status().isOk());
    }

    @Test
    public void getFlightLoadWeightWhenFlightWithBaggageAndCargoIsFound() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 4L)
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("$.baggageWeight.value").value("1.21"))
                .andExpect(jsonPath("$.baggageWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.cargoWeight.value").value("0.88"))
                .andExpect(jsonPath("$.cargoWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(jsonPath("$.totalWeight.value").value("2.09"))
                .andExpect(jsonPath("$.totalWeight.unit").value(WeightUnit.KILOGRAM.symbol()))
                .andExpect(status().isOk());
    }

    @Test
    public void getFlightLoadWeightInPoundsWhenFlightWithBaggageAndCargoIsFound() throws Exception {
        mockMvc.perform(get(GET_FLIGHT_LOAD_WEIGHT_ENDPOINT, 4L)
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(1)))
                        .param("weightUnit", WeightUnit.POUND.symbol()))
                .andExpect(jsonPath("$.baggageWeight.value").value("2.67"))
                .andExpect(jsonPath("$.baggageWeight.unit").value(WeightUnit.POUND.symbol()))
                .andExpect(jsonPath("$.cargoWeight.value").value("1.93"))
                .andExpect(jsonPath("$.cargoWeight.unit").value(WeightUnit.POUND.symbol()))
                .andExpect(jsonPath("$.totalWeight.value").value("4.6"))
                .andExpect(jsonPath("$.totalWeight.unit").value(WeightUnit.POUND.symbol()))
                .andExpect(status().isOk());
    }
}