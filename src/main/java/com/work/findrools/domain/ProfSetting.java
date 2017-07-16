package com.work.findrools.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProfSetting.
 */
@Entity
@Table(name = "prof_setting")
public class ProfSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prof_key")
    private String profKey;

    @Column(name = "prof_value")
    private String profValue;

    @Column(name = "category")
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

    public ProfSetting profKey(String profKey) {
        this.profKey = profKey;
        return this;
    }

    public void setProfKey(String profKey) {
        this.profKey = profKey;
    }

    public String getProfValue() {
        return profValue;
    }

    public ProfSetting profValue(String profValue) {
        this.profValue = profValue;
        return this;
    }

    public void setProfValue(String profValue) {
        this.profValue = profValue;
    }

    public String getCategory() {
        return category;
    }

    public ProfSetting category(String category) {
        this.category = category;
        return this;
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
        ProfSetting profSetting = (ProfSetting) o;
        if (profSetting.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profSetting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfSetting{" +
            "id=" + getId() +
            ", profKey='" + getProfKey() + "'" +
            ", profValue='" + getProfValue() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
