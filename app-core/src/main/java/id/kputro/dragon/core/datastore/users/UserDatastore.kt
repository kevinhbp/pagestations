package id.kputro.dragon.core.datastore.users

import com.orhanobut.hawk.Hawk
import id.kputro.dragon.core.data.entity.MemberInfo

/**
 * Datastore for USER usecase
 *
 * @author Kevin Putro
 */
interface UserDatastoreContract {
  fun getMemberInfo(): MemberInfo
  fun saveMemberInfo(newMemberInfo: MemberInfo)
}

class UserDatastore : UserDatastoreContract {

  override fun getMemberInfo(): MemberInfo {
    var mResult: MemberInfo? = null
    if (Hawk.contains(MEMBER_INFO)) {
      mResult = Hawk.get(MEMBER_INFO)
    }
    if (mResult == null) {
      mResult = MemberInfo.init("")
      Hawk.put(MEMBER_INFO, mResult)
    }
    return mResult
  }

  override fun saveMemberInfo(newMemberInfo: MemberInfo) {
    if (Hawk.contains(MEMBER_INFO)) {
      Hawk.delete(MEMBER_INFO)
    }
    Hawk.put(MEMBER_INFO, newMemberInfo)
  }
}

