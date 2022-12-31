package com.inventory.app.domain.valueobjects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Component
public class Console {

    private @Getter @Setter String consoleDescription;

    private Console(String consoleDescription) {
        this.consoleDescription = consoleDescription;
    }

    public Console() {}

    public static Console createConsole(ConsoleEnum consoleEnum) {
        return new Console(consoleEnum.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return consoleDescription.equals(console.consoleDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consoleDescription);
    }

    public enum ConsoleEnum {
        PLAYSTATION,
        PLAYSTATION2,
        PLAYSTATION3,
        PLAYSTATION4,
        PLAYSTATION5,
        PSP,
        PSVITA,
        XBOX,
        XBOX360,
        XBOXONE,
        XBOXSERIESXS,
        SWITCH,
        WIIU,
        WII,
        GAMECUBE,
        NINTENDO64,
        SNES,
        NES,
        GAMEBOY,
        GAMEBOYCOLOR,
        GAMEBOYADVANCE,
        NINTENDODS,
        NINTENDO3DS,
        MASTERSYSTEM,
        MEGADRIVE,
        SATURN,
        DREAMCAST,
        GAMEGEAR,
        PC
    }

}
