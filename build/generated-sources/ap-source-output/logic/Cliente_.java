package logic;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logic.Ciudad;
import logic.Login;
import logic.Tipoidentificacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-03T15:56:12")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Long> idCliente;
    public static volatile SingularAttribute<Cliente, String> direccion;
    public static volatile SingularAttribute<Cliente, String> identificacion;
    public static volatile SingularAttribute<Cliente, Login> idLogin;
    public static volatile SingularAttribute<Cliente, String> telefono;
    public static volatile SingularAttribute<Cliente, String> nombre;
    public static volatile SingularAttribute<Cliente, Tipoidentificacion> idTipoidentificacion;
    public static volatile SingularAttribute<Cliente, Ciudad> idCiudad;

}