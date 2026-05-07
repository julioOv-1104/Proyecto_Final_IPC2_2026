package Services;

import DAOs.*;
import Modelos.Habilidad;
import java.util.ArrayList;

public class FreelancerService {

    private FreelancerDAO freelancerDao = new FreelancerDAO();
    private ClienteDAO clienteDao = new ClienteDAO();

    public boolean comprobarQueFreelancerSeaApto(int id_usuario, int id_proyecto) {

        int bandera = 0; 

        ArrayList<Habilidad> habilidadesFree = freelancerDao.obtenerHabilidadesDeFreelancer(id_usuario);
        ArrayList<Habilidad> habilidadesProyecto = clienteDao.obtenerHabilidadesDeProyecto(id_proyecto);
        //obtiene las habilidades del freelancer y del proyecto

        if (habilidadesFree == null || habilidadesProyecto == null) {
            return false;
        }//verifica que ninguna lista sea nula

        for (Habilidad h : habilidadesProyecto) {

            for (Habilidad h2 : habilidadesFree) {

                if (h.getNombre().equals(h2.getNombre())) {

                    bandera++;
                }

            }

        }
        
        if (freelancerDao.verificarPropuestaYaExistente(id_usuario, id_proyecto)) {
            
            bandera = 0;
            
        }

        return bandera > 0;//si es cero es porque no tiene ninguna habilidad requerida
        //devuelve true si tiene al menos una habilidad de las requeridas

    }
    
    public boolean retirarPropuesta(int id_propuesta, int id_proyecto){
        
        if (clienteDao.consultarEstadoProyecto(id_proyecto).equals("ABIERTO")) {
           return freelancerDao.retirarPropuesta(id_propuesta);//deja retirar la propuesta si el proyecto está abierto
        }
        
        return false;//el proyecto no esta abierto
        
    }
    
    public boolean senviarEntrega(int id_contrato, int id_proyecto, String descripcion, String url){
        
        if (freelancerDao.consultarEstadoContrato(id_contrato).equals("ACTIVO")) {
            
            if (freelancerDao.registrarEntrega(id_contrato, descripcion, url)) {
                
                clienteDao.cambiarEstadoProyecto("ENTREGA_PENDIENTE", id_proyecto);
                return true;
            //Envia la entrega y cambia el estado del proyecto si el contrato esta activo
            }
            
        }
        
            return false;

    }

}
