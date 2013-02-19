package com.earth2me.components.core.storage;

import com.earth2me.annotations.Resource;
import com.earth2me.components.core.resources.R;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Dumper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;


/**
 * Persistently stores configuration data to a YAML file.
 *
 * @param <T> the type to serialize/store.
 * @author Zenexer
 */
public final class YamlStore<T extends IStorable> implements IBackedStore<T>
{
	private final static transient Map<Tag, Class<?>> classTags;
	@Resource
	private final static String ERROR_COULD_NOT_LOAD = "Error loading YAML file: {0}\n{1}";
	@Resource
	private final static String ERROR_COULD_NOT_WRITE = "Error writing YAML file: {0}\n{1}";
	@Resource
	private final static String ERROR_TYPE_MISMATCH = "Error loading YAML file: {0}\nThe file was loaded as an unexpected type.";
	private final transient Yaml yaml;
	private final transient File file;
	private final transient Class<?> type;
	private final transient boolean readonly;
	private T data;

	static
	{
		final Map<Tag, Class<?>> classTagsBuilder = new HashMap<>();
		classTagsBuilder.put(new Tag("!File"), File.class);
		classTagsBuilder.put(new Tag("!Locale"), String.class);
		classTags = Collections.unmodifiableMap(classTagsBuilder);
	}

	/**
	 * Instantiates a new YAML store. Does NOT load from the file. Defaults to readonly.
	 *
	 * @param file the file used to back the YAML store
	 * @param data an instance to initialize the store
	 */
	public YamlStore(File file, T data)
	{
		this(file, data, true);
	}

	/**
	 * Instantiates a new YAML store. Does NOT load from the file.
	 *
	 * @param file     the file used to back the YAML store
	 * @param data     an instance to initialize the store
	 * @param readonly true to prevent saving; otherwise false
	 */
	public YamlStore(File file, T data, boolean readonly)
	{
		this.file = file;
		this.data = data;
		this.readonly = readonly;
		this.type = data.getClass();

		// Configure the YAML emitter's options.
		final DumperOptions options = new DumperOptions();
		options.setIndent(2);
		options.setExplicitStart(true);
		options.setExplicitEnd(true);
		options.setAllowUnicode(true);
		options.setAllowReadOnlyProperties(true);
		options.setLineBreak(DumperOptions.LineBreak.WIN);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.AUTO);
		options.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);

		// Register class tag for this document.
		final Representer representer = new Representer();
		representer.addClassTag(type, new Tag("!" + data.getId()));

		// Register standard class tags.
		for (Tag tag : classTags.keySet())
		{
			representer.addClassTag(classTags.get(tag), tag);
		}

		// Register custom class tags.
		final Map<String, Class<?>> customTags = data.getCustomClassTags();
		for (String tag : customTags.keySet())
		{
			representer.addClassTag(customTags.get(tag), new Tag("!" + tag));
		}

		// All of that gets stored in the dumper:
		final Dumper dumper = new Dumper(representer, options);

		// Defines corresponding Java type for the document.
		final Constructor constructor = new Constructor(type);
		final Loader loader = new Loader(constructor);

		// Instantiate yaml with all the parameters we just defined.
		this.yaml = new Yaml(loader, dumper);
	}

	/**
	 * Save the data as to a YAML file.
	 *
	 * @return true if successful; otherwise, false
	 */
	@Override
	public boolean save()
	{
		if (readonly)
		{
			// This data shouldn't be written.
			return false;
		}

		try
		{
			// Save the YAML data to the file.
			try (FileWriter writer = new FileWriter(file))
			{
				writer.write(yaml.dump(getData()));
				writer.flush();
			}

			return true;
		}
		catch (Throwable ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, R.string(ERROR_COULD_NOT_WRITE), new Object[]
				{
					file.getPath(), ex.getLocalizedMessage()
				});
			return false;
		}
	}

	/**
	 * Load the data from a YAML file.
	 *
	 * @return true if successful; otherwise, false
	 */
	@Override
	public boolean load()
	{
		try
		{
			if (!file.exists())
			{
				return false;
			}
			try (FileReader reader = new FileReader(file))
			{
				final Object data = yaml.load(reader);

				if (data.getClass().equals(type))
				{
					this.data = (T)data;
				}
				else
				{
					Bukkit.getLogger().log(Level.SEVERE, R.string(ERROR_TYPE_MISMATCH), new Object[]
						{
							file.getPath()
						});
					return false;
				}
			}

			return true;
		}
		catch (Throwable ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, R.string(ERROR_COULD_NOT_LOAD), new Object[]
				{
					file.getPath(), ex.getLocalizedMessage()
				});
			return false;
		}
	}

	/**
	 * Gets the YAML-backed data.
	 *
	 * @return A mutable, serializable object housing the data
	 */
	@Override
	public final T getData()
	{
		return data;
	}

	@Override
	public void close()
	{
	}
}
