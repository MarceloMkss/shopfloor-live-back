package com.infotek.shopfloor_live.service;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infotek.shopfloor_live.dto.MachineDTO;
import com.infotek.shopfloor_live.dto.PageResponse;
import com.infotek.shopfloor_live.mapper.MachineMapper;
import com.infotek.shopfloor_live.model.Machine;
import com.infotek.shopfloor_live.repository.MachineRepository;

@Service
public class MachineService {

    private final MachineRepository repo;
    private final MachineMapper mapper;

    public MachineService(MachineRepository repo, MachineMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public PageResponse<MachineDTO> page(String status, int page, int size) {
        Pageable p = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Machine> result = (status == null || status.isBlank())
                ? repo.findAll(p)
                : repo.findByStatusIgnoreCase(status, p);
        return new PageResponse<>(
                result.getContent().stream().map(mapper::toDto).toList(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.getSize(),
                result.getNumber()
        );
    }

    @Transactional(readOnly = true)
    public java.util.List<MachineDTO> findAll() {
        return repo.findAll(Sort.by("id").descending()).stream().map(mapper::toDto).toList();
    }

    @Transactional
    public MachineDTO create(MachineDTO dto) {
        Machine saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Transactional
    public MachineDTO update(Long id, MachineDTO dto) {
        Machine m = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Machine not found"));
        mapper.updateEntity(m, dto);
        return mapper.toDto(m);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NoSuchElementException("Machine not found");
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public MachineDTO get(Long id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Machine not found"));
    }
}
