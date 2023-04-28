package com.payhere.infrastructure.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.domain.PrincipalDetails;
import com.payhere.presentation.request.SignInRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        SignInRequest signInRequest = null;

        try {
            signInRequest = objectMapper.readValue(request.getInputStream(), SignInRequest.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        log.info("SignInRequest : {}", signInRequest);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInRequest.getPhoneNumber(), signInRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = jwtTokenProvider.generateToken(principalDetails.getUsername());

        response.getWriter().write("Bearer " + jwtToken);
        response.getWriter().flush();
    }
}
