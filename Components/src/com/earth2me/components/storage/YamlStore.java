package com.earth2me.components.storage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;


/**
 * Persistently stores configuration data to a YAML file.
 *
 * @param <T> the type to serialize/store.
 * @author Zenexer
 */
public class YamlStore<T extends IStorable> implements IBackedStore
{

	private final transient Yaml yaml;
	private final transient File file;
	private final transient Class<?> type;
	private transient T data;

	/**
	 * Instantiates a new YAML store.  Does NOT load from the file.
	 *
	 * @param file the file used to back the YAML store.
	 * @param data an instance to initialize the store.
	 */
	public YamlStore(File file, T data)
	{
		this.file = file;
		this.data = data;
		this.type = data.getClass();

		// Configure the YAML parser/emitter.

		final DumperOptions options = new DumperOptions();
		options.setPrettyFlow(true);
		options.setIndent(2);
		options.setExplicitStart(true);
		options.setExplicitEnd(true);
		options.setAllowUnicode(true);
		options.setAllowReadOnlyProperties(true);
		options.setLineBreak(DumperOptions.LineBreak.WIN);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.AUTO);
		options.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);

		final Representer representer = new Representer();
		representer.addClassTag(type, new Tag("!" + getData().getId()));

		this.yaml = new Yaml(representer, options);
	}

	/**
	 * Save the data as to a YAML file.
	 *
	 * @return true if successful; otherwise, false.
	 */
	@Override
	public boolean save()
	{
		try
		{
			final FileWriter writer = new FileWriter(file);
			try
			{
				writer.write(yaml.dump(getData()));
				writer.flush();
			}
			finally
			{
				writer.close();
			}

			return true;
		}
		catch (Throwable ex)
		{
			return false;
		}
	}

	/**
	 * Load the data from a YAML file.
	 *
	 * @return true if successful; otherwise, false.
	 */
	@Override
	public boolean load()
	{
		try
		{
			final FileReader reader = new FileReader(file);
			try
			{
				yaml.loadAs(reader, type);
			}
			finally
			{
				reader.close();
			}

			return true;
		}
		catch (Throwable ex)
		{
			return false;
		}
	}

	/**
	 * Gets the YAML-backed data.
	 *
	 * @return A mutable, serializable object housing the data.
	 */
	public final T getData()
	{
		return data;
	}
}
