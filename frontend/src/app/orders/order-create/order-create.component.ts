import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-order-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  template: `
    <div>
      <h2>Create Order</h2>
      <form [formGroup]="orderForm" (ngSubmit)="onSubmit()">
        <div>
          <label>Customer Name</label>
          <input type="text" formControlName="customerName">
          <div *ngIf="orderForm.get('customerName')?.invalid && orderForm.get('customerName')?.touched" class="error">
            Customer name is required
          </div>
        </div>
        <div formArrayName="items">
          <h4>Items</h4>
          <div *ngFor="let item of items.controls; let i = index" [formGroupName]="i">
            <input type="text" formControlName="name" placeholder="Item name">
            <input type="number" formControlName="unitPrice" placeholder="Unit price">
            <input type="number" formControlName="quantity" placeholder="Quantity">
            <button type="button" (click)="removeItem(i)">Remove</button>
          </div>
          <button type="button" (click)="addItem()">Add Item</button>
        </div>
        <button type="submit" [disabled]="orderForm.invalid || loading">Create</button>
        <div *ngIf="loading">Creating...</div>
        <div *ngIf="error" class="error">{{ error }}</div>
      </form>
      <button (click)="goBack()">Cancel</button>
    </div>
  `,
  styles: [`
    .error { color: red; }
    div { margin-bottom: 10px; }
    input { margin-right: 10px; }
  `]
})
export class OrderCreateComponent {
  orderForm: FormGroup;
  loading = false;
  error = '';

  constructor(private fb: FormBuilder, private orderService: OrderService, private router: Router) {
    this.orderForm = this.fb.group({
      customerName: ['', Validators.required],
      items: this.fb.array([])
    });
    this.addItem();
  }

  get items() {
    return this.orderForm.get('items') as FormArray;
  }

  addItem() {
    this.items.push(this.fb.group({
      name: ['', Validators.required],
      unitPrice: [0, [Validators.required, Validators.min(1)]],
      quantity: [1, [Validators.required, Validators.min(1)]]
    }));
  }

  removeItem(index: number) {
    this.items.removeAt(index);
  }

  onSubmit() {
    if (this.orderForm.invalid) return;
    this.loading = true;
    this.error = '';
    this.orderService.createOrder(this.orderForm.value).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/orders']);
      },
      error: () => {
        this.loading = false;
        this.error = 'Failed to create order';
      }
    });
  }

  goBack() {
    this.router.navigate(['/orders']);
  }
}