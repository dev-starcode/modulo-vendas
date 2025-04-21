package com.starcode.erp_vendas_caixa.infra.cashierMovements.persistence;
import com.starcode.erp_vendas_caixa.domain.entities.CashierMovements.CashierMovements;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cashier_movements")
public class CashierMovementsJPAEntity {
    @Id
    @Column(name = "cashier_movement_id")
    private String cashierMovementId;
    @Column(name = "cashier_id")
    private String cashierId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "type")
    private String type;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "reason")
    private String reason;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CashierMovementsJPAEntity(String cashier_movement_id, String cashier_id, String user_id, String type, Double amount, String reason, LocalDateTime created_at) {
        this.cashierMovementId = cashier_movement_id;
        this.cashierId = cashier_id;
        this.userId = user_id;
        this.type = type;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = created_at;
    }

    public CashierMovementsJPAEntity() {
    }
    public static CashierMovementsJPAEntity create(final CashierMovements cashierMoviments){
        return new CashierMovementsJPAEntity(
                cashierMoviments.getCashierMovementId().getValue(),
                cashierMoviments.getCashierId(),
                cashierMoviments.getUserId(),
                cashierMoviments.getType().name(),
                cashierMoviments.getAmount().getValue(),
                cashierMoviments.getReason(),
                cashierMoviments.getCreatedAt()
        );
    }
    public static CashierMovements toAggregate(final CashierMovementsJPAEntity cashierMovementsRepositoryJPA){
        return CashierMovements.restore(
                cashierMovementsRepositoryJPA.getCashierMovementId(),
                cashierMovementsRepositoryJPA.getCashierId(),
                cashierMovementsRepositoryJPA.getUserId(),
                cashierMovementsRepositoryJPA.getType(),
                cashierMovementsRepositoryJPA.getAmount(),
                cashierMovementsRepositoryJPA.getReason(),
                cashierMovementsRepositoryJPA.getCreatedAt()
        );
    }

    public String getCashierMovementId() {
        return cashierMovementId;
    }

    public void setCashierMovementId(String cashierMovementId) {
        this.cashierMovementId = cashierMovementId;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
