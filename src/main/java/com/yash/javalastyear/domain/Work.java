package com.yash.javalastyear.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Work.
 */
@Entity
@Table(name = "work")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_id")
    private Long workID;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @Column(name = "status")
    private String status;

    @Column(name = "completion_date")
    private ZonedDateTime completionDate;

    @Column(name = "completion_remarks")
    private String completionRemarks;

    @ManyToOne
    private Employee creator;

    @ManyToOne
    private Employee assigned;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkID() {
        return workID;
    }

    public Work workID(Long workID) {
        this.workID = workID;
        return this;
    }

    public void setWorkID(Long workID) {
        this.workID = workID;
    }

    public String getTitle() {
        return title;
    }

    public Work title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Work description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Work createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public Work dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public Work status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getCompletionDate() {
        return completionDate;
    }

    public Work completionDate(ZonedDateTime completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public void setCompletionDate(ZonedDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public String getCompletionRemarks() {
        return completionRemarks;
    }

    public Work completionRemarks(String completionRemarks) {
        this.completionRemarks = completionRemarks;
        return this;
    }

    public void setCompletionRemarks(String completionRemarks) {
        this.completionRemarks = completionRemarks;
    }

    public Employee getCreator() {
        return creator;
    }

    public Work creator(Employee employee) {
        this.creator = employee;
        return this;
    }

    public void setCreator(Employee employee) {
        this.creator = employee;
    }

    public Employee getAssigned() {
        return assigned;
    }

    public Work assigned(Employee employee) {
        this.assigned = employee;
        return this;
    }

    public void setAssigned(Employee employee) {
        this.assigned = employee;
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
        Work work = (Work) o;
        if (work.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), work.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Work{" +
            "id=" + getId() +
            ", workID=" + getWorkID() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", completionRemarks='" + getCompletionRemarks() + "'" +
            "}";
    }
}
