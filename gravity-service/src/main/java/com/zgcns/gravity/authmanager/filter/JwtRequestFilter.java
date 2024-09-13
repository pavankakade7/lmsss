package com.zgcns.gravity.authmanager.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zgcns.gravity.authmanager.service.CustomUserDetailsService;
import com.zgcns.gravity.authmanager.utilities.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	 @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private CustomUserDetailsService userDetailsService;

		@Override
		protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
				jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
				throws jakarta.servlet.ServletException, IOException {
			 final String authorizationHeader = request.getHeader("Authorization");

		        String username = null;
		        String jwt = null;

		        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		            jwt = authorizationHeader.substring(7);
		            username = jwtUtil.extractUsername(jwt);
		        }

		        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		            if (jwtUtil.validateToken(jwt, userDetails)) {
		                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
		                        userDetails, null, userDetails.getAuthorities());
		                usernamePasswordAuthenticationToken
		                        .setDetails(new WebAuthenticationDetailsSource().buildDetails((jakarta.servlet.http.HttpServletRequest) request));
		                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		            }
		        }
		        filterChain.doFilter(request, response);
			
		}
}