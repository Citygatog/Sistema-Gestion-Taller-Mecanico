
from datetime import date
from decimal import Decimal

from sqlmodel import SQLModel, Field, Relationship


# MODELO CLIENTE
class Cliente(SQLModel, table=True):

    id: int | None = Field(default=None, primary_key=True)
    nombres: str = Field(max_length=60)
    apellidos: str = Field(max_length=60)
    documento: str = Field(max_length=20, unique=True, index=True)
    telefono: str = Field(max_length=20)
    correo: str = Field(max_length=100)

    # Relacion: un cliente puede tener varios vehiculos
    vehiculos: list["Vehiculo"] = Relationship(back_populates="cliente")


# MODELO VEHICULO
class Vehiculo(SQLModel, table=True):

    id: int | None = Field(default=None, primary_key=True)
    placa: str = Field(max_length=10, unique=True, index=True)
    marca: str = Field(max_length=40)
    modelo: str = Field(max_length=40)
    anio: int
    color: str = Field(max_length=30)

    # Llave foranea hacia Cliente
    cliente_id: int = Field(foreign_key="cliente.id")

    # Relaciones
    cliente: Cliente = Relationship(back_populates="vehiculos")
    reparaciones: list["Reparacion"] = Relationship(back_populates="vehiculo")


# MODELO REPARACION
class Reparacion(SQLModel, table=True):

    id: int | None = Field(default=None, primary_key=True)
    fecha_ingreso: date
    descripcion: str = Field(max_length=200)
    costo: Decimal = Field(decimal_places=2, max_digits=10)
    estado: str = Field(max_length=20)
    observaciones: str = Field(max_length=300)

    # Llave foranea hacia Vehiculo
    vehiculo_id: int = Field(foreign_key="vehiculo.id")

    # Relacion con Vehiculo
    vehiculo: Vehiculo = Relationship(back_populates="reparaciones")
