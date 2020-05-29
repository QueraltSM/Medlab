/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author QSM
 */
@Entity
@Table(name = "CARTITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cartitems.findAll", query = "SELECT c FROM Cartitems c"),
    @NamedQuery(name = "Cartitems.findByCartId", query = "SELECT c FROM Cartitems c WHERE c.cartid = :cartid"),
    @NamedQuery(name = "Cartitems.findById", query = "SELECT c FROM Cartitems c WHERE c.id = :id"),
    @NamedQuery(name = "Cartitems.findByBookid", query = "SELECT c FROM Cartitems c WHERE c.bookid = :bookid"),
    @NamedQuery(name = "Cartitems.findByQuantity", query = "SELECT c FROM Cartitems c WHERE c.quantity = :quantity")})
public class Cartitems implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BOOKID")
    private long bookid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @JoinColumn(name = "CARTID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cart cartid;

    public Cartitems() {
    }

    public Cartitems(Long id) {
        this.id = id;
    }

    public Cartitems(Long id, long bookid, int quantity) {
        this.id = id;
        this.bookid = bookid;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCartid() {
        return cartid;
    }

    public void setCartid(Cart cartid) {
        this.cartid = cartid;
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
        if (!(object instanceof Cartitems)) {
            return false;
        }
        Cartitems other = (Cartitems) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cartitems[ id=" + id + " ]";
    }
    
}
