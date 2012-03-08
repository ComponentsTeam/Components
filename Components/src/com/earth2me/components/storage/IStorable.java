package com.earth2me.components.storage;


/**
 * Represents a simple object prepared for storage.
 *
 * @author paul
 */
public interface IStorable
{
	/**
	 * Gets an ID for use in serialization.
	 *
	 * @return an ID for use in serialization.  Typically camel-case, with first letter lower-case.
	 */
	String getId();
}
