package ua.kogutenko.market.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class CustomerDto implements Serializable {
    private Long id;
    private Long created;
    private Long updated;
    private String full_name;
    private String email;
    private String phone;
    private boolean is_active;

    public CustomerDto() {}

    public CustomerDto(Long id, Long created, Long updated, String full_name, String email, String phone, boolean is_active) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.is_active = is_active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto entity = (CustomerDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.created, entity.created) &&
                Objects.equals(this.updated, entity.updated) &&
                Objects.equals(this.full_name, entity.full_name) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.is_active, entity.is_active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, full_name, email, phone, is_active);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "created = " + created + ", " +
                "updated = " + updated + ", " +
                "full_name = " + full_name + ", " +
                "email = " + email + ", " +
                "phone = " + phone + ", " +
                "is_active = " + is_active + ")";
    }
}
