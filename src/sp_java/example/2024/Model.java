package com.lgcns.test;

import java.util.ArrayList;

public class Model {
	@Override
	public String toString() {
		return "Model [modelname=" + modelname + ", classes=" + classes + ", url=" + url + "]";
	}
	String modelname;
	ArrayList<ModelClass> classes;
	String url;
}