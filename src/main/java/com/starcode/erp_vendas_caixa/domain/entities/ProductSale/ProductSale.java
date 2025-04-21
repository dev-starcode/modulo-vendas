package com.starcode.erp_vendas_caixa.domain.entities.ProductSale;
import com.starcode.erp_vendas_caixa.domain.aggregate.AggregateRoot;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;
import com.starcode.erp_vendas_caixa.domain.value_objects.Identifier;
import com.starcode.erp_vendas_caixa.domain.value_objects.Price;

public class ProductSale extends AggregateRoot<Identifier> {
    private String sale_id;
    private String product_id;
    private String product_name;
    private Price price_coust;
    private Price price_sale;
    private Integer quantity;
    private Price total_price;

    public ProductSale(Identifier sale_product_id, String sale_id, String product_id, String product_name, Price price_coust, Price price_sale, Integer quantity, Price total_price) {
        super(sale_product_id);
        this.sale_id = sale_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.price_coust = price_coust;
        this.price_sale = price_sale;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    @Override
    public void validate(ValidationHandler validationHandler) {
        new ProductSaleValidator(this, validationHandler).validate();
    }

    public static ProductSale create(final String sale_id, final String product_id, final String product_name, final Double price_coust, final Double price_sale, final Integer quantity) {
        final var sale_product_id = Identifier.unique();
        return new ProductSale(sale_product_id, sale_id, product_id, product_name, Price.validate(price_coust), Price.validate(price_sale), quantity, Price.validate(0.0));
    }
    public static ProductSale restore(final String sale_product_id, final String sale_id, final String product_id, final String product_name, final Double price_coust, final Double price_sale, final Integer quantity, final Double total_price) {
        final var id = Identifier.restore(sale_product_id);
        return new ProductSale(id, sale_id, product_id, product_name, Price.restore(price_coust), Price.restore(price_sale), quantity, Price.restore(total_price));
    }

    public void calculateTotal() {
        final var total = this.price_sale.getValue() * this.quantity;
        this.total_price.setValue(total);
    }

    public Identifier getSale_product_id() {
        return this.id;
    }

    public String getSale_id() {
        return sale_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Price getPrice_coust() {
        return price_coust;
    }

    public Price getPrice_sale() {
        return price_sale;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Price getTotal_price() {
        return total_price;
    }
}
