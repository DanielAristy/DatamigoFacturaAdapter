package com.example.datamigoapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Documento {

    private int imagen;
    private String nit_proveedor;
    private String valor_factura;
    private String fecha;
}
