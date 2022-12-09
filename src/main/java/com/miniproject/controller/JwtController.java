package com.miniproject.controller;

import com.miniproject.config.JwtUtil;
import com.miniproject.model.*;
import com.miniproject.service.JwtSessionService;
import com.miniproject.service.JwtUserDetailsService;
import com.miniproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtSessionService jwtSessionService;

    @Autowired
    private ProductService productService;
                //User methods
    // user login method
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) throws Exception {
        final String user= userDetailsService.loginCheck(jwtRequest.getEmailId(), jwtRequest.getPassword());
        if (user!=null){
            final String token = jwtUtil.generateJwtToken(user);
            jwtSessionService.tokenSave(jwtRequest.getEmailId(),token);
            response.setHeader("authToken", token);
            return ResponseEntity.ok(userDetailsService.profileShow(jwtRequest.getEmailId()));
        }
        return ResponseEntity.ok("EmailId and Password is Invalid");
    }

    // user register method
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDao user) throws Exception {
        System.out.println(user);
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    //for view user profile

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String token=request.getHeader("authToken");
        String email = jwtUtil.getSubject(token);
        String validate = jwtSessionService.checkToken(email, token);
        if (validate!=null){
            return ResponseEntity.ok(userDetailsService.profileShow(email));
        }
        return ResponseEntity.ok("Invalid Token");
    }

    //for update user profile

    @RequestMapping(value = "/user/profile", method = RequestMethod.PUT)
    public ResponseEntity<?> getUser(HttpServletRequest request,@RequestBody UserDao userDao) {
        String token=request.getHeader("authToken");
        String email = jwtUtil.getSubject(token);
        String validate = jwtSessionService.checkToken(email, token);
        if (validate!=null){
            return ResponseEntity.ok(userDetailsService.updateProfile(userDao,email));
        }
        return ResponseEntity.ok("Invalid Token");
    }

    // user change password method
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(HttpServletRequest request,HttpServletResponse response, @RequestBody JwtPassword jwtPassword) throws Exception {
        String token=request.getHeader("authToken");
        String email = jwtUtil.getSubject(token);
        String validate = jwtSessionService.checkToken(email, token);
        if (validate!=null){
            String verifyEmail=userDetailsService.changePassword(email,jwtPassword.getNewPassword(),jwtPassword.getOldPassword());
            if (verifyEmail!=null){
                String newToken = jwtUtil.generateJwtToken(email);
                jwtSessionService.changePasswordMethod(email,newToken);
                response.setHeader("authToken", newToken);
                return ResponseEntity.ok(userDetailsService.profileShow(email));
            }
            return ResponseEntity.ok("Old password not match");
        }
        return ResponseEntity.ok("Invalid Token");
    }
        // Product add Methods
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(HttpServletRequest request, @RequestBody Product product){
        String token=request.getHeader("authToken");
        String email = jwtUtil.getSubject(token);
        String validate = jwtSessionService.checkToken(email, token);
        if (validate!= null){
            return ResponseEntity.ok(productService.productSave(product));
        }
        else {
            return ResponseEntity.ok("Token is not valid");
        }
    }

    // Product All List

    @RequestMapping(value ="/productList", method = RequestMethod.GET)
    public List<Product> productList(HttpServletRequest request){
        return productService.allProductList();
    }

    //Product findById

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Optional<Product> productById(HttpServletRequest request,@PathVariable("id") Long id){
        String token=request.getHeader("authToken");
        String email = jwtUtil.getSubject(token);
        String validate = jwtSessionService.checkToken(email, token);
        return productService.findProductById(id);
    }

    //Product DeleteById

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(HttpServletRequest request,@PathVariable("id") Long id){
        String token=request.getHeader("authToken");
        String email = jwtUtil.getSubject(token);
        String validate = jwtSessionService.checkToken(email, token);
        String x = productService.deleteProductById(id);
        if (x!=null){
            return  ResponseEntity.ok("Delete SuccessFully");
        }
        else {
            return ResponseEntity.ok("Id not found");
        }
    }

    // Product Stock Update

    @RequestMapping(value = "/products/stock/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> stockUpdate(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody ProductInventory productInventory){
        String token=request.getHeader("authToken");
        String email = jwtUtil.getSubject(token);
        String validate = jwtSessionService.checkToken(email, token);
        return ResponseEntity.ok(productService.stockUpdate(id,productInventory));
    }
}
