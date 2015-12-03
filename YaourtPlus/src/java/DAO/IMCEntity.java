/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author tbenoist
 */
@Entity
public class IMCEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, precision = 5, scale = 3)
    private double valeur;

    @Column(nullable = false)
    private int QteLourd;

    @Column(nullable = false)
    private int QteLeger;

// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE	
// Relations MANY TO MANY

// Accesseurs ==================================================================
    public Integer getId() {
        return id;
    }

    public double getValeur() {
        return valeur;
    }

    public int getQteLourd() {
        return QteLourd;
    }

    public int getQteLeger() {
        return QteLeger;
    }

    

// Mutateurs ===================================================================
    public void setId(Integer id) {
        this.id = id;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public void setQteLourd(int QteLourd) {
        this.QteLourd = QteLourd;
    }

    public void setQteLeger(int QteLeger) {
        this.QteLeger = QteLeger;
    }


// =============================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.valeur) ^ (Double.doubleToLongBits(this.valeur) >>> 32));
        hash = 53 * hash + this.QteLourd;
        hash = 53 * hash + this.QteLeger;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IMCEntity other = (IMCEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valeur) != Double.doubleToLongBits(other.valeur)) {
            return false;
        }
        if (this.QteLourd != other.QteLourd) {
            return false;
        }
        if (this.QteLeger != other.QteLeger) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IMCEntity{" + "id=" + id + ", valeur=" + valeur + ", QteLourd=" + QteLourd + ", QteLeger=" + QteLeger + '}';
    }

}
