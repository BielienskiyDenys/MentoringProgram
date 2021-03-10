package com.epam.mentoring.exceptions;

public class EntryValidationException extends Exception {

	public EntryValidationException() {
		super();
	};

	public EntryValidationException(String errorMessage) {
		super(errorMessage);
	};
}
