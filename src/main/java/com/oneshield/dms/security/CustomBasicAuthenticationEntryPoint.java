package com.oneshield.dms.security;

public class CustomBasicAuthenticationEntryPoint //extends BasicAuthenticationEntryPoint 
{

    /*@Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException authException) throws IOException, ServletException {
	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");

	PrintWriter writer = response.getWriter();
	writer.println("HTTP Status 401 : " + authException.getMessage());

    }

    @Override
    public void afterPropertiesSet() throws Exception {
	this.setRealmName(SpringSecurityConfig.REALM);
	super.afterPropertiesSet();
    }*/
}
