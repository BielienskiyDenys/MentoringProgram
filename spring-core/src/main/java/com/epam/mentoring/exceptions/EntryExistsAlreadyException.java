package com.epam.mentoring.exceptions;

public class EntryExistsAlreadyException extends Exception {

	public EntryExistsAlreadyException() {
		super();
	};

	public EntryExistsAlreadyException(String errorMessage) {
		super(errorMessage);
	};
}
