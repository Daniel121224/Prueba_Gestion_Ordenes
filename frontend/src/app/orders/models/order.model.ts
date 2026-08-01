export interface Item {
  name: string;
  unitPrice: number;
  quantity: number;
}

export interface Order {
  id: number;
  customerName: string;
  items: Item[];
  total: number;
  status: string;
}

export interface StatusUpdateRequest {
  status: string;
}