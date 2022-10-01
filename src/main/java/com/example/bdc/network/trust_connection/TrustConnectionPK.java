package com.example.bdc.network.trust_connection;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TrustConnectionPK implements Serializable {

    @Column(name = "BENEFACTOR_ID")
    private Integer benefactor_id;

    @Column(name = "BENEFICIARY_ID")
    private Integer beneficiary_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrustConnectionPK that = (TrustConnectionPK) o;
        return Objects.equals(benefactor_id, that.benefactor_id) && Objects.equals(beneficiary_id, that.beneficiary_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benefactor_id, beneficiary_id);
    }
}
