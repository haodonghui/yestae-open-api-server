/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.config.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author daniel
 */
public class OAuth2Token2 implements AuthenticationToken {
    /** 
	 */
	private static final long serialVersionUID = -2837891795363304385L;
	private String token;

    public OAuth2Token2(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
