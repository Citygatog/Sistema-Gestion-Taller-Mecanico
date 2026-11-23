
from sqlmodel import SQLModel, Session, create_engine

# Direccion de nuestra base de datos SQLite
DATABASE_URL = "sqlite:///./taller.db"

# Configuracion de conexion
engine = create_engine(
    DATABASE_URL,
    connect_args={"check_same_thread": False}
)


# Crear las tablas de la base de datos
def crear_tablas():
    from app import models

    SQLModel.metadata.create_all(engine)


# Proporcionar una sesion para trabajar con la base de datos
def obtener_sesion():
    with Session(engine) as sesion:
        yield sesion
