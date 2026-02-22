package com.tauceti.nowdu.event.repository;

import com.tauceti.nowdu.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByUserIdAndEventDate(Long userId, LocalDate eventDate);

    Optional<Event> findByIdAndUserId(Long id, Long userId);
}