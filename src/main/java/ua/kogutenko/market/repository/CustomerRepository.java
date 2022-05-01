package ua.kogutenko.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.kogutenko.market.dto.CustomerDTO;

/**
 * The customer repository.
 * <p>
 *
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerDTO, Long> {
    /**
     * Exists by email boolean.
     *
     * @param val the val
     * @return the boolean
     */
    boolean existsByEmail(String val);
}