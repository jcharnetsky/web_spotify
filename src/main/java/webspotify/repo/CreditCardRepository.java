package webspotify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.payment.CreditCard;

/**
 * @author Cardinals
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
}
