package com.work.findrools.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the RiskProfile entity.
 */
public class RiskProfileDTO implements Serializable {

    private Long id;

    private Long personId;

    private String riskData;

    private ZonedDateTime profileDate;

    private Integer finScore;

    private BigDecimal wealthProjected;

    private BigDecimal wealthActual;

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

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getRiskData() {
        return riskData;
    }

    public void setRiskData(String riskData) {
        this.riskData = riskData;
    }

    public ZonedDateTime getProfileDate() {
        return profileDate;
    }

    public void setProfileDate(ZonedDateTime profileDate) {
        this.profileDate = profileDate;
    }

    public Integer getFinScore() {
        return finScore;
    }

    public void setFinScore(Integer finScore) {
        this.finScore = finScore;
    }

    public BigDecimal getWealthProjected() {
        return wealthProjected;
    }

    public void setWealthProjected(BigDecimal wealthProjected) {
        this.wealthProjected = wealthProjected;
    }

    public BigDecimal getWealthActual() {
        return wealthActual;
    }

    public void setWealthActual(BigDecimal wealthActual) {
        this.wealthActual = wealthActual;
    }

    public Double getSocialScore() {
        return socialScore;
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

        RiskProfileDTO riskProfileDTO = (RiskProfileDTO) o;
        if(riskProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riskProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RiskProfileDTO{" +
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
