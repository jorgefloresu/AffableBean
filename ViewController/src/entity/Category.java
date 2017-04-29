package entity;

import java.io.Serializable;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "Category.findAll", query = "select o from Category o") })
@Table(name = "\"category\"")
public class Category implements Serializable {
    private static final long serialVersionUID = 4839951052803910932L;
    @Id
    @Column(name = "id", nullable = false)
    private byte id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "category", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<Product> productCollection;

    public Category() {
    }

    public Category(byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public Product addProduct(Product product) {
        getProductCollection().add(product);
        product.setCategory(this);
        return product;
    }

    public Product removeProduct(Product product) {
        getProductCollection().remove(product);
        product.setCategory(null);
        return product;
    }
}
