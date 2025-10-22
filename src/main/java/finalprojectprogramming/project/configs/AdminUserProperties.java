package finalprojectprogramming.project.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.admin")
public class AdminUserProperties {

    /**
     * Enables or disables the automatic administrator provisioning.
     */
    private boolean enabled = true;

    /**
     * Azure AD identifier (or any unique identifier) for the administrator account.
     */
    private String azureId;

    /**
     * Optional email address used both for login and contact.
     */
    private String email;

    /**
     * Display name for the administrator.
     */
    private String name = "Administrator";

    /**
     * Plain password that will be hashed before persisting. Must comply with the configured password policy.
     */
    private String password;

    /**
     * When true the password stored in the database will be refreshed on every startup.
     */
    private boolean forcePasswordReset = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAzureId() {
        return azureId;
    }

    public void setAzureId(String azureId) {
        this.azureId = azureId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isForcePasswordReset() {
        return forcePasswordReset;
    }

    public void setForcePasswordReset(boolean forcePasswordReset) {
        this.forcePasswordReset = forcePasswordReset;
    }
}
