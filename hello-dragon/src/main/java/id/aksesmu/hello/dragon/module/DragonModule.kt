package id.aksesmu.hello.dragon.module

/**
 * Dragon Router Module
 * Gather/help compose Router definitions
 *
 * @author Kevin Putro
 */
class DragonModule {

  private val mMappings: HashMap<String, HashMap<String, Class<*>>> = hashMapOf()

  private val registeredSchemes: ArrayList<String> = arrayListOf()

  fun registerActivity(scheme: String, host: String, cls: Class<*>) {
    val sm = getInnerMappings(scheme)
    if (sm == null) { // register new scheme
      val newMap = HashMap<String, Class<*>>()
      newMap[host] = cls
      mMappings[scheme] = newMap
      registeredSchemes.add(scheme)
      return
    }

    sm[host] = cls
  }

  fun findClass(scheme: String?, host: String?): Class<*>? {
    if (scheme.isNullOrEmpty()) return null
    if (host.isNullOrEmpty()) return null
    val inner = getInnerMappings(scheme) ?: return null
    return getClass(inner, host)
  }

  fun isSchemeSupported(mScheme: String): Boolean {
    return registeredSchemes.contains(mScheme)
  }

  private fun getInnerMappings(scheme: String): HashMap<String, Class<*>>? {
    return mMappings[scheme]
  }

  private fun getClass(innerMappings: HashMap<String, Class<*>>, host: String): Class<*>? {
    return innerMappings[host]
  }
}