package com.mckesson.mhs.interop.jwt;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;

public class JWTTokenUtil {

	private static int expirationTimeInterval = 60 *1000000;
	private static String issuer = new String("https://interop.mhs.com");
	   
   private static PrivateKey getPrivateKey(String fileName) 
	        throws IOException, GeneralSecurityException {
	    PrivateKey key = null;
	    InputStream is = null;
	    BufferedReader br = null;
	    try {
	    	is = new FileInputStream(fileName);
	        br = new BufferedReader(new InputStreamReader(is));
	        StringBuilder builder = new StringBuilder();
	        boolean inKey = false;
	        for (String line = br.readLine(); line != null; line = br.readLine()) {
	            if (!inKey) {
	                if (line.startsWith("-----BEGIN ") && 
	                        line.endsWith(" PRIVATE KEY-----")) {
	                    inKey = true;
	                }
	                continue;
	            }
	            else {
	                if (line.startsWith("-----END ") && 
	                        line.endsWith(" PRIVATE KEY-----")) {
	                    inKey = false;
	                    break;
	                }
	                builder.append(line);
	            }
	        }
	        //
	        byte[] encoded = DatatypeConverter.parseBase64Binary(builder.toString());
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
	        KeyFactory kf = KeyFactory.getInstance("RSA");
	        key = kf.generatePrivate(keySpec);
	    } finally {
	    	if (br != null) {
	    		try { br.close(); } catch (Exception ign) {}
	    	}
	    	if (is != null) {
	    		try { is.close(); } catch (Exception ign) {}
	    	}
	    }
	    return key;
	}

	   
	public static String generateToken(String subject, String[] scopes, String[] audiences, String privateKeyLocation) throws BadJWTException {
		// TODO Auto-generated method stub
		String accessToken = null;
		
		try {
						
			List<String> scopeList = new ArrayList<String>(Arrays.asList(scopes));
			List<String> audienceList = new ArrayList<String>(Arrays.asList(audiences));
			
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		     .subject(subject)
		     .claim("client_id", subject)
		     .claim("scope", scopeList)
		     .audience(audienceList)
		     .issuer(issuer)
		     .expirationTime(new Date(new Date().getTime() + expirationTimeInterval))
		     .issueTime(new Date(new Date().getTime()))
		     .jwtID(java.util.UUID.randomUUID().toString())
		     .build();
		     
			PrivateKey privateKey = getPrivateKey(privateKeyLocation);
	
			if(privateKey instanceof RSAPrivateKey)
			{
				SignedJWT signedJWT = new SignedJWT(
					    new JWSHeader(JWSAlgorithm.RS256), 
					    claimsSet);
				// Create RSA-signer with the private key
				JWSSigner signer = new RSASSASigner(privateKey);
	
				// Compute the RSA signature
				signedJWT.sign(signer);
				accessToken = signedJWT.serialize();
			} else {
				throw new BadJWTException("Public / Private key cryptography other than RSA not supported");
			}

		} catch (Exception e) {
			throw new BadJWTException(e.getMessage());
		}
		
		return accessToken;
	}
	
	private static PublicKey getPublicKey(String publicKeyLocation) throws FileNotFoundException, CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
	    File f = new File(publicKeyLocation);
	    FileInputStream fis = new FileInputStream(f);
	    DataInputStream dis = new DataInputStream(fis);
	    byte[] keyBytes = new byte[(int)f.length()];
	    dis.readFully(keyBytes);
	    dis.close();

	    X509EncodedKeySpec spec =  new X509EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    return kf.generatePublic(spec);
	}
	
	public static boolean validateToken(String accessToken, String publicKeyLocation) throws BadJWTException {
		
		boolean isValid = false;
		
		try {

			SignedJWT signedJWT = SignedJWT.parse(accessToken);
			PublicKey pubKey = getPublicKey(publicKeyLocation);
			JWSVerifier verifier = null;

			if (pubKey instanceof RSAPublicKey) {
				verifier = new RSASSAVerifier((RSAPublicKey)pubKey);
				isValid = signedJWT.verify(verifier);
			} else {
				throw new Exception("Public / Private key cryptography other than RSA not supported");
			}

		} catch (Exception e) {
			throw new BadJWTException("Invalid JWT Token - " + e.getMessage());
		}
		return isValid;
	}
	
	public static boolean verifyAuthorizationInToken(String accessToken, String scopeToBeValidated, String publicKeyLocation) throws BadJWTException{
		
		boolean isAuthorized = false;
		
		try {
			boolean isValid = validateToken(accessToken, publicKeyLocation);
			if(isValid) {
				SignedJWT signedJWT = SignedJWT.parse(accessToken);
				@SuppressWarnings("unchecked")
				List<String> tokenScopes = (List<String>)signedJWT.getJWTClaimsSet().getClaim("scope");
				if (tokenScopes.contains(scopeToBeValidated)){
					isAuthorized = true; 
				}
			}
		}
		catch (Exception e) {
			throw new BadJWTException("Invalid JWT Token - " + e.getMessage());
		}
		return isAuthorized;
	}
	

}
