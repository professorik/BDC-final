package com.example.bdc.network.trust_connection;

import com.example.bdc.network.user.User;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trust_levels")
public class TrustConnection {

    @EmbeddedId
    private TrustConnectionPK id;

    @ManyToOne
    @JoinColumn(name = "benefactor_id", insertable = false, updatable = false)
    private User from;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id", insertable = false, updatable = false)
    private User to;

    @Column
    @Getter
    @Setter
    @Range(min=1, max=10)
    private Integer level;

    public TrustConnection(User from, User to, Integer level) {
        id = new TrustConnectionPK(from.getId(), to.getId());
        this.from = from;
        this.to = to;
        this.level = level;
    }

    public String getToName() {
        return to.getName();
    }

    public User getTargetUser() {
        return to;
    }

    /**
     * This class implements the primary key.
     * Consist of 2 primary keys: {@code benefactor_id, beneficiary_id}
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class TrustConnectionPK implements Serializable {

        @Column(name = "BENEFACTOR_ID")
        private Integer benefactor_id;

        @Column(name = "BENEFICIARY_ID")
        private Integer beneficiary_id;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof TrustConnectionPK that)) return false;
            return Objects.equals(benefactor_id, that.benefactor_id) &&
                    Objects.equals(beneficiary_id, that.beneficiary_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(benefactor_id, beneficiary_id);
        }
    }
}
