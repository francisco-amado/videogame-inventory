package com.inventory.app.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionTest {

    @Test
    void createNewRegionSuccessfullyWithPalValue() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.PAL;
        Region region = Region.createRegion(regionValue);
        String expected = "PAL";

        //Act
        String actual = region.getRegionDescription();

        //Assert
        assertEquals(expected, actual);
        assertNotNull(region);
    }

    @Test
    void createNewRegionSuccessfullyWithNtscCValue() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.NTSCC;
        Region region = Region.createRegion(regionValue);
        String expected = "NTSCC";

        //Act
        String actual = region.getRegionDescription();

        //Assert
        assertEquals(expected, actual);
        assertNotNull(region);
    }

    @Test
    void createNewRegionSuccessfullyWithNtscJValue() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.NTSCJ;
        Region region = Region.createRegion(regionValue);
        String expected = "NTSCJ";

        //Act
        String actual = region.getRegionDescription();

        //Assert
        assertEquals(expected, actual);
        assertNotNull(region);
    }

    @Test
    void createNewRegionSuccessfullyWithNtscUValue() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.NTSCU;
        Region region = Region.createRegion(regionValue);
        String expected = "NTSCU";

        //Act
        String actual = region.getRegionDescription();

        //Assert
        assertEquals(expected, actual);
        assertNotNull(region);
    }

    @Test
    void createNewRegionSuccessfullyWithRegionFreeValue() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Region region = Region.createRegion(regionValue);
        String expected = "REGIONFREE";

        //Act
        String actual = region.getRegionDescription();

        //Assert
        assertEquals(expected, actual);
        assertNotNull(region);
    }

    @Test
    void createNewRegionSuccessfullyWithNoValue() {

        //Arrange
        Region region = new Region();

        //Act & Assert
        assertNotNull(region);
    }

    @Test
    void bothObjectsAreEqual() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Region.RegionEnum regionValue1 = Region.RegionEnum.REGIONFREE;
        Region region = Region.createRegion(regionValue);
        Region region1 = Region.createRegion(regionValue1);

        //Act & Assert
        assertEquals(region, region1);
    }

    @Test
    void comparingTheSameObject() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Region region = Region.createRegion(regionValue);

        //Act & Assert
        assertEquals(region, region);
    }

    @Test
    void bothObjectsAreDifferent() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Region.RegionEnum regionValue1 = Region.RegionEnum.PAL;
        Region region = Region.createRegion(regionValue);
        Region region1 = Region.createRegion(regionValue1);

        //Act & Assert
        assertNotEquals(region, region1);
    }

    @Test
    void bothObjectsAreDifferentInNature() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Object o = new Object();
        Region region = Region.createRegion(regionValue);

        //Act & Assert
        assertNotEquals(region, o);
    }

    @Test
    void oneOfTheTwoObjectsIsNull() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Region region = Region.createRegion(regionValue);
        Region region1 = null;

        //Act & Assert
        assertNotEquals(region, region1);
    }

    @Test
    void hashCodeIsTheSame() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Region.RegionEnum regionValue1 = Region.RegionEnum.REGIONFREE;
        Region region = Region.createRegion(regionValue);
        Region region1 = Region.createRegion(regionValue1);

        //Act & Assert
        assertEquals(region.hashCode(), region1.hashCode());
    }

    @Test
    void hashCodeIsDifferent() {

        //Arrange
        Region.RegionEnum regionValue = Region.RegionEnum.REGIONFREE;
        Region.RegionEnum regionValue1 = Region.RegionEnum.PAL;
        Region region = Region.createRegion(regionValue);
        Region region1 = Region.createRegion(regionValue1);

        //Act & Assert
        assertNotEquals(region.hashCode(), region1.hashCode());
    }
}