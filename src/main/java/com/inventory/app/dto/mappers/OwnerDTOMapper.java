package com.inventory.app.dto.mappers;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.OwnerDTO;
import org.springframework.stereotype.Service;

@Service
public class OwnerDTOMapper {

    public OwnerDTO toDTO(Owner owner) {

        OwnerDTO ownerDTO = new OwnerDTO();

        ownerDTO.setUserName(owner.getUsername());
        ownerDTO.setEmail(owner.getEmail());
        ownerDTO.setOwnerId(owner.getOwnerId());

        return ownerDTO;
    }
}
