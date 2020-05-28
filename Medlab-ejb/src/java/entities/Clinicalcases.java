/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author QSM
 */
@Entity
@Table(name = "CLINICALCASES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clinicalcases.findAll", query = "SELECT c FROM Clinicalcases c"),
    @NamedQuery(name = "Clinicalcases.findById", query = "SELECT c FROM Clinicalcases c WHERE c.id = :id"),
    @NamedQuery(name = "Clinicalcases.findByDate", query = "SELECT c FROM Clinicalcases c WHERE c.date = :date"),
    @NamedQuery(name = "Clinicalcases.findByDescription", query = "SELECT c FROM Clinicalcases c WHERE c.description = :description"),
    @NamedQuery(name = "Clinicalcases.findByExamination", query = "SELECT c FROM Clinicalcases c WHERE c.examination = :examination"),
    @NamedQuery(name = "Clinicalcases.findByHistory", query = "SELECT c FROM Clinicalcases c WHERE c.history = :history"),
    @NamedQuery(name = "Clinicalcases.findByQuestions", query = "SELECT c FROM Clinicalcases c WHERE c.questions = :questions"),
    @NamedQuery(name = "Clinicalcases.findByTitle", query = "SELECT c FROM Clinicalcases c WHERE c.title = :title"),
    @NamedQuery(name = "Clinicalcases.findByViews", query = "SELECT c FROM Clinicalcases c WHERE c.views = :views")})
public class Clinicalcases implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "EXAMINATION")
    private String examination;
    @Size(max = 255)
    @Column(name = "HISTORY")
    private String history;
    @Size(max = 255)
    @Column(name = "QUESTIONS")
    private String questions;
    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "VIEWS")
    private Integer views;
    @JoinColumn(name = "SPECIALITY", referencedColumnName = "TYPE")
    @ManyToOne(optional = false)
    private Speciality speciality;
    @JoinColumn(name = "AUTHOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users author;

    public Clinicalcases() {
    }

    public Clinicalcases(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clinicalcases)) {
            return false;
        }
        Clinicalcases other = (Clinicalcases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Clinicalcases[ id=" + id + " ]";
    }
    
}
