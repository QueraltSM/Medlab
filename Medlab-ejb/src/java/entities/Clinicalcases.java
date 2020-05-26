/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Clinicalcases.findByTitle", query = "SELECT c FROM Clinicalcases c WHERE c.title = :title"),
    @NamedQuery(name = "Clinicalcases.findByAuthor", query = "SELECT c FROM Clinicalcases c WHERE c.author = :author"),
    @NamedQuery(name = "Clinicalcases.findByDescription", query = "SELECT c FROM Clinicalcases c WHERE c.description = :description"),
    @NamedQuery(name = "Clinicalcases.findByHistory", query = "SELECT c FROM Clinicalcases c WHERE c.history = :history"),
    @NamedQuery(name = "Clinicalcases.findByQuestions", query = "SELECT c FROM Clinicalcases c WHERE c.questions = :questions"),
    @NamedQuery(name = "Clinicalcases.findByExamination", query = "SELECT c FROM Clinicalcases c WHERE c.examination = :examination"),
    @NamedQuery(name = "Clinicalcases.findByDate", query = "SELECT c FROM Clinicalcases c WHERE c.date = :date"),
    @NamedQuery(name = "Clinicalcases.findByViews", query = "SELECT c FROM Clinicalcases c WHERE c.views = :views")})
public class Clinicalcases implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "AUTHOR")
    private String author;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 700)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 700)
    @Column(name = "HISTORY")
    private String history;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 700)
    @Column(name = "QUESTIONS")
    private String questions;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 700)
    @Column(name = "EXAMINATION")
    private String examination;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIEWS")
    private int views;
    @JoinColumn(name = "SPECIALITY", referencedColumnName = "TYPE")
    @ManyToOne(optional = false)
    private Speciality speciality;

    public Clinicalcases() {
    }

    public Clinicalcases(Long id) {
        this.id = id;
    }

    public Clinicalcases(Long id, String title, String author, String description, String history, String questions, String examination, Date date, int views) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.history = history;
        this.questions = questions;
        this.examination = examination;
        this.date = date;
        this.views = views;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
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
