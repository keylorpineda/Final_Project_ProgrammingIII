package finalprojectprogramming.project.repositories;

import finalprojectprogramming.project.models.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
}