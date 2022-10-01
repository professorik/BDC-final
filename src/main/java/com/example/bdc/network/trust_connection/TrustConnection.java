package com.example.bdc.network.trust_connection;

import com.example.bdc.network.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "trust_levels")
public class TrustConnection {

    @EmbeddedId
    private TrustConnectionPK id;

    @ManyToOne
    @JoinColumn(name = "BENEFACTOR_ID", insertable = false, updatable = false)
    private User from;

    @ManyToOne
    @JoinColumn(name = "BENEFICIARY_ID", insertable = false, updatable = false)
    private User to;

    @Column
    private Integer level;

    public TrustConnection(User from, User to, Integer level) {
        this.id = new TrustConnectionPK(from.getId(), to.getId());
        this.from = from;
        this.to = to;
        this.level = level;
    }
}
