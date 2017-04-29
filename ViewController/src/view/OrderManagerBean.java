package view;

import cart.ShoppingCart;

import cart.ShoppingCartItem;

import entity.Customer;

import entity.CustomerOrder;

import entity.OrderedProduct;
import entity.OrderedProductPK;

import entity.Product;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "OrderManager", mappedName = "AffableBean-ViewController-OrderManager")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManagerBean {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;
    @Resource
    SessionContext sessionContext;
    @EJB(beanName = "SessionEJB")
    private SessionEJBBean dataBean;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber,
                          ShoppingCart cart) {

        try {
            Customer customer = addCustomer(name, email, phone, address, cityRegion, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            return 0;
        }
    }

    private Customer addCustomer(String name, String email, String phone, String address, String cityRegion,
                                 String ccNumber) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCity_region(cityRegion);
        customer.setCc_number(ccNumber);

        em.persist(customer);
        return customer;
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmation_number(i);

        em.persist(order);
        return order;
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        em.flush();

        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {

            int productId = scItem.getProduct().getId();

            // set up primary key object
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrder(order.getId());
            orderedProductPK.setOrderedProduct(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);

            // set quantity
            orderedItem.setQuantity(scItem.getQuantity());

            em.persist(orderedItem);
        }
    }

    public Map getOrderDetails(int orderId) {

        Map orderMap = new HashMap();

        // get order
        CustomerOrder order = dataBean.getCustomerOrderById(orderId);

        // get customer
        Customer customer = order.getCustomer();

        // get all ordered products
        List<OrderedProduct> orderedProducts = dataBean.findByOrderId(orderId);

        // get product details for ordered items
        List<Product> products = new ArrayList<Product>();

        for (OrderedProduct op : orderedProducts) {

            Product p = (Product) dataBean.getProductById(op.getOrderedProductPK().getOrderedProduct());
            products.add(p);
        }

        // add each item to orderMap
        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("products", products);

        return orderMap;
    }
    
    public Map test(int orderId) {
        Map orderMap = new HashMap();
        CustomerOrder order = dataBean.getCustomerOrderById(orderId);
        orderMap.put("orderRecord", order);
        return orderMap;
    }

    public OrderManagerBean() {
    }
}
