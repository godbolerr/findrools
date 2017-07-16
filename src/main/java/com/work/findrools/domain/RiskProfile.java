package com.work.findrools.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RiskProfile.
 */
@Entity
@Table(name = "risk_profile")
public class RiskProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "risk_data")
    private String riskData;

    @Column(name = "profile_date")
    private ZonedDateTime profileDate;

    @Column(name = "fin_score")
    private Integer finScore;

    @Column(name = "wealth_projected", precision=10, scale=2)
    private BigDecimal wealthProjected;

    @Column(name = "wealth_actual", precision=10, scale=2)
    private BigDecimal wealthActual;

    @Column(name = "social_score")
    private Double socialScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public RiskProfile personId(Long personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getRiskData() {
        return riskData;
    }

    public RiskProfile riskData(String riskData) {
        this.riskData = riskData;
        return this;
    }

    public void setRiskData(String riskData) {
        this.riskData = riskData;
    }

    public ZonedDateTime getProfileDate() {
        return profileDate;
    }

    public RiskProfile profileDate(ZonedDateTime profileDate) {
        this.profileDate = profileDate;
        return this;
    }

    public void setProfileDate(ZonedDateTime profileDate) {
        this.profileDate = profileDate;
    }

    public Integer getFinScore() {
        return finScore;
    }

    public RiskProfile finScore(Integer finScore) {
        this.finScore = finScore;
        return this;
    }

    public void setFinScore(Integer finScore) {
        this.finScore = finScore;
    }

    public BigDecimal getWealthProjected() {
        return wealthProjected;
    }

    public RiskProfile wealthProjected(BigDecimal wealthProjected) {
        this.wealthProjected = wealthProjected;
        return this;
    }

    public void setWealthProjected(BigDecimal wealthProjected) {
        this.wealthProjected = wealthProjected;
    }

    public BigDecimal getWealthActual() {
        return wealthActual;
    }

    public RiskProfile wealthActual(BigDecimal wealthActual) {
        this.wealthActual = wealthActual;
        return this;
    }

    public void setWealthActual(BigDecimal wealthActual) {
        this.wealthActual = wealthActual;
    }

    public Double getSocialScore() {
        return socialScore;
    }

    public RiskProfile socialScore(Double socialScore) {
        this.socialScore = socialScore;
        return this;
    }

    public void setSocialScore(Double socialScore) {
        this.socialScore = socialScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RiskProfile riskProfile = (RiskProfile) o;
        if (riskProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riskProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RiskProfile{" +
            "id=" + getId() +
            ", personId='" + getPersonId() + "'" +
            ", riskData='" + getRiskData() + "'" +
            ", profileDate='" + getProfileDate() + "'" +
            ", finScore='" + getFinScore() + "'" +
            ", wealthProjected='" + getWealthProjected() + "'" +
            ", wealthActual='" + getWealthActual() + "'" +
            ", socialScore='" + getSocialScore() + "'" +
            "}";
    }
}
