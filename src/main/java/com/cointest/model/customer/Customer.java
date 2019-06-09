package com.cointest.model.customer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "Customer")
@Table(name = "Customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)//needed for not returning null values as json response
public class Customer {

    /** Serial Version UID. */
    private static final long serialVersionUID = -558952448L;

    /** Use a WeakHashMap so entries will be garbage collected once all entities
     referring to a saved hash are garbage collected themselves. */
    private static final Map<Serializable, Integer> SAVED_HASHES =
            Collections.synchronizedMap(new WeakHashMap<Serializable, Integer>());

    /** hashCode temporary storage. */
    private volatile Integer hashCode;


    /** Field mapping. */
    private Set<Bank> banks = new HashSet<Bank>();


    /** Field mapping. */
    private String username;

    /** Field mapping. */
    private String email;
    /** Field mapping. */
    private String firstname;
    /** Field mapping. */
    private String userid; // init for hibernate bug workaround
    /** Field mapping. */
    private String lastname;
    /** Field mapping. */
    private String password;
    /** Field mapping. */
    private Set<Phonenumber> phonenumbers = new HashSet<Phonenumber>();

    /** Field mapping. */
    private Wallet walletidentifier;
    /**
     * Default constructor, mainly for hibernate use.
     */
    public Customer() {
        // Default constructor
    }

    /** Constructor taking a given ID.
     * @param id to set
     */
    public Customer(String id) {
        this.userid = id;
    }

    /** Constructor taking a given ID.
     * @param email String object;
     * @param firstname String object;
     * @param id Integer object;
     * @param lastname String object;
     * @param password Byte[] object;
     * @param walletidentifier Wallet object;
     */
    public Customer(String email, String firstname, String id,
                    String lastname, String password, Wallet walletidentifier) {

        this.email = email;
        this.firstname = firstname;
        this.userid = id;
        this.lastname = lastname;
        this.password = password;
        this.walletidentifier = walletidentifier;
    }





    /** Return the type of this class. Useful for when dealing with proxies.
     * @return Defining class.
//     */
//    @Transient
//    public Class<?> getClassType() {
//        return Customer.class;
//    }


    /**
     * Return the value associated with the column: bank.
     * @return A Set&lt;Bank&gt; object (this.bank)
     */
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "userid"  )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @Column( nullable = false  )
    public Set<Bank> getBanks() {
        return this.banks;

    }

    @Basic( optional = false )
    @Column( nullable = false, length = 66  )
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Adds a bi-directional link of type Bank to the banks set.
     * @param bank item to add
     */
    public void addBank(Bank bank) {
        bank.setUserid(this);
        this.banks.add(bank);
    }


    /**
     * Set the value related to the column: bank.
     * @param bank the bank value you wish to set
     */
    public void setBanks(final Set<Bank> bank) {
        this.banks = bank;
    }

    /**
     * Return the value associated with the column: email.
     * @return A String object (this.email)
     */
    @Basic( optional = false )
    @Column( nullable = false, length = 66  )
    public String getEmail() {
        return this.email;

    }



    /**
     * Set the value related to the column: email.
     * @param email the email value you wish to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Return the value associated with the column: firstname.
     * @return A String object (this.firstname)
     */
    @Basic( optional = false )
    @Column( nullable = false, length = 53  )
    public String getFirstname() {
        return this.firstname;

    }



    /**
     * Set the value related to the column: firstname.
     * @param firstname the firstname value you wish to set
     */
    
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * Return the value associated with the column: id.
     * @return A Integer object (this.id)
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic( optional = false )
    @Column( name = "userid", nullable = false ,length = 64 )
    public String getId() {
        return this.userid;

    }



    /**
     * Set the value related to the column: id.
     * @param id the id value you wish to set
     */
    public void setId(final String id) {
        // If we've just been persisted and hashCode has been
        // returned then make sure other entities with this
        // ID return the already returned hash code
        if ( (this.userid == null || this.userid == "") &&
                (id != null) &&
                (this.hashCode != null) ) {
            SAVED_HASHES.put( id, this.hashCode );
        }
        this.userid = id;
    }

    /**
     * Return the value associated with the column: lastname.
     * @return A String object (this.lastname)
     */
    @Basic( optional = false )
    @Column( nullable = false, length = 53  )
    public String getLastname() {
        return this.lastname;

    }



    /**
     * Set the value related to the column: lastname.
     * @param lastname the lastname value you wish to set
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * Return the value associated with the column: password.
     * @return A Byte[] object (this.password)
     */
    @Basic( optional = false )
    @Column( nullable = false ,length = 60  )
    public String getPassword() {
        return this.password;

    }



    /**
     * Set the value related to the column: password.
     * @param password the password value you wish to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Return the value associated with the column: phonenumber.
     * @return A Set&lt;Phonenumber&gt; object (this.phonenumber)
     */
    @OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "userid"  )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @Column( nullable = false  )
    public Set<Phonenumber> getPhonenumbers() {
        return this.phonenumbers;

    }

    /**
     * Adds a bi-directional link of type Phonenumber to the phonenumbers set.
     * @param phonenumber item to add
     */
    public void addPhonenumber(Phonenumber phonenumber) {
        phonenumber.setUserid(this);
        this.phonenumbers.add(phonenumber);
    }


    /**
     * Set the value related to the column: phonenumber.
     * @param phonenumber the phonenumber value you wish to set
     */
    public void setPhonenumbers(final Set<Phonenumber> phonenumber) {
        this.phonenumbers = phonenumber;
    }

    /**
     * Return the value associated with the column: walletidentifier.
     * @return A Wallet object (this.walletidentifier)
     */
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY )
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic( optional = false )
    @JoinColumn(name = "walletidentifier", nullable = true )
    public Wallet getWalletidentifier() {
        return this.walletidentifier;

    }



    /**
     * Set the value related to the column: walletidentifier.
     * @param walletidentifier the walletidentifier value you wish to set
     */
    public void setWalletidentifier(final Wallet walletidentifier) {
        this.walletidentifier = walletidentifier;
    }

}
