package com.tauceti.nowdu.routine.domain;

import com.tauceti.nowdu.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "routines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private String memo;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private boolean mon;
    @Column(nullable = false)
    private boolean tue;
    @Column(nullable = false)
    private boolean wed;
    @Column(nullable = false)
    private boolean thu;
    @Column(nullable = false)
    private boolean fri;
    @Column(nullable = false)
    private boolean sat;
    @Column(nullable = false)
    private boolean sun;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String memo, LocalTime startTime,
                       boolean mon, boolean tue, boolean wed,
                       boolean thu, boolean fri, boolean sat, boolean sun) {
        this.title = title;
        this.memo = memo;
        this.startTime = startTime;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }
}