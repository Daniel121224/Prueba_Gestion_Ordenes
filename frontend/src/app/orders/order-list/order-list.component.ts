import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../order.service';
import { AuthService } from '../../auth/auth.service';
import { Order } from '../models/order.model';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  template: `
    <div>
      <h2>Orders</h2>
      <div>
        <button (click)="goToCreate()">New Order</button>
        <button (click)="logout()">Logout</button>
      </div>
      <div>
        <label>Filter by status:</label>
        <select [(ngModel)]="selectedStatus" (ngModelChange)="applyFilter()">
          <option value="">All</option>
          <option value="PENDING">Pending</option>
          <option value="CONFIRMED">Confirmed</option>
          <option value="SHIPPED">Shipped</option>
          <option value="DELIVERED">Delivered</option>
        </select>
      </div>
      <div *ngIf="loading">Loading...</div>
      <div *ngIf="error" class="error">{{ error }}</div>
      <table *ngIf="!loading && !error">
        <thead>
          <tr><th>ID</th><th>Customer</th><th>Total</th><th>Status</th><th>Actions</th></tr>
        </thead>
        <tbody>
          <tr *ngFor="let order of filteredOrders">
            <td>{{ order.id }}</td>
            <td>{{ order.customerName }}</td>
            <td>{{ order.total | currency }}</td>
            <td><span class="badge" [ngClass]="statusClass(order.status)">{{ order.status }}</span></td>
            <td><a [routerLink]="['/orders', order.id]">View</a></td>
          </tr>
        </tbody>
      </table>
    </div>
  `,
  styles: [`
    .badge { padding: 4px 8px; border-radius: 4px; color: white; }
    .badge-pending { background-color: #ffc107; }
    .badge-confirmed { background-color: #17a2b8; }
    .badge-shipped { background-color: #fd7e14; }
    .badge-delivered { background-color: #28a745; }
    .error { color: red; }
    table { width: 100%; border-collapse: collapse; }
    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    th { background-color: #f2f2f2; }
  `]
})
export class OrderListComponent implements OnInit {
  orders: Order[] = [];
  filteredOrders: Order[] = [];
  selectedStatus = '';
  loading = false;
  error = '';

  constructor(
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.loading = true;
    this.error = '';
    this.orderService.getOrders().subscribe({
      next: data => {
        this.orders = data;
        this.applyFilter();
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'Failed to load orders';
      }
    });
  }

  applyFilter() {
    this.filteredOrders = this.selectedStatus
      ? this.orders.filter(o => o.status === this.selectedStatus)
      : this.orders;
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      PENDING: 'badge-pending',
      CONFIRMED: 'badge-confirmed',
      SHIPPED: 'badge-shipped',
      DELIVERED: 'badge-delivered'
    };
    return map[status] || '';
  }

  goToCreate() {
    this.router.navigate(['/orders/new']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}