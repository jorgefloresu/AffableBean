package entity;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Collection;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "CustomerOrder.findAll", query = "select o from CustomerOrder o") })
@Table(name = "\"customer_order\"")
public class CustomerOrder implements Serializable {
    private static final long serialVersionUID = 1831082202503516488L;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "confirmation_number", nullable = false)
    private int confirmation_number;
    @Column(name = "date_created", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date_created;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerOrder")
    private Collection<OrderedProduct> orderedProductCollection;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName="id")
    private Customer customer;

    public CustomerOrder() {
    }
    
    public CustomerOrder(int id) {
        this.id = id;
    }

    public CustomerOrder(BigDecimal amount, int confirmation_number, Date date_created, int id) {
        this.amount = amount;
        this.confirmation_number = confirmation_number;
        //this.customer = customer;
        this.date_created = date_created;
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getConfirmation_number() {
        return confirmation_number;
    }

    public void setConfirmation_number(int confirmation_number) {
        this.confirmation_number = confirmation_number;
    }


    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<OrderedProduct> getOrderedProductCollection() {
        return orderedProductCollection;
    }

    public void setOrderedProductCollection(Collection<OrderedProduct> orderedProductCollection) {
        this.orderedProductCollection = orderedProductCollection;
    }

    public OrderedProduct addOrderedProduct(OrderedProduct orderedProduct) {
        getOrderedProductCollection().add(orderedProduct);
        orderedProduct.setCustomerOrder(this);
        return orderedProduct;
    }

    public OrderedProduct removeOrderedProduct(OrderedProduct orderedProduct) {
        getOrderedProductCollection().remove(orderedProduct);
        orderedProduct.setCustomerOrder(null);
        return orderedProduct;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
