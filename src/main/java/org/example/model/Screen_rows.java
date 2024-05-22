package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screen_rows")
public class Screen_rows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", referencedColumnName = "id_screen")
    private Screen screen;

    @Column(name = "row_number")
    private Integer row_number;

    @Column(name = "number_of_seats")
    private Integer number_of_seats;

}
