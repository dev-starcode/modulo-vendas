package com.starcode.erp_vendas_caixa.infra.productSale.persistence;
import com.starcode.erp_vendas_caixa.domain.entities.ProductSale.ProductSale;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products_sale")
public class ProductSaleJPAEntity {
    @Id
    @Column(name = "product_sale_id", length = 36, nullable = false)
    private String productSaleId;

    @Column(name = "sale_id", length = 36, nullable = false)
    private String saleId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "price_cost", nullable = false)
    private Double priceCost;

    @Column(name = "price_sale", nullable = false)
    private Double priceSale;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    public ProductSaleJPAEntity(String sale_product_id, String sale_id, String product_id, String product_name, Double price_coust, Double price_sale, Integer quantity, Double total_price) {
        this.productSaleId = sale_product_id;
        this.saleId = sale_id;
        this.productId = product_id;
        this.productName = product_name;
        this.priceCost = price_coust;
        this.priceSale = price_sale;
        this.quantity = quantity;
        this.totalPrice = total_price;
    }

    public ProductSaleJPAEntity() {
    }

    public static ProductSale toAggregate(final ProductSaleJPAEntity saleProductsJPAEntity){
        return ProductSale.restore(
                saleProductsJPAEntity.getProductSaleId().trim(),
                saleProductsJPAEntity.getSaleId().trim(),
                saleProductsJPAEntity.getProductId().trim(),
                saleProductsJPAEntity.getProductName(),
                saleProductsJPAEntity.getPriceCost(),
                saleProductsJPAEntity.getPriceSale(),
                saleProductsJPAEntity.getQuantity(),
                saleProductsJPAEntity.getTotalPrice()
        );
    }

    public static ProductSaleJPAEntity create(final ProductSale productSale) {
        return new ProductSaleJPAEntity(
                productSale.getSale_product_id().getValue(),
                productSale.getSale_id(),
                productSale.getProduct_id(),
                productSale.getProduct_name(),
                productSale.getPrice_coust().getValue(),
                productSale.getPrice_sale().getValue(),
                productSale.getQuantity(),
                productSale.getTotal_price().getValue()
        );
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Double priceSale) {
        this.priceSale = priceSale;
    }

    public Double getPriceCost() {
        return priceCost;
    }

    public void setPriceCost(Double price_cost) {
        this.priceCost = price_cost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getProductSaleId() {
        return productSaleId;
    }

    public void setProductSaleId(String productSaleId) {
        this.productSaleId = productSaleId;
    }
}
