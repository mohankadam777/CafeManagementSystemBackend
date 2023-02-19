package com.example.demo.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomerUserDetailsService service;
	
	Claims claims = null;
	private String username=null;
	Claims prevClaims;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		
		// TODO Auto-generated method stub
		if(request.getServletPath().matches("/user/login|/user/forgotPassword|/user/signup")) {
			filterChain.doFilter(request, response);
		}
		else {
			String authHeader=request.getHeader("Authorization");
//			System.out.println(authHeader);
			String token = null;
			if((authHeader!=null)&&(authHeader.startsWith("Bearer "))) {
				token=authHeader.substring(7);
				username=jwtService.extractUsername(token);
				
				claims=jwtService.extractAllClaims(token);
				prevClaims=claims;
//				System.err.println(">>>caims "+claims.getSubject());
//				System.err.println(">>>prevcaims "+prevClaims);
			
				}
				if((username!=null ) &&( SecurityContextHolder.getContext().getAuthentication()==null )) {

					UserDetails userDetails=service.loadUserByUsername(username);
//				
					if(jwtService.validateToken(token, userDetails)) {
					
						UsernamePasswordAuthenticationToken uPAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
						uPAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(uPAuthenticationToken);
					}
				}
				filterChain.doFilter(request, response);
		}
		
	}
	public boolean isAdmin() {
//		System.err.println(prevClaims.get("role")+"<<<<<<<<<<<<<<");
		return "admin".equalsIgnoreCase((String) claims.get("role"));
				}
	public boolean isUser() {
		return "user".equalsIgnoreCase((String) claims.get("role"));
				}
	public String getCurrentUser() {
	return username;	
	}
	

}
