package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get all favorites
     * @return {@link List}
     */
    List<Neighbour> getFavorites();

    /**
     * Add a Favorite
     * @param neighbour
     */
    void addToFavorite(Neighbour neighbour);

    /**
     * Remove a favorite from the list
     * @param neighbour
     */
    void deleteFavorite(Neighbour neighbour);
}
