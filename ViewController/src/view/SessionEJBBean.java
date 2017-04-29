package view;

import entity.Category;
import entity.Customer;
import entity.CustomerOrder;
import entity.Product;
import entity.OrderedProduct;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "SessionEJB", mappedName = "AffableBean-ViewController-SessionEJB")
public class SessionEJBBean {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public SessionEJBBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    public void removeProduct(Product product) {
        product = em.find(Product.class, product.getId());
        em.remove(product);
    }

    /** <code>select o from Product o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Product> getProductFindAll() {
        return em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    public void removeCategory(Category category) {
        category = em.find(Category.class, category.getId());
        em.remove(category);
    }

    /** <code>select o from Category o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Category> getCategoryFindAll() {
        return em.createNamedQuery("Category.findAll", Category.class).getResultList();
    }

    /** <code>select o from Category o where o.id = :id</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Category> getCategoryFindById(Byte id) {
        return em.createNamedQuery("Category.findById", Category.class)
                 .setParameter("id", id)
                 .getResultList();
    }

    public Category getCategoryById(Byte id) {
        return em.find(Category.class, id);
    }
    
    public Product getProductById(int id) {
        return em.find(Product.class, id);
    }


    public void removeCustomerOrder(CustomerOrder customerOrder) {
        customerOrder = em.find(CustomerOrder.class, customerOrder.getId());
        em.remove(customerOrder);
    }

    /** <code>select o from CustomerOrder o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CustomerOrder> getCustomerOrderFindAll() {
        return em.createNamedQuery("CustomerOrder.findAll", CustomerOrder.class).getResultList();
    }

    /** <code>select o from Customer o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Customer> getCustomerFindAll() {
        return em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }
    
    public CustomerOrder getCustomerOrderById(int id) {
        return em.find(CustomerOrder.class, id);
    }
    
    public OrderedProduct getOrderedProductById(int id) {
        return em.find(OrderedProduct.class, id);
    }

    // manually created
    public List<OrderedProduct> findByOrderId(Object id) {
        return em.createNamedQuery("OrderedProduct.findByCustomerOrderId", OrderedProduct.class).setParameter("customerOrder", id).getResultList();
    }


}
