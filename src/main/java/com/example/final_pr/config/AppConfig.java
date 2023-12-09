package com.example.final_pr.config;

import com.example.final_pr.dto.*;
import com.example.final_pr.enums.FloorType;
import com.example.final_pr.enums.UserRole;
import com.example.final_pr.service.GeneralService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;




@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration {

    public static final String ADMIN_LOGIN = "admin";

    @Bean
    public CommandLineRunner demo(final GeneralService generalService,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                generalService.addUser(ADMIN_LOGIN,
                        encoder.encode("password"),
                        UserRole.ADMIN, "");
                generalService.addUser("user",
                        encoder.encode("password"),
                        UserRole.USER, "");
            }
        };
    }

}
