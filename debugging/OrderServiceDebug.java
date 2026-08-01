package com.example.ordenes.debugging;
//MÉTODO 1
public Order updateStatus(Long id, String newStatus) {
    //ERROR 1: Se usaba == para comparar Longs, lo cual falla para valores > 127
    //ERROR 2: No se persiste el cambio, solo se modifica el objeto en memoria
    Order order = orders.stream()
            .filter(o -> o.getId().equals(id)) // CORREGIDO: usar .equals() para Long
            .findFirst()
            .orElseThrow(() -> new OrderNotFoundException(id));

    //ERROR 2 CORREGIDO: Persistir el cambio (en este caso, reemplazar en la lista)
    //Como usamos lista en memoria, eliminamos y volvemos a agregar
    orders.removeIf(o -> o.getId().equals(id));
    order.setStatus(newStatus);
    orders.add(order);

    return order;
}

//MÉTODO 2
public boolean isTokenValid(String token) {
    try {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return true;
    } catch (ExpiredJwtException e) {
        //ERROR 3: Retornar true para token expirado es un problema de seguridad
        return false; //CORREGIDO: token expirado debe retornar false
    } catch (JwtException e) {
        return false;
    }
}
