package org.pillow.common.security;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求中的摘要验证信息，包含用户id,随机数和签名
 */
public class AuthorizationParam {
    private static final String AUTHORIZATION_HEADER_NAME = "authorization";
    private String token;
    private String nonce;
    private String sign;

    public AuthorizationParam(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authorization == null) {
            authorization = request.getParameter(AUTHORIZATION_HEADER_NAME);
        }  
        //简单情况只需要考虑token
        token = authorization;
        
        //复杂情况需要引入随机数与签名
        /*
        String[] authorizations = authorization.split(";");
        this.token = new Long(authorizations[0]);
        this.nonce = authorizations[1];
        this.sign = authorizations[2];
        */
    }

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNonce() {
        return nonce;
    }

    public void setNouce(String nonce) {
        this.nonce = nonce;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}