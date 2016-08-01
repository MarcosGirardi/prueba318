package prueba318

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class UsuarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_USER', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def index(Integer max) {

      def asd = getPrincipal().authorities.join(", ").contains(Constants.USER_ROLE)
      log.println("${asd}")
      log.println(getAuthenticatedUser().id)
      log.println(getAuthenticatedUser().getClass())

      if (getPrincipal().authorities.join(", ").contains(Constants.USER_ROLE)){
        def forms
        def f = Usuario.createCriteria()
        forms = f{
          eq("id", getAuthenticatedUser().id)
        }
        respond forms, model:[usuarioCount: Usuario.count()]
      } else{
        params.max = Math.min(max ?: 10, 100)
        respond Usuario.list(params), model:[usuarioCount: Usuario.count()]
      }

    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def show(Usuario usuario) {

      def asd =  getPrincipal().authorities.join(", ").contains(Constants.USER_ROLE)
      log.println("${asd}")
      log.println(getAuthenticatedUser().id)
      log.println(getAuthenticatedUser().getClass())

      if ((asd && usuario.id == getAuthenticatedUser().id) || !asd){
          respond usuario
      }else {
          flash.message = message(code: 'No puede ver este usuario')
          redirect action:"index", method:"GET"
      }

    }

    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def create() {
        respond new Usuario(params)
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def save(Usuario usuario) {
        if (usuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuario.errors, view:'create'
            return
        }

        usuario.save flush:true

        def role = SecRole.findByAuthority(usuario.role)
          if (!usuario.authorities.contains(role)) {
            SecUserSecRole.create usuario, role
          }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect usuario
            }
            '*' { respond usuario, [status: CREATED] }
        }
    }

    @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
    def edit(Usuario usuario) {

      def asd =  getPrincipal().authorities.join(", ").contains(Constants.USER_ROLE)
      log.println("${asd}")
      log.println(getAuthenticatedUser().id)
      log.println(getAuthenticatedUser().getClass())

      if ((asd && usuario.id == getAuthenticatedUser().id) || !asd){
          respond usuario
      }else {
          flash.message = message(code: 'No puede modificar este usuario')
          redirect action:"index", method:"GET"
      }

    }

    @Transactional
    @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
    def update(Usuario usuario) {
        if (usuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuario.errors, view:'edit'
            return
        }

        usuario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect usuario
            }
            '*'{ respond usuario, [status: OK] }
        }
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def delete(Usuario usuario) {

        if (usuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        usuario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
