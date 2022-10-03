package com.example.bdc.network.trust_connection;

import com.example.bdc.network.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * This interface describes JpaRepository with TrustConnection as an entity
 * and TrustConnectionPK as a primary key.
 * @see TrustConnection
 * @see TrustConnection.TrustConnectionPK
 */
public interface TrustConnectionRepository extends JpaRepository<TrustConnection, TrustConnection.TrustConnectionPK> {

    /**
     * Creates or updates a connection in DB.
     * If there's no such connection {@code from -> to}
     * creates a new connection or updates {@code level}
     * otherwise.
     *
     * @param from User AKA benefactor
     * @param to User AKA beneficiary
     * @param level Integer trust level
     * @see TrustConnection
     */
    @Transactional
    @Modifying
    default void save(User from, User to, Integer level) {
        var connection = findByFromAndTo(from, to);
        TrustConnection res = new TrustConnection(from, to, level);
        if (connection.isPresent()) {
            res = connection.get();
            res.setLevel(level);
        }
        save(res);
    }

    /**
     * Creates or updates a connection in DB.
     * If there's no such connection {@code from -> to}
     * creates a new connection or updates {@code level}
     * otherwise.
     *
     * @param from User AKA benefactor
     * @param to User AKA beneficiary
     * @return Optional<TrustConnection>
     * @see TrustConnection
     */
    Optional<TrustConnection> findByFromAndTo(User from, User to);
}
