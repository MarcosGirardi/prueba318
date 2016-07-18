package prueba318

class Usuario {

  String    nombre
  String    apellido
  Integer   dni
  String    direccion

  Integer   telCasa
  Integer   telTrabajo
  Integer   telTutor

  String    mailPart
  String    mailTrabajo
  String    mailOtro

    static constraints = {
    nombre      (nullable:true)
    apellido    (nullable:true)
    dni         (nullable:true)
    direccion   (nullable:true)
    telCasa     (nullable:true)
    telTrabajo  (nullable:true)
    telTutor    (nullable:true)
    mailPart    (nullable:true)
    mailTrabajo (nullable:true)
    mailOtro    (nullable:true)
    }
}
