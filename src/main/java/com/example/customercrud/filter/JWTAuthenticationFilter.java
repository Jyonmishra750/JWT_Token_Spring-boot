package com.example.customercrud.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.customercrud.entity.Customer;
import com.example.customercrud.service.CustomerJwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
    @Autowired
    private CustomerJwtService customerJwtService;
    
    @Autowired
    private ObjectMapper objectMapper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var path = request.getRequestURI();
		var passedPaths = List.of("login", "signup", "swagger", "api-doc");

        for (var passedPath : passedPaths) {
            if (path.contains(passedPath) || path.matches(passedPath)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

		String token = request.getHeader("Authorization");
		
		try {
            if (token == null) {
                throw new RuntimeException("Token should not be empty");
            }

            String email = customerJwtService.validateToken(token.substring(7));

            if (email == null || email.isEmpty() || email.isBlank())
                throw new RuntimeException("Token not found");

            Customer customer = new Customer();
            var auth = new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword(), null);

            SecurityContextHolder.getContext().setAuthentication(auth);

            request.setAttribute("email", email);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            var problemDetails = ProblemDetail.forStatus(400);
            problemDetails.setTitle("Token issue");
            problemDetails.setDetail(e.getMessage());

            response.setContentType("application/json");
            response.setStatus(400);
            response.getWriter().println(objectMapper.writeValueAsString(problemDetails));
        }
	}

}
