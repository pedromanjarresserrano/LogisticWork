package logic;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Camiones;
import logic.Ciudad;
import logic.Empleado;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(Camion.class)
public class Camion_ { 

    public static volatile SingularAttribute<Camion, BigInteger> volumenCapacidad;
    public static volatile ListAttribute<Camion, Camiones> camioness;
    public static volatile SingularAttribute<Camion, Ciudad> idCiudadActual;
    public static volatile SingularAttribute<Camion, Empleado> idEmpleado;
    public static volatile SingularAttribute<Camion, Long> idCamion;
    public static volatile SingularAttribute<Camion, String> modelo;
    public static volatile SingularAttribute<Camion, BigInteger> pesoCapacidad;
    public static volatile SingularAttribute<Camion, String> placa;
    public static volatile SingularAttribute<Camion, Boolean> disponible;

}