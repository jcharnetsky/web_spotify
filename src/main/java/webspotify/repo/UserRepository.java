package webspotify.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.users.User;

/**
 * @author Cardinals
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  List<User> findByEmail(String email);

  List<User> findByNameContaining(String name);
  
  Integer countByIsPremiumIsTrue();
}
