package com.oneshield.dms.security;

//@EnableWebSecurity
public class SpringSecurityConfig //extends WebSecurityConfigurerAdapter 
{
    public static String REALM = "OS_DMS_REALM";

    /*
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws
     * Exception {
     * auth.inMemoryAuthentication().withUser("user").password("user").roles("USER")
     * ; }
     * 
     * @Override protected void configure(HttpSecurity http) throws Exception {
     * http.csrf().disable().authorizeRequests().antMatchers("/api/**").hasRole(
     * "USER").and().httpBasic()
     * .realmName(REALM).authenticationEntryPoint(this.getBasicAuthEntryPoint()).and
     * ().sessionManagement()
     * .sessionCreationPolicy(SessionCreationPolicy.STATELESS); }
     * 
     * @Bean public AuthenticationEntryPoint getBasicAuthEntryPoint() { return new
     * CustomBasicAuthenticationEntryPoint(); }
     */
}
