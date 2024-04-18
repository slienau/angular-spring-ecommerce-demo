import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderHistory } from '../common/order-history';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class OrderHistoryService {
  private orderUrl = `${environment.apiUrl}/orders`;

  constructor(private httpClient: HttpClient) {}

  getOrderHistory(email: string): Observable<GetResponseOrderHistory> {
    return this.httpClient.get<GetResponseOrderHistory>(
      `${this.orderUrl}/search/findByCustomerEmailOrderByDateCreatedDesc?email=${email}`,
    );
  }
}

interface GetResponseOrderHistory {
  _embedded: {
    orders: OrderHistory[];
  };
}
