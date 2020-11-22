package luabui.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .authorities("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
                .antMatchers(HttpMethod.POST, "/restaurants/**").access("hasAnyAuthority({'ADMIN', 'RESTAURANT'})")
                .antMatchers(HttpMethod.PUT, "/restaurants/**").access("hasAnyAuthority({'ADMIN', 'RESTAURANT'})")
                .antMatchers(HttpMethod.DELETE, "/restaurants/**").access("hasAnyAuthority({'ADMIN', 'RESTAURANT'})")
                .antMatchers(HttpMethod.GET, "restaurants/{restaurantId}/fooditems").access("hasAnyAuthority({'RESTAURANT'})")
                .antMatchers(HttpMethod.GET, "restaurants/{restaurantId}/orders").access("hasAnyAuthority({'RESTAURANT'})")
                .antMatchers(HttpMethod.GET, "restaurants/{restaurantId}/orders/{orderId}").access("hasAnyAuthority({'RESTAURANT'})")
                .antMatchers(HttpMethod.GET, "/customers/**").access("hasAnyAuthority({'ADMIN', 'CUSTOMER'})")
                .antMatchers(HttpMethod.POST, "/customers/**").access("hasAnyAuthority({'ADMIN'})")
                .antMatchers(HttpMethod.PUT, "/customers/**").access("hasAnyAuthority({'ADMIN', 'CUSTOMER'})")
                .antMatchers(HttpMethod.DELETE, "/customers/**").access("hasAnyAuthority({'ADMIN'})")
                .antMatchers(HttpMethod.GET, "/deliveryguys/**").access("hasAnyAuthority({'ADMIN', 'DELIVERY'})")
                .antMatchers(HttpMethod.POST, "/deliveryguys/**").access("hasAnyAuthority({'ADMIN'})")
                .antMatchers(HttpMethod.PUT, "/deliveryguys/**").access("hasAnyAuthority({'ADMIN', 'DELIVERY'})")
                .antMatchers(HttpMethod.DELETE, "/deliveryguys/**").access("hasAnyAuthority({'ADMIN', 'DELIVERY'})")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint);


        http.addFilterAfter(new CustomFilter(),
                BasicAuthenticationFilter.class);
//        http.csrf().disable();
    }
}
