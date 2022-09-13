package com.inventory.app.domain.valueobjects;

import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Embeddable
@Component
public class OwnerId implements Serializable {

    String ownerId;

    public OwnerId(String ownerId){

        this.ownerId = ownerId;
    }

    private static final long serialVersionUID = 5L;

    private static final AtomicLong idCounter = new AtomicLong();

    public OwnerId() {

    }

    public static OwnerId createOwnerId() {

        return new OwnerId("OWNER" + idCounter.getAndIncrement());
    }
}
