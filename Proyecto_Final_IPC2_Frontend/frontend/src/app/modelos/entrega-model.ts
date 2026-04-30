export interface EntregaModel {

    id_entrega: number;
    id_contrato: number;
    descripcion: string;
    archivo_url: string;
    fecha: Date;
    estado: string;
    motivo_rechazo: string;
}
