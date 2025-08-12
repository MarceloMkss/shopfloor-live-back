package com.infotek.shopfloor_live.mapper;

import org.springframework.stereotype.Component;

import com.infotek.shopfloor_live.dto.MachineDTO;
import com.infotek.shopfloor_live.model.Machine;

@Component
public class MachineMapper {

    public MachineDTO toDto(Machine m) {
        return new MachineDTO(m.getId(), m.getName(), m.getVendor(), m.getModel(), m.getStatus());
    }

    public Machine toEntity(MachineDTO d) {
        Machine m = new Machine();
        m.setId(d.id());
        m.setName(d.name());
        m.setVendor(d.vendor());
        m.setModel(d.model());
        m.setStatus(d.status());
        return m;
    }

    public void updateEntity(Machine m, MachineDTO d) {
        // para PUT/PATCH (no toques id)
        if (d.name()   != null) m.setName(d.name());
        if (d.vendor() != null) m.setVendor(d.vendor());
        if (d.model()  != null) m.setModel(d.model());
        if (d.status() != null) m.setStatus(d.status());
    }
}

