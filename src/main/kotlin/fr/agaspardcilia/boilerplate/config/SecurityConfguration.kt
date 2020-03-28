package fr.agaspardcilia.boilerplate.config

import fr.agaspardcilia.boilerplate.users.Authority
import fr.agaspardcilia.boilerplate.users.authentication.JWTConfigurer
import fr.agaspardcilia.boilerplate.users.authentication.TokenProvider
import org.springframework.beans.factory.BeanInitializationException
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter
import javax.annotation.PostConstruct

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
open class SecurityConfiguration(
    private val tokenProvider: TokenProvider,
    private val corsFilter: CorsFilter,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {

    @PostConstruct
    open fun init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
        } catch (e: java.lang.Exception) {
            throw BeanInitializationException("Security configuration failed", e)
        }
    }

    @Bean
    open fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity?) {
        web!!.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**")
    }

    @Throws(Exception::class)
    public override fun configure(http: HttpSecurity) {
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
            .headers()
            .frameOptions()
            .disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/users/register").permitAll()
            .antMatchers("/api/users/password").permitAll()
            .antMatchers("/api/users/activate/*").permitAll()
            .antMatchers("/api/users/current-user").authenticated()
            .antMatchers("/api/users/**").hasAnyAuthority(Authority.ADMIN.name)
            .antMatchers("/api/**").authenticated()
            .antMatchers("**").permitAll()
            .and()
            .apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(securityConfigurerAdapter())
    }

    private fun securityConfigurerAdapter() = JWTConfigurer(tokenProvider)

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

}

