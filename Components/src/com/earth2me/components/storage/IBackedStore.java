package com.earth2me.components.storage;


/**
 * Storage container backed by another storage method, such as a file. Must be
 * explicitly saved and loaded. Not concurrent.
 *
 * @author Zenexer
 */
public interface IBackedStore<T extends IStorable> extends IStore
{
	/**
	 * Saves the current dataset. All changes to the backing container will be
	 * overwritten.
	 *
	 * @return true if the operation is successful; otherwise, false.
	 */
	boolean save();

	/**
	 * Loads the dataset. All changes in memory will be overwritten.
	 *
	 * @return true if the operation is successful; otherwise, false.
	 */
	boolean load();
	
	T getData();
}
