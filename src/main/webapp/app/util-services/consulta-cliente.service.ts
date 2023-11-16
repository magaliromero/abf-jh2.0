import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({
  providedIn: 'root',
})
export class ConsultaClienteService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('/consulta-ruc/');
  private resourceObtenerNroFactura = this.applicationConfigService.getEndpointFor('/numero-factura/');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  consultarCliente(ruc: String) {
    return this.http.get(this.resourceUrl + ruc, { observe: 'response' });
  }
  consultaNumeroFac(timbrado: any, pe: any, sucursal: any) {
    return this.http.get(this.resourceObtenerNroFactura + `${timbrado}/${pe}/${sucursal}`, { observe: 'response' });
  }
}
