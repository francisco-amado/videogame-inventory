package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.GameFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.inventory.app.dto.EditGameDTO;
import com.inventory.app.dto.GameDTO;
import com.inventory.app.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameFactoryInterface gameFactoryInterface;

    @Autowired
    public GameService(GameRepository gameRepository, GameFactoryInterface gameFactoryInterface) {
        this.gameRepository = gameRepository;
        this.gameFactoryInterface = gameFactoryInterface;
    }

    public UUID createGame(GameDTO gameDTO) throws IllegalStateException {

        if (gameDTO == null) {
            throw new IllegalStateException("Invalid entry data");
        }

        boolean anyParameterNull = gameDTO.getConsole() == null || gameDTO.getRegion() == null ||
                gameDTO.getReleaseDate() == null || gameDTO.getName() == null;

        if (anyParameterNull) {
            throw new IllegalStateException("Invalid entry data");
        }

        Name name = Name.createName(gameDTO.getName());
        Console console = Console.createConsole(Console.ConsoleEnum.valueOf(gameDTO.getConsole()));
        Region region = Region.createRegion(Region.RegionEnum.valueOf(gameDTO.getRegion()));

        Game game = gameFactoryInterface.createGame(name, console, gameDTO.getReleaseDate(), region);
        gameRepository.save(game);
        return game.getGameId();
    }

    public Optional<Game> findById(UUID gameId) {
        return gameRepository.findById(gameId);
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public void editGame(UUID gameId, EditGameDTO editGameDTO) throws NoSuchElementException {

        Optional<Game> gameToEditOpt = gameRepository.findById(gameId);

        if (gameToEditOpt.isEmpty()) {
            throw new NoSuchElementException("The requested game does not exist");
        } else {
            Game gameToEdit = gameToEditOpt.get();

            if (editGameDTO.getName() != null) {
                Name newName = Name.createName(editGameDTO.getName());
                gameToEdit.setName(newName);
            } else if (editGameDTO.getConsole() != null) {
                Console newConsole = Console.createConsole(Console.ConsoleEnum.valueOf(editGameDTO.getConsole()));
                gameToEdit.setConsole(newConsole);
            } else if (editGameDTO.getReleaseDate() != null) {
                gameToEdit.setReleaseDate(editGameDTO.getReleaseDate());
            } else if (editGameDTO.getRegion() != null) {
                Region newRegion = Region.createRegion(Region.RegionEnum.valueOf(editGameDTO.getRegion()));
                gameToEdit.setRegion(newRegion);
            } else if (editGameDTO.getLocation() != null) {
                gameToEdit.setLocation(editGameDTO.getLocation());
            } else if (editGameDTO.getLocalBought() != null) {
                gameToEdit.setLocalBought(editGameDTO.getLocalBought());
            } else if (editGameDTO.getWasGifted() != null) {
                gameToEdit.setWasGifted(editGameDTO.getWasGifted());
            }
        }
    }

    public void deleteGame(UUID gameId) throws UnsupportedOperationException {

        Optional<Game> gameToDelete = gameRepository.findById(gameId);

        if (gameToDelete.isPresent() && gameToDelete.get().getCollection() != null) {
            throw new UnsupportedOperationException("Game cannot be deleted if it belongs to a collection");
        }

        gameToDelete.ifPresent(gameRepository::delete);
    }
}
