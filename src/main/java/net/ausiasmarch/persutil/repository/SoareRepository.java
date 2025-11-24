package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.SoareEntity;

public interface SoareRepository extends JpaRepository<SoareEntity, Long> {

    Page<SoareEntity> findByPublicacionTrue(Pageable oPageable);

    Page<SoareEntity> findByPreguntasContainingIgnoreCase(String preguntas, Pageable oPageable);

    Page<SoareEntity> findByPublicacionTrueAndPreguntasContainingIgnoreCase(String preguntas, Pageable oPageable);
}
