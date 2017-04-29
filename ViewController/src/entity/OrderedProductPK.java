package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderedProductPK implements Serializable {
    @SuppressWarnings("compatibility:5647499078824364641")
    private static final long serialVersionUID = 1L;

    @Column(name = "customer_order_id")
    private int customerOrder;
    @Column(name = "product_id")
    private int orderedProduct;

    public OrderedProductPK() {
    }

    public OrderedProductPK(int customerOrder, int orderedProduct) {
        this.customerOrder = customerOrder;
        this.orderedProduct = orderedProduct;
    }

    public boolean equals(Object other) {
        if (other instanceof OrderedProductPK) {
            final OrderedProductPK otherOrderedProductPK = (OrderedProductPK) other;
            final boolean areEqual =
                (otherOrderedProductPK.customerOrder == customerOrder &&
                 otherOrderedProductPK.orderedProduct == orderedProduct);
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public int getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(int customerOrder) {
        this.customerOrder = customerOrder;
    }

    public int getOrderedProduct() {
        return orderedProduct;
    }

    public void setOrderedProduct(int orderedProduct) {
        this.orderedProduct = orderedProduct;
    }
}
