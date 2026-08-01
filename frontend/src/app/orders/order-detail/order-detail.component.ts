import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../order.service';
import { AuthService } from '../../auth/auth.service';
import { Order } from '../models/order.model';

@Component({
  selector: 'app-order-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div>
      <h2>Order Detail</h2>
      <div *ngIf="loading">Loading...</div>
      <div *ngIf="error" class="error">{{ error }}</div>
      <div *ngIf="order && !loading && !error">
        <p><strong>ID:</strong> {{ order.id }}</p>
        <p><strong>Customer:</strong> {{ order.customerName }}</p>
        <p><strong>Total:</strong> {{ order.total | currency }}</p>
        <p><strong>Status:</strong> {{ order.status }}</p>

        <div *ngIf="isAdmin">
          <label>Change Status:</label>
          <select [(ngModel)]="newStatus" (change)="updateStatus()">
            <option value="PENDING">Pending</option>
            <option value="CONFIRMED">Confirmed</option>
            <option value="SHIPPED">Shipped</option>
            <option value="DELIVERED">Delivered</option>
          </select>
          <div *ngIf="statusError" class="error">{{ statusError }}</div>
        </div>

        <h3>Items</h3>
        <table>
          <thead>
            <tr><th>Name</th><th>Unit Price</th><th>Quantity</th><th>Subtotal</th></tr>
          </thead>
          <tbody>
            <tr *ngFor="let item of order.items">
              <td>{{ item.name }}</td>
              <td>{{ item.unitPrice | currency }}</td>
              <td>{{ item.quantity }}</td>
              <td>{{ item.unitPrice * item.quantity | currency }}</td>
            </tr>
          </tbody>
        </table>
        <button (click)="goBack()">Back</button>
      </div>
    </div>
  `,
  styles: [`
    .error { color: red; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    th { background-color: #f2f2f2; }
  `]
})
export class OrderDetailComponent implements OnInit {
  order: Order | null = null;
  loading = false;
  error = '';
  newStatus = '';
  statusError = '';
  isAdmin = false;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    const username = this.authService.getUsername();
    this.isAdmin = username === 'admin';
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) this.loadOrder(id);
  }

  loadOrder(id: number) {
    this.loading = true;
    this.error = '';
    this.orderService.getOrder(id).subscribe({
      next: data => {
        this.order = data;
        this.newStatus = data.status;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'Failed to load order';
      }
    });
  }

  updateStatus() {
    if (!this.order || this.newStatus === this.order.status) return;
    this.statusError = '';
    this.orderService.updateOrderStatus(this.order.id, this.newStatus).subscribe({
      next: updated => {
        this.order = updated;
        this.newStatus = updated.status;
      },
      error: err => {
        if (err.status === 409) this.statusError = 'Invalid status transition';
        else if (err.status === 404) this.statusError = 'Status not recognized';
        else this.statusError = 'Error updating status';
        this.newStatus = this.order!.status; // revertir
      }
    });
  }

  goBack() {
    this.router.navigate(['/orders']);
  }
}