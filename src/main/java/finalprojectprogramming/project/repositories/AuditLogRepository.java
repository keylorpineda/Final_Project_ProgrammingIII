package finalprojectprogramming.project.repositories;

import finalprojectprogramming.project.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}