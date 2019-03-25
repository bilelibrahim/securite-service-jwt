package org.sid.sec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Bilel on 23/03/2019.
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //Gestion Cross site .....
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Headers, Access-Control-Request-Method, authorization");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Headers,  Access-Control-Allow-Credentials, authorization");
        httpServletResponse.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,PATCH");
                // Ce n'est pas la peine de chercher token ect ....
        if("OPTIONS".equals(httpServletRequest.getMethod())){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if("/login".equals(httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        // Cas ou on accède aux taches utilisateurs ou roles ....
        String jwt = httpServletRequest.getHeader(SecurityParams.JWT_HEADER_NAME);
        if(jwt == null || !jwt.startsWith(SecurityParams.HEADER_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(jwt.substring(SecurityParams.HEADER_PREFIX.length()));
        String username = decodedJWT.getSubject();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        decodedJWT.getClaims().get("roles").asList(String.class).forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r));
        });
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
