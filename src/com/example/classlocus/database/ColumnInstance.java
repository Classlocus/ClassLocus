package com.example.classlocus.database;

public class ColumnInstance<T> {
	
	private String name;
	private T value;
	
	public ColumnInstance(String nm, T val) {
		// TODO Auto-generated constructor stub
		name = nm;
		value = val;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	
}
