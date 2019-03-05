package com.yash.javalastyear.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_id")
    private Long billID;

    @Column(name = "title")
    private String title;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "bill_doc")
    private String billDoc;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillID() {
        return billID;
    }

    public Bill billID(Long billID) {
        this.billID = billID;
        return this;
    }

    public void setBillID(Long billID) {
        this.billID = billID;
    }

    public String getTitle() {
        return title;
    }

    public Bill title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAmount() {
        return amount;
    }

    public Bill amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBillDoc() {
        return billDoc;
    }

    public Bill billDoc(String billDoc) {
        this.billDoc = billDoc;
        return this;
    }

    public void setBillDoc(String billDoc) {
        this.billDoc = billDoc;
    }

    public String getStatus() {
        return status;
    }

    public Bill status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Bill customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Bill employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bill bill = (Bill) o;
        if (bill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + getId() +
            ", billID=" + getBillID() +
            ", title='" + getTitle() + "'" +
            ", amount=" + getAmount() +
            ", billDoc='" + getBillDoc() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
