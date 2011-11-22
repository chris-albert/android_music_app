package com.creasetoph.music;

public class LibraryItem implements Comparable<LibraryItem>{
	
	private String _type;
	private String _value;
	
	public LibraryItem(String type,String value) {
		setType(type);
		setValue(value);
	}

	public void setType(String type) {
		_type = type;
	}

	public String getType() {
		return _type;
	}

	public void setValue(String value) {
		_value = value;
	}

	public String getValue() {
		return _value;
	}

	public int compareTo(LibraryItem another) {
		return _value.compareTo(another.getValue());
	}
}
