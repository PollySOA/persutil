package net.ausiasmarch.persutil.helper;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTHelper {
    
    private static final String SECRET_KEY = "tu_clave_secreta_aqui_1234567890_DAWSIAS"; // Cambia esto por una clave segura

    /**
     * Valida un token JWT y devuelve el username si es válido
     * @param strToken Token JWT a validar
     * @return username si el token es válido, null si no lo es o si está expirado
     */
    public static String validate(String strToken) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(strToken);
            
            if (jws == null || jws.getBody() == null) {
                return null; // token no válido
            } else {
                
                // Comprobar expiración del token
                Date expiration = jws.getBody().getExpiration();
                if (expiration != null && expiration.before(new Date())) {
                    return null; // token expirado
                }
                
                // Extraer el nombre de usuario del token
                String username = jws.getBody().get("username", String.class);
                return username;
            }
            
        } catch (Exception e) {
            // Si hay cualquier error (token expirado, firma inválida, formato incorrecto, etc.)
            return null;
        }
    }

    /**
     * Genera un nuevo token JWT para un usuario
     * @param username Nombre de usuario
     * @return Token JWT generado
     */
    public static String generateJWT(String username) {
        return Jwts.builder()
                .setIssuer("ausiasmarch.net")
                .setSubject("DAWsiasmarchPERSUTIL")
                .claim("username", username)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 10800000)) // 3 horas
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }
}