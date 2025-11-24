package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.SoareEntity;
import net.ausiasmarch.persutil.repository.SoareRepository;

@Service
public class SoareService {

    @Autowired
    SoareRepository oSoareRepository;

    @Autowired
    AleatorioService oAleatorioService;

    public SoareEntity get(Long id) {
        return oSoareRepository.findById(id).orElse(null);
    }

    public Long create(SoareEntity oSoareEntity) {
        oSoareEntity.setFechaCreacion(LocalDateTime.now());
        oSoareEntity.setFechaModificacion(LocalDateTime.now());
        // Por defecto, una nueva pregunta no está publicada (requiere aprobación del admin)
        if (oSoareEntity.getPublicacion() == null) {
            oSoareEntity.setPublicacion(false);
        }
        return oSoareRepository.save(oSoareEntity).getId();
    }

    public SoareEntity update(SoareEntity oSoareEntity) {
        SoareEntity oSoareEntityDB = this.get(oSoareEntity.getId());
        if (oSoareEntityDB != null) {
            oSoareEntityDB.setPreguntas(oSoareEntity.getPreguntas());
            oSoareEntityDB.setPublicacion(oSoareEntity.getPublicacion());
            oSoareEntityDB.setFechaModificacion(LocalDateTime.now());
            return oSoareRepository.save(oSoareEntityDB);
        } else {
            return null;
        }
    }

    public Long delete(Long id) {
        oSoareRepository.deleteById(id);
        return id;
    }

    public Page<SoareEntity> getPage(Pageable oPageable, String filter) {
        if (filter == null || filter.isEmpty()) {
            return oSoareRepository.findAll(oPageable);
        } else {
            return oSoareRepository.findByPreguntasContainingIgnoreCase(filter, oPageable);
        }
    }

    public Page<SoareEntity> getPageByPublicacion(Pageable oPageable, String filter) {
        if (filter == null || filter.isEmpty()) {
            return oSoareRepository.findByPublicacionTrue(oPageable);
        } else {
            return oSoareRepository.findByPublicacionTrueAndPreguntasContainingIgnoreCase(filter, oPageable);
        }
    }

    public Long populate(int amount) {
        List<SoareEntity> soares = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            SoareEntity oSoareEntity = new SoareEntity();
            oSoareEntity.setPreguntas(oAleatorioService.getPreguntaNeuro());
            oSoareEntity.setFechaCreacion(oAleatorioService.getFechaCreacion());
            oSoareEntity.setFechaModificacion(oAleatorioService.getFechaModificacion(oSoareEntity.getFechaCreacion()));
            oSoareEntity.setPublicacion(ThreadLocalRandom.current().nextBoolean());
            soares.add(oSoareEntity);
        }
        oSoareRepository.saveAll(soares);
        return oSoareRepository.count();
    }

    public Long empty() {
        oSoareRepository.deleteAll();
        oSoareRepository.flush();
        return oSoareRepository.count();
    }

}
