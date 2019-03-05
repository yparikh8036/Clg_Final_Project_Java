package com.yash.javalastyear.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CustAccDoc.
 */
@Entity
@Table(name = "cust_acc_doc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustAccDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cust_acc_doc_id")
    private Long custAccDocID;

    @Column(name = "doc_file")
    private String docFile;

    @Column(name = "doc_date")
    private ZonedDateTime docDate;

    @ManyToOne
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustAccDocID() {
        return custAccDocID;
    }

    public CustAccDoc custAccDocID(Long custAccDocID) {
        this.custAccDocID = custAccDocID;
        return this;
    }

    public void setCustAccDocID(Long custAccDocID) {
        this.custAccDocID = custAccDocID;
    }

    public String getDocFile() {
        return docFile;
    }

    public CustAccDoc docFile(String docFile) {
        this.docFile = docFile;
        return this;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    public ZonedDateTime getDocDate() {
        return docDate;
    }

    public CustAccDoc docDate(ZonedDateTime docDate) {
        this.docDate = docDate;
        return this;
    }

    public void setDocDate(ZonedDateTime docDate) {
        this.docDate = docDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustAccDoc customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        CustAccDoc custAccDoc = (CustAccDoc) o;
        if (custAccDoc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custAccDoc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustAccDoc{" +
            "id=" + getId() +
            ", custAccDocID=" + getCustAccDocID() +
            ", docFile='" + getDocFile() + "'" +
            ", docDate='" + getDocDate() + "'" +
            "}";
    }
}
