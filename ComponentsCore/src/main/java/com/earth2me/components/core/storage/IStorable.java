package com.earth2me.components.core.storage;

import java.util.Map;


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

	/**
	 * Gets an immutable map of custom class tags to register with the YAML
	 * parser/emitter.  Tags exclude prefixing exclamation marks.
	 *
	 * @return an immutable map of custom class tags.
	 */
	Map<String, Class<?>> getCustomClassTags();
}
