export interface ProyectoModel {

    id_proyecto: number;
    id_cliente: number;
    id_categoria: number;
    titulo: string;
    descripcion: string;
    presupuesto_max: number;
    fecha_limite: Date;
    estado: string;
    fecha_creacion: Date;
}
