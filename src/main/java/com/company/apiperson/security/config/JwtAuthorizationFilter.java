package com.company.apiperson.security.config;

import com.company.apiperson.security.utils.TokenProviderService;
import com.company.apiperson.security.service.UserService;
import com.company.apiperson.security.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = httpServletRequest.getHeader(Constants.HEADER_AUTHORIZATION_KEY);
		String authorizationParam = httpServletRequest.getParameter(Constants.TOKEN_KEY);

		if ((StringUtils.isEmpty(authorizationHeader) || !authorizationHeader
				.startsWith(Constants.TOKEN_BEARER_PREFIX)) && (StringUtils.isEmpty(authorizationParam))) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		if(!StringUtils.isEmpty(authorizationParam)) {
			authorizationHeader = Constants.TOKEN_BEARER_PREFIX + " " + authorizationParam;
		}
		final String token = authorizationHeader.replace(Constants.TOKEN_BEARER_PREFIX + " ", "");

		String userName = TokenProviderService.getUserName(token);
		UserDetails user = userService.loadUserByUsername(userName);

		UsernamePasswordAuthenticationToken authenticationToken = TokenProviderService.getAuthentication(token, user);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
