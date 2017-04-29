package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "\"ordered_product\"")
@NamedQueries({ @NamedQuery(name = "OrderedProduct.findAll", query = "select o from OrderedProduct o"),
                @NamedQuery(name = "OrderedProduct.findByCustomerOrderId",
                            query =
                            "SELECT o FROM OrderedProduct o WHERE o.orderedProductPK.customerOrder = :customerOrder")
    })

public class OrderedProduct implements Serializable {
    @SuppressWarnings("compatibility:-8221269259405860458")
    private static final long serialVersionUID = 1524421982556396720L;
    @EmbeddedId
    protected OrderedProductPK orderedProductPK;
    @Column(name = "quantity", nullable = false)
    private short quantity;
    @ManyToOne
    @JoinColumn(name = "customer_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerOrder customerOrder;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product orderedProduct;

    public OrderedProduct() {
    }

    public OrderedProduct(OrderedProductPK orderedProductPK) {
            this.orderedProductPK = orderedProductPK;
        }
    
    public OrderedProduct(OrderedProductPK orderedProductPK, short quantity) {
            this.orderedProductPK = orderedProductPK;
            this.quantity = quantity;
        }
    
    public OrderedProduct(CustomerOrder customerOrder, Product orderedProduct, short quantity) {
        this.customerOrder = customerOrder;
        this.orderedProduct = orderedProduct;
        this.quantity = quantity;
    }

    public OrderedProductPK getOrderedProductPK() {
            return orderedProductPK;
        }

    public void setOrderedProductPK(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Product getOrderedProduct() {
        return orderedProduct;
    }

    public void setOrderedProduct(Product orderedProduct) {
        this.orderedProduct = orderedProduct;
    }
}
