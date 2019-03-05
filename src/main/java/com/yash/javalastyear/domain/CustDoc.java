package com.yash.javalastyear.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CustDoc.
 */
@Entity
@Table(name = "cust_doc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cust_doc_id")
    private Long custDocID;

    @Column(name = "doc_title")
    private String docTitle;

    @Column(name = "doc_file")
    private String docFile;

    @Column(name = "doc_remarks")
    private String docRemarks;

    @Column(name = "issue_date")
    private ZonedDateTime issueDate;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustDocID() {
        return custDocID;
    }

    public CustDoc custDocID(Long custDocID) {
        this.custDocID = custDocID;
        return this;
    }

    public void setCustDocID(Long custDocID) {
        this.custDocID = custDocID;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public CustDoc docTitle(String docTitle) {
        this.docTitle = docTitle;
        return this;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocFile() {
        return docFile;
    }

    public CustDoc docFile(String docFile) {
        this.docFile = docFile;
        return this;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    public String getDocRemarks() {
        return docRemarks;
    }

    public CustDoc docRemarks(String docRemarks) {
        this.docRemarks = docRemarks;
        return this;
    }

    public void setDocRemarks(String docRemarks) {
        this.docRemarks = docRemarks;
    }

    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    public CustDoc issueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public CustDoc dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustDoc customer(Customer customer) {
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
        CustDoc custDoc = (CustDoc) o;
        if (custDoc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), custDoc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustDoc{" +
            "id=" + getId() +
            ", custDocID=" + getCustDocID() +
            ", docTitle='" + getDocTitle() + "'" +
            ", docFile='" + getDocFile() + "'" +
            ", docRemarks='" + getDocRemarks() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }
}
