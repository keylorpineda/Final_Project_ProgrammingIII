package finalprojectprogramming.project.configs;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import finalprojectprogramming.project.models.User;
import finalprojectprogramming.project.models.enums.UserRole;
import finalprojectprogramming.project.repositories.UserRepository;
import finalprojectprogramming.project.security.hash.PasswordHashService;

@Configuration
public class AdminUserInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserInitializer.class);

    @Bean
    public ApplicationRunner adminProvisioningRunner(
            AdminUserProperties properties,
            UserRepository userRepository,
            PasswordHashService passwordHashService
    ) {
        return args -> {
            if (!properties.isEnabled()) {
                LOGGER.info("Admin auto-provisioning is disabled");
                return;
            }

            String azureId = properties.getAzureId();
            String password = properties.getPassword();

            if (!StringUtils.hasText(azureId) || !StringUtils.hasText(password)) {
                LOGGER.warn("Admin provisioning skipped because mandatory properties (azureId/password) are missing");
                return;
            }

            Optional<User> existingUser = userRepository.findByAzureId(azureId);
            if (existingUser.isEmpty() && StringUtils.hasText(properties.getEmail())) {
                existingUser = userRepository.findByEmail(properties.getEmail());
            }

            LocalDateTime now = LocalDateTime.now();

            if (existingUser.isPresent()) {
                User user = existingUser.get();
                boolean updated = false;

                if (user.getRole() != UserRole.ADMIN) {
                    user.setRole(UserRole.ADMIN);
                    updated = true;
                }

                if (!Boolean.TRUE.equals(user.getActive())) {
                    user.setActive(true);
                    user.setDeletedAt(null);
                    updated = true;
                }

                if (StringUtils.hasText(properties.getName()) && !Objects.equals(user.getName(), properties.getName())) {
                    user.setName(properties.getName());
                    updated = true;
                }

                if (StringUtils.hasText(properties.getEmail()) && !Objects.equals(user.getEmail(), properties.getEmail())) {
                    user.setEmail(properties.getEmail());
                    updated = true;
                }

                boolean shouldResetPassword = properties.isForcePasswordReset()
                        || !passwordHashService.matches(password, user.getPasswordHash());
                if (shouldResetPassword) {
                    user.setPasswordHash(passwordHashService.encode(password));
                    updated = true;
                }

                if (updated) {
                    user.setUpdatedAt(now);
                    userRepository.save(user);
                    LOGGER.info("Updated existing administrator account with azureId={} (id={})", azureId, user.getId());
                } else {
                    LOGGER.info("Administrator account with azureId={} already up-to-date", azureId);
                }
                return;
            }

            User admin = User.builder()
                    .azureId(azureId)
                    .email(StringUtils.hasText(properties.getEmail()) ? properties.getEmail() : null)
                    .name(StringUtils.hasText(properties.getName()) ? properties.getName() : "Administrator")
                    .role(UserRole.ADMIN)
                    .active(true)
                    .passwordHash(passwordHashService.encode(password))
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            User saved = userRepository.save(admin);
            LOGGER.info("Administrator account provisioned with azureId={} (id={})", azureId, saved.getId());
        };
    }
}