package lib;


import java.lang.annotation.*;

@Target(ElementType.METHOD) // ne s'applique que sur les méthodes
@Retention(RetentionPolicy.RUNTIME) // l'annotation sera conservée pendant l'exécution
public @interface JsonIgnore {


}
