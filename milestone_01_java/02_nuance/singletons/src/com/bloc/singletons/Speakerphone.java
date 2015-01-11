package com.bloc.singletons;

import java.util.*;
import java.lang.*;

/*
 * This is a singleton class which maintains communication
 * between classes and Listeners
 */
public class Speakerphone extends Object {
	
	private static Speakerphone sPhone;

	/*
	 * get
	 * @return the singleton instance of Speakerphone
	 */
	public static Speakerphone getInstance(){
		if(sPhone == null){
			sPhone = new Speakerphone();
		}
		return sPhone;
	}

	private List<Listener> mListeners;

	private Speakerphone(){
		mListeners = new ArrayList<Listener>();
	}

	/*
	 * addListener
	 * Add a listener to Speakerphone's list
	 * @param listener an instance of the Listener interface
	 */
	public void addListener(Listener listener){
		mListeners.add(listener);
	}
	/*
	 * removeListener
	 * Remove a Listener from the Speakerphone's list
	 * @param listener the Listener to remove
	 */
	public void removeListener(Listener listener){
		mListeners.remove(listener);
	}
	/*
	 * shoutMessage
	 * Sends the message to all of the Listeners tracked by Speakerphone
	 * @param talker a Talker whose message will be sent
	 */
	public void shoutMessage(Talker talker){
		Iterator<Listener> listIterator = mListeners.iterator();
		while(listIterator.hasNext()){
			listIterator.next().onMessageReceived(talker.getMessage());
		}
	}
	/*
	 * shoutMessage
	 * Sends the message to all of the Listeners which are instances of
	 * the class parameter
	 * @param talker a Talker whose message will be sent
	 * @param cls a Class object representing the type which the Listener
	 *			  should extend from in order to receive the message
	 *
	 * HINT: see Class.isAssignableFrom()
	 *		 http://docs.oracle.com/javase/7/docs/api/java/lang/Class.html#isAssignableFrom(java.lang.Class)
	 */
	public void shoutMessage(Talker talker, Class cls){
		Iterator<Listener> listIterator = mListeners.iterator();

		while(listIterator.hasNext()){
			Listener listenTemp = listIterator.next();
			if(listenTemp.getClass().isAssignableFrom(cls)){
				listenTemp.onMessageReceived(talker.getMessage());
			}
		}
		//App.class.getName()
	}
	/*
	 * removeAll
	 * Removes all Listeners from Speakerphone
	 */
	public void removeAll(){
		mListeners.clear();
	}
}