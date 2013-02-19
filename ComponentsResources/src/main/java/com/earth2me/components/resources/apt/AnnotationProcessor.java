package com.earth2me.components.resources.apt;

import com.earth2me.components.resources.Resource;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


/**
 * Processes Earth2Me annotations.
 *
 * @author Zenexer
 */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public final class AnnotationProcessor extends AbstractProcessor
{

	private static final transient String PACKAGE = "resources.localization";
	private static final transient String LOCALE_FILE_NAME = "en-US.locale";
	private transient Writer out;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv)
	{
		try
		{
			out = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, PACKAGE, LOCALE_FILE_NAME).openWriter();
		}
		catch (IOException ex)
		{
			final String message = "Unable to open locale file for writing.";
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
			throw new RuntimeException(message, ex);
		}

		super.init(processingEnv);
	}

	@Override
	public boolean process(Set<? extends TypeElement> set, RoundEnvironment re)
	{
		Set<? extends Element> resources = re.getElementsAnnotatedWith(Resource.class);

		boolean handled = false;
		for (Element element : resources)
		{
			handled |= processResource(element);
		}

		try
		{
			out.close();
		}
		catch (IOException ex)
		{
			processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, ex.getLocalizedMessage());
		}

		return handled;
	}

	private boolean processResource(final Element element)
	{
		return element.accept(new SimpleElementVisitor6<Boolean, Void>()
		{

			@Override
			protected Boolean defaultAction(Element e, Void p)
			{
				super.defaultAction(e, p);
				return false;
			}

			@Override
			public Boolean visitVariable(VariableElement e, Void p)
			{
				Object constant = e.getConstantValue();
				if (constant == null)
				{
					return super.visitVariable(e, p);
				}

				final int id = e.hashCode();
				final String value = constant.toString();
				try
				{
					out.append(String.format("%011d\t%s\r\n", id, value));
					out.flush();
				}
				catch (IOException ex)
				{
					throw new RuntimeException("Unable to write to locale file.", ex);
				}

				return true;
			}
		}, null);
	}
}
