package springbootstarter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//@EnableWebSecurity tells application this is web security configuration
@EnableWebSecurity
/* EnableGlobalMethodSecurity - it enables the method level security like @RolesAllowed, @Preauthorize
* jsr250 - @RolesAllowed
* prePost - @PreAuthorize & @PostAuthorize
* secured - @Secured*/
//@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       //set configuration on auth object
       auth.inMemoryAuthentication()
               .withUser("user").password("user1234").roles(USER)
               .and()
               .withUser("abc").password("abc123").roles(ADMIN);
   }

   /* This part is required for jdbc authentication when we add jdbc dependency
   @Autowired
    DataSource dataSource;
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
               .withUser(User.withUsername("user").password("user123").roles("USER"))
               .withUser(User.withUsername("admin").password("admin123").roles("ADMIN"));

   }
    */

   @Bean
    public PasswordEncoder getPasswordEncoder() {
       return NoOpPasswordEncoder.getInstance();
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().authenticated()       //anyRequest can not be configured with antMatchers
                .antMatchers("/admin").hasRole(ADMIN)
                .antMatchers("/user").hasAnyRole(USER,ADMIN)
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }
}