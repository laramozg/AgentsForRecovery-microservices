package org.example.sportsfight.repositories;

import org.example.sportsfight.models.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PerformerRepository extends JpaRepository<Performer, UUID> {

    Optional<Performer> findById(UUID id);
}