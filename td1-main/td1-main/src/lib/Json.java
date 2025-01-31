package lib;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.*;

import helpers.CaseUtils;


public class Json {

	public String write(Object instance) {
		
		if(instance==null) {
			return null;
		}
		if(Number.class.isAssignableFrom(instance.getClass()) || Boolean.class.isAssignableFrom(instance.getClass())) {
			return instance.toString();
		}
		if(instance.getClass().equals(String.class)) {
			return"\""+ instance.toString()+"\"";
		}
		if(instance.getClass().getComponentType()!=null) {
			return "[" + Arrays.stream((Object[])instance)
								.map(e -> write(e))
								.collect(Collectors.joining(","))
								+ "]";
			}
		final List<Method> getters = new ArrayList<Method>();
		Class<?> clazz = instance.getClass();
		while(!clazz.equals(Object.class)) {
			getters.addAll(
				Arrays.stream(clazz.getDeclaredMethods())
				// on ne prend en compte que les getters
				// (i.e. les méthodes getXXX() sans argument)
				.filter(m -> m.getName().startsWith("get"))
				.filter(m -> m.getParameterCount() == 0)
				// gestion de l'absence d'annotation @JsonIgnore
				.filter(m -> m.getAnnotation(JsonIgnore.class) == null)
				// attention au doublons introduits par l'héritage
				// on ignore tout getter qui a déjà été traité
				// dans une sous-classe pour éviter ça 👇
				//  {"Prop":0.5772,"Prop":0.1.414, ...}
				// mais pas possible de filtrer sur l'annotation @Override
				// puisqu'elle a une rétention SOURCE uniquement :'(
				.filter(m -> getters.stream().filter(g -> m.getName().equals(g.getName())).findAny().isEmpty())
				.collect(Collectors.toList())
			);
			clazz = clazz.getSuperclass();
		}

		// conversion tableau de getters -> json
		return "{" + getters.stream().map(m -> {

				final JsonProperty anotation = (JsonProperty)m.getAnnotation(JsonProperty.class);
				final String jsonPropName = CaseUtils.camelCaseToSnakeCase(anotation != null ? anotation.value() : m.getName().substring(3));
				try {
					// requis pour introspection
					m.setAccessible(true);
					// appel de la méthode sur l'instance
					final Object value = m.invoke(instance);
					return "\"" + jsonPropName + "\":" + write(value);
				} catch(Exception e) {
					throw new RuntimeException(e);
				}
			})
			.collect(Collectors.joining(",")) + "}";

	
		return null;
		
	}

}
