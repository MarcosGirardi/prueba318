package prueba318

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class SecRole implements Serializable {

	private static final long serialVersionUID = 1

	String	authority
	String	cretaedBy
	Date		created
	String	lastModifiedBy
	Date		lastModified

	static constraints = {
		authority blank: false, unique: true
		cretaedBy (nullable:true)
		created (nullable:true)
		lastModifiedBy (nullable:true)
		lastModified (nullable:true)
	}

	String toString() {"${this.authority}" }

	def beforeInsert() {
		created = new Date()
		lastModified = created
		//cretaedBy = getPrincipal().toString()
		//lastModifiedBy = cretaedBy
	}

	def beforeUpdate() {
		/*if (isDirty('password')) {
			encodePassword()
		}*/
	}

	static mapping = {
		cache true
	}
}
