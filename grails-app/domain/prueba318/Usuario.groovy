package prueba318

class Usuario extends SecUser{

  String    nombre
  String    apellido
  Integer   dni
  String    direccion

  Integer   telCasa
  Integer   telCelular
  Integer   telTrabajo
  Integer   telTutor

  String    mailPart
  String    mailTrabajo
  String    mailOtro

  static belongsTo = [role: SecRole]

    static constraints = {
      role        ()
      nombre      (nullable:true)
      apellido    (nullable:true)
      dni         (nullable:true)
      direccion   (nullable:true)
      telCasa     (nullable:true)
      telCelular  (nullable:true)
      telTrabajo  (nullable:true)
      telTutor    (nullable:true)
      mailPart    (nullable:true)
      mailTrabajo (nullable:true)
      mailOtro    (nullable:true)

    }

    String toString() {"${this.nombre} ${this.apellido}" }

}
