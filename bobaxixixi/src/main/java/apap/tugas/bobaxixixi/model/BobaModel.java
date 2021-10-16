package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "boba")
public class BobaModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoba;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Integer price;

    @NotNull
    @Column(nullable = false)
    private Integer size;

    @NotNull
    @Column(nullable = false)
    private Integer iceLevel;

    @NotNull
    @Column(nullable = false)
    private Integer sugarLevel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_topping", referencedColumnName = "idTopping", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ToppingModel topping;

    @OneToMany(mappedBy = "boba")
    List<StoreBobaModel> storeBoba;

}
