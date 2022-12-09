package com.miniproject.config;

import com.miniproject.model.UserDao;
import com.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyFilter implements Filter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    public static List<String> allowedUrls;
    public static List<String> allowedUrlsUser;
    public static List<String> allowedUrlsAdmin;


    static{
        allowedUrls = new ArrayList<>();
        allowedUrls.add("/login");
        allowedUrls.add("/register");
        allowedUrls.add("/productList");

    }

    static{
        allowedUrlsUser = new ArrayList<>();
        allowedUrlsUser.add("/user/profile");
        allowedUrlsUser.add("/user/changePassword");
        allowedUrlsUser.add("/products/");
        allowedUrlsUser.add("/order");

    }
    static{
        allowedUrlsAdmin = new ArrayList<>();
        allowedUrlsAdmin.add("/user/profile");
        allowedUrlsAdmin.add("/user/changePassword");
        allowedUrlsAdmin.add("/addProduct");
        allowedUrlsAdmin.add("/products/");
        allowedUrlsAdmin.add("/products/stock/");

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest) request;
        //check if api is allowed
        if(isAllowed(req.getRequestURI())){
            chain.doFilter(request, response);
        }
        else{
            String token = req.getHeader("authToken");
            //check if token is verified and valid
            if(jwtUtil.isVerified(token)){
                String email = jwtUtil.getSubject(token);
                request.setAttribute("email", email);
                UserDao user = userRepository.findByEmailId(email);
                String roleName = user.getRole().getRollName();
                if (roleName.equals("ADMIN")){
                    if (isAllowedAdmin(req.getRequestURI())){
                        chain.doFilter(request, response);
                    }
                }
                else {
                    if (isAllowedUser(req.getRequestURI())){
                        chain.doFilter(request, response);
                    }
                }
            }else{
                throw new ServletException("Exception is authorization");
            }
        }
    }

    private boolean isAllowed(String requestURI) {
        return allowedUrls.stream().anyMatch(requestURI::startsWith);
    }
    private boolean isAllowedUser(String requestURI) {
        return allowedUrlsUser.stream().anyMatch(requestURI::startsWith);
    }
    private boolean isAllowedAdmin(String requestURI) {
        return allowedUrlsAdmin.stream().anyMatch(requestURI::startsWith);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
