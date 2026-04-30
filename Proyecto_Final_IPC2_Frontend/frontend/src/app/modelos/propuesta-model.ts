export interface PropuestaModel {

    id_propuesta: number;
    id_proyecto: number;
    id_freelancer: number;
    monto: number;
    plazo_dias: number;
    descripcion: string;
    estado: string;
    fecha: string;
    motivo_rechazo: string;
    nombre_freelancer: string;
    calificacion_promedio: number;
}
