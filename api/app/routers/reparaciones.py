
from fastapi import APIRouter, Depends, HTTPException, status
from sqlmodel import Session, select

from app.database import obtener_sesion
from app.models import Reparacion, Vehiculo
from app.schemas import ReparacionCreate, ReparacionRead


router = APIRouter(
    prefix="/reparaciones",
    tags=["Reparaciones"]
)


# 1. LISTAR Y FILTRAR REPARACIONES
@router.get(
    "/",
    response_model=list[ReparacionRead],
    summary="Listar y filtrar reparaciones"
)
def listar_reparaciones(
    estado: str | None = None,
    sesion: Session = Depends(obtener_sesion)
):

    consulta = select(Reparacion)

    if estado is not None:
        consulta = consulta.where(
            Reparacion.estado == estado
        )

    reparaciones = sesion.exec(consulta).all()

    return reparaciones


# 2. CONSULTAR UNA REPARACION POR ID
@router.get(
    "/{reparacion_id}",
    response_model=ReparacionRead,
    summary="Consultar una reparación por su ID"
)
def consultar_reparacion(
    reparacion_id: int,
    sesion: Session = Depends(obtener_sesion)
):

    reparacion = sesion.get(Reparacion, reparacion_id)

    if reparacion is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="La reparacion no existe"
        )

    return reparacion


# 3. REGISTRAR UNA REPARACION
@router.post(
    "/",
    response_model=ReparacionRead,
    status_code=status.HTTP_201_CREATED,
    summary="Registrar una nueva reparación"
)
def crear_reparacion(
    datos: ReparacionCreate,
    sesion: Session = Depends(obtener_sesion)
):

    # Verificar que el vehiculo exista
    vehiculo = sesion.get(Vehiculo, datos.vehiculo_id)

    if vehiculo is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El vehiculo asociado no existe"
        )

    nueva_reparacion = Reparacion.model_validate(datos)

    sesion.add(nueva_reparacion)

    sesion.commit()
    sesion.refresh(nueva_reparacion)

    return nueva_reparacion


# 4. REEMPLAZAR UNA REPARACION
@router.put(
    "/{reparacion_id}",
    response_model=ReparacionRead,
    summary="Actualizar los datos de una reparación"
)
def reemplazar_reparacion(
    reparacion_id: int,
    datos: ReparacionCreate,
    sesion: Session = Depends(obtener_sesion)
):

    reparacion = sesion.get(Reparacion, reparacion_id)

    if reparacion is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="La reparacion no existe"
        )

    # Verificar que el vehiculo exista
    vehiculo = sesion.get(Vehiculo, datos.vehiculo_id)

    if vehiculo is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El vehiculo asociado no existe"
        )

    reparacion.sqlmodel_update(datos.model_dump())

    sesion.add(reparacion)
    sesion.commit()
    sesion.refresh(reparacion)

    return reparacion


# 5. ELIMINAR UNA REPARACION
@router.delete(
    "/{reparacion_id}",
    status_code=status.HTTP_204_NO_CONTENT,
    summary="Eliminar una reparación"
)
def eliminar_reparacion(
    reparacion_id: int,
    sesion: Session = Depends(obtener_sesion)
):

    reparacion = sesion.get(Reparacion, reparacion_id)

    if reparacion is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="La reparacion no existe"
        )

    sesion.delete(reparacion)
    sesion.commit()

    return None
