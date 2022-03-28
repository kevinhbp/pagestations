package id.kputro.dragon.utils.applink

object Route {
  const val APP_SCHEME = "kputro"
  const val HTTP_SCHEME = "http"
  const val HTTPS_SCHEME = "https"

  const val CLOSE_PAGE = "close_page"
  const val CLOSE_APP = "close_app"
  const val NAVIGATION_BACK = "navigation_back"

  const val PAGE_DASHBOARD = "page_dashboard"
  const val PAGE_SETTINGS = "page_settings"
  const val PAGE_PERMISSIONS = "page_permission"
  const val PAGE_NOTIFICATIONS = "page_notifications"
  const val PAGE_BROWSER = "page_browser"

  const val PAGE_APPLINK = "page_applink"
  const val PAGE_APPLINK_SAVED = "page_applink_saved"

  const val PAGE_ABOUT_ME = "page_about_me"

  const val PAGE_LOGIN = "page_login"
  const val PAGE_PROFILE = "page_profile"
  const val PAGE_REGISTRATION = "page_registration"
}

fun getAppScheme(): String {
  return Route.APP_SCHEME + "://"
}

fun getDashboardPageApplink(): String {
  return getAppScheme() + Route.PAGE_DASHBOARD
}