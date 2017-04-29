package entity;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "Product.findAll", query = "select o from Product o") })
@Table(name = "\"product\"")
public class Product implements Serializable {
    private static final long serialVersionUID = -2447311434626750200L;
    @Column(name = "description")
    private String description;
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "last_update", nullable = false)
    private Timestamp last_update;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "orderedProduct", cascade = CascadeType.ALL)
    private Collection<OrderedProduct> orderedProductCollection;

    public Product() {
    }
    
    public Product(int id) {
        this.id = id;
    }

    public Product(String description, int id, Timestamp last_update, String name, BigDecimal price) {
        //this.category = category;
        this.description = description;
        this.id = id;
        this.last_update = last_update;
        this.name = name;
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection<OrderedProduct> getOrderedProductCollection() {
        return orderedProductCollection;
    }

    public void setOrderedProductCollection(Collection<OrderedProduct> orderedProductCollection) {
        this.orderedProductCollection = orderedProductCollection;
    }

    public OrderedProduct addOrderedProduct(OrderedProduct orderedProduct) {
        getOrderedProductCollection().add(orderedProduct);
        orderedProduct.setOrderedProduct(this);
        return orderedProduct;
    }

    public OrderedProduct removeOrderedProduct(OrderedProduct orderedProduct) {
        getOrderedProductCollection().remove(orderedProduct);
        orderedProduct.setOrderedProduct(null);
        return orderedProduct;
    }
}
