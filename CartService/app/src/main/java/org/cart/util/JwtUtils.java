package org.cart.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.HttpServletRequest;
import org.cart.exception.ForbiddenException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.List;

@Component
public class JwtUtils {

    private final String secret = "b936cee86c9f87aa5d3c6f2e84cb5a4239a5fe50480a6ec66b70ab5b1f4ac6730c6c515421b327ec1d69402e53dfb49ad7381eb067b338fd7b0cb22247225d47";
    private Key key;

    public JwtUtils() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    public static boolean isAllowed(HttpServletRequest request, Scopes scope) {
        List<String> scopes = (List<String>) request.getAttribute("scopes");
        if (scopes != null) {
            if (scopes.contains(scope.getScope())) {
                return true;
            }
        }
        return false;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return getUserNameFromJwtToken(token).equals(userDetails.getUsername());
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public static void validateAccess(HttpServletRequest request, Scopes scope) throws ForbiddenException {
        if (!JwtUtils.isAllowed(request, scope)) {
            throw new ForbiddenException("Access Denied: "+ scope);
        }
    }
}