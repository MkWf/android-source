package com.bloc.securitypackages.apples;

import com.bloc.securitypackages.citrus.*;
import com.bloc.securitypackages.colors.*;
import com.bloc.securitypackages.*;

public abstract class Apple extends Fruit {

	public Apple(String name, int calories, Color color, double weight){
		super(name, calories, color, weight);
	}

	abstract void bite();

}