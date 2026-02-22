package com.tauceti.nowdu.routine.repository;

import com.tauceti.nowdu.routine.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    List<Routine> findAllByUserId(Long userId);

    Optional<Routine> findByIdAndUserId(Long id, Long userId);
}