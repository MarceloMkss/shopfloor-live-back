package com.infotek.shopfloor_live.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infotek.shopfloor_live.dto.MachineDTO;
import com.infotek.shopfloor_live.dto.PageResponse;
import com.infotek.shopfloor_live.service.MachineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin(origins = "*")
public class MachineController {

		private final MachineService service;
	    public MachineController(MachineService service) { 
	    	this.service = service;
	    }

	   @GetMapping
	    public List<MachineDTO> getAll() {
	        return service.findAll();
	    }

	    @GetMapping("/page")
	    public PageResponse<MachineDTO> page(
	            @RequestParam(defaultValue = "") String status,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {
	        return service.page(status, page, size);
	    }

	    @GetMapping("/{id}")
	    public MachineDTO get(@PathVariable Long id) {
	        return service.get(id);
	    }

	    @PostMapping
	    public ResponseEntity<MachineDTO> create(@Valid @RequestBody MachineDTO dto) {
	        MachineDTO created = service.create(dto);
	        return ResponseEntity.created(URI.create("/api/machines/" + created.id())).body(created);
	    }

	    // PUT total (idempotente)
	    @PutMapping("/{id}")
	    public MachineDTO put(@PathVariable Long id, @Valid @RequestBody MachineDTO dto) {
	        return service.update(id, dto);
	    }

	    // PATCH parcial (valida solo lo que venga)
	    @PatchMapping("/{id}")
	    public MachineDTO patch(@PathVariable Long id, @RequestBody MachineDTO dto) {
	        return service.update(id, dto);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        service.delete(id);
	        return ResponseEntity.noContent().build();
	    }    
}
