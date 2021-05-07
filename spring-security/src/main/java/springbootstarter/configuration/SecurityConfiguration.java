package springbootstarter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableWebSecurity tells application this is web security configuration
@EnableWebSecurity
/* EnableGlobalMethodSecurity - it enables the method level security like @RolesAllowed, @Preauthorize
* jsr250 - @RolesAllowed
* prePost - @PreAuthorize & @PostAuthorize
* secured - @Secured*/
//@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       //set configuration on auth object
       auth.inMemoryAuthentication()
               .withUser("user").password("user1234").roles("USER")
               .and()
               .withUser("abc").password("abc123").roles("ADMIN");
   }

   @Bean
    public PasswordEncoder getPasswordEncoder() {
       return NoOpPasswordEncoder.getInstance();
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().authenticated()       //anyRequest can not be configured with antMatchers
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }
}