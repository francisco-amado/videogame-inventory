package com.inventory.app.utils;

import com.inventory.app.domain.factories.OwnerFactoryInterface;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.owner.OwnerRole;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.repositories.OwnerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseLoader {

    private final OwnerRepository ownerRepository;
    private final OwnerFactoryInterface ownerFactoryInterface;

    public DatabaseLoader(OwnerRepository ownerRepository, OwnerFactoryInterface ownerFactoryInterface) {
        this.ownerRepository = ownerRepository;
        this.ownerFactoryInterface = ownerFactoryInterface;
    }

    @Bean
    public CommandLineRunner databaseInitializer() {
        return inserts -> {
            Owner admin = ownerFactoryInterface.createOwner(
                    Name.createName("Admin"), Email.createEmail("admin@game-inventory.com"), "admin");
            admin.setOwnerRole(OwnerRole.ADMIN);

            Owner user = ownerFactoryInterface.createOwner(
                    Name.createName("User"), Email.createEmail("user@game-inventory.com"), "user");
            user.setOwnerRole(OwnerRole.USER);

            ownerRepository.saveAll(List.of(admin, user));

            System.out.println("Database loaded successfully");
        };
    }
}
