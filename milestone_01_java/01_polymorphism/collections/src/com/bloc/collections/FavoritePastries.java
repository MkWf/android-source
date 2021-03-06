package com.bloc.collections;

import java.util.*;

/*
 * FavoritePastries
 * 
 * This class maintains a record of Pastry objects and their
 * relation to a 1 to 5 rating scale.
 *
 * For example:
 * 5 Star Pastries: Cronut, Cherry Pie
 * 4 Star Pastries: Strudel, Apple Pie
 * 3 Star Pastries: Bear Claw
 * 2 Star Pastries: Pop-Tart
 * 1 Star Pastries: Pot Pie
 *
 * A pastry may not have duplicate entries
 */
public class FavoritePastries {

	/*
	 * Use a HashMap to store the relationship
	 * between rating and pastry
	 */
	private Map<Pastry, Integer> pastryRank;

	public FavoritePastries() {
		// WORK HERE
		pastryRank = new HashMap<Pastry, Integer>();
	}

	/* 
	 * Add a Pastry to the FavoritePastries class.
	 *
	 * This method stores this Pastry and its
	 * associated rating in some sort of data structure.
	 *
	 * If this Pastry already exists in FavoritePastries,
	 * its old rating should be updated.
	 *
	 * @param pastry The Pastry to store
	 * @param rating The rating to associate with it
	 * @return nothing
	 */
	public void addPastry(Pastry pastry, int rating) {
		// WORK HERE
		if(pastryRank.containsKey(pastry)){
			pastryRank.remove(pastry);
			pastryRank.put(pastry, rating);
		}else{
			pastryRank.put(pastry, rating);
		}
	}

	/*
	 * Remove a Pastry from FavoritePastries
	 *
	 * This method removes any reference to this exact
	 * Pastry object and its associated rating
	 *
	 * @param pastry The Pastry to remove
	 * @return true if the Pastry was found and removed,
	 *		   false otherwise
	 */
	public boolean removePastry(Pastry pastry) {
		// WORK HERE
		if(pastryRank.containsKey(pastry)){
			pastryRank.remove(pastry);
			return true;
		}else{
			return false;
		}
	}

	/*
	 * Return the associated rating for a given Pastry
	 *
	 * This method will find the associated rating for
	 * this Pastry inside of FavoritePastries and return
	 * it to its caller.
	 *
	 * @param  pastry The Pastry for which a rating must
	 * 		   be recovered
	 * @return the rating associated with this Pastry or
	 *		   -1 if not found among FavoritePastries
	 */
	public int getRatingForPastry(Pastry pastry) {
		// WORK HERE
		if(pastryRank.containsKey(pastry)){
			return pastryRank.get(pastry);
		}else{
			return -1;
		}
		
	}

	/*
	 * Return a Set of all the Pastries with a given
	 * rating.
	 *
	 * This method returns a Set<Pastry> object containing
	 * references to all of the Pastries associated with
	 * a particular rating.
	 *
	 * @param  rating The rating of the Pastry objects the
	 *		   caller wishes to recover
	 * @return A Set of all the Pastry objects with a rating
	 * 		   of `rating`. Returns an empty set if none are
	 *         found
	 */
	public Collection<Pastry> getPastriesForRating(int rating) {
		// WORK HERE
		List<Pastry> pastryList = new ArrayList<Pastry>();
		for(Pastry pastry : pastryRank.keySet()){
			if(pastryRank.get(pastry).equals(rating)){
				pastryList.add(pastry);
			}
		}
		return pastryList;
	}
}