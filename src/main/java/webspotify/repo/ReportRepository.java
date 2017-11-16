package webspotify.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.administration.Report;

/**
 *
 * @author Cardinals
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
