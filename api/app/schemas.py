
from datetime import date
from decimal import Decimal
from typing import Literal

from sqlmodel import SQLModel, Field


# ==========================================
# ESQUEMAS DE CLIENTE
# ==========================================

class ClienteCreate(SQLModel):

    nombres: str = Field(min_length=2, max_length=60)
    apellidos: str = Field(min_length=2, max_length=60)
    documento: str = Field(min_length=5, max_length=20)
    telefono: str = Field(min_length=7, max_length=20)
    correo: str = Field(min_length=6, max_length=100)

# ==========================================
# ESQUEMAS DE VEHICULO
# ==========================================

class VehiculoCreate(SQLModel):

    placa: str = Field(min_length=5, max_length=10)
    marca: str = Field(min_length=2, max_length=40)
    modelo: str = Field(min_length=1, max_length=40)
    anio: int = Field(ge=1950, le=2100)
    color: str = Field(min_length=3, max_length=30)
    cliente_id: int = Field(gt=0)

# ==========================================
# ESQUEMAS DE REPARACION
# ==========================================

class ReparacionCreate(SQLModel):

    fecha_ingreso: date
    descripcion: str = Field(min_length=5, max_length=200)
    costo: Decimal = Field(ge=0, max_digits=10, decimal_places=2)
    estado: Literal[
        "Pendiente",
        "En proceso",
        "Finalizada",
        "Cancelada"
    ]
    observaciones: str = Field(min_length=3, max_length=300)
    vehiculo_id: int = Field(gt=0)



# ==========================================
# ESQUEMAS DE SALIDA
# ==========================================

class ClienteRead(SQLModel):

    id: int
    nombres: str
    apellidos: str
    documento: str
    telefono: str
    correo: str

class VehiculoRead(SQLModel):

    id: int
    placa: str
    marca: str
    modelo: str
    anio: int
    color: str
    cliente_id: int

class ReparacionRead(SQLModel):

    id: int
    fecha_ingreso: date
    descripcion: str
    costo: Decimal
    estado: str
    observaciones: str
    vehiculo_id: int

