from fastapi import FastAPI

from app.database import crear_tablas
from app.routers import clientes, vehiculos, reparaciones


app = FastAPI(
    title="Sistema de gestión de taller mecánico",
    description="API REST para gestionar clientes, vehículos y reparaciones.",
    version="1.0.0"
)


@app.on_event("startup")
def iniciar_base_datos():
    crear_tablas()


# Registrar router de clientes, vehiculos y reparaciones
app.include_router(clientes.router)
app.include_router(vehiculos.router)
app.include_router(reparaciones.router)


@app.get("/")
def inicio():
    return {
        "mensaje": "Bienvenido al sistema de gestión del taller mecánico",
        "estado": "API funcionando correctamente"
    }
