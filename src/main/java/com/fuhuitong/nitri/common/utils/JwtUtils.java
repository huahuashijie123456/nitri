package com.fuhuitong.nitri.common.utils;

import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:08
 **/
@Component
public class JwtUtils {
    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";

    // private Map<String, String> tokenMap = new ConcurrentHashMap<>(32);

    @Autowired
    private RedisUtil redisUtil;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long access_token_expiration;

    @Value("${jwt.expiration}")
    private Long refresh_token_expiration;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public UserDetail getUserFromToken(String token) {
        UserDetail userDetail;
        userDetail = JSON.parseObject(redisUtil.get(token).toString(), UserDetail.class);
    /*    try {
            final Claims claims = getClaimsFromToken(token);
            String userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            String roleName = claims.get(CLAIM_KEY_AUTHORITIES).toString();
            Role role = Role.builder().name(roleName).build();
            userDetail = new UserDetail(userId, username, role, "");
        } catch (Exception e) {
            userDetail = null;
        }*/
        return userDetail;
    }

    public String getUserIdFromToken(String token) {
        if (!StringUtil.isNullOrEmpty(token))
            token = token.substring(token.indexOf("^") + 1);
        String userId = "";
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = (String.valueOf(claims.get(CLAIM_KEY_USER_ID)));
        } catch (Exception e) {
            //userId = 0;
        }
        return userId;
    }

    public String getUsernameFromToken(String token) {
        if (!StringUtil.isNullOrEmpty(token))
            token = token.substring(token.indexOf("^") + 1);
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
            //  throw  new AsException(ResultJson.failure(ResultCode.UNAUTHORIZED));
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public String generateAccessToken(UserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, authoritiesToArray(userDetail.getAuthorities()).get(0));
        return generateAccessToken(userDetail.getUsername(), claims);
    }

    public String generateAccessMobileToken(UserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        //  claims.put(CLAIM_KEY_AUTHORITIES, authoritiesToArray(userDetail.getAuthorities()).get(0));
        return generateAccessToken(userDetail.getUsername(), claims);
    }


    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(), claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }


    public Boolean validateToken(String token, UserDetails userDetails) {

        UserDetail userDetail = (UserDetail) userDetails;
        final String userId = getUserIdFromToken(token);
        final String username = getUsernameFromToken(token);
//        final Date created = getCreatedDateFromToken(token);
        return (userId.equals(userDetail.getId().toString())
                && username.equals(userDetail.getUsername())
                && !isTokenExpired(token)
//                && !isCreatedBeforeLastPasswordReset(created, userDetail.getLastPasswordResetDate())
        );
    }

    public String generateRefreshToken(UserDetail userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        // 只授于更新 token 的权限
        String roles[] = new String[]{JwtUtils.ROLE_REFRESH_TOKEN};
        claims.put(CLAIM_KEY_AUTHORITIES, JSON.toJSON(roles));
        return generateRefreshToken(userDetail.getUsername(), claims);
    }

    public void putToken(String userName, String token) {
        // tokenMap.put(userName, token);
        redisUtil.setExpire(token, userName, access_token_expiration);
    }

    public void deleteToken(String token) {
        // tokenMap.remove(userName);
        redisUtil.del(token);
    }

    public boolean containToken(String userName, String token) {
     /*   if (userName != null && tokenMap.containsKey(userName) && tokenMap.get(userName).equals(token)) {
            return true;
        }*/
        if (token == null || userName == null) {
            return false;
        }
        Object u = redisUtil.get(token);
        if (u == null) {
            return false;
        } else if (userName != null && userName.equals(JSON.parseObject((String) u).getString("username"))) {
            return true;
        }
        return false;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
            // throw  new AsException(ResultJson.failure(ResultCode.UNAUTHORIZED));
        }
        return claims;
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        token = token.substring(token.indexOf("^") + 1);
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Map<String, Object> generateClaims(UserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USER_ID, userDetail.getId());
        return claims;
    }

    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, access_token_expiration);
    }

    private List authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }


    private String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, refresh_token_expiration);
    }


    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

}

