package finalprojectprogramming.project.repositories;

import finalprojectprogramming.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}