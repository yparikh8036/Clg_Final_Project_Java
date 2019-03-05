package com.yash.javalastyear.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Bill entity.
 */
public class BillDTO implements Serializable {

    private Long id;

    private Long billID;

    private String title;

    private Long amount;

    private String billDoc;

    private String status;


    private Long customerId;

    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillID() {
        return billID;
    }

    public void setBillID(Long billID) {
        this.billID = billID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBillDoc() {
        return billDoc;
    }

    public void setBillDoc(String billDoc) {
        this.billDoc = billDoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillDTO billDTO = (BillDTO) o;
        if (billDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillDTO{" +
            "id=" + getId() +
            ", billID=" + getBillID() +
            ", title='" + getTitle() + "'" +
            ", amount=" + getAmount() +
            ", billDoc='" + getBillDoc() + "'" +
            ", status='" + getStatus() + "'" +
            ", customer=" + getCustomerId() +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
