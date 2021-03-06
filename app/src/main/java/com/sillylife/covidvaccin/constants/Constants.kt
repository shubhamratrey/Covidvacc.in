package com.sillylife.covidvaccin.constants


object Constants {

    const val GALLERY: Int = 1000
    const val AUDIO_LIBRARY: Int = 1001
    const val FIREBASE_REMOTE_CONFIG_CACHE_EXPIRATION: Long = 3600

    const val AWS_BUCKET_NAME = "zoopzam"
    const val S3_BASE_URL = "https://zoopzam.s3.ap-south-1.amazonaws.com"

    const val HOME_PAGINATE: String = "home_paginate"

    const val KEY_EVENT_ACTION = "key_event_action"
    const val IMPRESSION = "impression"
    const val KEY_EVENT_EXTRA = "key_event_extra"
    const val IMMERSIVE_FLAG_TIMEOUT = 500L
    const val RECENTLY_LOWER_LIMIT = 6
    const val USER_PTR_ID = "userPtrId"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val ACTION_TYPE = "action_type"
    const val FROM_MY_WORKER = "fromMyWorker"
    const val NOTIFICATION_CHANNEL_ID = "KnockNockNotificationChannel"
    const val NOTIFICATION_DESC: String = "Network sync going on"
    const val PLEASE_ALLOW_LOCATION = "Location must be ON at all times to give you accurate safety updates."

    val EXTENSION_WHITELIST = arrayOf("JPG")

    interface SocialLinks {
        companion object {
            const val INSTAGRAM = "https://www.instagram.com//"
            const val FACEBOOK = "https://www.facebook.com/"
            const val TWITTER = "https://twitter.com/"
        }
    }

    interface CallbackActionType {
        companion object {
            const val WIDGET_PHOTO_CLICKED = "widget_photo_clicked"
            const val KNOCK_BACK = "knock_back"
            const val UPDATE_LOCATION = "update_location"
            const val SYNC_CONTACTS = "sync_contacts"
        }
    }

}