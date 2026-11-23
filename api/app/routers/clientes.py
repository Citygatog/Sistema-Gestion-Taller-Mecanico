
from fastapi import APIRouter, Depends, HTTPException, status
from sqlmodel import Session, select
from sqlalchemy.exc import IntegrityError

from app.database import obtener_sesion
from app.models import Cliente
from app.schemas import ClienteCreate, ClienteRead


router = APIRouter(
    prefix="/clientes",
    tags=["Clientes"]
)


# 1. LISTAR TODOS LOS CLIENTES
@router.get(
    "/",
    response_model=list[ClienteRead],
    summary="Listar todos los clientes"
)
def listar_clientes(sesion: Session = Depends(obtener_sesion)):

    clientes = sesion.exec(select(Cliente)).all()

    return clientes


# 2. CONSULTAR UN CLIENTE POR ID
@router.get(
    "/{cliente_id}",
    response_model=ClienteRead,
    summary="Consultar un cliente por su ID"
)
def consultar_cliente(
    cliente_id: int,
    sesion: Session = Depends(obtener_sesion)
):

    cliente = sesion.get(Cliente, cliente_id)

    if cliente is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El cliente no existe"
        )

    return cliente


# 3. REGISTRAR UN CLIENTE
@router.post(
    "/",
    response_model=ClienteRead,
    status_code=status.HTTP_201_CREATED,
    summary="Registrar un nuevo cliente"
)
def crear_cliente(
    datos: ClienteCreate,
    sesion: Session = Depends(obtener_sesion)
):

    nuevo_cliente = Cliente.model_validate(datos)

    sesion.add(nuevo_cliente)

    try:
        sesion.commit()
        sesion.refresh(nuevo_cliente)

    except IntegrityError:
        sesion.rollback()
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="Ya existe un cliente con ese documento"
        )

    return nuevo_cliente


# 4. REEMPLAZAR UN CLIENTE
@router.put(
    "/{cliente_id}",
    response_model=ClienteRead,
    summary="Actualizar los datos de un cliente"
)
def reemplazar_cliente(
    cliente_id: int,
    datos: ClienteCreate,
    sesion: Session = Depends(obtener_sesion)
):

    cliente = sesion.get(Cliente, cliente_id)

    if cliente is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="El cliente no existe"
        )

    cliente.sqlmodel_update(datos.model_dump())

    try:
        sesion.add(cliente)
        sesion.commit()
        sesion.refresh(cliente)

    except IntegrityError:
        sesion.rollback()
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="El documento ya pertenece a otro cliente"
        )

    return cliente


# 5. ELIMINAR UN CLIENTE
@router.delete(
    "/{cliente_id}",
    status_code=status.HTTP_204_NO_CONTENT,
    summary="Eliminar un cliente"
)
def eliminar_cliente(
    cliente_id: int,
    sesion: Session = Depends(obtener_sesion)
):

    cliente = sesion.get(Cliente, cliente_id)

    # Evitar eliminar clientes que tengan vehiculos asociados
    if cliente.vehiculos:
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail="No se puede eliminar un cliente que tiene vehiculos asociados"
        )

    sesion.delete(cliente)
    sesion.commit()

    return None
