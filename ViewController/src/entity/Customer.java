package entity;

import java.io.Serializable;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "Customer.findAll", query = "select o from Customer o") })
@Table(name = "\"customer\"")
public class Customer implements Serializable {
    private static final long serialVersionUID = 7191616196585327646L;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "cc_number", nullable = false)
    private String cc_number;
    @Column(name = "city_region", nullable = false)
    private String city_region;
    @Column(name = "email", nullable = false)
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phone;
    @OneToMany(mappedBy = "customer", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<CustomerOrder> customerOrderCollection;

    public Customer() {
    }

    public Customer(String address, String cc_number, String city_region, String email, int id, String name,
                    String phone) {
        this.address = address;
        this.cc_number = cc_number;
        this.city_region = city_region;
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCc_number() {
        return cc_number;
    }

    public void setCc_number(String cc_number) {
        this.cc_number = cc_number;
    }

    public String getCity_region() {
        return city_region;
    }

    public void setCity_region(String city_region) {
        this.city_region = city_region;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<CustomerOrder> getCustomerOrderCollection() {
        return customerOrderCollection;
    }

    public void setCustomerOrderCollection(Collection<CustomerOrder> customerOrderCollection) {
        this.customerOrderCollection = customerOrderCollection;
    }

    public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
        getCustomerOrderCollection().add(customerOrder);
        customerOrder.setCustomer(this);
        return customerOrder;
    }

    public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder) {
        getCustomerOrderCollection().remove(customerOrder);
        customerOrder.setCustomer(null);
        return customerOrder;
    }
}
