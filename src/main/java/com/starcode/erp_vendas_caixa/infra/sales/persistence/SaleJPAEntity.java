package com.starcode.erp_vendas_caixa.infra.sales.persistence;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class SaleJPAEntity {
    @Id
    @Column(name = "sale_id")
    private String saleId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "cashier_id")
    private String cashierId;
    @Column(name = "partial_price")
    private Double partialPrice;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "total")
    private Double total;
    @Column(name = "status")
    private String status;
    @Column(name = "sale_date")
    private LocalDateTime saleDate;

    public SaleJPAEntity(String sale_id, String user_id, String client_id, String cashier_id, Double partial_price, Double discount, Double total, String status, LocalDateTime sale_date) {
        this.saleId = sale_id;
        this.userId = user_id;
        this.clientId = client_id;
        this.cashierId = cashier_id;
        this.partialPrice = partial_price;
        this.discount = discount;
        this.total = total;
        this.status = status;
        this.saleDate = sale_date;
    }

    public SaleJPAEntity() {
    }

    public static SaleJPAEntity create(final Sale sale){
        return new SaleJPAEntity(
                sale.getSaleId().getValue(),
                sale.getUserId(),
                sale.getClientId(),
                sale.getCashierId(),
                sale.getPartialPrice().getValue(),
                sale.getDiscount().getValue(),
                sale.getTotal(),
                sale.getStatus().name(),
                sale.getSaleDate()
        );
    }

    public static Sale toAggregate(final SaleJPAEntity sale){
        return Sale.restore(
                sale.getSaleId(),
                sale.getUserId(),
                sale.getClientId(),
                sale.getCashierId(),
                sale.getPartialPrice(),
                sale.getDiscount(),
                sale.getTotal(),
                sale.getStatus(),
                sale.getSaleDate()
        );
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public Double getPartialPrice() {
        return partialPrice;
    }

    public void setPartialPrice(Double partialPrice) {
        this.partialPrice = partialPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
