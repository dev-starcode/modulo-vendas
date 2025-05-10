package com.starcode.erp_vendas_caixa.infra.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starcode.erp_vendas_caixa.ErpVendasCaixaApplication;
import com.starcode.erp_vendas_caixa.application.usecases.Cashier.CreateCashierUseCase;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.infra.ControllerTest;
import com.starcode.erp_vendas_caixa.infra.cashier.models.CreateCashierApiInput;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierRepositoryJPA;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.database.CashierPostgresqlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CashierAPI.class)
public class CashierAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CashierRepositoryJPA cashierRepositoryJPA;

    @Autowired
    private CashierPostgresqlRepository cashierPostgresqlRepository;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private CreateCashierUseCase createCashierUseCase;

    @Test
    @DisplayName("Deve criar um caixa corretamente")
    public void deveCriarUmCaixaCorretamente() throws Exception {
        final var userOpenedId = "user_opened_id";
        final var openingAmount = 100D;

        final var input = new CreateCashierApiInput(userOpenedId, openingAmount);
        final var cashier = Cashier.restore("123",100D,"closed", LocalDateTime.now(),LocalDateTime.now().plusHours(1), "user_opened_id","user_closed_id", 10, 100D);
        final var cashierDto = CashierOutputDTO.create(cashier);
        Mockito.when(createCashierUseCase.execute(any()))
                .thenReturn(cashierDto);

        final var request = MockMvcRequestBuilders.post("/cashiers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/cashiers/123"));
        Mockito.verify(createCashierUseCase, Mockito.times(1)).execute(
                argThat(cmd ->
                        Objects.equals(userOpenedId, cmd.userOpenedId()) &&
                        Objects.equals(openingAmount, cmd.openingAmount())));
        }

    @Test
    @DisplayName("Deve lançar um erro caso o user_id seja null")
    public void deveLancarUmaExceptionQuandoOUserForNull() throws Exception {
        final var openingAmount = 100D;
        final var expectedMessage = "user_opened_id não pode ser null";
        final var expectedErrorCode = "UnprocessableEntity";
        final var input = new CreateCashierApiInput(null, openingAmount);
        Mockito.when(createCashierUseCase.execute(any()))
                .thenThrow(DomainException.with(Error.create(expectedErrorCode, expectedMessage)));

        final var request = MockMvcRequestBuilders.post("/cashiers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Location", nullValue()),
                        jsonPath("$.message", equalTo(expectedMessage)),
                        jsonPath("$.error", equalTo("UnprocessableEntity")));
        Mockito.verify(createCashierUseCase, Mockito.times(1)).execute(
                argThat(cmd ->
                        cmd.userOpenedId() == null || cmd.userOpenedId().isBlank() &&
                                Objects.equals(openingAmount, cmd.openingAmount())));
    }

    @Test
    @DisplayName("Deve lançar um erro caso o user_id seja vazio")
    public void deveLancarUmErroQuandoUserIdForVazio() throws Exception {
        final var openingAmount = 100D;
        final var expectedMessage = "user_opened_id deve ser maior que zero.";
        final var expectedErrorCode = "UnprocessableEntity";

        final var input = new CreateCashierApiInput("", openingAmount);

        Mockito.when(createCashierUseCase.execute(any()))
                .thenThrow(DomainException.with(Error.create(expectedErrorCode, expectedMessage)));

        final var request = MockMvcRequestBuilders.post("/cashiers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Location", nullValue()),
                        jsonPath("$.message", equalTo(expectedMessage)),
                        jsonPath("$.error", equalTo("UnprocessableEntity"))
                );

        Mockito.verify(createCashierUseCase, Mockito.times(1)).execute(
                argThat(cmd ->
                        cmd.userOpenedId() == null || cmd.userOpenedId().isBlank()
                        && Objects.equals(openingAmount, cmd.openingAmount()))
        );
    }
}
