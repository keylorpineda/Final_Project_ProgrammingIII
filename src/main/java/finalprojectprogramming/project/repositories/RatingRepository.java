package finalprojectprogramming.project.repositories;

import finalprojectprogramming.project.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}