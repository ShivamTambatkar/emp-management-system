package com.humancloud.Employeemanagementsystem.Config;

import com.humancloud.Employeemanagementsystem.Filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
//
        return new EmployeeDetailsService();
    }


    //For Authorization of user
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         String[] PUBLIC_URL={
                 "/emp/create",
                 "/emp/authenticate",
                 "/swagger-ui/**",
                 "/swagger-resources/**",
                 "/v3/api-docs/**"};
         String[]ADMIN_URL={

                 "/emp/allemp",
                 "/emp/search-employee/{keyword}",
                 "/emp/delete/{empId}",
                 "/lc/create-leavecategory",
                 "/lc/update-leavecategory/{lcId}",
                 "/lc/getall-leavecategories",
                 "/lc/deletelc/{lcId}",
                 "/lc/getleavecategory/{lcId}",
                 "/leaves/getall-leaves"};

          String [] MANAGER_URL={"/leaves/reject-leave/leave/{leaveId}/manager/{reportingManagerId}",
                  "/leaves/approve-leave/leave/{leaveId}/manager/{reportingManagerId}",
                  "/leaves/pending-leaves-requests/{reportingMangerId}",
                  "/leaves/leaves/{empId}"};
          String [] EMPLOYEE_URL={
                  "/emp/{empId}",
                  "/leaves/apply-leave/{empId}",
                  "/leaves/update-leave/{leaveId}"
                  ,"/leaves/pending-leaves/{empId}",
                  "/lc/getall-leavecategories",
                  "/lc/getleavecategory/{lcId}"};

        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_URL).permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(ADMIN_URL).hasAuthority("ADMIN")
                .requestMatchers(MANAGER_URL).hasAuthority("MANAGER")
                .requestMatchers(EMPLOYEE_URL).hasAuthority("EMPLOYEE")
                .requestMatchers("/emp/update/{empId}/rm/{rmId}").hasAuthority("ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
 // For Encrypting Password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
