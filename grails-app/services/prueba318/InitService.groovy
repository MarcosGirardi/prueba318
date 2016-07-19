package prueba318

import grails.transaction.Transactional

@Transactional
class InitService {

    def init() {
      log.println("321")

      def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
      def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)

      def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: 'admin',
                enabled: true).save(failOnError: true)

      if (!adminUser.authorities.contains(adminRole)) {
          SecUserSecRole.create adminUser, adminRole
        }

      def userUser = SecUser.findByUsername('user') ?: new SecUser(
                username: 'user',
                password: 'user',
                enabled: true).save(failOnError: true)

      if (!userUser.authorities.contains(userRole)) {
          SecUserSecRole.create userUser, userRole
        }

      log.println("321")


    }
}
