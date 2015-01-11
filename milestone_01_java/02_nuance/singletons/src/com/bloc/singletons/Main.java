package com.bloc.singletons;

import com.bloc.singletons.listeners.*;
import com.bloc.singletons.talkers.*;

public class Main extends Object {

	public static void main(String [] args) {
		// Instantiate some talkers and some listeners
		Talker [] talkers = {new Parent(), new PetOwner(), new Singer()};
		Listener [] listeners = {new AudienceMember(), new Child(), new Pet()};
		
		// Register listeners
		Speakerphone phone = Speakerphone.getInstance();
		phone.addListener(listeners[0]);
		phone.addListener(listeners[1]);
		phone.addListener(listeners[2]);
		
		// Send messages!
		phone.shoutMessage(talkers[0]);
		phone.shoutMessage(talkers[1]);
		phone.shoutMessage(talkers[2], listeners[0].getClass());
		phone.shoutMessage(talkers[1], listeners[2].getClass());
	}
}
