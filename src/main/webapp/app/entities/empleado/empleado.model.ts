export interface IEmpleado {
  id?: number;
  dni?: string | null;
  nombre?: string | null;
  activo?: boolean | null;
  numeroVentas?: number | null;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public dni?: string | null,
    public nombre?: string | null,
    public activo?: boolean | null,
    public numeroVentas?: number | null
  ) {
    this.activo = this.activo ?? false;
  }
}

export function getEmpleadoIdentifier(empleado: IEmpleado): number | undefined {
  return empleado.id;
}
