package id.kputro.dragon.utils.applink

import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.singleton.DataSingleton

object Route {
  const val APP_SCHEME = "kputro"
  const val HTTP_SCHEME = "http"
  const val HTTPS_SCHEME = "https"

  const val CLOSE_PAGE = "close_page"
  const val CLOSE_APP = "close_app"
  const val NAVIGATION_BACK = "navigation_back"
  const val UPCOMING_FEATURES = "upcoming_features"

  const val PAGE_DASHBOARD = "page_dashboard"
  const val PAGE_SETTINGS = "page_settings"
  const val PAGE_PERMISSIONS = "page_permission"
  const val PAGE_NOTIFICATIONS = "page_notifications"
  const val PAGE_MENU_DETAILS = "page_menu_details"

  const val PAGE_APPLINK = "page_applink"

  const val PAGE_ABOUT_ME = "page_about_me"

  const val PAGE_LOGIN = "page_login"
  const val PAGE_PROFILE = "page_profile"
  const val PAGE_REGISTRATION = "page_registration"
}

fun getAppScheme(): String {
  return Route.APP_SCHEME + "://"
}

fun getClosePageAddrs(): String {
  return getAppScheme() + Route.CLOSE_PAGE
}

fun getDashboardPageAddrs(): String {
  return getAppScheme() + Route.PAGE_DASHBOARD
}

fun getApplinkPageAddrs(): String {
  return getAppScheme() + Route.PAGE_APPLINK
}

fun getUpcomingFeatureAddrs(): String {
  return getAppScheme() + Route.UPCOMING_FEATURES
}

fun getMenuDetailsPageAddrs(mModel: MenuItemModel): String {
  DataSingleton.get().selMenuItem = mModel
  return getAppScheme() + Route.PAGE_MENU_DETAILS
}