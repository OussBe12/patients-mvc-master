package ma.emsi.patientsmvc;

import ma.emsi.patientsmvc.entities.Patient;
import ma.emsi.patientsmvc.repositories.PatientRepository;
import ma.emsi.patientsmvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);}

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
    //@Bean
CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args ->{
            patientRepository.save(new Patient(null,"Oussama1",new Date(),false,122));
            patientRepository.save(new Patient(null,"Oussama3",new Date(),true,321));
            patientRepository.save(new Patient(null,"Said",new Date(),true,165));
            patientRepository.save(new Patient(null,"Oussama2",new Date(),false,132));
            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });

        };
}
//@Bean
    CommandLineRunner saveUsers(SecurityService securityService) {
        return args -> {
            securityService.saveNewUser("mohamed", "1234", "1234");
            securityService.saveNewUser("simo", "1234", "1234");
            securityService.saveNewUser("hassan", "1234", "1234");

            securityService.saveNewRole("USER", "");
            securityService.saveNewRole("ADMIN", "");

            securityService.addRoleToUser("mohamed", "USER");
            securityService.addRoleToUser("mohamed", "ADMIN");
            securityService.addRoleToUser("simo", "USER");
            securityService.addRoleToUser("hassan", "USER");
 /*   auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);*/


        };
    }

        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin();
            http.authorizeRequests().antMatchers("/").permitAll();
            http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
            http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
            http.authorizeRequests().antMatchers("/webjars/**").permitAll();
            http.authorizeRequests().anyRequest().authenticated();
            http.exceptionHandling().accessDeniedPage("/403");
        }

}

