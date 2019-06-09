package com.cointest.model.customer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

@Entity
@Table(name = "Phonenumber")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)//

public class Phonenumber{
    /** Serial Version UID. */
    private static final long serialVersionUID = -558952447L;

    /** Use a WeakHashMap so entries will be garbage collected once all entities
     referring to a saved hash are garbage collected themselves. */
    private static final Map<Serializable, Serializable> SAVED_HASHES =
            Collections.synchronizedMap(new WeakHashMap<Serializable, Serializable>());

    /** hashCode temporary storage. */
    private volatile Serializable hashCode;


    /** Field mapping. */
    @Id
    @GeneratedValue
    private Integer mobilenumber;
    /** Field mapping. */
    @ManyToOne
    @JoinColumn(name = "userid")
    private Customer userid;




    /** Return the type of this class. Useful for when dealing with proxies.
     * @return Defining class.
     */
    @Transient
    public Class<?> getClassType() {
        return Phonenumber.class;
    }


    /**
     * Return the value associated with the column: mobilenumber.
     * @return A Integer object (this.mobilenumber)
     */
    @Basic( optional = false )
    @Column( nullable = false  )
    public Integer getMobilenumber() {
        return this.mobilenumber;

    }



    /**
     * Set the value related to the column: mobilenumber.
     * @param mobilenumber the mobilenumber value you wish to set
     */
    public void setMobilenumber(final Integer mobilenumber) {
        this.mobilenumber = mobilenumber;
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
