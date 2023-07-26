package com.diptopaul.blog.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtHelperService {

	
	//put these in a properties file
	private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";// use https://randomkeygen.com/ to generate a random 256 bit key
	private static final Long EXPIRATION_TIME = (long) 24 * 60 * 60 * 1000; // 24 hours = 24 * 60 minutes * 60 seconds * 1000 milliseconds = 86,400,000 milliseconds
	
	
	//convert the SECRET_KEY from String to Key type,
	private Key getSignInkey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	//-------------Generating/Creating token code-------------
	//generating a token with no extraClaims
	public String generateToken(UserDetails userDetails) {
		//create an empty claims map
		Map<String, Object> claims = new HashMap<>();
		//pass the userDtailsa and the empty claims map
		return generateToken(claims,userDetails);
	}
	
	//generate a token with claims and UserDetails
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		//use Jwts class's builder (method) for adding or setting claims, subject, issuedDate, expiraration etc
		Date now = new Date(System.currentTimeMillis());
		Date expirationDate = new Date(System.currentTimeMillis()+EXPIRATION_TIME);
		return Jwts
				.builder()
				.addClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.signWith(getSignInkey(), SignatureAlgorithm.HS256)
				.compact();
	}
	//-------------
	
	//-------------Validation check code-------------
	//check if token valid or not
	public boolean isTokenValid(String token, UserDetails userDetails) {
		//extract the userName from token at first
		final String username = extractUsername(token);
		
		//compare this uername with username from UserDetails and check if the token is not expired and return comparision boolean result
		return ((username.equals(userDetails.getUsername())) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		//compare the expired date time with todays date time, return the comparision boolean result
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		//extract the claim for expiration and return it
		return extractClaim(token, Claims::getExpiration);
	}
   //------------
	
	//-------------Extracting claim/claims code-------------
	// Extract information from JWT token using the provided claims resolver
	//<ParameterTypeT> ReturnTypeT methodName(T variableName)
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		//first get all the claims 
		final Claims claims = extractAllClaims(token);
		//now claimsResolver function which has taken a Claims object (representing the decoded JWT claims) will filter out that specific claim
		return claimsResolver.apply(claims);
	}
	
	// Extract all claims from JWT token
	private Claims extractAllClaims(String token)
	{
		return Jwts.parserBuilder().setSigningKey(getSignInkey()).build().parseClaimsJws(token).getBody();
	}

	// Extract username from JWT token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
}
