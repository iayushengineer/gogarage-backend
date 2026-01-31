package com.app.filters;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.utils.JwtTokenUtil;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// Skip JWT processing for auth endpoints
		String requestPath = req.getRequestURI();
		if (requestPath.startsWith("/auth/") || requestPath.startsWith("/servicepackage/") ||
		    requestPath.startsWith("/home/") || requestPath.startsWith("/h2-console/")) {
			chain.doFilter(req, res);
			return;
		}

		String authToken = req.getHeader("Authorization");

		String email = null;
		String role = null;
		String password = null;

		if (authToken != null) {
			try {
				authToken = authToken.substring(7);

				email = jwtTokenUtil.getEmailFromToken(authToken);
				role = jwtTokenUtil.getRoleFromToken(authToken);
				password = jwtTokenUtil.getPasswordFromToken(authToken);

				req.setAttribute("email", email);
				req.setAttribute("password", password);

			} catch (Exception e) {
				System.out.println("Invalid Token");
			}
		} else {
			System.out.println("Token Not Found");
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role)));
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				logger.info("authenticated user " + email + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(req, res);
	}
}
