package com.earth2me.components;

import com.earth2me.components.storage.IStorable;
import java.util.Collections;
import java.util.Map;
import lombok.Data;


/**
 * Stores settings common to all components.
 *
 * @author Zenexer
 */
@Data
public final class CommonSettings implements IStorable
{
	private String locale;

	@Override
	public String getId()
	{
		return "CommonSettings";
	}

	@Override
	public Map<String, Class<?>> getCustomClassTags()
	{
		return Collections.emptyMap();
	}
}
