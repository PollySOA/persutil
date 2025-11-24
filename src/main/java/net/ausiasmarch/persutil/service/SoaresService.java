package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.SoaresEntity;
import net.ausiasmarch.persutil.repository.SoaresRepository;

@Service
public class SoaresService {

    @Autowired
    SoaresRepository oSoaresRepository;

    @Autowired
    SoaresAleatorioService oSoaresAleatorioService;

    public SoaresEntity get(Long id) {
        return oSoaresRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(" No encontrado Entity Soares con id_ " + id));
    }

    public Long create(SoaresEntity oSoaresEntity) {
        oSoaresEntity.setFechaCreacion(LocalDateTime.now());
        oSoaresEntity.setFechaModificacion(null);
        // Por defecto, una nueva pregunta no está publicada (requiere aprobación del admin)
        if (oSoaresEntity.getPublicacion() == null) {
            oSoaresEntity.setPublicacion(false);
        }
        return oSoaresRepository.save(oSoaresEntity).getId();
    }

    public Long update(SoaresEntity oSoaresEntity) {
        SoaresEntity oSoaresExisting = oSoaresRepository.findById(oSoaresEntity.getId())
          .orElseThrow(() -> new RuntimeException(" No encontrado Entity Soares con id_ " + oSoaresEntity.getId()));
        oSoaresExisting.setPreguntas(oSoaresEntity.getPreguntas());
        oSoaresExisting.setPublicacion(oSoaresEntity.getPublicacion());
        oSoaresExisting.setFechaModificacion(LocalDateTime.now());
        oSoaresRepository.save(oSoaresExisting);
        return oSoaresExisting.getId();
    }

    public Long delete(Long id) {
        oSoaresRepository.deleteById(id);
        return id;
    }

    public Page<SoaresEntity> getPage(Pageable oPageable, String filter) {
        if (filter == null || filter.isEmpty()) {
            return oSoaresRepository.findAll(oPageable);
        } else {
            return oSoaresRepository.findByPreguntasContainingIgnoreCase(filter, oPageable);
        }
    }

    public Page<SoaresEntity> getPageByPublicacion(Pageable oPageable, String filter) {
        if (filter == null || filter.isEmpty()) {
            return oSoaresRepository.findByPublicacionTrue(oPageable);
        } else {
            return oSoaresRepository.findByPublicacionTrueAndPreguntasContainingIgnoreCase(filter, oPageable);
        }
    }

    public Long populate(int amount) {
        List<SoaresEntity> soares = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            SoaresEntity oSoaresEntity = new SoaresEntity();
            oSoaresEntity.setPreguntas(oSoaresAleatorioService.getPreguntaNeuro());
            oSoaresEntity.setFechaCreacion(oSoaresAleatorioService.getFechaCreacion());
            oSoaresEntity.setFechaModificacion(oSoaresAleatorioService.getFechaModificacion(oSoaresEntity.getFechaCreacion()));
            oSoaresEntity.setPublicacion(ThreadLocalRandom.current().nextBoolean());
            soares.add(oSoaresEntity);
        }
        oSoaresRepository.saveAll(soares);
        return oSoaresRepository.count();
    }

    public Long empty() {
        oSoaresRepository.deleteAll();
        oSoaresRepository.flush();
        return oSoaresRepository.count();
    }

}
