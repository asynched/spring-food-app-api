package tech.asynched.ilunch.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired()
  private JwtTokenGenerator tokenGenerator;

  @Autowired()
  private CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var token = request.getHeader("Authorization");

    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    if (!token.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    token = token.replace("Bearer ", "");

    if (!tokenGenerator.validate(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    var username = tokenGenerator.getUsername(token);
    var user = userDetailsService.loadUserByUsername(username);

    var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}
