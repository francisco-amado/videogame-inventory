package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.GameFactory;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.valueobjects.Console;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Region;
import com.inventory.app.dto.EditGameDTO;
import com.inventory.app.dto.CreateGameDTO;
import com.inventory.app.exceptions.BusinessRulesException;
import com.inventory.app.exceptions.InvalidEntryDataException;
import com.inventory.app.repositories.GameRepository;
import com.inventory.app.utils.ServiceResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameFactory gameFactory;

    @Autowired
    public GameService(GameRepository gameRepository, GameFactory gameFactory) {

        this.gameRepository = gameRepository;
        this.gameFactory = gameFactory;
    }

    public Game createGame(CreateGameDTO createGameDTO, Collection collection) throws InvalidEntryDataException {

        if (createGameDTO == null) {
            throw new InvalidEntryDataException(ServiceResponses.getGAME_NOT_FOUND());
        }

        boolean anyParameterNull = createGameDTO.getConsole() == null || createGameDTO.getRegion() == null ||
                createGameDTO.getReleaseDate() == null || createGameDTO.getName() == null;

        if (anyParameterNull) {
            throw new InvalidEntryDataException(ServiceResponses.getINVALID_ENTRY_DATA());
        }

        Name name = Name.createName(createGameDTO.getName());
        Console console = Console.createConsole(Console.ConsoleEnum.valueOf(createGameDTO.getConsole()));
        Region region = Region.createRegion(Region.RegionEnum.valueOf(createGameDTO.getRegion()));

        Game game = gameFactory.createGame(name, console, createGameDTO.getReleaseDate(), region);
        game.setCollection(collection);
        gameRepository.save(game);
        return game;
    }

    public Optional<Game> findById(UUID gameId) {
        return gameRepository.findById(gameId);
    }

    @Transactional
    public void setCollectionForGameList(List<Game> gameList, Collection collection) {

        if(gameList != null) {
            for (Game gameInList : gameList) {
                gameInList.setCollection(collection);
                gameRepository.saveAll(gameList);
            }
        }
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public void editGame(UUID gameId, EditGameDTO editGameDTO) throws NoSuchElementException {

        Optional<Game> gameToEditOpt = gameRepository.findById(gameId);

        if (gameToEditOpt.isEmpty()) {
            throw new NoSuchElementException(ServiceResponses.getGAME_NOT_FOUND());
        } else {
            Game gameToEdit = gameToEditOpt.get();

            if (editGameDTO.getName() != null && !Objects.equals(editGameDTO.getName(),
                    gameToEditOpt.get().getName().getName())) {
                Name newName = Name.createName(editGameDTO.getName());
                gameToEdit.setName(newName);
            }

            if (editGameDTO.getConsole() != null && !Objects.equals(editGameDTO.getConsole(),
                    gameToEditOpt.get().getConsole().getConsoleDescription())) {
                Console newConsole = Console.createConsole(Console.ConsoleEnum.valueOf(editGameDTO.getConsole()));
                gameToEdit.setConsole(newConsole);
            }

            if (editGameDTO.getReleaseDate() != null && !Objects.equals(editGameDTO.getReleaseDate(),
                    gameToEditOpt.get().getReleaseDate())) {
                gameToEdit.setReleaseDate(editGameDTO.getReleaseDate());
            }

            if (editGameDTO.getRegion() != null && !Objects.equals(editGameDTO.getRegion(),
                    gameToEditOpt.get().getRegion().getRegionDescription())) {
                Region newRegion = Region.createRegion(Region.RegionEnum.valueOf(editGameDTO.getRegion()));
                gameToEdit.setRegion(newRegion);
            }
            if (editGameDTO.getLocation() != null && !Objects.equals(editGameDTO.getLocation(),
                    gameToEditOpt.get().getLocation())) {
                gameToEdit.setLocation(editGameDTO.getLocation());
            }

            if (editGameDTO.getLocalBought() != null && !Objects.equals(editGameDTO.getLocalBought(),
                    gameToEditOpt.get().getLocalBought())) {
                gameToEdit.setLocalBought(editGameDTO.getLocalBought());
            }

            if (editGameDTO.getWasGifted() != null && !Objects.equals(editGameDTO.getWasGifted(),
                    gameToEditOpt.get().getWasGifted())) {
                gameToEdit.setWasGifted(editGameDTO.getWasGifted());
            }

            gameRepository.save(gameToEdit);
        }
    }

    public void deleteGame(UUID gameId) {
        Optional<Game> gameToDelete = gameRepository.findById(gameId);
        gameToDelete.ifPresent(gameRepository::delete);
    }

    public void deleteGameList(UUID collectionId) {
        List<Game> gamesToDelete = gameRepository.findAllByCollection_CollectionId(collectionId);
        gameRepository.deleteAll(gamesToDelete);
    }

    public boolean existsById(UUID gameId) {
        return gameRepository.existsById(gameId);
    }
}
