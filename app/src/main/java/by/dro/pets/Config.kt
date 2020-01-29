package by.dro.pets

class Config {
    companion object{
        const val MIN_TRANSITION_SDK = 21

        const val STORAGE_PATH_DOGS = "gs://pets-i.appspot.com/dogs"

        const val LINKEDIN_URL = "https://www.linkedin.com/in/yakovchik/"
        const val GITHUB_URL = "https://github.com/nypojekt"
        const val GOOGLE_PLAY_URL = "https://play.google.com/store/apps/dev?id=6437300643703400451"

        const val deepLinkPattern = "https://pets.dro.by/link/?link=https://pets.dro.by/app/?type%3D%s%26uid%3D%s&apn=by.dro.pets"
    }
}