package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.PavonEntity;
import net.ausiasmarch.persutil.repository.PavonRepository;

@Service    //Es necesatrio poner el @service para que lo detecten
public class PavonService {

    @Autowired
    PavonRepository oPavonRepository;

     // ----------------------------CRUD---------------------------------
    public PavonEntity get(Long id) {
        return oPavonRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public Long create(PavonEntity pavonEntity) {
        pavonEntity.setFechaCreacion(LocalDateTime.now());
        pavonEntity.setFechaModificacion(null);
        oPavonRepository.save(pavonEntity);
        return pavonEntity.getId();
    }

    public Long update(PavonEntity pavonEntity) {
        PavonEntity existingBlog = oPavonRepository.findById(pavonEntity.getId())
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        existingBlog.setNombre(pavonEntity.getNombre());
        existingBlog.setUrl(pavonEntity.getUrl());
        existingBlog.setFechaModificacion(LocalDateTime.now());
        oPavonRepository.save(existingBlog);
        return existingBlog.getId();
    }

    public Long delete(Long id) {
        oPavonRepository.deleteById(id);
        return id;
    }

    public Page<PavonEntity> getPage(Pageable oPageable) {
        return oPavonRepository.findAll(oPageable);
    }

    public Long count() {
        return oPavonRepository.count();
    }

}
