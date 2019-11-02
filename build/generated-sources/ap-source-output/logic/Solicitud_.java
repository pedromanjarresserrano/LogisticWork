package logic;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Ciudad;
import logic.Cliente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(Solicitud.class)
public class Solicitud_ { 

    public static volatile SingularAttribute<Solicitud, Date> fecha;
    public static volatile SingularAttribute<Solicitud, Boolean> aceptada;
    public static volatile SingularAttribute<Solicitud, Cliente> idCliente;
    public static volatile SingularAttribute<Solicitud, Ciudad> idCiudadOrigen;
    public static volatile SingularAttribute<Solicitud, String> volumenMercancia;
    public static volatile SingularAttribute<Solicitud, Long> idSolicitud;
    public static volatile SingularAttribute<Solicitud, Ciudad> idCiudadDestino;
    public static volatile SingularAttribute<Solicitud, String> pesoMercancia;

}