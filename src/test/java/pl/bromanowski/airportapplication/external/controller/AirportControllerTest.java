package pl.bromanowski.airportapplication.external.controller;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AirportControllerTest extends ControllerTest {

    private static final String GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT = "/airports/{iataCode}/flights";

    @Test
    public void getAirportFlightsDetailsWithoutProvidingDateParameter() throws Exception {
        mockMvc.perform(get(GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT, "CODE1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAirportFlightsDetailsWithWrongDateParameter() throws Exception {
        mockMvc.perform(get(GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT, "CODE1")
                        .param("date", "01.01.2000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAirportFlightsDetailsWhenAirportWithRequestedCodeIsNotFound() throws Exception {
        mockMvc.perform(get(GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT, "NON_EXISTING_CODE")
                        .param("date", "2020-01-01"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAirportFlightsDetailsWhenNoThereWasNoFlightsForRequestedDate() throws Exception {
        mockMvc.perform(get(GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT, "CODE1")
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(999))))
                .andExpect(jsonPath("$.arrivingFlights.numberOfFlights").value("0"))
                .andExpect(jsonPath("$.arrivingFlights.numberOfBaggagePieces").value("0"))
                .andExpect(jsonPath("$.departingFlights.numberOfFlights").value("0"))
                .andExpect(jsonPath("$.departingFlights.numberOfBaggagePieces").value("0"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAirportFlightsDetailsWhenOnlyDepartingFlightsFound() throws Exception {
        mockMvc.perform(get(GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT, "CODE4")
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("$.arrivingFlights.numberOfFlights").value("0"))
                .andExpect(jsonPath("$.arrivingFlights.numberOfBaggagePieces").value("0"))
                .andExpect(jsonPath("$.departingFlights.numberOfFlights").value("1"))
                .andExpect(jsonPath("$.departingFlights.numberOfBaggagePieces").value("5"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAirportFlightsDetailsWhenOnlyArrivingFlightsFound() throws Exception {
        mockMvc.perform(get(GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT, "CODE3")
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(5))))
                .andExpect(jsonPath("$.arrivingFlights.numberOfFlights").value("1"))
                .andExpect(jsonPath("$.arrivingFlights.numberOfBaggagePieces").value("8"))
                .andExpect(jsonPath("$.departingFlights.numberOfFlights").value("0"))
                .andExpect(jsonPath("$.departingFlights.numberOfBaggagePieces").value("0"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAirportFlightsDetailsWhenArrivingAndDepartingFlightsFound() throws Exception {
        mockMvc.perform(get(GET_AIRPORT_FLIGHTS_DETAILS_ENDPOINT, "CODE4")
                        .param("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(10))))
                .andExpect(jsonPath("$.arrivingFlights.numberOfFlights").value("1"))
                .andExpect(jsonPath("$.arrivingFlights.numberOfBaggagePieces").value("22"))
                .andExpect(jsonPath("$.departingFlights.numberOfFlights").value("1"))
                .andExpect(jsonPath("$.departingFlights.numberOfBaggagePieces").value("176"))
                .andExpect(status().isOk());
    }
}