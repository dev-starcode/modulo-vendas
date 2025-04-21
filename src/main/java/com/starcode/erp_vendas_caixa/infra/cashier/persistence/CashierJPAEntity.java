package com.starcode.erp_vendas_caixa.infra.cashier.persistence;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "cashier")
public class CashierJPAEntity {
    @Id
    @Column(name = "cashier_id")
    private String cashierId;
    @Column(name = "opening_amount")
    private Double openingAmount;
    @Column(name = "status")
    private String status;
    @Column(name = "opened_at")
    private LocalDateTime openedAt;
    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    @Column(name = "user_opened_id")
    private String userOpenedId;
    @Column(name = "user_closed_id")
    private String userClosedCd;
    @Column(name = "total_sales")
    private Integer totalSales;
    @Column(name = "closing_amount")
    private Double closingAmount;

    private CashierJPAEntity(String cashier_id, Double opening_amount, String status, LocalDateTime opened_at, LocalDateTime closed_at, String user_opened_id, String user_closed_id, Integer total_sales, Double closing_amount) {
        this.cashierId = cashier_id;
        this.openingAmount = opening_amount;
        this.status = status;
        this.openedAt = opened_at;
        this.closedAt = closed_at;
        this.userOpenedId = user_opened_id;
        this.userClosedCd = user_closed_id;
        this.totalSales = total_sales;
        this.closingAmount = closing_amount;
    }

    public CashierJPAEntity() {
    }

    public static CashierJPAEntity create(final Cashier cashier){
        return new CashierJPAEntity(
                cashier.getCashierId().getValue(),
                cashier.getOpeningAmount().getValue(),
                cashier.getStatus().name(),
                cashier.getOpenedAt(),
                cashier.getClosedAt(),
                cashier.getUserOpenedId(),
                cashier.getUserClosedId(),
                cashier.getTotalSales(),
                cashier.getClosingAmount().getValue()
                );
    }

    public Cashier toAggregate(){
        return Cashier.restore(
                this.cashierId,
                this.openingAmount,
                this.status,
                this.openedAt,
                this.closedAt,
                this.userOpenedId,
                this.userClosedCd,
                this.totalSales,
                this.closingAmount);
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public Double getOpeningAmount() {
        return openingAmount;
    }

    public void setOpeningAmount(Double openingAmount) {
        this.openingAmount = openingAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOpened_at() {
        return openedAt;
    }

    public void setOpened_at(LocalDateTime opened_at) {
        this.openedAt = opened_at;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public String getUserOpenedId() {
        return userOpenedId;
    }

    public void setUserOpenedId(String userOpenedId) {
        this.userOpenedId = userOpenedId;
    }

    public String getUserClosedCd() {
        return userClosedCd;
    }

    public void setUserClosedCd(String userClosedCd) {
        this.userClosedCd = userClosedCd;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public Double getClosingAmount() {
        return closingAmount;
    }

    public void setClosingAmount(Double closingAmount) {
        this.closingAmount = closingAmount;
    }
}
