package finalprojectprogramming.project.repositories;

import finalprojectprogramming.project.models.SpaceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceScheduleRepository extends JpaRepository<SpaceSchedule, Long> {
}