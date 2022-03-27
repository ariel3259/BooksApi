package com.ariel.BooksApi.Utils;

import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class Token {

    private JWT jwt;

    private final String secret = "LoremLoremLorem";

    /**
     *
     * @param subject String(dni value)
     * @return String
     */
    public String getToken(String subject){
        //Create A hmac signer
        HMACSigner signer = HMACSigner.newSHA256Signer(this.secret);

        //Build jwt
        this.jwt = new JWT().setSubject(subject)
                .setIssuer("http://localhost:3000")
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60));

        //return jwt
        return JWT.getEncoder().encode(this.jwt, signer);
    }

    /**
     *
     * @param token String
     * @param subject String(dni value)
     * @return Boolean
     */
    public boolean verifyToken(String token, String subject){
        //Create hmac verifier
        HMACVerifier verifier = HMACVerifier.newVerifier(this.secret);

        //Incomplete data
        if(token == null || subject == null){
            return false;
        }

        //Decode jwt
        try{
            this.jwt = JWT.getDecoder().decode(token, verifier);

            return this.jwt.subject.equals(subject);
        }
        //Invalid jwt or Expired jwt
        catch (Exception e){
            return false;
        }
    }
}
