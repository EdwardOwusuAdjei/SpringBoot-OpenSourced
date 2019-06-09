package com.cointest.model.customer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Transactions")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)//needed for not returning null values as json response
public class Transactions{


    /** Serial Version UID. */
    private static final long serialVersionUID = -558952446L;

    /** Use a WeakHashMap so entries will be garbage collected once all entities
     referring to a saved hash are garbage collected themselves. */
    private static final Map<Serializable, Integer> SAVED_HASHES =
            Collections.synchronizedMap(new WeakHashMap<Serializable, Integer>());

    /** hashCode temporary storage. */
    private volatile Integer hashCode;


    /** Field mapping. */
    private java.math.BigDecimal amount;
    /** Field mapping. */
    private java.math.BigDecimal charge;
    /** Field mapping. */
    private Date date;
    /** Field mapping. */
    private Integer id = 0; // init for hibernate bug workaround
    /** Field mapping. */
    private String message;
    /** Field mapping. */
    private Wallet walletidentifierfrom;
    /** Field mapping. */
    private Wallet walletidentifierto;
    /**
     * Default constructor, mainly for hibernate use.
     */
    public Transactions() {
        // Default constructor
    }

    /** Constructor taking a given ID.
     * @param id to set
     */
    public Transactions(Integer id) {
        this.id = id;
    }

    /** Constructor taking a given ID.
     * @param amount java.math.BigDecimal object;
     * @param charge java.math.BigDecimal object;
     * @param date Date object;
     * @param id Integer object;
     * @param message String object;
     * @param walletidentifierfrom Wallet object;
     * @param walletidentifierto Wallet object;
     */
    public Transactions(java.math.BigDecimal amount, java.math.BigDecimal charge, Date date,
                        Integer id, String message, Wallet walletidentifierfrom,
                        Wallet walletidentifierto) {

        this.amount = amount;
        this.charge = charge;
        this.date = date;
        this.id = id;
        this.message = message;
        this.walletidentifierfrom = walletidentifierfrom;
        this.walletidentifierto = walletidentifierto;
    }





    /** Return the type of this class. Useful for when dealing with proxies.
     * @return Defining class.
     */
    @Transient
    public Class<?> getClassType() {
        return Transactions.class;
    }


    /**
     * Return the value associated with the column: amount.
     * @return A java.math.BigDecimal object (this.amount)
     */
    @Basic( optional = false )
    @Column( nullable = false  )
    public java.math.BigDecimal getAmount() {
        return this.amount;

    }



    /**
     * Set the value related to the column: amount.
     * @param amount the amount value you wish to set
     */
    public void setAmount(final java.math.BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Return the value associated with the column: charge.
     * @return A java.math.BigDecimal object (this.charge)
     */
    @Basic( optional = false )
    @Column( nullable = false  )
    public java.math.BigDecimal getCharge() {
        return this.charge;

    }



    /**
     * Set the value related to the column: charge.
     * @param charge the charge value you wish to set
     */
    public void setCharge(final java.math.BigDecimal charge) {
        this.charge = charge;
    }

    /**
     * Return the value associated with the column: date.
     * @return A Date object (this.date)
     */
    @Basic( optional = false )
    @Column( nullable = false  )
    public Date getDate() {
        return this.date;

    }



    /**
     * Set the value related to the column: date.
     * @param date the date value you wish to set
     */
    public void setDate(final Date date) {
        this.date = date;
    }

    /**
     * Return the value associated with the column: id.
     * @return A Integer object (this.id)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic( optional = false )
    @Column( name = "txid", nullable = false  )
    public Integer getId() {
        return this.id;

    }



    /**
     * Set the value related to the column: id.
     * @param id the id value you wish to set
     */
    public void setId(final Integer id) {
        // If we've just been persisted and hashCode has been
        // returned then make sure other entities with this
        // ID return the already returned hash code
        if ( (this.id == null || this.id == 0) &&
                (id != null) &&
                (this.hashCode != null) ) {
            SAVED_HASHES.put( id, this.hashCode );
        }
        this.id = id;
    }

    /**
     * Return the value associated with the column: message.
     * @return A String object (this.message)
     */
    @Basic( optional = false )
    @Column( nullable = false, length = 66  )
    public String getMessage() {
        return this.message;

    }



    /**
     * Set the value related to the column: message.
     * @param message the message value you wish to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Return the value associated with the column: walletidentifierfrom.
     * @return A Wallet object (this.walletidentifierfrom)
     */
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @JoinColumn(name = "walletidentifierfrom", nullable = false )
    public Wallet getWalletidentifierfrom() {
        return this.walletidentifierfrom;

    }



    /**
     * Set the value related to the column: walletidentifierfrom.
     * @param walletidentifierfrom the walletidentifierfrom value you wish to set
     */
    public void setWalletidentifierfrom(final Wallet walletidentifierfrom) {
        this.walletidentifierfrom = walletidentifierfrom;
    }

    /**
     * Return the value associated with the column: walletidentifierto.
     * @return A Wallet object (this.walletidentifierto)
     */
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @JoinColumn(name = "walletidentifierto", nullable = false )
    public Wallet getWalletidentifierto() {
        return this.walletidentifierto;

    }



    /**
     * Set the value related to the column: walletidentifierto.
     * @param walletidentifierto the walletidentifierto value you wish to set
     */
    public void setWalletidentifierto(final Wallet walletidentifierto) {
        this.walletidentifierto = walletidentifierto;
    }


    /**
     * Deep copy.
     * @return cloned object
     * @throws CloneNotSupportedException on error
     */
    @Override
    public Transactions clone() throws CloneNotSupportedException {

        final Transactions copy = (Transactions)super.clone();

        copy.setAmount(this.getAmount());
        copy.setCharge(this.getCharge());
        copy.setDate(this.getDate());
        copy.setId(this.getId());
        copy.setMessage(this.getMessage());
        copy.setWalletidentifierfrom(this.getWalletidentifierfrom());
        copy.setWalletidentifierto(this.getWalletidentifierto());
        return copy;
    }



    /** Provides toString implementation.
     * @see java.lang.Object#toString()
     * @return String representation of this class.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("amount: " + this.getAmount() + ", ");
        sb.append("charge: " + this.getCharge() + ", ");
        sb.append("date: " + this.getDate() + ", ");
        sb.append("id: " + this.getId() + ", ");
        sb.append("message: " + this.getMessage() + ", ");
        return sb.toString();
    }

}