package lab.cloud.bookstore.web.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    @Column(length = 50)
    private final String username;

    @Column(length = 100)
    private final String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private final List<String> authorities;

    @SuppressWarnings("unused")
    private User() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.authorities = null;
    }

    public User(String username, String password, String... authorities) {
        this.id = null;
        this.username = username;
        this.password = password;
        this.authorities = Arrays.asList(authorities);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}