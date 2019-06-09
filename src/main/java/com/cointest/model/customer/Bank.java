package com.cointest.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

@Entity(name="Bank")
@Table(name = "Bank")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)//needed for not returning null values as json response
public class Bank {
    /** Serial Version UID. */
    private static final long serialVersionUID = -558952449L;

    /** Use a WeakHashMap so entries will be garbage collected once all entities
     referring to a saved hash are garbage collected themselves. */
    private static final Map<Serializable, Serializable> SAVED_HASHES =
            Collections.synchronizedMap(new WeakHashMap<Serializable, Serializable>());

    /** hashCode temporary storage. */
    private volatile Serializable hashCode;


    /** Field mapping. */
    @Id
    @GeneratedValue
    private Integer accountnumber;
    /** Field mapping. */
    private String bankname;
    /** Field mapping. */
    @ManyToOne
    @JoinColumn(name = "userid")
    private Customer userid;




    /** Return the type of this class. Useful for when dealing with proxies.
     * @return Defining class.
     */
    @Transient
    public Class<?> getClassType() {
        return Bank.class;
    }


    /**
     * Return the value associated with the column: accountnumber.
     * @return A Integer object (this.accountnumber)
     */
    @Basic( optional = false )
    @Column( nullable = false  )
    public Integer getAccountnumber() {
        return this.accountnumber;

    }



    /**
     * Set the value related to the column: accountnumber.
     * @param accountnumber the accountnumber value you wish to set
     */
    public void setAccountnumber(final Integer accountnumber) {
        this.accountnumber = accountnumber;
    }

    /**
     * Return the value associated with the column: bankname.
     * @return A String object (this.bankname)
     */
    @Basic( optional = false )
    @Column( nullable = false, length = 60  )
    public String getBankname() {
        return this.bankname;

    }



    /**
     * Set the value related to the column: bankname.
     * @param bankname the bankname value you wish to set
     */
    public void setBankname(final String bankname) {
        this.bankname = bankname;
    }

    /**
     * Return the value associated with the column: userid.
     * @return A Customer object (this.userid)
     */
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @JoinColumn(name = "userid", nullable = false )
    public Customer getUserid() {
        return this.userid;

    }



    /**
     * Set the value related to the column: userid.
     * @param userid the userid value you wish to set
     */
    public void setUserid(final Customer userid) {
        this.userid = userid;
    }



}