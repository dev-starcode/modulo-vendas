package com.starcode.erp_vendas_caixa.domain.entities.Cashier;
import com.starcode.erp_vendas_caixa.domain.aggregate.AggregateRoot;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.validation.handler.ThrowsValidationHandler;
import com.starcode.erp_vendas_caixa.domain.value_objects.Identifier;
import com.starcode.erp_vendas_caixa.domain.value_objects.Price;

import java.time.LocalDateTime;
import java.util.List;

public class Cashier extends AggregateRoot<Identifier> {
    private Price openingAmount;
    private Status status;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private String userOpenedId;
    private String userClosedId;
    private Integer totalSales;
    private Price closingAmount;

    private Cashier(Identifier cashier_id, Price opening_amount, Status status, LocalDateTime opened_at, LocalDateTime closed_at, String user_opened_id, String user_closed_id, Integer total_sales, Price closing_amount) {
        super(cashier_id);
        this.openingAmount = opening_amount;
        this.status = status;
        this.openedAt = opened_at;
        this.closedAt = closed_at;
        this.userOpenedId = user_opened_id;
        this.userClosedId = user_closed_id;
        this.totalSales = total_sales;
        this.closingAmount = closing_amount;
    }

    public enum Status {
        opened,
        closed;
    };

    public static Cashier create(final String user_opened_id, final Double opening_amount) {
        final var cashier_id = Identifier.unique();
        final int total_sales = 0;
        final LocalDateTime opened_at = LocalDateTime.now();
        final var opening = Price.validate(opening_amount);
        final var closing_amount = Price.validate(0.0);
        return new Cashier(cashier_id, opening, Status.opened, opened_at, null, user_opened_id, null, total_sales, closing_amount);
    }

    public static Cashier restore(final String cashier_id, final Double opening_amount, final String status, final LocalDateTime opened_at,
                                  final LocalDateTime closed_at, final String user_opened_id, final String user_closed_id,
                                  final Integer total_sales, final Double closing_amount){
        final var id = Identifier.restore(cashier_id);
        final var opening = Price.restore(opening_amount);
        final var closing = Price.restore(closing_amount);
        return new Cashier(id, opening, Status.valueOf(status), opened_at, closed_at, user_opened_id, user_closed_id, total_sales, closing);
    }

    @Override
    public void validate(ValidationHandler handler) {
        final var throwsValidationHandler = new ThrowsValidationHandler();
        new CashierValidator(this, throwsValidationHandler).validate();
    }

    public void open(final String user_opened_id) {
        if(this.status == Status.opened) throw DomainException.with(Error.create("Conflict","O caixa já está aberto."));
        this.status = Status.opened;
        this.userOpenedId =  user_opened_id;
        this.userClosedId = null;
        this.closingAmount.setValue(0D);
    }
    public void close(final String user_closed_id)  {
        if(this.status == Status.closed) throw DomainException.with(Error.create("Conflict", "O caixa já está fechado."));
        this.status = Status.closed;
        this.closedAt = LocalDateTime.now();
        this.userClosedId = user_closed_id;
    }

    public void calculateClosingAmount(final List<Sale> sales){
        var amount = 0.0;
        if(sales == null){
            this.closingAmount.setValue(this.openingAmount.getValue());
            return;
        }
        for(Sale sale : sales){
            amount+=sale.getTotal();
        }
        final var total_amount = this.openingAmount.getValue() + amount;
        this.closingAmount.setValue(total_amount);
    }
    public void addSale() {
        if(this.status == Status.closed)
            throw DomainException.with(Error.create("Conflict","Não é possível adicionar vendas a um caixa fechado."));
        this.totalSales++;
    }
    public void addTotalSales(final int tota){
        if(this.status == Status.closed)
            throw DomainException.with(Error.create("Conflict","Não é possível adicionar vendas a um caixa fechado."));
        this.totalSales = tota;
    }

    public Identifier getCashierId() {
        return this.id;
    }

    public Status getStatus() {return status;}

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public String getUserOpenedId() {
        return userOpenedId;
    }

    public String getUserClosedId() {
        return userClosedId;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public Price getClosingAmount() {
        return closingAmount;
    }

    public Price getOpeningAmount() {
        return openingAmount;
    }
}
