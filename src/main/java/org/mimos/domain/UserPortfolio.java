package org.mimos.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserPortfolio.
 */
@Entity
@Table(name = "user_portfolio")
@Document(indexName = "userportfolio")
public class UserPortfolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private String userid;

    @ManyToOne
    private StockInfo sector;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public UserPortfolio userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public StockInfo getSector() {
        return sector;
    }

    public UserPortfolio sector(StockInfo stockInfo) {
        this.sector = stockInfo;
        return this;
    }

    public void setSector(StockInfo stockInfo) {
        this.sector = stockInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPortfolio userPortfolio = (UserPortfolio) o;
        if (userPortfolio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPortfolio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPortfolio{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            "}";
    }
}
