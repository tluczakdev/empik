package org.interview.empik.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "VISIT_COUNTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name = "REQUEST_COUNT")
    private int requestCounter;

    public VisitCounter(String login, int requestCounter) {
        this.login = login;
        this.requestCounter = requestCounter;
    }
}
