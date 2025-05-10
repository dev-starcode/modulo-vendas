package com.starcode.erp_vendas_caixa.domain.entities.Sale;
import com.starcode.erp_vendas_caixa.domain.aggregate.AggregateRoot;
import com.starcode.erp_vendas_caixa.domain.enums.StatusSale;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.value_objects.Identifier;
import com.starcode.erp_vendas_caixa.domain.value_objects.Price;
import java.time.LocalDateTime;

public class Sale extends AggregateRoot<Identifier> {
    private String userId;
    private String clientId;
    private String cashierId;
    private Price partialPrice;
    private Price discount;
    private Price total;
    private StatusSale status;
    private LocalDateTime saleDate;

    private Sale(Identifier sale_id, String user_id, String client_id, String cashier_id, Price partial_price, Price discount, Price total, StatusSale status, LocalDateTime sale_date) {
        super(sale_id);
        this.userId = user_id;
        this.clientId = client_id;
        this.cashierId = cashier_id;
        this.partialPrice = partial_price;
        this.discount = discount;
        this.total = total;
        this.status = status;
        this.saleDate = sale_date;
    }


    public static Sale create(final String user_id, final String client_id, final String cashier_id, final Double partial_price, final Double discount, final String status) {
        final var sale_id = Identifier.unique();
        final var statusSale = StatusSale.fromString(status);
        final var sale_date = LocalDateTime.now();
        return new Sale(sale_id, user_id, client_id, cashier_id, Price.validate(partial_price), Price.validate(discount), Price.validate(0.0), statusSale, sale_date);
    }

    public static Sale restore(final String sale_id, final String user_id, final String client_id, final String cashier_id, final Double partial_price, final Double discount, final Double total, final String status, final LocalDateTime sale_date){
        final var id = Identifier.restore(sale_id);
        return new Sale(id, user_id, client_id, cashier_id, Price.restore(partial_price), Price.restore(discount), Price.restore(total), StatusSale.fromString(status), sale_date);
    }

    @Override
    public void validate(ValidationHandler validationHandler) {
        new SaleValidator(this, validationHandler).validate();
    }

    public void calculateTotal() {
        if (this.discount == null) {
            this.total = this.partialPrice;
            return;
        }
        final var total = this.partialPrice.getValue() - this.discount.getValue();
        this.total.setValue(total);
    }
    public void paid(){
        this.status = StatusSale.PAID;
    }
    public void pending(){
        this.status = StatusSale.PENDING;
    }
    public void cancel(){
        this.status = StatusSale.CANCELED;
    }

    public Identifier getSaleId() {
        return this.id;
    }

    public String getUserId() {
        return userId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCashierId() {
        return cashierId;
    }

    public Price getPartialPrice() {
        return partialPrice;
    }

    public Price getDiscount() {
        return discount;
    }

    public Double getTotal() {
        return total.getValue();
    }

    public StatusSale getStatus() {return status;}

    public LocalDateTime getSaleDate() {
        return saleDate;
    }
}
