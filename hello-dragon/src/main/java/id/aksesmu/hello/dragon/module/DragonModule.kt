package id.aksesmu.hello.dragon.module

/**
 * Dragon Router Module
 * Gather/help compose Router definitions
 *
 * @author Kevin Putro
 */
class DragonModule(
  @PublishedApi
  internal val _createdAtStart: Boolean = false
) {

  val mappings: HashMap<String, Class<*>> = hashMapOf()

  fun registerActivity(host: String, cls: Class<*>) {
    mappings[host] = cls
  }
}