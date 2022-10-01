package com.example.bdc.network.trust_connection;

import com.example.bdc.network.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface TrustConnectionRepository extends JpaRepository<TrustConnection, TrustConnectionPK> {
    @Transactional
    @Modifying
    default TrustConnection save(User from, User to, Integer level) {
        var connection = findByFromAndTo(from, to);
        TrustConnection kar = new TrustConnection(from, to, level);
        if (connection.isPresent()) {
            kar = connection.get();
            kar.setLevel(level);
        }
        return save(kar);
    }

    Optional<TrustConnection> findByFromAndTo(User from, User to);
}
