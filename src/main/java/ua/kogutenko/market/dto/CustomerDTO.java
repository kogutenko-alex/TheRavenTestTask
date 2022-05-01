package ua.kogutenko.market.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ua.kogutenko.market.dto.marked.OnCreated;
import ua.kogutenko.market.dto.marked.OnUpdated;
import ua.kogutenko.market.validations.EmailChecker;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * The customer DTO.
 * <p></p>
 * Has 7 fields.
 * <p>1. id - generated value; responsible for the primary key.
 * <p>2. created - json ignored; responsible for the creation date of the customer.
 * <p>3. updated - json ignored; responsible for the last updating of the customer.
 * <p>4. is_active - json ignored; responsible for removed or not the customer.
 * <p>5. full_name - not null, not blank and has length[2,100]; responsible for the full name of the customer.
 * <p>6. email - not null, not blank and has length[2,50]; responsible for the storage about of email.
 * <p>7. phone - has length[6,14]; responsible for the storage about of phone.
 * <p>Hash code and equals by full_name, email and phone.
 * <p>
 * @author Oleksandr Kogutenko
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Long created;
    @JsonIgnore
    private Long updated;
    @JsonIgnore
    private boolean is_active;

    @NotBlank(groups = {OnCreated.class}, message = "Name cannot be empty")
    @NotNull(groups = {OnCreated.class}, message = "Name cannot be null")
    @Length(groups = {OnCreated.class, OnUpdated.class}, min = 2, max = 50,
            message = "Name's length must be between 2 and 100")
    private String full_name;

    @NotNull(groups = {OnCreated.class}, message = "Email cannot be null")
    @NotBlank(groups = {OnCreated.class}, message = "Email cannot be empty")
    @Length(groups = {OnCreated.class}, min = 2, max = 100,
            message = "Email's length must be between 2 and 100")
    @EmailChecker(groups = {OnCreated.class})
    private String email;
    @Pattern(groups = {OnCreated.class, OnUpdated.class},
            regexp = "(\\+)[0-9]{6,14}", message = "Incorrect phone number (+111111)")
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDTO)) return false;

        CustomerDTO customer = (CustomerDTO) o;

        return (getId().equals(customer.getId())) ||
                (getFull_name().equals(customer.getFull_name())) ||
                (getEmail().equals(customer.getEmail())) ||
                (getPhone().equals(customer.getPhone()));
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFull_name().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }
}
