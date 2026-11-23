
from fastapi import APIRouter, Depends, HTTPException, status
from sqlmodel import Session, select
from sqlalchemy.exc import IntegrityError

from app.database import obtener_sesion
from app.models import Vehiculo, Cliente, Reparacion
from app.schemas import VehiculoCreate, VehiculoRead, ReparacionRead


router = APIRouter(
    prefix="/vehiculos",
    tags=["Vehiculos"]
)


# 1. LISTAR TODOS LOS VEHICULOS
@router.get(
    "/",
    response_model=list[VehiculoRead],
    summary="Listar todos los vehículos"
)
def listar_vehiculos(sesion: Session = Depends(obtener_sesion)):

    vehiculos = sesion.exec(select(Vehiculo)).all()

    return vehiculos


# 2. CONSULTAR UN VEHICULO POR ID
@router.get(
    "/{vehiculo_id}",
    response_model=VehiculoRead,
    summary="Consultar un vehículo por su ID"
)
def consultar_vehiculo(
    vehiculo_id: int,
    sesion: Session = Depends(obtener_sesion)
):

    vehiculo = sesion.get(Vehiculo, vehiculo_id)

    if vehiculo is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El vehiculo no existe"
        )

    return vehiculo


# 3. REGISTRAR UN VEHICULO
@router.post(
    "/",
    response_model=VehiculoRead,
    status_code=status.HTTP_201_CREATED,
    summary="Registrar un nuevo vehículo"
)
def crear_vehiculo(
    datos: VehiculoCreate,
    sesion: Session = Depends(obtener_sesion)
):

    # Verificar que el cliente exista
    cliente = sesion.get(Cliente, datos.cliente_id)

    if cliente is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El cliente asociado no existe"
        )

    nuevo_vehiculo = Vehiculo.model_validate(datos)

    sesion.add(nuevo_vehiculo)

    try:
        sesion.commit()
        sesion.refresh(nuevo_vehiculo)

    except IntegrityError:
        sesion.rollback()
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="Ya existe un vehiculo con esa placa"
        )

    return nuevo_vehiculo


# 4. REEMPLAZAR UN VEHICULO
@router.put(
    "/{vehiculo_id}",
    response_model=VehiculoRead,
    summary="Actualizar los datos de un vehículo"
)
def reemplazar_vehiculo(
    vehiculo_id: int,
    datos: VehiculoCreate,
    sesion: Session = Depends(obtener_sesion)
):

    vehiculo = sesion.get(Vehiculo, vehiculo_id)

    if vehiculo is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El vehiculo no existe"
        )

    # Verificar que el nuevo cliente exista
    cliente = sesion.get(Cliente, datos.cliente_id)

    if cliente is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El cliente asociado no existe"
        )

    vehiculo.sqlmodel_update(datos.model_dump())

    try:
        sesion.add(vehiculo)
        sesion.commit()
        sesion.refresh(vehiculo)

    except IntegrityError:
        sesion.rollback()
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="La placa ya pertenece a otro vehiculo"
        )

    return vehiculo


# 5. ELIMINAR UN VEHICULO
@router.delete(
    "/{vehiculo_id}",
    status_code=status.HTTP_204_NO_CONTENT,
    summary="Eliminar un vehículo"
)
def eliminar_vehiculo(
    vehiculo_id: int,
    sesion: Session = Depends(obtener_sesion)
):

    vehiculo = sesion.get(Vehiculo, vehiculo_id)

    if vehiculo is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El vehiculo no existe"
        )

    # Evitar eliminar vehiculos que tengan reparaciones
    if vehiculo.reparaciones:
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="No se puede eliminar un vehiculo que tiene reparaciones"
        )

    sesion.delete(vehiculo)
    sesion.commit()

    return None


# 6. CONSULTAR LAS REPARACIONES DE UN VEHICULO
@router.get(
    "/{vehiculo_id}/reparaciones",
    response_model=list[ReparacionRead],
    summary="Consultar las reparaciones de un vehículo"
)
def listar_reparaciones_vehiculo(
    vehiculo_id: int,
    sesion: Session = Depends(obtener_sesion)
):

    vehiculo = sesion.get(Vehiculo, vehiculo_id)

    if vehiculo is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El vehiculo no existe"
        )

    reparaciones = sesion.exec(
        select(Reparacion).where(
            Reparacion.vehiculo_id == vehiculo_id
        )
    ).all()

    return reparaciones
