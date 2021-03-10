package com.epam.mentoring.exceptions;

public class EntryNotFoundException extends Exception {

	public EntryNotFoundException() {
		super();
	};

	public EntryNotFoundException(String errorMessage) {
		super(errorMessage);
	};
}
