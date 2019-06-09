package com.cointest.model.customer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "Wallet")//introduced this so queries will be mapped when using jpql. <- edd
@Table(name = "Wallet")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)//needed for not returning null values as json response
public class Wallet{

    /** Serial Version UID. */
    private static final long serialVersionUID = -558952445L;

    /** Use a WeakHashMap so entries will be garbage collected once all entities
     referring to a saved hash are garbage collected themselves. */
    private static final Map<Serializable, Integer> SAVED_HASHES =
            Collections.synchronizedMap(new WeakHashMap<Serializable, Integer>());

    /** hashCode temporary storage. */
    private volatile Integer hashCode;


    /** Field mapping. */
    private java.math.BigDecimal amount;
    /** Field mapping. */
    private Set<Customer> customers = new HashSet<Customer>();

    /** Field mapping. */
    private Integer id = 0; // init for hibernate bug workaround
    /** Field mapping. */
    private Set<Transactions> transactionss = new HashSet<Transactions>();

    /**
     * Default constructor, mainly for hibernate use.
     */
    public Wallet() {
        // Default constructor
    }

    /** Constructor taking a given ID.
     * @param id to set
     */
    public Wallet(Integer id) {
        this.id = id;
    }

    /** Constructor taking a given ID.
     * @param amount java.math.BigDecimal object;
     * @param id Integer object;
     */
    public Wallet(java.math.BigDecimal amount, Integer id) {

        this.amount = amount;
        this.id = id;
    }





    /** Return the type of this class. Useful for when dealing with proxies.
     * @return Defining class.
     */
    @Transient
    public Class<?> getClassType() {
        return Wallet.class;
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
     * Return the value associated with the column: customer.
     * @return A Set&lt;Customer&gt; object (this.customer)
     */
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "walletidentifier"  )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @Column( nullable = false  )
    public Set<Customer> getCustomers() {
        return this.customers;

    }

    /**
     * Adds a bi-directional link of type Customer to the customers set.
     * @param customer item to add
     */
    public void addCustomer(Customer customer) {
        customer.setWalletidentifier(this);
        this.customers.add(customer);
    }


    /**
     * Set the value related to the column: customer.
     * @param customer the customer value you wish to set
     */
    public void setCustomers(final Set<Customer> customer) {
        this.customers = customer;
    }

    /**
     * Return the value associated with the column: id.
     * @return A Integer object (this.id)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic( optional = false )
    @Column( name = "walletidentifier", nullable = false  )
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
            //SAVED_HASHES.put( id, this.hashCode );
        }
        this.id = id;
    }

    /**
     * Return the value associated with the column: transactions.
     * @return A Set&lt;Transactions&gt; object (this.transactions)
     */
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "walletidentifierfrom"  )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @Column( nullable = false  )
    public Set<Transactions> getTransactionss() {
        return this.transactionss;

    }

    /**
     * Adds a bi-directional link of type Transactions to the transactionss set.
     * @param transactions item to add
     */
    public void addTransactions(Transactions transactions) {
        transactions.setWalletidentifierfrom(this);
        this.transactionss.add(transactions);
    }


    /**
     * Set the value related to the column: transactions.
     * @param transactions the transactions value you wish to set
     */
    public void setTransactionss(final Set<Transactions> transactions) {
        this.transactionss = transactions;
    }


}