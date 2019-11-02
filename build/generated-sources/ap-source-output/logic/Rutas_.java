package logic;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Ciudad;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(Rutas.class)
public class Rutas_ { 

    public static volatile SingularAttribute<Rutas, Ciudad> ciudad_origen;
    public static volatile SingularAttribute<Rutas, Ciudad> ciudad_destino;
    public static volatile SingularAttribute<Rutas, String> cantidad;
    public static volatile SingularAttribute<Rutas, Long> id;

}