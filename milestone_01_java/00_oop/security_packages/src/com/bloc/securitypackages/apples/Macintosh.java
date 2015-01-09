package com.bloc.securitypackages.apples;

import com.bloc.securitypackages.citrus.*;
import com.bloc.securitypackages.colors.*;
import com.bloc.securitypackages.*;

public class Macintosh extends Apple{

	public Macintosh() {
		super(Macintosh.class.getSimpleName(), 200, new Red(), 0.14d);
	}

	public void bite() {
		setWeight(getWeight() - 0.01d);
	}

}