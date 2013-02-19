package com.earth2me.components.core;


/**
 * @author Zenexer
 */
public abstract class Component
{
	private final IContext context;
	
	protected Component(final IContext context)
	{
		this.context = context;
	}
	
	protected final IContext getContext()
	{
		return context;
	}
	
	public void onLoad()
	{
	}
	
	public void onUnload()
	{
	}
}
