package logic;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Camion;
import logic.Solicitud;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(Envio.class)
public class Envio_ { 

    public static volatile SingularAttribute<Envio, Date> fechaEntrega;
    public static volatile SingularAttribute<Envio, Date> fechaRecogida;
    public static volatile SingularAttribute<Envio, Long> idEnvio;
    public static volatile SingularAttribute<Envio, Solicitud> idSolicitud;
    public static volatile SingularAttribute<Envio, Camion> idCamion;

}