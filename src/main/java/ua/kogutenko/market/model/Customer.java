package ua.kogutenko.market.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import ua.kogutenko.market.model.marked.OnCreated;
import ua.kogutenko.market.model.marked.OnUpdated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    private Long created;
    @JsonIgnore
    private Long updated;
    @JsonIgnore
    private boolean is_active;


    @NotBlank(groups = {OnCreated.class}, message = "Name cannot be empty")
    @NotNull(groups = {OnCreated.class}, message = "Name cannot be null")
    @Length(groups = {OnCreated.class, OnUpdated.class}, min = 2, max = 50)
    private String full_name;

    @NotBlank(groups = {OnCreated.class}, message = "Email cannot be empty")
    @NotNull(groups = {OnCreated.class}, message = "Email cannot be null")
    @Email(groups = {OnCreated.class}, regexp = "[a-z0-9._]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    @Length(groups = {OnCreated.class}, min = 2, max = 100)
    @Column(name = "email",unique = true)
    private String email;

    @Pattern(groups = {OnCreated.class, OnUpdated.class}, regexp = "(\\+)[0-9]{6,14}",
            message = "Incorrect phone number")
    private String phone;

    public Customer() {
    }

    public Customer(String full_name, String email, String phone) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
    }

    public Customer(String full_name, String email) {
        this.full_name = full_name;
        this.email = email;
    }

    public Customer(Customer customerClone) {
        setCreated(customerClone.getCreated());
        setEmail(customerClone.getEmail());
        setFull_name(customerClone.getFull_name());
        setId(customerClone.getId());
        setPhone(customerClone.getPhone());
        setUpdated(customerClone.getUpdated());
        setIs_active(customerClone.isIs_active());
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return (is_active == customer.is_active) &&
                (id != null ? id.equals(customer.id) : customer.id == null) &&
                (created != null ? created.equals(customer.created) : customer.created == null) &&
                (updated != null ? updated.equals(customer.updated) : customer.updated == null) &&
                (full_name != null ? full_name.equals(customer.full_name) : customer.full_name == null) &&
                (email != null ? email.equals(customer.email) : customer.email == null) &&
                (phone != null ? !phone.equals(customer.phone) : customer.phone != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (is_active ? 1 : 0);
        result = 31 * result + (full_name != null ? full_name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
