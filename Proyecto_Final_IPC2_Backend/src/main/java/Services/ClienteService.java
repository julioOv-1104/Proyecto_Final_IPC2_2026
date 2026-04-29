package Services;

import DAOs.ClienteDAO;

public class ClienteService {

    private ClienteDAO clienteDao = new ClienteDAO();

    public boolean cancelarProyecto(int id_proyecto) {//verifica si el proyecto es apto para ser cancelado

        String estado = clienteDao.consultarEstadoProyecto(id_proyecto);

        if (estado.equals("ABIERTO") || estado.equals("EN_REVISION")) {
            return clienteDao.cancelarProyecto(id_proyecto);
        }
        return false;
    }

}
