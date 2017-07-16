package com.work.findrools.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProfSetting entity.
 */
public class ProfSettingDTO implements Serializable {

    private Long id;

    private String profKey;

    private String profValue;

    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfKey() {
        return profKey;
    }

    public void setProfKey(String profKey) {
        this.profKey = profKey;
    }

    public String getProfValue() {
        return profValue;
    }

    public void setProfValue(String profValue) {
        this.profValue = profValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfSettingDTO profSettingDTO = (ProfSettingDTO) o;
        if(profSettingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profSettingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfSettingDTO{" +
            "id=" + getId() +
            ", profKey='" + getProfKey() + "'" +
            ", profValue='" + getProfValue() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
