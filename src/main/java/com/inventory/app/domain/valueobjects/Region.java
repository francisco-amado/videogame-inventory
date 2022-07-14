package com.inventory.app.domain.valueobjects;

import java.util.Objects;

public class Region {

    public Region() {

    }

    private String regionDescription;

    private Region(String regionDescription) {

        this.regionDescription = regionDescription;
    }

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
        NTSC,
        SECAM,
        REGIONFREE,
        UNKNOWN
    }
}
