
---

## **frontend/README.md**

```markdown
# Frontend - Sistema de Gestión de Órdenes

## Tecnologías
- Angular 17+
- Angular Router
- Angular Forms (Reactive Forms)
- RxJS (Observables)
- HttpClient
- Components Standalone

## Estructura del Proyecto
frontend/src/app/
├── app.component.ts
├── app.config.ts
├── app.routes.ts
├── auth/
│ ├── auth.service.ts
│ ├── auth.guard.ts
│ └── login/
│ └── login.component.ts
├── orders/
│ ├── order.service.ts
│ ├── models/
│ │ └── order.model.ts
│ ├── order-list/
│ │ └── order-list.component.ts
│ ├── order-detail/
│ │ └── order-detail.component.ts
│ └── order-create/
│ └── order-create.component.ts
└── interceptors/
└── auth.interceptor.ts


## Requisitos Previos

- Node.js 18+ y npm
- Angular CLI 17+
- Backend corriendo en `http://localhost:8080`

## Instalación

```bash
# Clonar el repositorio
git clone <repo-url>

# Navegar al directorio del frontend
cd frontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
ng serve

# La aplicación estará disponible en:
# http://localhost:4200