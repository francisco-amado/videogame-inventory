package com.inventory.app.domain.factories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.Name;
import org.springframework.stereotype.Service;

@Service
public interface OwnerFactoryInterface {

    public Owner createOwner(Name userName, String email, String password);
}
