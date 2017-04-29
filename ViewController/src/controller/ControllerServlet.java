package controller;

import cart.ShoppingCart;

import entity.Category;

import entity.CustomerOrder;
import entity.Product;

import java.io.IOException;

import java.util.Collection;

import java.util.Map;

import javax.ejb.EJB;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import validate.Validator;

import view.OrderManagerBean;
import view.SessionEJBBean;


public class ControllerServlet extends HttpServlet {
    //private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private String surcharge;
    @EJB(beanName = "SessionEJB")
    private SessionEJBBean dataBean;
    @EJB(beanName = "OrderManager")
    private transient OrderManagerBean orderManager;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // initialize servlet with configuration information
        surcharge = config.getServletContext().getInitParameter("deliverySurcharge");
        // store category list in servlet context
        getServletContext().setAttribute("categories", dataBean.getCategoryFindAll());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Collection<Product> categoryProducts;
        Category selectedCategory;

        // if category page is requested
        if (userPath.equals("/category")) {
            // get categoryId from request
            String categoryId = request.getQueryString();

            if (categoryId != null) {

                // get selected category
                selectedCategory = dataBean.getCategoryById(Byte.parseByte(categoryId));
                // place selected category in request scope
                session.setAttribute("selectedCategory", selectedCategory);
                // get all products for selected category
                categoryProducts = selectedCategory.getProductCollection();
                // place category products in request scope
                session.setAttribute("categoryProducts", categoryProducts);
            }

            // if cart page is requested
        } else if (userPath.equals("/viewCart")) {
            String clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {

                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                cart.clear();
            }

            userPath = "/cart";

            userPath = "/cart";

            // if checkout page is requested
        } else if (userPath.equals("/checkout")) {
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

            // calculate total
            cart.calculateTotal(surcharge);

            // forward to checkout page and switch to a secure channel
        } else if (userPath.equals("/testdata")) {
            
            Map orderMap = orderManager.test(3);
            request.setAttribute("orderRecord", orderMap.get("orderRecord"));
            
            userPath = "/confirmation2";

            // if user switches language
        } else if (userPath.equals("/chooseLanguage")) {
            // TODO: Implement language request

        }

        // use RequestDispatcher to forward request internally
        String url = "/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Validator validator = new Validator();

        // if addToCart action is called
        if (userPath.equals("/addToCart")) {
            // if user is adding item to cart for first time
            // create cart object and attach it to user session
            if (cart == null) {

                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }

            // get user input from request
            String productId = request.getParameter("productId");

            if (!productId.isEmpty()) {

                Product product = dataBean.getProductById(Integer.parseInt(productId));
                cart.addItem(product);
            }

            userPath = "/category";


            // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {
            // get input from request
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");

            Product product = dataBean.getProductById(Integer.parseInt(productId));
            cart.update(product, quantity);

            userPath = "/cart";
            
            // if purchase action is called
        } else if (userPath.equals("/purchase")) {
            if (cart != null) {
                // extract user data from request
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String cityRegion = request.getParameter("cityRegion");
                String ccNumber = request.getParameter("creditcard");

                // validate user data
                boolean validationErrorFlag = false;
                validationErrorFlag =
                    validator.validateForm(name, email, phone, address, cityRegion, ccNumber, request);

                // if validation error found, return user to checkout
                if (validationErrorFlag == true) {
                    request.setAttribute("validationErrorFlag", validationErrorFlag);
                    userPath = "/checkout";

                    // otherwise, save order to database
                } else {

                    int orderId = orderManager.placeOrder(name, email, phone, address, cityRegion, ccNumber, cart);

                    // if order processed successfully send user to confirmation page
                    if (orderId != 0) {

                        // dissociate shopping cart from session
                        cart = null;

                        // end session
                        session.invalidate();

                        // get order details
                        Map orderMap = orderManager.getOrderDetails(orderId);

                        // place order details in request scope
                        request.setAttribute("customer", orderMap.get("customer"));
                        request.setAttribute("products", orderMap.get("products"));
                        request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                        request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));

                        userPath = "/confirmation";

                        // otherwise, send back to checkout page and display error
                    } else {
                        userPath = "/checkout";
                        request.setAttribute("orderFailureFlag", true);
                    }
                }
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
