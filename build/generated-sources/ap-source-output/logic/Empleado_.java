package logic;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Cargo;
import logic.Login;
import logic.Tipoidentificacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile SingularAttribute<Empleado, Cargo> idCargo;
    public static volatile SingularAttribute<Empleado, Long> idEmpleado;
    public static volatile SingularAttribute<Empleado, String> identificacion;
    public static volatile SingularAttribute<Empleado, Login> idLogin;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile SingularAttribute<Empleado, Tipoidentificacion> idTipodeidentificacion;

}