package com.inventory.app.domain.valueobjects;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Component
public class Region {

    private @Getter @Setter String regionDescription;

    private Region(String regionDescription) {
        this.regionDescription = regionDescription;
    }

    public Region() {}

    public static Region createRegion(Region.RegionEnum regionEnum) {
        return new Region(regionEnum.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return regionDescription.equals(region.regionDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionDescription);
    }

    public enum RegionEnum{
        PAL,
        NTSCU,
        NTSCJ,
        NTSCC,
        REGIONFREE
    }
}
