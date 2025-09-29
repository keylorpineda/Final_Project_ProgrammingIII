package finalprojectprogramming.project.repositories;

import finalprojectprogramming.project.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}