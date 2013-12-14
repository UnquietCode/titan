package com.thinkaurelius.faunus.mapreduce.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.thinkaurelius.faunus.FaunusEdge;
import com.thinkaurelius.faunus.FaunusElement;
import com.thinkaurelius.faunus.FaunusProperty;
import com.thinkaurelius.faunus.Tokens;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ElementPicker {

    protected ElementPicker() {
    }

    public static String getPropertyAsString(final FaunusElement element, final String key) {
        if (key.equals(Tokens._ID) || key.equals(Tokens.ID))
            return element.getId().toString();
        else if (key.equals(Tokens._PROPERTIES)) {
            final ListMultimap<String, Object> properties = ArrayListMultimap.create();
            for (final FaunusProperty property : element.getProperties()) {
                properties.put(property.getType().getName(), property.getValue());
            }
            properties.put(Tokens._ID, element.getId());
            if (element instanceof FaunusEdge)
                properties.put(Tokens._LABEL, ((FaunusEdge) element).getLabel());

            return properties.toString();
        } else if (key.equals(Tokens.LABEL) && element instanceof FaunusEdge) {
            return ((FaunusEdge) element).getLabel();
        } else {
            final Object value = element.getProperty(key);
            if (null != value)
                return value.toString();
            else
                return Tokens.NULL;
        }
    }

    public static Object getProperty(final FaunusElement element, final String key) {
        if (key.equals(Tokens._ID) || key.equals(Tokens.ID))
            return element.getId();
        else if (key.equals(Tokens._PROPERTIES)) {
            final ListMultimap<String, Object> properties = ArrayListMultimap.create();
            for (final FaunusProperty property : element.getProperties()) {
                properties.put(property.getType().getName(), property.getValue());
            }
            properties.put(Tokens._ID, element.getId());
            return properties;
        } else if (key.equals(Tokens.LABEL) && element instanceof FaunusEdge) {
            return ((FaunusEdge) element).getLabel();
        } else {
            return element.getProperty(key);
        }
    }
}
