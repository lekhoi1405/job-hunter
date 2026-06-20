package Group.Artifact.domain.entity;

import java.util.ArrayList;
import java.util.List;

import Group.Artifact.domain.base.AuditBaseEntity;
import Group.Artifact.util.constant.GenderEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditBaseEntity{
    private String name;
    private String email;
    private String password;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String address;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshToken = new ArrayList<>();

    public void addToken(RefreshToken refreshToken){
        this.refreshToken.add(refreshToken);
        if(refreshToken != null){
            refreshToken.setUser(this);
        }
    }

}
