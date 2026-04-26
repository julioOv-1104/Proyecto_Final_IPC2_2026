export interface SolicitudModel {
    id_solicitud: number;
    id_usuario: number;
    nombre: string;
    descripcion: string;
    fecha: Date;
    estado: string;
}
