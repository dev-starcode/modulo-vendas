package com.starcode.erp_vendas_caixa.infra.api.controllers;

import com.starcode.erp_vendas_caixa.application.usecases.Cashier.*;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CloseCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.OpenCashierInputDTO;
import com.starcode.erp_vendas_caixa.infra.api.CashierAPI;
import com.starcode.erp_vendas_caixa.infra.cashier.models.CashierApiOutput;
import com.starcode.erp_vendas_caixa.infra.cashier.models.CloseCashierApiInput;
import com.starcode.erp_vendas_caixa.infra.cashier.models.CreateCashierApiInput;
import com.starcode.erp_vendas_caixa.infra.cashier.models.OpenCashierApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CashierController implements CashierAPI {

    private final CreateCashierUseCase createCashierUseCase;
    private final GetAllCashierUseCase getAllCashierUseCase;
    private final GetCashierById getCashierByIdUseCase;
    private final CloseCashierUseCase closeCashierUseCase;
    private final OpenCashierUseCase openCashierUseCase;

    public CashierController(final CreateCashierUseCase createCashierUseCase, GetAllCashierUseCase getAllCashierUseCase, GetCashierById getCashierByIdUseCase, CloseCashierUseCase closeCashierUseCase, OpenCashierUseCase openCashierUseCase) {
        this.createCashierUseCase = Objects.requireNonNull(createCashierUseCase);
        this.getAllCashierUseCase = getAllCashierUseCase;
        this.getCashierByIdUseCase = getCashierByIdUseCase;
        this.closeCashierUseCase = closeCashierUseCase;
        this.openCashierUseCase = openCashierUseCase;
    }

    @Override
    public ResponseEntity<?> createCashier(final CreateCashierApiInput input) {
        final var data = CreateCashierInputDTO.create(input.userOpeningId(), input.openingAmount());
        final var output = this.createCashierUseCase.execute(data);
        final var response = new CashierApiOutput("Caixa criado com sucesso", output);
        return ResponseEntity.created(URI.create("/cashiers/" + output.cashierId())).body(response);
    }

    @Override
    public ResponseEntity<?> getAllCashiers(
            final int page,
            final int parPage,
            final String term,
            final String sort,
            final String direction) {
        final var input = new SearchQuery(page, parPage, term, sort, direction);
        final var output = this.getAllCashierUseCase.execute(input);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<?> getCashierById(final String cashier_id) {
        final var output = this.getCashierByIdUseCase.execute(cashier_id);
        final var response = new CashierApiOutput("Caixa encontrado com sucesso", output);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> closeCashier(String cashier_id, CloseCashierApiInput data) {
        final var input = new CloseCashierInputDTO(cashier_id, data.userClosedId());
        final var output = this.closeCashierUseCase.execute(input);
        final var response = new CashierApiOutput("Caixa fechado com sucesso", output);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> abrirCaixa(String cashier_id, OpenCashierApiInput data) {
        final var input = new OpenCashierInputDTO(cashier_id, data.userOpenedId());
        final var output = this.openCashierUseCase.execute(input);
        final var response = new CashierApiOutput("Caixa aberto com sucesso", output);
        return ResponseEntity.ok(response);
    }

}
