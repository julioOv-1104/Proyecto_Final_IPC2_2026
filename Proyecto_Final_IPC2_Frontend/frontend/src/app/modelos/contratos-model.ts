export interface ContratosModel {

    id_contrato: number;
    id_proyecto: number;
    fecha_inicio: Date;
    fecha_fin: Date;
    estado: string;
    monto: number;
    motivo_cancelacion: string;
    porcentaje: number;


}
