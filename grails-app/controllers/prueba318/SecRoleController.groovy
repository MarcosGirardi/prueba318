package prueba318

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class SecRoleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SecRole.list(params), model:[secRoleCount: SecRole.count()]
    }

    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def show(SecRole secRole) {
        respond secRole
    }

    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def create() {
        respond new SecRole(params)
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def save(SecRole secRole) {
        if (secRole == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (secRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secRole.errors, view:'create'
            return
        }

        secRole.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'secRole.label', default: 'SecRole'), secRole.id])
                redirect secRole
            }
            '*' { respond secRole, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def edit(SecRole secRole) {
        respond secRole
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def update(SecRole secRole) {
        if (secRole == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (secRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secRole.errors, view:'edit'
            return
        }

        secRole.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'secRole.label', default: 'SecRole'), secRole.id])
                redirect secRole
            }
            '*'{ respond secRole, [status: OK] }
        }
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def delete(SecRole secRole) {

        if (secRole == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        secRole.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'secRole.label', default: 'SecRole'), secRole.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'secRole.label', default: 'SecRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
