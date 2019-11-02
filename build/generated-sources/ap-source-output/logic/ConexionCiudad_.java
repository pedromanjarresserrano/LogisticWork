package logic;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Ciudad;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(ConexionCiudad.class)
public class ConexionCiudad_ { 

    public static volatile SingularAttribute<ConexionCiudad, Long> idConexionCiudad;
    public static volatile SingularAttribute<ConexionCiudad, BigInteger> distancia;
    public static volatile SingularAttribute<ConexionCiudad, Ciudad> idCiudadB;
    public static volatile SingularAttribute<ConexionCiudad, Ciudad> idCiudadA;

}