package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favorites = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavorites(){
        return favorites;
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void addToFavorite(Neighbour neighbour){
        if (!favorites.contains(neighbour)){
            favorites.add(neighbour);
        }
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void deleteFavorite(Neighbour neighbour){
        favorites.remove(neighbour);
    }

}
