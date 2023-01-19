package vn.ptit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.ptit.security.JwtAuthenticationEntryPoint;
import vn.ptit.security.JwtAuthenticationFilter;
import vn.ptit.service.auth.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**", "/").permitAll()

                .antMatchers("/swagger-ui/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**"
                ).permitAll()

                // User api
                .antMatchers("/user/insert").hasAnyRole("ADMIN")
                .antMatchers("/user/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/user/update").hasAnyRole("ADMIN")
                .antMatchers("/user/list").hasAnyRole("ADMIN")
                .antMatchers("/user/detail/**").hasAnyRole("ADMIN")
                .antMatchers("/user/get*").hasAnyRole("USER")
                .antMatchers("/user/edit-profile").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/search").hasAnyRole("ADMIN")
                // ---

                // Laptop api
                .antMatchers("/laptop/insert").hasAnyRole("ADMIN")
                .antMatchers("/laptop/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/laptop/update").hasAnyRole("ADMIN")
                .antMatchers("/laptop/detail/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/laptop/list").hasAnyRole("ADMIN")
                .antMatchers("/laptop/same").hasAnyRole("ADMIN", "USER")
                .antMatchers("/laptop/filter").hasAnyRole("ADMIN", "USER")
                // ---

                // Manufacturer api
                .antMatchers("/manufacturer/insert").hasAnyRole("ADMIN")
                .antMatchers("/manufacturer/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/manufacturer/update").hasAnyRole("ADMIN")
                .antMatchers("/manufacturer/detail/**").hasAnyRole("ADMIN")
                .antMatchers("/manufacturer/list").hasAnyRole("ADMIN", "USER")
                // ---

                // Shipment api
                .antMatchers("/shipment/insert").hasAnyRole("ADMIN")
                .antMatchers("/shipment/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/shipment/update").hasAnyRole("ADMIN")
                .antMatchers("/shipment/detail/**").hasAnyRole("ADMIN")
                .antMatchers("/shipment/list").hasAnyRole("ADMIN", "USER")
                // ---

                // Order api
                .antMatchers("/order/insert").hasAnyRole("ADMIN", "USER")
                .antMatchers("/order/get*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/order/list").hasAnyRole("ADMIN")
                .antMatchers("/order/detail/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/order/update-status").hasAnyRole("ADMIN", "USER")
                // ---

                // Comment api
                .antMatchers("/comment/**").hasAnyRole("ADMIN", "USER")
                // ---

                // Cart api
                .antMatchers("/cart/**").hasAnyRole("ADMIN", "USER")
                // ---

                // Statistic api
                .antMatchers("/statistic/shipment-with-total-shipped").hasAnyRole("ADMIN")
                .antMatchers("/statistic/total-laptop").hasAnyRole("ADMIN")
                .antMatchers("/statistic/total-money-received").hasAnyRole("ADMIN")
                .antMatchers("/statistic/total-user-purchased").hasAnyRole("ADMIN")
                .antMatchers("/statistic/user-with-total-money").hasAnyRole("ADMIN")
                .antMatchers("/statistic/laptop-with-total-sold").hasAnyRole("ADMIN", "USER")
                // ---

                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
