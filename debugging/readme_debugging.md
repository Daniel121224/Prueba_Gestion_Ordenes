### Error 1

**Problema:** Se utiliza el operador `==` para comparar objetos `Long`, lo cual compara referencias de memoria en lugar de valores. Aunque funciona para números entre -128 y 127 por el caché de Java, falla completamente para IDs mayores, impidiendo encontrar órdenes en la lista.

**Consecuencia en producción:** El endpoint de actualización de estado devolverá 404 para la mayoría de las órdenes con IDs reales (generalmente > 127), dejando inoperante una funcionalidad crítica de la aplicación y generando errores constantes para los usuarios.



### Error 2

**Problema:** Se modifica el objeto en memoria (`order.setStatus(newStatus)`) pero no se actualiza en la fuente de datos (la lista `orders`), por lo que el cambio se pierde al finalizar la petición.

**Consecuencia en producción:** Los cambios de estado de órdenes nunca se guardan, los usuarios reciben confirmación de acciones que no se ejecutaron realmente, los datos quedan inconsistentes y el sistema pierde toda confiabilidad en la gestión de estados de órdenes.



### Error 3

**Problema:** El método captura `ExpiredJwtException` y retorna `true`, indicando que el token es válido cuando en realidad está expirado, anulando completamente el mecanismo de expiración.

**Consecuencia en producción:** Vulnerabilidad crítica de seguridad que permite a usuarios mantener sesiones activas indefinidamente con tokens expirados, posibilitando accesos no autorizados al sistema y exponiendo datos sensibles sin ninguna restricción temporal.