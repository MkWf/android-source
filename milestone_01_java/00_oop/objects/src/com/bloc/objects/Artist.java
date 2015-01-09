package com.bloc.objects;

class Artist extends Object {
	// The artist's first name
	String mFirstName;
	// The artist's last name
	String mLastName;

	/*
	 * Only Constructor
	 * @param firstName
	 * @param lastName
	 */
	// CONSTRUCTOR CODE GOES HERE
	Artist(String firstName, String lastName){
		mFirstName = firstName;
		mLastName = lastName;
	}
}