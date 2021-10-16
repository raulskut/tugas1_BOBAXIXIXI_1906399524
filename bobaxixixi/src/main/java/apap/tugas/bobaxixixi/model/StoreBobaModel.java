package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "store_boba")
public class StoreBobaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTokoBoba;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    StoreModel store;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_boba")
    BobaModel boba;

    @NotNull
    @Column(nullable = false)
    private String productionCode;
}
