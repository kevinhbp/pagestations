package id.kputro.dragon.core.data.entity

import java.io.Serializable

data class MemberInfo(val createDate: Long = System.currentTimeMillis()) : Serializable {
  var fullname: String = ""
  var photo: String = ""

  var sex: String = ""
  var email: String = ""
  var phoneNumber: String = ""

  var lastUpdate: Long = System.currentTimeMillis()

  companion object {
    fun init(mFullname: String): MemberInfo {
      return MemberInfo().apply {
        this.fullname = mFullname
      }
    }
  }
}