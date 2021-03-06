package es.juanlsanchez.bm.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user_api")
public class User extends BaseEntity {

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60)
    private String password;

    private String surname;

    @Past
    private Instant creationMoment;

    @NotNull
    @Column(nullable = false)
    private boolean activated = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
	    joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
	    inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") })
    private Set<Authority> authorities = new HashSet<>();

}
