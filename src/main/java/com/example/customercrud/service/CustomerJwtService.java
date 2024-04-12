package com.example.customercrud.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.customercrud.entity.Customer;
import com.example.customercrud.repository.CustomerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class CustomerJwtService implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public static Instant now = Instant.now();
	public final long EXPIRATION_TIME_MS = 5*60*1000;
	private final String secret = "385a472fe0ba0c4f085773084a786f59f0942e3e4abca5b0dffa5edbde9b1a57";
	
	@SuppressWarnings("deprecation")
	public String generateToken(String email) {
	
        Instant expiryInstant = now.plus(EXPIRATION_TIME_MS, ChronoUnit.MILLIS);
        
		return Jwts.builder()
                .signWith(getKey())
                .subject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryInstant))
                .compact();
	}
	
	public String validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
	
	private SecretKey getKey() {
        byte bytes[] = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found"));
		return customer;
	}
}
