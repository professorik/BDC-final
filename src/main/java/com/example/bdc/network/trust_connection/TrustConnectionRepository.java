package com.example.bdc.network.trust_connection;

import com.example.bdc.network.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Optional;


public interface TrustConnectionRepository extends JpaRepository<TrustConnection, TrustConnection.TrustConnectionPK> {

    @Transactional
    @Modifying
    default void save(User from, User to, Integer level) {
        var connection = findByFromAndTo(from, to);
        TrustConnection kar = new TrustConnection(from, to, level);
        if (connection.isPresent()) {
            kar = connection.get();
            kar.setLevel(level);
        }
        save(kar);
    }

    Optional<TrustConnection> findByFromAndTo(User from, User to);
}
