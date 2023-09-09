package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.CollectionFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.dto.CreateCollectionDTO;
import com.inventory.app.repositories.CollectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollectionServiceTest {

    @InjectMocks
    public CollectionService collectionService;

    @Mock
    CollectionRepository collectionRepository;

    @Mock
    GameService gameService;

    @Mock
    CollectionFactoryInterface collectionFactoryInterface;

    @Test
    void createAndSaveCollectionSuccessfully(){

        //Arrange
        Collection collection1 = mock(Collection.class);
        Game game1 = mock(Game.class);
        Game game2 = mock(Game.class);
        Owner owner = mock(Owner.class);
        List<Game> gameList = new ArrayList<>();
        gameList.add(game1);
        gameList.add(game2);
        CreateCollectionDTO createCollectionDTO = new CreateCollectionDTO();
        createCollectionDTO.setGameList(gameList);
        when(collectionFactoryInterface.createCollection(owner, createCollectionDTO)).thenReturn(collection1);
        when(collectionRepository.save(collection1)).thenReturn(collection1);

        //Act
        Collection collection2 = collectionFactoryInterface.createCollection(owner, createCollectionDTO);
        Collection result = collectionRepository.save(collection2);

        //Assert
        assertEquals(collection1, result);
    }

    @Test
    void createAndSaveCollectionSameOwnerNewCollection(){

        //Arrange
        Collection collection1 = mock(Collection.class);
        Game game1 = mock(Game.class);
        Game game2 = mock(Game.class);
        Owner owner = mock(Owner.class);
        List<Game> gameList1 = new ArrayList<>();
        List<Game> gameList2 = new ArrayList<>();
        gameList1.add(game1);
        gameList2.add(game2);
        CreateCollectionDTO createCollectionDTO = new CreateCollectionDTO();
        createCollectionDTO.setGameList(gameList2);
        collection1.setOwner(owner);
        collection1.setGameList(gameList1);

        //Act
        Collection collection2 = collectionFactoryInterface.createCollection(owner, createCollectionDTO);

        //Assert
        assertNotEquals(collection1, collection2);
        assertNotNull(collection1);
        assertNull(collection2);
    }
}
