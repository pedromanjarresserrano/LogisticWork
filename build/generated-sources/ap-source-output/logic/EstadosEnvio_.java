package logic;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Empleado;
import logic.Envio;
import logic.Estado;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(EstadosEnvio.class)
public class EstadosEnvio_ { 

    public static volatile SingularAttribute<EstadosEnvio, Date> fecha;
    public static volatile SingularAttribute<EstadosEnvio, Estado> idEstado;
    public static volatile SingularAttribute<EstadosEnvio, Long> idEstadoEnvio;
    public static volatile SingularAttribute<EstadosEnvio, Empleado> idEmpleado;
    public static volatile SingularAttribute<EstadosEnvio, Envio> idEnvio;

}