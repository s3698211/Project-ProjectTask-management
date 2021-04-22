package myapp.springstarter.security;

public class SecurityConstants {
	
	public static final String SIGN_UP_URLS = "/api/users/**";
	public static final String SECRET = "SecretKeyToGenerateJWT";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final long EXPIRATION_TIME = 300_000; // miliseconds
			
}
