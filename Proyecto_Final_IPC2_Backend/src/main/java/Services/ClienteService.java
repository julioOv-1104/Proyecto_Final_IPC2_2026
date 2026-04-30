package Services;

import DAOs.ClienteDAO;
import Modelos.Propuesta;

public class ClienteService {

    private ClienteDAO clienteDao = new ClienteDAO();

    public boolean cancelarProyecto(int id_proyecto) {//verifica si el proyecto es apto para ser cancelado

        String estado = clienteDao.consultarEstadoProyecto(id_proyecto);

        if (estado.equals("ABIERTO") || estado.equals("EN_REVISION")) {
            return clienteDao.cancelarProyecto(id_proyecto);
        }
        return false;
    }

    public boolean aceptarPropuesta(int id_propuesta, int id_usuario) {

        Propuesta obtenida = clienteDao.obtenerPropuestaEspecifica(id_propuesta);

        if (obtenida == null) {
            return false;

        } else if (clienteDao.comprobarSaldoCliente(id_usuario, obtenida.getMonto())) {//si tiene suficiente saldo

            if (clienteDao.aceptarPropuesta(id_propuesta)) {

                if (clienteDao.bloquearSaldoCliente(obtenida.getMonto(), id_usuario)) {//se bloquea el saldo del cliente

                    return clienteDao.crearContrato(obtenida);//despues de aceptar una propuesta se crea un contrato
                }

            }

        }

        return false;

    }

    public boolean rechazarEntrega(int id_entrega, String motivo, int id_proyecto) {

        clienteDao.cambiarEstadoProyecto("EN_PROGRESO", id_proyecto);

        return clienteDao.rechazarEntrega(id_entrega, motivo);

    }

}
