package com.tauceti.nowdu.event.domain;

import com.tauceti.nowdu.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Event {

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
    private LocalDate eventDate;

    @Column(nullable = false)
    private LocalTime startTime;

    private LocalTime endTime; // optional

    @Column(nullable = false)
    @Builder.Default
    private boolean isDone = false;

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

    public void toggleDone() {
        this.isDone = !this.isDone;
    }

    public void update(String title, String memo, LocalDate eventDate, LocalTime startTime, LocalTime endTime) {
        this.title = title;
        this.memo = memo;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}