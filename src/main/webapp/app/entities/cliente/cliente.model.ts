export interface ICliente {
  id?: number;
  dni?: string | null;
  nombre?: string | null;
  numeroCompras?: number | null;
  tier?: number | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public dni?: string | null,
    public nombre?: string | null,
    public numeroCompras?: number | null,
    public tier?: number | null
  ) {}
}

export function getClienteIdentifier(cliente: ICliente): number | undefined {
  return cliente.id;
}
