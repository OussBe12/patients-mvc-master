package ma.emsi.patientsmvc.sec.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean active;
    @ManyToMany(fetch = FetchType.EAGER) /*a chaque fois je charge un user a partir de la bdd automatiquement les données seront chargées*/
    private List<AppRole> appRoles=new ArrayList<>();
}
