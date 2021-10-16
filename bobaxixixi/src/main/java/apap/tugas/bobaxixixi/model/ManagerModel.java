package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "manager")
public class ManagerModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idManager;

    @NotNull
    @Column(nullable = false)
    private String fullName;

    @NotNull
    @Column(nullable = false)
    private Integer gender;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "DD/MM/YYYY")
    private LocalDate dateOfBirth;

    @OneToOne(mappedBy = "manager")
    private StoreModel store;
}
